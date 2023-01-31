package com.hbm.particle;

import java.util.HashMap;

/**
 * Definition for spent casing particles, what style and color they should use
 * @author uffr, hbm
 */
public class SpentCasing implements Cloneable {
	
	public static final HashMap<String, SpentCasing> casingMap = new HashMap();
	
	public enum CasingType {
		BRASS_STRAIGHT_WALL("Straight"),
		BRASS_BOTTLENECK("Bottleneck"),
		SHOTGUN("Shotgun", "ShotgunBase"), //plastic shell, brass case
		AR2("AR2", "AR2Highlight"); //plug, back detailing
		
		public final String[] partNames;

		private CasingType(String... names) {
			this.partNames = names;
		}
	}

	private String registryName;
	private float scaleX = 1F;
	private float scaleY = 1F;
	private float scaleZ = 1F;
	private int[] colors;
	private CasingType type;
	private String bounceSound;
	private float smokeChance;
	private float bounceYaw = 0F;
	private float bouncePitch = 0F;
	
	public SpentCasing(CasingType type) {
		this.type = type;
	}
	
	public SpentCasing register(String name) {
		this.registryName = name;
		casingMap.put(name, this);
		return this;
	}
	
	public SpentCasing setScale(float scale) {
		this.scaleX = scale;
		this.scaleY = scale;
		this.scaleZ = scale;
		return this;
	}
	
	public SpentCasing setScale(float x, float y, float z) {
		this.scaleX = x;
		this.scaleY = y;
		this.scaleZ = z;
		return this;
	}
	
	public SpentCasing setColor(int... color) {
		this.colors = color;
		return this;
	}
	
	public SpentCasing setSound(String bounce) {
		this.bounceSound = bounce;
		return this;
	}
	
	public SpentCasing setupSmoke(float chance, float lift, float duration) {
		this.smokeChance = chance;
		return this;
	}
	
	public static SpentCasing fromName(String name) {
		return casingMap.get(name);
	}
	
	public SpentCasing setBounceMotion(float yaw, float pitch) {
		this.bounceYaw = yaw;
		this.bouncePitch = pitch;
		return this;
	}

	public String getName() { return this.registryName; }
	public float getScaleX() { return this.scaleX; }
	public float getScaleY() { return this.scaleY; }
	public float getScaleZ() { return this.scaleZ; }
	public int[] getColors() { return this.colors; }
	public CasingType getType() { return this.type; }
	public String getSound() { return this.bounceSound; }
	public float getSmokeChance() { return this.smokeChance; }
	public float getBounceYaw() { return this.bounceYaw; }
	public float getBouncePitch() { return this.bouncePitch; }
	
	@Override
	public SpentCasing clone() {
		try {
			return (SpentCasing) super.clone();
		} catch(CloneNotSupportedException e) {
			return new SpentCasing(this.type);
		}
	}
}
