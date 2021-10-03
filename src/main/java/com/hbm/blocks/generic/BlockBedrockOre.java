package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import api.hbm.block.IDrillInteraction;
import api.hbm.block.IMiningDrill;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockBedrockOre extends Block implements IDrillInteraction {

	public BlockBedrockOre() {
		super(Material.rock);
	}

	@Override
	public boolean canBreak(World world, int x, int y, int z, int meta, IMiningDrill drill) {
		return drill.getDrillRating() > 70;
	}

	@Override
	public ItemStack extractResource(World world, int x, int y, int z, int meta, IMiningDrill drill) {
		
		if(drill.getDrillRating() > 70)
			return null;
		
		Item drop = this.getDrop();
		
		if(drop == null)
			return null;
		
		return world.rand.nextInt(50) == 0 ? new ItemStack(drop) : null;
	}

	@Override
	public float getRelativeHardness(World world, int x, int y, int z, int meta, IMiningDrill drill) {
		return 30;
	}
	
	private Item getDrop() {

		if(this == ModBlocks.ore_bedrock_coltan)
			return ModItems.fragment_coltan;
		
		return null;
	}
}
