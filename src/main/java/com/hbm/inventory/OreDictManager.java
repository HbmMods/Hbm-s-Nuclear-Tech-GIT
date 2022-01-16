package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;

//i love you
import static com.hbm.items.ModItems.*;
import static com.hbm.blocks.ModBlocks.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//the more i optimize this, the more it starts looking like gregtech
public class OreDictManager {

	/*
	 * Standard keys
	 */
	public static final String KEY_STICK = "stickWood";
	public static final String KEY_ANYGLASS = "blockGlass";
	public static final String KEY_CLEARGLASS = "blockGlassColorless";
	public static final String KEY_ANYPANE = "paneGlass";
	public static final String KEY_CLEARPANE = "paneGlassColorless";
	public static final String KEY_BRICK = "ingotBrick";
	public static final String KEY_NETHERBRICK = "ingotBrickNether";
	public static final String KEY_SLIME = "slimeball";
	public static final String KEY_LOG = "logWood";
	public static final String KEY_PLANKS = "plankWood";
	public static final String KEY_SLAB = "slabWood";
	public static final String KEY_LEAVES = "treeLeaves";
	public static final String KEY_SAPLING = "treeSapling";
	
	public static final String KEY_BLACK = "dyeBlack";
    public static final String KEY_RED = "dyeRed";
    public static final String KEY_GREEN = "dyeGreen";
    public static final String KEY_BROWN = "dyeBrown";
    public static final String KEY_BLUE = "dyeBlue";
    public static final String KEY_PURPLE = "dyePurple";
    public static final String KEY_CYAN = "dyeCyan";
    public static final String KEY_LIGHTGRAY = "dyeLightGray";
    public static final String KEY_GRAY = "dyeGray";
    public static final String KEY_PINK = "dyePink";
    public static final String KEY_LIME = "dyeLime";
    public static final String KEY_YELLOW = "dyeYellow";
    public static final String KEY_LIGHTBLUE = "dyeLightBlue";
    public static final String KEY_MAGENTA = "dyeMagenta";
    public static final String KEY_ORANGE = "dyeOrange";
    public static final String KEY_WHITE = "dyeWhite";
	
	/*
	 * PREFIXES
	 */
	public static final String NUGGET = "nugget";
	public static final String TINY = "tiny";
	public static final String INGOT = "ingot";
	public static final String DUSTTINY = "dustTiny";
	public static final String DUST = "dust";
	public static final String GEM = "gem";
	public static final String CRYSTAL = "crystal";
	public static final String PLATE = "plate";
	public static final String BILLET = "billet";
	public static final String BLOCK = "block";
	public static final String ORE = "ore";
	public static final String ORENETHER = "oreNether";

