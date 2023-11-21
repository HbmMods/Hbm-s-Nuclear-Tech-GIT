package com.hbm.entity.mob.ai;

import com.hbm.entity.mob.EntityScutterfish;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

public class EntityAISwimmingVTwo extends EntityAIBase {
    private EntityScutterfish fish;
    private float speed;
    double xPosition;
    double yPosition;
    double zPosition;

    public EntityAISwimmingVTwo(EntityScutterfish fish, float speed) {
        this.fish = fish;
        this.speed = speed;
        this.setMutexBits(4);
        fish.getNavigator().setCanSwim(true);
    }

    @Override
    public boolean shouldExecute() {
        // This AI should only execute if the fish is in water
        if (fish.isInWater() && fish.getRNG().nextDouble() < 0.5) {
            // Randomly choose a destination within the water
            xPosition = fish.posX + (fish.getRNG().nextDouble() - 0.5) * 8.0;
            yPosition = fish.posY + (fish.getRNG().nextDouble() - 0.5) * 4.0;
            zPosition = fish.posZ + (fish.getRNG().nextDouble() - 0.5) * 8.0;
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        // Continue executing as long as the target position is in water and not reached
        return !fish.getNavigator().noPath() && fish.isInWater();
    }

    @Override
    public void startExecuting() {
        // Start moving towards the target position
        fish.getNavigator().tryMoveToXYZ(xPosition, yPosition, zPosition, speed);
    }

    @Override
    public void resetTask() {
        // Reset the pathfinder when the task is interrupted or completed
        xPosition = 0.0;
        yPosition = 0.0;
        zPosition = 0.0;
        fish.getNavigator().clearPathEntity();
    }
}


