package com.hbm.tileentity.network.pneumatic;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPneumoStorageClutter;
import com.hbm.inventory.gui.GUIPneumoStorageClutter;
import com.hbm.tileentity.IGUIProvider;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import api.hbm.ntl.IPneumaticConnector;
import api.hbm.ntl.ISlotMonitorProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityPneumoStorageClutter extends TileEntityPneumaticStorageBase implements IPneumaticConnector, IFluidStandardReceiverMK2, ISlotMonitorProvider, IControlReceiver, IGUIProvider {

	public TileEntityPneumoStorageClutter() {
		super(6 * 9);
	}

	@Override
	public String getName() {
		return "container.pneumoStorageClutter";
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPneumoStorageClutter(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPneumoStorageClutter(player.inventory, this);
	}
	
	@Override public long getAmountAt(int index) { ItemStack stack = getSlotAt(index); return stack != null ? stack.stackSize : 0; }
	@Override public boolean allowTypeSetting() { return true; }

	@Override
	public long useUpItem(int index, long amount) {
		ItemStack stack = slots[index];
		if(stack != null) {
			int toRemove = (int) Math.min(stack.stackSize, amount);
			this.decrStackSize(index, toRemove);
			return amount - toRemove;
		}
		return amount;
	}

	@Override
	public long addItem(int index, long amount) {
		ItemStack stack = slots[index];
		if(stack != null) {
			int capacity = Math.min(stack.getMaxStackSize(), this.getInventoryStackLimit());
			int toAdd = (int) Math.min(amount, capacity - stack.stackSize);
			stack.stackSize += toAdd;
			return amount - toAdd;
		}
		return amount;
	}

	@Override
	public long setupType(int index, ItemStack zeroStack, long amount) {
		int capacity = Math.min(zeroStack.getMaxStackSize(), this.getInventoryStackLimit());
		int finalSize = (int) Math.min(amount, capacity);
		slots[index] = zeroStack.copy();
		slots[index].stackSize = finalSize;
		return amount - finalSize;
	}
}
