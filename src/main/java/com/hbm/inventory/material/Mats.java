package com.hbm.inventory.material;

import static com.hbm.inventory.material.MaterialShapes.*;

import java.util.HashMap;

import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;

/* with every new rewrite, optimization and improvement, the code becomes more gregian */
/**
 * Defines materials that wrap around DictFrames to more accurately describe that material.
 * Direct uses are the crucible and possibly item auto-gen, depending on what traits are set.
 * @author hbm
 */
public class Mats {
	
	public static HashMap<String, MaterialShapes> prefixByName = new HashMap();
	public static HashMap<String, NTMMaterial> matByName = new HashMap();
	
	public static NTMMaterial
			IRON		= makeSmeltable(OreDictManager.IRON,		0).omitAutoGen(),
			GOLD		= makeSmeltable(OreDictManager.GOLD,		0).omitAutoGen(),
			STEEL		= makeSmeltable(OreDictManager.STEEL, 		0).setShapes(DUSTTINY, INGOT, DUST, PLATE, BLOCK),
			TUNGSTEN	= makeSmeltable(OreDictManager.W,			0).setShapes(WIRE, INGOT, DUST, BLOCK),
			COPPER		= makeSmeltable(OreDictManager.CU,			0).setShapes(WIRE, INGOT, DUST, PLATE, BLOCK),
			ALUMINIUM	= makeSmeltable(OreDictManager.AL,			0).setShapes(WIRE, INGOT, DUST, PLATE, BLOCK),
			MINGRADE	= makeSmeltable(OreDictManager.MINGRADE,	0).setShapes(WIRE, INGOT, DUST, BLOCK),
			ALLOY		= makeSmeltable(OreDictManager.ALLOY,		0).setShapes(WIRE, INGOT, DUST, PLATE, BLOCK);

	public static NTMMaterial make(DictFrame dict) {
		return new NTMMaterial(dict);
	}
	
	public static NTMMaterial makeSmeltable(DictFrame dict, int color) {
		return new NTMMaterial(dict).smeltable(SmeltingBehavior.SMELTABLE).setMoltenColor(color);
	}
}
