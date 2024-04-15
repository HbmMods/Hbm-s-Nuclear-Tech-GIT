package com.hbm.items.armor;

import java.util.List;

import com.hbm.util.BobMathUtil;

import api.hbm.energymk2.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ArmorFSBPowered extends ArmorFSB implements IBatteryItem {

	public long maxPower = 1;
	public long chargeRate;
	public long consumption;
	public long drain;

	public ArmorFSBPowered(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, texture);
		this.maxPower = maxPower;
		this.chargeRate = chargeRate;
		this.consumption = consumption;
		this.drain = drain;
		this.setMaxDamage(1);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		list.add("Charge: " + BobMathUtil.getShortNumber(getCharge(stack)) + " / " + BobMathUtil.getShortNumber(maxPower));

		super.addInformation(stack, player, list, ext);
	}

	@Override
	public boolean isArmorEnabled(ItemStack stack) {
		return getCharge(stack) > 0;
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ArmorFSBPowered) {
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
		if(stack.getItem() instanceof ArmorFSBPowered) {
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
		if(stack.getItem() instanceof ArmorFSBPowered) {
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
		if(stack.getItem() instanceof ArmorFSBPowered) {
			if(stack.hasTagCompound()) {
				return stack.stackTagCompound.getLong("charge");
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ArmorFSBPowered) stack.getItem()).maxPower);
				return stack.stackTagCompound.getLong("charge");
			}
		}

		return 0;
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
	public long getMaxCharge() {
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

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {

		super.onArmorTick(world, player, itemStack);

		if(this.drain > 0 && ArmorFSB.hasFSBArmor(player) && !player.capabilities.isCreativeMode) {
			this.dischargeBattery(itemStack, drain);
		}
	}
}
