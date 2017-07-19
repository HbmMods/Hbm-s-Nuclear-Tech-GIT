package com.hbm.handler;

import com.hbm.items.tool.ItemAssemblyTemplate.EnumAssemblyTemplate;

public class FluidTypeHandler {
	
	public enum FluidType {
		NONE(0x888888, 8947848, 0, 0, "hbmfluid.none"), WATER(0x3333FF, 3355647, 1, 0, "hbmfluid.water"),
		LAVA(0xFF3300, 16724736, 2, 0, "hbmfluid.lava"), UF6(0xD1CEBE, 13749950, 3, 0, "hbmfluid.uf6"),
		PUF6(0x4C4C4C, 5000268, 0, 1, "hbmfluid.puf6"), DEUTERIUM(0x0000FF, 255, 1, 1, "hbmfluid.deuterium"),
		TRITIUM(0x000099, 153, 2, 1, "hbmfluid.tritium"), OIL(0x020202, 131586, 3, 1, "hbmfluid.oil"),
		SMEAR(0x190f01, 1642241, 0, 2, "hbmfluid.smear"), LUBRICANT(0x606060, 6316128, 1, 2, "hbmfluid.lubricant"),
		DIESEL(0xf2eed5, 15920853, 2, 2, "hbmfluid.diesel"), KEROSENE(0xffa5d2, 16754130, 3, 2, "hbmfluid.kerosene"),
		GAS(0xfffeed, 16776941, 0, 3, "hbmfluid.gas");

		private int color;
		private int msa;
		private int textureX;
		private int textureY;
		private String name;
		
		private FluidType(int color, int msa, int x, int y, String name) {
			this.color = color;
			this.msa = msa;
			this.textureX = x;
			this.textureY = y;
			this.name = name;
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
		public String getUnlocalizedName() {
			return this.name;
		}
		
		public static FluidType getEnum(int i) {
			return FluidType.values()[i];
		}
		
		public String getName() {
			return this.toString();
		}
	};
	
	//More stuff to follow.

}
