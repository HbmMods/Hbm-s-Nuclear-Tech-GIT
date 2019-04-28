package com.hbm.items.weapon;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.WarheadType;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemCustomMissile extends Item {
	
	public static ItemStack buildMissile(ItemStack chip, ItemStack warhead, ItemStack fuselage, ItemStack stability, ItemStack thruster) {
		
		ItemStack missile = new ItemStack(ModItems.missile_custom);

		writeToNBT(missile, "chip", ItemMissile.parts.indexOf(chip.getItem()));
		writeToNBT(missile, "warhead", ItemMissile.parts.indexOf(warhead.getItem()));
		writeToNBT(missile, "fuselage", ItemMissile.parts.indexOf(fuselage.getItem()));
		writeToNBT(missile, "thruster", ItemMissile.parts.indexOf(thruster.getItem()));
		
		if(stability != null)
			writeToNBT(missile, "stability", ItemMissile.parts.indexOf(stability.getItem()));
		
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

		ItemMissile chip = ItemMissile.parts.get(readFromNBT(stack, "chip"));
		ItemMissile warhead = ItemMissile.parts.get(readFromNBT(stack, "warhead"));
		ItemMissile fuselage = ItemMissile.parts.get(readFromNBT(stack, "fuselage"));
		ItemMissile stability = ItemMissile.parts.get(readFromNBT(stack, "stability"));
		ItemMissile thruster = ItemMissile.parts.get(readFromNBT(stack, "thruster"));

		list.add(I18n.format(StatCollector.translateToLocal(chip.getUnlocalizedName() + ".name")).trim());
		list.add(I18n.format(StatCollector.translateToLocal(warhead.getUnlocalizedName() + ".name")).trim());
		list.add(I18n.format(StatCollector.translateToLocal(fuselage.getUnlocalizedName() + ".name")).trim());
		if(stability != null)
			list.add(I18n.format(StatCollector.translateToLocal(stability.getUnlocalizedName() + ".name")).trim());
		list.add(I18n.format(StatCollector.translateToLocal(thruster.getUnlocalizedName() + ".name")).trim());
	}
}
