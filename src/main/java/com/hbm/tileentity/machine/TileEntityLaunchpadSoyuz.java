package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.missile.EntitySoyuz;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerLaunchpadSoyuz;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUILaunchpadSoyuz;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.EnumUtil;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLaunchpadSoyuz extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiverMK2, IGUIProvider, IControlReceiver {

	public long power;
	public static final long maxPower = 1_000_000;
	public static final long CONSUMPTION = 10_000;
	public FluidTank[] tanks;

	public static final int INDEX_STRUT1	= 0; // struts 1-5 on the support tower
	public static final int INDEX_STRUT2	= 1;
	public static final int INDEX_STRUT3	= 2;
	public static final int INDEX_STRUT4	= 3;
	public static final int INDEX_STRUT5	= 4;
	public static final int INDEX_CARRIAGE	= 5; // delivery carriage
	public static final int INDEX_ROTOR		= 6; // carriage deploy progress
	public static final int INDEX_TILT		= 7; // carriage tilt after ramming the buffer stops

	public float[] positions		= new float[8];
	public float[] prevPositions	= new float[8];
	public float[] speed			= new float[8];
	public float[] target			= new float[8];
	public float[] syncPositions	= new float[8];
	
	protected int turnProgress;

	public SoyuzStatus soyuzStatus = SoyuzStatus.ABSENT;
	public ComponentStatus strutStatus = ComponentStatus.RETRACT;
	public ComponentStatus carriageStatus = ComponentStatus.RETRACT;
	public ComponentStatus rotorStatus = ComponentStatus.RETRACT;
	
	public boolean cargoMode = false;
	public int loadedType = -1;
	public int fuelCountdown = 0;
	public static final int FUEL_DURATION = 15 * 20;

	public static final int COUNTDOWN_DURATION = 600;
	public int countdown;
	
	public float getInterpPos(int index, float interp) {
		return prevPositions[index] + (positions[index] - prevPositions[index]) * interp;
	}

	public TileEntityLaunchpadSoyuz() {
		super(27);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.KEROSENE_REFORM, 128_000);
		tanks[1] = new FluidTank(Fluids.OXYGEN, 128_000);
	}

	@Override
	public String getName() {
		return "container.launchpadSoyuz";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 8, power, maxPower);
			
			tanks[0].loadTank(4, 5, slots);
			tanks[1].loadTank(6, 7, slots);
			
			if(!hasRocketLoaded()) {
				
				boolean isMoving = false;
				for(int i = 0; i < this.positions.length; i++) {
					if(!this.finishedMoving(i)) {
						isMoving = true;
						break;
					}
				}
				
				// if all parts are currently idle, set the status back to absent and prepare new loading procedure
				if(!isMoving) this.soyuzStatus = soyuzStatus.ABSENT;
				
				this.loadedType = -1;
			} else {
				this.loadedType = this.slots[0].getItemDamage();
			}
			
			if(this.power >= CONSUMPTION) {
				this.updateStates();
				this.move();
				this.power -= CONSUMPTION;
			}
			
			this.networkPackNT(300);
			
		} else {
			
			for(int i = 0; i < this.positions.length; i++) {

				this.prevPositions[i] = this.positions[i];

				if(this.turnProgress > 0) {
					this.positions[i] = this.positions[i] + ((this.syncPositions[i] - this.positions[i]) / (float) this.turnProgress);
					--this.turnProgress;
				} else {
					this.positions[i] = this.syncPositions[i];
				}
			}
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.blockMetadata - 10);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			
			double x = xCoord + 0.5 - dir.offsetX * 4 - rot.offsetX;
			double z = zCoord + 0.5 - dir.offsetZ * 4 - rot.offsetZ * 4;
			
			if((this.soyuzStatus == SoyuzStatus.FUELING || this.soyuzStatus == SoyuzStatus.LAUNCHING) && this.hasOxidizer()) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 0F);
				data.setFloat("base", 0.5F);
				data.setFloat("max", 2F);
				data.setInteger("life", 70 + worldObj.rand.nextInt(30));
				data.setDouble("posX", x + worldObj.rand.nextGaussian() * 0.75);
				data.setDouble("posZ", z + worldObj.rand.nextGaussian() * 0.75);
				data.setDouble("posY", yCoord + 4);
				data.setBoolean("noWind", true);
				data.setFloat("alphaMod", 2F);
				data.setFloat("strafe", 0.075F);
				for(int i = 0; i < 3; i++) MainRegistry.proxy.effectNT(data);
			}
			
			List<EntitySoyuz> entities = worldObj.getEntitiesWithinAABB(EntitySoyuz.class, AxisAlignedBB.getBoundingBox(x - 1, yCoord + 4, z - 1, x + 1, yCoord + 14, z + 1));
			
			if(!entities.isEmpty()) {
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "smoke");
				data.setString("mode", "shockRand");
				data.setInteger("count", 50);
				data.setDouble("strength", worldObj.rand.nextGaussian() * 3 + 6);
				data.setDouble("posX", x);
				data.setDouble("posY", yCoord + 1);
				data.setDouble("posZ", z);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}
	
	public void updateStates() {
		
		if(this.soyuzStatus == SoyuzStatus.ABSENT) {
			
			/// RETURN BEHAVIOR ///
			
			// retract all struts
			if(this.strutStatus == ComponentStatus.DEPLOY) {
				this.strutStatus = ComponentStatus.RETRACT;
				
				for(int i = 0; i <= INDEX_STRUT5; i++) {
					setTarget(i, false, 60 + worldObj.rand.nextInt(21)); // 3-4 seconds
				}
			}
			
			// first send away the carriage
			if(this.carriageStatus == ComponentStatus.DEPLOY) {
				this.carriageStatus = ComponentStatus.RETRACT;
				setTarget(INDEX_CARRIAGE, false, 100); // 5 seconds
			}
			
			// once carriage has stopped, tilt, then retract rotor
			if(this.carriageStatus == ComponentStatus.RETRACT && this.finishedMoving(INDEX_CARRIAGE)) {
				// if the carriage has hit the buffer stops, tilt
				if(wasMoving(INDEX_CARRIAGE)) setTarget(INDEX_TILT, true, 3);
				
				// after tilt has returned
				if(this.target[INDEX_TILT] == 0 && this.rotorStatus == ComponentStatus.DEPLOY) {
					this.rotorStatus = ComponentStatus.RETRACT;
					setTarget(INDEX_ROTOR, false, 100); // 5 seconds
				}
			}

			// return tilt
			if(this.target[INDEX_TILT] > 0 && this.finishedMoving(INDEX_TILT)) {
				setTarget(INDEX_TILT, false, 3);
			}
			
			/// DEPLOY BEHAVIOR ///
			
			// start if rocket is loaded and all relevant components are fully retracted
			if(this.hasRocketLoaded() &&
					this.carriageStatus == ComponentStatus.RETRACT && this.finishedMoving(INDEX_CARRIAGE) &&
					this.rotorStatus == ComponentStatus.RETRACT && this.finishedMoving(INDEX_ROTOR)) {
				
				// deploy carriage
				this.carriageStatus = ComponentStatus.DEPLOY;
				setTarget(INDEX_CARRIAGE, true, 200); // 10 seconds

				this.soyuzStatus = SoyuzStatus.LOADING;
				return; // always return on status change
			}
		}
		
		if(this.soyuzStatus == SoyuzStatus.LOADING) {
			
			// once carriage has returned, start rotor and lift the rocket onto the launch pad
			if(this.rotorStatus == ComponentStatus.RETRACT && this.finishedMoving(INDEX_CARRIAGE)) {
				this.rotorStatus = ComponentStatus.DEPLOY;
				setTarget(INDEX_ROTOR, true, 200); // 10 seconds
			}
			
			if(this.carriageStatus == ComponentStatus.DEPLOY && this.finishedMoving(INDEX_CARRIAGE) &&
					this.rotorStatus == ComponentStatus.DEPLOY && this.finishedMoving(INDEX_ROTOR)) {

				// once carriage and rotor are deployed, extend struts
				if(this.strutStatus == ComponentStatus.RETRACT) {
					this.strutStatus = ComponentStatus.DEPLOY;
					
					for(int i = 0; i <= INDEX_STRUT5; i++) {
						setTarget(i, true, 60 + worldObj.rand.nextInt(21)); // 3-4 seconds
					}
				} else {

					// if struts are fully deployed, the rocket is in place, start fueling
					boolean strutsDeployed = true;
					for(int i = 0; i <= INDEX_STRUT5; i++) {
						if(!this.finishedMoving(i)) strutsDeployed = false;
					}
					
					if(strutsDeployed) {
						this.fuelCountdown = FUEL_DURATION;
						this.soyuzStatus = SoyuzStatus.FUELING;
						return; // always return on status change
					}
				}
			}
		}
		
		if(this.soyuzStatus == SoyuzStatus.FUELING) {
			
			if(this.hasAllFuel()) {
				if(this.fuelCountdown > 0) {
					this.fuelCountdown--;
				} else {
					this.soyuzStatus = SoyuzStatus.READY;
					return; // always return on status change
				}
			}
		}
		
		if(this.soyuzStatus == SoyuzStatus.READY) {
			
			// should the fuel somehow not be present during this phase, reset phase back to fueling
			if(!this.hasAllFuel()) {
				this.fuelCountdown = FUEL_DURATION;
				this.soyuzStatus = SoyuzStatus.FUELING;
				return; // always return on status change
			}
		}
		
		if(this.soyuzStatus == SoyuzStatus.LAUNCHING) {
			
			// return carriage
			if(this.carriageStatus == ComponentStatus.DEPLOY) {
				this.carriageStatus = ComponentStatus.RETRACT;
				this.setTarget(INDEX_CARRIAGE, false, 100); // 5 seconds
				
			// return rotor
			}
			
			if(this.carriageStatus == ComponentStatus.RETRACT && this.finishedMoving(INDEX_CARRIAGE)) {
				if(wasMoving(INDEX_CARRIAGE)) setTarget(INDEX_TILT, true, 3);
				
				if(this.target[INDEX_TILT] == 0 && this.rotorStatus == ComponentStatus.DEPLOY) {
					this.rotorStatus = ComponentStatus.RETRACT;
					setTarget(INDEX_ROTOR, false, 100); // 5 seconds
				}
			}

			if(this.target[INDEX_TILT] > 0 && this.finishedMoving(INDEX_TILT)) {
				setTarget(INDEX_TILT, false, 3);
			}
			
			if(this.countdown == 80) {
				for(int i = 0; i <= INDEX_STRUT5; i++) {
					setTarget(i, false, 60 + worldObj.rand.nextInt(21)); // 3-4 seconds
				}
			}
			
			if(this.countdown > 0) {
				this.countdown--;
				
				if(countdown % 100 == 0 && countdown > 0) worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:alarm.hatch", 100F, 1.1F);
				
			} else {
				
				if(canLaunch()) {
					this.soyuzStatus = SoyuzStatus.ABSENT;
					this.liftOff();
				} else {
					this.soyuzStatus = SoyuzStatus.READY;
				}
			}
		}
	}
	
	public boolean hasRocketLoaded() { return slots[0] != null && slots[0].getItem() == ModItems.missile_soyuz; }
	public boolean finishedMoving(int index) { return this.positions[index] == this.target[index]; }
	public boolean wasMoving(int index) { return this.positions[index] != this.prevPositions[index]; }
	
	public boolean hasAllFuel() {
		return hasJetFuel() && hasOxidizer();
	}

	public boolean hasJetFuel() { return this.tanks[0].getFill() >= 100_000; }
	public boolean hasOxidizer() { return this.tanks[1].getFill() >= 100_000; }
	
	public void setTarget(int index, boolean deploy, int duration) {
		this.target[index] = deploy ? 1F : 0F;
		this.speed[index] = 1F / duration;
	}
	
	public void move() {
		
		for(int i = 0; i < this.positions.length; i++) {
			
			this.prevPositions[i] = this.positions[i];
			
			if(Math.abs(this.positions[i] - this.target[i]) <= this.speed[i]) {
				this.positions[i] = this.target[i];
			} else if(this.positions[i] < this.target[i]) {
				this.positions[i] += this.speed[i];
			} else {
				this.positions[i] -= this.speed[i];
			}
		}
	}
	
	public boolean canLaunch() {
		
		// prerequisites for all modes
		if(this.loadedType < 0) return false;
		if(!this.hasAllFuel()) return false;
		if(this.power < this.CONSUMPTION) return false;
		
		// at least one cargo slot must be occupied
		if(this.cargoMode) {
			for(int i = 9; i < 27; i++) {
				if(slots[i] != null) return true;
			}
			
			return false;
			
		// checks for satellite and optional orbital module
		} else {
			
			if(this.orbital() == 1) return false;
			if(slots[2] == null) return false;
			
			return true;
		}
	}
	
	public void liftOff() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.blockMetadata - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		double x = xCoord + 0.5 - dir.offsetX * 4 - rot.offsetX * 4;
		double y = yCoord + 4;
		double z = zCoord + 0.5 - dir.offsetZ * 4 - rot.offsetZ * 4;
		
		EntitySoyuz soyuz = new EntitySoyuz(worldObj);
		soyuz.setSkin(this.loadedType);
		soyuz.mode = this.cargoMode ? 1 : 0;
		soyuz.setLocationAndAngles(x, y, z, 0, 0);
		worldObj.spawnEntityInWorld(soyuz);

		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:entity.soyuzTakeoff", 100F, 1.1F);

		tanks[0].setFill(tanks[0].getFill() - 100_000);
		tanks[1].setFill(tanks[1].getFill() - 100_000);
		
		if(!this.cargoMode) {
			soyuz.setSat(slots[2]);
			if(this.orbital() == 2) slots[3] = null;
			slots[2] = null;
			
		} else {
			
			List<ItemStack> payload = new ArrayList();
			for(int i = 9; i < 27; i++) { payload.add(slots[i]); slots[i] = null; }

			soyuz.targetX = slots[1].stackTagCompound.getInteger("xCoord");
			soyuz.targetZ = slots[1].stackTagCompound.getInteger("zCoord");
			soyuz.setPayload(payload);
		}
		
		slots[0] = null;
	}
	
	/** Returns 0 if no orbital module is required, 1 if it is and it's missing and 2 if the orbital module is required and loaded */
	public int orbital() {
		if(this.cargoMode) return 0;
		
		if(slots[2] != null && (slots[2].getItem() == ModItems.sat_gerald || slots[2].getItemDamage() == EnumSatType.MINER_LUNAR.ordinal())) {
			if(slots[3] != null && slots[3].getItem() == ModItems.missile_soyuz_lander) return 2;
			return 1;
		}
		return 0;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return stack.getItem() == ModItems.missile_soyuz;
		if(slot == 1) return stack.getItem() instanceof IDesignatorItem;
		if(slot == 2) return stack.getItem() instanceof ISatChip && !cargoMode;
		if(slot == 3) return stack.getItem() == ModItems.missile_soyuz_lander && !cargoMode;
		if(slot > 8) {
			if(!cargoMode) return false;
			// only allow items not compatible with slots 0-3
			for(int i = 0; i <= 3; i++) if(isItemValidForSlot(i, stack)) return false;
			return true;
		}
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {
				0, 2, 3,	// soyuz, satellite, orbital module
				9, 10, 11,	// cargo
				12, 13, 14,
				15, 16, 17,
				18, 19, 20,
				21, 22, 23,
				24, 25, 26
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		buf.writeLong(power);
		buf.writeInt(loadedType);
		buf.writeBoolean(cargoMode);
		buf.writeInt(countdown);
		buf.writeByte((byte) this.soyuzStatus.ordinal());
		
		for(int i = 0; i < this.positions.length; i++) {
			buf.writeFloat(this.positions[i]);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		this.power = buf.readLong();
		this.loadedType = buf.readInt();
		this.cargoMode = buf.readBoolean();
		this.countdown = buf.readInt();
		this.soyuzStatus = EnumUtil.grabEnumSafely(SoyuzStatus.class, buf.readByte());

		for(int i = 0; i < this.positions.length; i++) {
			float newSync = buf.readFloat();
			if(this.syncPositions[i] != newSync) {
				this.syncPositions[i] = newSync; 
				this.turnProgress = 2;
			}
		}
	}

	@Override public long getPower() { return this.power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getReceivingTanks() { return tanks; }
	@Override public FluidTank[] getAllTanks() { return tanks; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerLaunchpadSoyuz(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUILaunchpadSoyuz(player.inventory, this); }
	
	public static enum SoyuzStatus {
		ABSENT,		// no rocket is present, return all components to null position
		LOADING,	// rocket is moved to launch pad
		FUELING,	// rocket is on the launch pad, cooldown is active
		READY,		// rocket is ready to launch
		LAUNCHING	// countdown is active
	}
	
	public static enum ComponentStatus {
		DEPLOY, RETRACT
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override public double getUseRange() { return 50D; }
	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("cargo")) {
			this.cargoMode = data.getBoolean("cargo");
			this.markChanged();
		}
		
		if(data.hasKey("launch")) {
			if(this.soyuzStatus == SoyuzStatus.READY) {
				this.soyuzStatus = SoyuzStatus.LAUNCHING;
				this.countdown = this.COUNTDOWN_DURATION;
				this.markChanged();
			}
		}
	}
}
