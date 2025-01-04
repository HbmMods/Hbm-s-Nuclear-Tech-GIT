package com.hbm.util;

import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ChatBuilder {

	private ChatComponentText text;
	private ChatComponentStyle last;

	private ChatBuilder(String text) {
		this.text = new ChatComponentText(text);
		this.last = this.text;
	}

	public static ChatBuilder start(String text) {
		ChatBuilder builder = new ChatBuilder(text);
		return builder;
	}

	public static ChatBuilder startTranslation(String text, Object... o) {
		ChatBuilder builder = new ChatBuilder("").nextTranslation(text, o);
		return builder;
	}

	public ChatBuilder next(String text) {
		ChatComponentText append = new ChatComponentText(text);
		this.last.appendSibling(append);
		this.last = append;
		return this;
	}

	public ChatBuilder nextTranslation(String text, Object... o) {
		ChatComponentTranslation append = new ChatComponentTranslation(text, o);
		this.last.appendSibling(append);
		this.last = append;
		return this;
	}

	public ChatBuilder color(EnumChatFormatting format) {
		ChatStyle style = this.last.getChatStyle();
		style.setColor(format);
		return this;
	}

	/** Will recursively go over all IChatComponents added to the root and then set the style */
	public ChatBuilder colorAll(EnumChatFormatting format) {

		List list = new ArrayList();
		list.add(text);

		ListIterator it = list.listIterator();

		while(it.hasNext()) {
			Object o = it.next();
			IChatComponent component = (IChatComponent) o;
			component.getChatStyle().setColor(format);
			for(Object s : component.getSiblings()) it.add(s);
		}

		return this;
	}

	public ChatComponentText flush() {
		return this.text;
	}
}
