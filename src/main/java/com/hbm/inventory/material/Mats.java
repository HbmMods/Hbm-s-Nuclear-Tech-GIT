package com.hbm.inventory.material;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.material.MaterialShapes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;

/* with every new rewrite, optimization and improvement, the code becomes more gregian */
/**
 * Defines materials that wrap around DictFrames to more accurately describe that material.
 * Direct uses are the crucible and possibly item auto-gen, depending on what traits are set.
 * @author hbm
 */
public class Mats {
	
	public static HashMap<String, MaterialShapes> prefixByName = new HashMap();
	public static HashMap<String, NTMMaterial> matByName = new HashMap();
	public static HashMap<ComparableStack, List<MaterialStack>> materialEntries = new HashMap();
	public static HashMap<String, List<MaterialStack>> materialOreEntries = new HashMap();
	
	public static final NTMMaterial
			MAT_STONE		= makeSmeltable(new DictFrame("Stone"), 0),
			MAT_COAL		= make(COAL).smeltable(SmeltingBehavior.ADDITIVE),
			MAT_IRON		= makeSmeltable(IRON,		0).omitAutoGen(),
			MAT_GOLD		= makeSmeltable(GOLD,		0).omitAutoGen(),
			MAT_STEEL		= makeSmeltable(STEEL, 		0).setShapes(DUSTTINY, INGOT, DUST, PLATE, BLOCK),
			MAT_TUNGSTEN	= makeSmeltable(W,			0).setShapes(WIRE, INGOT, DUST, BLOCK),
			MAT_COPPER		= makeSmeltable(CU,			0).setShapes(WIRE, INGOT, DUST, PLATE, BLOCK),
			MAT_ALUMINIUM	= makeSmeltable(AL,			0).setShapes(WIRE, INGOT, DUST, PLATE, BLOCK),
			MAT_MINGRADE	= makeSmeltable(MINGRADE,	0).setShapes(WIRE, INGOT, DUST, BLOCK),
			MAT_ALLOY		= makeSmeltable(ALLOY,		0).setShapes(WIRE, INGOT, DUST, PLATE, BLOCK),
			MAT_TITANIUM	= makeSmeltable(TI,			0).setShapes(INGOT, DUST, PLATE, BLOCK),
			MAT_LEAD		= makeSmeltable(PB,			0).setShapes(NUGGET, INGOT, DUST, PLATE, BLOCK);

	public static NTMMaterial make(DictFrame dict) {
		return new NTMMaterial(dict);
	}
	
	public static NTMMaterial makeSmeltable(DictFrame dict, int color) {
		return new NTMMaterial(dict).smeltable(SmeltingBehavior.SMELTABLE).setMoltenColor(color);
	}
	
	public static List<MaterialStack> getMaterialsFromItem(ItemStack stack) {
		List<MaterialStack> list = new ArrayList();
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		if(!names.isEmpty()) {
			outer:
			for(String name : names) {
				
				List<MaterialStack> oreEntries = materialOreEntries.get(name);
				
				if(oreEntries != null) {
					list.addAll(oreEntries);
					break outer;
				}
				
				for(Entry<String, MaterialShapes> prefixEntry : prefixByName.entrySet()) {
					String prefix = prefixEntry.getKey();
						
					if(name.startsWith(prefix)) {
						String materialName = name.substring(prefix.length());
						NTMMaterial material = matByName.get(materialName);
						
						if(material != null) {
							list.add(new MaterialStack(material, prefixEntry.getValue().quantity));
							break outer;
						}
					}
				}
			}
		}
		
		List<MaterialStack> entries = materialEntries.get(new ComparableStack(stack));
		
		if(entries != null) {
			list.addAll(entries);
		}
		
		return list;
	}
	
	public static class MaterialStack {
		//final fields to prevent accidental changing
		public final NTMMaterial material;
		public final int amount;
		
		public MaterialStack(NTMMaterial material, int amount) {
			this.material = material;
			this.amount = amount;
		}
	}
	
	public static String formatAmount(int amount) {
		String format = "";
		
		int blocks = amount / BLOCK.quantity;
		amount -= BLOCK.q(blocks);
		int ingots = amount / INGOT.quantity;
		amount -= INGOT.q(ingots);
		int nuggets = amount / NUGGET.quantity;
		amount -= NUGGET.q(nuggets);
		int quanta = amount;
		
		if(blocks > 0) format += blocks + " Blocks ";
		if(ingots > 0) format += ingots + " Ingots ";
		if(nuggets > 0) format += nuggets + " Nuggets ";
		if(quanta > 0) format += quanta + " Quanta ";
		
		return format.trim();
	}
}
