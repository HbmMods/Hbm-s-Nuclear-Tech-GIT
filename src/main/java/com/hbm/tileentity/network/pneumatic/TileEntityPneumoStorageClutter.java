package com.hbm.tileentity.network.pneumatic;

import com.hbm.inventory.container.ContainerPneumoStorageClutter;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIPneumoStorageClutter;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoTube.PneumaticNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PneumaticNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import api.hbm.ntl.ISlotMonitorProvider;
import api.hbm.ntl.SlotMonitor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityPneumoStorageClutter extends TileEntityMachineBase implements IFluidStandardReceiverMK2, ISlotMonitorProvider, IGUIProvider {
	
	public FluidTank compair;
	public SlotMonitor[] monitors;
	
	protected PneumaticNode node;

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
			
			if(this.node == null || this.node.expired) {
				this.node = (PneumaticNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
				
				if(this.node == null || this.node.expired) {
					this.node = (PneumaticNode) new PneumaticNode(new BlockPos(xCoord, yCoord, zCoord)).setConnections(
							new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
							new DirPos(xCoord - 1, yCoord, zCoord, Library.NEG_X),
							new DirPos(xCoord, yCoord + 1, zCoord, Library.POS_Y),
							new DirPos(xCoord, yCoord - 1, zCoord, Library.NEG_Y),
							new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
							new DirPos(xCoord, yCoord, zCoord - 1, Library.NEG_Z)
							);
					UniNodespace.createNode(worldObj, this.node);
				}
			}
			
			if(node != null && !node.expired && node.hasValidNet()) {
				this.node.net.storages.put(this, worldObj.getTotalWorldTime());
			}

			this.networkPackNT(15);
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.node != null) {
				UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
			}
		}
	}
	
	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if(node != null && !node.expired && node.hasValidNet()) {
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

	@Override
	public boolean isAvailableToTerminal(int termX, int termY, int termZ) {
		return true;
	}
}
