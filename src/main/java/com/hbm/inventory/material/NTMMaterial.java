package com.hbm.inventory.material;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.hbm.inventory.OreDictManager.DictFrame;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Encapsulates most materials that are currently listed as DictFrames, even vanilla ones.
 * @author hbm
 *
 */
public class NTMMaterial {

	public final int id;
	public String[] names;
	public Set<MaterialShapes> autogen = new HashSet();
	public Set<MatTraits> traits = new HashSet();
	public SmeltingBehavior smeltable = SmeltingBehavior.NOT_SMELTABLE;
	public int solidColorLight = 0xFF4A00;
	public int solidColorDark = 0x802000;
	public int moltenColor = 0xFF4A00;
	
	public NTMMaterial smeltsInto;
	public int convIn;
	public int convOut;
	
	public NTMMaterial(int id, DictFrame dict) {
		
		this.names = dict.mats;
		this.id = id;
		
		this.smeltsInto = this;
		this.convIn = 1;
		this.convOut = 1;
		
		for(String name : dict.mats) {
			Mats.matByName.put(name, this);
		}
		
		Mats.orderedList.add(this);
		Mats.matById.put(id, this);
	}
	
	public String getUnlocalizedName() {
		return "hbmmat." + this.names[0].toLowerCase(Locale.US);
	}
	
	public NTMMaterial setConversion(NTMMaterial mat, int in, int out) {
		this.smeltsInto = mat;
		this.convIn = in;
		this.convOut = out;
		return this;
	}
	
	/** Shapes for autogen */
	public NTMMaterial setAutogen(MaterialShapes... shapes) {
		for(MaterialShapes shape : shapes) this.autogen.add(shape);
		return this;
	}
	
	/** Traits for recipe detection */
	public NTMMaterial setTraits(MatTraits... traits) {
		for(MatTraits trait : traits) this.traits.add(trait);
		return this;
	}

	public NTMMaterial m() { this.traits.add(MatTraits.METAL); return this; }
	public NTMMaterial n() { this.traits.add(MatTraits.NONMETAL); return this; }
	
	/** Defines smelting behavior */
	public NTMMaterial smeltable(SmeltingBehavior behavior) {
		this.smeltable = behavior;
		return this;
	}
	
	public NTMMaterial setSolidColor(int colorLight, int colorDark) {
		this.solidColorLight = colorLight;
		this.solidColorDark = colorDark;
		return this;
	}
	
	public NTMMaterial setMoltenColor(int color) {
		this.moltenColor = color;
		return this;
	}
	
	public ItemStack make(Item item, int amount) {
		return new ItemStack(item, amount, this.id);
	}
	
	public ItemStack make(Item item) {
		return make(item, 1);
	}
	
	public static enum SmeltingBehavior {
		NOT_SMELTABLE,	//anything that can't be smelted or otherwise doesn't belong in a smelter, like diamond. may also include things that are smeltable but turn into a different type
		VAPORIZES,		//can't be smelted because the material would skadoodle
		BREAKS,			//can't be smelted because the material doesn't survive the temperatures
		SMELTABLE,		//mostly metal
		ADDITIVE		//stuff like coal which isn't smeltable but can be put in a crucible anyway
	}
	
	public static enum MatTraits {
		METAL,		//metal(like), smeltable by arc furnaces
		NONMETAL;	//non-metal(like), for gems, non-alloy compounds and similar
	}
}
