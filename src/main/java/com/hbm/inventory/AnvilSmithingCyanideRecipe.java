package com.hbm.inventory;

import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AnvilSmithingCyanideRecipe extends AnvilSmithingRecipe {
	
	public AnvilSmithingCyanideRecipe() {
		super(0, new ItemStack(Items.bread), new ComparableStack(Items.bread), new ComparableStack(ModItems.plan_c));
		
		if(!this.output.hasTagCompound())
			this.output.stackTagCompound = new NBTTagCompound();
		
		this.output.stackTagCompound.setBoolean("ntmCyanide", true);
		
	}
	
	@Override
	public boolean matches(ItemStack left, ItemStack right) {
		return doesStackMatch(right, this.right) && left.getItem() instanceof ItemFood;
	}

	@Override
	public int matchesInt(ItemStack left, ItemStack right) {
		return matches(left, right) ? 0 : -1;
	}
	
	@Override
	public ItemStack getOutput(ItemStack left, ItemStack right) {
		
		ItemStack out = left.copy();
		if(!out.hasTagCompound())
			out.stackTagCompound = new NBTTagCompound();
		
		out.stackTagCompound.setBoolean("ntmCyanide", true);
		
		return out;
	}
}
