package com.hbm.blocks.machine;

import com.hbm.blocks.BlockBase;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class MachineTeleanchor extends BlockBase {

	@SideOnly(Side.CLIENT) private IIcon iconTop;
	
	public MachineTeleanchor() {
		super(Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":tele_anchor_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":tele_anchor_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : this.blockIcon;
	}
}
