package com.hbm.items.weapon.sedna;

import com.hbm.inventory.RecipesCommon.ComparableStack;

public class BulletConfig {
	
	public ComparableStack ammo;
	public int ammoCount = 1;
	public float velocity = 5F;
	public float spread = 0F;
	public float wear = 1F;
	public int projectilesMin;
	public int projectilesMax;

	public float damageMult = 1.0F;
	public float headshotMult = 1.0F;
	
	public double gravity = 0;
	public int expires = 100;
}
