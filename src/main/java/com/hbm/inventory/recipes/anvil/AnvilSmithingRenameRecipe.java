package com.hbm.inventory.recipes.anvil;

import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AnvilSmithingRenameRecipe extends AnvilSmithingRecipe {
	
	public AnvilSmithingRenameRecipe() {
		super(1, new ItemStack(Items.iron_sword), new ComparableStack(Items.iron_sword), new ComparableStack(Items.name_tag, 0));
	}
	
	@Override
	public boolean matches(ItemStack left, ItemStack right) {
		return doesStackMatch(right, this.right) && getDisplayName(right) != null;
	}

	@Override
	public int matchesInt(ItemStack left, ItemStack right) {
		return matches(left, right) ? 0 : -1;
	}
	
	@Override
	public ItemStack getOutput(ItemStack left, ItemStack right) {
		
		ItemStack out = left.copy();
		out.stackSize = 1;
		
		String name = getDisplayName(right);
				
		if(name != null) {
			name = name.replace("\\&", "§");
			out.setStackDisplayName("§r" + name);
		}
		
		return out;
	}
	
	public String getDisplayName(ItemStack stack) {
		String s = null;

		if(stack.stackTagCompound != null && stack.stackTagCompound.hasKey("display", 10)) {
			NBTTagCompound nbttagcompound = stack.stackTagCompound.getCompoundTag("display");

			if(nbttagcompound.hasKey("Name", 8)) {
				s = nbttagcompound.getString("Name");
			}
		}

		return s;
	}
}
