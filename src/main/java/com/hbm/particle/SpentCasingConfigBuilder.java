package com.hbm.particle;

import java.util.Objects;

import com.google.common.annotations.Beta;
import com.hbm.calc.EasyLocation;
import com.hbm.interfaces.ILocationProvider;
import com.hbm.main.MainRegistry;
import com.hbm.particle.SpentCasingConfig.CasingType;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

@Beta
public class SpentCasingConfigBuilder implements Cloneable
{
	/**Unique name used for map lookup.**/
	private String registryName;
	/**Change position of the ejecting shell.**/
	private ILocationProvider posOffset = EasyLocation.getZeroLocation();
	/**Set initial motion after ejecting.**/
	private Vec3 initialMotion = Vec3.createVectorHelper(0, 0, 0);
	/**Multipliers for random pitch and yaw.**/
	private float pitchFactor, yawFactor;
	/**Rescale the sprite to match the approximate scale of the rounds.**/
	private float scaleX = 1, scaleY = 1, scaleZ = 1;
	/**Overrides for the sprite colors.**/
	private int redOverride, greenOverride, blueOverride;
	/**Whether or not to override the default sprite color scheme.**/
	private boolean overrideColor;
	/**The type of casing.**/
	private CasingType casingType = CasingType.BRASS_STRAIGHT_WALL;
	/**Amount of casings to spawn per event. Default 1.**/
	private byte casingAmount = 1;
	/**If the casing(s) should be spawned after reloading, instead of after firing.**/
	private boolean afterReload;
	/**Sound effect for bouncing. Make empty string to have no sound.**/
	private String bounceSound = "";
	/**Delay before casings are actually spawned**/
	private byte delay;
	/**Chance for the casing to emit smoke. 0 for 100% chance and -1 for it to never make smoke.**/
	private byte smokeChance = -1;
	/**
	 * Constructor with fields for the required bare minimum parameters.<br>
	 * All parameters may overridden using setters at any time at your discretion, however.
	 * @param registryName The unique name for map lookup. Null not permitted, becomes empty string.
	 * @param casingType Type of casing model to use. Null not permitted, defaults to straight wall type.
	 * @param overrideColor Whether or not the config will override the model texture's color.
	 */
	public SpentCasingConfigBuilder(String registryName, CasingType casingType, boolean overrideColor)
	{
		this.registryName = registryName == null ? "" : registryName;
		this.casingType = casingType == null ? CasingType.BRASS_STRAIGHT_WALL : casingType;
		this.overrideColor = overrideColor;
	}
	
	public ILocationProvider getPosOffset()
	{
		return posOffset;
	}
	
