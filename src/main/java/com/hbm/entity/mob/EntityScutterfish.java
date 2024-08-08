package com.hbm.entity.mob;

import java.util.Random;
import java.util.function.Predicate;

import com.hbm.entity.mob.ai.EntityAIEatBread;
import com.hbm.entity.mob.ai.EntityAISwimAway;
import com.hbm.entity.mob.ai.EntityAISwimmingConditional;
import com.hbm.entity.mob.ai.EntityAISwimmingVTwo;
import com.hbm.entity.mob.ai.EntityAIWanderConditional;
import com.hbm.items.ModItems;

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
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityScutterfish extends EntityWaterMob implements IAnimals {
	

    private Vec3 currentSwimTarget;
    private static final Random rand = new Random();

    public EntityScutterfish(World world) {
        super(world);
        Predicate<EntityPlayer> tooCloseCondition = player -> this.getDistanceToEntity(player) < 5.0D;
        this.getNavigator().setCanSwim(true);

        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIWander(this, 1.5));

        this.currentSwimTarget = Vec3.createVectorHelper(posX, posY, posZ);
        this.setSize(2.0F, 2.0F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(10.0);
    }

    @Override
    protected void damageEntity(DamageSource source, float amount) {
        super.damageEntity(source, amount);
    }

    @Override
    public boolean isInWater() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();

        if (!this.isInWater()) {
            this.rotationPitch += 0.2F;
            this.rotationYaw = 0.0F;
            this.setJumping(true);
            if (this.onGround) {
                this.addVelocity(0.4 * rand.nextDouble() - 0.4 * rand.nextDouble(), 0.0, 0.4 * rand.nextDouble() - 0.4 * rand.nextDouble());
            }
        }
    }
    @Override
    protected boolean isAIEnabled()
    {
       return true;
    }
    
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
        this.updateSwimTarget();
        this.moveTowardsTarget();
        updateRotation();
	}
    private void updateSwimTarget() {
        if (rand.nextInt(50) == 0 || !this.isInWater()) {
            double targetX = this.posX + (rand.nextDouble() - 0.5) * 20.0;
            double targetY = MathHelper.clamp_double(this.posY + (rand.nextDouble() - 0.5) * 8.0, 0, this.worldObj.getHeight() - 1);
            double targetZ = this.posZ + (rand.nextDouble() - 0.5) * 20.0;
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

        double speed = 0.01; // You can adjust this value for different speeds
        this.motionX += dirX * speed + (rand.nextDouble() - 0.5) * 0.02; // Added randomness
        this.motionY += dirY * speed + (rand.nextDouble() - 0.5) * 0.02;
        this.motionZ += dirZ * speed + (rand.nextDouble() - 0.5) * 0.02;
        
        updateRotation((float)dirX, (float)dirY, (float)dirZ);
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

    private void updateRotation() {
        double deltaX = this.motionX;
        double deltaZ = this.motionZ;
        double deltaY = this.motionY;
        float targetYaw = (float) (Math.atan2(deltaZ, deltaX) * (180D / Math.PI)) - 90F;
        float targetPitch = (float) -(Math.atan2(deltaY, Math.sqrt(deltaX * deltaX + deltaZ * deltaZ)) * (180D / Math.PI));

        this.rotationYaw = this.updateRotation(this.rotationYaw, targetYaw, 10.0F);
        this.rotationPitch = this.updateRotation(this.rotationPitch, targetPitch, 10.0F);
    }

    private float updateRotation(float currentRotation, float targetRotation, float maxIncrement) {
        float deltaRotation = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
        if (deltaRotation > maxIncrement) {
            deltaRotation = maxIncrement;
        }
        if (deltaRotation < -maxIncrement) {
            deltaRotation = -maxIncrement;
        }
        return currentRotation + deltaRotation;
    }

	@Override
	protected Item getDropItem() {
		return ModItems.scuttertail;
	}
}
	
