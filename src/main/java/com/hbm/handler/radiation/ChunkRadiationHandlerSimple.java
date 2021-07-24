package com.hbm.handler.radiation;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * Most basic implementation of a chunk radiation system: Each chunk has a radiation value which spreads out to its neighbors.
 * @author hbm
 */
public class ChunkRadiationHandlerSimple extends ChunkRadiationHandler {
	
	private HashMap<World, SimpleRadiationPerWorld> perWorld = new HashMap();

	@Override
	public float getRadiation(World world, int x, int y, int z) {
		SimpleRadiationPerWorld radWorld = perWorld.get(world);
		
		if(radWorld != null) {
			ChunkCoordIntPair coords = new ChunkCoordIntPair(x >> 4, z >> 4);
			Float rad = radWorld.radiation.get(coords);
			return rad == null ? 0F : rad;
		}
		
		return 0;
	}

	@Override
	public void setRadiation(World world, int x, int y, int z, float rad) {
		SimpleRadiationPerWorld radWorld = perWorld.get(world);
		
		if(radWorld != null) {
			
			if(world.blockExists(x, 0, z)) {
				
				ChunkCoordIntPair coords = new ChunkCoordIntPair(x >> 4, z >> 4);
				
				if(radWorld.radiation.containsKey(coords)) {
					radWorld.radiation.put(coords, rad);
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

	@Override
	public void updateSystem() {
		
		for(Entry<World, SimpleRadiationPerWorld> entry : perWorld.entrySet()) {
			
			HashMap<ChunkCoordIntPair, Float> radiation = entry.getValue().radiation;
			HashMap<ChunkCoordIntPair, Float> buff = new HashMap(radiation);
			radiation.clear();
			
			for(Entry<ChunkCoordIntPair, Float> chunk : buff.entrySet()) {
				
				ChunkCoordIntPair coord = chunk.getKey();
				
				for(int i = -1; i <= 1; i++) {
					for(int j = -1; j<= 1; j++) {
						
						int type = Math.abs(i) + Math.abs(j);
						float percent = type == 0 ? 0.6F : type == 1 ? 0.075F : 0.025F;
						ChunkCoordIntPair newCoord = new ChunkCoordIntPair(coord.chunkXPos + i, coord.chunkZPos + j);
						
						if(buff.containsKey(newCoord)) {
							Float val = radiation.get(newCoord);
							float rad = val == null ? 0 : val;
							float newRad = rad + chunk.getValue() * percent;
							newRad = Math.max(0F, newRad * 0.99F - 0.05F);
							radiation.put(newCoord, newRad);
						}
					}
				}
			}
		}
	}

	@Override
	public void receiveWorldLoad(WorldEvent.Load event) {
		if(!event.world.isRemote)
			perWorld.put(event.world, new SimpleRadiationPerWorld());
	}

	@Override
	public void receiveWorldUnload(WorldEvent.Unload event) {
		if(!event.world.isRemote)
			perWorld.remove(event.world);
	}
	
	private static final String NBT_KEY_CHUNK_RADIATION = "hfr_simple_radiation";

	@Override
	public void receiveChunkLoad(ChunkDataEvent.Load event) {
		
		if(!event.world.isRemote) {
			SimpleRadiationPerWorld radWorld = perWorld.get(event.world);
			
			if(radWorld != null) {
				radWorld.radiation.put(event.getChunk().getChunkCoordIntPair(), event.getData().getFloat(NBT_KEY_CHUNK_RADIATION));
			}
		}
	}

	@Override
	public void receiveChunkSave(ChunkDataEvent.Save event) {
		
		if(!event.world.isRemote) {
			SimpleRadiationPerWorld radWorld = perWorld.get(event.world);
			
			if(radWorld != null) {
				Float val = radWorld.radiation.get(event.getChunk().getChunkCoordIntPair());
				float rad = val == null ? 0F : val;
				event.getData().setFloat(NBT_KEY_CHUNK_RADIATION, rad);
			}
		}
	}

	@Override
	public void receiveChunkUnload(ChunkEvent.Unload event) {
		
		if(!event.world.isRemote) {
			SimpleRadiationPerWorld radWorld = perWorld.get(event.world);
			
			if(radWorld != null) {
				radWorld.radiation.remove(event.getChunk());
			}
		}
	}
	
	public static class SimpleRadiationPerWorld {
		
		public HashMap<ChunkCoordIntPair, Float> radiation = new HashMap();
	}
}
