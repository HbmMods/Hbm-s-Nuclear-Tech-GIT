package com.hbm.blocks.generic;

import java.awt.Color;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockSellafieldSlaked extends Block {

	public IIcon[] icons;
	
	public BlockSellafieldSlaked(Material mat) {
		super(mat);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		long l = (long) (x * 3129871) ^ (long)y * 116129781L ^ (long)z;
		l = l * l * 42317861L + l * 11L;
		int i = (int)(l >> 16 & 3L);
		return icons[(int)(Math.abs(i) % icons.length)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[4];
		icons[0] = reg.registerIcon(RefStrings.MODID + ":sellafield_slaked");
		icons[1] = reg.registerIcon(RefStrings.MODID + ":sellafield_slaked_1");
		icons[2] = reg.registerIcon(RefStrings.MODID + ":sellafield_slaked_2");
		icons[3] = reg.registerIcon(RefStrings.MODID + ":sellafield_slaked_3");
	}
	
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return Color.HSBtoRGB(0F, 0F, 1F - meta / 15F);
	}
	
	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return this == ModBlocks.sellafield_bedrock ? false : super.canCreatureSpawn(type, world, x, y, z);
	}
}
