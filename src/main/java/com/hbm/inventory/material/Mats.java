package com.hbm.inventory.material;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.material.MaterialShapes.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;
import com.hbm.main.MainRegistry;
import com.hbm.render.util.EnumSymbol;
import com.hbm.util.I18nUtil;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;

/* with every new rewrite, optimization and improvement, the code becomes more gregian */

/**
 * Defines materials that wrap around DictFrames to more accurately describe that material.
 * Direct uses are the crucible and possibly item auto-gen, depending on what traits are set.
 * @author hbm
 */
public class Mats {
	public static final Gson gson = new Gson();
	public static List<NTMMaterial> orderedList = new ArrayList();
	public static HashMap<String, MaterialShapes> prefixByName = new HashMap();
	public static HashMap<Integer, NTMMaterial> matById = new HashMap();
	public static HashMap<String, NTMMaterial> matByName = new HashMap();
	public static HashMap<ComparableStack, List<MaterialStack>> materialEntries = new HashMap();
	public static HashMap<String, List<MaterialStack>> materialOreEntries = new HashMap();

	/*
	 * ItemStacks are saved with their metadata being truncated to a short, so the max meta is 32767
	 * Format for elements: Atomic number *100, plus the last two digits of the mass number. Mass number is 0 for generic/undefined/mixed materials.
	 * Vanilla numbers are in vanilla space (0-29), basic alloys use alloy space (30-99)
	 */

	/* Vanilla Space, up to 30 materials, */
	public static final int _VS = 0;
	/* Alloy Space, up to 70 materials. Use >20_000 as an extension.*/
	public static final int _AS = 30;
	public static final int _ES = 20_000;

	//Vanilla and vanilla-like
	public static NTMMaterial MAT_WOOD;
	public static NTMMaterial MAT_IVORY;
	public static NTMMaterial MAT_STONE ;
	public static NTMMaterial MAT_CARBON;
	public static  NTMMaterial MAT_COAL;
	public static  NTMMaterial MAT_LIGNITE;
	public static  NTMMaterial MAT_COALCOKE;
	public static  NTMMaterial MAT_PETCOKE;
	public static  NTMMaterial MAT_LIGCOKE;
	public static  NTMMaterial MAT_GRAPHITE;
	public static  NTMMaterial MAT_DIAMOND;
	public static  NTMMaterial MAT_IRON;
	public static  NTMMaterial MAT_GOLD;
	public static  NTMMaterial MAT_REDSTONE;
	public static NTMMaterial MAT_OBSIDIAN;
	public static NTMMaterial MAT_HEMATITE;
	public static NTMMaterial MAT_WROUGHTIRON;
	public static NTMMaterial MAT_PIGIRON;
	public static NTMMaterial MAT_METEORICIRON;
	public static NTMMaterial MAT_MALACHITE;

	//Radioactive
	public static NTMMaterial MAT_URANIUM;
	public static NTMMaterial MAT_U233;
	public static NTMMaterial MAT_U235;
	public static NTMMaterial MAT_U238;
	public static NTMMaterial MAT_THORIUM;
	public static NTMMaterial MAT_PLUTONIUM;
	public static NTMMaterial MAT_RGP;
	public static NTMMaterial MAT_PU238;
	public static NTMMaterial MAT_PU239;
	public static NTMMaterial MAT_PU240;
	public static NTMMaterial MAT_PU241;
	public static NTMMaterial MAT_RGA;
	public static NTMMaterial MAT_AM241;
	public static NTMMaterial MAT_AM242;
	public static NTMMaterial MAT_NEPTUNIUM ;
	public static NTMMaterial MAT_POLONIUM ;
	public static NTMMaterial MAT_TECHNETIUM ;
	public static NTMMaterial MAT_RADIUM ;
	public static NTMMaterial MAT_ACTINIUM ;
	public static NTMMaterial MAT_CO60;
	public static NTMMaterial MAT_AU198 ;
	public static NTMMaterial MAT_PB209 ;
	public static NTMMaterial MAT_SCHRABIDIUM ;
	public static NTMMaterial MAT_SOLINIUM;
	public static NTMMaterial MAT_SCHRABIDATE;
	public static NTMMaterial MAT_SCHRARANIUM ;
	public static NTMMaterial MAT_GHIORSIUM;

