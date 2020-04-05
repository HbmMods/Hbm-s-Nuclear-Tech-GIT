package com.hbm.handler;

import java.util.Arrays;

public class FluidTypeHandler {
	
	public enum FluidType {
		NONE			(0x888888,	0,	1,	1, "hbmfluid.none"),

		WATER			(0x3333FF,	1,	1,	1,  "hbmfluid.water"),
		STEAM			(0xe5e5e5,	9,	2,	1,  "hbmfluid.steam", true, false, false),
		HOTSTEAM		(0xE7D6D6,	1,	1,	2,  "hbmfluid.hotsteam", true, false, false),
		SUPERHOTSTEAM	(0xE7B7B7,	2,	1,	2,  "hbmfluid.superhotsteam", true, false, false),
		COOLANT			(0xd8fcff,	2,	1,	1,  "hbmfluid.coolant"),
		
		LAVA			(0xFF3300,	3,	1,	1,  "hbmfluid.lava", true, false, false),
		
		DEUTERIUM		(0x0000FF,	4,	1,	1,  "hbmfluid.deuterium"),
		TRITIUM			(0x000099,	5,	1,	1,  "hbmfluid.tritium"),

		OIL				(0x020202,	6,	1,	1,  "hbmfluid.oil"),
		HOTOIL			(0x300900,	8,	2,	1,  "hbmfluid.hotoil", true, false, false),
		
		HEAVYOIL		(0x141312,	2,	2,	1,  "hbmfluid.heavyoil"),
		BITUMEN			(0x1f2426,	3,	2,	1,  "hbmfluid.bitumen"),
		SMEAR			(0x190f01,	7,	1,	1,  "hbmfluid.smear"),
		HEATINGOIL		(0x211806,	4,	2,	1,  "hbmfluid.heatingoil"),
		
		RECLAIMED		(0x332b22,	8,	1,	1,  "hbmfluid.reclaimed"),
		PETROIL			(0x44413d,	9,	1,	1,  "hbmfluid.petroil"),
		
		LUBRICANT		(0x606060,	10,	1,	1,  "hbmfluid.lubricant"),
		
		NAPHTHA			(0x595744,	5,	2,	1,  "hbmfluid.naphtha"),
		DIESEL			(0xf2eed5,	11,	1,	1,  "hbmfluid.diesel"),
		
		LIGHTOIL		(0x8c7451,	6,	2,	1,  "hbmfluid.lightoil"),
		KEROSENE		(0xffa5d2,	12,	1,	1,  "hbmfluid.kerosene"),
		
		GAS				(0xfffeed,	13,	1,	1,  "hbmfluid.gas"),
		PETROLEUM		(0x7cb7c9,	7,	2,	1,  "hbmfluid.petroleum"),
		
		BIOGAS			(0xbfd37c,	12,	2,	1,  "hbmfluid.biogas"),
		BIOFUEL			(0xeef274,	13,	2,	1,  "hbmfluid.biofuel"),

		NITAN			(0x8018ad,	15,	2,	1,  "hbmfluid.nitan"),
		
		UF6				(0xD1CEBE,	14,	1,	1,  "hbmfluid.uf6", false, true, false),
		PUF6			(0x4C4C4C,	15,	1,	1,  "hbmfluid.puf6", false, true, false),
		SAS3			(0x4ffffc,	14,	2,	1,  "hbmfluid.sas3", false, true, false),
		
		AMAT			(0x010101,	0,	2,	1,  "hbmfluid.amat", false, false, true),
		ASCHRAB			(0xb50000,	1,	2,	1,  "hbmfluid.aschrab", false, false, true),

		ACID			(0xfff7aa,	10,	2,	1,  "hbmfluid.acid", false, true, false),
		WATZ			(0x86653E,	11,	2,	1,  "hbmfluid.watz", false, true, false),
		CRYOGEL			(0x32ffff,	0,	1,	2,  "hbmfluid.cryogel"),
		
		HYDROGEN		(0x4286f4,	3,	1,	2,  "hbmfluid.hydrogen"),
		OXYGEN			(0x98bdf9,	4,	1,	2,  "hbmfluid.oxygen"),
		XENON			(0xba45e8,	5,	1,	2,  "hbmfluid.xenon"),
		BALEFIRE		(0x28e02e,	6,	1,	2,  "hbmfluid.balefire", true, true, false);
		

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
		
		private FluidType(int color, int x, int y, int sheet, String name) {
			this.color = color;
			this.textureX = x;
			this.textureY = y;
			this.name = name;
			this.sheetID = sheet;
		}
		
		private FluidType(int color, int x, int y, int sheet, String name, boolean hot, boolean corrosive, boolean antimatter) {
			this.color = color;
			this.textureX = x;
			this.textureY = y;
			this.name = name;
			this.sheetID = sheet;
			this.hot = hot;
			this.corrosive = corrosive;
			this.antimatter = antimatter;
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
