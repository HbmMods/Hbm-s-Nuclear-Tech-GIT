package com.hbm.items.weapon.sedna.mags;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.particle.SpentCasing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MagazineInfinite implements IMagazine {
	
	public BulletConfig type;
	
	public MagazineInfinite(BulletConfig type) {
		this.type = type;
	}

	@Override
	public Object getType(ItemStack stack, IInventory inventory) {
		return this.type;
	}

	@Override public void setType(ItemStack stack, Object type) { }
	@Override public int getCapacity(ItemStack stack) { return 9999; }
	@Override public int getAmount(ItemStack stack, IInventory inventory) { return 9999; }
	@Override public void setAmount(ItemStack stack, int amount) { }
	@Override public void useUpAmmo(ItemStack stack, IInventory inventory, int amount) { }
	@Override public boolean canReload(ItemStack stack, IInventory inventory) { return false; }
	@Override public void initNewType(ItemStack stack, IInventory inventory) { }
	@Override public void reloadAction(ItemStack stack, IInventory inventory) { }
	@Override public ItemStack getIconForHUD(ItemStack stack, EntityPlayer player) { return new ItemStack(ModItems.nothing); }
	@Override public String reportAmmoStateForHUD(ItemStack stack, EntityPlayer player) { return "∞"; }
	@Override public SpentCasing getCasing(ItemStack stack, IInventory inventory) { return this.type.casing; }
	@Override public void setAmountBeforeReload(ItemStack stack, int amount) { }
	@Override public int getAmountBeforeReload(ItemStack stack) { return 9999; }
	@Override public void setAmountAfterReload(ItemStack stack, int amount) { }
	@Override public int getAmountAfterReload(ItemStack stack) { return 9999; }
}
