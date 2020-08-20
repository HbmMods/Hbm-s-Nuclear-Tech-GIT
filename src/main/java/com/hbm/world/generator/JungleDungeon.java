package com.hbm.world.generator;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.MetaBlock;

public class JungleDungeon extends CellularDungeon {

	public JungleDungeon(int width, int height, int dimX, int dimZ, int tries, int branches) {
		super(width, height, dimX, dimZ, tries, branches);

		this.floor.add(new MetaBlock(ModBlocks.brick_jungle));
		this.floor.add(new MetaBlock(ModBlocks.brick_jungle_cracked));
		
		for(int i = 0; i < 50; i++) {
			this.wall.add(new MetaBlock(ModBlocks.brick_jungle));
			this.wall.add(new MetaBlock(ModBlocks.brick_jungle_cracked));
		}
		for(int i = 0; i < 16; i++) {
			this.wall.add(new MetaBlock(ModBlocks.brick_jungle_glyph, i));
		}
		
		this.ceiling.add(new MetaBlock(ModBlocks.brick_jungle));
		this.ceiling.add(new MetaBlock(ModBlocks.brick_jungle_cracked));
	}

}
