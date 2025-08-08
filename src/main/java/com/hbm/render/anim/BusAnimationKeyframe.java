package com.hbm.render.anim;

import com.hbm.config.ClientConfig;

//"pieces" that make up a bus
public class BusAnimationKeyframe {

	// whether the next frame "snaps" to the intended value or has interpolation
	// it's an enum so stuff like accelerated animations between just
	// two frames could be implemented
	public static enum IType {
		/** Teleport */
		CONSTANT,
		/** Linear interpolation */
		LINEAR,
		/** "Sine wave up", quarter of a sine peak that goes from neutral to rising */
		SIN_UP,
		/** "Sine wave down", quarter of a sine peak that goes from rising back to neutral */
		SIN_DOWN,
		/** "Sine wave", first half of a sine peak, accelerating up and then decelerating, makes for smooth movement */
		SIN_FULL,

		// blender magic curves
		BEZIER,

		// blender inertial
		SINE,
		QUAD,
		CUBIC,
		QUART,
		QUINT,
		EXPO,
		CIRC,

		// blendor dynamic
		BOUNCE,
		ELASTIC,
		BACK,
	}

	// Easing
	public static enum EType {
		AUTO,
		EASE_IN,
		EASE_OUT,
		EASE_IN_OUT,
	}

	// Handle type
	public static enum HType {
		FREE,
		ALIGNED,
		VECTOR,
		AUTO,
		AUTO_CLAMPED,
	}

	public double value;
	public IType interpolationType;
	public EType easingType;
	public int duration;

	// bezier handles
	public double leftX;
	public double leftY;
	public HType leftType;
	public double rightX;
	public double rightY;
	public HType rightType;

	// elastics
	public double amplitude;
	public double period;

	// back (overshoot)
	public double back;

	// this one can be used for "reset" type keyframes
	public BusAnimationKeyframe() {
		this.value = 0;
		this.duration = 1;
		this.interpolationType = IType.LINEAR;
		this.easingType = EType.AUTO;
	}

	public BusAnimationKeyframe(double value, int duration) {
		this();
		this.value = value;
		this.duration = (int) (duration / Math.max(0.001D, ClientConfig.GUN_ANIMATION_SPEED.get()));
	}

	public BusAnimationKeyframe(double value, int duration, IType interpolation) {
		this(value, duration);
		this.interpolationType = interpolation;
	}

	public BusAnimationKeyframe(double value, int duration, IType interpolation, EType easing) {
		this(value, duration, interpolation);
		this.easingType = easing;
	}

