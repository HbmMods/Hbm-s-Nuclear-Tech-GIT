package com.hbm.items.weapon.sedna.mags;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.particle.SpentCasing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/** Base class for typical magazines, i.e. ones that hold bullets, shells, grenades, etc, any ammo item. Stores a single type of BulletConfigs */
public abstract class MagazineSingleTypeBase implements IMagazine<BulletConfig> {
	
	public static final String KEY_MAG_COUNT = "magcount";
	public static final String KEY_MAG_TYPE = "magtype";
	public static final String KEY_MAG_PREV = "magprev";
	public static final String KEY_MAG_AFTER = "magafter";

	protected List<BulletConfig> acceptedBullets = new ArrayList();
	
	/** A number so the gun tell multiple mags apart */
	public int index;
	/** How much ammo this mag can hold */
	public int capacity;
	
	public MagazineSingleTypeBase(int index, int capacity) {
		this.index = index;
		this.capacity = capacity;
	}
	
	public MagazineSingleTypeBase addConfigs(BulletConfig... cfgs) { for(BulletConfig cfg : cfgs) acceptedBullets.add(cfg); return this; }

	@Override
	public BulletConfig getType(ItemStack stack, IInventory inventory) {
		int type = getMagType(stack, index);
		if(type >= 0 && type < BulletConfig.configs.size()) {
			BulletConfig cfg = BulletConfig.configs.get(type);
			if(acceptedBullets.contains(cfg)) return cfg;
			return acceptedBullets.get(0);
		}
		return null;
	}

	@Override
	public void setType(ItemStack stack, BulletConfig type) {
		int i = BulletConfig.configs.indexOf(type);
		if(i >= 0) setMagType(stack, index, i);
	}

	@Override
	public ItemStack getIconForHUD(ItemStack stack, EntityPlayer player) {
		BulletConfig config = this.getType(stack, player.inventory);
		if(config != null) return config.ammo.toStack();
		return null;
	}

	@Override
	public String reportAmmoStateForHUD(ItemStack stack, EntityPlayer player) {
		return getAmount(stack, player.inventory) + " / " + getCapacity(stack);
	}

	@Override
	public SpentCasing getCasing(ItemStack stack, IInventory inventory) {
		return this.getType(stack, inventory).casing;
	}

	@Override
	public void useUpAmmo(ItemStack stack, IInventory inventory, int amount) {
		this.setAmount(stack, this.getAmount(stack, inventory) - amount);
		IMagazine.handleAmmoBag(inventory, this.getType(stack, inventory), amount);
	}

	@Override public int getCapacity(ItemStack stack) { return capacity; }
	@Override public int getAmount(ItemStack stack, IInventory inventory) { return getMagCount(stack, index); }
	@Override public void setAmount(ItemStack stack, int amount) { setMagCount(stack, index, amount); }
	
	@Override public void setAmountBeforeReload(ItemStack stack, int amount) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_PREV + index, amount); }
	@Override public int getAmountBeforeReload(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_PREV + index); }
	@Override public void setAmountAfterReload(ItemStack stack, int amount) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_AFTER + index, amount); }
	@Override public int getAmountAfterReload(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_AFTER + index); }

	// MAG TYPE //
	public static int getMagType(ItemStack stack, int index) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_TYPE + index); } //TODO: replace with named tags to avoid ID shifting
	public static void setMagType(ItemStack stack, int index, int value) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_TYPE + index, value); }

	// MAG COUNT //
	public static int getMagCount(ItemStack stack, int index) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_COUNT + index); }
	public static void setMagCount(ItemStack stack, int index, int value) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_COUNT + index, value); }
}
