package com.hbm.items.weapon;

import java.util.List;

import com.hbm.handler.MissileStruct;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.render.misc.MissilePart;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemCustomMissile extends Item {

	public static ItemStack buildMissile(Item chip, Item warhead, Item fuselage, Item stability, Item thruster) {
		
		if(stability == null) {
			return buildMissile(new ItemStack(chip), new ItemStack(warhead), new ItemStack(fuselage), null, new ItemStack(thruster));
		} else {
			return buildMissile(new ItemStack(chip), new ItemStack(warhead), new ItemStack(fuselage), new ItemStack(stability), new ItemStack(thruster));
		}
	}
	
	public static ItemStack buildMissile(ItemStack chip, ItemStack warhead, ItemStack fuselage, ItemStack stability, ItemStack thruster) {
		
		ItemStack missile = new ItemStack(ModItems.missile_custom);

		writeToNBT(missile, "chip", Item.getIdFromItem(chip.getItem()));
		writeToNBT(missile, "warhead", Item.getIdFromItem(warhead.getItem()));
		writeToNBT(missile, "fuselage", Item.getIdFromItem(fuselage.getItem()));
		writeToNBT(missile, "thruster", Item.getIdFromItem(thruster.getItem()));
		
		if(stability != null)
			writeToNBT(missile, "stability", Item.getIdFromItem(stability.getItem()));
		
		return missile;
	}
	
	private static void writeToNBT(ItemStack stack, String key, int value) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setInteger(key, value);
	}
	
	public static int readFromNBT(ItemStack stack, String key) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound.getInteger(key);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		ItemMissile chip = (ItemMissile) Item.getItemById(readFromNBT(stack, "chip"));
		ItemMissile warhead = (ItemMissile) Item.getItemById(readFromNBT(stack, "warhead"));
		ItemMissile fuselage = (ItemMissile) Item.getItemById(readFromNBT(stack, "fuselage"));
		ItemMissile stability = (ItemMissile) Item.getItemById(readFromNBT(stack, "stability"));
		ItemMissile thruster = (ItemMissile) Item.getItemById(readFromNBT(stack, "thruster"));

		list.add(EnumChatFormatting.BOLD + "Warhead: " + EnumChatFormatting.GRAY + warhead.getWarhead((WarheadType)warhead.attributes[0]));
		list.add(EnumChatFormatting.BOLD + "Strength: " + EnumChatFormatting.GRAY + (Float)warhead.attributes[1]);
		list.add(EnumChatFormatting.BOLD + "Fuel Type: " + EnumChatFormatting.GRAY + fuselage.getFuel((FuelType)fuselage.attributes[0]));
		list.add(EnumChatFormatting.BOLD + "Fuel amount: " + EnumChatFormatting.GRAY + (Float)fuselage.attributes[1] + "l");
		list.add(EnumChatFormatting.BOLD + "Chip inaccuracy: " + EnumChatFormatting.GRAY + (Float)chip.attributes[0] * 100 + "%");
		
		if(stability != null)
			list.add(EnumChatFormatting.BOLD + "Fin inaccuracy: " + EnumChatFormatting.GRAY + (Float)stability.attributes[0] * 100 + "%");
		else
			list.add(EnumChatFormatting.BOLD + "Fin inaccuracy: " + EnumChatFormatting.GRAY + "100%");
		
		list.add(EnumChatFormatting.BOLD + "Size: " + EnumChatFormatting.GRAY + fuselage.getSize(fuselage.top) + "/" + fuselage.getSize(fuselage.bottom));
		
		float health = warhead.health + fuselage.health + thruster.health;
		if(stability != null)
			health += stability.health;

		list.add(EnumChatFormatting.BOLD + "Health: " + EnumChatFormatting.GRAY + health + "HP");
	}
	
	public static MissileStruct getStruct(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemCustomMissile))
			return null;
		
		ItemMissile warhead = (ItemMissile) Item.getItemById(readFromNBT(stack, "warhead"));
		ItemMissile fuselage = (ItemMissile) Item.getItemById(readFromNBT(stack, "fuselage"));
		ItemMissile stability = (ItemMissile) Item.getItemById(readFromNBT(stack, "stability"));
		ItemMissile thruster = (ItemMissile) Item.getItemById(readFromNBT(stack, "thruster"));
		
		MissileStruct missile = new MissileStruct(warhead, fuselage, stability, thruster);
		
		return missile;
	}
}
