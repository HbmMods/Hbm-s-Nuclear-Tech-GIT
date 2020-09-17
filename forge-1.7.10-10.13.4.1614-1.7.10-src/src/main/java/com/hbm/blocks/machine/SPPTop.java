package com.hbm.blocks.machine;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class SPPTop extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	private IIcon iconBottom;

	public SPPTop(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + (":machine_spp_blank"));
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + (":machine_spp_t_bottom"));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_spp_t_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

}
