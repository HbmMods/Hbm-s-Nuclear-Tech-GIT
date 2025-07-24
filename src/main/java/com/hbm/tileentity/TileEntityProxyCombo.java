package com.hbm.tileentity;

import api.hbm.block.ICrucibleAcceptor;
import com.hbm.handler.CompatHandler;
import com.hbm.handler.CompatHandler.OCComponent;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidConnectorMK2;
import api.hbm.fluidmk2.IFluidReceiverMK2;
import api.hbm.redstoneoverradio.IRORInfo;
import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import api.hbm.tile.IHeatSource;
import com.hbm.inventory.material.Mats;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({
		@Optional.Interface(iface = "com.hbm.handler.CompatHandler.OCComponent", modid = "opencomputers"),
		@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
})
public class TileEntityProxyCombo extends TileEntityProxyBase implements IEnergyReceiverMK2, ISidedInventory, IFluidReceiverMK2, IHeatSource, ICrucibleAcceptor, SimpleComponent, OCComponent, IRORValueProvider, IRORInteractive {
	
	TileEntity tile;
	boolean inventory;
	boolean power;
	boolean fluid;
	boolean heat;
	public boolean moltenMetal;

	// due to some issues with OC deciding that it's gonna call the component name function before the worldObj is loaded
	// the component name must be cached to prevent it from shitting itself
	String componentName = CompatHandler.nullComponent;

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
	
	/** Returns the actual tile entity that represents the core. Only for internal use, and EnergyControl. */
	public TileEntity getTile() {
		if(tile == null || tile.isInvalid()) {
			tile = this.getTE();
		}
		return tile;
	}
	
	/** Returns the core tile entity, or a delegate object. */
	protected Object getCoreObject() {
		return getTile();
	}

	@Override
	public void setPower(long i) {
		
		if(!power)
			return;
		
		if(getCoreObject() instanceof IEnergyReceiverMK2) {
			((IEnergyReceiverMK2)getCoreObject()).setPower(i);
		}
	}

	@Override
	public long getPower() {
		
		if(!power)
			return 0;
		
		if(getCoreObject() instanceof IEnergyReceiverMK2) {
			return ((IEnergyReceiverMK2)getCoreObject()).getPower();
		}
		
		return 0;
	}

	@Override
	public long getMaxPower() {
		
		if(!power)
			return 0;
		
		if(getCoreObject() instanceof IEnergyReceiverMK2) {
			return ((IEnergyReceiverMK2)getCoreObject()).getMaxPower();
		}
		
		return 0;
	}

	@Override
	public long transferPower(long power) {
		
		if(!this.power)
			return power;
		
		if(getCoreObject() instanceof IEnergyReceiverMK2) {
			return ((IEnergyReceiverMK2)getCoreObject()).transferPower(power);
		}
		
		return power;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		
		if(!power)
			return false;
		
		if(getCoreObject() instanceof IEnergyReceiverMK2) {
			return ((IEnergyReceiverMK2)getCoreObject()).canConnect(dir);
		}
		
		return true;
	}

	public static final FluidTank[] EMPTY_TANKS = new FluidTank[0];
	
	@Override
	public FluidTank[] getAllTanks() {
		if(!fluid) return EMPTY_TANKS;
		
		if(getCoreObject() instanceof IFluidReceiverMK2) {
			return ((IFluidReceiverMK2)getCoreObject()).getAllTanks();
		}
		
		return EMPTY_TANKS;
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long amount) {
		if(!fluid) return amount;
		
		if(getCoreObject() instanceof IFluidReceiverMK2) {
			return ((IFluidReceiverMK2)getCoreObject()).transferFluid(type, pressure, amount);
		}
		
		return amount;
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		if(!fluid) return 0;
		
		if(getCoreObject() instanceof IFluidReceiverMK2) {
			return ((IFluidReceiverMK2)getCoreObject()).getDemand(type, pressure);
		}
		
		return 0;
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		
		if(!this.fluid)
			return false;
		
		if(getCoreObject() instanceof IFluidConnectorMK2) {
			return ((IFluidConnectorMK2) getCoreObject()).canConnect(type, dir);
		}
		return true;
	}

