package com.hbm.entity.mob;

import java.util.Random;
import java.util.function.Predicate;

import com.hbm.entity.mob.ai.EntityAIEatBread;
import com.hbm.entity.mob.ai.EntityAISwimAway;
import com.hbm.entity.mob.ai.EntityAISwimmingConditional;
import com.hbm.entity.mob.ai.EntityAISwimmingVTwo;
import com.hbm.entity.mob.ai.EntityAIWanderConditional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityScutterfish extends EntityWaterMob implements IAnimals {
	
    private Vec3 currentSwimTarget;

    public boolean outOfWater;
    private static Random rand;


	public EntityScutterfish(World world) {
		super(world);
        Predicate<EntityPlayer> tooCloseCondition = player -> this.getDistanceToEntity(player) < 5.0D;
        this.getNavigator().setCanSwim(true);

		this.tasks.addTask(0, new EntityAISwimmingConditional(this, tooCloseCondition));
		this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(2, new EntityAILookIdle(this));
        this.tasks.addTask(3, new EntityAIWander(this, 1.5));
        this.tasks.addTask(4, new EntityAISwimmingVTwo(this, 1.0));

        this.currentSwimTarget = Vec3.createVectorHelper(posX, posY, posZ);

		this.setSize(2.0F, 2.0F);

	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(10.0);
    }
	protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_) {
		super.damageEntity(p_70665_1_, p_70665_2_);
	}
	@Override
    public boolean isInWater()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
    }

	public void onDeath(DamageSource p_70645_1_) {
		if(!worldObj.isRemote) MinecraftServer.getServer().getConfigurationManager().sendChatMsg(this.func_110142_aN().func_151521_b());
		super.onDeath(p_70645_1_);
	}
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
	    this.updateSwimTarget();
	    this.moveTowardsTarget();
		if(!this.isInWater()) {
		       this.rotationPitch += (float)0.2;
		        this.rotationYaw = 0.0f;
		        this.setJumping(true);
		        if (this.onGround) {
		            this.addVelocity(0.4 * EntityScutterfish.rand.nextDouble() - 0.4 * EntityScutterfish.rand.nextDouble(), 0.0, 0.4 * EntityScutterfish.rand.nextDouble() - 0.4 * EntityScutterfish.rand.nextDouble());
		        }
		}
	}

	private void updateSwimTarget() {
	    if (this.rand.nextInt(50) == 0 || !this.isInWater()) { // More frequent target updates
	        double targetX = this.posX + (this.rand.nextDouble() - 0.5) * 20.0;
	        double targetY = MathHelper.clamp_double(this.posY + (this.rand.nextDouble() - 0.5) * 8.0, 0, this.worldObj.getHeight() - 1);
	        double targetZ = this.posZ + (this.rand.nextDouble() - 0.5) * 20.0;
	        this.currentSwimTarget = Vec3.createVectorHelper(targetX, targetY, targetZ);
	    }
	}

	private void moveTowardsTarget() {
	    double deltaX = this.currentSwimTarget.xCoord - this.posX;
	    double deltaY = this.currentSwimTarget.yCoord - this.posY;
	    double deltaZ = this.currentSwimTarget.zCoord - this.posZ;
	    double distance = MathHelper.sqrt_double(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

	    double dirX = deltaX / distance;
	    double dirY = deltaY / distance;
	    double dirZ = deltaZ / distance;

	    double speed = 0.1; // You can adjust this value for different speeds
	    this.motionX += dirX * speed + (rand.nextDouble() - 0.5) * 0.02; // Added randomness
	    this.motionY += dirY * speed + (rand.nextDouble() - 0.5) * 0.02;
	    this.motionZ += dirZ * speed + (rand.nextDouble() - 0.5) * 0.02;
	}

    @Override
    public void moveEntityWithHeading(float strafe, float forward) {
        if (this.isInWater()) {
            this.motionY *= 0.8F;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }
    }

	private float updateRotation(float angle, float targetAngle, float maxIncrease) {
	    float angleDifference = MathHelper.wrapAngleTo180_float(targetAngle - angle);
	    if (angleDifference > maxIncrease) {
	        angleDifference = maxIncrease;
	    }
	    if (angleDifference < -maxIncrease) {
	        angleDifference = -maxIncrease;
	    }
	    return angle + angleDifference;
	}
}
	
