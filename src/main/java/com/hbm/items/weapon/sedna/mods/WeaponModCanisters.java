package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.mags.MagazineElectricEngine;
import com.hbm.items.weapon.sedna.mags.MagazineLiquidEngine;

import net.minecraft.item.ItemStack;

public class WeaponModCanisters extends WeaponModBase {

	protected static MagazineLiquidEngine DUMMY_LIQUID = new MagazineLiquidEngine(0, 0);
	protected static MagazineElectricEngine DUMMY_ELECTRIC = new MagazineElectricEngine(0, 0);

	public WeaponModCanisters(int id) {
		super(id, "CANISTERS");
		this.setPriority(PRIORITY_MULT_FINAL);
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == Receiver.O_MAGAZINE) {
			if(base instanceof MagazineLiquidEngine) {
				MagazineLiquidEngine original = (MagazineLiquidEngine) base;
				DUMMY_LIQUID.acceptedTypes = original.acceptedTypes;
				DUMMY_LIQUID.capacity = original.capacity * 3;
				DUMMY_LIQUID.index = original.index;
				return (T) DUMMY_LIQUID;
			}
			
			if(base instanceof MagazineElectricEngine) {
				MagazineElectricEngine original = (MagazineElectricEngine) base;
				DUMMY_ELECTRIC.capacity = original.capacity * 3;
				DUMMY_ELECTRIC.index = original.index;
				return (T) DUMMY_ELECTRIC;
			}
		}
		return base;
	}

	@Override public void onInstall(ItemStack gun, ItemStack mod, int index) { XWeaponModManager.changedMagState(); }
	@Override public void onUninstall(ItemStack gun, ItemStack mod, int index) { XWeaponModManager.changedMagState(); }
}
