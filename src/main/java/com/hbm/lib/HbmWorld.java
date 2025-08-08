package com.hbm.lib;

import com.hbm.world.gen.MapGenNTMFeatures;
import com.hbm.world.gen.NTMWorldGenerator;
import com.hbm.world.gen.component.*;
import com.hbm.world.gen.component.BunkerComponents.BunkerStart;
import com.hbm.world.gen.nbt.NBTStructure;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
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
	}

	private static void registerWorldGen(IWorldGenerator nukerWorldGen, int weightedProbability) {
		GameRegistry.registerWorldGenerator(nukerWorldGen, weightedProbability);
	}

	/** Register structures in MapGenStructureIO */
	private static void registerNTMFeatures() {
		CivilianFeatures.registerComponents();
		OfficeFeatures.registerComponents();
		RuinFeatures.registerComponents();
		BunkerComponents.registerComponents();
		MapGenStructureIO.func_143031_a(SiloComponent.class, "NTMSiloComponent");
	}
}