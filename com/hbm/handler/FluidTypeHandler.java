package com.hbm.handler;

import java.util.Arrays;

public class FluidTypeHandler {
	
	public enum FluidType {
		NONE		(0x888888, 8947848, 	0,	1,	1, "hbmfluid.none"),

		WATER		(0x3333FF, 3355647, 	1,	1,	1,  "hbmfluid.water"),
		STEAM		(0xe5e5e5, 15066597, 	9,	1,	1,  "hbmfluid.steam"),
		COOLANT		(0xd8fcff, 14220543, 	2,	1,	1,  "hbmfluid.coolant"),
		
		LAVA		(0xFF3300, 16724736, 	3,	1,	1,  "hbmfluid.lava"),
		
		DEUTERIUM	(0x0000FF, 255, 		4,	1,	1,  "hbmfluid.deuterium"),
		TRITIUM		(0x000099, 153, 		5,	1,	1,  "hbmfluid.tritium"),

		OIL			(0x020202, 131586, 		6,	1,	1,  "hbmfluid.oil"),
		HOTOIL		(0x300900, 3148032, 	8,	1,	1,  "hbmfluid.hotoil"),
		
		HEAVYOIL	(0x141312, 1315602,		2,	2,	1,  "hbmfluid.heavyoil"),
		BITUMEN		(0x1f2426, 2040870,		3,	2,	1,  "hbmfluid.bitumen"),
		SMEAR		(0x190f01, 1642241, 	7,	1,	1,  "hbmfluid.smear"),
		HEATINGOIL	(0x211806, 2168838,		4,	2,	1,  "hbmfluid.heatingoil"),
		
		RECLAIMED	(0x332b22, 3353378, 	8,	1,	1,  "hbmfluid.reclaimed"),
		PETROIL		(0x44413d, 4473149, 	9,	1,	1,  "hbmfluid.petroil"),
		
		LUBRICANT	(0x606060, 6316128, 	10,	1,	1,  "hbmfluid.lubricant"),
		
		NAPHTHA		(0x595744, 5855044,		5,	2,	1,  "hbmfluid.naphtha"),
		DIESEL		(0xf2eed5, 15920853, 	11,	1,	1,  "hbmfluid.diesel"),
		
		LIGHTOIL	(0x8c7451, 9204817,		6,	2,	1,  "hbmfluid.lightoil"),
		KEROSENE	(0xffa5d2, 16754130, 	12,	1,	1,  "hbmfluid.kerosene"),
		
		GAS			(0xfffeed, 16776941, 	13,	1,	1,  "hbmfluid.gas"),
		PETROLEUM	(0x7cb7c9, 8173513,		7,	2,	1,  "hbmfluid.petroleum"),
		
		BIOGAS		(0xbfd37c, 12571516, 	12,	2,	1,  "hbmfluid.biogas"),
		BIOFUEL		(0xeef274, 15659636,	13,	2,	1,  "hbmfluid.biofuel"),

		NITAN		(0x8018ad, 8394925, 	15,	2,	1,  "hbmfluid.nitan"),
		
		UF6			(0xD1CEBE, 13749950, 	14,	1,	1,  "hbmfluid.uf6"),
		PUF6		(0x4C4C4C, 5000268, 	15,	1,	1,  "hbmfluid.puf6"),
		SAS3		(0x4ffffc, 5242876, 	14,	2,	1,  "hbmfluid.sas3"),
		
		AMAT		(0x010101, 65793, 		0,	2,	1,  "hbmfluid.amat"),
		ASCHRAB		(0xb50000, 11862016, 	1,	2,	1,  "hbmfluid.aschrab"),

		ACID		(0xfff7aa, 16775082, 	10,	2,	1,  "hbmfluid.acid"),
		WATZ		(0x86653E, 8807742, 	11,	2,	1,  "hbmfluid.watz"),
		CRYOGEL		(0x32ffff, 3342335, 	0,	1,	2,  "hbmfluid.cryogel");
		

		//Approximate HEX Color of the fluid, used for pipe rendering
		private int color;
		//Converted MS Access color code, used for item rendering
		private int msa;
		//X position of the fluid on the sheet, the "row"
		private int textureX;
		//Y position of the fluid on the sheet, the "column"
		private int textureY;
		//ID of the texture sheet the fluid is on
		private int sheetID;
		//Unlocalized string ID of the fluid
		private String name;
		
		private FluidType(int color, int msa, int x, int y, int sheet, String name) {
			this.color = color;
			this.msa = msa;
			this.textureX = x;
			this.textureY = y;
			this.name = name;
			this.sheetID = sheet;
		}

		public int getColor() {
			return this.color;
		}
		public int getMSAColor() {
			return this.msa;
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
	};
	
	//More stuff to follow.

}
