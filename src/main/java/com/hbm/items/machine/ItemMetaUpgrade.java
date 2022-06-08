package com.hbm.items.machine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMetaUpgrade extends ItemMachineUpgrade {
	
	protected int levels;
	
	public ItemMetaUpgrade(int levels) {
		super();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.levels = levels;
	}
	
	public ItemMetaUpgrade(UpgradeType type, int levels) {
		super(type);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.levels = levels;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		
		for(int i = 0; i < this.levels; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
