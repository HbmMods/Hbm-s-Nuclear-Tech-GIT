package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModInk extends ItemArmorMod {

	public ItemModInk() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKeyArray("armorMod.mod.Ink")[0]);
		list.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKeyArray("armorMod.mod.Ink")[1]);
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.LIGHT_PURPLE + "  " + stack.getDisplayName() + I18nUtil.resolveKeyArray("armorMod.mod.Ink")[2]);
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		if(event.entity.worldObj.rand.nextInt(10) == 0) {
			event.ammount = 0;
			
			if(!event.entity.worldObj.isRemote) {
				
				if(event.entity.worldObj.rand.nextInt(10) == 0)
					event.entity.entityDropItem(new ItemStack(Blocks.yellow_flower), 1.0F);
				
				event.entity.entityDropItem(new ItemStack(Blocks.red_flower, 1, event.entity.worldObj.rand.nextInt(9)), 1.0F);
			}
		}
	}
}
