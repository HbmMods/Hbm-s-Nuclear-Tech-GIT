package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import com.hbm.util.i18n.I18nUtil;

import net.minecraft.util.EnumChatFormatting;

public class FT_Corrosive extends FluidTrait {
	
	/* 0-100 */
	private int rating;

	public FT_Corrosive() { }

	public FT_Corrosive(int rating) {
		this.rating = rating;
	}
	
	public int getRating() {
		return rating;
	}
	
	public boolean isHighlyCorrosive() {
		return rating > 50;
	}
	
	@Override
	public void addInfo(List<String> info) {
		
		if(isHighlyCorrosive())
			info.add(EnumChatFormatting.GOLD + "[" + I18nUtil.resolveKey("hbmfluid.trait.corrosiveStrong") + "]");
		else
			info.add(EnumChatFormatting.YELLOW + "[" + I18nUtil.resolveKey("hbmfluid.trait.corrosive") + "]");
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("rating").value(rating);
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.rating = obj.get("rating").getAsInt();
	}
}
