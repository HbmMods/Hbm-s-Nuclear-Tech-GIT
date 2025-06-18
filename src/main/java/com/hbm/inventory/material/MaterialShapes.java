package com.hbm.inventory.material;

import java.util.ArrayList;
import java.util.List;

import com.hbm.util.Compat;

public class MaterialShapes {
	
	public static final List<MaterialShapes> allShapes = new ArrayList();
	
	public static final MaterialShapes ANY = new MaterialShapes(0, "any").noAutogen();
	public static final MaterialShapes ONLY_ORE = new MaterialShapes(0, "ore").noAutogen();
	public static final MaterialShapes ORE = new MaterialShapes(0, "ore", "oreNether").noAutogen();
	public static final MaterialShapes ORENETHER = new MaterialShapes(0, "oreNether").noAutogen();
	
	public static final MaterialShapes QUANTUM = new MaterialShapes(1); // 1/72 of an ingot, allows the ingot to be divisible through 2, 4, 6, 8, 9, 12, 24 and 36
	public static final MaterialShapes NUGGET = new MaterialShapes(8, "nugget", "tiny");
	public static final MaterialShapes TINY = new MaterialShapes(8, "tiny").noAutogen();
	public static final MaterialShapes FRAGMENT = new MaterialShapes(8, "bedrockorefragment");
	public static final MaterialShapes DUSTTINY = new MaterialShapes(NUGGET.quantity, "dustTiny");
	public static final MaterialShapes WIRE = new MaterialShapes(9, "wireFine");
	public static final MaterialShapes BOLT = new MaterialShapes(9, "bolt");
	public static final MaterialShapes BILLET = new MaterialShapes(NUGGET.quantity * 6, "billet");
	public static final MaterialShapes INGOT = new MaterialShapes(NUGGET.quantity * 9, "ingot");
	public static final MaterialShapes GEM = new MaterialShapes(INGOT.quantity, "gem");
	public static final MaterialShapes CRYSTAL = new MaterialShapes(INGOT.quantity, "crystal");
	public static final MaterialShapes DUST = new MaterialShapes(INGOT.quantity, "dust");
	public static final MaterialShapes DENSEWIRE = new MaterialShapes(INGOT.quantity, "wireDense");
	public static final MaterialShapes PLATE = new MaterialShapes(INGOT.quantity, "plate");
	public static final MaterialShapes CASTPLATE = new MaterialShapes(INGOT.quantity * 3, "plateTriple");
	public static final MaterialShapes WELDEDPLATE = new MaterialShapes(INGOT.quantity * 6, "plateSextuple");
	public static final MaterialShapes SHELL = new MaterialShapes(INGOT.quantity * 4, "shell");
	public static final MaterialShapes PIPE = new MaterialShapes(INGOT.quantity * 3, "ntmpipe");
	public static final MaterialShapes QUART = new MaterialShapes(162);
	public static final MaterialShapes BLOCK = new MaterialShapes(INGOT.quantity * 9, "block");
	public static final MaterialShapes HEAVY_COMPONENT = new MaterialShapes(CASTPLATE.quantity * 256, "componentHeavy");

	public static final MaterialShapes LIGHTBARREL =	new MaterialShapes(INGOT.quantity * 3, "barrelLight");
	public static final MaterialShapes HEAVYBARREL =	new MaterialShapes(INGOT.quantity * 6, "barrelHeavy");
	public static final MaterialShapes LIGHTRECEIVER =	new MaterialShapes(INGOT.quantity * 4, "receiverLight");
	public static final MaterialShapes HEAVYRECEIVER =	new MaterialShapes(INGOT.quantity * 9, "receiverHeavy");
	public static final MaterialShapes MECHANISM =		new MaterialShapes(INGOT.quantity * 4, "gunMechanism");
	public static final MaterialShapes STOCK =			new MaterialShapes(INGOT.quantity * 4, "stock");
	public static final MaterialShapes GRIP =			new MaterialShapes(INGOT.quantity * 2, "grip");
	
	public static void registerCompatShapes() {

		if(Compat.isModLoaded(Compat.MOD_GT6)) {
			new MaterialShapes(BLOCK.q(1), "blockDust").noAutogen();
			new MaterialShapes(BLOCK.q(1), "blockGem").noAutogen();
			new MaterialShapes(BLOCK.q(1), "blockIngot").noAutogen();
			new MaterialShapes(BLOCK.q(1), "blockSolid").noAutogen();
			new MaterialShapes(INGOT.q(9, 8), "crushed").noAutogen();
			new MaterialShapes(INGOT.q(9, 72), "crushedTiny").noAutogen();
			new MaterialShapes(INGOT.q(10, 8), "crushedPurified").noAutogen();
			new MaterialShapes(INGOT.q(10, 72), "crushedPurifiedTiny").noAutogen();
			new MaterialShapes(INGOT.q(11, 8), "crushedCentrifuged").noAutogen();
			new MaterialShapes(INGOT.q(11, 72), "crushedCentrifugedTiny").noAutogen();
			new MaterialShapes(INGOT.q(1, 4), "dustSmall").noAutogen();
			new MaterialShapes(INGOT.q(1, 72), "dustDiv72").noAutogen();
			new MaterialShapes(INGOT.q(10, 9), "dustImpure").noAutogen();
			new MaterialShapes(INGOT.q(11, 9), "dustPure").noAutogen();
			new MaterialShapes(INGOT.q(12, 9), "dustRefined").noAutogen();
		}
	}
	
	public boolean noAutogen = false;
	private int quantity;
	public final String[] prefixes;
	
	private MaterialShapes(int quantity, String... prefixes) {
		this.quantity = quantity;
		this.prefixes = prefixes;
		
		for(String prefix : prefixes) {
			Mats.prefixByName.put(prefix, this);
		}
		
		allShapes.add(this);
	}
	
	/** Disables recipe autogen for special cases like compatibility prefixes (TINY, ORENETHER), technical prefixes (ANY) or prefixes that have to be handled manually (ORE) */
	public MaterialShapes noAutogen() {
		this.noAutogen = true;
		return this;
	}
	
	public int q(int amount) {
		return this.quantity * amount;
	}
	
	public int q(int unitsUsed, int itemsProduced) { //eg rails: INOGT.q(6, 16) since the recipe uses 6 iron ingots producing 16 individual rail blocks
		return this.quantity * unitsUsed / itemsProduced;
	}

	public String name() {
		return (prefixes != null && prefixes.length > 0) ? prefixes[0] : "unknown";
	}
	
	public String make(NTMMaterial mat) {
		return this.name() + mat.names[0];
	}
}
