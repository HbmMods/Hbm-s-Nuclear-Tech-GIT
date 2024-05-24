package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

import api.hbm.block.IDrillInteraction;
import api.hbm.block.IMiningDrill;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class BlockCluster extends BlockOre implements IDrillInteraction, ITooltipProvider {

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
		if(this == ModBlocks.cluster_iron)		return ModItems.crystal_iron;
		if(this == ModBlocks.cluster_titanium)	return ModItems.crystal_titanium;
		if(this == ModBlocks.cluster_aluminium)	return ModItems.crystal_aluminium;
		if(this == ModBlocks.cluster_copper)	return ModItems.crystal_copper;
		
		return null;
	}

	@Override
	public boolean canBreak(World world, int x, int y, int z, int meta, IMiningDrill drill) {
		return drill.getDrillRating() > 70 || world.rand.nextFloat() < 0.05;
	}

	@Override
	public ItemStack extractResource(World world, int x, int y, int z, int meta, IMiningDrill drill) {
		return drill.getDrillRating() <= 70 ? new ItemStack(getDrop()) : null;
	}

	@Override
	public float getRelativeHardness(World world, int x, int y, int z, int meta, IMiningDrill drill) {
		return this.getBlockHardness(world, x, y, z);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.tile.cluster"));
	}
}
