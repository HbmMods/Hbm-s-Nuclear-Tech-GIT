package com.hbm.items.machine;

import java.util.List;

import com.hbm.interfaces.IOrderedEnum;
import com.hbm.items.ItemEnumMulti;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;

import api.hbm.energymk2.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class ItemBatteryPack extends ItemEnumMulti implements IBatteryItem {

	public ItemBatteryPack() {
		super(EnumBatteryPack.class, true, false);
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.controlTab);
	}
	
	public static enum EnumBatteryPack {
		BATTERY_REDSTONE	("battery_redstone",	      100L, false),
		BATTERY_LEAD		("battery_lead",		    1_000L, false),
		BATTERY_LITHIUM		("battery_lithium",		   10_000L, false),
		BATTERY_SODIUM		("battery_sodium",		   50_000L, false),
		BATTERY_SCHRABIDIUM	("battery_schrabidium",	  250_000L, false),
		BATTERY_QUANTUM		("battery_quantum",		1_000_000L, 20 * 60 * 60),

		CAPACITOR_COPPER	("capacitor_copper",	     1_000L, true),
		CAPACITOR_GOLD		("capacitor_gold",		    10_000L, true),
		CAPACITOR_NIOBIUM	("capacitor_niobium",	   100_000L, true),
		CAPACITOR_TANTALUM	("capacitor_tantalum",	   500_000L, true),
		CAPACITOR_BISMUTH	("capacitor_bismuth",	 2_500_000L, true),
		CAPACITOR_SPARK		("capacitor_spark",		10_000_000L, true);
		
		public ResourceLocation texture;
		public long capacity;
		public long chargeRate;
		public long dischargeRate;
		
		private EnumBatteryPack(String tex, long dischargeRate, boolean capacitor) {
			this(tex,
					capacitor ? (dischargeRate * 20 * 30) : (dischargeRate * 20 * 60 * 15),
					capacitor ? dischargeRate : dischargeRate * 10,
					dischargeRate);
		}
		
		private EnumBatteryPack(String tex, long dischargeRate, long duration) {
			this(tex, dischargeRate * duration, dischargeRate * 10, dischargeRate);
		}
		
		private EnumBatteryPack(String tex, long capacity, long chargeRate, long dischargeRate) {
			this.texture = new ResourceLocation(RefStrings.MODID, "textures/models/machines/" + tex + ".png");
			this.capacity = capacity;
			this.chargeRate = chargeRate;
			this.dischargeRate = dischargeRate;
		}
		
		public boolean isCapacitor() { return this.ordinal() > BATTERY_QUANTUM.ordinal(); }
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) {
		if(stack.hasTagCompound()) {
			stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
		} else {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", i);
		}
	}

	@Override
	public void setCharge(ItemStack stack, long i) {
		if(stack.hasTagCompound()) {
			stack.stackTagCompound.setLong("charge", i);
		} else {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", i);
		}
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i) {
		if(stack.hasTagCompound()) {
			stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
		} else {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", this.getMaxCharge(stack) - i);
		}
	}

	@Override
	public long getCharge(ItemStack stack) {
		if(stack.hasTagCompound()) {
			return stack.stackTagCompound.getLong("charge");
		} else {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", getMaxCharge(stack));
			return stack.stackTagCompound.getLong("charge");
		}
	}

	@Override
	public long getMaxCharge(ItemStack stack) {
		EnumBatteryPack pack = EnumUtil.grabEnumSafely(EnumBatteryPack.class, stack.getItemDamage());
		return pack.capacity;
	}

	@Override
	public long getChargeRate(ItemStack stack) {
		EnumBatteryPack pack = EnumUtil.grabEnumSafely(EnumBatteryPack.class, stack.getItemDamage());
		return pack.chargeRate;
	}

	@Override
	public long getDischargeRate(ItemStack stack) {
		EnumBatteryPack pack = EnumUtil.grabEnumSafely(EnumBatteryPack.class, stack.getItemDamage());
		return pack.dischargeRate;
	}

	@Override public boolean showDurabilityBar(ItemStack stack) { return true; }
	@Override public double getDurabilityForDisplay(ItemStack stack) { return 1D - (double) getCharge(stack) / (double) getMaxCharge(stack); }

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		long maxCharge = this.getMaxCharge(itemstack);
		long chargeRate = this.getChargeRate(itemstack);
		long dischargeRate = this.getDischargeRate(itemstack);
		long charge = maxCharge;
		
		if(itemstack.hasTagCompound()) charge = getCharge(itemstack);

		list.add(EnumChatFormatting.GREEN + "Energy stored: " + BobMathUtil.getShortNumber(charge) + "/" + BobMathUtil.getShortNumber(maxCharge) + "HE (" + (charge * 1000 / maxCharge / 10D) + "%)");
		list.add(EnumChatFormatting.YELLOW + "Charge rate: " + BobMathUtil.getShortNumber(chargeRate) + "HE/t");
		list.add(EnumChatFormatting.YELLOW + "Discharge rate: " + BobMathUtil.getShortNumber(dischargeRate) + "HE/t");
		list.add(EnumChatFormatting.GOLD + "Time for full charge: " + (maxCharge / chargeRate / 20 / 60D) + "min");
		list.add(EnumChatFormatting.GOLD + "Charge lasts for: " + (maxCharge / dischargeRate / 20 / 60D) + "min");
	}

	public static ItemStack makeEmptyBattery(ItemStack stack) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setLong("charge", 0);
		return stack;
	}

	public static ItemStack makeFullBattery(ItemStack stack) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setLong("charge", ((ItemBatteryPack) stack.getItem()).getMaxCharge(stack));
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		Enum[] order = theEnum.getEnumConstants();
		if(order[0] instanceof IOrderedEnum) order = ((IOrderedEnum) order[0]).getOrder();
		
		for(int i = 0; i < order.length; i++) {
			list.add(makeEmptyBattery(new ItemStack(item, 1, order[i].ordinal())));
			list.add(makeFullBattery(new ItemStack(item, 1, order[i].ordinal())));
		}
	}
}
