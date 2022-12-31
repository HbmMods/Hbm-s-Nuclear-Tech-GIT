package com.hbm.blocks.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPorous extends BlockStone {

	public BlockPorous() {
		super();
		this.setHardness(1.5F);
		this.setResistance(30.0F);
	}
	
	@Override
	protected ItemStack createStackedBlock(int meta) {
		//in theory this should keep the block silk-harvestable, but dropping smooth stone instead
		return new ItemStack(Blocks.stone);
	}
	
	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return target == this || target == Blocks.stone;
	}
}
