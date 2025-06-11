package com.hbm.explosion;

import com.hbm.config.BombConfig;
import com.hbm.interfaces.IExplosionRay;
import com.hbm.main.MainRegistry;
import com.hbm.util.ChunkKey;
import com.hbm.util.ConcurrentBitSet;
import com.hbm.util.SubChunkSnapshot;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.DoubleAdder;

/**
 * Threaded DDA raytracer for the nuke explosion.
 *
 * @author mlbv
 */
public class ExplosionNukeRayParallelized implements IExplosionRay {

	private static final int WORLD_HEIGHT = 256;
	private static final int BITSET_SIZE = 16 * WORLD_HEIGHT * 16;

	protected final World world;
	private final double explosionX, explosionY, explosionZ;
	private final int originX, originY, originZ;
	private final int strength;
	private final int radius;

	private volatile List<Vec3> directions;
	private final CompletableFuture<List<Vec3>> directionsFuture;
	private final ConcurrentMap<ChunkCoordIntPair, ConcurrentBitSet> destructionMap;
	private final ConcurrentMap<ChunkCoordIntPair, ChunkDamageAccumulator> accumulatedDamageMap;

	private final ConcurrentMap<ChunkKey, SubChunkSnapshot> snapshots;

	private final BlockingQueue<RayTask> rayQueue;
	private final BlockingQueue<ChunkKey> cacheQueue;
	private final ExecutorService pool;
	private final CountDownLatch latch;
	private final Thread latchWatcherThread;
	private final List<ChunkCoordIntPair> orderedChunks;
	private volatile boolean collectFinished = false;
	private volatile boolean consolidationFinished = false;
	private volatile boolean destroyFinished = false;

	public ExplosionNukeRayParallelized(World world, double x, double y, double z, int strength, int speed, int radius) {
		this.world = world;
		this.explosionX = x;
		this.explosionY = y;
		this.explosionZ = z;

		this.originX = (int) Math.floor(x);
		this.originY = (int) Math.floor(y);
		this.originZ = (int) Math.floor(z);

		this.strength = strength;
		this.radius = radius;

		int rayCount = Math.max(0, (int) (2.5 * Math.PI * strength * strength));

		this.latch = new CountDownLatch(rayCount);
		this.destructionMap = new ConcurrentHashMap<>();
		this.accumulatedDamageMap = new ConcurrentHashMap<>();
		this.snapshots = new ConcurrentHashMap<>();
		this.orderedChunks = new ArrayList<>();

		this.rayQueue = new LinkedBlockingQueue<>();
		this.cacheQueue = new LinkedBlockingQueue<>();

		int workers = Math.max(1, Runtime.getRuntime().availableProcessors() - 1);
		this.pool = Executors.newWorkStealingPool(workers);
		this.directionsFuture = CompletableFuture.supplyAsync(() -> generateSphereRays(rayCount));

		for (int i = 0; i < rayCount; i++) rayQueue.add(new RayTask(i));
		for (int i = 0; i < workers; i++) pool.submit(new Worker());

		this.latchWatcherThread = new Thread(() -> {
			try {
				latch.await();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				collectFinished = true;
				if (BombConfig.explosionAlgorithm == 2) {
					pool.submit(this::runConsolidation);
				} else {
					consolidationFinished = true;
				}
			}
		}, "ExplosionNuke-LatchWatcher-" + System.nanoTime());
		this.latchWatcherThread.setDaemon(true);
		this.latchWatcherThread.start();
	}

	private static float getNukeResistance(Block b) {
		if (b.getMaterial().isLiquid()) return 0.1F;
		if (b == Blocks.sandstone) return Blocks.stone.getExplosionResistance(null);
		if (b == Blocks.obsidian) return Blocks.stone.getExplosionResistance(null) * 3;
		return b.getExplosionResistance(null);
	}

	@Override
	public void cacheChunksTick(int timeBudgetMs) {
		if (collectFinished || this.cacheQueue == null) return;

		final long deadline = System.nanoTime() + (timeBudgetMs * 1_000_000L);
		while (System.nanoTime() < deadline) {
			ChunkKey ck = cacheQueue.poll();
			if (ck == null) break;
			snapshots.computeIfAbsent(ck, k -> SubChunkSnapshot.getSnapshot(world, k, BombConfig.chunkloading));
		}
	}

