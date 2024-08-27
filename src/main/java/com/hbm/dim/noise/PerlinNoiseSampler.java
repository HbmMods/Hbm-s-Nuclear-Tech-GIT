package com.hbm.dim.noise;

import java.util.Random;

import com.hbm.util.BobMathUtil;

public final class PerlinNoiseSampler {

	private final byte[] permutations;
	public final double originX;
	public final double originY;
	public final double originZ;

	public PerlinNoiseSampler(Random random) {
		this.originX = random.nextDouble() * 256.0D;
		this.originY = random.nextDouble() * 256.0D;
		this.originZ = random.nextDouble() * 256.0D;
		this.permutations = new byte[256];

		int j;
		for(j = 0; j < 256; ++j) {
			this.permutations[j] = (byte)j;
		}

		for(j = 0; j < 256; ++j) {
			int k = random.nextInt(256 - j);
			byte b = this.permutations[j];
			this.permutations[j] = this.permutations[j + k];
			this.permutations[j + k] = b;
		}

	}

	public double sample(double x, double y, double z) {
		return this.sample(x, y, z, 0.0D, 0.0D);
	}

	@Deprecated
	public double sample(double x, double y, double z, double yScale, double yMax) {
		double d = x + this.originX;
		double e = y + this.originY;
		double f = z + this.originZ;
		int i = BobMathUtil.floor(d);
		int j = BobMathUtil.floor(e);
		int k = BobMathUtil.floor(f);
		double g = d - (double)i;
		double h = e - (double)j;
		double l = f - (double)k;
		double p;
		if (yScale != 0.0D) {
			double n;
			if (yMax >= 0.0D && yMax < h) {
				n = yMax;
			} else {
				n = h;
			}

			p = (double)BobMathUtil.floor(n / yScale + 1.0000000116860974E-7D) * yScale;
		} else {
			p = 0.0D;
		}

		return this.sample(i, j, k, g, h - p, l, h);
	}

	public double sampleDerivative(double x, double y, double z, double[] ds) {
		double d = x + this.originX;
		double e = y + this.originY;
		double f = z + this.originZ;
		int i = BobMathUtil.floor(d);
		int j = BobMathUtil.floor(e);
		int k = BobMathUtil.floor(f);
		double g = d - (double)i;
		double h = e - (double)j;
		double l = f - (double)k;
		return this.sampleDerivative(i, j, k, g, h, l, ds);
	}

	private static double grad(int hash, double x, double y, double z) {
		return SimplexNoiseSampler.dot(SimplexNoiseSampler.GRADIENTS[hash & 15], x, y, z);
	}

	private int getGradient(int hash) {
		return this.permutations[hash & 255] & 255;
	}

	private double sample(int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double fadeLocalX) {
		int i = this.getGradient(sectionX);
		int j = this.getGradient(sectionX + 1);
		int k = this.getGradient(i + sectionY);
		int l = this.getGradient(i + sectionY + 1);
		int m = this.getGradient(j + sectionY);
		int n = this.getGradient(j + sectionY + 1);
		double d = grad(this.getGradient(k + sectionZ), localX, localY, localZ);
		double e = grad(this.getGradient(m + sectionZ), localX - 1.0D, localY, localZ);
		double f = grad(this.getGradient(l + sectionZ), localX, localY - 1.0D, localZ);
		double g = grad(this.getGradient(n + sectionZ), localX - 1.0D, localY - 1.0D, localZ);
		double h = grad(this.getGradient(k + sectionZ + 1), localX, localY, localZ - 1.0D);
		double o = grad(this.getGradient(m + sectionZ + 1), localX - 1.0D, localY, localZ - 1.0D);
		double p = grad(this.getGradient(l + sectionZ + 1), localX, localY - 1.0D, localZ - 1.0D);
		double q = grad(this.getGradient(n + sectionZ + 1), localX - 1.0D, localY - 1.0D, localZ - 1.0D);
		double r = BobMathUtil.perlinFade(localX);
		double s = BobMathUtil.perlinFade(fadeLocalX);
		double t = BobMathUtil.perlinFade(localZ);
		return BobMathUtil.lerp3(r, s, t, d, e, f, g, h, o, p, q);
	}

