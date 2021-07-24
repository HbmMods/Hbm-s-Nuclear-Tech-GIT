package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Anything that deals exclusively with nuggets, ingots, blocks or compression in general
 * @author hbm
 */
public class MineralRecipes {
	
	public static void register() {

		RecipesCommon.add1To9Pair(ModItems.powder_coal, ModItems.powder_coal_tiny);
		
		RecipesCommon.add9To1(ModItems.ingot_aluminium, ModBlocks.block_aluminium);
		RecipesCommon.add1To9(ModBlocks.block_aluminium, ModItems.ingot_aluminium);

		RecipesCommon.add9To1(ModItems.ingot_graphite, ModBlocks.block_graphite);
		RecipesCommon.add1To9(ModBlocks.block_graphite, ModItems.ingot_graphite);

		RecipesCommon.add9To1(ModItems.ingot_boron, ModBlocks.block_boron);
		RecipesCommon.add1To9(ModBlocks.block_boron, ModItems.ingot_boron);

		RecipesCommon.add9To1(ModItems.powder_boron_tiny, ModItems.powder_boron);
		RecipesCommon.add1To9(ModItems.powder_boron, ModItems.powder_boron_tiny);

		RecipesCommon.add9To1(ModItems.ingot_zirconium, ModBlocks.block_zirconium);
		RecipesCommon.add1To9(ModBlocks.block_zirconium, ModItems.ingot_zirconium);
		RecipesCommon.add9To1(ModItems.nugget_zirconium, ModItems.ingot_zirconium);
		RecipesCommon.add1To9(ModItems.ingot_zirconium, ModItems.nugget_zirconium);

		RecipesCommon.add9To1(ModItems.ingot_schraranium, ModBlocks.block_schraranium);
		RecipesCommon.add1To9(ModBlocks.block_schraranium, ModItems.ingot_schraranium);
		
		RecipesCommon.add9To1(ModItems.ingot_lanthanium, ModBlocks.block_lanthanium);
		RecipesCommon.add1To9(ModBlocks.block_lanthanium, ModItems.ingot_lanthanium);
		
		RecipesCommon.add9To1(ModItems.ingot_actinium, ModBlocks.block_actinium);
		RecipesCommon.add1To9(ModBlocks.block_actinium, ModItems.ingot_actinium);
		
		RecipesCommon.add9To1(ModItems.ingot_schrabidate, ModBlocks.block_schrabidate);
		RecipesCommon.add1To9(ModBlocks.block_schrabidate, ModItems.ingot_schrabidate);
		
		RecipesCommon.add9To1(ModItems.ingot_dineutronium, ModBlocks.block_dineutronium);
		RecipesCommon.add1To9(ModBlocks.block_dineutronium, ModItems.ingot_dineutronium);

		RecipesCommon.add9To1(ModItems.powder_xe135_tiny, ModItems.powder_xe135);
		RecipesCommon.add1To9(ModItems.powder_xe135, ModItems.powder_xe135_tiny);
		RecipesCommon.add9To1(ModItems.powder_cs137_tiny, ModItems.powder_cs137);
		RecipesCommon.add1To9(ModItems.powder_cs137, ModItems.powder_cs137_tiny);
		RecipesCommon.add9To1(ModItems.powder_i131_tiny, ModItems.powder_i131);
		RecipesCommon.add1To9(ModItems.powder_i131, ModItems.powder_i131_tiny);

		RecipesCommon.add1To9Pair(ModItems.ingot_technetium, ModItems.nugget_technetium);
		RecipesCommon.add1To9Pair(ModItems.ingot_co60, ModItems.nugget_co60);
		RecipesCommon.add1To9Pair(ModItems.ingot_au198, ModItems.nugget_au198);

		RecipesCommon.add1To9Pair(ModItems.ingot_bismuth, ModItems.nugget_bismuth);
		RecipesCommon.add1To9Pair(ModBlocks.block_bismuth, ModItems.ingot_bismuth);
		RecipesCommon.add1To9Pair(ModBlocks.block_coltan, ModItems.fragment_coltan);
		RecipesCommon.add1To9Pair(ModItems.ingot_tantalium, ModItems.nugget_tantalium);
		RecipesCommon.add1To9Pair(ModBlocks.block_tantalium, ModItems.ingot_tantalium);

		RecipesCommon.add1To9Pair(ModItems.ingot_pu241, ModItems.nugget_pu241);
		RecipesCommon.add1To9Pair(ModItems.ingot_am241, ModItems.nugget_am241);
		RecipesCommon.add1To9Pair(ModItems.ingot_am242, ModItems.nugget_am242);
		RecipesCommon.add1To9Pair(ModItems.ingot_am_mix, ModItems.nugget_am_mix);
		RecipesCommon.add1To9Pair(ModItems.ingot_americium_fuel, ModItems.nugget_americium_fuel);

		for(int i = 0; i < ItemWasteLong.WasteClass.values().length; i++) {
			RecipesCommon.add9To1SameMeta(ModItems.nuclear_waste_long_tiny, ModItems.nuclear_waste_long, i);
			RecipesCommon.add1To9SameMeta(ModItems.nuclear_waste_long, ModItems.nuclear_waste_long_tiny, i);
			RecipesCommon.add9To1SameMeta(ModItems.nuclear_waste_long_depleted_tiny, ModItems.nuclear_waste_long_depleted, i);
			RecipesCommon.add1To9SameMeta(ModItems.nuclear_waste_long_depleted, ModItems.nuclear_waste_long_depleted_tiny, i);
		}

		for(int i = 0; i < ItemWasteShort.WasteClass.values().length; i++) {
			RecipesCommon.add9To1SameMeta(ModItems.nuclear_waste_short_tiny, ModItems.nuclear_waste_short, i);
			RecipesCommon.add1To9SameMeta(ModItems.nuclear_waste_short, ModItems.nuclear_waste_short_tiny, i);
			RecipesCommon.add9To1SameMeta(ModItems.nuclear_waste_short_depleted_tiny, ModItems.nuclear_waste_short_depleted, i);
			RecipesCommon.add1To9SameMeta(ModItems.nuclear_waste_short_depleted, ModItems.nuclear_waste_short_depleted_tiny, i);
		}
		
		RecipesCommon.add9To1(ModItems.fallout, ModBlocks.block_fallout);
		RecipesCommon.add1To9(ModBlocks.block_fallout, ModItems.fallout);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.fallout, 2), new Object[] { "##", '#', ModItems.fallout });
		
		RecipesCommon.add9To1(ModItems.ingot_pu_mix, ModBlocks.block_pu_mix);
		RecipesCommon.add1To9(ModBlocks.block_pu_mix, ModItems.ingot_pu_mix);
		RecipesCommon.add9To1(ModItems.nugget_pu_mix, ModItems.ingot_pu_mix);
		RecipesCommon.add1To9(ModItems.ingot_pu_mix, ModItems.nugget_pu_mix);
		
		RecipesCommon.add9To1(ModItems.nugget_neptunium_fuel, ModItems.ingot_neptunium_fuel);
		RecipesCommon.add1To9(ModItems.ingot_neptunium_fuel, ModItems.nugget_neptunium_fuel);

		RecipesCommon.addBillet(ModItems.billet_uranium, ModItems.nugget_uranium, "nuggetUranium");
		RecipesCommon.addBillet(ModItems.billet_u233, ModItems.nugget_u233, "nuggetUranium233", "tinyU233");
		RecipesCommon.addBillet(ModItems.billet_u235, ModItems.nugget_u235, "nuggetUranium235", "tinyU235");
		RecipesCommon.addBillet(ModItems.billet_u238, ModItems.nugget_u238, "nuggetUranium238", "tinyU238");
		RecipesCommon.addBillet(ModItems.billet_th232, ModItems.nugget_th232, "nuggetThorium232", "tinyTh232");
		RecipesCommon.addBillet(ModItems.billet_plutonium, ModItems.nugget_plutonium, "nuggetPlutonium");
		RecipesCommon.addBillet(ModItems.billet_pu238, ModItems.nugget_pu238, "nuggetPlutonium238", "tinyPu238");
		RecipesCommon.addBillet(ModItems.billet_pu239, ModItems.nugget_pu239, "nuggetPlutonium239", "tinyPu239");
		RecipesCommon.addBillet(ModItems.billet_pu240, ModItems.nugget_pu240, "nuggetPlutonium240", "tinyPu240");
		RecipesCommon.addBillet(ModItems.billet_pu241, ModItems.nugget_pu241, "nuggetPlutonium241", "tinyPu241");
		RecipesCommon.addBillet(ModItems.billet_pu_mix, ModItems.nugget_pu_mix);
		RecipesCommon.addBillet(ModItems.billet_am241, ModItems.nugget_am241, "nuggetAmericium241", "tinyAm241");
		RecipesCommon.addBillet(ModItems.billet_am242, ModItems.nugget_am242, "nuggetAmericium242", "tinyAm242");
		RecipesCommon.addBillet(ModItems.billet_am_mix, ModItems.nugget_am_mix);
		RecipesCommon.addBillet(ModItems.billet_neptunium, ModItems.nugget_neptunium, "nuggetNeptunium237", "tinyNp237");
		RecipesCommon.addBillet(ModItems.billet_polonium, ModItems.nugget_polonium, "nuggetPolonium");
		RecipesCommon.addBillet(ModItems.billet_technetium, ModItems.nugget_technetium, "nuggetTechnetium");
		RecipesCommon.addBillet(ModItems.billet_au198, ModItems.nugget_au198);
		RecipesCommon.addBillet(ModItems.billet_schrabidium, ModItems.nugget_schrabidium);
		RecipesCommon.addBillet(ModItems.billet_solinium, ModItems.nugget_solinium);
		RecipesCommon.addBillet(ModItems.billet_uranium_fuel, ModItems.nugget_uranium_fuel);
		RecipesCommon.addBillet(ModItems.billet_thorium_fuel, ModItems.nugget_thorium_fuel);
		RecipesCommon.addBillet(ModItems.billet_plutonium_fuel, ModItems.nugget_plutonium_fuel);
		RecipesCommon.addBillet(ModItems.billet_neptunium_fuel, ModItems.nugget_neptunium_fuel);
		RecipesCommon.addBillet(ModItems.billet_mox_fuel, ModItems.nugget_mox_fuel);
		RecipesCommon.addBillet(ModItems.billet_les, ModItems.nugget_les);
		RecipesCommon.addBillet(ModItems.billet_schrabidium_fuel, ModItems.nugget_schrabidium_fuel);
		RecipesCommon.addBillet(ModItems.billet_hes, ModItems.nugget_hes);
		RecipesCommon.addBillet(ModItems.billet_australium, ModItems.nugget_australium);
		RecipesCommon.addBillet(ModItems.billet_australium_greater, ModItems.nugget_australium_greater);
		RecipesCommon.addBillet(ModItems.billet_australium_lesser, ModItems.nugget_australium_lesser);

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_thorium_fuel, 3), new Object[] { ModItems.billet_th232, ModItems.billet_th232, ModItems.billet_u233 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_thorium_fuel, 1), new Object[] { "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetUranium233", "nuggetUranium233" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_thorium_fuel, 1), new Object[] { "tinyTh232", "tinyTh232", "tinyTh232", "tinyTh232", "tinyU233", "tinyU233" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_uranium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u235 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_uranium_fuel, 1), new Object[] { "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium235", "nuggetUranium235" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_uranium_fuel, 1), new Object[] { "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU235", "tinyU235" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_plutonium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_pu_mix, ModItems.billet_pu_mix });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_plutonium_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "nuggetUranium238", "nuggetUranium238" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_plutonium_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "tinyU238", "tinyU238" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_pu_mix, 3), new Object[] { ModItems.billet_pu239, ModItems.billet_pu239, ModItems.billet_pu240 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_pu_mix, 1), new Object[] { "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium240", "nuggetPlutonium240" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_pu_mix, 1), new Object[] { "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu240", "tinyPu240" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_americium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_am_mix });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_americium_fuel, 1), new Object[] { ModItems.nugget_am_mix, ModItems.nugget_am_mix, "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_americium_fuel, 1), new Object[] { ModItems.nugget_am_mix, ModItems.nugget_am_mix, "tinyU238", "tinyU238", "tinyU238", "tinyU238" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_am_mix, 3), new Object[] { ModItems.billet_am241, ModItems.billet_am242, ModItems.billet_am242 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_am_mix, 1), new Object[] { "nuggetAmericium241", "nuggetAmericium241", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_am_mix, 1), new Object[] { "tinyAm241", "tinyAm241", "tinyAm242", "tinyAm242", "tinyAm242", "tinyAm242" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_neptunium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_neptunium });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_neptunium_fuel, 1), new Object[] { "nuggetNeptunium237", "nuggetNeptunium237", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_neptunium_fuel, 1), new Object[] { "tinyNp237", "tinyNp237", "tinyU238", "tinyU238", "tinyU238", "tinyU238" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_mox_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u235, ModItems.billet_pu_mix });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_mox_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "nuggetUranium238", "nuggetUranium238", "nuggetUranium235", "nuggetUranium235" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_mox_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "tinyU238", "tinyU238", "tinyU235", "tinyU235" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_schrabidium_fuel, 3), new Object[] { ModItems.billet_schrabidium, ModItems.billet_neptunium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_schrabidium_fuel, 1), new Object[] { ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, "nuggetNeptunium237", "nuggetNeptunium237", ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_schrabidium_fuel, 1), new Object[] { ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, "tinyNp237", "tinyNp237", ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_po210be, 1), new Object[] { "nuggetPolonium", "nuggetPolonium", "nuggetPolonium", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_pu238be, 1), new Object[] { "nuggetPlutonium238", "nuggetPlutonium238", "nuggetPlutonium238", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_ra226be, 1), new Object[] { "nuggetRadium226", "nuggetRadium226", "nuggetRadium226", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));


		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_uranium, 9), new Object[] { ModItems.billet_u235, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium, 1), new Object[] { "nuggetUranium235", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium, 1), new Object[] { "tinyU235", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238" }));
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_uranium, 2), new Object[] { ModItems.billet_uranium, ModItems.billet_uranium, ModItems.billet_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_u233, 2), new Object[] { ModItems.billet_u233, ModItems.billet_u233, ModItems.billet_u233 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_u235, 2), new Object[] { ModItems.billet_u235, ModItems.billet_u235, ModItems.billet_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_u238, 2), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_plutonium, 2), new Object[] { ModItems.billet_plutonium, ModItems.billet_plutonium, ModItems.billet_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_pu238, 2), new Object[] { ModItems.billet_pu238, ModItems.billet_pu238, ModItems.billet_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_pu239, 2), new Object[] { ModItems.billet_pu239, ModItems.billet_pu239, ModItems.billet_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_pu240, 2), new Object[] { ModItems.billet_pu240, ModItems.billet_pu240, ModItems.billet_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_pu241, 2), new Object[] { ModItems.billet_pu241, ModItems.billet_pu241, ModItems.billet_pu241 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_pu_mix, 2), new Object[] { ModItems.billet_pu_mix, ModItems.billet_pu_mix, ModItems.billet_pu_mix });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_am241, 2), new Object[] { ModItems.billet_am241, ModItems.billet_am241, ModItems.billet_am241 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_am242, 2), new Object[] { ModItems.billet_am242, ModItems.billet_am242, ModItems.billet_am242 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_am_mix, 2), new Object[] { ModItems.billet_am_mix, ModItems.billet_am_mix, ModItems.billet_am_mix });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 2), new Object[] { ModItems.billet_uranium_fuel, ModItems.billet_uranium_fuel, ModItems.billet_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 2), new Object[] { ModItems.billet_plutonium_fuel, ModItems.billet_plutonium_fuel, ModItems.billet_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_americium_fuel, 2), new Object[] { ModItems.billet_americium_fuel, ModItems.billet_americium_fuel, ModItems.billet_americium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_mox_fuel, 2), new Object[] { ModItems.billet_mox_fuel, ModItems.billet_mox_fuel, ModItems.billet_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_neptunium, 2), new Object[] { ModItems.billet_neptunium, ModItems.billet_neptunium, ModItems.billet_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_neptunium_fuel, 2), new Object[] { ModItems.billet_neptunium_fuel, ModItems.billet_neptunium_fuel, ModItems.billet_neptunium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_polonium, 2), new Object[] { ModItems.billet_polonium, ModItems.billet_polonium, ModItems.billet_polonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_technetium, 2), new Object[] { ModItems.billet_technetium, ModItems.billet_technetium, ModItems.billet_technetium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_schrabidium, 2), new Object[] { ModItems.billet_schrabidium, ModItems.billet_schrabidium, ModItems.billet_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_solinium, 2), new Object[] { ModItems.billet_solinium, ModItems.billet_solinium, ModItems.billet_solinium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_les, 2), new Object[] { ModItems.billet_les, ModItems.billet_les, ModItems.billet_les });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 2), new Object[] { ModItems.billet_schrabidium_fuel, ModItems.billet_schrabidium_fuel, ModItems.billet_schrabidium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_hes, 2), new Object[] { ModItems.billet_hes, ModItems.billet_hes, ModItems.billet_hes });
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_balefire_gold, 1), new Object[] { ModItems.billet_au198, ModItems.cell_antimatter, ModItems.pellet_charged });
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg), new Object[] { ModItems.billet_pu238, ModItems.billet_pu238, ModItems.billet_pu238, "plateIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_weak), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_pu238, "plateIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_polonium), new Object[] { ModItems.billet_polonium, ModItems.billet_polonium, ModItems.billet_polonium, "plateIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_gold), new Object[] { ModItems.billet_au198, ModItems.billet_au198, ModItems.billet_au198, "plateIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_americium), new Object[] { ModItems.billet_am241, ModItems.billet_am241, ModItems.billet_am241, "plateIron" }));
		
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_copper), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_fluorite), 1), new Object[] { "###", "###", "###", '#', ModItems.fluorite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_niter), 1), new Object[] { "###", "###", "###", '#', ModItems.niter });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_red_copper), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_red_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_sulfur), 1), new Object[] { "###", "###", "###", '#', ModItems.sulfur });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_titanium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_tungsten), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_tungsten });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_uranium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_uranium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_thorium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_th232 });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_lead), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_lead });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_trinitite), 1), new Object[] { "###", "###", "###", '#', ModItems.trinitite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_waste), 1), new Object[] { "###", "###", "###", '#', ModItems.nuclear_waste });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_scrap), 1), new Object[] { "##", "##", '#', ModItems.scrap });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_scrap), 1), new Object[] { "###", "###", "###", '#', ModItems.dust });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_beryllium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_beryllium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_schrabidium_cluster, 1), new Object[] { "#S#", "SXS", "#S#", '#', ModItems.ingot_schrabidium, 'S', ModItems.ingot_starmetal, 'X', ModItems.ingot_schrabidate });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_euphemium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_euphemium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_advanced_alloy), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_magnetized_tungsten), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_magnetized_tungsten });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_combine_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_combine_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_australium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_australium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_desh), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_desh });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_dura_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_dura_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_meteor_cobble), 1), new Object[] { "##", "##", '#', ModItems.fragment_meteorite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_meteor_broken), 1), new Object[] { "###", "###", "###", '#', ModItems.fragment_meteorite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_yellowcake), 1), new Object[] { "###", "###", "###", '#', ModItems.powder_yellowcake });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_starmetal, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_u233, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_u233 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_u235, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_u235 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_u238, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_uranium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_uranium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_neptunium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_polonium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_polonium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_plutonium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_pu238, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_pu239, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_pu240, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_mox_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_mox_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_plutonium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_plutonium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_thorium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_thorium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_solinium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_solinium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_schrabidium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_schrabidium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_lithium, 1), new Object[] { "###", "###", "###", '#', ModItems.lithium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_white_phosphorus, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_red_phosphorus, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_insulator, 1), new Object[] { "###", "###", "###", '#', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_asbestos, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_asbestos });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_fiberglass, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_fiberglass });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_cobalt, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_cobalt });

		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_copper) });
		GameRegistry.addRecipe(new ItemStack(ModItems.fluorite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_fluorite) });
		GameRegistry.addRecipe(new ItemStack(ModItems.niter, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_niter) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_red_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_red_copper) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_steel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.sulfur, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_sulfur) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_titanium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_titanium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_tungsten) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_uranium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_th232, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_thorium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_lead, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_lead) });
		GameRegistry.addRecipe(new ItemStack(ModItems.trinitite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_trinitite) });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_waste) });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_waste_painted) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_beryllium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_beryllium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_schrabidium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_euphemium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_euphemium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_advanced_alloy, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_advanced_alloy) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_magnetized_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_magnetized_tungsten) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_combine_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_combine_steel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_australium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_australium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_desh, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_desh) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_dura_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_dura_steel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_yellowcake, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_yellowcake) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_starmetal, 9), new Object[] { "#", '#', ModBlocks.block_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u233, 9), new Object[] { "#", '#', ModBlocks.block_u233 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u235, 9), new Object[] { "#", '#', ModBlocks.block_u235 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u238, 9), new Object[] { "#", '#', ModBlocks.block_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_uranium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_neptunium, 9), new Object[] { "#", '#', ModBlocks.block_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_polonium, 9), new Object[] { "#", '#', ModBlocks.block_polonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_plutonium, 9), new Object[] { "#", '#', ModBlocks.block_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu238, 9), new Object[] { "#", '#', ModBlocks.block_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu239, 9), new Object[] { "#", '#', ModBlocks.block_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu240, 9), new Object[] { "#", '#', ModBlocks.block_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_mox_fuel, 9), new Object[] { "#", '#', ModBlocks.block_mox_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_plutonium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_thorium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_thorium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_solinium, 9), new Object[] { "#", '#', ModBlocks.block_solinium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_schrabidium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.lithium, 9), new Object[] { "#", '#', ModBlocks.block_lithium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_phosphorus, 9), new Object[] { "#", '#', ModBlocks.block_white_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_fire, 9), new Object[] { "#", '#', ModBlocks.block_red_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_polymer, 9), new Object[] { "#", '#', ModBlocks.block_insulator });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_asbestos, 9), new Object[] { "#", '#', ModBlocks.block_asbestos });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_fiberglass, 9), new Object[] { "#", '#', ModBlocks.block_fiberglass });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_cobalt, 9), new Object[] { "#", '#', ModBlocks.block_cobalt });

		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_plutonium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_plutonium, 9), new Object[] { "#", '#', ModItems.ingot_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu238, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_pu238, 9), new Object[] { "#", '#', ModItems.ingot_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu239, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_pu239, 9), new Object[] { "#", '#', ModItems.ingot_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu240, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_pu240, 9), new Object[] { "#", '#', ModItems.ingot_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_th232, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_th232 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_th232, 9), new Object[] { "#", '#', ModItems.ingot_th232 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_uranium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_uranium, 9), new Object[] { "#", '#', ModItems.ingot_uranium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u233, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u233 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_u233, 9), new Object[] { "#", '#', ModItems.ingot_u233 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u235, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u235 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_u235, 9), new Object[] { "#", '#', ModItems.ingot_u235 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u238, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_u238, 9), new Object[] { "#", '#', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_neptunium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_neptunium, 9), new Object[] { "#", '#', ModItems.ingot_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_polonium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_polonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_polonium, 9), new Object[] { "#", '#', ModItems.ingot_polonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_lead, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_lead, 9), new Object[] { "#", '#', ModItems.ingot_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_beryllium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_beryllium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_beryllium, 9), new Object[] { "#", '#', ModItems.ingot_beryllium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_schrabidium, 9), new Object[] { "#", '#', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_uranium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_uranium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_thorium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_thorium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_thorium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_thorium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_plutonium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_plutonium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_plutonium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_mox_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_mox_fuel, 9), new Object[] { "#", '#', ModItems.ingot_mox_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_schrabidium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_schrabidium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_schrabidium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_hes, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_hes });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_hes, 9), new Object[] { "#", '#', ModItems.ingot_hes });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_les, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_les });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_les, 9), new Object[] { "#", '#', ModItems.ingot_les });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_australium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_australium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_australium, 9), new Object[] { "#", '#', ModItems.ingot_australium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_steel, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_steel_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_steel_tiny, 9), new Object[] { "#", '#', ModItems.powder_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_lithium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_lithium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_lithium_tiny, 9), new Object[] { "#", '#', ModItems.powder_lithium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_cobalt, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_cobalt_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_cobalt_tiny, 9), new Object[] { "#", '#', ModItems.powder_cobalt });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_neodymium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_neodymium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_neodymium_tiny, 9), new Object[] { "#", '#', ModItems.powder_neodymium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_niobium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_niobium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_niobium_tiny, 9), new Object[] { "#", '#', ModItems.powder_niobium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_cerium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_cerium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_cerium_tiny, 9), new Object[] { "#", '#', ModItems.powder_cerium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_lanthanium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_lanthanium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_lanthanium_tiny, 9), new Object[] { "#", '#', ModItems.powder_lanthanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_actinium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_actinium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_actinium_tiny, 9), new Object[] { "#", '#', ModItems.powder_actinium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_meteorite, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_meteorite_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_meteorite_tiny, 9), new Object[] { "#", '#', ModItems.powder_meteorite });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_solinium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_solinium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_solinium, 9), new Object[] { "#", '#', ModItems.ingot_solinium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste, 1), new Object[] { "###", "###", "###", '#', ModItems.nuclear_waste_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste_tiny, 9), new Object[] { "#", '#', ModItems.nuclear_waste });
		GameRegistry.addRecipe(new ItemStack(ModItems.bottle_mercury, 1), new Object[] { "###", "#B#", "###", '#', ModItems.nugget_mercury, 'B', Items.glass_bottle });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_mercury, 8), new Object[] { "#", '#', ModItems.bottle_mercury });
		GameRegistry.addRecipe(new ItemStack(ModItems.egg_balefire, 1), new Object[] { "###", "###", "###", '#', ModItems.egg_balefire_shard });
		GameRegistry.addRecipe(new ItemStack(ModItems.egg_balefire_shard, 9), new Object[] { "#", '#', ModItems.egg_balefire });

		GameRegistry.addRecipe(new ItemStack(ModBlocks.hazmat, 8), new Object[] { "###", "# #", "###", '#', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_cloth, 1), new Object[] { "#", '#', ModBlocks.hazmat });
		GameRegistry.addRecipe(new ItemStack(ModItems.egg_balefire_shard, 1), new Object[] { "##", "##", '#', ModItems.powder_balefire });
		RecipesCommon.add9To1(ModItems.cell_balefire, ModItems.egg_balefire_shard);
		
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_euphemium, 1), new Object[] { "#", '#', ModItems.rod_quad_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_euphemium, 1), new Object[] { "###", "###", "###", '#', ModItems.rod_quad_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_euphemium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_euphemium, 9), new Object[] { "#", '#', ModItems.ingot_euphemium });

		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "nuggetUranium235", "nuggetUranium235", "nuggetUranium235", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "nuggetUranium233", "nuggetUranium233", "nuggetUranium233", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_thorium_fuel, 1), new Object[] { "nuggetUranium233", "nuggetUranium233", "nuggetUranium233", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "nuggetPlutonium238", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium240", "nuggetPlutonium240", "nuggetPlutonium240" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "tinyU235", "tinyU235", "tinyU235", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "tinyPu238", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu240", "tinyPu240", "tinyPu240" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "tinyU235", "tinyU235", "tinyU235", "tinyU238", "tinyU238", "tinyPu238", "tinyPu239", "tinyPu239", "tinyPu239" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 1), new Object[] { "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetNeptunium237", "nuggetNeptunium237", "nuggetNeptunium237", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_hes, 1), new Object[] { "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetNeptunium237", "nuggetNeptunium237", ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_les, 1), new Object[] { "nuggetSchrabidium", "nuggetNeptunium237", "nuggetNeptunium237", "nuggetNeptunium237", "nuggetNeptunium237", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_pu_mix, 1), new Object[] { "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPluonium240", "nuggetPluonium240", "nuggetPluonium240" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_pu_mix, 1), new Object[] { "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu240", "tinyPu240", "tinyPu240" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_am_mix, 1), new Object[] { "nuggetAmericium241", "nuggetAmericium241", "nuggetAmericium241", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_am_mix, 1), new Object[] { "tinyAm241", "tinyAm241", "tinyAm241", "tinyAm242", "tinyAm242", "tinyAm242", "tinyAm242", "tinyAm242", "tinyAm242" }));
	}
}
