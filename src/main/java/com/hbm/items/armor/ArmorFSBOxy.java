package com.hbm.items.armor;

import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import api.hbm.fluid.IFillableItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ArmorFSBOxy extends ArmorFSB implements IFillableItem {

	FluidType oxyType;
	public int maxFuel = 1;
	public int fillRate;
	public int consumption;
	public int drain;

	public ArmorFSBOxy(ArmorMaterial material, int slot, String texture, FluidType fuelType, int maxFuel, int fillRate, int consumption, int drain) {
		super(material, slot, texture);
		this.oxyType = fuelType;
		this.fillRate = fillRate;
		this.consumption = consumption;
		this.drain = drain;
		this.maxFuel = maxFuel;
	}

	public int getFill(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			setFill(stack, maxFuel);
			return maxFuel;
		}
		
		return stack.stackTagCompound.getInteger("Oxygen");
	}

	public void setFill(ItemStack stack, int fill) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("Oxygen", fill);
	}

	public int getMaxFill(ItemStack stack) {
		return this.maxFuel;
	}

	public int getLoadSpeed(ItemStack stack) {
		return this.fillRate;
	}

	public int getUnloadSpeed(ItemStack stack) {
		return 0;
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		this.setFill(stack, Math.max(this.getFill(stack) - (damage * consumption), 0));
	}

	@Override
	public boolean isArmorEnabled(ItemStack stack) {
		return getFill(stack) > 0;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		super.onArmorTick(world, player, stack);

		if(this.drain > 0 && ArmorFSB.hasFSBArmor(player) && !player.capabilities.isCreativeMode && world.getTotalWorldTime() % 10 == 0) {
			this.setFill(stack, Math.max(this.getFill(stack) - this.drain, 0));
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey(oxyType.getUnlocalizedName()) + ": " + this.getFill(stack) + "mB / " + this.maxFuel + "mB");
		super.addInformation(stack, player, list, ext);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getFill(stack) < getMaxFill(stack);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1 - (double) getFill(stack) / (double) getMaxFill(stack);
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return type == this.oxyType;
	}

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		
		if(!acceptsFluid(type, stack))
			return amount;
		
		int toFill = Math.min(amount, this.fillRate);
		toFill = Math.min(toFill, this.maxFuel - this.getFill(stack));
		this.setFill(stack, this.getFill(stack) + toFill);
		
		return amount - toFill;
	}

	@Override
	public boolean providesFluid(FluidType type, ItemStack stack) {
		return false;
	}

	@Override
	public int tryEmpty(FluidType type, int amount, ItemStack stack) {
		return 0;
	}
}
