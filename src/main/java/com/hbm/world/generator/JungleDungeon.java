package com.hbm.world.generator;

import com.hbm.blocks.ModBlocks;

public class JungleDungeon extends CellularDungeon {

	public JungleDungeon(int width, int height, int dimX, int dimZ, int tries, int branches) {
		super(width, height, dimX, dimZ, tries, branches);

		this.floor.add(ModBlocks.brick_jungle);
		this.floor.add(ModBlocks.brick_jungle_cracked);
		this.wall.add(ModBlocks.brick_jungle);
		this.wall.add(ModBlocks.brick_jungle_cracked);
		this.ceiling.add(ModBlocks.brick_jungle);
		this.ceiling.add(ModBlocks.brick_jungle_cracked);
	}

}
