package com.hbm.items.weapon.sedna.factory;

import com.hbm.items.weapon.sedna.hud.HUDComponentAmmoCounter;
import com.hbm.items.weapon.sedna.hud.HUDComponentDurabilityBar;

public class LegoClient {

	public static HUDComponentDurabilityBar HUD_COMPONENT_DURABILITY = new HUDComponentDurabilityBar();
	public static HUDComponentAmmoCounter HUD_COMPONENT_AMMO = new HUDComponentAmmoCounter(0);
}
