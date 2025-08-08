package com.hbm.qmaw;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public class QuickManualAndWiki {

	public String name;
	public ItemStack icon;

	public HashMap<String, String> title = new HashMap();
	public HashMap<String, String> contents = new HashMap();
	
	public QuickManualAndWiki(String name) {
		this.name = name;
	}
	
	public QuickManualAndWiki setIcon(ItemStack stack) {
		this.icon = stack;
		return this;
	}
	
	public QuickManualAndWiki addTitle(String lang, String title) {
		this.title.put(lang, title);
		return this;
	}
	
	public QuickManualAndWiki addLang(String lang, String contents) {
		this.contents.put(lang, contents);
		return this;
	}
}
