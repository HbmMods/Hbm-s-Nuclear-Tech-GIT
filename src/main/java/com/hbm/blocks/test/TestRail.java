package com.hbm.blocks.test;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TestRail extends BlockContainer {

	public TestRail(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	/*
	 *     +x
	 *    1 2 3
	 * +z 4 0 5 -z
	 *    6 7 8
	 *     -x
	 */

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		if(!(player instanceof EntityPlayer))
			return;

		world.setBlock(x + 1,	y,	z + 1,	this, 1, 3);
		world.setBlock(x + 1,	y,	z,		this, 2, 3);
		world.setBlock(x + 1,	y,	z - 1,	this, 3, 3);
		world.setBlock(x,		y,	z + 1,	this, 4, 3);
		world.setBlock(x,		y,	z - 1,	this, 5, 3);
		world.setBlock(x - 1,	y,	z + 1,	this, 6, 3);
		world.setBlock(x - 1,	y,	z,		this, 7, 3);
		world.setBlock(x - 1,	y,	z - 1,	this, 8, 3);
	}
}