	@Override
	public int getSizeInventory() {
		
		if(!inventory)
			return 0;
		
		if(getCoreObject() instanceof ISidedInventory) {
			return ((ISidedInventory)getCoreObject()).getSizeInventory();
		}
		
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		
		if(!inventory)
			return null;
		
		if(getCoreObject() instanceof ISidedInventory) {
			return ((ISidedInventory)getCoreObject()).getStackInSlot(slot);
		}
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		
		if(getCoreObject() instanceof ISidedInventory) {
			return ((ISidedInventory)getCoreObject()).decrStackSize(i, j);
		}
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		
		if(!inventory)
			return null;
		
		if(getCoreObject() instanceof ISidedInventory) {
			return ((ISidedInventory)getCoreObject()).getStackInSlotOnClosing(slot);
		}
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		
		if(!inventory)
			return;
		
		if(getCoreObject() instanceof ISidedInventory) {
			((ISidedInventory)getCoreObject()).setInventorySlotContents(slot, stack);
		}
	}

	@Override
	public String getInventoryName() {
		
		if(!inventory)
			return null;
		
		if(getCoreObject() instanceof ISidedInventory) {
			return ((ISidedInventory)getCoreObject()).getInventoryName();
		}
		
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		
		if(!inventory)
			return false;
		
		if(getCoreObject() instanceof ISidedInventory) {
			return ((ISidedInventory)getCoreObject()).hasCustomInventoryName();
		}
		
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		
		if(!inventory)
			return 0;
		
		if(getCoreObject() instanceof ISidedInventory) {
			return ((ISidedInventory)getCoreObject()).getInventoryStackLimit();
		}
		
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		
		if(!inventory)
			return false;
		
		if(getCoreObject() instanceof ISidedInventory) {
			return ((ISidedInventory)getCoreObject()).isUseableByPlayer(player);
		}
		
		return false;
	}

	@Override
	public void openInventory() {
		
		if(!inventory)
			return;
		
		if(getCoreObject() instanceof ISidedInventory) {
			((ISidedInventory)getCoreObject()).openInventory();
		}
	}

