package com.hbm.entity.mob.ai;

import java.util.List;

import com.hbm.entity.mob.EntityScutterfish;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class EntityAISwimAway extends EntityAIBase {
    private EntityScutterfish fish;
    private double speed;
    private EntityPlayer closestPlayer;
	private double randPosY;
	private double randPosX;
	private double randPosZ;

    public EntityAISwimAway(EntityScutterfish fish, double speed) {
        this.fish = fish;
        this.speed = speed;
    }

    @Override
    public boolean shouldExecute() {
        List players = fish.worldObj.getEntitiesWithinAABB(EntityPlayer.class, fish.boundingBox.expand(5.0D, 3.0D, 5.0D));
        if (!players.isEmpty()) {
            closestPlayer = (EntityPlayer) players.get(0);
			Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.fish, 5, 4);


				this.randPosX = vec3.xCoord;
				this.randPosY = vec3.yCoord;
				this.randPosZ = vec3.zCoord;
				return true;
			}
	
        return false;
    }
	
	

	@Override
	public void startExecuting() {
		this.fish.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
	}
    @Override
    public boolean continueExecuting() {
        // Ensure that the fish continues to flee as long as the player is close
        return fish.getDistanceToEntity(closestPlayer) < 10.0F;
    }
}