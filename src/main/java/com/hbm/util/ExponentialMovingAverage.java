package com.hbm.util;

/**
 * Calculates a moving average based on inputs provided over time.
 * @author BallOfEnergy01
 */
public class ExponentialMovingAverage {

	private final double alpha;
	private double ema = -1;

	public ExponentialMovingAverage(double alpha) {
		this.alpha = alpha;
	}

	public void next(double value) {
		if (ema == -1) {
			ema = value;
		} else {
			ema = alpha * value + (1 - alpha) * ema;
		}
	}

	public long getValue() {
		return (long) ema;
	}
}