	public double interpolate(double startTime, double currentTime, BusAnimationKeyframe previous) {
		if(previous == null)
			previous = new BusAnimationKeyframe();

		double a = value;
		double b = previous.value;
		double t = time(startTime, currentTime, duration);

		double begin = previous.value;
		double change = value - previous.value;
		double time = currentTime - startTime;

		// Constant value optimisation
		if(Math.abs(previous.value - value) < 0.000001) return value;

		if(previous.interpolationType == IType.BEZIER) {
			double v1x = startTime;
			double v1y = previous.value;
			double v2x = previous.rightX;
			double v2y = previous.rightY;

			double v3x = leftX;
			double v3y = leftY;
			double v4x = startTime + duration;
			double v4y = value;

			// correct beziers into non-looping fcurves
			double h1x = v1x - v2x;
			double h1y = v1y - v2y;

			double h2x = v4x - v3x;
			double h2y = v4y - v3y;

			double len = v4x - v1x;
			double len1 = Math.abs(h1x);
			double len2 = Math.abs(h2x);

			if(len1 + len2 != 0) {
				if(len1 > len) {
					double fac = len / len1;
					v2x = v1x - fac * h1x;
					v2y = v1y - fac * h1y;
				}

				if(len2 > len) {
					double fac = len / len2;
					v3x = v4x - fac * h2x;
					v3y = v4y - fac * h2y;
				}
			}

			double curveT = findZero(currentTime, v1x, v2x, v3x, v4x);
			return cubicBezier(v1y, v2y, v3y, v4y, curveT);
		} else if(previous.interpolationType == IType.BACK) {
			switch (previous.easingType) {
				case EASE_IN: return BLI_easing_back_ease_in(time, begin, change, duration, previous.back);
				case EASE_IN_OUT: return BLI_easing_back_ease_in_out(time, begin, change, duration, previous.back);
				default: return BLI_easing_back_ease_out(time, begin, change, duration, previous.back);
			}
		} else if(previous.interpolationType == IType.BOUNCE) {
			switch (previous.easingType) {
				case EASE_IN: return BLI_easing_bounce_ease_in(time, begin, change, duration);
				case EASE_IN_OUT: return BLI_easing_bounce_ease_in_out(time, begin, change, duration);
				default: return BLI_easing_bounce_ease_out(time, begin, change, duration);
			}
		} else if(previous.interpolationType == IType.CIRC) {
			switch (previous.easingType) {
				case EASE_OUT: return BLI_easing_circ_ease_out(time, begin, change, duration);
				case EASE_IN_OUT: return BLI_easing_circ_ease_in_out(time, begin, change, duration);
				default: return BLI_easing_circ_ease_in(time, begin, change, duration);
			}
		} else if(previous.interpolationType == IType.CUBIC) {
			switch (previous.easingType) {
				case EASE_OUT: return BLI_easing_cubic_ease_out(time, begin, change, duration);
				case EASE_IN_OUT: return BLI_easing_cubic_ease_in_out(time, begin, change, duration);
				default: return BLI_easing_cubic_ease_in(time, begin, change, duration);
			}
		} else if(previous.interpolationType == IType.ELASTIC) {
			switch (previous.easingType) {
				case EASE_IN: return BLI_easing_elastic_ease_in(time, begin, change, duration, previous.amplitude, previous.period);
				case EASE_IN_OUT: return BLI_easing_elastic_ease_in_out(time, begin, change, duration, previous.amplitude, previous.period);
				default: return BLI_easing_elastic_ease_out(time, begin, change, duration, previous.amplitude, previous.period);
			}
		} else if(previous.interpolationType == IType.EXPO) {
			switch (previous.easingType) {
				case EASE_OUT: return BLI_easing_expo_ease_out(time, begin, change, duration);
				case EASE_IN_OUT: return BLI_easing_expo_ease_in_out(time, begin, change, duration);
				default: return BLI_easing_expo_ease_in(time, begin, change, duration);
			}
		} else if(previous.interpolationType == IType.QUAD) {
			switch (previous.easingType) {
				case EASE_OUT: return BLI_easing_quad_ease_out(time, begin, change, duration);
				case EASE_IN_OUT: return BLI_easing_quad_ease_in_out(time, begin, change, duration);
				default: return BLI_easing_quad_ease_in(time, begin, change, duration);
			}
		} else if(previous.interpolationType == IType.QUART) {
			switch (previous.easingType) {
				case EASE_OUT: return BLI_easing_quart_ease_out(time, begin, change, duration);
				case EASE_IN_OUT: return BLI_easing_quart_ease_in_out(time, begin, change, duration);
				default: return BLI_easing_quart_ease_in(time, begin, change, duration);
			}
		} else if(previous.interpolationType == IType.QUINT) {
			switch (previous.easingType) {
				case EASE_OUT: return BLI_easing_quint_ease_out(time, begin, change, duration);
				case EASE_IN_OUT: return BLI_easing_quint_ease_in_out(time, begin, change, duration);
				default: return BLI_easing_quint_ease_in(time, begin, change, duration);
			}
		} else if(previous.interpolationType == IType.SINE) {
			switch (previous.easingType) {
				case EASE_OUT: return BLI_easing_sine_ease_out(time, begin, change, duration);
				case EASE_IN_OUT: return BLI_easing_sine_ease_in_out(time, begin, change, duration);
				default: return BLI_easing_sine_ease_in(time, begin, change, duration);
			}
		}

		return (a - b) * t + b;
	}

	private double sqrt3(double d) {
		if(d > 0.000001) {
			return Math.exp(Math.log(d) / 3.0);
		} else if(d > -0.000001) {
			return 0;
		} else {
			return -Math.exp(Math.log(-d) / 3.0);
		}
	}

	private double time(double start, double end, double duration) {
		if(interpolationType == IType.SIN_UP) return -Math.sin(((end - start) / duration * Math.PI + Math.PI) / 2) + 1;
		if(interpolationType == IType.SIN_DOWN) return Math.sin((end - start) / duration * Math.PI / 2);
		if(interpolationType == IType.SIN_FULL) return (-Math.cos((end - start) / duration * Math.PI) + 1) / 2D;
		return (end - start) / duration;
	}

