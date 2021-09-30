package com.hbm.hazard;

import static com.hbm.blocks.ModBlocks.*;
import static com.hbm.items.ModItems.*;

import com.hbm.hazard.transformer.HazardTransformerRadiationNBT;
import com.hbm.hazard.type.*;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class HazardRegistry {

	//CO60		             5a		β−	030.00Rad/s	Spicy
	//TC99		       211,000a		β−	002.75Rad/s	Spicy
	//I181		           192h		β−	150.00Rad/s	2 much spice :(
	//XE135		             9h		β−	aaaaaaaaaaaaaaaa
	//CS137		            30a		β−	020.00Rad/s	Spicy
	//AU192		            64h		β−	500.00Rad/s	2 much spice :(
	//AT209		             5h		β+	like 2k or sth idk bruv
	//PO210		           138d		α	075.00Rad/s	Spicy
	//RA226		         1,600a		α	007.50Rad/s
	//TH232		14,000,000,000a		α	000.10Rad/s
	//U233		       160,000a		α	005.00Rad/s
	//U235		   700,000,000a		α	001.00Rad/s
	//U238		 4,500,000,000a		α	000.25Rad/s
	//NP237		     2,100,000a		α	002.50Rad/s
	//PU238		            88a		α	010.00Rad/s	Spicy
	//PU239		        24,000a		α	005.00Rad/s
	//PU240		         6,600a		α	007.50Rad/s
	//PU241		            14a		β−	025.00Rad/s	Spicy
	//AM241		           432a		α	008.50Rad/s
	//AM242		           141a		β−	009.50Rad/s

	public static final float co60 = 30.0F;
	public static final float tc99 = 2.75F;
	public static final float i131 = 150.0F;
	public static final float xe135 = 1250.0F;
	public static final float cs137 = 20.0F;
	public static final float au198 = 500.0F;
	public static final float at209 = 2000.0F;
	public static final float po210 = 75.0F;
	public static final float ra226 = 7.5F;
	public static final float th232 = 0.1F;
	public static final float thf = 1.75F;
	public static final float u = 0.35F;
	public static final float u233 = 5.0F;
	public static final float u235 = 1.0F;
	public static final float u238 = 0.25F;
	public static final float uf = 0.5F;
	public static final float np237 = 2.5F;
	public static final float npf = 1.5F;
	public static final float pu = 7.5F;
	public static final float purg = 6.25F;
	public static final float pu238 = 10.0F;
	public static final float pu239 = 5.0F;
	public static final float pu240 = 7.5F;
	public static final float pu241 = 25.0F;
	public static final float puf = 4.25F;
	public static final float am241 = 8.5F;
	public static final float am242 = 9.5F;
	public static final float amrg = 9.0F;
	public static final float amf = 4.75F;
	public static final float mox = 2.5F;
	public static final float sa326 = 15.0F;
	public static final float sa327 = 17.5F;
	public static final float saf = 5.85F;
	public static final float radsource_mult = 0.5F;
	public static final float pobe = po210 * radsource_mult;
	public static final float rabe = ra226 * radsource_mult;
	public static final float pube = pu238 * radsource_mult;

	public static final float sr = sa326 * 0.1F;
	public static final float sb = sa326 * 0.1F;
	public static final float trx = 25.0F;
	public static final float trn = 0.1F;
	public static final float wst = 15.0F;
	public static final float yc = u;
	public static final float fo = 10F;

	public static final float nugget = 0.1F;
	public static final float ingot = 1.0F;
	public static final float gem = 1.0F;
	public static final float plate = 1.0F;
	public static final float powder_mult = 3.0F;
	public static final float powder = ingot * powder_mult;
	public static final float powder_tiny = nugget * powder_mult;
	public static final float ore = ingot;
	public static final float block = 10.0F;
	public static final float crystal = block;
	public static final float billet = 0.5F;
	public static final float rtg = billet * 3;
	public static final float rod = 0.5F;
	public static final float rod_dual = rod * 2;
	public static final float rod_quad = rod * 4;
	public static final float rod_rbmk = rod * 8;

	public static final HazardTypeBase RADIATION = new HazardTypeRadiation();
	public static final HazardTypeBase DIGAMMA = new HazardTypeDigamma();
	public static final HazardTypeBase HOT = new HazardTypeHot();
	public static final HazardTypeBase BLINDING = new HazardTypeBlinding();
	public static final HazardTypeBase ASBESTOS = new HazardTypeAsbestos();
	public static final HazardTypeBase COAL = new HazardTypeCoal();
	public static final HazardTypeBase HYDROACTIVE = new HazardTypeHydroactive();
	public static final HazardTypeBase EXPLOSIVE = new HazardTypeExplosive();
	
	public static void registerItems() {
		
		HazardSystem.register(Items.gunpowder, makeData(EXPLOSIVE, 1F));
		HazardSystem.register(Blocks.tnt, makeData(EXPLOSIVE, 4F));
		HazardSystem.register(Items.pumpkin_pie, makeData(EXPLOSIVE, 4F));

		HazardSystem.register("dustCoal", makeData(COAL, powder));
		HazardSystem.register("dustTinyCoal", makeData(COAL, powder_tiny));
		HazardSystem.register("dustLignite", makeData(COAL, powder));
		HazardSystem.register("dustTinyLignite", makeData(COAL, powder_tiny));
		
		HazardSystem.register(ingot_semtex, makeData(EXPLOSIVE, 10F));
		HazardSystem.register(block_semtex, makeData(EXPLOSIVE, 40F));

		HazardSystem.register(trinitite, makeData(RADIATION, trn * ingot));
		HazardSystem.register(nuclear_waste_long, makeData(RADIATION, 5F));
		HazardSystem.register(nuclear_waste_long_tiny, makeData(RADIATION, 0.5F));
		HazardSystem.register(nuclear_waste_short, new HazardData().addEntry(RADIATION, 30F).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_short_tiny, new HazardData().addEntry(RADIATION, 3F).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_long_depleted, makeData(RADIATION, 0.5F));
		HazardSystem.register(nuclear_waste_long_depleted_tiny, makeData(RADIATION, 0.05F));
		HazardSystem.register(nuclear_waste_short_depleted, makeData(RADIATION, 3F));
		HazardSystem.register(nuclear_waste_short_depleted_tiny, makeData(RADIATION, 0.3F));
		HazardSystem.register(nuclear_waste, makeData(RADIATION, 15F));
		HazardSystem.register(nuclear_waste_tiny, makeData(RADIATION, 1.5F));
		HazardSystem.register(nuclear_waste_vitrified, makeData(RADIATION, 7.5F));
		HazardSystem.register(nuclear_waste_vitrified_tiny, makeData(RADIATION, 0.75F));
		HazardSystem.register(waste_uranium, makeData(RADIATION, 15F));
		HazardSystem.register(waste_thorium, makeData(RADIATION, 10F));
		HazardSystem.register(waste_plutonium, makeData(RADIATION, 15F));
		HazardSystem.register(waste_mox, makeData(RADIATION, 15F));
		HazardSystem.register(waste_schrabidium, new HazardData().addEntry(RADIATION, 15F).addEntry(HOT, 5F));
		HazardSystem.register(waste_uranium_hot, new HazardData().addEntry(RADIATION, 10F).addEntry(HOT, 5F));
		HazardSystem.register(waste_thorium_hot, new HazardData().addEntry(RADIATION, 15F).addEntry(HOT, 5F));
		HazardSystem.register(waste_plutonium_hot, new HazardData().addEntry(RADIATION, 15F).addEntry(HOT, 5F));
		HazardSystem.register(waste_mox_hot, new HazardData().addEntry(RADIATION, 15F).addEntry(HOT, 5F));
		HazardSystem.register(waste_schrabidium_hot, new HazardData().addEntry(RADIATION, 40F).addEntry(HOT, 5F).addEntry(BLINDING, 5F));
		
		HazardSystem.register(nugget_uranium_fuel, makeData(RADIATION, uf * nugget));
		HazardSystem.register(billet_uranium_fuel, makeData(RADIATION, uf * billet));
		HazardSystem.register(ingot_uranium_fuel, makeData(RADIATION, uf * ingot));
		HazardSystem.register(block_uranium_fuel, makeData(RADIATION, uf * block));
		
		HazardSystem.register(nugget_plutonium_fuel, makeData(RADIATION, puf * nugget));
		HazardSystem.register(billet_plutonium_fuel, makeData(RADIATION, puf * billet));
		HazardSystem.register(ingot_plutonium_fuel, makeData(RADIATION, puf * ingot));
		HazardSystem.register(block_plutonium_fuel, makeData(RADIATION, puf * block));
		
		HazardSystem.register(nugget_thorium_fuel, makeData(RADIATION, thf * nugget));
		HazardSystem.register(billet_thorium_fuel, makeData(RADIATION, thf * billet));
		HazardSystem.register(ingot_thorium_fuel, makeData(RADIATION, thf * ingot));
		HazardSystem.register(block_thorium_fuel, makeData(RADIATION, thf * block));
		
		HazardSystem.register(nugget_neptunium_fuel, makeData(RADIATION, npf * nugget));
		HazardSystem.register(billet_neptunium_fuel, makeData(RADIATION, npf * billet));
		HazardSystem.register(ingot_neptunium_fuel, makeData(RADIATION, npf * ingot));
		
		HazardSystem.register(nugget_mox_fuel, makeData(RADIATION, mox * nugget));
		HazardSystem.register(billet_mox_fuel, makeData(RADIATION, mox * billet));
		HazardSystem.register(ingot_mox_fuel, makeData(RADIATION, mox * ingot));
		HazardSystem.register(block_mox_fuel, makeData(RADIATION, mox * block));
		
		HazardSystem.register(nugget_americium_fuel, makeData(RADIATION, amf * nugget));
		HazardSystem.register(billet_americium_fuel, makeData(RADIATION, amf * billet));
		HazardSystem.register(ingot_americium_fuel, makeData(RADIATION, amf * ingot));
		
		HazardSystem.register(nugget_schrabidium_fuel, makeData(RADIATION, saf * nugget));
		HazardSystem.register(billet_schrabidium_fuel, makeData(RADIATION, saf * billet));
		HazardSystem.register(ingot_schrabidium_fuel, makeData(RADIATION, saf * ingot));
		HazardSystem.register(block_schrabidium_fuel, makeData(RADIATION, saf * block));
		
		HazardSystem.register(nugget_hes, makeData(RADIATION, saf * nugget));
		HazardSystem.register(billet_hes, makeData(RADIATION, saf * billet));
		HazardSystem.register(ingot_hes, makeData(RADIATION, saf * ingot));
		
		HazardSystem.register(nugget_les, makeData(RADIATION, saf * nugget));
		HazardSystem.register(billet_les, makeData(RADIATION, saf * billet));
		HazardSystem.register(ingot_les, makeData(RADIATION, saf * ingot));

		HazardSystem.register(billet_balefire_gold, makeData(RADIATION, au198 * billet));
		HazardSystem.register(billet_po210be, makeData(RADIATION, pobe * billet));
		HazardSystem.register(billet_ra226be, makeData(RADIATION, rabe * billet));
		HazardSystem.register(billet_pu238be, makeData(RADIATION, pube * billet));
		
		//TODO: move this into its own method
		HazardSystem.trafos.add(new HazardTransformerRadiationNBT());
	}
	
	private static HazardData makeData() { return new HazardData(); }
	private static HazardData makeData(HazardTypeBase hazard) { return new HazardData().addEntry(hazard); }
	private static HazardData makeData(HazardTypeBase hazard, float level) { return new HazardData().addEntry(hazard, level); }
	private static HazardData makeData(HazardTypeBase hazard, float level, boolean override) { return new HazardData().addEntry(hazard, level, override); }
}
