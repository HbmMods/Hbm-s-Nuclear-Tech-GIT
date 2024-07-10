package com.hbm.items.weapon;

import java.util.List;

import com.hbm.handler.RocketStruct;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemCustomRocket extends Item {

	public static ItemStack build(RocketStruct rocket) {
		ItemStack stack = new ItemStack(ModItems.rocket_custom);

		stack.stackTagCompound = new NBTTagCompound();
		rocket.writeToNBT(stack.stackTagCompound);

		return stack;
	}

	public static RocketStruct get(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCustomRocket) || stack.stackTagCompound == null)
			return null;

		return RocketStruct.readFromNBT(stack.stackTagCompound);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		RocketStruct rocket = get(stack);

		if(rocket == null) return;

		list.add(EnumChatFormatting.BOLD + "Stages: " + EnumChatFormatting.GRAY + rocket.stages.size());
	}

}
