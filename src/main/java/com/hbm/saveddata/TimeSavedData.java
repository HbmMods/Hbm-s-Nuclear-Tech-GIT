package com.hbm.saveddata;

import java.util.List;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.I18nUtil;

import api.hbm.Date;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class TimeSavedData extends WorldSavedData
{
	private static TimeSavedData currentInstance;
	private World worldObj;
	/** Some ~50-100 years into the future initially **/
//	private long year = 2040L;
	/** 100 days a year **/
//	private byte day = 1;
	/** 10 hours a day **/
//	private float time = 0.0000F;
//	private int timeInternal = 0;
//	private BigInteger fullData = BigInteger.valueOf(2040 * 100 + ticksInDay);
//	private static final BigInteger hundred = BigInteger.valueOf(100);
	public static final String dataKey = "HBMTimeReference";
//	public static final int ticksInDay = 48000;
//	public static final BigInteger tickDayBig = BigInteger.valueOf(ticksInDay);
	public Date dateData = new Date(2040, 0, 0);
	// Time Doctor Freeman?
	// Is it really that time again?
	public TimeSavedData(String name)
	{
		super(name);
	}
	
	public TimeSavedData(World worldIn)
	{
		super(dataKey);
		
		dateData.add(new Date(worldIn.getWorldTime()).add(new Date(worldIn.rand.nextInt(5), worldIn.rand.nextInt(99), 0)));
		
		worldObj = worldIn;
		markDirty();
		MainRegistry.logger.info("[TimeSavedData] New instance created");
	}
	public enum TimeDurationType
	{
		/** Counted in days **/
		SHORT,
		/** Counted in years **/
		MEDIUM,
		/** Counted in hundreds of years **/
		LONG;
	}

	public static void resetTime(World worldIn)
	{
		currentInstance = new TimeSavedData(worldIn);
		MainRegistry.logger.info("[TimeSavedData] Time data was reset");
	}
	
	public static void updateNormal(World worldIn)
	{
//		if (!worldIn.getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
//			return;
		final TimeSavedData date = getData(worldIn);
//		System.out.println(Arrays.toString(date.getDateFull()));
		date.dateData.increment();
		
//		for (EntityPlayer p : (List<EntityPlayer>) worldIn.playerEntities)
//		{
//			if (HbmPlayerProps.getBirthday(p) == date.dateData.getDay())
//			{
//				HbmPlayerProps.setAge(p, 1, true);
//				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(I18nUtil.resolveKey("desc.player.birthday"), 0), (EntityPlayerMP) p);
//				final String[] msg = HbmPlayerProps.hasAscended(p) ? I18nUtil.resolveKeyArray("desc.player.birthdayAlt") : I18nUtil.resolveKeyArray("desc.player.birhdayMsg");
//				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(Library.randomFromArray(msg), 0), (EntityPlayerMP) p);
//			}
//		}
		
		date.markDirty();
	}
	
	public static void setDate(World worldIn, int time, int day, long year, boolean increment)
	{
		TimeSavedData date = getData(worldIn);
		if (increment)
			date.incrementDate(time, day, year);
		else
			date.setDate(time, day, year);
	}
	
	public void setDate(int time, int day, long year)
	{
		dateData = new Date(year, day, time);
		markDirty();
	}
	
	public void incrementDate(int timeIn, int dayIn, long yearIn)
	{
		dateData.add(new Date(yearIn, dayIn, timeIn));
		markDirty();
	}
	
	public static TimeSavedData getData(World worldIn)
	{
		if (currentInstance != null && currentInstance.worldObj.equals(worldIn))
			return currentInstance;
		TimeSavedData date = (TimeSavedData) worldIn.perWorldStorage.loadData(TimeSavedData.class, dataKey);
		if (date == null)
		{
			worldIn.perWorldStorage.setData(dataKey, new TimeSavedData(worldIn));
			date = (TimeSavedData) worldIn.perWorldStorage.loadData(TimeSavedData.class, dataKey);
		}
		date.worldObj = worldIn;
		currentInstance = date;
		return currentInstance;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		dateData = new Date(nbt.getByteArray("savedDate"));
//		MainRegistry.logger.info("[TimeSavedData] Time data has been read from NBT");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setByteArray("savedDate", dateData.getData().toByteArray());
//		MainRegistry.logger.info("[TimeSavedData] Time data has been written to NBT");
	}
	
	@Deprecated
	public static Number[] constructUncompressed(long year, int day, int time)
	{
		final Number[] date = new Number[4];
		date[0] = year;
		date[1] = (byte) day;
		date[2] = Library.convertScale(time, 0, 48000, 0, 10);
		date[3] = time;
		return date;
	}
	
	@Override
	public String toString()
	{
		return dateData.toString();
	}
}
