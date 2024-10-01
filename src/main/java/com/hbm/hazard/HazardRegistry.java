package com.hbm.hazard;

import static com.hbm.blocks.ModBlocks.*;
import static com.hbm.items.ModItems.*;
import static com.hbm.inventory.OreDictManager.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.hazard.modifier.*;
import com.hbm.hazard.transformer.*;
import com.hbm.hazard.type.*;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.BreedingRodType;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import com.hbm.items.machine.ItemRTGPelletDepleted.DepletedRTGMaterial;
import com.hbm.items.machine.ItemWatzPellet.EnumWatzType;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.items.special.ItemHolotapeImage.EnumHoloImage;
import com.hbm.util.Compat;
import com.hbm.util.Compat.ReikaIsotope;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SuppressWarnings("unused") //shut the fuck up
public class HazardRegistry {

	//CO60		             5a		β−	030.00Rad/s	Spicy
	//SR90		            29a		β−	015.00Rad/s Spicy
	//TC99		       211,000a		β−	002.75Rad/s	Spicy
	//I181		           192h		β−	150.00Rad/s	2 much spice :(
	//XE135		             9h		β−	aaaaaaaaaaaaaaaa
	//CS137		            30a		β−	020.00Rad/s	Spicy
	//AU198		            64h		β−	500.00Rad/s	2 much spice :(
	//PB209		             3h		β−	10,000.00Rad/s mama mia my face is melting off
	//AT209		             5h		β+	like 7.5k or sth idk bruv
	//PO210		           138d		α	075.00Rad/s	Spicy
	//RA226		         1,600a		α	007.50Rad/s
	//AC227		            22a		β−	030.00Rad/s Spicy
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

	//simplified groups for ReC compat
	public static final float gen_S = 10_000F;
	public static final float gen_H = 2_000F;
	public static final float gen_10D = 100F;
	public static final float gen_100D = 80F;
	public static final float gen_1Y = 50F;
	public static final float gen_10Y = 30F;
	public static final float gen_100Y = 10F;
	public static final float gen_1K = 7.5F;
	public static final float gen_10K = 6.25F;
	public static final float gen_100K = 5F;
	public static final float gen_1M = 2.5F;
	public static final float gen_10M = 1.5F;
	public static final float gen_100M = 1F;
	public static final float gen_1B = 0.5F;
	public static final float gen_10B = 0.1F;

	public static final float co60 = 30.0F;
	public static final float sr90 = 15.0F;
	public static final float tc99 = 2.75F;
	public static final float i131 = 150.0F;
	public static final float xe135 = 1250.0F;
	public static final float cs137 = 20.0F;
	public static final float au198 = 500.0F;
	public static final float pb209 = 10000.0F;
	public static final float at209 = 7500.0F;
	public static final float po210 = 75.0F;
	public static final float ra226 = 7.5F;
	public static final float ac227 = 30.0F;
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
	public static final float gh336 = 5.0F;
	public static final float mud = 1.0F;
	public static final float radsource_mult = 3.0F;
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
	public static final float plate = ingot;
	public static final float plateCast = plate * 3;
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
		HazardSystem.register(Items.pumpkin_pie, makeData(EXPLOSIVE, 1F));
		
		HazardSystem.register(ball_dynamite, makeData(EXPLOSIVE, 2F));
		HazardSystem.register(stick_dynamite, makeData(EXPLOSIVE, 1F));
		HazardSystem.register(stick_tnt, makeData(EXPLOSIVE, 1.5F));
		HazardSystem.register(stick_semtex, makeData(EXPLOSIVE, 2.5F));
		HazardSystem.register(stick_c4, makeData(EXPLOSIVE, 2.5F));

		HazardSystem.register(cordite, makeData(EXPLOSIVE, 2F));
		HazardSystem.register(ballistite, makeData(EXPLOSIVE, 1F));

		HazardSystem.register("dustCoal", makeData(COAL, powder));
		HazardSystem.register("dustTinyCoal", makeData(COAL, powder_tiny));
		HazardSystem.register("dustLignite", makeData(COAL, powder));
		HazardSystem.register("dustTinyLignite", makeData(COAL, powder_tiny));
		
		HazardSystem.register(insert_polonium, makeData(RADIATION, 100F));

		HazardSystem.register(demon_core_open, makeData(RADIATION, 5F));
		HazardSystem.register(demon_core_closed, makeData(RADIATION, 100_000F));
		HazardSystem.register(lamp_demon, makeData(RADIATION, 100_000F));

