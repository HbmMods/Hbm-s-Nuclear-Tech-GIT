package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemDemonCore extends Item {

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		
		if(entityItem != null && !entityItem.worldObj.isRemote && entityItem.onGround) {
			entityItem.setEntityItemStack(new ItemStack(ModItems.demon_core_closed));
			entityItem.worldObj.spawnEntityInWorld(new EntityItem(entityItem.worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, new ItemStack(ModItems.screwdriver)));
			return true;
		}
		
		return false;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(itemstack, player, list, bool);
		list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]");
	}
}
