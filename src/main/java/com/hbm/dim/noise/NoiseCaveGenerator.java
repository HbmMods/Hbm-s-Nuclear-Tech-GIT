package com.hbm.dim.noise;

import java.util.Random;

import com.hbm.util.BobMathUtil;

public final class NoiseCaveGenerator {

	private final DoublePerlinNoiseSampler terrainAdditionNoise;
	private final DoublePerlinNoiseSampler pillarNoise;
	private final DoublePerlinNoiseSampler pillarFalloffNoise;
	private final DoublePerlinNoiseSampler pillarScaleNoise;
	private final DoublePerlinNoiseSampler caveNoise;
	private final DoublePerlinNoiseSampler horizontalCaveNoise;
	private final DoublePerlinNoiseSampler caveScaleNoise;
	private final DoublePerlinNoiseSampler caveFalloffNoise;
	private final DoublePerlinNoiseSampler tunnelNoise1;
	private final DoublePerlinNoiseSampler tunnelNoise2;
	private final DoublePerlinNoiseSampler tunnelScaleNoise;
	private final DoublePerlinNoiseSampler tunnelFalloffNoise;
	private final DoublePerlinNoiseSampler offsetNoise;
	private final DoublePerlinNoiseSampler offsetScaleNoise;
	private final DoublePerlinNoiseSampler caveDensityNoise;

