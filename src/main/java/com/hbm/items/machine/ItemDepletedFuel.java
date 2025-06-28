package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.special.ItemNuclearWaste;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemDepletedFuel extends ItemNuclearWaste {
	
	public ItemDepletedFuel() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for(int i = 0; i < 2; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_)
    {
        return p_82790_1_.getItemDamage() > 0 ? 0xFFBFA5 : 16777215;
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if (itemstack.getItemDamage() > 0)
			list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("desc.item.wasteCooling"));
	}
}
