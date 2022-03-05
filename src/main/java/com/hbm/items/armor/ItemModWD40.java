package com.hbm.items.armor;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModWD40 extends ItemArmorMod {

	public ItemModWD40() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		String color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.BLUE : EnumChatFormatting.YELLOW);

		list.add(color + "Highly reduces damage taken by armor, +2 HP");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		String color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.BLUE : EnumChatFormatting.YELLOW);
		
		list.add(color + "  " + stack.getDisplayName() + " (-80% armor wear / +2 HP)");
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		if(!event.entityLiving.worldObj.isRemote && armor.getItemDamage() > 0 && event.entityLiving.getRNG().nextInt(5) != 0) {
			armor.setItemDamage(armor.getItemDamage() - 1);
		}
	}
	
	@Override
	public Multimap getModifiers(ItemStack armor) {
		Multimap multimap = super.getAttributeModifiers(armor);
		
		multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)armor.getItem()).armorType], "NTM Armor Mod Health", 4, 0));
		
		return multimap;
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		if(entity.worldObj.isRemote && entity.hurtTime > 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaExt");
			data.setString("mode", "reddust");
			data.setDouble("posX", entity.posX + (entity.getRNG().nextDouble() - 0.5) * entity.width * 2);
			data.setDouble("posY", entity.posY - entity.yOffset + entity.getRNG().nextDouble() * entity.height);
			data.setDouble("posZ", entity.posZ + (entity.getRNG().nextDouble() - 0.5) * entity.width * 2);
			data.setDouble("mX", 0.01);
			data.setDouble("mY", 0.5);
			data.setDouble("mZ", 0.8);
			MainRegistry.proxy.effectNT(data);
		}
	}
}
