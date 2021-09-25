package com.hbm.tileentity.machine;

import java.util.List;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.nbt.NBTTagCompound;
@Beta
public class TileEntityFENSU2 extends TileEntityMachineBase implements IConsumer
{
	private double charge = 0;
	public static final double maxCharge = Double.MAX_VALUE;
	public TileEntityFENSU2()
	{
		super(4);
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
		data.setDouble("power", charge);
		networkPack(data, 25);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		charge = nbt.getDouble("power");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setDouble("power", charge);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		charge = nbt.getDouble("power");
	}
	
	@Override
	public void setPower(long i)
	{
		charge = i;
	}
	@Override
	public long getPower()
	{
		return (long) charge;
	}
	@Override
	public long getMaxPower()
	{
		return (long) maxCharge;
	}
}
