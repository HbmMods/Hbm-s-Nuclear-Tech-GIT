package com.hbm.particle;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableMap;
import com.hbm.interfaces.ILocationProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Beta
public class SpentCasingConfig
{
	private static final Map<String, SpentCasingConfig> CONFIG_MAP = new HashMap<String, SpentCasingConfig>();
	private static final Random RANDOM = new Random();
	public enum CasingType
	{
		/**The typical handgun ejected type. Most pistol and some cannon rounds will likely use it.**/
		BRASS_STRAIGHT_WALL("Brass_Straight_Casing"),
		/**The typical rife ejected type. Most rifle round and sometimes cannon rounds use it. Only use if the bottleneck shape is noticeable.**/
		BRASS_BOTTLENECK("Brass_Bottleneck_Casing.002"),
		/**Shotgun shells.**/
		SHOTGUN("Shotgun_Shell_Cylinder.002"),
		/**AR2 pulse rifle plugs.**/
		AR2("AR2_Cylinder.001");
		public final String objName;
		private CasingType(String objName)
		{
			this.objName = objName;
		}
	}
	
	/**Unique name used for map lookup.**/
	private final String registryName;
	/**Change position of the ejecting shell.**/
	private final ILocationProvider posOffset;
	/**Set initial motion after ejecting.**/
	private final Vec3 initialMotion;
	/**Multipliers for random pitch and yaw.**/
	private final float pitchFactor, yawFactor;
	/**Rescale the sprite to match the approximate scale of the rounds.**/
	private final float scaleX, scaleY, scaleZ;
	/**Overrides for the sprite colors.**/
	private final int redOverride, greenOverride, blueOverride;
	/**Whether or not to override the default sprite color scheme.**/
	private final boolean overrideColor;
	/**The type of casing.**/
	private final CasingType casingType;
	/**Amount of casings to spawn per event. Default 1.**/
	private final byte casingAmount;
	/**If the casing(s) should be spawned after reloading, instead of after firing.**/
	private final boolean afterReload;
	/**Sound effect for bouncing. Make empty string to have no sound.**/
	private final String bounceSound;
	/**Delay before casings are actually spawned**/
	private final byte delay;
	/**Chance for the casing to emit smoke. 0 for 100% chance and -1 for it to never make smoke.**/
	private final byte smokeChance;
	// TODO Setting to disregard crouch effect and/or another offset specifically for crouching which can be set to null to use the default one
	public SpentCasingConfig(
			String registryName, ILocationProvider posOffset, Vec3 initialMotion, float pitchFactor, float yawFactor,
			float scaleX, float scaleY, float scaleZ, int redOverride, int greenOverride, int blueOverride,
			boolean overrideColor, CasingType casingType, byte casingAmount, boolean afterReload, String bounceSound,
			byte delay, byte smokeChance
	)
	{
		this.registryName = registryName;
		this.posOffset = posOffset;
		this.initialMotion = initialMotion;
		this.pitchFactor = pitchFactor;
		this.yawFactor = yawFactor;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		this.redOverride = redOverride;
		this.greenOverride = greenOverride;
		this.blueOverride = blueOverride;
		this.overrideColor = overrideColor;
		this.casingType = casingType;
		this.casingAmount = casingAmount;
		this.afterReload = afterReload;
		this.bounceSound = bounceSound;
		this.delay = delay;
		this.smokeChance = smokeChance;
		
		CONFIG_MAP.put(registryName, this);
	}

	public void spawnCasing(TextureManager textureManager, World world, double x, double y, double z, float pitch, float yaw, boolean crouched)
	{
		final Vec3 rotatedMotionVec = rotateVector(getInitialMotion(),
				pitch + (float) RANDOM.nextGaussian() * getPitchFactor(), yaw + (float) RANDOM.nextGaussian() * getPitchFactor(),
				getPitchFactor(), getPitchFactor());
		
		final ParticleSpentCasing casing = new ParticleSpentCasing(textureManager, world, x,
				y, z, rotatedMotionVec.xCoord, rotatedMotionVec.yCoord, rotatedMotionVec.zCoord,
//				0, 0,
				(float) (getPitchFactor() * RANDOM.nextGaussian()), (float) (getYawFactor() * RANDOM.nextGaussian()),
				this);

		offsetCasing(casing, getPosOffset(), pitch, yaw, crouched);

		casing.rotationPitch = (float) Math.toDegrees(pitch);
		casing.rotationYaw = (float) Math.toDegrees(yaw);
		
		if (overrideColor)
			casing.setRBGColorF(redOverride / 255f, blueOverride / 255f, greenOverride / 255f);
		Minecraft.getMinecraft().effectRenderer.addEffect(casing);
	}
	
