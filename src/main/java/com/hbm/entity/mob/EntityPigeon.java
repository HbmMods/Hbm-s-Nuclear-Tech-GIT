package com.hbm.entity.mob;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;

public class EntityPigeon extends EntityChicken {

	public EntityPigeon(World p_i1682_1_) {
		super(p_i1682_1_);
	}

	protected String getLivingSound() {
		return null;
	}

	protected String getHurtSound() {
		return null;
	}

	protected String getDeathSound() {
		return null;
	}
}
