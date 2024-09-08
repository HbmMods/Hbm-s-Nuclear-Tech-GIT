package com.hbm.items.weapon.sedna.mags;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBase;

import net.minecraft.item.ItemStack;

/** Base class for typical magazines, i.e. ones that hold bullets, shells, grenades, etc, any ammo item. Type methods deal with BulletConfigs */
public abstract class MagazineStandardBase implements IMagazine {
	
	public static final String KEY_MAG_COUNT = "magcount";
	public static final String KEY_MAG_TYPE = "magtype";

	protected List<BulletConfig> acceptedBullets = new ArrayList();
	
	/** A number so the gun tell multiple mags apart */
	public int index;
	/** How much ammo this mag can hold */
	public int capacity;
	
	public MagazineStandardBase(int index, int capacity) {
		this.index = index;
		this.capacity = capacity;
	}
	
	public MagazineStandardBase addConfigs(BulletConfig... cfgs) { for(BulletConfig cfg : cfgs) acceptedBullets.add(cfg); return this; }

	@Override
	public Object getType(ItemStack stack) {
		int type = getMagType(stack, index);
		if(type >= 0 && type < acceptedBullets.size()) {
			return acceptedBullets.get(type);
		}
		return null;
	}

	@Override
	public void setType(ItemStack stack, Object type) {
		if(!(type instanceof BulletConfig)) return;
		int i = acceptedBullets.indexOf(type);
		if(i >= 0) setMagType(stack, index, i);
	}

	@Override public int getCapacity(ItemStack stack) { return capacity; }
	@Override public int getAmount(ItemStack stack) { return getMagCount(stack, index); }
	@Override public void setAmount(ItemStack stack, int amount) { setMagCount(stack, index, amount); }

	// MAG TYPE //
	public static int getMagType(ItemStack stack, int index) { return ItemGunBase.getValueInt(stack, KEY_MAG_TYPE + index); }
	public static void setMagType(ItemStack stack, int index, int value) { ItemGunBase.setValueInt(stack, KEY_MAG_TYPE + index, value); }

	// MAG COUNT //
	public static int getMagCount(ItemStack stack, int index) { return ItemGunBase.getValueInt(stack, KEY_MAG_COUNT + index); }
	public static void setMagCount(ItemStack stack, int index, int value) { ItemGunBase.setValueInt(stack, KEY_MAG_COUNT + index, value); }
}
