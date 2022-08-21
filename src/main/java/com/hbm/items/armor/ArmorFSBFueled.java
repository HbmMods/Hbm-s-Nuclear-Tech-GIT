package com.hbm.items.armor;

import java.util.List;

import com.hbm.interfaces.IPartiallyFillable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
		this.maxFuel = maxFuel;
	}

	@Override
	public FluidType getType(ItemStack stack) {
		return this.fuelType;
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
	public boolean isArmorEnabled(ItemStack stack) {
		return getFill(stack) > 0;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		super.onArmorTick(world, player, stack);

		if(this.drain > 0 && ArmorFSB.hasFSBArmor(player) && !player.capabilities.isCreativeMode) {
			this.setFill(stack, Math.max(this.getFill(stack) - this.drain, 0));
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(I18nUtil.resolveKey(this.fuelType.getUnlocalizedName()) + ": " + BobMathUtil.getShortNumber(getFill(stack)) + " / " + BobMathUtil.getShortNumber(getMaxFill(stack)));
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
}
