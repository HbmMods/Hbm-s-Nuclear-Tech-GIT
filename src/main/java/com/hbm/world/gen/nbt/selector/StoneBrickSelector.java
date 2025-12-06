package com.hbm.world.gen.nbt.selector;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureComponent.BlockSelector;

public class StoneBrickSelector extends BlockSelector {

	@Override
	public void selectBlocks(Random rand, int x, int y, int z, boolean notInterior) {
		field_151562_a = Blocks.stonebrick;
		float f = rand.nextFloat();

		if (f < 0.2F) {
			this.selectedBlockMetaData = 2;
		} else if (f < 0.5F) {
			this.selectedBlockMetaData = 1;
		} else {
			this.selectedBlockMetaData = 0;
		}
	}

}
