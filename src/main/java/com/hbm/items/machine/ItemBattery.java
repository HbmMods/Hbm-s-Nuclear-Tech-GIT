package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.BobMathUtil;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBattery extends Item implements IBatteryItem {

	protected long maxCharge;
	protected long chargeRate;
	protected long dischargeRate;

	public ItemBattery(long dura, long chargeRate, long dischargeRate) {
		this.maxCharge = dura;
		this.chargeRate = chargeRate;
		this.dischargeRate = dischargeRate;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		long charge = maxCharge;
		if(itemstack.hasTagCompound())
			charge = getCharge(itemstack);

		if(itemstack.getItem() != ModItems.fusion_core && itemstack.getItem() != ModItems.energy_core) {
			list.add("Energy stored: " + BobMathUtil.getShortNumber(charge) + "/" + BobMathUtil.getShortNumber(maxCharge) + "HE");
		} else {
			String charge1 = BobMathUtil.getShortNumber((charge * 100) / this.maxCharge);
			list.add("Charge: " + charge1 + "%");
			list.add("(" + BobMathUtil.getShortNumber(charge) + "/" + BobMathUtil.getShortNumber(maxCharge) + "HE)");
		}
		list.add("Charge rate: " + BobMathUtil.getShortNumber(chargeRate) + "HE/t");
		list.add("Discharge rate: " + BobMathUtil.getShortNumber(dischargeRate) + "HE/t");
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

		if(this == ModItems.battery_schrabidium) {
			return EnumRarity.rare;
		}

		if(this == ModItems.fusion_core || this == ModItems.energy_core) {
			return EnumRarity.uncommon;
		}

		return EnumRarity.common;
	}

	public void chargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	public void setCharge(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	public void dischargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", this.maxCharge - i);
			}
		}
	}

	public long getCharge(ItemStack stack) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				return stack.stackTagCompound.getLong("charge");
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ItemBattery) stack.getItem()).maxCharge);
				return stack.stackTagCompound.getLong("charge");
			}
		}

		return 0;
	}

	public long getMaxCharge() {
		return maxCharge;
	}

	public long getChargeRate() {
		return chargeRate;
	}

	public long getDischargeRate() {
		return dischargeRate;
	}

	public static ItemStack getEmptyBattery(Item item) {

		if(item instanceof ItemBattery) {
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", 0);
			return stack.copy();
		}

		return null;
	}

	public static ItemStack getFullBattery(Item item) {

		if(item instanceof ItemBattery) {
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", ((ItemBattery) item).getMaxCharge());
			return stack.copy();
		}

		return new ItemStack(item);
	}

	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double) getCharge(stack) / (double) getMaxCharge();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		if(this.chargeRate > 0) {
			list.add(getEmptyBattery(item));
		}
		
		if(this.dischargeRate > 0) {
			list.add(getFullBattery(item));
		}
	}
}
