package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockRotatablePillar extends BlockRotatedPillar implements ITooltipProvider {

	@SideOnly(Side.CLIENT)
	protected IIcon iconSide;

	private String textureTop;

	public BlockRotatablePillar(Material mat, String top) {
		super(mat);
		textureTop = top;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {

		this.field_150164_N = reg.registerIcon(textureTop);
		this.iconSide = reg.registerIcon(this.getTextureName());
	}

	@Override
	protected IIcon getSideIcon(int p_150163_1_) {
		return iconSide;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) { }

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		
		if(this == ModBlocks.block_schrabidium_cluster)
			return EnumRarity.rare;
		
		return EnumRarity.common;
	}
}
