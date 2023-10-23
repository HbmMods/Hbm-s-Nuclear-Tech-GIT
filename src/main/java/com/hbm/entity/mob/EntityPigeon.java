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
	
	public float fallTime;
	public float dest;
	public float prevDest;
	public float prevFallTime;
	public float offGroundTimer = 1.0F;

	public EntityPigeon(World world) {
		super(world);
		Predicate noFlyCondition = x -> { return ((EntityPigeon) x).getFlyingState() == IFlyingCreature.STATE_WALKING; };
		this.tasks.addTask(0, new EntityAIStartFlying(this, this));
		this.tasks.addTask(0, new EntityAIStopFlying(this, this));
		this.tasks.addTask(1, new EntityAISwimmingConditional(this, noFlyCondition));
		this.tasks.addTask(2, new EntityAIFlutterAroundAimlessly(this, this));
		//this.tasks.addTask(2, new EntityAIPanicConditional(this, 1.4D, noFlyCondition));
		this.tasks.addTask(5, new EntityAIWanderConditional(this, 0.2D, noFlyCondition));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.setSize(0.5F, 1.0F);
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

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		
		if(this.getFlyingState() == this.STATE_FLYING) {
			int height = worldObj.getHeightValue((int) Math.floor(posX), (int) Math.floor(posZ));
			
			boolean ceil = posY - height > 10;
	
			this.motionY = this.getRNG().nextGaussian() * 0.05 + (ceil ? 0 : 0.04) + (this.isInWater() ? 0.2 : 0);
			
			if(onGround) this.motionY = Math.abs(this.motionY) + 0.1D;
			
			this.moveForward = 1.5F;
			if(this.getRNG().nextInt(20) == 0) this.rotationYaw += this.getRNG().nextGaussian() * 30;
		} else if(!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.8D;
		}
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.prevFallTime = this.fallTime;
		this.prevDest = this.dest;
		this.dest = (float) ((double) this.dest + (double) (this.onGround ? -1 : 4) * 0.3D);

		if(this.dest < 0.0F) {
			this.dest = 0.0F;
		}

		if(this.dest > 1.0F) {
			this.dest = 1.0F;
		}

		if(!this.onGround && this.offGroundTimer < 1.0F) {
			this.offGroundTimer = 1.0F;
		}

		this.offGroundTimer = (float) ((double) this.offGroundTimer * 0.9D);

		if(!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.6D;
		}

		this.fallTime += this.offGroundTimer * 2.0F;
	}

	@Override public boolean doesEntityNotTriggerPressurePlate() { return true; }
	@Override protected boolean canTriggerWalking() { return false; }

	@Override protected void fall(float p_70069_1_) { }
	@Override protected void updateFallState(double p_70064_1_, boolean p_70064_3_) { }
}
