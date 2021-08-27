package com.hbm.saveddata;

import codechicken.nei.WorldOverlayRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class TimeSavedData extends WorldSavedData
{
	private static TimeSavedData currentInstance;
	private World worldObj;
	/** Some 300 years into the future initially **/
	private double year = 2300D;
	/** 100 days a year **/
	private byte day = 0;
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
		worldObj = worldIn;
		markDirty();
	}

	public double getYear()
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
	
	public static void setDate(World worldIn, float time, int day, double year, boolean increment)
	{
		TimeSavedData date = getData(worldIn);
		if (increment)
			date.incrementDate(time, day, year);
		else
			date.setDate(time, day, year);
	}
	
	public void setDate(float time, int day, double year)
	{
		this.time = time;
		this.day = (byte) day;
		this.year = year;
	}
	
	public void incrementDate(float time, int day, double year)
	{
		this.time += time;
		this.day += day;
		this.year += year;
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
		year = nbt.getDouble("year");
		day = nbt.getByte("day");
		time = nbt.getFloat("time");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setDouble("year", year);
		nbt.setByte("day", day);
		nbt.setFloat("time", time);
	}
}
