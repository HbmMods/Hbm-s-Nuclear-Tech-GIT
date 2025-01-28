package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

//i love you
import static com.hbm.items.ModItems.*;
import static com.hbm.blocks.ModBlocks.*;
import static com.hbm.inventory.OreDictManager.DictFrame.*;
import static com.hbm.inventory.material.MaterialShapes.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockOreBasalt.EnumBasaltOreType;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.config.GeneralConfig;
import com.hbm.crafting.MineralRecipes;
import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.interfaces.NotableComments;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ItemEnums.EnumChunkType;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.special.ItemBedrockOre.EnumBedrockOre;
import com.hbm.main.MainRegistry;
import com.hbm.util.Compat;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

//the more i optimize this, the more it starts looking like gregtech
@NotableComments
public class OreDictManager {
	
	/** Alternate, additional names for ore dict registration. Used mostly for DictGroups */
	private static final HashMap<String, HashSet<String>> reRegistration = new HashMap();

	/*
	 * Standard keys
	 */
	public static final String KEY_STICK = "stickWood";					//if there's no "any" or "<shape>Any" prefix required, simply use a String key instead of a DictFrame
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
	public static final String KEY_SAND = "sand";
	public static final String KEY_COBBLESTONE = "cobblestone";
	
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

	public static final String KEY_OIL_TAR = "oiltar";
	public static final String KEY_CRACK_TAR = "cracktar";
	public static final String KEY_COAL_TAR = "coaltar";
	public static final String KEY_WOOD_TAR = "woodtar";

	public static final String KEY_UNIVERSAL_TANK = "ntmuniversaltank";
	public static final String KEY_HAZARD_TANK = "ntmhazardtank";
	public static final String KEY_UNIVERSAL_BARREL = "ntmuniversalbarrel";

	public static final String KEY_TOOL_SCREWDRIVER = "ntmscrewdriver";
	public static final String KEY_TOOL_HANDDRILL = "ntmhanddrill";
	public static final String KEY_TOOL_CHEMISTRYSET = "ntmchemistryset";
	public static final String KEY_TOOL_TORCH = "ntmtorch";

	public static final String KEY_GLYPHID_MEAT = "glyphidMeat";

	/*
	 * MATERIALS
	 */
	/*
	 * VANILLA
	 */
	public static final DictFrame WOOD = new DictFrame("Wood");
	public static final DictFrame BONE = new DictFrame("Bone");
	public static final DictFrame COAL = new DictFrame("Coal");
	public static final DictFrame IRON = new DictFrame("Iron");
	public static final DictFrame GOLD = new DictFrame("Gold");
	public static final DictFrame LAPIS = new DictFrame("Lapis");
	public static final DictFrame REDSTONE = new DictFrame("Redstone");
	public static final DictFrame NETHERQUARTZ = new DictFrame("NetherQuartz");
	public static final DictFrame QUARTZ = new DictFrame("Quartz");
	public static final DictFrame DIAMOND = new DictFrame("Diamond");
	public static final DictFrame EMERALD = new DictFrame("Emerald");
	/*
	 * RADIOACTIVE
	 */
	public static final DictFrame U = new DictFrame(Compat.isModLoaded(Compat.MOD_GT6) ? "Uraninite" : "Uranium");
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
	public static final DictFrame AC227 = new DictFrame("Actinium227", "Ac227");
	public static final DictFrame CO60 = new DictFrame("Cobalt60", "Co60");
	public static final DictFrame AU198 = new DictFrame("Gold198", "Au198");
	public static final DictFrame PB209 = new DictFrame("Lead209", "Pb209");
	public static final DictFrame SA326 = new DictFrame("Schrabidium");
	public static final DictFrame SA327 = new DictFrame("Solinium");
	public static final DictFrame SBD = new DictFrame("Schrabidate");
	public static final DictFrame SRN = new DictFrame("Schraranium");
	public static final DictFrame GH336 = new DictFrame("Ghiorsium336", "Gh336");
	public static final DictFrame MUD = new DictFrame("WatzMud");
	/*
	 * STABLE
	 */
	/** TITANIUM */ 
	public static final DictFrame TI = new DictFrame("Titanium");
	/** COPPER */ 
	public static final DictFrame CU = new DictFrame("Copper");
	public static final DictFrame MINGRADE = new DictFrame("Mingrade");
	public static final DictFrame ALLOY = new DictFrame("AdvancedAlloy");
	/** TUNGSTEN */ 
	public static final DictFrame W = new DictFrame("Tungsten");
	/** ALUMINUM */ 
	public static final DictFrame AL = new DictFrame("Aluminum");
	public static final DictFrame STEEL = new DictFrame("Steel");
	/** TECHNETIUM STEEL */ 
	public static final DictFrame TCALLOY = new DictFrame("TcAlloy");
	/** CADMIUM STEEL */
	public static final DictFrame CDALLOY = new DictFrame("CdAlloy");
	/** BISMUTH BRONZE */ 
	public static final DictFrame BBRONZE = new DictFrame("BismuthBronze");
	/** ARSENIC BRONZE */
	public static final DictFrame ABRONZE = new DictFrame("ArsenicBronze");
	/** BISMUTH STRONTIUM CALCIUM COPPER OXIDE */
	public static final DictFrame BSCCO = new DictFrame("BSCCO");
	/** LEAD */ 
	public static final DictFrame PB = new DictFrame("Lead");
	public static final DictFrame BI = new DictFrame("Bismuth");
	public static final DictFrame AS = new DictFrame("Arsenic");
	public static final DictFrame CA = new DictFrame("Calcium");
	public static final DictFrame CD = new DictFrame("Cadmium");
	/** TANTALUM */ 
	public static final DictFrame TA = new DictFrame("Tantalum");
	public static final DictFrame COLTAN = new DictFrame("Coltan");
	/** NIOBIUM */ 
	public static final DictFrame NB = new DictFrame("Niobium");
	/** BERYLLIUM */ 
	public static final DictFrame BE = new DictFrame("Beryllium");
	/** COBALT */ 
	public static final DictFrame CO = new DictFrame("Cobalt");
	/** BORON */ 
	public static final DictFrame B = new DictFrame("Boron");
	/** SILICON */ 
	public static final DictFrame SI = new DictFrame("Silicon");
	public static final DictFrame GRAPHITE = new DictFrame("Graphite");
	public static final DictFrame CARBON = new DictFrame("Carbon");
	public static final DictFrame DURA = new DictFrame("DuraSteel");
	public static final DictFrame POLYMER = new DictFrame("Polymer");
	public static final DictFrame BAKELITE = new DictFrame("Bakelite");
	public static final DictFrame PET = new DictFrame("PET");
	public static final DictFrame PC = new DictFrame("Polycarbonate");
	public static final DictFrame PVC = new DictFrame("PVC");
	public static final DictFrame LATEX = new DictFrame("Latex");
	public static final DictFrame RUBBER = new DictFrame("Rubber");
	public static final DictFrame MAGTUNG = new DictFrame("MagnetizedTungsten");
	public static final DictFrame CMB = new DictFrame("CMBSteel");
	public static final DictFrame DESH = new DictFrame("WorkersAlloy");
	public static final DictFrame STAR = new DictFrame("Starmetal");
	public static final DictFrame GUNMETAL = new DictFrame("GunMetal");
	public static final DictFrame WEAPONSTEEL = new DictFrame("WeaponSteel");
	public static final DictFrame BIGMT = new DictFrame("Saturnite");
	public static final DictFrame FERRO = new DictFrame("Ferrouranium");
	public static final DictFrame EUPH = new DictFrame("Euphemium");
	public static final DictFrame DNT = new DictFrame("Dineutronium");
	public static final DictFrame FIBER = new DictFrame("Fiberglass");
	public static final DictFrame ASBESTOS = new DictFrame("Asbestos");
	public static final DictFrame OSMIRIDIUM = new DictFrame("Osmiridium");
	/*
	 * DUST AND GEM ORES
	 */
	/** SULFUR */ 
	public static final DictFrame S = new DictFrame("Sulfur");
	/** SALTPETER/NITER */ 
	public static final DictFrame KNO = new DictFrame("Saltpeter");
	/** FLUORITE */ 
	public static final DictFrame F = new DictFrame("Fluorite");
	public static final DictFrame LIGNITE = new DictFrame("Lignite");
	public static final DictFrame COALCOKE = new DictFrame("CoalCoke");
	public static final DictFrame PETCOKE = new DictFrame("PetCoke");
	public static final DictFrame LIGCOKE = new DictFrame("LigniteCoke");
	public static final DictFrame CINNABAR = new DictFrame("Cinnabar");
	public static final DictFrame BORAX = new DictFrame("Borax");
	public static final DictFrame CHLOROCALCITE = new DictFrame("Chlorocalcite");
	public static final DictFrame MOLYSITE = new DictFrame("Molysite");
	public static final DictFrame SODALITE = new DictFrame("Sodalite");
	public static final DictFrame VOLCANIC = new DictFrame("Volcanic");
	public static final DictFrame HEMATITE = new DictFrame("Hematite");
	public static final DictFrame MALACHITE = new DictFrame("Malachite");
	public static final DictFrame LIMESTONE = new DictFrame("Limestone");
	public static final DictFrame SLAG = new DictFrame("Slag");
	/*
	 * HAZARDS, MISC
	 */
	/** LITHIUM */ 
	public static final DictFrame LI = new DictFrame("Lithium");
	/** SODIUM */
	public static final DictFrame NA = new DictFrame("Sodium");
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
	public static final DictFrame RAREEARTH = new DictFrame("RareEarth");
	/** LANTHANUM */ 
	public static final DictFrame LA = new DictFrame("Lanthanum");
	/** ZIRCONIUM */ 
	public static final DictFrame ZR = new DictFrame("Zirconium");
	/** NEODYMIUM */ 
	public static final DictFrame ND = new DictFrame("Neodymium");
	/** CERIUM */ 
	public static final DictFrame CE = new DictFrame("Cerium");
	/*
	 * NITAN
	 */
	/** IODINE */ 
	public static final DictFrame I = new DictFrame("Iodine");
	/** ASTATINE */ 
	public static final DictFrame AT = new DictFrame("Astatine");
	/** CAESIUM */ 
	public static final DictFrame CS = new DictFrame("Caesium");
	/** STRONTIUM */ 
	public static final DictFrame ST = new DictFrame("Strontium");
	/** BROMINE */ 
	public static final DictFrame BR = new DictFrame("Bromine");
	/** TENNESSINE */ 
	public static final DictFrame TS = new DictFrame("Tennessine") ;
	/*
	 * FISSION FRAGMENTS
	 */
	public static final DictFrame SR = new DictFrame("Strontium");
	public static final DictFrame SR90 = new DictFrame("Strontium90", "Sr90");
	public static final DictFrame I131 = new DictFrame("Iodine131", "I131");
	public static final DictFrame XE135 = new DictFrame("Xenon135", "Xe135");
	public static final DictFrame CS137 = new DictFrame("Caesium137", "Cs137");
	public static final DictFrame AT209 = new DictFrame("Astatine209", "At209");
	
