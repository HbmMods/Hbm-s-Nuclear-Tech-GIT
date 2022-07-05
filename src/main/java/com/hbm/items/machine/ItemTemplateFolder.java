package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTemplateFolder extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			player.openGui(MainRegistry.instance, ModItems.guiID_item_folder, world, 0, 0, 0);
		
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		String[] lang = I18nUtil.resolveKeyArray(this.getUnlocalizedName() + ".desc");
		for(String line : lang) {
			list.add(line);
		}
	}
}
