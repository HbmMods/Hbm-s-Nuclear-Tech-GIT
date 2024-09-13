package com.hbm.util;

import java.util.function.Function;

/**
 * Represents a value that is either of generic type L or R
 * @author martinthedragon
 */
@SuppressWarnings("unchecked")
public final class Either<L, R> {
	
	public static <L, R> Either<L, R> left(L value) {
		return new Either<>(value, true);
	}

	public static <L, R> Either<L, R> right(R value) {
		return new Either<>(value, false);
	}

	private final Object value;
	private final boolean isLeft;

	private Either(Object value, boolean isLeft) {
		this.value = value;
		this.isLeft = isLeft;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public boolean isRight() {
		return !isLeft;
	}

	public L left() {
		if(isLeft)
			return (L) value;
		else
			throw new IllegalStateException("Tried accessing value as the L type, but was R type");
	}

	public R right() {
		if(!isLeft)
			return (R) value;
		else
			throw new IllegalStateException("Tried accessing value as the R type, but was L type");
	}

	public L leftOrNull() {
		return isLeft ? (L) value : null;
	}

	public R rightOrNull() {
		return !isLeft ? (R) value : null;
	}

	public <V> V cast() {
		return (V) value;
	}

	public <T> T run(Function<L, T> leftFunc, Function<R, T> rightFunc) {
		return isLeft ? leftFunc.apply((L) value) : rightFunc.apply((R) value);
	}

	public <T> T runLeftOrNull(Function<L, T> func) {
		return isLeft ? func.apply((L) value) : null;
	}

	public <T> T runRightOrNull(Function<R, T> func) {
		return !isLeft ? func.apply((R) value) : null;
	}

	public <V, T> T runCasting(Function<V, T> func) {
		return func.apply((V) value);
	}
}