	/*
	 * COLLECTIONS
	 */
	/** Any form of elastic polymer */
	public static final DictGroup ANY_RUBBER = new DictGroup("AnyRubber", LATEX, RUBBER);	
	/** Any post oil polymer like teflon ("polymer") or bakelite */
	public static final DictGroup ANY_PLASTIC = new DictGroup("AnyPlastic", POLYMER, BAKELITE);		//using the Any prefix means that it's just the secondary prefix, and that shape prefixes are applicable
	/** Any post vacuum polymer like PET or PVC */
	public static final DictGroup ANY_HARDPLASTIC = new DictGroup("AnyHardPlastic", PC, PVC);
	/** Any post nuclear steel like TCA or CDA */
	public static final DictGroup ANY_RESISTANTALLOY = new DictGroup("AnyResistantAlloy", TCALLOY, CDALLOY);
	/** Any post RBMK bronze like BB or AB */
	public static final DictGroup ANY_BISMOIDBRONZE = new DictGroup("AnyBismoidBronze", BBRONZE, ABRONZE);
	/** Any "powder" propellant like gunpowder, ballistite and cordite */
	public static final DictFrame ANY_GUNPOWDER = new DictFrame("AnyPropellant");
	/** Any smokeless powder like ballistite and cordite */
	public static final DictFrame ANY_SMOKELESS = new DictFrame("AnySmokeless");
	/** Any plastic explosive like semtex H or C-4 */
	public static final DictFrame ANY_PLASTICEXPLOSIVE = new DictFrame("AnyPlasticexplosive");
	/** Any higher tier high explosive (therefore excluding dynamite) like TNT */
	public static final DictFrame ANY_HIGHEXPLOSIVE = new DictFrame("AnyHighexplosive");
	public static final DictFrame ANY_COKE = new DictFrame("AnyCoke", "Coke");
	public static final DictFrame ANY_CONCRETE = new DictFrame("Concrete");			//no any prefix means that any has to be appended with the any() or anys() getters, registering works with the any (i.e. no shape) setter
	public static final DictGroup ANY_TAR = new DictGroup("Tar", KEY_OIL_TAR, KEY_COAL_TAR, KEY_CRACK_TAR, KEY_WOOD_TAR);
	/** Any special post-RBMK gating material, namely bismuth and arsenic */
	public static final DictFrame ANY_BISMOID = new DictFrame("AnyBismoid");
	public static final DictFrame ANY_ASH = new DictFrame("Ash");

	
	public static void registerOres() {

		/*
		 * VANILLA
		 */
		COAL.gem(Items.coal).dustSmall(powder_coal_tiny).dust(powder_coal);
		IRON.plate(plate_iron).dust(powder_iron).ore(ore_gneiss_iron);
		GOLD.plate(plate_gold).dust(powder_gold).ore(ore_gneiss_gold);
		LAPIS.dust(powder_lapis);
		NETHERQUARTZ.gem(Items.quartz).dust(powder_quartz).ore(Blocks.quartz_ore);
		QUARTZ.dust(powder_quartz);
		DIAMOND.dust(powder_diamond).ore(gravel_diamond, ore_sellafield_diamond);
		EMERALD.dust(powder_emerald).ore(ore_sellafield_emerald);
		
		/*
		 * RADIOACTIVE
		 */
		U		.rad(HazardRegistry.u)								.nugget(nugget_uranium)		.billet(billet_uranium)		.ingot(ingot_uranium)		.dust(powder_uranium)									.block(block_uranium)		.ore(ore_uranium, ore_uranium_scorched, ore_gneiss_uranium, ore_gneiss_uranium_scorched, ore_nether_uranium, ore_nether_uranium_scorched, ore_sellafield_uranium_scorched)	.oreNether(ore_nether_uranium, ore_nether_uranium_scorched);
		U233	.rad(HazardRegistry.u233)							.nugget(nugget_u233)		.billet(billet_u233)		.ingot(ingot_u233)																	.block(block_u233);
		U235	.rad(HazardRegistry.u235)							.nugget(nugget_u235)		.billet(billet_u235)		.ingot(ingot_u235)																	.block(block_u235);
		U238	.rad(HazardRegistry.u238)							.nugget(nugget_u238)		.billet(billet_u238)		.ingot(ingot_u238)																	.block(block_u238);
		TH232	.rad(HazardRegistry.th232)							.nugget(nugget_th232)		.billet(billet_th232)		.ingot(ingot_th232)			.dust(powder_thorium)									.block(block_thorium)		.ore(ore_thorium);
		PU		.rad(HazardRegistry.pu)								.nugget(nugget_plutonium)	.billet(billet_plutonium)	.ingot(ingot_plutonium)		.dust(powder_plutonium)									.block(block_plutonium)		.ore(ore_nether_plutonium)	.oreNether(ore_nether_plutonium);
		PURG	.rad(HazardRegistry.purg)							.nugget(nugget_pu_mix)		.billet(billet_pu_mix)		.ingot(ingot_pu_mix)																.block(block_pu_mix);
		PU238	.rad(HazardRegistry.pu238)	.hot(3F)				.nugget(nugget_pu238)		.billet(billet_pu238)		.ingot(ingot_pu238)																	.block(block_pu238);
		PU239	.rad(HazardRegistry.pu239)							.nugget(nugget_pu239)		.billet(billet_pu239)		.ingot(ingot_pu239)																	.block(block_pu239);
		PU240	.rad(HazardRegistry.pu240)							.nugget(nugget_pu240)		.billet(billet_pu240)		.ingot(ingot_pu240)																	.block(block_pu240);
		PU241	.rad(HazardRegistry.pu241)							.nugget(nugget_pu241)		.billet(billet_pu241)		.ingot(ingot_pu241);																//.block(block_pu241);
		AM241	.rad(HazardRegistry.am241)							.nugget(nugget_am241)		.billet(billet_am241)		.ingot(ingot_am241);
		AM242	.rad(HazardRegistry.am242)							.nugget(nugget_am242)		.billet(billet_am242)		.ingot(ingot_am242);
		AMRG	.rad(HazardRegistry.amrg)							.nugget(nugget_am_mix)		.billet(billet_am_mix)		.ingot(ingot_am_mix);
		NP237	.rad(HazardRegistry.np237)							.nugget(nugget_neptunium)	.billet(billet_neptunium)	.ingot(ingot_neptunium)		.dust(powder_neptunium)									.block(block_neptunium);
		PO210	.rad(HazardRegistry.po210)	.hot(3)					.nugget(nugget_polonium)	.billet(billet_polonium)	.ingot(ingot_polonium)		.dust(powder_polonium)									.block(block_polonium);
		TC99	.rad(HazardRegistry.tc99)							.nugget(nugget_technetium)	.billet(billet_technetium)	.ingot(ingot_technetium);
		RA226	.rad(HazardRegistry.ra226)							.nugget(nugget_ra226)		.billet(billet_ra226)		.ingot(ingot_ra226)			.dust(powder_ra226)										.block(block_ra226);
		AC227	.rad(HazardRegistry.ac227)							.nugget(nugget_actinium)	.billet(billet_actinium)	.ingot(ingot_actinium)		.dust(powder_actinium)									.block(block_actinium)		.dustSmall(powder_actinium_tiny);
		CO60	.rad(HazardRegistry.co60)	.hot(1)					.nugget(nugget_co60)		.billet(billet_co60)		.ingot(ingot_co60)			.dust(powder_co60);
		AU198	.rad(HazardRegistry.au198)	.hot(5)					.nugget(nugget_au198)		.billet(billet_au198)		.ingot(ingot_au198)			.dust(powder_au198);
		PB209	.rad(HazardRegistry.pb209)	.blinding(50F)	.hot(7)	.nugget(nugget_pb209)		.billet(billet_pb209)		.ingot(ingot_pb209);
		SA326	.rad(HazardRegistry.sa326)	.blinding(50F)			.nugget(nugget_schrabidium)	.billet(billet_schrabidium)	.ingot(ingot_schrabidium)	.dust(powder_schrabidium).plate(plate_schrabidium).plateCast(Mats.MAT_SCHRABIDIUM.make(plate_cast)).block(block_schrabidium).ore(ore_schrabidium, ore_gneiss_schrabidium, ore_nether_schrabidium, ore_sellafield_schrabidium)	.oreNether(ore_nether_schrabidium);
		SA327	.rad(HazardRegistry.sa327)	.blinding(50F)			.nugget(nugget_solinium)	.billet(billet_solinium)	.ingot(ingot_solinium)																.block(block_solinium);
		SBD		.rad(HazardRegistry.sb)		.blinding(50F)																	.ingot(ingot_schrabidate)	.dust(powder_schrabidate)								.block(block_schrabidate);
		SRN		.rad(HazardRegistry.sr)		.blinding(50F)																	.ingot(ingot_schraranium)															.block(block_schraranium);
		GH336	.rad(HazardRegistry.gh336)							.nugget(nugget_gh336)		.billet(billet_gh336)		.ingot(ingot_gh336);
		MUD		.rad(HazardRegistry.mud)																					.ingot(ingot_mud);
		
		/*
		 * STABLE
		 */
		TI																	.ingot(ingot_titanium)												.dust(powder_titanium)			.plate(plate_titanium)			.block(block_titanium)		.ore(ore_titanium);
		CU																	.ingot(ingot_copper)												.dust(powder_copper)			.plate(plate_copper)			.block(block_copper)		.ore(ore_copper, ore_gneiss_copper);
		MINGRADE															.ingot(ingot_red_copper)											.dust(powder_red_copper)										.block(block_red_copper);
		ALLOY																.ingot(ingot_advanced_alloy)										.dust(powder_advanced_alloy)	.plate(plate_advanced_alloy)	.block(block_advanced_alloy);
		W																	.ingot(ingot_tungsten)												.dust(powder_tungsten)											.block(block_tungsten)		.ore(ore_tungsten, ore_nether_tungsten)	.oreNether(ore_nether_tungsten);
		AL																	.ingot(ingot_aluminium)												.dust(powder_aluminium)			.plate(plate_aluminium)			.block(block_aluminium)		.ore(ore_aluminium);
		STEEL																.ingot(ingot_steel)				.dustSmall(powder_steel_tiny)		.dust(powder_steel)				.plate(plate_steel)				.block(block_steel);
		TCALLOY																.ingot(ingot_tcalloy)												.dust(powder_tcalloy)											.block(block_tcalloy);
		CDALLOY																.ingot(ingot_cdalloy)																												.block(block_cdalloy);
		BBRONZE																.ingot(ingot_bismuth_bronze);
		ABRONZE																.ingot(ingot_arsenic_bronze);
		BSCCO																.ingot(ingot_bscco);
		PB			.nugget(nugget_lead)									.ingot(ingot_lead)													.dust(powder_lead)				.plate(plate_lead)				.block(block_lead)			.ore(ore_lead);
		BI			.nugget(nugget_bismuth)		.billet(billet_bismuth)		.ingot(ingot_bismuth)												.dust(powder_bismuth)											.block(block_bismuth);
		AS			.nugget(nugget_arsenic)									.ingot(ingot_arsenic);
		CA																	.ingot(ingot_calcium)												.dust(powder_calcium);
		CD																	.ingot(ingot_cadmium)												.dust(powder_cadmium)											.block(block_cadmium);
		TA			.nugget(nugget_tantalium)	.gem(gem_tantalium)			.ingot(ingot_tantalium)												.dust(powder_tantalium)											.block(block_tantalium);
		COLTAN																.ingot(fragment_coltan)												.dust(powder_coltan_ore)										.block(block_coltan)		.ore(ore_coltan);
		NB			.nugget(nugget_niobium, fragment_niobium)				.ingot(ingot_niobium)			.dustSmall(powder_niobium_tiny)		.dust(powder_niobium)											.block(block_niobium);
		BE			.nugget(nugget_beryllium)	.billet(billet_beryllium)	.ingot(ingot_beryllium)												.dust(powder_beryllium)											.block(block_beryllium)		.ore(ore_beryllium);
		CO			.nugget(fragment_cobalt)	.nugget(nugget_cobalt)		.billet(billet_cobalt)			.ingot(ingot_cobalt)				.dust(powder_cobalt)			.dustSmall(powder_cobalt_tiny)	.block(block_cobalt)		.ore(ore_cobalt, ore_nether_cobalt);
		B			.nugget(fragment_boron)									.ingot(ingot_boron)				.dustSmall(powder_boron_tiny)		.dust(powder_boron)												.block(block_boron);
		SI			.nugget(nugget_silicon)		.billet(billet_silicon)		.ingot(ingot_silicon);
		GRAPHITE															.ingot(ingot_graphite)																												.block(block_graphite);
		CARBON																.ingot(ingot_graphite)																												.block(block_graphite);
		DURA																.ingot(ingot_dura_steel)											.dust(powder_dura_steel)		.plate(plate_dura_steel)		.block(block_dura_steel);
		POLYMER																.ingot(ingot_polymer)												.dust(powder_polymer)											.block(block_polymer);
		BAKELITE															.ingot(ingot_bakelite)												.dust(powder_bakelite)											.block(block_bakelite);
		LATEX									.gem(ball_resin)			.ingot(ingot_biorubber);
		RUBBER																.ingot(ingot_rubber)																												.block(block_rubber);
		//PET																	.ingot(ingot_pet);
		PC																	.ingot(ingot_pc);
		PVC																	.ingot(ingot_pvc);
		MAGTUNG																.ingot(ingot_magnetized_tungsten)									.dust(powder_magnetized_tungsten)								.block(block_magnetized_tungsten);
		CMB																	.ingot(ingot_combine_steel)											.dust(powder_combine_steel)		.plate(plate_combine_steel)		.block(block_combine_steel);
		DESH		.nugget(nugget_desh)									.ingot(ingot_desh)													.dust(powder_desh)												.block(block_desh);
		STAR																.ingot(ingot_starmetal)																												.block(block_starmetal);
		GUNMETAL															.ingot(ingot_gunmetal)																				.plate(plate_gunmetal);
		WEAPONSTEEL															.ingot(ingot_weaponsteel)																			.plate(plate_weaponsteel);
		BIGMT																.ingot(ingot_saturnite)																				.plate(plate_saturnite);
		FERRO																.ingot(ingot_ferrouranium);
		EUPH		.nugget(nugget_euphemium)								.ingot(ingot_euphemium)												.dust(powder_euphemium)											.block(block_euphemium);
		DNT			.nugget(nugget_dineutronium)							.ingot(ingot_dineutronium)											.dust(powder_dineutronium)										.block(block_dineutronium);
		FIBER																.ingot(ingot_fiberglass)																											.block(block_fiberglass);
		ASBESTOS	.asbestos(1F)											.ingot(ingot_asbestos)												.dust(powder_asbestos)											.block(block_asbestos)		.ore(ore_asbestos, ore_gneiss_asbestos, DictFrame.fromOne(ore_basalt, EnumBasaltOreType.ASBESTOS), DictFrame.fromOne(stone_resource, EnumStoneType.ASBESTOS));
		OSMIRIDIUM	.nugget(nugget_osmiridium)								.ingot(ingot_osmiridium);

		/*
		 * DUST AND GEM ORES
		 */
		S																				.dust(sulfur)			.block(block_sulfur)	.ore(ore_sulfur, ore_nether_sulfur, DictFrame.fromOne(ore_basalt, EnumBasaltOreType.SULFUR), DictFrame.fromOne(stone_resource, EnumStoneType.SULFUR))	.oreNether(ore_nether_sulfur);
		KNO																				.dust(niter)			.block(block_niter)		.ore(ore_niter);
		F																				.dust(fluorite)			.block(block_fluorite)	.ore(ore_fluorite, DictFrame.fromOne(ore_basalt, EnumBasaltOreType.FLUORITE));
		LIGNITE							.gem(lignite)									.dust(powder_lignite)							.ore(ore_lignite);
		COALCOKE						.gem(fromOne(coke, EnumCokeType.COAL))									.block(fromOne(block_coke, EnumCokeType.COAL));
		PETCOKE							.gem(fromOne(coke, EnumCokeType.PETROLEUM))								.block(fromOne(block_coke, EnumCokeType.PETROLEUM));
		LIGCOKE							.gem(fromOne(coke, EnumCokeType.LIGNITE))								.block(fromOne(block_coke, EnumCokeType.LIGNITE));
		CINNABAR	.crystal(cinnebar)	.gem(cinnebar)																					.ore(ore_cinnebar, ore_depth_cinnebar);
		BORAX																			.dust(powder_borax)								.ore(ore_depth_borax);
		CHLOROCALCITE																	.dust(powder_chlorocalcite);
		MOLYSITE																		.dust(powder_molysite)							.ore(DictFrame.fromOne(ore_basalt, EnumBasaltOreType.MOLYSITE));
		SODALITE						.gem(gem_sodalite);
		VOLCANIC						.gem(gem_volcanic)																				.ore(DictFrame.fromOne(ore_basalt, EnumBasaltOreType.GEM));
		HEMATITE																														.ore(fromOne(stone_resource, EnumStoneType.HEMATITE));
		MALACHITE						.ingot(DictFrame.fromOne(chunk_ore, EnumChunkType.MALACHITE))									.ore(fromOne(stone_resource, EnumStoneType.MALACHITE));
		LIMESTONE																		.dust(powder_limestone)							.ore(fromOne(stone_resource, EnumStoneType.LIMESTONE));
		SLAG																									.block(block_slag);
		
		/*
		 * HAZARDS, MISC
		 */
		LI	.hydro(1F)	.ingot(lithium)	.dustSmall(powder_lithium_tiny)	.dust(powder_lithium)	.block(block_lithium)	.ore(ore_gneiss_lithium);
		NA	.hydro(1F)													.dust(powder_sodium);

		/*
		 * PHOSPHORUS
		 */
		P_WHITE	.hot(5)	.ingot(ingot_phosphorus)	.block(block_white_phosphorus);
		P_RED			.dust(powder_fire)			.block(block_red_phosphorus);
		
		/*
		 * RARE METALS
		 */
		AUSTRALIUM	.nugget(nugget_australium)	.billet(billet_australium)	.ingot(ingot_australium)	.dust(powder_australium)	.block(block_australium)	.ore(ore_australium);
		REIIUM		.block(block_reiium);
		WEIDANIUM	.block(block_weidanium);
		UNOBTAINIUM	.block(block_unobtainium);
		VERTICIUM	.block(block_verticium);
		DAFFERGON	.block(block_daffergon);

		/*
		 * RARE EARTHS
		 */
		RAREEARTH.ingot(DictFrame.fromOne(ModItems.chunk_ore, EnumChunkType.RARE)).ore(ore_rare, ore_gneiss_rare);
		LA	.nugget(fragment_lanthanium)	.ingot(ingot_lanthanium)										.dustSmall(powder_lanthanium_tiny)	.dust(powder_lanthanium)	.block(block_lanthanium);
		ZR	.nugget(nugget_zirconium)		.ingot(ingot_zirconium)		.billet(billet_zirconium)												.dust(powder_zirconium)		.block(block_zirconium)		.ore(ore_depth_zirconium);
		ND	.nugget(fragment_neodymium)																		.dustSmall(powder_neodymium_tiny)	.dust(powder_neodymium)									.ore(ore_depth_nether_neodymium)	.oreNether(ore_depth_nether_neodymium);
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
		SR									.hot(1F)	.hydro(1F)									.dust(powder_strontium);
		SR90	.rad(HazardRegistry.sr90)	.hot(1F)	.hydro(1F)	.dustSmall(powder_sr90_tiny)	.dust(powder_sr90)	.ingot(ingot_sr90)	.billet(billet_sr90)	.nugget(nugget_sr90);
		I131	.rad(HazardRegistry.i131)	.hot(1F)				.dustSmall(powder_i131_tiny)	.dust(powder_i131);
		XE135	.rad(HazardRegistry.xe135)	.hot(10F)				.dustSmall(powder_xe135_tiny)	.dust(powder_xe135);
		CS137	.rad(HazardRegistry.cs137)	.hot(3F)	.hydro(3F)	.dustSmall(powder_cs137_tiny)	.dust(powder_cs137);
		AT209	.rad(HazardRegistry.at209)	.hot(20F)												.dust(powder_at209);
		
		/*
		 * COLLECTIONS
		 */
		ANY_GUNPOWDER			.dust(Items.gunpowder, ballistite, cordite);
		ANY_SMOKELESS			.dust(ballistite, cordite);
		ANY_PLASTICEXPLOSIVE	.ingot(ingot_semtex, ingot_c4);
		ANY_HIGHEXPLOSIVE		.ingot(ball_tnt).ingot(ball_tatb);
		ANY_CONCRETE			.any(concrete, concrete_smooth, concrete_asbestos, ducrete, ducrete_smooth);
		for(int i = 0; i < 16; i++) { ANY_CONCRETE.any(new ItemStack(ModBlocks.concrete_colored, 1, i)); }
		for(int i = 0; i < 16; i++) { ANY_CONCRETE.any(new ItemStack(ModBlocks.concrete_colored_ext, 1, i)); }
		ANY_COKE				.gem(fromAll(coke, EnumCokeType.class)).block(fromAll(block_coke, EnumCokeType.class));
		ANY_BISMOID				.ingot(ingot_bismuth, ingot_arsenic).nugget(nugget_bismuth, nugget_arsenic).block(block_bismuth);
		ANY_ASH					.any(fromOne(ModItems.powder_ash, EnumAshType.WOOD), fromOne(ModItems.powder_ash, EnumAshType.COAL), fromOne(ModItems.powder_ash, EnumAshType.MISC), fromOne(ModItems.powder_ash, EnumAshType.FLY), fromOne(ModItems.powder_ash, EnumAshType.SOOT));

		/*
		 * TAR
		 */
		OreDictionary.registerOre(KEY_OIL_TAR, fromOne(oil_tar, EnumTarType.CRUDE));
		OreDictionary.registerOre(KEY_CRACK_TAR, fromOne(oil_tar, EnumTarType.CRACK));
		OreDictionary.registerOre(KEY_COAL_TAR, fromOne(oil_tar, EnumTarType.COAL));
		OreDictionary.registerOre(KEY_WOOD_TAR, fromOne(oil_tar, EnumTarType.WOOD));

		/*
		 * TANKS
		 */
		OreDictionary.registerOre(KEY_UNIVERSAL_TANK, new ItemStack(fluid_tank_full, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(KEY_HAZARD_TANK, new ItemStack(fluid_tank_lead_full, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(KEY_UNIVERSAL_BARREL, new ItemStack(fluid_barrel_full, 1, OreDictionary.WILDCARD_VALUE));

		/*
		 * TOOLS
		 */
		OreDictionary.registerOre(KEY_TOOL_SCREWDRIVER, new ItemStack(screwdriver, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(KEY_TOOL_SCREWDRIVER, new ItemStack(screwdriver_desh, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(KEY_TOOL_HANDDRILL, new ItemStack(hand_drill, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(KEY_TOOL_HANDDRILL, new ItemStack(hand_drill_desh, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(KEY_TOOL_CHEMISTRYSET, new ItemStack(chemistry_set, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(KEY_TOOL_CHEMISTRYSET, new ItemStack(chemistry_set_boron, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(KEY_TOOL_TORCH, new ItemStack(blowtorch, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(KEY_TOOL_TORCH, new ItemStack(acetylene_torch, 1, OreDictionary.WILDCARD_VALUE));

		/*
		 * GLYPHID M E A T
		 */
		OreDictionary.registerOre(KEY_GLYPHID_MEAT, new ItemStack(glyphid_meat));
		OreDictionary.registerOre(KEY_GLYPHID_MEAT, new ItemStack(glyphid_meat_grilled));

		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.smeltable == SmeltingBehavior.SMELTABLE) {
				if(mat.autogen.contains(MaterialShapes.CASTPLATE)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.CASTPLATE.name() + name, new ItemStack(ModItems.plate_cast, 1, mat.id));
				if(mat.autogen.contains(MaterialShapes.WELDEDPLATE)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.WELDEDPLATE.name() + name, new ItemStack(ModItems.plate_welded, 1, mat.id));
				if(mat.autogen.contains(MaterialShapes.HEAVY_COMPONENT)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.HEAVY_COMPONENT.name() + name, new ItemStack(ModItems.heavy_component, 1, mat.id));
				if(mat.autogen.contains(MaterialShapes.DENSEWIRE)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.DENSEWIRE.name() + name, new ItemStack(ModItems.wire_dense, 1, mat.id));
			}
			if(mat.autogen.contains(MaterialShapes.BOLT)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.BOLT.name() + name, new ItemStack(ModItems.bolt, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.INGOT)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.INGOT.name() + name, new ItemStack(ModItems.ingot_raw, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.SHELL)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.SHELL.name() + name, new ItemStack(ModItems.shell, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.PIPE)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.PIPE.name() + name, new ItemStack(ModItems.pipe, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.FRAGMENT)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.FRAGMENT.name() + name, new ItemStack(ModItems.bedrock_ore_fragment, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.WIRE)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.WIRE.name() + name, new ItemStack(ModItems.wire_fine, 1, mat.id));

			if(mat.autogen.contains(MaterialShapes.LIGHTBARREL)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.LIGHTBARREL.name() + name, new ItemStack(ModItems.part_barrel_light, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.HEAVYBARREL)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.HEAVYBARREL.name() + name, new ItemStack(ModItems.part_barrel_heavy, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.LIGHTRECEIVER)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.LIGHTRECEIVER.name() + name, new ItemStack(ModItems.part_receiver_light, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.HEAVYRECEIVER)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.HEAVYRECEIVER.name() + name, new ItemStack(ModItems.part_receiver_heavy, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.MECHANISM)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.MECHANISM.name() + name, new ItemStack(ModItems.part_mechanism, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.STOCK)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.STOCK.name() + name, new ItemStack(ModItems.part_stock, 1, mat.id));
			if(mat.autogen.contains(MaterialShapes.GRIP)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.GRIP.name() + name, new ItemStack(ModItems.part_grip, 1, mat.id));
		}
		
		for(EnumBedrockOre ore : EnumBedrockOre.values()) {
			OreDictionary.registerOre("ore" + ore.oreName, new ItemStack(ModItems.ore_enriched, 1, ore.ordinal()));
		}

		OreDictionary.registerOre("itemRubber", ingot_rubber);

		OreDictionary.registerOre("coalCoke", fromOne(coke, EnumCokeType.COAL));
		
		for(String name : new String[] {"fuelCoke", "coke"}) {
			OreDictionary.registerOre(name, fromOne(coke, EnumCokeType.COAL));
			OreDictionary.registerOre(name, fromOne(coke, EnumCokeType.LIGNITE));
			OreDictionary.registerOre(name, fromOne(coke, EnumCokeType.PETROLEUM));
		}
		
		OreDictionary.registerOre("briquetteCoal", fromOne(briquette, EnumBriquetteType.COAL));
		OreDictionary.registerOre("briquetteLignite", fromOne(briquette, EnumBriquetteType.LIGNITE));
		OreDictionary.registerOre("briquetteWood", fromOne(briquette, EnumBriquetteType.WOOD));
		
		OreDictionary.registerOre(getReflector(), neutron_reflector);

		OreDictionary.registerOre("logWood", pink_log);
		OreDictionary.registerOre("logWoodPink", pink_log);
		OreDictionary.registerOre("plankWood", pink_planks);
		OreDictionary.registerOre("plankWoodPink", pink_planks);
		OreDictionary.registerOre("slabWood", pink_slab);
		OreDictionary.registerOre("slabWoodPink", pink_slab);
		OreDictionary.registerOre("stairWood", pink_stairs);
		OreDictionary.registerOre("stairWoodPink", pink_stairs);
		
		String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
		for(int i = 0; i < 16; i++) {
			String dyeName = "dye" + dyes[i];
			
			OreDictionary.registerOre(dyeName, new ItemStack(ModItems.chemical_dye, 1, i));
			
			OreDictionary.registerOre(dyeName, new ItemStack(ModItems.crayon, 1, i));
		}
		OreDictionary.registerOre("dye", new ItemStack(chemical_dye, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("dye", new ItemStack(crayon, 1, OreDictionary.WILDCARD_VALUE));

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
		OreDictionary.registerOre("dyeBlack", fromOne(oil_tar, EnumTarType.CRUDE));
		OreDictionary.registerOre("dyeBlack", fromOne(oil_tar, EnumTarType.CRACK));
		OreDictionary.registerOre("dyeGray", fromOne(oil_tar, EnumTarType.COAL));
		OreDictionary.registerOre("dyeBrown", fromOne(oil_tar, EnumTarType.WOOD));
		OreDictionary.registerOre("dyeCyan", fromOne(oil_tar, EnumTarType.WAX));
		OreDictionary.registerOre("dyeWhite", fromOne(oil_tar, EnumTarType.PARAFFIN));
		OreDictionary.registerOre("dye", new ItemStack(oil_tar, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("dyeOrange", powder_cadmium);
		OreDictionary.registerOre("dye", powder_cadmium);
		OreDictionary.registerOre("dyeLightGray", fromOne(powder_ash, EnumAshType.WOOD));
		OreDictionary.registerOre("dyeBlack", fromOne(powder_ash, EnumAshType.COAL));
		OreDictionary.registerOre("dyeGray", fromOne(powder_ash, EnumAshType.MISC));
		OreDictionary.registerOre("dyeBrown", fromOne(powder_ash, EnumAshType.FLY));
		OreDictionary.registerOre("dyeBlack", fromOne(powder_ash, EnumAshType.SOOT));
		OreDictionary.registerOre("dyeMagenta", fromOne(powder_ash, EnumAshType.FULLERENE));
		OreDictionary.registerOre("dye", new ItemStack(powder_ash, 1, OreDictionary.WILDCARD_VALUE));

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

		OreDictionary.registerOre("container1000lubricant", bdcl);
		OreDictionary.registerOre("itemSilicon", billet_silicon);
		
		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.autogen.contains(MaterialShapes.FRAGMENT)) {
				String name = mat.names[0];
				if(!OreDictionary.getOres(MaterialShapes.DUST.name() + name).isEmpty()) MineralRecipes.add9To1(mat.make(ModItems.bedrock_ore_fragment), OreDictionary.getOres(MaterialShapes.DUST.name() + name).get(0));
				else if(!OreDictionary.getOres(MaterialShapes.GEM.name() + name).isEmpty()) MineralRecipes.add9To1(mat.make(ModItems.bedrock_ore_fragment), OreDictionary.getOres(MaterialShapes.GEM.name() + name).get(0));
				else if(!OreDictionary.getOres(MaterialShapes.CRYSTAL.name() + name).isEmpty()) MineralRecipes.add9To1(mat.make(ModItems.bedrock_ore_fragment), OreDictionary.getOres(MaterialShapes.CRYSTAL.name() + name).get(0));
				else if(!OreDictionary.getOres(MaterialShapes.INGOT.name() + name).isEmpty()) MineralRecipes.add9To1(mat.make(ModItems.bedrock_ore_fragment), OreDictionary.getOres(MaterialShapes.INGOT.name() + name).get(0));
				else if(!OreDictionary.getOres(MaterialShapes.BILLET.name() + name).isEmpty()) MineralRecipes.addBilletFragment(OreDictionary.getOres(MaterialShapes.BILLET.name() + name).get(0), mat.make(ModItems.bedrock_ore_fragment));
				else MineralRecipes.add9To1(mat.make(ModItems.bedrock_ore_fragment), new ItemStack(ModItems.nothing));
			}
		}
		
		MaterialShapes.registerCompatShapes();
		compensateMojangSpaghettiBullshit();
	}
	
	public static String getReflector() {
		return GeneralConfig.enableReflectorCompat ? "plateDenseLead" : "plateTungCar"; //let's just mangle the name into "tungCar" so that it can't conflict with anything ever
	}
	
	public static void registerGroups() {
		ANY_RUBBER.addPrefix(INGOT, true);
		ANY_PLASTIC.addPrefix(INGOT, true).addPrefix(DUST, true).addPrefix(BLOCK, true).addPrefix(GRIP, true).addPrefix(STOCK, true);
		ANY_HARDPLASTIC.addPrefix(INGOT, true).addPrefix(STOCK, true).addPrefix(GRIP, true);
		ANY_RESISTANTALLOY.addPrefix(INGOT, true).addPrefix(DUST, true).addPrefix(CASTPLATE, true).addPrefix(WELDEDPLATE, true).addPrefix(HEAVY_COMPONENT, true).addPrefix(BLOCK, true)
		.addPrefix(LIGHTBARREL, true).addPrefix(HEAVYBARREL, true).addPrefix(LIGHTRECEIVER, true).addPrefix(HEAVYRECEIVER, true);
		ANY_BISMOIDBRONZE.addPrefix(INGOT, true).addPrefix(CASTPLATE, true).addPrefix(LIGHTBARREL, true).addPrefix(HEAVYBARREL, true).addPrefix(LIGHTRECEIVER, true).addPrefix(HEAVYRECEIVER, true);
		ANY_TAR.addPrefix(ANY, false);
	}
	
	private static boolean recursionBrake = false;
	
	@SubscribeEvent
	public void onRegisterOre(OreRegisterEvent event) {
		if(recursionBrake) return;
		
		recursionBrake = true;
		
		HashSet<String> strings = reRegistration.get(event.Name);
		
		if(strings != null) {
			for(String name : strings) {
				OreDictionary.registerOre(name, event.Ore);
				MainRegistry.logger.info("Re-registration for " + event.Name + " to " + name);
			}
		}
		
		recursionBrake = false;

		if(event.Name.startsWith("ingot") || event.Name.startsWith("ore") || event.Name.startsWith("plate") || event.Name.startsWith("block")) {
			arcSmeltable.add(new ComparableStack(event.Ore));
		}
	}

	public static final HashSet<ComparableStack> arcSmeltable = new HashSet();
	
	/** Vanilla item ore dict registration events never actually register in the ODM because vanilla items are registered so early that the ODM event handler doesn't exist yet. */
	public static void compensateMojangSpaghettiBullshit() {

		arcSmeltable.add(new ComparableStack(Blocks.gold_ore));
		arcSmeltable.add(new ComparableStack(Blocks.iron_ore));
		arcSmeltable.add(new ComparableStack(Blocks.lapis_ore));
		arcSmeltable.add(new ComparableStack(Blocks.diamond_ore));
		arcSmeltable.add(new ComparableStack(Blocks.redstone_ore));
		arcSmeltable.add(new ComparableStack(Blocks.emerald_ore));
		arcSmeltable.add(new ComparableStack(Blocks.quartz_ore));
		arcSmeltable.add(new ComparableStack(Blocks.gold_block));
		arcSmeltable.add(new ComparableStack(Blocks.iron_block));
		arcSmeltable.add(new ComparableStack(Blocks.lapis_block));
		arcSmeltable.add(new ComparableStack(Blocks.diamond_block));
		arcSmeltable.add(new ComparableStack(Blocks.redstone_block));
		arcSmeltable.add(new ComparableStack(Blocks.emerald_block));
		arcSmeltable.add(new ComparableStack(Blocks.quartz_block));
		arcSmeltable.add(new ComparableStack(Items.iron_ingot));
		arcSmeltable.add(new ComparableStack(Items.gold_ingot));
		arcSmeltable.add(new ComparableStack(Items.brick));
		arcSmeltable.add(new ComparableStack(Items.netherbrick));
	}
	
	public static class DictFrame {
		public String[] mats;
		float hazMult = 1.0F;
		List<HazardEntry> hazards = new ArrayList();
		
		public DictFrame(String... mats) {
			this.mats = mats;
		}

		/*
		 * Quick access methods to grab ore names for recipes.
		 */
		public String any() {			return ANY.name()				+ mats[0]; }
		public String nugget() {		return NUGGET.name()			+ mats[0]; }
		public String tiny() {			return TINY.name()				+ mats[0]; }
		public String bolt() {			return BOLT.name()				+ mats[0]; }
		public String ingot() {			return INGOT.name()				+ mats[0]; }
		public String dustTiny() {		return DUSTTINY.name()			+ mats[0]; }
		public String dust() {			return DUST.name()				+ mats[0]; }
		public String gem() {			return GEM.name()				+ mats[0]; }
		public String crystal() {		return CRYSTAL.name()			+ mats[0]; }
		public String plate() {			return PLATE.name()				+ mats[0]; }
		public String plateCast() {		return CASTPLATE.name()			+ mats[0]; }
		public String plateWelded() {	return WELDEDPLATE.name()		+ mats[0]; }
		public String heavyComp() {		return HEAVY_COMPONENT.name()	+ mats[0]; }
		public String wireFine() {		return WIRE.name()				+ mats[0]; }
		public String wireDense() {		return DENSEWIRE.name()			+ mats[0]; }
		public String shell() {			return SHELL.name()				+ mats[0]; }
		public String pipe() {			return PIPE.name()				+ mats[0]; }
		public String billet() {		return BILLET.name()			+ mats[0]; }
		public String block() {			return BLOCK.name()				+ mats[0]; }
		public String ore() {			return ORE.name()				+ mats[0]; }
		public String fragment() {		return FRAGMENT.name()			+ mats[0]; }
		public String lightBarrel() {	return LIGHTBARREL.name()		+ mats[0]; }
		public String heavyBarrel() {	return HEAVYBARREL.name()		+ mats[0]; }
		public String lightReceiver() {	return LIGHTRECEIVER.name()		+ mats[0]; }
		public String heavyReceiver() {	return HEAVYRECEIVER.name()		+ mats[0]; }
		public String mechanism() {		return MECHANISM.name()			+ mats[0]; }
		public String stock() {			return STOCK.name()				+ mats[0]; }
		public String grip() {			return GRIP.name()				+ mats[0]; }
		public String[] all(MaterialShapes shape) {				return appendToAll(shape.prefixes); }
		
		/** Returns cast (triple) plates if 528 mode is enabled or normal plates if not */
		public String plate528() { return GeneralConfig.enable528 ? plateCast() : plate(); }
		
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
		
		/** Returns an ItemStack composed of the supplied item with the meta being the enum's ordinal. Purely syntactic candy */
		public static ItemStack fromOne(Item item, Enum en) {
			return new ItemStack(item, 1, en.ordinal());
		}
		public static ItemStack fromOne(Block block, Enum en) {
			return new ItemStack(block, 1, en.ordinal());
		}
		public static ItemStack fromOne(Item item, Enum en, int stacksize) {
			return new ItemStack(item, stacksize, en.ordinal());
		}
		public static ItemStack fromOne(Block block, Enum en, int stacksize) {
			return new ItemStack(block, stacksize, en.ordinal());
		}
		/** Same as fromOne but with an array of ItemStacks. The array type is Object[] so that the ODM methods work with it. Generates ItemStacks for the entire enum class. */
		public static Object[] fromAll(Item item, Class<? extends Enum> en) {
			Enum[] vals = en.getEnumConstants();
			Object[] stacks = new Object[vals.length];
			
			for(int i = 0; i < vals.length; i++) {
				stacks[i] = new ItemStack(item, 1, vals[i].ordinal());
			}
			return stacks;
		}
		public static Object[] fromAll(Block block, Class<? extends Enum> en) {
			Enum[] vals = en.getEnumConstants();
			Object[] stacks = new Object[vals.length];
			
			for(int i = 0; i < vals.length; i++) {
				stacks[i] = new ItemStack(block, 1, vals[i].ordinal());
			}
			return stacks;
		}
		
		public DictFrame any(Object... thing) {
			return makeObject(ANY, thing);
		}
		public DictFrame nugget(Object... nugget) {
			hazMult = HazardRegistry.nugget;
			return makeObject(NUGGET, nugget).makeObject(TINY, nugget);
		}
		public DictFrame ingot(Object... ingot) {
			hazMult = HazardRegistry.ingot;
			return makeObject(INGOT, ingot);
		}
		public DictFrame dustSmall(Object... dustSmall) {
			hazMult = HazardRegistry.powder_tiny;
			return makeObject(DUSTTINY, dustSmall);
		}
		public DictFrame dust(Object... dust) {
			hazMult = HazardRegistry.powder;
			return makeObject(DUST, dust);
		}
		public DictFrame gem(Object... gem) {
			hazMult = HazardRegistry.gem;
			return makeObject(GEM, gem);
		}
		public DictFrame crystal(Object... crystal) {
			hazMult = HazardRegistry.gem;
			return makeObject(CRYSTAL, crystal);
		}
		public DictFrame plate(Object... plate) {
			hazMult = HazardRegistry.plate;
			return makeObject(PLATE, plate);
		}
		public DictFrame plateCast(Object... plate) {
			hazMult = HazardRegistry.plateCast;
			return makeObject(CASTPLATE, plate);
		}
		public DictFrame billet(Object... billet) {
			hazMult = HazardRegistry.billet;
			return makeObject(BILLET, billet);
		}
		
		public DictFrame block(Object... block) {
			hazMult = HazardRegistry.block;
			return makeObject(BLOCK, block);
		}
		public DictFrame ore(Object... ore) {
			hazMult = HazardRegistry.ore;
			return makeObject(ORE, ore);
		}
		public DictFrame oreNether(Object... oreNether) {
			hazMult = HazardRegistry.ore;
			return makeObject(ORENETHER, oreNether);
		}

		public DictFrame makeObject(MaterialShapes shape, Object... objects) {
			
			String tag = shape.name();
			for(Object o : objects) {
				if(o instanceof Item)		registerStack(tag, new ItemStack((Item) o));
				if(o instanceof Block)		registerStack(tag, new ItemStack((Block) o));
				if(o instanceof ItemStack)	registerStack(tag, (ItemStack) o);
			}
			
			return this;
		}
		
		public DictFrame makeItem(String tag, Item... items) {
			for(Item i : items) registerStack(tag, new ItemStack(i));
			return this;
		}
		public DictFrame makeStack(String tag, ItemStack... stacks) {
			for(ItemStack s : stacks) registerStack(tag, s);
			return this;
		}
		public DictFrame makeBlocks(String tag, Block... blocks) {
			for(Block b : blocks) registerStack(tag, new ItemStack(b));
			return this;
		}
		
		public static void registerHazards(List<HazardEntry> hazards, float hazMult, String dictKey) {
			
			if(!hazards.isEmpty() && hazMult > 0F) {
				HazardData data = new HazardData().setMutex(0b1);
				
				for(HazardEntry hazard : hazards) {
					data.addEntry(hazard.clone(hazMult));
				}
				
				HazardSystem.register(dictKey, data);
			}
		}
		
		public void registerStack(String tag, ItemStack stack) {
			for(String mat : mats) {
				OreDictionary.registerOre(tag + mat, stack);
				registerHazards(hazards, hazMult, tag + mat);
			}
			
			/*
			 * Fix for a small oddity in nuclearcraft: many radioactive elements do not have an ore prefix and the sizes
			 * seem generally inconsistent (TH and U are 20 "tiny"s per ingot while boron is 12), so we assume those to be ingots.
			 * Therefore we register all ingots a second time but without prefix. TODO: add a config option to disable this compat.
			 * I'd imagine greg's OD system might not like things without prefixes.
			 */
			if("ingot".equals(tag)) {
				registerStack("", stack);
			}
		}
	}
	
	public static class DictGroup {
		
		private String groupName;
		private HashSet<String> names = new HashSet();
		
		public DictGroup(String groupName) {
			this.groupName = groupName;
		}
		public DictGroup(String groupName, String... names) {
			this(groupName);
			this.addNames(names);
		}
		public DictGroup(String groupName, DictFrame... frames) {
			this(groupName);
			this.addFrames(frames);
		}
		
		public DictGroup addNames(String... names) {
			for(String mat : names) this.names.add(mat);
			return this;
		}
		public DictGroup addFrames(DictFrame... frames) {
			for(DictFrame frame : frames) this.addNames(frame.mats);
			return this;
		}
		
		/**
		 * Will add a reregistration entry for every mat name of every added DictFrame for the given prefix
		 * @param prefix The prefix of both the input and result of the reregistration
		 * @return
		 */
		public DictGroup addPrefix(MaterialShapes shape, boolean inputPrefix) {
			
			String prefix = shape.name();
			String group = prefix + groupName;
			
			for(String name : names) {
				String original = (inputPrefix ? prefix : "") + name;
				addReRegistration(original, group);
			}
			
			return this;
		}
		/**
		 * Same thing as addPrefix, but the input for the reregistration is not bound by the prefix or any mat names
		 * @param prefix The prefix for the resulting reregistration entry (in full: prefix + group name)
		 * @param original The full original ore dict key, not bound by any naming conventions
		 * @return
		 */
		public DictGroup addFixed(String prefix, String original) {
			
			String group = prefix + groupName;
			addReRegistration(original, group);
			return this;
		}
		
		public String any() {			return ANY.name()				+ groupName; }
		public String nugget() {		return NUGGET.name()			+ groupName; }
		public String tiny() {			return TINY.name()				+ groupName; }
		public String bolt() {			return BOLT.name()				+ groupName; }
		public String ingot() {			return INGOT.name()				+ groupName; }
		public String dustTiny() {		return DUSTTINY	.name()			+ groupName; }
		public String dust() {			return DUST.name()				+ groupName; }
		public String gem() {			return GEM.name()				+ groupName; }
		public String crystal() {		return CRYSTAL.name()			+ groupName; }
		public String plate() {			return PLATE.name()				+ groupName; }
		public String plateCast() {		return CASTPLATE.name()			+ groupName; }
		public String plateWelded() {	return WELDEDPLATE.name()		+ groupName; }
		public String heavyComp() {		return HEAVY_COMPONENT.name()	+ groupName; }
		public String wireFine() {		return WIRE.name()				+ groupName; }
		public String wireDense() {		return DENSEWIRE.name()			+ groupName; }
		public String billet() {		return BILLET.name()			+ groupName; }
		public String block() {			return BLOCK.name()				+ groupName; }
		public String ore() {			return ORE.name()				+ groupName; }
		public String lightBarrel() {	return LIGHTBARREL.name()		+ groupName; }
		public String heavyBarrel() {	return HEAVYBARREL.name()		+ groupName; }
		public String lightReceiver() {	return LIGHTRECEIVER.name()		+ groupName; }
		public String heavyReceiver() {	return HEAVYRECEIVER.name()		+ groupName; }
		public String mechanism() {		return MECHANISM.name()			+ groupName; }
		public String stock() {			return STOCK.name()				+ groupName; }
		public String grip() {			return GRIP.name()				+ groupName; }
	}
	
	private static void addReRegistration(String original, String additional) {
		
		HashSet<String> strings = reRegistration.get(original);
		
		if(strings == null)
			strings = new HashSet();
		
		strings.add(additional);
		
		reRegistration.put(original, strings);
	}
}
