package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.BlockMulti;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockForgottenBrick extends BlockMulti {

	private IIcon iconTop;
	private IIcon iconAlt;
	private IIcon iconAltTop;
	private IIcon iconStone;
	private IIcon iconHole;
	private IIcon iconEmpty;
	private IIcon iconPlanks;

	public BlockForgottenBrick() {
		super(Material.rock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconTop = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_top");
		this.iconAlt = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_bw");
		this.iconAltTop = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_bw_top");
		this.iconStone = reg.registerIcon(RefStrings.MODID + ":playground/nullstone_demo_1_wip");
		this.iconHole = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_hole");
		this.iconEmpty = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_hole_empty");
		this.iconPlanks = reg.registerIcon(RefStrings.MODID + ":nr_planks");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if(meta == 1) {
			if(side == 0 || side == 1) return this.iconAltTop;
			return this.iconAlt;
		}
		if(meta == 2) {
			return this.iconStone;
		}
		if(meta == 3) {
			if(side == 0 || side == 1) return this.iconTop;
			return this.iconHole;
		}
		if(meta == 4) {
			if(side == 0 || side == 1) return this.iconTop;
			return this.iconEmpty;
		}
		if(meta == 5) {
			return this.iconPlanks;
		}

		if(side == 0 || side == 1) return this.iconTop;
		return this.blockIcon;
	}

	@Override public int getSubCount() { return 6; }

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < getSubCount(); ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
