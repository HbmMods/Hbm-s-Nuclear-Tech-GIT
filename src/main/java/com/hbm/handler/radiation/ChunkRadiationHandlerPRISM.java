package com.hbm.handler.radiation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * The PRISM system aims to make a semi-realistic containment system with simplified and variable resistance values.
 * The general basis for this system is the simplified 3D system with its 16x16x16 regions, but in addition to those
 * sub-chunks, each sub-chunk has several arrays of resistance values (three arrays, one for each axis) where each
 * value represents the resistance of one "slice" of the sub-chunk. This allows resistances to be handled differently
 * depending on the direction the radiation is coming from, and depending on the sub-chunk's localized block resistance
 * density. While not as accurate as the pocket-based system from 1.12, it does a better job at simulating resistances
 * of various block types instead of a binary sealing/not sealing system. For example it is therefore possible to
 * safely store radioactive waste in a cave, shielded by many layers of rock and dirt, without needing extra concrete.
 * The system's name stems from the "gradient"-like handling of the resistance values per axis, multiple color
 * gradients make a rainbow, and rainbows come from prisms. Just like a prism, sub-chunks too handle the radiation
 * going through them differently depending on the angle of approach.
 *      ___
 *     /\  \
 *    /  \  \
 *   /    \  \
 *  /      \  \
 * /________\__\
 * 
 * @author hbm
 *
 */
public class ChunkRadiationHandlerPRISM extends ChunkRadiationHandler {
	
	public ConcurrentHashMap<World, RadPerWorld> perWorld = new ConcurrentHashMap();
	public static int cycles = 0;
	
	public static final float MAX_RADIATION = 1_000_000;
	private static final String NBT_KEY_CHUNK_RADIATION = "hfr_prism_radiation_";
	private static final String NBT_KEY_CHUNK_RESISTANCE = "hfr_prism_resistance_";
	private static final String NBT_KEY_CHUNK_EXISTS = "hfr_prism_exists_";

	@Override
	public float getRadiation(World world, int x, int y, int z) {
		
		RadPerWorld system = perWorld.get(world);
		
		if(system != null) {
			ChunkCoordIntPair coords = new ChunkCoordIntPair(x >> 4, z >> 4);
			int yReg = MathHelper.clamp_int(y >> 4, 0, 15);
			SubChunk[] subChunks = system.radiation.get(coords);
			if(subChunks != null) {
				SubChunk rad = subChunks[yReg];
				if(rad != null) return rad.radiation;
			}
		}
		
		return 0;
	}

	@Override
	public void setRadiation(World world, int x, int y, int z, float rad) {
		
		if(Float.isNaN(rad)) rad = 0;
		
		RadPerWorld system = perWorld.get(world);
		
		if(system != null) {
			ChunkCoordIntPair coords = new ChunkCoordIntPair(x >> 4, z >> 4);
			int yReg = MathHelper.clamp_int(y >> 4, 0, 15);
			SubChunk[] subChunks = system.radiation.get(coords);
			if(subChunks == null) {
				subChunks = new SubChunk[16];
				system.radiation.put(coords, subChunks);
			}
			if(subChunks[yReg] == null) subChunks[yReg] = new SubChunk().rebuild(world, x, y, z);
			subChunks[yReg].radiation = MathHelper.clamp_float(rad, 0, MAX_RADIATION);
			world.getChunkFromBlockCoords(x, z).isModified = true;
		}
	}

	@Override
	public void incrementRad(World world, int x, int y, int z, float rad) {
		setRadiation(world, x, y, z, getRadiation(world, x, y, z) + rad);
	}

	@Override
	public void decrementRad(World world, int x, int y, int z, float rad) {
		setRadiation(world, x, y, z, getRadiation(world, x, y, z) - rad);
	}

	@Override
	public void receiveWorldLoad(WorldEvent.Load event) {
		if(!event.world.isRemote) perWorld.put(event.world, new RadPerWorld());
	}

	@Override
	public void receiveWorldUnload(WorldEvent.Unload event) {
		if(!event.world.isRemote) perWorld.remove(event.world);
	}

