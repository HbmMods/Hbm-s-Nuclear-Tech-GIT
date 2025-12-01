package com.hbm.world.gen.nbt.selector;

import java.util.Random;

public class BiomeFillerSelector extends BiomeBlockSelector {

	@Override
	public void selectBlocks(Random rand, int x, int y, int z, boolean notInterior) {
		field_151562_a = nextBiome.fillerBlock;
	}

}
