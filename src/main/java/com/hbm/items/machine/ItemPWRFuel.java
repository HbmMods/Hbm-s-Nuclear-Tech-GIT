package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.EnumUtil;
import com.hbm.util.function.Function;
import com.hbm.util.function.Function.FunctionLogarithmic;
import com.hbm.util.function.Function.FunctionSqrt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemPWRFuel extends ItemEnumMulti {

	public ItemPWRFuel() {
		super(EnumPWRFuel.class, true, true);
	}
	
	public static enum EnumPWRFuel {
		MEU(	05.0D,	new FunctionLogarithmic(25)),
		HEU233(	07.5D,	new FunctionSqrt(25)),
		HEU235(	07.5D,	new FunctionSqrt(25)),
		MEN(	07.5D,	new FunctionLogarithmic(25)),
		HEN237(	07.5D,	new FunctionSqrt(25)),
		MOX(	07.5D,	new FunctionLogarithmic(25)),
		MEP(	07.5D,	new FunctionLogarithmic(25)),
		HEP239(	10.0D,	new FunctionSqrt(25)),
		HEP24(	10.0D,	new FunctionSqrt(25)),
		MEA(	07.5D,	new FunctionLogarithmic(25)),
		HEA242(	10.0D,	new FunctionSqrt(25)),
		HES326(	15.0D,	new FunctionSqrt(25)),
		HES327(	15.0D,	new FunctionSqrt(25));

		public double yield = 1_000_000_000;
		public double heatEmission;
		public Function function;
		
		private EnumPWRFuel(double heatEmission, Function function) {
			this.heatEmission = heatEmission;
			this.function = function;
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0D;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - getEnrichment(stack);
	}
	
	public static double getEnrichment(ItemStack stack) {
		EnumPWRFuel num = EnumUtil.grabEnumSafely(EnumPWRFuel.class, stack.getItemDamage());
		return getYield(stack) / num.yield;
	}
	
	public static double getYield(ItemStack stack) {
		return getDouble(stack, "yield");
	}
	
	public static void setYield(ItemStack stack, double yield) {
		setDouble(stack, "yield", yield);
	}
	
	public static void setDouble(ItemStack stack, String key, double yield) {
		if(!stack.hasTagCompound()) setNBTDefaults(stack);
		stack.stackTagCompound.setDouble(key, yield);
	}
	
	public static double getDouble(ItemStack stack, String key) {
		if(!stack.hasTagCompound()) setNBTDefaults(stack);
		return stack.stackTagCompound.getDouble(key);
	}
	
	private static void setNBTDefaults(ItemStack stack) {
		EnumPWRFuel num = EnumUtil.grabEnumSafely(EnumPWRFuel.class, stack.getItemDamage());
		stack.stackTagCompound = new NBTTagCompound();
		setYield(stack, num.yield);
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		setNBTDefaults(stack);
	}
}
