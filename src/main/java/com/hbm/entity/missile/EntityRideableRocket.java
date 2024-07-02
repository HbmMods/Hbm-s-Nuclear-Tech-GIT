package com.hbm.entity.missile;

import com.hbm.dim.DebugTeleporter;
import com.hbm.dim.SolarSystem;
import com.hbm.handler.MissileStruct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRideableRocket extends EntityMissileCustom {
	
	private ItemStack payload;

	private RocketState state = RocketState.AWAITING;
	private static final int WATCHABLE_STATE = 13;

	private enum RocketState {
		AWAITING,		// Prepped for launch, once mounted will transition to launching
		LAUNCHING,		// Ascending through the atmosphere up to the target altitude, at which point it'll teleport to the target body
		LANDING,		// Descending onto the target location
		LANDED,			// Landed on the target, will not launch until the player activates the rocket, at which point it'll transition back to AWAITING
	}

	public EntityRideableRocket(World world) {
		super(world);
		setSize(2, 20);
	}

	public EntityRideableRocket(World world, float x, float y, float z, MissileStruct template) {
		super(world, x, y, z, (int)x, (int)z, template);
		setSize(2, 20);
	}

	public EntityRideableRocket withPayload(ItemStack stack) {
		this.payload = stack.copy();
		return this;
	}

	public void beginLandingSequence() {
		setState(RocketState.LANDING);
		posY = 500;

		setStuckIn(0);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		EntityPlayer rider = (EntityPlayer) this.riddenByEntity;
		RocketState state = getState();
		
		if(!worldObj.isRemote) {
			if(state == RocketState.AWAITING && rider != null) {
				state = RocketState.LAUNCHING;
			}

			motionX = 0;
			motionY = 0;
			motionZ = 0;

			if(state == RocketState.LAUNCHING)
				motionY = 1;

			if(state == RocketState.LANDING)
				motionY = -0.1;

			if(state == RocketState.LAUNCHING && posY > 600) {
				beginLandingSequence();

				if(rider != null && payload != null) {
					SolarSystem.Body destination = SolarSystem.Body.values()[payload.getItemDamage()];

					if(destination.getBody() != null) {
						DebugTeleporter.teleport(rider, destination.getBody().dimensionId, rider.posX, 300, rider.posZ, false);
					}
				}
			}

			rotationPitch = 0;
		}
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(super.interactFirst(player)) {
			return true;
		} else if(!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player)) {
			player.mountEntity(this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		setState(RocketState.LANDED);
	}

	@Override
	public void onImpact() {
		// no boom
	}

	@Override
	protected void spawnContrail() {
		if(getState() == RocketState.LAUNCHING || getState() == RocketState.LANDING) {
			super.spawnContrail();
		}
	}

	protected RocketState getState() {
		return RocketState.values()[this.dataWatcher.getWatchableObjectInt(WATCHABLE_STATE)];
	}

	protected void setState(RocketState state) {
		this.state = state;
		this.dataWatcher.updateObject(WATCHABLE_STATE, state.ordinal());
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		state = RocketState.AWAITING;
		this.dataWatcher.addObject(WATCHABLE_STATE, state.ordinal()); // rocket state
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setState(RocketState.values()[nbt.getInteger("state")]);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("state", state.ordinal());
	}
	
}
