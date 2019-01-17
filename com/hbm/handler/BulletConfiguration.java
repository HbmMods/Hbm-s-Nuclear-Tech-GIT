package com.hbm.handler;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;

public class BulletConfiguration {
	
	//what item this specific configuration consumes
	public Item ammo;
	//how fast the bullet is (in sanics per second, or sps)
	public float velocity;
	//spread of bullets in gaussian range
	public float spread;
	//weapon durability reduced (centered around 10)
	public float tear;
	
	//damage bounds
	public float dmgMin;
	public float dmgMax;
	
	//acceleration torwards neg Y
	public double gravity;
	//max age in ticks before despawning
	public int maxAge;

	//whether the projectile should be able to bounce off of blocks
	public boolean doesRicochet;
	//the maximum angle at which the projectile should bounce
	public double ricochetAngle;

	//whether or not the bullet should penetrate mobs
	public boolean doesPenetrate;
	//whether or not the bullet should phase through blocks
	public boolean isSpectral;
	//whether or not the bullet should break glass
	public boolean doesBreakGlass;
	
	//bullet effects
	public List<PotionEffect> effects;
	public int incendiary;
	public int emp;
	public float explosive;
	public int rainbow;
	public int nuke;
	public boolean boxcar;
	public boolean boat;
	public boolean destroysBlocks;
	
	//appearance
	public int style;
	//additional appearance data, i.e. particle effects
	public int trail;
	//0: no plinking, 1: bullet plink, 2: grenade plink, 3: sing boink
	public int plink;

}
