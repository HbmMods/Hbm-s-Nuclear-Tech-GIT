package com.hbm.lib;

import com.hbm.world.test.StructureComponentTest;
import com.hbm.world.test.StructureStartTest;
import com.hbm.world.test.WorldGenTest;
import com.hbm.world.worldgen.ComponentNTMFeatures;
import com.hbm.world.worldgen.MapGenNTMFeatures;
import com.hbm.world.worldgen.NTMWorldGenerator;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class HbmWorld {
	
	public static void mainRegistry() {
		initWorldGen();
	}
	
	public static void initWorldGen() {

		//MapGenStructureIO.registerStructure(StructureStartTest.class, "HFR_STRUCTURE");
		//MapGenStructureIO.func_143031_a(StructureComponentTest.class, "HFR_COMPONENT");
		MapGenStructureIO.registerStructure(MapGenNTMFeatures.Start.class, "NTMFeatures");
		ComponentNTMFeatures.registerNTMFeatures();
		
		registerWorldGen(new HbmWorldGen(), 1);
		registerWorldGen(new NTMWorldGenerator(), 1);
		//registerWorldGen(new WorldGenTest(), 1);
	}
	
	public static void registerWorldGen(IWorldGenerator nukerWorldGen, int weightedProbability) {
		GameRegistry.registerWorldGenerator(nukerWorldGen, weightedProbability);
	}
}
