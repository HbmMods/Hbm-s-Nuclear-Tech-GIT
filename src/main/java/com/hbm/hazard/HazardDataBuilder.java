package com.hbm.hazard;

import com.hbm.hazard.modifier.HazardModifier;

public class HazardDataBuilder {
	private final HazardData hazardData;

	public HazardDataBuilder(){
		this(new HazardData());
	}

	public HazardDataBuilder(HazardData hazardData){
		this.hazardData = hazardData;
	}

	public HazardDataBuilder addAsbestos(float level){
		hazardData.addEntry(HazardRegistry.ASBESTOS, level);
		return this;
	}

	public HazardDataBuilder addAsbestos(float level, HazardModifier[] modifiers){
		HazardEntry newEntry = new HazardEntry(HazardRegistry.ASBESTOS, level);
		for (HazardModifier mod : modifiers){
			newEntry.addMod(mod);
		}
		hazardData.addEntry(newEntry);
		return this;
	}

	public HazardDataBuilder addBlinding(float level){
		hazardData.addEntry(HazardRegistry.BLINDING, level);
		return this;
	}

	public HazardDataBuilder addCoal(float level){
		hazardData.addEntry(HazardRegistry.COAL, level);
		return this;
	}

	public HazardDataBuilder addDigamma(float level){
		hazardData.addEntry(HazardRegistry.DIGAMMA, level);
		return this;
	}

	public HazardDataBuilder addExplosive(float level){
		hazardData.addEntry(HazardRegistry.EXPLOSIVE, level);
		return this;
	}

	public HazardDataBuilder addHot(float level){
		hazardData.addEntry(HazardRegistry.HOT, level);
		return this;
	}

	public HazardDataBuilder addHydroactive(float level){
		hazardData.addEntry(HazardRegistry.HYDROACTIVE, level);
		return this;
	}

	public HazardDataBuilder addRadiation(float level){
		hazardData.addEntry(HazardRegistry.RADIATION, level);
		return this;
	}

	public HazardDataBuilder addRadiation(float level, HazardModifier[] modifiers){
		HazardEntry newEntry = new HazardEntry(HazardRegistry.RADIATION, level);
		for (HazardModifier mod : modifiers){
			newEntry.addMod(mod);
		}
		hazardData.addEntry(newEntry);
		return this;
	}

	public HazardData getHazardData(){
		return hazardData;
	}

}
