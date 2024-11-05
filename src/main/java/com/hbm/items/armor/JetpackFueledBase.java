package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.inventory.fluid.FluidType;

import api.hbm.fluid.IFillableItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public abstract class JetpackFueledBase extends JetpackBase implements IFillableItem {

	public FluidType fuel;
	public int maxFuel;

	public JetpackFueledBase(FluidType fuel, int maxFuel) {
		super();
		this.fuel = fuel;
		this.maxFuel = maxFuel;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.LIGHT_PURPLE + fuel.getLocalizedName() + ": " + this.getFuel(itemstack) + "mB / " + this.maxFuel + "mB");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}
	
	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		ItemStack jetpack = ArmorModHandler.pryMods(armor)[ArmorModHandler.plate_only];
		
		if(jetpack == null)
			return;
		
		list.add(EnumChatFormatting.RED + "  " + stack.getDisplayName() + " (" + fuel.getLocalizedName() + ": " + this.getFuel(jetpack) + "mB / " + this.maxFuel + "mB)");
	}
	
	protected void useUpFuel(EntityPlayer player, ItemStack stack, int rate) {
		if(player.ticksExisted % rate == 0){
			this.setFuel(stack, this.getFuel(stack) - 1);
        }
	}
	
    public static int getFuel(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("fuel");
		
	}
	
	public static void setFuel(ItemStack stack, int i) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("fuel", i);
		
	}

	public int getMaxFill(ItemStack stack) {
		return this.maxFuel;
	}

	public int getLoadSpeed(ItemStack stack) {
		return 10;
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return type == this.fuel;
	}

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		
		if(!acceptsFluid(type, stack))
			return amount;
		
		int fill = this.getFuel(stack);
		int req = maxFuel - fill;
		
		int toFill = Math.min(amount, req);
		//toFill = Math.min(toFill, getLoadSpeed(stack));
		
		this.setFuel(stack, fill + toFill);
		
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

	@Override
	public FluidType getFirstFluidType(ItemStack stack) {
		return null;
	}

	@Override
	public int getFill(ItemStack stack) {
		return 0;
	}
}
