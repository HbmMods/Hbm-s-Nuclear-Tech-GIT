package com.hbm.tileentity.network.pneumatic;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPneumoStorageMono;
import com.hbm.inventory.gui.GUIPneumoStorageMono;
import com.hbm.tileentity.IControlReceiverFilter;
import com.hbm.tileentity.IGUIProvider;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import api.hbm.ntl.IPneumaticConnector;
import api.hbm.ntl.ISlotMonitorProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityPneumoStorageMono extends TileEntityPneumaticStorageBase implements IPneumaticConnector, IFluidStandardReceiverMK2, ISlotMonitorProvider, IControlReceiver, IGUIProvider, IControlReceiverFilter {

	public static final int CAPACITY = 100_000;
	public int[] amounts;

	public TileEntityPneumoStorageMono() {
		super(3);

		this.amounts = new int[this.monitors.length];
	}

	@Override
	public String getName() {
		return "container.pneumoStorageMono";
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for(int i = 0; i < amounts.length; i++) buf.writeInt(amounts[i]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(int i = 0; i < amounts.length; i++) amounts[i]= buf.readInt();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.amounts = nbt.getIntArray("amounts");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setIntArray("amounts", amounts);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPneumoStorageMono(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPneumoStorageMono(player.inventory, this);
	}

	@Override public long getAmountAt(int index) { return amounts[index]; }
	@Override public boolean allowTypeSetting() { return false; }

	@Override
	public void receiveControl(NBTTagCompound data) {
		super.receiveControl(data);
		
		if(data.hasKey("slot")){
			setFilterContents(data);
		}
	}

	@Override
	public long useUpItem(int index, long amount) {
		if(amounts[index] <= 0) return amount;
		int toRemove = (int) Math.min(amount, amounts[index]);
		amounts[index] -= toRemove;
		return amount - toRemove;
	}

	@Override
	public long addItem(int index, long amount) {
		int capacity = CAPACITY - amounts[index];
		if(capacity <= 0) return amount;
		int toAdd = (int) Math.min(amount, capacity);
		amounts[index] += toAdd;
		return amount - toAdd;
	}

	@Override public long setupType(int index, ItemStack zeroStack, long amount) { return amount; }

	@Override
	public void nextMode(int i) { }

	@Override
	public int[] getFilterSlots() {
		return new int[] {0, 3};
	}
}
