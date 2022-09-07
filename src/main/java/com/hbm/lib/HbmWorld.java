package com.hbm.lib;

import com.hbm.world.worldgen.MapGenNTMFeatures;
import com.hbm.world.worldgen.NTMWorldGenerator;
import com.hbm.world.worldgen.components.CivilianFeatures.*;
import com.hbm.world.worldgen.components.MilitaryBaseFeatures.*;
import com.hbm.world.worldgen.components.OfficeFeatures.*;
import com.hbm.world.worldgen.components.RuinFeatures.*;

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
		MapGenStructureIO.func_143031_a(NTMHouse1.class, "NTMHouse1");
		MapGenStructureIO.func_143031_a(NTMHouse2.class, "NTMHouse2");
		MapGenStructureIO.func_143031_a(NTMLab1.class, "NTMLab1");
		MapGenStructureIO.func_143031_a(NTMLab2.class, "NTMLab2");
		MapGenStructureIO.func_143031_a(NTMWorkshop1.class, "NTMWorkshop1");
		MapGenStructureIO.func_143031_a(NTMRuin1.class, "NTMRuin1");
		MapGenStructureIO.func_143031_a(NTMRuin2.class, "NTMRuin2");
		MapGenStructureIO.func_143031_a(NTMRuin3.class, "NTMRuin3");
		MapGenStructureIO.func_143031_a(NTMRuin4.class, "NTMRuin4");
		//aggggggggggg
		MapGenStructureIO.func_143031_a(BasicHelipad.class, "NTMBasicHelipad");
		MapGenStructureIO.func_143031_a(RadioShack.class, "NTMRadioShack");
		MapGenStructureIO.func_143031_a(LargeOffice.class, "NTMLargeOffice");
	}
}
