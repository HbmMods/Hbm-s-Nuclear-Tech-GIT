package com.hbm.crafting.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedOreRecipeExt extends ShapedOreRecipe {


	public ShapedOreRecipeExt(Block result, Object... recipe) { this(new ItemStack(result), recipe); }
	public ShapedOreRecipeExt(Item result, Object... recipe) { this(new ItemStack(result), recipe); }

	public ShapedOreRecipeExt(ItemStack result, Object... recipe) {
		super(result, recipe);
	}
}
