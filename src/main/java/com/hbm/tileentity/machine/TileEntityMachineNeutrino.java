package com.hbm.tileentity.machine;
import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachineNeutrino extends TileEntityMachineBase implements ISource, IFluidContainer, IFluidAcceptor {
	
	public long power;
	public int soundCycle = 0;
	public static final long maxPower = 500000000;
	public long powerCap = 500000000;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	public FluidTank tank;
	
	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 2 };
	
	public TileEntityMachineNeutrino() {
		super(5);
		tank = new FluidTank(FluidType.HEAVYWATER, 128000, 0);
	}
	
	@Override
	public String getName() {
		return "container.machineNeutrino";
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
				age++;
				if (age >= 20) {
					age = 0;
				}

				if (age == 9 || age == 19)
					ffgeuaInit();

				//Tank Management
				tank.setType(3, 4, slots);
				tank.loadTank(0, 1, slots);
				tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);

				FluidType type = tank.getTankType();
				powerCap = maxPower;
				
				// Battery Item
				power = Library.chargeItemsFromTE(slots, 2, power, powerCap);

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
			if (age >= 0 && age < 10) {
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