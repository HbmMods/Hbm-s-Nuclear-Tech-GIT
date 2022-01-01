package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.blocks.machine.MachineNukeFurnace;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.BreederRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCustomLore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
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
	
	private static int getItemPower(ItemStack stack) {
		if(stack == null) {
			return 0;
		} else {

			int[] power = getFuelValue(stack);
			
			if(power == null)
				return 0;
			
			return power[0] * power[1] * 5;
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
		this.hasPower();
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
	
	private static HashMap<ComparableStack, int[]> fuels = new HashMap();
	//for the int array: [0] => level (1-4) [1] => amount of operations
	
	/* 
	 * I really don't want to have to do this, but it's better then making a new class, for one TE, for not even recipes but just *fuels*
	 * 
	 * Who even uses this furnace? Nobody, but it's better then removing it without prior approval
	 */
	public static void registerFuels() {
		fuels.put(new ComparableStack(ModItems.rod_u233), new int[] {2, 2});
		fuels.put(new ComparableStack(ModItems.rod_dual_u233), new int[] {2, 4});
		fuels.put(new ComparableStack(ModItems.rod_quad_u233), new int[] {2, 8});
		
		fuels.put(new ComparableStack(ModItems.rod_u235), new int[] {2, 3});
		fuels.put(new ComparableStack(ModItems.rod_dual_u235), new int[] {2, 6});
		fuels.put(new ComparableStack(ModItems.rod_quad_u235), new int[] {2, 12});
		
		fuels.put(new ComparableStack(ModItems.rod_u238), new int[] {1, 1});
		fuels.put(new ComparableStack(ModItems.rod_dual_u238), new int[] {1, 2});
		fuels.put(new ComparableStack(ModItems.rod_quad_u238), new int[] {1, 4});
		
		fuels.put(new ComparableStack(ModItems.rod_neptunium), new int[] {2, 3});
		fuels.put(new ComparableStack(ModItems.rod_dual_neptunium), new int[] {2, 6});
		fuels.put(new ComparableStack(ModItems.rod_quad_neptunium), new int[] {2, 12});
		
		fuels.put(new ComparableStack(ModItems.rod_pu238), new int[] {1, 2});
		fuels.put(new ComparableStack(ModItems.rod_dual_pu238), new int[] {1, 4});
		fuels.put(new ComparableStack(ModItems.rod_quad_pu238), new int[] {1, 8});
		
		fuels.put(new ComparableStack(ModItems.rod_pu239), new int[] {3, 5});
		fuels.put(new ComparableStack(ModItems.rod_dual_pu239), new int[] {3, 10});
		fuels.put(new ComparableStack(ModItems.rod_quad_pu239), new int[] {3, 20});
		
		fuels.put(new ComparableStack(ModItems.rod_pu240), new int[] {1, 2});
		fuels.put(new ComparableStack(ModItems.rod_dual_pu240), new int[] {1, 4});
		fuels.put(new ComparableStack(ModItems.rod_quad_pu240), new int[] {1, 8});
		
		fuels.put(new ComparableStack(ModItems.rod_schrabidium), new int[] {3, 10});
		fuels.put(new ComparableStack(ModItems.rod_dual_schrabidium), new int[] {3, 20});
		fuels.put(new ComparableStack(ModItems.rod_quad_schrabidium), new int[] {3, 40});
		
		fuels.put(new ComparableStack(ModItems.rod_solinium), new int[] {3, 15});
		fuels.put(new ComparableStack(ModItems.rod_dual_solinium), new int[] {3, 30});
		fuels.put(new ComparableStack(ModItems.rod_quad_solinium), new int[] {3, 60});
		
		fuels.put(new ComparableStack(ModItems.rod_polonium), new int[] {4, 2});
		fuels.put(new ComparableStack(ModItems.rod_dual_polonium), new int[] {4, 4});
		fuels.put(new ComparableStack(ModItems.rod_quad_polonium), new int[] {4, 8});
		
		fuels.put(new ComparableStack(ModItems.rod_tritium), new int[] {1, 1});
		fuels.put(new ComparableStack(ModItems.rod_dual_tritium), new int[] {1, 2});
		fuels.put(new ComparableStack(ModItems.rod_quad_tritium), new int[] {1, 4});
		
		fuels.put(new ComparableStack(ModItems.rod_balefire), new int[] {2, 150});
		fuels.put(new ComparableStack(ModItems.rod_dual_balefire), new int[] {2, 300});
		fuels.put(new ComparableStack(ModItems.rod_quad_balefire), new int[] {2, 600});
		
		fuels.put(new ComparableStack(ModItems.rod_balefire_blazing), new int[] {4, 75});
		fuels.put(new ComparableStack(ModItems.rod_dual_balefire_blazing), new int[] {4, 150});
		fuels.put(new ComparableStack(ModItems.rod_quad_balefire_blazing), new int[] {4, 300});
	}
	
	/**
	 * Returns an integer array of the fuel value of a certain stack
	 * @param stack
	 * @return an integer array (possibly null) with two fields, the HEAT value and the amount of operations
	 */
	public static int[] getFuelValue(ItemStack stack) {
		
		if(stack == null)
			return null;
		
		ComparableStack sta = new ComparableStack(stack);
		int[] ret = fuels.get(sta);
		
		return ret;
	}
}
