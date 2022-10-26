package com.hbm.inventory.fluid.trait;

import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.util.EnumChatFormatting;

public class FT_Coolable extends FluidTrait {
	
	protected HashMap<CoolingType, Double> efficiency = new HashMap();
	
	public final FluidType coolsTo;
	public int amountReq;
	public int amountProduced;
	public final int heatEnergy;
	
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
		for(CoolingType type : CoolingType.values()) {
			
			double eff = getEfficiency(type);
			
			if(eff > 0) {
				info.add(EnumChatFormatting.AQUA + "[" + type.name + "]");
				info.add(EnumChatFormatting.AQUA + "Efficiency: " + ((int) (eff * 100D)) + "%");
			}
		}
	}
	
	public static enum CoolingType {
		TURBINE("Turbine Steam"),
		HEATEXCHANGER("Coolable");
		
		public String name;
		
		private CoolingType(String name) {
			this.name = name;
		}
	}
}
