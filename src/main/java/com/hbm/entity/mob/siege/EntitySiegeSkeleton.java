package com.hbm.entity.mob.siege;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;

public class EntitySiegeSkeleton extends EntityMob implements IRangedAttackMob, IRadiationImmune {

	public EntitySiegeSkeleton(World world) {
		super(world);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
		
	}
}
