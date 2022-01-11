package com.hbm.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.FluidType.FluidTrait;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.render.util.EnumSymbol;

public class FluidTypeHandler {
	
	@Deprecated //you might spot this deprecated annotation and thing "oh goodie, that's something for me to remove and replace!"
	//no.
	//if you tough any of this i promise you will regret being born
	//deprecated means "avoid using in the future" not "fuck with this with your heart's content"
	//hands off. if you can read this, close this class now.
	public static class FluidTypeTheOldOne extends com.hbm.inventory.fluid.FluidType {

		/*WATER			= new FluidType (0x3333FF,	1,	1,	1,	0,	0,	0,	EnumSymbol.NONE,		"hbmfluid.water"),
		STEAM			= new FluidType (0xe5e5e5,	9,	2,	1,	3,	0,	0,	EnumSymbol.NONE,		"hbmfluid.steam", 100),
		HOTSTEAM		= new FluidType (0xE7D6D6,	1,	1,	2,	4,	0,	0,	EnumSymbol.NONE,		"hbmfluid.hotsteam", 300),
		SUPERHOTSTEAM	= new FluidType (0xE7B7B7,	2,	1,	2,	4,	0,	0,	EnumSymbol.NONE,		"hbmfluid.superhotsteam", 450),
		ULTRAHOTSTEAM	= new FluidType (0xE39393,	13,	1,	2,	4,	0,	0,	EnumSymbol.NONE,		"hbmfluid.ultrahotsteam", 600),
		COOLANT			= new FluidType (0xd8fcff,	2,	1,	1,	1,	0,	0,	EnumSymbol.NONE,		"hbmfluid.coolant"),
		
		LAVA			= new FluidType (0xFF3300,	3,	1,	1,	4,	0,	0,	EnumSymbol.NOWATER,		"hbmfluid.lava", 1200),
		
		DEUTERIUM		= new FluidType (0x0000FF,	4,	1,	1,	3,	4,	0,	EnumSymbol.NONE,		"hbmfluid.deuterium"),
		TRITIUM			= new FluidType (0x000099,	5,	1,	1,	3,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.tritium"),

		OIL				= new FluidType (0x020202,	6,	1,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.oil"),
		HOTOIL			= new FluidType (0x300900,	8,	2,	1,	2,	3,	0,	EnumSymbol.NONE,		"hbmfluid.hotoil", 350),
		
		HEAVYOIL		= new FluidType (0x141312,	2,	2,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.heavyoil"),
		BITUMEN			= new FluidType (0x1f2426,	3,	2,	1,	2,	0,	0,	EnumSymbol.NONE,		"hbmfluid.bitumen"),
		SMEAR			= new FluidType (0x190f01,	7,	1,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.smear"),
		HEATINGOIL		= new FluidType (0x211806,	4,	2,	1,	2,	2,	0,	EnumSymbol.NONE,		"hbmfluid.heatingoil"),
		
		RECLAIMED		= new FluidType (0x332b22,	8,	1,	1,	2,	2,	0,	EnumSymbol.NONE,		"hbmfluid.reclaimed"),
		PETROIL			= new FluidType (0x44413d,	9,	1,	1,	1,	3,	0,	EnumSymbol.NONE,		"hbmfluid.petroil"),
		
		LUBRICANT		= new FluidType (0x606060,	10,	1,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.lubricant"),
		
		NAPHTHA			= new FluidType (0x595744,	5,	2,	1,	2,	1,	0,	EnumSymbol.NONE,		"hbmfluid.naphtha"),
		DIESEL			= new FluidType (0xf2eed5,	11,	1,	1,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.diesel"),
		
		LIGHTOIL		= new FluidType (0x8c7451,	6,	2,	1,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.lightoil"),
		KEROSENE		= new FluidType (0xffa5d2,	12,	1,	1,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.kerosene"),
		
		GAS				= new FluidType (0xfffeed,	13,	1,	1,	1,	4,	1,	EnumSymbol.NONE,		"hbmfluid.gas"),
		PETROLEUM		= new FluidType (0x7cb7c9,	7,	2,	1,	1,	4,	1,	EnumSymbol.NONE,		"hbmfluid.petroleum"),
		LPG				= new FluidType (0x4747EA,	5,	2,	2,	1,	3,	1,	EnumSymbol.NONE,		"hbmfluid.lpg"),
		
		BIOGAS			= new FluidType (0xbfd37c,	12,	2,	1,	1,	4,	1,	EnumSymbol.NONE,		"hbmfluid.biogas"),
		BIOFUEL			= new FluidType (0xeef274,	13,	2,	1,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.biofuel"),

		NITAN			= new FluidType (0x8018ad,	15,	2,	1,	2,	4,	1,	EnumSymbol.NONE,		"hbmfluid.nitan"),
		
		UF6				= new FluidType (0xD1CEBE,	14,	1,	1,	4,	0,	2,	EnumSymbol.RADIATION,	"hbmfluid.uf6", FluidTrait.CORROSIVE),
		PUF6			= new FluidType (0x4C4C4C,	15,	1,	1,	4,	0,	4,	EnumSymbol.RADIATION,	"hbmfluid.puf6", FluidTrait.CORROSIVE, FluidTrait.LEAD_CONTAINER),
		SAS3			= new FluidType (0x4ffffc,	14,	2,	1,	5,	0,	4,	EnumSymbol.RADIATION,	"hbmfluid.sas3", FluidTrait.CORROSIVE, FluidTrait.LEAD_CONTAINER),
		SCHRABIDIC		= new FluidType (0x006B6B,	14,	1,	2,	5,	0,	5,	EnumSymbol.ACID,		"hbmfluid.schrabidic", FluidTrait.CORROSIVE_2, FluidTrait.LEAD_CONTAINER),
		
		AMAT			= new FluidType (0x010101,	0,	2,	1,	5,	0,	5,	EnumSymbol.ANTIMATTER,	"hbmfluid.amat", FluidTrait.AMAT),
		ASCHRAB			= new FluidType (0xb50000,	1,	2,	1,	5,	0,	5,	EnumSymbol.ANTIMATTER,	"hbmfluid.aschrab", FluidTrait.AMAT),

		ACID			= new FluidType (0xfff7aa,	10,	2,	1,	3,	0,	3,	EnumSymbol.OXIDIZER,	"hbmfluid.acid", FluidTrait.CORROSIVE),
		WATZ			= new FluidType (0x86653E,	11,	2,	1,	4,	0,	3,	EnumSymbol.ACID,		"hbmfluid.watz", FluidTrait.CORROSIVE_2, FluidTrait.LEAD_CONTAINER),
		CRYOGEL			= new FluidType (0x32ffff,	0,	1,	2,	2,	0,	0,	EnumSymbol.CROYGENIC,	"hbmfluid.cryogel", -170),
		
		HYDROGEN		= new FluidType (0x4286f4,	3,	1,	2,	3,	4,	0,	EnumSymbol.CROYGENIC,	"hbmfluid.hydrogen"),
		OXYGEN			= new FluidType (0x98bdf9,	4,	1,	2,	3,	0,	0,	EnumSymbol.CROYGENIC,	"hbmfluid.oxygen"),
		XENON			= new FluidType (0xba45e8,	5,	1,	2,	0,	0,	0,	EnumSymbol.ASPHYXIANT,	"hbmfluid.xenon"),
		BALEFIRE		= new FluidType (0x28e02e,	6,	1,	2,	4,	4,	3,	EnumSymbol.RADIATION,	"hbmfluid.balefire", 1500, FluidTrait.CORROSIVE, FluidTrait.LEAD_CONTAINER),
		
		MERCURY			= new FluidType (0x808080,	7,	1,	2,	2,	0,	0,	EnumSymbol.NONE,		"hbmfluid.mercury"),
		PAIN			= new FluidType (0x938541,	15,	1,	2,	2,	0,	1,	EnumSymbol.ACID,		"hbmfluid.pain", 300, FluidTrait.CORROSIVE),

		WASTEFLUID		= new FluidType (0x544400,	0,	2,	2,	2,	0,	1,	EnumSymbol.RADIATION,	"hbmfluid.wastefluid", FluidTrait.LEAD_CONTAINER),
		WASTEGAS		= new FluidType (0xB8B8B8,	1,	2,	2,	2,	0,	1,	EnumSymbol.RADIATION,	"hbmfluid.wastegas", FluidTrait.LEAD_CONTAINER),

		GASOLINE		= new FluidType (0x445772,	2,	2,	2,	1,	2,	0,	EnumSymbol.NONE,		"hbmfluid.gasoline"),
		SPENTSTEAM		= new FluidType (0x445772,	3,	2,	2,	2,	0,	0,	EnumSymbol.NONE,		"hbmfluid.spentsteam"),
		FRACKSOL		= new FluidType (0x798A6B,	4,	2,	2,	1,	3,	3,	EnumSymbol.ACID,		"hbmfluid.fracksol", FluidTrait.CORROSIVE),
		
		PLASMA_DT		= new FluidType (0xF7AFDE,	8,	1,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_dt", 3250, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID),
		PLASMA_HD		= new FluidType (0xF0ADF4,	9,	1,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_hd", 2500, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID),
		PLASMA_HT		= new FluidType (0xD1ABF2,	10,	1,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_ht", 3000, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID),
		PLASMA_XM		= new FluidType (0xC6A5FF,	11,	1,	2,	0,	4,	1,	EnumSymbol.RADIATION,	"hbmfluid.plasma_xm", 4250, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID),
		PLASMA_BF		= new FluidType (0xA7F1A3,	12,	1,	2,	4,	5,	4,	EnumSymbol.ANTIMATTER,	"hbmfluid.plasma_bf", 8500, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID),
		PLASMA_DH3		= new FluidType (0xFF83AA,	6,	2,	2,	0,	4,	0,	EnumSymbol.RADIATION,	"hbmfluid.plasma_dh3", 3480, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID),

		HELIUM3			= new FluidType (0xFCF0C4,	7,	2,	2,	3,	4,	0,	EnumSymbol.ASPHYXIANT,	"hbmfluid.helium3"),
		DEATH			= new FluidType (0x717A88,	8,	2,	2,	2,	0,	1,	EnumSymbol.ACID,		"hbmfluid.death", 300, FluidTrait.CORROSIVE_2, FluidTrait.LEAD_CONTAINER),
		ETHANOL			= new FluidType (0xe0ffff,	9,	2,	2,	2,	3,	0,	EnumSymbol.NONE,		"hbmfluid.ethanol"),
		HEAVYWATER		= new FluidType (0x00a0b0,	10,	2,	2,	1,	0,	0,	EnumSymbol.NONE,		"hbmfluid.heavywater"),
		CARBONDIOXIDE	= new FluidType (0x747474,	11,	2,	2,	3,	0,	0,	EnumSymbol.ASPHYXIANT,	"hbmfluid.carbondioxide");*/
				
