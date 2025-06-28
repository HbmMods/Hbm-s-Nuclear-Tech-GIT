package com.hbm.util.i18n;

import java.util.List;

public interface ITranslate {

	public String resolveKey(String s, Object... args);

	/**
	 * Wrapper for I18n but cuts up the result using NTM's line break character ($)
	 * @param s
	 * @param args
	 * @return
	 */
	public String[] resolveKeyArray(String s, Object... args);

	/**
	 * The same as autoBreak, but it also respects NTM's break character ($) for manual line breaking in addition to the automatic ones
	 * @param fontRenderer
	 * @param text
	 * @param width
	 * @return
	 */
	public List<String> autoBreakWithParagraphs(Object fontRenderer, String text, int width);

	/**
	 * Turns one string into a list of strings, cutting sentences up to fit within the defined width if they were rendered in a GUI
	 * @param fontRenderer
	 * @param text
	 * @param width
	 * @return
	 */
	public List<String> autoBreak(Object fontRenderer, String text, int width);
}
