package com.hbm.tileentity.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.machine.MachineCoal;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

public class TileEntityMachineCoal extends TileEntity implements ISidedInventory, ISource, IFluidContainer, IFluidAcceptor {

	private ItemStack slots[];
	
	public long power;
	public int burnTime;
	public static final long maxPower = 100000;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	public FluidTank tank;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {0, 2};
	private static final int[] slots_side = new int[] {0, 2};
	
	private String customName;
	
	public TileEntityMachineCoal() {
		slots = new ItemStack[4];
		tank = new FluidTank(FluidType.WATER, 5000, 0);
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
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 0)
			if(stack.getItem() == ModItems.rod_water || stack.getItem() == ModItems.rod_dual_water || stack.getItem() == ModItems.rod_quad_water || stack.getItem() == Items.water_bucket)
				return true;
		if(i == 2)
			if(stack.getItem() instanceof ItemBattery)
				return true;
		if(i == 1)
			if(stack.getItem() == Items.coal || stack.getItem() == ModItems.powder_coal || stack.getItem() == Item.getItemFromBlock(Blocks.coal_block))
				return true;
		
		return false;
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
		
		this.power = nbt.getLong("powerTime");
		tank.readFromNBT(nbt, "water");
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
		nbt.setLong("powerTime", power);
		tank.writeToNBT(nbt, "water");
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
		if(i == 0)
			if(itemStack.getItem() == Items.bucket || itemStack.getItem() == ModItems.rod_empty || itemStack.getItem() == ModItems.rod_dual_empty || itemStack.getItem() == ModItems.rod_quad_empty)
				return true;
		if(i == 2)
			if (itemStack.getItem() instanceof ItemBattery && ItemBattery.getCharge(itemStack) == ItemBattery.getMaxChargeStatic(itemStack))
				return true;
		
		return false;
	}
	
	public long getPowerScaled(long i) {
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

		boolean flag = this.burnTime > 0;
		boolean flag1 = false;
		
		if(!worldObj.isRemote)
		{
		
			//Water
			tank.loadTank(0, 3, slots);
			
			tank.updateTank(xCoord, yCoord, zCoord);

			//Battery Item
			power = Library.chargeItemsFromTE(slots, 2, power, maxPower);
			
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
			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, power));
		}
		
		generate();
	}
	
	public void generate() {
		
		if(slots[1] != null && TileEntityFurnace.getItemBurnTime(slots[1]) > 0 && burnTime <= 0)
		{
			burnTime = TileEntityFurnace.getItemBurnTime(slots[1]) / 2;
			slots[1].stackSize -= 1;
			if(slots[1].stackSize == 0)
			{
				slots[1] = null;
			}
		}
		
		if(burnTime > 0)
		{
			burnTime--;
			
			if(tank.getFill() > 0)
			{
				tank.setFill(tank.getFill() - 1);
				
				if(power + 25 <= maxPower)
				{
					power += 25;
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
		if(slots[1] != null && slots[1].getItem() == ModItems.powder_coal)
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
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
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
	
	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}

	@Override
	public void setAFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public int getAFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public int getMaxAFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}
}
