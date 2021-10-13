package com.hbm.items.food;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMarshmallow extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon iconRoasted;

	public ItemMarshmallow() {
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		super.registerIcons(p_94581_1_);
		this.iconRoasted = p_94581_1_.registerIcon(this.getIconString() + "_roasted");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		
		if(damage == 1)
			return this.iconRoasted;
		
		return super.getIconFromDamage(damage);
	}
}
