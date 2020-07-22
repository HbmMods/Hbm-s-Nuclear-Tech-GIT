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
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
        
		this.setSize(2F, 5F);
		this.isImmuneToFire = true;
	}

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }
    
    public boolean isAIEnabled()
    {
        return true;
    }
    
    protected boolean canDespawn()
    {
        return false;
    }

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float dist) {
		
		Vec3 vec = Vec3.createVectorHelper(posX - target.posX, (posY + 3) - (target.posY + target.getEyeHeight()), posZ - target.posZ);
		
		EntityBulletBase rawkett = new EntityBulletBase(worldObj, BulletConfigSyncingUtil.ROCKET_SHRAPNEL);
		rawkett.copyLocationAndAnglesFrom(target);

		//rawkett.lastTickPosX = rawkett.prevPosX = rawkett.posX = this.posX;
		//rawkett.lastTickPosY = rawkett.prevPosY = rawkett.posY = this.posY + 3;
		//rawkett.lastTickPosZ = rawkett.prevPosZ = rawkett.posZ = this.posZ;
		
		rawkett.lastTickPosY = rawkett.prevPosY = rawkett.posY = rawkett.posY + 3;
		rawkett.setVelocity(vec.xCoord, vec.yCoord, vec.zCoord);
		
		System.out.println("aaaaaa");
		
		worldObj.spawnEntityInWorld(rawkett);
	}

}