		HazardSystem.register(cell_tritium, makeData(RADIATION, 0.001F));
		HazardSystem.register(cell_sas3, makeData().addEntry(RADIATION, sas3).addEntry(BLINDING, 60F));
		HazardSystem.register(cell_balefire, makeData(RADIATION, 50F));
		HazardSystem.register(powder_balefire, makeData(RADIATION, 500F));
		HazardSystem.register(egg_balefire_shard, makeData(RADIATION, bf * nugget));
		HazardSystem.register(egg_balefire, makeData(RADIATION, bf * ingot));

		HazardSystem.register(solid_fuel_bf, makeData(RADIATION, 1000)); //roughly the amount of the balefire shard diluted in 250mB of rocket fuel
		HazardSystem.register(solid_fuel_presto_bf, makeData(RADIATION, 2000));
		HazardSystem.register(solid_fuel_presto_triplet_bf, makeData(RADIATION, 6000));

		HazardSystem.register(nuclear_waste_long, makeData(RADIATION, 5F));
		HazardSystem.register(nuclear_waste_long_tiny, makeData(RADIATION, 0.5F));
		HazardSystem.register(nuclear_waste_short, makeData().addEntry(RADIATION, 30F).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_short_tiny, makeData().addEntry(RADIATION, 3F).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_long_depleted, makeData(RADIATION, 0.5F));
		HazardSystem.register(nuclear_waste_long_depleted_tiny, makeData(RADIATION, 0.05F));
		HazardSystem.register(nuclear_waste_short_depleted, makeData(RADIATION, 3F));
		HazardSystem.register(nuclear_waste_short_depleted_tiny, makeData(RADIATION, 0.3F));