	// Blender bezier solvers, but rewritten (pain)
	private double solveCubic(double c0, double c1, double c2, double c3) {
		if(c3 > 0.000001 || c3 < -0.000001) {
			double a = c2 / c3;
			double b = c1 / c3;
			double c = c0 / c3;
			a = a / 3;

			double p = b / 3 - a * a;
			double q = (2 * a * a * a - a * b + c) / 2;
			double d = q * q + p * p * p;

			if(d > 0.000001) {
				double t = Math.sqrt(d);
				return sqrt3(-q + t) + sqrt3(-q - t) - a;
			} else if(d > -0.000001) {
				double t = sqrt3(-q);
				double result = 2 * t - a;
				if(result < 0.000001 || result > 1.000001) {
					result = -t - a;
				}
				return result;
			}

			double phi = Math.acos(-q / Math.sqrt(-(p * p * p)));
			double t = Math.sqrt(-p);
			p = Math.cos(phi / 3);
			q = Math.sqrt(3 - 3 * p * p);
			double result = 2 * t * p - a;
			if(result < 0.000001 || result > 1.000001) {
				result = -t * (p + q) - a;
			}
			if(result < 0.000001 || result > 1.000001) {
				result = -t * (p - q) - a;
			}
			return result;
		}

		double a = c2;
		double b = c1;
		double c = c0;

		if(a > 0.000001) {
			double p = b * b - 4 * a * c;

			if(p > 0.000001) {
				p = Math.sqrt(p);
				double result = (-b - p) / (2 * a);
				if(result < 0.000001 || result > 1.000001) {
					result = (-b + p) / (2 * a);
				}
				return result;
			} else if(p > -0.000001) {
				return -b / (2 * a);
			}
		}

		if(b > 0.000001) {
			return -c / b;
		}

		return 0;
	}

	private double findZero(double t, double x1, double x2, double x3, double x4) {
		double c0 = x1 - t;
		double c1 = 3.0f * (x2 - x1);
		double c2 = 3.0f * (x1 - 2.0f * x2 + x3);
		double c3 = x4 - x1 + 3.0f * (x2 - x3);

		return solveCubic(c0, c1, c2, c3);
	}

	private double cubicBezier(double y1, double y2, double y3, double y4, double t) {
		double c0 = y1;
		double c1 = 3.0f * (y2 - y1);
		double c2 = 3.0f * (y1 - 2.0f * y2 + y3);
		double c3 = y4 - y1 + 3.0f * (y2 - y3);

		return c0 + t * c1 + t * t * c2 + t * t * t * c3;
	}

	/**
	 * EASING FUNCTIONS, taken directly from Blender `easing.c`
	 */

	double BLI_easing_back_ease_in(double time, double begin, double change, double duration, double overshoot) {
		time /= duration;
		return change * time * time * ((overshoot + 1) * time - overshoot) + begin;
	}

	double BLI_easing_back_ease_out(double time, double begin, double change, double duration, double overshoot) {
		time = time / duration - 1;
		return change * (time * time * ((overshoot + 1) * time + overshoot) + 1) + begin;
	}

	double BLI_easing_back_ease_in_out(double time, double begin, double change, double duration, double overshoot) {
		overshoot *= 1.525f;
		if((time /= duration / 2) < 1.0f) {
			return change / 2 * (time * time * ((overshoot + 1) * time - overshoot)) + begin;
		}
		time -= 2.0f;
		return change / 2 * (time * time * ((overshoot + 1) * time + overshoot) + 2) + begin;
	}

	double BLI_easing_bounce_ease_out(double time, double begin, double change, double duration) {
		time /= duration;
		if(time < (1 / 2.75f)) {
			return change * (7.5625f * time * time) + begin;
		}
		if(time < (2 / 2.75f)) {
			time -= (1.5f / 2.75f);
			return change * ((7.5625f * time) * time + 0.75f) + begin;
		}
		if(time < (2.5f / 2.75f)) {
			time -= (2.25f / 2.75f);
			return change * ((7.5625f * time) * time + 0.9375f) + begin;
		}
		time -= (2.625f / 2.75f);
		return change * ((7.5625f * time) * time + 0.984375f) + begin;
	}

	double BLI_easing_bounce_ease_in(double time, double begin, double change, double duration) {
		return change - BLI_easing_bounce_ease_out(duration - time, 0, change, duration) + begin;
	}