	/**
	 * Set the relative x, y, z coordinate offset on spawn.
	 * @param posOffset Any ILocationProvider that serves as the offset. Null becomes a zero location.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setPosOffset(ILocationProvider posOffset)
	{
		this.posOffset = posOffset == null ? EasyLocation.getZeroLocation() : posOffset;
		return this;
	}

	public Vec3 getInitialMotion()
	{
		return initialMotion;
	}
	
	/**
	 * Sets the casing's initial relative x, y, z motion on spawn.
	 * @param initialMotion Vector representing the initial motion. Null becomes a zero vector.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setInitialMotion(Vec3 initialMotion)
	{
		this.initialMotion = initialMotion == null ? Vec3.createVectorHelper(0, 0, 0) : initialMotion;
		return this;
	}
	
	public double getScaleX()
	{
		return scaleX;
	}

	/**
	 * Scale of the model on its x-axis.
	 * @param scaleX The scale/stretch factor of the model on the x-axis.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setScaleX(float scaleX)
	{
		this.scaleX = scaleX;
		return this;
	}

	public double getScaleY()
	{
		return scaleY;
	}

	/**
	 * Scale of the model on its y-axis.
	 * @param scaleY The scale/stretch factor of the model on the y-axis.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setScaleY(float scaleY)
	{
		this.scaleY = scaleY;
		return this;
	}
	
	public float getScaleZ()
	{
		return scaleZ;
	}
	
	/**
	 * Scale of the model on its z-axis.
	 * @param scaleZ The scale/stretch of the model on the z-axis.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setScaleZ(float scaleZ)
	{
		this.scaleZ = scaleZ;
		return this;
	}
	
	public float getPitchFactor()
	{
		return pitchFactor;
	}
	
	/**
	 * Multiplier for random pitch-related motion. Recommended to keep in the thousanths (0.00X), as even with the hundreths (0.0X) the pitch can get crazy at times.
	 * @param pitchFactor The multiplier.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setPitchFactor(float pitchFactor)
	{
		this.pitchFactor = pitchFactor;
		return this;
	}
	
	public float getYawFactor()
	{
		return yawFactor;
	}
	
	/**
	 * Multiplier for random yaw-related motion. Recommended to keep in the thousanths (0.00X), as even with the hundreths (0.0X) the yaw can get crazy at times.
	 * @param yawFactor The multiplier.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setYawFactor(float yawFactor)
	{
		this.yawFactor = yawFactor;
		return this;
	}

	public int getRedOverride()
	{
		return redOverride;
	}

	/**
	 * Red color override. Clamped between 0-255, but I don't know how it actually works on the receiving end, so feel free to change.
	 * @param redOverride Red color override.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setRedOverride(int redOverride)
	{
		this.redOverride = MathHelper.clamp_int(redOverride, 0, 255);
		return this;
	}

	public int getGreenOverride()
	{
		return greenOverride;
	}

	/**
	 * Green color override. Clamped between 0-255, but I don't know how it actually works on the receiving end, so feel free to change.
	 * @param greenOverride Green color override.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setGreenOverride(int greenOverride)
	{
		this.greenOverride = MathHelper.clamp_int(greenOverride, 0, 255);
		return this;
	}

	public int getBlueOverride()
	{
		return blueOverride;
	}

	/**
	 * Blue color override. Clamped between 0-255, but I don't know how it actually works on the receiving end, so feel free to change.
	 * @param blueOverride Blue color override.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setBlueOverride(int blueOverride)
	{
		this.blueOverride = MathHelper.clamp_int(blueOverride, 0, 255);
		return this;
	}

	public boolean doesOverrideColor()
	{
		return overrideColor;
	}

	/**
	 * Sets whether or not the config will override color. If false, the integer overrides will be ignored.
	 * @param overrideColor True, to use the integer color overrides, false to ignore them.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setOverrideColor(boolean overrideColor)
	{
		this.overrideColor = overrideColor;
		return this;
	}

	public CasingType getCasingType()
	{
		return casingType;
	}

	/**
	 * Sets the model the casing will use.
	 * @param casingType One of the 4 casing model types. Null is not permitted, will have no effect.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setCasingType(CasingType casingType)
	{
		this.casingType = casingType == null ? this.casingType : casingType;
		return this;
	}
	
	public byte getCasingAmount()
	{
		return casingAmount;
	}
	
	/**
	 * Sets the amount of casings to spawn. Default is 1.
	 * @param casingAmount Amount of casings to spawn. Clamped to a positive byte (0 - 127).
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setCasingAmount(int casingAmount)
	{
		this.casingAmount = (byte) MathHelper.clamp_int(casingAmount, 0, 127);
		return this;
	}
	
	public boolean isAfterReload()
	{
		return afterReload;
	}
	
	/**
	 * Sets whether or not the casing will be spawned after reloading is done or after firing.
	 * @param afterReload If true, casings will be spawned when reloading, false, they will be spawned after firing.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setAfterReload(boolean afterReload)
	{
		this.afterReload = afterReload;
		return this;
	}
	
	public String getRegistryName()
	{
		return registryName;
	}
	
	/**
	 * Resets the unique name for the config used in map lookup.<br>
	 * As the real class is self-registering, <i>make sure to set this in reused builders.</i>
	 * @param registryName The registry name to use. Null is not permitted, becomes an empty string.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setRegistryName(String registryName)
	{
		this.registryName = registryName == null ? "" : registryName;
		return this;
	}
	
	public String getBounceSound()
	{
		return bounceSound;
	}
	
	/**
	 * The sound used when bouncing. If empty, no sound will be made.
	 * @param bounceSound The sound path. Null is not permitted, becomes an empty string.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setBounceSound(String bounceSound)
	{
		this.bounceSound = bounceSound == null ? "" : bounceSound;
		return this;
	}
	
	public byte getDelay()
	{
		return delay;
	}
	
	/**
	 * The delay in ticks before spawning.
	 * @param delay Tick spawn delay. Must be nonnegative, otherwise 0.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setDelay(int delay)
	{
		this.delay = (byte) (delay < 0 ? 0 : delay);
		return this;
	}
	
	public byte getSmokeChance()
	{
		return smokeChance;
	}
	
	/**
	 * Chance for the casing to emit smoke. 0 for 100% chance and -1 for it to never make smoke.
	 * @param smokeChance Chance to emit smoke. Clamped to a byte.
	 * @return Itself for chaining.
	 */
	public SpentCasingConfigBuilder setSmokeChance(int smokeChance)
	{
		this.smokeChance = (byte) smokeChance;
		return this;
	}
	
