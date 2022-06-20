package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineBoiler;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.lib.Library;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityMachineBoiler extends TileEntity implements ISidedInventory, IFluidContainer, IFluidAcceptor, IFluidSource, IFluidStandardTransceiver {

	private ItemStack slots[];
	
	public int burnTime;
	public int heat = 2000;
	public static final int maxHeat = 50000;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	public FluidTank[] tanks;
	
	private String customName;
	
	public TileEntityMachineBoiler() {
		slots = new ItemStack[7];
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.WATER, 8000, 0);
		tanks[1] = new FluidTank(Fluids.STEAM, 8000, 1);
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineBoiler";
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
		return i == 4 && TileEntityFurnace.getItemBurnTime(stack) > 0;
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

		heat = nbt.getInteger("heat");
		burnTime = nbt.getInteger("burnTime");
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
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
		nbt.setInteger("heat", heat);
		nbt.setInteger("burnTime", burnTime);
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
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
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 4 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 4 && !this.isItemValidForSlot(i, itemStack);
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / maxHeat;
	}
	
	@Override
	public void updateEntity() {
		
		boolean mark = false;
		
		if(!worldObj.isRemote)
		{
			
			this.subscribeToAllAround(tanks[0].getTankType(), this);
			this.sendFluidToAll(tanks[1].getTankType(), this);
			
			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19)
				fillFluidInit(tanks[1].getTankType());

			tanks[0].setType(0, 1, slots);
			tanks[0].loadTank(2, 3, slots);
			
			Object[] outs = MachineRecipes.getBoilerOutput(tanks[0].getTankType());
			
			if(outs == null) {
				tanks[1].setTankType(Fluids.NONE);
			} else {
				tanks[1].setTankType((FluidType) outs[0]);
			}
			
			tanks[1].unloadTank(5, 6, slots);
			
			for(int i = 0; i < 2; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			boolean flag1 = false;
			
			if(heat > 2000) {
				heat -= 15;
			}
			
			if(burnTime > 0) {
				burnTime--;
				heat += 50;
				flag1 = true;
			}
			
			if(burnTime == 0 && flag1) {
				mark = true;
			}
			
			if(burnTime <= 0 && worldObj.getBlock(xCoord, yCoord, zCoord) == ModBlocks.machine_boiler_on)
				MachineBoiler.updateBlockState(false, worldObj, xCoord, yCoord, zCoord);
			
			if(heat > maxHeat)
				heat = maxHeat;
			
			if(burnTime == 0 && TileEntityFurnace.getItemBurnTime(slots[4]) > 0) {
				burnTime = (int) (TileEntityFurnace.getItemBurnTime(slots[4]) * 0.25);
				slots[4].stackSize--;
				
				if(slots[4].stackSize <= 0) {
					
					if(slots[4].getItem().getContainerItem() != null)
						slots[4] = new ItemStack(slots[4].getItem().getContainerItem());
					else
						slots[4] = null;
				}
				
				if(!flag1) {
					mark = true;
				}
			}
			
			if(burnTime > 0 && worldObj.getBlock(xCoord, yCoord, zCoord) == ModBlocks.machine_boiler_off)
				MachineBoiler.updateBlockState(true, worldObj, xCoord, yCoord, zCoord);
			
			if(outs != null) {
				
				for(int i = 0; i < (heat / ((Integer)outs[3]).intValue()); i++) {
					if(tanks[0].getFill() >= ((Integer)outs[2]).intValue() && tanks[1].getFill() + ((Integer)outs[1]).intValue() <= tanks[1].getMaxFill()) {
						tanks[0].setFill(tanks[0].getFill() - ((Integer)outs[2]).intValue());
						tanks[1].setFill(tanks[1].getFill() + ((Integer)outs[1]).intValue());
						
						if(i == 0)
							heat -= 25;
						else
							heat -= 40;
					}
				}
			}
			
			if(heat < 2000) {
				heat = 2000;
			}

			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, heat, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, burnTime, 1), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
		
		if(mark) {
			this.markDirty();
		}
	}
	
	public boolean isItemValid() {

		if(slots[1] != null && TileEntityFurnace.getItemBurnTime(slots[1]) > 0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
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
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}
}
