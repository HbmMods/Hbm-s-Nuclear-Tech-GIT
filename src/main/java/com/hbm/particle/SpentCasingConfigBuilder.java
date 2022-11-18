package com.hbm.particle;

import java.util.Objects;

import com.hbm.particle.SpentCasingConfig.CasingType;

import net.minecraft.util.MathHelper;

public class SpentCasingConfigBuilder implements Cloneable
{
	/**Change position of the ejecting shell.**/
	private double offsetX, offsetY, offsetZ;
	/**Set initial motion after ejecting.**/
	private double motionX, motionY, motionZ;
	/**Rescale the sprite to match the approximate scale of the rounds.**/
	private float stretchX, stretchY;
	/**Multipliers for random pitch and yaw.**/
	private float pitchFactor = 1, yawFactor = 1;
	/**Overrides for the sprite colors.**/
	private int redOverride, greenOverride, blueOverride;
	/**Whether or not to override the default sprite color scheme.**/
	private boolean overrideColor;
	/**The type of casing.**/
	private CasingType casingType;
	/**Amount of casings to spawn per event. Default 1.**/
	private int casingAmount = 1;
	/**If the casing(s) should be spawned after reloading, instead of after firing.**/
	private boolean afterReload;
	public SpentCasingConfigBuilder(CasingType casingType, boolean overrideColor)
	{
		this.casingType = casingType;
		this.overrideColor = overrideColor;
	}
	
	public double getOffsetX()
	{
		return offsetX;
	}

	public SpentCasingConfigBuilder setOffsetX(double offsetX)
	{
		this.offsetX = offsetX;
		return this;
	}

	public double getOffsetY()
	{
		return offsetY;
	}

	public SpentCasingConfigBuilder setOffsetY(double offsetY)
	{
		this.offsetY = offsetY;
		return this;
	}

	public double getOffsetZ()
	{
		return offsetZ;
	}

	public SpentCasingConfigBuilder setOffsetZ(double offsetZ)
	{
		this.offsetZ = offsetZ;
		return this;
	}

	public double getMotionX()
	{
		return motionX;
	}

	public SpentCasingConfigBuilder setMotionX(double motionX)
	{
		this.motionX = motionX;
		return this;
	}

	public double getMotionY()
	{
		return motionY;
	}

	public SpentCasingConfigBuilder setMotionY(double motionY)
	{
		this.motionY = motionY;
		return this;
	}

	public double getMotionZ()
	{
		return motionZ;
	}

	public SpentCasingConfigBuilder setMotionZ(double motionZ)
	{
		this.motionZ = motionZ;
		return this;
	}

	public double getStretchX()
	{
		return stretchX;
	}

	public SpentCasingConfigBuilder setStretchX(float stretchX)
	{
		this.stretchX = stretchX;
		return this;
	}

	public double getStretchY()
	{
		return stretchY;
	}

	public SpentCasingConfigBuilder setStretchY(float stretchY)
	{
		this.stretchY = stretchY;
		return this;
	}
	
	public float getPitchFactor()
	{
		return pitchFactor;
	}
	
	public SpentCasingConfigBuilder setPitchFactor(float pitchFactor)
	{
		this.pitchFactor = pitchFactor;
		return this;
	}
	
	public float getYawFactor()
	{
		return yawFactor;
	}
	
	public SpentCasingConfigBuilder setYawFactor(float yawFactor)
	{
		this.yawFactor = yawFactor;
		return this;
	}

	public int getRedOverride()
	{
		return redOverride;
	}

	public SpentCasingConfigBuilder setRedOverride(int redOverride)
	{
		this.redOverride = MathHelper.clamp_int(redOverride, 0, 255);
		return this;
	}

	public int getGreenOverride()
	{
		return greenOverride;
	}

	public SpentCasingConfigBuilder setGreenOverride(int greenOverride)
	{
		this.greenOverride = MathHelper.clamp_int(greenOverride, 0, 255);
		return this;
	}

	public int getBlueOverride()
	{
		return blueOverride;
	}

	public SpentCasingConfigBuilder setBlueOverride(int blueOverride)
	{
		this.blueOverride = MathHelper.clamp_int(blueOverride, 0, 255);
		return this;
	}

	public boolean isOverrideColor()
	{
		return overrideColor;
	}

	public SpentCasingConfigBuilder setOverrideColor(boolean overrideColor)
	{
		this.overrideColor = overrideColor;
		return this;
	}

	public CasingType getCasingType()
	{
		return casingType;
	}

	public SpentCasingConfigBuilder setCasingType(CasingType casingType)
	{
		this.casingType = casingType;
		return this;
	}
	
	public int getCasingAmount()
	{
		return casingAmount;
	}
	
	public SpentCasingConfigBuilder setCasingAmount(int casingAmount)
	{
		this.casingAmount = casingAmount;
		return this;
	}
	
	public boolean isAfterReload()
	{
		return afterReload;
	}
	
	public SpentCasingConfigBuilder setAfterReload(boolean afterReload)
	{
		this.afterReload = afterReload;
		return this;
	}
	
	public SpentCasingConfig build()
	{
		return new SpentCasingConfig(offsetX, offsetY, offsetZ, motionX, motionY, motionZ, stretchX, stretchY, pitchFactor, yawFactor, redOverride, greenOverride, blueOverride, overrideColor, casingType, casingAmount, afterReload);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(afterReload, blueOverride, casingAmount, casingType, greenOverride, motionX, motionY,
				motionZ, offsetX, offsetY, offsetZ, overrideColor, pitchFactor, redOverride, stretchX, stretchY,
				yawFactor);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof SpentCasingConfigBuilder))
			return false;
		final SpentCasingConfigBuilder other = (SpentCasingConfigBuilder) obj;
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
		builder.append("SpentCasingConfigBuilder [offsetX=").append(offsetX).append(", offsetY=").append(offsetY)
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
	public SpentCasingConfigBuilder clone()
	{
		try
		{
			return (SpentCasingConfigBuilder) super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return new SpentCasingConfigBuilder(casingType, overrideColor).setBlueOverride(blueOverride)
					.setCasingAmount(casingAmount).setGreenOverride(greenOverride).setMotionX(motionX)
					.setMotionY(motionY).setMotionZ(motionZ).setOffsetX(offsetX).setOffsetY(offsetY)
					.setOffsetZ(offsetZ).setPitchFactor(pitchFactor).setRedOverride(redOverride)
					.setStretchX(stretchX).setStretchY(stretchY).setYawFactor(yawFactor);
		}
	}

}
