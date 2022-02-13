package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFactory;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoreTitanium extends TileEntity implements ISidedInventory, IFactory, IEnergyUser {
	
	public int progress = 0;
	public long power = 0;
	public int soundCycle = 0;
	public final static int processTime = 200;
	public final static int maxPower = (int)((ItemBattery)ModItems.factory_core_titanium).getMaxCharge();
	private ItemStack slots[];
	
	private String customName;

	public TileEntityCoreTitanium() {
		slots = new ItemStack[23];
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
		if(slots[i] != null)
		{
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
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.factoryTitanium";
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
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=64;
		}
	}
	
	@Override
	public void openInventory() {}
	
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.progress = nbt.getShort("cookTime");
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("cookTime", (short) progress);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Spaghetti("2016 bobcode *shudders*")
	@Override
	public boolean isStructureValid(World world) {
		if(world.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.factory_titanium_core &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord) == ModBlocks.factory_titanium_hull &&
				(world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == ModBlocks.factory_titanium_conductor || world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == ModBlocks.factory_titanium_hull) &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord - 1) == ModBlocks.factory_titanium_furnace &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord) == ModBlocks.factory_titanium_furnace &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord) == ModBlocks.factory_titanium_furnace &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord + 1) == ModBlocks.factory_titanium_furnace &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord) == ModBlocks.factory_titanium_hull &&
				(world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == ModBlocks.factory_titanium_conductor || world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == ModBlocks.factory_titanium_hull) &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull)
		{
			return true;
		}
		return false;
	}

	@Override
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public int getProgressScaled(int i) {
		return (progress * i) / processTime;
	}
	
	@Override
	public boolean isProcessable(ItemStack item) {
		if(item != null)
		{
			return FurnaceRecipes.smelting().getSmeltingResult(item) != null;
		} else {
			return false;
		}
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) == ModBlocks.factory_titanium_conductor)
				this.trySubscribe(worldObj, xCoord, yCoord + 2, zCoord, ForgeDirection.UP);
			else
				this.tryUnsubscribe(worldObj, xCoord, yCoord + 2, zCoord);
			
			if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.factory_titanium_conductor)
				this.trySubscribe(worldObj, xCoord, yCoord - 2, zCoord, ForgeDirection.DOWN);
			else
				this.tryUnsubscribe(worldObj, xCoord, yCoord - 2, zCoord);
		}
		
		if(this.slots[22] != null && this.slots[22].getItem() == ModItems.factory_core_titanium)
		{
			this.power = (int) ((IBatteryItem)slots[22].getItem()).getCharge(slots[22]);
		} else {
			this.power = 0;
		}
		
		if(this.slots[9] == null)
		{
			for(int i = 0; i < 9; i++)
			{
				if(slots[i] != null && isProcessable(slots[i]))
				{
					slots[9] = slots[i].copy();
					slots[i] = null;
					break;
				}
			}
		} else {
			for(int i = 0; i < 9; i++)
			{
				if(slots[i] != null)
				{
					if(slots[i].getItem() == slots[9].getItem() && slots[i].getItemDamage() == slots[9].getItemDamage())
					{
						if(slots[9].stackSize + slots[i].stackSize <= slots[i].getMaxStackSize())
						{
							slots[9].stackSize += slots[i].stackSize;
							slots[i] = null;
						} else {
							int j = 64 - slots[9].stackSize;
							slots[9].stackSize += j;
							slots[i].stackSize -= j;
						}
						break;
					}
				}
			}
		}
		
		if(this.slots[10] == null)
		{
			for(int i = 0; i < 9; i++)
			{
				if(slots[i] != null && isProcessable(slots[i]))
				{
					slots[10] = slots[i].copy();
					slots[i] = null;
					break;
				}
			}
		} else {
			for(int i = 0; i < 9; i++)
			{
				if(slots[i] != null)
				{
					if(slots[i].getItem() == slots[10].getItem() && slots[i].getItemDamage() == slots[10].getItemDamage())
					{
						if(slots[10].stackSize + slots[i].stackSize <= slots[i].getMaxStackSize())
						{
							slots[10].stackSize += slots[i].stackSize;
							slots[i] = null;
						} else {
							int j = 64 - slots[10].stackSize;
							slots[10].stackSize += j;
							slots[i].stackSize -= j;
						}
						break;
					}
				}
			}
		}
		
		if(this.power > 0 && (isProcessable(slots[9]) || isProcessable(slots[10])) && isStructureValid(worldObj))
		{
			this.progress += 1;
			((ItemBattery)slots[22].getItem()).dischargeBattery(slots[22], 1);
			if(soundCycle == 0)
	        	this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "minecart.base", 1.0F, 0.75F);
			soundCycle++;
			
			if(soundCycle >= 50)
				soundCycle = 0;
		} else {
			this.progress = 0;
		}
		
		if(slots[9] != null && slots[11] != null && (FurnaceRecipes.smelting().getSmeltingResult(slots[9]).getItem() != slots[11].getItem() || FurnaceRecipes.smelting().getSmeltingResult(slots[9]).getItemDamage() != slots[11].getItemDamage()))
		{
			this.progress = 0;
		}
		
		if(slots[10] != null && slots[12] != null && (FurnaceRecipes.smelting().getSmeltingResult(slots[10]).getItem() != slots[12].getItem() || FurnaceRecipes.smelting().getSmeltingResult(slots[10]).getItemDamage() != slots[12].getItemDamage()))
		{
			this.progress = 0;
		}
		
		if(this.progress >= TileEntityCoreTitanium.processTime)
		{
			if(this.slots[9] != null && isProcessable(this.slots[9]))
			{
				ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(slots[9]);
				if(slots[11] == null)
				{
					slots[11] = itemStack.copy();
				}else if(slots[11].isItemEqual(itemStack)) {
					slots[11].stackSize += itemStack.stackSize;
				}
				if(slots[9].stackSize <= 0)
				{
					slots[9] = new ItemStack(slots[9].getItem().setFull3D());
				}else{
					slots[9].stackSize--;
				}
				if(slots[9].stackSize <= 0)
				{
					slots[9] = null;
				}
			}
			if(this.slots[10] != null && isProcessable(this.slots[10]))
			{
				ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(slots[10]);
				if(slots[12] == null)
				{
					slots[12] = itemStack.copy();
				}else if(slots[12].isItemEqual(itemStack)) {
					slots[12].stackSize += itemStack.stackSize;
				}
				if(slots[10].stackSize <= 0)
				{
					slots[10] = new ItemStack(slots[10].getItem().setFull3D());
				}else{
					slots[10].stackSize--;
				}
				if(slots[10].stackSize <= 0)
				{
					slots[10] = null;
				}
			}
			
			this.progress = 0;
		}
			
		if(this.slots[11] != null)
		{
			for(int i = 0; i < 9; i++)
			{
				int j = i + 13;
				if(slots[j] != null)
				{
					if(slots[j].getItem() == slots[11].getItem() && slots[j].getItemDamage() == slots[11].getItemDamage())
					{
						if(slots[j].stackSize < slots[j].getMaxStackSize())
						{
							if(slots[j].stackSize + slots[11].stackSize <= slots[11].getMaxStackSize())
							{
								slots[j].stackSize += slots[11].stackSize;
								slots[11] = null;
								break;
							} else {
								int k = slots[j].getMaxStackSize() - slots[j].stackSize;
								if(k < 0)
								{
									slots[j].stackSize += k;
									slots[11].stackSize -= k;
									break;
								}
							}
						}
					}
				} else {
					slots[j] = slots[11].copy();
					slots[11] = null;
					break;
				}
			}
		}
		
		if(this.slots[12] != null)
		{
			for(int i = 0; i < 9; i++)
			{
				int j = i + 13;
				if(slots[j] != null)
				{
					if(slots[j].getItem() == slots[12].getItem() && slots[j].getItemDamage() == slots[12].getItemDamage())
					{
						if(slots[j].stackSize < slots[j].getMaxStackSize())
						{
							if(slots[j].stackSize + slots[12].stackSize <= slots[12].getMaxStackSize())
							{
								slots[j].stackSize += slots[12].stackSize;
								slots[12] = null;
								break;
							} else {
								int k = slots[j].getMaxStackSize() - slots[j].stackSize;
								if(k < 0)
								{
									slots[j].stackSize += k;
									slots[12].stackSize -= k;
									break;
								}
							}
						}
					}
				} else {
					slots[j] = slots[12].copy();
					slots[12] = null;
					break;
				}
			}
		}
	}
	@Override
	public void setPower(long i) {
		if(this.slots[22] != null && this.slots[22].getItem() == ModItems.factory_core_titanium)
		{
			((ItemBattery)slots[22].getItem()).setCharge(slots[22], (int)i);
		}
	}
	@Override
	public long getPower() {
		return power;
	}
	@Override
	public long getMaxPower() {
		return maxPower;
	}

}