	double BLI_easing_bounce_ease_in_out(double time, double begin, double change, double duration) {
		if(time < duration / 2) {
			return BLI_easing_bounce_ease_in(time * 2, 0, change, duration) * 0.5f + begin;
		}
		return BLI_easing_bounce_ease_out(time * 2 - duration, 0, change, duration) * 0.5f + change * 0.5f + begin;
	}

	double BLI_easing_circ_ease_in(double time, double begin, double change, double duration) {
		time /= duration;
		return -change * (Math.sqrt(1 - time * time) - 1) + begin;
	}

	double BLI_easing_circ_ease_out(double time, double begin, double change, double duration) {
		time = time / duration - 1;
		return change * Math.sqrt(1 - time * time) + begin;
	}

	double BLI_easing_circ_ease_in_out(double time, double begin, double change, double duration) {
		if((time /= duration / 2) < 1.0f) {
			return -change / 2 * (Math.sqrt(1 - time * time) - 1) + begin;
		}
		time -= 2.0f;
		return change / 2 * (Math.sqrt(1 - time * time) + 1) + begin;
	}

	double BLI_easing_cubic_ease_in(double time, double begin, double change, double duration) {
		time /= duration;
		return change * time * time * time + begin;
	}

	double BLI_easing_cubic_ease_out(double time, double begin, double change, double duration) {
		time = time / duration - 1;
		return change * (time * time * time + 1) + begin;
	}

	double BLI_easing_cubic_ease_in_out(double time, double begin, double change, double duration) {
		if((time /= duration / 2) < 1.0f) {
			return change / 2 * time * time * time + begin;
		}
		time -= 2.0f;
		return change / 2 * (time * time * time + 2) + begin;
	}

	double elastic_blend(double time, double change, double duration, double amplitude, double s, double f) {
		if(change != 0) {
			/*
			 * Looks like a magic number,
			 * but this is a part of the sine curve we need to blend from
			 */
			double t = Math.abs(s);
			if(amplitude != 0) {
				f *= amplitude / Math.abs(change);
			} else {
				f = 0.0f;
			}

			if(Math.abs(time * duration) < t) {
				double l = Math.abs(time * duration) / t;
				f = (f * l) + (1.0f - l);
			}
		}

		return f;
	}

	double BLI_easing_elastic_ease_in(double time, double begin, double change, double duration, double amplitude, double period) {
		double s;
		double f = 1.0f;

		if(time == 0.0f) {
			return begin;
		}

		if((time /= duration) == 1.0f) {
			return begin + change;
		}
		time -= 1.0f;
		if(period == 0) {
			period = duration * 0.3f;
		}
		if(amplitude == 0 || amplitude < Math.abs(change)) {
			s = period / 4;
			f = elastic_blend(time, change, duration, amplitude, s, f);
			amplitude = change;
		} else {
			s = period / (2 * (double) Math.PI) * Math.asin(change / amplitude);
		}

		return (-f * (amplitude * Math.pow(2, 10 * time) * Math.sin((time * duration - s) * (2 * (double) Math.PI) / period))) + begin;
	}

	double BLI_easing_elastic_ease_out(double time, double begin, double change, double duration, double amplitude, double period) {
		double s;
		double f = 1.0f;

		if(time == 0.0f) {
			return begin;
		}
		if((time /= duration) == 1.0f) {
			return begin + change;
		}
		time = -time;
		if(period == 0) {
			period = duration * 0.3f;
		}
		if(amplitude == 0 || amplitude < Math.abs(change)) {
			s = period / 4;
			f = elastic_blend(time, change, duration, amplitude, s, f);
			amplitude = change;
		} else {
			s = period / (2 * (double) Math.PI) * Math.asin(change / amplitude);
		}

		return (f * (amplitude * Math.pow(2, 10 * time) * Math.sin((time * duration - s) * (2 * (double) Math.PI) / period))) + change + begin;
	}

