package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKRod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityWasteDrum extends TileEntity implements ISidedInventory {

	private ItemStack slots[];
	
	private static final int[] slots_arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	
	private String customName;
	
	private static final HashMap<ComparableStack, ItemStack> wasteMap = new HashMap<ComparableStack, ItemStack>();
	static {
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_natural_uranium, 1, 1)), new ItemStack(ModItems.waste_natural_uranium));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_uranium, 1, 1)), new ItemStack(ModItems.waste_uranium));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_thorium, 1, 1)), new ItemStack(ModItems.waste_thorium));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_mox, 1, 1)), new ItemStack(ModItems.waste_mox));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_plutonium, 1, 1)), new ItemStack(ModItems.waste_plutonium));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_u233, 1, 1)), new ItemStack(ModItems.waste_u233));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_u235, 1, 1)), new ItemStack(ModItems.waste_u235));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_schrabidium, 1, 1)), new ItemStack(ModItems.waste_schrabidium));
		
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_plate_u233, 1, 1)), new ItemStack(ModItems.waste_plate_u233));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_plate_u235, 1, 1)), new ItemStack(ModItems.waste_plate_u235));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_plate_mox, 1, 1)), new ItemStack(ModItems.waste_plate_mox));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_plate_pu239, 1, 1)), new ItemStack(ModItems.waste_plate_pu239));
		wasteMap.put(new ComparableStack(new ItemStack(ModItems.waste_plate_sa326, 1, 1)), new ItemStack(ModItems.waste_plate_sa326));
	}
	
	public TileEntityWasteDrum() {
		slots = new ItemStack[12];
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
		return this.hasCustomInventoryName() ? this.customName : "container.wasteDrum";
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
		return 1;
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
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return wasteMap.keySet().contains(new ComparableStack(itemStack)) || itemStack.getItem() instanceof ItemRBMKRod;
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

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
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return slots_arr;
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {	
		if(itemStack.getItem() instanceof ItemRBMKRod) {
			return ItemRBMKRod.getCoreHeat(itemStack) < 50 && ItemRBMKRod.getHullHeat(itemStack) < 50;
		} else {
			return wasteMap.containsValue(getStackInSlot(i));
		}
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			int water = 0;

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == Blocks.water || worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == Blocks.flowing_water) {
					water++;
				}
			}
			
			if(water > 0) {
				
				int r = 60 * 60 * 20 / water;
				
				for(int i = 0; i < 12; i++) {
					
					if(slots[i] != null) {
						
						if(slots[i].getItem() instanceof ItemRBMKRod) {
							
							ItemRBMKRod rod = (ItemRBMKRod) slots[i].getItem();
							rod.updateHeat(worldObj, slots[i], 0.025D);
							rod.provideHeat(worldObj, slots[i], 20D, 0.025D);
							
						} else if(worldObj.rand.nextInt(r) == 0) {

							ComparableStack comp = new ComparableStack(getStackInSlot(i));
							if(wasteMap.keySet().contains(comp)) {
								slots[i] = wasteMap.get(comp).copy();
							}
						}
					}
				}
			}
		}
	}
}
