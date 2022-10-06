package com.hbm.inventory.fluid.trait;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		for(HeatingType type : HeatingType.values()) {
			
			double eff = getEfficiency(type);
			
			if(eff > 0) {
				info.add(EnumChatFormatting.AQUA + "[" + type.name + "]");
				info.add(EnumChatFormatting.AQUA + "Efficiency: " + ((int) (eff * 100D)) + "%");
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
		HEATEXCHANGER("Heatable");
		
		public String name;
		
		private HeatingType(String name) {
			this.name = name;
		}
	}
}
