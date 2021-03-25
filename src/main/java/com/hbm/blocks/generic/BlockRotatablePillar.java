package com.hbm.blocks.generic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockRotatablePillar extends BlockRotatedPillar {

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

}
