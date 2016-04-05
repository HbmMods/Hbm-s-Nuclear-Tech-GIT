package com.hbm.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

public class TileEntityMachineCoal extends TileEntity implements ISidedInventory, ISource {

	private ItemStack slots[];
	
	public int power;
	public int water;
	public int burnTime;
	public static final int maxPower = 10000;
	public static final int maxWater = 10000;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2};
	private static final int[] slots_side = new int[] {0};
	
	private String customName;
	
	public TileEntityMachineCoal() {
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineCoal";
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
		
		this.power = nbt.getShort("powerTime");
		this.water = nbt.getShort("water");
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
		nbt.setShort("powerTime", (short) power);
		nbt.setShort("water", (short) water);
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
	
	public int getWaterScaled(int i) {
		return (water * i) / maxWater;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void updateEntity() {
		age++;
		if(age >= 20)
		{
			age = 0;
		}
		
		if(age == 9 || age == 19)
			ffgeuaInit();
		
		//Water
		if(slots[0] != null && slots[0].getItem() == Items.water_bucket && this.water + 2500 <= maxWater)
		{
			this.slots[0].stackSize--;
			this.water += 2500;
			if(this.slots[0].stackSize == 0)
			{
				this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
			}
		}
		if(slots[0] != null && slots[0].getItem() == ModItems.rod_water && this.water + 2500 <= maxWater)
		{
			this.slots[0].stackSize--;
			this.water += 2500;
			if(this.slots[0].stackSize == 0)
			{
				this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
			}
		}
		if(slots[0] != null && slots[0].getItem() == ModItems.rod_dual_water && this.water + 5000 <= maxWater)
		{
			this.slots[0].stackSize--;
			this.water += 5000;
			if(this.slots[0].stackSize == 0)
			{
				this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
			}
		}
		if(slots[0] != null && slots[0].getItem() == ModItems.rod_quad_water && this.water + 10000 <= maxWater)
		{
			this.slots[0].stackSize--;
			this.water += 10000;
			if(this.slots[0].stackSize == 0)
			{
				this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
			}
		}
		if(slots[0] != null && slots[0].getItem() == ModItems.inf_water)
		{
			this.water = this.maxWater;
		}

		//Battery Item
		if(power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.battery_generic && slots[2].getItemDamage() > 0)
		{
			power -= 100;
			slots[2].setItemDamage(slots[2].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.battery_advanced && slots[2].getItemDamage() > 0)
		{
			power -= 100;
			slots[2].setItemDamage(slots[2].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.battery_schrabidium && slots[2].getItemDamage() > 0)
		{
			power -= 100;
			slots[2].setItemDamage(slots[2].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.factory_core_titanium && slots[2].getItemDamage() > 0)
		{
			power -= 100;
			slots[2].setItemDamage(slots[2].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.factory_core_advanced && slots[2].getItemDamage() > 0)
		{
			power -= 100;
			slots[2].setItemDamage(slots[2].getItemDamage() - 1);
		}

		boolean flag = this.burnTime > 0;
		boolean flag1 = false;
		
		if(!worldObj.isRemote)
		{
			
			boolean trigger = true;
			
			if(isItemValid() && this.burnTime == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
                MachineCoal.updateBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
		}
		
		generate();
	}
	
	public void generate() {
		if(slots[1] != null && slots[1].getItem() == Items.coal && burnTime <= 0)
		{
			slots[1].stackSize -= 1;
			burnTime = 200;
			if(slots[1].stackSize == 0)
			{
				slots[1] = null;
			}
		}
		if(slots[1] != null && slots[1].getItem() == Item.getItemFromBlock(Blocks.coal_block) && burnTime <= 0)
		{
			slots[1].stackSize -= 1;
			burnTime = 2000;
			if(slots[1].stackSize == 0)
			{
				slots[1] = null;
			}
		}
		
		if(burnTime > 0)
		{
			burnTime--;
			
			if(water > 0)
			{
				water -= 1;
				
				if(power + 10 <= maxPower)
				{
					power += 10;
				} else {
					power = maxPower;
				}
			}
		}
	}
	
	public boolean isItemValid() {
		
		if(slots[1] != null && slots[1].getItem() == Items.coal)
		{
			return true;
		}
		if(slots[1] != null && slots[1].getItem() == Item.getItemFromBlock(Blocks.coal_block))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		Block block = this.worldObj.getBlock(x, y, z);
		TileEntity tileentity = this.worldObj.getTileEntity(x, y, z);

		if(block == ModBlocks.factory_titanium_conductor && this.worldObj.getBlock(x, y + 1, z) == ModBlocks.factory_titanium_core)
		{
			tileentity = this.worldObj.getTileEntity(x, y + 1, z);
		}
		if(block == ModBlocks.factory_titanium_conductor && this.worldObj.getBlock(x, y - 1, z) == ModBlocks.factory_titanium_core)
		{
			tileentity = this.worldObj.getTileEntity(x, y - 1, z);
		}
		if(block == ModBlocks.factory_advanced_conductor && this.worldObj.getBlock(x, y + 1, z) == ModBlocks.factory_advanced_core)
		{
			tileentity = this.worldObj.getTileEntity(x, y + 1, z);
		}
		if(block == ModBlocks.factory_advanced_conductor && this.worldObj.getBlock(x, y - 1, z) == ModBlocks.factory_advanced_core)
		{
			tileentity = this.worldObj.getTileEntity(x, y - 1, z);
		}
		
		if(tileentity instanceof IConductor)
		{
			if(tileentity instanceof TileEntityCable)
			{
				if(Library.checkUnionList(((TileEntityCable)tileentity).uoteab, this))
				{
					for(int i = 0; i < ((TileEntityCable)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityCable)tileentity).uoteab.get(i).source == this)
						{
							if(((TileEntityCable)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityCable)tileentity).uoteab.get(i).ticked = newTact;
								ffgeua(x, y + 1, z, getTact());
								ffgeua(x, y - 1, z, getTact());
								ffgeua(x - 1, y, z, getTact());
								ffgeua(x + 1, y, z, getTact());
								ffgeua(x, y, z - 1, getTact());
								ffgeua(x, y, z + 1, getTact());
							}
						}
					}
				} else {
					((TileEntityCable)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(this, newTact));
				}
			}
			if(tileentity instanceof TileEntityWireCoated)
			{
				if(Library.checkUnionList(((TileEntityWireCoated)tileentity).uoteab, this))
				{
					for(int i = 0; i < ((TileEntityWireCoated)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityWireCoated)tileentity).uoteab.get(i).source == this)
						{
							if(((TileEntityWireCoated)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityWireCoated)tileentity).uoteab.get(i).ticked = newTact;
								ffgeua(x, y + 1, z, getTact());
								ffgeua(x, y - 1, z, getTact());
								ffgeua(x - 1, y, z, getTact());
								ffgeua(x + 1, y, z, getTact());
								ffgeua(x, y, z - 1, getTact());
								ffgeua(x, y, z + 1, getTact());
							}
						}
					}
				} else {
					((TileEntityWireCoated)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(this, newTact));
				}
			}
		}
		
		if(tileentity instanceof IConsumer && newTact && !(tileentity instanceof TileEntityMachineBattery && ((TileEntityMachineBattery)tileentity).conducts))
		{
			list.add((IConsumer)tileentity);
		}
		
		if(!newTact)
		{
			int size = list.size();
			if(size > 0)
			{
				int part = this.power / size;
				for(IConsumer consume : list)
				{
					if(consume.getPower() < consume.getMaxPower())
					{
						if(consume.getMaxPower() - consume.getPower() >= part)
						{
							this.power -= part;
							consume.setPower(consume.getPower() + part);
						} else {
							this.power -= consume.getMaxPower() - consume.getPower();
							consume.setPower(consume.getMaxPower());
						}
					}
				}
			}
			list.clear();
		}
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 1, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
	}
	
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}
}