	/**
	 * Constructs the true immutable config with all settings loaded from this builder.<br>
	 * This builder may be reused as all non-immutable and primitive fields are copied before being passed to the constructor.<br>
	 * The config's constructor is self-registering.
	 * @return The finished config with its settings specified by this builder.
	 */
	public SpentCasingConfig build()
	{
		return new SpentCasingConfig(registryName, new EasyLocation(posOffset),
				Vec3.createVectorHelper(initialMotion.xCoord, initialMotion.yCoord, initialMotion.zCoord), pitchFactor,
				yawFactor, scaleX, scaleY, scaleZ, redOverride, greenOverride, blueOverride, overrideColor, casingType,
				casingAmount, afterReload, bounceSound, delay, smokeChance);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(afterReload, blueOverride, bounceSound, casingAmount, casingType, delay, greenOverride,
				initialMotion.xCoord, initialMotion.yCoord, initialMotion.zCoord, overrideColor, pitchFactor,
				posOffset, redOverride, registryName, scaleX, scaleY, scaleZ, smokeChance, yawFactor);
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
				&& Objects.equals(bounceSound, other.bounceSound) && casingAmount == other.casingAmount
				&& casingType == other.casingType && delay == other.delay && greenOverride == other.greenOverride
				&& Double.doubleToLongBits(initialMotion.xCoord) == Double.doubleToLongBits(other.initialMotion.xCoord)
				&& Double.doubleToLongBits(initialMotion.yCoord) == Double.doubleToLongBits(other.initialMotion.yCoord)
				&& Double.doubleToLongBits(initialMotion.zCoord) == Double.doubleToLongBits(other.initialMotion.zCoord)
				&& overrideColor == other.overrideColor
				&& Float.floatToIntBits(pitchFactor) == Float.floatToIntBits(other.pitchFactor)
				&& Objects.equals(posOffset, other.posOffset) && redOverride == other.redOverride
				&& Objects.equals(registryName, other.registryName)
				&& Float.floatToIntBits(scaleX) == Float.floatToIntBits(other.scaleX)
				&& Float.floatToIntBits(scaleY) == Float.floatToIntBits(other.scaleY)
				&& Float.floatToIntBits(scaleZ) == Float.floatToIntBits(other.scaleZ)
				&& smokeChance == other.smokeChance
				&& Float.floatToIntBits(yawFactor) == Float.floatToIntBits(other.yawFactor);
	}
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("SpentCasingConfigBuilder [registryName=").append(registryName).append(", posOffset=")
				.append(posOffset).append(", initialMotion=").append(initialMotion).append(", pitchFactor=")
				.append(pitchFactor).append(", yawFactor=").append(yawFactor).append(", scaleX=").append(scaleX)
				.append(", scaleY=").append(scaleY).append(", scaleZ=").append(scaleZ).append(", redOverride=")
				.append(redOverride).append(", greenOverride=").append(greenOverride).append(", blueOverride=")
				.append(blueOverride).append(", overrideColor=").append(overrideColor).append(", casingType=")
				.append(casingType).append(", casingAmount=").append(casingAmount).append(", afterReload=")
				.append(afterReload).append(", bounceSound=").append(bounceSound).append(", delay=").append(delay)
				.append(", smokeChance=").append(smokeChance).append(']');
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
			MainRegistry.logger.catching(e);
			return new SpentCasingConfigBuilder(registryName, casingType, overrideColor);
		}
	}

}
