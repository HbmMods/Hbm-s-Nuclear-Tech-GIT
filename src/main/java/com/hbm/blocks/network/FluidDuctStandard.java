package com.hbm.blocks.network;

import java.util.List;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.test.TestPipe;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class FluidDuctStandard extends FluidDuctBase implements IBlockMulti {

	@SideOnly(Side.CLIENT)
	protected IIcon[] icon;
	@SideOnly(Side.CLIENT)
	protected IIcon[] overlay;

	public FluidDuctStandard(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		icon = new IIcon[3];
		overlay = new IIcon[3];

		this.icon[0] = iconRegister.registerIcon(this.getTextureName());
		this.icon[1] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_silver");
		this.icon[2] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_colored");
		this.overlay[0] = iconRegister.registerIcon(this.getTextureName() + "_overlay");
		this.overlay[1] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_silver_overlay");
		this.overlay[2] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_colored_overlay");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 0 ? this.icon[rectify(metadata)] : this.overlay[rectify(metadata)];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 3; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public int damageDropped(int meta) {
		return rectify(meta);
	}

	@Override
	public int getRenderType() {
		return TestPipe.renderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getSubCount() {
		return 3;
	}
}
