package com.hbm.entity.effect;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.config.FalloutConfigJSON;
import com.hbm.config.FalloutConfigJSON.FalloutEntry;
import com.hbm.config.WorldConfig;
import com.hbm.entity.item.EntityFallingBlockNT;
import com.hbm.entity.logic.EntityExplosionChunkloading;
import com.hbm.world.WorldUtil;
import com.hbm.world.biome.BiomeGenCraterBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public class EntityFalloutRain extends EntityExplosionChunkloading {
	
	private boolean firstTick = true; // Of course Vanilla has it private in Entity...

	public EntityFalloutRain(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

	public EntityFalloutRain(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.isImmuneToFire = true;
	}

	private int tickDelay = BombConfig.fDelay;

	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {
			
			long start = System.currentTimeMillis();
			
			if(firstTick) {
				if(chunksToProcess.isEmpty() && outerChunksToProcess.isEmpty()) gatherChunks();
				firstTick = false;
			}

			if(tickDelay == 0) {
				tickDelay = BombConfig.fDelay;
				
				while(System.currentTimeMillis() < start + BombConfig.mk5) {
					if(!chunksToProcess.isEmpty()) {
						long chunkPos = chunksToProcess.remove(chunksToProcess.size() - 1); // Just so it doesn't shift the whole list every time
						int chunkPosX = (int) (chunkPos & Integer.MAX_VALUE);
						int chunkPosZ = (int) (chunkPos >> 32 & Integer.MAX_VALUE);
						boolean biomeModified = false;
						for(int x = chunkPosX << 4; x < (chunkPosX << 4) + 16; x++) {
							for(int z = chunkPosZ << 4; z < (chunkPosZ << 4) + 16; z++) {
								double percent = Math.hypot(x - posX, z - posZ) * 100 / getScale();
								stomp(x, z, percent);
								BiomeGenBase biome = getBiomeChange(percent, getScale(), worldObj.getBiomeGenForCoords(x, z));
								if(biome != null) {
									WorldUtil.setBiome(worldObj, x, z, biome);
									biomeModified = true;
								}
							}
						}
						if(biomeModified) WorldUtil.syncBiomeChange(worldObj, chunkPosX << 4, chunkPosZ << 4);
						
					} else if (!outerChunksToProcess.isEmpty()) {
						long chunkPos = outerChunksToProcess.remove(outerChunksToProcess.size() - 1);
						int chunkPosX = (int) (chunkPos & Integer.MAX_VALUE);
						int chunkPosZ = (int) (chunkPos >> 32 & Integer.MAX_VALUE);
						boolean biomeModified = false;
						for(int x = chunkPosX << 4; x < (chunkPosX << 4) + 16; x++) {
							for(int z = chunkPosZ << 4; z < (chunkPosZ << 4) + 16; z++) {
								double distance = Math.hypot(x - posX, z - posZ);
								if(distance <= getScale()) {
									double percent = distance * 100 / getScale();
									stomp(x, z, percent);
									BiomeGenBase biome = getBiomeChange(percent, getScale(), worldObj.getBiomeGenForCoords(x, z));
									if(biome != null) {
										WorldUtil.setBiome(worldObj, x, z, biome);
										biomeModified = true;
									}
								}
							}
						}
						if(biomeModified) WorldUtil.syncBiomeChange(worldObj, chunkPosX << 4, chunkPosZ << 4);
						
					} else {
						this.clearChunkLoader();
						this.setDead();
						break;
					}
				}
			}

			tickDelay--;
		}
	}
	
	public static BiomeGenBase getBiomeChange(double dist, int scale, BiomeGenBase original) {
		if(!WorldConfig.enableCraterBiomes) return null;
		if(scale >= 150 && dist < 15)
			return BiomeGenCraterBase.craterInnerBiome;
		if(scale >= 100 && dist < 55 && original != BiomeGenCraterBase.craterInnerBiome)
			return BiomeGenCraterBase.craterBiome;
		if(scale >= 25 && original != BiomeGenCraterBase.craterInnerBiome && original != BiomeGenCraterBase.craterBiome)
			return BiomeGenCraterBase.craterOuterBiome;
		return null;
	}

	private final List<Long> chunksToProcess = new ArrayList<>();
	private final List<Long> outerChunksToProcess = new ArrayList<>();

	// Is it worth the effort to split this into a method that can be called over multiple ticks? I'd say it's fast enough anyway...
	private void gatherChunks() {
		Set<Long> chunks = new LinkedHashSet<>(); // LinkedHashSet preserves insertion order
		Set<Long> outerChunks = new LinkedHashSet<>();
		int outerRange = getScale();
		// Basically defines something like the step size, but as indirect proportion. The actual angle used for rotation will always end up at 360° for angle == adjustedMaxAngle
		// So yea, I mathematically worked out that 20 is a good value for this, with the minimum possible being 18 in order to reach all chunks
		int adjustedMaxAngle = 20 * outerRange / 32; // step size = 20 * chunks / 2
		for (int angle = 0; angle <= adjustedMaxAngle; angle++) {
			Vec3 vector = Vec3.createVectorHelper(outerRange, 0, 0);
			vector.rotateAroundY((float) (angle * Math.PI / 180.0 / (adjustedMaxAngle / 360.0))); // Ugh, mutable data classes (also, ugh, radians; it uses degrees in 1.18; took me two hours to debug)
			outerChunks.add(ChunkCoordIntPair.chunkXZ2Int((int) (posX + vector.xCoord) >> 4, (int) (posZ + vector.zCoord) >> 4));
		}
		for (int distance = 0; distance <= outerRange; distance += 8) for (int angle = 0; angle <= adjustedMaxAngle; angle++) {
			Vec3 vector = Vec3.createVectorHelper(distance, 0, 0);
			vector.rotateAroundY((float) (angle * Math.PI / 180.0 / (adjustedMaxAngle / 360.0)));
			long chunkCoord = ChunkCoordIntPair.chunkXZ2Int((int) (posX + vector.xCoord) >> 4, (int) (posZ + vector.zCoord) >> 4);
			if (!outerChunks.contains(chunkCoord)) chunks.add(chunkCoord);
		}

		chunksToProcess.addAll(chunks);
		outerChunksToProcess.addAll(outerChunks);
		Collections.reverse(chunksToProcess); // So it starts nicely from the middle
		Collections.reverse(outerChunksToProcess);
	}
	
	private void stomp(int x, int z, double dist) {

		int depth = 0;

		for(int y = 255; y >= 0; y--) {
			
			if(depth >= 3) return;

			Block b = worldObj.getBlock(x, y, z);

			if(b.getMaterial() == Material.air || b == ModBlocks.fallout) continue;
			
			if(b == ModBlocks.volcano_core) {
				worldObj.setBlock(x, y, z, ModBlocks.volcano_rad_core, worldObj.getBlockMetadata(x, y, z), 3);
				continue;
			}
			
			Block ab = worldObj.getBlock(x, y + 1, z);
			int meta = worldObj.getBlockMetadata(x, y, z);

			if(depth == 0 && b != ModBlocks.fallout && (ab == Blocks.air || (ab.isReplaceable(worldObj, x, y + 1, z) && !ab.getMaterial().isLiquid()))) {

				double d = dist / 100;

				double chance = 0.1 - Math.pow((d - 0.7) * 1.0, 2);

				if(chance >= rand.nextDouble() && ModBlocks.fallout.canPlaceBlockAt(worldObj, x, y + 1, z))
					setBlock(x, y + 1, z, ModBlocks.fallout);
			}

			if(dist < 65 && b.isFlammable(worldObj, x, y, z, ForgeDirection.UP)) {
				if(rand.nextInt(5) == 0 && worldObj.getBlock(x, y + 1, z).isAir(worldObj, x, y + 1, z))
					setBlock(x, y + 1, z, Blocks.fire);
			}
			
			boolean eval = false;
			
			for(FalloutEntry entry : FalloutConfigJSON.entries) {
				
				if(entry.eval(worldObj, x, y, z, b, meta, dist, b, meta)) {
					if(entry.isSolid()) {
						depth++;
					}
					eval = true;
					break;
				}
			}
			
			float hardness = b.getBlockHardness(worldObj, x, y, z);
			if(y > 0 && dist < 65 && hardness <= Blocks.stonebrick.getExplosionResistance(null) && hardness >= 0/* && !b.hasTileEntity(worldObj.getBlockMetadata(x, y, z))*/) {
				
				if(worldObj.getBlock(x, y - 1, z) == Blocks.air) {
					for(int i = 0; i <= depth; i++) {
						Block block = worldObj.getBlock(x, y + i, z);
						hardness = block.getBlockHardness(worldObj, x, y + i, z);
						if(hardness <= Blocks.stonebrick.getExplosionResistance(null) && hardness >= 0) {
							EntityFallingBlockNT entityfallingblock = new EntityFallingBlockNT(worldObj, x + 0.5D, y + 0.5D + i, z + 0.5D, block, worldObj.getBlockMetadata(x, y + i, z));
							entityfallingblock.canDrop = false; //turn off block drops because block dropping was coded by a mule with dementia
							worldObj.spawnEntityInWorld(entityfallingblock);
						}
					}
				}
			}
			
			if(!eval && b.isNormalCube()) {
				depth++;
			}
		}
	}
	
	public void setBlock(int x, int y, int z, Block block) {
		setBlock(x, y, z, block, 0);
	}
	
	public void setBlock(int x, int y, int z, Block block, int meta) {
		worldObj.setBlock(x, y, z, block, meta, 3); //this was supposed to write the position to a list for a multi block update, but forge already has that built-in. whoops.
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, 0);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		setScale(tag.getInteger("scale"));
		chunksToProcess.addAll(readChunksFromIntArray(tag.getIntArray("chunks")));
		outerChunksToProcess.addAll(readChunksFromIntArray(tag.getIntArray("outerChunks")));
	}

	private Collection<Long> readChunksFromIntArray(int[] data) {
		List<Long> coords = new ArrayList<>();
		boolean firstPart = true;
		int x = 0;
		for (int coord : data) {
			if (firstPart) x = coord;
			else coords.add(ChunkCoordIntPair.chunkXZ2Int(x, coord));
			firstPart = !firstPart;
		}
		return coords;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setInteger("scale", getScale());
		tag.setIntArray("chunks", writeChunksToIntArray(chunksToProcess));
		tag.setIntArray("outerChunks", writeChunksToIntArray(outerChunksToProcess));
	}

	private int[] writeChunksToIntArray(List<Long> coords) {
		int[] data = new int[coords.size() * 2];
		for (int i = 0; i < coords.size(); i++) {
			data[i * 2] = (int) (coords.get(i) & Integer.MAX_VALUE);
			data[i * 2 + 1] = (int) (coords.get(i) >> 32 & Integer.MAX_VALUE);
		}
		return data;
	}

	public void setScale(int i) {
		this.dataWatcher.updateObject(16, i);
	}

	public int getScale() {
		int scale = this.dataWatcher.getWatchableObjectInt(16);
		return scale == 0 ? 1 : scale;
	}
}
