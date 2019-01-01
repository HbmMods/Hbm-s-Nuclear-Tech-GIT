package com.hbm.handler;

import java.util.List;

public class GunConfiguration {
	
	//amount of ticks between each bullet
	public int rateOfFire;
	public int rateOfFireAlt;
	//greatest amount of pellets created each shot
	public int bulletsMin;
	public int bulletsMinAlt;
	//least amount of pellets created each shot
	public int bulletsMax;
	public int bulletsMaxAlt;
	//0 = normal, 1 = release, 2 = both
	public int gunMode;
	//0 = manual, 1 = automatic
	public int firingMode;
	
	//whether or not there is a reload delay animation
	public boolean hasReloadAnim;
	//whether or not there is a firing delay with animation
	public boolean hasFiringAnim;
	
	//how long the reload animation will play
	public int reloadDuration;
	//duration of every animation cycle
	public int firingDuration;
	
	//how much ammo the clip can hold, 0 if drawn from inventory
	public int ammoCap;
	//0 does not allow direct reload, 1 is full clip, 2 is single bullet
	public int reloadType;

	public List<BulletConfiguration> bConfig;
	public List<BulletConfiguration> altConfig;

}
