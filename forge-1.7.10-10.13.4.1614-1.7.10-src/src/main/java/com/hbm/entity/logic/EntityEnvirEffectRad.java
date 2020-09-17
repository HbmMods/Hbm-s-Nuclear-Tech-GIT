package com.hbm.entity.logic;

import net.minecraft.world.World;

public class EntityEnvirEffectRad extends EntityEnvirEffect {

	public EntityEnvirEffectRad(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public void randomizeAge(int min, int max) {
		this.maxAge = min + rand.nextInt(max - min);
	}

}