	//Base metals
	public static NTMMaterial MAT_TITANIUM ;
	public static NTMMaterial MAT_COPPER ;
	public static NTMMaterial MAT_TUNGSTEN ;
	public static NTMMaterial MAT_ALUMINIUM ;
	public static NTMMaterial MAT_LEAD ;
	public static NTMMaterial MAT_BISMUTH ;
	public static NTMMaterial MAT_ARSENIC ;
	public static NTMMaterial MAT_TANTALIUM ;
	public static NTMMaterial MAT_NEODYMIUM ;
	public static NTMMaterial MAT_NIOBIUM ;
	public static NTMMaterial MAT_BERYLLIUM ;
	public static NTMMaterial MAT_EMERALD ;
	public static NTMMaterial MAT_COBALT;
	public static NTMMaterial MAT_BORON;
	public static NTMMaterial MAT_BORAX;
	public static NTMMaterial MAT_LANTHANIUM;
	public static NTMMaterial MAT_ZIRCONIUM;
	public static NTMMaterial MAT_SODIUM;
	public static NTMMaterial MAT_SODALITE;
	public static NTMMaterial MAT_STRONTIUM;
	public static NTMMaterial MAT_CALCIUM;
	public static NTMMaterial MAT_LITHIUM;
	public static NTMMaterial MAT_SULFUR;
	public static NTMMaterial MAT_KNO;
	public static NTMMaterial MAT_FLUORITE;
	public static NTMMaterial MAT_PHOSPHORUS;
	public static NTMMaterial MAT_CHLOROCALCITE;
	public static NTMMaterial MAT_MOLYSITE;
	public static NTMMaterial MAT_CINNABAR;
	public static NTMMaterial MAT_CADMIUM;
	public static NTMMaterial MAT_SILICON;
	public static NTMMaterial MAT_ASBESTOS;
	public static NTMMaterial MAT_OSMIRIDIUM;

	//Alloys
	public static NTMMaterial MAT_STEEL;
	public static NTMMaterial MAT_MINGRADE;
	public static NTMMaterial MAT_ALLOY ;
	public static NTMMaterial MAT_DURA ;
	public static NTMMaterial MAT_SATURN ;
	public static NTMMaterial MAT_DESH ;
	public static NTMMaterial MAT_STAR ;
	public static NTMMaterial MAT_FERRO;
	public static NTMMaterial MAT_TCALLOY ;
	public static NTMMaterial MAT_CDALLOY ;
	public static NTMMaterial MAT_BBRONZE ;
	public static NTMMaterial MAT_ABRONZE ;
	public static NTMMaterial MAT_BSCCO ;
	public static NTMMaterial MAT_MAGTUNG ;
	public static NTMMaterial MAT_CMB ;
	public static NTMMaterial MAT_DNT ;
	public static NTMMaterial MAT_FLUX ;
	public static NTMMaterial MAT_SLAG ;
	public static NTMMaterial MAT_MUD;
	public static NTMMaterial MAT_GUNMETAL;
	public static NTMMaterial MAT_WEAPONSTEEL;

