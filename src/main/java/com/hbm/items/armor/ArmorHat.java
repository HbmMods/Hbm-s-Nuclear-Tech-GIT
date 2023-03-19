package com.hbm.items.armor;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ArmorHat extends ArmorModel implements IAttackHandler, IDamageHandler {

	public ArmorHat(ArmorMaterial armorMaterial, int armorType) {
		super(armorMaterial, armorType);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		entityItem.setDead();
		return true;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.BLUE + "+2 DT");
	}

	@Override
	public void handleDamage(LivingHurtEvent event, ItemStack stack) {
		
		if(event.source.isUnblockable())
			return;
		
		event.ammount -= 2F;
		
		if(event.ammount < 0)
			event.ammount = 0;
	}

	@Override
	public void handleAttack(LivingAttackEvent event, ItemStack armor) {
		
		if(event.source.isUnblockable())
			return;
		
		if(event.ammount <= 2F) {
			event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "random.break", 5F, 1.0F + event.entityLiving.getRNG().nextFloat() * 0.5F);
			event.setCanceled(true);
		}
	}
}
