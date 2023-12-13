package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.MobConfig;
import com.hbm.entity.logic.EntityWaypoint;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.main.ResourceManager;
import com.hbm.world.feature.GlyphidHive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EntityGlyphidScout extends EntityGlyphid {

	boolean hasTarget = false;
	int timer;
	int scoutingRange = 45;
	int minDistanceToHive = 8;
	boolean useLargeHive = false;
	float largeHiveChance = MobConfig.largeHiveChance;
	public EntityGlyphidScout(World world) {
		super(world);
		this.setSize(1.25F, 0.75F);
	}

	//extreme measures for anti-scout bullying
	@Override
	public boolean attackEntityAsMob(Entity victum) {
		if(super.attackEntityAsMob(victum) && victum instanceof EntityLivingBase){
			((EntityLivingBase)victum).addPotionEffect(new PotionEffect(Potion.poison.id, 10 * 20, 3));
			return true;
		}
		return false;
	}
	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_scout_tex;
	}

	@Override
	public double getScale() {
		return 0.75D;
	}

	@Override
	public boolean isArmorBroken(float amount) {
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount, 2), 100);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.5D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
	}
	@Override
	public void onUpdate() {

		super.onUpdate();

		if((getCurrentTask() != expand || getCurrentTask() != terraform) && taskWaypoint == null) {

			    if(MobConfig.rampantGlyphidGuidance && PollutionHandler.targetCoords != null){
					if(!hasTarget) {
						Vec3 dirVec = playerBaseDirFinder(
								Vec3.createVectorHelper(posX, posY, posZ),
								PollutionHandler.targetCoords);

						EntityWaypoint target = new EntityWaypoint(worldObj);
						target.setLocationAndAngles(dirVec.xCoord, dirVec.yCoord, dirVec.zCoord, 0, 0);
						target.maxAge = 300;
						target.radius = 6;
						worldObj.spawnEntityInWorld(target);
						hasTarget = true;

						setCurrentTask(1, target);
					}

					if(super.isAtDestination()) {
						setCurrentTask(2, null) ;
						hasTarget = false;
					}

				} else {
					setCurrentTask(2, null);
				}

		}

		if(getCurrentTask() == expand || getCurrentTask() == terraform) {

			if(!worldObj.isRemote && !hasTarget) {
				//Check for whether a big man johnson is nearby, this makes the scout switch into its terraforming task
				if(scoutingRange != 60 && findJohnson()){
					setCurrentTask(5, null);
				}

				//Placeholder for a more advanced hive design
				/*
				if(PollutionHandler.getPollution(worldObj,
						(int) posX,
						(int) posY,
						(int) posZ, PollutionHandler.PollutionType.SOOT) >= MobConfig.largeHiveThreshold){

					useLargeHive = true;
					this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60 * 20, 3));
				}*/

				if (expandHive()){
					this.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 180*20, 1));
					hasTarget = true;
				}
			}

			if (getCurrentTask() == terraform && super.isAtDestination() && doubleCheckHive()) {
				communicate(terraform, taskWaypoint);
			}

			if (ticksExisted % 10 == 0 && isAtDestination()) {
				timer++;

				if (!worldObj.isRemote && doubleCheckHive()) {
					 if(timer == 1) {

						 EntityWaypoint additional = new EntityWaypoint(worldObj);
						 additional.setLocationAndAngles(posX, posY, posZ, 0, 0);
						 additional.setWaypointType(none);

						 //First, go home and get reinforcements
						 EntityWaypoint home = new EntityWaypoint(worldObj);
						 home.setWaypointType(comm);
						 home.setAdditionalWaypoint(additional);
						 home.setLocationAndAngles(homeX, homeY, homeZ, 0, 0);
						 home.maxAge = 1200;
						 home.radius = 6;

						 worldObj.spawnEntityInWorld(home);

						 this.taskWaypoint = home;
						 this.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40 * 20, 10));
						 communicate(comm, taskWaypoint);

					 } else if (timer >= 5) {

							 worldObj.newExplosion(this, posX, posY, posZ, 5F, false, false);
							 GlyphidHive.generateBigGround(worldObj,
									 (int) Math.floor(posX),
									 (int) Math.floor(posY),
									 (int) Math.floor(posZ), rand, true);
							 this.setDead();

					 } else {
						 communicate(follow, taskWaypoint);
					 }
				}
			}
		}
	}
    public boolean doubleCheckHive(){
		int length = useLargeHive ? 16 : 8;
		for(int i = 0; i < 8; i++) {
			float angle = (float) Math.toRadians(360D / 16 * i);
			Vec3 rot = Vec3.createVectorHelper(0, 0, length);
			rot.rotateAroundY(angle);
			Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY + 1, this.posZ);
			Vec3 nextPos = Vec3.createVectorHelper(this.posX + rot.xCoord, this.posY + 1, this.posZ + rot.zCoord);
			MovingObjectPosition mop = this.worldObj.rayTraceBlocks(pos, nextPos);

			if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

				Block block = worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);

				if (block == ModBlocks.glyphid_base) {
					setCurrentTask(none ,null);
					hasTarget = false;
					return false;
				}

			}
		}
		return true;
	}

	@Override
	public boolean isAtDestination() {
		return this.getCurrentTask() == expand && super.isAtDestination();
	}

	public boolean findJohnson(){
		int radius = 8;

		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
				this.posX - radius,
				this.posY - radius,
				this.posZ - radius,
				this.posX + radius,
				this.posY + radius,
				this.posZ + radius);

		List<Entity> bugs = worldObj.getEntitiesWithinAABBExcludingEntity(this, bb);
		for (Entity e: bugs){
			if(e instanceof EntityGlyphidNuclear){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean expandHive() {

	   int nestX = rand.nextInt((homeX + scoutingRange) - (homeX - scoutingRange)) + (homeX - scoutingRange);
	   int nestZ = rand.nextInt((homeZ + scoutingRange) - (homeZ - scoutingRange)) + (homeZ - scoutingRange);
	   int nestY = worldObj.getHeightValue(nestX, nestZ);
	   Block b = worldObj.getBlock(nestX, nestY - 1, nestZ);

	   boolean distanceCheck = Vec3.createVectorHelper(
			   nestX - homeX,
			   nestY - homeY,
			   nestZ - homeZ).lengthVector() > minDistanceToHive;

	   if(distanceCheck && b.getMaterial() != Material.air && b.isNormalCube() && b != ModBlocks.glyphid_base) {

		   if(b == ModBlocks.basalt) {
			   useLargeHive = true;
			   largeHiveChance /= 2;
			   this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60 * 20, 3));
		   }
		   if(!worldObj.isRemote) {
			   EntityWaypoint nest = new EntityWaypoint(worldObj);
			   nest.setWaypointType(getCurrentTask());
			   nest.radius = 5;

			   if(useLargeHive)
				   nest.setHighPriority();

			   nest.setLocationAndAngles(nestX, nestY, nestZ, 0, 0);
			   worldObj.spawnEntityInWorld(nest);

			   taskWaypoint = nest;

               //updates the task coordinates
			   setCurrentTask(getCurrentTask(), taskWaypoint);
			   communicate(expand, taskWaypoint);
		   }
		   return true;
	   }
		   return false;
	}


	@Override
	public void carryOutTask() {
		if (!worldObj.isRemote && taskWaypoint == null) {
			switch(getCurrentTask()){
				case reinforcements:
					this.removePotionEffect(Potion.moveSlowdown.id);
					this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20 * 20, 4));

					//then, come back later
					EntityWaypoint additional = new EntityWaypoint(worldObj);
					additional.setLocationAndAngles(posX, posY, posZ, 0, 0);
					additional.setWaypointType(0);
					
					//First, go home and get reinforcements
					EntityWaypoint home = new EntityWaypoint(worldObj);
					home.setWaypointType(2);
					home.setAdditionalWaypoint(additional);
					home.setHighPriority();
					home.radius = 6;
					home.setLocationAndAngles(homeX, homeY, homeZ, 0, 0);
					worldObj.spawnEntityInWorld(home);

					communicate(4, home);
				break;

				//terraforming task, only used if a big man johnson is near the scout
				case terraform:
					scoutingRange = 60;
					minDistanceToHive = 20;
			}
		}
		super.carryOutTask();

	}
	@Override
	public boolean useExtendedTargeting() {
		return false;
	}

    ///RAMPANT MODE STUFFS

	/** Finds the direction from the bug's location to the target and adds it to their current coord
	 * Used as a performant way to make scouts expand toward the player's spawn point
	 * @return An adjusted direction vector, to be added into the bug's current position for it to path in the required direction**/
	public static Vec3 playerBaseDirFinder(Vec3 currentLocation, Vec3 target){
		Vec3 dirVec = currentLocation.subtract(target).normalize();
		return Vec3.createVectorHelper(
				currentLocation.xCoord + dirVec.xCoord * 10,
				currentLocation.yCoord + dirVec.yCoord * 10,
				currentLocation.zCoord + dirVec.zCoord * 10
		);
	}

}
