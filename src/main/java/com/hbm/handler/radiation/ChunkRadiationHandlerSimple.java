package com.hbm.handler.radiation;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.RadiationConfig;
import com.hbm.main.MainRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * Most basic implementation of a chunk radiation system: Each chunk has a radiation value which spreads out to its neighbors.
 * @author hbm
 */
public class ChunkRadiationHandlerSimple extends ChunkRadiationHandler {

	private HashMap<World, SimpleRadiationPerWorld> perWorld = new HashMap();
	private static final float maxRad = 100_000F;

	@Override
	public float getRadiation(World world, int x, int y, int z) {
		SimpleRadiationPerWorld radWorld = perWorld.get(world);

		if(radWorld != null) {
			ChunkCoordIntPair coords = new ChunkCoordIntPair(x >> 4, z >> 4);
			Float rad = radWorld.radiation.get(coords);
			return rad == null ? 0F : MathHelper.clamp_float(rad, 0, maxRad);
		}

		return 0;
	}

	@Override
	public void setRadiation(World world, int x, int y, int z, float rad) {
		SimpleRadiationPerWorld radWorld = perWorld.get(world);

		if(radWorld != null) {

			if(world.blockExists(x, 0, z)) {

				ChunkCoordIntPair coords = new ChunkCoordIntPair(x >> 4, z >> 4);
				radWorld.radiation.put(coords, MathHelper.clamp_float(rad, 0, maxRad));
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
			World world = entry.getKey();

			for(Entry<ChunkCoordIntPair, Float> chunk : buff.entrySet()) {

				if(chunk.getValue() == 0)
					continue;

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
							newRad = MathHelper.clamp_float(0F, newRad * 0.99F - 0.05F, maxRad);
							radiation.put(newCoord, newRad);
						} else {
							radiation.put(newCoord, chunk.getValue() * percent);
						}

						float rad = radiation.get(newCoord);
						if(rad > RadiationConfig.fogRad && world != null && world.rand.nextInt(RadiationConfig.fogCh) == 0 && world.getChunkProvider().chunkExists(coord.chunkXPos, coord.chunkZPos)) {

							int x = coord.chunkXPos * 16 + world.rand.nextInt(16);
							int z = coord.chunkZPos * 16 + world.rand.nextInt(16);
							int y = world.getHeightValue(x, z) + world.rand.nextInt(5);

							NBTTagCompound data = new NBTTagCompound();
							data.setString("type", "radFog");
							data.setDouble("posX", x);
							data.setDouble("posY", y);
							data.setDouble("posZ", z);
							MainRegistry.proxy.effectNT(data);
						}
					}
				}
			}
		}
	}

	@Override
	public void clearSystem(World world) {
		SimpleRadiationPerWorld radWorld = perWorld.get(world);

		if(radWorld != null) {
			radWorld.radiation.clear();
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

	@Override
	public void handleWorldDestruction() {

		int count = 10;
		int threshold = 10;
		int chunks = 5;

		//for all worlds
		for(Entry<World, SimpleRadiationPerWorld> per : perWorld.entrySet()) {

			World world = per.getKey();
			SimpleRadiationPerWorld list = per.getValue();

			Object[] entries = list.radiation.entrySet().toArray();

			if(entries.length == 0)
				continue;

			//chose this many random chunks
			for(int c = 0; c < chunks; c++) {

				Entry<ChunkCoordIntPair, Float> randEnt = (Entry<ChunkCoordIntPair, Float>) entries[world.rand.nextInt(entries.length)];

				ChunkCoordIntPair coords = randEnt.getKey();
				WorldServer serv = (WorldServer) world;
				ChunkProviderServer provider = (ChunkProviderServer) serv.getChunkProvider();

				//choose this many random locations within the chunk
				for(int i = 0; i < count; i++) {

					if(randEnt == null || randEnt.getValue() < threshold)
						continue;

					if(provider.chunkExists(coords.chunkXPos, coords.chunkZPos)) {

						for(int a = 0; a < 16; a++) {
							for(int b = 0; b < 16; b++) {

								if(world.rand.nextInt(3) != 0)
									continue;

								int x = coords.getCenterXPos() - 8 + a;
								int z = coords.getCenterZPosition() - 8 + b;
								int y = world.getHeightValue(x, z) - world.rand.nextInt(2);

								if(world.getBlock(x, y, z) == Blocks.grass) {
									world.setBlock(x, y, z, ModBlocks.waste_earth);

								} else if(world.getBlock(x, y, z) == Blocks.tallgrass) {
									world.setBlock(x, y, z, Blocks.air);

								} else if(world.getBlock(x, y, z).getMaterial() == Material.leaves && !(world.getBlock(x, y, z) == ModBlocks.waste_leaves)) {

									if(world.rand.nextInt(7) <= 5) {
										world.setBlock(x, y, z, ModBlocks.waste_leaves);
									} else {
										world.setBlock(x, y, z, Blocks.air);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
