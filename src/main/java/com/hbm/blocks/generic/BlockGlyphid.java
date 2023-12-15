package com.hbm.blocks.generic;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockGlyphid extends Block {

	public IIcon[] iconsStandard = new IIcon[2];
	public IIcon[] iconsInfested = new IIcon[2];

	public BlockGlyphid(Material mat) {
		super(mat);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		long l = (long) (x * 3129871) ^ (long)y * 116129781L ^ (long)z;
		l = l * l * 42317861L + l * 11L;
		int i = (int)(l >> 16 & 3L);
		IIcon[] icons = this.getIconArray(world.getBlockMetadata(x, y, z));
		return icons[(int)(Math.abs(i) % icons.length)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		IIcon[] icons = this.getIconArray(meta);
		return icons[meta % icons.length];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		iconsStandard[0] = reg.registerIcon(RefStrings.MODID + ":glyphid_base");
		iconsStandard[1] = reg.registerIcon(RefStrings.MODID + ":glyphid_base_alt");
		iconsInfested[0] = reg.registerIcon(RefStrings.MODID + ":glyphid_base_infested");
		iconsInfested[1] = reg.registerIcon(RefStrings.MODID + ":glyphid_base_infested_alt");
	}

	protected IIcon[] getIconArray(int meta) {
		if(meta == 1) return this.iconsInfested;
		return this.iconsStandard;
	}
}
