package com.hbm.lib;

import com.hbm.world.test.StructureComponentTest;
import com.hbm.world.test.StructureStartTest;
import com.hbm.world.test.WorldGenTest;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class HbmWorld {
	
	public static void mainRegistry() {
		initWorldGen();
	}
	
	public static void initWorldGen() {

		MapGenStructureIO.registerStructure(StructureStartTest.class, "HFR_STRUCTURE");
		MapGenStructureIO.func_143031_a(StructureComponentTest.class, "HFR_COMPONENT");
		
		registerWorldGen(new HbmWorldGen(), 1);
		//registerWorldGen(new WorldGenTest(), 1);
	}
	
	public static void registerWorldGen(IWorldGenerator nukerWorldGen, int weightedProbability) {
		GameRegistry.registerWorldGenerator(nukerWorldGen, weightedProbability);
	}
}
