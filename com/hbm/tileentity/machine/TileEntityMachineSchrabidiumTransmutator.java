package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.interfaces.IConsumer;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineSchrabidiumTransmutator extends TileEntity implements ISidedInventory, IConsumer {

	private ItemStack slots[];

	public int power = 0;
	public int process = 0;
	public int soundCycle = 0;
	public static final int maxPower = 5000000;
	public static final int processSpeed = 60;
	Random rand = new Random();

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 3, 2 };

	private String customName;

	public TileEntityMachineSchrabidiumTransmutator() {
		slots = new ItemStack[4];
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (slots[i] != null) {
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machine_schrabidium_transmutator";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
		}
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		switch (i) {
		case 0:
			if (stack.getItem() == ModItems.ingot_uranium)
				return true;
			break;
		case 2:
			if (stack.getItem() == ModItems.redcoil_capacitor)
				return true;
			break;
		case 3:
			if (stack.getItem() instanceof ItemBattery)
				return true;
			break;
		}
		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (slots[i] != null) {
			if (slots[i].stackSize <= j) {
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0) {
				slots[i] = null;
			}

			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		power = nbt.getInteger("power");
		process = nbt.getShort("process");
		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("power", power);
		nbt.setShort("process", (short) process);
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if (i == 2 && stack.getItem() != null && stack.getItem() == ModItems.redcoil_capacitor
				&& stack.getItemDamage() == stack.getMaxDamage()) {
			return true;
		}

		if (i == 1) {
			return true;
		}

		if (i == 3) {
			if (stack.getItem() instanceof ItemBattery && ItemBattery.getCharge(stack) == 0)
				return true;
		}

		return false;
	}

	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	public int getProgressScaled(int i) {
		return (process * i) / processSpeed;
	}

	public boolean canProcess() {
		if (power >= 4990000 && slots[0] != null && slots[0].getItem() == ModItems.ingot_uranium && slots[2] != null
				&& slots[2].getItem() == ModItems.redcoil_capacitor
				&& slots[2].getItemDamage() < slots[2].getMaxDamage()
				&& (slots[1] == null || (slots[1] != null && slots[1].getItem() == ModItems.ingot_schrabidium
						&& slots[1].stackSize < slots[1].getMaxStackSize()))) {
			return true;
		}
		return false;
	}

	public boolean isProcessing() {
		return process > 0;
	}

	public void process() {
		process++;

		if (isProcessing()) {
			if (soundCycle == 0)
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "minecart.base", 1.0F, 1.0F);
			soundCycle++;

			if (soundCycle >= 38)
				soundCycle = 0;
		}

		if (process >= processSpeed) {

			power = 0;
			process = 0;

			slots[0].stackSize--;
			if (slots[0].stackSize <= 0) {
				slots[0] = null;
			}

			if (slots[1] == null) {
				slots[1] = new ItemStack(ModItems.ingot_schrabidium);
			} else {
				slots[1].stackSize++;
			}
			if (slots[2] != null) {
				slots[2].setItemDamage(slots[2].getItemDamage() + 1);
			}

			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "ambient.weather.thunder", 10000.0F,
					0.8F + this.rand.nextFloat() * 0.2F);
		}
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 3, power, maxPower);

			if (canProcess()) {

				//if (!worldObj.isRemote) {
				process();
				//}
			} else {
				process = 0;
			}

			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, power));
		}
	}

	@Override
	public void setPower(int i) {
		power = i;

	}

	@Override
	public int getPower() {
		return power;

	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}
}
