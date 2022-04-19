package com.hbm.world.worldgen;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenMesa;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenNTMFeatures extends MapGenStructure {
	
	private static List biomelist = Arrays.asList(new BiomeGenBase[] {BiomeGenBase.ocean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.deepOcean});
	/** Maximum distance between structures */
	private int maxDistanceBetweenScatteredFeatures;
	/** Minimum distance between structures */
	private int minDistanceBetweenScatteredFeatures;
	
	public MapGenNTMFeatures() {
		this.maxDistanceBetweenScatteredFeatures = 24;
		this.minDistanceBetweenScatteredFeatures = 8;
	}
	
	/** String ID for this MapGen */
	@Override
	public String func_143025_a() {
		return "NTMFeatures";
	}
	
	/**
	 * Checks if a structure can be spawned at coords, based off of chance and biome
	 * (Good approach would probably be to only exclude ocean biomes through biomelist and rely on temperature and rainfall instead of biomegenbase, would allow for biomes o' plenty compat)
	 */
	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		
		int k = chunkX;
		int l = chunkZ;
		
		if(chunkX < 0)
			chunkX -= this.maxDistanceBetweenScatteredFeatures - 1;
		if(chunkZ < 0)
			chunkZ -= this.maxDistanceBetweenScatteredFeatures - 1;
		
		int i1 = chunkX / this.maxDistanceBetweenScatteredFeatures;
		int j1 = chunkZ / this.maxDistanceBetweenScatteredFeatures;
		Random random = this.worldObj.setRandomSeed(i1, j1, 14357617);
		i1 *= this.maxDistanceBetweenScatteredFeatures;
		j1 *= this.maxDistanceBetweenScatteredFeatures;
		i1 += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
		j1 += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
		
		if(k == i1 && l == j1) {
			BiomeGenBase biomegenbase = this.worldObj.getWorldChunkManager().getBiomeGenAt(k * 16 + 8, l * 16 + 8);
			Iterator iterator = biomelist.iterator();
			
			while(iterator.hasNext()) {
				BiomeGenBase biomegenbase1 = (BiomeGenBase)iterator.next();
				
				if(biomegenbase == biomegenbase1)
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	
	//StructureStart Methods Class
	
	/** Returns new StructureStart if structure can be spawned at coords */
	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new MapGenNTMFeatures.Start(this.worldObj, this.rand, chunkX, chunkZ);
	}
	
	public static class Start extends StructureStart {
		
		public Start(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);
			
			//annoying predetermination angre
			rand.setSeed((chunkX + chunkZ) * 31);
			
			BiomeGenBase biomegenbase = world.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8);
			int posY = world.getHeightValue(chunkX * 16 + 8, chunkZ * 16 + 8);
			if(posY == 0)
				posY = world.getTopSolidOrLiquidBlock(chunkX * 16 + 8, chunkZ * 16 + 8);
			
			/*
			 * Probably want to use nextInt() to increase the structures of rarity here. As a fallback, you could have generic stone brick/useless block ruins that will always be chosen if the
			 * chance/location fails for all other structures. Might not even be necessary, but whatever.
			 * Rainfall & Temperature Check
			 */
			
			if(biomegenbase.temperature >= 1.2 && biomegenbase.rainfall == 0 && !(biomegenbase instanceof BiomeGenMesa)) {
				ComponentNTMFeatures.NTMHouse1 house1 = new ComponentNTMFeatures.NTMHouse1(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
				this.components.add(house1);
			} else {
				if(rand.nextBoolean()) {
					ComponentNTMFeatures.NTMLab2 lab2 = new ComponentNTMFeatures.NTMLab2(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(lab2);
				} else {
					ComponentNTMFeatures.NTMLab1 lab1 = new ComponentNTMFeatures.NTMLab1(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(lab1);
				}
			}
			
			this.updateBoundingBox();
		}
	}
}
