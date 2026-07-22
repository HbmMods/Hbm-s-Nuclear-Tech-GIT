package com.hbm.tileentity.network.pneumatic;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoTube.PneumaticNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PneumaticNetwork;
import com.hbm.uninos.networkproviders.PneumaticNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import api.hbm.ntl.IPneumaticConnector;
import api.hbm.ntl.ISlotMonitorProvider;
import api.hbm.ntl.SlotMonitor;
import api.hbm.ntl.StackCache;
import api.hbm.ntl.StackCache.CacheSlot;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityPneumaticStorageBase extends TileEntityMachineBase implements IPneumaticConnector, IFluidStandardReceiverMK2, ISlotMonitorProvider, IControlReceiver, IGUIProvider {

	public FluidTank compair;
	public SlotMonitor[] monitors;

	protected PneumaticNode node;
	protected boolean wasAvailable = false;

	public TileEntityPneumaticStorageBase(int slots) {
		super(slots);
		this.compair = new FluidTank(Fluids.AIR, 4_000).withPressure(1);
		this.monitors = new SlotMonitor[slots];

		for(int i = 0; i < monitors.length; i++) this.monitors[i] = new SlotMonitor(i, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {

		if(data.hasKey("pressure")) {
			int pressure = this.compair.getPressure() + 1;
			if(pressure > 5) pressure = 1;
			this.compair.setTankType(Fluids.AIR);
			this.compair.withPressure(pressure);
			for(SlotMonitor monitor : this.monitors) monitor.availabilityHasChanged();
		}
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			boolean isAvailable = this.isAvailable();

			if(isAvailable != wasAvailable) {
				this.wasAvailable = isAvailable;
				for(SlotMonitor monitor : monitors) monitor.availabilityHasChanged();
			}

			if(this.node == null || this.node.expired) {
				this.node = (PneumaticNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);

				if(this.node == null || this.node.expired) {
					this.node = (PneumaticNode) new PneumaticNode(new BlockPos(xCoord, yCoord, zCoord)).setStandardConnections(xCoord, yCoord, zCoord);
					UniNodespace.createNode(worldObj, this.node);
				}
			}

			if(node != null && !node.expired && node.hasValidNet()) {
				this.node.net.storages.add(this);
			}

			if(worldObj.getTotalWorldTime() % 10 == 0) for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				this.trySubscribe(compair.getTankType(), worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			}

			if(this.compair.getFill() > 0) {
				int consumption = (int) Math.ceil(this.compair.getFill() * 9 / this.compair.getMaxFill()) + 1;
				this.compair.setFill(Math.max(this.compair.getFill() - consumption, 0));
			}

			this.updateMonitors();
			this.networkPackNT(15);
		}
	}

	public boolean isAvailable() {
		return this.isLoaded && !this.isInvalid() && this.compair.getFill() > 0;
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {

			for(SlotMonitor monitor : this.monitors) {
				for(CacheSlot cache : monitor.viewedBy) cache.removeMonitor(monitor);
			}

			if(this.node != null) {

				if(node.hasValidNet()) {
					this.node.net.storages.remove(this);
				}

				UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
			}
		}
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		for(SlotMonitor monitor : this.monitors) {
			for(CacheSlot cache : monitor.viewedBy) cache.removeMonitor(monitor);
		}

		if(node != null && node.hasValidNet()) {
			this.node.net.storages.remove(this);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		compair.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		compair.deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.compair.readFromNBT(nbt, "tank");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.compair.writeToNBT(nbt, "tank");
	}

	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return true; }

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {compair}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {compair}; }

	@Override public SlotMonitor[] getMonitors() { return monitors; }
	@Override public ItemStack getSlotAt(int index) { return this.getStackInSlot(index); }

	@Override
	public PneumaticNetwork getRelevantNetwork() {
		if(this.node == null || this.node.expired || !this.node.hasValidNet()) return null;
		return this.node.net;
	}

	@Override
	public boolean isAvailableToCache(StackCache cache) {
		if(!isAvailable()) return false;
		int range = TileEntityPneumoTube.getRangeFromPressure(this.compair.getPressure());
		int dX = xCoord - cache.x;
		int dY = yCoord - cache.y;
		int dZ = zCoord - cache.z;
		return dX * dX + dY * dY + dZ * dZ <= range * range;
	}
}
