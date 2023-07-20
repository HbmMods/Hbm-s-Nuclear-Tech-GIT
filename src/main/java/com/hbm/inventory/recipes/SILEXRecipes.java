package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
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

		itemTranslation.put(new ComparableStack(ModItems.fluid_icon, 1, Fluids.UF6.getID()), new ComparableStack(ModItems.ingot_uranium));
		dictTranslation.put(U.dust(), U.ingot());
		recipes.put(U.ingot(), new SILEXRecipe(900, 100, EnumWavelengths.UV)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 1))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 11))
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_pu_mix), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 6))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 3))
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_am_mix), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am241), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am242), 6))
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_cm_mix), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm244), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm245), 6))
				);

		itemTranslation.put(new ComparableStack(ModItems.fluid_icon, 1, Fluids.PUF6.getID()), new ComparableStack(ModItems.ingot_plutonium));
		dictTranslation.put(PU.dust(), PU.ingot());
		recipes.put(PU.ingot(), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 2))
				);

		recipes.put(new ComparableStack(ModItems.ingot_schraranium), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 2))
				);

		itemTranslation.put(new ComparableStack(ModItems.powder_australium), new ComparableStack(ModItems.ingot_australium));
		recipes.put(new ComparableStack(ModItems.ingot_australium), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_lesser), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_greater), 1))
				);
		
		recipes.put(new ComparableStack(ModItems.crystal_schraranium), new SILEXRecipe(900, 100, 3)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 2))
				);
		
		recipes.put(new ComparableStack(ModBlocks.ore_tikite), new SILEXRecipe(900, 100, EnumWavelengths.UV)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_plutonium), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cobalt), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_niobium), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_nitan_mix), 2))
				);
		
		recipes.put(new ComparableStack(ModItems.crystal_trixite), new SILEXRecipe(1200, 100, EnumWavelengths.UV)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_plutonium), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cobalt), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_niobium), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_nitan_mix), 1))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_spark_mix), 1))
				);

		itemTranslation.put(new ComparableStack(ModItems.powder_lapis), new ComparableStack(Items.dye, 1, 4));
		recipes.put(new ComparableStack(Items.dye, 1, 4), new SILEXRecipe(100, 100 ,1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.sulfur), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_aluminium), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cobalt), 3))
				);

		recipes.put(new ComparableStack(ModItems.fluid_icon, 1, Fluids.DEATH.getID()), new SILEXRecipe(1000, 1000, 5)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_impure_osmiridium), 1))
				);
		
		for(int i = 0; i < 5; i++) {
			
			// UEU //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ueu, 1, i), new SILEXRecipe(600, 100, 1) 	//NU and MEU will breed more plutonium due to their higher concentrations of U-238
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 86 - i * 11))	//NU is unenriched to the point where it'll always be lower burnup; so more Pu239 for longer
					.addOut(new WeightedRandomObject(i < 2 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 10 + i * 3)) 
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 2 + 5 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ueu, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 86 - i * 11))
					.addOut(new WeightedRandomObject(i < 2 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 10 + i * 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 1 + 5 * i)) );
			
			// MEU //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_meu, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium_fuel), 84 - i * 16))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 4))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 7 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_meu, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium_fuel), 83 - i * 16))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 4))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 7 * i)) );
			
			// HEU233 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu233, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u233), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), 6 + 12 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu233, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u233), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), 6 + 12 * i)) );
			
			// HEU235 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu235, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 12 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu235, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 12 * i)) );
			
			// TH232 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_thmeu, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_thorium_fuel), 84 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u233), 6 + i * 4))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), 10 + 16 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_thmeu, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_thorium_fuel), 83 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u233), 6 + i * 4))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), 10 + 16 * i)) );
			
			// LEP //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lep, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium_fuel), 84 - i * 14))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 2))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_cm_mix) : new ItemStack(ModItems.nugget_cm_mix), 2 / 50 + i * 2))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 7 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 3 + 4 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lep, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium_fuel), 83 - i * 14))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 2))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_cm_mix) : new ItemStack(ModItems.nugget_cm_mix), 2 / 50 + i * 2))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 7 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 3 + 4 * i)) );
			
			// MEP //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mep, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu_mix), 85 - i * 20))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_cm_mix) : new ItemStack(ModItems.nugget_cm_mix), 2 / 50 + i * 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 10 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 5 + 5 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mep, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu_mix), 84 - i * 20))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_cm_mix) : new ItemStack(ModItems.nugget_cm_mix), 2 / 50 + i * 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 10 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 5 + 5 * i)) );
			
			// HEP239 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep239, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 85 - i * 20))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_cm_mix) : new ItemStack(ModItems.nugget_cm_mix), 2 / 50 + i * 4))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 15 + 20 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep239, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 84 - i * 20))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_cm_mix) : new ItemStack(ModItems.nugget_cm_mix), 2 / 50 + i * 4))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 15 + 20 * i)) );
			
			// HEP241 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep241, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 85 - i * 20))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_cm_mix) : new ItemStack(ModItems.nugget_cm_mix), 2 / 50 + i * 4 + 1/2))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), 15 + 20 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep241, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 84 - i * 20))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_cm_mix) : new ItemStack(ModItems.nugget_cm_mix), 2 / 50 + i * 4 + 1/2))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), 15 + 20 * i)) );
			
			// MEN //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_men, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium_fuel), 84 - i * 14))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 2))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 7 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_men, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium_fuel), 83 - i * 14))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 2))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 7 * i)) );
			
			// HEN //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hen, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 12 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hen, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 8 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 12 * i)) );
			
			// MOX //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mox, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_mox_fuel), 84 - i * 20))
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 4))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 2 + 3 * i)) );
			
			//TODO: Readd xenon processing if/when the NEI handler can display more than 6 outputs properly
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mox, 1, i + 5), new SILEXRecipe(600, 100, 1)	//Plutonium processing isn't possible w/o fucking up the NEI handler or removing xenon
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_mox_fuel), 84 - i * 20))		//To prevent people from taking advantage of differing waste types, conform to the latter
					.addOut(new WeightedRandomObject(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 4))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 2 + 3 * i)) );
			
			// LEAUS //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_leaus, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_lesser), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 6 + 12 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pb209), 4 + 8 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_leaus, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_lesser), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 6 + 12 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pb209), 4 + 8 * i)) );
			
			// HEAUS //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heaus, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_greater), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 5 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(Items.gold_nugget), 3 + 6 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pb209), 2 + 4 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heaus, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_greater), 89 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 5 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(Items.gold_nugget), 3 + 6 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pb209), 2 + 4 * i)) );
			
			// LES //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_les, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_les), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 2 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 1 + 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 1 + 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 4 + 8 * i)) );
						
			//TODO: Readd xenon processing if/when the NEI handler can display more than 6 outputs properly
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_les, 1, i + 5), new SILEXRecipe(600, 100, 2)	//I'd rather not fuck up the NEI handler, so six items it is
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_les), 90 - i * 20))			//Just bullshit something about "not enough np237 for extractable amounts of xe135"
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 2 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 1 + 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 1 + 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 4 + 8 * i)) ); 
						
			// MES //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mes, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium_fuel), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 1 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 2 + 4 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 1 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 2 + 4 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 4 + 6 * i)) );
				    
			//TODO: Readd xenon processing if/when the NEI handler can display more than 6 outputs properly
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mes, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium_fuel), 90 - i * 20)) //ditto
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 1 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 2 + 4 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 1 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 2 + 4 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 4 + 6 * i)) );
				    
			// HES //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hes, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_hes), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 1 + 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 1 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 4 + 6 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 2 + 4 * i)) );
				    
			//TODO: Readd xenon processing if/when the NEI handler can display more than 6 outputs properly
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hes, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_hes), 90 - i * 20)) //ditto
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 1 + 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 1 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 4 + 6 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 2 + 4 * i)) );
					
			// BALEFIRE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire, 1, i), new SILEXRecipe(400, 100, 3)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_balefire), 90 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10 + 20 * i)) );
			
			// FLASHGOLD //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire_gold, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 90 - 20 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_balefire), 10 + 20 * i)) );
			
			// FLASHLEAD //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_flashlead, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 44 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pb209), 44 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 1 + 6 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_mercury), 1 + 6 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_gh336), 10 + 8 * i)) ); //Reimumunch
			
			// POBE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_po210be, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_polonium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 5 + 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i)) );
			
			// PUBE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_pu238be, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i))
			        .addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_mix), 2 / 50 * i * 2)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_pu238be, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 44 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i)) );
			
			// RABE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ra226be, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_ra226), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 3 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_polonium), 2 + 5 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i)) );
			
			// DRX //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_drx, 1, i), new SILEXRecipe(600, 100, 5)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_drx, 1, i + 5), new SILEXRecipe(600, 100, 5)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.undefined), 1)) );
			
			// ZFB BI //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_bismuth, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 50 + i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 150)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_bismuth, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 50 + i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 147)) );
			
			// ZFB PU-241 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_pu241, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 50 + i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 150)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_pu241, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 50 + i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 147)) );
			
			// ZFB RG-AM //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_am_mix, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am_mix), 50 + i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 150)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_am_mix, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am_mix), 50 + i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 147)) );
			//LEA//
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lea, 1, i), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_americium_fuel), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bk247), 1 + 10 * i / 90))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM242.ordinal()), 1 + 3 * (i / 2)))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM241.ordinal()), 1 + (2 + 1/2) * (i / 2))));
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lea, 1, i + 5), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_americium_fuel), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bk247), 1 + 10 * i / 90))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM242.ordinal()), 1 + 3 * (i / 2)))
			        .addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM241.ordinal()), 1 + (2 + 1/2) * (i / 2))));
			//MEA//
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mea, 1, i), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am_mix), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bk247), 1 + 10 * i / 70))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM242.ordinal()), 2 + 3 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM241.ordinal()), 2 + 2 * i)));
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mea, 1, i + 5), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am_mix), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bk247), 1 + 10 * i / 70))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM242.ordinal()), 2 + 3 * i))
			        .addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM241.ordinal()), 2 + 2 * i)));
			//HEA242//
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hea242, 1, i), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am242), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bk247), 1 + 10 * i / 50))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM242.ordinal()), 4 + 6 * i)));
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hea242, 1, i + 5), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am242), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bk247), 1 + 10 * i / 50))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM242.ordinal()), 4 + 6 * i)));
			//HEA241//
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hea241, 1, i), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am241), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bk247), 1 + 10 * i / 50))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM241.ordinal()), 4 + 6 * i)));
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hea241, 1, i + 5), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am241), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bk247), 1 + 10 * i / 50))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.AMERICIUM241.ordinal()), 4 + 6 * i)));
			//LEC//
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lecm, 1, i), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_fuel), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu_mix), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 1 + 2 * i / 50))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM244.ordinal()), 2 + 1 * i))
			        .addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM245.ordinal()), 2 + 1 * i)));
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lecm, 1, i + 5), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_fuel), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu_mix), 50 - i * 10))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 1 + 2 * i / 50))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM244.ordinal()), 2 + 1 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM245.ordinal()), 2 + 1 * i)));
			//MEC//
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mecm, 1, i), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_mix), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 2 + 4 * i / 30))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM244.ordinal()), 4 + 4 * i))
			        .addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM245.ordinal()), 5 + 4 * i)));
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mecm, 1, i + 5), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_mix), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 2 + 4 * i / 30))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM244.ordinal()), 4 + 4 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM245.ordinal()), 5 + 4 * i)));
			//HEC//
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hecm, 1, i), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm245), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 3 + 5 * i / 30))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM244.ordinal()), 6 + 7 * i))
			        .addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM245.ordinal()), 7 + 8 * i)));
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hecm, 1, i + 5), new SILEXRecipe(600, 100, 4)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm245), 100 - i * 20))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 3 + 5 * i / 30))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 3))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM244.ordinal()), 6 + 7 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.CURIUM245.ordinal()), 7 + 8 * i)));
			
		}

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 45))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_technetium), 15))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 65))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 15))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 12))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_sr90_tiny), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 12))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 56))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 32))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 22))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 16))
				);

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 45))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_technetium), 15))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 60))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 15))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_sr90_tiny), 12))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 14))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 60))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 12))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 34))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 13))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 29))
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_sr90_tiny), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 6))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 52))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 16))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 39))
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu241), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_neptunium), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_sr90_tiny), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 7))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 66))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 22))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 17))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 36))
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am241), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am242), 35))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_technetium), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 7))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 25))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 60))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 5))
				);

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u233), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 35))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 25))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 35))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10))
				);

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_technetium), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 15))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 16))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 55))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 9))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_sr90_tiny), 7))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 8))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 40))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_zirconium), 7))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 29))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_lead), 45))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 17))
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_solinium), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_euphemium), 18))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_gh336), 16))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_tantalium), 8))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_neodymium_tiny), 8))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 25))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_solinium), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_euphemium), 18))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_gh336), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_tantalium), 8))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_neodymium_tiny), 8))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 31))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pb209), 7)) //We don't have any spicy lanthanides, and lead 209 + gold 198 is already *severely* pushing it, but there's no
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 7)) //point in contributing to pointless item bloat, so this will have to do
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 5)) 
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 76))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 7))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_mercury), 12))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cerium_tiny), 14))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_lanthanium_tiny), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 32))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.AMERICIUM241.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_mix), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_am242), 30))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.AMERICIUM241.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_mix), 50))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm242), 10))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.AMERICIUM242.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_mix), 70))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.AMERICIUM242.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_mix), 50))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm242), 20))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.CURIUM244.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm245), 30))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm246), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cf251), 25))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cf252), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_es253), 10))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.CURIUM244.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_mix), 40)) //from short-lived californium isotope decay
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm246), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cf251), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cf252), 10))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.CURIUM245.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm246), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cf251), 35))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cf252), 30))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_es253), 10))
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.CURIUM245.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm_mix), 10)) //from short-lived californium isotope decay
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm246), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cm247), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nuclear_waste_tiny), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cf252), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_cf251), 25))
				);
		
		recipes.put(new ComparableStack(ModItems.fallout, 1), new SILEXRecipe(900, 100, 2)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust_tiny), 90))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_co60), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_sr90_tiny), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_i131_tiny), 1))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cs137_tiny), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 1))
				);
		
		recipes.put(new ComparableStack(Blocks.gravel, 1), new SILEXRecipe(1000, 1000, EnumWavelengths.VISIBLE)
				.addOut(new WeightedRandomObject(new ItemStack(Items.flint), 80))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_boron), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_lithium), 10))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.fluorite), 5))
				);
		
		//okay so at this point, silex for ore processing should be a lategame incentive, instead of the standard
		recipes.put(new ComparableStack(ModItems.mineral_fragment, 1, 0), new SILEXRecipe(1000, 200, EnumWavelengths.IR) //peroxide
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_nickel), 50))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_boron), 20))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_calcium), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.fluorite), 25))
				);
		recipes.put(new ComparableStack(ModItems.mineral_fragment, 1, 1), new SILEXRecipe(1000, 200, EnumWavelengths.VISIBLE)//nitric
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_hafnium), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_neodymium), 30))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_zirconium), 26))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_gallium_tiny), 4))
				);
		recipes.put(new ComparableStack(ModItems.mineral_fragment, 1, 2), new SILEXRecipe(1000, 200, EnumWavelengths.UV)//chloric and sulfuric
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_arsenic), 30))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_aluminium, 5), 50))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cerium_tiny), 7))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_asbestos), 13))
				);
		recipes.put(new ComparableStack(ModItems.mineral_fragment, 1, 3), new SILEXRecipe(1000, 200, EnumWavelengths.XRAY)//solvent
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_boron), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_tantalium), 40))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_iodine), 15))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cobalt), 5))
				);
		recipes.put(new ComparableStack(ModItems.mineral_fragment, 1, 4), new SILEXRecipe(1000, 200, EnumWavelengths.XRAY)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_desh_mix), 44))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_boron), 21))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_tantalium), 30))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_bismuth), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_caesium), 2))
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
		public EnumWavelengths laserStrength;
		public List<WeightedRandomObject> outputs = new ArrayList();
		
		public SILEXRecipe(int fluidProduced, int fluidConsumed, EnumWavelengths laserStrength) {
			this.fluidProduced = fluidProduced;
			this.fluidConsumed = fluidConsumed;
			this.laserStrength = laserStrength;
		}
		
		public SILEXRecipe(int fluidProduced, int fluidConsumed, int laserStrength) {
			this(fluidProduced, fluidConsumed, EnumWavelengths.values()[laserStrength]);
		}
		
		public SILEXRecipe addOut(WeightedRandomObject entry) {
			outputs.add(entry);
			return this;
		} 
	}
}
