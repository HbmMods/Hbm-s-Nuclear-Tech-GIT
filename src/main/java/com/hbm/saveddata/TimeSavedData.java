package com.hbm.saveddata;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
	private float time = 0.0000F;
	private long timeInternal = 0;
	public static final String dataKey = "HBMTimeReference";
	public static final int ticksInDay = 48000;
	// Time Doctor Freeman?
	// Is it really that time again?
	public TimeSavedData(String name)
	{
		super(name);
	}
	
	public TimeSavedData(World worldIn)
	{
		super(dataKey);
		long calcDays = Math.floorDiv(worldIn.getWorldTime(), ticksInDay);
		
		if (calcDays > 100)
		{
			final long calcYear = Math.floorDiv(calcDays, 100);
			year += calcYear;
			calcDays /= 100;
			calcDays -= calcYear;
			day = (byte) (calcDays * 100);
			timeInternal = worldIn.getWorldTime() - (calcYear * 100 + calcDays);
			clampDate(this);
		}
		else
		{
			day = (byte) calcDays;
			timeInternal = worldIn.getWorldTime() - (calcDays * ticksInDay);
		}
		time = (float) Library.convertScale(timeInternal, 0, ticksInDay, 0, 10.0000F);
		year += worldIn.rand.nextInt(50);
		worldObj = worldIn;
		markDirty();
		MainRegistry.logger.log(Level.INFO, "[TimeSavedData] New instance created");
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
	
	public static void resetTime(World worldIn)
	{
		currentInstance = new TimeSavedData(worldIn);
		MainRegistry.logger.log(Level.INFO, "[TimeSavedData] Time data was reset");
	}
	
	public static void updateNormal(World worldIn)
	{
		TimeSavedData date = getData(worldIn);
		if (date.timeInternal >= 24000)
		{
			date.timeInternal = 0;
			if (date.day >= 100)
			{
				date.day = 0;
				date.year++;
			}
			else
			{
				date.day++;
				for (EntityPlayer p : (List<EntityPlayer>) date.worldObj.playerEntities)
				{
					if (HbmPlayerProps.getBirthday(p) == date.day)
					{
						HbmPlayerProps.setAge(p, 1, true);
//						p.addChatMessage(new ChatComponentText(I18nUtil.resolveKey("desc.player.birthday")));
						PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(I18nUtil.resolveKey("desc.player.birthday")), (EntityPlayerMP) p);
						String[] msg = HbmPlayerProps.hasAscended(p) ? I18nUtil.resolveKeyArray("desc.player.birthdayAlt") : I18nUtil.resolveKeyArray("desc.player.birhdayMsg");
//						p.addChatMessage(new ChatComponentText(msg[rand.nextInt(msg.length)]));
						PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(msg[date.worldObj.rand.nextInt(msg.length)]), (EntityPlayerMP) p);
					}
				}
			}
		}
		else
			date.timeInternal++;
		
		date.time = (float) Library.convertScale(date.timeInternal, 0L, 24000L, 0F, 10.0000F);
		date.markDirty();
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
		clampDate(this);
		markDirty();
	}
	
	public void incrementDate(float time, int day, long year)
	{
		this.time += time;
		this.day += day;
		this.year += year;
		clampDate(this);
		markDirty();
	}
	
	private static void clampDate(TimeSavedData data)
	{
		if (data.timeInternal > ticksInDay)
			data.timeInternal = 0;
		if (data.time > 10)
		{
			data.time = 0;
			data.day++;
		}
		if (data.day > 100)
		{
			data.day = 1;
			data.year++;
		}
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
		year = nbt.getLong("year");
		day = nbt.getByte("day");
		time = nbt.getFloat("time");
		timeInternal = nbt.getLong("timeInternal");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setLong("year", year);
		nbt.setByte("day", day);
		nbt.setFloat("time", time);
		nbt.setLong("timeInternal", timeInternal);
	}
}
