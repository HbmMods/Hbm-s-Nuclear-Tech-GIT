package com.hbm.tileentity;

import com.hbm.gui.MachineRecipes;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineCentrifuge extends TileEntity implements ISidedInventory {

	private ItemStack slots[];
	
	public int dualCookTime;
	public int dualPower;
	public int soundCycle = 0;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 500;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {2, 3, 4, 5};
	private static final int[] slots_side = new int[] {1};
	
	private String customName;
	
	public TileEntityMachineCentrifuge() {
		slots = new ItemStack[6];
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
		return this.hasCustomInventoryName() ? this.customName : "container.centrifuge";
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
		if(i == 2 || i == 3 || i == 4 || i == 5)
		{
			return false;
		}
		
		if(i == 1 && hasItemPower(itemStack))
		{
			return true;
		}
		
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
		
		if(item == Items.coal) return 2500;
		if(item == Item.getItemFromBlock(Blocks.coal_block)) return 25000;
		if(item == Items.lava_bucket) return 50000;
		if(item == Items.redstone) return  1000;
		if(item == Item.getItemFromBlock(Blocks.redstone_block)) return 10000;
		if(item == Item.getItemFromBlock(Blocks.netherrack)) return 1750;
		if(item == Items.blaze_rod) return 15000;
		if(item == Items.blaze_powder) return 5000;
		
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
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return j != 0 || i != 1 || itemStack.getItem() == Items.bucket;
	}
	
	public int getCentrifugeProgressScaled(int i) {
		return (dualCookTime * i) / processingSpeed;
	}
	
	public int getPowerRemainingScaled(int i) {
		return (dualPower * i) / maxPower;
	}
	
	
	public boolean canProcess() {
		
		if(slots[0] == null)
		{
			return false;
		}
		ItemStack[] itemStack = MachineRecipes.getCentrifugeProcessingResult(slots[0].getItem());
		if(itemStack == null)
		{
			return false;
		}
		
		if(slots[2] == null && slots[3] == null && slots[4] == null && slots[5] == null)
		{
			return true;
		}
		
		if((slots[2] == null || (itemStack[0] != null && (slots[2].isItemEqual(itemStack[0])) && slots[2].stackSize + itemStack[0].stackSize <= itemStack[0].getMaxStackSize())) && 
				(slots[3] == null || (itemStack[1] != null && (slots[3].isItemEqual(itemStack[1])) && slots[3].stackSize + itemStack[1].stackSize <= itemStack[1].getMaxStackSize())) && 
				(slots[4] == null || (itemStack[2] != null && (slots[4].isItemEqual(itemStack[2])) && slots[4].stackSize + itemStack[2].stackSize <= itemStack[2].getMaxStackSize())) && 
				(slots[5] == null || (itemStack[3] != null && (slots[5].isItemEqual(itemStack[3])) && slots[5].stackSize + itemStack[3].stackSize <= itemStack[3].getMaxStackSize())))
		{
			return true;
		}
		
		return false;
	}
	
	private void processItem() {
		if(canProcess()) {
			ItemStack[] itemStack = MachineRecipes.getCentrifugeProcessingResult(slots[0].getItem());
			
			if(slots[2] == null && itemStack[0] != null)
			{
				slots [2] = itemStack[0].copy();
			}else if(itemStack[0] != null && slots[2].isItemEqual(itemStack[0]))
			{
				slots[2].stackSize += itemStack[0].stackSize;
			}
			
			if(slots[3] == null && itemStack[1] != null)
			{
				slots [3] = itemStack[1].copy();
			}else if(itemStack[1] != null && slots[3].isItemEqual(itemStack[1]))
			{
				slots[3].stackSize += itemStack[1].stackSize;
			}
			
			if(slots[4] == null && itemStack[2] != null)
			{
				slots [4] = itemStack[2].copy();
			}else if(itemStack[2] != null && slots[4].isItemEqual(itemStack[2]))
			{
				slots[4].stackSize += itemStack[2].stackSize;
			}
			
			if(slots[5] == null && itemStack[3] != null)
			{
				slots [5] = itemStack[3].copy();
			}else if(itemStack[3] != null && slots[5].isItemEqual(itemStack[3]))
			{
				slots[5].stackSize += itemStack[3].stackSize;
			}
			
			for(int i = 0; i < 1; i++)
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
		
		if(hasPower() && isProcessing())
		{
			this.dualPower = this.dualPower - 50;
			
			if(this.dualPower < 0)
			{
				this.dualPower = 0;
			}
		}
		
		if(!worldObj.isRemote)
		{
			if(this.hasItemPower(this.slots[1]) && this.dualPower <= (TileEntityMachineCentrifuge.maxPower - TileEntityMachineCentrifuge.getItemPower(this.slots[1])))
			{
				this.dualPower += getItemPower(this.slots[1]);
				if(this.slots[1] != null)
				{
					flag1 = true;
					this.slots[1].stackSize--;
					if(this.slots[1].stackSize == 0)
					{
						this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
					}
				}
			}
			
			if(this.slots[1] != null && this.slots[1].getItem() == ModItems.pellet_rtg)
			{
				this.dualPower = maxPower;
			}
			
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.dualCookTime == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
            }
		}
		
		if(hasPower() && canProcess())
		{
			dualCookTime++;
			if(soundCycle == 0)
		        this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "minecart.base", 1.0F, 1.5F);
			soundCycle++;
				
			if(soundCycle >= 25)
				soundCycle = 0;
			
			if(this.dualCookTime >= TileEntityMachineCentrifuge.processingSpeed)
			{
				this.dualCookTime = 0;
				this.processItem();
			}
		}else{
			dualCookTime = 0;
		}
		
		if(flag1)
		{
			this.markDirty();
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
}
