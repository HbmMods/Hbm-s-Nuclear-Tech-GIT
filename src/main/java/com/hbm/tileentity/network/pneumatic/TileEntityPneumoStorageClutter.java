package com.hbm.tileentity.network.pneumatic;

import com.hbm.inventory.container.ContainerPneumoStorageClutter;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIPneumoStorageClutter;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityPneumoStorageClutter extends TileEntityMachineBase implements IPneumaticConnector, IFluidStandardReceiverMK2, ISlotMonitorProvider, IGUIProvider {
	
	public FluidTank compair;
	public SlotMonitor[] monitors;
	
	protected PneumaticNode node;
	protected boolean wasAvailable = false;

	public TileEntityPneumoStorageClutter() {
		super(6 * 9);
		this.compair = new FluidTank(Fluids.AIR, 4_000).withPressure(1);
		this.monitors = new SlotMonitor[6 * 9];
		
		for(int i = 0; i < monitors.length; i++) this.monitors[i] = new SlotMonitor(i, this);
	}

	@Override
	public String getName() {
		return "container.pneumoStorageClutter";
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
			
			this.updateMonitors();
			this.networkPackNT(15);
		}
	}
	
	public boolean isAvailable() {
		return this.isLoaded && !this.isInvalid();
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

	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return true; }

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {compair}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {compair}; }

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPneumoStorageClutter(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPneumoStorageClutter(player.inventory, this);
	}

	@Override public SlotMonitor[] getMonitors() { return monitors; }
	@Override public ItemStack getSlotAt(int index) { return this.getStackInSlot(index); }
	@Override public long getAmountAt(int index) { ItemStack stack = getSlotAt(index); return stack != null ? stack.stackSize : 0; }

	@Override
	public PneumaticNetwork getRelevantNetwork() {
		if(this.node == null || this.node.expired || !this.node.hasValidNet()) return null;
		return this.node.net;
	}

	@Override
	public boolean isAvailableToCache(StackCache cache) {
		return this.isLoaded && !this.isInvalid();
	}
}
