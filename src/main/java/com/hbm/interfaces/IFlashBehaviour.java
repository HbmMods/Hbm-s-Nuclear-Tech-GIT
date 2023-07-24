package com.hbm.interfaces;

import com.hbm.entity.projectile.EntityBulletBaseNT;

import net.minecraft.entity.player.EntityPlayer;

public interface IFlashBehaviour {
	
	//once every update, for lcokon, steering and other memes
	public void behave(EntityPlayer player);

}

