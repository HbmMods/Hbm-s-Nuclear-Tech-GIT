package com.hbm.handler.radiation;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.interfaces.Untested;

import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * A slightly more sophisticated version of the ChunkRadiationHandlerSimple, each chunk has 16 radiation values depending on height.
 * The bottom and topmost values extend up to infinity, preventing people from escaping radiation when leaving the build height.
 * @author hbm
 */
public class ChunkRadiationHandler3D extends ChunkRadiationHandler {
	
	private HashMap<World, ThreeDimRadiationPerWorld> perWorld = new HashMap();

	@Override @Untested
	public float getRadiation(World world, int x, int y, int z) {
		ThreeDimRadiationPerWorld radWorld = perWorld.get(world);
		
		if(radWorld != null) {
			ChunkCoordIntPair coords = new ChunkCoordIntPair(x >> 4, z >> 4);
			
			int yReg = MathHelper.clamp_int(y >> 4, 0, 15);
			
			Float rad = radWorld.radiation.get(coords)[yReg]; // this will crash if the coord pair isn't nullchecked
			return rad == null ? 0F : rad;
		}
		
		return 0;
	}

	@Override
	public void setRadiation(World world, int x, int y, int z, float rad) {
		ThreeDimRadiationPerWorld radWorld = perWorld.get(world);
		
		if(radWorld != null) {
			
			if(world.blockExists(x, 0, z)) {
				
				ChunkCoordIntPair coords = new ChunkCoordIntPair(x >> 4, z >> 4);
				
				int yReg = MathHelper.clamp_int(y >> 4, 0, 15);
				
				if(radWorld.radiation.containsKey(coords)) {
					radWorld.radiation.get(coords)[yReg] = rad;
				}

				world.getChunkFromBlockCoords(x, z).isModified = true;
			}
		}
	}

	@Override
	public void incrementRad(World world, int x, int y, int z, float rad) {
		setRadiation(world, x, y, z, getRadiation(world, x, y, z) + rad);
	}

	@Override
	public void decrementRad(World world, int x, int y, int z, float rad) {
		setRadiation(world, x, y, z, Math.max(getRadiation(world, x, y, z) - rad, 0));
	}

	@Override @Untested //will most definitely crash, for this to work i need to figure out what it even was i wanted to do in the first place
	public void updateSystem() {
		
		for(Entry<World, ThreeDimRadiationPerWorld> entry : perWorld.entrySet()) {
			
			HashMap<ChunkCoordIntPair, Float[]> radiation = entry.getValue().radiation;
			HashMap<ChunkCoordIntPair, Float[]> buff = new HashMap(radiation);
			radiation.clear();
			
			for(Entry<ChunkCoordIntPair, Float[]> chunk : buff.entrySet()) {
				
				ChunkCoordIntPair coord = chunk.getKey();
				
				for(int y = 0; y < 16; y++) {
					
					for(int i = -1; i <= 1; i++) {
						for(int j = -1; j <= 1; j++) {
							for(int k = -1; k <= 1; k++) {
								
								int type = Math.abs(i) + Math.abs(j) + Math.abs(k);
								
								if(type == 3)
									continue;
								
								float percent = type == 0 ? 0.6F : type == 1 ? 0.075F : 0.025F;
								
								ChunkCoordIntPair newCoord = new ChunkCoordIntPair(coord.chunkXPos + i, coord.chunkZPos + k);
								
								if(buff.containsKey(newCoord)) {
									int newY = MathHelper.clamp_int(y + j, 0, 15);
									Float[] vals = radiation.get(newCoord); // ????????? but radiation was cleared!
									float newRad = vals[newY] + chunk.getValue()[newY] * percent;
									vals[newY] = Math.max(0F, newRad * 0.999F - 0.05F);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void clearSystem(World world) {
		ThreeDimRadiationPerWorld radWorld = perWorld.get(world);
		
		if(radWorld != null) {
			radWorld.radiation.clear();
		}
	}

	@Override
	public void receiveWorldLoad(WorldEvent.Load event) {
		if(!event.world.isRemote)
			perWorld.put(event.world, new ThreeDimRadiationPerWorld());
	}

	@Override
	public void receiveWorldUnload(WorldEvent.Unload event) {
		if(!event.world.isRemote)
			perWorld.remove(event.world);
	}
	
	private static final String NBT_KEY_CHUNK_RADIATION = "hfr_3d_radiation_";

	@Override
	public void receiveChunkLoad(ChunkDataEvent.Load event) {
		
		if(!event.world.isRemote) {
			ThreeDimRadiationPerWorld radWorld = perWorld.get(event.world);
			
			if(radWorld != null) {
				
				Float[] vals = new Float[16];
				
				for(int i = 0; i < 16; i++) {
					vals[i] = event.getData().getFloat(NBT_KEY_CHUNK_RADIATION + i);
				}
				
				radWorld.radiation.put(event.getChunk().getChunkCoordIntPair(), vals);
			}
		}
	}

	@Override
	public void receiveChunkSave(ChunkDataEvent.Save event) {
		
		if(!event.world.isRemote) {
			ThreeDimRadiationPerWorld radWorld = perWorld.get(event.world);
			
			if(radWorld != null) {
				Float[] vals = radWorld.radiation.get(event.getChunk().getChunkCoordIntPair());
				
				for(int i = 0; i < 16; i++) {
					float rad = vals[i] == null ? 0F : vals[i];
					event.getData().setFloat(NBT_KEY_CHUNK_RADIATION + i, rad);
				}
			}
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void receiveChunkUnload(ChunkEvent.Unload event) {
		
		if(!event.world.isRemote) {
			ThreeDimRadiationPerWorld radWorld = perWorld.get(event.world);
			
			if(radWorld != null) { 
				radWorld.radiation.remove(event.getChunk());
			}
		}
	}
	
	public static class ThreeDimRadiationPerWorld {
		
		public HashMap<ChunkCoordIntPair, Float[]> radiation = new HashMap();
	}
}
