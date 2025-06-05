package com.hbm.tileentity.machine;

import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.inventory.container.ContainerSatDock;
import com.hbm.inventory.gui.GUISatDock;
import com.hbm.itempool.ItemPool;
import com.hbm.items.ISatChip;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteMiner;
import com.hbm.tileentity.IGUIProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityMachineSatDock extends TileEntity implements ISidedInventory, IGUIProvider {
	
	private ItemStack[] slots;
	private static final int[] access = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

	private String customName;
	private AxisAlignedBB renderBoundingBox;

	public TileEntityMachineSatDock() { slots = new ItemStack[16]; }

	@Override public int getSizeInventory() { return slots.length; }
	@Override public ItemStack getStackInSlot(int i) { return slots[i]; }

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

	@Override public String getInventoryName() { return this.hasCustomInventoryName() ? this.customName : "container.satDock"; }
	@Override public boolean hasCustomInventoryName() { return this.customName != null && this.customName.length() > 0; }
	public void setCustomName(String name) { this.customName = name; markDirty(); }
	@Override public int getInventoryStackLimit() { return 64; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
		}
	}

	@Override public void openInventory() { }
	@Override public void closeInventory() { }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) { return i == 15; }

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

		slots = new ItemStack[getSizeInventory()];

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}

		customName = nbt.getString("name");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
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
		
		if (customName != null) {
			nbt.setString("name", customName);
		}
	}

	@Override public int[] getAccessibleSlotsFromSide(int p_94128_1_) { return access; }
	@Override public boolean canInsertItem(int i, ItemStack itemStack, int j) { return this.isItemValidForSlot(i, itemStack); }
	@Override public boolean canExtractItem(int i, ItemStack itemStack, int j) { return true; }

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			SatelliteSavedData data = SatelliteSavedData.getData(worldObj);

			if(slots[15] != null) {
				int freq = ISatChip.getFreqS(slots[15]);

				Satellite sat = data.getSatFromFreq(freq);

				int delay = 10 * 60 * 1000;

				if(sat instanceof SatelliteMiner) {
					SatelliteMiner miner = (SatelliteMiner) sat;

					if(miner.lastOp + delay < System.currentTimeMillis()) {
						EntityMinerRocket rocket = new EntityMinerRocket(worldObj);
						rocket.posX = xCoord + 0.5;
						rocket.posY = 300;
						rocket.posZ = zCoord + 0.5;

						rocket.getDataWatcher().updateObject(17, freq);
						worldObj.spawnEntityInWorld(rocket);
						miner.lastOp = System.currentTimeMillis();
						data.markDirty();
					}
				}
			}

			@SuppressWarnings("unchecked")
			List<EntityMinerRocket> list = worldObj.getEntitiesWithinAABBExcludingEntity(null,
					AxisAlignedBB.getBoundingBox(xCoord - 0.25 + 0.5, yCoord + 0.75, zCoord - 0.25 + 0.5, xCoord + 0.25 + 0.5, yCoord + 2, zCoord + 0.25 + 0.5),
					entity -> entity instanceof EntityMinerRocket);

			for(EntityMinerRocket rocket : list) {
				if(slots[15] != null && ISatChip.getFreqS(slots[15]) != rocket.getDataWatcher().getWatchableObjectInt(17)) {
					rocket.setDead();
					ExplosionNukeSmall.explode(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, ExplosionNukeSmall.PARAMS_TOTS);
					break;
				}

				if(rocket.getDataWatcher().getWatchableObjectInt(16) == 1 && rocket.timer == 50) {
					Satellite sat = data.getSatFromFreq(ISatChip.getFreqS(slots[15]));
					if(sat != null) unloadCargo((SatelliteMiner) sat);
				}
			}

			ejectInto(xCoord + 2, yCoord, zCoord);
			ejectInto(xCoord - 2, yCoord, zCoord);
			ejectInto(xCoord, yCoord, zCoord + 2);
			ejectInto(xCoord, yCoord, zCoord - 2);
		}
	}

	private void unloadCargo(SatelliteMiner satellite) {
		int itemAmount = worldObj.rand.nextInt(6) + 10;

		WeightedRandomChestContent[] cargo = ItemPool.getPool(satellite.getCargo());

		for(int i = 0; i < itemAmount; i++) {
			addToInv(ItemPool.getStack(cargo, worldObj.rand));
		}
	}

	private void addToInv(ItemStack stack) {
		for(int i = 0; i < 15; i++) {
			if(slots[i] != null && slots[i].getItem() == stack.getItem() && slots[i].getItemDamage() == stack.getItemDamage() && slots[i].stackSize < slots[i].getMaxStackSize()) {
				int toAdd = Math.min(slots[i].getMaxStackSize() - slots[i].stackSize, stack.stackSize);

				slots[i].stackSize += toAdd;
				stack.stackSize -= toAdd;

				if(stack.stackSize <= 0)
					return;
			}
		}

		for(int i = 0; i < 15; i++) {
			if(slots[i] == null) {
				slots[i] = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
				return;
			}
		}
	}

	private void ejectInto(int x, int y, int z) {
		TileEntity te = worldObj.getTileEntity(x, y, z);

		if(te instanceof IInventory) {
			IInventory chest = (IInventory) te;

			for(int i = 0; i < 15; i++) {
				if(slots[i] != null) {
					for(int j = 0; j < chest.getSizeInventory(); j++) {
						ItemStack sta = slots[i].copy();
						sta.stackSize = 1;

						if(chest.getStackInSlot(j) != null && chest.getStackInSlot(j).isItemEqual(slots[i]) && ItemStack.areItemStackTagsEqual(chest.getStackInSlot(j), slots[i])
								&& chest.getStackInSlot(j).stackSize < chest.getStackInSlot(j).getMaxStackSize()) {

							slots[i].stackSize--;

							if(slots[i].stackSize <= 0)
								slots[i] = null;

							chest.getStackInSlot(j).stackSize++;
							return;
						}
					}
				}
			}

			for(int i = 0; i < 15; i++) {
				if(slots[i] != null) {
					for(int j = 0; j < chest.getSizeInventory(); j++) {
						ItemStack sta = slots[i].copy();
						sta.stackSize = 1;

						if(chest.getStackInSlot(j) == null && chest.isItemValidForSlot(j, sta)) {
							slots[i].stackSize--;

							if(slots[i].stackSize <= 0)
								slots[i] = null;

							chest.setInventorySlotContents(j, sta);
							return;
						}
					}
				}
			}
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(renderBoundingBox == null) {
			renderBoundingBox = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 1, zCoord + 2);
		}

		return renderBoundingBox;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerSatDock(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUISatDock(player.inventory, this);
	}
}
