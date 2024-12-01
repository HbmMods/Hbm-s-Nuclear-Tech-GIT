package com.hbm.inventory.fluid.trait;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.EnumChatFormatting;


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
			info.add(EnumChatFormatting.AQUA + "[Glyphid Pheromones]");
		} else {
			info.add(EnumChatFormatting.BLUE + "[Modified Pheromones]");
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
