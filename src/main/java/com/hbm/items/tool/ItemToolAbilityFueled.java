package com.hbm.items.tool;

import java.util.HashSet;
import java.util.List;

import com.hbm.inventory.fluid.FluidType;

import api.hbm.fluidmk2.IFillableItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemToolAbilityFueled extends ItemToolAbility implements IFillableItem {
	
	protected int fillRate;
	protected int consumption;
	protected int maxFuel;
	protected HashSet<FluidType> acceptedFuels = new HashSet();

	public ItemToolAbilityFueled(float damage, double movement, ToolMaterial material, EnumToolType type, int maxFuel, int consumption, int fillRate, FluidType... acceptedFuels) {
		super(damage, movement, material, type);
		this.maxFuel = maxFuel;
		this.consumption = consumption;
		this.fillRate = fillRate;
		this.setMaxDamage(1);
		for(FluidType fuel : acceptedFuels) this.acceptedFuels.add(fuel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		list.add(EnumChatFormatting.GOLD + "Fuel: " + this.getFill(stack) + "/" + this.maxFuel + "mB");
		
		for(FluidType type : acceptedFuels) {
			list.add(EnumChatFormatting.YELLOW + "- " + type.getLocalizedName());
		}

		super.addInformation(stack, player, list, ext);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getFill(stack) < maxFuel;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1 - (double) getFill(stack) / (double) maxFuel;
	}

	@Override
	public boolean canOperate(ItemStack stack) {
		return getFill(stack) >= this.consumption;
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		this.setFill(stack, Math.max(this.getFill(stack) - damage * consumption, 0));
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public int getFill(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			setFill(stack, maxFuel);
			return maxFuel;
		}
		
		return stack.stackTagCompound.getInteger("fuel");
	}

	public void setFill(ItemStack stack, int fill) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("fuel", fill);
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return this.acceptedFuels.contains(type);
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
		return amount;
	}
	
	public static ItemStack getEmptyTool(Item item) {
		ItemToolAbilityFueled tool = (ItemToolAbilityFueled) item;
		ItemStack stack = new ItemStack(item);
		tool.setFill(stack, 0);
		return stack;
	}

	@Override
	public FluidType getFirstFluidType(ItemStack stack) {
		return null;
	}
}
