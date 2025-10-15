package com.hbm.items.weapon.sedna.mags;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.particle.SpentCasing;
import com.hbm.util.BobMathUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MagazineElectricEngine implements IMagazine {

	public static final String KEY_MAG_COUNT = "magcount";
	public static final String KEY_MAG_PREV = "magprev";
	public static final String KEY_MAG_AFTER = "magafter";
	
	/** A number so the gun tell multiple mags apart */
	public int index;
	/** How much ammo this mag can hold */
	public int capacity;
	
	public MagazineElectricEngine(int index, int capacity) {
		this.index = index;
		this.capacity = capacity;
	}

	@Override public Object getType(ItemStack stack, IInventory inventory) { return null; }
	@Override public void setType(ItemStack stack, Object type) { }
	@Override public int getCapacity(ItemStack stack) { return capacity; }

	@Override
	public void useUpAmmo(ItemStack stack, IInventory inventory, int amount) {
		this.setAmount(stack, Math.max(this.getAmount(stack, inventory) - amount, 0));
	}
	
	@Override public int getAmount(ItemStack stack, IInventory inventory) { return getMagCount(stack, index); }
	@Override public void setAmount(ItemStack stack, int amount) { setMagCount(stack, index, amount); }

	@Override public boolean canReload(ItemStack stack, IInventory inventory) { return false; }
	@Override public void initNewType(ItemStack stack, IInventory inventory) { }
	@Override public void reloadAction(ItemStack stack, IInventory inventory) { }
	@Override public SpentCasing getCasing(ItemStack stack, IInventory inventory) { return null; }

	@Override public ItemStack getIconForHUD(ItemStack stack, EntityPlayer player) { return new ItemStack(ModItems.battery_generic); }
	@Override public String reportAmmoStateForHUD(ItemStack stack, EntityPlayer player) { return BobMathUtil.getShortNumber(getAmount(stack, player.inventory)) + "/" + BobMathUtil.getShortNumber(this.capacity) + "HE"; }
	
	@Override public void setAmountBeforeReload(ItemStack stack, int amount) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_PREV + index, amount); }
	@Override public int getAmountBeforeReload(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_PREV + index); }
	@Override public void setAmountAfterReload(ItemStack stack, int amount) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_AFTER + index, amount); }
	@Override public int getAmountAfterReload(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_AFTER + index); }
	
	public static int getMagCount(ItemStack stack, int index) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_COUNT + index); }
	public static void setMagCount(ItemStack stack, int index, int value) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_COUNT + index, value); }
}
