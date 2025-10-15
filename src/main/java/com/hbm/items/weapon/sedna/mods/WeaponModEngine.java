package com.hbm.items.weapon.sedna.mods;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.items.weapon.sedna.mags.MagazineElectricEngine;
import com.hbm.items.weapon.sedna.mags.MagazineLiquidEngine;

import net.minecraft.item.ItemStack;

public class WeaponModEngine extends WeaponModBase {

	public static final MagazineLiquidEngine ENGINE_DIESEL = new MagazineLiquidEngine(0, 4_000, Fluids.DIESEL, Fluids.DIESEL_CRACK, Fluids.LIGHTOIL);
	public static final MagazineLiquidEngine ENGINE_AVIATION = new MagazineLiquidEngine(0, 4_000, Fluids.KEROSENE, Fluids.LPG);
	public static final MagazineElectricEngine ENGINE_ELECTRIC = new MagazineElectricEngine(0, 1_000_000);
	public static final MagazineLiquidEngine ENGINE_TURBO = new MagazineLiquidEngine(0, 4_000, Fluids.KEROSENE_REFORM, Fluids.REFORMATE);

	protected IMagazine mag;
	protected int delay;
	
	public WeaponModEngine(int id) {
		super(id, "ENGINE");
		this.setPriority(PRIORITY_SET);
	}
	
	public WeaponModEngine mag(IMagazine mag) { this.mag = mag; return this; }
	public WeaponModEngine delay(int delay) { this.delay = delay; return this; }

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {

		if(key == Receiver.O_MAGAZINE && mag != null) return cast(mag, base);
		if(key == Receiver.I_DELAYAFTERFIRE) return cast((Integer) delay, base);
		
		return base;
	}

	@Override public void onInstall(ItemStack gun, ItemStack mod, int index) { XWeaponModManager.changedMagState(); }
	@Override public void onUninstall(ItemStack gun, ItemStack mod, int index) { XWeaponModManager.changedMagState(); }
}
