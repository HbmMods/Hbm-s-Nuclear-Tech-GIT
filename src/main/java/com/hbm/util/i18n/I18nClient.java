package com.hbm.util.i18n;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class I18nClient implements ITranslate {

	@Override
	@SideOnly(Side.CLIENT)
	public String resolveKey(String s, Object... args) {
		return I18n.format(s, args);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String[] resolveKeyArray(String s, Object... args) {
		return resolveKey(s, args).split("\\$");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<String> autoBreakWithParagraphs(Object fontRenderer, String text, int width) {

		String[] paragraphs = text.split("\\$");
		List<String> lines = new ArrayList();

		for(String paragraph : paragraphs) {
			lines.addAll(autoBreak(fontRenderer, paragraph, width));
		}

		return lines;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<String> autoBreak(Object o, String text, int width) {

		FontRenderer fontRenderer = (FontRenderer) o;
		List<String> lines = new ArrayList();
		//split the text by all spaces
		String[] words = text.split(" ");

		//add the first word to the first line, no matter what
		lines.add(words[0]);
		//starting indent is the width of the first word
		int indent = fontRenderer.getStringWidth(words[0]);

		for(int w = 1; w < words.length; w++) {

			//increment the indent by the width of the next word + leading space
			indent += fontRenderer.getStringWidth(" " + words[w]);

			//if the indent is within bounds
			if(indent <= width) {
				//add the next word to the last line (i.e. the one in question)
				String last = lines.get(lines.size() - 1);
				lines.set(lines.size() - 1, last += (" " + words[w]));
			} else {
				//otherwise, start a new line and reset the indent
				lines.add(words[w]);
				indent = fontRenderer.getStringWidth(words[w]);
			}
		}

		return lines;
	}
}
