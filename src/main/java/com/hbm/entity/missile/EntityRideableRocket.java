package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.DebugTeleporter;
import com.hbm.dim.SolarSystem;
import com.hbm.handler.MissileStruct;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
import com.hbm.render.util.MissileMultipart;
import com.hbm.render.util.MissilePart;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class EntityRideableRocket extends EntityMissileCustom implements ILookOverlay {

	public ItemStack navDrive;

	private int stateTimer = 0;

	private static final int WATCHABLE_STATE = 13;
	private static final int WATCHABLE_DESTINATION = 14;

	private NBTTagCompound itemData;

	private double rocketVelocity = 0;

	private boolean sizeSet = false;

	public enum RocketState {
		AWAITING,		// Prepped for launch, once mounted will transition to launching
		LAUNCHING,		// Ascending through the atmosphere up to the target altitude, at which point it'll teleport to the target body
		LANDING,		// Descending onto the target location
		LANDED,			// Landed on the target, will not launch until the player activates the rocket, at which point it'll transition back to AWAITING
	}

	public EntityRideableRocket(World world) {
		super(world);
	}

	public EntityRideableRocket(World world, float x, float y, float z, MissileStruct template, NBTTagCompound itemData) {
		super(world, x, y, z, (int) x, (int) z, template);
		this.itemData = itemData;
		
		MissileMultipart missile = new MissileMultipart();
		missile.warhead = MissilePart.getPart(template.warhead);
		missile.fuselage = MissilePart.getPart(template.fuselage);
		missile.thruster = MissilePart.getPart(template.thruster);

		setSize(2, (float)missile.getHeight() + 1);
	}

	public EntityRideableRocket withPayload(ItemStack stack) {
		this.navDrive = stack.copy();
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

		if(!sizeSet) {
			MissileMultipart missile = new MissileMultipart();
			missile.warhead = MissilePart.getPart(Item.getItemById(dataWatcher.getWatchableObjectInt(9)));
			missile.fuselage = MissilePart.getPart(Item.getItemById(dataWatcher.getWatchableObjectInt(10)));
			missile.thruster = MissilePart.getPart(Item.getItemById(dataWatcher.getWatchableObjectInt(12)));
			
			setSize(2, (float)missile.getHeight() + 1);
		}

		EntityPlayer rider = (EntityPlayer) this.riddenByEntity;
		RocketState state = getState();

		if(!worldObj.isRemote) {
			if(state == RocketState.AWAITING && rider != null && rider.isJumping) {
				setState(RocketState.LAUNCHING);
			}

			if(state == RocketState.LAUNCHING) {
				if(rocketVelocity < 4)
					rocketVelocity += MathHelper.clamp_double(stateTimer / 120D * 0.05D, 0, 0.05);

				rotationPitch = Math.min(stateTimer * 0.4F, 60.0F);
			} else if(state == RocketState.LANDING) {
				double targetHeight = (double)worldObj.getHeightValue((int)posX, (int)posZ) + 1;
				rocketVelocity = MathHelper.clamp_double((targetHeight - posY) * 0.005, -0.15, -0.005);
				rotationPitch = 0;
			} else {
				rocketVelocity = 0;
				rotationPitch = 0;
			}

			if(state == RocketState.LAUNCHING) {
				Vec3 motion = BobMathUtil.getDirectionFromAxisAngle(rotationPitch - 90.0F, 180.0F - rotationYaw, rocketVelocity);
				motionX = MathHelper.clamp_double(motion.xCoord, -1, 1);
				motionY = rocketVelocity;
				motionZ = MathHelper.clamp_double(motion.zCoord, -1, 1);
			} else {
				motionX = 0;
				motionY = rocketVelocity;
				motionZ = 0;
			}

			if(state == RocketState.LAUNCHING && posY > 600) {
				beginLandingSequence();

				if(rider != null && navDrive != null) {
					SolarSystem.Body destination = SolarSystem.Body.values()[navDrive.getItemDamage()];

					if(destination.getBody() != null) {
						DebugTeleporter.teleport(rider, destination.getBody().dimensionId, 0, 300, 0, false);
					}
				}
			}

			if(navDrive != null && navDrive.getItem() instanceof ItemVOTVdrive) {
				ItemVOTVdrive drive = (ItemVOTVdrive) navDrive.getItem();
				setDestinationName(drive.getDestination(navDrive).name);
			} else {
				setDestinationName("NO DRIVE PRESENT");
			}
		}

		stateTimer++;
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
    public void updateRiderPosition() {
        if (this.riddenByEntity == null) return;

		double length = getMountedYOffset() + riddenByEntity.getYOffset();
		Vec3 target = BobMathUtil.getDirectionFromAxisAngle(rotationPitch - 90.0F, 180.0F - rotationYaw, length);

		riddenByEntity.setPosition(posX + target.xCoord, posY + target.yCoord, posZ + target.zCoord);
    }

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		setState(RocketState.LANDED);

		posY = (double)worldObj.getHeightValue((int)posX, (int)posZ);

		motionX = 0;
		motionY = 0;
		motionZ = 0;
	}

	@Override
	public double getMountedYOffset() {
		return height - 3.0;
	}
	
	@Override
    protected void setSize(float width, float height) {
		super.setSize(width, height);
		sizeSet = true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(!worldObj.isRemote && !isDead) {
			if(isEntityInvulnerable()) {
				return false;
			} else if(riddenByEntity == null) {
				dropNDie(source);
			}

			return true;
		} else {
			return true;
		}
	}

	public void dropNDie(DamageSource p_94095_1_) {
		setDead();

		// Drop the rocket itself, to be taken to a pad and refueled
		ItemStack itemstack = new ItemStack(ModItems.missile_custom, 1);
		itemstack.stackTagCompound = itemData;
		entityDropItem(itemstack, 0.0F);

		// Drop the drive if it is still present
		if(navDrive != null) {
			entityDropItem(navDrive, 0.0F);
		}
	}

	@Override
	public void onImpact() {
		// no boom
	}

	@Override
	protected void spawnContrail() {
		if(getState() == RocketState.LAUNCHING) {
			super.spawnContrail();
		}
	}

	public RocketState getState() {
		return RocketState.values()[this.dataWatcher.getWatchableObjectInt(WATCHABLE_STATE)];
	}

	public void setState(RocketState state) {
		this.dataWatcher.updateObject(WATCHABLE_STATE, state.ordinal());
		stateTimer = 0;
	}

	public String getDestinationName() {
		return this.dataWatcher.getWatchableObjectString(WATCHABLE_DESTINATION);
	}

	public void setDestinationName(String destination) {
		this.dataWatcher.updateObject(WATCHABLE_DESTINATION, destination);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(WATCHABLE_STATE, RocketState.AWAITING.ordinal());
		this.dataWatcher.addObject(WATCHABLE_DESTINATION, "NO DRIVE PRESENT");
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setState(RocketState.values()[nbt.getInteger("state")]);
		itemData = nbt.getCompoundTag("item");

		if(nbt.hasKey("drive")) {
			navDrive = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("drive"));
		} else {
			navDrive = null;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("state", getState().ordinal());
		nbt.setTag("item", itemData);

		if(navDrive != null) {
			NBTTagCompound driveData = new NBTTagCompound();
			navDrive.writeToNBT(driveData);
	
			nbt.setTag("drive", driveData);
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		List<String> text = new ArrayList<>();

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if(riddenByEntity == null) {
			text.add("Interact to enter");
		} else if(riddenByEntity != player) {
			text.add("OCCUPIED");
		} else {
			text.add("Destination: " + getDestinationName());

			if(getState() == RocketState.AWAITING) {
				text.add("JUMP TO LAUNCH");
			}
		}

		ILookOverlay.printGeneric(event, "rokemt", 0xffff00, 0x404000, text);
	}

}