	@Override
	public void receiveChunkLoad(ChunkDataEvent.Load event) {
		
		if(!event.world.isRemote) {
			RadPerWorld radWorld = perWorld.get(event.world);
			
			if(radWorld != null) {
				SubChunk[] chunk = new SubChunk[16];
				
				for(int i = 0; i < 16; i++) {
					if(!event.getData().getBoolean(NBT_KEY_CHUNK_EXISTS + i)) {
						chunk[i] = new SubChunk().rebuild(event.world, event.getChunk().xPosition << 4, i << 4, event.getChunk().zPosition << 4);
						continue;
					}
					SubChunk sub = new SubChunk();
					chunk[i] = sub;
					sub.radiation = event.getData().getFloat(NBT_KEY_CHUNK_RADIATION + i);
					for(int j = 0; j < 16; j++) sub.xResist[j] = event.getData().getFloat(NBT_KEY_CHUNK_RESISTANCE + "x_" + j + "_" + i);
					for(int j = 0; j < 16; j++) sub.yResist[j] = event.getData().getFloat(NBT_KEY_CHUNK_RESISTANCE + "y_" + j + "_" + i);
					for(int j = 0; j < 16; j++) sub.zResist[j] = event.getData().getFloat(NBT_KEY_CHUNK_RESISTANCE + "z_" + j + "_" + i);
				}
				
				radWorld.radiation.put(event.getChunk().getChunkCoordIntPair(), chunk);
			}
		}
	}

	@Override
	public void receiveChunkSave(ChunkDataEvent.Save event) {
		if(!event.world.isRemote) {
			RadPerWorld radWorld = perWorld.get(event.world);
			if(radWorld != null) {
				SubChunk[] chunk = radWorld.radiation.get(event.getChunk().getChunkCoordIntPair());
				if(chunk != null) {
					for(int i = 0; i < 16; i++) {
						SubChunk sub = chunk[i];
						if(sub != null) {
							float rad = sub.radiation;
							event.getData().setFloat(NBT_KEY_CHUNK_RADIATION + i, rad);
							for(int j = 0; j < 16; j++) event.getData().setFloat(NBT_KEY_CHUNK_RESISTANCE + "x_" + j + "_" + i, sub.xResist[j]);
							for(int j = 0; j < 16; j++) event.getData().setFloat(NBT_KEY_CHUNK_RESISTANCE + "y_" + j + "_" + i, sub.yResist[j]);
							for(int j = 0; j < 16; j++) event.getData().setFloat(NBT_KEY_CHUNK_RESISTANCE + "z_" + j + "_" + i, sub.zResist[j]);
							event.getData().setBoolean(NBT_KEY_CHUNK_EXISTS + i, true);
						}
					}
				}
			}
		}
	}

	@Override
	public void receiveChunkUnload(ChunkEvent.Unload event) {
		if(!event.world.isRemote) {
			RadPerWorld radWorld = perWorld.get(event.world);
			if(radWorld != null) {
				radWorld.radiation.remove(event.getChunk().getChunkCoordIntPair());
			}
		}
	}
	
	public static final HashMap<ChunkCoordIntPair, SubChunk[]> newAdditions = new HashMap();

