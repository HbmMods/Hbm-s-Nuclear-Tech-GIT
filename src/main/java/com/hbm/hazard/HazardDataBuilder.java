package com.hbm.hazard;

public class HazardDataBuilder {
	private final HazardData hazardData;

	public HazardDataBuilder(){
		this(new HazardData());
	}

	public HazardDataBuilder(HazardData hazardData){
		this.hazardData = hazardData;
	}

	public HazardDataBuilder addCoalHazard(float level){
		hazardData.addEntry(HazardRegistry.COAL, level);
		return this;
	}

	public HazardDataBuilder addExplosiveHazard(float level){
		hazardData.addEntry(HazardRegistry.EXPLOSIVE, level);
		return this;
	}

	public HazardDataBuilder addHydroactiveHazard(float level){
		hazardData.addEntry(HazardRegistry.HYDROACTIVE, level);
		return this;
	}

	public HazardData getHazardData(){
		return hazardData;
	}

}
