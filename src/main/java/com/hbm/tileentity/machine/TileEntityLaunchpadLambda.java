package com.hbm.tileentity.machine;

import com.hbm.entity.missile.EntityRocketLambda;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerLaunchpadLambda;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUILaunchpadLambda;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.util.Vec3NT;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLaunchpadLambda extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiverMK2, IGUIProvider, IControlReceiver {

	public long power;
	public static final long maxPower = 1_000_000;
	public static final long CONSUMPTION = 10_000;
	public FluidTank[] tanks;

	public static final int INDEX_DOORS		= 0;
	public static final int INDEX_ERECTOR	= 1;
	public static final int INDEX_ROTOR		= 2;
	public static final int INDEX_CLAMPS	= 3;
	public static final int INDEX_PISTONS	= 4;

	public float[] positions		= new float[5];
	public float[] prevPositions	= new float[5];
	public float[] speed			= new float[5];
	public float[] target			= new float[5];
	public float[] syncPositions	= new float[5];
	
	protected int turnProgress;
	
	/** True if the rocket has been placed on the silo door, detaches the rendering from the erector */
	public boolean erected = false;
	/** True if the erector is moving up, placing the rocket on the silo door */
	public boolean erecting = false;
	
	// yeah fuck it i'm not gonna make a state for every single thing that moves so here's a counter with ambiguous and unexplained values
	public int animationProgress = 0;
	public int animationDelay = 0;
	
	public boolean autolaunch = false;
	
	private AudioWrapper[] audios;

	public static final int COUNTDOWN_DURATION = 200;
	public int countdown;

	public TileEntityLaunchpadLambda() {
		super(7);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.GASOLINE_LEADED, 64_000);
		tanks[1] = new FluidTank(Fluids.PEROXIDE, 64_000);
		
		this.audios = new AudioWrapper[4];
	}

	protected PortDef[] cachedPorts;

	public PortDef[] getPorts() {
		if(cachedPorts == null) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			
			cachedPorts = new PortDef[] {
					PortDef.make(xCoord + dir.offsetX * 7 - rot.offsetX * 2, yCoord + 1, zCoord + dir.offsetZ * 7 - rot.offsetZ * 2, dir),
					PortDef.make(xCoord + dir.offsetX * 7 - rot.offsetX * 3, yCoord + 1, zCoord + dir.offsetZ * 7 - rot.offsetZ * 3, dir),
					PortDef.make(xCoord + dir.offsetX * 7 - rot.offsetX * 4, yCoord + 1, zCoord + dir.offsetZ * 7 - rot.offsetZ * 4, dir),
					PortDef.make(xCoord + dir.offsetX * 7 - rot.offsetX * 5, yCoord + 1, zCoord + dir.offsetZ * 7 - rot.offsetZ * 5, dir),
					PortDef.make(xCoord + dir.offsetX * 7 - rot.offsetX * 6, yCoord + 1, zCoord + dir.offsetZ * 7 - rot.offsetZ * 6, dir),
			};
		}
		return cachedPorts;
	}

	@Override
	public String getName() {
		return "container.launchpadLambda";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return stack.getItem() == ModItems.missile_lambda;
		if(slot == 1) return stack.getItem() instanceof ISatChip && !TileEntityLaunchpadSoyuz.needsOrbiter(stack);
		if(slot == 2) return this.isFluidContainer(stack, tanks[0]);
		if(slot == 3) return false;
		if(slot == 4) return this.isFluidContainer(stack, tanks[1]);
		if(slot == 5) return false;
		if(slot == 6) return this.isBattery(stack);
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {
			0, 1,	// lambda, satellite
		};
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.setupAllPorts(getPorts());
			this.updatePortPIFIFO();
			
			this.power = Library.chargeTEFromItems(slots, 6, power, maxPower);
			
			tanks[0].loadTank(2, 3, slots);
			tanks[1].loadTank(4, 5, slots);
			
			if(!this.hasRocketLoaded()) {
				this.erected = false;
				this.erecting = false;
				this.countdown = 0;
			}
			
			if(this.power >= CONSUMPTION) {
				this.updateStates();
				this.move();
				this.power -= CONSUMPTION;
				
				if(this.autolaunch && this.erected && this.canLaunch() && this.countdown <= 0) {
					this.countdown = this.COUNTDOWN_DURATION;
				}
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
			
			if(this.erected && this.hasOxidizer() && MainRegistry.proxy.me().getDistanceSq(xCoord + 0.5, yCoord + 2, zCoord + 0.5) <= 100 * 100) {
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 0F);
				data.setFloat("base", 0.5F);
				data.setFloat("max", 2F);
				data.setInteger("life", 70 + worldObj.rand.nextInt(30));
				data.setDouble("posX", xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25);
				data.setDouble("posZ", zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25);
				data.setDouble("posY", yCoord + 2);
				data.setBoolean("noWind", true);
				data.setFloat("alphaMod", 2F);
				data.setFloat("strafe", 0.075F);
				for(int i = 0; i < 3; i++) MainRegistry.proxy.effectNT(data);
			}

			handleSound(0, this.syncPositions[INDEX_DOORS] > 0F && this.syncPositions[INDEX_DOORS] < 3F);
			handleSound(1, !this.finishedMoving(INDEX_ERECTOR) && this.wasMoving(INDEX_ERECTOR));
			handleSound(2, this.syncPositions[INDEX_ROTOR] > 0F && this.syncPositions[INDEX_ROTOR] < 180F);
			handleSound(3, this.countdown > 0);
			
			if(this.wasMoving(INDEX_CLAMPS) && this.syncPositions[INDEX_CLAMPS] == 90F)
				MainRegistry.proxy.playSoundClient(xCoord + 0.5F, yCoord + 12, zCoord + 0.5F, "hbm:door.sliding_seal_stop", 25F, 0.75F);
		}
	}
	
	protected void handleSound(int index, boolean isRunning) {
		
		if(isRunning) {
			
			if(this.audios[index] == null) {
				this.audios[index] = createSound(index);
				this.audios[index].startSound();
				
			} else if(!this.audios[index].isPlaying()){
				this.audios[index].stopSound();
				this.audios[index] = createSound(index);
				this.audios[index].startSound();
			}

			Vec3NT pos = getSoundPosition(index);
			this.audios[index].keepAlive();
			this.audios[index].updatePosition((float) pos.xCoord, (float) pos.yCoord, (float) pos.zCoord);
			
		} else {
			
			if(this.audios[index] != null) {
				this.audios[index].stopSound();
				this.audios[index] = null;

				Vec3NT pos = getSoundPosition(index);
				if(index == 0) MainRegistry.proxy.playSoundClient((float) pos.xCoord, (float) pos.yCoord, (float) pos.zCoord, "hbm:door.garage_stop", 35F, 1F);
				if(index == 1 && this.target[INDEX_ERECTOR] == 25F) MainRegistry.proxy.playSoundClient((float) pos.xCoord, (float) pos.yCoord, (float) pos.zCoord, "hbm:door.wgh_big_stop", 50F, 0.5F);
				if(index == 1 && this.target[INDEX_ERECTOR] != 25F) MainRegistry.proxy.playSoundClient((float) pos.xCoord, (float) pos.yCoord, (float) pos.zCoord, "hbm:door.wgh_stop", 25F, 1F);
				if(index == 2) MainRegistry.proxy.playSoundClient((float) pos.xCoord, (float) pos.yCoord, (float) pos.zCoord, "hbm:door.wgh_big_stop", 25F, 0.75F);
			}
		}
	}
	
	protected AudioWrapper createSound(int index) {
		
		Vec3NT pos = getSoundPosition(index);
		
		if(index == 0) return MainRegistry.proxy.getLoopedSound("hbm:door.garage_move", (float) pos.xCoord, (float) pos.yCoord, (float) pos.zCoord, 2.0F, 35F, 1.0F, 10);
		if(index == 1) return MainRegistry.proxy.getLoopedSound("hbm:door.wgh_start", (float) pos.xCoord, (float) pos.yCoord, (float) pos.zCoord, 2.0F, 35F, 1.0F, 10);
		if(index == 2) return MainRegistry.proxy.getLoopedSound("hbm:door.wgh_big_start", (float) pos.xCoord, (float) pos.yCoord, (float) pos.zCoord, 2.0F, 35F, 0.75F, 10);
		if(index == 3) return MainRegistry.proxy.getLoopedSound("hbm:alarm.regularSiren", (float) pos.xCoord, (float) pos.yCoord, (float) pos.zCoord, 10F, 50F, 1F, 20);
		
		return null;
	}
	
	protected Vec3NT getSoundPosition(int index) {

		double x = xCoord + 0.5;
		double y = yCoord + 3;
		double z = zCoord + 0.5;
		
		if(index == 2) {
			y += 12;
		}
		
		return new Vec3NT(x, y, z);
	}
	
	public void updateStates() {
		
		// rocket is being erected
		if(finishedAllMoving() && this.erecting && !this.erected) {
			
			if(this.erectorDown() && this.doorsClosed()) {
				this.setTarget(INDEX_DOORS, 3F, 3F, 60);
				this.setTarget(INDEX_PISTONS, 0F, 1F, 1);
				this.setTarget(INDEX_CLAMPS, 0F, 1F, 1);
				this.setTarget(INDEX_ROTOR, 180F, 180F, 1);
			} else if(this.erectorDown() && this.doorsOpen()) {
				this.animationDelay = 20;
				this.setTarget(INDEX_ERECTOR, 27F, 27F, 100);
			} else if(this.erectorUp() && this.rotorDown()) {
				this.animationDelay = 20;
				this.setTarget(INDEX_ROTOR, 0F, 180F, 60);
				this.setTarget(INDEX_DOORS, 0F, 3F, 60);
			} else if(this.erectorUp() && this.rotorUp() && this.doorsClosed()) {
				this.animationDelay = 20;
				this.setTarget(INDEX_ERECTOR, 25F, 2F, 60);
			} else if(this.erectorCenter() && this.rotorUp() && this.doorsClosed()) {
				this.erected = true;
				this.markChanged();
			} else {
				// oops, we've gone off-script, just run everything back
				this.erecting = false;
				this.markChanged();
			}
		}
		
		// return erector when countdown hits T-1
		if(this.erected && this.countdown <= 20 && this.countdown > 0) {
			this.erecting = false;
			this.markChanged();
		}
		
		// return erector post launch
		if(finishedAllMoving() && !this.erecting) {
			
			// keep the fucking doors closed
			this.setTarget(INDEX_DOORS, 0F, 3F, 60);
			
			if(this.clampsOn()) {
				this.setTarget(INDEX_PISTONS, 0.75F, 0.75F, 20);
			} else if(this.clampsOff() && this.clampsDown()) {
				this.animationDelay = 10;
				this.setTarget(INDEX_CLAMPS, 90F, 90F, 40);
			} else if(this.clampsUp() && !this.erectorDown()) {
				this.animationDelay = 20;
				this.setTarget(INDEX_ERECTOR, 0F, 25F, 100);
			} else if(this.erectorDown() && this.doorsClosed()) {
				
				if(this.hasRocketLoaded()) {
					this.erecting = true;
					this.markChanged();
				}
			}
		}
		
		if(this.erected && this.countdown > 0) {
			this.countdown--;
			
			if(this.countdown <= 0) {
				this.markChanged();
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:entity.soyuzTakeoff", 100F, 1.1F);
				this.liftOff();
			}
		}
	}
	
	public void liftOff() {
		
		double x = xCoord + 0.5;
		double y = yCoord + 2;
		double z = zCoord + 0.5;
		
		EntityRocketLambda soyuz = new EntityRocketLambda(worldObj);
		soyuz.setLocationAndAngles(x, y, z, 0, 0);
		worldObj.spawnEntityInWorld(soyuz);

		tanks[0].setFill(tanks[0].getFill() - 24_000);
		tanks[1].setFill(tanks[1].getFill() - 24_000);
		
		soyuz.setSat(slots[1]);
		
		slots[0] = null;
		slots[1] = null;
		
		this.erected = false;
		
		this.markChanged();
	}

	// please kill me
	public boolean doorsClosed() { return this.positions[INDEX_DOORS] == 0F; }
	public boolean doorsOpen() { return this.positions[INDEX_DOORS] == 3F; }
	public boolean erectorDown() { return this.positions[INDEX_ERECTOR] == 0F; }
	public boolean erectorCenter() { return this.positions[INDEX_ERECTOR] == 25F; }
	public boolean erectorUp() { return this.positions[INDEX_ERECTOR] == 27F; }
	public boolean rotorUp() { return this.positions[INDEX_ROTOR] == 0F; }
	public boolean rotorDown() { return this.positions[INDEX_ROTOR] == 180F; }
	public boolean clampsOn() { return this.positions[INDEX_PISTONS] == 0F; }
	public boolean clampsOff() { return this.positions[INDEX_PISTONS] == 0.75F; }
	public boolean clampsUp() { return this.positions[INDEX_CLAMPS] == 90F; }
	public boolean clampsDown() { return this.positions[INDEX_CLAMPS] == 0F; }

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		buf.writeLong(power);
		buf.writeBoolean(erecting);
		buf.writeBoolean(erected);
		buf.writeBoolean(autolaunch);
		buf.writeInt(countdown);

		for(int i = 0; i < this.target.length; i++) buf.writeFloat(this.target[i]);
		for(int i = 0; i < this.positions.length; i++) buf.writeFloat(this.positions[i]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		this.power = buf.readLong();
		this.erecting = buf.readBoolean();
		this.erected = buf.readBoolean();
		this.autolaunch = buf.readBoolean();
		this.countdown = buf.readInt();

		for(int i = 0; i < this.target.length; i++) this.target[i] = buf.readFloat();
		for(int i = 0; i < this.positions.length; i++) {
			float newSync = buf.readFloat();
			if(this.syncPositions[i] != newSync) {
				this.syncPositions[i] = newSync;
				this.turnProgress = 3;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(!nbt.hasKey("power")) return;
		this.power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "tank0");
		tanks[1].readFromNBT(nbt, "tank1");
		for(int i = 0; i < 5; i++) {
			positions[i] = nbt.getFloat("p" + i);
			speed[i] = nbt.getFloat("s" + i);
			target[i] = nbt.getFloat("t" + i);
		}
		erected = nbt.getBoolean("erected");
		erecting = nbt.getBoolean("erecting");
		animationProgress = nbt.getInteger("animationProgress");
		animationDelay = nbt.getInteger("animationDelay");
		autolaunch = nbt.getBoolean("autolaunch");
		countdown = nbt.getInteger("countdown");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "tank0");
		tanks[1].writeToNBT(nbt, "tank1");
		for(int i = 0; i < 5; i++) {
			nbt.setFloat("p" + i, positions[i]);
			nbt.setFloat("s" + i, speed[i]);
			nbt.setFloat("t" + i, target[i]);
		}
		nbt.setBoolean("erected", erected);
		nbt.setBoolean("erecting", erecting);
		nbt.setInteger("animationProgress", animationProgress);
		nbt.setInteger("animationDelay", animationDelay);
		nbt.setBoolean("autolaunch", autolaunch);
		nbt.setInteger("countdown", countdown);
	}
	
	public void setTarget(int index, float target, float span, int duration) {
		if(span <= 0) span = 1F;
		this.target[index] = target;
		this.speed[index] = span / duration;
	}
	
	public void move() {
		
		if(this.animationDelay > 0) this.animationDelay--;
		
		for(int i = 0; i < this.positions.length; i++) {
			
			this.prevPositions[i] = this.positions[i];
			if(this.animationDelay > 0) continue;
			
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
		if(!this.hasRocketLoaded()) return false;
		if(!this.hasAllFuel()) return false;
		if(this.power < this.CONSUMPTION) return false;
		if(slots[1] == null) return false;
		if(!this.isItemValidForSlot(1, slots[1])) return false;
		if(TileEntityLaunchpadSoyuz.needsOrbiter(slots[1])) return false;
		
		return true;
	}

	public boolean hasRocketLoaded() { return slots[0] != null && slots[0].getItem() == ModItems.missile_lambda; }
	public boolean finishedMoving(int index) { return this.positions[index] == this.target[index]; }
	public boolean wasMoving(int index) { return this.positions[index] != this.prevPositions[index]; }
	
	public boolean finishedAllMoving() {
		for(int i = 0; i < this.positions.length; i++) if(!finishedMoving(i)) return false;
		return true;
	}
	
	public float getInterpPos(int index, float interp) {
		return prevPositions[index] + (positions[index] - prevPositions[index]) * interp;
	}
	
	public boolean hasAllFuel() {
		return hasJetFuel() && hasOxidizer();
	}

	public boolean hasJetFuel() { return this.tanks[0].getFill() >= 24_000; }
	public boolean hasOxidizer() { return this.tanks[1].getFill() >= 24_000; }

	@Override public long getPower() { return this.power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getReceivingTanks() { return tanks; }
	@Override public FluidTank[] getAllTanks() { return tanks; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerLaunchpadLambda(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUILaunchpadLambda(player.inventory, this); }

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 7,
					yCoord,
					zCoord - 7,
					xCoord + 8,
					yCoord + 30,
					zCoord + 8
					);
		}

		return bb;
	}
	
	@Override public double getUseRange() { return 24D; }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {
		
		if(data.hasKey("auto")) {
			this.autolaunch = data.getBoolean("auto");
			this.markChanged();
		}
		
		if(data.hasKey("launch")) {
			
			if(canLaunch()) {
				this.countdown = this.COUNTDOWN_DURATION;
			}
		}
	}
}
