package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.IOilAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineRefinery extends TileEntity implements ISidedInventory, IConsumer, IFluidContainer, IFluidAcceptor, IFluidSource {

	private ItemStack slots[];

	public int power = 0;
	public int sulfur = 0;
	public static final int maxSulfur = 1000;
	public static final int maxPower = 100000;
	public int age = 0;
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list1 = new ArrayList();
	public List<IFluidAcceptor> list2 = new ArrayList();
	public List<IFluidAcceptor> list3 = new ArrayList();
	public List<IFluidAcceptor> list4 = new ArrayList();

	private static final int[] slots_top = new int[] { 1 };
	private static final int[] slots_bottom = new int[] { 0, 2, 4, 6, 8, 10, 11};
	private static final int[] slots_side = new int[] { 0, 3, 5, 7, 9 };
	
	private String customName;
	
	public TileEntityMachineRefinery() {
		slots = new ItemStack[12];
		tanks = new FluidTank[5];
		tanks[0] = new FluidTank(FluidType.OIL, 64000, 0);
		tanks[1] = new FluidTank(FluidType.HEAVYOIL, 16000, 1);
		tanks[2] = new FluidTank(FluidType.NAPHTHA, 16000, 2);
		tanks[3] = new FluidTank(FluidType.LIGHTOIL, 16000, 3);
		tanks[4] = new FluidTank(FluidType.PETROLEUM, 16000, 4);
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineRefinery";
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
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=128;
		}
	}
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(i == 0 && stack.getItem() instanceof ItemBattery)
			return true;
		if(i == 1 && stack.getItem() == ModItems.canister_oil)
			return true;
		if(stack.getItem() == ModItems.canister_empty) {
			if(i == 3)
				return true;
			if(i == 5)
				return true;
			if(i == 7)
				return true;
			if(i == 9)
				return true;
		}
		
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

		power = nbt.getInteger("power");
		tanks[0].readFromNBT(nbt, "input");
		tanks[1].readFromNBT(nbt, "heavy");
		tanks[2].readFromNBT(nbt, "naphtha");
		tanks[3].readFromNBT(nbt, "light");
		tanks[4].readFromNBT(nbt, "petroleum");
		sulfur = nbt.getInteger("sulfur");
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
		nbt.setInteger("power", power);
		tanks[0].writeToNBT(nbt, "input");
		tanks[1].writeToNBT(nbt, "heavy");
		tanks[2].writeToNBT(nbt, "naphtha");
		tanks[3].writeToNBT(nbt, "light");
		tanks[4].writeToNBT(nbt, "petroleum");
		nbt.setInteger("sulfur", sulfur);
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
			if (itemStack.getItem() instanceof ItemBattery && ItemBattery.getCharge(itemStack) == 0)
				return true;
		if(i == 2)
			return true;
		if(i == 4)
			return true;
		if(i == 6)
			return true;
		if(i == 8)
			return true;
		if(i == 10)
			return true;
		if(i == 11)
			return true;
		
		return false;
	}
	
	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);

			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19) {
				fillFluidInit(tanks[1].getTankType());
				fillFluidInit(tanks[2].getTankType());
				fillFluidInit(tanks[3].getTankType());
				fillFluidInit(tanks[4].getTankType());
			}
			
			tanks[0].loadTank(1, 2, slots);
			
			int ho = 50;
			int nt = 25;
			int lo = 15;
			int pe = 10;
			
			if(power >= 50 && tanks[0].getFill() >= 100 &&
					tanks[1].getFill() + ho <= tanks[1].getMaxFill() && 
					tanks[2].getFill() + nt <= tanks[2].getMaxFill() && 
					tanks[3].getFill() + lo <= tanks[3].getMaxFill() && 
					tanks[4].getFill() + pe <= tanks[4].getMaxFill()) {

				tanks[0].setFill(tanks[0].getFill() - 100);
				tanks[1].setFill(tanks[1].getFill() + ho);
				tanks[2].setFill(tanks[2].getFill() + nt);
				tanks[3].setFill(tanks[3].getFill() + lo);
				tanks[4].setFill(tanks[4].getFill() + pe);
				sulfur += 1;
				power -= 100;
			}

			tanks[1].unloadTank(3, 4, slots);
			tanks[2].unloadTank(5, 6, slots);
			tanks[3].unloadTank(7, 8, slots);
			tanks[4].unloadTank(9, 10, slots);
			
			for(int i = 0; i < 5; i++) {
				tanks[i].updateTank(xCoord, yCoord, zCoord);
			}
			
			if(sulfur >= maxSulfur) {
				if(slots[11] == null) {
					slots[11] = new ItemStack(ModItems.sulfur);
					sulfur -= maxSulfur;
				} else if(slots[11] != null && slots[11].getItem() == ModItems.sulfur && slots[11].stackSize < slots[11].getMaxStackSize()) {
					slots[11].stackSize++;
					sulfur -= maxSulfur;
				}
			}
		}
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	@Override
	public void setPower(int i) {
		power = i;
		
	}

	@Override
	public int getPower() {
		return power;
		
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 2, getTact(), type);
		
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getSFluidFill(FluidType type) {
		if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if(type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getFill();
		else if(type.name().equals(tanks[3].getTankType().name()))
			return tanks[3].getFill();
		else if(type.name().equals(tanks[4].getTankType().name()))
			return tanks[4].getFill();
		
		return 0;
	}

	@Override
	public void setSFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if(type.name().equals(tanks[2].getTankType().name()))
			tanks[2].setFill(i);
		else if(type.name().equals(tanks[3].getTankType().name()))
			tanks[3].setFill(i);
		else if(type.name().equals(tanks[4].getTankType().name()))
			tanks[4].setFill(i);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type.name().equals(tanks[1].getTankType().name()))
			return list1;
		if(type.name().equals(tanks[2].getTankType().name()))
			return list2;
		if(type.name().equals(tanks[3].getTankType().name()))
			return list3;
		if(type.name().equals(tanks[4].getTankType().name()))
			return list4;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type.name().equals(tanks[1].getTankType().name()))
			list1.clear();
		if(type.name().equals(tanks[2].getTankType().name()))
			list2.clear();
		if(type.name().equals(tanks[3].getTankType().name()))
			list3.clear();
		if(type.name().equals(tanks[4].getTankType().name()))
			list4.clear();
	}

	@Override
	public void setAFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
	}

	@Override
	public int getAFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else
			return 0;
	}

	@Override
	public int getMaxAFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if(index < 5 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if(index < 5 && tanks[index] != null)
			tanks[index].setTankType(type);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
