package com.hbm.items.tool;

import java.util.List;

import com.hbm.util.BobMathUtil;

import api.hbm.energymk2.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemToolAbilityPower extends ItemToolAbility implements IBatteryItem {

	public long maxPower = 1;
	public long chargeRate;
	public long consumption;

	public ItemToolAbilityPower(float damage, double movement, ToolMaterial material, EnumToolType type, long maxPower, long chargeRate, long consumption) {
		super(damage, movement, material, type);
		this.maxPower = maxPower;
		this.chargeRate = chargeRate;
		this.consumption = consumption;
		this.setMaxDamage(1);
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemToolAbilityPower) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void setCharge(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemToolAbilityPower) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemToolAbilityPower) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", this.maxPower - i);
			}

			if(stack.stackTagCompound.getLong("charge") < 0)
				stack.stackTagCompound.setLong("charge", 0);
		}
	}

	@Override
	public long getCharge(ItemStack stack) {
		if(stack.getItem() instanceof ItemToolAbilityPower) {
			if(stack.hasTagCompound()) {
				return stack.stackTagCompound.getLong("charge");
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ItemToolAbilityPower) stack.getItem()).maxPower);
				return stack.stackTagCompound.getLong("charge");
			}
		}

		return 0;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		list.add("Charge: " + BobMathUtil.getShortNumber(getCharge(stack)) + " / " + BobMathUtil.getShortNumber(maxPower));
		super.addInformation(stack, player, list, ext);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getCharge(stack) < maxPower;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1 - (double) getCharge(stack) / (double) maxPower;
	}

	@Override
	public boolean canOperate(ItemStack stack) {
		return getCharge(stack) >= this.consumption;
	}

	@Override
	public long getMaxCharge(ItemStack stack) {
		return maxPower;
	}

	@Override
	public long getChargeRate() {
		return chargeRate;
	}

	@Override
	public long getDischargeRate() {
		return 0;
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		this.dischargeBattery(stack, damage * consumption);
	}

	@Override
	public boolean isDamageable() {
		return true;
	}
}
