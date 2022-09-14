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
	
	//Vanilla
	public static final NTMMaterial MAT_STONE		= makeSmeltable(df("Stone"), 0);
	public static final NTMMaterial MAT_COAL		= make(COAL).smeltable(SmeltingBehavior.ADDITIVE);
	public static final NTMMaterial MAT_IRON		= makeSmeltable(IRON,		0).omitAutoGen();
	public static final NTMMaterial MAT_GOLD		= makeSmeltable(GOLD,		0).omitAutoGen();
	public static final NTMMaterial MAT_REDSTONE	= makeSmeltable(REDSTONE,	0).omitAutoGen();

	//Radioactive
	public static final NTMMaterial MAT_URANIUM		= makeSmeltable(U,			0).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_THORIUM		= makeSmeltable(TH232,		0).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_PLUTONIUM	= makeSmeltable(PU,			0).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_RADIUM		= makeSmeltable(RA226,		0).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK);

	//Base metals
	public static final NTMMaterial MAT_TITANIUM	= makeSmeltable(TI,			0).setShapes(INGOT, DUST, PLATE, BLOCK);
	public static final NTMMaterial MAT_COPPER		= makeSmeltable(CU,			0).setShapes(WIRE, INGOT, DUST, PLATE, BLOCK);
	public static final NTMMaterial MAT_TUNGSTEN	= makeSmeltable(W,			0).setShapes(WIRE, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_ALUMINIUM	= makeSmeltable(AL,			0).setShapes(WIRE, INGOT, DUST, PLATE, BLOCK);
	public static final NTMMaterial MAT_LEAD		= makeSmeltable(PB,			0).setShapes(NUGGET, INGOT, DUST, PLATE, BLOCK);
	public static final NTMMaterial MAT_BISMUTH		= makeSmeltable(df("Bismuth"), 0).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_ARSENIC		= makeSmeltable(AS,			0).setShapes(NUGGET, INGOT);
	public static final NTMMaterial MAT_TANTALIUM	= makeSmeltable(TA,			0).setShapes(NUGGET, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_NIOBIUM		= makeSmeltable(NB,			0).setShapes(NUGGET, DUSTTINY, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_BERYLLIUM	= makeSmeltable(BE,			0).setShapes(NUGGET, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_COBALT		= makeSmeltable(CO,			0).setShapes(NUGGET, DUSTTINY, BILLET, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_BORON		= makeSmeltable(B,			0).setShapes(DUSTTINY, INGOT, DUST, BLOCK);
	
	//Alloys
	public static final NTMMaterial MAT_STEEL		= makeSmeltable(STEEL,		0).setShapes(DUSTTINY, INGOT, DUST, PLATE, BLOCK);
	public static final NTMMaterial MAT_MINGRADE	= makeSmeltable(MINGRADE,	0).setShapes(WIRE, INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_ALLOY		= makeSmeltable(ALLOY,		0).setShapes(WIRE, INGOT, DUST, PLATE, BLOCK);
	public static final NTMMaterial MAT_TCALLOY		= makeSmeltable(TCALLOY,	0).setShapes(INGOT, DUST);
	public static final NTMMaterial MAT_DURA		= makeSmeltable(DURA,		0).setShapes(INGOT, DUST, BLOCK);
	public static final NTMMaterial MAT_MAGTUNG		= makeSmeltable(MAGTUNG,	0).setShapes(INGOT, DUST, BLOCK);

	public static NTMMaterial make(DictFrame dict) {
		return new NTMMaterial(dict);
	}
	
	public static NTMMaterial makeSmeltable(DictFrame dict, int color) {
		return new NTMMaterial(dict).smeltable(SmeltingBehavior.SMELTABLE).setMoltenColor(color);
	}
	
	public static DictFrame df(String string) {
		return new DictFrame(string);
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
							list.add(new MaterialStack(material, prefixEntry.getValue().q(1)));
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
		
		int blocks = amount / BLOCK.q(1);
		amount -= BLOCK.q(blocks);
		int ingots = amount / INGOT.q(1);
		amount -= INGOT.q(ingots);
		int nuggets = amount / NUGGET.q(1);
		amount -= NUGGET.q(nuggets);
		int quanta = amount;
		
		if(blocks > 0) format += blocks + " Blocks ";
		if(ingots > 0) format += ingots + " Ingots ";
		if(nuggets > 0) format += nuggets + " Nuggets ";
		if(quanta > 0) format += quanta + " Quanta ";
		
		return format.trim();
	}
}
