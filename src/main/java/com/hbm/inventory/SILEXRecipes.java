package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.util.WeightedRandomObject;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SILEXRecipes {

	private static HashMap<Object, SILEXRecipe> recipes = new HashMap();
	private static HashMap<ComparableStack, ComparableStack> itemTranslation = new HashMap();
	private static HashMap<String, String> dictTranslation = new HashMap();
	
	public static void register() {

		itemTranslation.put(new ComparableStack(ModItems.fluid_icon, 1, FluidType.UF6.ordinal()), new ComparableStack(ModItems.ingot_uranium));
		dictTranslation.put("dustUranium", "ingotUranium");
		recipes.put("ingotUranium", new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 1))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 8))
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_pu_mix), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 6))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 3))
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_am_mix), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am241), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am242), 6))
				);

		itemTranslation.put(new ComparableStack(ModItems.fluid_icon, 1, FluidType.PUF6.ordinal()), new ComparableStack(ModItems.ingot_plutonium));
		dictTranslation.put("dustPlutonium", "ingotPlutonium");
		recipes.put("ingotPlutonium", new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 2))
				);

		recipes.put(new ComparableStack(ModItems.ingot_schraranium), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 2))
				);

		itemTranslation.put(new ComparableStack(ModItems.powder_australium), new ComparableStack(ModItems.ingot_australium));
		recipes.put(new ComparableStack(ModItems.ingot_australium), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_lesser), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_greater), 1))
				);
		
		recipes.put(new ComparableStack(ModItems.crystal_schraranium), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 2))
				);

		itemTranslation.put(new ComparableStack(ModItems.powder_lapis), new ComparableStack(Items.dye, 1, 4));
		recipes.put(new ComparableStack(Items.dye, 1, 4), new SILEXRecipe(100, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.sulfur), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_aluminium), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cobalt), 3))
				);

		for(int i = 0; i < 5; i++) {
			
			// UEU //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ueu, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 87 - i * 6))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 9 - i * 2))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 2 + 5 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ueu, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 87 - i * 6))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 8 - i * 2))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 2 + 5 * i)) );
			
			// MEU //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_meu, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium_fuel), 90 - i * 12))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 7 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_meu, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium_fuel), 89 - i * 12))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 7 * i)) );
			
			// HEU233 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu233, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u233), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), 6 + 12 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu233, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u233), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), 6 + 12 * i)) );
			
			// MEU //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu235, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 12 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu235, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 12 * i)) );
			
			// TH232 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_thmeu, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_thorium_fuel), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), 10 + 20 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_thmeu, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_thorium_fuel), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), 10 + 20 * i)) );
			
			// LEP //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lep, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium_fuel), 90 - i * 15))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 7 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 3 + 5 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lep, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium_fuel), 89 - i * 15))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 7 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 3 + 5 * i)) );
			
			// MEP //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mep, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium_fuel), 85 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 10 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 5 + 5 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mep, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu_mix), 84 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 10 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 5 + 5 * i)) );
			
			// HEP239 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep239, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 85 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 15 + 20 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep239, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 84 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 15 + 20 * i)) );
			
			// HEP241 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep241, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 85 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), 15 + 20 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep241, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 84 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), 15 + 20 * i)) );
			
			// MEN //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_men, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium_fuel), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 12 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_men, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium_fuel), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 12 * i)) );
			
			// MOX //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mox, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_mox_fuel), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 4 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 3 + 6 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 3 + 7 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 2 + 3 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mox, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_mox_fuel), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 4 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 3 + 6 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 3 + 7 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 2 + 3 * i)) );
			
			// LEAUS //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_leaus, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_lesser), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 10 + 20 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_leaus, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_lesser), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 10 + 20 * i)) );
			
			// HEAUS //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heaus, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_lesser), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 5 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(Items.gold_nugget), 5 + 10 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heaus, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_lesser), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 5 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(Items.gold_nugget), 5 + 10 * i)) );
			
			// BALEFIRE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire, 1, i), new SILEXRecipe(400, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_balefire), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10 + 20 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire, 1, i + 5), new SILEXRecipe(400, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_balefire), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10 + 20 * i)) );
			
			// FLASHGOLD //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire_gold, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 9 - 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_balefire), 1 + 2 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire_gold, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 8 - 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_balefire), 1 + 2 * i)) );
			
			// POBE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_po210be, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_polonium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 5 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_po210be, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_polonium), 44 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 5 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i)) );
			
			// PUBE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_pu238be, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_pu238be, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 44 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i)) );
			
			// RABE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ra226be, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_ra226), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_polonium), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ra226be, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_ra226), 44 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_polonium), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i)) );
			
			// FLASHGOLD //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_drx, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_drx, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1)) );
		}

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 45))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 15))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 65))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 20))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_technetium), 30))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_ra226), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 40))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 45))
				);

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 45))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 15))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 65))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 20))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 30))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_ra226), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 40))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 45))
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_ra226), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 40))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 35))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 45))
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_ra226), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 40))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 35))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 45))
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am241), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am242), 35))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 30))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 55))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 25))
				);

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u233), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 35))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 25))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 75))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10))
				);

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 40))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 55))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 45))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_polonium), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 40))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 35))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 45))
				);
		
		recipes.put(new ComparableStack(ModItems.fallout, 1), new SILEXRecipe(100, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust), 90))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_co60), 6))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 1))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_ra226), 3))
				);
		
		recipes.put(new ComparableStack(Blocks.gravel, 1), new SILEXRecipe(1000, 250)
				.addOut(new WeightedRandomObject(new ItemStack(Items.flint), 80))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_boron), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_lithium), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.fluorite), 5))
				);
	}
	
	public static SILEXRecipe getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = translateItem(stack);
		
		if(recipes.containsKey(comp))
			return recipes.get(comp);
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			
			String translation = translateDict(key);
			if(recipes.containsKey(translation))
				return recipes.get(translation);
		}
		
		return null;
	}
	
	public static ComparableStack translateItem(ItemStack stack) {
		ComparableStack orig = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		ComparableStack translation = itemTranslation.get(orig);
		
		if(translation != null)
			return translation;
		
		return orig;
	}
	
	public static String translateDict(String key) {
		
		String translation = dictTranslation.get(key);
		
		if(translation != null)
			return translation;
		
		return key;
	}
	
	public static List<Object> getAllIngredients() {
		List<Object> ing = new ArrayList();
		
		for(Entry<Object, SILEXRecipe> entry : SILEXRecipes.recipes.entrySet()) {
			ing.add(entry.getKey());
		}
		for(Entry<ComparableStack, ComparableStack> entry : SILEXRecipes.itemTranslation.entrySet()) {
			ing.add(entry.getKey());
		}
		for(Entry<String, String> entry : SILEXRecipes.dictTranslation.entrySet()) {
			ing.add(entry.getKey());
		}
		
		return ing;
	}

	public static Map<Object, SILEXRecipe> getRecipes() {
		
		Map<Object, SILEXRecipe> recipes = new HashMap<Object, SILEXRecipe>();
		List<Object> ing = getAllIngredients();
		
		for(Object ingredient : ing) {
			
			if(ingredient instanceof String) {
				List<ItemStack> ingredients = OreDictionary.getOres((String)ingredient);
				if(ingredients.size() > 0) {
					SILEXRecipe output = getOutput(ingredients.get(0));
					if(output != null)
						recipes.put(ingredients, output);
				}
				
			} else if(ingredient instanceof ComparableStack) {
				SILEXRecipe output = getOutput(((ComparableStack) ingredient).toStack());
				if(output != null)
					recipes.put(((ComparableStack)ingredient).toStack(), output);
			}
		}
		
		return recipes;
	}
	
	public static class SILEXRecipe {
		
		public int fluidProduced;
		public int fluidConsumed;
		public List<WeightedRandomObject> outputs = new ArrayList();
		
		public SILEXRecipe(int fluidProduced, int fluidConsumed) {
			this.fluidProduced = fluidProduced;
			this.fluidConsumed = fluidConsumed;
		}
		
		public SILEXRecipe addOut(WeightedRandomObject entry) {
			outputs.add(entry);
			return this;
		}
	}
}
