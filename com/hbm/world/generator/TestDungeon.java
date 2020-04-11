package com.hbm.world.generator;

import com.hbm.blocks.ModBlocks;

public class TestDungeon extends CellularDungeon {

	public TestDungeon(int width, int height, int dimX, int dimZ, int tries) {
		super(width, height, dimX, dimZ, tries);

		this.floor.add(ModBlocks.meteor_polished);
		this.wall.add(ModBlocks.meteor_brick);
		this.wall.add(ModBlocks.meteor_brick);
		this.wall.add(ModBlocks.meteor_brick_mossy);
		this.wall.add(ModBlocks.meteor_brick_cracked);
		this.ceiling.add(ModBlocks.block_meteor_broken);
	}

}
