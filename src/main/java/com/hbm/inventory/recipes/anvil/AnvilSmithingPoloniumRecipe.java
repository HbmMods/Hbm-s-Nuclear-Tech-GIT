package com.hbm.inventory.recipes.anvil;

import com.google.common.annotations.Beta;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.transformer.HazardTransformerRadiationNBT;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
@Beta
public class AnvilSmithingPoloniumRecipe extends AnvilSmithingRecipe
{

	public AnvilSmithingPoloniumRecipe()
	{
		super(1, new ItemStack(Items.bread), new ComparableStack(Items.bread), new OreDictStack("nuggetPolonium210"));
		output = HazardTransformerRadiationNBT.addRadNBT(output, HazardRegistry.po210);
	}
	
	@Override
	public boolean matches(ItemStack left, ItemStack right)
	{
		return doesStackMatch(right, this.right) && left.getItem() instanceof ItemFood;
	}
	
	@Override
	public int matchesInt(ItemStack left, ItemStack right)
	{
		return matches(left, right) ? 0 : -1;
	}
	
	@Override
	public ItemStack getOutput(ItemStack left, ItemStack right)
	{
		ItemStack out = left.copy();
		out.stackSize = 1;
		if (!out.hasTagCompound())
			out.stackTagCompound = new NBTTagCompound();
		
		out.stackTagCompound.setFloat(HazardTransformerRadiationNBT.RAD_KEY, HazardRegistry.po210);
		return out.copy();
	}

}
