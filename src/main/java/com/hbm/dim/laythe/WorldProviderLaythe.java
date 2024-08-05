package com.hbm.dim.laythe;

import com.hbm.dim.WorldChunkManagerCelestial.BiomeGenLayers;
import com.hbm.dim.WorldChunkManagerCelestial;
import com.hbm.dim.WorldProviderCelestial;
import com.hbm.dim.laythe.GenLayerLaythe.GenLayerDiversifyLaythe;
import com.hbm.dim.laythe.GenLayerLaythe.GenLayerLaytheBiomes;
import com.hbm.dim.laythe.GenLayerLaythe.GenLayerLaytheIslands;
import com.hbm.dim.laythe.GenLayerLaythe.GenLayerLaytheOceans;
import com.hbm.dim.laythe.GenLayerLaythe.GenLayerLaythePolar;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderLaythe extends WorldProviderCelestial {

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerCelestial(createBiomeGenerators(worldObj.getSeed()));
	}

	@Override
	public String getDimensionName() {
		return "Laythe";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderLaythe(this.worldObj, this.getSeed(), false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderLaytheSunset();
	}

	@Override
	public boolean hasLife() {
		return true;
	}

	@Override
	public int getWaterOpacity() {
		return 1;
	}

	@Override
	public boolean updateLightmap(int[] lightmap) {
		for(int i = 0; i < 256; i++) {
			float sun = getSunBrightness(1.0F);
			float sky = lightBrightnessTable[i / 16];
			float jool = Math.max(sky - sun, 0);

			int[] color = unpackColor(lightmap[i]);

			color[1] += jool * 60;
			if(color[1] > 255) color[1] = 255;

			lightmap[i] = packColor(color);
		}
		return true;
	}

	private static BiomeGenLayers createBiomeGenerators(long seed) {
		GenLayer biomes = new GenLayerLaytheBiomes(seed);
		GenLayer polar = new GenLayerLaythePolar(1000L, biomes);
		
		
		
		biomes = new GenLayerFuzzyZoom(2000L, biomes);

		biomes = new GenLayerZoom(2001L, biomes);
		
		
		polar = new GenLayerZoom(1000L, polar);
		GenLayer polarmag = GenLayerZoom.magnify(1000L, polar, 1);
		biomes = new GenLayerLaythePolar(1000L, polarmag);
		
		biomes = new GenLayerDiversifyLaythe(1000L, biomes);
		
		biomes = new GenLayerZoom(1000L, biomes);
		biomes = new GenLayerZoom(1001L, biomes);

		biomes = new GenLayerLaytheOceans(4000L, biomes);
		biomes = new GenLayerLaytheOceans(4000L, biomes);
		biomes = new GenLayerLaytheOceans(4000L, biomes);
		biomes = new GenLayerLaytheOceans(4000L, biomes);
		
		GenLayer oceanGenLayer = new GenLayerLaytheOceans(4000L, biomes);
		oceanGenLayer = GenLayerZoom.magnify(4000L, biomes, 0);
		
		biomes = new GenLayerZoom(1003L, biomes);
		biomes = new GenLayerSmooth(700L, biomes);
		biomes = new GenLayerLaytheIslands(200L, biomes);

		biomes = new GenLayerZoom(1006L, biomes);
			
		GenLayer genLayerVoronoiZoom = new GenLayerVoronoiZoom(10L, biomes);

		return new BiomeGenLayers(biomes, genLayerVoronoiZoom, seed);
	}

}