package com.hbm.inventory.fluid.trait;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class FT_Heatable extends FluidTrait {
	
	protected List<HeatingStep> steps = new ArrayList();
	protected HashMap<HeatingType, Double> efficiency = new HashMap();
	
	/** Add in ascending order, lowest heat required goes first! */
	public FT_Heatable addStep(int heat, int req, FluidType type, int prod) {
		steps.add(new HeatingStep(req, heat, type, prod));
		return this;
	}
	
	/** sets efficiency for different types of heating, main difference is with water */
	public FT_Heatable setEff(HeatingType type, double eff) {
		efficiency.put(type, eff);
		return this;
	}
	
	public double getEfficiency(HeatingType type) {
		Double eff = this.efficiency.get(type);
		return eff != null ? eff : 0.0D;
	}
	
	public HeatingStep getFirstStep() {
		return this.steps.get(0);
	}

	@Override
	public void addInfoHidden(List<String> info) {
		info.add(EnumChatFormatting.RED + "Thermal capacity: " + this.getFirstStep().heatReq + " TU per " + this.getFirstStep().amountReq + "mB");
		for(HeatingType type : HeatingType.values()) {
			
			double eff = getEfficiency(type);
			
			if(eff > 0) {
				info.add(EnumChatFormatting.YELLOW + "[" + type.name + "] " + EnumChatFormatting.AQUA + "Efficiency: " + ((int) (eff * 100D)) + "%");
			}
		}
	}
	
	public static class HeatingStep {
		public final int amountReq;
		public final int heatReq;
		public final FluidType typeProduced;
		public final int amountProduced;
		
		public HeatingStep(int req, int heat, FluidType type, int prod) {
			this.amountReq = req;
			this.heatReq = heat;
			this.typeProduced = type;
			this.amountProduced = prod;
		}
	}
	
	public static enum HeatingType {
		BOILER("Boilable"),
		HEATEXCHANGER("Heatable"),
		PWR("PWR Coolant"),
		ICF("ICF Coolant"),
		PA("Particle Accelerator Coolant");
		
		public String name;
		
		private HeatingType(String name) {
			this.name = name;
		}
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		
		writer.name("steps").beginArray();
		
		for(HeatingStep step : steps) {
			writer.beginObject();
			writer.name("typeProduced").value(step.typeProduced.getName());
			writer.name("amountReq").value(step.amountReq);
			writer.name("amountProd").value(step.amountProduced);
			writer.name("heatReq").value(step.heatReq);
			writer.endObject();
		}
		
		writer.endArray();
		
		for(Entry<HeatingType, Double> entry : this.efficiency.entrySet()) {
			writer.name(entry.getKey().name()).value(entry.getValue());
		}
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		
		JsonArray steps = obj.get("steps").getAsJsonArray();
		
		for(int i = 0; i < steps.size(); i++) {
			JsonObject step = steps.get(i).getAsJsonObject();
			this.steps.add(new HeatingStep(
					step.get("amountReq").getAsInt(),
					step.get("heatReq").getAsInt(),
					Fluids.fromName(step.get("typeProduced").getAsString()),
					step.get("amountProd").getAsInt()
			));
		}
		
		for(HeatingType type : HeatingType.values()) {
			if(obj.has(type.name())) efficiency.put(type, obj.get(type.name()).getAsDouble());
		}
	}
}
