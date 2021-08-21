package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class BlockCluster extends Block {

	public BlockCluster(Material mat) {
		super(mat);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		
		if(player instanceof FakePlayer || player == null) {
			return;
		}

		if(!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots) {
			
			Item drop = getDrop();
			
			if(drop == null)
				return;
			
			float f = 0.7F;
			double mX = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double mY = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double mZ = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			
			EntityItem entityitem = new EntityItem(world, (double) x + mX, (double) y + mY, (double) z + mZ, new ItemStack(drop));
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}
	
	private Item getDrop() {

		if(this == ModBlocks.cluster_iron)
			return ModItems.crystal_iron;
		if(this == ModBlocks.cluster_titanium)
			return ModItems.crystal_titanium;
		if(this == ModBlocks.cluster_aluminium)
			return ModItems.crystal_aluminium;
		if(this == ModBlocks.basalt_gem)
			return ModItems.gem_volcanic;
		
		return null;
	}
}