	public NoiseCaveGenerator(Random random) {
		this.pillarNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -7, 1.0D, 1.0D);
		this.pillarFalloffNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -8, 1.0D);
		this.pillarScaleNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -8, 1.0D);
		this.caveNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -7, 1.0D);
		this.horizontalCaveNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -8, 1.0D);
		this.caveScaleNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -11, 1.0D);
		this.caveFalloffNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -11, 1.0D);
		this.tunnelNoise1 = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -7, 1.0D);
		this.tunnelNoise2 = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -7, 1.0D);
		this.tunnelScaleNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -11, 1.0D);
		this.tunnelFalloffNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -8, 1.0D);
		this.offsetNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -5, 1.0D);
		this.offsetScaleNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -8, 1.0D);
		this.terrainAdditionNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -8, 1.0D);
		this.caveDensityNoise = DoublePerlinNoiseSampler.create(new Random(random.nextLong()), -8, 0.5D, 1.0D, 2.0D, 1.0D, 2.0D, 1.0D, 0.0D, 2.0D, 0.0D);
	}

	public double sample(double noise, int y, int z, int x) {
		boolean generateLimited = noise < 170.0D;
		double tunnelOffset = this.getTunnelOffsetNoise(x, y, z);
		double tunnel = this.getTunnelNoise(x, y, z);

		if (generateLimited) {
			return Math.min(noise, (tunnel + tunnelOffset) * 128.0D * 5.0D);
		} else {
			double caveDensity = this.caveDensityNoise.sample((double) x, (double) y / 1.5D, (double) z);

			double scaledCaveDensity = BobMathUtil.clamp(caveDensity + 0.25D, -1.0D, 1.0D);
			double yScale = (float)(30 - y) / 8.0F;
			double caveOffset = scaledCaveDensity + BobMathUtil.clampedLerp(0.5D, 0.0D, yScale);

			double terrainAddition = this.getTerrainAdditionNoise(x, y, z);
			double caveNoise = this.getCaveNoise(x, y, z);

			double offset = caveOffset + terrainAddition;
			double smallerNoise = Math.min(offset, Math.min(tunnel, caveNoise) + tunnelOffset);
			double finalNoise = Math.max(smallerNoise, this.getPillarNoise(x, y, z));

			return 128.0D * BobMathUtil.clamp(finalNoise, -1.0D, 1.0D);
		}
	}

	private double getPillarNoise(int x, int y, int z) {
		double pillarFalloff = lerpFromProgress(this.pillarFalloffNoise, (double)x, (double)y, (double)z, 0.0D, 2.0D);
		double pillarScale = lerpFromProgress(this.pillarScaleNoise, (double)x, (double)y, (double)z, 0.0D, 1.1D);

		pillarScale = Math.pow(pillarScale, 3.0D);
		double pillarNoise = this.pillarNoise.sample((double)x * 25.0D, (double)y * 0.3D, (double)z * 25.0D);

		pillarNoise = pillarScale * (pillarNoise * 2.0D - pillarFalloff);

		// Make it so pillars only exist in certain areas
		return pillarNoise > 0.03D ? pillarNoise : Double.NEGATIVE_INFINITY;
	}

	private double getTerrainAdditionNoise(int x, int y, int z) {
		// Creates ledges within the cave, making it less round and uniform
		double addition = this.terrainAdditionNoise.sample(x, y * 8, z);
		return addition * addition * 4.0D;
	}

	private double getTunnelNoise(int x, int y, int z) {
		double tunnelScaleNoise = this.tunnelScaleNoise.sample(x * 2, y, z * 2);
		double tunnelScale = scaleTunnels(tunnelScaleNoise);

		double tunnelFalloff = lerpFromProgress(this.tunnelFalloffNoise, x, y, z, 0.065D, 0.088D);

		double tunnelNoise1 = sample(this.tunnelNoise1, x, y, z, tunnelScale);
		double scaledTunnelNoise1 = Math.abs(tunnelScale * tunnelNoise1) - tunnelFalloff;

		double tunnelNoise2 = sample(this.tunnelNoise2, x, y, z, tunnelScale);
		double scaledTunnelNoise2 = Math.abs(tunnelScale * tunnelNoise2) - tunnelFalloff;

		// Find the max of the 2 tunnel noises, creating multiple tunnels that criss-cross
		return clamp(Math.max(scaledTunnelNoise1, scaledTunnelNoise2));
	}

	private double getCaveNoise(int x, int y, int z) {
		double caveScaleNoise = this.caveScaleNoise.sample((x * 2), y, (z * 2));
		double caveScale = scaleCaves(caveScaleNoise);

		double caveFalloff = lerpFromProgress(this.caveFalloffNoise, (x * 2), y, (z * 2), 0.6D, 1.3D);
		double caveNoise = sample(this.caveNoise, x, y, z, caveScale);

		double scaledCaveNoise = Math.abs(caveScale * caveNoise) - 0.083D * caveFalloff;

		// GregGen change: make the yStart -4 and the yEnd 4, but then add 4.
		// This allows us to create values between 0 and 8, which will be the offset of the caves.
		int yStart = -4;
		double horizontalCaveNoise = lerpFromProgress(this.horizontalCaveNoise, x, 0.0D, z,  yStart, 4.0D) + 4;

		double caveFalloffNoise = (Math.abs((horizontalCaveNoise - (double)y / 8.0D)) - (2.0D * caveFalloff));
		caveFalloffNoise = caveFalloffNoise * caveFalloffNoise * caveFalloffNoise;
		return clamp(Math.max(caveFalloffNoise, scaledCaveNoise));
	}

	private double getTunnelOffsetNoise(int x, int y, int z) {
		double scale = lerpFromProgress(this.offsetScaleNoise, x, y, z, 0.0D, 0.1D);
		return (0.4D - Math.abs(this.offsetNoise.sample(x, y, z))) * scale;
	}

	private static double clamp(double value) {
		return BobMathUtil.clamp(value, -1.0D, 1.0D);
	}

	private static double sample(DoublePerlinNoiseSampler sampler, double x, double y, double z, double scale) {
		return sampler.sample(x / scale, y / scale, z / scale);
	}

	public static double lerpFromProgress(DoublePerlinNoiseSampler sampler, double x, double y, double z, double start, double end) {
		double value = sampler.sample(x, y, z);
		return BobMathUtil.lerpFromProgress(value, -1.0D, 1.0D, start, end);
	}

	private static double scaleCaves(double value) {
		if (value < -0.75D) {
			return 0.5D;
		} else if (value < -0.5D) {
			return 0.75D;
		} else if (value < 0.5D) {
			return 1.0D;
		} else {
			return value < 0.75D ? 2.0D : 3.0D;
		}
	}

	private static double scaleTunnels(double value) {
		if (value < -0.5D) {
			return 0.75D;
		} else if (value < 0.0D) {
			return 1.0D;
		} else {
			return value < 0.5D ? 1.5D : 2.0D;
		}
	}
}