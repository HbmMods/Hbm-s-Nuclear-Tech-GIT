package com.hbm.blocks.generic;

import com.hbm.blocks.BlockEnumMulti;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockConcreteColored extends BlockEnumMulti {
	public BlockConcreteColored(Material mat) {
		super(mat, EnumConcreteType.class, true, true);
	}
	
	// Order matches the original concrete ordering
	public enum EnumConcreteType {
		WHITE,
		ORANGE,
		MAGENTA,
		LIGHTBLUE,
		YELLOW,
		LIME,
		PINK,
		GRAY,
		SILVER,
		CYAN,
		PURPLE,
		BLUE,
		BROWN,
		GREEN,
		RED,
		BLACK,
	}

	public MapColor getMapColor(int p_149728_1_) {
		return MapColor.getMapColorForBlockColored(p_149728_1_);
	}
}
