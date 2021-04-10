package com.hbm.blocks.generic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FragileBrick extends Block {

	public FragileBrick(Material mat) {
		super(mat);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(world.isRemote)
			return;
		
		world.func_147480_a(x, y, z, false);
		notifyNeighbors(world, x, y, z);
	}
	
	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		
		if(world.isRemote)
			return;
		
		world.func_147480_a(x, y, z, false);
		notifyNeighbors(world, x, y, z);
	}
	
	private void notifyNeighbors(World world, int x, int y, int z) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			Block n = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

			if(n == this) {
				world.scheduleBlockUpdate(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, this, world.rand.nextInt(4) + 8);
			}
		}
	}

	public void breakBlock(World world, int x, int y, int z, Block b, int m) {
		notifyNeighbors(world, x, y, z);
	}
}
