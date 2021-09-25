package com.hbm.tileentity.machine;

import java.math.BigInteger;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.IConsumer;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.nbt.NBTTagCompound;
@Beta
public class TileEntityChungusBattery extends TileEntityMachineBase implements IConsumer
{
	private BigInteger power = BigInteger.valueOf(0);
	public static final BigInteger maxPower = new BigInteger(String.valueOf(Double.POSITIVE_INFINITY));
	public TileEntityChungusBattery()
	{
		super(4);
	}

	@Override
	public void setPower(long i)
	{
		power = BigInteger.valueOf(i);
	}

	@Override
	public long getPower()
	{
		return power.longValue();
	}

	@Override
	public long getMaxPower()
	{
		return maxPower.longValue();
	}

	@Override
	public String getName()
	{
		return "container.pain";
	}

	@Override
	public void updateEntity()
	{
		// TODO Auto-generated method stub

		NBTTagCompound data = new NBTTagCompound();
		data.setByteArray("power", power.toByteArray());
		networkPack(data, 25);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		power = new BigInteger(nbt.getByteArray("power"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setByteArray("power", power.toByteArray());
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		power = new BigInteger(nbt.getByteArray("power"));
	}
}
