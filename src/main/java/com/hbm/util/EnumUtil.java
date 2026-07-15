package com.hbm.util;

public class EnumUtil {

	public static <T extends Enum> T grabEnumSafely(Class<? extends Enum> theEnum, int index) {
		Enum[] values = theEnum.getEnumConstants();
		if(values == null || values.length == 0) throw new IllegalArgumentException("Class has no enum constants: " + theEnum);
		index = Math.floorMod(index, values.length);
		return (T)values[index];
	}

	public static <T extends Enum> T getEnumOrDefault(Class<? extends Enum> theEnum, int index, T fallback) {
		Enum[] values = theEnum.getEnumConstants();
		return values != null && index >= 0 && index < values.length ? (T) values[index] : fallback;
	}
}
