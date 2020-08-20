package com.hbm.world.generator;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.MetaBlock;

public class TestDungeon extends CellularDungeon {

	public TestDungeon(int width, int height, int dimX, int dimZ, int tries, int branches) {
		super(width, height, dimX, dimZ, tries, branches);

		this.floor.add(new MetaBlock(ModBlocks.meteor_polished));
		this.wall.add(new MetaBlock(ModBlocks.meteor_brick));
		this.wall.add(new MetaBlock(ModBlocks.meteor_brick));
		this.wall.add(new MetaBlock(ModBlocks.meteor_brick_mossy));
		this.wall.add(new MetaBlock(ModBlocks.meteor_brick_cracked));
		this.ceiling.add(new MetaBlock(ModBlocks.block_meteor_broken));
	}

}