	private double sampleDerivative(int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double[] ds) {
		int i = this.getGradient(sectionX);
		int j = this.getGradient(sectionX + 1);
		int k = this.getGradient(i + sectionY);
		int l = this.getGradient(i + sectionY + 1);
		int m = this.getGradient(j + sectionY);
		int n = this.getGradient(j + sectionY + 1);
		int o = this.getGradient(k + sectionZ);
		int p = this.getGradient(m + sectionZ);
		int q = this.getGradient(l + sectionZ);
		int r = this.getGradient(n + sectionZ);
		int s = this.getGradient(k + sectionZ + 1);
		int t = this.getGradient(m + sectionZ + 1);
		int u = this.getGradient(l + sectionZ + 1);
		int v = this.getGradient(n + sectionZ + 1);
		int[] is = SimplexNoiseSampler.GRADIENTS[o & 15];
		int[] js = SimplexNoiseSampler.GRADIENTS[p & 15];
		int[] ks = SimplexNoiseSampler.GRADIENTS[q & 15];
		int[] ls = SimplexNoiseSampler.GRADIENTS[r & 15];
		int[] ms = SimplexNoiseSampler.GRADIENTS[s & 15];
		int[] ns = SimplexNoiseSampler.GRADIENTS[t & 15];
		int[] os = SimplexNoiseSampler.GRADIENTS[u & 15];
		int[] ps = SimplexNoiseSampler.GRADIENTS[v & 15];
		double d = SimplexNoiseSampler.dot(is, localX, localY, localZ);
		double e = SimplexNoiseSampler.dot(js, localX - 1.0D, localY, localZ);
		double f = SimplexNoiseSampler.dot(ks, localX, localY - 1.0D, localZ);
		double g = SimplexNoiseSampler.dot(ls, localX - 1.0D, localY - 1.0D, localZ);
		double h = SimplexNoiseSampler.dot(ms, localX, localY, localZ - 1.0D);
		double w = SimplexNoiseSampler.dot(ns, localX - 1.0D, localY, localZ - 1.0D);
		double x = SimplexNoiseSampler.dot(os, localX, localY - 1.0D, localZ - 1.0D);
		double y = SimplexNoiseSampler.dot(ps, localX - 1.0D, localY - 1.0D, localZ - 1.0D);
		double z = BobMathUtil.perlinFade(localX);
		double aa = BobMathUtil.perlinFade(localY);
		double ab = BobMathUtil.perlinFade(localZ);
		double ac = BobMathUtil.lerp3(z, aa, ab, (double)is[0], (double)js[0], (double)ks[0], (double)ls[0], (double)ms[0], (double)ns[0], (double)os[0], (double)ps[0]);
		double ad = BobMathUtil.lerp3(z, aa, ab, (double)is[1], (double)js[1], (double)ks[1], (double)ls[1], (double)ms[1], (double)ns[1], (double)os[1], (double)ps[1]);
		double ae = BobMathUtil.lerp3(z, aa, ab, (double)is[2], (double)js[2], (double)ks[2], (double)ls[2], (double)ms[2], (double)ns[2], (double)os[2], (double)ps[2]);
		double af = BobMathUtil.lerp2(aa, ab, e - d, g - f, w - h, y - x);
		double ag = BobMathUtil.lerp2(ab, z, f - d, x - h, g - e, y - w);
		double ah = BobMathUtil.lerp2(z, aa, h - d, w - e, x - f, y - g);
		double ai = BobMathUtil.perlinFadeDerivative(localX);
		double aj = BobMathUtil.perlinFadeDerivative(localY);
		double ak = BobMathUtil.perlinFadeDerivative(localZ);
		double al = ac + ai * af;
		double am = ad + aj * ag;
		double an = ae + ak * ah;
		ds[0] += al;
		ds[1] += am;
		ds[2] += an;
		return BobMathUtil.lerp3(z, aa, ab, d, e, f, g, h, w, x, y);
	}
}
