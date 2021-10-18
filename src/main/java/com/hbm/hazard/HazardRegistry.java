package com.hbm.hazard;

import static com.hbm.blocks.ModBlocks.*;
import static com.hbm.items.ModItems.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.hazard.modifier.*;
import com.hbm.hazard.transformer.HazardTransformerRadiationNBT;
import com.hbm.hazard.type.*;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class HazardRegistry {

	//CO60		             5a		β−	030.00Rad/s	Spicy
	//TC99		       211,000a		β−	002.75Rad/s	Spicy
	//I181		           192h		β−	150.00Rad/s	2 much spice :(
	//XE135		             9h		β−	aaaaaaaaaaaaaaaa
	//CS137		            30a		β−	020.00Rad/s	Spicy
	//AU198		            64h		β−	500.00Rad/s	2 much spice :(
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
	public static final float sas3 = 5F;
	public static final float radsource_mult = 0.5F;
	public static final float pobe = po210 * radsource_mult;
	public static final float rabe = ra226 * radsource_mult;
	public static final float pube = pu238 * radsource_mult;
	public static final float zfb_bi = u235 * 0.35F;
	public static final float zfb_pu241 = pu241 * 0.5F;
	public static final float zfb_am_mix = amrg * 0.5F;
	public static final float bf = 300_000.0F;
	public static final float bfb = 500_000.0F;

	public static final float sr = sa326 * 0.1F;
	public static final float sb = sa326 * 0.1F;
	public static final float trx = 25.0F;
	public static final float trn = 0.1F;
	public static final float wst = 15.0F;
	public static final float wstv = 7.5F;
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
		HazardSystem.register(cordite, makeData(EXPLOSIVE, 2F));
		HazardSystem.register(ballistite, makeData(EXPLOSIVE, 1F));
		
		HazardSystem.register(insert_polonium, makeData(RADIATION, 100F));

		HazardSystem.register(demon_core_open, makeData(RADIATION, 5F));
		HazardSystem.register(demon_core_closed, makeData(RADIATION, 100_000F));

		HazardSystem.register(cell_tritium, makeData(RADIATION, 0.001F));
		HazardSystem.register(cell_sas3, new HazardData().addEntry(RADIATION, sas3).addEntry(BLINDING, 3F));
		HazardSystem.register(cell_balefire, makeData(RADIATION, 50F));
		HazardSystem.register(powder_balefire, makeData(RADIATION, 500F));
		HazardSystem.register(egg_balefire_shard, makeData(RADIATION, bf * nugget));
		HazardSystem.register(egg_balefire, makeData(RADIATION, bf * ingot));

		HazardSystem.register(nuclear_waste_long, makeData(RADIATION, 5F));
		HazardSystem.register(nuclear_waste_long_tiny, makeData(RADIATION, 0.5F));
		HazardSystem.register(nuclear_waste_short, new HazardData().addEntry(RADIATION, 30F).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_short_tiny, new HazardData().addEntry(RADIATION, 3F).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_long_depleted, makeData(RADIATION, 0.5F));
		HazardSystem.register(nuclear_waste_long_depleted_tiny, makeData(RADIATION, 0.05F));
		HazardSystem.register(nuclear_waste_short_depleted, makeData(RADIATION, 3F));
		HazardSystem.register(nuclear_waste_short_depleted_tiny, makeData(RADIATION, 0.3F));

		HazardSystem.register(scrap_nuclear, makeData(RADIATION, 1F));
		HazardSystem.register(trinitite, makeData(RADIATION, trn * ingot));
		HazardSystem.register(nuclear_waste, makeData(RADIATION, wst * ingot));
		HazardSystem.register(billet_nuclear_waste, makeData(RADIATION, wst * billet));
		HazardSystem.register(nuclear_waste_tiny, makeData(RADIATION, wst * nugget));
		HazardSystem.register(nuclear_waste_vitrified, makeData(RADIATION, wstv * ingot));
		HazardSystem.register(nuclear_waste_vitrified_tiny, makeData(RADIATION, wstv * nugget));
		HazardSystem.register(block_waste, makeData(RADIATION, wst * block));
		HazardSystem.register(block_waste_painted, makeData(RADIATION, wst * block));
		HazardSystem.register(block_waste_vitrified, makeData(RADIATION, wstv * block));
		HazardSystem.register(ancient_scrap, makeData(RADIATION, 150F));
		HazardSystem.register(block_corium, makeData(RADIATION, 150F));
		HazardSystem.register(block_corium_cobble, makeData(RADIATION, 150F));
		
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
		
		HazardSystem.register(nugget_schrabidium_fuel, new HazardData().addEntry(RADIATION, saf * nugget).addEntry(BLINDING, 5F * nugget));
		HazardSystem.register(billet_schrabidium_fuel, new HazardData().addEntry(RADIATION, saf * billet).addEntry(BLINDING, 5F * billet));
		HazardSystem.register(ingot_schrabidium_fuel, new HazardData().addEntry(RADIATION, saf * ingot).addEntry(BLINDING, 5F * ingot));
		HazardSystem.register(block_schrabidium_fuel, new HazardData().addEntry(RADIATION, saf * block).addEntry(BLINDING, 5F * block));
		
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

		registerRodRadiation(rod_th232, rod_dual_th232, rod_quad_th232, th232);
		registerRodRadiation(rod_uranium, rod_dual_uranium, rod_quad_uranium, u);
		registerRodRadiation(rod_u233, rod_dual_u233, rod_quad_u233, u233);
		registerRodRadiation(rod_u235, rod_dual_u235, rod_quad_u235, u235);
		registerRodRadiation(rod_u238, rod_dual_u238, rod_quad_u238, u238);
		registerRodRadiation(rod_plutonium, rod_dual_plutonium, rod_quad_plutonium, pu);
		registerRodRadiation(rod_pu238, rod_dual_pu238, rod_quad_pu238, pu238);
		registerRodRadiation(rod_pu239, rod_dual_pu239, rod_quad_pu239, pu239);
		registerRodRadiation(rod_pu240, rod_dual_pu240, rod_quad_pu240, pu240);
		registerRodRadiation(rod_neptunium, rod_dual_neptunium, rod_quad_neptunium, np237);
		registerRodRadiation(rod_polonium, rod_dual_polonium, rod_quad_polonium, po210);
		registerRodRadiationExtra(rod_schrabidium, rod_dual_schrabidium, rod_quad_schrabidium, sa326, BLINDING, 3F);
		registerRodRadiationExtra(rod_solinium, rod_dual_solinium, rod_quad_solinium, sa327, BLINDING, 3F);
		registerRodRadiation(rod_balefire, rod_dual_balefire, rod_quad_balefire, bf);
		registerRodRadiationExtra(rod_balefire_blazing, rod_dual_balefire_blazing, rod_quad_balefire_blazing, bfb, HOT, 5F);

		registerRBMKRod(rbmk_fuel_ueu, u * rod_rbmk, u * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_meu, uf * rod_rbmk, uf * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_heu233, u233 * rod_rbmk, u233 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_heu235, u235 * rod_rbmk, u235 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_thmeu, thf * rod_rbmk, u233 * rod_rbmk * 10);
		registerRBMKRod(rbmk_fuel_lep, puf * rod_rbmk, puf * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_mep, purg * rod_rbmk, purg * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_hep239, pu239 * rod_rbmk, pu239 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_hep241, pu241 * rod_rbmk, pu241 * rod_rbmk * 100);

		registerRBMKPellet(rbmk_pellet_ueu, u * billet, u * billet * 100);
		registerRBMKPellet(rbmk_pellet_meu, uf * billet, uf * billet * 100);
		registerRBMKPellet(rbmk_pellet_heu233, u233 * billet, u233 * billet * 100);
		registerRBMKPellet(rbmk_pellet_heu235, u235 * billet, u235 * billet * 100);
		registerRBMKPellet(rbmk_pellet_thmeu, thf * billet, u233 * billet * 10);
		registerRBMKPellet(rbmk_pellet_lep, puf * billet, puf * billet * 100);
		registerRBMKPellet(rbmk_pellet_mep, purg * billet, purg * billet * 100);
		registerRBMKPellet(rbmk_pellet_hep239, pu239 * billet, pu239 * billet * 100);
		registerRBMKPellet(rbmk_pellet_hep241, pu241 * billet, pu241 * billet * 100);

		HazardSystem.register(powder_yellowcake, makeData(RADIATION, yc * powder));
		HazardSystem.register(block_yellowcake, makeData(RADIATION, yc * block * powder_mult));
		HazardSystem.register(ModItems.fallout, makeData(RADIATION, fo * powder));
		HazardSystem.register(ModBlocks.fallout, makeData(RADIATION, fo * powder * 2));
		HazardSystem.register(ModBlocks.block_fallout, makeData(RADIATION, yc * block * powder_mult));
		
		//TODO
	}
	
	public static void registerTrafos() {
		HazardSystem.trafos.add(new HazardTransformerRadiationNBT());
	}
	
	private static HazardData makeData() { return new HazardData(); }
	private static HazardData makeData(HazardTypeBase hazard) { return new HazardData().addEntry(hazard); }
	private static HazardData makeData(HazardTypeBase hazard, float level) { return new HazardData().addEntry(hazard, level); }
	private static HazardData makeData(HazardTypeBase hazard, float level, boolean override) { return new HazardData().addEntry(hazard, level, override); }
	
	private static void registerRodRadiation(Item single, Item dual, Item quad, float base) {
		HazardSystem.register(single, makeData(RADIATION, base * rod));
		HazardSystem.register(dual, makeData(RADIATION, base * rod_dual));
		HazardSystem.register(quad, makeData(RADIATION, base * rod_quad));
	}
	
	private static void registerRodRadiationExtra(Item single, Item dual, Item quad, float base, HazardTypeBase extra, float base2) {
		HazardSystem.register(single, new HazardData().addEntry(RADIATION, base * rod).addEntry(extra, base2 * rod));
		HazardSystem.register(dual, new HazardData().addEntry(RADIATION, base * rod_dual).addEntry(extra, base2 * rod_dual));
		HazardSystem.register(quad, new HazardData().addEntry(RADIATION, base * rod_quad).addEntry(extra, base2 * rod_quad));
	}
	
	private static void registerRBMKPellet(Item pellet, float base, float dep) { registerRBMKPellet(pellet, base, dep, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, float blinding) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep)));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerRBMKRod(Item rod, float base, float dep) { registerRBMK(rod, base, dep, true, 0F); }
	private static void registerRBMKRod(Item rod, float base, float dep, float blinding) { registerRBMK(rod, base, dep, true, blinding); }
	
	private static void registerRBMK(Item rod, float base, float dep, boolean hot, float blinding) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep)));
		if(hot) data.addEntry(new HazardEntry(HOT, 0).addMod(new HazardModifierRBMKHot()));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		HazardSystem.register(rod, data);
	}
}
