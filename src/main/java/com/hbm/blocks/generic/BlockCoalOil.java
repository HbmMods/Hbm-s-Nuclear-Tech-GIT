package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockCoalOil extends Block {

	public BlockCoalOil(Material mat) {
		super(mat);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		world.setBlock(x, y, z, ModBlocks.ore_coal_oil_burning);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return Items.coal;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 2 + rand.nextInt(2);
	}
}
