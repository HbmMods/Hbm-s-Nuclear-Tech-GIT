package com.hbm.entity.mob.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIBreaking extends EntityAIBase {

	EntityLivingBase target;
	int[] markedLoc;
	EntityLiving entityDigger;
	int digTick = 0;
	int scanTick = 0;
	
	public EntityAIBreaking(EntityLiving entity)
	{
		this.entityDigger = entity;
	}
	
	@Override
	public boolean shouldExecute()
	{
		target = entityDigger.getAttackTarget();
		
		if(target != null && entityDigger.getNavigator().noPath() && entityDigger.getDistanceToEntity(target) > 1D && (target.onGround || !entityDigger.canEntityBeSeen(target)))
		{
			MovingObjectPosition mop = GetNextObstical(entityDigger, 2D);
			
			if(mop == null || mop.typeOfHit != MovingObjectType.BLOCK)
			{
				return false;
			}

			Block block = entityDigger.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			
			if(block.getBlockHardness(entityDigger.worldObj, mop.blockX, mop.blockY, mop.blockZ) >= 0) {
				markedLoc = new int[]{mop.blockX, mop.blockY, mop.blockZ};
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean continueExecuting()
	{
		//return target != null && entityDigger != null && target.isEntityAlive() && entityDigger.isEntityAlive() && markedLoc != null && entityDigger.getNavigator().noPath() && entityDigger.getDistanceToEntity(target) > 1D && (target.onGround || !entityDigger.canEntityBeSeen(target));
		
		if(markedLoc != null)  {
			
			Vec3 vector = Vec3.createVectorHelper(
					markedLoc[0] - entityDigger.posX,
					markedLoc[1] - (entityDigger.posY + entityDigger.getEyeHeight()),
					markedLoc[2] - entityDigger.posZ);

			return entityDigger != null && entityDigger.isEntityAlive() && vector.lengthVector() <= 4;
		}
		
		return false;
	}
	
	@Override
	public void updateTask()
	{
    	MovingObjectPosition mop = null;
    	
    	if(entityDigger.ticksExisted % 10 == 0)
    	{
    		mop = GetNextObstical(entityDigger, 2D);
    	}
		
		if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
		{
			markedLoc = new int[]{mop.blockX, mop.blockY, mop.blockZ};
		}
		
		if(markedLoc == null || markedLoc.length != 3 || entityDigger.worldObj.getBlock(markedLoc[0], markedLoc[1], markedLoc[2]) == Blocks.air)
		{
			digTick = 0;
			return;
		}
		
		Block block = entityDigger.worldObj.getBlock(markedLoc[0], markedLoc[1], markedLoc[2]);
		digTick++;
		
		int health = (int) block.getBlockHardness(entityDigger.worldObj, markedLoc[0], markedLoc[1], markedLoc[2]) / 3;
		
		if(health < 0) {
			markedLoc = null;
			return;
		}
		
		float str = (digTick * 0.05F) / (float)health;
		
		if(str >= 1F)
		{
			digTick = 0;
			
			boolean canHarvest = false;
			entityDigger.worldObj.func_147480_a(markedLoc[0], markedLoc[1], markedLoc[2], canHarvest);
			markedLoc = null;
			
			if(target != null)
				entityDigger.getNavigator().setPath(entityDigger.getNavigator().getPathToEntityLiving(target), 1D);
		} else
		{
			if(digTick % 5 == 0)
			{
				entityDigger.worldObj.playSoundAtEntity(entityDigger, block.stepSound.getStepResourcePath(), block.stepSound.getVolume() + 1F, block.stepSound.getPitch());
				entityDigger.swingItem();
				entityDigger.worldObj.destroyBlockInWorldPartially(entityDigger.getEntityId(), markedLoc[0], markedLoc[1], markedLoc[2], (int)(str * 10F));
			}
		}
	}
	
	@Override
	public void resetTask()
	{
		markedLoc = null;
		digTick = 0;
	}
	
	/**
	 * Rolls through all the points in the bounding box of the entity and raycasts them toward it's current heading to return any blocks that may be obstructing it's path.
	 * The bigger the entity the longer this calculation will take due to the increased number of points (Generic bipeds should only need 2)
	 */
    public MovingObjectPosition GetNextObstical(EntityLivingBase entityLiving, double dist)
    {
    	// Returns true if something like Iguana Tweaks is nerfing the vanilla picks. This will then cause zombies to ignore the harvestability of blocks when holding picks
        float f = 1.0F;
        float f1 = entityLiving.prevRotationPitch + (entityLiving.rotationPitch - entityLiving.prevRotationPitch) * f;
        float f2 = entityLiving.prevRotationYaw + (entityLiving.rotationYaw - entityLiving.prevRotationYaw) * f;
        
        int digWidth = MathHelper.ceiling_double_int(entityLiving.width);
        int digHeight = MathHelper.ceiling_double_int(entityLiving.height);
        
        int passMax = digWidth * digWidth * digHeight;
        
        int x = scanTick%digWidth - (digWidth/2);
        int y = scanTick/(digWidth * digWidth);
        int z = (scanTick%(digWidth * digWidth))/digWidth - (digWidth/2);
        
		double rayX = x + entityLiving.posX;
		double rayY = y + entityLiving.posY;
		double rayZ = z + entityLiving.posZ;
		
    	MovingObjectPosition mop = RayCastBlocks(entityLiving.worldObj, rayX, rayY, rayZ, f2, f1, dist, false);
    	
    	if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
    	{
    		Block block = entityLiving.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
    		
    		if(block.getBlockHardness(entityLiving.worldObj, mop.blockX, mop.blockY, mop.blockZ) >= 0)
    		{
    			scanTick = 0;
    			return mop;
    		} else
    		{
    			scanTick = (scanTick + 1)%passMax;
    			return null;
    		}
    	} else
    	{
			scanTick = (scanTick + 1)%passMax;
			return null;
    	}
    }
    
    public static MovingObjectPosition RayCastBlocks(World world, double x, double y, double z, float yaw, float pitch, double dist, boolean liquids)
    {
        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);
        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
        float f6 = MathHelper.sin(-pitch * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = dist; // Ray Distance
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return RayCastBlocks(world, vec3, vec31, liquids);
    }
    
    public static MovingObjectPosition RayCastBlocks(World world, Vec3 vector1, Vec3 vector2, boolean liquids)
    {
        return world.func_147447_a(vector1, vector2, liquids, !liquids, false);
    }
}
