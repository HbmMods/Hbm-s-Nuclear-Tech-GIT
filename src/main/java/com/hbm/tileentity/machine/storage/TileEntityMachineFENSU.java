package com.hbm.tileentity.machine.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.lib.Library;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IPowerNet;
import api.hbm.energy.PowerNet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineFENSU extends TileEntityMachineBattery {

	public float prevRotation = 0F;
	public float rotation = 0F;
	
	public static final long maxTransfer = 10_000_000_000_000_000L;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			long prevPower = this.power;
			
			power = Library.chargeItemsFromTE(slots, 1, power, getMaxPower());
			
			//////////////////////////////////////////////////////////////////////
			this.transmitPower();
			//////////////////////////////////////////////////////////////////////
			
			byte comp = this.getComparatorPower();
			if(comp != this.lastRedstone)
				this.markDirty();
			this.lastRedstone = comp;
			
			power = Library.chargeTEFromItems(slots, 0, power, getMaxPower());

			long avg = (power / 2 + prevPower / 2);
			this.delta = avg - this.log[0];
			
			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}
			
			this.log[19] = avg;
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setLong("power", avg);
			nbt.setLong("delta", delta);
			nbt.setShort("redLow", redLow);
			nbt.setShort("redHigh", redHigh);
			nbt.setByte("priority", (byte) this.priority.ordinal());
			this.networkPack(nbt, 20);
		}
		
		if(worldObj.isRemote) {
			this.prevRotation = this.rotation;
			this.rotation += this.getSpeed();
			
			if(rotation >= 360) {
				rotation -= 360;
				prevRotation -= 360;
			}
		}
	}
	
	@Deprecated protected void transmitPower() {
		
		short mode = (short) this.getRelevantMode();
		
		//HasSets to we don'T have any duplicates
		Set<IPowerNet> nets = new HashSet();
		Set<IEnergyConnector> consumers = new HashSet();
		
		//iterate over all sides
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			
			//if it's a cable, buffer both the network and all subscribers of the net
			if(te instanceof IEnergyConductor) {
				IEnergyConductor con = (IEnergyConductor) te;
				if(con.canConnect(dir.getOpposite()) && con.getPowerNet() != null) {
					nets.add(con.getPowerNet());
					con.getPowerNet().unsubscribe(this);
					consumers.addAll(con.getPowerNet().getSubscribers());
				}
				
			//if it's just a consumer, buffer it as a subscriber
			} else if(te instanceof IEnergyConnector) {
				IEnergyConnector con = (IEnergyConnector) te;
				if(con.canConnect(dir.getOpposite())) {
					consumers.add((IEnergyConnector) te);
				}
			}
		}

		//send power to buffered consumers, independent of nets
		if(this.power > 0 && (mode == mode_buffer || mode == mode_output)) {
			List<IEnergyConnector> con = new ArrayList();
			con.addAll(consumers);
			
			if(PowerNet.trackingInstances == null) {
				PowerNet.trackingInstances = new ArrayList();
			}
			PowerNet.trackingInstances.clear();
			
			nets.forEach(x -> {
				if(x instanceof PowerNet) PowerNet.trackingInstances.add((PowerNet) x);
			});
			
			long toSend = Math.min(this.power, maxTransfer);
			long powerRemaining = this.power - toSend;
			this.power = PowerNet.fairTransfer(con, toSend) + powerRemaining;
		}
		
		//resubscribe to buffered nets, if necessary
		if(mode == mode_buffer || mode == mode_input) {
			nets.forEach(x -> x.subscribe(this));
		}
	}

	@Override
	public long getPowerRemainingScaled(long i) {
		double powerScaled = (double)power / (double)getMaxPower();
		return (long)(i * powerScaled);
	}

	@Override
	public long getMaxPower() {
		return Long.MAX_VALUE;
	}

	@Override
	public long getTransferWeight() {
		return Math.min(Math.max(this.getMaxPower() - getPower(), 0), maxTransfer);
	}
	
	public float getSpeed() {
		return (float) Math.pow(Math.log(power * 0.75 + 1) * 0.05F, 5);
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
	
	@Override
	public long transferPower(long power) {
		
		long overshoot = 0;
		
		// if power exceeds our transfer limit, truncate
		if(power > maxTransfer) {
			overshoot += power - maxTransfer;
			power = maxTransfer;
		}
		
		// this check is in essence the same as the default implementation, but re-arranged to never overflow the int64 range
		// if the remaining power exceeds the power cap, truncate again
		long freespace = this.getMaxPower() - this.getPower();
		
		if(freespace < power) {
			overshoot += power - freespace;
			power = freespace;
		}
		
		// what remains is sure to not exceed the transfer limit and the power cap (and therefore the int64 range)
		this.setPower(this.getPower() + power);
		
		return overshoot;
	}
}
