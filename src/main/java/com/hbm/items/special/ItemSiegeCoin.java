package com.hbm.items.special;

import java.util.List;

import com.hbm.entity.mob.siege.SiegeTier;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemSiegeCoin extends Item {
	
	public ItemSiegeCoin() {
		this.hasSubtypes = true;
		this.setMaxDamage(0);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(int i = 0; i < SiegeTier.getLength(); i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(stack, player, list, bool);
		list.add(EnumChatFormatting.YELLOW + "Tier " + (stack.getItemDamage() + 1));
	}
}
