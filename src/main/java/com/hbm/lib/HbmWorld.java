package com.hbm.lib;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.config.WorldConfig;
import com.hbm.world.gen.MapGenChainloader;
import com.hbm.world.gen.MapGenNTMFeatures;
import com.hbm.world.gen.NTMWorldGenerator;
import com.hbm.world.gen.component.*;
import com.hbm.world.gen.component.BunkerComponents.BunkerStart;
import com.hbm.world.gen.nbt.NBTStructure;
import com.hbm.world.gen.terrain.MapGenBedrockOil;
import com.hbm.world.gen.terrain.MapGenBubble;
import com.hbm.world.gen.terrain.MapGenCrater;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;

public class HbmWorld {

	public static void mainRegistry() {
		initWorldGen();
	}

	public static NTMWorldGenerator worldGenerator;

	public static void initWorldGen() {
		MapGenStructureIO.registerStructure(MapGenNTMFeatures.Start.class, "NTMFeatures");
		MapGenStructureIO.registerStructure(BunkerStart.class, "NTMBunker");
		registerNTMFeatures();

		registerWorldGen(new HbmWorldGen(), 1);

		worldGenerator = new NTMWorldGenerator();
		registerWorldGen(worldGenerator, 1); //Ideally, move everything over from HbmWorldGen to NTMWorldGenerator
		MinecraftForge.EVENT_BUS.register(worldGenerator);

		NBTStructure.register();

		MapGenChainloader.register();
		registerNTMTerrain();
	}

	private static void registerWorldGen(IWorldGenerator nukerWorldGen, int weightedProbability) {
		GameRegistry.registerWorldGenerator(nukerWorldGen, weightedProbability);
	}

	/** Register structures in MapGenStructureIO */
	private static void registerNTMFeatures() {
		CivilianFeatures.registerComponents();
		OfficeFeatures.registerComponents();
//		RuinFeatures.registerComponents();
		BunkerComponents.registerComponents();
		MapGenStructureIO.func_143031_a(SiloComponent.class, "NTMSiloComponent");
	}

	/** Register multi-chunk spanning terrain features using chainloader */
	private static void registerNTMTerrain() {
		if(GeneralConfig.enableRad && WorldConfig.radfreq > 0) {
			MapGenCrater sellafieldCrater = new MapGenCrater(WorldConfig.radfreq);
			sellafieldCrater.regolith = sellafieldCrater.rock = ModBlocks.sellafield_slaked;
			sellafieldCrater.targetBiome = BiomeGenBase.desert;
			MapGenChainloader.addOverworldGenerator(sellafieldCrater);
		}

		if(WorldConfig.oilSpawn > 0) {
			MapGenBubble oilBubble = new MapGenBubble(WorldConfig.oilSpawn);
			oilBubble.block = ModBlocks.ore_oil;
			oilBubble.setSize(8, 16);
			MapGenChainloader.addOverworldGenerator(oilBubble);
		}

		if(WorldConfig.bedrockOilSpawn > 0) {
			MapGenBedrockOil bedrockBubble = new MapGenBedrockOil(WorldConfig.bedrockOilSpawn);
			MapGenChainloader.addOverworldGenerator(bedrockBubble);
		}

		int sandBubbleSpawn = 200;
		if(sandBubbleSpawn > 0) {
			MapGenBubble sandOilBubble = new MapGenBubble(sandBubbleSpawn);
			sandOilBubble.replace = Blocks.sand;
			sandOilBubble.block = ModBlocks.ore_oil_sand;
			sandOilBubble.canSpawn = biome -> !biome.canSpawnLightningBolt() && biome.temperature >= 1.5F;
			sandOilBubble.minY = 56;
			sandOilBubble.rangeY = 16;
			sandOilBubble.setSize(16, 48);
			sandOilBubble.fuzzy = true;
			MapGenChainloader.addOverworldGenerator(sandOilBubble);
		}
	}

}
