package com.hbm.world.worldgen;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.config.StructureConfig;
import com.hbm.world.worldgen.components.CivilianFeatures.*;
import com.hbm.world.worldgen.components.OfficeFeatures.*;
import com.hbm.world.worldgen.components.RuinFeatures.*;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBeach;
import net.minecraft.world.biome.BiomeGenMesa;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenNTMFeatures extends MapGenStructure {
	
	//BiomeDictionary could be /very/ useful, since it automatically sorts *all* biomes into predefined categories
	private static List biomelist = Arrays.asList(new BiomeGenBase[] {BiomeGenBase.ocean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.deepOcean});
	/** Maximum distance between structures */
	private int maxDistanceBetweenScatteredFeatures;
	/** Minimum distance between structures */
	private int minDistanceBetweenScatteredFeatures;
	
	public MapGenNTMFeatures() {
		this.maxDistanceBetweenScatteredFeatures = StructureConfig.structureMaxChunks;
		this.minDistanceBetweenScatteredFeatures = StructureConfig.structureMinChunks;
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
		
		public Start() {}
		
		public Start(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);
			
			BiomeGenBase biome = world.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8);
			int posY = world.getHeightValue(chunkX * 16 + 8, chunkZ * 16 + 8);
			if(posY == 0)
				posY = world.getTopSolidOrLiquidBlock(chunkX * 16 + 8, chunkZ * 16 + 8);
			
			/*
			 * Probably want to use nextInt() to increase the structures of rarity here. As a fallback, you could have generic stone brick/useless block ruins that will always be chosen if the
			 * chance/location fails for all other structures. Might not even be necessary, but whatever.
			 * Rainfall & Temperature Check
			 */
			//TODO: Do something about this so it's nice-looking and easily readable. Plus, test compatibility against mods like BoP
			if(rand.nextInt(3) == 0) { //Empty Ruin Structures
				switch(rand.nextInt(4)) {
				case 0:
					NTMRuin1 ruin1 = new NTMRuin1(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(ruin1);
					break;
				case 1:
					NTMRuin2 ruin2 = new NTMRuin2(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(ruin2);
					break;
				case 2:
					NTMRuin3 ruin3 = new NTMRuin3(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(ruin3);
					break;
				case 3:
					NTMRuin4 ruin4 = new NTMRuin4(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(ruin4);
				}
				
			} else if(biome.temperature >= 1.0 && biome.rainfall == 0 && !(biome instanceof BiomeGenMesa)) { //Desert & Savannah
				if(rand.nextBoolean()) {
					NTMHouse1 house1 = new NTMHouse1(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(house1);
				} else {
					NTMHouse2 house2 = new NTMHouse2(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(house2);
				}
				
			} else if(biome.temperature >= 0.25 && biome.temperature <= 0.3 && biome.rainfall >= 0.6 && biome.rainfall <= 0.9 && rand.nextBoolean()) { //Taiga & Mega Taiga
					NTMWorkshop1 workshop1 = new NTMWorkshop1(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(workshop1);
			} else { //Everything else
				switch(rand.nextInt(4)) {
				case 0:
					NTMLab2 lab2 = new NTMLab2(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(lab2); break;
				case 1:
					NTMLab1 lab1 = new NTMLab1(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(lab1); break;
				case 2:
					LargeOffice office = new LargeOffice(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(office); break;
				case 3:
					LargeOfficeCorner officeCorner = new LargeOfficeCorner(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
					this.components.add(officeCorner); break;
				}
			}
			
			if(GeneralConfig.enableDebugMode) {
				System.out.print("[Debug] StructureStart at " + (chunkX * 16 + 8) + ", " + posY + ", " + (chunkZ * 16 + 8) + "\n[Debug] Components: ");
				this.components.forEach((component) -> {
					System.out.print(MapGenStructureIO.func_143036_a((StructureComponent) component) + " ");
				});
				
				System.out.print("\n");
			}
			
			this.updateBoundingBox();
		}
	}
}
