package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.config.GeneralConfig;
import com.hbm.interfaces.IConsumer;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityFoodSynthesizer extends TileEntityMachineBase implements IConsumer
{
	private long power = 0;
	public static final long powerRate = 100000;
	public static final long maxPower = 1000000000;
	private int fuel = 0;
	public static final int fuelRatio = GeneralConfig.enableBabyMode ? 500 : 250;
	public static final int maxFuel = 32000;
	private static HashMap<Integer, Item> recipes = new HashMap<>();
	static
	{
		recipes.put(250, Items.apple);
		recipes.put(350, Items.potato);
		recipes.put(350, Items.baked_potato);
		recipes.put(300, ModItems.spongebob_macaroni);
	}
	public TileEntityFoodSynthesizer()
	{
		super(3);
	}

	@Override
	public void updateEntity()
	{
		// TODO Auto-generated method stub
		if (!worldObj.isRemote)
		{
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			// Add to fuel
			if (fuel + fuelRatio <= maxFuel && slots[1] != null && getStackInSlot(1).getItem() == ModItems.biomass)
			{
				fuel += fuelRatio;
				decrStackSize(1, 1);
			}
		}
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setInteger("fuel", fuel);
		networkPack(data, 10);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		power = nbt.getLong("power");
		fuel = nbt.getInteger("fuel");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setInteger("fuel", fuel);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		fuel = nbt.getInteger("fuel");
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
		return "container.foodSynthesizer";
	}
}
