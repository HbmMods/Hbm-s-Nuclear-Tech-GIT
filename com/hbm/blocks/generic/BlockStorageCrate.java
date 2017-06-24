package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockStorageCrate extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockStorageCrate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		if(this == ModBlocks.crate_iron)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_iron_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_iron_side");
		}
		if(this == ModBlocks.crate_steel)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_steel_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_steel_side");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

}
