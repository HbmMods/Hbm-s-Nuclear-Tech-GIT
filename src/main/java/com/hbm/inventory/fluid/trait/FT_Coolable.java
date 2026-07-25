package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.util.EnumChatFormatting;

public class FT_Coolable extends FluidTrait {
	
	protected HashMap<CoolingType, Double> efficiency = new HashMap();
	
	public FluidType coolsTo;
	public int amountReq;
	public int amountProduced;
	public int heatEnergy;
	
	public FT_Coolable() { }
	
	public FT_Coolable(FluidType type, int req, int prod, int heat) {
		this.coolsTo = type;
		this.amountReq = req;
		this.amountProduced = prod;
		this.heatEnergy = heat;
	}
	
	public FT_Coolable setEff(CoolingType type, double eff) {
		efficiency.put(type, eff);
		return this;
	}
	
	public double getEfficiency(CoolingType type) {
		Double eff = this.efficiency.get(type);
		return eff != null ? eff : 0.0D;
	}
	
	@Override
	public void addInfoHidden(List<String> info) {
		info.add(EnumChatFormatting.RED + I18nUtil.resolveKey("hbmfluid.trait.thermalCapacity") + ": " + heatEnergy + " " + I18nUtil.resolveKey("hbmfluid.trait.perTU") + " " + amountReq + "mB");
		for(CoolingType type : CoolingType.values()) {
			
			double eff = getEfficiency(type);
			
			if(eff > 0) {
				info.add(EnumChatFormatting.YELLOW + "[" + type.getLocalizedName() + "] " + EnumChatFormatting.AQUA + I18nUtil.resolveKey("hbmfluid.trait.efficiency") + ": " + ((int) (eff * 100D)) + "%");
			}
		}
	}
	
	public static enum CoolingType {
		TURBINE("steam"),
		HEATEXCHANGER("coolable");
		
		private String name;
		
		private CoolingType(String name) {
			this.name = name;
		}
		
		public String getLocalizedName() {
			return I18nUtil.resolveKey("hbmfluid.trait." + this.name);
		}
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("coolsTo").value(this.coolsTo.getName());
		writer.name("amountReq").value(this.amountReq);
		writer.name("amountProd").value(this.amountProduced);
		writer.name("heatEnergy").value(this.heatEnergy);
		
		for(Entry<CoolingType, Double> entry : this.efficiency.entrySet()) {
			writer.name(entry.getKey().name()).value(entry.getValue());
		}
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.coolsTo = Fluids.fromName(obj.get("coolsTo").getAsString());
		this.amountReq = obj.get("amountReq").getAsInt();
		this.amountProduced = obj.get("amountProd").getAsInt();
		this.heatEnergy = obj.get("heatEnergy").getAsInt();
		
		for(CoolingType type : CoolingType.values()) {
			if(obj.has(type.name())) efficiency.put(type, obj.get(type.name()).getAsDouble());
		}
	}
}
