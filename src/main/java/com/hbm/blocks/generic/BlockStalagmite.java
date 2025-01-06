package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.BlockEnums;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockStalagmite extends BlockEnumMulti {

	public BlockStalagmite() {
		super(Material.rock, BlockEnums.EnumStalagmiteType.class, true, true);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	public Item getItemDropped(int meta, Random rang, int fortune) {
		
		switch(meta) {
		case 0: return ModItems.sulfur;
		case 1: return ModItems.powder_asbestos;
		}
		
		return null;
	}
	
	public static int getMetaFromResource(int meta) {
		return meta;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {

		if(this == ModBlocks.stalagmite) return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z);
		if(this == ModBlocks.stalactite) return world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN);
		
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(!canPlaceBlockAt(world, x, y, z)) {
			world.func_147480_a(x, y, z, true);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return ModBlocks.getDropsWithoutDamage(world, this, metadata, fortune);
	}

}
