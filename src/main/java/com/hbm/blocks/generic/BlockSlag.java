package com.hbm.blocks.generic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockSlag extends BlockBeaconable {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconAlt;

	public BlockSlag(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconAlt = iconRegister.registerIcon(this.getTextureName() + "_broken");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(metadata == 1) return this.iconAlt;
		
		return this.blockIcon;
	}
}
