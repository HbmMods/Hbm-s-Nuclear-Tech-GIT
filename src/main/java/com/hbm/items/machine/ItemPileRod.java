package com.hbm.items.machine;

import java.util.List;

import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPileRod extends Item {
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		String[] defaultLocs = I18nUtil.resolveKey("desc.item.pileRod").split("\\$");
		
		for(String loc : defaultLocs) {
			list.add(loc);
		}
		
		String[] descLocs = I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc").split("\\$");
		
		for(String loc : descLocs) {
			list.add(loc);
		}
	}
}
