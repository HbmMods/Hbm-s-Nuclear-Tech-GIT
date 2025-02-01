package com.hbm.util;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Vec3NT extends Vec3 {

	public Vec3NT(double x, double y, double z) {
		super(x, y, z);
	}

	public Vec3NT(Vec3 vec) {
		super(vec.xCoord, vec.yCoord, vec.zCoord);
	}

	public Vec3NT normalizeSelf() {
		double len = MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
		if(len < 1.0E-4D) {
			return multiply(0D);
		} else {
			return multiply(1D / len);
		}
	}

	public Vec3NT add(double x, double y, double z) {
		this.xCoord += x;
		this.yCoord += y;
		this.zCoord += z;
		return this;
	}

	public Vec3NT multiply(double m) {
		this.xCoord *= m;
		this.yCoord *= m;
		this.zCoord *= m;
		return this;
	}

	public Vec3NT multiply(double x, double y, double z) {
		this.xCoord *= x;
		this.yCoord *= y;
		this.zCoord *= z;
		return this;
	}

	@Override
	public Vec3NT setComponents(double x, double y, double z) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		return this;
	}

	public Vec3NT rotateAroundXRad(double alpha) {
		double cos = Math.cos(alpha);
		double sin = Math.sin(alpha);
		double x = this.xCoord;
		double y = this.yCoord * cos + this.zCoord * sin;
		double z = this.zCoord * cos - this.yCoord * sin;
		return this.setComponents(x, y, z);
	}

	public Vec3NT rotateAroundYRad(double alpha) {
		double cos = Math.cos(alpha);
		double sin = Math.sin(alpha);
		double x = this.xCoord * cos + this.zCoord * sin;
		double y = this.yCoord;
		double z = this.zCoord * cos - this.xCoord * sin;
		return this.setComponents(x, y, z);
	}

	public Vec3NT rotateAroundZRad(double alpha) {
		double cos = Math.cos(alpha);
		double sin = Math.sin(alpha);
		double x = this.xCoord * cos + this.yCoord * sin;
		double y = this.yCoord * cos - this.xCoord * sin;
		double z = this.zCoord;
		return this.setComponents(x, y, z);
	}

	public Vec3NT rotateAroundXDeg(double alpha) {
		return this.rotateAroundXRad(alpha / 180D * Math.PI);
	}

	public Vec3NT rotateAroundYDeg(double alpha) {
		return this.rotateAroundYRad(alpha / 180D * Math.PI);
	}

	public Vec3NT rotateAroundZDeg(double alpha) {
		return this.rotateAroundZRad(alpha / 180D * Math.PI);
	}
	
	public static double getMinX(Vec3NT... vecs) {
		double min = Double.POSITIVE_INFINITY;
		for(Vec3NT vec : vecs) if(vec.xCoord < min) min = vec.xCoord;
		return min;
	}
	
	public static double getMinY(Vec3NT... vecs) {
		double min = Double.POSITIVE_INFINITY;
		for(Vec3NT vec : vecs) if(vec.yCoord < min) min = vec.yCoord;
		return min;
	}
	
	public static double getMinZ(Vec3NT... vecs) {
		double min = Double.POSITIVE_INFINITY;
		for(Vec3NT vec : vecs) if(vec.zCoord < min) min = vec.zCoord;
		return min;
	}
	
	public static double getMaxX(Vec3NT... vecs) {
		double max = Double.NEGATIVE_INFINITY;
		for(Vec3NT vec : vecs) if(vec.xCoord > max) max = vec.xCoord;
		return max;
	}
	
	public static double getMaxY(Vec3NT... vecs) {
		double max = Double.NEGATIVE_INFINITY;
		for(Vec3NT vec : vecs) if(vec.yCoord > max) max = vec.yCoord;
		return max;
	}
	
	public static double getMaxZ(Vec3NT... vecs) {
		double max = Double.NEGATIVE_INFINITY;
		for(Vec3NT vec : vecs) if(vec.zCoord > max) max = vec.zCoord;
		return max;
	}
}
