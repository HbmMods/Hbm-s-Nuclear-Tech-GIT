package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineDiesel extends TileEntityMachineBase implements IEnergyGenerator, IFluidContainer, IFluidAcceptor {

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
		tank = new FluidTank(FluidType.DIESEL, 16000, 0);
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
			tank.setType(3, 4, slots);
			tank.loadTank(0, 1, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);

			FluidType type = tank.getTankType();
			if(type.name().equals(FluidType.NITAN.name()))
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
	
	public static final HashMap<FluidType, Integer> fuels = new HashMap();
	
	static {
		fuels.put(FluidType.HYDROGEN,	10);
		fuels.put(FluidType.DIESEL,		500);
		fuels.put(FluidType.PETROIL,	300);
		fuels.put(FluidType.BIOFUEL,	400);
		fuels.put(FluidType.GASOLINE,	1500);
		fuels.put(FluidType.NITAN,		5000);
		fuels.put(FluidType.LPG,		450);
		fuels.put(FluidType.ETHANOL,	200);
	}
	
	public int getHEFromFuel() {
		FluidType type = tank.getTankType();
		Integer value = fuels.get(type);
		return value != null ? value : 0;
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
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tank);
		
		return list;
	}
}
