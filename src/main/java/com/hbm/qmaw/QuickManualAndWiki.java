package com.hbm.qmaw;

import java.util.HashMap;

import net.minecraft.client.resources.LanguageManager;
import net.minecraft.item.ItemStack;

public class QuickManualAndWiki {

	public static final String EN_US = "en_US";
	
	public String name;
	public ItemStack icon;

	/** Removes this manual from the calculator search feature */
	public boolean noIndex = false;

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
	
	public QuickManualAndWiki noIndex() {
		this.noIndex = true;
		return this;
	}
	
	public String getTitle(LanguageManager lang) {
		String localizedTitle = title.get(lang.getCurrentLanguage().getLanguageCode());
		if(localizedTitle == null) localizedTitle = title.get(EN_US);
		if(localizedTitle == null) localizedTitle = "Missing Localization!";
		
		return localizedTitle;
	}
}
