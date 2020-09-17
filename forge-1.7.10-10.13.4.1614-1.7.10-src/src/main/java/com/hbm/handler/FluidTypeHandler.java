package com.hbm.handler;

import java.util.Arrays;

import com.hbm.render.util.EnumSymbol;

public class FluidTypeHandler {
	
	public enum FluidType {
		NONE			(0x888888,	0,	1,	1,	0,	0,	0,	EnumSymbol.NONE,		"hbmfluid.none"),

		WATER			(0x3333FF,	1,	1,	1,	0,	0,	0,	EnumSymbol.NONE,		"hbmfluid.water"),
		STEAM			(0xe5e5e5,	9,	2,	1,	3,	0,	0,	EnumSymbol.NONE,		"hbmfluid.steam", true, false, false, 100),
		HOTSTEAM		(0xE7D6D6,	1,	1,	2,	4,	0,	0,	EnumSymbol.NONE,		"hbmfluid.hotsteam", true, false, false, 300),
		SUPERHOTSTEAM	(0xE7B7B7,	2,	1,	2,	4,	0,	0,	EnumSymbol.NONE,		"hbmfluid.superhotsteam", true, false, false, 450),
		ULTRAHOTSTEAM	(0xE39393,	13,	1,	2,	4,	0,	0,	EnumSymbol.NONE,		"hbmfluid.ultrahotsteam", true, false, false, 600),
		COOLANT			(0xd8fcff,	2,	1,	1,	1,	0,	0,	EnumSymbol.NONE,		"hbmfluid.coolant"),
		
		LAVA			(0xFF3300,	3,	1,	1,	4,	0,	0,	EnumSymbol.NOWATER,		"hbmfluid.lava", true, false, false, 1200),
		
