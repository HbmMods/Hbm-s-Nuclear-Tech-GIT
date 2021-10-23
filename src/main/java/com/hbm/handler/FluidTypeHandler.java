package com.hbm.handler;

import java.util.Arrays;
import java.util.HashSet;

import com.google.common.collect.ImmutableSet;
import com.hbm.render.util.EnumSymbol;

public class FluidTypeHandler {
	
	public static enum FluidTrait
	{
		ANTIMATTER,
		AMAT,
		NO_CONTAINER,
		NO_ID,
		CRYO,
		HOT,
		CORROSIVE,
		CORROSIVE_STRONG,
		TOXIC,
		BIOHAZARD,
		CHEMICAL,
		RADIOACTIVE;
	}
	public enum FluidType
	{
		NONE			(0x888888,	0,	1,	1,	0,	0,	0,	EnumSymbol.NONE,		"hbmfluid.none"),

		WATER			(0x3333FF,	1,	1,	1,	0,	0,	0,	EnumSymbol.NONE,		"hbmfluid.water"),
		STEAM			(0xe5e5e5,	9,	2,	1,	3,	0,	0,	EnumSymbol.HOT, 		"hbmfluid.steam", true, false, false, 100),
		HOTSTEAM		(0xE7D6D6,	1,	1,	2,	4,	0,	0,	EnumSymbol.HOT, 		"hbmfluid.hotsteam", true, false, false, 300),
		SUPERHOTSTEAM	(0xE7B7B7,	2,	1,	2,	4,	0,	0,	EnumSymbol.HOT, 		"hbmfluid.superhotsteam", true, false, false, 450),
		ULTRAHOTSTEAM	(0xE39393,	13,	1,	2,	4,	0,	0,	EnumSymbol.HOT, 		"hbmfluid.ultrahotsteam", true, false, false, 600),
		COOLANT			(0xd8fcff,	2,	1,	1,	1,	0,	0,	EnumSymbol.NONE,		"hbmfluid.coolant"),
		
		LAVA			(0xFF3300,	3,	1,	1,	4,	0,	0,	EnumSymbol.NOWATER,		"hbmfluid.lava", 1200),
		
