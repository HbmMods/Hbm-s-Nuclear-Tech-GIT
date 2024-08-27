package com.hbm.dim.noise;

import java.util.Random;

import com.hbm.util.BobMathUtil;

public class SimplexNoiseSampler {
	protected static final int[][] GRADIENTS = new int[][]{{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}, {1, 1, 0}, {0, -1, 1}, {-1, 1, 0}, {0, -1, -1}};
	private static final double SQRT_3 = Math.sqrt(3.0D);
	private static final double SKEW_FACTOR_2D;
	private static final double UNSKEW_FACTOR_2D;
	private final int[] permutations = new int[512];
	public final double originX;
	public final double originY;
	public final double originZ;

	public SimplexNoiseSampler(Random random) {
		this.originX = random.nextDouble() * 256.0D;
		this.originY = random.nextDouble() * 256.0D;
		this.originZ = random.nextDouble() * 256.0D;

		int j;
		for(j = 0; j < 256; this.permutations[j] = j++) {
		}

		for(j = 0; j < 256; ++j) {
			int k = random.nextInt(256 - j);
			int l = this.permutations[j];
			this.permutations[j] = this.permutations[k + j];
			this.permutations[k + j] = l;
		}

	}

	private int getGradient(int hash) {
		return this.permutations[hash & 255];
	}

	protected static double dot(int[] gArr, double x, double y, double z) {
		return (double)gArr[0] * x + (double)gArr[1] * y + (double)gArr[2] * z;
	}

	private double grad(int hash, double x, double y, double z, double distance) {
		double d = distance - x * x - y * y - z * z;
		double f;
		if (d < 0.0D) {
			f = 0.0D;
		} else {
			d *= d;
			f = d * d * dot(GRADIENTS[hash], x, y, z);
		}

		return f;
	}

	public double sample(double x, double y) {
		double d = (x + y) * SKEW_FACTOR_2D;
		int i = BobMathUtil.floor(x + d);
		int j = BobMathUtil.floor(y + d);
		double e = (double)(i + j) * UNSKEW_FACTOR_2D;
		double f = (double)i - e;
		double g = (double)j - e;
		double h = x - f;
		double k = y - g;
		byte n;
		byte o;
		if (h > k) {
			n = 1;
			o = 0;
		} else {
			n = 0;
			o = 1;
		}

		double p = h - (double)n + UNSKEW_FACTOR_2D;
		double q = k - (double)o + UNSKEW_FACTOR_2D;
		double r = h - 1.0D + 2.0D * UNSKEW_FACTOR_2D;
		double s = k - 1.0D + 2.0D * UNSKEW_FACTOR_2D;
		int t = i & 255;
		int u = j & 255;
		int v = this.getGradient(t + this.getGradient(u)) % 12;
		int w = this.getGradient(t + n + this.getGradient(u + o)) % 12;
		int z = this.getGradient(t + 1 + this.getGradient(u + 1)) % 12;
		double aa = this.grad(v, h, k, 0.0D, 0.5D);
		double ab = this.grad(w, p, q, 0.0D, 0.5D);
		double ac = this.grad(z, r, s, 0.0D, 0.5D);
		return 70.0D * (aa + ab + ac);
	}

	public double sample(double x, double y, double z) {
		double e = (x + y + z) * 0.3333333333333333D;
		int i = BobMathUtil.floor(x + e);
		int j = BobMathUtil.floor(y + e);
		int k = BobMathUtil.floor(z + e);
		double g = (double)(i + j + k) * 0.16666666666666666D;
		double h = (double)i - g;
		double l = (double)j - g;
		double m = (double)k - g;
		double n = x - h;
		double o = y - l;
		double p = z - m;
		byte w;
		byte aa;
		byte ab;
		byte ac;
		byte ad;
		byte bc;
		if (n >= o) {
			if (o >= p) {
				w = 1;
				aa = 0;
				ab = 0;
				ac = 1;
				ad = 1;
				bc = 0;
			} else if (n >= p) {
				w = 1;
				aa = 0;
				ab = 0;
				ac = 1;
				ad = 0;
				bc = 1;
			} else {
				w = 0;
				aa = 0;
				ab = 1;
				ac = 1;
				ad = 0;
				bc = 1;
			}
		} else if (o < p) {
			w = 0;
			aa = 0;
			ab = 1;
			ac = 0;
			ad = 1;
			bc = 1;
		} else if (n < p) {
			w = 0;
			aa = 1;
			ab = 0;
			ac = 0;
			ad = 1;
			bc = 1;
		} else {
			w = 0;
			aa = 1;
			ab = 0;
			ac = 1;
			ad = 1;
			bc = 0;
		}

		double bd = n - (double)w + 0.16666666666666666D;
		double be = o - (double)aa + 0.16666666666666666D;
		double bf = p - (double)ab + 0.16666666666666666D;
		double bg = n - (double)ac + 0.3333333333333333D;
		double bh = o - (double)ad + 0.3333333333333333D;
		double bi = p - (double)bc + 0.3333333333333333D;
		double bj = n - 1.0D + 0.5D;
		double bk = o - 1.0D + 0.5D;
		double bl = p - 1.0D + 0.5D;
		int bm = i & 255;
		int bn = j & 255;
		int bo = k & 255;
		int bp = this.getGradient(bm + this.getGradient(bn + this.getGradient(bo))) % 12;
		int bq = this.getGradient(bm + w + this.getGradient(bn + aa + this.getGradient(bo + ab))) % 12;
		int br = this.getGradient(bm + ac + this.getGradient(bn + ad + this.getGradient(bo + bc))) % 12;
		int bs = this.getGradient(bm + 1 + this.getGradient(bn + 1 + this.getGradient(bo + 1))) % 12;
		double bt = this.grad(bp, n, o, p, 0.6D);
		double bu = this.grad(bq, bd, be, bf, 0.6D);
		double bv = this.grad(br, bg, bh, bi, 0.6D);
		double bw = this.grad(bs, bj, bk, bl, 0.6D);
		return 32.0D * (bt + bu + bv + bw);
	}

	static {
		SKEW_FACTOR_2D = 0.5D * (SQRT_3 - 1.0D);
		UNSKEW_FACTOR_2D = (3.0D - SQRT_3) / 6.0D;
	}
}
