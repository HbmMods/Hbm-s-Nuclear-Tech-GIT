package com.hbm.items.machine;

import java.util.List;
import java.util.Locale;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStampBook extends ItemStamp {

	public ItemStampBook() {
		super(0, null);
		
		for(int i = 0; i < 8; i++) {
			StampType type = getStampType(this, i);
			this.addStampToList(this, i, type);
		}
	}

	@Override
	public StampType getStampType(Item item, int meta) {
		meta %= 8;
		return StampType.values()[StampType.PRINTING1.ordinal() + meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 8; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		StampType type = this.getStampType(stack.getItem(), stack.getItemDamage());
		return super.getUnlocalizedName() + "." + type.name().toLowerCase(Locale.US);
	}
}