	@Override
	public void updateSystem() {
		
		cycles++;
		
		for(WorldServer world : DimensionManager.getWorlds()) { //only updates loaded worlds
			
			RadPerWorld system = perWorld.get(world);
			if(system == null) continue;
			
			int rebuildAllowance = 25;
			
			//it would be way to expensive to replace the sub-chunks entirely like with the old system
			//(that only used floats anyway...) so instead we shift the radiation into the prev value
			for(Entry<ChunkCoordIntPair, SubChunk[]> chunk : system.radiation.entrySet()) {
				ChunkCoordIntPair coord = chunk.getKey();
				
				for(int i = 0; i < 16; i++) {
					
					SubChunk sub = chunk.getValue()[i];
					
					boolean hasTriedRebuild = false;
					
					if(sub != null) {
						sub.prevRadiation = sub.radiation;
						sub.radiation = 0;
						
						//process some chunks that need extra rebuilding
						if(rebuildAllowance > 0 && sub.needsRebuild) {
							sub.rebuild(world, coord.chunkXPos << 4, i << 4, coord.chunkZPos << 4);
							if(!sub.needsRebuild) {
								rebuildAllowance--;
								hasTriedRebuild = true;
							}
						}
						
						if(!hasTriedRebuild && Math.abs(coord.chunkXPos * coord.chunkZPos) % 5 == cycles % 5 && world.getChunkProvider().chunkExists(coord.chunkXPos, coord.chunkZPos)) {

							Chunk c = world.getChunkFromChunkCoords(coord.chunkXPos, coord.chunkZPos);
							ExtendedBlockStorage[] xbs = c.getBlockStorageArray();
							ExtendedBlockStorage subChunk = xbs[i];
							int checksum = 0;
							
							if(subChunk != null) {
								for(int iX = 0; iX < 16; iX++) for(int iY = 0; iY < 16; iY ++) for(int iZ = 0; iZ < 16; iZ ++) checksum += subChunk.getBlockLSBArray()[MathHelper.clamp_int(iY << 8 | iZ << 4 | iX, 0, 4095)];
							}
							
							if(checksum != sub.checksum) {
								sub.rebuild(world, coord.chunkXPos << 4, i << 4, coord.chunkZPos << 4);
							}
						}
					}
				}
			}

			//has to support additions while iterating
			Iterator<Entry<ChunkCoordIntPair, SubChunk[]>> it = system.radiation.entrySet().iterator();
			while(it.hasNext()) {
				Entry<ChunkCoordIntPair, SubChunk[]> chunk = it.next();
				if(this.getPrevChunkRadiation(chunk.getValue()) <= 0) continue;
				for(int i = 0; i < 16; i++) {
					
					SubChunk sub = chunk.getValue()[i];
					
					if(sub != null) {
						if(sub.prevRadiation <= 0 || Float.isNaN(sub.prevRadiation) || Float.isInfinite(sub.prevRadiation)) continue;
						float radSpread = 0;
						for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) radSpread += spreadRadiation(world, sub, i, chunk.getKey(), chunk.getValue(), system.radiation, dir);
						sub.radiation += (sub.prevRadiation - radSpread) * 0.95F;
						sub.radiation -= 1F;
						sub.radiation = MathHelper.clamp_float(sub.radiation, 0, MAX_RADIATION);
					}
				}
			}
			
			system.radiation.putAll(newAdditions);
			newAdditions.clear();
			
			/*
			//reap chunks with no radiation at all
			system.radiation.entrySet().removeIf(x -> getTotalChunkRadiation(x.getValue()) <= 0F);
			*/ //is this even a good idea? by reaping unused chunks we still lose our cached resistance values
		}
	}
	
	/** Returns the amount of radiation spread */
	private static float spreadRadiation(World world, SubChunk source, int y, ChunkCoordIntPair origin, SubChunk[] chunk, ConcurrentHashMap<ChunkCoordIntPair, SubChunk[]> map, ForgeDirection dir) {
		
		float spread = 0.1F;
		float amount = source.prevRadiation * spread;
		
		if(amount <= 1F) return 0;
		
		if(dir.offsetY != 0) {
			if(dir == Library.POS_Y && y == 15) return amount; // out of world
			if(dir == Library.NEG_Y && y == 0) return amount; // out of world
			if(chunk[y + dir.offsetY] == null) chunk[y + dir.offsetY] = new SubChunk().rebuild(world, origin.chunkXPos << 4, (y + dir.offsetY) << 4, origin.chunkZPos << 4);
			SubChunk to = chunk[y + dir.offsetY];
			return spreadRadiationTo(source, to, amount, dir);
		} else {
			ChunkCoordIntPair newPos = new ChunkCoordIntPair(origin.chunkXPos + dir.offsetX, origin.chunkZPos + dir.offsetZ);
			if(!world.getChunkProvider().chunkExists(newPos.chunkXPos, newPos.chunkZPos)) return amount;
			SubChunk[] newChunk = map.get(newPos);
			if(newChunk == null) {
				newChunk = new SubChunk[16];
				newAdditions.put(newPos, newChunk);
			}
			if(newChunk[y] == null) newChunk[y] = new SubChunk().rebuild(world, newPos.chunkXPos << 4, y << 4, newPos.chunkZPos << 4);
			SubChunk to = newChunk[y];
			return spreadRadiationTo(source, to, amount, dir);
		}
	}
	
	private static float spreadRadiationTo(SubChunk from, SubChunk to, float amount, ForgeDirection movement) {
		float resistance = from.getResistanceValue(movement.getOpposite()) + to.getResistanceValue(movement);
		double fun = Math.pow(Math.E, -resistance / 10_000D);
		float toMove = (float) Math.min(amount * fun, amount);
		to.radiation += toMove;
		return toMove;
	}

	//private static float getTotalChunkRadiation(SubChunk[] chunk) { float rad = 0; for(SubChunk sub : chunk) if(sub != null) rad += sub.radiation; return rad; }
	private static float getPrevChunkRadiation(SubChunk[] chunk) { float rad = 0; for(SubChunk sub : chunk) if(sub != null) rad += sub.prevRadiation; return rad; }

	@Override
	public void clearSystem(World world) {
		RadPerWorld system = perWorld.get(world);
		if(system != null) system.radiation.clear();
	}
	
	public static class RadPerWorld {
		public ConcurrentHashMap<ChunkCoordIntPair, SubChunk[]> radiation = new ConcurrentHashMap();
	}
	
	public static class SubChunk {
		
		public float prevRadiation;
		public float radiation;
		public float[] xResist = new float[16];
		public float[] yResist = new float[16];
		public float[] zResist = new float[16];
		public boolean needsRebuild = false;
		public int checksum = 0;
		
		@Deprecated public void updateBlock(World world, int x, int y, int z) {
			int cX = x >> 4;
			int cY = MathHelper.clamp_int(y >> 4, 0, 15);
			int cZ = z >> 4;
			
			if(!world.getChunkProvider().chunkExists(cX, cZ)) return;
			
			int tX = cX << 4;
			int tY = cY << 4;
			int tZ = cX << 4;
			
			int sX = MathHelper.clamp_int(x - tX, 0, 15);
			int sY = MathHelper.clamp_int(y - tY, 0, 15);
			int sZ = MathHelper.clamp_int(z - tZ, 0, 15);
			
			Chunk chunk = world.getChunkFromChunkCoords(cX, cZ);
			ExtendedBlockStorage[] xbs = chunk.getBlockStorageArray();
			ExtendedBlockStorage subChunk = xbs[cY];
			
			xResist[sX] =  yResist[sY] = zResist[sZ] = 0;
			
			for(int iX = 0; iX < 16; iX++) {
				for(int iY = 0; iY < 16; iY ++) {
					for(int iZ = 0; iZ < 16; iZ ++) {
						
						if(iX == sX || iY == sY || iZ == sZ) { //only redo the three affected slices by this position change
							
							Block b = subChunk.getBlockByExtId(iX, iY, iZ);
							if(b.getMaterial() == Material.air) continue;
							float resistance = Math.min(b.getExplosionResistance(null, world, tX + iX, tY + iY, tZ + iZ, x, y, z), 100);
							if(iX == sX) xResist[iX] += resistance;
							if(iY == sY) yResist[iY] += resistance;
							if(iZ == sZ) zResist[iZ] += resistance;
						}
					}
				}
			}
		}
		
		public SubChunk rebuild(World world, int x, int y, int z) {
			needsRebuild = true;
			int cX = x >> 4;
			int cY = MathHelper.clamp_int(y >> 4, 0, 15);
			int cZ = z >> 4;
			
			if(!world.getChunkProvider().chunkExists(cX, cZ)) return this; //if the chunk isn't actually loaded, quit (but needsRebuild is still set!)
			
			int tX = cX << 4;
			int tY = cY << 4;
			int tZ = cX << 4;
			
			for(int i = 0; i < 16; i++) xResist[i] = yResist[i] = zResist[i] = 0;
			
			Chunk chunk = world.getChunkFromChunkCoords(cX, cZ);
			ExtendedBlockStorage[] xbs = chunk.getBlockStorageArray();
			ExtendedBlockStorage subChunk = xbs[cY];
			checksum = 0;
			
			if(subChunk != null) {
				for(int iX = 0; iX < 16; iX++) {
					for(int iY = 0; iY < 16; iY ++) {
						for(int iZ = 0; iZ < 16; iZ ++) {
							
							Block b = subChunk.getBlockByExtId(iX, iY, iZ);
							if(b.getMaterial() == Material.air) continue;
							float resistance = Math.min(b.getExplosionResistance(null, world, tX + iX, tY + iY, tZ + iZ, x, y, z), 100);
							xResist[iX] += resistance;
							yResist[iY] += resistance;
							zResist[iZ] += resistance;
							checksum += subChunk.getBlockLSBArray()[MathHelper.clamp_int(iY << 8 | iZ << 4 | iX, 0, 4095)]; // the "good enough" approach
						}
					}
				}
			}
			
			needsRebuild = false;
			return this;
		}
		
		public float getResistanceValue(ForgeDirection movement) {
			if(movement == Library.POS_X) return getResistanceFromArray(xResist, true);
			if(movement == Library.NEG_X) return getResistanceFromArray(xResist, false);
			if(movement == Library.POS_Y) return getResistanceFromArray(yResist, true);
			if(movement == Library.NEG_Y) return getResistanceFromArray(yResist, false);
			if(movement == Library.POS_Z) return getResistanceFromArray(zResist, true);
			if(movement == Library.NEG_Z) return getResistanceFromArray(zResist, false);
			return 0;
		}
		
		private float getResistanceFromArray(float[] resist, boolean reverse) {
			float res = 0F;
			for(int i = 1; i < 16; i++) {
				int index = reverse ? 15 - i : i;
				res += resist[index] / 15F * i;
			}
			return res;
		}
	}
}
