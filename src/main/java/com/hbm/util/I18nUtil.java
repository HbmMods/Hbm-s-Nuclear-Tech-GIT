package com.hbm.util;

import net.minecraft.client.resources.I18n;

public class I18nUtil {

	public static String resolveKey(String s, Object... args) {
		return I18n.format(s, args);
	}

	public static String[] resolveKeyArray(String s, Object... args) {
		return resolveKey(s, args).split("\\$");
	}
}
