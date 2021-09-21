package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;

//i love you
import static com.hbm.items.ModItems.*;
import static com.hbm.blocks.ModBlocks.*;

import com.hbm.config.GeneralConfig;
import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.interfaces.Untested;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictManager {
	
	public static void registerOres() {

		/*
		 * VANILLA
		 */
		new DictFrame("Coal").gem(Items.coal).dustSmall(powder_coal_tiny).dust(powder_coal);
		new DictFrame("Iron").plate(plate_iron).dust(powder_iron).ore(ore_gneiss_iron);
		new DictFrame("Gold").plate(plate_gold).dust(powder_gold).ore(ore_gneiss_gold);
		new DictFrame("Lapis").dust(powder_lapis);
		new DictFrame("NetherQuartz").gem(Items.quartz).dust(powder_quartz);
		new DictFrame("Diamond").dust(powder_diamond).ore(gravel_diamond);
		new DictFrame("Emerald").dust(powder_emerald);
		
		/*
		 * RADIOACTIVE
		 */
		new DictFrame("Uranium")							.rad(HazardRegistry.u)				.nugget(nugget_uranium)		.billet(billet_uranium)		.ingot(ingot_uranium)		.dust(powder_uranium)									.block(block_uranium)		.ore(ore_uranium, ore_uranium_scorched, ore_gneiss_uranium, ore_gneiss_uranium_scorched, ore_nether_uranium, ore_nether_uranium_scorched, ore_meteor_uranium);
		new DictFrame("Uranium233", "U233")					.rad(HazardRegistry.u233)			.nugget(nugget_u233)		.billet(billet_u233)		.ingot(ingot_u233)																	.block(block_u233);
		new DictFrame("Uranium235", "U235")					.rad(HazardRegistry.u235)			.nugget(nugget_u235)		.billet(billet_u235)		.ingot(ingot_u235)																	.block(block_u235);
		new DictFrame("Uranium238", "U238")					.rad(HazardRegistry.u238)			.nugget(nugget_u238)		.billet(billet_u238)		.ingot(ingot_u238)																	.block(block_u238);
		new DictFrame("Thorium232", "Th232", "Thorium")		.rad(HazardRegistry.th232)			.nugget(nugget_th232)		.billet(billet_th232)		.ingot(ingot_th232)			.dust(powder_thorium)									.block(block_thorium)		.ore(ore_thorium, ore_meteor_thorium);
		new DictFrame("Plutonium")							.rad(HazardRegistry.pu)				.nugget(nugget_plutonium)	.billet(billet_plutonium)	.ingot(ingot_plutonium)		.dust(powder_plutonium)									.block(block_plutonium)		.ore(ore_nether_plutonium);
		new DictFrame("PlutoniumRG")						.rad(HazardRegistry.purg)			.nugget(nugget_pu_mix)		.billet(billet_pu_mix)		.ingot(ingot_pu_mix)																.block(block_pu_mix);
		new DictFrame("Plutonium238", "Pu238")				.rad(HazardRegistry.pu238)			.nugget(nugget_pu238)		.billet(billet_pu238)		.ingot(ingot_pu238)																	.block(block_pu238);
		new DictFrame("Plutonium239", "Pu239")				.rad(HazardRegistry.pu239)			.nugget(nugget_pu239)		.billet(billet_pu239)		.ingot(ingot_pu239)																	.block(block_pu239);
		new DictFrame("Plutonium240", "Pu240")				.rad(HazardRegistry.pu240)			.nugget(nugget_pu240)		.billet(billet_pu240)		.ingot(ingot_pu240)																	.block(block_pu240);
		new DictFrame("Plutonium241", "Pu241")				.rad(HazardRegistry.pu241)			.nugget(nugget_pu241)		.billet(billet_pu241)		.ingot(ingot_pu241);																//.block(block_pu241);
		new DictFrame("Americium241", "Am241")				.rad(HazardRegistry.am241)			.nugget(nugget_am241)		.billet(billet_am241)		.ingot(ingot_am241);
		new DictFrame("Americium242", "Am242")				.rad(HazardRegistry.am242)			.nugget(nugget_am242)		.billet(billet_am242)		.ingot(ingot_am242);
		new DictFrame("AmericiumRG")						.rad(HazardRegistry.amrg)			.nugget(nugget_am_mix)		.billet(billet_am_mix)		.ingot(ingot_am_mix);
		new DictFrame("Neptunium237", "Np237", "Neptunium")	.rad(HazardRegistry.np237)			.nugget(nugget_neptunium)	.billet(billet_neptunium)	.ingot(ingot_neptunium)		.dust(powder_neptunium)									.block(block_neptunium);
		new DictFrame("Polonium210", "Po210")				.rad(HazardRegistry.po210).hot(3)	.nugget(nugget_polonium)	.billet(billet_polonium)	.ingot(ingot_polonium)		.dust(powder_polonium)									.block(block_polonium);
		new DictFrame("Technetium99", "Tc99")				.rad(HazardRegistry.tc99)			.nugget(nugget_technetium)	.billet(billet_technetium)	.ingot(ingot_technetium);
		new DictFrame("Radium226", "Ra226")					.rad(HazardRegistry.ra226)			.nugget(nugget_ra226);
		new DictFrame("Cobalt60", "Co60")					.rad(HazardRegistry.co60).hot(1)	.nugget(nugget_co60)		.billet(billet_co60)		.ingot(ingot_co60)			.dust(powder_co60);
		new DictFrame("Gold198", "Au198")					.rad(HazardRegistry.au198).hot(5)	.nugget(nugget_au198)		.billet(billet_au198)		.ingot(ingot_au198)			.dust(powder_au198);
		new DictFrame("Schrabidium")						.rad(HazardRegistry.sa326)			.nugget(nugget_schrabidium)	.billet(billet_schrabidium)	.ingot(ingot_schrabidium)	.dust(powder_schrabidium)	.plate(plate_schrabidium)	.block(block_schrabidium)	.ore(ore_schrabidium, ore_gneiss_schrabidium, ore_nether_schrabidium);
		new DictFrame("Solinium")							.rad(HazardRegistry.sa327)			.nugget(nugget_solinium)	.billet(billet_solinium)	.ingot(ingot_solinium)																.block(block_solinium);

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
		new DictFrame("Beryllium")			.nugget(nugget_beryllium)								.ingot(ingot_beryllium)												.dust(powder_beryllium)											.block(block_beryllium)		.ore(ore_beryllium);
		new DictFrame("Cobalt")				.nugget(fragment_cobalt)								.ingot(ingot_cobalt)			.dustSmall(powder_cobalt_tiny)		.dust(powder_cobalt)											.block(block_cobalt)		.ore(ore_cobalt, ore_nether_cobalt);
		new DictFrame("Boron")				.nugget(fragment_boron)									.ingot(ingot_boron)				.dustSmall(powder_boron_tiny)		.dust(powder_boron)												.block(block_boron);
		new DictFrame("Graphite")																	.ingot(ingot_graphite)																												.block(block_graphite);
		new DictFrame("DuraSteel")																	.ingot(ingot_dura_steel)											.dust(powder_dura_steel)										.block(block_dura_steel);
		new DictFrame("Polymer")																	.ingot(ingot_polymer)												.dust(powder_polymer);
		new DictFrame("Schrabidate")																.ingot(ingot_schrabidate)											.dust(powder_schrabidate)										.block(block_schrabidate);
		new DictFrame("MagnetizedTungsten")															.ingot(ingot_magnetized_tungsten)									.dust(powder_magnetized_tungsten)								.block(block_magnetized_tungsten);
		new DictFrame("CMBSteel")																	.ingot(ingot_combine_steel)											.dust(powder_combine_steel)		.plate(plate_combine_steel)		.block(block_combine_steel);
		new DictFrame("Desh")				.nugget(nugget_desh)									.ingot(ingot_desh)													.dust(powder_desh)												.block(block_desh);
		new DictFrame("Starmetal")																	.ingot(ingot_starmetal)																												.block(block_starmetal)		.ore(ore_meteor_starmetal);
		new DictFrame("Saturnite")																	.ingot(ingot_saturnite)																				.plate(plate_saturnite);
		new DictFrame("Euphemium")			.nugget(nugget_euphemium)								.ingot(ingot_euphemium)												.dust(powder_euphemium)											.block(block_euphemium);
		new DictFrame("Dineutronium")		.nugget(nugget_dineutronium)							.ingot(ingot_dineutronium)											.dust(powder_dineutronium)										.block(block_dineutronium);
		new DictFrame("Lithium")																	.ingot(lithium)					.dustSmall(powder_lithium_tiny)		.dust(powder_lithium)											.block(block_lithium)		.ore(ore_gneiss_lithium, ore_meteor_lithium);
		new DictFrame("Fiberglass")																	.ingot(ingot_fiberglass)																											.block(block_fiberglass);
		new DictFrame("Asbestos")																	.ingot(ingot_asbestos)												.dust(powder_asbestos)											.block(block_asbestos)		.ore(ore_asbestos, ore_gneiss_asbestos, basalt_asbestos);

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
		 * PHOSPHORUS
		 */
		new DictFrame("WhitePhosphorus")	.hot(5)	.ingot(ingot_phosphorus)	.block(block_white_phosphorus);
		new DictFrame("RedPhosphorus")		.hot(2)	.dust(powder_fire)			.block(block_red_phosphorus);
		
		/*
		 * RARE METALS
		 */
		new DictFrame("Australium")		.nugget(nugget_australium)	.billet(billet_australium)	.ingot(ingot_australium)	.dust(powder_australium)	.block(block_australium)	.ore(ore_australium);
		new DictFrame("Reiium")			.nugget(nugget_reiium)									.ingot(ingot_reiium)		.dust(powder_reiium)		.block(block_reiium)		.ore(ore_reiium);
		new DictFrame("Weidanium")		.nugget(nugget_weidanium)								.ingot(ingot_weidanium)		.dust(powder_weidanium)		.block(block_weidanium)		.ore(ore_weidanium);
		new DictFrame("Unobtainium")	.nugget(nugget_unobtainium)								.ingot(ingot_unobtainium)	.dust(powder_unobtainium)	.block(block_unobtainium)	.ore(ore_unobtainium);
		new DictFrame("Verticium")		.nugget(nugget_verticium)								.ingot(ingot_verticium)		.dust(powder_verticium)		.block(block_verticium)		.ore(ore_verticium);
		new DictFrame("Daffergon")		.nugget(nugget_daffergon)								.ingot(ingot_daffergon)		.dust(powder_daffergon)		.block(block_daffergon)		.ore(ore_daffergon);

		/*
		 * RARE EARTHS
		 */
		new DictFrame("Lanthanum")	.nugget(fragment_lanthanium)	.ingot(ingot_lanthanium)	.dustSmall(powder_lanthanium_tiny)	.dust(powder_lanthanium)	.block(block_lanthanium);
		new DictFrame("Actinium")	.nugget(fragment_actinium)		.ingot(ingot_actinium)		.dustSmall(powder_actinium_tiny)	.dust(powder_actinium)		.block(block_actinium);
		new DictFrame("Zirconium")	.nugget(nugget_zirconium)		.ingot(ingot_zirconium)											.dust(powder_zirconium)		.block(block_zirconium)		.ore(ore_depth_zirconium);
		new DictFrame("Neodymium")	.nugget(fragment_neodymium)									.dustSmall(powder_neodymium_tiny)	.dust(powder_neodymium)									.ore(ore_depth_nether_neodymium);
		new DictFrame("Cerium")		.nugget(fragment_cerium)									.dustSmall(powder_cerium_tiny)		.dust(powder_cerium);
		
		/*
		 * NITAN
		 */
		new DictFrame("Iodine")		.dust(powder_iodine);
		new DictFrame("Astatine")	.dust(powder_astatine);

		/*
		 * FISSION FRAGMENTS
		 */
		new DictFrame("Iodine131", "I131")		.dustSmall(powder_i131_tiny)	.dust(powder_i131);
		new DictFrame("Xenon135", "Xe135")		.dustSmall(powder_xe135_tiny)	.dust(powder_xe135);
		new DictFrame("Caesium137", "Cs137")	.dustSmall(powder_cs137_tiny)	.dust(powder_cs137);
		new DictFrame("Astatine209", "At209")									.dust(powder_at209);

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
		OreDictionary.registerOre("dye", cinnebar);
		OreDictionary.registerOre("dyeYellow", sulfur);
		OreDictionary.registerOre("dye", sulfur);
		OreDictionary.registerOre("dyeBlack", powder_coal);
		OreDictionary.registerOre("dye", powder_coal);
		OreDictionary.registerOre("dyeBrown", powder_lignite);
		OreDictionary.registerOre("dye", powder_lignite);
		OreDictionary.registerOre("dyeLightGray", powder_titanium);
		OreDictionary.registerOre("dye", powder_titanium);
		OreDictionary.registerOre("dyeWhite", fluorite);
		OreDictionary.registerOre("dye", fluorite);
		OreDictionary.registerOre("dyeBlue", powder_lapis);
		OreDictionary.registerOre("dye", powder_lapis);
		OreDictionary.registerOre("dyeBlack", oil_tar);
		OreDictionary.registerOre("dye", oil_tar);

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
	
	@Untested
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

		public DictFrame rad(float rad) {
			return this.haz(new HazardEntry(HazardRegistry.RADIATION, rad));
		}
		public DictFrame hot(float time) {
			return this.haz(new HazardEntry(HazardRegistry.HOT, time));
		}
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
			return makeItem("dustSmall", dustSmall);
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
			hazMult = 0.0F;
			return makeBlocks("ore", ore);
		}
		public DictFrame oreNether(Block... oreNether) {
			hazMult = 0.0F;
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
}
