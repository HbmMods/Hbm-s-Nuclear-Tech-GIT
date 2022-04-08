package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class FluidDuctStandard extends FluidDuctBase {

	@SideOnly(Side.CLIENT)
	protected IIcon[] icon;
	@SideOnly(Side.CLIENT)
	protected IIcon[] overlay;

	protected FluidDuctStandard(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		icon = new IIcon[3];
		overlay = new IIcon[3];

		this.icon[0] = iconRegister.registerIcon(this.getTextureName());
		this.icon[1] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_silver");
		this.icon[2] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_colored");
		this.overlay[0] = iconRegister.registerIcon(this.getTextureName() + "_overlay");
		this.overlay[1] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_silver_overlay");
		this.overlay[2] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_colored_overlay");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 0 ? this.icon[rectify(metadata)] : this.overlay[rectify(metadata)];
	}
	
	public int damageDropped(int meta) {
		return rectify(meta);
	}
	
	private int rectify(int meta) {
		return Math.abs(meta % 3);
	}
}
