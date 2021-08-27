package com.hbm.util;

import com.hbm.saveddata.TimeSavedData;

import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class TimeDataDials
{
	public static final String KEY_TIME = "dialDateTime";
	public static final String KEY_DAY = "dialDateDay";
	public static final String KEY_YEAR = "dialDateYear";
	static TimeSavedData data;
	
	public static void registerDials(World worldIn)
	{
		GameRules rules = worldIn.getGameRules();
		data = TimeSavedData.getData(worldIn);
		
		rules.setOrCreateGameRule(KEY_TIME, Float.toString(data.getTime()));
		rules.setOrCreateGameRule(KEY_DAY, Byte.toString(data.getDay()));
		rules.setOrCreateGameRule(KEY_YEAR, Double.toString(data.getYear()));
	}
	
	public static float getNewTime(World worldIn)
	{
		return MathHelper.clamp_float(parseFloat(worldIn.getGameRules().getGameRuleStringValue(KEY_TIME), data.getTime()), 0, 10F);
	}
	
	public static byte getNewDay(World worldIn)
	{
		return (byte) MathHelper.clamp_int(parseByte(worldIn.getGameRules().getGameRuleStringValue(KEY_DAY), data.getDay()), 0, 100);
	}
	
	public static double getNewYear(World worldIn)
	{
		return MathHelper.clamp_double(parseDouble(worldIn.getGameRules().getGameRuleStringValue(KEY_YEAR), data.getYear()), Double.MIN_VALUE, Double.MAX_VALUE);
	}
	
	private static float parseFloat(String s, float def)
	{
		try
		{
			return Float.parseFloat(s);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return def;
		}
	}
	private static byte parseByte(String s, byte def)
	{
		try
		{
			return Byte.parseByte(s);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return def;
		}
	}
	private static double parseDouble(String s, double def)
	{
		try
		{
			return Double.parseDouble(s);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return def;
		}
	}
}