	@Override
	public void destructionTick(int timeBudgetMs) {
		if (!collectFinished || !consolidationFinished || destroyFinished) return; // Added consolidationFinished check

		final long deadline = System.nanoTime() + timeBudgetMs * 1_000_000L;

		if (orderedChunks.isEmpty() && !destructionMap.isEmpty()) {
			orderedChunks.addAll(destructionMap.keySet());
			orderedChunks.sort(Comparator.comparingInt(c -> Math.abs((originX >> 4) - c.chunkXPos) + Math.abs((originZ >> 4) - c.chunkZPos)));
		}

		Iterator<ChunkCoordIntPair> it = orderedChunks.iterator();
		while (it.hasNext() && System.nanoTime() < deadline) {
			ChunkCoordIntPair cp = it.next();
			ConcurrentBitSet bs = destructionMap.get(cp);
			if (bs == null) {
				it.remove();
				continue;
			}

			Chunk chunk = world.getChunkFromChunkCoords(cp.chunkXPos, cp.chunkZPos);
			ExtendedBlockStorage[] storages = chunk.getBlockStorageArray();
			boolean chunkModified = false;

			for (int subY = 0; subY < storages.length; subY++) {
				ExtendedBlockStorage storage = storages[subY];
				if (storage == null) continue;

				int startBit = (WORLD_HEIGHT - 1 - ((subY << 4) + 15)) << 8;
				int endBit = ((WORLD_HEIGHT - 1 - (subY << 4)) << 8) | 0xFF;

				int bit = bs.nextSetBit(startBit);

				while (bit >= 0 && bit <= endBit && System.nanoTime() < deadline) {
					int yGlobal = WORLD_HEIGHT - 1 - (bit >>> 8);
					int xGlobal = (cp.chunkXPos << 4) | ((bit >>> 4) & 0xF);
					int zGlobal = (cp.chunkZPos << 4) | (bit & 0xF);
					int xLocal = xGlobal & 0xF;
					int yLocal = yGlobal & 0xF;
					int zLocal = zGlobal & 0xF;
					if (storage.getBlockByExtId(xLocal, yLocal, zLocal) != Blocks.air) {
						if (world.getTileEntity(xGlobal, yGlobal, zGlobal) != null) {
							world.removeTileEntity(xGlobal, yGlobal, zGlobal);
						}

						storage.func_150818_a(xLocal, yLocal, zLocal, Blocks.air);
						storage.setExtBlockMetadata(xLocal, yLocal, zLocal, 0);
						chunkModified = true;

						world.notifyBlocksOfNeighborChange(xGlobal, yGlobal, zGlobal, Blocks.air);
						world.markBlockForUpdate(xGlobal, yGlobal, zGlobal);

						world.updateLightByType(EnumSkyBlock.Sky, xGlobal, yGlobal, zGlobal);
						world.updateLightByType(EnumSkyBlock.Block, xGlobal, yGlobal, zGlobal);
					}
					bs.clear(bit);
					bit = bs.nextSetBit(bit + 1);
				}
			}

			if (chunkModified) {
				chunk.setChunkModified();
				world.markBlockRangeForRenderUpdate(cp.chunkXPos << 4, 0, cp.chunkZPos << 4, (cp.chunkXPos << 4) | 15, WORLD_HEIGHT - 1, (cp.chunkZPos << 4) | 15);
			}
			if (bs.isEmpty()) {
				destructionMap.remove(cp);
				for (int sy = 0; sy < (WORLD_HEIGHT >> 4); sy++) {
					snapshots.remove(new ChunkKey(cp, sy));
				}
				it.remove();
			}
		}

		if (orderedChunks.isEmpty() && destructionMap.isEmpty()) {
			destroyFinished = true;
			if (pool != null) pool.shutdown();
		}
	}

	@Override
	public boolean isComplete() {
		return collectFinished && consolidationFinished && destroyFinished;
	}

