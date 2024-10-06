package com.hbm.blocks.generic;

import com.hbm.blocks.BlockEnumMulti;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockConcreteColored extends BlockEnumMulti {
	public BlockConcreteColored(Material mat) {
		super(mat, EnumConcreteType.class, true, true);
	}
	
	// Order matches the vanilla dye colors
	public enum EnumConcreteType {
		BLACK,
		RED,
		GREEN,
		BROWN,
		BLUE,
		PURPLE,
		CYAN,
		SILVER,
		GRAY,
		PINK,
		LIME,
		YELLOW,
		LIGHTBLUE,
		MAGENTA,
		ORANGE,
		WHITE
	}

	public MapColor getMapColor(int p_149728_1_) {
		return MapColor.getMapColorForBlockColored(p_149728_1_);
	}
}