	@Override
	public void closeInventory() {
		
		if(!inventory)
			return;
		
		if(getCoreObject() instanceof ISidedInventory) {
			((ISidedInventory)getCoreObject()).closeInventory();
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		
		if(!inventory)
			return false;
		
		if(getCoreObject() instanceof ISidedInventory) {
			
			if(getCoreObject() instanceof IConditionalInvAccess) return ((IConditionalInvAccess) getCoreObject()).isItemValidForSlot(xCoord, yCoord, zCoord, slot, stack);
			
			return ((ISidedInventory)getCoreObject()).isItemValidForSlot(slot, stack);
		}
		
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		
		if(!inventory)
			return new int[0];
		
		if(getCoreObject() instanceof ISidedInventory) {
			
			if(getCoreObject() instanceof IConditionalInvAccess) return ((IConditionalInvAccess) getCoreObject()).getAccessibleSlotsFromSide(xCoord, yCoord, zCoord, side);
			
			return ((ISidedInventory)getCoreObject()).getAccessibleSlotsFromSide(side);
		}
		
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		
		if(!inventory)
			return false;
		
		if(getCoreObject() instanceof ISidedInventory) {
			
			if(getCoreObject() instanceof IConditionalInvAccess) return ((IConditionalInvAccess) getCoreObject()).canInsertItem(xCoord, yCoord, zCoord, i, stack, j);
			
			return ((ISidedInventory)getCoreObject()).canInsertItem(i, stack, j);
		}
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		
		if(!inventory)
			return false;
		
		if(getCoreObject() instanceof ISidedInventory) {
			
			if(getCoreObject() instanceof IConditionalInvAccess) return ((IConditionalInvAccess) getCoreObject()).canExtractItem(xCoord, yCoord, zCoord, i, stack, j);
			
			return ((ISidedInventory)getCoreObject()).canExtractItem(i, stack, j);
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
		if(Loader.isModLoaded("OpenComputers"))
			this.componentName = nbt.getString("ocname");

	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("inv", inventory);
		nbt.setBoolean("power", power);
		nbt.setBoolean("fluid", fluid);
		nbt.setBoolean("metal", moltenMetal);
		nbt.setBoolean("heat", heat);
		if(Loader.isModLoaded("OpenComputers"))
			nbt.setString("ocname", componentName);
	}

	@Override
	public int getHeatStored() {
		
		if(!this.heat)
			return 0;
		
		if(getCoreObject() instanceof IHeatSource) {
			return ((IHeatSource)getCoreObject()).getHeatStored();
		}
		
		return 0;
	}

	@Override
	public void useUpHeat(int heat) {
		
		if(!this.heat)
			return;
		
		if(getCoreObject() instanceof IHeatSource) {
			((IHeatSource)getCoreObject()).useUpHeat(heat);
		}
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack) {
		if(this.moltenMetal && getCoreObject() instanceof ICrucibleAcceptor){
			return ((ICrucibleAcceptor)getCoreObject()).canAcceptPartialPour(world, x, y, z, dX, dY, dZ, side, stack);
		}
		return false;
	}

	@Override
	public Mats.MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack) {
		if(this.moltenMetal && getCoreObject() instanceof ICrucibleAcceptor){
			return ((ICrucibleAcceptor)getCoreObject()).pour(world, x, y, z, dX, dY, dZ, side, stack);
		}
		return null;
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, Mats.MaterialStack stack) {
		if(this.moltenMetal && getCoreObject() instanceof ICrucibleAcceptor){
			return ((ICrucibleAcceptor)getCoreObject()).canAcceptPartialFlow(world, x, y, z, side, stack);
		}
		return false;
	}

	@Override
	public Mats.MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, Mats.MaterialStack stack) {
		if(this.moltenMetal && getCoreObject() instanceof ICrucibleAcceptor){
			return ((ICrucibleAcceptor)getCoreObject()).flow(world, x, y, z, side, stack);
		}
		return null;
	}

	@Override // please work
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		if(this.worldObj == null) // OC is going too fast, grab from NBT!
			return componentName;
		if(this.getCoreObject() instanceof OCComponent) {
			if (componentName == null || componentName.equals(OCComponent.super.getComponentName())) {
				componentName = ((OCComponent) this.getCoreObject()).getComponentName();
			}
			return componentName;
		}
		return OCComponent.super.getComponentName();
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public boolean canConnectNode(ForgeDirection side) {
		if(this.getCoreObject() instanceof OCComponent)
			return (this.getBlockMetadata() >= 6 && this.getBlockMetadata() <= 11)
					&& (power || fluid) &&
					((OCComponent) this.getCoreObject()).canConnectNode(side);
		return OCComponent.super.canConnectNode(null);
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		if(this.getCoreObject() instanceof OCComponent)
			return ((OCComponent) this.getCoreObject()).methods();
		return OCComponent.super.methods();
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		if(this.getCoreObject() instanceof OCComponent)
			return ((OCComponent) this.getCoreObject()).invoke(method, context, args);
		return OCComponent.super.invoke(null, null, null);
	}

	@Override
	public String[] getFunctionInfo() {
		if(getCoreObject() instanceof IRORInfo) return ((IRORInfo) getCoreObject()).getFunctionInfo();
		return new String[0];
	}

	@Override
	public String provideRORValue(String name) {
		if(getCoreObject() instanceof IRORValueProvider) return ((IRORValueProvider) getCoreObject()).provideRORValue(name);
		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		if(getCoreObject() instanceof IRORInteractive) return ((IRORInteractive) getCoreObject()).runRORFunction(name, params);
		return null;
	}
}
