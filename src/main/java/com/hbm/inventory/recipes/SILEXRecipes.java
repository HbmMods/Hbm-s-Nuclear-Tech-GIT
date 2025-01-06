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
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.util.WeightedRandomObject;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class SILEXRecipes {

	private static HashMap<Object, SILEXRecipe> recipes = new HashMap();
	private static HashMap<ComparableStack, ComparableStack> itemTranslation = new HashMap();
	private static HashMap<String, String> dictTranslation = new HashMap();
	
	public static void register() {

		itemTranslation.put(new ComparableStack(ModItems.fluid_icon, 1, Fluids.UF6.getID()), new ComparableStack(ModItems.ingot_uranium));
		dictTranslation.put(U.dust(), U.ingot());
		recipes.put(U.ingot(), new SILEXRecipe(900, 100, EnumWavelengths.VISIBLE)
				.addOut(new ItemStack(ModItems.nugget_u235), 1)
				.addOut(new ItemStack(ModItems.nugget_u238), 11)
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_pu_mix), new SILEXRecipe(900, 100, 2)
				.addOut(new ItemStack(ModItems.nugget_pu239), 6)
				.addOut(new ItemStack(ModItems.nugget_pu240), 3)
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_am_mix), new SILEXRecipe(900, 100, 2)
				.addOut(new ItemStack(ModItems.nugget_am241), 3)
				.addOut(new ItemStack(ModItems.nugget_am242), 6)
				);

		itemTranslation.put(new ComparableStack(ModItems.fluid_icon, 1, Fluids.PUF6.getID()), new ComparableStack(ModItems.ingot_plutonium));
		dictTranslation.put(PU.dust(), PU.ingot());
		recipes.put(PU.ingot(), new SILEXRecipe(900, 100, 2)
				.addOut(new ItemStack(ModItems.nugget_pu238), 3)
				.addOut(new ItemStack(ModItems.nugget_pu239), 4)
				.addOut(new ItemStack(ModItems.nugget_pu240), 2)
				);

		recipes.put(new ComparableStack(ModItems.ingot_schraranium), new SILEXRecipe(900, 100, 2)
				.addOut(new ItemStack(ModItems.nugget_schrabidium), 4)
				.addOut(new ItemStack(ModItems.nugget_uranium), 3)
				.addOut(new ItemStack(ModItems.nugget_neptunium), 2)
				);

		itemTranslation.put(new ComparableStack(ModItems.powder_australium), new ComparableStack(ModItems.ingot_australium));
		recipes.put(new ComparableStack(ModItems.ingot_australium), new SILEXRecipe(900, 100, 2)
				.addOut(new ItemStack(ModItems.nugget_australium_lesser), 5)
				.addOut(new ItemStack(ModItems.nugget_australium_greater), 1)
				);
		
		recipes.put(new ComparableStack(ModItems.crystal_schraranium), new SILEXRecipe(900, 100, 3)
				.addOut(new ItemStack(ModItems.nugget_schrabidium), 5)
				.addOut(new ItemStack(ModItems.nugget_uranium), 2)
				.addOut(new ItemStack(ModItems.nugget_neptunium), 2)
				);
		
		recipes.put(new ComparableStack(ModBlocks.ore_tikite), new SILEXRecipe(900, 100, EnumWavelengths.UV)
				.addOut(new ItemStack(ModItems.powder_plutonium), 2)
				.addOut(new ItemStack(ModItems.powder_cobalt), 3)
				.addOut(new ItemStack(ModItems.powder_niobium), 3)
				.addOut(new ItemStack(ModItems.powder_nitan_mix), 2)
				);
		
		recipes.put(new ComparableStack(ModItems.crystal_trixite), new SILEXRecipe(1200, 100, EnumWavelengths.UV)
				.addOut(new ItemStack(ModItems.powder_plutonium), 2)
				.addOut(new ItemStack(ModItems.powder_cobalt), 3)
				.addOut(new ItemStack(ModItems.powder_niobium), 3)
				.addOut(new ItemStack(ModItems.powder_nitan_mix), 1)
				.addOut(new ItemStack(ModItems.powder_spark_mix), 1)
				);

		itemTranslation.put(new ComparableStack(ModItems.powder_lapis), new ComparableStack(Items.dye, 1, 4));
		recipes.put(new ComparableStack(Items.dye, 1, 4), new SILEXRecipe(100, 100 ,1)
				.addOut(new ItemStack(ModItems.sulfur), 4)
				.addOut(new ItemStack(ModItems.powder_aluminium), 3)
				.addOut(new ItemStack(ModItems.powder_cobalt), 3)
				);

		recipes.put(new ComparableStack(ModItems.fluid_icon, 1, Fluids.DEATH.getID()), new SILEXRecipe(1000, 1000, 4)
				.addOut(new ItemStack(ModItems.powder_impure_osmiridium), 1)
				);

		recipes.put(new ComparableStack(ModItems.fluid_icon, 1, Fluids.VITRIOL.getID()), new SILEXRecipe(1000, 1000, EnumWavelengths.IR)
				.addOut(new ItemStack(ModItems.powder_bromine), 5)
				.addOut(new ItemStack(ModItems.powder_iodine), 5)
				.addOut(new ItemStack(ModItems.powder_iron), 5)
				.addOut(new ItemStack(ModItems.sulfur), 15)
				);
		
		for(int i = 0; i < 5; i++) {
			
			// UEU //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ueu, 1, i), new SILEXRecipe(600, 100, 1) 	//NU and MEU will breed more plutonium due to their higher concentrations of U-238
					.addOut(new ItemStack(ModItems.nugget_uranium), 86 - i * 11)	//NU is unenriched to the point where it'll always be lower burnup; so more Pu239 for longer
					.addOut(i < 2 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 10 + i * 3)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 2 + 5 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ueu, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_uranium), 86 - i * 11)
					.addOut(i < 2 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 10 + i * 3)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 1 + 5 * i) );
			
			// MEU //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_meu, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_uranium_fuel), 84 - i * 16)
					.addOut(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 4)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 7 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_meu, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_uranium_fuel), 83 - i * 16)
					.addOut(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 4)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 7 * i) );
			
			// HEU233 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu233, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_u233), 90 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), 4 + 8 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), 6 + 12 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu233, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_u233), 89 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), 4 + 8 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), 6 + 12 * i) );
			
			// HEU235 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu235, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_u235), 90 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 8 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 12 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heu235, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_u235), 89 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 4 + 8 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 6 + 12 * i) );
			
			// TH232 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_thmeu, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_thorium_fuel), 84 - i * 20)
					.addOut(new ItemStack(ModItems.nugget_u233), 6 + i * 4)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), 10 + 16 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_thmeu, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_thorium_fuel), 83 - i * 20)
					.addOut(new ItemStack(ModItems.nugget_u233), 6 + i * 4)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), 10 + 16 * i) );
			
			// LEP //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lep, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_plutonium_fuel), 84 - i * 14)
					.addOut(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 2)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 7 + 8 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 3 + 4 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_lep, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_plutonium_fuel), 83 - i * 14)
					.addOut(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 2)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 7 + 8 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 3 + 4 * i) );
			
			// MEP //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mep, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_pu_mix), 85 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 10 + 10 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 5 + 5 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mep, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_pu_mix), 84 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 10 + 10 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 5 + 5 * i) );
			
			// HEP239 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep239, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_pu239), 85 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 15 + 20 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep239, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_pu239), 84 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 15 + 20 * i) );
			
			// HEP241 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep241, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_pu241), 85 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), 15 + 20 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hep241, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_pu241), 84 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), 15 + 20 * i) );
			
			// MEN //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_men, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_neptunium_fuel), 84 - i * 14)
					.addOut(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 2)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 7 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_men, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_neptunium_fuel), 83 - i * 14)
					.addOut(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 2)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 7 * i) );
			
			// HEN //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hen, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_neptunium), 90 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 8 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 12 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hen, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_neptunium), 89 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 4 + 8 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 6 + 12 * i) );
			
			// MOX //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mox, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_mox_fuel), 84 - i * 20)
					.addOut(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 4)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 3 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 3 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 2 + 3 * i) );
			
			//TODO: Readd xenon processing if/when the NEI handler can display more than 6 outputs properly
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mox, 1, i + 5), new SILEXRecipe(600, 100, 1)	//Plutonium processing isn't possible w/o fucking up the NEI handler or removing xenon
					.addOut(new ItemStack(ModItems.nugget_mox_fuel), 84 - i * 20)		//To prevent people from taking advantage of differing waste types, conform to the latter
					.addOut(i < 1 ? new ItemStack(ModItems.nugget_pu239) : new ItemStack(ModItems.nugget_pu_mix), 6 + i * 4)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), 2 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), 3 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), 3 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), 2 + 3 * i) );
			
			// LEAUS //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_leaus, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_australium_lesser), 90 - i * 20)
					.addOut(new ItemStack(ModItems.nugget_lead), 6 + 12 * i)
					.addOut(new ItemStack(ModItems.nugget_pb209), 4 + 8 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_leaus, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_australium_lesser), 89 - i * 20)
					.addOut(new ItemStack(ModItems.nugget_lead), 6 + 12 * i)
					.addOut(new ItemStack(ModItems.nugget_pb209), 4 + 8 * i) );
			
			// HEAUS //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heaus, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_australium_greater), 90 - i * 20)
					.addOut(new ItemStack(ModItems.nugget_au198), 5 + 10 * i)
					.addOut(new ItemStack(Items.gold_nugget), 3 + 6 * i)
					.addOut(new ItemStack(ModItems.nugget_pb209), 2 + 4 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_heaus, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_australium_greater), 89 - i * 20)
					.addOut(new ItemStack(ModItems.nugget_au198), 5 + 10 * i)
					.addOut(new ItemStack(Items.gold_nugget), 3 + 6 * i)
					.addOut(new ItemStack(ModItems.nugget_pb209), 2 + 4 * i) );
			
			// LES //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_les, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_les), 90 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 2 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 2 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 1 + 2 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 1 + 2 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 4 + 8 * i) );
						
			//TODO: Readd xenon processing if/when the NEI handler can display more than 6 outputs properly
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_les, 1, i + 5), new SILEXRecipe(600, 100, 2)	//I'd rather not fuck up the NEI handler, so six items it is
					.addOut(new ItemStack(ModItems.nugget_les), 90 - i * 20)			//Just bullshit something about "not enough np237 for extractable amounts of xe135"
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 2 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 2 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 1 + 2 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 1 + 2 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 4 + 8 * i) ); 
						
			// MES //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mes, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_schrabidium_fuel), 90 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 1 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 2 + 4 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 1 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 2 + 4 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 4 + 6 * i) );
				    
			//TODO: Readd xenon processing if/when the NEI handler can display more than 6 outputs properly
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_mes, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_schrabidium_fuel), 90 - i * 20) //ditto
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 1 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 2 + 4 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 1 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 2 + 4 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 4 + 6 * i) );
				    
			// HES //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hes, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_hes), 90 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 1 + 2 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 1 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 2 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 4 + 6 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 2 + 4 * i) );
				    
			//TODO: Readd xenon processing if/when the NEI handler can display more than 6 outputs properly
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_hes, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_hes), 90 - i * 20) //ditto
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), 1 + 2 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), 1 + 3 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), 2 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), 4 + 6 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 2 + 4 * i) );
					
			// BALEFIRE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire, 1, i), new SILEXRecipe(400, 100, 3)
					.addOut(new ItemStack(ModItems.powder_balefire), 90 - i * 20)
					.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 10 + 20 * i) );
			
			// FLASHGOLD //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire_gold, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_au198), 90 - 20 * i)
					.addOut(new ItemStack(ModItems.powder_balefire), 10 + 20 * i) );
			
			// FLASHLEAD //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_flashlead, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_au198), 44 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_pb209), 44 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_bismuth), 1 + 6 * i)
					.addOut(new ItemStack(ModItems.nugget_mercury), 1 + 6 * i)
					.addOut(new ItemStack(ModItems.nugget_gh336), 10 + 8 * i) ); //Reimumunch
			
			// POBE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_po210be, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_polonium), 45 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_lead), 5 + 10 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i) );
			
			// PUBE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_pu238be, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_pu238), 45 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_lead), 3 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 2 + 5 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_pu238be, 1, i + 5), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 1)
					.addOut(new ItemStack(ModItems.nugget_pu238), 44 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_lead), 3 + 5 * i)
					.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 2 + 5 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i) );
			
			// RABE //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ra226be, 1, i), new SILEXRecipe(600, 100, 1)
					.addOut(new ItemStack(ModItems.nugget_ra226), 45 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_beryllium), 45 - 10 * i)
					.addOut(new ItemStack(ModItems.nugget_lead), 3 + 5 * i)
					.addOut(new ItemStack(ModItems.nugget_polonium), 2 + 5 * i)
					.addOut(new ItemStack(ModItems.powder_coal_tiny), 5 + 10 * i) );
			
			// DRX //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_drx, 1, i), new SILEXRecipe(600, 100, 4)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_drx, 1, i + 5), new SILEXRecipe(600, 100, 4)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1)
					.addOut(new ItemStack(ModItems.undefined), 1) );
			
			// ZFB BI //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_bismuth, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_uranium), 50 - i * 10)
					.addOut(new ItemStack(ModItems.nugget_pu241), 50 - i * 10)
					.addOut(new ItemStack(ModItems.nugget_bismuth), 50 + i * 20)
					.addOut(new ItemStack(ModItems.nugget_zirconium), 150) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_bismuth, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 3)
					.addOut(new ItemStack(ModItems.nugget_uranium), 50 - i * 10)
					.addOut(new ItemStack(ModItems.nugget_pu241), 50 - i * 10)
					.addOut(new ItemStack(ModItems.nugget_bismuth), 50 + i * 20)
					.addOut(new ItemStack(ModItems.nugget_zirconium), 147) );
			
			// ZFB PU-241 //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_pu241, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_u235), 50 - i * 10)
					.addOut(new ItemStack(ModItems.nugget_pu240), 50 - i * 10)
					.addOut(new ItemStack(ModItems.nugget_pu241), 50 + i * 20)
					.addOut(new ItemStack(ModItems.nugget_zirconium), 150) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_pu241, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 3)
					.addOut(new ItemStack(ModItems.nugget_u235), 50 - i * 10)
					.addOut(new ItemStack(ModItems.nugget_pu240), 50 - i * 10)
					.addOut(new ItemStack(ModItems.nugget_pu241), 50 + i * 20)
					.addOut(new ItemStack(ModItems.nugget_zirconium), 147) );
			
			// ZFB RG-AM //
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_am_mix, 1, i), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.nugget_pu241), 100 - i * 20)
					.addOut(new ItemStack(ModItems.nugget_am_mix), 50 + i * 20)
					.addOut(new ItemStack(ModItems.nugget_zirconium), 150) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_zfb_am_mix, 1, i + 5), new SILEXRecipe(600, 100, 2)
					.addOut(new ItemStack(ModItems.powder_xe135_tiny), 3)
					.addOut(new ItemStack(ModItems.nugget_pu241), 100 - i * 20)
					.addOut(new ItemStack(ModItems.nugget_am_mix), 50 + i * 20)
					.addOut(new ItemStack(ModItems.nugget_zirconium), 147) );
		}

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_neptunium), 20)
				.addOut(new ItemStack(ModItems.nugget_pu239), 45)
				.addOut(new ItemStack(ModItems.nugget_pu240), 20)
				.addOut(new ItemStack(ModItems.nugget_technetium), 15)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_lead), 65)
				.addOut(new ItemStack(ModItems.nugget_bismuth), 20)
				.addOut(new ItemStack(ModItems.dust_tiny), 15)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_pu238), 12)
				.addOut(new ItemStack(ModItems.powder_sr90_tiny), 10)
				.addOut(new ItemStack(ModItems.powder_i131_tiny), 10)
				.addOut(new ItemStack(ModItems.powder_cs137_tiny), 12)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 56)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.URANIUM235.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_zirconium), 10)
				.addOut(new ItemStack(ModItems.dust_tiny), 32)
				.addOut(new ItemStack(ModItems.nugget_lead), 22)
				.addOut(new ItemStack(ModItems.nugget_u238), 5)
				.addOut(new ItemStack(ModItems.nugget_bismuth), 15)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 16)
				);

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_u235), 15)
				.addOut(new ItemStack(ModItems.nugget_neptunium), 25)
				.addOut(new ItemStack(ModItems.nugget_pu239), 45)
				.addOut(new ItemStack(ModItems.nugget_technetium), 15)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_lead), 60)
				.addOut(new ItemStack(ModItems.nugget_bismuth), 25)
				.addOut(new ItemStack(ModItems.dust_tiny), 15)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_pu238), 4)
				.addOut(new ItemStack(ModItems.powder_sr90_tiny), 12)
				.addOut(new ItemStack(ModItems.powder_i131_tiny), 10)
				.addOut(new ItemStack(ModItems.powder_cs137_tiny), 14)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 60)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.URANIUM233.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_zirconium), 12)
				.addOut(new ItemStack(ModItems.dust_tiny), 34)
				.addOut(new ItemStack(ModItems.nugget_lead), 13)
				.addOut(new ItemStack(ModItems.nugget_u238), 2)
				.addOut(new ItemStack(ModItems.nugget_bismuth), 10)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 29)
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_pu240), 10)
				.addOut(new ItemStack(ModItems.nugget_pu241), 25)
				.addOut(new ItemStack(ModItems.powder_sr90_tiny), 2)
				.addOut(new ItemStack(ModItems.powder_i131_tiny), 5)
				.addOut(new ItemStack(ModItems.powder_cs137_tiny), 6)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 52)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.PLUTONIUM239.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_zirconium), 2)
				.addOut(new ItemStack(ModItems.dust_tiny), 16)
				.addOut(new ItemStack(ModItems.nugget_lead), 40)
				.addOut(new ItemStack(ModItems.nugget_u238), 3)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 39)
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_pu241), 15)
				.addOut(new ItemStack(ModItems.nugget_neptunium), 5)
				.addOut(new ItemStack(ModItems.powder_sr90_tiny), 2)
				.addOut(new ItemStack(ModItems.powder_i131_tiny), 5)
				.addOut(new ItemStack(ModItems.powder_cs137_tiny), 7)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 66)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.PLUTONIUM240.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_zirconium), 2)
				.addOut(new ItemStack(ModItems.dust_tiny), 22)
				.addOut(new ItemStack(ModItems.nugget_bismuth), 20)
				.addOut(new ItemStack(ModItems.nugget_lead), 17)
				.addOut(new ItemStack(ModItems.nugget_u238), 3)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 36)
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new ItemStack(ModItems.nugget_am241), 25)
				.addOut(new ItemStack(ModItems.nugget_am242), 35)
				.addOut(new ItemStack(ModItems.nugget_technetium), 5)
				.addOut(new ItemStack(ModItems.powder_i131_tiny), 3)
				.addOut(new ItemStack(ModItems.powder_cs137_tiny), 7)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 25)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.PLUTONIUM241.ordinal()), new SILEXRecipe(900, 100, 2)
				.addOut(new ItemStack(ModItems.nugget_bismuth), 60)
				.addOut(new ItemStack(ModItems.dust_tiny), 20)
				.addOut(new ItemStack(ModItems.nugget_lead), 15)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 5)
				);

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_u233), 40)
				.addOut(new ItemStack(ModItems.nugget_u235), 35)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 25)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.THORIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_lead), 35)
				.addOut(new ItemStack(ModItems.nugget_bismuth), 40)
				.addOut(new ItemStack(ModItems.dust_tiny), 15)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 10)
				);

		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_u238), 15)
				.addOut(new ItemStack(ModItems.nugget_pu239), 40)
				.addOut(new ItemStack(ModItems.nugget_pu240), 15)
				.addOut(new ItemStack(ModItems.nugget_technetium), 15)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 15)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_u238), 16)
				.addOut(new ItemStack(ModItems.nugget_lead), 55)
				.addOut(new ItemStack(ModItems.dust_tiny), 20)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 9)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_pu238), 40)
				.addOut(new ItemStack(ModItems.powder_sr90_tiny), 7)
				.addOut(new ItemStack(ModItems.powder_i131_tiny), 5)
				.addOut(new ItemStack(ModItems.powder_cs137_tiny), 8)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 40)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.NEPTUNIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_zirconium), 7)
				.addOut(new ItemStack(ModItems.dust_tiny), 29)
				.addOut(new ItemStack(ModItems.nugget_u238), 2)
				.addOut(new ItemStack(ModItems.nugget_lead), 45)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 17)
				);
		
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_solinium), 25)
				.addOut(new ItemStack(ModItems.nugget_euphemium), 18)
				.addOut(new ItemStack(ModItems.nugget_gh336), 16)
				.addOut(new ItemStack(ModItems.nugget_tantalium), 8)
				.addOut(new ItemStack(ModItems.powder_neodymium_tiny), 8)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 25)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.SCHRABIDIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_solinium), 20)
				.addOut(new ItemStack(ModItems.nugget_euphemium), 18)
				.addOut(new ItemStack(ModItems.nugget_gh336), 15)
				.addOut(new ItemStack(ModItems.nugget_tantalium), 8)
				.addOut(new ItemStack(ModItems.powder_neodymium_tiny), 8)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 31)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_pb209), 7) //We don't have any spicy lanthanides, and lead 209 + gold 198 is already *severely* pushing it, but there's no
				.addOut(new ItemStack(ModItems.nugget_au198), 7) //point in contributing to pointless item bloat, so this will have to do
				.addOut(new ItemStack(ModItems.powder_cs137_tiny), 5)
				.addOut(new ItemStack(ModItems.powder_i131_tiny), 5)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 76)
				);
		recipes.put(new ComparableStack(ModItems.nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.SCHRABIDIUM.ordinal()), new SILEXRecipe(900, 100, 1)
				.addOut(new ItemStack(ModItems.nugget_bismuth), 7)
				.addOut(new ItemStack(ModItems.nugget_mercury), 12)
				.addOut(new ItemStack(ModItems.powder_cerium_tiny), 14)
				.addOut(new ItemStack(ModItems.powder_lanthanium_tiny), 15)
				.addOut(new ItemStack(ModItems.dust_tiny), 20)
				.addOut(new ItemStack(ModItems.nuclear_waste_tiny), 32)
				);
		
		recipes.put(new ComparableStack(ModItems.fallout, 1), new SILEXRecipe(900, 100, 2)
				.addOut(new ItemStack(ModItems.dust_tiny), 90)
				.addOut(new ItemStack(ModItems.nugget_co60), 2)
				.addOut(new ItemStack(ModItems.powder_sr90_tiny), 3)
				.addOut(new ItemStack(ModItems.powder_i131_tiny), 1)
				.addOut(new ItemStack(ModItems.powder_cs137_tiny), 3)
				.addOut(new ItemStack(ModItems.nugget_au198), 1)
				);
		
		recipes.put(new ComparableStack(Blocks.gravel, 1), new SILEXRecipe(1000, 250, EnumWavelengths.VISIBLE)
				.addOut(new ItemStack(Items.flint), 80)
				.addOut(new ItemStack(ModItems.powder_boron), 5)
				.addOut(new ItemStack(ModItems.powder_lithium), 10)
				.addOut(new ItemStack(ModItems.fluorite), 5)
				);
		
		recipes.put(new ComparableStack(ModItems.fluid_icon, 1, Fluids.FULLERENE.getID()),
				new SILEXRecipe(1_000, 1_000, EnumWavelengths.VISIBLE).addOut(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.FULLERENE), 1));
	}

	private static final HashMap<Item, Item> tinyWasteTranslation = new HashMap();

	static {
		tinyWasteTranslation.put(ModItems.nuclear_waste_short_tiny, ModItems.nuclear_waste_short);
		tinyWasteTranslation.put(ModItems.nuclear_waste_long_tiny, ModItems.nuclear_waste_long);
		tinyWasteTranslation.put(ModItems.nuclear_waste_short_depleted_tiny, ModItems.nuclear_waste_short_depleted);
		tinyWasteTranslation.put(ModItems.nuclear_waste_long_depleted_tiny, ModItems.nuclear_waste_long_depleted);
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

		if(tinyWasteTranslation.containsKey(comp.item)) {
			SILEXRecipe result = getOutput(new ItemStack(tinyWasteTranslation.get(comp.item), comp.stacksize, comp.meta));

			if(result != null) {
				// This way it rounds down if somehow the recipe's fluid produced is not divisible by 900
				int fluidProduced = (result.fluidProduced / 900) * 100;
				SILEXRecipe tinyVersion = new SILEXRecipe(fluidProduced, result.fluidConsumed, result.laserStrength);
				// Shared ownership shouldn't be an issue since the resulting recipe isn't modified by the caller
				tinyVersion.outputs = result.outputs;
				
				// TODO: Cache? Might break saving recipes, IDK
				// recipes.put(comp, tinyVersion);

				return tinyVersion;
			}
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
		
		for(Entry<Object, SILEXRecipe> entry : SILEXRecipes.recipes.entrySet())							ing.add(entry.getKey());
		for(Entry<ComparableStack, ComparableStack> entry : SILEXRecipes.itemTranslation.entrySet())	ing.add(entry.getKey());
		for(Entry<String, String> entry : SILEXRecipes.dictTranslation.entrySet())						ing.add(entry.getKey());
		
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
		
		public SILEXRecipe addOut(ItemStack stack, int weight) {
			return addOut(new WeightedRandomObject(stack, weight));
		} 
		
		public SILEXRecipe addOut(WeightedRandomObject entry) {
			outputs.add(entry);
			return this;
		} 
	}
}
