package com.hbm.interfaces;

import com.google.common.annotations.Beta;
import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IItemHazard {
	
	public HazardData getModule();
	
	public enum EnumToxicity
	{
		BERYLLIUM(1000, '2'),
		HEAVY_METAL(1000, '1'),
		CHEMICAL(250, 'e');
		private int cap;
		private char code;
		private EnumToxicity(int cap, char code)
		{
			this.cap = cap;
			this.code = code;
		}
		public int getCap()
		{
			return cap;
		}
		public char getCode()
		{
			return code;
		}
		public String getColor()
		{
			return "\u00a7" + code;
		}
	}
	
	public default IItemHazard addRadiation(float radiation) {
		this.getModule().addEntry(HazardRegistry.RADIATION, radiation);
		return this;
	}
	
	public default IItemHazard addDigamma(float digamma) {
		this.getModule().addEntry(HazardRegistry.DIGAMMA, digamma);
		return this;
	}
	
	public default IItemHazard addFire(int fire) {
		this.getModule().addEntry(HazardRegistry.HOT, fire);
		return this;
	}
	
	public default IItemHazard addAsbestos() {
		this.getModule().addEntry(HazardRegistry.ASBESTOS, 1);
		return this;
	}
	/**
	 * Experimental custom toxicity handler
	 * @param tox - The toxicity object
	 */
	@Untested
	@Beta
	public default IItemHazard addCustomToxicity(HazardEntry tox)
	{
		getModule().addEntry(tox);
		return this;
	}
	
	public default IItemHazard addBlinding() {
		this.getModule().addEntry(HazardRegistry.BLINDING);
		return this;
	}
	
	public default IItemHazard addHydroReactivity() {
		this.getModule().addEntry(HazardRegistry.HYDROACTIVE);
		return this;
	}
	
	public default IItemHazard addExplosive(float bang) {
		this.getModule().addEntry(HazardRegistry.EXPLOSIVE, bang);
		return this;
	}
	
	//the only ugly part of this entire system is the manual casting so that the rest of the daisychained setters work
	public default Item toItem()
	{
//		HazardSystem.register(this, getModule()); No good deed goes unpunished apparently
		return (Item)this;
	}
	public default Block toBlock()
	{
//		HazardSystem.register(this, getModule());
		return (Block)this;
	}
	public default IItemHazard addCoal(int coal) {
		this.getModule().addEntry(HazardRegistry.COAL, coal);
		return this;
	}
}