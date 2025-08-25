package com.hbm.items.weapon;

import java.util.List;

import com.hbm.handler.MissileStruct;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissilePart.FuelType;
import com.hbm.items.weapon.ItemCustomMissilePart.WarheadType;
import com.hbm.util.i18n.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

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

		if(!stack.hasTagCompound())
			return;

		try {
			ItemCustomMissilePart chip = (ItemCustomMissilePart) Item.getItemById(readFromNBT(stack, "chip"));
			ItemCustomMissilePart warhead = (ItemCustomMissilePart) Item.getItemById(readFromNBT(stack, "warhead"));
			ItemCustomMissilePart fuselage = (ItemCustomMissilePart) Item.getItemById(readFromNBT(stack, "fuselage"));
			ItemCustomMissilePart stability = (ItemCustomMissilePart) Item.getItemById(readFromNBT(stack, "stability"));
			ItemCustomMissilePart thruster = (ItemCustomMissilePart) Item.getItemById(readFromNBT(stack, "thruster"));

			// warhead name
			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.missile.warhead") + ": " + EnumChatFormatting.GRAY + warhead.getWarhead((WarheadType)warhead.attributes[0]));

			// strength
			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.missile.strength") + ": " + EnumChatFormatting.GRAY + (Float)warhead.attributes[1]);

			// fuel type & amount
			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.missile.fuelType") + ": " + EnumChatFormatting.GRAY + fuselage.getFuel((FuelType)fuselage.attributes[0]));
			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.missile.fuelAmount") + ": " + EnumChatFormatting.GRAY + (Float)fuselage.attributes[1] + "l");

			// chip inaccuracy
			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.missile.chipInaccuracy") + ": " + EnumChatFormatting.GRAY + (Float)chip.attributes[0] * 100 + "%");

			// fin inaccuracy
			if(stability != null)
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.missile.finInaccuracy") + ": " + EnumChatFormatting.GRAY + (Float)stability.attributes[0] * 100 + "%");
			else
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.missile.finInaccuracy") + ": " + EnumChatFormatting.GRAY + "100%");

			// size
			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.missile.size") + ": " + EnumChatFormatting.GRAY + fuselage.getSize(fuselage.top) + "/" + fuselage.getSize(fuselage.bottom));

			// health
			float health = warhead.health + fuselage.health + thruster.health;
			if(stability != null)
				health += stability.health;

			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.missile.health") + ": " + EnumChatFormatting.GRAY + health + "HP");

		} catch(Exception ex) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("error.generic"));
		}
	}

	public static MissileStruct getStruct(ItemStack stack) {

		if(stack == null || !(stack.getItem() instanceof ItemCustomMissile))
			return null;

		ItemCustomMissilePart warhead = (ItemCustomMissilePart) Item.getItemById(readFromNBT(stack, "warhead"));
		ItemCustomMissilePart fuselage = (ItemCustomMissilePart) Item.getItemById(readFromNBT(stack, "fuselage"));
		ItemCustomMissilePart stability = (ItemCustomMissilePart) Item.getItemById(readFromNBT(stack, "stability"));
		ItemCustomMissilePart thruster = (ItemCustomMissilePart) Item.getItemById(readFromNBT(stack, "thruster"));

		MissileStruct missile = new MissileStruct(warhead, fuselage, stability, thruster);

		return missile;
	}
}
