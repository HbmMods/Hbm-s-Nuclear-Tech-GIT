package com.hbm.util;

public class EnumUtil {

	public static <T extends Enum> T grabEnumSafely(Class<? extends Enum> theEnum, int index) {
		Enum[] values = theEnum.getEnumConstants();
		index = Math.abs(index % values.length);
		return (T)values[index];
	}
}
