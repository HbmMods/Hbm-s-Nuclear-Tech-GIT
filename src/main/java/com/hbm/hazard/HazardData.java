package com.hbm.hazard;

import java.util.ArrayList;
import java.util.List;

import com.hbm.hazard.type.HazardTypeBase;
import com.hbm.interfaces.Untested;

public class HazardData {
	
	/*
	 * Purges all previously loaded data when read, useful for when specific items should fully override ore dict data.
	 */
	boolean doesOverride = false;
	/*
	 * MUTEX, even more precise to make only specific entries mutually exclusive, for example oredict aliases such as plutonium238 and pu238.
	 * Does the opposite of overrides, if a previous entry collides with this one, this one will yield.
	 * 
	 * RESERVED BITS (please keep this up to date)
	 * -1: oredict ("ingotX")
	 */
	int mutexBits = 0b0000_0000_0000_0000_0000_0000_0000_0000;
	
	List<HazardEntry> entries = new ArrayList<HazardEntry>();
	
	byte[] radTypes = new byte[4];
	
	public HazardData addEntry(HazardTypeBase hazard) {
		return this.addEntry(hazard, 1F, false);
	}
	
	public HazardData addEntry(HazardTypeBase hazard, float level) {
		return this.addEntry(hazard, level, false);
	}
	
	public HazardData addEntry(HazardTypeBase hazard, float level, boolean override) {
		this.entries.add(new HazardEntry(hazard, level));
		this.doesOverride = override;
		return this;
	}
	
	public HazardData addEntry(HazardEntry entry) {
		this.entries.add(entry);
		return this;
	}
	/**
	 * Only applies to radiation items
	 * @param types The proportions of the types of radiation.<br>In order:<br>
	 * <ol>
	 * <li>alpha<br>
	 * <li>beta<br>
	 * <li>gamma<br>
	 * <li>neutron
	 * </ol>
	 * @return Itself
	 */
	@Untested
	public HazardData addRadTypes(byte[] types)
	{
		if (types.length != 4)
			throw new IllegalArgumentException("Rad type length is not 4! Length: " + types.length);
		radTypes = types;
		return this;
	}
	
	public HazardData addRadTypes(float types)
	{
		radTypes = HazardSystem.decompactRadTypes(types);
		return this;
	}
	
	public HazardData alpha(int a) { radTypes[0] = (byte) a; return this; }
	public HazardData alpha() { return alpha(10); }
	public HazardData beta(int b) { radTypes[1] = (byte) b; return this; }
	public HazardData beta() { return beta(10); }
	public HazardData gamma(int g) { radTypes[2] = (byte) g; return this; }
	public HazardData gamma() { return gamma(10); }
	public HazardData neutron(int n) { radTypes[3] = (byte) n; return this; }
	public HazardData neutron() { return neutron(10); }
	
	public HazardData setMutex(int mutex) {
		this.mutexBits = mutex;
		return this;
	}
	
	public int getMutex() {
		return mutexBits;
	}
}