	/*
	 * MATERIALS
	 */
	/*
	 * VANILLA
	 */
	public static final DictFrame COAL = new DictFrame("Coal");
	public static final DictFrame IRON = new DictFrame("Iron");
	public static final DictFrame GOLD = new DictFrame("Gold");
	public static final DictFrame LAPIS = new DictFrame("Lapis");
	public static final DictFrame REDSTONE = new DictFrame("Redstone");
	public static final DictFrame NETHERQUARTZ = new DictFrame("NetherQuartz");
	public static final DictFrame DIAMOND = new DictFrame("Diamond");
	public static final DictFrame EMERALD = new DictFrame("Emerald");
	/*
	 * RADIOACTIVE
	 */
	public static final DictFrame U = new DictFrame("Uranium");
	public static final DictFrame U233 = new DictFrame("Uranium233", "U233");
	public static final DictFrame U235 = new DictFrame("Uranium235", "U235");
	public static final DictFrame U238 = new DictFrame("Uranium238", "U238");
	public static final DictFrame TH232 = new DictFrame("Thorium232", "Th232", "Thorium");
	public static final DictFrame PU = new DictFrame("Plutonium");
	public static final DictFrame PURG = new DictFrame("PlutoniumRG");
	public static final DictFrame PU238 = new DictFrame("Plutonium238", "Pu238");
	public static final DictFrame PU239 = new DictFrame("Plutonium239", "Pu239");
	public static final DictFrame PU240 = new DictFrame("Plutonium240", "Pu240");
	public static final DictFrame PU241 = new DictFrame("Plutonium241", "Pu241");
	public static final DictFrame AM241 = new DictFrame("Americium241", "Am241");
	public static final DictFrame AM242 = new DictFrame("Americium242", "Am242");
	public static final DictFrame AMRG = new DictFrame("AmericiumRG");
	public static final DictFrame NP237 = new DictFrame("Neptunium237", "Np237", "Neptunium");
	public static final DictFrame PO210 = new DictFrame("Polonium210", "Po210", "Polonium");
	public static final DictFrame TC99 = new DictFrame("Technetium99", "Tc99");
	public static final DictFrame RA226 = new DictFrame("Radium226", "Ra226");
	public static final DictFrame CO60 = new DictFrame("Cobalt60", "Co60");
	public static final DictFrame AU198 = new DictFrame("Gold198", "Au198");
	public static final DictFrame PB209 = new DictFrame("Lead209", "Pb209");
	public static final DictFrame SA326 = new DictFrame("Schrabidium");
	public static final DictFrame SA327 = new DictFrame("Solinium");
	public static final DictFrame SBD = new DictFrame("Schrabidate");
	public static final DictFrame SRN = new DictFrame("Schraranium");
	/*
	 * STABLE
	 */
	public static final DictFrame TI = new DictFrame("Titanium");
	public static final DictFrame CU = new DictFrame("Copper");
	public static final DictFrame MINGRADE = new DictFrame("Mingrade");
	public static final DictFrame ALLOY = new DictFrame("AdvancedAlloy");
	public static final DictFrame W = new DictFrame("Tungsten");
	public static final DictFrame AL = new DictFrame("Aluminum");
	public static final DictFrame STEEL = new DictFrame("Steel");
	public static final DictFrame TCALLOY = new DictFrame("TcAlloy");
	public static final DictFrame PB = new DictFrame("Lead");
	//public static final DictFrame BI = new DictFrame("Bismuth");
	public static final DictFrame TA = new DictFrame("Tantalum");
	public static final DictFrame COLTAN = new DictFrame("Coltan");
	public static final DictFrame NB = new DictFrame("Niobium");
	public static final DictFrame BE = new DictFrame("Beryllium");
	public static final DictFrame CO = new DictFrame("Cobalt");
	public static final DictFrame B = new DictFrame("Boron");
	public static final DictFrame GRAPHITE = new DictFrame("Graphite");
	public static final DictFrame DURA = new DictFrame("DuraSteel");
	public static final DictFrame POLYMER = new DictFrame("Polymer");
	public static final DictFrame BAKELITE = new DictFrame("Bakelite");
	public static final DictFrame MAGTUNG = new DictFrame("MagnetizedTungsten");
	public static final DictFrame CMB = new DictFrame("CMBSteel");
	public static final DictFrame DESH = new DictFrame("WorkersAlloy");
	public static final DictFrame STAR = new DictFrame("Starmetal");
	public static final DictFrame BIGMT = new DictFrame("Saturnite");
	public static final DictFrame EUPH = new DictFrame("Euphemium");
	public static final DictFrame DNT = new DictFrame("Dineutronium");
	public static final DictFrame FIBER = new DictFrame("Fiberglass");
	public static final DictFrame ASBESTOS = new DictFrame("Asbestos");
	public static final DictFrame OSMIRIDIUM = new DictFrame("Osmiridium");
	/*
	 * DUST AND GEM ORES
	 */
	public static final DictFrame S = new DictFrame("Sulfur");
	public static final DictFrame KNO = new DictFrame("Saltpeter");
	public static final DictFrame F = new DictFrame("Fluorite");
	public static final DictFrame LIGNITE = new DictFrame("Lignite");
	public static final DictFrame CINNABAR = new DictFrame("Cinnabar");
	public static final DictFrame BORAX = new DictFrame("Borax");
	public static final DictFrame VOLCANIC = new DictFrame("Volcanic");
	/*
	 * HAZARDS, MISC
	 */
	public static final DictFrame LI = new DictFrame("Lithium");
	/*
	 * PHOSPHORUS
	 */
	public static final DictFrame P_WHITE = new DictFrame("WhitePhosphorus");
	public static final DictFrame P_RED = new DictFrame("RedPhosphorus");
	/*
	 * RARE METALS
	 */
	public static final DictFrame AUSTRALIUM = new DictFrame("Australium");
	public static final DictFrame REIIUM = new DictFrame("Reiium");
	public static final DictFrame WEIDANIUM = new DictFrame("Weidanium");
	public static final DictFrame UNOBTAINIUM = new DictFrame("Unobtainium");
	public static final DictFrame VERTICIUM = new DictFrame("Verticium");
	public static final DictFrame DAFFERGON = new DictFrame("Daffergon");
	/*
	 * RARE EARTHS
	 */
	public static final DictFrame LA = new DictFrame("Lanthanum");
	public static final DictFrame AC = new DictFrame("Actinium");
	public static final DictFrame ZR = new DictFrame("Zirconium");
	public static final DictFrame ND = new DictFrame("Neodymium");
	public static final DictFrame CE = new DictFrame("Cerium");
	/*
	 * NITAN
	 */
	public static final DictFrame I = new DictFrame("Iodine");
	public static final DictFrame AT = new DictFrame("Astatine");
	public static final DictFrame CS = new DictFrame("Caesium");
	public static final DictFrame ST = new DictFrame("Strontium");
	public static final DictFrame BR = new DictFrame("Bromine");
	public static final DictFrame TS = new DictFrame("Tennessine") ;
	/*
	 * FISSION FRAGMENTS
	 */
	public static final DictFrame SR90 = new DictFrame("Strontium90", "Sr90");
	public static final DictFrame I131 = new DictFrame("Iodine131", "I131");
	public static final DictFrame XE135 = new DictFrame("Xenon135", "Xe135");
	public static final DictFrame CS137 = new DictFrame("Caesium137", "Cs137");
	public static final DictFrame AT209 = new DictFrame("Astatine209", "At209");
	
