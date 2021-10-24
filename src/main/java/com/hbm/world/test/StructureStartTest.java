package com.hbm.world.test;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureStartTest extends StructureStart {

	public StructureStartTest(World world, Random rand, int x, int z) {
		System.out.println("StructureStartTest");
		this.components.add(new StructureComponentTest(x, z));
		this.updateBoundingBox();
	}
}