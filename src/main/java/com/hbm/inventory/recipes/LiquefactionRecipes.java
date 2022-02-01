package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*; 

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class LiquefactionRecipes {

	private static HashMap<Object, FluidStack> recipes = new HashMap();
	
	public static void register() {
		
		//oil processing
		recipes.put(COAL.gem(),									new FluidStack(100, Fluids.COALOIL));
		recipes.put(COAL.dust(),								new FluidStack(100, Fluids.COALOIL));
		recipes.put(KEY_OIL_TAR,								new FluidStack(75, Fluids.BITUMEN));
		recipes.put(KEY_CRACK_TAR,								new FluidStack(100, Fluids.BITUMEN));
		recipes.put(KEY_COAL_TAR,								new FluidStack(50, Fluids.BITUMEN));
		//general utility recipes because why not
		recipes.put(new ComparableStack(Blocks.netherrack),		new FluidStack(250, Fluids.LAVA));
		recipes.put(new ComparableStack(Blocks.cobblestone),	new FluidStack(250, Fluids.LAVA));
		recipes.put(new ComparableStack(Blocks.stone),			new FluidStack(250, Fluids.LAVA));
		recipes.put(new ComparableStack(Blocks.obsidian),		new FluidStack(500, Fluids.LAVA));
		recipes.put(new ComparableStack(Items.snowball),		new FluidStack(125, Fluids.WATER));
		recipes.put(new ComparableStack(Blocks.snow),			new FluidStack(500, Fluids.WATER));
		recipes.put(new ComparableStack(Blocks.ice),			new FluidStack(1000, Fluids.WATER));
		recipes.put(new ComparableStack(Blocks.packed_ice),		new FluidStack(1000, Fluids.WATER));
		
		//TODO: more recipes as the crack oil derivatives are added
	}
	
	public static FluidStack getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		
		if(recipes.containsKey(comp))
			return recipes.get(comp);
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {

			if(recipes.containsKey(key))
				return recipes.get(key);
		}
		
		return null;
	}

	public static HashMap<Object, ItemStack> getRecipes() {
		
		HashMap<Object, ItemStack> recipes = new HashMap<Object, ItemStack>();
		
		for(Entry<Object, FluidStack> entry : LiquefactionRecipes.recipes.entrySet()) {
			
			FluidStack out = entry.getValue();
			
			if(entry.getKey() instanceof String) {
				recipes.put(new OreDictStack((String)entry.getKey()), ItemFluidIcon.make(out.type, out.fill));
			} else {
				recipes.put(((ComparableStack)entry.getKey()).toStack(), ItemFluidIcon.make(out.type, out.fill));
			}
		}
		
		return recipes;
	}
}
