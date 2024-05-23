package com.hbm.entity.mob;

import com.hbm.entity.mob.ai.EntityAIBehemothGun;
import com.hbm.entity.mob.ai.EntityAIMaskmanMinigun;
import com.hbm.entity.mob.ai.EntityAIStepTowardsTarget;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import scala.reflect.internal.Trees.This;
//no model yet, im gonna take this low n slow
public class EntityWarBehemoth extends EntityMob implements IRangedAttackMob {
    private int stepTimer = 0;
    private static final int STEP_DURATION = 20; // Duration of each step in ticks (1 second)
    private static final int STEP_COOLDOWN = 60; // Cooldown period between steps in ticks (1 second)

    private static final IEntitySelector selector = new IEntitySelector() {
		public boolean isEntityApplicable(Entity p_82704_1_) {
			return !(p_82704_1_ instanceof EntityCyberCrab || p_82704_1_ instanceof EntityCreeper);
		}
	};

    public EntityWarBehemoth(World p_i1733_1_)
    {
        super(p_i1733_1_);
        this.setSize(0.75F, 1.35F);
        this.getNavigator().setAvoidsWater(true);
                
        this.tasks.addTask(1, new EntityAIWander(this, 0.55F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, true, selector));
        this.tasks.addTask(3, new EntityAIStepTowardsTarget(this, 4, 0.4, STEP_DURATION, STEP_COOLDOWN, 0.6));
		this.tasks.addTask(4, new EntityAIBehemothGun(this, true, true, 1));

    }
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(300.0D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000.0D);
	}

    @Override
	protected String getHurtSound()
    {
        return "hbm:entity.cybercrab";
    }
	@Override
	public boolean isAIEnabled() {
		return true;
	}
    @Override
    public void onUpdate() {
        super.onUpdate();
    }
    public double getMaxTargetRange() {
        return 64;
    }
}
