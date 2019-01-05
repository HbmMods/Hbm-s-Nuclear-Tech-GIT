package com.hbm.handler;

import net.minecraft.item.Item;

public class BulletConfiguration {
	
	//what item this specific configuration consumes
	public Item ammo;
	
	//damage bounds
	public float dmgMin;
	public float dmgMax;
	
	//acceleration torwards neg Y
	public double gravity;

	//whether the projectile should be able to bounce off of blocks
	public boolean doesRicochet;
	//the maximum angle at which the projectile should bounce
	public double ricochetAngle;

	//whether or not the bullet should penetrate mobs
	public boolean doesPenetrate;
	//whether or not the bullet should break glass
	public boolean doesBreakGlass;
	
	//bullet effects
	public int incendiary;
	public int poison;
	public int wither;
	public int emp;
	public int stun;
	public int explosive;
	public int rainbow;
	public int nuke;
	public boolean boxcar;
	public boolean destroysBlocks;
	
	//appearance for rendering
	public int style;

}
