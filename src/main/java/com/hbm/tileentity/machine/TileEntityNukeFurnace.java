package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineNukeFurnace;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCustomLore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityNukeFurnace extends TileEntity implements ISidedInventory {

	private ItemStack slots[];
	
	public int dualCookTime;
	public int dualPower;
	public static final int maxPower = 1000;
	public static final int processingSpeed = 30;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2, 0};
	private static final int[] slots_side = new int[] {0};
	
	private String customName;
	
	public TileEntityNukeFurnace() {
		slots = new ItemStack[3];
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
		return this.hasCustomInventoryName() ? this.customName : "container.nukeFurnace";
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
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
	public boolean hasItemPower(ItemStack itemStack) {
		return getItemPower(itemStack) > 0;
	}
	
	private static int getItemPower(ItemStack itemStack) {
		if(itemStack == null)
		{
			return 0;
		}else{
		Item item = itemStack.getItem();

		if(item == ModItems.rod_u238) return 1 * 5;
		if(item == ModItems.rod_dual_u238) return 2 * 5;
		if(item == ModItems.rod_quad_u238) return 4 * 5;
		if(item == ModItems.rod_u235) return 3 * 5;
		if(item == ModItems.rod_dual_u235) return 6 * 5;
		if(item == ModItems.rod_quad_u235) return 12 * 5;
		if(item == ModItems.rod_pu238) return 5 * 5;
		if(item == ModItems.rod_dual_pu238) return 10 * 5;
		if(item == ModItems.rod_quad_pu238) return 20 * 5;
		if(item == ModItems.rod_pu239) return 3 * 5;
		if(item == ModItems.rod_dual_pu239) return 6 * 5;
		if(item == ModItems.rod_quad_pu239) return 12 * 5;
		if(item == ModItems.rod_pu240) return 1 * 5;
		if(item == ModItems.rod_dual_pu240) return 2 * 5;
		if(item == ModItems.rod_quad_pu240) return 4 * 5;
		if(item == ModItems.rod_neptunium) return 3 * 5;
		if(item == ModItems.rod_dual_neptunium) return 6 * 5;
		if(item == ModItems.rod_quad_neptunium) return 12 * 5;
		if(item == ModItems.rod_schrabidium) return 15 * 5;
		if(item == ModItems.rod_dual_schrabidium) return 30 * 5;
		if(item == ModItems.rod_quad_schrabidium) return 60 * 5;
		
		return 0;
		}
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
		
		dualPower = nbt.getShort("powerTime");
		dualCookTime = nbt.getShort("CookTime");
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
		nbt.setShort("powerTime", (short) dualPower);
		nbt.setShort("cookTime", (short) dualCookTime);
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
        return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		if(i == 0)
		{
			if(itemStack.getItem() instanceof ItemCustomLore)
			{
				return true;
			}
			
			return false;
		}
		
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(i == 0)
		{
			if(itemStack.getItem() == ModItems.rod_empty || itemStack.getItem() == ModItems.rod_dual_empty || itemStack.getItem() == ModItems.rod_quad_empty)
			{
				return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (dualCookTime * i) / processingSpeed;
	}
	
	public int getPowerRemainingScaled(int i) {
		return (dualPower * i) / maxPower;
	}
	
	public boolean canProcess() {
		if(slots[1] == null)
		{
			return false;
		}
        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);
		if(itemStack == null)
		{
			return false;
		}
		
		if(slots[2] == null)
		{
			return true;
		}
		
		if(!slots[2].isItemEqual(itemStack)) {
			return false;
		}
		
		if(slots[2].stackSize < getInventoryStackLimit() && slots[2].stackSize < slots[2].getMaxStackSize()) {
			return true;
		}else{
			return slots[2].stackSize < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
	        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);
			
			if(slots[2] == null)
			{
				slots[2] = itemStack.copy();
			}else if(slots[2].isItemEqual(itemStack)) {
				slots[2].stackSize += itemStack.stackSize;
			}
			
			for(int i = 1; i < 2; i++)
			{
				if(slots[i].stackSize <= 0)
				{
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				}else{
					slots[i].stackSize--;
				}
				if(slots[i].stackSize <= 0)
				{
					slots[i] = null;
				}
			}
			
			{
				dualPower--;
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
		boolean flag = this.hasPower();
		boolean flag1 = false;
		
		if(!worldObj.isRemote)
		{
			if(this.hasItemPower(this.slots[0]) && this.dualPower == 0)
			{
				this.dualPower += getItemPower(this.slots[0]);
				if(this.slots[0] != null)
				{
					flag1 = true;
					this.slots[0].stackSize--;
					if(this.slots[0].stackSize == 0)
					{
						this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
					}
				}
			}
			
			if(hasPower() && canProcess())
			{
				dualCookTime++;
				
				if(this.dualCookTime == TileEntityNukeFurnace.processingSpeed)
				{
					this.dualCookTime = 0;
					this.processItem();
					flag1 = true;
				}
			}else{
				dualCookTime = 0;
			}
			
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.dualCookTime == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
                MachineNukeFurnace.updateBlockState(this.dualCookTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
		}
		
		if(flag1)
		{
			this.markDirty();
		}
	}

}
