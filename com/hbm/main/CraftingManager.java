package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingManager {
	
	public static void mainRegistry()
	{
		AddCraftingRec();
		AddSmeltingRec();
	}

	public static void AddCraftingRec()
	{
		GameRegistry.addRecipe(new ItemStack(ModItems.redstone_sword, 1), new Object[] { "R", "R", "S", 'R', Blocks.redstone_block, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.big_sword, 1), new Object[] { "QIQ", "QIQ", "GSG", 'G', Items.gold_ingot, 'S', Items.stick, 'I', Items.iron_ingot, 'Q', Items.quartz});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_titanium, 16), true, new Object[] { "TT", "TT", 'T', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_aluminium, 16), new Object[] { "TT", "TT", 'T', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_steel, 16), new Object[] { "TT", "TT", 'T', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_iron, 16), new Object[] { "TT", "TT", 'T', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_lead, 16), new Object[] { "TT", "TT", 'T', "ingotLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_copper, 16), new Object[] { "TT", "TT", 'T', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.board_copper, 1), new Object[] { "TTT", "TTT", 'T', "plateCopper" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_schrabidium, 16), new Object[] { "TT", "TT", 'T', ModItems.ingot_schrabidium});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_gold, 16), new Object[] { "TT", "TT", 'T', "ingotGold" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_advanced_alloy, 16), new Object[] { "TT", "TT", 'T', ModItems.ingot_advanced_alloy});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_combine_steel, 16), new Object[] { "TT", "TT", 'T', ModItems.ingot_combine_steel});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wire_red_copper, 6), new Object[] { "CCC", 'S', Items.string, 'C', "ingotRedstoneAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wire_tungsten, 6), new Object[] { "CCC", 'S', Items.string, 'C', "ingotTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wire_aluminium, 6), new Object[] { "CCC", 'S', Items.string, 'C', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wire_copper, 6), new Object[] { "CCC", 'S', Items.string, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wire_gold, 6), new Object[] { "CCC", 'S', Items.string, 'C', "ingotGold" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.wire_schrabidium, 6), new Object[] { "CCC", 'S', Items.string, 'C', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.wire_advanced_alloy, 6), new Object[] { "CCC", 'S', Items.string, 'C', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(ModItems.wire_magnetized_tungsten, 6), new Object[] { "CCC", 'S', Items.string, 'C', ModItems.ingot_magnetized_tungsten });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_cloth, 4), new Object[] { "LN", "LN", 'L', Items.leather, 'N', "nuggetLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_cloth, 4), new Object[] { "SCS", "CPC", "SCS", 'S', Items.string, 'P', Items.leather, 'C', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.asbestos_cloth, 4), new Object[] { "SCS", "CPC", "SCS", 'S', Items.string, 'P', Blocks.wool, 'C', "dustNetherQuartz" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.asbestos_cloth, 16), new Object[] { "SCS", "CPC", "SCS", 'S', Items.string, 'P', ModItems.powder_bromine, 'C', Blocks.wool }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.filter_coal, 1), new Object[] { "SCS", "CPC", "SCS", 'S', Items.string, 'P', Items.paper, 'C', "dustCoal" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_mixed, 3), new Object[] { "ANA", "NCN", "ANA", 'A', ModItems.plate_advanced_alloy, 'N', "plateDenseLead", 'C', ModItems.plate_combine_steel }));
		GameRegistry.addRecipe(new ItemStack(ModItems.bolt_dura_steel, 4), new Object[] { "D", "D", 'D', ModItems.ingot_dura_steel});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pipes_steel, 1), new Object[] { "B", "B", "B", 'B', "blockSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.drill_titanium, 1), new Object[] { " B ", "IBI", "PPP", 'B', ModItems.bolt_dura_steel, 'I', ModItems.ingot_dura_steel, 'P', "plateTitanium" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.magnet_dee, 1), new Object[] { "SMM", "M M", "MMT", 'S', "ingotSteel", 'M', ModBlocks.fusion_conductor, 'T', ModItems.coil_advanced_torus }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.magnet_circular, 2), new Object[] { "PSP", "MMM", "PSP", 'S', "ingotSteel", 'M', ModBlocks.fusion_conductor, 'P', "plateAdvanced" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.cyclotron_tower, 1), new Object[] { "CDC", "CDC", "CDC", 'C', ModItems.magnet_circular, 'D', ModItems.magnet_dee });
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.marker_structure, 1), new Object[] { "L", "G", "R", 'L', ModItems.powder_lapis, 'G', Items.glowstone_dust, 'R', Blocks.redstone_torch });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_aluminium, 1), new Object[] { "RAR", "ASA", "RAR", 'S', "plateSteel", 'R', "dustRedstone", 'A', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_copper, 1), new Object[] { "RAR", "ASA", "RAR", 'S', ModItems.circuit_aluminium, 'R', "dustNetherQuartz", 'A', ModItems.wire_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_red_copper, 1), new Object[] { "RAR", "ASA", "RAR", 'S', ModItems.circuit_copper, 'R', "dustGold", 'A', ModItems.wire_red_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_gold, 1), new Object[] { "RAR", "ASA", "RAR", 'S', ModItems.circuit_red_copper, 'R', "dustLapis", 'A', ModItems.wire_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_schrabidium, 1), new Object[] { "RAR", "ASA", "RAR", 'S', ModItems.circuit_gold, 'R', "dustDiamond", 'A', ModItems.wire_schrabidium }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 3), new Object[] { "sulfur", "salpeter", Items.coal }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 3), new Object[] { "sulfur", "salpeter", new ItemStack(Items.coal, 1, 1) }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 3), new Object[] { "dustSulfur", "dustSalpeter", Items.coal }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 3), new Object[] { "dustSulfur", "dustSalpeter", new ItemStack(Items.coal, 1, 1) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cell_empty, 6), new Object[] { "SSS", "G G", "SSS", 'S', "plateSteel", 'G', Item.getItemFromBlock(Blocks.glass_pane) }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.cell_uf6, 1), new Object[] { ModItems.cell_empty, "dustUranium", "dustFluorite", "dustFluorite", "dustFluorite", Items.water_bucket }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.cell_puf6, 1), new Object[] { ModItems.cell_empty, "dustPlutonium", "dustFluorite", "dustFluorite", "dustFluorite", Items.water_bucket }));
		GameRegistry.addRecipe(new ItemStack(ModItems.cell_deuterium, 8), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.cell_empty, 'T', ModItems.mike_deut });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.cell_sas3, 1), new Object[] { ModItems.cell_empty, ModItems.powder_schrabidium, "dustSulfur", "dustSulfur" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.cell_sas3, 1), new Object[] { ModItems.cell_empty, ModItems.powder_schrabidium, "sulfur", "sulfur" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.canister_empty, 2), new Object[] { "S ", "AA", "AA", 'S', "plateSteel", 'A', "plateAluminum" }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_barrel), 1), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.canister_fuel, 'T', ModItems.tank_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.yellow_barrel), 1), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.nuclear_waste, 'T', ModItems.tank_steel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_empty, 2), new Object[] { "S ", "AA", "AA", 'A', "plateSteel", 'S', "plateCopper" }));

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_aluminium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_aluminium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_copper), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_fluorite), 1), new Object[] { "###", "###", "###", '#', ModItems.fluorite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_niter), 1), new Object[] { "###", "###", "###", '#', ModItems.niter });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_red_copper), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_red_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_sulfur), 1), new Object[] { "###", "###", "###", '#', ModItems.sulfur });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_titanium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_tungsten), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_tungsten });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_uranium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_uranium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_lead), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_lead });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_trinitite), 1), new Object[] { "###", "###", "###", '#', ModItems.trinitite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_waste), 1), new Object[] { "###", "###", "###", '#', ModItems.nuclear_waste });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_scrap), 1), new Object[] { "##", "##", '#', ModItems.scrap });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_scrap), 1), new Object[] { "###", "###", "###", '#', ModItems.dust });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_beryllium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_beryllium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_advanced_alloy), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_magnetized_tungsten), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_magnetized_tungsten });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_combine_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_combine_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_australium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_australium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_weidanium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_weidanium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_reiium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_reiium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_unobtainium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_unobtainium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_daffergon), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_daffergon });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_verticium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_verticium });

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "nuggetUranium235", "nuggetUranium235", "nuggetUranium235", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "nuggetPlutonium238", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium240", "nuggetPlutonium240", "nuggetPlutonium240" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "nuggetUranium235", "nuggetUranium235", "nuggetUranium235", "nuggetUranium238", "nuggetUranium238", "nuggetPlutonium238", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "tinyU235", "tinyU235", "tinyU235", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "tinyPu238", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu240", "tinyPu240", "tinyPu240" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "tinyU235", "tinyU235", "tinyU235", "tinyU238", "tinyU238", "tinyPu238", "tinyPu239", "tinyPu239", "tinyPu239" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 1), new Object[] { "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetNeptunium", "nuggetNeptunium", "nuggetNeptunium", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_hes, 1), new Object[] { "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetNeptunium", "nuggetNeptunium", ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_les, 1), new Object[] { "nuggetSchrabidium", "nuggetNeptunium", "nuggetNeptunium", "nuggetNeptunium", "nuggetNeptunium", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_aluminium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_aluminium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_copper) });
		GameRegistry.addRecipe(new ItemStack(ModItems.fluorite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_fluorite) });
		GameRegistry.addRecipe(new ItemStack(ModItems.niter, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_niter) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_red_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_red_copper) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_steel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.sulfur, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_sulfur) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_titanium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_titanium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_tungsten) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_uranium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_lead, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_lead) });
		GameRegistry.addRecipe(new ItemStack(ModItems.trinitite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_trinitite) });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_waste) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_beryllium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_beryllium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_schrabidium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_advanced_alloy, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_advanced_alloy) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_magnetized_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_magnetized_tungsten) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_combine_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_combine_steel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_australium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_australium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_weidanium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_weidanium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_reiium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_reiium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_unobtainium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_unobtainium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_daffergon, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_daffergon) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_verticium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_verticium) });

		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_plutonium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_plutonium, 9), new Object[] { "#", '#', ModItems.ingot_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu238, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_pu238, 9), new Object[] { "#", '#', ModItems.ingot_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu239, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_pu239, 9), new Object[] { "#", '#', ModItems.ingot_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu240, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_pu240, 9), new Object[] { "#", '#', ModItems.ingot_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_uranium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_uranium, 9), new Object[] { "#", '#', ModItems.ingot_uranium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u235, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u235 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_u235, 9), new Object[] { "#", '#', ModItems.ingot_u235 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u238, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_u238, 9), new Object[] { "#", '#', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_neptunium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_neptunium, 9), new Object[] { "#", '#', ModItems.ingot_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_lead, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_lead, 9), new Object[] { "#", '#', ModItems.ingot_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_beryllium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_beryllium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_beryllium, 9), new Object[] { "#", '#', ModItems.ingot_beryllium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_schrabidium, 9), new Object[] { "#", '#', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_uranium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_uranium_fuel });
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
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_weidanium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_weidanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_weidanium, 9), new Object[] { "#", '#', ModItems.ingot_weidanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_reiium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_reiium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_reiium, 9), new Object[] { "#", '#', ModItems.ingot_reiium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_unobtainium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_unobtainium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_unobtainium, 9), new Object[] { "#", '#', ModItems.ingot_unobtainium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_daffergon, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_daffergon });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_daffergon, 9), new Object[] { "#", '#', ModItems.ingot_daffergon });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_verticium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_verticium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_verticium, 9), new Object[] { "#", '#', ModItems.ingot_verticium });
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

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.rod_empty, 16), new Object[] { "SSS", "L L", "SSS", 'S', "plateSteel", 'L', "plateLead" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_uranium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_u235, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_u238, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_plutonium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_pu238, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_pu239, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_pu240, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_neptunium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_lead, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_schrabidium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_uranium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_plutonium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_mox_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_schrabidium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_euphemium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_australium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_weidanium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_weidanium, ModItems.nugget_weidanium, ModItems.nugget_weidanium, ModItems.nugget_weidanium, ModItems.nugget_weidanium, ModItems.nugget_weidanium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_reiium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_reiium, ModItems.nugget_reiium, ModItems.nugget_reiium, ModItems.nugget_reiium, ModItems.nugget_reiium, ModItems.nugget_reiium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_unobtainium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_daffergon, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_daffergon, ModItems.nugget_daffergon, ModItems.nugget_daffergon, ModItems.nugget_daffergon, ModItems.nugget_daffergon, ModItems.nugget_daffergon });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_verticium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_verticium, ModItems.nugget_verticium, ModItems.nugget_verticium, ModItems.nugget_verticium, ModItems.nugget_verticium, ModItems.nugget_verticium });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_empty, 2), new Object[] { ModItems.rod_dual_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_uranium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_u235, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_u238, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_plutonium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_pu238, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_pu239, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_pu240, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_neptunium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_lead, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_schrabidium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_uranium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_plutonium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_mox_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_schrabidium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_lithium, 1), new Object[] { ModItems.rod_empty, ModItems.lithium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_lithium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.lithium, ModItems.lithium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_lithium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.lithium, ModItems.lithium, ModItems.lithium, ModItems.lithium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_tritium, 1), new Object[] { ModItems.rod_tritium, ModItems.cell_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_tritium, 2), new Object[] { ModItems.rod_dual_tritium, ModItems.cell_empty, ModItems.cell_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_tritium, 4), new Object[] { ModItems.rod_quad_tritium, ModItems.cell_empty, ModItems.cell_empty, ModItems.cell_empty, ModItems.cell_empty });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_empty, 4), new Object[] { ModItems.rod_quad_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_dual_empty, ModItems.rod_dual_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_uranium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_uranium, ModItems.ingot_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_u235, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u235, ModItems.ingot_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_u238, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u238, ModItems.ingot_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_plutonium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_plutonium, ModItems.ingot_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu238, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu238, ModItems.ingot_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu239, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu239, ModItems.ingot_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu240, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu240, ModItems.ingot_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_neptunium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_neptunium, ModItems.ingot_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_lead, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_lead, ModItems.ingot_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_schrabidium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_schrabidium, ModItems.ingot_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_uranium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_uranium_fuel, ModItems.ingot_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_plutonium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_plutonium_fuel, ModItems.ingot_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_mox_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_mox_fuel, ModItems.ingot_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_schrabidium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_schrabidium_fuel, ModItems.ingot_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_water, 1), new Object[] { ModItems.rod_empty, Items.water_bucket });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_water, 1), new Object[] { ModItems.rod_dual_empty, Items.water_bucket, Items.water_bucket });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_water, 1), new Object[] { ModItems.rod_quad_empty, Items.water_bucket, Items.water_bucket, Items.water_bucket, Items.water_bucket });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_coolant, 1), new Object[] { ModItems.rod_empty, Items.water_bucket, ModItems.powder_ice });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_coolant, 1), new Object[] { ModItems.rod_dual_empty, Items.water_bucket, Items.water_bucket, ModItems.powder_ice, ModItems.powder_ice });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_coolant, 1), new Object[] { ModItems.rod_quad_empty, Items.water_bucket, Items.water_bucket, Items.water_bucket, Items.water_bucket, ModItems.powder_ice, ModItems.powder_ice, ModItems.powder_ice, ModItems.powder_ice });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_uranium, 6), new Object[] { ModItems.rod_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_u235, 6), new Object[] { ModItems.rod_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_u238, 6), new Object[] { ModItems.rod_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium, 6), new Object[] { ModItems.rod_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_pu238, 6), new Object[] { ModItems.rod_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_pu239, 6), new Object[] { ModItems.rod_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_pu240, 6), new Object[] { ModItems.rod_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_neptunium, 6), new Object[] { ModItems.rod_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_lead, 6), new Object[] { ModItems.rod_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium, 6), new Object[] { ModItems.rod_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 6), new Object[] { ModItems.rod_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium_fuel, 6), new Object[] { ModItems.rod_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_mox_fuel, 6), new Object[] { ModItems.rod_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium_fuel, 6), new Object[] { ModItems.rod_schrabidium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_australium, 6), new Object[] { ModItems.rod_australium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_weidanium, 6), new Object[] { ModItems.rod_weidanium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_reiium, 6), new Object[] { ModItems.rod_reiium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_unobtainium, 6), new Object[] { ModItems.rod_unobtainium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_daffergon, 6), new Object[] { ModItems.rod_daffergon });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_verticium, 6), new Object[] { ModItems.rod_verticium });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_uranium, 12), new Object[] { ModItems.rod_dual_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_u235, 12), new Object[] { ModItems.rod_dual_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_u238, 12), new Object[] { ModItems.rod_dual_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium, 12), new Object[] { ModItems.rod_dual_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_pu238, 12), new Object[] { ModItems.rod_dual_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_pu239, 12), new Object[] { ModItems.rod_dual_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_pu240, 12), new Object[] { ModItems.rod_dual_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_neptunium, 12), new Object[] { ModItems.rod_dual_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_lead, 12), new Object[] { ModItems.rod_dual_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium, 12), new Object[] { ModItems.rod_dual_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 12), new Object[] { ModItems.rod_dual_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium_fuel, 12), new Object[] { ModItems.rod_dual_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_mox_fuel, 12), new Object[] { ModItems.rod_dual_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium_fuel, 12), new Object[] { ModItems.rod_dual_schrabidium_fuel });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_uranium, 24), new Object[] { ModItems.rod_quad_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_u235, 24), new Object[] { ModItems.rod_quad_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_u238, 24), new Object[] { ModItems.rod_quad_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium, 24), new Object[] { ModItems.rod_quad_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_pu238, 24), new Object[] { ModItems.rod_quad_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_pu239, 24), new Object[] { ModItems.rod_quad_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_pu240, 24), new Object[] { ModItems.rod_quad_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_neptunium, 24), new Object[] { ModItems.rod_quad_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_lead, 24), new Object[] { ModItems.rod_quad_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium, 24), new Object[] { ModItems.rod_quad_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 24), new Object[] { ModItems.rod_quad_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium_fuel, 24), new Object[] { ModItems.rod_quad_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_mox_fuel, 24), new Object[] { ModItems.rod_quad_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium_fuel, 24), new Object[] { ModItems.rod_quad_schrabidium_fuel });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 2), new Object[] { ModItems.rod_waste });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 4), new Object[] { ModItems.rod_dual_waste });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 8), new Object[] { ModItems.rod_quad_waste });
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 4), new Object[] { ModItems.rod_uranium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 8), new Object[] { ModItems.rod_dual_uranium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 16), new Object[] { ModItems.rod_quad_uranium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 4), new Object[] { ModItems.rod_plutonium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 8), new Object[] { ModItems.rod_dual_plutonium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 16), new Object[] { ModItems.rod_quad_plutonium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 4), new Object[] { ModItems.rod_mox_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 8), new Object[] { ModItems.rod_dual_mox_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 16), new Object[] { ModItems.rod_quad_mox_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 4), new Object[] { ModItems.rod_schrabidium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 8), new Object[] { ModItems.rod_dual_schrabidium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 16), new Object[] { ModItems.rod_quad_schrabidium_fuel_depleted });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_lithium), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustLithium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_beryllium), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustBeryllium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_carbon), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustCoal" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_copper), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_plutonium), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustPlutonium" }));
		
		if(false)
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_euphemium, 1, 34), new Object[] { ModItems.rod_quad_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_euphemium, 1, 34), new Object[] { "###", "###", "###", '#', new ItemStack(ModItems.nugget_euphemium, 1, 34) });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_euphemium, 9, 34), new Object[] { "#", '#', new ItemStack(ModItems.ingot_euphemium, 1, 34) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_rtg, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', "tinyPu238" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_rtg_weak, 1), new Object[] { "IUI", "UPU", "IUI", 'I', "plateIron", 'P', "tinyPu238", 'U', "tinyU238" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tritium_deuterium_cake, 1), new Object[] { "DLD", "LTL", "DLD", 'L', "ingotLithium", 'D', ModItems.cell_deuterium, 'T', ModItems.cell_tritium }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_schrabidium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', "ingotSchrabidium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_hes, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_hes }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_mes, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_schrabidium_fuel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_les, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_les }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_beryllium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_beryllium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_neptunium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', "ingotNeptunium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_lead, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', "ingotLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_advanced, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_advanced_alloy }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_copper, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_red_copper, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_advanced_alloy, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_advanced_alloy, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_gold, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_gold, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { "PCP", "C C", "PCP", 'P', "plateIron", 'C', ModItems.coil_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { "PCP", "C C", "PCP", 'P', "plateIron", 'C', ModItems.coil_advanced_alloy }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { "PCP", "C C", "PCP", 'P', "plateIron", 'C', ModItems.coil_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_tungsten, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_magnetized_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tank_steel, 1), new Object[] { "STS", "S S", "STS", 'S', "plateSteel", 'T', "plateTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.motor, 1), new Object[] { " R ", "ICI", "ITI", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', "plateIron", 'C', ModItems.coil_copper }));
		GameRegistry.addRecipe(new ItemStack(ModItems.centrifuge_element, 1), new Object[] { " T ", "WTW", "RMR", 'R', ModItems.wire_red_copper, 'T', ModItems.tank_steel, 'M', ModItems.motor, 'W', ModItems.coil_tungsten });
		GameRegistry.addRecipe(new ItemStack(ModItems.centrifuge_tower, 1), new Object[] { "LL", "EE", "EE", 'E', ModItems.centrifuge_element, 'L', new ItemStack(Items.dye, 1, 4) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reactor_core, 1), new Object[] { "LNL", "N N", "LNL", 'N', "plateDenseLead", 'L', "plateLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.rtg_unit, 1), new Object[] { "TIT", "PCP", "TIT", 'T', ModItems.thermo_element, 'I', "ingotLead", 'P', ModItems.board_copper, 'C', ModItems.circuit_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.thermo_unit_empty, 1), new Object[] { "TTT", " S ", "P P", 'S', "ingotSteel", 'P', "plateTitanium", 'T', ModItems.coil_copper_torus }));
		GameRegistry.addRecipe(new ItemStack(ModItems.thermo_unit_endo, 1), new Object[] { "EEE", "ETE", "EEE", 'E', Item.getItemFromBlock(Blocks.ice), 'T', ModItems.thermo_unit_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.thermo_unit_exo, 1), new Object[] { "LLL", "LTL", "LLL", 'L', Items.lava_bucket, 'T', ModItems.thermo_unit_empty });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.levitation_unit, 1), new Object[] { "CSC", "TAT", "PSP", 'C', ModItems.coil_copper, 'S', ModItems.nugget_schrabidium, 'T', ModItems.coil_tungsten, 'P', "plateTitanium", 'A', "ingotSteel" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cap_aluminium, 1), new Object[] { "PIP", 'P', "plateAluminum", 'I', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_small_steel, 1), new Object[] { "PPP", "   ", "PPP", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_small_aluminium, 1), new Object[] { "PPP", "   ", "PPP", 'P', "plateAluminum", 'I', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_big_steel, 1), new Object[] { "III", "   ", "III", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_big_aluminium, 1), new Object[] { "III", "   ", "III", 'P', "plateAluminum", 'I', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_big_titanium, 1), new Object[] { "III", "   ", "III", 'P', "plateTitanium", 'I', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_flat, 1), new Object[] { "IP", "PP", "IP", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_small_steel, 1), new Object[] { " PP", "PII", " PP", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_big_steel, 1), new Object[] { " PI", "III", " PI", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_tri_steel, 1), new Object[] { " PI", "IIB", " PI", 'P', "plateSteel", 'I', "ingotSteel", 'B', "blockSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_quad_titanium, 1), new Object[] { " PP", "III", " PP", 'P', "plateTitanium", 'I', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.sphere_steel, 1), new Object[] { "PIP", "I I", "PIP", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pedestal_steel, 1), new Object[] { "P P", "P P", "III", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.dysfunctional_reactor, 1), new Object[] { "PPP", "CDC", "PPP", 'P', "plateSteel", 'C', ModItems.rod_quad_empty, 'D', new ItemStack(Items.dye, 1, 3) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.warhead_generic_small, 1), new Object[] { " P ", "PTP", "PTP", 'P', "plateTitanium", 'T', Item.getItemFromBlock(Blocks.tnt) }));
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_incendiary_small, 1), new Object[] { " P ", "PWP", " P ", 'P', ModItems.powder_fire, 'W', ModItems.warhead_generic_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_cluster_small, 1), new Object[] { " P ", "PWP", " P ", 'P', ModItems.pellet_cluster, 'W', ModItems.warhead_generic_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_buster_small, 1), new Object[] { " P ", "PWP", " P ", 'P', Item.getItemFromBlock(ModBlocks.det_cord), 'W', ModItems.warhead_generic_small });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.warhead_generic_medium, 1), new Object[] { " P ", "PTP", "TTT", 'P', "plateTitanium", 'T', Item.getItemFromBlock(Blocks.tnt) }));
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_incendiary_medium, 1), new Object[] { "PPP", "PWP", "PPP", 'P', ModItems.powder_fire, 'W', ModItems.warhead_generic_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_cluster_medium, 1), new Object[] { "PPP", "PWP", "PPP", 'P', ModItems.pellet_cluster, 'W', ModItems.warhead_generic_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_buster_medium, 1), new Object[] { "PPP", "PWP", "PPP", 'P', Item.getItemFromBlock(ModBlocks.det_cord), 'W', ModItems.warhead_generic_medium });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.warhead_generic_large, 1), new Object[] { "PTP", "PTP", "TTT", 'P', "plateTitanium", 'T', Item.getItemFromBlock(Blocks.tnt) }));
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_incendiary_large, 1), new Object[] { "PXP", "XWX", "PXP", 'P', ModItems.powder_fire, 'W', ModItems.warhead_generic_large, 'X', Items.lava_bucket });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_cluster_large, 1), new Object[] { "PXP", "XWX", "PXP", 'P', ModItems.pellet_cluster, 'W', ModItems.warhead_generic_large, 'X', Item.getItemFromBlock(ModBlocks.det_cord) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_buster_large, 1), new Object[] { "PXP", "XWX", "PXP", 'P', Item.getItemFromBlock(ModBlocks.det_cord), 'W', ModItems.warhead_generic_large, 'X', ModBlocks.det_charge });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_nuclear, 1), new Object[] { " N ", "TZT", "TBT", 'N', ModItems.boy_shielding, 'Z', ModItems.boy_target, 'B', ModItems.boy_bullet, 'T', ModItems.plate_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_mirvlet, 1), new Object[] { " S ", "SPS", "STS", 'S', ModItems.plate_steel, 'P', ModItems.ingot_pu239, 'T', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_mirv, 1), new Object[] { "MMM", "MWM", "MMM", 'M', ModItems.warhead_mirvlet, 'W', ModItems.warhead_generic_large });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_thermo_endo, 1), new Object[] { " T ", "TBT", "TBT", 'T', ModItems.plate_titanium, 'B', Item.getItemFromBlock(ModBlocks.therm_endo) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_thermo_exo, 1), new Object[] { " T ", "TBT", "TBT", 'T', ModItems.plate_titanium, 'B', Item.getItemFromBlock(ModBlocks.therm_exo) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fuel_tank_small, 1), new Object[] { "PTP", "PTP", "PTP", 'P', "plateTitanium", 'T', ModItems.canister_kerosene }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fuel_tank_medium, 1), new Object[] { "PTP", "PTP", "PTP", 'P', "plateTitanium", 'T', ModItems.fuel_tank_small }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fuel_tank_large, 1), new Object[] { "PTP", "PTP", "PTP", 'P', "plateTitanium", 'T', ModItems.fuel_tank_medium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.thruster_small, 1), new Object[] { "AS ", "AH ", " L ", 'A', ModItems.wire_aluminium, 'S', "plateSteel", 'H', ModItems.hull_small_steel, 'L', ModItems.hull_small_steel }));
		GameRegistry.addRecipe(new ItemStack(ModItems.thruster_medium, 1), new Object[] { "AS ", "AH ", " L ", 'A', ModItems.wire_copper, 'S', ModItems.thruster_small, 'H', ModItems.hull_small_steel, 'L', ModItems.hull_big_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.thruster_large, 1), new Object[] { "AS ", "AH ", " L ", 'A', ModItems.wire_red_copper, 'S', ModItems.thruster_medium, 'H', ModItems.hull_big_steel, 'L', ModItems.hull_big_steel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.lemon, 1), new Object[] { " D ", "DSD", " D ", 'D', new ItemStack(Items.dye, 1, 11), 'S', "stone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.chopper_blades, 1), new Object[] { "CCC", "SIS", " I ", 'C', ModItems.plate_combine_steel, 'S', "plateSteel", 'I', ModItems.ingot_combine_steel }));
		GameRegistry.addRecipe(new ItemStack(ModItems.chopper_gun, 1), new Object[] { " PI", "WWC", " PM", 'P', ModItems.plate_combine_steel, 'W', ModItems.wire_magnetized_tungsten, 'I', ModItems.ingot_combine_steel, 'C', ModItems.coil_magnetized_tungsten, 'M', ModItems.motor });
		GameRegistry.addRecipe(new ItemStack(ModItems.chopper_head, 1), new Object[] { " GI", "WIB", "WCB", 'G', ModBlocks.reinforced_glass, 'W', ModItems.wire_magnetized_tungsten, 'I', ModItems.ingot_combine_steel, 'C', ModBlocks.fwatz_computer, 'B', ModBlocks.block_combine_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.chopper_tail, 1), new Object[] { "PPP", "IIM", " PR", 'P', ModItems.plate_combine_steel, 'I', ModItems.ingot_combine_steel, 'M', ModItems.motor, 'R', ModItems.chopper_blades });
		GameRegistry.addRecipe(new ItemStack(ModItems.chopper_torso, 1), new Object[] { "IMI", "MBB", "RCI", 'P', ModItems.plate_combine_steel, 'I', ModItems.ingot_combine_steel, 'M', ModItems.motor, 'B', ModBlocks.block_combine_steel, 'R', ModItems.chopper_blades, 'C', ModBlocks.fwatz_computer });
		GameRegistry.addRecipe(new ItemStack(ModItems.chopper_wing, 1), new Object[] { "III", " PP", " PP", 'P', ModItems.plate_combine_steel, 'I', ModItems.ingot_combine_steel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.telepad, 1), new Object[] { "CPC", "SUS", "WWW", 'C', ModItems.plate_combine_steel, 'P', ModItems.plate_schrabidium, 'S', "plateSteel", 'U', ModItems.circuit_schrabidium, 'W', ModItems.wire_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.entanglement_kit, 1), new Object[] { "CEC", "PDP", "CSC", 'C', ModItems.coil_magnetized_tungsten, 'P', "plateDenseLead", 'S', ModItems.singularity_super_heated, 'E', ModItems.singularity_counter_resonant, 'D', ModItems.powder_power }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.entanglement_kit, 1), new Object[] { "CSC", "PDP", "CEC", 'C', ModItems.coil_magnetized_tungsten, 'P', "plateDenseLead", 'S', ModItems.singularity_super_heated, 'E', ModItems.singularity_counter_resonant, 'D', ModItems.powder_power }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blade_titanium, 2), new Object[] { "TP", "TP", "TT", 'P', "plateTitanium", 'T', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turbine_titanium, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_titanium, 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.rotor_steel, 3), new Object[] { "CCC", "SSS", "CCC", 'C', ModItems.coil_gold, 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.generator_steel, 1), new Object[] { "RRR", "CCC", "SSS", 'C', ModItems.coil_gold_torus, 'S', "ingotSteel", 'R', ModItems.rotor_steel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.generator_front, 1), new Object[] { "PSP", "TBT", "TWT", 'P', "plateSteel", 'S', "ingotSteel", 'T', ModItems.tank_steel, 'B', ModItems.turbine_titanium, 'W', ModBlocks.red_wire_coated }));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.toothpicks, 3), new Object[] { Items.stick, Items.stick, Items.stick });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.ducttape, 6), new Object[] { "FSF", "SPS", "FSF", 'F', Items.string, 'S', Items.slime_ball, 'P', Items.paper });

		GameRegistry.addRecipe(new ItemStack(ModItems.missile_generic, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_generic_small, 'T', ModItems.fuel_tank_small, 'M', ModItems.thruster_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_incendiary, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_incendiary_small, 'T', ModItems.fuel_tank_small, 'M', ModItems.thruster_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_cluster, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_cluster_small, 'T', ModItems.fuel_tank_small, 'M', ModItems.thruster_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_buster, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_buster_small, 'T', ModItems.fuel_tank_small, 'M', ModItems.thruster_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_strong, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_generic_medium, 'T', ModItems.fuel_tank_medium, 'M', ModItems.thruster_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_incendiary_strong, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_incendiary_medium, 'T', ModItems.fuel_tank_medium, 'M', ModItems.thruster_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_cluster_strong, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_cluster_medium, 'T', ModItems.fuel_tank_medium, 'M', ModItems.thruster_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_buster_strong, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_buster_medium, 'T', ModItems.fuel_tank_medium, 'M', ModItems.thruster_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_burst, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_generic_large, 'T', ModItems.fuel_tank_large, 'M', ModItems.thruster_large });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_inferno, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_incendiary_large, 'T', ModItems.fuel_tank_large, 'M', ModItems.thruster_large });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_rain, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_cluster_large, 'T', ModItems.fuel_tank_large, 'M', ModItems.thruster_large });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_drill, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_buster_large, 'T', ModItems.fuel_tank_large, 'M', ModItems.thruster_large });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_nuclear, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_nuclear, 'T', ModItems.fuel_tank_large, 'M', ModItems.thruster_large });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_nuclear_cluster, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_mirv, 'T', ModItems.fuel_tank_large, 'M', ModItems.thruster_large });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_endo, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_thermo_endo, 'T', ModItems.fuel_tank_large, 'M', ModItems.thruster_large });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_exo, 1), new Object[] { "W", "T", "M", 'W', ModItems.warhead_thermo_exo, 'T', ModItems.fuel_tank_large, 'M', ModItems.thruster_large });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 1), new Object[] { "AHA", "TCT", "TPT", 'T', "plateTitanium", 'A', "plateAluminum", 'S', "plateSteel", 'C', "ingotCopper", 'P', Item.getItemFromBlock(Blocks.piston), 'H', Item.getItemFromBlock(Blocks.hopper) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_centrifuge), 1), new Object[] { " T ", "RDR", "RSR", 'S', "plateSteel", 'T', ModItems.centrifuge_tower, 'W', ModItems.coil_tungsten, 'R', ModItems.coil_copper, 'D', Item.getItemFromBlock(ModBlocks.machine_difurnace_off) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', "plateTitanium", 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', "ingotRedstoneAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', "plateSteel", 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', "ingotRedstoneAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_reactor), 1), new Object[] { "LSL", "SCS", "LSL", 'S', "ingotSteel", 'L', "ingotLead", 'C', ModItems.reactor_core }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 1), new Object[] { "SSS", "SFS", "CCC", 'S', "plateSteel", 'C', "plateCopper", 'F', Item.getItemFromBlock(Blocks.furnace) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_rtg_furnace_off), 1), new Object[] { "NNN", "NFN", "UUU", 'N', "plateDenseLead", 'U', ModItems.rtg_unit, 'F', Item.getItemFromBlock(Blocks.furnace) }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 1), new Object[] { "BBB", "WFW", "RRR", 'B', ModItems.ingot_beryllium, 'R', ModItems.coil_tungsten, 'W', ModItems.wire_red_copper, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_generator), 1), new Object[] { "SLS", "LCL", "SLS", 'C', ModItems.circuit_red_copper, 'L', ModItems.rod_quad_lead, 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_industrial_generator), 1), new Object[] { "PPP", "FGG", "WSS", 'P', ModItems.board_copper, 'F', ModItems.generator_front, 'G', ModItems.generator_steel, 'W', ModBlocks.red_wire_coated, 'S', ModItems.pedestal_steel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 16), new Object[] { "WRW", "RIR", "WRW", 'W', "ingotTungsten", 'I', "ingotRedstoneAlloy", 'R', ModItems.wire_red_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_cable), 16), new Object[] { "WRW", "RIR", "WRW", 'W', "plateSteel", 'I', "ingotRedstoneAlloy", 'R', ModItems.wire_red_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_pylon), 4), new Object[] { "CWC", "PWP", " T ", 'C', ModItems.coil_copper_torus, 'W', "plankWood", 'P', "plateSteel", 'T', ModBlocks.red_wire_coated }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.oil_duct_solid), 16), new Object[] { "SPS", "P P", "SPS", 'S', "ingotSteel", 'P', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.oil_duct), 16), new Object[] { "SIS", "   ", "SIS", 'S', "plateSteel", 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.gas_duct_solid), 16), new Object[] { "SPS", "P P", "SPS", 'S', "ingotSteel", 'P', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.gas_duct), 16), new Object[] { "SIS", "   ", "SIS", 'S', "plateSteel", 'I', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_deuterium), 1), new Object[] { "TIT", "RFR", "CCC", 'T', ModItems.tank_steel, 'I', "ingotTitanium", 'R', ModItems.wire_red_copper, 'F', Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 'C', ModItems.coil_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_battery), 1), new Object[] { "TST", "RIR", "TLT", 'T', "ingotTungsten", 'I', "ingotRedstoneAlloy", 'R', ModItems.wire_red_copper, 'S', "blockSulfur", 'L', "blockLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_battery), 1), new Object[] { "TLT", "RIR", "TST", 'T', "ingotTungsten", 'I', "ingotRedstoneAlloy", 'R', ModItems.wire_red_copper, 'S', "blockSulfur", 'L', "blockLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_coal_off), 1), new Object[] { "STS", "SCS", "SFS", 'S', "ingotSteel", 'T', ModItems.tank_steel, 'C', "ingotRedstoneAlloy", 'F', Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_converter_he_rf), 1), new Object[] { "SSS", "CRC", "SSS", 'S', "ingotSteel", 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_converter_rf_he), 1), new Object[] { "SSS", "CRC", "SSS", 'S', ModItems.ingot_beryllium, 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_schrabidium_transmutator), 1), new Object[] { "TST", "ARA", "BBB", 'S', ModItems.nugget_schrabidium , 'T', "ingotTitanium", 'A', Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 'R', Item.getItemFromBlock(ModBlocks.machine_reactor), 'B', Item.getItemFromBlock(ModBlocks.machine_battery) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_diesel), 1), new Object[] { "HTH", "PRP", "SFS", 'S', "ingotSteel", 'T', ModItems.tank_steel, 'H', ModItems.hull_small_steel, 'P', Item.getItemFromBlock(Blocks.piston), 'R', "ingotRedstoneAlloy", 'F', Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off) }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_shredder), 1), new Object[] { "SHS", "MBM", "CFC", 'H', ModItems.hull_big_steel, 'S', ModBlocks.steel_beam, 'M', ModItems.motor, 'B', Blocks.iron_bars, 'C', ModBlocks.red_wire_coated, 'F', Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_combine_factory), 1), new Object[] { "HCH", "STS", "RFR", 'H', ModItems.tank_steel, 'C', ModItems.coil_advanced_torus, 'S', "plateSteel", 'T', ModBlocks.block_magnetized_tungsten, 'R', ModItems.wire_red_copper, 'F', Item.getItemFromBlock(ModBlocks.machine_difurnace_off) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_teleporter), 1), new Object[] { "PTP", "FKF", "BBB", 'P', "plateTitanium", 'T', ModItems.telepad, 'F', Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 'K', ModItems.entanglement_kit, 'B', Item.getItemFromBlock(ModBlocks.machine_battery) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_rtg_grey), 1), new Object[] { "UPU", "UWU", "UPU", 'P', "plateSteel", 'U', ModItems.rtg_unit, 'W', ModBlocks.red_wire_coated }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_cyclotron), 1), new Object[] { "PTP", "PCP", "WBW", 'P', ModItems.board_copper, 'T', ModItems.cyclotron_tower, 'C', ModBlocks.fusion_core, 'W', ModBlocks.red_wire_coated, 'B', ModBlocks.machine_battery });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_well), 1), new Object[] { "TPM", "GPG", "GDG", 'T', ModItems.tank_steel, 'P', ModItems.pipes_steel, 'M', ModItems.motor, 'G', ModBlocks.steel_scaffold, 'D', ModItems.drill_titanium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_flare), 1), new Object[] { "UHU", "GPG", "GTG", 'U', ModItems.thermo_element, 'H', ModItems.hull_small_steel, 'G', ModBlocks.steel_scaffold, 'P', ModItems.pipes_steel, 'T', ModItems.tank_steel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_refinery), 1), new Object[] { "PTP", "CTC", "WFW", 'P', "plateTitanium", 'T', ModItems.tank_steel, 'C', ModItems.coil_tungsten, 'W', ModBlocks.red_wire_coated, 'F', ModBlocks.machine_electric_furnace_off }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 1), new Object[] { "PIP", "I I", "PIP", 'P', "plateTitanium", 'I', "ingotTitanium" }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_furnace), 1), new Object[] { "HMH", "MFM", "HMH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'M', ModItems.motor, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_conductor), 1), new Object[] { "SWS", "FFF", "SWS", 'S', "ingotTitanium", 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'F', ModItems.fuse }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_core), 1), new Object[] { "HPH", "PCP", "HPH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'C', ModItems.circuit_aluminium, 'P', Item.getItemFromBlock(Blocks.piston) });
		GameRegistry.addRecipe(ItemBattery.getEmptyBattery(ModItems.factory_core_titanium), new Object[] { "BRB", "RHR", "BRB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic), 'R', Item.getItemFromBlock(Blocks.redstone_block), 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 1), new Object[] { "PIP", "I I", "PIP", 'P', ModItems.plate_advanced_alloy, 'I', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_furnace), 1), new Object[] { "HMH", "MFM", "HMH", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 'M', ModItems.motor, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_conductor), 1), new Object[] { "SWS", "FFF", "SWS", 'S', ModItems.ingot_advanced_alloy, 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'F', ModItems.fuse });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_core), 1), new Object[] { "HPH", "PCP", "HPH", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 'C', ModItems.circuit_red_copper, 'P', Item.getItemFromBlock(Blocks.piston) });
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.factory_core_advanced), new Object[] { "BLB", "SHS", "BLB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced), 'S', "blockSulfur", 'L', "blockLead", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.factory_core_advanced), new Object[] { "BSB", "LHL", "BSB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced), 'S', "blockSulfur", 'L', "blockLead", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fuse, 1), new Object[] { " S ", "GAG", " S ", 'S', "plateSteel", 'G', Item.getItemFromBlock(Blocks.glass_pane), 'A', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.redcoil_capacitor, 1), new Object[] { "PFP", "CBC", "CBC", 'P', "plateGold", 'B', Item.getItemFromBlock(Blocks.redstone_block), 'C', ModItems.coil_advanced_alloy, 'F', ModItems.fuse }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.titanium_filter, 1), new Object[] { "PFP", "CBC", "CBC", 'P', "plateLead", 'B', "U238", 'C', "plateTitanium", 'F', ModItems.fuse }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.detonator, 1), new Object[] { " W", "SC", "CE", 'S', "plateSteel", 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'E', "ingotSteel" }));

		GameRegistry.addRecipe(new ItemStack(ModItems.singularity, 1), new Object[] { "ESE", "SBS", "ESE", 'E', new ItemStack(ModItems.nugget_euphemium, 1, 34), 'S', ModItems.cell_anti_schrabidium, 'B', Item.getItemFromBlock(ModBlocks.block_schrabidium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.singularity_counter_resonant, 1), new Object[] { "CTC", "TST", "CTC", 'C', ModItems.plate_combine_steel, 'T', ModItems.ingot_magnetized_tungsten, 'S', ModItems.singularity });
		GameRegistry.addRecipe(new ItemStack(ModItems.singularity_super_heated, 1), new Object[] { "CTC", "TST", "CTC", 'C', ModItems.plate_advanced_alloy, 'T', ModItems.powder_power, 'S', ModItems.singularity });
		GameRegistry.addRecipe(new ItemStack(ModItems.black_hole, 1), new Object[] { "SSS", "SCS", "SSS", 'C', ModItems.singularity, 'S', ModItems.crystal_xen });
		GameRegistry.addRecipe(new ItemStack(ModItems.crystal_xen, 1), new Object[] { "EEE", "EIE", "EEE", 'E', ModItems.powder_power, 'I', new ItemStack(ModItems.ingot_euphemium, 1, 34) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.screwdriver, 1), new Object[] { "  I", " I ", "S  ", 'S', "ingotSteel", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.screwdriver, "dustNeptunium", ModItems.powder_iodine, ModItems.powder_thorium, ModItems.powder_astatine, ModItems.powder_neodymium, ModItems.board_copper, ModItems.black_hole, ModItems.powder_caesium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.screwdriver, ModItems.powder_strontium, ModItems.powder_bromine, ModItems.powder_cobalt, ModItems.powder_tennessine, ModItems.powder_niobium, ModItems.board_copper, ModItems.black_hole, ModItems.powder_cerium }));
		GameRegistry.addRecipe(new ItemStack(ModItems.crystal_energy, 1), new Object[] { "EEE", "EGE", "EEE", 'E', ModItems.powder_power, 'G', Items.glowstone_dust });
		GameRegistry.addRecipe(new ItemStack(ModItems.pellet_coolant, 1), new Object[] { "CRC", "RBR", "CRC", 'C', ModItems.powder_ice, 'R', ModItems.rod_quad_coolant, 'B', ModBlocks.block_niter });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.thermo_element, 1), new Object[] { "GRG", "APA", "GRG", 'P', "plateSteel", 'G', Items.gold_nugget, 'R', ModItems.wire_red_copper, 'A', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.limiter, 1), new Object[] { "SC", "SC", "LS", 'S', "plateSteel", 'C', ModItems.circuit_copper, 'L', "plateLead" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_aluminium, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateAluminum", 'I', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_gold, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateGold", 'I', "ingotGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_iron, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateIron", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_titanium, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateTitanium", 'I', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateAdvanced", 'I', "ingotAdvanced" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_combine_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateCMBSteel", 'I', "ingotCMBSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_schrabidium, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateSchrabidium", 'I', "ingotSchrabidium" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reactor_element), 1), new Object[] { "SCS", "CSC", "SCS", 'S', "ingotSteel", 'C', ModItems.rod_quad_empty }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reactor_control), 1), new Object[] { "SLS", "SLS", "SLS", 'S', "ingotSteel", 'L', "ingotLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reactor_conductor), 1), new Object[] { "SWS", "FFF", "SWS", 'S', "ingotSteel", 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'F', ModItems.fuse }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reactor_computer), 1), new Object[] { "CWC", "CRC", "CWC", 'C', ModItems.circuit_red_copper, 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'R', Item.getItemFromBlock(ModBlocks.reactor_conductor) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reactor_hatch), 1), new Object[] { "BBB", "BFB", "BBB", 'B', Item.getItemFromBlock(ModBlocks.brick_concrete), 'F', Item.getItemFromBlock(Blocks.furnace) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fusion_conductor), 1), new Object[] { "SSS", "CCC", "SSS", 'S', "plateSteel", 'C', ModItems.coil_advanced_alloy }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fusion_center), 1), new Object[] { "TMT", "TWT", "TMT", 'T', "ingotTungsten", 'M', Item.getItemFromBlock(ModBlocks.fusion_conductor), 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fusion_motor), 1), new Object[] { "MTM", "TTT", "MTM", 'T', "ingotTitanium", 'M', ModItems.motor }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fusion_heater), 1), new Object[] { "TTT", "CCC", "TTT", 'T', "ingotTungsten", 'C', ModItems.magnetron }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fusion_hatch), 1), new Object[] { "TTT", "TFT", "TTT", 'T', Item.getItemFromBlock(ModBlocks.fusion_heater), 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fusion_core), 1), new Object[] { "CWC", "CRC", "CWC", 'C', ModItems.circuit_gold, 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'R', Item.getItemFromBlock(ModBlocks.fusion_center) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.watz_element), 2), new Object[] { "TET", "ERE", "TET", 'T', "ingotTungsten", 'R', Item.getItemFromBlock(ModBlocks.fusion_conductor), 'E', Item.getItemFromBlock(ModBlocks.reactor_element) }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.watz_control), 2), new Object[] { "TMT", "MWM", "TMT", 'T', ModItems.ingot_advanced_alloy, 'M', Item.getItemFromBlock(ModBlocks.reactor_control), 'W', ModItems.coil_copper_torus });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.watz_cooler), 1), new Object[] { "SCS", "CNC", "SCS", 'N', "blockSteel", 'C', ModItems.rod_quad_coolant, 'S', "dustNetherQuartz" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.watz_end), 1), new Object[] { "TST", "SBS", "TST", 'T', "ingotTungsten", 'S', "ingotSteel", 'B', "blockLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.watz_conductor), 1), new Object[] { "TWT", "FNF", "TWT", 'T', "ingotTungsten", 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'F', ModItems.fuse, 'N', "nuggetSchrabidium" }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.watz_hatch), 1), new Object[] { "RRR", "RFR", "RRR", 'R', Item.getItemFromBlock(ModBlocks.reinforced_brick), 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.watz_core), 1), new Object[] { "CWC", "CRC", "CWC", 'C', ModItems.circuit_schrabidium, 'W', Item.getItemFromBlock(ModBlocks.watz_conductor), 'R', Item.getItemFromBlock(ModBlocks.block_meteor) });

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_conductor), 1), new Object[] { "SSS", "CCC", "SSS", 'S', ModItems.plate_combine_steel, 'C', ModItems.coil_magnetized_tungsten });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_cooler), 1), new Object[] { "IPI", "IPI", "IPI", 'I', "ingotTitanium", 'P', "plateTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_tank), 1), new Object[] { "CGC", "GGG", "CGC", 'C', ModItems.plate_combine_steel, 'G', "paneGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_scaffold), 1), new Object[] { "IPI", "P P", "IPI", 'I', "ingotTungsten", 'P', "plateDenseLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_hatch), 1), new Object[] { "SSS", "SFS", "SSS", 'S', ModBlocks.fwatz_scaffold, 'F', Blocks.furnace }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_computer), 1), new Object[] { "DTD", "TMT", "DTD", 'D', "dustDiamond", 'T', "dustMagnetizedTungsten", 'M', ModBlocks.block_meteor }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_core), 1), new Object[] { "CMC", "MAM", "CMC", 'C', ModItems.circuit_schrabidium, 'M', ModBlocks.fwatz_computer, 'A', ModBlocks.fwatz_conductor }));

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.asphalt), 8), new Object[] { "BGB", "GSG", "BGB", 'B', ModItems.canister_smear, 'G', Blocks.gravel, 'S', Blocks.sand });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_light), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.fence, 'B', Blocks.brick_block });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_concrete), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.stone });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_obsidian), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.obsidian });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.cmb_brick), 4), new Object[] { "PPP", "PIP", "PPP", 'P', ModItems.plate_combine_steel, 'I', ModItems.ingot_combine_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.cmb_brick_reinforced), 8), new Object[] { "TBT", "BCB", "TBT", 'T', ModBlocks.block_magnetized_tungsten, 'B', ModBlocks.brick_concrete, 'C', ModBlocks.cmb_brick });

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_brick), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', ModBlocks.brick_concrete });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_glass), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.glass });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_light), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_lamp_off), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.redstone_lamp });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_sand), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.sandstone });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.tape_recorder), 4), new Object[] { "TST", "SSS", 'T', "ingotTungsten", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_poles), 16), new Object[] { "S S", "SSS", "S S", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_top), 1), new Object[] { "T T", "TRT", "BBB", 'T', "ingotTungsten", 'B', ModItems.ingot_beryllium, 'R', "ingotRedstoneAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 1), new Object[] { "SS ", "SCR", "SS ", 'S', "ingotSteel", 'C', ModItems.circuit_red_copper, 'R', ModItems.wire_red_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_wall), 4), new Object[] { "SSS", "SSS", 'S', "ingotSteel" }));
		GameRegistry.addShapelessRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_corner)), new Object[] { Item.getItemFromBlock(ModBlocks.steel_wall), Item.getItemFromBlock(ModBlocks.steel_wall) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_roof), 2), new Object[] { "SSS", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_scaffold), 8), new Object[] { "SSS", " S ", "SSS", 'S', "ingotSteel" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.seal_frame, 2), new Object[] { "SSS", "WRW", "III", 'S', ModBlocks.steel_roof, 'W', ModItems.wire_aluminium, 'R', "dustRedstone", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.seal_controller, 1), new Object[] { "SSS", "RCR", "III", 'S', ModBlocks.steel_roof, 'C', ModItems.ingot_red_copper, 'R', "dustRedstone", 'I', "ingotSteel" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_rpg, 1), new Object[] { "SSW", " SW", 'S', "plateSteel", 'W', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_rpg_ammo, 8), new Object[] { "SI ", "ITI", " I ", 'S', "plateSteel", 'T', Item.getItemFromBlock(Blocks.tnt), 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver, 1), new Object[] { "SSS", " RW", 'S', "plateSteel", 'W', Item.getItemFromBlock(Blocks.planks), 'R', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_ammo, 16), new Object[] { "L", "S", 'L', "plateLead", 'S', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_iron, 1), new Object[] { "SSS", " RW", 'S', "plateIron", 'W', Item.getItemFromBlock(Blocks.planks), 'R', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_iron_ammo, 16), new Object[] { "L", "S", 'L', "plateIron", 'S', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_gold, 1), new Object[] { "SSS", " RW", 'S', "plateGold", 'W', "ingotGold", 'R', ModItems.wire_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_gold_ammo, 16), new Object[] { "L", "S", 'L', ModItems.plate_gold, 'S', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_lead, 1), new Object[] { "SSS", " RW", 'S', "plateLead", 'W', "ingotTungsten", 'R', ModItems.wire_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_lead_ammo, 16), new Object[] { "L", "S", 'L', "paneGlass", 'S', ModItems.ingot_u235 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_lead_ammo, 16), new Object[] { "L", "S", 'L', "paneGlass", 'S', ModItems.ingot_pu239 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_lead_ammo, 16), new Object[] { "L", "S", 'L', "paneGlass", 'S', ModItems.nuclear_waste }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_lead_ammo, 16), new Object[] { "L", "S", 'L', "paneGlass", 'S', ModItems.trinitite }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_schrabidium, 1), new Object[] { "SSS", " RW", 'S', ModItems.plate_schrabidium, 'W', "ingotTungsten", 'R', ModItems.wire_schrabidium }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_schrabidium_ammo, 16), new Object[] { "L", "S", 'L', ModItems.plate_schrabidium, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_cursed, 1), new Object[] { "TTM", "SRI", 'S', "plateSteel", 'I', "ingotSteel", 'R', ModItems.wire_red_copper, 'T', "plateTitanium", 'M', ModItems.motor }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_cursed_ammo, 32), new Object[] { "L", "L", 'L', "plateSteel", 'S', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_nightmare, 1), new Object[] { "SSE", " RW", 'S', "plateSteel", 'W', Item.getItemFromBlock(Blocks.planks), 'R', ModItems.wire_aluminium, 'E', ModItems.powder_power }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_nightmare_ammo, 16), new Object[] { "L", "S", 'L', "plateDenseLead", 'S', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_nightmare2, 1), new Object[] { "SSS", "RRW", 'S', "plateDenseLead", 'W', "ingotTungsten", 'R', ModItems.wire_gold }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_nightmare2_ammo, 6), new Object[] { "L", "S", 'L', ModItems.powder_power, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_fatman, 1), new Object[] { "SSI", "III", "WPH", 'S', "plateSteel", 'I', "ingotSteel", 'W', ModItems.wire_aluminium, 'H', ModItems.hull_small_steel, 'P', Item.getItemFromBlock(Blocks.piston) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_fatman_ammo, 2), new Object[] { " S ", "SPS", "ITI", 'S', "plateSteel", 'P', ModItems.ingot_pu239, 'T', Item.getItemFromBlock(Blocks.tnt), 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_mirv, 1), new Object[] { "LLL", "WFW", "SSS", 'S', "plateSteel", 'L', "plateLead", 'W', ModItems.wire_gold, 'F', ModItems.gun_fatman }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_mirv_ammo, 1), new Object[] { "NNN", "NSN", "NNN", 'S', ModItems.hull_small_steel, 'N', ModItems.gun_fatman_ammo });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_bf, 1), new Object[] { "LLL", "WFW", "SSS", 'S', ModItems.plate_paa, 'L', "plateDenseLead", 'W', ModItems.wire_advanced_alloy, 'F', ModItems.gun_mirv }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_bf_ammo, 1), new Object[] { "AEA", "SHS", "AEA", 'H', ModItems.hull_small_steel, 'A', ModItems.cell_antimatter, 'S', ModItems.cell_anti_schrabidium, 'E', ModItems.powder_power });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_mp40, 1), new Object[] { "III", " SW", " S ", 'S', "plateSteel", 'I', "ingotSteel", 'W', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_mp40_ammo, 16), new Object[] { "P", "G", 'P', "plateCopper", 'G', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_uboinik, 1), new Object[] { "II ", "SPW", 'P', "plateSteel", 'I', "ingotSteel", 'W', "plankWood", 'S', Items.stick }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_uboinik_ammo, 6), new Object[] { "P", "G", 'P', ModItems.pellet_buckshot, 'G', Items.gunpowder });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456, 1), new Object[] { "PBB", "ACC", "PRY", 'P', "plateSteel", 'R', ModItems.redcoil_capacitor, 'A', ModItems.coil_advanced_alloy, 'B', ModItems.battery_generic, 'C', ModItems.coil_advanced_torus, 'Y', ModItems.circuit_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 64), new Object[] { "SSS", "SRS", "SSS", 'S', "plateSteel", 'R', ModItems.rod_quad_uranium_fuel_depleted }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 32), new Object[] { " S ", "SRS", " S ", 'S', "plateSteel", 'R', ModItems.rod_dual_uranium_fuel_depleted }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { " S ", " R ", " S ", 'S', "plateSteel", 'R', ModItems.rod_uranium_fuel_depleted }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', "plateSteel", 'R', ModItems.rod_uranium_fuel_depleted }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { " S ", " R ", " S ", 'S', "plateSteel", 'R', "U238" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', "plateSteel", 'R', "U238" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_osipr, 1), new Object[] { "CCT", "WWI", "MCC", 'C', ModItems.plate_combine_steel, 'T', "ingotTungsten", 'W', ModItems.wire_magnetized_tungsten, 'I', ModItems.ingot_magnetized_tungsten, 'M', ModItems.coil_magnetized_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_osipr_ammo, 16), new Object[] { "G", "R", "S", 'G', "dustGlowstone", 'R', "dustRedstone", 'S', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_osipr_ammo, 16), new Object[] { "R", "G", "S", 'G', "dustGlowstone", 'R', "dustRedstone", 'S', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_osipr_ammo2, 1), new Object[] { " C ", "PPP", " C ", 'C', ModItems.plate_combine_steel, 'P', ModItems.powder_power }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_immolator, 1), new Object[] { "WCC", "PMT", "WAA", 'W', ModItems.wire_gold, 'C', "plateCopper", 'P', "plateAdvanced", 'M', ModItems.motor, 'T', ModItems.tank_steel, 'A', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_immolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', "plateSteel", 'C', "dustCoal", 'P', ModItems.powder_fire }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_immolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', "plateSteel", 'F', ModItems.canister_fuel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_immolator_ammo, 24), new Object[] { " F ", "SFS", " F ", 'S', "plateSteel", 'F', ModItems.canister_napalm }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_cryolator, 1), new Object[] { "SSS", "IWL", "LMI", 'S', "plateSteel", 'I', "plateIron", 'L', Items.leather, 'M', ModItems.motor, 'W', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_cryolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', "plateSteel", 'C', "dustSalpeter", 'P', Items.snowball }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_cryolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', "plateSteel", 'F', ModItems.powder_ice }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_zomg, 1), new Object[] { "PRR", "CCS", "PXX", 'P', ModItems.plate_paa, 'R', "plateDenseLead", 'S', ModItems.singularity_counter_resonant, 'X', ModItems.crystal_xen, 'C', ModItems.coil_magnetized_tungsten}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_mp, 1), new Object[] { "EEE", "SSM", "III", 'E', new ItemStack(ModItems.ingot_euphemium, 1, 34), 'S', "plateSteel", 'I', "ingotSteel", 'M', ModItems.motor}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_mp_ammo, 32), new Object[] { "G", "C", 'G', "plateGold", 'C', "plateCopper", 'S', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_emp, 1), new Object[] { "CPG", "CMF", "CPI", 'C', ModItems.coil_copper, 'P', "plateLead", 'G', ModItems.circuit_gold, 'M', ModItems.magnetron, 'I', "ingotTungsten", 'F', ModItems.fuse }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_emp_ammo, 8), new Object[] { "IGI", "IPI", "IPI", 'G', "plateGold", 'I', "plateIron", 'P', ModItems.powder_power }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_jack, 1), new Object[] { "WW ", "TSD", " TT", 'W', "ingotWeidanium", 'T', ModItems.toothpicks, 'S', ModItems.gun_uboinik, 'D', ModItems.ducttape }));
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.gun_jack_ammo, 3), new Object[] { "PP", "GG", 'G', Items.gunpowder, 'P', ModItems.pellet_buckshot });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.gun_jack_ammo, 1), new Object[] { ModItems.gun_uboinik_ammo, ModItems.gun_uboinik_ammo, ModItems.gun_uboinik_ammo, ModItems.gun_uboinik_ammo });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_euthanasia, 1), new Object[] { "TDT", "AAS", " T ", 'A', "ingotAustralium", 'T', ModItems.toothpicks, 'S', ModItems.gun_mp40, 'D', ModItems.ducttape }));
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.gun_euthanasia_ammo, 12), new Object[] { "P", "S", "N", 'P', ModItems.powder_poison, 'N', ModItems.niter, 'S', ModItems.syringe_metal_empty });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_spark, 1), new Object[] { "TTD", "AAS", "  T", 'A', "ingotDaffergon", 'T', ModItems.toothpicks, 'S', ModItems.gun_rpg, 'D', ModItems.ducttape }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_spark_ammo, 4), new Object[] { "PCP", "DDD", "PCP", 'P', "plateLead", 'C', ModItems.coil_gold, 'D', ModItems.powder_power }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_hp, 1), new Object[] { "TDT", "ASA", " T ", 'A', "ingotReiium", 'T', ModItems.toothpicks, 'S', ModItems.gun_xvl1456, 'D', ModItems.ducttape }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_hp_ammo, 8), new Object[] { " R ", "BSK", " Y ", 'S', "plateSteel", 'K', new ItemStack(Items.dye, 1, 0), 'R', new ItemStack(Items.dye, 1, 1), 'B', new ItemStack(Items.dye, 1, 4), 'Y', new ItemStack(Items.dye, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_defabricator, 1), new Object[] { " SS", "DDD", "TCB", 'S', "plateSteel", 'D', ModItems.plate_dalekanium, 'T', "plateTitanium", 'C', ModItems.circuit_gold, 'B', ModItems.battery_lithium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_defabricator_ammo, 16), new Object[] { "PCP", "DDD", "PCP", 'P', "plateSteel", 'C', ModItems.coil_copper, 'D', "dustLithium" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_generic, 4), new Object[] { "RS ", "ITI", " I ", 'I', "plateIron", 'R', ModItems.wire_red_copper, 'S', "plateSteel", 'T', Item.getItemFromBlock(Blocks.tnt) }));
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_strong, 2), new Object[] { " G ", "SGS", " S ", 'G', ModItems.grenade_generic, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_frag, 2), new Object[] { " G ", "WGW", " K ", 'G', ModItems.grenade_generic, 'W', Item.getItemFromBlock(Blocks.planks), 'K', Item.getItemFromBlock(Blocks.gravel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_fire, 2), new Object[] { " G ", "PFP", " P ", 'G', ModItems.grenade_generic, 'F', ModItems.grenade_frag, 'P', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_shrapnel, 2), new Object[] { " G ", "PFP", " P ", 'G', ModItems.grenade_generic, 'P', ModItems.pellet_buckshot, 'F', ModItems.grenade_strong });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_cluster, 2), new Object[] { " G ", "PFP", " P ", 'G', ModItems.grenade_generic, 'P', ModItems.pellet_cluster, 'F', ModItems.grenade_frag });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_flare, 2), new Object[] { " G ", "DGD", " D ", 'G', ModItems.grenade_generic, 'D', "dustGlowstone" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_electric, 2), new Object[] { " G ", "CSC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.circuit_red_copper, 'S', ModItems.grenade_strong });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_poison, 2), new Object[] { " G ", "PGP", " P ", 'G', ModItems.grenade_generic, 'P', ModItems.powder_poison });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_gas, 2), new Object[] { " G ", "CGC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.pellet_gas });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_schrabidium, 2), new Object[] { " G ", "CFC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.ingot_schrabidium, 'F', ModItems.grenade_flare });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_nuclear, 1), new Object[] {"RS ", "ITI", " I ", 'I', "plateIron", 'R', ModItems.wire_red_copper, 'S', "plateSteel", 'T', ModItems.gun_fatman_ammo }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_plasma, 1), new Object[] {"RS ", "ICI", "TID", 'I', "plateIron", 'R', ModItems.wire_red_copper, 'S', "plateSteel", 'C', ModItems.coil_advanced_torus, 'D', ModItems.cell_deuterium, 'T', ModItems.cell_tritium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_tau, 1), new Object[] {"RS ", "ITI", "UIU", 'I', "plateLead", 'R', ModItems.wire_red_copper, 'S', "plateAdvanced", 'T', ModItems.coil_advanced_torus, 'U', ModItems.gun_xvl1456_ammo }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_zomg, 1), new Object[] {"RC ", "PXP", "NPN", 'P', ModItems.plate_paa, 'R', ModItems.wire_red_copper, 'C', "plateCMBSteel", 'X', ModItems.crystal_xen, 'N', ModItems.powder_power }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_aschrab, 1), new Object[] {"RS ", "ITI", " S ", 'I', "paneGlassColorless", 'R', ModItems.wire_red_copper, 'S', "plateSteel", 'T', ModItems.cell_anti_schrabidium }));
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_mk2, 2), new Object[] { " G ", "SGS", " S ", 'G', ModItems.grenade_strong, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_pulse, 4), new Object[] { "WPW", "WSW", "SMS", 'W', ModItems.wire_red_copper, 'P', "plateIron", 'S', "plateSteel", 'M', ModItems.magnetron }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.grenade_lemon, 1), new Object[] { ModItems.lemon, ModItems.grenade_strong }));
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_black_hole, 1), new Object[] { " C ", "PBP", "PCP", 'C', ModItems.coil_advanced_alloy, 'P', ModItems.ingot_polymer, 'B', ModItems.black_hole });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.gun_moist_nugget, 12), new Object[] { Items.bread, Items.wheat, Items.cooked_chicken, Items.egg });

		GameRegistry.addRecipe(new ItemStack(ModItems.bomb_waffle, 1), new Object[] { "WEW", "MPM", "WEW", 'W', Items.wheat, 'E', Items.egg, 'M', Items.milk_bucket, 'P', ModItems.man_core });
		GameRegistry.addRecipe(new ItemStack(ModItems.schnitzel_vegan, 3), new Object[] { "RWR", "WPW", "RWR", 'W', ModItems.nuclear_waste, 'R', Items.reeds, 'P', Items.pumpkin_seeds });
		GameRegistry.addRecipe(new ItemStack(ModItems.cotton_candy, 2), new Object[] { " S ", "SPS", " H ", 'P', ModItems.nugget_pu239, 'S', Items.sugar, 'H', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', ModItems.nugget_schrabidium, 'A', Items.apple });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 1), new Object[] { "SSS", "SAS", "SSS", 'S', ModItems.ingot_schrabidium, 'A', Items.apple });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 2), new Object[] { "SSS", "SAS", "SSS", 'S', Item.getItemFromBlock(ModBlocks.block_schrabidium), 'A', Items.apple });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 0), new Object[] { Items.gold_nugget, Items.paper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 1), new Object[] { Items.gold_nugget, Items.gold_nugget, Items.gold_nugget, Items.paper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 2), new Object[] { Items.gold_ingot, Items.gold_ingot, Items.gold_nugget, Items.gold_nugget, Items.paper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.glowing_stew, 1), new Object[] { Items.bowl, Item.getItemFromBlock(ModBlocks.mush), Item.getItemFromBlock(ModBlocks.mush) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.can_empty, 1), new Object[] { "P", "P", 'P', "plateAluminum" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_smart, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.niter });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_creature, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.canister_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_redbomb, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.pellet_cluster });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_mrsugar, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.fluorite });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_overcharge, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.sulfur });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bottle_empty, 6), new Object[] { " G ", "G G", "GGG", 'G', "paneGlass" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle_nuka, 1), new Object[] { ModItems.bottle_empty, Items.potionitem, Items.sugar, ModItems.powder_coal });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle_cherry, 1), new Object[] { ModItems.bottle_empty, Items.potionitem, Items.sugar, Items.redstone });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle_quantum, 1), new Object[] { ModItems.bottle_empty, Items.potionitem, Items.sugar, ModItems.trinitite });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bottle2_empty, 6), new Object[] { " G ", "G G", "G G", 'G', "paneGlass" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle2_korl, 1), new Object[] { ModItems.bottle2_empty, Items.potionitem, Items.sugar, ModItems.powder_copper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle2_fritz, 1), new Object[] { ModItems.bottle2_empty, Items.potionitem, Items.sugar, ModItems.powder_tungsten });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle2_korl_special, 1), new Object[] { ModItems.bottle2_empty, Items.potionitem, Items.sugar, ModItems.powder_copper, ModItems.powder_strontium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle2_fritz_special, 1), new Object[] { ModItems.bottle2_empty, Items.potionitem, Items.sugar, ModItems.powder_tungsten, ModItems.powder_thorium });
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_empty, 6), new Object[] { "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.iron_bars), 'C', ModItems.cell_empty, 'P', "plateIron" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.pumpkin_seeds, 'M', Items.milk_bucket });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.pumpkin_seeds, 'M', Items.milk_bucket });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.pumpkin_seeds, 'M', Items.reeds });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.pumpkin_seeds, 'M', Items.reeds });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_poison, 1), new Object[] { "SLS", "LCL", "SLS", 'C', ModItems.syringe_empty, 'S', Items.spider_eye, 'L', "dustLead" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_poison, 1), new Object[] { "SLS", "LCL", "SLS", 'C', ModItems.syringe_empty, 'S', Items.spider_eye, 'L', ModItems.powder_poison });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SPS", "NCN", "SPS", 'C', ModItems.syringe_empty, 'S', "sulfur", 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SNS", "PCP", "SNS", 'C', ModItems.syringe_empty, 'S', "sulfur", 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SPS", "NCN", "SPS", 'C', ModItems.syringe_empty, 'S', "dustSulfur", 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SNS", "PCP", "SNS", 'C', ModItems.syringe_empty, 'S', "dustSulfur", 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_metal_empty, 6), new Object[] { "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.iron_bars), 'C', ModItems.rod_empty, 'P', "plateIron" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_metal_stimpak, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.nether_wart, 'S', ModItems.syringe_metal_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_metal_medx, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.quartz, 'S', ModItems.syringe_metal_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_metal_psycho, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.glowstone_dust, 'S', ModItems.syringe_metal_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.pill_iodine, 8), new Object[] { "IF", 'I', ModItems.powder_iodine, 'F', ModItems.fluorite });
		GameRegistry.addRecipe(new ItemStack(ModItems.plan_c, 1), new Object[] { "PFP", 'P', ModItems.powder_poison, 'F', ModItems.fluorite });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stealth_boy, 1), new Object[] { " B", "LI", "LC", 'B', Item.getItemFromBlock(Blocks.stone_button), 'L', Items.leather, 'I', "ingotSteel", 'C', ModItems.circuit_red_copper }));
		
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_gadget), 1), new Object[] { "DGD", "FCF", "DPD", 'G', ModItems.wire_gold, 'F', ModItems.fins_flat, 'C', ModItems.sphere_steel, 'P', ModItems.pedestal_steel, 'D', new ItemStack(Items.dye, 1, 8) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_boy), 1), new Object[] { "ADD", "HHF", "CDD", 'A', ModItems.wire_aluminium, 'H', ModItems.hull_small_steel, 'C', ModItems.circuit_aluminium, 'F', ModItems.fins_small_steel, 'D', new ItemStack(Items.dye, 1, 4) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_man), 1), new Object[] { "ADD", "SHF", "CDD", 'A', ModItems.wire_aluminium, 'S', ModItems.sphere_steel, 'H', ModItems.hull_big_steel, 'F', ModItems.fins_big_steel, 'C', ModItems.circuit_aluminium, 'D', new ItemStack(Items.dye, 1, 11) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_mike), 1), new Object[] { "DCD", "HSH", "ATA", 'C', ModItems.cap_aluminium, 'H', ModItems.hull_big_aluminium, 'S', ModItems.sphere_steel, 'A', ModItems.circuit_red_copper, 'T', ModItems.tank_steel, 'D', new ItemStack(Items.dye, 1, 7) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_tsar), 1), new Object[] { "CHD", "STF", "CHD", 'C', ModItems.circuit_red_copper, 'H', ModItems.hull_big_titanium, 'S', ModItems.sphere_steel, 'T', ModItems.tank_steel, 'F', ModItems.fins_tri_steel, 'D', new ItemStack(Items.dye, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_fleija), 1), new Object[] { "DGD", "CHF", "DGD", 'G', ModItems.wire_gold, 'C', ModItems.circuit_gold, 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', new ItemStack(Items.dye, 1, 15) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_prototype), 1), new Object[] { "GCG", "HRH", "GCG", 'G', ModItems.wire_gold, 'C', new ItemStack(ModItems.ingot_euphemium, 1, 34), 'H', ModItems.hull_small_steel, 'R', ModItems.dysfunctional_reactor });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_custom), 1), new Object[] { "ADD", "HHF", "CDD", 'A', ModItems.wire_gold, 'H', ModItems.hull_small_steel, 'C', ModItems.circuit_gold, 'F', ModItems.fins_small_steel, 'D', new ItemStack(Items.dye, 1, 8) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.launch_pad), 1), new Object[] { "PPP", "ICI", "CBC", 'P', "plateSteel", 'I', "ingotSteel", 'C', ModItems.circuit_gold, 'B', Item.getItemFromBlock(ModBlocks.machine_battery) }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.book_guide), 1), new Object[] { "IBI", "LBL", "IBI", 'B', Items.book, 'I', new ItemStack(Items.dye, 1, 0), 'L', new ItemStack(Items.dye, 1, 4) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.rail_highspeed), 16), new Object[] { "S S", "SIS", "S S", 'S', "ingotSteel", 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.rail_booster), 6), new Object[] { "S S", "CIC", "SRS", 'S', "ingotSteel", 'I', "plateIron", 'R', "ingotRedstoneAlloy", 'C', ModItems.coil_copper }));

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.bomb_multi), 1), new Object[] { "AAD", "CHF", "AAD", 'A', ModItems.wire_aluminium, 'C', ModItems.circuit_aluminium, 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', new ItemStack(Items.dye, 1, 15) });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_cluster, 8), new Object[] { "plateIron", Item.getItemFromBlock(Blocks.tnt), "plateSteel" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_fire, 4), new Object[] { Items.blaze_powder, "dustSulfur", "dustRedstone" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_ice, 4), new Object[] { Items.snowball, "dustNiter", "dustRedstone" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_poison, 4), new Object[] { Items.spider_eye, "dustRedstone", "gemQuartz" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_gas, 2), new Object[] { Items.water_bucket, "dustGlowstone", "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.magnetron, 2), new Object[] { "PWP", "ITI", "PWP", 'P', "plateAdvanced", 'I', "ingotAdvanced", 'W', ModItems.wire_tungsten, 'T', ModItems.coil_tungsten }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_buckshot, 2), new Object[] { "nuggetLead", "nuggetLead", "nuggetLead" }));

		GameRegistry.addRecipe(new ItemStack(ModItems.flame_pony, 1), new Object[] { " O ", "DPD", " O ", 'D', new ItemStack(Items.dye, 1, 11), 'O', new ItemStack(Items.dye, 1, 9), 'P', Items.paper });
		GameRegistry.addRecipe(new ItemStack(ModItems.flame_conspiracy, 1), new Object[] { " S ", "STS", " S ", 'S', ModItems.canister_fuel, 'T', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.flame_politics, 1), new Object[] { " I ", "IPI", " I ", 'P', Items.paper, 'I', new ItemStack(Items.dye, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(ModItems.flame_opinion, 1), new Object[] { " R ", "RPR", " R ", 'P', Items.paper, 'R', new ItemStack(Items.dye, 1, 1) });
		
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.flame_war), 1), new Object[] { "WHW", "CTP", "WOW", 'W', Item.getItemFromBlock(Blocks.planks), 'T', Item.getItemFromBlock(Blocks.tnt), 'H', ModItems.flame_pony, 'C', ModItems.flame_conspiracy, 'P', ModItems.flame_politics, 'O', ModItems.flame_opinion });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.float_bomb), 1), new Object[] { "TGT", "TUT", "TGT", 'T', "plateTitanium", 'U', ModItems.levitation_unit, 'G', ModItems.circuit_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.therm_endo), 1), new Object[] { "TGT", "TUT", "TGT", 'T', "plateTitanium", 'U', ModItems.thermo_unit_endo, 'G', ModItems.circuit_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.therm_exo), 1), new Object[] { "TGT", "TUT", "TGT", 'T', "plateTitanium", 'U', ModItems.thermo_unit_exo, 'G', ModItems.circuit_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.det_cord), 8), new Object[] { "TNT", "NGN", "TNT", 'T', "plateIron", 'N', "dustNiter", 'G', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.det_charge), 1), new Object[] { "PDP", "DTD", "PDP", 'P', "plateSteel", 'D', ModBlocks.det_cord, 'T', Blocks.tnt }));

		GameRegistry.addRecipe(new ItemStack(ModItems.gadget_core, 1), new Object[] { "PPP", "PUP", "PPP", 'P', ModItems.nugget_pu239, 'U', ModItems.nugget_u238 });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gadget_explosive, 16), new Object[] { "ATP", "ATW", "ATP", 'P', "plateTitanium", 'A', "plateAluminum", 'T', Item.getItemFromBlock(Blocks.tnt), 'W', ModItems.wire_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gadget_explosive8, 1), new Object[] { "EEE", "EPE", "EEE", 'E', ModItems.gadget_explosive, 'P', "plateAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gadget_wireing, 1), new Object[] { "WWW", "WSW", "WWW", 'W', ModItems.wire_gold, 'S', "plateTitanium" }));
		
		GameRegistry.addRecipe(new ItemStack(ModItems.boy_bullet, 1), new Object[] { "##", '#', ModItems.nugget_u235 });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.boy_igniter, 1), new Object[] { " AA", "WWS", " AA", 'A', "plateAluminum", 'W', ModItems.wire_red_copper, 'S', ModItems.circuit_aluminium }));
		GameRegistry.addRecipe(new ItemStack(ModItems.boy_propellant, 1), new Object[] { "TTT", "PPW", "TTT", 'T', ModItems.plate_titanium, 'W', ModItems.wire_red_copper, 'P', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.boy_shielding, 1), new Object[] { "## ", "# #", "## ", '#', "plateDenseLead" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.boy_target, 1), new Object[] { "###", "#  ", "###", '#', ModItems.nugget_u235 });
		
		GameRegistry.addRecipe(new ItemStack(ModItems.man_core, 1), new Object[] { "PPP", "PBP", "PPP", 'P', ModItems.nugget_pu239, 'B', ModItems.nugget_beryllium });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.man_explosive, 16), new Object[] { "UTP", "UTW", "UTP", 'P', "plateTitanium", 'U', ModItems.nugget_u238, 'T', Item.getItemFromBlock(Blocks.tnt), 'W', ModItems.wire_red_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.man_explosive8, 1), new Object[] { "EEE", "ESE", "EEE", 'E', ModItems.man_explosive, 'S', "plateSteel" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.man_igniter, 1), new Object[] { " S ", "WWW", 'W', ModItems.wire_red_copper, 'S', ModItems.circuit_aluminium });

		GameRegistry.addRecipe(new ItemStack(ModItems.mike_core, 1), new Object[] { "UPU", "UPU", "UPU", 'U', ModItems.nugget_u238, 'P', ModItems.nugget_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.mike_deut, 1), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.cell_deuterium, 'T', ModItems.tank_steel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mike_cooling_unit, 1), new Object[] { "WSC", "WMC", "WAC", 'W', ModItems.coil_tungsten, 'C', ModItems.coil_copper, 'S', "plateSteel", 'M', ModItems.motor, 'A', ModItems.circuit_copper }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fleija_igniter, 1), new Object[] { " TT", "TSW", " TT", 'T', "plateTitanium", 'S', ModItems.circuit_schrabidium, 'W', ModItems.wire_schrabidium }));
		GameRegistry.addRecipe(new ItemStack(ModItems.fleija_propellant, 1), new Object[] { "PPP", "TST", "PPP", 'P', ModItems.plate_schrabidium, 'S', ModItems.ingot_schrabidium, 'T', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.fleija_core, 1), new Object[] { "NUU", "BHW", "NUU", 'N', ModItems.nugget_neptunium, 'B', ModItems.nugget_beryllium, 'U', ModItems.nugget_u235, 'H', ModItems.coil_copper, 'W', ModItems.wire_red_copper });

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_generic), new Object[] { " A ", "PRP", "PRP", 'A', ModItems.wire_aluminium, 'P', "plateAluminum", 'R', Items.redstone }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "sulfur", 'L', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "sulfur", 'L', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "dustSulfur", 'L', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "dustSulfur", 'L', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PSP", "PLP", 'A', ModItems.wire_gold, 'P', "plateTitanium", 'S', "dustLithium", 'L', ModItems.powder_cobalt }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PLP", "PSP", 'A', ModItems.wire_gold, 'P', "plateTitanium", 'S', "dustLithium", 'L', ModItems.powder_cobalt }));
		GameRegistry.addRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PNP", "PSP", 'A', ModItems.wire_schrabidium, 'P', ModItems.plate_schrabidium, 'S', ModItems.powder_schrabidium, 'N', ModItems.powder_neptunium });
		GameRegistry.addRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PSP", "PNP", 'A', ModItems.wire_schrabidium, 'P', ModItems.plate_schrabidium, 'S', ModItems.powder_schrabidium, 'N', ModItems.powder_neptunium });
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.energy_core), new Object[] { "PCW", "TRD", "PCW", 'P', ModItems.plate_advanced_alloy, 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', "ingotTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.energy_core), new Object[] { "PCW", "TDR", "PCW", 'P', ModItems.plate_advanced_alloy, 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', "ingotTungsten" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_red_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_aluminium, 'P', "plateAluminum", 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_red_copper, 'P', "plateCopper", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_gold, 'P', "plateTitanium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6), new Object[] { "BBB", "WPW", "BBB", 'W', ModItems.wire_aluminium, 'P', "plateAluminum", 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_red_copper, 'P', "plateCopper", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_gold, 'P', "plateTitanium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_24), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_aluminium, 'P', "plateAluminum", 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_12), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_red_copper, 'P', "plateCopper", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_gold, 'P', "plateTitanium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_4), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2) }));

		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.steel_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.steel_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.steel_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.steel_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.steel_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.steel_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.steel_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.steel_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.steel_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.titanium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.titanium_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.titanium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.titanium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.titanium_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_titanium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.titanium_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_titanium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.titanium_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_titanium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.titanium_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_titanium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.titanium_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_titanium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.alloy_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(ModItems.alloy_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(ModItems.alloy_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(ModItems.alloy_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(ModItems.alloy_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_advanced_alloy, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.alloy_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_advanced_alloy, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.alloy_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_advanced_alloy, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.alloy_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_advanced_alloy, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.alloy_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_advanced_alloy, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.cmb_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_combine_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.cmb_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_combine_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.cmb_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_combine_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.cmb_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_combine_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.cmb_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_combine_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.cmb_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_combine_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.cmb_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_combine_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.cmb_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_combine_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.cmb_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_combine_steel, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.elec_sword, 1), new Object[] { "RPR", "RPR", " B ", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_generic });
		GameRegistry.addRecipe(new ItemStack(ModItems.elec_pickaxe, 1), new Object[] { "RDM", " PB", " P ", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_generic });
		GameRegistry.addRecipe(new ItemStack(ModItems.elec_axe, 1), new Object[] { " DP", "RRM", " PB", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_generic });
		GameRegistry.addRecipe(new ItemStack(ModItems.elec_shovel, 1), new Object[] { "  P", "RRM", "  B", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_generic });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.chainsaw, 1), new Object[] { "TTG", "SSM", 'T', "plateIron", 'S', "plateSteel", 'M', ModItems.motor, 'G', ModItems.circuit_gold }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wiring_red_copper, 1), new Object[] { "PPP", "PIP", "PPP", 'P', "plateSteel", 'I', "ingotSteel" }));

		ItemStack multitool = new ItemStack(ModItems.multitool_dig, 1);
		multitool.addEnchantment(Enchantment.looting, 3);
		multitool.addEnchantment(Enchantment.fortune, 3);
		GameRegistry.addShapedRecipe((multitool), new Object[] { "R#V", "W+U", "A@D", 'R', ModItems.rod_reiium, 'W', ModItems.rod_weidanium, 'A', ModItems.rod_australium, 'V', ModItems.rod_verticium, 'U', ModItems.rod_unobtainium, 'D', ModItems.rod_daffergon, '#', ModBlocks.steel_scaffold, '+', ModItems.circuit_gold, '@', ModItems.ducttape });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tank_waste, 1), new Object[] { "PTP", "PTP", "PTP", 'T', ModItems.tank_steel, 'P', "plateSteel" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet, 1), new Object[] { "EEE", "EIE", "FPF", 'E', ModItems.hazmat_cloth, 'I', "paneGlass", 'P', "plateSteel", 'F', ModItems.filter_coal }));
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.asbestos_helmet, 1), new Object[] { "EEE", "EIE", 'E', ModItems.asbestos_cloth, 'I', "plateGold" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.asbestos_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.asbestos_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.asbestos_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.asbestos_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.asbestos_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.asbestos_cloth });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_paa_helmet, 1), new Object[] { "EEE", "IEI", "FPF", 'E', ModItems.plate_paa, 'I', "paneGlass", 'P', "plateSteel", 'F', ModItems.filter_coal }));
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_paa_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.plate_paa });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_paa_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.plate_paa });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_paa_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.plate_paa });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.paa_plate, 1), new Object[] { "E E", "NEN", "ENE", 'E', ModItems.plate_paa, 'N', "plateDenseLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.paa_legs, 1), new Object[] { "EEE", "N N", "E E", 'E', ModItems.plate_paa, 'N', "plateDenseLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.paa_boots, 1), new Object[] { "E E", "N N", 'E', ModItems.plate_paa, 'N', "plateDenseLead" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.australium_iii, 1), new Object[] { "WIW", "PAP", " W ", 'W', ModItems.wire_copper, 'I', "ingotSteel", 'A', ModItems.ingot_australium, 'P', "plateSteel" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.australium_iv, 1), new Object[] { "WCW", "PAP", "SWS", 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'P', "plateTitanium", 'A', ModItems.rod_australium, 'S', ModItems.syringe_metal_empty }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.australium_v, 1), new Object[] { "SCS", "PAP", "ESE", 'S', ModItems.syringe_metal_empty, 'C', ModItems.circuit_gold, 'P', "plateAdvanced", 'A', ModItems.nugget_australium, 'E', ModItems.powder_power }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.goggles, 1), new Object[] { "P P", "GPG", 'G', "paneGlass", 'P', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_mask, 1), new Object[] { "PPP", "GPG", "FPF", 'G', "paneGlass", 'P', "plateSteel", 'F', ModItems.filter_coal }));

		GameRegistry.addRecipe(new ItemStack(ModItems.cape_radiation, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), 'D', new ItemStack(Items.dye, 1, 11), 'I', ModItems.nuclear_waste });
		GameRegistry.addRecipe(new ItemStack(ModItems.cape_gasmask, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), 'D', new ItemStack(Items.dye, 1, 0), 'I', ModItems.gas_mask });
		GameRegistry.addRecipe(new ItemStack(ModItems.cape_schrabidium, 1), new Object[] { "W W", "WIW", "WDW", 'W', ModItems.ingot_schrabidium, 'D', new ItemStack(Items.dye, 1, 0), 'I', ModItems.circuit_red_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.cape_hbm, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), 'D', ModItems.ingot_neptunium, 'I', new ItemStack(ModItems.ingot_euphemium, 1, 34) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cape_dafnik, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), 'D', new ItemStack(Items.dye, 1, 0), 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cape_lpkukin, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), 'D', new ItemStack(Items.dye, 1, 8), 'I', "plateSteel" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.igniter, 1), new Object[] { " W", "SC", "CE", 'S', "plateSteel", 'W', ModItems.wire_schrabidium, 'C', ModItems.circuit_schrabidium, 'E', new ItemStack(ModItems.ingot_euphemium, 1, 34) }));
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_helmet, 1), new Object[] { "EEE", "E E", 'E', new ItemStack(ModItems.ingot_euphemium, 1, 34) });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_plate, 1), new Object[] { "EWE", "EEE", "EEE", 'E', new ItemStack(ModItems.ingot_euphemium, 1, 34), 'W', ModItems.watch });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', new ItemStack(ModItems.ingot_euphemium, 1, 34) });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_boots, 1), new Object[] { "E E", "E E", 'E', new ItemStack(ModItems.ingot_euphemium, 1, 34) });
		GameRegistry.addRecipe(new ItemStack(ModItems.watch, 1), new Object[] { "LEL", "EWE", "LEL", 'E', new ItemStack(ModItems.ingot_euphemium, 1, 34), 'L', new ItemStack(Items.dye, 1, 4), 'W', Items.clock });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_euphemium, 1), new Object[] { "EEE", "EAE", "EEE", 'E', new ItemStack(ModItems.nugget_euphemium, 1, 34), 'A', Items.apple });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mask_of_infamy, 1), new Object[] { "III", "III", " I ", 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.designator, 1), new Object[] { "  A", "#B#", "#B#", '#', "plateIron", 'A', "plateSteel", 'B', ModItems.circuit_red_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.designator_range, 1), new Object[] { "RRD", "PIC", "  P", 'P', "plateSteel", 'R', Items.redstone, 'C', ModItems.circuit_gold, 'D', ModItems.designator, 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.linker, 1), new Object[] { "I I", "ICI", "GGG", 'I', "plateIron", 'G', "plateGold", 'C', ModItems.circuit_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.oil_detector, 1), new Object[] { "W I", "WCI", "PPP", 'W', ModItems.wire_gold, 'I', "ingotCopper", 'C', ModItems.circuit_red_copper, 'P', "plateSteel" }));

		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_hammer, 1), new Object[] { "BBB", "BBB", " S ", 'B', Item.getItemFromBlock(ModBlocks.block_schrabidium), 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_stopper, 1), new Object[] { "I", "S", "S", 'I', new ItemStack(ModItems.ingot_euphemium, 1, 34), 'S', Items.stick });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', "sulfur", 'S', Items.stick }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', "dustSulfur", 'S', Items.stick }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.remote, 1), new Object[] { "I", "S", 'I', "dustRedstone", 'S', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.crowbar, 1), new Object[] { "II", " I", " I", 'I', "ingotSteel" }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_power, 5), new Object[] { "dustRedstone", "dustGlowstone", "dustDiamond", "dustNeptunium", "dustMagnetizedTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.coal, 1), new Object[] { "#", '#', "dustCoal" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.quartz, 1), new Object[] { "#", '#', "dustQuartz" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.quartz, 1), new Object[] { "#", '#', "dustNetherQuartz" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.dye, 1, 4), new Object[] { "#", '#', "dustLapis" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.diamond, 1), new Object[] { "#", '#', "dustDiamond" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.emerald, 1), new Object[] { "#", '#', "dustEmerald" }));
		if(MainRegistry.enableNITAN) {
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.canister_NITAN, 1), new Object[] { ModItems.canister_empty, ModItems.canister_napalm, ModItems.powder_neptunium, ModItems.powder_iodine, ModItems.powder_thorium, ModItems.powder_astatine, ModItems.powder_neodymium, ModItems.powder_caesium });
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.canister_NITAN, 1), new Object[] { ModItems.canister_empty, ModItems.canister_napalm, ModItems.powder_strontium, ModItems.powder_cobalt, ModItems.powder_bromine, ModItems.powder_tennessine, ModItems.powder_niobium, ModItems.powder_cerium });
		}
		GameRegistry.addRecipe(new ItemStack(ModItems.canister_petroil, 9), new Object[] { "RRR", "RLR", "RRR", 'R', ModItems.canister_reoil, 'L', ModItems.canister_canola });
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.record_lc, 1), new Object[] { " S ", "SDS", " S ", 'S', "ingotSteel", 'D', "dustLapis" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.record_ss, 1), new Object[] { " S ", "SDS", " S ", 'S', "ingotSteel", 'D', "dustAdvanced" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.record_vc, 1), new Object[] { " S ", "SDS", " S ", 'S', "ingotSteel", 'D', "dustCMBSteel" }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { "dustRedstone", "dustIron", "dustCoal", "dustCopper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 3), new Object[] { "dustIron", "dustCoal", "dustRedstoneAlloy" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 3), new Object[] { "dustRedstone", "dustSteel", "dustCopper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 2), new Object[] { "dustRedstoneAlloy", "dustSteel" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_magnetized_tungsten, 1), new Object[] { "dustTungsten", "nuggetSchrabidium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_red_copper, 2), new Object[] { "dustRedstone", "dustCopper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_steel, 2), new Object[] { "dustIron", "dustCoal" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { "dustSteel", "dustTungsten" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { "dustSteel", ModItems.powder_cobalt }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { "dustIron", "dustCoal", "dustTungsten", "dustTungsten" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { "dustIron", "dustCoal", ModItems.powder_cobalt, ModItems.powder_cobalt }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_polymer, 2), new Object[] { "dustCoal", "dustSalpeter" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.saw, 1), new Object[] { "IIL", "PP ", 'P', "plateSteel", 'I', "ingotSteel", 'L', Items.leather }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bat, 1), new Object[] { "P", "P", "S", 'S', "plateSteel", 'P', "plankWood" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.bat_nail, 1), new Object[] { ModItems.bat, "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.golf_club, 1), new Object[] { "IP", " P", " P", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pipe_rusty, 1), new Object[] { "II", " I", " I", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pipe_lead, 1), new Object[] { "II", " I", " I", 'I', "ingotLead" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bottle_opener, 1), new Object[] { "S", "P", 'S', "plateSteel", 'P', "plankWood" }));

		GameRegistry.addRecipe(new ItemStack(ModItems.chopper, 1), new Object[] { "BBB", "HTF", "GWW", 'B', ModItems.chopper_blades, 'H', ModItems.chopper_head, 'T', ModItems.chopper_torso, 'F', ModItems.chopper_tail, 'G', ModItems.chopper_gun, 'W', ModItems.chopper_wing });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.polaroid, 1), new Object[] { " C ", "RPY", " B ", 'B', "dustLapis", 'C', "dustCoal", 'R', "dustAdvanced", 'Y', "dustGold", 'P', Items.paper }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ullapool_caber, 1), new Object[] { "ITI", " S ", " S ", 'I', "plateIron", 'T', Blocks.tnt, 'S', Items.stick }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chocolate_milk, 1), new Object[] { "paneGlass", new ItemStack(Items.dye, 1, 3), Items.milk_bucket, ModBlocks.block_niter, ModItems.sulfur, ModItems.sulfur, ModItems.sulfur, ModItems.powder_fire }));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.crystal_horn, 1), new Object[] { ModItems.powder_neptunium, ModItems.powder_iodine, ModItems.powder_thorium, ModItems.powder_astatine, ModItems.powder_neodymium, ModItems.powder_caesium, ModBlocks.block_meteor, ModBlocks.gravel_obsidian, Items.water_bucket });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.crystal_charred, 1), new Object[] { ModItems.powder_strontium, ModItems.powder_cobalt, ModItems.powder_bromine, ModItems.powder_niobium, ModItems.powder_tennessine, ModItems.powder_cerium, ModBlocks.block_meteor, ModBlocks.block_aluminium, Items.water_bucket });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystal_virus, 1), new Object[] { "STS", "THT", "STS", 'S', ModItems.cell_sas3, 'T', "dustTungsten", 'H', ModItems.crystal_horn }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystal_pulsar, 32), new Object[] { "STS", "THT", "STS", 'S', ModItems.cell_uf6, 'T', "dustAluminum", 'H', ModItems.crystal_charred }));
	}
	
	public static void AddSmeltingRec()
	{
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_uranium), new ItemStack(ModItems.ingot_uranium), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_uranium), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_plutonium), new ItemStack(ModItems.ingot_plutonium), 24.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_titanium), new ItemStack(ModItems.ingot_titanium), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_copper), new ItemStack(ModItems.ingot_copper), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_tungsten), new ItemStack(ModItems.ingot_tungsten), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_tungsten), new ItemStack(ModItems.ingot_tungsten), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_aluminium), new ItemStack(ModItems.ingot_aluminium), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_lead), new ItemStack(ModItems.ingot_lead), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_beryllium), new ItemStack(ModItems.ingot_beryllium), 2.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 128.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 256.0F);

		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_australium), new ItemStack(ModItems.nugget_australium), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_weidanium), new ItemStack(ModItems.nugget_weidanium, 6), 16.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_reiium), new ItemStack(ModItems.ingot_reiium), 24.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_unobtainium), new ItemStack(ModItems.nugget_unobtainium, 4), 10.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_daffergon), new ItemStack(ModItems.nugget_daffergon, 3), 8.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_verticium), new ItemStack(ModItems.ingot_verticium), 24.0F);
		GameRegistry.addSmelting(ModItems.powder_australium, new ItemStack(ModItems.ingot_australium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_weidanium, new ItemStack(ModItems.ingot_weidanium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_reiium, new ItemStack(ModItems.ingot_reiium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_unobtainium, new ItemStack(ModItems.ingot_unobtainium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_daffergon, new ItemStack(ModItems.ingot_daffergon), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_verticium, new ItemStack(ModItems.ingot_verticium), 5.0F);

		GameRegistry.addSmelting(ModItems.powder_lead, new ItemStack(ModItems.ingot_lead), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_neptunium, new ItemStack(ModItems.ingot_neptunium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_schrabidium, new ItemStack(ModItems.ingot_schrabidium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_aluminium, new ItemStack(ModItems.ingot_aluminium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_beryllium, new ItemStack(ModItems.ingot_beryllium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_copper, new ItemStack(ModItems.ingot_copper), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_gold, new ItemStack(Items.gold_ingot), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_iron, new ItemStack(Items.iron_ingot), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_titanium, new ItemStack(ModItems.ingot_titanium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_tungsten, new ItemStack(ModItems.ingot_tungsten), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_uranium, new ItemStack(ModItems.ingot_uranium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_plutonium, new ItemStack(ModItems.ingot_plutonium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_advanced_alloy, new ItemStack(ModItems.ingot_advanced_alloy), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_combine_steel, new ItemStack(ModItems.ingot_combine_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_magnetized_tungsten, new ItemStack(ModItems.ingot_magnetized_tungsten), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_red_copper, new ItemStack(ModItems.ingot_red_copper), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_steel, new ItemStack(ModItems.ingot_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_lithium, new ItemStack(ModItems.lithium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_dura_steel, new ItemStack(ModItems.ingot_dura_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_polymer, new ItemStack(ModItems.ingot_polymer), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_lanthanium, new ItemStack(ModItems.ingot_lanthanium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_actinium, new ItemStack(ModItems.ingot_actinium), 1.0F);

		GameRegistry.addSmelting(ModItems.combine_scrap, new ItemStack(ModItems.ingot_combine_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.tank_waste, new ItemStack(ModItems.tank_waste), 0.0F);
		GameRegistry.addSmelting(ModItems.canister_smear, new ItemStack(ModItems.canister_reoil), 1.0F);
		
		GameRegistry.addSmelting(Items.bone, new ItemStack(Items.slime_ball, 3), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Items.dye, 1, 15), new ItemStack(Items.slime_ball, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Blocks.gravel, 1), new ItemStack(Blocks.cobblestone, 1), 0.0F);
	}
}
