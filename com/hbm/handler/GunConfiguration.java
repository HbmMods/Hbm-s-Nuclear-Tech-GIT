package com.hbm.handler;

import java.util.List;

public class GunConfiguration {
	
	/**
	 * alt function restrictions:
	 * alt can not be reloaded (reload type of 0, ammo cap of 0)
	 * alt cooldown and main cooldown are shared (alt cooldown will almoast always be greater or equal)
	 * alt is always the lower priority, mouse2 will be canceled then mouse1 is activated at the same time
	 * restrictions must be applied in gun's logic, mechanism may be dysfunctional if these rules are ignored
	 */
	
	//TODO: bind RoF and spread to ammo
	
	//amount of ticks between each bullet
	public int rateOfFire;
	//greatest amount of pellets created each shot
	public int bulletsMin;
	//least amount of pellets created each shot
	public int bulletsMax;
	//0 = normal, 1 = release, 2 = both
	public int gunMode;
	//0 = manual, 1 = automatic
	public int firingMode;
	
	//whether or not there is a reload delay animation
	public boolean hasReloadAnim;
	//whether or not there is a firing delay with animation
	public boolean hasFiringAnim;
	//whether there is a warmup duration for spinup
	public boolean hasSpinup;
	//whether there is a cooldown duration for spindown
	public boolean hasSpindown;
	
	//how long the reload animation will play
	public int reloadDuration;
	//duration of every animation cycle
	public int firingDuration;
	
	//how much ammo the clip can hold, 0 if drawn from inventory
	public int ammoCap;
	//0 does not allow direct reload, 1 is full clip, 2 is single bullet
	public int reloadType;
	//whether or not the infinity enchantment should work
	public boolean allowsInfinity;

	//bullet configs for main and alt fire
	public List<BulletConfiguration> config;

}
