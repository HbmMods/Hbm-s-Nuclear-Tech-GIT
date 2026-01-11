package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ItemEnumMulti;
import com.hbm.main.MainRegistry;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBatterySC extends ItemEnumMulti implements IBatteryItem {

	public ItemBatterySC() {
		super(EnumBatterySC.class, true, true);
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.controlTab);
	}
	
	public static enum EnumBatterySC {
		
		EMPTY(	    0),
		WASTE(	  150),
		RA226(	  200),
		TC99(	  500),
		CO60(	  750),
		PU238(	1_000),
		PO210(	1_250),
		AU198(	1_500),
		PB209(	2_000),
		AM241(	2_500);

		public long power;
		
		private EnumBatterySC(long power) {
			this.power = power;
		}
	}

	@Override public void chargeBattery(ItemStack stack, long i) { }
	@Override public void setCharge(ItemStack stack, long i) { }
	@Override public void dischargeBattery(ItemStack stack, long i) { }
	@Override public long getChargeRate(ItemStack stack) { return 0; }

	@Override public long getCharge(ItemStack stack) { return getMaxCharge(stack); }
	@Override public long getDischargeRate(ItemStack stack) { return getMaxCharge(stack); }
	
	@Override
	public long getMaxCharge(ItemStack stack) {
		EnumBatterySC pack = EnumUtil.grabEnumSafely(EnumBatterySC.class, stack.getItemDamage());
		return pack.power;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		EnumBatterySC pack = EnumUtil.grabEnumSafely(EnumBatterySC.class, stack.getItemDamage());
		if(pack.power > 0) list.add(EnumChatFormatting.YELLOW + "Discharge rate: " + BobMathUtil.getShortNumber(pack.power) + "HE/t");
	}
}