	double BLI_easing_elastic_ease_in_out(double time, double begin, double change, double duration, double amplitude, double period) {
		double s;
		double f = 1.0f;

		if(time == 0.0f) {
			return begin;
		}
		if((time /= duration / 2) == 2.0f) {
			return begin + change;
		}
		time -= 1.0f;
		if(period == 0) {
			period = duration * (0.3f * 1.5f);
		}
		if(amplitude == 0 || amplitude < Math.abs(change)) {
			s = period / 4;
			f = elastic_blend(time, change, duration, amplitude, s, f);
			amplitude = change;
		} else {
			s = period / (2 * (double) Math.PI) * Math.asin(change / amplitude);
		}

		if(time < 0.0f) {
			f *= -0.5f;
			return (f * (amplitude * Math.pow(2, 10 * time) * Math.sin((time * duration - s) * (2 * (double) Math.PI) / period))) + begin;
		}

		time = -time;
		f *= 0.5f;
		return (f * (amplitude * Math.pow(2, 10 * time) * Math.sin((time * duration - s) * (2 * (double) Math.PI) / period))) + change + begin;
	}

	static final double pow_min = 0.0009765625f; /* = 2^(-10) */
	static final double pow_scale = 1.0f / (1.0f - 0.0009765625f);

	double BLI_easing_expo_ease_in(double time, double begin, double change, double duration) {
		if(time == 0.0) {
			return begin;
		}
		return change * (Math.pow(2, 10 * (time / duration - 1)) - pow_min) * pow_scale + begin;
	}

	double BLI_easing_expo_ease_out(double time, double begin, double change, double duration) {
		if(time == 0.0) {
			return begin;
		}
		return change * (1 - (Math.pow(2, -10 * time / duration) - pow_min) * pow_scale) + begin;
	}

	double BLI_easing_expo_ease_in_out(double time, double begin, double change, double duration) {
		double duration_half = duration / 2.0f;
		double change_half = change / 2.0f;
		if(time <= duration_half) {
			return BLI_easing_expo_ease_in(time, begin, change_half, duration_half);
		}
		return BLI_easing_expo_ease_out(time - duration_half, begin + change_half, change_half, duration_half);
	}

	double BLI_easing_linear_ease(double time, double begin, double change, double duration) {
		return change * time / duration + begin;
	}

	double BLI_easing_quad_ease_in(double time, double begin, double change, double duration) {
		time /= duration;
		return change * time * time + begin;
	}

	double BLI_easing_quad_ease_out(double time, double begin, double change, double duration) {
		time /= duration;
		return -change * time * (time - 2) + begin;
	}

	double BLI_easing_quad_ease_in_out(double time, double begin, double change, double duration) {
		if((time /= duration / 2) < 1.0f) {
			return change / 2 * time * time + begin;
		}
		time -= 1.0f;
		return -change / 2 * (time * (time - 2) - 1) + begin;
	}

	double BLI_easing_quart_ease_in(double time, double begin, double change, double duration) {
		time /= duration;
		return change * time * time * time * time + begin;
	}

	double BLI_easing_quart_ease_out(double time, double begin, double change, double duration) {
		time = time / duration - 1;
		return -change * (time * time * time * time - 1) + begin;
	}

	double BLI_easing_quart_ease_in_out(double time, double begin, double change, double duration) {
		if((time /= duration / 2) < 1.0f) {
			return change / 2 * time * time * time * time + begin;
		}
		time -= 2.0f;
		return -change / 2 * (time * time * time * time - 2) + begin;
	}

	double BLI_easing_quint_ease_in(double time, double begin, double change, double duration) {
		time /= duration;
		return change * time * time * time * time * time + begin;
	}

	double BLI_easing_quint_ease_out(double time, double begin, double change, double duration) {
		time = time / duration - 1;
		return change * (time * time * time * time * time + 1) + begin;
	}

	double BLI_easing_quint_ease_in_out(double time, double begin, double change, double duration) {
		if((time /= duration / 2) < 1.0f) {
			return change / 2 * time * time * time * time * time + begin;
		}
		time -= 2.0f;
		return change / 2 * (time * time * time * time * time + 2) + begin;
	}

	double BLI_easing_sine_ease_in(double time, double begin, double change, double duration) {
		return -change * Math.cos(time / duration * (double) Math.PI / 2) + change + begin;
	}

	double BLI_easing_sine_ease_out(double time, double begin, double change, double duration) {
		return change * Math.sin(time / duration * (double) Math.PI / 2) + begin;
	}

	double BLI_easing_sine_ease_in_out(double time, double begin, double change, double duration) {
		return -change / 2 * (Math.cos((double) Math.PI * time / duration) - 1) + begin;
	}

}
