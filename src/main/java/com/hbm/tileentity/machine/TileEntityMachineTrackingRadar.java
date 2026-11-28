package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerTrackingRadar;
import com.hbm.inventory.gui.GUITrackingRadar;
import com.hbm.lib.Library;
import com.hbm.module.portmanager.ModulePortManPower;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.SatelliteBase;
import com.hbm.saveddata.satellites.SatelliteDetector;
import com.hbm.saveddata.satellites.SatelliteRayScan;
import com.hbm.saveddata.satellites.SatelliteDetector.BurstIntensity;
import com.hbm.saveddata.satellites.SatelliteRayScan.RayEvent;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.tileentity.TilePortShapes;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Compat;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityMachineTrackingRadar extends TileEntityMachineBase implements IEnergyReceiverMK2, IControlReceiver, IGUIProvider, IRORValueProvider, IRORInteractive {

	public float rot = INACTIVE_ROT;
	public float prevRot = INACTIVE_ROT;
	public float lift = INACTIVE_LIFT;
	public float prevLift = INACTIVE_LIFT;

	public static final float SPEED = 0.25F;
	public static final float ACTIVE_ROT = -15F;
	public static final float ACTIVE_LIFT = -45F;
	public static final float INACTIVE_ROT = 0F;
	public static final float INACTIVE_LIFT = -85F;
	
	public boolean wasActive;
	
	public long power;
	public static final long maxPower = 100_000_000;
	public double progress;
	public static final long CONSUMPTION = 1_000_000;
	
	public int mode = -1;
	public int dataStream;
	public int lastSecondaryTimer;
	public int lastTertiaryTimer;
	public BlockPos lastSecondary;
	public BlockPos lastTertiary;
	
	/** Integer array of four, [SAT FREQ/TYPE] - X - Y - Z */
	public List<int[]> knownSats = new ArrayList();
	
	protected ModulePortManPower portman;
	
	public TileEntityMachineTrackingRadar() {
		super(1);
		portman = new ModulePortManPower(this).onlyInput();
	}

	@Override
	public String getName() {
		return "container.machineTrackingRadar";
	}

	protected PortDef[] cachedPorts;
	public PortDef[] getPorts() { if(cachedPorts == null) cachedPorts = TilePortShapes.refinery(xCoord, yCoord, zCoord); return cachedPorts; }

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			this.portman.update(getPorts());
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			if(mode < 0) {
				this.wasActive = false;
				
			} else {
				
				if(this.power < CONSUMPTION || worldObj.getHeightValue(xCoord, zCoord) > yCoord) {
					this.wasActive = false;
				} else {
					this.wasActive = true;
					this.power -= CONSUMPTION;
					
					if(worldObj.getTotalWorldTime() % 20 == 0) {
						SatelliteRayScan.reportEvent(worldObj, xCoord, yCoord, zCoord, RayEvent.INFO_RADIO, 300);
						SatelliteDetector.reportEvent(worldObj, 60, BurstIntensity.MEDIUM, xCoord, zCoord);
					}
				}
				
				if(mode == 0) {
					
					double speed = this.getProcessingSpeed();
					if(speed > 0) {
						this.progress += speed;
						
						if(progress >= 1D) {
							progress -= 1D;
							this.discoverNewSat();
						}
					}
					
				} else {
					
					if(worldObj.getTotalWorldTime() % 20 == 0) {
						this.dataStream = worldObj.rand.nextInt();
					}
				}
			}

			if(lastSecondaryTimer > 0) lastSecondaryTimer--;
			if(lastTertiaryTimer > 0) lastTertiaryTimer--;
			
			if(!this.muffled && this.wasActive && (worldObj.getTotalWorldTime() + xCoord + zCoord) % 60 == 0) {
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.sonarPing", 2.0F, 1.25F);
			}
			
			this.networkPackNT(100);
			
		} else {
			
			this.prevRot = this.rot;
			this.prevLift = this.lift;

			float targetR = this.wasActive ? ACTIVE_ROT : INACTIVE_ROT;
			float targetL = this.wasActive ? ACTIVE_LIFT : INACTIVE_LIFT;

			if(Math.abs(rot - targetR) <= SPEED) rot = targetR;
			else if(rot < targetR) rot += SPEED;
			else if(rot > targetR) rot -= SPEED;

			if(Math.abs(lift - targetL) <= SPEED) lift = targetL;
			else if(lift < targetL) lift += SPEED;
			else if(lift > targetL) lift -= SPEED;
		}
	}
	
	public double getProcessingSpeed() {
		if(lastSecondaryTimer <= 0 || lastTertiaryTimer <= 0) return 0D;
		if(lastSecondary == null || lastTertiary == null) return 0D;

		double p2s = Math.sqrt((xCoord - lastSecondary.getX()) * (xCoord - lastSecondary.getX()) + (zCoord - lastSecondary.getZ()) * (zCoord - lastSecondary.getZ()));
		double p2t = Math.sqrt((xCoord - lastTertiary.getX()) * (xCoord - lastTertiary.getX()) + (zCoord - lastTertiary.getZ()) * (zCoord - lastTertiary.getZ()));
		double s2t = Math.sqrt((lastSecondary.getX() - lastTertiary.getX()) * (lastSecondary.getX() - lastTertiary.getX()) + (lastSecondary.getZ() - lastTertiary.getZ()) * (lastSecondary.getZ() - lastTertiary.getZ()));
		
		double min = BobMathUtil.min(p2s, p2t, s2t);
		double maxSpeed = 1D / 300D;
		
		// max speed once we reach 1,000m
		return Math.min(min * maxSpeed / 1_000D, maxSpeed);
	}
	
	public void discoverNewSat() {
		
		SatelliteSavedData dat = SatelliteSavedData.getData(worldObj);
		List<Entry<Integer, SatelliteBase>> sats = new ArrayList(dat.sats.entrySet());
		
		if(sats.isEmpty()) return;
		
		Entry<Integer, SatelliteBase> randEntry = sats.get(worldObj.rand.nextInt(sats.size()));
		SatelliteBase sat = randEntry.getValue();
		
		if(!sat.isTrackable()) return;
		
		for(int[] known : this.knownSats) {
			int id = known[0];
			if(id == randEntry.getKey()) return; // if we already have this satellite, skip
		}
		this.knownSats.add(new int[] {randEntry.getKey(), sat.posX, sat.posY, sat.posZ});
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeByte((byte) mode);
		buf.writeFloat((float) progress); // client doesn't need as much precision
		buf.writeBoolean(wasActive);
		
		SatelliteSavedData dat = SatelliteSavedData.getData(worldObj);
		
		buf.writeInt(knownSats.size());
		for(int i = 0; i < knownSats.size(); i++) {
			int[] values = knownSats.get(i);
			SatelliteBase sat = dat.sats.get(values[0]);
			if(sat == null) continue;
			buf.writeInt(sat.getID()); // convert frequency to ID for the icon
			buf.writeInt(values[1]);
			buf.writeInt(values[2]);
			buf.writeInt(values[3]);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.mode = buf.readByte();
		this.progress = buf.readFloat();
		this.wasActive = buf.readBoolean();
		
		int sats = buf.readInt();
		knownSats.clear();
		for(int i = 0; i < sats; i++) {
			knownSats.add(new int[] {buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt()});
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return this.isBattery(stack);
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		portman.destroy();
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		portman.destroy();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.mode = nbt.getInteger("mode");
		this.dataStream = nbt.getInteger("dataStream");
		this.lastSecondaryTimer = nbt.getInteger("lastSecondaryTimer");
		this.lastTertiaryTimer = nbt.getInteger("lastTertiaryTimer");
		this.lastSecondary = BlockPos.fromArray(nbt.getIntArray("lastSecondary"));
		this.lastTertiary = BlockPos.fromArray(nbt.getIntArray("lastTertiary"));
		
		this.knownSats.clear();
		int satCount = nbt.getInteger("satCount");
		
		for(int i = 0; i < satCount; i++) {
			int[] sat = nbt.getIntArray("s" + i);
			if(sat != null && sat.length == 4) knownSats.add(sat);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setInteger("mode", mode);
		nbt.setInteger("dataStream", dataStream);
		nbt.setInteger("lastSecondaryTimer", lastSecondaryTimer);
		nbt.setInteger("lastTertiaryTimer", lastTertiaryTimer);
		nbt.setIntArray("lastSecondary", lastSecondary.toArray());
		nbt.setIntArray("lastTertiary", lastTertiary.toArray());
		
		nbt.setInteger("satCount", knownSats.size());
		for(int i = 0; i < knownSats.size(); i++) {
			nbt.setIntArray("s" + i, knownSats.get(i));
		}
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerTrackingRadar(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUITrackingRadar(player.inventory, this); }


	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {
		
		if(data.hasKey("b")) {
			int mode = MathHelper.clamp_int(data.getByte("b"), 0, 2);
			if(this.mode == mode) {
				this.mode = -1;
			} else {
				this.mode = mode;
			}
			this.markChanged();
		}
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord,
					zCoord - 5,
					xCoord + 6,
					yCoord + 16,
					zCoord + 6
			);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_VALUE + "datastream",
				PREFIX_VALUE + "detectioncount",
				PREFIX_FUNCTION + "sec" + NAME_SEPARATOR + "x" + PARAM_SEPARATOR + "y" + PARAM_SEPARATOR + "z" + PARAM_SEPARATOR + "data",
				PREFIX_FUNCTION + "tert" + NAME_SEPARATOR + "x" + PARAM_SEPARATOR + "y" + PARAM_SEPARATOR + "z" + PARAM_SEPARATOR + "data",
		};
	}

	@Override
	public String provideRORValue(String name) {
		if(this.mode > 0) {
			if((PREFIX_VALUE + "datastream").equals(name)) return (mode == 1 ? "sec" : "tert") + NAME_SEPARATOR + this.xCoord + PARAM_SEPARATOR + this.yCoord + PARAM_SEPARATOR + this.zCoord + PARAM_SEPARATOR + this.dataStream;
		}
		if(mode == 0) {
			if((PREFIX_VALUE + "detectioncount").equals(name)) return "" + this.knownSats.size();
		}
		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {

		if(name.equals(PREFIX_FUNCTION + "sec") && params.length == 4) {
			BlockPos pos = isDataValid(params, 1);
			if(pos != null) {
				this.lastSecondary = pos;
				this.lastSecondaryTimer = 20;
			}
		}

		if(name.equals(PREFIX_FUNCTION + "tert") && params.length == 4) {
			BlockPos pos = isDataValid(params, 2);
			if(pos != null) {
				this.lastTertiary = pos;
				this.lastTertiaryTimer = 20;
			}
		}
		
		return null;
	}
	
	protected BlockPos isDataValid(String[] params, int requiredMode) {
		int x = IRORInteractive.parseInt(params[0]);
		int y = IRORInteractive.parseInt(params[1]);
		int z = IRORInteractive.parseInt(params[2]);
		int d = IRORInteractive.parseInt(params[3]);
		TileEntity tile = Compat.getTileStandard(worldObj, x, y, z);
		if(tile instanceof TileEntityMachineTrackingRadar) {
			TileEntityMachineTrackingRadar radar = (TileEntityMachineTrackingRadar) tile;
			if(radar.dataStream == d && radar.mode == requiredMode && radar.wasActive) {
				return new BlockPos(x, y, z);
			}
		}
		return null;
	}
}
