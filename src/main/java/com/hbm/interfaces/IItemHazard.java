package com.hbm.interfaces;

import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IItemHazard {
	
	public ItemHazardModule getModule();
	
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
	 * @param name - Name of the toxicity, for both tooltip and damage localization
	 * @param cap - When the toxicity reaches max and deals damage, lower for sooner, higher for later
	 * @param damage - How much damage does it deal when it reaches max?
	 */
	@Untested
	public default IItemHazard addCustomToxicity(String name, float max, float damage)
	{
		getModule().addCustomToxicity(name, max, damage);
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
	}
	
	public default Block toBlock() {
		return (Block)this;
	}
}
