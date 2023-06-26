package com.hbm.blocks.generic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockToolConversionPillar extends BlockToolConversion {

	public IIcon[] topIcons;
	public IIcon topIcon;

	public BlockToolConversionPillar(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		this.blockIcon = iconRegister.registerIcon(this.getTextureName() + "_side");
		this.topIcon = iconRegister.registerIcon(this.getTextureName() + "_top");
		
		if(names != null) {
			icons = new IIcon[names.length];
			topIcons = new IIcon[names.length];
			
			for(int i = 0; i < names.length; i++) {
				icons[i] = iconRegister.registerIcon(getTextureName() + "_side" + names[i]);
				topIcons[i] = iconRegister.registerIcon(getTextureName() + "_top" + names[i]);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		metadata -= 1;
		
		if(metadata == -1 || icons == null || metadata >= icons.length) {
			return side == 0 || side == 1 ? topIcon : blockIcon;
		}
		
		return side == 0 || side == 1 ? topIcons[metadata] : icons[metadata];
	}
}
