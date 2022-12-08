package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CombinationRecipes {

	private static HashMap<Object, Pair<ItemStack, FluidStack>> recipes = new HashMap();
	
	public static void register() {
		recipes.put(COAL.gem(),		new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), new FluidStack(Fluids.COALOIL, 50)));
		recipes.put(COAL.dust(),	new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), new FluidStack(Fluids.COALOIL, 50)));
		
		recipes.put(LIGNITE.gem(),										new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new FluidStack(Fluids.COALOIL, 50)));
		recipes.put(LIGNITE.dust(),										new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new FluidStack(Fluids.COALOIL, 50)));
		recipes.put(new ComparableStack(ModItems.briquette_lignite),	new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new FluidStack(Fluids.COALOIL, 50)));
		
		recipes.put(CINNABAR.crystal(), new Pair(new ItemStack(ModItems.sulfur), new FluidStack(Fluids.MERCURY, 100)));
		
		recipes.put(KEY_LOG, new Pair(new ItemStack(Items.coal, 1 ,1), new FluidStack(Fluids.HEATINGOIL, 10)));

		recipes.put(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE),	new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), null));
		recipes.put(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK),	new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), null));
		recipes.put(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL),	new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), null));
	}
	
	public static Pair<ItemStack, FluidStack> getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		
		if(recipes.containsKey(comp)) {
			Pair<ItemStack, FluidStack> out = recipes.get(comp);
			return new Pair(out.getKey() == null ? null : out.getKey().copy(), out.getValue());
		}
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {

			if(recipes.containsKey(key)) {
				Pair<ItemStack, FluidStack> out = recipes.get(key);
				return new Pair(out.getKey() == null ? null : out.getKey().copy(), out.getValue());
			}
		}
		
		return null;
	}

	public static HashMap getRecipes() {
		
		HashMap<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
		for(Entry<Object, Pair<ItemStack, FluidStack>> entry : CombinationRecipes.recipes.entrySet()) {
			Object key = entry.getKey();
			Pair<ItemStack, FluidStack> val = entry.getValue();
			Object o = key instanceof String ? new OreDictStack((String) key) : key;
			
			if(val.getKey() != null && val.getValue() != null) {
				recipes.put(o, new ItemStack[] {val.getKey(), ItemFluidIcon.make(val.getValue())});
			} else if(val.getKey() != null) {
				recipes.put(o, new ItemStack[] {val.getKey()});
			} else if(val.getValue() != null) {
				recipes.put(o, new ItemStack[] {ItemFluidIcon.make(val.getValue())});
			}
		}
		
		return recipes;
	}
}
