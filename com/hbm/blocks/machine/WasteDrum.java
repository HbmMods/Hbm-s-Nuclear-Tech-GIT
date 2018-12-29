package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class WasteDrum extends Block {

	private final Random field_149933_a = new Random();
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public WasteDrum(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":waste_drum");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":waste_drum_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		if(side == 0 || side == 1)
			return this.iconTop;
		
		return blockIcon;
	}
}