	/*
	 * COLLECTIONS
	 */
	public static final DictFrame ANY_PLASTIC = new DictFrame("AnyPlastic");
	public static final DictFrame ANY_GUNPOWDER = new DictFrame("AnyPropellant");
	public static final DictFrame ANY_SMOKELESS = new DictFrame("AnySmokeless");
	public static final String KEY_ANYCONCRETE = "anyConcrete";
	
	public static void registerOres() {

		/*
		 * VANILLA
		 */
		COAL.gem(Items.coal).dustSmall(powder_coal_tiny).dust(powder_coal);
		IRON.plate(plate_iron).dust(powder_iron).ore(ore_gneiss_iron);
		GOLD.plate(plate_gold).dust(powder_gold).ore(ore_gneiss_gold);
		LAPIS.dust(powder_lapis);
		NETHERQUARTZ.gem(Items.quartz).dust(powder_quartz);
		DIAMOND.dust(powder_diamond).ore(gravel_diamond);
		EMERALD.dust(powder_emerald);
		
		/*
		 * RADIOACTIVE
		 */
		U		.rad(HazardRegistry.u)						.nugget(nugget_uranium)		.billet(billet_uranium)		.ingot(ingot_uranium)		.dust(powder_uranium)									.block(block_uranium)		.ore(ore_uranium, ore_uranium_scorched, ore_gneiss_uranium, ore_gneiss_uranium_scorched, ore_nether_uranium, ore_nether_uranium_scorched, ore_meteor_uranium);
		U233	.rad(HazardRegistry.u233)					.nugget(nugget_u233)		.billet(billet_u233)		.ingot(ingot_u233)																	.block(block_u233);
		U235	.rad(HazardRegistry.u235)					.nugget(nugget_u235)		.billet(billet_u235)		.ingot(ingot_u235)																	.block(block_u235);
		U238	.rad(HazardRegistry.u238)					.nugget(nugget_u238)		.billet(billet_u238)		.ingot(ingot_u238)																	.block(block_u238);
		TH232	.rad(HazardRegistry.th232)					.nugget(nugget_th232)		.billet(billet_th232)		.ingot(ingot_th232)			.dust(powder_thorium)									.block(block_thorium)		.ore(ore_thorium, ore_meteor_thorium);
		PU		.rad(HazardRegistry.pu)						.nugget(nugget_plutonium)	.billet(billet_plutonium)	.ingot(ingot_plutonium)		.dust(powder_plutonium)									.block(block_plutonium)		.ore(ore_nether_plutonium);
		PURG	.rad(HazardRegistry.purg)					.nugget(nugget_pu_mix)		.billet(billet_pu_mix)		.ingot(ingot_pu_mix)																.block(block_pu_mix);
		PU238	.rad(HazardRegistry.pu238)	.hot(3F)		.nugget(nugget_pu238)		.billet(billet_pu238)		.ingot(ingot_pu238)																	.block(block_pu238);
		PU239	.rad(HazardRegistry.pu239)					.nugget(nugget_pu239)		.billet(billet_pu239)		.ingot(ingot_pu239)																	.block(block_pu239);
		PU240	.rad(HazardRegistry.pu240)					.nugget(nugget_pu240)		.billet(billet_pu240)		.ingot(ingot_pu240)																	.block(block_pu240);
		PU241	.rad(HazardRegistry.pu241)					.nugget(nugget_pu241)		.billet(billet_pu241)		.ingot(ingot_pu241);																//.block(block_pu241);
		AM241	.rad(HazardRegistry.am241)					.nugget(nugget_am241)		.billet(billet_am241)		.ingot(ingot_am241);
		AM242	.rad(HazardRegistry.am242)					.nugget(nugget_am242)		.billet(billet_am242)		.ingot(ingot_am242);
		AMRG	.rad(HazardRegistry.amrg)					.nugget(nugget_am_mix)		.billet(billet_am_mix)		.ingot(ingot_am_mix);
		NP237	.rad(HazardRegistry.np237)					.nugget(nugget_neptunium)	.billet(billet_neptunium)	.ingot(ingot_neptunium)		.dust(powder_neptunium)									.block(block_neptunium);
		PO210	.rad(HazardRegistry.po210)	.hot(3)			.nugget(nugget_polonium)	.billet(billet_polonium)	.ingot(ingot_polonium)		.dust(powder_polonium)									.block(block_polonium);
		TC99	.rad(HazardRegistry.tc99)					.nugget(nugget_technetium)	.billet(billet_technetium)	.ingot(ingot_technetium);
		RA226	.rad(HazardRegistry.ra226)					.nugget(nugget_ra226)		.billet(billet_ra226)		.ingot(ingot_ra226)			.dust(powder_ra226)										.block(block_ra226);
		CO60	.rad(HazardRegistry.co60)	.hot(1)			.nugget(nugget_co60)		.billet(billet_co60)		.ingot(ingot_co60)			.dust(powder_co60);
		AU198	.rad(HazardRegistry.au198)	.hot(5)			.nugget(nugget_au198)		.billet(billet_au198)		.ingot(ingot_au198)			.dust(powder_au198);
		PB209	.rad(HazardRegistry.pb209)	.blinding(3F)	.hot(7)						.nugget(nugget_pb209)		.billet(billet_pb209)		.ingot(ingot_pb209);
		SA326	.rad(HazardRegistry.sa326)	.blinding(3F)	.nugget(nugget_schrabidium)	.billet(billet_schrabidium)	.ingot(ingot_schrabidium)	.dust(powder_schrabidium)	.plate(plate_schrabidium)	.block(block_schrabidium)	.ore(ore_schrabidium, ore_gneiss_schrabidium, ore_nether_schrabidium);
		SA327	.rad(HazardRegistry.sa327)	.blinding(3F)	.nugget(nugget_solinium)	.billet(billet_solinium)	.ingot(ingot_solinium)																.block(block_solinium);
		SBD		.rad(HazardRegistry.sb)		.blinding(1F)															.ingot(ingot_schrabidate)	.dust(powder_schrabidate)								.block(block_schrabidate);
		SRN		.rad(HazardRegistry.sr)		.blinding(1F)															.ingot(ingot_schraranium)															.block(block_schraranium);

		/*
		 * STABLE
		 */
		TI																	.ingot(ingot_titanium)												.dust(powder_titanium)			.plate(plate_titanium)			.block(block_titanium)		.ore(ore_titanium, ore_meteor_titanium);
		CU																	.ingot(ingot_copper)												.dust(powder_copper)			.plate(plate_copper)			.block(block_copper)		.ore(ore_copper, ore_gneiss_copper, ore_meteor_copper);
		MINGRADE															.ingot(ingot_red_copper)											.dust(powder_red_copper)										.block(block_red_copper);
		ALLOY																.ingot(ingot_advanced_alloy)										.dust(powder_advanced_alloy)	.plate(plate_advanced_alloy)	.block(block_advanced_alloy);
		W																	.ingot(ingot_tungsten)												.dust(powder_tungsten)											.block(block_tungsten)		.ore(ore_tungsten, ore_nether_tungsten, ore_meteor_tungsten);
		AL																	.ingot(ingot_aluminium)												.dust(powder_aluminium)			.plate(plate_aluminium)			.block(block_aluminium)		.ore(ore_aluminium, ore_meteor_aluminium);
		STEEL																.ingot(ingot_steel)				.dustSmall(powder_steel_tiny)		.dust(powder_steel)				.plate(plate_steel)				.block(block_steel);
		TCALLOY																.ingot(ingot_tcalloy)												.dust(powder_tcalloy);
		PB			.nugget(nugget_lead)									.ingot(ingot_lead)													.dust(powder_lead)				.plate(plate_lead)				.block(block_lead)			.ore(ore_lead, ore_meteor_lead);
		//BI		.nugget(nugget_bismuth)									.ingot(ingot_bismuth); THAT'S WHAT YOU THOUGHT!
		TA			.nugget(nugget_tantalium)	.gem(gem_tantalium)			.ingot(ingot_tantalium)												.dust(powder_tantalium)											.block(block_tantalium);
		COLTAN																.ingot(fragment_coltan)												.dust(powder_coltan_ore)										.block(block_coltan)		.ore(ore_coltan);
		NB			.nugget(fragment_niobium)								.ingot(ingot_niobium)			.dustSmall(powder_niobium_tiny)		.dust(powder_niobium)											.block(block_niobium);
		BE			.nugget(nugget_beryllium)	.billet(billet_beryllium)	.ingot(ingot_beryllium)												.dust(powder_beryllium)											.block(block_beryllium)		.ore(ore_beryllium);
		CO			.nugget(fragment_cobalt)	.nugget(nugget_cobalt)		.billet(billet_cobalt)			.ingot(ingot_cobalt)				.dust(powder_cobalt)			.dustSmall(powder_cobalt_tiny)	.block(block_cobalt)		.ore(ore_cobalt, ore_nether_cobalt);
		B			.nugget(fragment_boron)									.ingot(ingot_boron)				.dustSmall(powder_boron_tiny)		.dust(powder_boron)												.block(block_boron);
		GRAPHITE															.ingot(ingot_graphite)																												.block(block_graphite);
		DURA																.ingot(ingot_dura_steel)											.dust(powder_dura_steel)										.block(block_dura_steel);
		POLYMER																.ingot(ingot_polymer)												.dust(powder_polymer);
		BAKELITE															.ingot(ingot_bakelite);
		MAGTUNG																.ingot(ingot_magnetized_tungsten)									.dust(powder_magnetized_tungsten)								.block(block_magnetized_tungsten);
		CMB																	.ingot(ingot_combine_steel)											.dust(powder_combine_steel)		.plate(plate_combine_steel)		.block(block_combine_steel);
		DESH		.nugget(nugget_desh)									.ingot(ingot_desh)													.dust(powder_desh)												.block(block_desh);
		STAR																.ingot(ingot_starmetal)																												.block(block_starmetal)		.ore(ore_meteor_starmetal);
		BIGMT																.ingot(ingot_saturnite)																				.plate(plate_saturnite);
		EUPH		.nugget(nugget_euphemium)								.ingot(ingot_euphemium)												.dust(powder_euphemium)											.block(block_euphemium);
		DNT			.nugget(nugget_dineutronium)							.ingot(ingot_dineutronium)											.dust(powder_dineutronium)										.block(block_dineutronium);
		FIBER																.ingot(ingot_fiberglass)																											.block(block_fiberglass);
		ASBESTOS	.asbestos(1F)											.ingot(ingot_asbestos)												.dust(powder_asbestos)											.block(block_asbestos)		.ore(ore_asbestos, ore_gneiss_asbestos, basalt_asbestos);
		OSMIRIDIUM	.nugget(nugget_osmiridium)								.ingot(ingot_osmiridium);

		/*
		 * DUST AND GEM ORES
		 */
		S													.dust(sulfur)			.block(block_sulfur)	.ore(ore_sulfur, ore_nether_sulfur, basalt_sulfur, ore_meteor_sulfur);
		KNO													.dust(niter)			.block(block_niter)		.ore(ore_niter);
		F													.dust(fluorite)			.block(block_fluorite)	.ore(ore_fluorite, basalt_fluorite);
		LIGNITE							.gem(lignite)		.dust(powder_lignite)							.ore(ore_lignite);
		CINNABAR	.crystal(cinnebar)	.gem(cinnebar)														.ore(ore_cinnebar, ore_depth_cinnebar);
		BORAX												.dust(powder_borax)								.ore(ore_depth_borax);
		VOLCANIC						.gem(gem_volcanic)													.ore(basalt_gem);
		
		/*
		 * HAZARDS, MISC
		 */
		LI	.hydro(1F)	.ingot(lithium)	.dustSmall(powder_lithium_tiny)	.dust(powder_lithium)	.block(block_lithium)	.ore(ore_gneiss_lithium, ore_meteor_lithium);

		/*
		 * PHOSPHORUS
		 */
		P_WHITE	.hot(5)	.ingot(ingot_phosphorus)	.block(block_white_phosphorus);
		P_RED	.hot(2)	.dust(powder_fire)			.block(block_red_phosphorus);
		
		/*
		 * RARE METALS
		 */
		AUSTRALIUM	.nugget(nugget_australium)	.billet(billet_australium)	.ingot(ingot_australium)	.dust(powder_australium)	.block(block_australium)	.ore(ore_australium);
		REIIUM		.nugget(nugget_reiium)									.ingot(ingot_reiium)		.dust(powder_reiium)		.block(block_reiium)		.ore(ore_reiium);
		WEIDANIUM	.nugget(nugget_weidanium)								.ingot(ingot_weidanium)		.dust(powder_weidanium)		.block(block_weidanium)		.ore(ore_weidanium);
		UNOBTAINIUM	.nugget(nugget_unobtainium)								.ingot(ingot_unobtainium)	.dust(powder_unobtainium)	.block(block_unobtainium)	.ore(ore_unobtainium);
		VERTICIUM	.nugget(nugget_verticium)								.ingot(ingot_verticium)		.dust(powder_verticium)		.block(block_verticium)		.ore(ore_verticium);
		DAFFERGON	.nugget(nugget_daffergon)								.ingot(ingot_daffergon)		.dust(powder_daffergon)		.block(block_daffergon)		.ore(ore_daffergon);

		/*
		 * RARE EARTHS
		 */
		LA	.nugget(fragment_lanthanium)	.ingot(ingot_lanthanium)										.dustSmall(powder_lanthanium_tiny)	.dust(powder_lanthanium)	.block(block_lanthanium);
		AC	.nugget(fragment_actinium)		.ingot(ingot_actinium)											.dustSmall(powder_actinium_tiny)	.dust(powder_actinium)		.block(block_actinium);
		ZR	.nugget(nugget_zirconium)		.ingot(ingot_zirconium)		.billet(billet_zirconium)												.dust(powder_zirconium)		.block(block_zirconium)		.ore(ore_depth_zirconium);
		ND	.nugget(fragment_neodymium)																		.dustSmall(powder_neodymium_tiny)	.dust(powder_neodymium)									.ore(ore_depth_nether_neodymium);
		CE	.nugget(fragment_cerium)																		.dustSmall(powder_cerium_tiny)		.dust(powder_cerium);
		
		/*
		 * NITAN
		 */
		I	.dust(powder_iodine);
		AT	.dust(powder_astatine);
		CS	.dust(powder_caesium);
		ST	.dust(powder_strontium);
		BR	.dust(powder_bromine);
		TS	.dust(powder_tennessine);

		/*
		 * FISSION FRAGMENTS
		 */
		SR90	.rad(HazardRegistry.sr90)	.hot(1F)	.hydro(1F)	.dustSmall(powder_sr90_tiny)	.dust(powder_sr90)	.ingot(ingot_sr90)	.billet(billet_sr90)	.nugget(nugget_sr90);
		I131	.rad(HazardRegistry.i131)	.hot(1F)				.dustSmall(powder_i131_tiny)	.dust(powder_i131);
		XE135	.rad(HazardRegistry.xe135)	.hot(10F)				.dustSmall(powder_xe135_tiny)	.dust(powder_xe135);
		CS137	.rad(HazardRegistry.cs137)	.hot(3F)	.hydro(3F)	.dustSmall(powder_cs137_tiny)	.dust(powder_cs137);
		AT209	.rad(HazardRegistry.at209)	.hot(20F)												.dust(powder_at209);
		
		/*
		 * COLLECTIONS
		 */
		ANY_PLASTIC		.ingot(ModItems.ingot_polymer, ModItems.ingot_bakelite).dust(ModItems.powder_polymer);
		ANY_GUNPOWDER	.dust(Items.gunpowder, ModItems.ballistite, ModItems.cordite);
		ANY_SMOKELESS	.dust(ModItems.ballistite, ModItems.cordite);

		OreDictionary.registerOre(KEY_ANYCONCRETE, ModBlocks.concrete);
		OreDictionary.registerOre(KEY_ANYCONCRETE, ModBlocks.concrete_smooth);
		OreDictionary.registerOre(KEY_ANYCONCRETE, ModBlocks.concrete_asbestos);
		
		OreDictionary.registerOre(getReflector(), neutron_reflector);
		OreDictionary.registerOre("oreRareEarth", ore_rare);
		OreDictionary.registerOre("oreRareEarth", ore_gneiss_rare);

		OreDictionary.registerOre("logWood", pink_log);
		OreDictionary.registerOre("logWoodPink", pink_log);
		OreDictionary.registerOre("plankWood", pink_planks);
		OreDictionary.registerOre("plankWoodPink", pink_planks);
		OreDictionary.registerOre("slabWood", pink_slab);
		OreDictionary.registerOre("slabWoodPink", pink_slab);
		OreDictionary.registerOre("stairWood", pink_stairs);
		OreDictionary.registerOre("stairWoodPink", pink_stairs);

		OreDictionary.registerOre("dyeRed", cinnebar);
		//OreDictionary.registerOre("dye", cinnebar);
		OreDictionary.registerOre("dyeYellow", sulfur);
		//OreDictionary.registerOre("dye", sulfur);
		OreDictionary.registerOre("dyeBlack", powder_coal);
		//OreDictionary.registerOre("dye", powder_coal);
		OreDictionary.registerOre("dyeBrown", powder_lignite);
		//OreDictionary.registerOre("dye", powder_lignite);
		OreDictionary.registerOre("dyeLightGray", powder_titanium);
		//OreDictionary.registerOre("dye", powder_titanium);
		OreDictionary.registerOre("dyeWhite", fluorite);
		//OreDictionary.registerOre("dye", fluorite);
		OreDictionary.registerOre("dyeBlue", powder_lapis);
		//OreDictionary.registerOre("dye", powder_lapis);
		OreDictionary.registerOre("dyeBlack", oil_tar);
		//OreDictionary.registerOre("dye", oil_tar);

		OreDictionary.registerOre("blockGlass", glass_boron);
		OreDictionary.registerOre("blockGlass", glass_lead);
		OreDictionary.registerOre("blockGlass", glass_uranium);
		OreDictionary.registerOre("blockGlass", glass_trinitite);
		OreDictionary.registerOre("blockGlass", glass_polonium);
		OreDictionary.registerOre("blockGlass", glass_ash);
		OreDictionary.registerOre("blockGlassYellow", glass_uranium);
		OreDictionary.registerOre("blockGlassLime", glass_trinitite);
		OreDictionary.registerOre("blockGlassRed", glass_polonium);
		OreDictionary.registerOre("blockGlassBlack", glass_ash);
	}
	
