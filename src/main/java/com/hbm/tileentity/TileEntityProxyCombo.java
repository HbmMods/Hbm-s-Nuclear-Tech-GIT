package com.hbm.tileentity;


import api.hbm.block.ICrucibleAcceptor;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.fluid.FluidType;

import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidConnector;
import api.hbm.tile.IHeatSource;
import com.hbm.inventory.material.Mats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityProxyCombo extends TileEntityProxyBase implements IEnergyUser, IFluidAcceptor, ISidedInventory, IFluidConnector, IHeatSource, ICrucibleAcceptor {
	
	TileEntity tile;
	boolean inventory;
	boolean power;
	boolean fluid;
	boolean heat;
	public boolean moltenMetal;
	
	public TileEntityProxyCombo() { }
	
	public TileEntityProxyCombo(boolean inventory, boolean power, boolean fluid) {
		this.inventory = inventory;
		this.power = power;
		this.fluid = fluid;
	}
	
	public TileEntityProxyCombo inventory() {
		this.inventory = true;
		return this;
	}
	
	public TileEntityProxyCombo power() {
		this.power = true;
		return this;
	}
	public TileEntityProxyCombo moltenMetal() {
		this.moltenMetal = true;
		return this;
	}
	public TileEntityProxyCombo fluid() {
		this.fluid = true;
		return this;
	}
	
	public TileEntityProxyCombo heatSource() {
		this.heat = true;
		return this;
	}
	
	//fewer messy recursive operations
	public TileEntity getTile() {
		
		if(tile == null || tile.isInvalid()) {
			tile = this.getTE();
		}
		
		return tile;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		
		if(!fluid)
			return;
		
		if(getTile() instanceof IFluidContainer) {
			((IFluidContainer)getTile()).setFillForSync(fill, index);
		}
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		if(!fluid)
			return;
		
		if(getTile() instanceof IFluidContainer) {
			((IFluidContainer)getTile()).setFluidFill(fill, type);
		}
	}

	@Override
	public int getFluidFillForReceive(FluidType type) {
		
		if(!fluid)
			return 0;
		
		if(getTile() instanceof IFluidAcceptor) {
			return ((IFluidAcceptor)getTile()).getFluidFillForReceive(type);
		}
		return 0;
	}

	@Override
	public int getMaxFluidFillForReceive(FluidType type) {
		
		if(!fluid)
			return 0;
		
		if(getTile() instanceof IFluidAcceptor) {
			return ((IFluidAcceptor)getTile()).getMaxFluidFillForReceive(type);
		}
		
		return 0;
	}

	@Override
	public void receiveFluid(int amount, FluidType type) {
		
		if(!fluid)
			return;
		
		if(getTile() instanceof IFluidAcceptor) {
			((IFluidAcceptor)getTile()).receiveFluid(amount, type);
		}
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		
		if(!fluid)
			return;
		
		if(getTile() instanceof IFluidContainer) {
			((IFluidContainer)getTile()).setTypeForSync(type, index);
		}
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		if(!fluid)
			return 0;
		
		if(getTile() instanceof IFluidContainer) {
			return ((IFluidContainer)getTile()).getFluidFill(type);
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
			return power;
		
		if(getTile() instanceof IEnergyConnector) {
			return ((IEnergyConnector)getTile()).transferPower(power);
		}
		
		return power;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		
		if(!power)
			return false;
		
		if(getTile() instanceof IEnergyConnector) {
			return ((IEnergyConnector)getTile()).canConnect(dir);
		}
		
		return true;
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
			
			if(getTile() instanceof IConditionalInvAccess) return ((IConditionalInvAccess) getTile()).isItemValidForSlot(xCoord, yCoord, zCoord, slot, stack);
			
			return ((ISidedInventory)getTile()).isItemValidForSlot(slot, stack);
		}
		
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		
		if(!inventory)
			return new int[0];
		
		if(getTile() instanceof ISidedInventory) {
			
			if(getTile() instanceof IConditionalInvAccess) return ((IConditionalInvAccess) getTile()).getAccessibleSlotsFromSide(xCoord, yCoord, zCoord, side);
			
			return ((ISidedInventory)getTile()).getAccessibleSlotsFromSide(side);
		}
		
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		
		if(!inventory)
			return false;
		
		if(getTile() instanceof ISidedInventory) {
			
			if(getTile() instanceof IConditionalInvAccess) return ((IConditionalInvAccess) getTile()).canInsertItem(xCoord, yCoord, zCoord, i, stack, j);
			
			return ((ISidedInventory)getTile()).canInsertItem(i, stack, j);
		}
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		
		if(!inventory)
			return false;
		
		if(getTile() instanceof ISidedInventory) {
			
			if(getTile() instanceof IConditionalInvAccess) return ((IConditionalInvAccess) getTile()).canExtractItem(xCoord, yCoord, zCoord, i, stack, j);
			
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
		this.moltenMetal = nbt.getBoolean("metal");
		this.heat = nbt.getBoolean("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("inv", inventory);
		nbt.setBoolean("power", power);
		nbt.setBoolean("fluid", fluid);
		nbt.setBoolean("metal", moltenMetal);
		nbt.setBoolean("heat", heat);
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		
		if(!this.fluid)
			return fluid;
		
		if(getTile() instanceof IFluidConnector) {
			return ((IFluidConnector)getTile()).transferFluid(type, pressure, fluid);
		}
		return fluid;
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		
		if(!this.fluid)
			return 0;
		
		if(getTile() instanceof IFluidConnector) {
			return ((IFluidConnector)getTile()).getDemand(type, pressure);
		}
		return 0;
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		
		if(!this.fluid)
			return false;
		
		if(getTile() instanceof IFluidConnector) {
			return ((IFluidConnector)getTile()).canConnect(type, dir);
		}
		return true;
	}

	@Override
	public int getHeatStored() {
		
		if(!this.heat)
			return 0;
		
		if(getTile() instanceof IHeatSource) {
			return ((IHeatSource)getTile()).getHeatStored();
		}
		
		return 0;
	}

	@Override
	public void useUpHeat(int heat) {
		
		if(!this.heat)
			return;
		
		if(getTile() instanceof IHeatSource) {
			((IHeatSource)getTile()).useUpHeat(heat);
		}
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack) {
		if(this.moltenMetal && getTile() instanceof ICrucibleAcceptor){
			return ((ICrucibleAcceptor)getTile()).canAcceptPartialPour(world, x, y, z, dX, dY, dZ, side, stack);
		}
		return false;
	}

	@Override
	public Mats.MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack) {
		if(this.moltenMetal && getTile() instanceof ICrucibleAcceptor){
			return ((ICrucibleAcceptor)getTile()).pour(world, x, y, z, dX, dY, dZ, side, stack);
		}
		return null;
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, Mats.MaterialStack stack) {
		if(this.moltenMetal && getTile() instanceof ICrucibleAcceptor){
			return ((ICrucibleAcceptor)getTile()).canAcceptPartialFlow(world, x, y, z, side, stack);
		}
		return false;
	}

	@Override
	public Mats.MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, Mats.MaterialStack stack) {
		if(this.moltenMetal && getTile() instanceof ICrucibleAcceptor){
			return ((ICrucibleAcceptor)getTile()).flow(world, x, y, z, side, stack);
		}
		return null;
	}
}
