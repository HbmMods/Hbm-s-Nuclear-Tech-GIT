package com.hbm.particle;

import java.util.Objects;

public class SpentCasingConfig implements Cloneable
{
	
	public enum CasingType
	{
		/**The typical ejected type. Most pistols, rifles, and even cannons will likely use it.**/
		BRASS,
		/**Shotgun shells.**/
		SHOTGUN,
		/**AR2 pulse rifle plugs.**/
		AR2;
	}
	
	/**Change position of the ejecting shell.**/
	private final double offsetX, offsetY, offsetZ;
	/**Set initial motion after ejecting.**/
	private final double motionX, motionY, motionZ;
	/**Rescale the sprite to match the approximate scale of the rounds.**/
	private final float stretchX, stretchY;
	/**Multipliers for random pitch and yaw.**/
	private final float pitchFactor, yawFactor;
	/**Overrides for the sprite colors.**/
	private final int redOverride, greenOverride, blueOverride;
	/**Whether or not to override the default sprite color scheme.**/
	private final boolean overrideColor;
	/**The type of casing.**/
	private final CasingType casingType;
	/**Amount of casings to spawn per event. Default 1.**/
	private final int casingAmount;
	/**If the casing(s) should be spawned after reloading, instead of after firing.**/
	private final boolean afterReload;
	public SpentCasingConfig(
			double offsetX, double offsetY, double offsetZ, double motionX, double motionY, double motionZ,
			float stretchX, float stretchY, float pitchFactor, float yawFactor, int redOverride, int greenOverride,
			int blueOverride, boolean overrideColor, CasingType casingType, int casingAmount, boolean afterReload
	)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.stretchX = stretchX;
		this.stretchY = stretchY;
		this.pitchFactor = pitchFactor;
		this.yawFactor = yawFactor;
		this.redOverride = redOverride;
		this.greenOverride = greenOverride;
		this.blueOverride = blueOverride;
		this.overrideColor = overrideColor;
		this.casingType = casingType;
		this.casingAmount = casingAmount;
		this.afterReload = afterReload;
	}
	
	/**
	 * Apply these settings to an initialized casing entity
	 * @param entity The entity to apply the settings defined by this object
	 */
	public void applyToEntity(ParticleSpentCasing entity)
	{
		entity.setPosition((entity.posX - Math.cos(entity.rotationYaw / 180 * Math.PI)) + offsetX,
							(entity.posY - 0.1) + offsetY,
							(entity.posZ - Math.sin(entity.rotationYaw / 180 * Math.PI) + offsetZ));
		
		entity.setRBGColorF((float) redOverride / 255, (float) greenOverride / 255, (float) blueOverride / 255);
	}
	
	public double getOffsetX()
	{
		return offsetX;
	}
	public double getOffsetY()
	{
		return offsetY;
	}
	public double getOffsetZ()
	{
		return offsetZ;
	}
	public double getMotionX()
	{
		return motionX;
	}
	public double getMotionY()
	{
		return motionY;
	}
	public double getMotionZ()
	{
		return motionZ;
	}
	public float getStretchX()
	{
		return stretchX;
	}
	public float getStretchY()
	{
		return stretchY;
	}
	public float getPitchFactor()
	{
		return pitchFactor;
	}
	public float getYawFactor()
	{
		return yawFactor;
	}
	public int getRedOverride()
	{
		return redOverride;
	}
	public int getGreenOverride()
	{
		return greenOverride;
	}
	public int getBlueOverride()
	{
		return blueOverride;
	}
	public boolean isOverrideColor()
	{
		return overrideColor;
	}
	public CasingType getCasingType()
	{
		return casingType;
	}
	public int getCasingAmount()
	{
		return casingAmount;
	}
	public boolean isAfterReload()
	{
		return afterReload;
	}
	@Override
	public int hashCode()
	{
		return Objects.hash(afterReload, blueOverride, casingAmount, casingType, greenOverride, motionX, motionY, motionZ,
				offsetX, offsetY, offsetZ, overrideColor, pitchFactor, redOverride, stretchX, stretchY, yawFactor);
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof SpentCasingConfig))
			return false;
		final SpentCasingConfig other = (SpentCasingConfig) obj;
		return afterReload == other.afterReload && blueOverride == other.blueOverride
				&& casingAmount == other.casingAmount && casingType == other.casingType
				&& greenOverride == other.greenOverride
				&& Double.doubleToLongBits(motionX) == Double.doubleToLongBits(other.motionX)
				&& Double.doubleToLongBits(motionY) == Double.doubleToLongBits(other.motionY)
				&& Double.doubleToLongBits(motionZ) == Double.doubleToLongBits(other.motionZ)
				&& Double.doubleToLongBits(offsetX) == Double.doubleToLongBits(other.offsetX)
				&& Double.doubleToLongBits(offsetY) == Double.doubleToLongBits(other.offsetY)
				&& Double.doubleToLongBits(offsetZ) == Double.doubleToLongBits(other.offsetZ)
				&& overrideColor == other.overrideColor
				&& Float.floatToIntBits(pitchFactor) == Float.floatToIntBits(other.pitchFactor)
				&& redOverride == other.redOverride
				&& Float.floatToIntBits(stretchX) == Float.floatToIntBits(other.stretchX)
				&& Float.floatToIntBits(stretchY) == Float.floatToIntBits(other.stretchY)
				&& Float.floatToIntBits(yawFactor) == Float.floatToIntBits(other.yawFactor);
	}
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("SpentCasingConfig [offsetX=").append(offsetX).append(", offsetY=").append(offsetY)
				.append(", offsetZ=").append(offsetZ).append(", motionX=").append(motionX).append(", motionY=")
				.append(motionY).append(", motionZ=").append(motionZ).append(", stretchX=").append(stretchX)
				.append(", stretchY=").append(stretchY).append(", pitchFactor=").append(pitchFactor)
				.append(", yawFactor=").append(yawFactor).append(", redOverride=").append(redOverride)
				.append(", greenOverride=").append(greenOverride).append(", blueOverride=").append(blueOverride)
				.append(", overrideColor=").append(overrideColor).append(", casingType=").append(casingType)
				.append(", casingAmount=").append(casingAmount).append(", afterReload=").append(afterReload)
				.append(']');
		return builder.toString();
	}
	@Override
	public SpentCasingConfig clone()
	{
		try
		{
			return (SpentCasingConfig) super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return new SpentCasingConfig(offsetX, offsetY, offsetZ, motionX, motionY, motionZ, stretchX, stretchY, pitchFactor, yawFactor, redOverride, greenOverride, blueOverride, overrideColor, casingType, casingAmount, afterReload);
		}
	}

}