	// Rotate a position
	private static void offsetCasing(ParticleSpentCasing casing, ILocationProvider offset, float pitch, float yaw, boolean crouched)
	{
//		// x-axis offset, 0 if crouched to center
//		final double oX = crouched ? 0 : offset.posX();
//		// Trigonometric operations, saved for convenience
//		final double sinP = Math.sin(pitch), cosP = Math.cos(pitch), sinY = Math.sin(yaw), cosY = Math.cos(yaw);
//		// New offsets
//		final double newX = oX * cosY - offset.posZ() * sinY,
//					 newY = offset.posY() * cosP - sinP * (oX * sinY + offset.posZ() * cosY),
//					 newZ = offset.posZ() * sinP + cosP * (oX * sinY + offset.posZ() * cosY);
//		
//		// Apply
//		casing.setPosition(casing.posX + newX, casing.posY + newY, casing.posZ + newZ);
		
	    // x-axis offset, 0 if crouched to center
	    final float oX = (float) (crouched ? 0 : offset.posX());
	    // Create rotation matrices for pitch and yaw
	    final Matrix4f pitchMatrix = new Matrix4f(), yawMatrix = new Matrix4f();
	    
	    pitchMatrix.rotate(pitch, new Vector3f(1, 0, 0)); // modify axis of rotation
	    yawMatrix.rotate(-yaw, new Vector3f(0, 1, 0));
	    
	    // Multiply matrices to get combined rotation matrix
	    final Matrix4f rotMatrix = Matrix4f.mul(yawMatrix, pitchMatrix, null);
	    // Create vector representing the offset and apply rotation
	    final Vector4f offsetVector = new Vector4f(oX, (float) offset.posY(), (float) offset.posZ(), 1); // set fourth coordinate to 1
	    Matrix4f.transform(rotMatrix, offsetVector, offsetVector);
	    final Vector3f result = new Vector3f(); // create result vector
	    result.set(offsetVector.x, offsetVector.y, offsetVector.z); // set result vector using transformed coordinates
	    // Apply rotation
	    casing.setPosition(casing.posX + result.x, casing.posY + result.y, casing.posZ + result.z);
	}
	
//	Rotate a vector
	private static Vec3 rotateVector(Vec3 vector, float pitch, float yaw, float pitchFactor, float yawFactor)
	{
		// Apply randomness to vector
		vector.xCoord += RANDOM.nextGaussian() * yawFactor;
		vector.yCoord += RANDOM.nextGaussian() * pitchFactor;
		vector.zCoord += RANDOM.nextGaussian() * yawFactor;
		
		final Matrix4f pitchMatrix = new Matrix4f(), yawMatrix = new Matrix4f();
		
		pitchMatrix.setIdentity();
		pitchMatrix.rotate(-pitch, new Vector3f(1, 0, 0));
		
		yawMatrix.setIdentity();
		yawMatrix.rotate(-yaw, new Vector3f(0, 1, 0));
		
		final Vector4f vector4f = new Vector4f((float) vector.xCoord, (float) vector.yCoord, (float) vector.zCoord, 1);
		
		Matrix4f.transform(pitchMatrix, vector4f, vector4f);
		Matrix4f.transform(yawMatrix, vector4f, vector4f);
		
		return Vec3.createVectorHelper(vector4f.x, vector4f.y, vector4f.z);
	}
	
	public ILocationProvider getPosOffset()
	{
		return posOffset;
	}
	public Vec3 getInitialMotion()
	{
		return Vec3.createVectorHelper(initialMotion.xCoord, initialMotion.yCoord, initialMotion.zCoord);
	}
	public float getScaleX()
	{
		return scaleX;
	}
	public float getScaleY()
	{
		return scaleY;
	}
	public float getScaleZ()
	{
		return scaleZ;
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
	public boolean doesOverrideColor()
	{
		return overrideColor;
	}
	public CasingType getCasingType()
	{
		return casingType;
	}
	public byte getCasingAmount()
	{
		return casingAmount;
	}
	public boolean isAfterReload()
	{
		return afterReload;
	}
	public String getRegistryName()
	{
		return registryName;
	}
	public String getBounceSound()
	{
		return bounceSound;
	}
	public byte getDelay()
	{
		return delay;
	}
	public byte getSmokeChance()
	{
		return smokeChance;
	}
	
	/**
	 * Convert to a new builder for modification.
	 * @param newName The new registry name.
	 * @return A new builder with all the settings inherited from this config, except for registry name.
	 */
	public SpentCasingConfigBuilder toBuilder(String newName)
	{
		final SpentCasingConfigBuilder builder = new SpentCasingConfigBuilder(newName, casingType, overrideColor);
		builder.setAfterReload(afterReload).setBlueOverride(blueOverride).setBounceSound(bounceSound).setCasingAmount(casingAmount)
				.setDelay(delay).setGreenOverride(greenOverride).setInitialMotion(getInitialMotion()).setPitchFactor(pitchFactor)
				.setPosOffset(getPosOffset()).setRedOverride(redOverride).setScaleX(scaleX).setScaleY(scaleY).setScaleZ(scaleZ)
				.setSmokeChance(smokeChance).setYawFactor(yawFactor);
		return builder;
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
		if (!(obj instanceof SpentCasingConfig))
			return false;
		final SpentCasingConfig other = (SpentCasingConfig) obj;
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
		builder.append("SpentCasingConfig [registryName=").append(registryName).append(", posOffset=").append(posOffset)
				.append(", initialMotion=").append(initialMotion).append(", pitchFactor=").append(pitchFactor)
				.append(", yawFactor=").append(yawFactor).append(", scaleX=").append(scaleX).append(", scaleY=")
				.append(scaleY).append(", scaleZ=").append(scaleZ).append(", redOverride=").append(redOverride)
				.append(", greenOverride=").append(greenOverride).append(", blueOverride=").append(blueOverride)
				.append(", overrideColor=").append(overrideColor).append(", casingType=").append(casingType)
				.append(", casingAmount=").append(casingAmount).append(", afterReload=").append(afterReload)
				.append(", bounceSound=").append(bounceSound).append(", delay=").append(delay).append(", smokeChance=")
				.append(smokeChance).append("]");
		return builder.toString();
	}

	public static boolean containsKey(String key)
	{
		return CONFIG_MAP.containsKey(key);
	}

	public static SpentCasingConfig get(String key)
	{
		return CONFIG_MAP.get(key);
	}

	public static Map<String, SpentCasingConfig> getConfigMap()
	{
		return ImmutableMap.copyOf(CONFIG_MAP);
	}
	
}
