package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class BlockCraneBase extends BlockContainer {

	@SideOnly(Side.CLIENT) protected IIcon iconSide;
	@SideOnly(Side.CLIENT) protected IIcon iconIn;
	@SideOnly(Side.CLIENT) protected IIcon iconSideIn;
	@SideOnly(Side.CLIENT) protected IIcon iconOut;
	@SideOnly(Side.CLIENT) protected IIcon iconSideOut;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectional;

	public BlockCraneBase(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crane_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":crane_side");
		this.iconIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_in");
		this.iconSideIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_in");
		this.iconOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_out");
		this.iconSideOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_out");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public abstract IIcon getIcon(int side, int metadata);
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}
}