	public static String getReflector() {
		return GeneralConfig.enableReflectorCompat ? "plateDenseLead" : "plateTungCar"; //let's just mangle the name into "tungCar" so that it can't conflict with anything ever
	}
	
	public static class DictFrame {
		String[] mats;
		float hazMult = 1.0F;
		List<HazardEntry> hazards = new ArrayList();
		
		public DictFrame(String... mats) {
			this.mats = mats;
		}

		/*
		 * Quick access methods to grab ore names for recipes.
		 */
		public String nugget() {	return NUGGET		+ mats[0]; }
		public String tiny() {		return TINY		+ mats[0]; }
		public String ingot() {		return INGOT		+ mats[0]; }
		public String dustTiny() {	return DUSTTINY	+ mats[0]; }
		public String dust() {		return DUST		+ mats[0]; }
		public String gem() {		return GEM		+ mats[0]; }
		public String crystal() {	return CRYSTAL	+ mats[0]; }
		public String plate() {		return PLATE		+ mats[0]; }
		public String billet() {	return BILLET		+ mats[0]; }
		public String block() {		return BLOCK		+ mats[0]; }
		public String ore() {		return ORE		+ mats[0]; }
		public String[] nuggets() {		return appendToAll(NUGGET); }
		public String[] tinys() {		return appendToAll(TINY); }
		public String[] allNuggets() {		return appendToAll(NUGGET, TINY); }
		public String[] ingots() {		return appendToAll(INGOT); }
		public String[] dustTinys() {	return appendToAll(DUSTTINY); }
		public String[] dusts() {		return appendToAll(DUST); }
		public String[] gems() {		return appendToAll(GEM); }
		public String[] crystals() {	return appendToAll(CRYSTAL); }
		public String[] plates() {		return appendToAll(PLATE); }
		public String[] billets() {		return appendToAll(BILLET); }
		public String[] blocks() {		return appendToAll(BLOCK); }
		public String[] ores() {		return appendToAll(ORE); }
		
