package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;

//i love you
import static com.hbm.items.ModItems.*;
import static com.hbm.blocks.ModBlocks.*;

import com.hbm.config.GeneralConfig;
import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.hazard.type.HazardTypeBase;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.RecipesCommon.*;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictManager
{
	public static final HashMap<String, Integer> sizeMap = new HashMap<String, Integer>();
	static
	{
		sizeMap.put("nugget", 1);
		sizeMap.put("tiny", 1);
		sizeMap.put("dustSmall", 1);
		sizeMap.put("dustTiny", 1);
		sizeMap.put("ingot", 9);
		sizeMap.put("plate", 9);
		sizeMap.put("gem", 9);
		sizeMap.put("dust", 9);
		sizeMap.put("material", 9);
		sizeMap.put("block", 81);
	}
	public static void registerOres()
	{

		/*
		 * VANILLA
		 */
		new DictFrame("Coal").gem(Items.coal).dustSmall(powder_coal_tiny).dust(powder_coal);
		new DictFrame("Iron").plate(plate_iron).dust(powder_iron).ore(ore_gneiss_iron);
		new DictFrame("Gold").plate(plate_gold).dust(powder_gold).ore(ore_gneiss_gold);
		new DictFrame("Lapis").dust(powder_lapis);
		new DictFrame("NetherQuartz", "Quartz").gem(Items.quartz).dust(powder_quartz);
		new DictFrame("Diamond").dust(powder_diamond).ore(gravel_diamond);
		new DictFrame("Emerald").dust(powder_emerald);
		
		/*
		 * RADIOACTIVE
		 */
		new DictFrame("Trinitite")							.rad(HazardRegistry.trn)																		.ingot(trinitite)																	.block(block_trinitite)		.ore(waste_trinitite, waste_trinitite_red);
		new DictFrame("Yellowcake")							.rad(HazardRegistry.yc)																										.dust(powder_yellowcake)								.block(block_yellowcake);
		new DictFrame("Fallout")							.rad(HazardRegistry.fo)																										.dust(ModItems.fallout)									.block(block_fallout);
//		new DictFrame("NuclearWaste")						.rad(HazardRegistry.wst)				.nugget(nuclear_waste_tiny)	.billet(billet_nuclear_waste).ingot(nuclear_waste)																.block(block_waste, block_waste_painted);
//		new DictFrame("NuclearWasteVitrified")				.rad(HazardRegistry.wstv)				.nugget(nuclear_waste_vitrified_tiny)					.ingot(nuclear_waste_vitrified)														.block(block_waste_vitrified);
		new DictFrame("Schraranium")						.rad(HazardRegistry.sr)																			.ingot(ingot_schraranium)															.block(block_schraranium).crystal(crystal_schraranium);
		
		new DictFrame("Actinium", "Actinium227", "Ac227")	.rad(HazardRegistry.ac227).hot(7.5F)	.nugget(nugget_actinium, fragment_actinium).billet(billet_actinium).ingot(ingot_actinium).dust(powder_actinium).dustSmall(powder_actinium_tiny).block(block_actinium);
		new DictFrame("Uranium")							.rad(HazardRegistry.u)					.nugget(nugget_uranium)		.billet(billet_uranium)		.ingot(ingot_uranium)		.dust(powder_uranium)									.block(block_uranium)		.ore(ore_uranium, ore_uranium_scorched, ore_gneiss_uranium, ore_gneiss_uranium_scorched, ore_nether_uranium, ore_nether_uranium_scorched, ore_meteor_uranium).crystal(crystal_uranium);
		new DictFrame("Uranium233", "U233")					.rad(HazardRegistry.u233)				.nugget(nugget_u233)		.billet(billet_u233)		.ingot(ingot_u233)																	.block(block_u233);
		new DictFrame("Uranium234", "U234")					.rad(HazardRegistry.u234)				.nugget(nugget_u234)		.billet(billet_u234)		.ingot(ingot_u234);
		new DictFrame("Uranium235", "U235")					.rad(HazardRegistry.u235)				.nugget(nugget_u235)		.billet(billet_u235)		.ingot(ingot_u235)			.dust(powder_u235)										.block(block_u235);
		new DictFrame("Uranium238", "U238")					.rad(HazardRegistry.u238)				.nugget(nugget_u238)		.billet(billet_u238)		.ingot(ingot_u238)			.dust(powder_u238)										.block(block_u238);
		new DictFrame("Thorium232", "Th232", "Thorium")		.rad(HazardRegistry.th232)				.nugget(nugget_th232)		.billet(billet_th232)		.ingot(ingot_th232)			.dust(powder_thorium)									.block(block_thorium)		.ore(ore_thorium, ore_meteor_thorium).crystal(crystal_thorium);
		new DictFrame("ThoriumActivated")					.rad(HazardRegistry.tha).hot(5)			.nugget(nugget_tha)			.billet(billet_tha)			.ingot(ingot_tha)																	.block(block_tha);
		new DictFrame("Plutonium")							.rad(HazardRegistry.pu)					.nugget(nugget_plutonium)	.billet(billet_plutonium)	.ingot(ingot_plutonium)		.dust(powder_plutonium)									.block(block_plutonium)		.ore(ore_nether_plutonium).crystal(crystal_plutonium);
		new DictFrame("PlutoniumRG")						.rad(HazardRegistry.purg)				.nugget(nugget_pu_mix)		.billet(billet_pu_mix)		.ingot(ingot_pu_mix)																.block(block_pu_mix);
		new DictFrame("Plutonium238", "Pu238")				.rad(HazardRegistry.pu238).hot(3F)		.nugget(nugget_pu238)		.billet(billet_pu238)		.ingot(ingot_pu238)																	.block(block_pu238);
		new DictFrame("Plutonium239", "Pu239")				.rad(HazardRegistry.pu239)				.nugget(nugget_pu239)		.billet(billet_pu239)		.ingot(ingot_pu239)			.dust(powder_pu239)										.block(block_pu239);
		new DictFrame("Plutonium240", "Pu240")				.rad(HazardRegistry.pu240)				.nugget(nugget_pu240)		.billet(billet_pu240)		.ingot(ingot_pu240)																	.block(block_pu240);
		new DictFrame("Plutonium241", "Pu241")				.rad(HazardRegistry.pu241)				.nugget(nugget_pu241)		.billet(billet_pu241)		.ingot(ingot_pu241);																//.block(block_pu241);
		new DictFrame("Americium241", "Am241")				.rad(HazardRegistry.am241)				.nugget(nugget_am241)		.billet(billet_am241)		.ingot(ingot_am241);
		new DictFrame("Americium242m", "Am242m")			.rad(HazardRegistry.am242m)				.nugget(nugget_am242)		.billet(billet_am242)		.ingot(ingot_am242);
		new DictFrame("AmericiumRG")						.rad(HazardRegistry.amrg)				.nugget(nugget_am_mix)		.billet(billet_am_mix)		.ingot(ingot_am_mix);
		new DictFrame("Neptunium237", "Np237", "Neptunium")	.rad(HazardRegistry.np237)				.nugget(nugget_neptunium)	.billet(billet_neptunium)	.ingot(ingot_neptunium)		.dust(powder_neptunium)									.block(block_neptunium);
		new DictFrame("Polonium210", "Po210", "Polonium")	.rad(HazardRegistry.po210).hot(3)		.nugget(nugget_polonium)	.billet(billet_polonium)	.ingot(ingot_polonium)		.dust(powder_polonium)									.block(block_polonium);
		new DictFrame("Technetium99", "Tc99")				.rad(HazardRegistry.tc99)				.nugget(nugget_technetium)	.billet(billet_technetium)	.ingot(ingot_technetium);
		new DictFrame("Radium226", "Ra226").hydro(2)		.rad(HazardRegistry.ra226)				.nugget(nugget_ra226);
		new DictFrame("Cobalt60", "Co60")					.rad(HazardRegistry.co60).hot(1)		.nugget(nugget_co60)		.billet(billet_co60)		.ingot(ingot_co60)			.dust(powder_co60);
		new DictFrame("Gold198", "Au198")					.rad(HazardRegistry.au198).hot(5)		.nugget(nugget_au198)		.billet(billet_au198)		.ingot(ingot_au198)			.dust(powder_au198);
		new DictFrame("Schrabidium")						.rad(HazardRegistry.sa326).blinding(3F)	.nugget(nugget_schrabidium)	.billet(billet_schrabidium)	.ingot(ingot_schrabidium)	.dust(powder_schrabidium)	.plate(plate_schrabidium)	.block(block_schrabidium)	.ore(ore_schrabidium, ore_gneiss_schrabidium, ore_nether_schrabidium).crystal(crystal_schrabidium);
		new DictFrame("Solinium")							.rad(HazardRegistry.sa327).blinding(3F)	.nugget(nugget_solinium)	.billet(billet_solinium)	.ingot(ingot_solinium)																.block(block_solinium);
		new DictFrame("Schrabidate")						.rad(HazardRegistry.sb)																			.ingot(ingot_schrabidate)	.dust(powder_schrabidate)								.block(block_schrabidate);
		new DictFrame("UraniumDioxide", "Uranium238Dioxide").rad(HazardRegistry.u238)				.ingot(ingot_du_dioxide)															.dust(powder_du_dioxide);
		/**
		 * HEAVY ACTINIDES
		 */
//		new DictFrame("Curium242", "Cm242").hot(20).rad(HazardRegistry.cm242).ingot(ingot_cm242);
		new DictFrame("Curium243", "Cm243").hot(20).rad(HazardRegistry.cm243).ingot(ingot_cm243);
		new DictFrame("Curium244", "Cm244").hot(2).rad(HazardRegistry.cm244).ingot(ingot_cm244);
		new DictFrame("Curium245", "Cm245").rad(HazardRegistry.cm245).ingot(ingot_cm245);
		new DictFrame("Curium246", "Cm246").rad(HazardRegistry.cm246).ingot(ingot_cm246);
		new DictFrame("Curium247", "Cm247").rad(HazardRegistry.cm247).ingot(ingot_cm247);
		new DictFrame("Curium248", "Cm248").rad(HazardRegistry.cm248).ingot(ingot_cm248).nugget(nugget_cm248);
		new DictFrame("Curium250", "Cm250").rad(HazardRegistry.cm250).ingot(ingot_cm250);
		new DictFrame("Berkelium247", "Bk247").rad(HazardRegistry.bk247).ingot(ingot_bk247);
		new DictFrame("Berkelium248", "Bk248").rad(HazardRegistry.bk248).ingot(ingot_bk248).billet(billet_bk248);
		new DictFrame("Berkelium249", "Bk249").hot(15).rad(HazardRegistry.bk249).ingot(ingot_bk249).billet(billet_bk249);
		new DictFrame("Californium249", "Cf249").rad(HazardRegistry.cf249).ingot(ingot_cf249);
		new DictFrame("Californium251", "Cf251").rad(HazardRegistry.cf251).ingot(ingot_cf251);
		new DictFrame("Californium252", "Cf252").hot(10).rad(HazardRegistry.cf252).ingot(ingot_cf252).billet(billet_cf252).nugget(nugget_cf252);
		new DictFrame("Einsteinium254", "Es254").hot(40).rad(HazardRegistry.es254).ingot(ingot_es254).dust(powder_es254);
//		new DictFrame("Mendelevium258", "Md258").hot(60).rad(HazardRegistry.md258).ingot(ingot_md258);
		/*
		 * TRANS-ACTINIDES / SUPERHEAVIES
		 */
//		new DictFrame("Copernicium285", "Cn285").hot(60).rad(HazardRegistry.cn285).ingot(ingot_cn285);
//		new DictFrame("Copernicium286", "Cn286").hot(180).rad(HazardRegistry.cn286).ingot(ingot_cn286);
		/*
		 * STABLE
		 */
		new DictFrame("Titanium")																	.ingot(ingot_titanium)												.dust(powder_titanium)			.plate(plate_titanium)			.block(block_titanium)		.ore(ore_titanium, ore_meteor_titanium);
		new DictFrame("Copper")																		.ingot(ingot_copper)												.dust(powder_copper)			.plate(plate_copper)			.block(block_copper)		.ore(ore_copper, ore_gneiss_copper, ore_meteor_copper);
		new DictFrame("Mingrade")																	.ingot(ingot_red_copper)											.dust(powder_red_copper)										.block(block_red_copper);
		new DictFrame("AdvancedAlloy", "Advanced")													.ingot(ingot_advanced_alloy)										.dust(powder_advanced_alloy)	.plate(plate_advanced_alloy)	.block(block_advanced_alloy);
		new DictFrame("Tungsten")																	.ingot(ingot_tungsten)												.dust(powder_tungsten)											.block(block_tungsten)		.ore(ore_tungsten, ore_nether_tungsten, ore_meteor_tungsten);
		new DictFrame("Aluminum")																	.ingot(ingot_aluminium)												.dust(powder_aluminium)			.plate(plate_aluminium)			.block(block_aluminium)		.ore(ore_aluminium, ore_meteor_aluminium);
		new DictFrame("Steel")																		.ingot(ingot_steel)				.dustSmall(powder_steel_tiny)		.dust(powder_steel)				.plate(plate_steel)				.block(block_steel);
		new DictFrame("TcAlloy")																	.ingot(ingot_tcalloy)												.dust(powder_tcalloy);
		new DictFrame("Lead")				.nugget(nugget_lead)									.ingot(ingot_lead)													.dust(powder_lead)				.plate(plate_lead)				.block(block_lead)			.ore(ore_lead, ore_meteor_lead);
		//new DictFrame("Bismuth")			.nugget(nugget_bismuth)									.ingot(ingot_bismuth); THAT'S WHAT YOU THOUGHT!
		new DictFrame("Tantalum")			.nugget(nugget_tantalium)	.gem(gem_tantalium)			.ingot(ingot_tantalium)												.dust(powder_tantalium)											.block(block_tantalium);
		new DictFrame("Coltan")																		.ingot(fragment_coltan)												.dust(powder_coltan_ore)										.block(block_coltan)		.ore(ore_coltan);
		new DictFrame("Niobium")			.nugget(fragment_niobium)								.ingot(ingot_niobium)			.dustSmall(powder_niobium_tiny)		.dust(powder_niobium)											.block(block_niobium);
		new DictFrame("Beryllium")			.nugget(nugget_beryllium)	.billet(billet_beryllium)	.ingot(ingot_beryllium)												.dust(powder_beryllium)											.block(block_beryllium)		.ore(ore_beryllium);
		new DictFrame("Cobalt")				.nugget(fragment_cobalt)								.ingot(ingot_cobalt)			.dustSmall(powder_cobalt_tiny)		.dust(powder_cobalt)											.block(block_cobalt)		.ore(ore_cobalt, ore_nether_cobalt);
		new DictFrame("Boron")				.nugget(fragment_boron)									.ingot(ingot_boron)				.dustSmall(powder_boron_tiny)		.dust(powder_boron)												.block(block_boron);
		new DictFrame("Graphite")																	.ingot(ingot_graphite)																												.block(block_graphite);
		new DictFrame("DuraSteel")																	.ingot(ingot_dura_steel)											.dust(powder_dura_steel)										.block(block_dura_steel);
		new DictFrame("Polymer")																	.ingot(ingot_polymer)												.dust(powder_polymer)											.block(block_polymer);
		new DictFrame("MagnetizedTungsten")															.ingot(ingot_magnetized_tungsten)									.dust(powder_magnetized_tungsten)								.block(block_magnetized_tungsten);
		new DictFrame("CMBSteel")																	.ingot(ingot_combine_steel)											.dust(powder_combine_steel)		.plate(plate_combine_steel)		.block(block_combine_steel);
		new DictFrame("DeshAlloy")			.nugget(nugget_desh)									.ingot(ingot_desh)													.dust(powder_desh)												.block(block_desh);
		new DictFrame("Starmetal")																	.ingot(ingot_starmetal)																												.block(block_starmetal)		.ore(ore_meteor_starmetal);
		new DictFrame("Saturnite")																	.ingot(ingot_saturnite)																				.plate(plate_saturnite)			.block(block_saturnite);
		new DictFrame("Euphemium")			.nugget(nugget_euphemium)								.ingot(ingot_euphemium)												.dust(powder_euphemium)											.block(block_euphemium);
		new DictFrame("Dineutronium")		.nugget(nugget_dineutronium)							.ingot(ingot_dineutronium)											.dust(powder_dineutronium)										.block(block_dineutronium);
		new DictFrame("Fiberglass")																	.ingot(ingot_fiberglass)																											.block(block_fiberglass);
		new DictFrame("Asbestos")			.asbestos(1F)											.ingot(ingot_asbestos)												.dust(powder_asbestos)											.block(block_asbestos)		.ore(ore_asbestos, ore_gneiss_asbestos, basalt_asbestos);
		new DictFrame("Ferrouranium")																.ingot(ingot_ferrouranium)																											.block(block_ferrouranium);
		new DictFrame("Staballoy")																	.ingot(ingot_staballoy)																												.block(block_staballoy);
		new DictFrame("Silicon")			.nugget(nugget_silicon)									.ingot(ingot_silicon)												.dust(powder_silicon)											.block(block_silicon);
		new DictFrame("NiobiumBeryllium")															.ingot(ingot_nbbe);
		new DictFrame("NiobiumSuperalloy")															.ingot(ingot_niobium_alloy);
		new DictFrame("Acrylic")																	.ingot(acrylic)														.dust(powder_acrylic);
		/*
		 * DUST AND GEM ORES
		 */
		new DictFrame("Sulfur")												.dust(sulfur)			.block(block_sulfur)	.ore(ore_sulfur, ore_nether_sulfur, basalt_sulfur, ore_meteor_sulfur);
		new DictFrame("Saltpeter")											.dust(niter)			.block(block_niter)		.ore(ore_niter);
		new DictFrame("Fluorite")											.dust(fluorite)			.block(block_fluorite)	.ore(ore_fluorite, basalt_fluorite);
		new DictFrame("Lignite")						.gem(lignite)		.dust(powder_lignite)							.ore(ore_lignite);
		new DictFrame("Cinnabar")	.crystal(cinnebar)	.gem(cinnebar)														.ore(ore_cinnebar, ore_depth_cinnebar);
		new DictFrame("Volcanic")						.gem(gem_volcanic)													.ore(basalt_gem);
		
		/*
		 * HAZARDS, MISC
		 */
		new DictFrame("Lithium")	.hydro(1F)	.ingot(lithium)	.dustSmall(powder_lithium_tiny)	.dust(powder_lithium)	.block(block_lithium)	.ore(ore_gneiss_lithium, ore_meteor_lithium).crystal(crystal_lithium);

		/*
		 * PHOSPHORUS
		 */
		new DictFrame("WhitePhosphorus")	.hot(5)	.ingot(ingot_phosphorus)	.block(block_white_phosphorus);
		new DictFrame("RedPhosphorus")		.hot(2)	.dust(powder_fire)			.block(block_red_phosphorus).crystal(crystal_phosphorus);
		
		/*
		 * RARE METALS
		 */
		new DictFrame("Australium")		.nugget(nugget_australium)	.billet(billet_australium)	.ingot(ingot_australium)	.dust(powder_australium)	.block(block_australium)	.ore(ore_australium);
		new DictFrame("Reiium")			.nugget(nugget_reiium)									.ingot(ingot_reiium)		.dust(powder_reiium)		.block(block_reiium)		.ore(ore_reiium);
		new DictFrame("Weidanium")		.nugget(nugget_weidanium)								.ingot(ingot_weidanium)		.dust(powder_weidanium)		.block(block_weidanium)		.ore(ore_weidanium);
		new DictFrame("Unobtainium")	.nugget(nugget_unobtainium)								.ingot(ingot_unobtainium)	.dust(powder_unobtainium)	.block(block_unobtainium)	.ore(ore_unobtainium);
		new DictFrame("Verticium")		.nugget(nugget_verticium)								.ingot(ingot_verticium)		.dust(powder_verticium)		.block(block_verticium)		.ore(ore_verticium);
		new DictFrame("Daffergon")		.nugget(nugget_daffergon)								.ingot(ingot_daffergon)		.dust(powder_daffergon)		.block(block_daffergon)		.ore(ore_daffergon);
		
		new DictFrame("AustraliumLesser", "Tasmanite").nugget(nugget_australium_lesser)			.billet(billet_australium_lesser);
		new DictFrame("AustraliumGreater", "Ayerite").nugget(nugget_australium_greater)			.billet(billet_australium_greater);
		/*
		 * RARE EARTHS
		 */
		new DictFrame("Lanthanum").hydro(1).hot(5).nugget(fragment_lanthanium).ingot(ingot_lanthanium)								.dustSmall(powder_lanthanium_tiny)	.dust(powder_lanthanium)	.block(block_lanthanium);
		new DictFrame("Actinium", "Actinium227", "Ac227").hot(2).rad(HazardRegistry.ac227).nugget(fragment_actinium, nugget_actinium).ingot(ingot_actinium).billet(billet_actinium).dustSmall(powder_actinium_tiny).dust(powder_actinium).block(block_actinium);
		new DictFrame("Zirconium")	.nugget(nugget_zirconium)		.ingot(ingot_zirconium)		.billet(billet_zirconium)												.dust(powder_zirconium)		.block(block_zirconium)		.ore(ore_depth_zirconium);
		new DictFrame("Neodymium")	.nugget(fragment_neodymium)		.ingot(ingot_neodymium)											.dustSmall(powder_neodymium_tiny)	.dust(powder_neodymium)									.ore(ore_depth_nether_neodymium);
		new DictFrame("Cerium")		.nugget(fragment_cerium)																		.dustSmall(powder_cerium_tiny)		.dust(powder_cerium);
		
		/*
		 * NITAN
		 */
		new DictFrame("Iodine")		.dust(powder_iodine);
		new DictFrame("Astatine", "Astatine209", "At209").rad(HazardRegistry.at209).hot(20).dust(powder_astatine);
		new DictFrame("Caesium", "Cesium").hydro(3).hot(2).dust(powder_caesium);
		new DictFrame("Strontium").hydro(3).dust(powder_strontium);
		new DictFrame("Bromine")	.dust(powder_bromine);
		new DictFrame("Tennessine", "Tennessine294", "Ts294").rad(HazardRegistry.ts294).hot(600).dust(powder_tennessine);

		/*
		 * FISSION FRAGMENTS
		 */
		new DictFrame("Iodine131", "I131")		.rad(HazardRegistry.i131)	.hot(1F)				.dustSmall(powder_i131_tiny)	.dust(powder_i131);
		new DictFrame("Xenon135", "Xe135")		.rad(HazardRegistry.xe135)	.hot(10F)				.dustSmall(powder_xe135_tiny)	.dust(powder_xe135);
		new DictFrame("Caesium137", "Cs137")	.rad(HazardRegistry.cs137)	.hot(3F)	.hydro(3F)	.dustSmall(powder_cs137_tiny)	.dust(powder_cs137);
		new DictFrame("Astatine209", "At209")	.rad(HazardRegistry.at209)	.hot(20F)												.dust(powder_at209);
		new DictFrame("Strontium90", "Sr90").hydro(3).rad(HazardRegistry.sr90).hot(4)				.dustSmall(powder_sr90_tiny).ingot(ingot_sr90).dust(powder_sr90);

		OreDictionary.registerOre("ingotTungstenCobalt", new ItemStack(component_ftl, 1, 4));
		
		OreDictionary.registerOre("record", record_glass);
		OreDictionary.registerOre("record", record_lc);
		OreDictionary.registerOre("record", record_nmj);
		OreDictionary.registerOre("record", record_ss);
		OreDictionary.registerOre("record", record_vc);
		
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
		
		OreDictionary.registerOre("blockConcrete", new ItemStack(concrete_colored, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockConcrete", concrete);
		OreDictionary.registerOre("blockConcrete", concrete_smooth);
		
		OreDictionary.registerOre("blockDucrete", ducrete);
		OreDictionary.registerOre("blockDucrete", ducrete_smooth);

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
		OreDictionary.registerOre("paneGlass", pane_acrylic);
		OreDictionary.registerOre("paneGlassColorless", pane_acrylic);
		OreDictionary.registerOre("paneGlass", pane_actinium);
	}
	
	@Untested
	public static String getReflector() {
		return GeneralConfig.enableReflectorCompat ? "plateDenseLead" : "plateTungCar"; //let's just mangle the name into "tungCar" so that it can't conflict with anything ever
	}
	
	public static class DictFrame {
		String[] mats;
		float hazMult = 1.0F;
		ArrayList<HazardEntry> hazards = new ArrayList<HazardEntry>();
		
		public DictFrame(String... mats) {
			this.mats = mats;
		}

		public DictFrame rad(float rad) { return this.haz(new HazardEntry(HazardRegistry.RADIATION, rad)); }
		public DictFrame hot(float time) { return this.haz(new HazardEntry(HazardRegistry.HOT, time)); }
		public DictFrame blinding(float time) { return this.haz(new HazardEntry(HazardRegistry.BLINDING, time)); }
		public DictFrame asbestos(float asb) { return this.haz(new HazardEntry(HazardRegistry.ASBESTOS, asb)); }
		public DictFrame hydro(float h) { return this.haz(new HazardEntry(HazardRegistry.HYDROACTIVE, h)); }
		public DictFrame cust(HazardTypeBase type) {return cust(type, 1);}
		public DictFrame cust(HazardTypeBase type, float amount) {return haz(new HazardEntry(type, amount));}
		
		public DictFrame haz(HazardEntry hazard) {
			hazards.add(hazard);
			return this;
		}
		
		public DictFrame nugget(Item... nugget) {
			hazMult = HazardRegistry.nugget;
			return makeItem("nugget", nugget).makeItem("tiny", nugget);
		}
		public DictFrame ingot(Item... ingot) {
			hazMult = HazardRegistry.ingot;
			return makeItem("ingot", ingot);
		}
		public DictFrame dustSmall(Item... dustSmall) {
			hazMult = HazardRegistry.powder_tiny;
			return makeItem("dustTiny", dustSmall);
		}
		public DictFrame dust(Item... dust) {
			hazMult = HazardRegistry.powder;
			return makeItem("dust", dust);
		}
		public DictFrame gem(Item... gem) {
			hazMult = HazardRegistry.gem;
			return makeItem("gem", gem);
		}
		public DictFrame crystal(Item... crystal) {
			hazMult = HazardRegistry.gem;
			return makeItem("crystal", crystal);
		}
		public DictFrame plate(Item... plate) {
			hazMult = HazardRegistry.plate;
			return makeItem("plate", plate);
		}
		public DictFrame billet(Item... billet) {
			hazMult = HazardRegistry.billet;
			return makeItem("billet", billet);
		}
		
		public DictFrame block(Block... block) {
			hazMult = HazardRegistry.block;
			return makeBlocks("block", block);
		}
		public DictFrame ore(Block... ore) {
			hazMult = HazardRegistry.ore;
			return makeBlocks("ore", ore);
		}
		public DictFrame oreNether(Block... oreNether) {
			hazMult = HazardRegistry.ore;
			return makeBlocks("oreNether", oreNether);
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
	@CheckReturnValue
	public static String getMatFromKey(String key)
	{
		for (String prefix : sizeMap.keySet())
			if (key.startsWith(prefix) && !key.substring(prefix.length()).isEmpty())
				return key.substring(prefix.length());
		
		return new String();
	}
	@CheckReturnValue
	public static String getPrefixFromKey(String key)
	{
		for (String prefix : sizeMap.keySet())
			if (key.startsWith(prefix) && !key.substring(prefix.length()).isEmpty())
				return prefix;
		
		return new String();
	}
	
	public static class MaterialStack
	{
		public static final MaterialStack NULL_STACK = nullStack();
		private String material = new String();
		private int size = 0;
		/** Unspecified stack **/
		protected MaterialStack()
		{}
		/** Assumes stack is an ingot or equivalent **/
		public MaterialStack(String matIn)
		{
			material = matIn;
			size = 9;
		}
		
		public MaterialStack(String matIn, int size)
		{
			this(matIn);
			this.size = size;
		}
		
		public MaterialStack(OreDictStack stack)
		{
			this(getMatFromKey(stack.name), sizeMap.get(getPrefixFromKey(stack.name)) * stack.stacksize);
		}
		public MaterialStack(ComparableStack stack, int multi)
		{
			this(stack.getStack().getUnlocalizedName(), stack.stacksize * multi);
		}
		public MaterialStack(ComparableStack stack)
		{
			this(stack, sizeMap.get(getPrefixFromKey(stack.getDictKeys()[0])) * stack.stacksize);
		}
		@CheckReturnValue
		@CheckForNull
		public static MaterialStack materialStackHelper(AStack stack)
		{
			if (stack instanceof ComparableStack)
				return new MaterialStack((ComparableStack) stack);
			else if (stack instanceof OreDictStack)
				return new MaterialStack((OreDictStack) stack);
			else
				return NULL_STACK;
		}
		
		public boolean isStackApplicable(AStack stack)
		{
			if (stack instanceof ComparableStack)
			{
				ComparableStack cStack = (ComparableStack) stack;
				for (String key : cStack.getDictKeys())
					if (getMatFromKey(key).equals(material))
						return true;
			}
			else if (stack instanceof OreDictStack)
			{
				OreDictStack oStack = (OreDictStack) stack;
				return getMatFromKey(oStack.name).equals(material);
			}
			return false;
		}
		
		public int getSize()
		{
			return size;
		}
		/** Returns the total size of the stack formatted in standard blocks, ingots, and nuggets **/
		public int[] getAllSizes()
		{
			int[] sizes = new int[3];
			sizes[0] = Math.floorDiv(getSize(), 81);
			sizes[1] = Math.floorDiv(getSize() - (sizes[0] * 81), 9);
			sizes[2] = getSize() - ((sizes[0] * 81) + (sizes[1] * 9));
			return sizes;
		}
		
		public MaterialStack setSize(int i)
		{
			size = i;
			return this;
		}
		public void incrementSize(int i)
		{
			size += i;
		}
		public void decrementSize(int i)
		{
			size -= i;
			if (size < 0)
				size = 0;
		}
		
		public static MaterialStack nullStack()
		{
			return new MaterialStack();
		}
		
		@Override
		public String toString()
		{
			return String.format("Material name: %s; Size (in nuggets): %s", material, size);
		}
		@Override
		public MaterialStack clone()
		{
			return new MaterialStack(material, size);
		}
	}
}
