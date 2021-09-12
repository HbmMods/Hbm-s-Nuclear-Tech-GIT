package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.handler.radiation.ChunkRadiationManager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPorous extends BlockStone {

	public BlockPorous() {
		super();
		this.setHardness(1.5F); //stone tier
		this.setResistance(300.0F); //ha
	}
	
	@Override
	protected ItemStack createStackedBlock(int meta) {
		//in theory this should keep the block silk-harvestable, but dropping smooth stone instead
		return new ItemStack(Blocks.stone);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public int tickRate(World world) {
		return 90 + world.rand.nextInt(20);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		ChunkRadiationManager.proxy.decrementRad(world, x, y, z, 10F);
		//world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}
	
	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return target == this || target == Blocks.stone;
	}
}
