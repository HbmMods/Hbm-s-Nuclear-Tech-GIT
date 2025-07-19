package com.hbm.tileentity.machine.storage;

import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.TileEntityLockableBase;

import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.Random;

public abstract class TileEntityCrateBase extends TileEntityLockableBase implements ISidedInventory, IGUIProvider {

	protected ItemStack[] slots;
	public String customName;

	public boolean hasSpiders = false;

	public TileEntityCrateBase(int count) {
		slots = new ItemStack[count];
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
		this.markDirty();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && !this.customName.isEmpty();
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
		this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.crateOpen", 1.0F, 1.0F);
	}

	@Override
	public void closeInventory() {
		this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.crateClose", 1.0F, 1.0F);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
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

			this.markDirty();
			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
		this.hasSpiders = nbt.getBoolean("spiders");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

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
		nbt.setBoolean("spiders", hasSpiders);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		int[] slots = new int[this.slots.length];
		for(int i = 0; i < slots.length; i++) slots[i] = i;
		return slots;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack) && !this.isLocked();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return !this.isLocked();
	}

	// Spiders!!!
	public void fillWithSpiders() {
		this.hasSpiders = true;
	}

	private static final int numSpiders = 3; // leave that at 3 for now TODO: maybe a config option or smth

	/// For when opening from a TileEntity.
	public static void spawnSpiders(EntityPlayer player, World worldObj, TileEntityCrateBase crate) {
		if(crate.hasSpiders) {
			Random random = new Random();

			for (int i = 0; i < numSpiders; i++) {

				EntityCaveSpider spider = new EntityCaveSpider(worldObj); // lord
				spider.setLocationAndAngles(crate.xCoord + random.nextGaussian() * 2, crate.yCoord + 1, crate.zCoord + random.nextGaussian() * 2, random.nextFloat(), 0);
				spider.setAttackTarget(player);

				worldObj.spawnEntityInWorld(spider);
			}
			crate.hasSpiders = false;
			crate.markDirty();
		}
	}

	/// For when opening from a player's inventory.
	public static void spawnSpiders(EntityPlayer player, World worldObj, ItemStack crate) {
		if(crate.hasTagCompound() && crate.getTagCompound().getBoolean("spiders")) {
			Random random = new Random();

			for (int i = 0; i < numSpiders; i++) {

				EntityCaveSpider spider = new EntityCaveSpider(worldObj);
				spider.setLocationAndAngles(player.posX + random.nextGaussian() * 2, player.posY + 1, player.posZ + random.nextGaussian() * 2, random.nextFloat(), 0);
				spider.setAttackTarget(player);

				worldObj.spawnEntityInWorld(spider);
			}
			crate.getTagCompound().removeTag("spiders");
		}
	}
}
