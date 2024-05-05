package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmLivingProps.ContaminationEffect;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFallout extends Block {
	

	public BlockFallout(Material mat) {
		super(mat);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return ModItems.fallout;
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y - 1, z);

		if (block == Blocks.ice || block == Blocks.packed_ice) return false;
		if (block.isLeaves(world, x, y - 1, z) && !block.isAir(world, x, y - 1, z)) return true;
		if (block == this && (world.getBlockMetadata(x, y - 1, z) & 7) == 7) return true;

		return block.isOpaqueCube() && block.getMaterial().blocksMovement();
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {

		if(!world.isRemote && entity instanceof EntityLivingBase) {
			if(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) return;
			PotionEffect effect = new PotionEffect(HbmPotion.radiation.id, 10 * 60 * 20, 0);
			effect.setCurativeItems(new ArrayList());
			((EntityLivingBase) entity).addPotionEffect(effect);
		}
	}

	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {

		if(!world.isRemote) {
			HbmLivingProps.addCont(player, new ContaminationEffect(1F, 200, false));
		}
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		this.func_150155_m(world, x, y, z);
	}

	private boolean func_150155_m(World world, int x, int y, int z) {
		if(!this.canPlaceBlockAt(world, x, y, z)) {
			world.setBlockToAir(x, y, z);
			return false;
		} else {
			return true;
		}
	}

	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}
}
