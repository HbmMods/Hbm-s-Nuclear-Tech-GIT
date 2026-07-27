package com.hbm.inventory.fluid.tank;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

// This is a class for items that use pipette like behaviors (rn its used for the pipette and autodrip)
public class FluidItemStorage {
	public static void initNBT(ItemStack stack) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setShort("type", (short) Fluids.NONE.getID());
		stack.stackTagCompound.setShort("fill", (short) 0);
	}

	public static FluidType getType(ItemStack stack) {
		if(!stack.hasTagCompound()) initNBT(stack);
		return Fluids.fromID(stack.stackTagCompound.getShort("type"));
	}
	public static short getFill(ItemStack stack) {
		if(!stack.hasTagCompound()) initNBT(stack);
		return stack.stackTagCompound.getShort("fill");
	}

	public static void setFill(ItemStack stack, FluidType type, short fill) {
		if(!stack.hasTagCompound()) initNBT(stack);
		stack.stackTagCompound.setShort("type", (short) type.getID());
		stack.stackTagCompound.setShort("fill", fill);
	}
}
