package com.hbm.entity.mob;

import java.util.function.Predicate;

import com.hbm.entity.mob.ai.EntityAIFlutterAroundAimlessly;
import com.hbm.entity.mob.ai.EntityAIStartFlying;
import com.hbm.entity.mob.ai.EntityAIStopFlying;
import com.hbm.entity.mob.ai.EntityAISwimmingConditional;
import com.hbm.entity.mob.ai.EntityAIWanderConditional;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityPigeon extends EntityCreature implements IFlyingCreature, IAnimals {

	public EntityPigeon(World world) {
		super(world);
		Predicate noFlyCondition = x -> { return ((EntityPigeon) x).getFlyingState() == IFlyingCreature.STATE_WALKING; };
		this.tasks.addTask(0, new EntityAIStartFlying(this, this));
		this.tasks.addTask(0, new EntityAIStopFlying(this, this));
		this.tasks.addTask(1, new EntityAISwimmingConditional(this, noFlyCondition));
		this.tasks.addTask(2, new EntityAIFlutterAroundAimlessly(this, this));
		//this.tasks.addTask(2, new EntityAIPanicConditional(this, 1.4D, noFlyCondition));
		this.tasks.addTask(5, new EntityAIWanderConditional(this, 1.0D, noFlyCondition));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
	}
	
	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(12, Byte.valueOf((byte) 0));
	}

	@Override
	public int getFlyingState() {
		return this.dataWatcher.getWatchableObjectByte(12);
	}

	@Override
	public void setFlyingState(int state) {
		this.dataWatcher.updateObject(12, (byte) state);
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
