package com.hbm.interfaces;

import com.google.common.annotations.Beta;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IItemHazard {
	
	public ItemHazardModule getModule();
	
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
		this.getModule().addRadiation(radiation);
		return this;
	}
	
	public default IItemHazard addDigamma(float digamma) {
		this.getModule().addDigamma(digamma);
		return this;
	}
	
	public default IItemHazard addFire(int fire) {
		this.getModule().addFire(fire);
		return this;
	}
	
	public default IItemHazard addAsbestos() {
		this.getModule().addAsbestos();
		return this;
	}
	/**
	 * Experimental custom toxicity handler
	 * @param tox - The toxicity object
	 */
	@Untested
	@Beta
	public default IItemHazard addCustomToxicity(EnumToxicity tox)
	{
		return addCustomToxicity(tox, 1);
	}
	
	public default IItemHazard addCustomToxicity(EnumToxicity tox, float mod)
	{
		getModule().addCustomToxicity(tox, mod);
		return this;
	}
	
	public default IItemHazard addBlinding() {
		this.getModule().addBlinding();
		return this;
	}
	
	public default IItemHazard addHydroReactivity() {
		this.getModule().addHydroReactivity();
		return this;
	}
	
	public default IItemHazard addExplosive(float bang) {
		this.getModule().addExplosive(bang);
		return this;
	}
	
	//the only ugly part of this entire system is the manual casting so that the rest of the daisychained setters work
	public default Item toItem() {
		return (Item)this;
	
	public default Block toBlock() {
		return (Block)this;
	}
	public default IItemHazard addCoal(int coal) {
		this.getModule().addCoal(coal);
		return this;
	}
}