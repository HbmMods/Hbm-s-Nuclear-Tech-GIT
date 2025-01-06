package com.hbm.blocks.machine;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockPillar extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconAlt;

	private String textureTop = "";
	private String textureAlt = "";

	public BlockPillar(Material mat, String top) {
		super(mat);
		textureTop = top;
	}

	public BlockPillar(Material mat, String top, String bottom) {
		this(mat, top);
		textureAlt = bottom;
	}

	public Block setBlockTextureName(String name) {

		if(textureTop.isEmpty()) textureTop = name;
		if(textureAlt.isEmpty()) textureAlt = name;
		this.textureName = name;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		this.iconAlt = iconRegister.registerIcon(textureAlt.isEmpty() ? RefStrings.MODID + ":code" : textureAlt);
		this.iconTop = iconRegister.registerIcon(textureTop);
		this.blockIcon = iconRegister.registerIcon(this.textureName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}
}
