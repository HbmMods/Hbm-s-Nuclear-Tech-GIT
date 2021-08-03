package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;

import api.hbm.block.ILaserable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCrateTungsten extends TileEntityLockableBase implements ISidedInventory, ILaserable {

	private ItemStack slots[];

	private String customName;
	
	private int heatTimer;

	public TileEntityCrateTungsten() {
		slots = new ItemStack[27];
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
		return this.hasCustomInventoryName() ? this.customName : "container.crateTungsten";
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

	// You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
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

			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			if(heatTimer > 0)
				heatTimer--;
	
			if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1 && heatTimer > 0)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
			
			if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0 && heatTimer <= 0)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
		}
		
		if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1) {
			worldObj.spawnParticle("flame", xCoord + worldObj.rand.nextDouble(), yCoord + 1.1, zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord + worldObj.rand.nextDouble(), yCoord + 1.1, zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			
			worldObj.spawnParticle("flame", xCoord - 0.1, yCoord + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord - 0.1, yCoord + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			
			worldObj.spawnParticle("flame", xCoord + 1.1, yCoord + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord + 1.1, yCoord + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			
			worldObj.spawnParticle("flame", xCoord + worldObj.rand.nextDouble(), yCoord + worldObj.rand.nextDouble(), zCoord - 0.1, 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord + worldObj.rand.nextDouble(), yCoord + worldObj.rand.nextDouble(), zCoord - 0.1, 0.0, 0.0, 0.0);
			
			worldObj.spawnParticle("flame", xCoord + worldObj.rand.nextDouble(), yCoord + worldObj.rand.nextDouble(), zCoord + 1.1, 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord + worldObj.rand.nextDouble(), yCoord + worldObj.rand.nextDouble(), zCoord + 1.1, 0.0, 0.0, 0.0);
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
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		int[] slots = new int[27];
		for(int i = 0; i < slots.length; i++)
			slots[i] = i;
		return slots;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack) && !this.isLocked();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(this.isLocked())
			return false;
		
		if(itemStack.getItem() == ModItems.billet_polonium)
			return false;
		
		if(itemStack.getItem() == ModItems.crucible && itemStack.getItemDamage() > 0)
			return false;
		
		if(FurnaceRecipes.smelting().getSmeltingResult(itemStack) == null)
			return true;
		
		return false;
	}

	@Override
	public void addEnergy(World world, int x, int y, int z, long energy, ForgeDirection dir) {
		heatTimer = 5;
		
		for(int i = 0; i < slots.length; i++) {
			
			if(slots[i] == null)
				continue;
			
			ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(slots[i]);
			
			if(slots[i].getItem() == ModItems.billet_polonium && energy > 10000000)
				result = new ItemStack(ModItems.billet_yharonite);
			
			if(slots[i].getItem() == ModItems.crucible && slots[i].getItemDamage() > 0 && energy > 10000000)
				result = new ItemStack(ModItems.crucible, 1, 0);
			
			int size = slots[i].stackSize;
			
			if(result != null && result.stackSize * size <= result.getMaxStackSize()) {
				slots[i] = result.copy();
				slots[i].stackSize *= size;
			}
		}
	}
}
