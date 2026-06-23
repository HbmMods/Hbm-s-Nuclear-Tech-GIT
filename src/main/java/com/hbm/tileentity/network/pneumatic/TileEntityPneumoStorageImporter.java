package com.hbm.tileentity.network.pneumatic;

import com.hbm.inventory.container.ContainerPneumoStorageImporter;
import com.hbm.inventory.gui.GUIPneumoStorageImporter;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoTube.PneumaticNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PneumaticNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.ntl.IPneumaticConnector;
import api.hbm.ntl.StackCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityPneumoStorageImporter extends TileEntityMachineBase implements IPneumaticConnector, IGUIProvider {
	
	protected PneumaticNode node;
	public StackCache cache;
	
	public int[] delay = new int[9];
	public int[] SLOT_ACCESS = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

	public TileEntityPneumoStorageImporter() {
		super(9);
	}

	@Override
	public String getName() {
		return "container.pneumoStorageImporter";
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null) this.delay[i] = Math.max(this.delay[i], 1);
	}

	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return true; }
	@Override public int[] getAccessibleSlotsFromSide(int side) { return SLOT_ACCESS; }

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.node == null || this.node.expired) {
				if(this.cache != null) this.cache.dissolveCache();
				
				this.node = (PneumaticNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
				
				if(this.node == null || this.node.expired) {
					this.node = (PneumaticNode) new PneumaticNode(new BlockPos(xCoord, yCoord, zCoord)).setStandardConnections(xCoord, yCoord, zCoord);
					UniNodespace.createNode(worldObj, this.node);
				}
			}
			
			if(this.cache == null || this.cache.hasExpired) {
				this.cache = new StackCache(xCoord, yCoord, zCoord);
			}
			
			if(this.node != null && this.node.hasValidNet()) {
				this.node.net.addStackCache(cache);
			}
			
			if(this.cache != null && !this.cache.hasExpired) for(int i = 0; i < 9; i++) {
				if(this.delay[i] > 0) {
					this.delay[i]--;
					continue;
				}
				ItemStack stack = slots[i];
				if(stack == null) continue;
				
				int leftover = (int) this.cache.addItemsAndReturnQuantity(stack, stack.stackSize);
				if(leftover == stack.stackSize) {
					this.delay[i] = 100;
				} else {
					this.decrStackSize(i, stack.stackSize - leftover);
				}
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote && this.node != null) {
			UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
		}
		
		if(this.cache != null) this.cache.dissolveCache();
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		if(!worldObj.isRemote && this.node != null) {
			UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
		}
		
		if(this.cache != null) this.cache.dissolveCache();
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerPneumoStorageImporter(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIPneumoStorageImporter(player.inventory, this); }
}
