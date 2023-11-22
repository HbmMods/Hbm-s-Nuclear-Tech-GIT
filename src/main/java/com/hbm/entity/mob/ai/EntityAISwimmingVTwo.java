package com.hbm.entity.mob.ai;

import com.hbm.entity.mob.EntityScutterfish;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAISwimmingVTwo extends EntityAIBase {
    private EntityScutterfish fish;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;

    public EntityAISwimmingVTwo(EntityScutterfish fish, double speed) {
        this.fish = fish;
        this.speed = speed;
        this.setMutexBits(1); // Set the mutex bits as needed
    }

    @Override
    public boolean shouldExecute() {
        if (this.fish.getRNG().nextInt(120) != 0) {
            return false;
        }
        Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.fish, 10, 7);
        if (randomTarget == null) {
            return false;
        } else {
            this.xPosition = randomTarget.xCoord;
            this.yPosition = randomTarget.yCoord;
            this.zPosition = randomTarget.zCoord;
            return true;
        }
    }

    @Override
    public boolean continueExecuting() {
        return !this.fish.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.fish.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
}


