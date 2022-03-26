package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockObserver extends Block {
	
	private boolean isActive;
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	@SideOnly(Side.CLIENT)
	private IIcon iconBack;

	public BlockObserver(Material mat, boolean isActive) {
		super(mat);
		this.isActive = isActive;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconBack = iconRegister.registerIcon(RefStrings.MODID + (this.isActive ? ":observer_back_on" : ":observer_back_off"));
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":observer_front");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":observer_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		ForgeDirection dir = ForgeDirection.getOrientation(metadata);
		ForgeDirection opp = dir.getOpposite();
		return side == dir.ordinal() ? iconFront : side == opp.ordinal() ? iconBack : blockIcon;
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int luck) {
		return Item.getItemFromBlock(ModBlocks.observer_off);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
		
		if(this.isActive)
			world.scheduleBlockUpdate(x, y, z, this, 2);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		if(!this.isActive) {
			
		}
	}
	
	@Override
	public boolean canProvidePower() {
		return this.isActive;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return this.isActive ? 15 : 0;
	}
}
