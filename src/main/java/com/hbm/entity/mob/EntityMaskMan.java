package com.hbm.entity.mob;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMaskMan extends EntityMob implements IRangedAttackMob, IBossDisplayData {

	public EntityMaskMan(World world) {
		super(world);
		
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        
		this.setSize(2F, 5F);
		this.isImmuneToFire = true;
	}

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    protected boolean canDespawn() {
        return false;
    }

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float dist) {
		
		Vec3 vec = Vec3.createVectorHelper(target.posX - posX, (target.posY + target.getEyeHeight()) - (posY + 3), target.posZ - posZ).normalize();
		
		EntityBulletBase rawkett = new EntityBulletBase(worldObj, BulletConfigSyncingUtil.ROCKET_SHRAPNEL);
		rawkett.setVelocity(vec.xCoord, vec.yCoord, vec.zCoord);
		rawkett.setLocationAndAngles(posX + vec.xCoord * 2, posY, posZ + vec.zCoord * 2, 0.0F, 0.0F);
		
		rawkett.lastTickPosY = rawkett.prevPosY = rawkett.posY = rawkett.posY + 3;
		
		worldObj.spawnEntityInWorld(rawkett);
	}

}
