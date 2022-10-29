package com.hbm.inventory.material;

import com.hbm.inventory.OreDictManager.DictFrame;

/**
 * Encapsulates most materials that are currently listed as DictFrames, even vanilla ones.
 * @author hbm
 *
 */
public class NTMMaterial {

	public final int id;
	public String[] names;
	public MaterialShapes[] shapes = new MaterialShapes[0];
	public boolean omitItemGen = false;
	public SmeltingBehavior smeltable = SmeltingBehavior.NOT_SMELTABLE;
	public int solidColor = 0xFF4A00; //TODO
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
		return "hbmmat." + this.names[0].toLowerCase();
	}
	
	public NTMMaterial setConversion(NTMMaterial mat, int in, int out) {
		this.smeltsInto = mat;
		this.convIn = in;
		this.convOut = out;
		return this;
	}
	
	/** Shapes for autogen */
	public NTMMaterial setShapes(MaterialShapes... shapes) {
		this.shapes = shapes;
		return this;
	}
	
	/** Turn off autogen for this material, use this for vanilla stuff */
	public NTMMaterial omitAutoGen() {
		this.omitItemGen = true;
		return this;
	}
	
	/** Defines smelting behavior */
	public NTMMaterial smeltable(SmeltingBehavior behavior) {
		this.smeltable = behavior;
		return this;
	}
	
	public NTMMaterial setMoltenColor(int color) {
		this.moltenColor = color;
		return this;
	}
	
	public static enum SmeltingBehavior {
		NOT_SMELTABLE,	//anything that can't be smelted or otherwise doesn't belong in a smelter, like diamond. may also include things that are smeltable but turn into a different type
		VAPORIZES,		//can't be smelted because the material would skadoodle
		BREAKS,			//can't be smelted because the material doesn't survive the temperatures
		SMELTABLE,		//mostly metal
		ADDITIVE		//stuff like coal which isn't smeltable but can be put in a crucible anyway
	}
}
