package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.DebugTeleporter;
import com.hbm.dim.SolarSystem;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.RocketStruct;
import com.hbm.handler.RocketStruct.RocketStage;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
import com.hbm.items.ItemVOTVdrive.Destination;
import com.hbm.items.weapon.ItemCustomRocket;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.ParticleUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class EntityRideableRocket extends EntityMissileBaseNT implements ILookOverlay {

	public ItemStack navDrive;

	public EntityRideableRocketDummy capDummy;

	private int stateTimer = 0;

	private static final int WATCHABLE_STATE = 8;
	private static final int WATCHABLE_DESTINATION = 9;
	private static final int WATCHABLE_TIMER = 10;

	private static final int WATCHABLE_ROCKET = 11; // Variable size, must always be last!

	private double rocketVelocity = 0;

	private boolean sizeSet = false;

	private AudioWrapper audio;

	private RocketState lastState = RocketState.AWAITING;

	private boolean willExplode = false;

	public enum RocketState {
		AWAITING,		// Prepped for launch, once mounted will transition to launching
		LAUNCHING,		// Ascending through the atmosphere up to the target altitude, at which point it'll teleport to the target body
		LANDING,		// Descending onto the target location
		LANDED,			// Landed on the target, will not launch until the player activates the rocket, at which point it'll transition back to AWAITING
		TIPPING,		// tipping culture is a burden on modern society
	}

	public EntityRideableRocket(World world) {
		super(world);
		setSize(2, 8);
		sizeSet = false;
	}

	public EntityRideableRocket(World world, float x, float y, float z, ItemStack stack) {
		super(world, x, y, z, (int) x, (int) z);
		RocketStruct rocket = ItemCustomRocket.get(stack);

		setRocket(rocket);
		setSize(2, (float)rocket.getHeight() + 1);
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

		RocketStruct rocket = getRocket();
		rocket.stages.remove(0);
		setRocket(rocket);

		setSize(2, (float)rocket.getHeight() + 1);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!sizeSet) {
			setSize(2, (float)getRocket().getHeight() + 1);
		}

		EntityPlayer rider = (EntityPlayer) this.riddenByEntity;
		RocketState state = getState();

		if(!worldObj.isRemote) {
			if(state == RocketState.AWAITING && rider != null && rider.isJumping) {
				CelestialBody localBody = CelestialBody.getBody(worldObj);
				CelestialBody destination = getDestination();

				// Check if the stage can make the journey
				if(destination != null && destination != localBody) {
					if(getRocket().hasSufficientFuel(localBody, destination)) {
						setState(RocketState.LAUNCHING);
					}
				}
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
					Destination destination = ItemVOTVdrive.getDestination(navDrive);
					posX = destination.x;
					posZ = destination.z;
				}
			} else if(state == RocketState.TIPPING) {
				float tipTime = (float)stateTimer * 0.1F;
				rotationPitch = tipTime * tipTime;

				if(rotationPitch > 90) {
					rotationPitch = 90;

					if(willExplode) {
						dropNDie(null);
						ExplosionLarge.explode(worldObj, posX, posY, posZ, 5, true, false, true);
						ExplosionLarge.spawnShrapnelShower(worldObj, posX, posY, posZ, motionX, motionY, motionZ, 15, 0.075);

						worldObj.playSoundEffect(posX, posY, posZ, "hbm:entity.pipefail", 10_000, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);
					}
				}

				rocketVelocity = 0;
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
					Destination destination = ItemVOTVdrive.getDestination(navDrive);

					if(destination.body != SolarSystem.Body.BLANK) {
						DebugTeleporter.teleport(rider, destination.body.getDimensionId(), destination.x, 800, destination.z, false);
					}
				}
			}

			if(state == RocketState.LANDING && worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)).getMaterial() == Material.water) {
				setState(RocketState.TIPPING);
			}

			if(navDrive != null && navDrive.getItem() instanceof ItemVOTVdrive) {
				setDestination(ItemVOTVdrive.getDestination(navDrive).body.getBody());
			} else {
				setDestination(null);
			}

			if(height > 8) {
				double offset = height - 4;
				if(capDummy == null || capDummy.isDead) {
					capDummy = new EntityRideableRocketDummy(worldObj, this);
					capDummy.parent = this;
					capDummy.setPosition(posX, posY + offset, posZ);
					worldObj.spawnEntityInWorld(capDummy);
				} else {
					capDummy.setPosition(posX, posY + offset, posZ);
				}
			} else if(capDummy != null) {
				capDummy.setDead();
				capDummy = null;
			}
		} else {
			// ON state transitions
			if(state != lastState) {
				if(state == RocketState.LAUNCHING) {
					AudioWrapper ignition = MainRegistry.proxy.getLoopedSound("hbm:entity.rocketIgnition", (float)posX, (float)posY, (float)posZ, 1.0F, 250.0F, 1.0F, 5);
					ignition.setDoesRepeat(false);
					ignition.startSound();
				}

				lastState = state;
			} else {
				// We can't start audio loops at the same time as playing a sound, for some reason
				if(state == RocketState.LAUNCHING || (state == RocketState.LANDING && motionY > -0.4)) {
					if(audio == null || !audio.isPlaying()) {
						String rocketAudio = getRocket().stages.size() <= 1 ? "hbm:entity.rocketFlyLight" : "hbm:entity.rocketFlyHeavy";
						audio = MainRegistry.proxy.getLoopedSound(rocketAudio, (float)posX, (float)posY, (float)posZ, 1.0F, 250.0F, 1.0F, 5);
						audio.startSound();
					}
	
					audio.updatePosition((float)posX, (float)posY, (float)posZ);
					audio.keepAlive();
				} else {
					if(audio != null) {
						audio.stopSound();
						audio = null;
					}
				}
			}
		}

		setStateTimer(++stateTimer);
	}

	@Override
	protected double motionMult() {
		RocketState state = getState();
		if(state == RocketState.AWAITING || state == RocketState.LANDED) return 0;
		return 4;
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
		if(getState() != RocketState.LANDING)
			return;

		// Check for a landing gear, if we don't have one, topple over catastrophically
		RocketStruct rocket = getRocket();
		if(rocket.stages.size() > 0 && rocket.stages.get(0).fins == null) {
			setState(RocketState.TIPPING);
			willExplode = true;
		} else {
			setState(RocketState.LANDED);
		}

		posY = (double)worldObj.getHeightValue((int)posX, (int)posZ);

		motionX = 0;
		motionY = 0;
		motionZ = 0;
	}

	@Override
	public void onImpact() {
		// no boom
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
			} else if(riddenByEntity == null && source.getEntity() instanceof EntityPlayer) {
				// A pickaxe is required to break, unless it's just the capsule (or it has tipped over)
				if(getRocket().stages.size() == 0 || getState() == RocketState.TIPPING) {
					dropNDie(source);
				} else {
					ItemStack stack = ((EntityPlayer) source.getEntity()).getHeldItem();
					if(stack != null && stack.getItem().canHarvestBlock(Blocks.stone, stack)) {
						dropNDie(source);
					}
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public void dropNDie(DamageSource source) {
		setDead();

		// Drop the rocket itself, to be taken to a pad and refueled
		// unless it's just the capsule
		RocketStruct rocket = getRocket();
		if(rocket.stages.size() == 0) {
			ItemStack stack = new ItemStack(rocket.capsule.part);
			entityDropItem(stack, 0.0F);
		} else {
			ItemStack stack = ItemCustomRocket.build(rocket);
			entityDropItem(stack, 0.0F);
		}

		// Drop the drive if it is still present
		if(navDrive != null) {
			entityDropItem(navDrive, 0.0F);
		}
	}

	@Override
	public void setDead() {
		super.setDead();
		if(capDummy != null) {
			capDummy.setDead();
		}
	}

	@Override
	protected void spawnContrail() {
		RocketState state = getState();

		if(state == RocketState.AWAITING || state == RocketState.LANDED || (state == RocketState.LANDING && motionY <= -0.4)) return;

		RocketStruct rocket = getRocket();
		if(rocket.stages.size() == 0) {
			if(state == RocketState.TIPPING) return;

			double r = rocket.capsule.part.bottom.radius * 0.5;
			ParticleUtil.spawnGasFlame(worldObj, posX + r, posY, posZ + r, 0.25, -0.75, 0.25);
			ParticleUtil.spawnGasFlame(worldObj, posX - r, posY, posZ + r, -0.25, -0.75, 0.25);
			ParticleUtil.spawnGasFlame(worldObj, posX + r, posY, posZ - r, 0.25, -0.75, -0.25);
			ParticleUtil.spawnGasFlame(worldObj, posX - r, posY, posZ - r, -0.25, -0.75, -0.25);

			double groundHeight = (double)worldObj.getHeightValue((int)posX, (int)posZ);
			double distanceToGround = posY - groundHeight;
			if(distanceToGround < 10) {
				ExplosionLarge.spawnShock(worldObj, posX, groundHeight + 0.5, posZ, 1 + worldObj.rand.nextInt(3), 1 + worldObj.rand.nextGaussian());
			}

			return;
		}

		RocketStage stage = rocket.stages.get(0);

		// the fuck is a contraol bob
		if(state == RocketState.LANDING) {
			ParticleUtil.spawnGasFlame(worldObj, posX, posY, posZ, 0.0, -1.0, 0.0);

			double groundHeight = (double)worldObj.getHeightValue((int)posX, (int)posZ);
			double distanceToGround = posY - groundHeight;
			if(distanceToGround < 10) {
				ExplosionLarge.spawnShock(worldObj, posX, groundHeight + 0.5, posZ, 1 + worldObj.rand.nextInt(3), 1 + worldObj.rand.nextGaussian());
			}
		} else if(state == RocketState.LAUNCHING || getStateTimer() < 200) {
			spawnContraolWithOffset(0, 0, 0);

			int cluster = stage.getCluster();
			for(int c = 1; c < cluster; c++) {
				float spin = (float)c / (float)(cluster - 1);
				double ox = Math.cos(spin * Math.PI * 2) * stage.fuselage.part.bottom.radius;
				double oz = Math.sin(spin * Math.PI * 2) * stage.fuselage.part.bottom.radius;
				spawnContraolWithOffset(ox, 0, oz);
			}
		}
	}

	public RocketStruct getRocket() {
		return RocketStruct.readFromDataWatcher(dataWatcher, WATCHABLE_ROCKET);
	}

	public void setRocket(RocketStruct rocket) {
		rocket.writeToDataWatcher(dataWatcher, WATCHABLE_ROCKET);
	}

	public RocketState getState() {
		return RocketState.values()[dataWatcher.getWatchableObjectInt(WATCHABLE_STATE)];
	}

	public void setState(RocketState state) {
		dataWatcher.updateObject(WATCHABLE_STATE, state.ordinal());
		stateTimer = 0;
	}

	public CelestialBody getDestination() {
		int dimensionId = dataWatcher.getWatchableObjectInt(WATCHABLE_DESTINATION);
		if(dimensionId < 0) return null;
		return CelestialBody.getBody(dimensionId);
	}

	public void setDestination(CelestialBody destination) {
		dataWatcher.updateObject(WATCHABLE_DESTINATION, destination != null ? destination.dimensionId : -1);
	}

	public int getStateTimer() {
		return dataWatcher.getWatchableObjectInt(WATCHABLE_TIMER);
	}

	public void setStateTimer(int timer) {
		dataWatcher.updateObject(WATCHABLE_TIMER, timer);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(WATCHABLE_STATE, RocketState.AWAITING.ordinal());
		dataWatcher.addObject(WATCHABLE_DESTINATION, 0);
		dataWatcher.addObject(WATCHABLE_TIMER, 0);
		RocketStruct.setupDataWatcher(dataWatcher, WATCHABLE_ROCKET); // again, this MUST be the highest int!
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		setStateTimer(nbt.getInteger("timer"));
		setState(RocketState.values()[nbt.getInteger("state")]);

		setRocket(RocketStruct.readFromNBT(nbt.getCompoundTag("rocket")));

		if(nbt.hasKey("drive")) {
			navDrive = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("drive"));
		} else {
			navDrive = null;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);

		nbt.setInteger("timer", getStateTimer());
		nbt.setInteger("state", getState().ordinal());

		NBTTagCompound rocketTag = new NBTTagCompound();
		getRocket().writeToNBT(rocketTag);
		nbt.setTag("rocket", rocketTag);

		if(navDrive != null) {
			NBTTagCompound driveData = new NBTTagCompound();
			navDrive.writeToNBT(driveData);
	
			nbt.setTag("drive", driveData);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		RocketState state = getState();
		if(state == RocketState.LAUNCHING || state == RocketState.LANDING || state == RocketState.TIPPING)
			return;

		RocketStruct rocket = getRocket();
		if(rocket.stages.size() == 0) return;

		List<String> text = new ArrayList<>();

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		CelestialBody localBody = CelestialBody.getBody(world);
		CelestialBody destination = getDestination();

		boolean canLaunch = state == RocketState.AWAITING;

		// Check if the stage can make the journey
		if(destination != null && destination != localBody) {
			if(!rocket.hasSufficientFuel(localBody, destination)) {
				text.add(EnumChatFormatting.RED + "Rocket can't reach destination!");
				canLaunch = false;
			}
		}

		if(riddenByEntity == null) {
			text.add("Interact to enter");
		} else if(riddenByEntity != player) {
			text.add("OCCUPIED");
		} else {
			if(destination != null) {
				text.add("Destination: " + I18nUtil.resolveKey("body." + destination.name));
			} else {
				text.add("Invalid destination!");
			}

			if(canLaunch) {
				text.add("JUMP TO LAUNCH");
			} else if(state == RocketState.LANDED) {
				text.add("Insert next drive to continue");
			}

			ItemStack stack = player.getHeldItem();
			if(stack != null && stack.getItem() instanceof ItemVOTVdrive) {
				if(ItemVOTVdrive.getProcessed(stack)) {
					text.add("Interact to swap drive");
				}
			}
		}

		ILookOverlay.printGeneric(event, "Rocket", 0xffff00, 0x404000, text);
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public ItemStack getMissileItemForInfo() {
		return new ItemStack(ModItems.rocket_custom);
	}

	@Override
	public List<ItemStack> getDebris() {
		return null;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return null;
	}

	// Don't chunkload rockets, they are only useful in the presence of players anyway
	@Override public void init(Ticket ticket) { }
	@Override public void loadNeighboringChunks(int newChunkX, int newChunkZ) { }
	@Override public void clearChunkLoader() { }

	public static class EntityRideableRocketDummy extends Entity implements ILookOverlay {

		public EntityRideableRocket parent;

		private static final int WATCHABLE_PARENT_ID = 3;

		public EntityRideableRocketDummy(World world) {
			super(world);
			setSize(4, 4);
		}

		public EntityRideableRocketDummy(World world, EntityRideableRocket parent) {
			this(world);
			this.parent = parent;
			dataWatcher.updateObject(WATCHABLE_PARENT_ID, parent.getEntityId());
		}

		@Override
		protected void entityInit() {
			dataWatcher.addObject(WATCHABLE_PARENT_ID, 0);
		}

		@Override
		public void onUpdate() {
			if(!worldObj.isRemote) {
				if(parent == null || parent.isDead) {
					setDead();
				}
			} else {
				if(parent == null) {
					Entity entity = worldObj.getEntityByID(dataWatcher.getWatchableObjectInt(WATCHABLE_PARENT_ID));
					if(entity != null && entity instanceof EntityRideableRocket) {
						parent = (EntityRideableRocket) entity;
					}
				}
			}
		}

		@Override protected void writeEntityToNBT(NBTTagCompound nbt) {}
		@Override public boolean writeToNBTOptional(NBTTagCompound nbt) { return false; }
		@Override public void readEntityFromNBT(NBTTagCompound nbt) { this.setDead(); }

		@Override
		public void printHook(Pre event, World world, int x, int y, int z) {
			if(parent == null) return;
			parent.printHook(event, world, x, y, z);
		}

		@Override
		public boolean interactFirst(EntityPlayer player) {
			if(parent == null) return false;
			return parent.interactFirst(player);
		}

		@Override
		public boolean canBeCollidedWith() {
			return true;
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if(parent == null) return false;
			return parent.attackEntityFrom(source, amount);
		}
		
	}

}
