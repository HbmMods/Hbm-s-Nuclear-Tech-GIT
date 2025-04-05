package com.hbm.inventory.material;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.material.MaterialShapes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;
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
	public static final NTMMaterial MAT_WOOD			= makeNonSmeltable(_VS + 03,	WOOD,				0x896727, 0x281E0B, 0x896727).setAutogen(STOCK, GRIP).n();
	public static final NTMMaterial MAT_IVORY			= makeNonSmeltable(_VS + 04,	BONE,				0xFFFEEE, 0x797870, 0xEDEBCA).setAutogen(GRIP).n();
	public static final NTMMaterial MAT_STONE			= makeSmeltable(_VS + 00,		df("Stone"),		0x7F7F7F, 0x353535, 0x4D2F23).n();
	public static final NTMMaterial MAT_CARBON			= makeAdditive(	699,			CARBON,				0x363636, 0x030303, 0x404040).setAutogen(WIRE, BLOCK).n();
	public static final NTMMaterial MAT_COAL			= makeNonSmeltable(600,			COAL, 				0x363636, 0x030303, 0x404040).setConversion(MAT_CARBON,  2, 1).setAutogen(FRAGMENT).n();
	public static final NTMMaterial MAT_LIGNITE			= makeNonSmeltable(601,			LIGNITE,			0x542D0F, 0x261508, 0x472913).setConversion(MAT_CARBON,  3, 1).setAutogen(FRAGMENT).n();
	public static final NTMMaterial MAT_COALCOKE		= make(			610,			COALCOKE)			.setConversion(MAT_CARBON,  4, 3).n();
	public static final NTMMaterial MAT_PETCOKE			= make(			611,			PETCOKE)			.setConversion(MAT_CARBON,  4, 3).n();
	public static final NTMMaterial MAT_LIGCOKE			= make(			612,			LIGCOKE)			.setConversion(MAT_CARBON,  4, 3).n();
	public static final NTMMaterial MAT_GRAPHITE		= make(			620,			GRAPHITE)			.setConversion(MAT_CARBON,  1, 1).n();
	public static final NTMMaterial MAT_DIAMOND			= makeNonSmeltable(1430,		DIAMOND,			0xFFFFFF, 0x1B7B6B, 0x8CF4E2).setConversion(MAT_CARBON,  1, 1).setAutogen(FRAGMENT).n();
	public static final NTMMaterial MAT_IRON			= makeSmeltable(2600,			IRON,				0xFFFFFF, 0x353535, 0xFFA259).setAutogen(FRAGMENT, DUST, PIPE, CASTPLATE, WELDEDPLATE, BLOCK).m();
	public static final NTMMaterial MAT_GOLD			= makeSmeltable(7900,			GOLD,				0xFFFF8B, 0xC26E00, 0xE8D754).setAutogen(FRAGMENT, WIRE, NUGGET, DUST, DENSEWIRE, CASTPLATE, BLOCK).m();
	public static final NTMMaterial MAT_REDSTONE		= makeSmeltable(_VS + 01,		REDSTONE,			0xE3260C, 0x700E06, 0xFF1000).setAutogen(FRAGMENT).n();
	public static final NTMMaterial MAT_OBSIDIAN		= makeSmeltable(_VS + 02,		df("Obsidian"),		0x3D234D).n();
	public static final NTMMaterial MAT_HEMATITE		= makeAdditive(	2601,			HEMATITE,			0xDFB7AE, 0x5F372E, 0x6E463D).m();
	public static final NTMMaterial MAT_WROUGHTIRON		= makeSmeltable(2602,			df("WroughtIron"),	0xFAAB89).m();
	public static final NTMMaterial MAT_PIGIRON			= makeSmeltable(2603,			df("PigIron"),		0xFF8B59).m();
	public static final NTMMaterial MAT_METEORICIRON	= makeSmeltable(2604,			df("MeteoricIron"),	0x715347).m();
	public static final NTMMaterial MAT_MALACHITE		= makeAdditive(	2901,			MALACHITE,			0xA2F0C8, 0x227048, 0x61AF87).m();
	public static final NTMMaterial MAT_BAUXITE			= makeNonSmeltable(2902,			BAUXITE,			0xF4BA30, 0xAA320A, 0xE2560F).setAutogen(FRAGMENT).n();
	public static final NTMMaterial MAT_CRYOLITE		= makeNonSmeltable(2903,			CRYOLITE,			0xCBC2A4, 0x8B711F, 0x8B701A).setAutogen(FRAGMENT).n();

	//Radioactive
	public static final NTMMaterial MAT_URANIUM		= makeSmeltable(9200,		U,			0xC1C7BD, 0x2B3227, 0x9AA196).setAutogen(FRAGMENT, NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_U233		= makeSmeltable(9233,		U233,		0xC1C7BD, 0x2B3227, 0x9AA196).setAutogen(NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_U235		= makeSmeltable(9235,		U235,		0xC1C7BD, 0x2B3227, 0x9AA196).setAutogen(NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_U238		= makeSmeltable(9238,		U238,		0xC1C7BD, 0x2B3227, 0x9AA196).setAutogen(FRAGMENT, NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_THORIUM		= makeSmeltable(9032,		TH232,		0xBF825F, 0x1C0000, 0xBF825F).setAutogen(FRAGMENT, NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_PLUTONIUM	= makeSmeltable(9400,		PU,			0x9AA3A0, 0x111A17, 0x78817E).setAutogen(NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_RGP			= makeSmeltable(9401,		PURG,		0x9AA3A0, 0x111A17, 0x78817E).setAutogen(NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_PU238		= makeSmeltable(9438,		PU238,		0xFFBC59, 0xFF8E2B, 0x78817E).setAutogen(NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_PU239		= makeSmeltable(9439,		PU239,		0x9AA3A0, 0x111A17, 0x78817E).setAutogen(NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_PU240		= makeSmeltable(9440,		PU240,		0x9AA3A0, 0x111A17, 0x78817E).setAutogen(NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_PU241		= makeSmeltable(9441,		PU241,		0x9AA3A0, 0x111A17, 0x78817E).setAutogen(NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_RGA			= makeSmeltable(9501,		AMRG,		0xCEB3B9, 0x3A1C21, 0x93767B).setAutogen(NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_AM241		= makeSmeltable(9541,		AM241,		0xCEB3B9, 0x3A1C21, 0x93767B).setAutogen(NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_AM242		= makeSmeltable(9542,		AM242,		0xCEB3B9, 0x3A1C21, 0x93767B).setAutogen(NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_NEPTUNIUM	= makeSmeltable(9337,		NP237,		0xA6B2A6, 0x030F03, 0x647064).setAutogen(NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_POLONIUM	= makeSmeltable(8410,		PO210,		0x968779, 0x3D1509, 0x715E4A).setAutogen(FRAGMENT, NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_TECHNETIUM	= makeSmeltable(4399,		TC99,		0xFAFFFF, 0x576C6C, 0xCADFDF).setAutogen(FRAGMENT, NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_RADIUM		= makeSmeltable(8826,		RA226,		0xFCFCFC, 0xADBFBA, 0xE9FAF6).setAutogen(FRAGMENT, NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_ACTINIUM	= makeSmeltable(8927,		AC227,		0xECE0E0, 0x221616, 0x958989).setAutogen(NUGGET, BILLET).m();
	public static final NTMMaterial MAT_CO60		= makeSmeltable(2760,		CO60,		0xC2D1EE, 0x353554, 0x8F72AE).setAutogen(NUGGET, BILLET, DUST).m();
	public static final NTMMaterial MAT_AU198		= makeSmeltable(7998,		AU198,		0xFFFF8B, 0xC26E00, 0xE8D754).setAutogen(NUGGET, BILLET, DUST).m();
	public static final NTMMaterial MAT_PB209		= makeSmeltable(8209,		PB209,		0xB38A94, 0x12020E, 0x7B535D).setAutogen(NUGGET, BILLET, DUST).m();
	public static final NTMMaterial MAT_SCHRABIDIUM	= makeSmeltable(12626,		SA326,		0x32FFFF, 0x005C5C, 0x32FFFF).setAutogen(NUGGET, WIRE, BILLET, DUST, DENSEWIRE, PLATE, CASTPLATE, BLOCK).m();
	public static final NTMMaterial MAT_SOLINIUM	= makeSmeltable(12627,		SA327,		0xA2E6E0, 0x00433D, 0x72B6B0).setAutogen(NUGGET, BILLET, BLOCK).m();
	public static final NTMMaterial MAT_SCHRABIDATE	= makeSmeltable(12600,		SBD,		0x77C0D7, 0x39005E, 0x6589B4).setAutogen(DUST, DENSEWIRE, CASTPLATE, BLOCK).m();
	public static final NTMMaterial MAT_SCHRARANIUM	= makeSmeltable(12601,		SRN,		0x2B3227, 0x2B3227, 0x24AFAC).setAutogen(BLOCK).m();
	public static final NTMMaterial MAT_GHIORSIUM	= makeSmeltable(12836,		GH336,		0xF4EFE1, 0x2A3306, 0xC6C6A1).setAutogen(NUGGET, BILLET, BLOCK).m();

	//Base metals
	public static final NTMMaterial MAT_TITANIUM		= makeSmeltable(2200,		TI,				0xF7F3F2, 0x4F4C4B, 0xA99E79).setAutogen(FRAGMENT, DUST, PLATE, DENSEWIRE, CASTPLATE, WELDEDPLATE, SHELL, BLOCK, HEAVY_COMPONENT).m();
	public static final NTMMaterial MAT_COPPER			= makeSmeltable(2900,		CU,				0xFDCA88, 0x601E0D, 0xC18336).setAutogen(FRAGMENT, WIRE, DUST, PLATE, DENSEWIRE, CASTPLATE, WELDEDPLATE, SHELL, PIPE, BLOCK, HEAVY_COMPONENT).m();
	public static final NTMMaterial MAT_TUNGSTEN		= makeSmeltable(7400,		W,				0x868686, 0x000000, 0x977474).setAutogen(FRAGMENT, WIRE, BOLT, DUST, DENSEWIRE, CASTPLATE, WELDEDPLATE, BLOCK, HEAVY_COMPONENT).m();
	public static final NTMMaterial MAT_ALUMINIUM		= makeSmeltable(1300,		AL,				0xFFFFFF, 0x344550, 0xD0B8EB).setAutogen(FRAGMENT, WIRE, DUST, PLATE, CASTPLATE, WELDEDPLATE, SHELL, PIPE, BLOCK, HEAVY_COMPONENT).m();
	public static final NTMMaterial MAT_LEAD			= makeSmeltable(8200,		PB,				0xA6A6B2, 0x03030F, 0x646470).setAutogen(FRAGMENT, NUGGET, WIRE, BOLT, DUST, PLATE, CASTPLATE, PIPE, BLOCK, HEAVY_COMPONENT).m();
	public static final NTMMaterial MAT_BISMUTH			= makeSmeltable(8300,		BI, 			0xB200FF, 0xB200FF, 0xB200FF).setAutogen(FRAGMENT, NUGGET, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_ARSENIC			= makeSmeltable(3300,		AS,				0x6CBABA, 0x242525, 0x558080).setAutogen(NUGGET).m();
	public static final NTMMaterial MAT_TANTALIUM		= makeSmeltable(7300,		TA,				0xFFFFFF, 0x1D1D36, 0xA89B74).setAutogen(NUGGET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_NEODYMIUM		= makeSmeltable(6000,		ND,				0xE6E6B6, 0x1C1C00, 0x8F8F5F).setAutogen(FRAGMENT, NUGGET, DUSTTINY, INGOT, DUST, DENSEWIRE, BLOCK).m();
	public static final NTMMaterial MAT_NIOBIUM			= makeSmeltable(4100,		NB,				0xB76EC9, 0x2F2D42, 0xD576B1).setAutogen(FRAGMENT, NUGGET, DUSTTINY, DUST, DENSEWIRE, BLOCK).m();
	public static final NTMMaterial MAT_BERYLLIUM		= makeSmeltable(400,		BE,				0xB2B2A6, 0x0F0F03, 0xAE9572).setAutogen(FRAGMENT, NUGGET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_EMERALD			= makeNonSmeltable(401,		EMERALD,		0xBAFFD4, 0x003900, 0x17DD62).setConversion(MAT_BERYLLIUM, 4, 3).setAutogen(FRAGMENT, DUST, GEM, BLOCK).n();
	public static final NTMMaterial MAT_COBALT			= makeSmeltable(2700,		CO,				0xC2D1EE, 0x353554, 0x8F72AE).setAutogen(FRAGMENT, NUGGET, DUSTTINY, BILLET, DUST, BLOCK).m();
	public static final NTMMaterial MAT_BORON			= makeSmeltable(500,		B,				0xBDC8D2, 0x29343E, 0xAD72AE).setAutogen(FRAGMENT, DUSTTINY, DUST, BLOCK).m();
	public static final NTMMaterial MAT_BORAX			= makeSmeltable(501,		BORAX,			0xFFFFFF, 0x946E23, 0xFFECC6).setAutogen(FRAGMENT, INGOT, DUST).n();
	public static final NTMMaterial MAT_LANTHANIUM		= makeSmeltable(5700,		LA,				0xC8E0E0, 0x3B5353, 0xA1B9B9).setAutogen(FRAGMENT, BLOCK).m();
	public static final NTMMaterial MAT_ZIRCONIUM		= makeSmeltable(4000,		ZR,				0xE3DCBE, 0x3E3719, 0xADA688).setAutogen(FRAGMENT, NUGGET, WIRE, DUSTTINY, BILLET, DUST, CASTPLATE, WELDEDPLATE, BLOCK).m();
	public static final NTMMaterial MAT_SODIUM			= makeSmeltable(1100,		NA,				0xD3BF9E, 0x3A5A6B, 0x7E9493).setAutogen(FRAGMENT, INGOT, DUST).m();
	public static final NTMMaterial MAT_SODALITE		= makeNonSmeltable(1101,	SODALITE,		0xDCE5F6, 0x4927B4, 0x96A7E6).setAutogen(FRAGMENT, GEM).n();
	public static final NTMMaterial MAT_STRONTIUM		= makeSmeltable(3800,		SR,				0xF1E8BA, 0x271E00, 0xCAC193).setAutogen(FRAGMENT, INGOT, DUST).m();
	public static final NTMMaterial MAT_CALCIUM			= makeSmeltable(2000,		CA,				0xCFCFA6, 0x747F6E, 0xB7B784).setAutogen(DUST).m();
	public static final NTMMaterial MAT_LITHIUM			= makeSmeltable(300,		LI,				0xFFFFFF, 0x818181, 0xD6D6D6).setAutogen(FRAGMENT, DUST, BLOCK).m();
	public static final NTMMaterial MAT_SULFUR			= makeNonSmeltable(1600,	S,				0xFCEE80, 0xBDA022, 0xF1DF68).setAutogen(FRAGMENT, DUST, BLOCK).n();
	public static final NTMMaterial MAT_KNO				= makeNonSmeltable(700,		KNO,			0xD4D4D4, 0x969696, 0xC9C9C9).setAutogen(FRAGMENT, DUST, BLOCK).n();
	public static final NTMMaterial MAT_FLUORITE		= makeNonSmeltable(900,		F,				0xFFFFFF, 0xB0A192, 0xE1DBD4).setAutogen(FRAGMENT, DUST, BLOCK).n();
	public static final NTMMaterial MAT_PHOSPHORUS		= makeNonSmeltable(1500,	P_RED,			0xCB0213, 0x600006, 0xBA0615).setAutogen(FRAGMENT, DUST, BLOCK).n();
	public static final NTMMaterial MAT_CHLOROCALCITE	= makeNonSmeltable(1701, 	CHLOROCALCITE,	0xF7E761, 0x475B46, 0xB8B963).setAutogen(FRAGMENT, DUST).n();
	public static final NTMMaterial MAT_MOLYSITE		= makeNonSmeltable(1702, 	MOLYSITE, 		0xF9E97B, 0x216E00, 0xD0D264).setAutogen(FRAGMENT, DUST).n();
	public static final NTMMaterial MAT_CINNABAR		= makeNonSmeltable(8001,	CINNABAR,		0xD87070, 0x993030, 0xBF4E4E).setAutogen(FRAGMENT, GEM).n();
	public static final NTMMaterial MAT_CADMIUM			= makeSmeltable(4800,		CD,				0xFFFADE, 0x350000, 0xA85600).setAutogen(DUST).m();
	public static final NTMMaterial MAT_SILICON			= makeSmeltable(1400,		SI,				0xD1D7DF, 0x1A1A3D, 0x878B9E).setAutogen(FRAGMENT, NUGGET, BILLET).m();
	public static final NTMMaterial MAT_ASBESTOS		= makeSmeltable(1401,		ASBESTOS,		0xD8D9CF, 0x616258, 0xB0B3A8).setAutogen(FRAGMENT, BLOCK).n();
	public static final NTMMaterial MAT_OSMIRIDIUM		= makeSmeltable(7699,		OSMIRIDIUM, 	0xDBE3EF, 0x7891BE, 0xACBDD9).setAutogen(NUGGET, CASTPLATE, WELDEDPLATE).m();

	//Alloys
	public static final NTMMaterial MAT_STEEL		= makeSmeltable(_AS + 0,	STEEL,			0xAFAFAF, 0x0F0F0F, 0x4A4A4A).setAutogen(DUSTTINY, BOLT, WIRE, DUST, PLATE, CASTPLATE, WELDEDPLATE, SHELL, PIPE, BLOCK, HEAVY_COMPONENT, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, GRIP).m();
	public static final NTMMaterial MAT_MINGRADE	= makeSmeltable(_AS + 1,	MINGRADE,		0xFFBA7D, 0xAF1700, 0xE44C0F).setAutogen(WIRE, DUST, BLOCK).m();
	public static final NTMMaterial MAT_ALLOY		= makeSmeltable(_AS + 2,	ALLOY,			0xFF8330, 0x700000, 0xFF7318).setAutogen(WIRE, DUST, DENSEWIRE, PLATE, CASTPLATE, BLOCK, HEAVY_COMPONENT).m();
	public static final NTMMaterial MAT_DURA		= makeSmeltable(_AS + 3,	DURA,			0x183039, 0x030B0B, 0x376373).setAutogen(BOLT, DUST, PLATE, CASTPLATE, PIPE, BLOCK, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, HEAVYRECEIVER, GRIP).m();
	public static final NTMMaterial MAT_DESH		= makeSmeltable(_AS + 12,	DESH,			0xFF6D6D, 0x720000, 0xF22929).setAutogen(DUST, CASTPLATE, BLOCK, HEAVY_COMPONENT, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, STOCK, GRIP).m();
	public static final NTMMaterial MAT_STAR		= makeSmeltable(_AS + 5,	STAR,			0xCCCCEA, 0x11111A, 0xA5A5D3).setAutogen(DUST, DENSEWIRE, BLOCK).m();
	public static final NTMMaterial MAT_FERRO		= makeSmeltable(_AS + 7,	FERRO,			0xB7B7C9, 0x101022, 0x6B6B8B).setAutogen(CASTPLATE, HEAVYBARREL, HEAVYRECEIVER).m();
	public static final NTMMaterial MAT_TCALLOY		= makeSmeltable(_AS + 6,	TCALLOY,		0xD4D6D6, 0x323D3D, 0x9CA6A6).setAutogen(DUST, CASTPLATE, WELDEDPLATE, HEAVY_COMPONENT, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, HEAVYRECEIVER).m();
	public static final NTMMaterial MAT_CDALLOY		= makeSmeltable(_AS + 13,	CDALLOY,		0xF7DF8F, 0x604308, 0xFBD368).setAutogen(CASTPLATE, WELDEDPLATE, HEAVY_COMPONENT, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, HEAVYRECEIVER).m();
	public static final NTMMaterial MAT_BBRONZE		= makeSmeltable(_AS + 16,	BBRONZE,		0xE19A69, 0x485353, 0x987D65).setAutogen(CASTPLATE, LIGHTBARREL, LIGHTRECEIVER, HEAVYRECEIVER).m();
	public static final NTMMaterial MAT_ABRONZE		= makeSmeltable(_AS + 17,	ABRONZE,		0xDB9462, 0x203331, 0x77644D).setAutogen(CASTPLATE, LIGHTBARREL, LIGHTRECEIVER, HEAVYRECEIVER).m();
	public static final NTMMaterial MAT_BSCCO		= makeSmeltable(_AS + 18,	BSCCO,			0x767BF1, 0x000000, 0x5E62C0).setAutogen(DENSEWIRE).m();
	public static final NTMMaterial MAT_MAGTUNG		= makeSmeltable(_AS + 8,	MAGTUNG,		0x22A2A2, 0x0F0F0F, 0x22A2A2).setAutogen(WIRE, DUST, DENSEWIRE, BLOCK).m();
	public static final NTMMaterial MAT_CMB			= makeSmeltable(_AS + 9,	CMB,			0x6F6FB4, 0x000011, 0x6F6FB4).setAutogen(DUST, PLATE, CASTPLATE, WELDEDPLATE, BLOCK).m();
	public static final NTMMaterial MAT_DNT			= makeSmeltable(_AS + 15,	DNT,			0x7582B9, 0x16000E, 0x455289).setAutogen(DUST, DENSEWIRE, BLOCK).m();
	public static final NTMMaterial MAT_FLUX		= makeAdditive(_AS + 10,	df("Flux"),		0xF1E0BB, 0x6F6256, 0xDECCAD).setAutogen(DUST).n();
	public static final NTMMaterial MAT_SLAG		= makeSmeltable(_AS + 11,	SLAG,			0x554940, 0x34281F, 0x6C6562).setAutogen(BLOCK).n();
	public static final NTMMaterial MAT_MUD			= makeSmeltable(_AS + 14,	MUD,			0xBCB5A9, 0x481213, 0x96783B).n();
	public static final NTMMaterial MAT_GUNMETAL	= makeSmeltable(_AS + 19,	GUNMETAL,		0xFFEF3F, 0xAD3600, 0xF9C62C).setAutogen(LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, HEAVYRECEIVER, MECHANISM, STOCK, GRIP).n();
	public static final NTMMaterial MAT_WEAPONSTEEL	= makeSmeltable(_AS + 20,	WEAPONSTEEL,	0xA0A0A0, 0x000000, 0x808080).setAutogen(CASTPLATE, SHELL, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, HEAVYRECEIVER, MECHANISM, STOCK, GRIP).n();
	public static final NTMMaterial MAT_SATURN		= makeSmeltable(_AS + 4,	BIGMT,			0x3AC4DA, 0x09282C, 0x30A4B7).setAutogen(PLATE, CASTPLATE, SHELL, BLOCK, LIGHTBARREL, HEAVYBARREL, LIGHTRECEIVER, HEAVYRECEIVER, MECHANISM, STOCK, GRIP).m();

	//Extension
	public static final NTMMaterial MAT_RAREEARTH	= makeNonSmeltable(_ES + 00, 		RAREEARTH,		0xC1BDBD, 0x384646, 0x7B7F7F).setAutogen(FRAGMENT).n();
	public static final NTMMaterial MAT_POLYMER		= makeNonSmeltable(_ES + 01, 		POLYMER,		0x363636, 0x040404, 0x272727).setAutogen(STOCK, GRIP).n();
	public static final NTMMaterial MAT_BAKELITE	= makeNonSmeltable(_ES + 02, 		BAKELITE,		0xF28086, 0x2B0608, 0xC93940).setAutogen(STOCK, GRIP).n();
	public static final NTMMaterial MAT_RUBBER		= makeNonSmeltable(_ES + 03, 		RUBBER,			0x817F75, 0x0F0D03, 0x4B4A3F).setAutogen(PIPE, GRIP).n();
	public static final NTMMaterial MAT_HARDPLASTIC	= makeNonSmeltable(_ES + 04, 		PC,				0xEDE7C4, 0x908A67, 0xE1DBB8).setAutogen(STOCK, GRIP).n();
	public static final NTMMaterial MAT_PVC			= makeNonSmeltable(_ES + 05, 		PVC,			0xFCFCFC, 0x9F9F9F, 0xF0F0F0).setAutogen(STOCK, GRIP).n();

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

						if(material != null && (material.smeltsInto.smeltable == SmeltingBehavior.SMELTABLE || material.smeltsInto.smeltable == SmeltingBehavior.ADDITIVE)) {
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
