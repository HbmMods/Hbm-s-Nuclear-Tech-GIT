package com.hbm.lib;

import com.hbm.world.worldgen.MapGenNTMFeatures;
import com.hbm.world.worldgen.NTMWorldGenerator;
import com.hbm.world.worldgen.components.CivilianFeatures;
import com.hbm.world.worldgen.components.OfficeFeatures;
import com.hbm.world.worldgen.components.RuinFeatures;

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
		registerNTMFeatures();
		
		registerWorldGen(new HbmWorldGen(), 1);
		registerWorldGen(new NTMWorldGenerator(), 1); //Ideally, move everything over from HbmWorldGen to NTMWorldGenerator
		//registerWorldGen(new WorldGenTest(), 1);
	}
	
	public static void registerWorldGen(IWorldGenerator nukerWorldGen, int weightedProbability) {
		GameRegistry.registerWorldGenerator(nukerWorldGen, weightedProbability);
	}
	
	/** Register structures in MapGenStructureIO */
	public static void registerNTMFeatures() {
		CivilianFeatures.registerComponents();
		OfficeFeatures.registerComponents();
		RuinFeatures.registerComponents();
	}
}
