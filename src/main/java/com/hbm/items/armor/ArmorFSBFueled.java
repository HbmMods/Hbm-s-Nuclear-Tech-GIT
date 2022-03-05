package com.hbm.items.armor;

import com.hbm.interfaces.IPartiallyFillable;
import com.hbm.inventory.fluid.FluidType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ArmorFSBFueled extends ArmorFSB implements IPartiallyFillable {

	FluidType fuelType;
	public int maxFuel = 1;
	public int fillRate;
	public int consumption;
	public int drain;

	public ArmorFSBFueled(ArmorMaterial material, int layer, int slot, String texture, FluidType fuelType, int maxFuel, int fillRate, int consumption, int drain) {
		super(material, layer, slot, texture);
		this.fuelType = fuelType;
		this.fillRate = fillRate;
		this.consumption = consumption;
		this.drain = drain;
	}

	@Override
	public FluidType getType(ItemStack stack) {
		return this.fuelType;
	}

	@Override
	public int getFill(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("fuel");
	}

	@Override
	public void setFill(ItemStack stack, int fill) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("fuel", fill);
	}

	@Override
	public int getMaxFill(ItemStack stack) {
		return this.maxFuel;
	}

	@Override
	public int getLoadSpeed(ItemStack stack) {
		return this.fillRate;
	}

	@Override
	public int getUnloadSpeed(ItemStack stack) {
		return 0;
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		this.setFill(stack, Math.max(this.getFill(stack) - (damage * consumption), 0));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		super.onArmorTick(world, player, stack);

		if(this.drain > 0 && ArmorFSB.hasFSBArmor(player) && !player.capabilities.isCreativeMode) {
			this.setFill(stack, Math.max(this.getFill(stack) - this.drain, 0));
		}
	}
}
