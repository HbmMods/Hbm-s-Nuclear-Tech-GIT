package com.hbm.handler;

import com.hbm.items.tool.ItemAssemblyTemplate.EnumAssemblyTemplate;

public class FluidTypeHandler {
	
	public enum FluidType {
		NONE(0x888888, 8947848, 0, 0), WATER(0x3333FF, 3355647, 1, 0), LAVA(0xFF3300, 16724736, 2, 0), UF6(0xD1CEBE, 13749950, 3, 0), PUF6(0x4C4C4C, 5000268, 0, 1),
		DEUTERIUM(0x0000FF, 255, 1, 1), TRITIUM(0x000099, 153, 2, 1);

		private int color;
		private int msa;
		private int textureX;
		private int textureY;
		
		private FluidType(int color, int msa, int x, int y) {
			this.color = color;
			this.msa = msa;
			this.textureX = x;
			this.textureY = y;
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
		
		public static FluidType getEnum(int i) {
			return FluidType.values()[i];
		}
		
		public String getName() {
			return this.toString();
		}
	};
	
	//More stuff to follow.

}
