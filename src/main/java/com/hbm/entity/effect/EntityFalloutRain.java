package com.hbm.entity.effect;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.config.FalloutConfigJSON;
import com.hbm.config.FalloutConfigJSON.FalloutEntry;
import com.hbm.saveddata.AuxSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public class EntityFalloutRain extends Entity {
	private boolean firstTick = true; // Of course Vanilla has it private in Entity...
	private boolean salted = false;
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
			
			if(firstTick) {
				if (chunksToProcess.isEmpty() && outerChunksToProcess.isEmpty()) gatherChunks();
				firstTick = false;
			}

			if(tickDelay == 0) {
				tickDelay = BombConfig.fDelay;
				
				if (!chunksToProcess.isEmpty()) {
					long chunkPos = chunksToProcess.remove(chunksToProcess.size() - 1); // Just so it doesn't shift the whole list every time
					int chunkPosX = (int) (chunkPos & Integer.MAX_VALUE);
					int chunkPosZ = (int) (chunkPos >> 32 & Integer.MAX_VALUE);
					for(int x = chunkPosX << 4; x <= (chunkPosX << 4) + 16; x++) {
						for(int z = chunkPosZ << 4; z <= (chunkPosZ << 4) + 16; z++) {
							stomp(x, z, Math.hypot(x - posX, z - posZ) * 100 / getScale());
						}
					}
					
				} else if (!outerChunksToProcess.isEmpty()) {
					long chunkPos = outerChunksToProcess.remove(outerChunksToProcess.size() - 1);
					int chunkPosX = (int) (chunkPos & Integer.MAX_VALUE);
					int chunkPosZ = (int) (chunkPos >> 32 & Integer.MAX_VALUE);
					for(int x = chunkPosX << 4; x <= (chunkPosX << 4) + 16; x++) {
						for(int z = chunkPosZ << 4; z <= (chunkPosZ << 4) + 16; z++) {
							double distance = Math.hypot(x - posX, z - posZ);
							if(distance <= getScale()) {
								stomp(x, z, distance * 100 / getScale());
							}
						}
					}
					
				} else {
					setDead();
				}
			}

			tickDelay--;

			if(this.isDead) {
				if(BombConfig.rain > 0 && getScale() > 150) {
					WorldInfo info = worldObj.getWorldInfo();
					info.setRaining(true);
					info.setThundering(true);
					info.setRainTime(BombConfig.rain);
					info.setThunderTime(BombConfig.rain);
					AuxSavedData.setThunder(worldObj, BombConfig.rain);
				}
			}
		}
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
	
	//private List<int[]> changedPositions = new ArrayList();
	
	// TODO cache chunks?
	private void stomp(int x, int z, double dist) {

		int depth = 0;

		for(int y = 255; y >= 0; y--) {
			
			if(depth >= 3)
				return;

			Block b = worldObj.getBlock(x, y, z);
			Block ab = worldObj.getBlock(x, y + 1, z);
			int meta = worldObj.getBlockMetadata(x, y, z);

			if(b.getMaterial() == Material.air)
				continue;

			if((b != ModBlocks.fallout && b != ModBlocks.salted_fallout )&& (ab == Blocks.air || (ab.isReplaceable(worldObj, x, y + 1, z) && !ab.getMaterial().isLiquid()))) {

				double d = dist / 100;

				double chance = 0.1 - Math.pow((d - 0.7) * 1.0, 2);
				//double chance = 1-d;
				if(this.salted)
				{
					if(chance >= rand.nextDouble() && ModBlocks.fallout.canPlaceBlockAt(worldObj, x, y + 1, z))
						setBlock(x, y + 1, z, ModBlocks.salted_fallout);	
				}
				else
				{
					if(chance >= rand.nextDouble() && ModBlocks.fallout.canPlaceBlockAt(worldObj, x, y + 1, z))
						setBlock(x, y + 1, z, ModBlocks.fallout);
				}
			}

			if(dist < 65 && b.isFlammable(worldObj, x, y, z, ForgeDirection.UP)) {
				if(rand.nextInt(5) == 0)
					setBlock(x, y + 1, z, Blocks.fire);
			}
			
			boolean eval = false;
			
			for(FalloutEntry entry : FalloutConfigJSON.entries) {
				
				if(entry.eval(worldObj, x, y, z, b, meta, dist)) {
					if(entry.isSolid()) {
						depth++;
					}
					eval = true;
					break;
				}
			}
			
			float hardness = b.getBlockHardness(worldObj, x, y, z);
			if(dist < 65 && hardness <= Blocks.stonebrick.getExplosionResistance(null) && hardness >= 0/* && !b.hasTileEntity(worldObj.getBlockMetadata(x, y, z))*/) {
				
				Block bl = worldObj.getBlock(x, y - 1, z);
				if(bl == Blocks.air) {
					for(int i = 0; i <= depth; i++) {
						hardness = worldObj.getBlock(x, y + i, z).getBlockHardness(worldObj, x, y + i, z);
						if(hardness <= Blocks.stonebrick.getExplosionResistance(null) && hardness >= 0) {
							EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldObj, x + 0.5D, y + 0.5D + i, z + 0.5D, worldObj.getBlock(x, y + i, z), worldObj.getBlockMetadata(x, y + i, z));
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
		this.dataWatcher.addObject(16, 0);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		setScale(tag.getInteger("scale"));
		this.salted = tag.getBoolean("salt");
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
		tag.setBoolean("salt", this.salted);
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
	public void setSalted(boolean salt) {
		this.salted = salt;
	}

	public boolean getSalted() {
		boolean salt = this.salted;
		return salt;
	}
}
