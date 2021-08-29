package com.hbm.saveddata;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class TimeSavedData extends WorldSavedData
{
	private static TimeSavedData currentInstance;
	private World worldObj;
	/** Some 300 years into the future initially **/
	private long year = 2300L;
	/** 100 days a year **/
	private byte day = 1;
	/** 10 hours a day **/
	private float time = 0.0F;
	// Time Doctor Freeman?
	// Is it really that time again?
	public TimeSavedData(String name)
	{
		super(name);
	}
	
	public TimeSavedData(World worldIn)
	{
		super("hbmtimereference");
		year += worldIn.rand.nextInt(50);
		worldObj = worldIn;
		markDirty();
	}
	
	private static byte[] toByteArray(Integer input)
	{
		return input.toString().getBytes();
	}
	private static byte[] toByteArray(Double input)
	{
		return input.toString().getBytes();
	}


	public long getYear()
	{
		return year;
	}
	public byte getDay()
	{
		return day;
	}
	public float getTime()
	{
		return time;
	}
	
	public static void setDate(World worldIn, float time, int day, long year, boolean increment)
	{
		TimeSavedData date = getData(worldIn);
		if (increment)
			date.incrementDate(time, day, year);
		else
			date.setDate(time, day, year);
	}
	
	public void setDate(float time, int day, long year)
	{
		this.time = time;
		this.day = (byte) day;
		this.year = year;
		markDirty();
	}
	
	public void incrementDate(float time, int day, long year)
	{
		this.time += time;
		this.day += day;
		this.year += year;
		markDirty();
	}
	
	public static TimeSavedData getData(World worldIn)
	{
		if (currentInstance != null && currentInstance.worldObj.equals(worldIn))
			return currentInstance;
		
		TimeSavedData date = (TimeSavedData) worldIn.perWorldStorage.loadData(TimeSavedData.class, "date");
		if (date == null)
		{
			worldIn.perWorldStorage.setData("date", new TimeSavedData(worldIn));
			date = (TimeSavedData) worldIn.perWorldStorage.loadData(TimeSavedData.class, "date");
		}
		date.worldObj = worldIn;
		currentInstance = date;
		return currentInstance;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		year = nbt.getLong("year");
		day = nbt.getByte("day");
		time = nbt.getFloat("time");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setLong("year", year);
		nbt.setByte("day", day);
		nbt.setFloat("time", time);
	}
}
