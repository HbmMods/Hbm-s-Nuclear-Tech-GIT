package com.hbm.items.weapon.sedna;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.item.Item;

public class BulletConfig {
	
	public static List<BulletConfig> configs = new ArrayList();
	
	public final int id;
	
	public ComparableStack ammo;
	public int ammoReloadCount = 1;
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

	public BulletConfig setItem(Item ammo) {					this.ammo = new ComparableStack(ammo); return this; }
	public BulletConfig setReloadCount(int ammoReloadCount) {	this.ammoReloadCount = ammoReloadCount; return this; }
	public BulletConfig setVel(float velocity) {				this.velocity = velocity; return this; }
	public BulletConfig setSpread(float spread) {				this.spread = spread; return this; }
	public BulletConfig setWear(float wear) {					this.wear = wear; return this; }
	public BulletConfig setProjectiles(int min, int max) {		this.projectilesMin = min; this.projectilesMax = max; return this; }
	public BulletConfig setDamage(float damageMult) {			this.damageMult = damageMult; return this; }
	public BulletConfig setHeadshot(float headshotMult) {		this.headshotMult = headshotMult; return this; }
	public BulletConfig setGrav(double gravity) {				this.gravity = gravity; return this; }
	public BulletConfig setLife(int expires) {					this.expires = expires; return this; }
}
