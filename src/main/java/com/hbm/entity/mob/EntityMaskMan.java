package com.hbm.entity.mob;

import com.hbm.entity.mob.ai.EntityAIMaskmanCasualApproach;
import com.hbm.entity.mob.ai.EntityAIMaskmanLasergun;
import com.hbm.entity.mob.ai.EntityAIMaskmanMinigun;
import com.hbm.interfaces.IRadiationImmune;
import com.hbm.items.ModItems;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMaskMan extends EntityMob implements IBossDisplayData, IRadiationImmune {

	public EntityMaskMan(World world) {
		super(world);
		
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMaskmanCasualApproach(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(2, new EntityAIMaskmanMinigun(this, true, true, 3));
        this.tasks.addTask(3, new EntityAIMaskmanLasergun(this, true, true));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        
		this.setSize(2F, 5F);
		this.isImmuneToFire = true;
        this.experienceValue = 100;
	}

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000.0D);
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	
    	if(source.isFireDamage())
    		amount = 0;
    	if(source.isMagicDamage())
    		amount = 0;
    	if(source.isProjectile())
    		amount *= 0.25F;
    	if(source.isExplosion())
    		amount *= 0.5F;
    	if(amount > 50)
    		amount = 50;
    	
    	return super.attackEntityFrom(source, amount);
    }
    
    
    public void onUpdate() {
        super.onUpdate();
        
        if(this.prevHealth >= this.getMaxHealth() / 2 && this.getHealth() < this.getMaxHealth() / 2) {
        	
        	prevHealth = this.getHealth();
        	
        	if(!worldObj.isRemote)
        		worldObj.createExplosion(this, posX, posY + 4, posZ, 2.5F, true);
        }
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    protected boolean canDespawn() {
        return false;
    }
    
    protected void dropFewItems(boolean bool, int i) {
    	
    	if(!worldObj.isRemote) {
    		this.dropItem(ModItems.coin_maskman, 1);
    		this.dropItem(ModItems.gas_mask_m65, 1);
    		this.dropItem(Items.skull, 1);
    	}
    }
}