		DEUTERIUM		(0x0000FF,	4,	1,	1,	3,	4,	0,	EnumSymbol.NONE,		"hbmfluid.deuterium"),
		TRITIUM			(0x000099,	5,	1,	1,	3,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.tritium", false, false, false, FluidTrait.RADIOACTIVE),

		OIL				(0x020202,	6,	1,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.oil"),
		HOTOIL			(0x300900,	8,	2,	1,	2,	3,	0,	EnumSymbol.HOT,			"hbmfluid.hotoil", true, false, false, 350),
		
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
		LPG				(0x4747EA,	5,	2,	2,	1,	3,	1,	EnumSymbol.NONE,		"hbmfluid.lpg"),
		
		BIOGAS			(0xbfd37c,	12,	2,	1,	1,	4,	1,	EnumSymbol.NONE,		"hbmfluid.biogas"),
		BIOFUEL			(0xeef274,	13,	2,	1,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.biofuel"),

		NITAN			(0x8018ad,	15,	2,	1,	2,	4,	1,	EnumSymbol.NONE,		"hbmfluid.nitan"),
		
		UF6				(0xD1CEBE,	14,	1,	1,	4,	0,	2,	EnumSymbol.RADIATION,	"hbmfluid.uf6", false, true, false, FluidTrait.RADIOACTIVE),
		PUF6			(0x4C4C4C,	15,	1,	1,	4,	0,	4,	EnumSymbol.RADIATION,	"hbmfluid.puf6", false, true, false, FluidTrait.RADIOACTIVE),
		SAS3			(0x4ffffc,	14,	2,	1,	5,	0,	4,	EnumSymbol.RADIATION,	"hbmfluid.sas3", false, true, false, FluidTrait.RADIOACTIVE),
		SCHRABIDIC		(0x006B6B,	14,	1,	2,	5,	0,	5,	EnumSymbol.ACID,		"hbmfluid.schrabidic", false, true, false, FluidTrait.RADIOACTIVE, FluidTrait.CORROSIVE_STRONG),
		
		AMAT			(0x010101,	0,	2,	1,	5,	0,	5,	EnumSymbol.ANTIMATTER,	"hbmfluid.amat", false, false, true),
		ASCHRAB			(0xb50000,	1,	2,	1,	5,	0,	5,	EnumSymbol.ANTIMATTER,	"hbmfluid.aschrab", false, false, true, FluidTrait.RADIOACTIVE),

		ACID			(0xfff7aa,	10,	2,	1,	3,	0,	3,	EnumSymbol.OXIDIZER,	"hbmfluid.acid", false, true, false),
		WATZ			(0x86653E,	11,	2,	1,	4,	0,	3,	EnumSymbol.ACID,		"hbmfluid.watz", false, true, false, 1500, FluidTrait.CORROSIVE_STRONG, FluidTrait.RADIOACTIVE),
		CRYOGEL			(0x32ffff,	0,	1,	2,	2,	0,	0,	EnumSymbol.CROYGENIC,	"hbmfluid.cryogel", false, false, false, -170),
		
		HYDROGEN		(0x4286f4,	3,	1,	2,	3,	4,	0,	EnumSymbol.CROYGENIC,	"hbmfluid.hydrogen", false, false, false, -253),
		OXYGEN			(0x98bdf9,	4,	1,	2,	3,	0,	0,	EnumSymbol.CROYGENIC,	"hbmfluid.oxygen", false, false, false, -183),
		XENON			(0xba45e8,	5,	1,	2,	0,	0,	0,	EnumSymbol.ASPHYXIANT,	"hbmfluid.xenon"),
		// Make highly corrosive
		SALT			(0x00e1ed,	1, 	2, 	2,	4, 	0,	4, 	EnumSymbol.ACID,		"hbmfluid.salt", true, true, false, 220, FluidTrait.CORROSIVE_STRONG),
		SALT_U			(0x60f7ff, 	2,	2,	2,	4,	0,	4,	EnumSymbol.RADIATION,	"hbmfluid.salt_u", true, true, false, 220, FluidTrait.CORROSIVE_STRONG, FluidTrait.RADIOACTIVE),
		SALT_PU			(0x00686d,	3,	2,	2,	4,	0,	5,	EnumSymbol.RADIATION,	"hbmfluid.salt_pu", true, true, false, 220, FluidTrait.CORROSIVE_STRONG, FluidTrait.RADIOACTIVE),
		SAS3_NIT		(0x47dedb, 	4,	2,	2,	4,	2,	4,	EnumSymbol.RADIATION, 	"hbmfluid.sas3_nit", false, true, false, FluidTrait.RADIOACTIVE),
		BALEFIRE		(0x28e02e,	6,	1,	2,	4,	4,	3,	EnumSymbol.RADIATION,	"hbmfluid.balefire", true, true, false, 1500, FluidTrait.RADIOACTIVE),
		
		SALT_U_DP		(0x40a0a5, 	8,	2,	2,	5,	0,	4,	EnumSymbol.RADIATION,	"hbmfluid.salt_u_dp", true, true, false, 320, FluidTrait.CORROSIVE_STRONG, FluidTrait.RADIOACTIVE),
		SALT_PU_DP		(0x003234,	9,	2,	2,	5,	0,	4,	EnumSymbol.RADIATION,	"hbmfluid.salt_pu_dp", true, true, false, 320, FluidTrait.CORROSIVE_STRONG, FluidTrait.RADIOACTIVE),
		SAS3_DP			(0x319b99,	10,	2,	1,	5,	2,	4,	EnumSymbol.RADIATION,	"hbmfluid.sas3_dp", true, true, false, 400, FluidTrait.RADIOACTIVE),
		SAS3_NIT_DP		(0x297e7c, 	11,	2,	2,	5,	2,	4,	EnumSymbol.RADIATION, 	"hbmfluid.sas3_nit_dp", false, true, false, 450, FluidTrait.CORROSIVE_STRONG, FluidTrait.RADIOACTIVE),
		BALEFIRE_DP		(0x136615,	7,	1,	2,	5,	4,	3,	EnumSymbol.RADIATION,	"hbmfluid.balefire_dp", true, true, false, 2250, FluidTrait.RADIOACTIVE),
		PAIN			(0x938541,	15,	1,	2,	2,	0,	1,	EnumSymbol.ACID,		"hbmfluid.pain", 300, FluidTrait.CORROSIVE),
		WASTEGAS		(0xB8B8B8,	1,	2,	2,	2,	0,	1,	EnumSymbol.RADIATION,	"hbmfluid.wastegas", FluidTrait.RADIOACTIVE),
		WASTEFLUID		(0x544400,	0,	2,	2,	2,	0,	1,	EnumSymbol.RADIATION,	"hbmfluid.wastefluid", FluidTrait.RADIOACTIVE),
		GASOLINE		(0x445772,	2,	2,	2,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.gasoline"),
		SPENTSTEAM		(0x445772,	3,	2,	2,	2,	0,	0,	EnumSymbol.NONE,		"hbmfluid.spentsteam", FluidTrait.NO_CONTAINER),
		FRACKSOL		(0x798A6B,	4,	2,	2,	1,	3,	3,	EnumSymbol.ACID,		"hbmfluid.fracksol", FluidTrait.CORROSIVE),
		
		MERCURY			(0x808080,	7,	1,	2,	2,	0,	0,	EnumSymbol.NONE,		"hbmfluid.mercury", FluidTrait.TOXIC),
		
		PLASMA_DT		(0xF7AFDE,	8,	1,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_dt", true, false, true, 3250),
		PLASMA_HD		(0xF0ADF4,	9,	1,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_hd", true, false, true, 2500),
		PLASMA_HT		(0xD1ABF2,	10,	1,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_ht", true, false, true, 3000),
		PLASMA_XM		(0xC6A5FF,	11,	1,	2,	0,	4,	1,	EnumSymbol.RADIATION,	"hbmfluid.plasma_xm", true, false, true, 4250),
		PLASMA_BF		(0xA7F1A3,	12,	1,	2,	4,	5,	4,	EnumSymbol.ANTIMATTER,	"hbmfluid.plasma_bf", true, false, true, 8500),
		PLASMA_WARP		(0x00FFFF,	15,	1,	2,	5,	5,	5,	EnumSymbol.ANTIMATTER,	"hbmfluid.plasma_warp", true, false, true, 1200000),
		ALCOHOL			(0x00bcbc, 	16, 2, 	2,	1, 	3,	0,  EnumSymbol.NONE,		"hbmfluid.alcohol"),
		
		SARIN			(0x00b4b4,	17, 2,	2,	4,	1,	1,	EnumSymbol.CHEMICAL,	"hbmfluid.sarin", FluidTrait.CHEMICAL);
		
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
		//private boolean hot;
		//Whether the fluid counts as corrosive and requires a steel tank
		//private boolean corrosive;
		//Whether the fluid is antimatter and requires magnetic storage
		//private boolean antimatter;
		
		private HashSet<FluidTrait> traits = new HashSet<FluidTrait>();
		
		public int poison;
		public int flammability;
		public int reactivity;
		public EnumSymbol symbol;
		public int temperature;
		
		private FluidType(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, FluidTrait...traits) {
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
			if (traits != null)
				this.traits.addAll(Arrays.asList(traits));
			if (p > 1)
				this.traits.add(FluidTrait.TOXIC);
		}
		
		private FluidType(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, int temp, FluidTrait...traits)
		{
			this(color, x, y, sheet, p, f, r, symbol, name, traits);
			temperature = temp;
		}
		
		private FluidType(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, boolean hot, boolean corrosive, boolean antimatter, FluidTrait...hazards) {
			this.color = color;
			this.textureX = x;
			this.textureY = y;
			this.name = name;
			this.sheetID = sheet;
			this.poison = p;
			this.flammability = f;
			this.reactivity = r;
			this.symbol = symbol;
			if (p > 1)
				this.traits.add(FluidTrait.TOXIC);
			if (hot)
				this.traits.add(FluidTrait.HOT);
			if (corrosive)
				this.traits.add(FluidTrait.CORROSIVE);
			if (antimatter)
				this.traits.add(FluidTrait.ANTIMATTER);
			this.temperature = 0;
			if (hazards != null)
				this.traits.addAll(Arrays.asList(hazards));
		}
		
		private FluidType(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, boolean hot, boolean corrosive, boolean antimatter, int temperature, FluidTrait...hazards) {
			this(color, x, y, sheet, p, f, r, symbol, name, hot, corrosive, antimatter, hazards);
			this.temperature = temperature;
			if (!hot && temperature < 20)
				this.traits.add(FluidTrait.CRYO);
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
			return this.temperature >= 100;
		}
		
		public boolean isCorrosive() {
			return this.traits.contains(FluidTrait.CORROSIVE) || this.traits.contains(FluidTrait.CORROSIVE_STRONG);
		}
		
		public boolean isAntimatter() {
			return this.traits.contains(FluidTrait.AMAT);
		}
		
		public ImmutableSet<FluidTrait> getTraitSet()
		{
			return ImmutableSet.copyOf(traits);
		}
		
		public boolean hasNoContainer() {
			return this.traits.contains(FluidTrait.NO_CONTAINER);
		}
		
		public boolean hasNoID() {
			return this.traits.contains(FluidTrait.NO_ID);
		}
	}
}
