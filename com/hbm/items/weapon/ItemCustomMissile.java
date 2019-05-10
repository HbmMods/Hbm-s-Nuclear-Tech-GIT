package com.hbm.items.weapon;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePart;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemCustomMissile extends Item {
	
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

		list.add(I18n.format(StatCollector.translateToLocal(chip.getUnlocalizedName() + ".name")).trim());
		list.add(I18n.format(StatCollector.translateToLocal(warhead.getUnlocalizedName() + ".name")).trim());
		list.add(I18n.format(StatCollector.translateToLocal(fuselage.getUnlocalizedName() + ".name")).trim());
		if(stability != null)
			list.add(I18n.format(StatCollector.translateToLocal(stability.getUnlocalizedName() + ".name")).trim());
		list.add(I18n.format(StatCollector.translateToLocal(thruster.getUnlocalizedName() + ".name")).trim());
	}
	
	public static MissileMultipart getMultipart(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemCustomMissile))
			return null;
		
		ItemMissile warhead = (ItemMissile) Item.getItemById(readFromNBT(stack, "warhead"));
		ItemMissile fuselage = (ItemMissile) Item.getItemById(readFromNBT(stack, "fuselage"));
		ItemMissile stability = (ItemMissile) Item.getItemById(readFromNBT(stack, "stability"));
		ItemMissile thruster = (ItemMissile) Item.getItemById(readFromNBT(stack, "thruster"));
		
		MissileMultipart missile = new MissileMultipart();

		missile.warhead = MissilePart.getPart(warhead);
		missile.fuselage = MissilePart.getPart(fuselage);
		missile.fins = MissilePart.getPart(stability);
		missile.thruster = MissilePart.getPart(thruster);
		
		return missile;
	}
}
