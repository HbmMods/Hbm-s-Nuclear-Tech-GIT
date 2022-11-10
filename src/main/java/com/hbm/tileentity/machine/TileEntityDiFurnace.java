package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineDiFurnace;
import com.hbm.inventory.recipes.BlastFurnaceRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.util.RTGUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDiFurnace extends TileEntity implements ISidedInventory, INBTPacketReceiver {

	private ItemStack slots[];

	public int dualCookTime;
	public int dualPower;
	public static final int maxPower = 12800;
	public static final int processingSpeed = 400;

	private static final int[] slots_io = new int[] { 0, 1, 2, 3 };
	public byte sideFuel = 1;
	public byte sideUpper = 1;
	public byte sideLower = 1;

	private String customName;

	public TileEntityDiFurnace() {
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
		if(slots[i] != null) {
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
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.diFurnace";
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
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
		}
	}

	// You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 3) {
			return false;
		}

		return true;
	}

	public boolean hasItemPower(ItemStack itemStack) {
		return getItemPower(itemStack) > 0;
	}

	//TODO: replace this terribleness
	private static int getItemPower(ItemStack itemStack) {
		if(itemStack == null) {
			return 0;
		} else {
			Item item = itemStack.getItem();

			if(item == Items.coal) return 200;
			if(item == Item.getItemFromBlock(Blocks.coal_block)) return 2000;
			if(item == Items.lava_bucket) return 12800;
			if(item == Items.blaze_rod) return 1000;
			if(item == Items.blaze_powder) return 300;
			if(item == ModItems.lignite) return 150;
			if(item == ModItems.powder_lignite) return 150;
			if(item == ModItems.powder_coal) return 200;
			if(item == ModItems.briquette_lignite) return 200;
			if(item == ModItems.coke) return 400;
			if(item == ModItems.solid_fuel) return 400;

			return 0;
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null) {
			if(slots[i].stackSize <= j) {
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if(slots[i].stackSize == 0) {
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

		this.dualPower = nbt.getInteger("powerTime");
		this.dualCookTime = nbt.getShort("cookTime");
		slots = new ItemStack[getSizeInventory()];

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
		
		byte[] modes = nbt.getByteArray("modes");
		this.sideFuel = modes[0];
		this.sideUpper = modes[1];
		this.sideLower = modes[2];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("powerTime", dualPower);
		nbt.setShort("cookTime", (short) dualCookTime);
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
		
		nbt.setByteArray("modes", new byte[] {(byte) sideFuel, (byte) sideUpper, (byte) sideLower});
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots_io;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {

		if(i == 0 && this.sideUpper != j) return false;
		if(i == 1 && this.sideLower != j) return false;
		if(i == 2 && this.sideFuel != j) return false;
		if(i == 3) return false;
		
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3;
	}

	public int getDiFurnaceProgressScaled(int i) {
		return (dualCookTime * i) / processingSpeed;
	}

	public int getPowerRemainingScaled(int i) {
		return (dualPower * i) / maxPower;
	}

	public boolean canProcess() {
		if(slots[0] == null || slots[1] == null) {
			return false;
		}
		ItemStack itemStack = BlastFurnaceRecipes.getOutput(slots[0], slots[1]);
		if(itemStack == null) {
			return false;
		}

		if(slots[3] == null) {
			return true;
		}

		if(!slots[3].isItemEqual(itemStack)) {
			return false;
		}

		if(slots[3].stackSize < getInventoryStackLimit() && slots[3].stackSize < slots[3].getMaxStackSize()) {
			return true;
		} else {
			return slots[3].stackSize < itemStack.getMaxStackSize();
		}
	}

	private void processItem() {
		if(canProcess()) {
			ItemStack itemStack = BlastFurnaceRecipes.getOutput(slots[0], slots[1]);

			if(slots[3] == null) {
				slots[3] = itemStack.copy();
			} else if(slots[3].isItemEqual(itemStack)) {
				slots[3].stackSize += itemStack.stackSize;
			}

			for(int i = 0; i < 2; i++) {
				if(slots[i].stackSize <= 0) {
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				} else {
					slots[i].stackSize--;
				}
				if(slots[i].stackSize <= 0) {
					slots[i] = null;
				}
			}
		}
	}

	public boolean hasPower() {
		return dualPower > 0;
	}

	public boolean isProcessing() {
		return this.dualCookTime > 0;
	}

	@Override
	public void updateEntity() {
		
		boolean flag1 = false;

		if(hasPower() && isProcessing()) {
			this.dualPower = this.dualPower - 1;

			if(this.dualPower < 0) {
				this.dualPower = 0;
			}
		}
		if(this.hasItemPower(this.slots[2]) && this.dualPower <= (TileEntityDiFurnace.maxPower - TileEntityDiFurnace.getItemPower(this.slots[2]))) {
			this.dualPower += getItemPower(this.slots[2]);
			if(this.slots[2] != null) {
				flag1 = true;
				this.slots[2].stackSize--;
				if(this.slots[2].stackSize == 0) {
					this.slots[2] = this.slots[2].getItem().getContainerItem(this.slots[2]);
				}
			}
		}

		if(hasPower() && canProcess()) {
			dualCookTime++;

			if(this.dualCookTime == TileEntityDiFurnace.processingSpeed) {
				this.dualCookTime = 0;
				this.processItem();
				flag1 = true;
			}
		} else {
			dualCookTime = 0;
		}

		if(!worldObj.isRemote) {
			boolean trigger = true;

			if(hasPower() && canProcess() && this.dualCookTime == 0) {
				trigger = false;
			}

			if(this.slots[2] != null && (this.slots[2].getItem() instanceof ItemRTGPellet)) {
				this.dualPower += RTGUtil.updateRTGs(slots, new int[] { 2 });
				if(this.dualPower > maxPower)
					this.dualPower = maxPower;
			}

			if(trigger) {
				flag1 = true;
				MachineDiFurnace.updateBlockState(this.dualCookTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("time", (short) this.dualCookTime);
			data.setShort("fuel", (short) this.dualPower);
			data.setByteArray("modes", new byte[] {(byte) sideFuel, (byte) sideUpper, (byte) sideLower});
			INBTPacketReceiver.networkPack(this, data, 15);
		}

		if(flag1) {
			this.markDirty();
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.dualCookTime = nbt.getShort("time");
		this.dualPower = nbt.getShort("fuel");
		byte[] modes = nbt.getByteArray("modes");
		this.sideFuel = modes[0];
		this.sideUpper = modes[1];
		this.sideLower = modes[2];
	}
}
