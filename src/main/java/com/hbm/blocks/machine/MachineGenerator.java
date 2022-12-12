package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class MachineGenerator extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconSide;

	public MachineGenerator(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":machine_generator_side");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_generator");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 0 ? blockIcon : (side == 1 ? blockIcon : iconSide);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return ModItems.circuit_targeting_tier3;
	}
}
