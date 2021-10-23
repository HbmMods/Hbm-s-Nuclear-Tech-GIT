package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.Beta;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.Library;
import com.hbm.saveddata.TimeSavedData;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
@Beta
public class TileEntityAtomicClock extends TileEntityMachineBase implements IConsumer, IFluidAcceptor
{
	private FluidTank tank = new FluidTank(FluidType.CRYOGEL, 64000, 0);
	private long power = 0;
	public static final long powerRate = 100000;
	public static final long maxPower = 50000000;
	public float time = 0.0F;
	public byte day = 1;
	public long year = 2300L;
	public boolean isOn = false;
	public long consumption = powerRate;
	private int[] upgrades = new int[] {0, 0};
	public TileEntityAtomicClock()
	{
		super(5);
	}

	@Override
	public void updateEntity()
	{
		// TODO Auto-generated method stub
		if (!worldObj.isRemote)
		{
			tank.updateTank(this);
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			updateUpgrades();
			TimeSavedData data = TimeSavedData.getData(getWorldObj());
			time = data.getTime();
			day = data.getDay();
			year = data.getYear();
			
			if (isOn && canOperate())
			{
				tank.decrementFill(1);
				power -= getConsumption();
			}
		}
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setFloat("time", time);
		data.setByte("day", day);
		data.setLong("year", year);
		data.setBoolean("isOn", isOn);
		data.setLong("consumption", consumption);
		networkPack(data, 100);
	}
	
	public boolean canOperate()
	{
		return tank.getFill() > 0 && power >= getConsumption();
	}
	
	@Override
	public void handleButtonPacket(int value, int meta)
	{
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "gui.button.press", 1.0F, 1.0F);
		isOn = !isOn;
	}
	
	private void updateUpgrades()
	{
		for (int i = 1; i < 5; i++)
		{
			if (getStackInSlot(i) == null || !(getStackInSlot(i).getItem() instanceof ItemMachineUpgrade))
				continue;
			
			if (getStackInSlot(i).getItem() == ModItems.upgrade_clock_1)
				upgrades[0] += 1;
			else if (getStackInSlot(i).getItem() == ModItems.upgrade_clock_2)
				upgrades[0] += 2;
			else if (getStackInSlot(i).getItem() == ModItems.upgrade_clock_3)
				upgrades[0] += 3;
			else if (getStackInSlot(i).getItem() == ModItems.upgrade_clock_4)
				upgrades[0] += 9;
			else if (getStackInSlot(i).getItem() == ModItems.upgrade_power_1)
				upgrades[1] -= 10000;
			else if (getStackInSlot(i).getItem() == ModItems.upgrade_power_2)
				upgrades[1] -= 25000;
			else if (getStackInSlot(i).getItem() == ModItems.upgrade_power_3)
				upgrades[1] -= 50000;
		}
		upgrades[0] = MathHelper.clamp_int(upgrades[0], 0, 9);
		upgrades[1] = MathHelper.clamp_int(upgrades[1], 0, 50000);
	}
	
	public long getConsumption()
	{
		consumption = powerRate + upgrades[1];
		return consumption;
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		power = nbt.getLong("power");
		time = nbt.getFloat("time");
		day = nbt.getByte("day");
		year = nbt.getLong("year");
		isOn = nbt.getBoolean("isOn");
		consumption = nbt.getLong("consumption");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setBoolean("isOn", isOn);
		tank.writeToNBT(nbt, "tank");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		isOn = nbt.getBoolean("isOn");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void setFillstate(int fill, int index)
	{
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type)
	{
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index)
	{
		tank.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks()
	{
		List<FluidTank> l = new ArrayList<FluidTank>(1);
		l.add(tank);
		return l;
	}

	@Override
	public int getFluidFill(FluidType type)
	{
		return tank.getFill();
	}

	@Override
	public int getMaxFluidFill(FluidType type)
	{
		return tank.getMaxFill();
	}

	@Override
	public void setPower(long i)
	{
		power = i;
	}

	@Override
	public long getPower()
	{
		return power;
	}

	@Override
	public long getMaxPower()
	{
		return maxPower;
	}

	@Override
	public String getName()
	{
		return "container.atomicClock";
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack)
	{
		super.setInventorySlotContents(i, itemStack);
//		System.out.println("Inserted item is: " + (itemStack == null ? "null" : itemStack.getItem().getUnlocalizedName()));
		if(itemStack != null && i >= 1 && i <= 5 && itemStack.getItem() instanceof ItemMachineUpgrade)
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
	}
}
