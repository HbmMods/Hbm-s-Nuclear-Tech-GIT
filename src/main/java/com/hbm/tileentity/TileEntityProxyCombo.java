package com.hbm.tileentity;

import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;

import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityProxyCombo extends TileEntityProxyBase implements IEnergyUser, IFluidAcceptor, ISidedInventory {
	
	TileEntity tile;
	boolean inventory;
	boolean power;
	boolean fluid;
	
	public TileEntityProxyCombo() { }
	
	public TileEntityProxyCombo(boolean inventory, boolean power, boolean fluid) {
		this.inventory = inventory;
		this.power = power;
		this.fluid = fluid;
	}
	
	//fewer messy recursive operations
	public TileEntity getTile() {
		
		if(tile == null) {
			tile = this.getTE();
		}
		
		return tile;
	}

	@Override
	public void setFillstate(int fill, int index) {
		
		if(!fluid)
			return;
		
		if(getTile() instanceof IFluidAcceptor) {
			((IFluidAcceptor)getTile()).setFillstate(fill, index);
		}
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		if(!fluid)
			return;
		
		if(getTile() instanceof IFluidAcceptor) {
			((IFluidAcceptor)getTile()).setFluidFill(fill, type);
		}
	}

	@Override
	public void setType(FluidType type, int index) {
		
		if(!fluid)
			return;
		
		if(getTile() instanceof IFluidAcceptor) {
			((IFluidAcceptor)getTile()).setType(type, index);
		}
	}

	@Override
	public List<FluidTank> getTanks() {
		
		if(!fluid)
			return null;
		
		if(getTile() instanceof IFluidAcceptor) {
			return ((IFluidAcceptor)getTile()).getTanks();
		}
		
		return null;
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		if(!fluid)
			return 0;
		
		if(getTile() instanceof IFluidAcceptor) {
			return ((IFluidAcceptor)getTile()).getFluidFill(type);
		}
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(!fluid)
			return 0;
		
		if(getTile() instanceof IFluidAcceptor) {
			return ((IFluidAcceptor)getTile()).getMaxFluidFill(type);
		}
		
		return 0;
	}

	@Override
	public void setPower(long i) {
		
		if(!power)
			return;
		
		if(getTile() instanceof IEnergyUser) {
			((IEnergyUser)getTile()).setPower(i);
		}
	}

	@Override
	public long getPower() {
		
		if(!power)
			return 0;
		
		if(getTile() instanceof IEnergyConnector) {
			return ((IEnergyConnector)getTile()).getPower();
		}
		
		return 0;
	}

	@Override
	public long getMaxPower() {
		
		if(!power)
			return 0;
		
		if(getTile() instanceof IEnergyConnector) {
			return ((IEnergyConnector)getTile()).getMaxPower();
		}
		
		return 0;
	}

	@Override
	public long transferPower(long power) {
		
		if(!this.power)
			return 0;
		
		if(getTile() instanceof IEnergyConnector) {
			return ((IEnergyConnector)getTile()).transferPower(power);
		}
		
		return 0;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		
		if(!power)
			return false;
		
		if(getTile() instanceof IEnergyConnector) {
			return ((IEnergyConnector)getTile()).canConnect(dir);
		}
		
		return false;
	}

	@Override
	public int getSizeInventory() {
		
		if(!inventory)
			return 0;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).getSizeInventory();
		}
		
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		
		if(!inventory)
			return null;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).getStackInSlot(slot);
		}
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).decrStackSize(i, j);
		}
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		
		if(!inventory)
			return null;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).getStackInSlotOnClosing(slot);
		}
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		
		if(!inventory)
			return;
		
		if(getTile() instanceof ISidedInventory) {
			((ISidedInventory)getTile()).setInventorySlotContents(slot, stack);
		}
	}

	@Override
	public String getInventoryName() {
		
		if(!inventory)
			return null;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).getInventoryName();
		}
		
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		
		if(!inventory)
			return false;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).hasCustomInventoryName();
		}
		
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		
		if(!inventory)
			return 0;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).getInventoryStackLimit();
		}
		
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		
		if(!inventory)
			return false;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).isUseableByPlayer(player);
		}
		
		return false;
	}

	@Override
	public void openInventory() {
		
		if(!inventory)
			return;
		
		if(getTile() instanceof ISidedInventory) {
			((ISidedInventory)getTile()).openInventory();
		}
	}

	@Override
	public void closeInventory() {
		
		if(!inventory)
			return;
		
		if(getTile() instanceof ISidedInventory) {
			((ISidedInventory)getTile()).closeInventory();
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		
		if(!inventory)
			return false;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).isItemValidForSlot(slot, stack);
		}
		
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		
		if(!inventory)
			return new int[0];
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).getAccessibleSlotsFromSide(side);
		}
		
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		
		if(!inventory)
			return false;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).canInsertItem(i, stack, j);
		}
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		
		if(!inventory)
			return false;
		
		if(getTile() instanceof ISidedInventory) {
			return ((ISidedInventory)getTile()).canExtractItem(i, stack, j);
		}
		
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.inventory = nbt.getBoolean("inv");
		this.power = nbt.getBoolean("power");
		this.fluid = nbt.getBoolean("fluid");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("inv", inventory);
		nbt.setBoolean("power", power);
		nbt.setBoolean("fluid", fluid);
	}
}
