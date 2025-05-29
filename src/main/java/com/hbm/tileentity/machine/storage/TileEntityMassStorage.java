package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerMassStorage;
import com.hbm.inventory.gui.GUIMassStorage;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.IControlReceiverFilter;

import com.hbm.util.BufferUtil;

import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityMassStorage extends TileEntityCrateBase implements IBufPacketReceiver, IControlReceiverFilter, IRORValueProvider, IRORInteractive {

	private int stack = 0;
	public boolean output = false;
	private int capacity;
	public int redstone = 0;

	@SideOnly(Side.CLIENT) public ItemStack type;

	public TileEntityMassStorage() {
		super(3);
	}

	public TileEntityMassStorage(int capacity) {
		this();
		this.capacity = capacity;
	}

	@Override
	public String getInventoryName() {
		return "container.massStorage";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			int newRed = this.getStockpile() * 15 / this.capacity;

			if(newRed != this.redstone) {
				this.redstone = newRed;
				this.markDirty();
			}

			if(slots[0] != null && slots[0].getItem() == ModItems.fluid_barrel_infinite) {
				this.stack = this.getCapacity();
			}

			if(this.getType() == null)
				this.stack = 0;

			if(canInsert(slots[0])) {

				int remaining = getCapacity() - getStockpile();
				int toRemove = Math.min(remaining, slots[0].stackSize);
				this.decrStackSize(0, toRemove);
				this.stack += toRemove;
				this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
			}

			if(output && getType() != null) {

				if(slots[2] != null && !(slots[2].isItemEqual(getType()) && ItemStack.areItemStackTagsEqual(slots[2], getType()))) {
					return;
				}

				int amount = Math.min(getStockpile(), getType().getMaxStackSize());

				if(amount > 0) {
					if(slots[2] == null) {
						slots[2] = slots[1].copy();
						slots[2].stackSize = amount;
					} else {
						amount = Math.min(amount, slots[2].getMaxStackSize() - slots[2].stackSize);
						slots[2].stackSize += amount;
					}
					this.stack -= amount;
				}
			}

			networkPackNT(15);
		}
	}

	public boolean canInsert(ItemStack stack) {
		return getType() != null && getStockpile() < getCapacity() && stack != null && stack.isItemEqual(getType()) && ItemStack.areItemStackTagsEqual(stack, getType());
	}

	public boolean quickInsert(ItemStack stack) {
		if (!canInsert(stack))
			return false;
		
		int remaining = getCapacity() - getStockpile();

		if (remaining < stack.stackSize)
			return false;

		this.stack += stack.stackSize;
		stack.stackSize = 0;
		this.markDirty();

		return true;
	}

	public ItemStack quickExtract() {
		if (!output) {
			return null;
		}

		int amount = getType().getMaxStackSize();

		if (getStockpile() < amount)
			return null;
		
		ItemStack result = slots[1].copy();
		result.stackSize = amount;
		this.stack -= amount;
		this.markDirty();

		return result;
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeInt(this.stack);
		buf.writeBoolean(this.output);
		BufferUtil.writeItemStack(buf, this.slots[1]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.stack = buf.readInt();
		this.output = buf.readBoolean();
		this.type = BufferUtil.readItemStack(buf);
	}

	public int getCapacity() {
		return capacity;
	}

	public ItemStack getType() {
		return slots[1] == null ? null : slots[1].copy();
	}

	public int getStockpile() {
		return stack;
	}

	public void setStockpile(int stack) {
		this.stack = stack;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void openInventory() {
		this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.storageOpen", 0.5F, 1.0F);
	}

	@Override
	public void closeInventory() {
		this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.storageClose", 0.5F, 1.0F);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.stack = nbt.getInteger("stack");
		this.output = nbt.getBoolean("output");
		this.capacity = nbt.getInteger("capacity");
		this.redstone = nbt.getByte("redstone");

		if(this.capacity <= 0) {
			this.capacity = 10_000;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("stack", stack);
		nbt.setBoolean("output", output);
		nbt.setInteger("capacity", capacity);
		nbt.setByte("redstone", (byte) redstone);
	}

	@Override
	public void nextMode(int i) {

	}

	@Override
	public void receiveControl(NBTTagCompound data) {

		if(data.hasKey("provide") && slots[1] != null) {

			if(this.getStockpile() == 0) {
				return;
			}

			int amount = data.getBoolean("provide") ? slots[1].getMaxStackSize() : 1;
			amount = Math.min(amount, getStockpile());

			if(slots[2] != null && !(slots[2].isItemEqual(getType()) && ItemStack.areItemStackTagsEqual(slots[2], getType()))) {
				return;
			}

			if(slots[2] == null) {
				slots[2] = slots[1].copy();
				slots[2].stackSize = amount;
			} else {
				amount = Math.min(amount, slots[2].getMaxStackSize() - slots[2].stackSize);
				slots[2].stackSize += amount;
			}
			this.stack -= amount;
		}

		if(data.hasKey("toggle")) {
			this.output = !output;
		}

		if(data.hasKey("slot") && this.getStockpile() <= 0){
			setFilterContents(data);
		}
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return !this.isLocked() && i == 0 && (this.getType() == null || (getType().isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(itemStack, getType())));
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return !this.isLocked() && i == 2;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 2 };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMassStorage(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMassStorage(player.inventory, this);
	}

	@Override
	public int[] getFilterSlots() {
		return new int[]{1,2};
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_VALUE + "type",
				PREFIX_VALUE + "fill",
				PREFIX_VALUE + "fillpercent",
				PREFIX_FUNCTION + "toggleoutput",
		};
	}

	@Override
	public String provideRORValue(String name) {
		if((PREFIX_VALUE + "fill").equals(name))		return "" + this.stack;
		if((PREFIX_VALUE + "fillpercent").equals(name))	return "" + this.stack * 100 / this.capacity;
		if((PREFIX_VALUE + "type").equals(name)) {
			if(slots[1] == null) return "None";
			return slots[1].getDisplayName();
		}
		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		
		if((PREFIX_FUNCTION + "toggleoutput").equals(name)) {
			this.output = !this.output;
			this.markDirty();
		}
		
		return null;
	}
}
