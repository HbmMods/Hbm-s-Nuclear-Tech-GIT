package com.hbm.entity.logic;

import com.hbm.config.MobConfig;
import com.hbm.entity.mob.glyphid.EntityGlyphid;
import com.hbm.entity.mob.glyphid.EntityGlyphidNuclear;
import com.hbm.entity.mob.glyphid.EntityGlyphidScout;
import com.hbm.main.MainRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import static com.hbm.entity.mob.glyphid.EntityGlyphid.*;

import java.util.List;

public class EntityWaypoint extends Entity {
	public EntityWaypoint(World world) {
		super(world);
		this.isImmuneToFire = true;
		this.noClip = true;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(10, 0);
		// this.dataWatcher.addObject(11, 0);

	}

	public int maxAge = 2400;
	public int radius = 3;
	public boolean highPriority = false;
	protected EntityWaypoint additional;

	public void setHighPriority() {
		highPriority = true;
	}

	public int getWaypointType() {
		return this.dataWatcher.getWatchableObjectInt(10);
	}

	public void setAdditionalWaypoint(EntityWaypoint waypoint) {
		additional = waypoint;
	}

	public void setWaypointType(int waypointType) {
		this.dataWatcher.updateObject(10, waypointType);
	}

	boolean hasSpawned = false;

	public int getColor() {
		switch(getWaypointType()) {

		case TASK_RETREAT_FOR_REINFORCEMENTS: return 0x5FA6E8;
		case TASK_BUILD_HIVE:
		case TASK_INITIATE_RETREAT: return 0x127766;
		default: return 0x566573;
		}
	}

	AxisAlignedBB bb;

	@Override
	public void onEntityUpdate() {
		if(ticksExisted >= maxAge) {
			this.setDead();
		}

		bb = AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(radius, radius, radius);

		if(!worldObj.isRemote) {

			if(ticksExisted % 40 == 0) {

				List<Entity> targets = worldObj.getEntitiesWithinAABBExcludingEntity(this, bb);

				for(Entity e : targets) {
					if(e instanceof EntityGlyphid) {

						EntityGlyphid bug = ((EntityGlyphid) e);

						if(additional != null && !hasSpawned) {
							worldObj.spawnEntityInWorld(additional);
							hasSpawned = true;
						}

						boolean exceptions = bug.getWaypoint() != this || e instanceof EntityGlyphidScout || e instanceof EntityGlyphidNuclear;

						if(!exceptions)
							bug.setCurrentTask(getWaypointType(), additional);

						if(getWaypointType() == TASK_BUILD_HIVE) {
							if(e instanceof EntityGlyphidScout)
								setDead();
						} else {
							setDead();
						}

					}
				}
			}
		} else if(MobConfig.waypointDebug) {

			double x = bb.minX + (rand.nextDouble() - 0.5) * (bb.maxX - bb.minX);
			double y = bb.minY + rand.nextDouble() * (bb.maxY - bb.minY);
			double z = bb.minZ + (rand.nextDouble() - 0.5) * (bb.maxZ - bb.minZ);

			NBTTagCompound fx = new NBTTagCompound();
			fx.setString("type", "tower");
			fx.setFloat("lift", 0.5F);
			fx.setFloat("base", 0.75F);
			fx.setFloat("max", 2F);
			fx.setInteger("life", 50 + worldObj.rand.nextInt(10));
			fx.setInteger("color", getColor());
			fx.setDouble("posX", x);
			fx.setDouble("posY", y);
			fx.setDouble("posZ", z);
			MainRegistry.proxy.effectNT(fx);
		}

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.setWaypointType(nbt.getInteger("type"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("type", getWaypointType());
	}
}
