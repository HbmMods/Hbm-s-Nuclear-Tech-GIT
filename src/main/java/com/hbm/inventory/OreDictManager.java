package com.hbm.inventory;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.interfaces.Untested;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictManager {
	
	public static void registerOres() {

		new DictFrame("Coal").gem(Items.coal).dustSmall(ModItems.powder_coal_tiny).dust(ModItems.powder_coal);
		new DictFrame("Iron").plate(ModItems.plate_iron).dust(ModItems.powder_iron).ore(ModBlocks.ore_gneiss_iron);
		new DictFrame("Gold").plate(ModItems.plate_gold).dust(ModItems.powder_gold).ore(ModBlocks.ore_gneiss_gold);
		new DictFrame("Lapis").dust(ModItems.powder_lapis);
		new DictFrame("NetherQuartz").gem(Items.quartz).dust(ModItems.powder_quartz);
		new DictFrame("Diamond").dust(ModItems.powder_diamond).ore(ModBlocks.gravel_diamond);
		new DictFrame("Emerald").dust(ModItems.powder_emerald);
		
		new DictFrame("Uranium").nugget(ModItems.nugget_uranium).billet(ModItems.billet_uranium).ingot(ModItems.ingot_uranium).dust(ModItems.powder_uranium).block(ModBlocks.block_uranium)
		.ore(ModBlocks.ore_uranium, ModBlocks.ore_uranium_scorched, ModBlocks.ore_gneiss_uranium, ModBlocks.ore_gneiss_uranium_scorched, ModBlocks.ore_nether_uranium, ModBlocks.ore_nether_uranium_scorched, ModBlocks.ore_meteor_uranium);
		new DictFrame("Uranium233", "U233").nugget(ModItems.nugget_u233).billet(ModItems.billet_u233).ingot(ModItems.ingot_u233).block(ModBlocks.block_u233);
		new DictFrame("Uranium235", "U235").nugget(ModItems.nugget_u235).billet(ModItems.billet_u235).ingot(ModItems.ingot_u235).block(ModBlocks.block_u235);
		new DictFrame("Uranium238", "U238").nugget(ModItems.nugget_u238).billet(ModItems.billet_u238).ingot(ModItems.ingot_u238).block(ModBlocks.block_u238);
		new DictFrame("Thorium232", "Th232").nugget(ModItems.nugget_th232).billet(ModItems.billet_th232).ingot(ModItems.ingot_th232).dust(ModItems.powder_thorium).block(ModBlocks.block_thorium).ore(ModBlocks.ore_thorium, ModBlocks.ore_meteor_thorium);
		new DictFrame("Plutonium").nugget(ModItems.nugget_plutonium).billet(ModItems.billet_plutonium).ingot(ModItems.ingot_plutonium).dust(ModItems.powder_plutonium).block(ModBlocks.block_plutonium).ore(ModBlocks.ore_nether_plutonium);
		new DictFrame("Plutonium238", "Pu238").nugget(ModItems.nugget_pu238).billet(ModItems.billet_pu238).ingot(ModItems.ingot_pu238).block(ModBlocks.block_pu238);
		new DictFrame("Plutonium239", "Pu239").nugget(ModItems.nugget_pu239).billet(ModItems.billet_pu239).ingot(ModItems.ingot_pu239).block(ModBlocks.block_pu239);
		new DictFrame("Plutonium240", "Pu240").nugget(ModItems.nugget_pu240).billet(ModItems.billet_pu240).ingot(ModItems.ingot_pu240).block(ModBlocks.block_pu240);
		new DictFrame("Plutonium241", "Pu241").nugget(ModItems.nugget_pu241).billet(ModItems.billet_pu241).ingot(ModItems.ingot_pu241);//.block(ModBlocks.block_pu241);
		new DictFrame("Americium241", "Am241").nugget(ModItems.nugget_am241).billet(ModItems.billet_am241).ingot(ModItems.ingot_am241);
		new DictFrame("Americium242", "Am242").nugget(ModItems.nugget_am242).billet(ModItems.billet_am242).ingot(ModItems.ingot_am242);
		new DictFrame("Neptunium237", "Np237").nugget(ModItems.nugget_neptunium).billet(ModItems.billet_neptunium).ingot(ModItems.ingot_neptunium).dust(ModItems.powder_neptunium).block(ModBlocks.block_neptunium);
		new DictFrame("Polonium210", "Po210").nugget(ModItems.nugget_polonium).billet(ModItems.billet_polonium).ingot(ModItems.ingot_polonium).dust(ModItems.powder_polonium).block(ModBlocks.block_polonium);
		new DictFrame("Technetium99", "Tc99").nugget(ModItems.nugget_technetium).billet(ModItems.billet_technetium).ingot(ModItems.ingot_technetium);
		new DictFrame("Radium226", "Ra226").nugget(ModItems.nugget_ra226);
		new DictFrame("Cobalt60", "Co60").nugget(ModItems.nugget_co60).billet(ModItems.billet_co60).ingot(ModItems.ingot_co60).dust(ModItems.powder_co60);
		new DictFrame("Gold198", "Au198").nugget(ModItems.nugget_au198).billet(ModItems.billet_au198).ingot(ModItems.ingot_au198).dust(ModItems.powder_au198);
		new DictFrame("Titanium").ingot(ModItems.ingot_titanium).dust(ModItems.powder_titanium).plate(ModItems.plate_titanium).block(ModBlocks.block_titanium).ore(ModBlocks.ore_titanium, ModBlocks.ore_meteor_titanium);
		new DictFrame("Copper").ingot(ModItems.ingot_copper).dust(ModItems.powder_copper).plate(ModItems.plate_copper).block(ModBlocks.block_copper).ore(ModBlocks.ore_copper, ModBlocks.ore_gneiss_copper, ModBlocks.ore_meteor_copper);
		new DictFrame("Mingrade").ingot(ModItems.ingot_red_copper).dust(ModItems.powder_red_copper).plate(ModItems.plate_advanced_alloy).block(ModBlocks.block_red_copper);
		new DictFrame("AdvancedAlloy").ingot(ModItems.ingot_advanced_alloy).dust(ModItems.powder_advanced_alloy).block(ModBlocks.block_advanced_alloy);
		new DictFrame("Tungsten").ingot(ModItems.ingot_tungsten).dust(ModItems.powder_tungsten).block(ModBlocks.block_tungsten).ore(ModBlocks.ore_tungsten, ModBlocks.ore_nether_tungsten, ModBlocks.ore_meteor_tungsten);
		new DictFrame("Aluminum").ingot(ModItems.ingot_aluminium).dust(ModItems.powder_aluminium).plate(ModItems.plate_aluminium).block(ModBlocks.block_aluminium).ore(ModBlocks.ore_aluminium, ModBlocks.ore_meteor_aluminium);
		new DictFrame("Steel").ingot(ModItems.ingot_steel).dustSmall(ModItems.powder_steel_tiny).dust(ModItems.powder_steel).plate(ModItems.plate_steel).block(ModBlocks.block_steel);
		new DictFrame("TcAlloy").ingot(ModItems.ingot_tcalloy).dust(ModItems.powder_tcalloy);
		new DictFrame("Lead").nugget(ModItems.nugget_lead).ingot(ModItems.ingot_lead).dust(ModItems.powder_lead).plate(ModItems.plate_lead).block(ModBlocks.block_lead).ore(ModBlocks.ore_lead, ModBlocks.ore_meteor_lead);
		//new DictFrame("Bismuth").nugget(ModItems.nugget_bismuth).ingot(ModItems.ingot_bismuth); THAT'S WHAT YOU THOUGHT!
		new DictFrame("Tantalum").nugget(ModItems.nugget_tantalium).gem(ModItems.gem_tantalium).ingot(ModItems.ingot_tantalium).dust(ModItems.powder_tantalium).block(ModBlocks.block_tantalium);
		new DictFrame("Coltan").ingot(ModItems.fragment_coltan).dust(ModItems.powder_coltan_ore).block(ModBlocks.block_coltan).ore(ModBlocks.ore_coltan);
		new DictFrame("Niobium").nugget(ModItems.fragment_niobium).ingot(ModItems.ingot_niobium).dustSmall(ModItems.powder_niobium_tiny).dust(ModItems.powder_niobium).block(ModBlocks.block_niobium);
		new DictFrame("Beryllium").nugget(ModItems.nugget_beryllium).ingot(ModItems.ingot_beryllium).dust(ModItems.powder_beryllium).block(ModBlocks.block_beryllium).ore(ModBlocks.ore_beryllium);
		new DictFrame("Cobalt").nugget(ModItems.fragment_cobalt).ingot(ModItems.ingot_cobalt).dustSmall(ModItems.powder_cobalt_tiny).dust(ModItems.powder_cobalt).block(ModBlocks.block_cobalt).ore(ModBlocks.ore_cobalt, ModBlocks.ore_nether_cobalt);
		new DictFrame("Boron").nugget(ModItems.fragment_boron).ingot(ModItems.ingot_boron).dustSmall(ModItems.powder_boron_tiny).dust(ModItems.powder_boron).block(ModBlocks.block_boron);
		new DictFrame("Graphite").ingot(ModItems.ingot_graphite).block(ModBlocks.block_graphite);
		new DictFrame("DuraSteel").ingot(ModItems.ingot_dura_steel).dust(ModItems.powder_dura_steel).block(ModBlocks.block_dura_steel);
		new DictFrame("Polymer").ingot(ModItems.ingot_polymer).dust(ModItems.powder_polymer);
		new DictFrame("Schrabidium").nugget(ModItems.nugget_schrabidium).billet(ModItems.billet_schrabidium).ingot(ModItems.ingot_schrabidium).plate(ModItems.plate_schrabidium).dust(ModItems.powder_schrabidium).block(ModBlocks.block_schrabidium).ore(ModBlocks.ore_schrabidium, ModBlocks.ore_gneiss_schrabidium, ModBlocks.ore_nether_schrabidium);
		new DictFrame("Schrabidate").ingot(ModItems.ingot_schrabidate).dust(ModItems.powder_schrabidate).block(ModBlocks.block_schrabidate);
		new DictFrame("MagnetizedTungsten").ingot(ModItems.ingot_magnetized_tungsten).dust(ModItems.powder_magnetized_tungsten).block(ModBlocks.block_magnetized_tungsten);
		new DictFrame("CMBSteel").ingot(ModItems.ingot_combine_steel).dust(ModItems.powder_combine_steel).block(ModBlocks.block_combine_steel);
		new DictFrame("Solinium").nugget(ModItems.nugget_solinium).billet(ModItems.billet_solinium).ingot(ModItems.ingot_solinium).block(ModBlocks.block_solinium);
		new DictFrame("Lanthanum").nugget(ModItems.fragment_lanthanium).ingot(ModItems.ingot_lanthanium).dustSmall(ModItems.powder_lanthanium_tiny).dust(ModItems.powder_lanthanium).block(ModBlocks.block_lanthanium);
		new DictFrame("Actinium").nugget(ModItems.fragment_actinium).ingot(ModItems.ingot_actinium).dustSmall(ModItems.powder_actinium_tiny).dust(ModItems.powder_actinium).block(ModBlocks.block_actinium);
		new DictFrame("Desh").nugget(ModItems.nugget_desh).ingot(ModItems.ingot_desh).dust(ModItems.powder_desh).block(ModBlocks.block_desh);
		new DictFrame("Starmetal").ingot(ModItems.ingot_starmetal).block(ModBlocks.block_starmetal).ore(ModBlocks.ore_meteor_starmetal);
		new DictFrame("Saturnite").ingot(ModItems.ingot_saturnite).plate(ModItems.plate_saturnite);
		new DictFrame("Euphemium").nugget(ModItems.nugget_euphemium).ingot(ModItems.ingot_euphemium).dust(ModItems.powder_euphemium).block(ModBlocks.block_euphemium);
		new DictFrame("Dineutronium").nugget(ModItems.nugget_dineutronium).ingot(ModItems.ingot_dineutronium).dust(ModItems.powder_dineutronium).block(ModBlocks.block_dineutronium);
		new DictFrame("WhitePhosphorus").ingot(ModItems.ingot_phosphorus).block(ModBlocks.block_white_phosphorus);
		new DictFrame("RedPhosphorus").dust(ModItems.powder_fire).block(ModBlocks.block_red_phosphorus);
		new DictFrame("Lithium").ingot(ModItems.lithium).dustSmall(ModItems.powder_lithium_tiny).dust(ModItems.powder_lithium).block(ModBlocks.block_lithium).ore(ModBlocks.ore_gneiss_lithium, ModBlocks.ore_meteor_lithium);
		new DictFrame("Zirconium").nugget(ModItems.nugget_zirconium).ingot(ModItems.ingot_zirconium).dust(ModItems.powder_zirconium).block(ModBlocks.block_zirconium).ore(ModBlocks.ore_depth_zirconium);
		new DictFrame("Fiberglass").ingot(ModItems.ingot_fiberglass).block(ModBlocks.block_fiberglass);
		new DictFrame("Asbestos").ingot(ModItems.ingot_asbestos).dust(ModItems.powder_asbestos).block(ModBlocks.block_asbestos).ore(ModBlocks.ore_asbestos, ModBlocks.ore_gneiss_asbestos, ModBlocks.basalt_asbestos);
		new DictFrame("Cinnabar").crystal(ModItems.cinnebar).gem(ModItems.cinnebar).ore(ModBlocks.ore_cinnebar, ModBlocks.ore_depth_cinnebar);
		new DictFrame("Lignite").gem(ModItems.lignite).dust(ModItems.powder_lignite).ore(ModBlocks.ore_lignite);
		new DictFrame("Sulfur").dust(ModItems.sulfur).block(ModBlocks.block_sulfur).ore(ModBlocks.ore_sulfur, ModBlocks.ore_nether_sulfur, ModBlocks.basalt_sulfur, ModBlocks.ore_meteor_sulfur);
		new DictFrame("Niter").dust(ModItems.niter).block(ModBlocks.block_niter).ore(ModBlocks.ore_niter);
		new DictFrame("Fluorite").dust(ModItems.fluorite).block(ModBlocks.block_fluorite).ore(ModBlocks.ore_fluorite, ModBlocks.basalt_fluorite);
		new DictFrame("Volcanic").gem(ModItems.gem_volcanic).ore(ModBlocks.basalt_gem);

		new DictFrame("Australium").nugget(ModItems.nugget_australium).billet(ModItems.billet_australium).ingot(ModItems.ingot_australium).dust(ModItems.powder_australium).block(ModBlocks.block_australium).ore(ModBlocks.ore_australium);
		new DictFrame("Reiium").nugget(ModItems.nugget_reiium).ingot(ModItems.ingot_reiium).dust(ModItems.powder_reiium).block(ModBlocks.block_reiium).ore(ModBlocks.ore_reiium);
		new DictFrame("Weidanium").nugget(ModItems.nugget_weidanium).ingot(ModItems.ingot_weidanium).dust(ModItems.powder_weidanium).block(ModBlocks.block_weidanium).ore(ModBlocks.ore_weidanium);
		new DictFrame("Unobtainium").nugget(ModItems.nugget_unobtainium).ingot(ModItems.ingot_unobtainium).dust(ModItems.powder_unobtainium).block(ModBlocks.block_unobtainium).ore(ModBlocks.ore_unobtainium);
		new DictFrame("Verticium").nugget(ModItems.nugget_verticium).ingot(ModItems.ingot_verticium).dust(ModItems.powder_verticium).block(ModBlocks.block_verticium).ore(ModBlocks.ore_verticium);
		new DictFrame("Daffergon").nugget(ModItems.nugget_daffergon).ingot(ModItems.ingot_daffergon).dust(ModItems.powder_daffergon).block(ModBlocks.block_daffergon).ore(ModBlocks.ore_daffergon);

		new DictFrame("Neodymium").nugget(ModItems.fragment_neodymium).dustSmall(ModItems.powder_neodymium_tiny).dust(ModItems.powder_neodymium).ore(ModBlocks.ore_depth_nether_neodymium);
		new DictFrame("Cerium").nugget(ModItems.fragment_cerium).dustSmall(ModItems.powder_cerium_tiny).dust(ModItems.powder_cerium);
		new DictFrame("Iodine").dust(ModItems.powder_iodine);
		new DictFrame("Astatine").dust(ModItems.powder_astatine);

		new DictFrame("Iodine131", "I131").dustSmall(ModItems.powder_i131_tiny).dust(ModItems.powder_i131);
		new DictFrame("Xenon135", "Xe135").dustSmall(ModItems.powder_xe135_tiny).dust(ModItems.powder_xe135);
		new DictFrame("Caesium137", "Cs137").dustSmall(ModItems.powder_cs137_tiny).dust(ModItems.powder_cs137);
		new DictFrame("Astatine209", "At209").dust(ModItems.powder_at209);

		OreDictionary.registerOre(getReflector(), ModItems.neutron_reflector);

		OreDictionary.registerOre("logWood", ModBlocks.pink_log);
		OreDictionary.registerOre("logWoodPink", ModBlocks.pink_log);
		OreDictionary.registerOre("plankWood", ModBlocks.pink_planks);
		OreDictionary.registerOre("plankWoodPink", ModBlocks.pink_planks);
		OreDictionary.registerOre("slabWood", ModBlocks.pink_slab);
		OreDictionary.registerOre("slabWoodPink", ModBlocks.pink_slab);
		OreDictionary.registerOre("stairWood", ModBlocks.pink_stairs);
		OreDictionary.registerOre("stairWoodPink", ModBlocks.pink_stairs);

		OreDictionary.registerOre("blockGlass", ModBlocks.glass_boron);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_lead);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_uranium);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_trinitite);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_polonium);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_ash);
		OreDictionary.registerOre("blockGlassYellow", ModBlocks.glass_uranium);
		OreDictionary.registerOre("blockGlassLime", ModBlocks.glass_trinitite);
		OreDictionary.registerOre("blockGlassRed", ModBlocks.glass_polonium);
		OreDictionary.registerOre("blockGlassBlack", ModBlocks.glass_ash);
	}
	
	@Untested
	public static String getReflector() {
		return GeneralConfig.enableReflectorCompat ? "plateDenseLead" : "plateTungCar"; //let's just mangle the name into "tungCar" so that it can't conflict with anything ever
	}
	
	public static class DictFrame {
		String[] mats;
		
		public DictFrame(String... mats) {
			this.mats = mats;
		}
		
		public DictFrame nugget(Item... nugget) {
			return makeItem("nugget", nugget).makeItem("tiny", nugget);
		}
		public DictFrame ingot(Item... ingot) {
			return makeItem("ingot", ingot);
		}
		public DictFrame dustSmall(Item... dustSmall) {
			return makeItem("dustSmall", dustSmall);
		}
		public DictFrame dust(Item... dust) {
			return makeItem("dust", dust);
		}
		public DictFrame gem(Item... gem) {
			return makeItem("gem", gem);
		}
		public DictFrame crystal(Item... crystal) {
			return makeItem("crystal", crystal);
		}
		public DictFrame plate(Item... plate) {
			return makeItem("plate", plate);
		}
		public DictFrame billet(Item... billet) {
			return makeItem("billet", billet);
		}
		
		public DictFrame block(Block... block) {
			return makeBlocks("block", block);
		}
		public DictFrame ore(Block... ore) {
			return makeBlocks("ore", ore);
		}
		public DictFrame oreNether(Block... oreNether) {
			return makeBlocks("oreNether", oreNether);
		}

		public DictFrame makeItem(String tag, Item... items) {
			for(String mat : mats) for(Item i : items) OreDictionary.registerOre(tag + mat, i);
			return this;
		}
		public DictFrame makeBlocks(String tag, Block... blocks) {
			for(String mat : mats) for(Block b : blocks) OreDictionary.registerOre(tag + mat, b);
			return this;
		}
	}
}
