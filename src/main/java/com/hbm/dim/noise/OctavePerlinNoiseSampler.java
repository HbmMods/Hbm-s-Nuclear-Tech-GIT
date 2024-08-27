package com.hbm.dim.noise;

import com.hbm.util.BobMathUtil;
import com.hbm.util.Tuple.Pair;

import java.util.*;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OctavePerlinNoiseSampler {

	private final PerlinNoiseSampler[] octaveSamplers;
	private final List<Double> amplitudes;
	private final double persistence;
	private final double lacunarity;

	public OctavePerlinNoiseSampler(Random random, IntStream octaves) {
		this(random, octaves.boxed().collect(Collectors.toList()));
	}

	public OctavePerlinNoiseSampler(Random random, List<Integer> octaves) {
		this(random, (SortedSet<Integer>)(new TreeSet<>(octaves)));
	}

	public static OctavePerlinNoiseSampler create(Random Random, int i, double... octaves) {
		List<Double> list = new ArrayList<>();
		for (double octave : octaves) {
			list.add(octave);
		}

		return create(Random, i, list);
	}

	public static OctavePerlinNoiseSampler create(Random random, int offset, List<Double> amplitudes) {
		return new OctavePerlinNoiseSampler(random, new Pair<>(offset, amplitudes));
	}

	private static Pair<Integer, List<Double>> calculateAmplitudes(SortedSet<Integer> octaves) {
		if (octaves.isEmpty()) {
			throw new IllegalArgumentException("Need some octaves!");
		} else {
			int i = -octaves.first();
			int j = octaves.last();
			int k = i + j + 1;
			if (k < 1) {
				throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
			} else {
				List<Double> doubleList = new ArrayList<>();
				Iterator<Integer> intBidirectionalIterator = octaves.iterator();

				while(intBidirectionalIterator.hasNext()) {
					int l = intBidirectionalIterator.next();
					doubleList.set(l + i, 1.0D);
				}

				return new Pair<>(-i, doubleList);
			}
		}
	}

	private OctavePerlinNoiseSampler(Random random, SortedSet<Integer> octaves) {
		this(random, octaves, Random::new);
	}

	private OctavePerlinNoiseSampler(Random random, SortedSet<Integer> octaves, LongFunction<Random> randomFunction) {
		this(random, calculateAmplitudes(octaves), randomFunction);
	}

	protected OctavePerlinNoiseSampler(Random random, Pair<Integer, List<Double>> offsetAndAmplitudes) {
		this(random, offsetAndAmplitudes, Random::new);
	}

	protected OctavePerlinNoiseSampler(Random random, Pair<Integer, List<Double>> octaves, LongFunction<Random> randomFunction) {
		int i = octaves.getKey();
		this.amplitudes = octaves.getValue();
		PerlinNoiseSampler perlinNoiseSampler = new PerlinNoiseSampler(random);
		int j = this.amplitudes.size();
		int k = -i;
		this.octaveSamplers = new PerlinNoiseSampler[j];
		if (k >= 0 && k < j) {
			double d = this.amplitudes.get(k);
			if (d != 0.0D) {
				this.octaveSamplers[k] = perlinNoiseSampler;
			}
		}

		for(int l = k - 1; l >= 0; --l) {
			if (l < j) {
				double e = this.amplitudes.get(l);
				if (e != 0.0D) {
					this.octaveSamplers[l] = new PerlinNoiseSampler(random);
				} else {
					skipCalls(random);
				}
			} else {
				skipCalls(random);
			}
		}

		if (k < j - 1) {
			throw new IllegalArgumentException("Positive octaves are temporarily disabled");
		} else {
			this.lacunarity = Math.pow(2.0D, (double)(-k));
			this.persistence = Math.pow(2.0D, (double)(j - 1)) / (Math.pow(2.0D, (double)j) - 1.0D);
		}
	}

	private static void skipCalls(Random random) {
//        random.skip(262);
	}

	public double sample(double x, double y, double z) {
		return this.sample(x, y, z, 0.0D, 0.0D, false);
	}

	@Deprecated
	public double sample(double x, double y, double z, double yScale, double yMax, boolean useOrigin) {
		double d = 0.0D;
		double e = this.lacunarity;
		double f = this.persistence;

		for(int i = 0; i < this.octaveSamplers.length; ++i) {
			PerlinNoiseSampler perlinNoiseSampler = this.octaveSamplers[i];
			if (perlinNoiseSampler != null) {
				double g = perlinNoiseSampler.sample(maintainPrecision(x * e), useOrigin ? -perlinNoiseSampler.originY : maintainPrecision(y * e), maintainPrecision(z * e), yScale * e, yMax * e);
				d += this.amplitudes.get(i) * g * f;
			}

			e *= 2.0D;
			f /= 2.0D;
		}

		return d;
	}

	public PerlinNoiseSampler getOctave(int octave) {
		return this.octaveSamplers[this.octaveSamplers.length - 1 - octave];
	}

	public static double maintainPrecision(double value) {
		return value - (double) BobMathUtil.lfloor(value / 3.3554432E7D + 0.5D) * 3.3554432E7D;
	}

	public double sample(double x, double y, double yScale, double yMax) {
		return this.sample(x, y, 0.0D, yScale, yMax, false);
	}
}