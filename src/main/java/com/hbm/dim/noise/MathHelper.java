package com.hbm.dim.noise;

public final class MathHelper {
	private MathHelper() {}

	public static int floor(double value) {
		int i = (int)value;
		return value < (double)i ? i - 1 : i;
	}

	public static long lfloor(double value) {
		long l = (long)value;
		return value < (double)l ? l - 1L : l;
	}

	public static double perlinFade(double value) {
		return value * value * value * (value * (value * 6.0D - 15.0D) + 10.0D);
	}

	public static double perlinFadeDerivative(double value) {
		return 30.0D * value * value * (value - 1.0D) * (value - 1.0D);
	}

	public static double lerp(double delta, double start, double end) {
		return start + delta * (end - start);
	}

	public static double lerp2(double deltaX, double deltaY, double x0y0, double x1y0, double x0y1, double x1y1) {
		return lerp(deltaY, lerp(deltaX, x0y0, x1y0), lerp(deltaX, x0y1, x1y1));
	}

	public static double lerp3(double deltaX, double deltaY, double deltaZ, double x0y0z0, double x1y0z0, double x0y1z0, double x1y1z0, double x0y0z1, double x1y0z1, double x0y1z1, double x1y1z1) {
		return lerp(deltaZ, lerp2(deltaX, deltaY, x0y0z0, x1y0z0, x0y1z0, x1y1z0), lerp2(deltaX, deltaY, x0y0z1, x1y0z1, x0y1z1, x1y1z1));
	}

	public static double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		} else {
			return value > max ? max : value;
		}
	}

	public static double getLerpProgress(double value, double start, double end) {
		return (value - start) / (end - start);
	}

	public static double lerpFromProgress(double lerpValue, double lerpStart, double lerpEnd, double start, double end) {
		return lerp(getLerpProgress(lerpValue, lerpStart, lerpEnd), start, end);
	}

	public static double clampedLerp(double start, double end, double delta) {
		if (delta < 0.0D) {
			return start;
		} else {
			return delta > 1.0D ? end : lerp(delta, start, end);
		}
	}
}