	//Extension
	public static NTMMaterial MAT_RAREEARTH;
	public static void init() {
		//Vanilla and vanilla-like
		MAT_WOOD			= makeNonSmeltable(_VS + 03,	WOOD,				0x896727, 0x281E0B, 0x896727).setShapes(STOCK, GRIP).n();
		MAT_IVORY			= makeNonSmeltable(_VS + 04,	BONE,				0xFFFEEE, 0x797870, 0xEDEBCA).setShapes(GRIP).n();
		MAT_STONE = makeSmeltable(_VS + 00, df("Stone"), 0x7F7F7F, 0x353535, 0x4D2F23).n();
		MAT_CARBON = makeAdditive(1499, CARBON, 0x363636, 0x030303, 0x404040).setShapes(WIRE, INGOT, BLOCK).n();
		MAT_COAL = make(1400, COAL).setConversion(MAT_CARBON, 2, 1).n();
		MAT_LIGNITE = make(1401, LIGNITE).setConversion(MAT_CARBON, 3, 1).n();
		MAT_COALCOKE = make(1410, COALCOKE).setConversion(MAT_CARBON, 4, 3).n();
		MAT_PETCOKE = make(1411, PETCOKE).setConversion(MAT_CARBON, 4, 3).n();
		MAT_LIGCOKE = make(1412, LIGCOKE).setConversion(MAT_CARBON, 4, 3).n();
		MAT_GRAPHITE = make(1420, GRAPHITE).setConversion(MAT_CARBON, 1, 1).n();
		MAT_DIAMOND = make(1430, DIAMOND).setConversion(MAT_CARBON, 1, 1).n();
		MAT_IRON = makeSmeltable(2600, IRON, 0xFFFFFF, 0x353535, 0xFFA259).setShapes(INGOT, DUST, PIPE, CASTPLATE, WELDEDPLATE, BLOCK).m();
		MAT_GOLD = makeSmeltable(7900, GOLD, 0xFFFF8B, 0xC26E00, 0xE8D754).setShapes(WIRE, NUGGET, INGOT, DUST, DENSEWIRE, CASTPLATE, BLOCK).m();
		MAT_REDSTONE = makeSmeltable(_VS + 01, REDSTONE, 0xE3260C, 0x700E06, 0xFF1000).n();
		MAT_OBSIDIAN = makeSmeltable(_VS + 02, df("Obsidian"), 0x3D234D).n();
		MAT_HEMATITE = makeAdditive(2601, HEMATITE, 0xDFB7AE, 0x5F372E, 0x6E463D).m();

		MAT_WROUGHTIRON = makeSmeltable(2602, df("WroughtIron"), 0xFAAB89).m();
		MAT_PIGIRON = makeSmeltable(2603, df("PigIron"), 0xFF8B59).m();
		MAT_METEORICIRON = makeSmeltable(2604, df("MeteoricIron"), 0x715347).m();
		MAT_MALACHITE = makeAdditive(	2901,		MALACHITE,			0xA2F0C8, 0x227048, 0x61AF87).m();

		//Radioactive
		MAT_URANIUM = makeSmeltable(9200, U, 0xC1C7BD, 0x2B3227, 0x9AA196).setShapes(FRAGMENT, NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_U233 = makeSmeltable(9233, U233, 0xC1C7BD, 0x2B3227, 0x9AA196).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_U235 = makeSmeltable(9235, U235, 0xC1C7BD, 0x2B3227, 0x9AA196).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_U238 = makeSmeltable(9238, U238, 0xC1C7BD, 0x2B3227, 0x9AA196).setShapes(FRAGMENT, NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_THORIUM = makeSmeltable(9032, TH232, 0xBF825F, 0x1C0000, 0xBF825F).setShapes(FRAGMENT, NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_PLUTONIUM = makeSmeltable(9400, PU, 0x9AA3A0, 0x111A17, 0x78817E).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_RGP = makeSmeltable(9401, PURG, 0x9AA3A0, 0x111A17, 0x78817E).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_PU238 = makeSmeltable(9438, PU238, 0xFFBC59, 0xFF8E2B, 0x78817E).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_PU239 = makeSmeltable(9439, PU239, 0x9AA3A0, 0x111A17, 0x78817E).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_PU240 = makeSmeltable(9440, PU240, 0x9AA3A0, 0x111A17, 0x78817E).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_PU241 = makeSmeltable(9441, PU241, 0x9AA3A0, 0x111A17, 0x78817E).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_RGA = makeSmeltable(9501, AMRG, 0x93767B).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_AM241 = makeSmeltable(9541, AM241, 0x93767B).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_AM242 = makeSmeltable(9542, AM242, 0x93767B).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_NEPTUNIUM = makeSmeltable(9337, NP237, 0x647064).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_POLONIUM = makeSmeltable(8410, PO210, 0x563A26).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_TECHNETIUM	= makeSmeltable(4399,		TC99,		0xFAFFFF, 0x576C6C, 0xCADFDF).setShapes(FRAGMENT, NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_RADIUM = makeSmeltable(8826, RA226, 0xE9FAF6).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_ACTINIUM = makeSmeltable(8927, AC227, 0x958989).setShapes(NUGGET, BILLET, INGOT).m();
		MAT_CO60 = makeSmeltable(2760, CO60, 0xC2D1EE, 0x353554, 0x8F72AE).setShapes(NUGGET, BILLET, INGOT, DUST).m();
		MAT_AU198 = makeSmeltable(7998, AU198, 0xFFFF8B, 0xC26E00, 0xE8D754).setShapes(NUGGET, BILLET, INGOT, DUST).m();
		MAT_PB209		= makeSmeltable(8209,		PB209,		0xB38A94, 0x12020E, 0x7B535D).setShapes(NUGGET, BILLET, INGOT, DUST).m();		MAT_SCHRABIDIUM = makeSmeltable(12626, SA326, 0x32FFFF, 0x005C5C, 0x32FFFF).setShapes(NUGGET, WIRE, BILLET, INGOT, DUST, DENSEWIRE, PLATE, CASTPLATE, BLOCK).m();
		MAT_SOLINIUM = makeSmeltable(12627, SA327, 0xA2E6E0, 0x00433D, 0x72B6B0).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();
		MAT_SCHRABIDATE = makeSmeltable(12600, SBD, 0x77C0D7, 0x39005E, 0x6589B4).setShapes(INGOT, DUST, DENSEWIRE, CASTPLATE, BLOCK).m();
		MAT_SCHRARANIUM = makeSmeltable(12601, SRN, 0x2B3227, 0x2B3227, 0x24AFAC).setShapes(INGOT, BLOCK).m();
		MAT_GHIORSIUM = makeSmeltable(12836, GH336, 0xF4EFE1, 0x2A3306, 0xC6C6A1).setShapes(NUGGET, BILLET, INGOT, BLOCK).m();

		//Base metals
		MAT_TITANIUM = makeSmeltable(2200, TI, 0xF7F3F2, 0x4F4C4B, 0xA99E79).setShapes(INGOT, DUST, PLATE, CASTPLATE, WELDEDPLATE, SHELL, BLOCK, HEAVY_COMPONENT).m();
		MAT_COPPER = makeSmeltable(2900, CU, 0xFDCA88, 0x601E0D, 0xC18336).setShapes(WIRE, INGOT, DUST, PLATE, CASTPLATE, WELDEDPLATE, SHELL, PIPE, BLOCK, HEAVY_COMPONENT).m();
		MAT_TUNGSTEN = makeSmeltable(7400, W, 0x868686, 0x000000, 0x977474).setShapes(WIRE, BOLT, INGOT, DUST, DENSEWIRE, CASTPLATE, WELDEDPLATE, BLOCK, HEAVY_COMPONENT).m();
		MAT_ALUMINIUM = makeSmeltable(1300, AL, 0xFFFFFF, 0x344550, 0xD0B8EB).setShapes(WIRE, INGOT, DUST, PLATE, CASTPLATE, WELDEDPLATE, SHELL, PIPE, BLOCK, HEAVY_COMPONENT).m();
		MAT_LEAD = makeSmeltable(8200, PB, 0xA6A6B2, 0x03030F, 0x646470).setShapes(NUGGET, WIRE, INGOT, DUST, PLATE, CASTPLATE, PIPE, BLOCK, HEAVY_COMPONENT).m();
		MAT_BISMUTH = makeSmeltable(8300, BI, 0xB200FF).setShapes(NUGGET, BILLET, INGOT, DUST, BLOCK).m();
		MAT_ARSENIC = makeSmeltable(3300, AS, 0x6CBABA, 0x242525, 0x558080).setShapes(NUGGET, INGOT).m();
		MAT_TANTALIUM = makeSmeltable(7300, TA, 0xFFFFFF, 0x1D1D36, 0xA89B74).setShapes(NUGGET, INGOT, DUST, BLOCK).m();
		MAT_NEODYMIUM = makeSmeltable(6000, ND, 0xE6E6B6, 0x1C1C00, 0x8F8F5F).setShapes(NUGGET, DUSTTINY, INGOT, DUST, DENSEWIRE, BLOCK).m();
		MAT_NIOBIUM = makeSmeltable(4100, NB, 0xB76EC9, 0x2F2D42, 0xD576B1).setShapes(NUGGET, DUSTTINY, INGOT, DUST, BLOCK).m();
		MAT_BERYLLIUM = makeSmeltable(400, BE, 0xB2B2A6, 0x0F0F03, 0xAE9572).setShapes(NUGGET, INGOT, DUST, BLOCK).m();
		MAT_EMERALD = make(401, EMERALD).setConversion(MAT_BERYLLIUM, 4, 3).n();
		MAT_COBALT = makeSmeltable(2700, CO, 0xC2D1EE, 0x353554, 0x8F72AE).setShapes(NUGGET, DUSTTINY, BILLET, INGOT, DUST, BLOCK).m();
		MAT_BORON = makeSmeltable(500, B, 0xBDC8D2, 0x29343E, 0xAD72AE).setShapes(DUSTTINY, INGOT, DUST, BLOCK).m();
		MAT_BORAX			= makeSmeltable(501,		BORAX,			0xFFFFFF, 0x946E23, 0xFFECC6).setShapes(FRAGMENT, DUST).n();
		MAT_LANTHANIUM		= makeSmeltable(5700,		LA,				0xC8E0E0, 0x3B5353, 0xA1B9B9).setShapes(FRAGMENT, INGOT, BLOCK).m();
		MAT_ZIRCONIUM = makeSmeltable(4000, ZR, 0xE3DCBE, 0x3E3719, 0xADA688).setShapes(NUGGET, WIRE, DUSTTINY, BILLET, INGOT, DUST, CASTPLATE, WELDEDPLATE, BLOCK).m();
		MAT_SODIUM = makeSmeltable(1100, NA, 0xD3BF9E, 0x3A5A6B, 0x7E9493).setShapes(DUST).m();
		MAT_SODALITE		= makeNonSmeltable(1101,	SODALITE,		0xDCE5F6, 0x4927B4, 0x96A7E6).setShapes(FRAGMENT, GEM).n();
		MAT_STRONTIUM = makeSmeltable(3800, SR, 0xF1E8BA, 0x271E00, 0xCAC193).setShapes(DUST).m();
		MAT_CALCIUM = makeSmeltable(2000, CA, 0xCFCFA6, 0x747F6E, 0xB7B784).setShapes(INGOT, DUST).m();
		MAT_LITHIUM = makeSmeltable(300, LI, 0xFFFFFF, 0x818181, 0xD6D6D6).setShapes(INGOT, DUST, BLOCK).m();
		MAT_SULFUR			= makeNonSmeltable(1600,	S,				0xFCEE80, 0xBDA022, 0xF1DF68).setShapes(FRAGMENT, DUST, BLOCK).n();
		MAT_KNO				= makeNonSmeltable(700,		KNO,			0xD4D4D4, 0x969696, 0xC9C9C9).setShapes(FRAGMENT, DUST, BLOCK).n();
		MAT_FLUORITE		= makeNonSmeltable(900,		F,				0xFFFFFF, 0xB0A192, 0xE1DBD4).setShapes(FRAGMENT, DUST, BLOCK).n();
		MAT_PHOSPHORUS		= makeNonSmeltable(1500,	P_RED,			0xCB0213, 0x600006, 0xBA0615).setShapes(FRAGMENT, DUST, BLOCK).n();
		MAT_CHLOROCALCITE	= makeNonSmeltable(1701, 	CHLOROCALCITE,	0xF7E761, 0x475B46, 0xB8B963).setShapes(FRAGMENT, DUST).n();
		MAT_MOLYSITE		= makeNonSmeltable(1702, 	MOLYSITE, 		0xF9E97B, 0x216E00, 0xD0D264).setShapes(FRAGMENT, DUST).n();
		MAT_CINNABAR		= makeNonSmeltable(8001,	CINNABAR,		0xD87070, 0x993030, 0xBF4E4E).setShapes(FRAGMENT, GEM).n();
		MAT_CADMIUM			= makeSmeltable(4800,		CD,				0xFFFADE, 0x350000, 0xA85600).setShapes(INGOT, DUST).m();
		MAT_SILICON			= makeSmeltable(1400,		SI,				0xD1D7DF, 0x1A1A3D, 0x878B9E).setShapes(FRAGMENT, NUGGET, BILLET, INGOT).m();
		MAT_ASBESTOS		= makeSmeltable(1401,		ASBESTOS,		0xD8D9CF, 0x616258, 0xB0B3A8).setShapes(FRAGMENT, INGOT, BLOCK).n();
		MAT_OSMIRIDIUM		= makeSmeltable(7699,		OSMIRIDIUM, 	0xDBE3EF, 0x7891BE, 0xACBDD9).setShapes(NUGGET, INGOT, CASTPLATE, WELDEDPLATE).m();

		//Alloys
		MAT_STEEL = makeSmeltable(_AS + 0, STEEL, 0xAFAFAF, 0x0F0F0F, 0x4A4A4A).setShapes(DUSTTINY, BOLT, WIRE, INGOT, DUST, PLATE, CASTPLATE, WELDEDPLATE, SHELL, PIPE, BLOCK, HEAVY_COMPONENT).m();
		MAT_MINGRADE = makeSmeltable(_AS + 1, MINGRADE, 0xFFBA7D, 0xAF1700, 0xE44C0F).setShapes(WIRE, INGOT, DUST, BLOCK).m();
		MAT_ALLOY = makeSmeltable(_AS + 2, ALLOY, 0xFF8330, 0x700000, 0xFF7318).setShapes(WIRE, INGOT, DUST, DENSEWIRE, PLATE, CASTPLATE, BLOCK, HEAVY_COMPONENT).m();
		MAT_DURA = makeSmeltable(_AS + 3, DURA, 0x183039, 0x030B0B, 0x376373).setShapes(BOLT, INGOT, DUST, PIPE, BLOCK).m();
		MAT_SATURN = makeSmeltable(_AS + 4,	BIGMT,			0x3AC4DA, 0x09282C, 0x30A4B7).setShapes(INGOT, PLATE, CASTPLATE, BLOCK, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, HEAVYRECEIVER, MECHANISM, STOCK, GRIP).m();
		MAT_DESH = makeSmeltable(_AS + 12, DESH, 0xFF6D6D, 0x720000, 0xF22929).setShapes(INGOT, DUST, CASTPLATE, BLOCK, HEAVY_COMPONENT).m();
		MAT_STAR = makeSmeltable(_AS + 5, STAR, 0xCCCCEA, 0x11111A, 0xA5A5D3).setShapes(INGOT, DUST, DENSEWIRE, BLOCK).m();
		MAT_FERRO = makeSmeltable(_AS + 7, FERRO, 0xB7B7C9, 0x101022, 0x6B6B8B).setShapes(INGOT).m();
		MAT_TCALLOY = makeSmeltable(_AS + 6, TCALLOY, 0xD4D6D6, 0x323D3D, 0x9CA6A6).setShapes(INGOT, DUST, CASTPLATE, WELDEDPLATE, HEAVY_COMPONENT).m();
		MAT_CDALLOY = makeSmeltable(_AS + 13, CDALLOY, 0xF7DF8F, 0x604308, 0xFBD368).setShapes(INGOT, CASTPLATE, WELDEDPLATE, HEAVY_COMPONENT).m();
		MAT_BBRONZE = makeSmeltable(_AS + 16, BBRONZE, 0xE19A69, 0x485353, 0x987D65).setShapes(INGOT, CASTPLATE).m();
		MAT_ABRONZE = makeSmeltable(_AS + 17, ABRONZE, 0xDB9462, 0x203331, 0x77644D).setShapes(INGOT, CASTPLATE).m();
		MAT_BSCCO = makeSmeltable(_AS + 18, BSCCO, 0x767BF1, 0x000000, 0x5E62C0).setShapes(INGOT, DENSEWIRE).m();
		MAT_MAGTUNG = makeSmeltable(_AS + 8, MAGTUNG, 0x22A2A2, 0x0F0F0F, 0x22A2A2).setShapes(WIRE, INGOT, DUST, DENSEWIRE, BLOCK).m();
		MAT_CMB = makeSmeltable(_AS + 9, CMB, 0x6F6FB4, 0x000011, 0x6F6FB4).setShapes(INGOT, DUST, PLATE, CASTPLATE, WELDEDPLATE, BLOCK).m();
		MAT_DNT = makeSmeltable(_AS + 15, DNT, 0x7582B9, 0x16000E, 0x455289).setShapes(INGOT, DUST, DENSEWIRE, BLOCK).m();
		MAT_FLUX = makeAdditive(_AS + 10, df("Flux"), 0xF1E0BB, 0x6F6256, 0xDECCAD).setShapes(DUST).n();
		MAT_SLAG = makeSmeltable(_AS + 11, SLAG, 0x554940, 0x34281F, 0x6C6562).setShapes(BLOCK).n();
		MAT_MUD = makeSmeltable(_AS + 14, MUD, 0xBCB5A9, 0x481213, 0x96783B).setShapes(INGOT).n();
		MAT_GUNMETAL	= makeSmeltable(_AS + 19,	GUNMETAL,		0xFFEF3F, 0xAD3600, 0xF9C62C).setShapes(INGOT, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, HEAVYRECEIVER, MECHANISM, STOCK, GRIP).n();
		MAT_WEAPONSTEEL	= makeSmeltable(_AS + 20,	WEAPONSTEEL,	0xA0A0A0, 0x000000, 0x808080).setShapes(INGOT, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, HEAVYRECEIVER, MECHANISM, STOCK, GRIP).n();

		//Extension
		MAT_RAREEARTH	= makeNonSmeltable(_ES + 00, 		RAREEARTH,		0xC1BDBD, 0x384646, 0x7B7F7F).setShapes(FRAGMENT, INGOT).n();

		//Extension
		MAT_RAREEARTH	= makeNonSmeltable(_ES + 00, 		RAREEARTH,		0xC1BDBD, 0x384646, 0x7B7F7F).setShapes(FRAGMENT, INGOT).n();
		File folder = MainRegistry.configHbmDir;
		File customMaterials = new File(folder.getAbsolutePath() + File.separatorChar + "hbmMaterials.json");
		if(!customMaterials.exists()) initDefaultMaterials(customMaterials);
		readCustomMaterials(customMaterials);
	}
	private static void initDefaultMaterials(File file) {

		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("  ");
			writer.beginObject();
            for(NTMMaterial Material : orderedList){
				writer.name(Material.names[0]).beginObject();
				writer.name("id").value(Material.id);
				writer.name("solidColorLight").value(Material.solidColorLight);
				writer.name("solidColorDark").value(Material.solidColorDark);
				writer.name("moltenColor").value(Material.moltenColor);
				writer.name("Shapes").beginArray();
				for (MaterialShapes shape : Material.shapes) {
					//writer.beginObject().setIndent("");
					writer.value(shape.name());
					//writer.endObject();
				}
				writer.endArray();
				//writer.name("Traits").value(Material.traits[0]);
				writer.name("SmeltingBehavior").value(Material.smeltable.name());
				writer.name("Traits").beginArray();
				for (NTMMaterial.MatTraits trait : Material.traits) {
					//writer.beginObject().setIndent("");
					writer.value(trait.name());
					//writer.endObject();
				}
				writer.endArray();
				writer.endObject();
			}
			writer.endObject();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	private static void readCustomMaterials(File file) {

		try {
			JsonObject json = gson.fromJson(new FileReader(file), JsonObject.class);

			for(Entry<String, JsonElement> entry : json.entrySet()) {

				JsonObject obj = (JsonObject) entry.getValue();

				String name = entry.getKey();

				//OreDictManager.getReflector()
				int id = obj.get("id").getAsInt();
				//String displayName = obj.get("name").getAsString();
				int solidColorLight = obj.get("solidColorLight").getAsInt();
				int solidColorDark = obj.get("solidColorDark").getAsInt();
				int moltenColor = obj.get("moltenColor").getAsInt();
				JsonArray shapes = obj.get("Shapes").getAsJsonArray();
				JsonArray traits = obj.get("Traits").getAsJsonArray();

				if(matById.containsKey(id)) {
					NTMMaterial mat = matById.get(id);
					mat.solidColorLight=solidColorLight;
					mat.solidColorDark=solidColorDark;
					mat.moltenColor=moltenColor;
					mat.smeltable=SmeltingBehavior.valueOf(obj.get("SmeltingBehavior").getAsString());
					mat.shapes.clear();
					for(JsonElement shape : shapes){
						for(MaterialShapes _shape : allShapes){
							if(Objects.equals(_shape.name(), shape.getAsString()))mat.shapes.add(_shape);
						}
					}
					mat.traits.clear();
					for(JsonElement trait : traits){
						mat.traits.add(NTMMaterial.MatTraits.valueOf(trait.getAsString()));
					}
					//EnumSymbol symbol = EnumSymbol.valueOf(obj.get("symbol").getAsString());

				}
				else{
					NTMMaterial material = SmeltingBehavior.valueOf(obj.get("SmeltingBehavior").getAsString()) == SmeltingBehavior.SMELTABLE ?
							makeSmeltable(id, df(name), solidColorLight, solidColorDark, moltenColor) :
							makeAdditive(id, df(name), solidColorLight, solidColorDark, moltenColor);
					material.shapes.clear();
					for(JsonElement shape : shapes){
						for(MaterialShapes _shape : allShapes){
							if(Objects.equals(_shape.name(), shape.getAsString()))material.shapes.add(_shape);
						}
					}
					material.traits.clear();
					for(JsonElement trait : traits){
						material.traits.add(NTMMaterial.MatTraits.valueOf(trait.getAsString()));
					}

				}

				//FluidType type = new FluidType(name, color, p, f, r, symbol, texture, tint, id, displayName).setTemp(temperature);
				//customFluids.add(type);
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static NTMMaterial makeSmeltable(int id, DictFrame dict, int color) { return makeSmeltable(id, dict, color, color, color); }

	public static NTMMaterial make(int id, DictFrame dict) {
		return new NTMMaterial(id, dict);
	}

	public static NTMMaterial makeSmeltable(int id, DictFrame dict, int solidColorLight, int solidColorDark, int moltenColor) {
		return new NTMMaterial(id, dict).smeltable(SmeltingBehavior.SMELTABLE).setSolidColor(solidColorLight, solidColorDark).setMoltenColor(moltenColor);
	}

	public static NTMMaterial makeAdditive(int id, DictFrame dict, int solidColorLight, int solidColorDark, int moltenColor) {
		return new NTMMaterial(id, dict).smeltable(SmeltingBehavior.ADDITIVE).setSolidColor(solidColorLight, solidColorDark).setMoltenColor(moltenColor);
	}

	public static NTMMaterial makeNonSmeltable(int id, DictFrame dict, int solidColorLight, int solidColorDark, int moltenColor) {
		return new NTMMaterial(id, dict).smeltable(SmeltingBehavior.NOT_SMELTABLE).setSolidColor(solidColorLight, solidColorDark).setMoltenColor(moltenColor);
	}

	public static DictFrame df(String string) {
		return new DictFrame(string);
	}

	/** will not respect stacksizes - all stacks will be treated as a singular */
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

		List<MaterialStack> entries = materialEntries.get(new ComparableStack(stack).makeSingular());

		if(entries != null) {
			entries.forEach(x -> { if(x != null) list.add(x); });
		}

		if(stack.getItem() == ModItems.scraps) {
			list.add(ItemScraps.getMats(stack));
		}

		return list;
	}

	public static List<MaterialStack> getSmeltingMaterialsFromItem(ItemStack stack) {
		List<MaterialStack> baseMats = getMaterialsFromItem(stack);
		List<MaterialStack> smelting = new ArrayList();
		baseMats.forEach(x -> smelting.add(new MaterialStack(x.material.smeltsInto, (int) (x.amount * x.material.convOut / x.material.convIn))));
		return smelting;
	}

	public static class MaterialStack {
		//final fields to prevent accidental changing
		public final NTMMaterial material;
		public int amount;

		public MaterialStack(NTMMaterial material, int amount) {
			this.material = material;
			this.amount = amount;
		}

		public MaterialStack copy() {
			return new MaterialStack(material, amount);
		}
	}

	public static String formatAmount(int amount, boolean showInMb) {

		if(showInMb) return (amount * 2) + "mB";

		String format = "";

		int blocks = amount / BLOCK.q(1);
		amount -= BLOCK.q(blocks);
		int ingots = amount / INGOT.q(1);
		amount -= INGOT.q(ingots);
		int nuggets = amount / NUGGET.q(1);
		amount -= NUGGET.q(nuggets);
		int quanta = amount;

		if(blocks > 0) format += (blocks == 1 ? I18nUtil.resolveKey("matshape.block", blocks) : I18nUtil.resolveKey("matshape.blocks", blocks)) + " ";
		if(ingots > 0) format += (ingots == 1 ? I18nUtil.resolveKey("matshape.ingot", ingots) : I18nUtil.resolveKey("matshape.ingots", ingots)) + " ";
		if(nuggets > 0) format += (nuggets == 1 ? I18nUtil.resolveKey("matshape.nugget", nuggets) : I18nUtil.resolveKey("matshape.nuggets", nuggets)) + " ";
		if(quanta > 0) format += (quanta == 1 ? I18nUtil.resolveKey("matshape.quantum", quanta) : I18nUtil.resolveKey("matshape.quanta", quanta)) + " ";

		return format.trim();
	}
}
