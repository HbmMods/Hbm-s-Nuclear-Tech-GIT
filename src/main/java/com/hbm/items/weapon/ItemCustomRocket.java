package com.hbm.items.weapon;

import java.util.List;

import com.hbm.handler.RocketStruct;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemCustomRocket extends Item implements ISatChip {

	public static ItemStack build(RocketStruct rocket) {
		ItemStack stack = new ItemStack(ModItems.rocket_custom);

		stack.stackTagCompound = new NBTTagCompound();
		rocket.writeToNBT(stack.stackTagCompound);

		return stack;
	}

	public static ItemStack build(RocketStruct rocket, boolean hasFuel) {
		ItemStack stack = build(rocket);

		setFuel(stack, hasFuel);
		
		return stack;
	}

	public static RocketStruct get(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCustomRocket) || stack.stackTagCompound == null)
			return null;

		return RocketStruct.readFromNBT(stack.stackTagCompound);
	}

	public static boolean hasFuel(ItemStack stack) {
		if(stack == null || stack.stackTagCompound == null) return false;
		return stack.stackTagCompound.getBoolean("hasFuel");
	}

	public static void setFuel(ItemStack stack, boolean hasFuel) {
		if(stack == null) return;
		if(stack.stackTagCompound == null) stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean("hasFuel", hasFuel);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		RocketStruct rocket = get(stack);

		if(rocket == null) return;

		list.add(EnumChatFormatting.BOLD + "Payload: " + EnumChatFormatting.GRAY + I18nUtil.resolveKey(rocket.capsule.part.getUnlocalizedName() + ".name"));
		list.add(EnumChatFormatting.BOLD + "Stages: " + EnumChatFormatting.GRAY + rocket.stages.size());

		if(hasFuel(stack)) {
			list.add(EnumChatFormatting.GRAY + "Is fully fueled");
		}

		if(getFreq(stack) != 0) {
			list.add(EnumChatFormatting.BOLD + "Satellite Frequency: " + EnumChatFormatting.GRAY + getFreq(stack));
		}
	}

}
