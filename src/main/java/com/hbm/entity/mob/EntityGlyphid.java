package com.hbm.entity.mob;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGlyphid extends EntityMob {

	public EntityGlyphid(World world) {
		super(world);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.setSize(2F, 1F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte) 0)); //wall climbing
		this.dataWatcher.addObject(17, new Byte((byte) 0b11111)); //armor
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(32D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1D);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(!source.isDamageAbsolute() && !source.isUnblockable() && !worldObj.isRemote) {
			byte armor = this.dataWatcher.getWatchableObjectByte(17);
			
			if(armor != 0) { //if at least one bit of armor is present
				int chance = amount < 10 ? 5 : amount < 20 ? 3 : 2; //chances of armor being broken off
				if(this.rand.nextInt(chance) == 0 && amount > 1) {
					List<Integer> indices = Arrays.asList(0, 1, 2, 3, 4);
					Collections.shuffle(indices);
					
					for(Integer i : indices) {
						byte bit = (byte) (1 << i);
						if((armor & bit) > 0) { //if this bit is present...
							armor &= ~bit; //...remove it
							armor = (byte) (armor & 0b11111);
							this.dataWatcher.updateObject(17, armor);
							amount = 0;
							break;
						}
					}
				}
				
				amount -= 0.5;
			}
			
			int divisor = 1;
			
			for(int i = 0; i < 5; i++) {
				if((armor & (1 << i)) > 0) {
					divisor++;
				}
			}
			
			amount /= divisor;
		}
		
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean attackEntityAsMob(Entity victum) {
		if(this.isSwingInProgress) return false;
		this.swingItem();
		return super.attackEntityAsMob(victum);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!this.worldObj.isRemote) {
			this.setBesideClimbableBlock(this.isCollidedHorizontally);
			
			if(worldObj.getTotalWorldTime() % 100 == 0) {
				this.swingItem();
			}
		}
	}

	@Override
	protected void updateArmSwingProgress() {
		int i = this.swingDuration();

		if(this.isSwingInProgress) {
			++this.swingProgressInt;

			if(this.swingProgressInt >= i) {
				this.swingProgressInt = 0;
				this.isSwingInProgress = false;
			}
		} else {
			this.swingProgressInt = 0;
		}

		this.swingProgress = (float) this.swingProgressInt / (float) i;
	}
	
	public int swingDuration() {
		return 15;
	}

	@Override
	public void setInWeb() { }
	
	@Override
	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}
	
	public boolean isBesideClimbableBlock() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean climbable) {
		byte watchable = this.dataWatcher.getWatchableObjectByte(16);

		if(climbable) {
			watchable = (byte) (watchable | 1);
		} else {
			watchable &= -2;
		}

		this.dataWatcher.updateObject(16, Byte.valueOf(watchable));
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}
}