		HazardSystem.register(scrap_nuclear, makeData(RADIATION, 1F));
		HazardSystem.register(trinitite, makeData(RADIATION, trn * ingot));
		HazardSystem.register(block_trinitite, makeData(RADIATION, trn * block));
		HazardSystem.register(nuclear_waste, makeData(RADIATION, wst * ingot));
		HazardSystem.register(yellow_barrel, makeData(RADIATION, wst * ingot * 10));
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
		
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 0), makeData(RADIATION, 0.5F));
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 1), makeData(RADIATION, 1F));
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 2), makeData(RADIATION, 2.5F));
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 3), makeData(RADIATION, 4F));
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 4), makeData(RADIATION, 5F));	
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 5), makeData(RADIATION, 10F));

		HazardSystem.register(new ItemStack(ModBlocks.ore_sellafield_radgem), makeData(RADIATION, 25F));
		HazardSystem.register(new ItemStack(ModItems.gem_rad), makeData(RADIATION, 25F));
		
		registerOtherFuel(rod_zirnox, EnumZirnoxType.NATURAL_URANIUM_FUEL.ordinal(), u * rod_dual, wst * rod_dual * 11.5F, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.URANIUM_FUEL.ordinal(), uf * rod_dual, wst * rod_dual * 10F, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.TH232.ordinal(), th232 * rod_dual, thf * rod_dual, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.THORIUM_FUEL.ordinal(), thf * rod_dual, wst * rod_dual * 7.5F, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.MOX_FUEL.ordinal(), mox * rod_dual, wst * rod_dual * 10F, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.PLUTONIUM_FUEL.ordinal(), puf * rod_dual, wst * rod_dual * 12.5F, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.U233_FUEL.ordinal(), u233 * rod_dual, wst * rod_dual * 10F, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.U235_FUEL.ordinal(), u235 * rod_dual, wst * rod_dual * 11F, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.LES_FUEL.ordinal(), saf * rod_dual, wst * rod_dual * 15F, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.LITHIUM.ordinal(), 0, 0.001F * rod_dual, false);
		registerOtherFuel(rod_zirnox, EnumZirnoxType.ZFB_MOX.ordinal(), mox * rod_dual, wst * rod_dual * 5F, false);
		
		HazardSystem.register(rod_zirnox_natural_uranium_fuel_depleted, makeData(RADIATION, wst * rod_dual * 11.5F));
		HazardSystem.register(rod_zirnox_uranium_fuel_depleted, makeData(RADIATION, wst * rod_dual * 10F));
		HazardSystem.register(rod_zirnox_thorium_fuel_depleted, makeData(RADIATION, wst * rod_dual * 7.5F));
		HazardSystem.register(rod_zirnox_mox_fuel_depleted, makeData(RADIATION, wst * rod_dual * 10F));
		HazardSystem.register(rod_zirnox_plutonium_fuel_depleted, makeData(RADIATION, wst * rod_dual * 12.5F));
		HazardSystem.register(rod_zirnox_u233_fuel_depleted, makeData(RADIATION, wst * rod_dual * 10F));
		HazardSystem.register(rod_zirnox_u235_fuel_depleted, makeData(RADIATION, wst * rod_dual * 11F));
		HazardSystem.register(rod_zirnox_les_fuel_depleted, makeData().addEntry(RADIATION, wst * rod_dual * 15F).addEntry(BLINDING, 20F));
		HazardSystem.register(rod_zirnox_tritium, makeData(RADIATION, 0.001F * rod_dual));
		HazardSystem.register(rod_zirnox_zfb_mox_depleted, makeData(RADIATION, wst * rod_dual * 5F));
		
		registerOtherWaste(waste_natural_uranium, wst * billet * 11.5F);
		registerOtherWaste(waste_uranium, wst * billet * 10F);
		registerOtherWaste(waste_thorium, wst * billet * 7.5F);
		registerOtherWaste(waste_mox, wst * billet * 10F);
		registerOtherWaste(waste_plutonium, wst * billet * 12.5F);
		registerOtherWaste(waste_u233, wst * billet * 10F);
		registerOtherWaste(waste_u235, wst * billet * 11F);
		registerOtherWaste(waste_schrabidium, wst * billet * 15F);
		registerOtherWaste(waste_zfb_mox, wst * billet * 5F);
		
		registerOtherFuel(plate_fuel_u233, u233 * ingot, wst * ingot * 13F, false);
		registerOtherFuel(plate_fuel_u235, u235 * ingot, wst * ingot * 10F, false);
		registerOtherFuel(plate_fuel_mox, mox * ingot, wst * ingot * 16F, false);
		registerOtherFuel(plate_fuel_pu239, pu239 * ingot, wst * ingot * 13.5F, false);
		registerOtherFuel(plate_fuel_sa326, sa326 * ingot, wst * ingot * 10F, true);
		registerOtherFuel(plate_fuel_ra226be, rabe * billet, pobe * nugget * 3, false);
		registerOtherFuel(plate_fuel_pu238be, pube * billet, pube * nugget * 1, false);
		
		registerOtherWaste(waste_plate_u233, wst * ingot * 13F);
		registerOtherWaste(waste_plate_u235, wst * ingot * 10F);
		registerOtherWaste(waste_plate_mox, wst * ingot * 16F);
		registerOtherWaste(waste_plate_pu239, wst * ingot * 13.5F);
		registerOtherWaste(waste_plate_sa326, wst * ingot * 10F);
		registerRadSourceWaste(waste_plate_ra226be, pobe * nugget * 3);
		registerRadSourceWaste(waste_plate_pu238be, pube * nugget * 1);
		
		HazardSystem.register(debris_graphite, makeData().addEntry(RADIATION, 70F).addEntry(HOT, 5F));
		HazardSystem.register(debris_metal, makeData(RADIATION, 5F));
		HazardSystem.register(debris_fuel, makeData().addEntry(RADIATION, 500F).addEntry(HOT, 5F));
		HazardSystem.register(debris_concrete, makeData(RADIATION, 30F));
		HazardSystem.register(debris_exchanger, makeData(RADIATION, 25F));
		HazardSystem.register(debris_shrapnel, makeData(RADIATION, 2.5F));
		HazardSystem.register(debris_element, makeData(RADIATION, 100F));
		
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
		
		HazardSystem.register(nugget_schrabidium_fuel, makeData().addEntry(RADIATION, saf * nugget).addEntry(BLINDING, 5F * nugget));
		HazardSystem.register(billet_schrabidium_fuel, makeData().addEntry(RADIATION, saf * billet).addEntry(BLINDING, 5F * billet));
		HazardSystem.register(ingot_schrabidium_fuel, makeData().addEntry(RADIATION, saf * ingot).addEntry(BLINDING, 5F * ingot));
		HazardSystem.register(block_schrabidium_fuel, makeData().addEntry(RADIATION, saf * block).addEntry(BLINDING, 5F * block));
		
		HazardSystem.register(nugget_hes, makeData(RADIATION, saf * nugget));
		HazardSystem.register(billet_hes, makeData(RADIATION, saf * billet));
		HazardSystem.register(ingot_hes, makeData(RADIATION, saf * ingot));
		
		HazardSystem.register(nugget_les, makeData(RADIATION, saf * nugget));
		HazardSystem.register(billet_les, makeData(RADIATION, saf * billet));
		HazardSystem.register(ingot_les, makeData(RADIATION, saf * ingot));

		HazardSystem.register(billet_balefire_gold, makeData(RADIATION, au198 * billet));
		HazardSystem.register(billet_flashlead, makeData().addEntry(RADIATION, pb209 * 1.25F * billet).addEntry(HOT, 7F));
		HazardSystem.register(billet_po210be, makeData(RADIATION, pobe * billet));
		HazardSystem.register(billet_ra226be, makeData(RADIATION, rabe * billet));
		HazardSystem.register(billet_pu238be, makeData(RADIATION, pube * billet));
		
		registerRTGPellet(pellet_rtg, pu238 * rtg, 0, 3F);
		registerRTGPellet(pellet_rtg_radium, ra226 * rtg, 0);
		registerRTGPellet(pellet_rtg_weak, (pu238 + (u238 * 2)) * billet, 0);
		registerRTGPellet(pellet_rtg_strontium, sr90 * rtg, 0);
		registerRTGPellet(pellet_rtg_cobalt, co60 * rtg, 0);
		registerRTGPellet(pellet_rtg_actinium, ac227 * rtg, 0);
		registerRTGPellet(pellet_rtg_polonium, po210 * rtg, 0, 3F);
		registerRTGPellet(pellet_rtg_lead, pb209 * rtg, 0, 7F, 50F);
		registerRTGPellet(pellet_rtg_gold, au198 * rtg, 0, 5F);
		registerRTGPellet(pellet_rtg_americium, am241 * rtg, 0);
		HazardSystem.register(new ItemStack(pellet_rtg_depleted, 1, DepletedRTGMaterial.NEPTUNIUM.ordinal()), makeData(RADIATION, np237 * rtg));
		
		HazardSystem.register(pile_rod_uranium, makeData(RADIATION, u * billet * 3));
		HazardSystem.register(pile_rod_pu239, makeData(RADIATION, !GeneralConfig.enable528 ? purg * billet + pu239 * billet + u * billet : purg * billet + pu239 * billet + wst * billet));
		HazardSystem.register(pile_rod_plutonium, makeData(RADIATION, !GeneralConfig.enable528 ? purg * billet * 2 + u * billet : purg * billet * 2 + wst * billet));
		HazardSystem.register(pile_rod_source, makeData(RADIATION, rabe * billet * 3));
		
		registerBreedingRodRadiation(BreedingRodType.TRITIUM, 0.001F);
		registerBreedingRodRadiation(BreedingRodType.CO60, co60);
		registerBreedingRodRadiation(BreedingRodType.RA226, ra226);
		registerBreedingRodRadiation(BreedingRodType.AC227, ac227);
		registerBreedingRodRadiation(BreedingRodType.TH232, th232);
		registerBreedingRodRadiation(BreedingRodType.THF, thf);
		registerBreedingRodRadiation(BreedingRodType.U235, u235);
		registerBreedingRodRadiation(BreedingRodType.NP237, np237);
		registerBreedingRodRadiation(BreedingRodType.U238, u238);
		registerBreedingRodRadiation(BreedingRodType.PU238, pu238); //it's in a container :)
		registerBreedingRodRadiation(BreedingRodType.PU239, pu239);
		registerBreedingRodRadiation(BreedingRodType.RGP, purg);
		registerBreedingRodRadiation(BreedingRodType.WASTE, wst);
		registerBreedingRodRadiation(BreedingRodType.URANIUM, u);

		registerRBMKRod(rbmk_fuel_ueu, u * rod_rbmk, wst * rod_rbmk * 20F);
		registerRBMKRod(rbmk_fuel_meu, uf * rod_rbmk, wst * rod_rbmk * 21.5F);
		registerRBMKRod(rbmk_fuel_heu233, u233 * rod_rbmk, wst * rod_rbmk * 31F);
		registerRBMKRod(rbmk_fuel_heu235, u235 * rod_rbmk, wst * rod_rbmk * 30F);
		registerRBMKRod(rbmk_fuel_thmeu, thf * rod_rbmk, wst * rod_rbmk * 17.5F);
		registerRBMKRod(rbmk_fuel_lep, puf * rod_rbmk, wst * rod_rbmk * 25F);
		registerRBMKRod(rbmk_fuel_mep, purg * rod_rbmk, wst * rod_rbmk * 30F);
		registerRBMKRod(rbmk_fuel_hep239, pu239 * rod_rbmk, wst * rod_rbmk * 32.5F);
		registerRBMKRod(rbmk_fuel_hep241, pu241 * rod_rbmk, wst * rod_rbmk * 35F);
		registerRBMKRod(rbmk_fuel_lea, amf * rod_rbmk, wst * rod_rbmk * 26F);
		registerRBMKRod(rbmk_fuel_mea, amrg * rod_rbmk, wst * rod_rbmk * 30.5F);
		registerRBMKRod(rbmk_fuel_hea241, am241 * rod_rbmk, wst * rod_rbmk * 33.5F);
		registerRBMKRod(rbmk_fuel_hea242, am242 * rod_rbmk, wst * rod_rbmk * 34F);
		registerRBMKRod(rbmk_fuel_men, npf * rod_rbmk, wst * rod_rbmk * 22.5F);
		registerRBMKRod(rbmk_fuel_hen, np237 * rod_rbmk, wst * rod_rbmk * 30F);
		registerRBMKRod(rbmk_fuel_mox, mox * rod_rbmk, wst * rod_rbmk * 25.5F);
		registerRBMKRod(rbmk_fuel_les, saf * rod_rbmk, wst * rod_rbmk * 24.5F);
		registerRBMKRod(rbmk_fuel_mes, saf * rod_rbmk, wst * rod_rbmk * 30F);
		registerRBMKRod(rbmk_fuel_hes, saf * rod_rbmk, wst * rod_rbmk * 50F);
		registerRBMKRod(rbmk_fuel_leaus, 0F, wst * rod_rbmk * 37.5F);
		registerRBMKRod(rbmk_fuel_heaus, 0F, wst * rod_rbmk * 32.5F);
		registerRBMKRod(rbmk_fuel_po210be, pobe * rod_rbmk, pobe * rod_rbmk * 0.1F, true);
		registerRBMKRod(rbmk_fuel_ra226be, rabe * rod_rbmk, rabe * rod_rbmk * 0.4F, true);
		registerRBMKRod(rbmk_fuel_pu238be, pube * rod_rbmk, wst * rod_rbmk * 2.5F);
		registerRBMKRod(rbmk_fuel_balefire_gold, au198 * rod_rbmk, bf * rod_rbmk * 0.5F, true);
		registerRBMKRod(rbmk_fuel_flashlead, pb209 * 1.25F * rod_rbmk, pb209 * nugget * 0.05F * rod_rbmk, true);
		registerRBMKRod(rbmk_fuel_balefire, bf * rod_rbmk, bf * rod_rbmk * 100F, true);
		registerRBMKRod(rbmk_fuel_zfb_bismuth, pu241 * rod_rbmk * 0.1F, wst * rod_rbmk * 5F);
		registerRBMKRod(rbmk_fuel_zfb_pu241, pu239 * rod_rbmk * 0.1F, wst * rod_rbmk * 7.5F);
		registerRBMKRod(rbmk_fuel_zfb_am_mix, pu241 * rod_rbmk * 0.1F, wst * rod_rbmk * 10F);
		registerRBMK(rbmk_fuel_drx, bf * rod_rbmk, bf * rod_rbmk * 100F, true, true, 0, 1F/3F);
		
		registerRBMKPellet(rbmk_pellet_ueu, u * billet, wst * billet * 20F);
		registerRBMKPellet(rbmk_pellet_meu, uf * billet, wst * billet * 21.5F);
		registerRBMKPellet(rbmk_pellet_heu233, u233 * billet, wst * billet * 31F);
		registerRBMKPellet(rbmk_pellet_heu235, u235 * billet, wst * billet * 30F);
		registerRBMKPellet(rbmk_pellet_thmeu, thf * billet, wst * billet * 17.5F);
		registerRBMKPellet(rbmk_pellet_lep, puf * billet, wst * billet * 25F);
		registerRBMKPellet(rbmk_pellet_mep, purg * billet, wst * billet * 30F);
		registerRBMKPellet(rbmk_pellet_hep239, pu239 * billet, wst * billet * 32.5F);
		registerRBMKPellet(rbmk_pellet_hep241, pu241 * billet, wst * billet * 35F);
		registerRBMKPellet(rbmk_pellet_lea, amf * billet, wst * billet * 26F);
		registerRBMKPellet(rbmk_pellet_mea, amrg * billet, wst * billet * 30.5F);
		registerRBMKPellet(rbmk_pellet_hea241, am241 * billet, wst * billet * 33.5F);
		registerRBMKPellet(rbmk_pellet_hea242, am242 * billet, wst * billet * 34F);
		registerRBMKPellet(rbmk_pellet_men, npf * billet, wst * billet * 22.5F);
		registerRBMKPellet(rbmk_pellet_hen, np237 * billet, wst * billet * 30F);
		registerRBMKPellet(rbmk_pellet_mox, mox * billet, wst * billet * 25.5F);
		registerRBMKPellet(rbmk_pellet_les, saf * billet, wst * billet * 24.5F);
		registerRBMKPellet(rbmk_pellet_mes, saf * billet, wst * billet * 30F);
		registerRBMKPellet(rbmk_pellet_hes, saf * billet, wst * billet * 50F);
		registerRBMKPellet(rbmk_pellet_leaus, 0F, wst * billet * 37.5F);
		registerRBMKPellet(rbmk_pellet_heaus, 0F, wst * billet * 32.5F);
		registerRBMKPellet(rbmk_pellet_po210be, pobe * billet, pobe * billet * 0.1F, true);
		registerRBMKPellet(rbmk_pellet_ra226be, rabe * billet, rabe * billet * 0.4F, true);
		registerRBMKPellet(rbmk_pellet_pu238be, pube * billet, wst * 1.5F);
		registerRBMKPellet(rbmk_pellet_balefire_gold, au198 * billet, bf * billet * 0.5F, true);
		registerRBMKPellet(rbmk_pellet_flashlead, pb209 * 1.25F * billet, pb209 * nugget * 0.05F, true);
		registerRBMKPellet(rbmk_pellet_balefire, bf * billet, bf * billet * 100F, true);
		registerRBMKPellet(rbmk_pellet_zfb_bismuth, pu241 * billet * 0.1F, wst * billet * 5F);
		registerRBMKPellet(rbmk_pellet_zfb_pu241, pu239 * billet * 0.1F, wst * billet * 7.5F);
		registerRBMKPellet(rbmk_pellet_zfb_am_mix, pu241 * billet * 0.1F, wst * billet * 10F);
		registerRBMKPellet(rbmk_pellet_drx, bf * billet, bf * billet * 100F, true, 0F, 1F/24F);
		
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.SCHRABIDIUM), makeData(RADIATION, sa326 * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.HES), makeData(RADIATION, saf * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.MES), makeData(RADIATION, saf * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.LES), makeData(RADIATION, saf * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.HEN), makeData(RADIATION, np237 * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.MEU), makeData(RADIATION, uf * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.MEP), makeData(RADIATION, purg * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.DU), makeData(RADIATION, u238 * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.NQD), makeData(RADIATION, u235 * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.NQR), makeData(RADIATION, pu239 * ingot * 4));

		registerPWRFuel(EnumPWRFuel.MEU, uf * billet * 2);
		registerPWRFuel(EnumPWRFuel.HEU233, u233 * billet * 2);
		registerPWRFuel(EnumPWRFuel.HEU235, u235 * billet * 2);
		registerPWRFuel(EnumPWRFuel.MEN, npf * billet * 2);
		registerPWRFuel(EnumPWRFuel.HEN237, np237 * billet * 2);
		registerPWRFuel(EnumPWRFuel.MOX, mox * billet * 2);
		registerPWRFuel(EnumPWRFuel.MEP, purg * billet * 2);
		registerPWRFuel(EnumPWRFuel.HEP239, pu239 * billet * 2);
		registerPWRFuel(EnumPWRFuel.HEP241, pu241 * billet * 2);
		registerPWRFuel(EnumPWRFuel.MEA, amrg * billet * 2);
		registerPWRFuel(EnumPWRFuel.HEA242, am242 * billet * 2);
		registerPWRFuel(EnumPWRFuel.HES326, sa326 * billet * 2);
		registerPWRFuel(EnumPWRFuel.HES327, sa327 * billet * 2);
		registerPWRFuel(EnumPWRFuel.BFB_AM_MIX, amrg * billet);
		registerPWRFuel(EnumPWRFuel.BFB_PU241, pu241 * billet);
		
		HazardSystem.register(powder_yellowcake, makeData(RADIATION, yc * powder));
		HazardSystem.register(block_yellowcake, makeData(RADIATION, yc * block * powder_mult));
		HazardSystem.register(ModItems.fallout, makeData(RADIATION, fo * powder));
		HazardSystem.register(ModBlocks.fallout, makeData(RADIATION, fo * powder * 2));
		HazardSystem.register(ModBlocks.block_fallout, makeData(RADIATION, yc * block * powder_mult));
		HazardSystem.register(powder_caesium, makeData().addEntry(HYDROACTIVE, 1F).addEntry(HOT, 3F));
		
		HazardSystem.register(brick_asbestos, makeData(ASBESTOS, 1F));
		HazardSystem.register(tile_lab_broken, makeData(ASBESTOS, 1F));
		HazardSystem.register(powder_coltan_ore, makeData(ASBESTOS, 3F));
		
		//crystals
		HazardSystem.register(crystal_uranium, makeData(RADIATION, u * crystal));
		HazardSystem.register(crystal_thorium, makeData(RADIATION, th232 * crystal));
		HazardSystem.register(crystal_plutonium, makeData(RADIATION, pu * crystal));
		HazardSystem.register(crystal_schraranium, makeData(RADIATION, sr * crystal));
		HazardSystem.register(crystal_schrabidium, makeData(RADIATION, sa326 * crystal));
		HazardSystem.register(crystal_phosphorus, makeData(HOT, 2F * crystal));
		HazardSystem.register(crystal_lithium, makeData(HYDROACTIVE, 1F * crystal));
		HazardSystem.register(ModItems.crystal_trixite, makeData(RADIATION, trx * crystal));
		
		//nuke parts
		HazardSystem.register(boy_propellant, makeData(EXPLOSIVE, 2F));
		
		HazardSystem.register(gadget_core, makeData(RADIATION, pu239 * nugget * 10));
		HazardSystem.register(boy_target, makeData(RADIATION, u235 * ingot * 2));
		HazardSystem.register(boy_bullet, makeData(RADIATION, u235 * ingot));
		HazardSystem.register(man_core, makeData(RADIATION, pu239 * nugget * 10));
		HazardSystem.register(mike_core, makeData(RADIATION, u238 * nugget * 10));
		HazardSystem.register(tsar_core, makeData(RADIATION, pu239 * nugget * 15));
		
		HazardSystem.register(fleija_propellant, makeData().addEntry(RADIATION, 15F).addEntry(EXPLOSIVE, 8F).addEntry(BLINDING, 50F));
		HazardSystem.register(fleija_core, makeData(RADIATION, 10F));
		
		HazardSystem.register(solinium_propellant, makeData(EXPLOSIVE, 10F));
		HazardSystem.register(solinium_core, makeData().addEntry(RADIATION, sa327 * nugget * 8).addEntry(BLINDING, 45F));

		HazardSystem.register(nuke_fstbmb, makeData(DIGAMMA, 0.01F));
		HazardSystem.register(DictFrame.fromOne(ModItems.holotape_image, EnumHoloImage.HOLO_RESTORED), makeData(DIGAMMA, 1F));
		HazardSystem.register(holotape_damaged, makeData(DIGAMMA, 1_000F));
		
		/*
		 * Blacklist
		 */
		for(String ore : TH232.all(MaterialShapes.ORE)) HazardSystem.blacklist(ore);
		for(String ore : U.all(MaterialShapes.ORE)) HazardSystem.blacklist(ore);

		
		/*
		 * ReC compat
		 */
		Item recWaste = Compat.tryLoadItem(Compat.MOD_REC, "reactorcraft_item_waste");
		if(recWaste != null) {
			for(ReikaIsotope i : ReikaIsotope.values()) {
				if(i.getRad() > 0) {
					HazardSystem.register(new ItemStack(recWaste, 1, i.ordinal()), makeData(RADIATION, i.getRad()));
				}
			}
		}
		
		if(Compat.isModLoaded(Compat.MOD_GT6)) {
			
			Object[][] data = new Object[][] {
				{"Naquadah", u},
				{"Naquadah-Enriched", u235},
				{"Naquadria", pu239},
			};
			
			for(MaterialShapes shape : MaterialShapes.allShapes) {
				if(!shape.noAutogen) for(String prefix : shape.prefixes) {
					for(Object[] o : data) {
						HazardSystem.register(prefix + o[0], new HazardData().setMutex(0b1).addEntry(new HazardEntry(RADIATION, (float) o[1] * shape.q(1) / MaterialShapes.INGOT.q(1))));
					}
				}
			}
		}
	}
	
	public static void registerTrafos() {
		HazardSystem.trafos.add(new HazardTransformerRadiationNBT());
		
		if(!(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSafeCrates))	HazardSystem.trafos.add(new HazardTransformerRadiationContainer());
		if(!(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSafeMEDrives))	HazardSystem.trafos.add(new HazardTransformerRadiationME());
	}
	
	private static HazardData makeData() { return new HazardData(); }
	private static HazardData makeData(HazardTypeBase hazard) { return new HazardData().addEntry(hazard); }
	private static HazardData makeData(HazardTypeBase hazard, float level) { return new HazardData().addEntry(hazard, level); }
	private static HazardData makeData(HazardTypeBase hazard, float level, boolean override) { return new HazardData().addEntry(hazard, level, override); }
	
	private static void registerPWRFuel(EnumPWRFuel fuel, float baseRad) {
		HazardSystem.register(DictFrame.fromOne(ModItems.pwr_fuel, fuel), makeData(RADIATION, baseRad));
		HazardSystem.register(DictFrame.fromOne(ModItems.pwr_fuel_hot, fuel), makeData(RADIATION, baseRad * 10).addEntry(HOT, 5));
		HazardSystem.register(DictFrame.fromOne(ModItems.pwr_fuel_depleted, fuel), makeData(RADIATION, baseRad * 10));
	}
	
	private static void registerRBMKPellet(Item pellet, float base, float dep) { registerRBMKPellet(pellet, base, dep, false, 0F, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, boolean linear) { registerRBMKPellet(pellet, base, dep, linear, 0F, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, boolean linear, float blinding, float digamma) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		if(digamma > 0) data.addEntry(new HazardEntry(DIGAMMA, digamma));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerRBMKRod(Item rod, float base, float dep) { registerRBMK(rod, base, dep, true, false, 0F, 0F); }
	private static void registerRBMKRod(Item rod, float base, float dep, float blinding) { registerRBMK(rod, base, dep, true, false, blinding, 0F); }
	private static void registerRBMKRod(Item rod, float base, float dep, boolean linear) { registerRBMK(rod, base, dep, true, linear, 0F, 0F); }
	
	private static void registerRBMK(Item rod, float base, float dep, boolean hot, boolean linear, float blinding, float digamma) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
		if(hot) data.addEntry(new HazardEntry(HOT, 0).addMod(new HazardModifierRBMKHot()));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		if(digamma > 0) data.addEntry(new HazardEntry(DIGAMMA, digamma));
		HazardSystem.register(rod, data);
	}
	
	private static void registerBreedingRodRadiation(BreedingRodType type, float base) {
		HazardSystem.register(new ItemStack(ModItems.rod, 1, type.ordinal()), makeData(RADIATION, base));
		HazardSystem.register(new ItemStack(ModItems.rod_dual, 1, type.ordinal()), makeData(RADIATION, base * rod_dual));
		HazardSystem.register(new ItemStack(ModItems.rod_quad, 1, type.ordinal()), makeData(RADIATION, base * rod_quad));
	}
	
	private static void registerOtherFuel(Item fuel, float base, float target, boolean blinding) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierFuelRadiation(target)));
		if(blinding)
			data.addEntry(BLINDING, 20F);
		HazardSystem.register(fuel, data);
	}
	
	private static void registerOtherFuel(Item fuel, int meta, float base, float target, boolean blinding) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierFuelRadiation(target)));
		if(blinding)
			data.addEntry(BLINDING, 20F);
		HazardSystem.register(new ItemStack(fuel, 1, meta), data);
	}
	
	private static void registerRTGPellet(Item pellet, float base, float target) { registerRTGPellet(pellet, base, target, 0, 0); }
	private static void registerRTGPellet(Item pellet, float base, float target, float hot) { registerRTGPellet(pellet, base, target, hot, 0); }
	
	private static void registerRTGPellet(Item pellet, float base, float target, float hot, float blinding) {
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRTGRadiation(target)));
		if(hot > 0) data.addEntry(new HazardEntry(HOT, hot));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerOtherWaste(Item waste, float base) {
		HazardSystem.register(new ItemStack(waste, 1, 0), makeData(RADIATION, base * 0.075F));
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base));
		data.addEntry(new HazardEntry(HOT, 5F));
		HazardSystem.register(new ItemStack(waste, 1, 1), data);
	}
	
	private static void registerRadSourceWaste(Item waste, float base) {
		HazardSystem.register(new ItemStack(waste, 1, 0), makeData(RADIATION, base));
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base));
		data.addEntry(new HazardEntry(HOT, 5F));
		HazardSystem.register(new ItemStack(waste, 1, 1), data);
	}
}