		public static FluidType NONE = Fluids.NONE, WATER = Fluids.WATER, STEAM = Fluids.STEAM, HOTSTEAM = Fluids.HOTSTEAM,
				SUPERHOTSTEAM = Fluids.SUPERHOTSTEAM, ULTRAHOTSTEAM = Fluids.ULTRAHOTSTEAM, COOLANT = Fluids.COOLANT, LAVA = Fluids.LAVA, DEUTERIUM = Fluids.DEUTERIUM,
				TRITIUM = Fluids.TRITIUM, OIL = Fluids.OIL, HOTOIL = Fluids.HOTOIL, HEAVYOIL = Fluids.HEAVYOIL, BITUMEN = Fluids.BITUMEN, SMEAR = Fluids.SMEAR,
				HEATINGOIL = Fluids.HEATINGOIL, RECLAIMED = Fluids.RECLAIMED, PETROIL = Fluids.PETROIL, LUBRICANT = Fluids.LUBRICANT, NAPHTHA = Fluids.NAPHTHA,
				DIESEL = Fluids.DIESEL, LIGHTOIL = Fluids.LIGHTOIL, KEROSENE = Fluids.KEROSENE, GAS = Fluids.GAS, PETROLEUM = Fluids.PETROLEUM, LPG = Fluids.LPG,
				BIOGAS = Fluids.BIOGAS, BIOFUEL = Fluids.BIOFUEL, NITAN = Fluids.NITAN, UF6 = Fluids.UF6, PUF6 = Fluids.PUF6, SAS3 = Fluids.SAS3, SCHRABIDIC = Fluids.SCHRABIDIC,
				AMAT = Fluids.AMAT, ASCHRAB = Fluids.ASCHRAB, ACID = Fluids.ACID, WATZ = Fluids.WATZ, CRYOGEL = Fluids.CRYOGEL, HYDROGEN = Fluids.HYDROGEN, OXYGEN = Fluids.OXYGEN,
				XENON = Fluids.XENON, BALEFIRE = Fluids.BALEFIRE, MERCURY = Fluids.MERCURY, PAIN = Fluids.PAIN, WASTEFLUID = Fluids.WASTEFLUID, WASTEGAS = Fluids.WASTEGAS,
				GASOLINE = Fluids.GASOLINE, SPENTSTEAM = Fluids.SPENTSTEAM, FRACKSOL = Fluids.FRACKSOL, PLASMA_DT = Fluids.PLASMA_DT, PLASMA_HD = Fluids.PLASMA_HD,
				PLASMA_HT = Fluids.PLASMA_HT, PLASMA_XM = Fluids.PLASMA_XM, PLASMA_BF = Fluids.PLASMA_BF, PLASMA_DH3 = Fluids.PLASMA_DH3, HELIUM3 = Fluids.HELIUM3,
				DEATH = Fluids.DEATH, ETHANOL = Fluids.ETHANOL, HEAVYWATER = Fluids.HEAVYWATER, CARBONDIOXIDE = Fluids.CARBONDIOXIDE;

		/*//Approximate HEX Color of the fluid, used for pipe rendering
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
		
		public int poison;
		public int flammability;
		public int reactivity;
		public EnumSymbol symbol;
		public int temperature;
		public List<FluidTrait> traits = new ArrayList();*/
		
		private FluidTypeTheOldOne(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name) {
			this(color, x, y, sheet, p, f, r, symbol, name, 0, new FluidTrait[0]);
		}
		
		private FluidTypeTheOldOne(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, FluidTrait... traits) {
			this(color, x, y, sheet, p, f, r, symbol, name, 0, traits);
		}
		
		private FluidTypeTheOldOne(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, int temperature) {
			this(color, x, y, sheet, p, f, r, symbol, name, temperature, new FluidTrait[0]);
		}
		
		private FluidTypeTheOldOne(int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, int temperature, FluidTrait... traits) {
			super(color, x, y, sheet, p, f, r, symbol, name, temperature, traits);
		}
		
		/*public static com.hbm.inventory.fluid.FluidType[] values() {
			return Fluids.metaOrder.toArray(new com.hbm.inventory.fluid.FluidType[0]);
		}
		
		public boolean needsLeadContainer() {
			return this.traits.contains(FluidTrait.LEAD_CONTAINER);
		}*/
	};
}
