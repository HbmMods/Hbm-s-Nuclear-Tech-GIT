package com.hbm.world.test;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class StructureComponentTest extends StructureComponent {
	
	public StructureComponentTest(int x, int z) {
		this.boundingBox = new StructureBoundingBox(x, 64, z, x + 4, 64 + 9, z + 4);
	}


	//write
	@Override
	protected void func_143012_a(NBTTagCompound nbt) {
		
	}

	//read
	@Override
	protected void func_143011_b(NBTTagCompound nbt) {
		
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingbox) {
		System.out.println("aaaaaaa");
		this.fillWithBlocks(world, boundingbox, 0, 0, 0, 4, 9, 4, Blocks.sandstone, Blocks.air, false);
		return true;
	}

}
