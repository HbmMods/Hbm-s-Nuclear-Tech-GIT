package com.hbm.items.weapon.sedna.mags;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.particle.SpentCasing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MagazineFluid implements IMagazine<FluidType> {

	public static final String KEY_MAG_COUNT = "magcount";
	public static final String KEY_MAG_TYPE = "magtype";
	public static final String KEY_MAG_PREV = "magprev";
	public static final String KEY_MAG_AFTER = "magafter";
	
	/** A number so the gun tell multiple mags apart */
	public int index;
	/** How much ammo this mag can hold */
	public int capacity;
	
	public MagazineFluid(int index, int capacity) {
		this.index = index;
		this.capacity = capacity;
	}

	@Override
	public FluidType getType(ItemStack stack, IInventory inventory) {
		int id = this.getMagType(stack, index);
		return Fluids.fromID(id);
	}

	@Override
	public void setType(ItemStack stack, FluidType type) {
		this.setMagType(stack, index, type.getID());
	}

	@Override
	public int getCapacity(ItemStack stack) {
		return capacity;
	}

	@Override
	public void useUpAmmo(ItemStack stack, IInventory inventory, int amount) {
		this.setAmount(stack, this.getAmount(stack, inventory) - amount);
	}
	
	@Override public int getAmount(ItemStack stack, IInventory inventory) { return getMagCount(stack, index); }
	@Override public void setAmount(ItemStack stack, int amount) { setMagCount(stack, index, amount); }

	@Override public boolean canReload(ItemStack stack, IInventory inventory) { return false; }
	@Override public void reloadAction(ItemStack stack, IInventory inventory) { }
	@Override public SpentCasing getCasing(ItemStack stack, IInventory inventory) { return null; }

	@Override public ItemStack getIconForHUD(ItemStack stack, EntityPlayer player) { return new ItemStack(ModItems.fluid_icon, 1, this.getMagType(stack, index)); }
	@Override public String reportAmmoStateForHUD(ItemStack stack, EntityPlayer player) { return getIconForHUD(stack, player).getDisplayName(); }
	
	@Override public void setAmountBeforeReload(ItemStack stack, int amount) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_PREV + index, amount); }
	@Override public int getAmountBeforeReload(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_PREV + index); }
	@Override public void setAmountAfterReload(ItemStack stack, int amount) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_AFTER + index, amount); }
	@Override public int getAmountAfterReload(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_AFTER + index); }
	
	public static int getMagType(ItemStack stack, int index) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_TYPE + index); }
	public static void setMagType(ItemStack stack, int index, int value) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_TYPE + index, value); }
	public static int getMagCount(ItemStack stack, int index) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_COUNT + index); }
	public static void setMagCount(ItemStack stack, int index, int value) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_COUNT + index, value); }
}
