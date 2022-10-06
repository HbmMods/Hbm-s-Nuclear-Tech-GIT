package com.hbm.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class BlockMulti extends BlockBase implements IBlockMulti {

	public BlockMulti() {
		super();
	}

	public BlockMulti(Material mat) {
		super(mat);
	}

	@Override
	public int damageDropped(int meta) {
		return rectify(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < getSubCount(); ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
