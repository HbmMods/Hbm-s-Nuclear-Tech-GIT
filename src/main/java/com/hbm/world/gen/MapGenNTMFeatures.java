package com.hbm.world.gen;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.config.StructureConfig;
import com.hbm.world.gen.component.BunkerComponents.BunkerStart;
import com.hbm.world.gen.component.CivilianFeatures.NTMHouse1;
import com.hbm.world.gen.component.CivilianFeatures.NTMHouse2;
import com.hbm.world.gen.component.CivilianFeatures.NTMLab1;
import com.hbm.world.gen.component.CivilianFeatures.NTMLab2;
import com.hbm.world.gen.component.CivilianFeatures.RuralHouse1;
import com.hbm.world.gen.component.OfficeFeatures.LargeOffice;
import com.hbm.world.gen.component.OfficeFeatures.LargeOfficeCorner;
import com.hbm.world.gen.component.RuinFeatures.NTMRuin1;
import com.hbm.world.gen.component.RuinFeatures.NTMRuin2;
import com.hbm.world.gen.component.RuinFeatures.NTMRuin3;
import com.hbm.world.gen.component.RuinFeatures.NTMRuin4;
import com.hbm.world.gen.component.SiloComponent;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenMesa;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenNTMFeatures extends MapGenStructure {

	//BiomeDictionary could be /very/ useful, since it automatically sorts *all* biomes into predefined categories
	private static List biomelist;
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

			if(biomelist == null) {
				biomelist = Arrays.asList(new BiomeGenBase[] {BiomeGenBase.ocean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.deepOcean});
			}

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
		if(this.rand.nextInt(15) == 0) { //eh might as well, they'll already be prettty rare anyway
			return new BunkerStart(this.worldObj, this.rand, chunkX, chunkZ);
		}
		return new MapGenNTMFeatures.Start(this.worldObj, this.rand, chunkX, chunkZ);
	}

	public static class Start extends StructureStart {

		public Start() {}

		public Start(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);

			int i = (chunkX << 4) + 8;
			int j = (chunkZ << 4) + 8;

			BiomeGenBase biome = world.getBiomeGenForCoords(i, j); //Only gets the biome in the corner of the chunk.

			/*
			 * Probably want to use nextInt() to increase the structures of rarity here. As a fallback, you could have generic stone brick/useless block ruins that will always be chosen if the
			 * chance/location fails for all other structures. Might not even be necessary, but whatever.
			 * Rainfall & Temperature Check
			 */
			//TODO: Do something about this so it's nice-looking and easily readable. Plus, test compatibility against mods like BoP

			if(rand.nextInt(3) == 0) { //Empty Ruin Structures
				switch(rand.nextInt(4)) {
				case 0:
					NTMRuin1 ruin1 = new NTMRuin1(rand, i, j);
					this.components.add(ruin1);
					break;
				case 1:
					NTMRuin2 ruin2 = new NTMRuin2(rand, i, j);
					this.components.add(ruin2);
					break;
				case 2:
					NTMRuin3 ruin3 = new NTMRuin3(rand, i, j);
					this.components.add(ruin3);
					break;
				case 3:
					NTMRuin4 ruin4 = new NTMRuin4(rand, i, j);
					this.components.add(ruin4);
				}

			} else if(biome.heightVariation <= 0.25F && rand.nextInt(10) == 0) { //for now our only restriction is kinda-flat biomes. that and chance might change idk
				SiloComponent silo = new SiloComponent(rand, i, j);
				this.components.add(silo);
			} else if(biome.temperature >= 1.0 && biome.rainfall == 0 && !(biome instanceof BiomeGenMesa)) { //Desert & Savannah
				if(rand.nextBoolean()) {
					NTMHouse1 house1 = new NTMHouse1(rand, i, j);
					this.components.add(house1);
				} else {
					NTMHouse2 house2 = new NTMHouse2(rand, i, j);
					this.components.add(house2);
				}
			} else { //Everything else
				switch(rand.nextInt(6)) {
				case 0:
					NTMLab2 lab2 = new NTMLab2(rand, i, j); //and these, too
					this.components.add(lab2); break;
				case 1:
					NTMLab1 lab1 = new NTMLab1(rand, i, j);
					this.components.add(lab1); break;
				case 2:
					LargeOffice office = new LargeOffice(rand, i, j);
					this.components.add(office); break;
				case 3:
					LargeOfficeCorner officeCorner = new LargeOfficeCorner(rand, i, j);
					this.components.add(officeCorner); break;
				case 4:
				case 5:
					RuralHouse1 ruralHouse = new RuralHouse1(rand, i, j);
					this.components.add(ruralHouse); break;
				}
			}

			if(GeneralConfig.enableDebugMode) {
				System.out.print("[Debug] StructureStart at " + i + ", 64, " + j + "\n[Debug] Components: ");
				this.components.forEach((component) -> {
					System.out.print(MapGenStructureIO.func_143036_a((StructureComponent) component) + " ");
				});

				System.out.print("\n");
			}

			this.updateBoundingBox();
		}
	}
}
