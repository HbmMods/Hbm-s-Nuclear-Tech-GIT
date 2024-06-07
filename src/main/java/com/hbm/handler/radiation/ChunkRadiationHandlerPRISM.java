package com.hbm.handler.radiation;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.util.ForgeDirection;

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
 * 
 * @author hbm
 *
 */
public class ChunkRadiationHandlerPRISM extends ChunkRadiationHandler {
	
	private HashMap<World, RadPerWorld> perWorld = new HashMap();
	
	public static final float MAX_RADIATION = 1_000_000;

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
		
		RadPerWorld system = perWorld.get(world);
		
		if(system != null) {
			ChunkCoordIntPair coords = new ChunkCoordIntPair(x >> 4, z >> 4);
			int yReg = MathHelper.clamp_int(y >> 4, 0, 15);
			SubChunk[] subChunks = system.radiation.get(coords);
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
	public void updateSystem() {
		
		for(Entry<World, RadPerWorld> entries : perWorld.entrySet()) {
			RadPerWorld system = entries.getValue();
			
			//it would be way to expensive to replace the sub-chunks entirely like with the old system
			//(that only used floats anyway...) so instead we shift the radiation into the prev value
			for(Entry<ChunkCoordIntPair, SubChunk[]> chunk : system.radiation.entrySet()) for(SubChunk sub : chunk.getValue()) if(sub != null) {
				sub.prevRadiation = sub.radiation;
				sub.radiation = 0;
			}

			for(Entry<ChunkCoordIntPair, SubChunk[]> chunk : system.radiation.entrySet()) {
				for(int i = 0; i < 16; i++) {
					
					SubChunk sub = chunk.getValue()[i];
					
					if(sub != null) {
						float radSpread = 0;
						for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) radSpread += spreadRadiation(sub, i, chunk.getKey(), system.radiation, dir);
						sub.radiation += (sub.prevRadiation - radSpread) * 0.9F;
					}
				}
			}
			
			/*
			//reap chunks with no radiation at all
			system.radiation.entrySet().removeIf(x -> getTotalChunkRadiation(x.getValue()) <= 0F);
			*/ //is this even a good idea? by reaping unused chunks we still lose our cached resistance values
		}
	}
	
	private static float spreadRadiation(SubChunk source, int y, ChunkCoordIntPair origin, HashMap<ChunkCoordIntPair, SubChunk[]> map, ForgeDirection dir) {
		
		//TODO
		
		return 0F;
	}
	
	public static float getTotalChunkRadiation(SubChunk[] chunk) {
		float rad = 0;
		for(SubChunk sub : chunk) if(sub != null) rad += sub.radiation;
		return rad;
	}

	@Override
	public void clearSystem(World world) {
		RadPerWorld system = perWorld.get(world);
		if(system != null) system.radiation.clear();
	}
	
	public static class RadPerWorld {
		public HashMap<ChunkCoordIntPair, SubChunk[]> radiation = new HashMap();
	}
	
	public static class SubChunk {
		
		public float prevRadiation;
		public float radiation;
		public float[] xResist = new float[16];
		public float[] yResist = new float[16];
		public float[] zResist = new float[16];
		public boolean needsRebuild = false;
		
		public void updateBlock(World world, int x, int y, int z) {
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
							float resistance = b.getExplosionResistance(null, world, tX + iX, tY + iY, tZ + iZ, x, y, z);
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
			
			for(int iX = 0; iX < 16; iX++) {
				for(int iY = 0; iY < 16; iY ++) {
					for(int iZ = 0; iZ < 16; iZ ++) {
						
						Block b = subChunk.getBlockByExtId(iX, iY, iZ);
						float resistance = b.getExplosionResistance(null, world, tX + iX, tY + iY, tZ + iZ, x, y, z);
						xResist[iX] += resistance;
						yResist[iY] += resistance;
						zResist[iZ] += resistance;
					}
				}
			}
			
			needsRebuild = false;
			return this;
		}
		
		public float getResistanceValue(ForgeDirection movement) {
			//TODO
			return 0;
		}
	}
}
