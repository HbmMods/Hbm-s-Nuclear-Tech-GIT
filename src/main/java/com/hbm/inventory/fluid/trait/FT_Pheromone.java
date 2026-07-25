package com.hbm.inventory.fluid.trait;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.EnumChatFormatting;

import com.hbm.util.i18n.I18nUtil;

import java.io.IOException;
import java.util.List;

public class FT_Pheromone extends  FluidTrait{

	public int type;
	public FT_Pheromone() {}

	public FT_Pheromone(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	@Override
	public void addInfo(List<String> info) {

		if(type == 1) {
			info.add(EnumChatFormatting.AQUA + "[" + I18nUtil.resolveKey("hbmfluid.trait.glyphidPheromones") + "]");
		} else {
			info.add(EnumChatFormatting.BLUE + "[" + I18nUtil.resolveKey("hbmfluid.trait.modifiedPheromones") + "]");
		}
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("type").value(type);
	}

	@Override
	public void deserializeJSON(JsonObject obj) {
		this.type = obj.get("type").getAsInt();
	}
}
