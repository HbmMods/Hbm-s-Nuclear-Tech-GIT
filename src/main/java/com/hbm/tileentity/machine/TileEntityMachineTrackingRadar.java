package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerTrackingRadar;
import com.hbm.inventory.gui.GUITrackingRadar;
import com.hbm.lib.Library;
import com.hbm.module.portmanager.ModulePortManPower;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.SatelliteBase;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.tileentity.TilePortShapes;

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
	
	public long power;
	public static final long maxPower = 100_000_000;
	public double progress;
	
	public int mode = -1;
	
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
			
			this.networkPackNT(100);
			
		} else {
			
			boolean isOn = true;

			this.prevRot = this.rot;
			this.prevLift = this.lift;

			float targetR = isOn ? ACTIVE_ROT : INACTIVE_ROT;
			float targetL = isOn ? ACTIVE_LIFT : INACTIVE_LIFT;

			if(Math.abs(rot - targetR) <= SPEED) rot = targetR;
			else if(rot < targetR) rot += SPEED;
			else if(rot > targetR) rot -= SPEED;

			if(Math.abs(lift - targetL) <= SPEED) lift = targetL;
			else if(lift < targetL) lift += SPEED;
			else if(lift > targetL) lift -= SPEED;
		}
	}
	
	public void discoverNewSat() {
		
		SatelliteSavedData dat = SatelliteSavedData.getData(worldObj);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeByte((byte) mode);
		buf.writeFloat((float) progress); // client doesn't need as much precision
		
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
		this.mode = buf.readByte();
		this.progress = buf.readFloat();
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
			
		};
	}

	@Override
	public String provideRORValue(String name) {
		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		return null;
	}
}
