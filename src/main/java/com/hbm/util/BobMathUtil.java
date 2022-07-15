package com.hbm.util;

import static java.math.MathContext.DECIMAL128;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnegative;

import com.hbm.calc.EasyLocation;
import com.hbm.interfaces.Untested;
import com.hbm.lib.HbmCollection;

import api.hbm.Date;
import ch.obermuhlner.math.big.BigDecimalMath;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class BobMathUtil {
	
	public static final short KB = 0x400;
	public static final int MB = 0x100000;
	public static final int GB = 0x40000000;
	public static final long TB = 0x10000000000L;
	public static final long PB = 0x4000000000000L;
	public static final long ZB = 0x100000000000000L;
	
	public static Vec3 interpVec(Vec3 vec1, Vec3 vec2, float interp) {
		return Vec3.createVectorHelper(
				interp(vec1.xCoord,  vec2.xCoord, interp),
				interp(vec1.yCoord,  vec2.yCoord, interp),
				interp(vec1.zCoord,  vec2.zCoord, interp)
				);
	}
	
	public static double interp(double x, double y, float interp) {
		return x + (y - x) * interp;
	}
	
	public static double safeClamp(double val, double min, double max) {

		val = MathHelper.clamp_double(val, min, max);
		
		if(val == Double.NaN) {
			val = (min + max) / 2D;
		}
		
		return val;
	}
	public static double getAngleFrom2DVecs(double x1, double z1, double x2, double z2) {
		
		double upper = x1 * x2 + z1 * z2;
		double lower = Math.sqrt(x1 * x1 + z1 * z1) * Math.sqrt(x2 * x2 + z2 * z2);
		
		double result = Math.toDegrees(Math.cos(upper / lower));
		
		if(result >= 180)
			result -= 180;
		
		return result;
	}
	
	public static double getCrossAngle(Vec3 vel, Vec3 rel) {
		
		vel.normalize();
		rel.normalize();

		double vecProd = rel.xCoord * vel.xCoord + rel.yCoord * vel.yCoord + rel.zCoord * vel.zCoord;
		double bot = rel.lengthVector() * vel.lengthVector();
		double angle = Math.acos(vecProd / bot) * 180 / Math.PI;
		
		if(angle >= 180)
			angle -= 180;
		
		return angle;
	}

	public static float remap(float num, float min1, float max1, float min2, float max2){
		return ((num - min1) / (max1 - min1)) * (max2 - min2) + min2;
	}
	
	public static float remap01(float num, float min1, float max1){
		return (num - min1) / (max1 - min1);
	}
	
	public static float remap01_clamp(float num, float min1, float max1){
		return MathHelper.clamp_float((num - min1) / (max1 - min1), 0, 1);
	}
	
	public static ForgeDirection[] getShuffledDirs() {
		
		final ForgeDirection[] dirs = new ForgeDirection[6];
//		List<Integer> indices = new ArrayList() {{ add(0); add(1); add(2); add(3); add(4); add(5); }};
		final List<Integer> indices = Arrays.asList(0, 1, 2, 3, 4, 5);
		Collections.shuffle(indices);
		
		for(int i = 0; i < 6; i++) {
			dirs[i] = ForgeDirection.getOrientation(indices.get(i));
		}
		
		return dirs;
	}

	public static String toPercentage(float amount, float total) {
		return NumberFormat.getPercentInstance().format(amount / total);
	}
	
	@Deprecated
	public static String[] ticksToDate(long ticks) {
		
		final String[] dateOut = new String[3];
		long year = Math.floorDiv(ticks, HbmCollection.tickYear);
		byte day = (byte) Math.floorDiv(ticks - HbmCollection.tickYear * year, HbmCollection.tickDay);
		float time = ticks - (HbmCollection.tickYear * year + HbmCollection.tickDay * day);
		time = (float) convertScale(time, 0, HbmCollection.tickDay, 0, 10F);
		dateOut[0] = String.valueOf(year);
		dateOut[1] = String.valueOf(day);
		dateOut[2] = String.valueOf(time);
		return dateOut;
	}
	
	/**
	 * Rescale a number from one range to another
	 * @param toScale - The integer to scale
	 * @param oldMin - The current minimum value
	 * @param oldMax - The current maximum value
	 * @param newMin - The desired minimum value
	 * @param newMax - The desired maximum value
	 * @return The scaled number
	 */
	public static double convertScale(double toScale, double oldMin, double oldMax, double newMin, double newMax) {
		final double prevRange = oldMax - oldMin;
		final double newRange = newMax - newMin;
		return (((toScale - oldMin) * newRange) / prevRange) + newMin;
	}
	
	/**
	 * Rounds a number to so many significant digits
	 * @param num The number to round
	 * @param digits Amount of digits
	 * @return The rounded double
	 */
	public static double roundDecimal(double num, @Nonnegative int digits) {
		if(digits < 0)
			throw new IllegalArgumentException("Attempted negative number in non-negative field! Attempted value: " + digits);

		return new BigDecimal(num).setScale(digits, RoundingMode.HALF_UP).doubleValue();
	}

	public static boolean getBlink() {
		return System.currentTimeMillis() % 1000 < 500;
	}

	public static String getShortNumber(long l) {
	
		if(l >= Math.pow(10, 18)) {
			double res = l / Math.pow(10, 18);
			res = Math.round(res * 100.0) / 100.0;
			return res + "E";
		}
		if(l >= Math.pow(10, 15)) {
			double res = l / Math.pow(10, 15);
			res = Math.round(res * 100.0) / 100.0;
			return res + "P";
		}
		if(l >= Math.pow(10, 12)) {
			double res = l / Math.pow(10, 12);
			res = Math.round(res * 100.0) / 100.0;
			return res + "T";
		}
		if(l >= Math.pow(10, 9)) {
			double res = l / Math.pow(10, 9);
			res = Math.round(res * 100.0) / 100.0;
			return res + "G";
		}
		if(l >= Math.pow(10, 6)) {
			double res = l / Math.pow(10, 6);
			res = Math.round(res * 100.0) / 100.0;
			return res + "M";
		}
		if(l >= Math.pow(10, 3)) {
			double res = l / Math.pow(10, 3);
			res = Math.round(res * 100.0) / 100.0;
			return res + "k";
		}
		
		return Long.toString(l);
	}
	
	public static double durabilityBarDisplay(double cur, double max)
	{
		return 1d - cur / max;
	}
	
	@Untested
	/**
	 * How much radioactive material remains after so much time given the half-life.
	 * (Probably won't work with microscopic half-lives)
	 * @param originalAmount The original amount of material present
	 * @param halflife Half-life of the material
	 * @param timePassed Amount of time that passed
	 * @return Amount of material that remains, in the same unit as originalAmount
	 */
	public static double remainingRadioactiveMaterial(double originalAmount, Date halflife, Date timePassed)
	{
		final BigDecimal A = BigDecimal.valueOf(originalAmount);
		return A.multiply(BigDecimalMath.e(DECIMAL128).pow((BigDecimal.valueOf(Math.log(0.5)).divide(new BigDecimal(halflife.getData()))).multiply(new BigDecimal(timePassed.getData())).intValue())).doubleValue();
	}
	
	@Untested
	public static double radiologicalDating(double remaining, Date halflife)
	{
		return BigDecimal.valueOf(Math.log(remaining)).divide((BigDecimal.valueOf(Math.log(0.5)).divide(new BigDecimal(halflife.getData())))).doubleValue();
	}

	public static Vec3 entityUnitVector(Entity entity)
	{
		return Vec3.createVectorHelper
					(
						-(Math.sin(entity.rotationYaw / 180 * Math.PI) * Math.cos(entity.rotationPitch / 180 * Math.PI)),
						-Math.sin(entity.rotationPitch / 180 * Math.PI),
						Math.cos(entity.rotationYaw / 180 * Math.PI) * Math.cos(entity.rotationPitch / 180 * Math.PI)
					);
	}
	
	public static void offsetForHand(Entity entity, EasyLocation loc)
	{
		loc.posX -= Math.cos(entity.rotationYaw / 180 * Math.PI) * 0.16;
		loc.posY -= 0.16;
		loc.posZ -= Math.sin(entity.rotationYaw / 180 * Math.PI) * 0.16;
	}
}
