package com.hbm.util;

import net.minecraft.client.resources.I18n;

public class I18nUtil {

	public static String resolveKey(String s, Object... args) {
		return I18n.format(s, args);
	}
	/** Because the [object args] parameter was <i>that</i> annoying to remove when coding **/
	public static String resolveKey(String s)
	{
		return I18n.format(s);
	}
	
	public static String[] resolveKeyArray(String s, Object... args) {
		return resolveKey(s, args).split("\\$");
	}
	
	public static String[] resolveKeyArray(String s)
	{
		return resolveKey(s).split("\\$");
	}
}
