package com.hbm.items.weapon;

import api.hbm.fluidmk2.IFillableItem;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ItemMissileFluid extends ItemMissile implements IFillableItem {
	
	public final int capacity;
	
	public ItemMissileFluid(MissileFormFactor form, MissileTier tier, int capacity) {
		super(form, tier);
		this.capacity = capacity;
	}
	
	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return getFill(stack) == 0 && !type.isAntimatter() && !type.hasNoContainer() || getFirstFluidType(stack) == type;
	}
	
	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		if(!acceptsFluid(type, stack)) return 0;
		int fill = getFill(stack);
		int toFill = Math.min(amount, capacity - fill);
		setFluid(stack, type, fill + toFill);
		return amount - toFill;
	}
	
	@Override
	public boolean providesFluid(FluidType type, ItemStack stack) {
		return getFill(stack) > 0 && getFirstFluidType(stack) == type;
	}
	
	@Override
	public int tryEmpty(FluidType type, int amount, ItemStack stack) {
		if(!providesFluid(type, stack)) return 0;
		int fill = getFill(stack);
		int toEmpty = Math.min(amount, fill);
		setFluid(stack, type, fill - toEmpty);
		return toEmpty;
	}
	
	@Override
	public FluidType getFirstFluidType(ItemStack stack) {
		if(!stack.hasTagCompound()) return Fluids.NONE;
		return Fluids.fromID(stack.stackTagCompound.getInteger("fluid"));
	}
	
	@Override
	public int getFill(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0;
		return stack.stackTagCompound.getInteger("fill");
	}
	
	public void setFluid(ItemStack stack, FluidType type, int fill) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		if(fill <= 0) {
			type = Fluids.NONE;
			fill = 0;
		}
		stack.stackTagCompound.setInteger("fluid", type.getID());
		stack.stackTagCompound.setInteger("fill", fill);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(itemstack, player, list, bool);
		list.add(String.format("%s: %d/%dmb", getFirstFluidType(itemstack).getLocalizedName(), getFill(itemstack), capacity));
	}
}
