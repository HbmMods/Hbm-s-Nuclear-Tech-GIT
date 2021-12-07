package com.hbm.world.test;

import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenTest extends MapGenStructure {

	@Override
	public String func_143025_a() {
		return "HFR_TEST";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int x, int z) {
		return true;
	}

	@Override
	protected StructureStart getStructureStart(int x, int z) {
		return new StructureStartTest(worldObj, rand, x, z);
	}

}
