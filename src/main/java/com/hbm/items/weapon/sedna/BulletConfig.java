package com.hbm.items.weapon.sedna;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.RecipesCommon.ComparableStack;

public class BulletConfig {
	
	public static List<BulletConfig> configs = new ArrayList();
	
	public final int id;
	
	public ComparableStack ammo;
	public int ammoCount = 1;
	public float velocity = 5F;
	public float spread = 0F;
	public float wear = 1F;
	public int projectilesMin = 1;
	public int projectilesMax = 1;

	public float damageMult = 1.0F;
	public float headshotMult = 1.0F;
	
	public double gravity = 0;
	public int expires = 100;
	
	public BulletConfig() {
		this.id = configs.size();
		configs.add(this);
	}
}
