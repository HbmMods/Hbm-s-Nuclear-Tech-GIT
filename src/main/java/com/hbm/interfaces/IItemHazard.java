package com.hbm.interfaces;

import com.google.common.annotations.Beta;
import com.hbm.modules.ItemHazardModule;
import com.hbm.modules.ItemHazardModule.CustomToxicity;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;

public interface IItemHazard {
	
	public ItemHazardModule getModule();
	
	public enum EnumToxicity
	{
		BERYLLIUM,
		HEAVY_METAL,
		CHEMICAL;
	}
	
	public static final CustomToxicity BERYLLIUM = new CustomToxicity("beryllium", 1000, 1000, EnumChatFormatting.DARK_GREEN);
	public static final CustomToxicity HEAVY_METAL = new CustomToxicity("heavyMetal", 1000, 1000, EnumChatFormatting.DARK_BLUE);
	public static final CustomToxicity CHEMICAL = new CustomToxicity("chemical", 5000, 250, EnumChatFormatting.YELLOW);
	
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
	public default IItemHazard addCustomToxicity(CustomToxicity tox)
	{
		return addCustomToxicity(tox, 1);
	}
	
	public default IItemHazard addCustomToxicity(CustomToxicity tox, float mod)
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
	}
	
	public default Block toBlock() {
		return (Block)this;
	}
}
