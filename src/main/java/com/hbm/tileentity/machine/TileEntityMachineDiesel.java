package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineDiesel extends TileEntityMachineBase implements IEnergyGenerator, IFluidContainer, IFluidAcceptor, IFluidStandardReceiver {

	public long power;
	public int soundCycle = 0;
	public static final long maxPower = 50000;
	public long powerCap = 50000;
	public FluidTank tank;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 2 };

	public TileEntityMachineDiesel() {
		super(5);
		tank = new FluidTank(Fluids.DIESEL, 16000, 0);
	}

	@Override
	public String getName() {
		return "container.machineDiesel";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if (i == 0)
			if (FluidContainerRegistry.getFluidContent(stack, tank.getTankType()) > 0)
				return true;
		if (i == 2)
			if (stack.getItem() instanceof IBatteryItem)
				return true;

		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("powerTime");
		this.powerCap = nbt.getLong("powerCap");
		tank.readFromNBT(nbt, "fuel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("powerTime", power);
		nbt.setLong("powerCap", powerCap);
		tank.writeToNBT(nbt, "fuel");
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if (i == 1)
			if (itemStack.getItem() == ModItems.canister_empty || itemStack.getItem() == ModItems.tank_steel)
				return true;
		if (i == 2)
			if (itemStack.getItem() instanceof IBatteryItem && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == ((IBatteryItem)itemStack.getItem()).getMaxCharge())
				return true;

		return false;
	}

	public long getPowerScaled(long i) {
		return (power * i) / powerCap;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);

			//Tank Management
			FluidType last = tank.getTankType();
			if(tank.setType(3, 4, slots)) this.unsubscribeToAllAround(last, this);
			tank.loadTank(0, 1, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			this.subscribeToAllAround(tank.getTankType(), this);

			FluidType type = tank.getTankType();
			if(type == Fluids.NITAN)
				powerCap = maxPower * 10;
			else
				powerCap = maxPower;
			
			// Battery Item
			power = Library.chargeItemsFromTE(slots, 2, power, powerCap);

			generate();

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("power", (int) power);
			data.setInteger("powerCap", (int) powerCap);
			this.networkPack(data, 50);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {

		power = data.getInteger("power");
		powerCap = data.getInteger("powerCap");
	}
	
	public boolean hasAcceptableFuel() {
		return getHEFromFuel() > 0;
	}
	
	public static HashMap<FuelGrade, Double> fuelEfficiency = new HashMap();
	
	static {
		fuelEfficiency.put(FuelGrade.MEDIUM,	0.9D);
		fuelEfficiency.put(FuelGrade.HIGH,		1.0D);
		fuelEfficiency.put(FuelGrade.AERO,		0.1D);
	}
	
	public long getHEFromFuel() {
		return getHEFromFuel(tank.getTankType());
	}
	
	public static long getHEFromFuel(FluidType type) {
		
		if(type.hasTrait(FT_Combustible.class)) {
			FT_Combustible fuel = type.getTrait(FT_Combustible.class);
			FuelGrade grade = fuel.getGrade();
			double efficiency = fuelEfficiency.containsKey(grade) ? fuelEfficiency.get(grade) : 0;
			
			if(fuel.getGrade() != FuelGrade.LOW) {
				return (long) (fuel.getCombustionEnergy() / 1000L * efficiency);
			}
		}
		
		return 0;
	}

	public void generate() {
		
		if (hasAcceptableFuel()) {
			if (tank.getFill() > 0) {
				
				if (soundCycle == 0) {
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fireworks.blast", 1.5F * this.getVolume(3), 0.5F);
				}
				soundCycle++;

				if (soundCycle >= 3)
					soundCycle = 0;

				tank.setFill(tank.getFill() - 1);
				if (tank.getFill() < 0)
					tank.setFill(0);

				if (power + getHEFromFuel() <= powerCap) {
					power += getHEFromFuel();
				} else {
					power = powerCap;
				}
			}
		}
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type == this.tank.getTankType() ? tank.getMaxFill() : 0;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type == this.tank.getTankType() ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == tank.getTankType())
			tank.setFill(i);
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}
}