	@Override
	public void cancel() {
		this.collectFinished = true;
		this.consolidationFinished = true;
		this.destroyFinished = true;

		if (this.rayQueue != null) this.rayQueue.clear();
		if (this.cacheQueue != null) this.cacheQueue.clear();

		if (this.latch != null) {
			while (this.latch.getCount() > 0) {
				this.latch.countDown();
			}
		}
		if (this.latchWatcherThread != null && this.latchWatcherThread.isAlive()) {
			this.latchWatcherThread.interrupt();
		}

		if (this.pool != null && !this.pool.isShutdown()) {
			this.pool.shutdownNow();
			try {
				if (!this.pool.awaitTermination(100, TimeUnit.MILLISECONDS)) {
					MainRegistry.logger.log(Level.ERROR, "ExplosionNukeRayParallelized thread pool did not terminate promptly on cancel.");
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				if (!this.pool.isShutdown()) {
					this.pool.shutdownNow();
				}
			}
		}
		if (this.destructionMap != null) this.destructionMap.clear();
		if (this.accumulatedDamageMap != null) this.accumulatedDamageMap.clear();
		if (this.snapshots != null) this.snapshots.clear();
		if (this.orderedChunks != null) this.orderedChunks.clear();
	}

	private List<Vec3> generateSphereRays(int count) {
		List<Vec3> list = new ArrayList<>(count);
		if (count == 0) return list;
		if (count == 1) {
			list.add(Vec3.createVectorHelper(1, 0, 0).normalize());
			return list;
		}
		double phi = Math.PI * (3.0 - Math.sqrt(5.0));
		for (int i = 0; i < count; i++) {
			double y = 1.0 - (i / (double) (count - 1)) * 2.0;
			double r = Math.sqrt(1.0 - y * y);
			double t = phi * i;
			list.add(Vec3.createVectorHelper(Math.cos(t) * r, y, Math.sin(t) * r));
		}
		return list;
	}

	private void runConsolidation() {
		Iterator<Map.Entry<ChunkCoordIntPair, ChunkDamageAccumulator>> chunkEntryIterator = accumulatedDamageMap.entrySet().iterator();
		while (chunkEntryIterator.hasNext()) {
			Map.Entry<ChunkCoordIntPair, ChunkDamageAccumulator> entry = chunkEntryIterator.next();
			ChunkCoordIntPair cp = entry.getKey();
			ChunkDamageAccumulator accumulator = entry.getValue();

			if (accumulator.isEmpty()) {
				chunkEntryIterator.remove();
				continue;
			}

			ConcurrentBitSet chunkDestructionBitSet = destructionMap.computeIfAbsent(cp, k -> new ConcurrentBitSet(BITSET_SIZE));

			Iterator<Map.Entry<Integer, DoubleAdder>> damageEntryIterator = accumulator.entrySet().iterator();
			while (damageEntryIterator.hasNext()) {
				Map.Entry<Integer, DoubleAdder> damageEntry = damageEntryIterator.next();
				int bitIndex = damageEntry.getKey();

				float accumulatedDamage = (float) damageEntry.getValue().sum();

				if (accumulatedDamage <= 0.0f) {
					damageEntryIterator.remove();
					continue;
				}

				int yGlobal = WORLD_HEIGHT - 1 - (bitIndex >>> 8);
				int subY = yGlobal >> 4;

				if (subY < 0) {
					damageEntryIterator.remove();
					continue;
				}

				ChunkKey snapshotKey = new ChunkKey(cp, subY);
				SubChunkSnapshot snap = snapshots.get(snapshotKey);
				Block originalBlock;

				if (snap == null || snap == SubChunkSnapshot.EMPTY) {
					damageEntryIterator.remove();
					continue;
				} else {
					int xLocal = (bitIndex >>> 4) & 0xF;
					int zLocal = bitIndex & 0xF;
					originalBlock = snap.getBlock(xLocal, yGlobal & 0xF, zLocal);
					if (originalBlock == Blocks.air) {
						damageEntryIterator.remove();
						continue;
					}
				}

				float resistance = getNukeResistance(originalBlock);
				if (accumulatedDamage >= resistance) {
					chunkDestructionBitSet.set(bitIndex);
					damageEntryIterator.remove();
				}
			}

			if (accumulator.isEmpty()) {
				chunkEntryIterator.remove();
			}
		}
		accumulatedDamageMap.clear();
		consolidationFinished = true;
	}

	private static class ChunkDamageAccumulator {
		// key = bitIndex, value = total accumulated damage
		private final ConcurrentHashMap<Integer, DoubleAdder> damageMap = new ConcurrentHashMap<>();

		public void addDamage(int bitIndex, float damageAmount) {
			if (damageAmount <= 0) return;
			DoubleAdder adder = damageMap.computeIfAbsent(bitIndex, k -> new DoubleAdder());
			adder.add(damageAmount);
		}

		/*public float getDamage(int bitIndex) {
			DoubleAdder adder = damageMap.get(bitIndex);
			return adder == null ? 0f : (float) adder.sum();
		}*/

		/*public void clearDamage(int bitIndex) {
			damageMap.remove(bitIndex);
		}*/

		public Set<Map.Entry<Integer, DoubleAdder>> entrySet() {
			return damageMap.entrySet();
		}


		public boolean isEmpty() {
			return damageMap.isEmpty();
		}
	}

	private class Worker implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					if (collectFinished && rayQueue.isEmpty()) break;
					RayTask task = rayQueue.poll(100, TimeUnit.MILLISECONDS);
					if (task == null) {
						if (collectFinished && rayQueue.isEmpty()) break;
						continue;
					}
					task.trace();
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	private class RayTask {
		final int dirIndex;
		double px, py, pz;
		int x, y, z;
		float energy;
		double tMaxX, tMaxY, tMaxZ, tDeltaX, tDeltaY, tDeltaZ;
		int stepX, stepY, stepZ;
		boolean initialised = false;
		double currentRayPosition;

		private static final double RAY_DIRECTION_EPSILON = 1e-6;
		private static final double PROCESSING_EPSILON = 1e-9;
		private static final float MIN_EFFECTIVE_DIST_FOR_ENERGY_CALC = 0.01f;

		RayTask(int dirIdx) {
			this.dirIndex = dirIdx;
		}

		void init() {
			if (directions == null) directions = directionsFuture.join();
			Vec3 dir = directions.get(this.dirIndex);
			// This scales the crater. Higher = bigger.
			// Currently the crater is a little bit bigger than the original implementation
			this.energy = strength * 0.3F;
			this.px = explosionX;
			this.py = explosionY;
			this.pz = explosionZ;
			this.x = originX;
			this.y = originY;
			this.z = originZ;
			this.currentRayPosition = 0.0;

			double dirX = dir.xCoord;
			double dirY = dir.yCoord;
			double dirZ = dir.zCoord;

			double absDirX = Math.abs(dirX);
			this.stepX = (absDirX < RAY_DIRECTION_EPSILON) ? 0 : (dirX > 0 ? 1 : -1);
			this.tDeltaX = (stepX == 0) ? Double.POSITIVE_INFINITY : 1.0 / absDirX;
			this.tMaxX = (stepX == 0) ? Double.POSITIVE_INFINITY :
				((stepX > 0 ? (this.x + 1 - this.px) : (this.px - this.x)) * this.tDeltaX);

			double absDirY = Math.abs(dirY);
			this.stepY = (absDirY < RAY_DIRECTION_EPSILON) ? 0 : (dirY > 0 ? 1 : -1);
			this.tDeltaY = (stepY == 0) ? Double.POSITIVE_INFINITY : 1.0 / absDirY;
			this.tMaxY = (stepY == 0) ? Double.POSITIVE_INFINITY :
				((stepY > 0 ? (this.y + 1 - this.py) : (this.py - this.y)) * this.tDeltaY);

			double absDirZ = Math.abs(dirZ);
			this.stepZ = (absDirZ < RAY_DIRECTION_EPSILON) ? 0 : (dirZ > 0 ? 1 : -1);
			this.tDeltaZ = (stepZ == 0) ? Double.POSITIVE_INFINITY : 1.0 / absDirZ;
			this.tMaxZ = (stepZ == 0) ? Double.POSITIVE_INFINITY :
				((stepZ > 0 ? (this.z + 1 - this.pz) : (this.pz - this.z)) * this.tDeltaZ);

			this.initialised = true;
		}

		void trace() {
			if (!initialised) init();
			if (energy <= 0) {
				latch.countDown();
				return;
			}

			while (energy > 0) {
				if (y < 0 || y >= WORLD_HEIGHT) break;
				if (currentRayPosition >= radius - PROCESSING_EPSILON) break;

				ChunkKey ck = new ChunkKey(x >> 4, z >> 4, y >> 4);
				SubChunkSnapshot snap = snapshots.get(ck);

				if (snap == null) {
					cacheQueue.offer(ck);
					rayQueue.offer(this);
					return;
				}
				double t_exit_voxel = Math.min(tMaxX, Math.min(tMaxY, tMaxZ));
				double segmentLenInVoxel = t_exit_voxel - this.currentRayPosition;
				double segmentLenForProcessing;
				boolean stopAfterThisSegment = false;

				if (this.currentRayPosition + segmentLenInVoxel > radius - PROCESSING_EPSILON) {
					segmentLenForProcessing = Math.max(0.0, radius - this.currentRayPosition);
					stopAfterThisSegment = true;
				} else {
					segmentLenForProcessing = segmentLenInVoxel;
				}

				if (snap != SubChunkSnapshot.EMPTY && segmentLenForProcessing > PROCESSING_EPSILON) {
					Block block = snap.getBlock(x & 0xF, y & 0xF, z & 0xF);
					if (block != Blocks.air) {
						float resistance = getNukeResistance(block);
						if (resistance >= 2_000_000F) { // cutoff
							energy = 0;
						} else {
							double energyLossFactor = getEnergyLossFactor(resistance);
							float damageDealt = (float) (energyLossFactor * segmentLenForProcessing);
							energy -= damageDealt;
							if (damageDealt > 0) {
								int bitIndex = ((WORLD_HEIGHT - 1 - y) << 8) | ((x & 0xF) << 4) | (z & 0xF);
								if (BombConfig.explosionAlgorithm == 2) {
									ChunkCoordIntPair chunkPos = ck.pos;
									ChunkDamageAccumulator chunkAccumulator =
										accumulatedDamageMap.computeIfAbsent(chunkPos, k -> new ChunkDamageAccumulator());
									chunkAccumulator.addDamage(bitIndex, damageDealt);
								} else {
									if (energy > 0) {
										ConcurrentBitSet bs = destructionMap.computeIfAbsent(
											ck.pos,
											posKey -> new ConcurrentBitSet(BITSET_SIZE)
										);
										bs.set(bitIndex);
									}
								}
							}
						}
					}
				}
				this.currentRayPosition = t_exit_voxel;
				if (energy <= 0 || stopAfterThisSegment || this.currentRayPosition >= radius - PROCESSING_EPSILON) break;

				if (tMaxX < tMaxY) {
					if (tMaxX < tMaxZ) {
						x += stepX;
						tMaxX += tDeltaX;
					} else {
						z += stepZ;
						tMaxZ += tDeltaZ;
					}
				} else {
					if (tMaxY < tMaxZ) {
						y += stepY;
						tMaxY += tDeltaY;
					} else {
						z += stepZ;
						tMaxZ += tDeltaZ;
					}
				}
			}
			latch.countDown();
		}

		private double getEnergyLossFactor(float resistance) {
			double dxBlockToCenter = (this.x + 0.5) - explosionX;
			double dyBlockToCenter = (this.y + 0.5) - explosionY;
			double dzBlockToCenter = (this.z + 0.5) - explosionZ;
			double distToBlockCenterSq = dxBlockToCenter * dxBlockToCenter +
				dyBlockToCenter * dyBlockToCenter +
				dzBlockToCenter * dzBlockToCenter;
			double distToBlockCenter = Math.sqrt(distToBlockCenterSq);

			double effectiveDist = Math.max(distToBlockCenter, MIN_EFFECTIVE_DIST_FOR_ENERGY_CALC);
			return (Math.pow(resistance + 1.0, 3.0 * (effectiveDist / radius)) - 1.0);
		}
	}
}
