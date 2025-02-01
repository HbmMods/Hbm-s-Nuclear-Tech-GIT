package com.hbm.entity.mob.glyphid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.MobConfig;
import com.hbm.entity.logic.EntityWaypoint;
import com.hbm.entity.mob.EntityParasiteMaggot;
import com.hbm.entity.mob.glyphid.GlyphidStats.StatBundle;
import com.hbm.entity.pathfinder.PathFinderUtils;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.*;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.ResourceManager;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import api.hbm.entity.IResistanceProvider;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.util.*;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityGlyphid extends EntityMob implements IResistanceProvider {

	//I might have overdone it a little bit

	public boolean hasHome = false;
	public int homeX;
	public int homeY;
	public int homeZ;
	protected int currentTask = 0;

	//both of those below are used for digging, so the glyphid remembers what it was doing
	protected int previousTask;
	protected EntityWaypoint previousWaypoint;
	public int taskX;
	public int taskY;
	public int taskZ;

	//used for digging, bigger glyphids have a longer reach
	public int blastSize = Math.min((int) (3 * (getScale())) / 2, 5);
	public int blastResToDig = Math.min((int) (50 * (getScale() * 2)), 150);
	public boolean shouldDig;

	// Tasks

	/** Idle state, only makes glpyhids wander around randomly */
	public static final int TASK_IDLE = 0;
	/** Causes the glyphid to walk to the waypoint, then communicate the FOLLOW task to nearby glyphids */
	public static final int TASK_RETREAT_FOR_REINFORCEMENTS = 1;
	/** Task used by scouts, if the waypoint is reached it will construct a new hive */
	public static final int TASK_BUILD_HIVE = 2;
	/** Creates a waypoint at the home position and then immediately initiates the RETREAT_FOR_REINFORCEMENTS task */
	public static final int TASK_INITIATE_RETREAT = 3;
	/** Will simply walk to the waypoint and enter IDLE once it is reached */
	public static final int TASK_FOLLOW = 4;
	/** Causes nuclear glyphids to immediately self-destruct, also signaling nearby scouts to retreat */
	public static final int TASK_TERRAFORM = 5;
	/** If any task other than IDLE is interrupted by an obstacle, initiates digging behavior which is also communicated to nearby glyohids */
	public static final int TASK_DIG = 6;
	
	protected boolean hasWaypoint = false;
	/** Yeah, fuck, whatever, anything goes now */
	protected EntityWaypoint taskWaypoint = null;

	//subtypes
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_INFECTED = 1;
	public static final int TYPE_RADIOACTIVE = 2;

	//data watcher keys
	public static final int DW_WALL = 16;
	public static final int DW_ARMOR = 17;
	public static final int DW_SUBTYPE = 18;
	
	public EntityGlyphid(World world) {
		super(world);
		this.setSize(1.75F, 1F);
	}

	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_tex;
	}

	public double getScale() {
		return 1.0D;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(DW_WALL, new Byte((byte) 0));		//wall climbing
		this.dataWatcher.addObject(DW_ARMOR, new Byte((byte) 0b11111));	//armor
		this.dataWatcher.addObject(DW_SUBTYPE, new Byte((byte) 0));		//subtype (i.e. normal, infected, etc)
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		int variant = this.dataWatcher.getWatchableObjectByte(DW_SUBTYPE);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(GlyphidStats.getStats().getGrunt().health);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(GlyphidStats.getStats().getGrunt().speed * (variant == TYPE_RADIOACTIVE ? 2D : 1D));
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(GlyphidStats.getStats().getGrunt().damage * (variant == TYPE_RADIOACTIVE ? 5D : 1D));
	}
	
	public StatBundle getStats() {
		return GlyphidStats.getStats().statsGrunt;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isDamageAbsolute() || damage.isUnblockable()) return new float[] {0F, 0F};
		StatBundle stats = this.getStats();
		float threshold = stats.thresholdMultForArmor * getGlyphidArmor() / 5F;
		
		if(damage == ModDamageSource.nuclearBlast) return new float[] {threshold * 0.25F, 0F}; // nukes shred shrough glyphids
		if(damage.damageType.equals(DamageClass.LASER.name().toLowerCase(Locale.US))) return new float[] {threshold * 0.5F, stats.resistanceMult * 0.5F}; //lasers are quite powerful too
		if(damage.damageType.equals(DamageClass.ELECTRIC.name().toLowerCase(Locale.US))) return new float[] {threshold * 0.25F, stats.resistanceMult * 0.25F}; //electricity even more so
		if(damage.damageType.equals(DamageClass.SUBATOMIC.name().toLowerCase(Locale.US))) return new float[] {0F, stats.resistanceMult * 0.1F}; //and particles are almsot commpletely unaffected
		
		if(damage.isFireDamage()) return new float[] {0F, stats.resistanceMult * 0.2F}; //fire ignores DT and most DR
		if(damage.isExplosion()) return new float[] {threshold * 0.5F, stats.resistanceMult * 0.35F}; //explosions  are still subject to DT and reduce DR by a fair amount
		
		return new float[] {threshold, stats.resistanceMult};
	}

	@Override
	public void onDamageDealt(DamageSource damage, float amount) {
		if(this.isArmorBroken(amount)) this.breakOffArmor();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!worldObj.isRemote) {
			if(!hasHome) {
				homeX = (int) posX;
				homeY = (int) posY;
				homeZ = (int) posZ;
				hasHome = true;
			}

			if(this.isPotionActive(Potion.blindness)) {
				onBlinded();
			}

			if(getCurrentTask() == TASK_FOLLOW){

				//incase the waypoint somehow doesn't exist and it got this task anyway
				if(isAtDestination() && !hasWaypoint) {
					setCurrentTask(TASK_IDLE, null);
				}
			//the task cannot be 6 outside of rampant, so this is a non issue p much
			} else if (getCurrentTask() == TASK_DIG && ticksExisted % 20 == 0 && isAtDestination()) {
				swingItem();

				ExplosionVNT vnt = new ExplosionVNT(worldObj, taskX, taskY + 2, taskZ, blastSize, this);
				vnt.setBlockAllocator(new BlockAllocatorGlyphidDig(blastResToDig));
				vnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop());
				vnt.setEntityProcessor(null);
				vnt.setPlayerProcessor(null);
				vnt.explode();

				this.setCurrentTask(previousTask, previousWaypoint);
			}

			this.setBesideClimbableBlock(isCollidedHorizontally);

			if(ticksExisted % 100 == 0) {
				this.swingItem();
			}
		}
	}


	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		super.dropFewItems(byPlayer, looting);
		Item drop = isBurning() ? ModItems.glyphid_meat_grilled : ModItems.glyphid_meat;
		if(rand.nextInt(2) == 0) this.entityDropItem(new ItemStack(drop, ((int) getScale() * 2) + looting), 0F);
	}

	@Override
	protected Entity findPlayerToAttack() {
		if(this.isPotionActive(Potion.blindness)) return null;

		return this.worldObj.getClosestVulnerablePlayerToEntity(this, useExtendedTargeting() ? 128D : 16D);
	}

	@Override
	protected void updateWanderPath() {
		if(getCurrentTask() == TASK_IDLE) {
			super.updateWanderPath();
		}
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();

		if(!this.isPotionActive(Potion.blindness)) {
			if (!this.hasPath()) {

				// hell yeah!!
				if(useExtendedTargeting() && this.entityToAttack != null) {
					this.setPathToEntity(PathFinderUtils.getPathEntityToEntityPartial(worldObj, this, this.entityToAttack, 16F, true, false, true, true));
				} else if (getCurrentTask() != TASK_IDLE) {

					this.worldObj.theProfiler.startSection("stroll");

					if (!isAtDestination()) {

						if (taskWaypoint != null) {

							taskX = (int) taskWaypoint.posX;
							taskY = (int) taskWaypoint.posY;
							taskZ = (int) taskWaypoint.posZ;

							if (taskWaypoint.highPriority) {
								setTarget(taskWaypoint);
							}

						}

						if(hasWaypoint) {
							
							if(canDig()) {

								MovingObjectPosition obstacle = findWaypointObstruction();
								if (getScale() >= 1 && getCurrentTask() != TASK_DIG && obstacle != null) {
									digToWaypoint(obstacle);
								} else {
									Vec3 vec = Vec3.createVectorHelper(posX, posY, posZ);
									int maxDist = (int) (Math.sqrt(vec.squareDistanceTo(taskX, taskY, taskZ)) * 1.2);
									this.setPathToEntity(PathFinderUtils.getPathEntityToCoordPartial(worldObj, this, taskX, taskY, taskZ, maxDist, true, false, true, true));
								}

							} else {
								Vec3 vec = Vec3.createVectorHelper(posX, posY, posZ);
								int maxDist = (int) (Math.sqrt(vec.squareDistanceTo(taskX, taskY, taskZ)) * 1.2);
								this.setPathToEntity(PathFinderUtils.getPathEntityToCoordPartial(worldObj, this, taskX, taskY, taskZ, maxDist, true, false, true, true));
							}
						}
					}
					
					this.worldObj.theProfiler.endSection();
				}
			}
		}
	}

	protected boolean canDig() {
		return MobConfig.rampantDig;
	}

	public void onBlinded(){
		this.entityToAttack = null;
		this.setPathToEntity(null);
		this.fleeingTick = 80;

		if(getScale() >= 1.25){
			if(ticksExisted % 20 == 0) {
				for (int i = 0; i < 16; i++) {
					float angle = (float) Math.toRadians(360D / 16 * i);
					Vec3 rot = Vec3.createVectorHelper(0, 0, 4);
					rot.rotateAroundY(angle);
					Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY + 1, this.posZ);
					Vec3 nextPos = Vec3.createVectorHelper(this.posX + rot.xCoord, this.posY + 1, this.posZ + rot.zCoord);
					MovingObjectPosition mop = this.worldObj.rayTraceBlocks(pos, nextPos);

					if (mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {

						Block block = worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);

						if (block == ModBlocks.lantern) {
							rotationYaw = 360F / 16 * i;
							swingItem();
							worldObj.func_147480_a(mop.blockX, mop.blockY, mop.blockZ, false);
						}

					}
				}
			}
		}
	}

	public boolean useExtendedTargeting() {
		return MobConfig.rampantExtendedTargetting || PollutionHandler.getPollution(worldObj, (int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ), PollutionType.SOOT) >= MobConfig.targetingThreshold;
	}

	@Override
	protected boolean canDespawn() {
		return entityToAttack == null && getCurrentTask() == TASK_IDLE && this.ticksExisted > 100;
	}
	
	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		
		if(doesInfectedSpawnMaggots() && this.dataWatcher.getWatchableObjectByte(DW_SUBTYPE) == TYPE_INFECTED) {

			int j = 2 + this.rand.nextInt(3);

			for(int k = 0; k < j; ++k) {
				float f = ((float) (k % 2) - 0.5F) * 0.5F;
				float f1 = ((float) (k / 2) - 0.5F) * 0.5F;
				EntityParasiteMaggot maggot = new EntityParasiteMaggot(worldObj);
				maggot.setLocationAndAngles(this.posX + (double) f, this.posY + 0.5D, this.posZ + (double) f1, this.rand.nextFloat() * 360.0F, 0.0F);
				maggot.motionX = f;
				maggot.motionZ = f1;
				maggot.velocityChanged = true;
				this.worldObj.spawnEntityInWorld(maggot);
			}

			worldObj.playSoundEffect(posX, posY, posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + worldObj.rand.nextFloat() * 0.2F);
			
			NBTTagCompound vdat = new NBTTagCompound();
			vdat.setString("type", "giblets");
			vdat.setInteger("ent", this.getEntityId());
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(vdat, posX, posY + height * 0.5, posZ), new TargetPoint(dimension, posX, posY + height * 0.5, posZ, 150));
			
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source.getEntity() instanceof EntityGlyphid) return false;
		boolean wasAttacked = GlyphidStats.getStats().handleAttack(this, source, amount);
		return wasAttacked;
	}

	/** Provides a direct entrypoint from outside to access the superclass' implementation because otherwise we end up with infinite recursion */
	public boolean attackSuperclass(DamageSource source, float amount) {
		
		/*NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "debug");
		data.setInteger("color", 0x0000ff);
		data.setFloat("scale", 2.5F);
		data.setString("text", "" + (int) amount);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY + 2, posZ), new TargetPoint(dimension, posX, posY + 2, posZ, 50));*/
		
		return super.attackEntityFrom(source, amount);
	}
	
	public boolean doesInfectedSpawnMaggots() {
		return true;
	}

	public boolean isArmorBroken(float amount) {
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.6, 2), 100);
	}

	public void breakOffArmor() {
		byte armor = this.dataWatcher.getWatchableObjectByte(DW_ARMOR);
		List<Integer> indices = Arrays.asList(0, 1, 2, 3, 4);
		Collections.shuffle(indices);

		for(Integer i : indices) {
			byte bit = (byte) (1 << i);
			if((armor & bit) > 0) {
				armor &= ~bit;
				armor = (byte) (armor & 0b11111);
				this.dataWatcher.updateObject(DW_ARMOR, armor);
				worldObj.playSoundAtEntity(this, "mob.zombie.woodbreak", 1.0F, 1.25F);
				break;
			}
		}
	}
	
	public int getGlyphidArmor() {
		int total = 0;
		byte armor = this.dataWatcher.getWatchableObjectByte(DW_ARMOR);
		List<Integer> indices = Arrays.asList(0, 1, 2, 3, 4);
		for(Integer i : indices) {
			total += (armor & (1 << i)) != 0 ? 1 : 0;
		}
		return total;
	}

	@Override
	protected void updateArmSwingProgress() {
		int i = this.swingDuration();

		if(this.isSwingInProgress) {
			++this.swingProgressInt;

			if(this.swingProgressInt >= i) {
				this.swingProgressInt = 0;
				this.isSwingInProgress = false;
			}
		} else {
			this.swingProgressInt = 0;
		}

		this.swingProgress = (float) this.swingProgressInt / (float) i;
	}

	public int swingDuration() {
		return 15;
	}

	@Override
	public void setInWeb() { }

	@Override
	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}

	public boolean isBesideClimbableBlock() {
		return (this.dataWatcher.getWatchableObjectByte(DW_WALL) & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean climbable) {
		byte watchable = this.dataWatcher.getWatchableObjectByte(DW_WALL);

		if(climbable) {
			watchable = (byte) (watchable | 1);
		} else {
			watchable &= -2;
		}

		this.dataWatcher.updateObject(DW_WALL, Byte.valueOf(watchable));
	}

	@Override
	public boolean attackEntityAsMob(Entity victim) {
		if(this.isSwingInProgress) return false;
		this.swingItem();
		
		if(this.dataWatcher.getWatchableObjectByte(DW_SUBTYPE) == TYPE_INFECTED && victim instanceof EntityLivingBase) {
			((EntityLivingBase) victim).addPotionEffect(new PotionEffect(Potion.poison.id, 100, 2));
			((EntityLivingBase) victim).addPotionEffect(new PotionEffect(Potion.confusion.id, 100, 0));
		}
		
		return super.attackEntityAsMob(victim);
	}


	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	/// TASK SYSTEM START ///
	public int getCurrentTask(){
		return currentTask;
	}

	public EntityWaypoint getWaypoint(){
		return taskWaypoint;
	}

	/**
	 * Sets a new task for the glyphid to do, a waypoint alongside with that task, and refreshes their waypoint coordinates
	 * @param task The task the glyphid is to do, refer to carryOutTask()
	 * @param waypoint The waypoint for the task, can be null
	 */
	public void setCurrentTask(int task, @Nullable EntityWaypoint waypoint){
		this.currentTask = task;
		this.taskWaypoint = waypoint;
		this.hasWaypoint = waypoint != null;
		if(taskWaypoint != null) {

			taskX = (int) taskWaypoint.posX;
			taskY = (int) taskWaypoint.posY;
			taskZ = (int) taskWaypoint.posZ;

			if(taskWaypoint.highPriority) {
				this.entityToAttack = null;
				this.setPathToEntity(null);
			}

		}
		carryOutTask();
	}

	/**
	 * Handles the task system, used mainly for things that only need to be done once, such as setting targets
	 */
	public void carryOutTask(){
		int task = getCurrentTask();

		switch(task){

		case TASK_RETREAT_FOR_REINFORCEMENTS:
			if(taskWaypoint != null) {
				communicate(TASK_FOLLOW, taskWaypoint);
				setCurrentTask(TASK_FOLLOW, taskWaypoint);
			}
			break;

		case TASK_INITIATE_RETREAT:

			if(!worldObj.isRemote && taskWaypoint == null) {

				// Then, Come back later
				EntityWaypoint additional = new EntityWaypoint(worldObj);
				additional.setLocationAndAngles(posX, posY, posZ, 0, 0);

				// First, go home and get reinforcements
				EntityWaypoint home = new EntityWaypoint(worldObj);
				home.setWaypointType(TASK_RETREAT_FOR_REINFORCEMENTS);
				home.setAdditionalWaypoint(additional);
				home.setHighPriority();
				home.setLocationAndAngles(homeX, homeY, homeZ, 0, 0);
				worldObj.spawnEntityInWorld(home);

				this.taskWaypoint = home;
				communicate(TASK_FOLLOW, home);
				setCurrentTask(TASK_FOLLOW, taskWaypoint);

				break;
			}

			break;
			
		case TASK_DIG:
			shouldDig = true;
			break;

		default:
			break;
			
		}

	}

	/** Copies tasks and waypoint to nearby glyphids. Does not work on glyphid scouts */
	public void communicate(int task, @Nullable EntityWaypoint waypoint) {
		int radius = waypoint != null ? waypoint.radius : 4;
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(radius, radius, radius);

		List<Entity> bugs = worldObj.getEntitiesWithinAABBExcludingEntity(this, bb);
		for(Entity e : bugs) {
			if(e instanceof EntityGlyphid && !(e instanceof EntityGlyphidScout)) {
				if(((EntityGlyphid) e).getCurrentTask() != task) {
					((EntityGlyphid) e).setCurrentTask(task, waypoint);
				}
			}
		}
	}

	/** What each type of glyphid does when it is time to expand the hive.
	 * @return Whether it has expanded successfully or not
	 * **/
	public boolean expandHive(){
		return false;
	}

	public boolean isAtDestination() {
		int destinationRadius = taskWaypoint != null ? (int) Math.pow(taskWaypoint.radius, 2) : 25;
		return this.getDistanceSq(taskX, taskY, taskZ) <= destinationRadius;
	}
	///TASK SYSTEM END

	///DIGGING SYSTEM START

	/** Handles the special digging system, used in Rampant mode due to high potential for destroyed bases**/
	public MovingObjectPosition findWaypointObstruction(){
		Vec3 bugVec = Vec3.createVectorHelper(posX, posY + getEyeHeight(), posZ);
		Vec3 waypointVec =  Vec3.createVectorHelper(taskX, taskY, taskZ);
		//incomplete forge docs my beloved
		MovingObjectPosition obstruction = worldObj.func_147447_a(bugVec, waypointVec, false, true, false);
		if(obstruction != null){
			Block blockHit = worldObj.getBlock(obstruction.blockX, obstruction.blockY, obstruction.blockZ);
			if(blockHit.getExplosionResistance(null) <= blastResToDig){
				return obstruction;
			}
		}
		return null;
	}

	public void digToWaypoint(MovingObjectPosition obstacle){

		EntityWaypoint target =  new EntityWaypoint(worldObj);
		target.setLocationAndAngles(obstacle.blockX, obstacle.blockY, obstacle.blockZ, 0 , 0);
		target.radius = 5;
		worldObj.spawnEntityInWorld(target);

		previousTask = getCurrentTask();
		previousWaypoint =  getWaypoint();

		setCurrentTask(TASK_DIG, target);

		Vec3 vec = Vec3.createVectorHelper(posX, posY, posZ);
		int maxDist = (int) (Math.sqrt(vec.squareDistanceTo(taskX, taskY, taskZ)) * 1.2);
		this.setPathToEntity(PathFinderUtils.getPathEntityToCoordPartial(worldObj, this, taskX, taskY, taskZ, maxDist, true, false, true, true));

		communicate(TASK_DIG, target);

	}
	///DIGGING END
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("armor", this.dataWatcher.getWatchableObjectByte(DW_ARMOR));
		nbt.setByte("subtype", this.dataWatcher.getWatchableObjectByte(DW_SUBTYPE));

		nbt.setBoolean("hasHome", hasHome);
		nbt.setInteger("homeX", homeX);
		nbt.setInteger("homeY", homeY);
		nbt.setInteger("homeZ", homeZ);

		nbt.setBoolean("hasWaypoint", hasWaypoint);
		nbt.setInteger("taskX", taskX);
		nbt.setInteger("taskY", taskY);
		nbt.setInteger("taskZ", taskZ);

		nbt.setInteger("task", currentTask);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(DW_ARMOR, nbt.getByte("armor"));
		this.dataWatcher.updateObject(DW_SUBTYPE, nbt.getByte("subtype"));

		this.hasHome = nbt.getBoolean("hasHome");
		this.homeX = nbt.getInteger("homeX");
		this.homeY = nbt.getInteger("homeY");
		this.homeZ = nbt.getInteger("homeZ");

		this.hasWaypoint = nbt.getBoolean("hasWaypoint");
		this.taskX = nbt.getInteger("taskX");
		this.taskY = nbt.getInteger("taskY");
		this.taskZ = nbt.getInteger("taskZ");

		this.currentTask = nbt.getInteger("task");
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
	}
}
