package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.DebugTeleporter;
import com.hbm.dim.SolarSystem;
import com.hbm.handler.MissileStruct;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
import com.hbm.items.ItemVOTVdrive.Destination;
import com.hbm.render.util.MissileMultipart;
import com.hbm.render.util.MissilePart;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class EntityRideableRocket extends EntityMissileBaseNT implements ILookOverlay {

	protected float fuel;
	protected float consumption;

	public ItemStack navDrive;

	private int stateTimer = 0;

	// temp, replace with VAB parts later
	private static final int WATCHABLE_WARHEAD = 9;
	private static final int WATCHABLE_FUSELAGE = 10;
	private static final int WATCHABLE_FINS = 11;
	private static final int WATCHABLE_THRUSTER = 12;

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
		super(world, x, y, z, (int) x, (int) z);
		this.itemData = itemData;

		this.dataWatcher.updateObject(WATCHABLE_WARHEAD, Item.getIdFromItem(template.warhead));
		this.dataWatcher.updateObject(WATCHABLE_FUSELAGE, Item.getIdFromItem(template.fuselage));
		this.dataWatcher.updateObject(WATCHABLE_THRUSTER, Item.getIdFromItem(template.thruster));
		if(template.fins != null) {
			this.dataWatcher.updateObject(WATCHABLE_FINS, Item.getIdFromItem(template.fins));
		} else {
			this.dataWatcher.updateObject(WATCHABLE_FINS, Integer.valueOf(0));
		}
		
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

		motionX = 0;
		motionY = 0;
		motionZ = 0;

		setStuckIn(0);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!sizeSet) {
			MissileMultipart missile = new MissileMultipart();
			missile.warhead = MissilePart.getPart(Item.getItemById(dataWatcher.getWatchableObjectInt(WATCHABLE_WARHEAD)));
			missile.fuselage = MissilePart.getPart(Item.getItemById(dataWatcher.getWatchableObjectInt(WATCHABLE_FUSELAGE)));
			missile.thruster = MissilePart.getPart(Item.getItemById(dataWatcher.getWatchableObjectInt(WATCHABLE_THRUSTER)));
			
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

				rotationPitch = MathHelper.clamp_float((stateTimer - 60) * 0.3F, 0.0F, 45.0F);
			} else if(state == RocketState.LANDING) {
				double targetHeight = (double)worldObj.getHeightValue((int)posX, (int)posZ);
				rocketVelocity = MathHelper.clamp_double((targetHeight - posY) * 0.005, -0.5, -0.005);
				rotationPitch = 0;

				if(navDrive != null && navDrive.getItem() instanceof ItemVOTVdrive) {
					Destination destination = ((ItemVOTVdrive)navDrive.getItem()).getDestination(navDrive);
					posX = destination.x;
					posZ = destination.z;
				}
			} else {
				rocketVelocity = 0;
				rotationPitch = 0;
			}

			if(state == RocketState.LAUNCHING) {
				Vec3 motion = BobMathUtil.getDirectionFromAxisAngle(rotationPitch - 90.0F, 180.0F - rotationYaw, rocketVelocity);
				motionX = motion.xCoord;
				motionY = motion.yCoord;
				motionZ = motion.zCoord;
			} else {
				motionX = 0;
				motionY = rocketVelocity;
				motionZ = 0;
			}

			if(state == RocketState.LAUNCHING && posY > 900) {
				beginLandingSequence();

				if(rider != null && navDrive != null && navDrive.getItem() instanceof ItemVOTVdrive) {
					Destination destination = ((ItemVOTVdrive)navDrive.getItem()).getDestination(navDrive);

					if(destination.body != SolarSystem.Body.BLANK) {
						DebugTeleporter.teleport(rider, destination.body.getDimensionId(), destination.x, 800, destination.z, false);
					}
				}
			}

			if(navDrive != null && navDrive.getItem() instanceof ItemVOTVdrive) {
				ItemVOTVdrive drive = (ItemVOTVdrive) navDrive.getItem();
				setDestinationName(drive.getDestination(navDrive).body.name);
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
		if(getState() == RocketState.LAUNCHING)
			return;

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
		this.dataWatcher.addObject(8, Integer.valueOf(this.health));
		this.dataWatcher.addObject(9, Integer.valueOf(0));
		this.dataWatcher.addObject(10, Integer.valueOf(0));
		this.dataWatcher.addObject(11, Integer.valueOf(0));
		this.dataWatcher.addObject(12, Integer.valueOf(0));
		this.dataWatcher.addObject(WATCHABLE_STATE, RocketState.AWAITING.ordinal());
		this.dataWatcher.addObject(WATCHABLE_DESTINATION, "NO DRIVE PRESENT");
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		fuel = nbt.getFloat("fuel");
		consumption = nbt.getFloat("consumption");
		this.dataWatcher.updateObject(9, nbt.getInteger("warhead"));
		this.dataWatcher.updateObject(10, nbt.getInteger("fuselage"));
		this.dataWatcher.updateObject(11, nbt.getInteger("fins"));
		this.dataWatcher.updateObject(12, nbt.getInteger("thruster"));

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

		nbt.setFloat("fuel", fuel);
		nbt.setFloat("consumption", consumption);
		nbt.setInteger("warhead", this.dataWatcher.getWatchableObjectInt(WATCHABLE_WARHEAD));
		nbt.setInteger("fuselage", this.dataWatcher.getWatchableObjectInt(WATCHABLE_FUSELAGE));
		nbt.setInteger("fins", this.dataWatcher.getWatchableObjectInt(WATCHABLE_FINS));
		nbt.setInteger("thruster", this.dataWatcher.getWatchableObjectInt(WATCHABLE_THRUSTER));

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

	@Override
	public ItemStack getMissileItemForInfo() {
		return new ItemStack(ModItems.missile_custom);
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_steel, 8));
		list.add(new ItemStack(ModItems.thruster_medium, 2));
		list.add(new ItemStack(ModItems.canister_empty, 1));
		list.add(new ItemStack(Blocks.glass_pane, 2));

		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.missile_generic);
	}

}
