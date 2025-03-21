package com.hbm.items.weapon.sedna.mods;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.mags.MagazineBelt;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;

import net.minecraft.item.ItemStack;

public class WeaponModCaliber extends WeaponModBase {
	
	protected static MagazineSingleReload DUMMY_SINGLE = new MagazineSingleReload(0, 0);
	protected static MagazineFullReload DUMMY_FULL = new MagazineFullReload(0, 0);
	protected static MagazineBelt DUMMY_BELT = new MagazineBelt();
	protected final List<BulletConfig> cfg = new ArrayList();
	protected final int count;
	protected final float baseDamage;

	public WeaponModCaliber(int id, int count, float baseDamage, BulletConfig... cfg) {
		super(id, "CALIBER");
		this.setPriority(PRIORITY_SET);
		for(BulletConfig conf : cfg) this.cfg.add(conf);
		this.count = count;
		this.baseDamage = baseDamage;
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == Receiver.O_MAGAZINE) {
			if(base instanceof MagazineSingleReload) {
				MagazineSingleReload original = (MagazineSingleReload) base;
				DUMMY_SINGLE.acceptedBullets = cfg;
				DUMMY_SINGLE.capacity = count;
				DUMMY_SINGLE.index = original.index;
				return (T) DUMMY_SINGLE;
			}
			if(base instanceof MagazineFullReload) {
				MagazineFullReload original = (MagazineFullReload) base;
				DUMMY_FULL.acceptedBullets = cfg;
				DUMMY_FULL.capacity = count;
				DUMMY_FULL.index = original.index;
				return (T) DUMMY_FULL;
			}
			if(base instanceof MagazineBelt) {
				MagazineBelt original = (MagazineBelt) base;
				DUMMY_BELT.acceptedBullets = cfg;
				return (T) DUMMY_BELT;
			}
		}
		if(key == Receiver.F_BASEDAMAGE) {
			return cast(baseDamage, base);
		}
		return base;
	}
}
