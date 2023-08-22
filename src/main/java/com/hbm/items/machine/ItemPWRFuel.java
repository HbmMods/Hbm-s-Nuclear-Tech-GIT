package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.EnumUtil;
import com.hbm.util.function.Function;
import com.hbm.util.function.Function.FunctionLogarithmic;
import com.hbm.util.function.Function.FunctionSqrt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

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
		HEP241(	10.0D,	new FunctionSqrt(25)),
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
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		EnumPWRFuel num = EnumUtil.grabEnumSafely(EnumPWRFuel.class, stack.getItemDamage());
		
		String color = EnumChatFormatting.GOLD + "";
		String reset = EnumChatFormatting.RESET + "";
		
		list.add(color + "Heat per flux: " + reset + num.heatEmission + " TU");
		list.add(color + "Reacton function: " + reset + num.function.getLabelForFuel());
		list.add(color + "Fuel type: " + reset + num.function.getDangerFromFuel());
	}
}
