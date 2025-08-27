package com.hbm.util.i18n;

import java.util.ArrayList;
import java.util.List;

public class I18nServer implements ITranslate {
	
	public static String SARCASTIC_MESSAGE = "I18N CALL SERVERSIDE - GREAT JOB";

	@Override
	public String resolveKey(String s, Object... args) {
		return SARCASTIC_MESSAGE;
	}

	@Override
	public String[] resolveKeyArray(String s, Object... args) {
		return new String[] { SARCASTIC_MESSAGE };
	}

	@Override
	public List<String> autoBreakWithParagraphs(Object fontRenderer, String text, int width) {
		List<String> list = new ArrayList();
		list.add(SARCASTIC_MESSAGE);
		return list;
	}

	@Override
	public List<String> autoBreak(Object fontRenderer, String text, int width) {
		List<String> list = new ArrayList();
		list.add(SARCASTIC_MESSAGE);
		return list;
	}
}
