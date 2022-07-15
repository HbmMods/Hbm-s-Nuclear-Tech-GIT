package com.hbm.util;

import com.hbm.main.MainRegistry;

public class LoggingUtil {
	
//	public static final String ANSI_RESET =		"\u001B[0m";
//	public static final String ANSI_BLACK =		"\u001B[30m";
//	public static final String ANSI_RED =		"\u001B[31m";
//	public static final String ANSI_GREEN =		"\u001B[32m";
//	public static final String ANSI_YELLOW =	"\u001B[33m";
//	public static final String ANSI_BLUE =		"\u001B[34m";
//	public static final String ANSI_PURPLE =	"\u001B[35m";
//	public static final String ANSI_CYAN =		"\u001B[36m";
//	public static final String ANSI_WHITE =		"\u001B[37m";
	/*
	 * Only works with ANSI-compatible outputs. http://www.mihai-nita.net/eclipse works really well for dev envs.
	 */
	public enum EnumANSIColoring
	{
		RESET("\u001B[0m"),
		BLACK("\u001B[30m"),
		RED("\u001B[31m"),
		GREEN("\u001B[32m"),
		YELLOW("\u001B[33m"),
		BLUE("\u001B[34m"),
		PURPLE("\u001B[35m"),
		CYAN("\u001B[36m"),
		WHITE("\u001B[37m");
		public final String code;
		private EnumANSIColoring(String code)
		{
			this.code = code;
		}
		@Override
		public String toString()
		{
			return code;
		}
	}
	
	public static void errorWithHighlight(String error) {
		MainRegistry.logger.error(EnumANSIColoring.RED + error + EnumANSIColoring.RESET);
	}
}
