package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import com.hbm.util.I18nUtil;
import net.minecraft.util.EnumChatFormatting;

public class FT_PWRModerator extends FluidTrait {

	private double multiplier;
	
	public FT_PWRModerator(double mulitplier) {
		this.multiplier = mulitplier;
	}
	
	public double getMultiplier() {
		return multiplier;
	}
	
	@Override
	public void addInfo(List<String> info) {
		info.add(EnumChatFormatting.BLUE + I18nUtil.resolveKeyArray("hbmfluid.Trait.PWRModerator","","")[0]);
	}

	@Override
	public void addInfoHidden(List<String> info) {
		int mult = (int) (multiplier * 100 - 100);
		info.add(EnumChatFormatting.BLUE + I18nUtil.resolveKeyArray("hbmfluid.Trait.PWRModerator",(mult >= 0 ? "+" : ""),mult)[1]);
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("multiplier").value(multiplier);
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.multiplier = obj.get("multiplier").getAsDouble();
	}
}
