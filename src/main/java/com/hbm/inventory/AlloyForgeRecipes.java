package com.hbm.inventory;

import java.util.ArrayList;

import com.google.common.annotations.Beta;
import com.hbm.inventory.OreDictManager.MaterialStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
@Beta
public class AlloyForgeRecipes
{
	public static final ArrayList<AlloyForgeRecipe> recipes = new ArrayList<AlloyForgeRecipes.AlloyForgeRecipe>();
	
	public static void register()
	{
		makeRecipe(ModItems.ingot_steel, "Iron", "Coal");
		makeRecipe(ModItems.ingot_red_copper, "Copper", "Redstone");
		makeRecipe(ModItems.ingot_advanced_alloy, "Steel", "RedCopperAlloy");
		makeRecipe(ModItems.ingot_advanced_alloy, 3, "Steel", "Copper", "Redstone");
		makeRecipe(ModItems.ingot_advanced_alloy, 3, "Iron", "Coal", "RedCopperAlloy");
		makeRecipe(ModItems.ingot_advanced_alloy, 4, "Iron", "Coal", "Copper", "Redstone");
		makeRecipe(ModItems.neutron_reflector, "Tungsten", "Coal");
		recipes.add(new AlloyForgeRecipe(new ComparableStack(ModItems.ingot_magnetized_tungsten), new MaterialStack("Tungsten", 9), new MaterialStack("Schrabidium", 1)));
		makeRecipe(ModItems.plate_paa, "Mixed", "Gold");
		makeRecipe(ModItems.ingot_dura_steel, "Steel", "Tungsten");
		makeRecipe(ModItems.ingot_dura_steel, "Steel", "Cobalt");
		recipes.add(new AlloyForgeRecipe(new ComparableStack(ModItems.plate_paa, 2), new MaterialStack(new ComparableStack(ModItems.plate_mixed)), new MaterialStack("Gold", 9)));
		recipes.add(new AlloyForgeRecipe(new ComparableStack(ModItems.plate_mixed, 6), new MaterialStack("AdvancedAlloy", 18), new MaterialStack("DenseLead", 18), new MaterialStack("CMBSteel", 9), new MaterialStack("Lead", 36)));
		recipes.add(new AlloyForgeRecipe(new ComparableStack(ModItems.ingot_niobium_alloy, 12), new MaterialStack("Niobium", 45), new MaterialStack("Beryllium", 18), new MaterialStack("Steel", 18), new MaterialStack("Titanium", 9), new MaterialStack("Tungsten", 9), new MaterialStack("Zirconium", 9)));
		recipes.add(new AlloyForgeRecipe(new ComparableStack(ModItems.ingot_starmetal, 2), new MaterialStack("Saturnite"), new MaterialStack(new ComparableStack(ModItems.powder_meteorite))));
		makeRecipe(ModItems.ingot_ferrouranium, "UraniumDioxide", "Steel");
		recipes.add(new AlloyForgeRecipe(new ComparableStack(ModItems.ingot_ferrouranium, 2), new MaterialStack("UraniumDioxide", 18), new MaterialStack("Iron"), new MaterialStack("Coal")));
		recipes.add(new AlloyForgeRecipe(new ComparableStack(ModItems.ingot_staballoy, 6), new MaterialStack("UraniumDioxide", 45), new MaterialStack("Titanium")));
		makeRecipe(ModItems.ingot_nbbe, "Niobium", "Beryllium");
		if (OreDictionary.doesOreNameExist("ingotElectrum") && OreDictionary.doesOreNameExist("ingotSilver"))
			makeRecipe(OreDictionary.getOres("ingotElectrum").get(0), 2, "Gold", "Silver");
		if (OreDictionary.doesOreNameExist("ingotBronze") && OreDictionary.doesOreNameExist("ingotTin"))
			makeRecipe(OreDictionary.getOres("ingotBronze").get(0), 4, "Copper", "Copper", "Copper", "Tin");
		if (OreDictionary.doesOreNameExist("ingotBrass") && OreDictionary.doesOreNameExist("ingotZinc"))
			makeRecipe(OreDictionary.getOres("ingotBrass").get(0), 4, "Copper", "Copper", "Copper", "Zinc");
		if (OreDictionary.doesOreNameExist("ingotInvar") && OreDictionary.doesOreNameExist("ingotNickel"))
			makeRecipe(OreDictionary.getOres("ingotInvar").get(0), 3, "Iron", "Iron", "Nickel");
	}
	
	public static void makeRecipe(Item out, String...names)
	{
		makeRecipe(out, 2, names);
	}
	
	public static void makeRecipe(Item out, int count, String...names)
	{
		makeRecipe(new ItemStack(out), count, names);
	}
	public static void makeRecipe(ItemStack out, int count, String...names)
	{
		MaterialStack[] stacks = new MaterialStack[names.length];
		out.stackSize = count;
		assert names.length <= 6;
		for (int i = 0; i < names.length; i++)
			stacks[i] = new MaterialStack(names[i], 9);
		recipes.add(new AlloyForgeRecipe(new ComparableStack(out), stacks));
	}
	public static int getSizeFromOre(String in)
	{
		for (String entry : OreDictManager.sizeMap.keySet())
			if (in.startsWith(entry))
				return OreDictManager.sizeMap.get(entry);
		
		return 0;
	}
	
	public static class AlloyForgeRecipe
	{
		private MaterialStack[] stacks;
		private ComparableStack stackOut;
		public AlloyForgeRecipe(ComparableStack output, MaterialStack...materialStacks)
		{
			assert materialStacks.length <= 6;
			stacks = materialStacks;
			stackOut = output;
		}

	}

	@Deprecated
	public static class MoltenStack
	{
		public static final MoltenStack EMPTY = new MoltenStack("EMPTY", 0);
		public static final byte nugget = 1;
		/** Or equivalent **/
		public static final byte ingot = 9;
		public static final byte block = 81;
		private int size;
		public final String name;
		public MoltenStack(String nameIn, int sizeIn)
		{
			name = nameIn;
			size = sizeIn;
		}
		public void setSize(int sizeIn)
		{
			size = sizeIn;
		}
		public void incrementSize(int i)
		{
			size += i;
		}
		public void decrementSize(int i)
		{
			size -= i;
			if (size < 0)
				size = 0;
		}
		public int getIngotCount()
		{
			return Math.floorDiv(size, 9);
		}
		public int getNuggetCount()
		{
			return size;
		}
		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof MoltenStack))
				return false;
			MoltenStack test = (MoltenStack) obj;
			return test.name.equals(this.name) && test.size == this.size;
		}
		@Override
		public MoltenStack clone()
		{
			return new MoltenStack(name, size);
		}
		public boolean isStackSufficient(MoltenStack s)
		{
			return equals(s) || (s.name.equals(name) && s.size <= size);
		}
	}

}
