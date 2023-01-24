package com.hbm.wiaj.cannery;

import com.hbm.wiaj.JarScript;

import net.minecraft.item.ItemStack;

public abstract class CanneryBase {

	public static final int[] colorCopper =	new int[] {0xFFFDCA88, 0xFFD57C4F, 0xFFAB4223, 0xFF1A1F22};
	public static final int[] colorGold =	new int[] {0xFFFFFDE0, 0xFFFAD64A, 0xFFDC9613, 0xFF1A1F22};
	public static final int[] colorBlue =	new int[] {0xFFA5D9FF, 0xFF39ACFF, 0xFF1A6CA7, 0xFF1A1F22};
	public static final int[] colorGrey =	new int[] {0xFFD1D1D1, 0xFF919191, 0xFF5D5D5D, 0xFF302E36};

	public abstract ItemStack getIcon();
	public abstract String getName();
	public abstract JarScript createScript();
	public CanneryBase[] seeAlso() { return new CanneryBase[0]; }
}