		private String[] appendToAll(String... prefix) {
			
			String[] names = new String[mats.length * prefix.length];
			
			for(int i = 0; i < mats.length; i++) {
				for(int j = 0; j < prefix.length; j++) {
					names[i * prefix.length + j] = prefix[j] + mats[i];
				}
			}
			return names;
		}

		public DictFrame rad(float rad) {		return this.haz(new HazardEntry(HazardRegistry.RADIATION, rad)); }
		public DictFrame hot(float time) {		return this.haz(new HazardEntry(HazardRegistry.HOT, time)); }
		public DictFrame blinding(float time) {	return this.haz(new HazardEntry(HazardRegistry.BLINDING, time)); }
		public DictFrame asbestos(float asb) {	return this.haz(new HazardEntry(HazardRegistry.ASBESTOS, asb)); }
		public DictFrame hydro(float h) {		return this.haz(new HazardEntry(HazardRegistry.HYDROACTIVE, h)); }
		
		public DictFrame haz(HazardEntry hazard) {
			hazards.add(hazard);
			return this;
		}
		
		public DictFrame nugget(Item... nugget) {
			hazMult = HazardRegistry.nugget;
			return makeItem(NUGGET, nugget).makeItem(TINY, nugget);
		}
		public DictFrame ingot(Item... ingot) {
			hazMult = HazardRegistry.ingot;
			return makeItem(INGOT, ingot);
		}
		public DictFrame dustSmall(Item... dustSmall) {
			hazMult = HazardRegistry.powder_tiny;
			return makeItem(DUSTTINY, dustSmall);
		}
		public DictFrame dust(Item... dust) {
			hazMult = HazardRegistry.powder;
			return makeItem(DUST, dust);
		}
		public DictFrame gem(Item... gem) {
			hazMult = HazardRegistry.gem;
			return makeItem(GEM, gem);
		}
		public DictFrame crystal(Item... crystal) {
			hazMult = HazardRegistry.gem;
			return makeItem(CRYSTAL, crystal);
		}
		public DictFrame plate(Item... plate) {
			hazMult = HazardRegistry.plate;
			return makeItem(PLATE, plate);
		}
		public DictFrame billet(Item... billet) {
			hazMult = HazardRegistry.billet;
			return makeItem(BILLET, billet);
		}
		
		public DictFrame block(Block... block) {
			hazMult = HazardRegistry.block;
			return makeBlocks(BLOCK, block);
		}
		public DictFrame ore(Block... ore) {
			hazMult = HazardRegistry.ore;
			return makeBlocks(ORE, ore);
		}
		public DictFrame oreNether(Block... oreNether) {
			hazMult = HazardRegistry.ore;
			return makeBlocks(ORENETHER, oreNether);
		}

		public DictFrame makeItem(String tag, Item... items) {
			for(Item i : items) registerStack(tag, new ItemStack(i));
			return this;
		}
		public DictFrame makeBlocks(String tag, Block... blocks) {
			for(Block b : blocks) registerStack(tag, new ItemStack(b));
			return this;
		}
		
		public void registerStack(String tag, ItemStack stack) {
			for(String mat : mats) {
				OreDictionary.registerOre(tag + mat, stack);
				
				if(!hazards.isEmpty() && hazMult > 0F) {
					HazardData data = new HazardData().setMutex(0b1);
					
					for(HazardEntry hazard : hazards) {
						data.addEntry(hazard.clone(this.hazMult));
					}
					
					HazardSystem.register(tag + mat, data);
				}
			}
		}
	}
}