		DEUTERIUM		(0x0000FF,	4,	1,	1,	3,	4,	0,	EnumSymbol.NONE,		"hbmfluid.deuterium"),
		TRITIUM			(0x000099,	5,	1,	1,	3,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.tritium"),

		OIL				(0x020202,	6,	1,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.oil"),
		HOTOIL			(0x300900,	8,	2,	1,	2,	3,	0,	EnumSymbol.NONE,		"hbmfluid.hotoil", true, false, false, 350),
		
		HEAVYOIL		(0x141312,	2,	2,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.heavyoil"),
		BITUMEN			(0x1f2426,	3,	2,	1,	2,	0,	0,	EnumSymbol.NONE,		"hbmfluid.bitumen"),
		SMEAR			(0x190f01,	7,	1,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.smear"),
		HEATINGOIL		(0x211806,	4,	2,	1,	2,	2,	0,	EnumSymbol.NONE,		"hbmfluid.heatingoil"),
		
		RECLAIMED		(0x332b22,	8,	1,	1,	2,	2,	0,	EnumSymbol.NONE,		"hbmfluid.reclaimed"),
		PETROIL			(0x44413d,	9,	1,	1,	1,	3,	0,	EnumSymbol.NONE,		"hbmfluid.petroil"),
		
		LUBRICANT		(0x606060,	10,	1,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.lubricant"),
		
		NAPHTHA			(0x595744,	5,	2,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.naphtha"),
		DIESEL			(0xf2eed5,	11,	1,	1,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.diesel"),
		
		LIGHTOIL		(0x8c7451,	6,	2,	1,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.lightoil"),
		KEROSENE		(0xffa5d2,	12,	1,	1,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.kerosene"),
		
		GAS				(0xfffeed,	13,	1,	1,	1,	4,	1,	EnumSymbol.NONE,		"hbmfluid.gas"),
		PETROLEUM		(0x7cb7c9,	7,	2,	1,	1,	4,	1,	EnumSymbol.NONE,		"hbmfluid.petroleum"),
		
		BIOGAS			(0xbfd37c,	12,	2,	1,	1,	4,	1,	EnumSymbol.NONE,		"hbmfluid.biogas"),
		BIOFUEL			(0xeef274,	13,	2,	1,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.biofuel"),

		NITAN			(0x8018ad,	15,	2,	1,	2,	4,	1,	EnumSymbol.NONE,		"hbmfluid.nitan"),
		
		UF6				(0xD1CEBE,	14,	1,	1,	4,	0,	2,	EnumSymbol.RADIATION,	"hbmfluid.uf6", false, true, false),
		PUF6			(0x4C4C4C,	15,	1,	1,	4,	0,	4,	EnumSymbol.RADIATION,	"hbmfluid.puf6", false, true, false),
		SAS3			(0x4ffffc,	14,	2,	1,	5,	0,	4,	EnumSymbol.RADIATION,	"hbmfluid.sas3", false, true, false),
		
		AMAT			(0x010101,	0,	2,	1,	5,	0,	5,	EnumSymbol.ANTIMATTER,	"hbmfluid.amat", false, false, true),
		ASCHRAB			(0xb50000,	1,	2,	1,	5,	0,	5,	EnumSymbol.ANTIMATTER,	"hbmfluid.aschrab", false, false, true),

		ACID			(0xfff7aa,	10,	2,	1,	3,	0,	3,	EnumSymbol.OXIDIZER,	"hbmfluid.acid", false, true, false),
		WATZ			(0x86653E,	11,	2,	1,	4,	0,	3,	EnumSymbol.ACID,		"hbmfluid.watz", false, true, false),
		CRYOGEL			(0x32ffff,	0,	1,	2,	2,	0,	0,	EnumSymbol.CROYGENIC,	"hbmfluid.cryogel"),
		
		HYDROGEN		(0x4286f4,	3,	1,	2,	3,	4,	0,	EnumSymbol.CROYGENIC,	"hbmfluid.hydrogen"),
		OXYGEN			(0x98bdf9,	4,	1,	2,	3,	0,	0,	EnumSymbol.CROYGENIC,	"hbmfluid.oxygen"),
		XENON			(0xba45e8,	5,	1,	2,	0,	0,	0,	EnumSymbol.ASPHYXIANT,	"hbmfluid.xenon"),
		BALEFIRE		(0x28e02e,	6,	1,	2,	4,	4,	3,	EnumSymbol.RADIATION,	"hbmfluid.balefire", true, true, false, 1500),
		
		MERCURY			(0x808080,	7,	1,	2,	2,	0,	0,	EnumSymbol.NONE,		"hbmfluid.mercury"),
		
		PLASMA_DT		(0xF7AFDE,	8,	1,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_dt", true, false, true, 3250),
		PLASMA_HD		(0xF0ADF4,	9,	1,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_hd", true, false, true, 2500),
		PLASMA_HT		(0xD1ABF2,	10,	1,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_ht", true, false, true, 3000),
		PLASMA_XM		(0xC6A5FF,	11,	1,	2,	0,	4,	1,	EnumSymbol.RADIATION,	"hbmfluid.plasma_xm", true, false, true, 4250),
		PLASMA_BF		(0xA7F1A3,	12,	1,	2,	4,	5,	4,	EnumSymbol.ANTIMATTER,	"hbmfluid.plasma_bf", true, false, true, 8500);
		

		//Approximate HEX Color of the fluid, used for pipe rendering
		private int color;
		//X position of the fluid on the sheet, the "row"
		private int textureX;
		//Y position of the fluid on the sheet, the "column"
		private int textureY;
		//ID of the texture sheet the fluid is on
		private int sheetID;
		//Unlocalized string ID of the fluid
		private String name;
		//Whether the fluid counts is too hot for certain tanks
		private boolean hot;
		//Whether the fluid counts as corrosive and requires a steel tank
		private boolean corrosive;
		//Whether the fluid is antimatter and requires magnetic storage
		private boolean antimatter;
		
		public int poison;
		public int flammability;
		public int reactivity;
		public EnumSymbol symbol;
		public int temperature;
		
		private FluidType(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name) {
			this.color = color;
			this.textureX = x;
			this.textureY = y;
			this.name = name;
			this.sheetID = sheet;
			this.poison = p;
			this.flammability = f;
			this.reactivity = r;
			this.symbol = symbol;
			this.temperature = 0;
		}
		
		private FluidType(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, boolean hot, boolean corrosive, boolean antimatter) {
			this.color = color;
			this.textureX = x;
			this.textureY = y;
			this.name = name;
			this.sheetID = sheet;
			this.poison = p;
			this.flammability = f;
			this.reactivity = r;
			this.symbol = symbol;
			this.hot = hot;
			this.corrosive = corrosive;
			this.antimatter = antimatter;
			this.temperature = 0;
		}
		
		private FluidType(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, boolean hot, boolean corrosive, boolean antimatter, int temperature) {
			this(color, x, y, sheet, p, f, r, symbol, name, hot, corrosive, antimatter);
			this.temperature = temperature;
		}

		public int getColor() {
			return this.color;
		}
		public int getMSAColor() {
			return this.color;
		}
		public int textureX() {
			return this.textureX;
		}
		public int textureY() {
			return this.textureY;
		}
		public int getSheetID() {
			return this.sheetID;
		}
		public String getUnlocalizedName() {
			return this.name;
		}
		
		public static FluidType getEnum(int i) {
			if(i < FluidType.values().length)
				return FluidType.values()[i];
			else
				return FluidType.NONE;
		}
		
		public static FluidType getEnumFromName(String s) {
			
			for(int i = 0; i < FluidType.values().length; i++)
				if(FluidType.values()[i].getName().equals(s))
					return FluidType.values()[i];
			
			return FluidType.NONE;
		}
		
		public int getID() {
			return Arrays.asList(FluidType.values()).indexOf(this);
		}
		
		public String getName() {
			return this.toString();
		}
		
		public boolean isHot() {
			return hot;
		}
		
		public boolean isCorrosive() {
			return corrosive;
		}
		
		public boolean isAntimatter() {
			return antimatter;
		}
	};
	
	//More stuff to follow.

}
