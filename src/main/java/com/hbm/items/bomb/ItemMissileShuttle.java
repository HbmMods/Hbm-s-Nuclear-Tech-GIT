package com.hbm.items.bomb;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMissileShuttle extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Tonite, on bo''om gear:");
		list.add("James huffs leaded gasoline and");
		list.add("goes insane, Richard spends the");
		list.add("entire budget on a broken .PNG,");
		list.add("And I forget to set the infinite");
		list.add("Water tanks on our RBMK to flow");
		list.add("out, blowing up our entire base");
	}
}