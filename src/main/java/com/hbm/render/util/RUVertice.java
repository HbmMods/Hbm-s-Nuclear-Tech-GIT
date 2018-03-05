package com.hbm.render.util;

public class RUVertice {
	
	public float x;
	public float y;
	public float z;

	public RUVertice(float X, float Y, float Z) {
		x = X;
		y = Y;
		z = Z;
	}

	public RUVertice normalize() {
		float l = (float) Math.sqrt(x * x + y * y + z * z);
		x /= l;
		y /= l;
		z /= l;
		return this;
	}
}
