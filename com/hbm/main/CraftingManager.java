package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_titanium, 16), new Object[] { "TT", "TT", 'T', ModItems.ingot_titanium});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_aluminium, 16), new Object[] { "TT", "TT", 'T', ModItems.ingot_aluminium});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_steel, 16), new Object[] { "TT", "TT", 'T', ModItems.ingot_steel});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_iron, 16), new Object[] { "TT", "TT", 'T', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_lead, 16), new Object[] { "TT", "TT", 'T', ModItems.ingot_lead});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_copper, 16), new Object[] { "TT", "TT", 'T', ModItems.ingot_copper});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_schrabidium, 16), new Object[] { "TT", "TT", 'T', ModItems.ingot_schrabidium});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_gold, 16), new Object[] { "TT", "TT", 'T', Items.gold_ingot});
		GameRegistry.addRecipe(new ItemStack(ModItems.wire_red_copper, 6), new Object[] { "CCC", 'S', Items.string, 'C', ModItems.ingot_red_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.wire_tungsten, 6), new Object[] { "CCC", 'S', Items.string, 'C', ModItems.ingot_tungsten });
		GameRegistry.addRecipe(new ItemStack(ModItems.wire_aluminium, 6), new Object[] { "CCC", 'S', Items.string, 'C', ModItems.ingot_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.wire_copper, 6), new Object[] { "CCC", 'S', Items.string, 'C', ModItems.ingot_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.wire_gold, 6), new Object[] { "CCC", 'S', Items.string, 'C', Items.gold_ingot });
		GameRegistry.addRecipe(new ItemStack(ModItems.wire_schrabidium, 6), new Object[] { "CCC", 'S', Items.string, 'C', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_cloth, 4), new Object[] { "LN", "LN", 'L', Items.leather, 'N', ModItems.nugget_lead });

		GameRegistry.addRecipe(new ItemStack(ModItems.circuit_aluminium, 1), new Object[] { "RAR", "ASA", "RAR", 'S', ModItems.plate_steel, 'R', Items.redstone, 'A', ModItems.wire_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.circuit_copper, 1), new Object[] { "RAR", "ASA", "RAR", 'S', ModItems.circuit_aluminium, 'R', Items.glowstone_dust, 'A', ModItems.wire_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.circuit_red_copper, 1), new Object[] { "RAR", "ASA", "RAR", 'S', ModItems.circuit_copper, 'R', new ItemStack(Items.dye, 1, 4), 'A', ModItems.wire_red_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.circuit_gold, 1), new Object[] { "RAR", "ASA", "RAR", 'S', ModItems.circuit_red_copper, 'R', Items.ender_pearl, 'A', ModItems.wire_gold });
		GameRegistry.addRecipe(new ItemStack(ModItems.circuit_schrabidium, 1), new Object[] { "RAR", "ASA", "RAR", 'S', ModItems.circuit_gold, 'R', Items.diamond, 'A', ModItems.wire_schrabidium });

		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 3), new Object[] { ModItems.sulfur, ModItems.niter, Items.coal });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 3), new Object[] { ModItems.sulfur, ModItems.niter, new ItemStack(Items.coal, 1, 1) });

		GameRegistry.addRecipe(new ItemStack(ModItems.cell_empty, 6), new Object[] { "SSS", "G G", "SSS", 'S', ModItems.plate_steel, 'G', Item.getItemFromBlock(Blocks.glass_pane) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_uf6, 1), new Object[] { ModItems.cell_empty, ModItems.ingot_uranium, ModItems.fluorite, ModItems.fluorite, ModItems.fluorite, Items.water_bucket });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_puf6, 1), new Object[] { ModItems.cell_empty, ModItems.ingot_plutonium, ModItems.fluorite, ModItems.fluorite, ModItems.fluorite, Items.water_bucket });
		GameRegistry.addRecipe(new ItemStack(ModItems.cell_deuterium, 8), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.cell_empty, 'T', ModItems.mike_deut });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_sas3, 1), new Object[] { ModItems.cell_empty, ModItems.powder_schrabidium, ModItems.sulfur, ModItems.sulfur });

		GameRegistry.addRecipe(new ItemStack(ModItems.canister_empty, 2), new Object[] { "S ", "AA", "AA", 'S', ModItems.plate_steel, 'A', ModItems.plate_aluminium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_barrel), 1), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.canister_fuel, 'T', ModItems.tank_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.yellow_barrel), 1), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.nuclear_waste, 'T', ModItems.tank_steel });

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
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_beryllium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_beryllium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_advanced_alloy), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_advanced_alloy });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { ModItems.nugget_pu238, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_pu238, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 1), new Object[] { ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium });
		
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
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_beryllium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_beryllium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_schrabidium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_advanced_alloy, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_advanced_alloy) });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.powder_lead, 2), new Object[] { ModItems.ingot_lead, ModItems.ingot_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.powder_neptunium, 2), new Object[] { ModItems.ingot_neptunium, ModItems.ingot_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.powder_schrabidium, 2), new Object[] { ModItems.ingot_schrabidium, ModItems.ingot_schrabidium });

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

		GameRegistry.addRecipe(new ItemStack(ModItems.rod_empty, 16), new Object[] { "SSS", "L L", "SSS", 'S', ModItems.plate_steel, 'L', ModItems.plate_lead });
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

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_empty, 4), new Object[] { ModItems.rod_quad_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_dual_empty, ModItems.rod_dual_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_uranium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_uranium, ModItems.ingot_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_u235, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u235, ModItems.ingot_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_u238, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u238, ModItems.ingot_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_plutonium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_plutonium, ModItems.ingot_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu238, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu238, ModItems.ingot_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu239, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
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
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_coolant, 1), new Object[] { ModItems.rod_empty, Items.water_bucket, new ItemStack(Items.dye, 1, 4) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_coolant, 1), new Object[] { ModItems.rod_dual_empty, Items.water_bucket, Items.water_bucket, new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 4) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_coolant, 1), new Object[] { ModItems.rod_quad_empty, Items.water_bucket, Items.water_bucket, Items.water_bucket, Items.water_bucket, new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 4) });

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

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_euphemium, 1), new Object[] { ModItems.rod_quad_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_euphemium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_euphemium, 9), new Object[] { "#", '#', ModItems.ingot_euphemium });

		GameRegistry.addRecipe(new ItemStack(ModItems.pellet_rtg, 1), new Object[] { "IPI", "PPP", "IPI", 'I', ModItems.plate_iron, 'P', ModItems.nugget_pu238 });

		GameRegistry.addRecipe(new ItemStack(ModItems.coil_copper, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_red_copper, 'I', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { "PCP", "C C", "PCP", 'P', ModItems.plate_iron, 'C', ModItems.coil_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.coil_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_tungsten, 'I', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(ModItems.tank_steel, 1), new Object[] { "STS", "S S", "STS", 'S', ModItems.plate_steel, 'T', ModItems.plate_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.motor, 1), new Object[] { " R ", "ICI", "ITI", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', ModItems.plate_iron, 'C', ModItems.coil_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.centrifuge_element, 1), new Object[] { " T ", "WTW", "RMR", 'R', ModItems.wire_red_copper, 'T', ModItems.tank_steel, 'M', ModItems.motor, 'W', ModItems.coil_tungsten });
		GameRegistry.addRecipe(new ItemStack(ModItems.centrifuge_tower, 1), new Object[] { "LL", "EE", "EE", 'E', ModItems.centrifuge_element, 'L', new ItemStack(Items.dye, 1, 4) });
		GameRegistry.addRecipe(new ItemStack(ModItems.reactor_core, 1), new Object[] { "LNL", "N N", "LNL", 'N', ModItems.neutron_reflector, 'L', ModItems.plate_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.rtg_unit, 1), new Object[] { "CLC", "NAN", "CLC", 'N', ModItems.neutron_reflector, 'L', ModItems.plate_lead, 'C', ModItems.plate_copper, 'A', ModItems.circuit_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.thermo_unit_empty, 1), new Object[] { "TTT", " S ", "P P", 'S', ModItems.ingot_steel, 'P', ModItems.plate_titanium, 'T', ModItems.coil_copper_torus });
		GameRegistry.addRecipe(new ItemStack(ModItems.thermo_unit_endo, 1), new Object[] { "EEE", "ETE", "EEE", 'E', Item.getItemFromBlock(Blocks.ice), 'T', ModItems.thermo_unit_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.thermo_unit_exo, 1), new Object[] { "LLL", "LTL", "LLL", 'L', Items.lava_bucket, 'T', ModItems.thermo_unit_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.levitation_unit, 1), new Object[] { "CSC", "TAT", "PSP", 'C', ModItems.coil_copper, 'S', ModItems.nugget_schrabidium, 'T', ModItems.coil_tungsten, 'P', ModItems.plate_titanium, 'A', ModItems.ingot_steel });

		GameRegistry.addRecipe(new ItemStack(ModItems.cap_aluminium, 1), new Object[] { "PIP", 'P', ModItems.plate_aluminium, 'I', ModItems.ingot_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.hull_small_steel, 1), new Object[] { "PPP", "   ", "PPP", 'P', ModItems.plate_steel, 'I', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.hull_small_aluminium, 1), new Object[] { "PPP", "   ", "PPP", 'P', ModItems.plate_aluminium, 'I', ModItems.ingot_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.hull_big_steel, 1), new Object[] { "III", "   ", "III", 'P', ModItems.plate_steel, 'I', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.hull_big_aluminium, 1), new Object[] { "III", "   ", "III", 'P', ModItems.plate_aluminium, 'I', ModItems.ingot_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.hull_big_titanium, 1), new Object[] { "III", "   ", "III", 'P', ModItems.plate_titanium, 'I', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.fins_flat, 1), new Object[] { "IP", "PP", "IP", 'P', ModItems.plate_steel, 'I', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.fins_small_steel, 1), new Object[] { " PP", "PII", " PP", 'P', ModItems.plate_steel, 'I', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.fins_big_steel, 1), new Object[] { " PI", "III", " PI", 'P', ModItems.plate_steel, 'I', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.fins_tri_steel, 1), new Object[] { " PI", "IIB", " PI", 'P', ModItems.plate_steel, 'I', ModItems.ingot_steel, 'B', Item.getItemFromBlock(ModBlocks.block_steel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.fins_quad_titanium, 1), new Object[] { " PP", "III", " PP", 'P', ModItems.plate_titanium, 'I', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.sphere_steel, 1), new Object[] { "PIP", "I I", "PIP", 'P', ModItems.plate_steel, 'I', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.pedestal_steel, 1), new Object[] { "P P", "P P", "III", 'P', ModItems.plate_steel, 'I', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.dysfunctional_reactor, 1), new Object[] { "PPP", "CDC", "PPP", 'P', ModItems.plate_steel, 'C', ModItems.rod_quad_empty, 'D', new ItemStack(Items.dye, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_generic_small, 1), new Object[] { " P ", "PTP", "PTP", 'P', ModItems.plate_titanium, 'T', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_incendiary_small, 1), new Object[] { " P ", "PWP", " P ", 'P', ModItems.powder_fire, 'W', ModItems.warhead_generic_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_cluster_small, 1), new Object[] { " P ", "PWP", " P ", 'P', ModItems.pellet_cluster, 'W', ModItems.warhead_generic_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_buster_small, 1), new Object[] { " P ", "PWP", " P ", 'P', Item.getItemFromBlock(ModBlocks.det_cord), 'W', ModItems.warhead_generic_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_generic_medium, 1), new Object[] { " P ", "PTP", "TTT", 'P', ModItems.plate_titanium, 'T', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_incendiary_medium, 1), new Object[] { "PPP", "PWP", "PPP", 'P', ModItems.powder_fire, 'W', ModItems.warhead_generic_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_cluster_medium, 1), new Object[] { "PPP", "PWP", "PPP", 'P', ModItems.pellet_cluster, 'W', ModItems.warhead_generic_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_buster_medium, 1), new Object[] { "PPP", "PWP", "PPP", 'P', Item.getItemFromBlock(ModBlocks.det_cord), 'W', ModItems.warhead_generic_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_generic_large, 1), new Object[] { "PTP", "PTP", "TTT", 'P', ModItems.plate_titanium, 'T', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_incendiary_large, 1), new Object[] { "PXP", "XWX", "PXP", 'P', ModItems.powder_fire, 'W', ModItems.warhead_generic_large, 'X', Items.lava_bucket });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_cluster_large, 1), new Object[] { "PXP", "XWX", "PXP", 'P', ModItems.pellet_cluster, 'W', ModItems.warhead_generic_large, 'X', Item.getItemFromBlock(ModBlocks.det_cord) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_buster_large, 1), new Object[] { "PXP", "XWX", "PXP", 'P', Item.getItemFromBlock(ModBlocks.det_cord), 'W', ModItems.warhead_generic_large, 'X', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_nuclear, 1), new Object[] { " N ", "TZT", "TBT", 'N', ModItems.boy_shielding, 'Z', ModItems.boy_target, 'B', ModItems.boy_bullet, 'T', ModItems.plate_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_mirvlet, 1), new Object[] { " S ", "SPS", "STS", 'S', ModItems.plate_steel, 'P', ModItems.ingot_pu239, 'T', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_mirv, 1), new Object[] { "MMM", "MWM", "MMM", 'M', ModItems.warhead_mirvlet, 'W', ModItems.warhead_generic_large });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_thermo_endo, 1), new Object[] { " T ", "TBT", "TBT", 'T', ModItems.plate_titanium, 'B', Item.getItemFromBlock(ModBlocks.therm_endo) });
		GameRegistry.addRecipe(new ItemStack(ModItems.warhead_thermo_exo, 1), new Object[] { " T ", "TBT", "TBT", 'T', ModItems.plate_titanium, 'B', Item.getItemFromBlock(ModBlocks.therm_exo) });
		GameRegistry.addRecipe(new ItemStack(ModItems.fuel_tank_small, 1), new Object[] { "PTP", "PTP", "PTP", 'P', ModItems.plate_titanium, 'T', Item.getItemFromBlock(ModBlocks.red_barrel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.fuel_tank_medium, 1), new Object[] { "PTP", "PTP", "PTP", 'P', ModItems.plate_titanium, 'T', ModItems.fuel_tank_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.fuel_tank_large, 1), new Object[] { "PTP", "PTP", "PTP", 'P', ModItems.plate_titanium, 'T', ModItems.fuel_tank_medium });
		GameRegistry.addRecipe(new ItemStack(ModItems.thruster_small, 1), new Object[] { "AS ", "AH ", " L ", 'A', ModItems.wire_aluminium, 'S', ModItems.plate_steel, 'H', ModItems.hull_small_steel, 'L', ModItems.hull_small_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.thruster_medium, 1), new Object[] { "AS ", "AH ", " L ", 'A', ModItems.wire_copper, 'S', ModItems.thruster_small, 'H', ModItems.hull_small_steel, 'L', ModItems.hull_big_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.thruster_large, 1), new Object[] { "AS ", "AH ", " L ", 'A', ModItems.wire_red_copper, 'S', ModItems.thruster_medium, 'H', ModItems.hull_big_steel, 'L', ModItems.hull_big_steel });

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

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 1), new Object[] { "AHA", "TCT", "TPT", 'T', ModItems.plate_titanium, 'A', ModItems.plate_aluminium, 'S', ModItems.plate_steel, 'C', ModItems.ingot_copper, 'P', Item.getItemFromBlock(Blocks.piston), 'H', Item.getItemFromBlock(Blocks.hopper) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_centrifuge), 1), new Object[] { " T ", "RDR", "RSR", 'S', ModItems.plate_steel, 'T', ModItems.centrifuge_tower, 'W', ModItems.coil_tungsten, 'R', ModItems.coil_copper, 'D', Item.getItemFromBlock(ModBlocks.machine_difurnace_off) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', ModItems.plate_titanium, 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', ModItems.ingot_red_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', ModItems.plate_steel, 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', ModItems.ingot_red_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_reactor), 1), new Object[] { "LSL", "SCS", "LSL", 'S', ModItems.ingot_steel, 'L', ModItems.ingot_lead, 'C', ModItems.reactor_core });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 1), new Object[] { "SSS", "SFS", "CCC", 'S', ModItems.plate_steel, 'C', ModItems.plate_copper, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_rtg_furnace_off), 1), new Object[] { "NNN", "NFN", "UUU", 'N', ModItems.neutron_reflector, 'U', ModItems.rtg_unit, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 1), new Object[] { "BBB", "WFW", "RRR", 'B', ModItems.ingot_beryllium, 'R', ModItems.coil_tungsten, 'W', ModItems.wire_red_copper, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_generator), 1), new Object[] { "SLS", "LCL", "SLS", 'C', ModItems.circuit_red_copper, 'L', ModItems.rod_quad_lead, 'S', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 16), new Object[] { "WRW", "RIR", "WRW", 'W', ModItems.ingot_tungsten, 'I', ModItems.ingot_red_copper, 'R', ModItems.wire_red_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_deuterium), 1), new Object[] { "TIT", "RFR", "CCC", 'T', ModItems.tank_steel, 'I', ModItems.ingot_titanium, 'R', ModItems.wire_red_copper, 'F', Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 'C', ModItems.coil_tungsten });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_battery), 1), new Object[] { "TST", "RIR", "TLT", 'T', ModItems.ingot_tungsten, 'I', ModItems.ingot_red_copper, 'R', ModItems.wire_red_copper, 'S', Item.getItemFromBlock(ModBlocks.block_sulfur), 'L', Item.getItemFromBlock(ModBlocks.block_lead) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_battery), 1), new Object[] { "TLT", "RIR", "TST", 'T', ModItems.ingot_tungsten, 'I', ModItems.ingot_red_copper, 'R', ModItems.wire_red_copper, 'S', Item.getItemFromBlock(ModBlocks.block_sulfur), 'L', Item.getItemFromBlock(ModBlocks.block_lead) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_coal_off), 1), new Object[] { "STS", "SCS", "SFS", 'S', ModItems.ingot_steel, 'T', ModItems.tank_steel, 'C', ModItems.ingot_red_copper, 'F', Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off) });

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 1), new Object[] { "PIP", "I I", "PIP", 'P', ModItems.plate_titanium, 'I', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_furnace), 1), new Object[] { "HMH", "MFM", "HMH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'M', ModItems.motor, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_core), 1), new Object[] { "HPH", "PCP", "HPH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'C', ModItems.circuit_aluminium, 'P', Item.getItemFromBlock(Blocks.piston) });
		GameRegistry.addRecipe(new ItemStack(ModItems.factory_core_titanium, 1, ModItems.factory_core_titanium.getMaxDamage()), new Object[] { "BRB", "RHR", "BRB", 'B', new ItemStack(ModItems.battery_generic, 1, ModItems.battery_generic.getMaxDamage()), 'R', Item.getItemFromBlock(Blocks.redstone_block), 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 1), new Object[] { "PIP", "I I", "PIP", 'P', ModItems.plate_advanced_alloy, 'I', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_furnace), 1), new Object[] { "HMH", "MFM", "HMH", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 'M', ModItems.motor, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_core), 1), new Object[] { "HPH", "PCP", "HPH", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 'C', ModItems.circuit_red_copper, 'P', Item.getItemFromBlock(Blocks.piston) });
		GameRegistry.addRecipe(new ItemStack(ModItems.factory_core_advanced, 1, ModItems.factory_core_advanced.getMaxDamage()), new Object[] { "BLB", "SHS", "BLB", 'B', new ItemStack(ModItems.battery_advanced, 1, ModItems.battery_advanced.getMaxDamage()), 'S', Item.getItemFromBlock(ModBlocks.block_sulfur), 'L', Item.getItemFromBlock(ModBlocks.block_lead), 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) });
		GameRegistry.addRecipe(new ItemStack(ModItems.factory_core_advanced, 1, ModItems.factory_core_advanced.getMaxDamage()), new Object[] { "BSB", "LHL", "BSB", 'B', new ItemStack(ModItems.battery_advanced, 1, ModItems.battery_advanced.getMaxDamage()), 'S', Item.getItemFromBlock(ModBlocks.block_sulfur), 'L', Item.getItemFromBlock(ModBlocks.block_lead), 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) });

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_light), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.fence, 'B', Blocks.brick_block });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_concrete), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.stone });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_obsidian), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.obsidian });

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_brick), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', ModBlocks.brick_concrete });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_glass), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.glass });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_light), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_lamp_off), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.redstone_lamp });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_sand), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.sandstone });

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.tape_recorder), 4), new Object[] { "TST", "SSS", 'T', ModItems.ingot_tungsten, 'S', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_poles), 16), new Object[] { "S S", "SSS", "S S", 'S', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_top), 1), new Object[] { "T T", "TRT", "BBB", 'T', ModItems.ingot_tungsten, 'B', ModItems.ingot_beryllium, 'R', ModItems.ingot_red_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 1), new Object[] { "SS ", "SCR", "SS ", 'S', ModItems.ingot_steel, 'C', ModItems.circuit_red_copper, 'R', ModItems.wire_red_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_wall), 4), new Object[] { "SSS", "SSS", 'S', ModItems.ingot_steel });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_corner)), new Object[] { Item.getItemFromBlock(ModBlocks.steel_wall), Item.getItemFromBlock(ModBlocks.steel_wall) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_roof), 2), new Object[] { "SSS", 'S', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_scaffold), 8), new Object[] { "SSS", " S ", "SSS", 'S', ModItems.ingot_steel });

		GameRegistry.addRecipe(new ItemStack(ModItems.gun_rpg, 1), new Object[] { "SSW", " S ", 'S', ModItems.plate_steel, 'W', Item.getItemFromBlock(Blocks.log) });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_rpg_ammo, 8), new Object[] { "SI ", "ITI", " I ", 'S', ModItems.plate_steel, 'T', Item.getItemFromBlock(Blocks.tnt), 'I', ModItems.plate_iron });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver, 1), new Object[] { "SSS", " RW", 'S', ModItems.plate_steel, 'W', Item.getItemFromBlock(Blocks.planks), 'R', ModItems.wire_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_ammo, 16), new Object[] { "L", "S", 'L', ModItems.plate_lead, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_iron, 1), new Object[] { "SSS", " RW", 'S', ModItems.plate_iron, 'W', Item.getItemFromBlock(Blocks.planks), 'R', ModItems.wire_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_iron_ammo, 16), new Object[] { "L", "S", 'L', ModItems.plate_iron, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_gold, 1), new Object[] { "SSS", " RW", 'S', ModItems.plate_gold, 'W', Items.gold_ingot, 'R', ModItems.wire_gold });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_gold_ammo, 16), new Object[] { "L", "S", 'L', ModItems.plate_gold, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_schrabidium, 1), new Object[] { "SSS", " RW", 'S', ModItems.plate_schrabidium, 'W', ModItems.ingot_tungsten, 'R', ModItems.wire_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_schrabidium_ammo, 16), new Object[] { "L", "S", 'L', ModItems.plate_schrabidium, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_cursed, 1), new Object[] { "TTT", "SRI", 'S', ModItems.plate_steel, 'I', ModItems.ingot_steel, 'R', ModItems.wire_red_copper, 'T', ModItems.plate_titanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_cursed_ammo, 16), new Object[] { "L", "S", 'L', ModItems.plate_steel, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_fatman, 1), new Object[] { "SSI", "III", "WPH", 'S', ModItems.plate_steel, 'I', ModItems.ingot_steel, 'W', ModItems.wire_aluminium, 'H', ModItems.hull_small_steel, 'P', Item.getItemFromBlock(Blocks.piston) });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_fatman_ammo, 2), new Object[] { " S ", "SPS", "ITI", 'S', ModItems.plate_steel, 'P', ModItems.ingot_pu239, 'T', Item.getItemFromBlock(Blocks.tnt), 'I', ModItems.plate_iron });

		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_generic, 4), new Object[] { "RS ", "ITI", " I ", 'I', ModItems.plate_iron, 'R', ModItems.wire_red_copper, 'S', ModItems.plate_steel, 'T', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_strong, 2), new Object[] { " G ", "SGS", " S ", 'G', ModItems.grenade_generic, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_frag, 2), new Object[] { " G ", "WGW", " K ", 'G', ModItems.grenade_generic, 'W', Item.getItemFromBlock(Blocks.planks), 'K', Item.getItemFromBlock(Blocks.gravel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_fire, 2), new Object[] { " G ", "PFP", " S ", 'G', ModItems.grenade_generic, 'F', ModItems.grenade_frag, 'P', ModItems.powder_fire, 'S', ModItems.plate_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_cluster, 2), new Object[] { " G ", "PFP", " P ", 'G', ModItems.grenade_generic, 'P', ModItems.pellet_cluster, 'F', ModItems.grenade_frag });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_flare, 2), new Object[] { " G ", "DGD", " D ", 'G', ModItems.grenade_generic, 'D', Items.glowstone_dust });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_electric, 2), new Object[] { " G ", "CSC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.circuit_red_copper, 'S', ModItems.grenade_strong });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_poison, 2), new Object[] { " G ", "PGP", " P ", 'G', ModItems.grenade_generic, 'P', ModItems.powder_poison });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_gas, 2), new Object[] { " G ", "CGC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.pellet_gas });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_schrabidium, 2), new Object[] { " G ", "CFC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.ingot_schrabidium, 'F', ModItems.grenade_flare });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_nuclear, 1), new Object[] {"RS ", "ITI", " I ", 'I', ModItems.plate_iron, 'R', ModItems.wire_red_copper, 'S', ModItems.plate_steel, 'T', ModItems.gun_fatman_ammo });

		GameRegistry.addRecipe(new ItemStack(ModItems.bomb_waffle, 1), new Object[] { "WEW", "MPM", "WEW", 'W', Items.wheat, 'E', Items.egg, 'M', Items.milk_bucket, 'P', ModItems.man_core });
		GameRegistry.addRecipe(new ItemStack(ModItems.schnitzel_vegan, 3), new Object[] { "RWR", "WPW", "RWR", 'W', ModItems.trinitite, 'R', Items.reeds, 'P', Items.pumpkin_seeds });
		GameRegistry.addRecipe(new ItemStack(ModItems.cotton_candy, 2), new Object[] { " S ", "SPS", " H ", 'P', ModItems.nugget_pu239, 'S', Items.sugar, 'H', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', ModItems.nugget_schrabidium, 'A', Items.apple });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 1), new Object[] { "SSS", "SAS", "SSS", 'S', ModItems.ingot_schrabidium, 'A', Items.apple });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 2), new Object[] { "SSS", "SAS", "SSS", 'S', Item.getItemFromBlock(ModBlocks.block_schrabidium), 'A', Items.apple });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 0), new Object[] { Items.gold_nugget, Items.paper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 1), new Object[] { Items.gold_nugget, Items.gold_nugget, Items.gold_nugget, Items.paper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 2), new Object[] { Items.gold_ingot, Items.gold_ingot, Items.gold_nugget, Items.gold_nugget, Items.paper });
		
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_empty, 6), new Object[] { "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.iron_bars), 'C', ModItems.cell_empty, 'P', ModItems.plate_iron });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.pumpkin_seeds, 'M', Items.milk_bucket });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.pumpkin_seeds, 'M', Items.milk_bucket });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.pumpkin_seeds, 'M', Items.reeds });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.pumpkin_seeds, 'M', Items.reeds });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_poison, 1), new Object[] { "SLS", "LCL", "SLS", 'C', ModItems.syringe_empty, 'S', Items.spider_eye, 'L', ModItems.powder_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SPS", "NCN", "SPS", 'C', ModItems.syringe_empty, 'S', ModItems.sulfur, 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SNS", "PCP", "SNS", 'C', ModItems.syringe_empty, 'S', ModItems.sulfur, 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_metal_empty, 6), new Object[] { "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.iron_bars), 'C', ModItems.rod_empty, 'P', ModItems.plate_iron });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_metal_stimpak, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.nether_wart, 'S', ModItems.syringe_metal_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_metal_medx, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.quartz, 'S', ModItems.syringe_metal_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.syringe_metal_psycho, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.glowstone_dust, 'S', ModItems.syringe_metal_empty });

		GameRegistry.addRecipe(new ItemStack(ModItems.stealth_boy, 1), new Object[] { " B", "LI", "LC", 'B', Item.getItemFromBlock(Blocks.stone_button), 'L', Items.leather, 'I', ModItems.ingot_steel, 'C', ModItems.circuit_red_copper });
		
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_gadget), 1), new Object[] { "DGD", "FCF", "DPD", 'G', ModItems.wire_gold, 'F', ModItems.fins_flat, 'C', ModItems.sphere_steel, 'P', ModItems.pedestal_steel, 'D', new ItemStack(Items.dye, 1, 8) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_boy), 1), new Object[] { "ADD", "HHF", "CDD", 'A', ModItems.wire_aluminium, 'H', ModItems.hull_small_steel, 'C', ModItems.circuit_aluminium, 'F', ModItems.fins_small_steel, 'D', new ItemStack(Items.dye, 1, 4) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_man), 1), new Object[] { "ADD", "SHF", "CDD", 'A', ModItems.wire_aluminium, 'S', ModItems.sphere_steel, 'H', ModItems.hull_big_steel, 'F', ModItems.fins_big_steel, 'C', ModItems.circuit_aluminium, 'D', new ItemStack(Items.dye, 1, 11) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_mike), 1), new Object[] { "DCD", "HSH", "ATA", 'C', ModItems.cap_aluminium, 'H', ModItems.hull_big_aluminium, 'S', ModItems.sphere_steel, 'A', ModItems.circuit_red_copper, 'T', ModItems.tank_steel, 'D', new ItemStack(Items.dye, 1, 7) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_tsar), 1), new Object[] { "CHD", "STF", "CHD", 'C', ModItems.circuit_red_copper, 'H', ModItems.hull_big_titanium, 'S', ModItems.sphere_steel, 'T', ModItems.tank_steel, 'F', ModItems.fins_tri_steel, 'D', new ItemStack(Items.dye, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_fleija), 1), new Object[] { "DGD", "CHF", "DGD", 'G', ModItems.wire_gold, 'C', ModItems.circuit_gold, 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', new ItemStack(Items.dye, 1, 15) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_prototype), 1), new Object[] { "GCG", "HRH", "GCG", 'G', ModItems.wire_gold, 'C', ModItems.ingot_euphemium, 'H', ModItems.hull_small_steel, 'R', ModItems.dysfunctional_reactor });

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.launch_pad), 1), new Object[] { "PPP", "ICI", "CBC", 'P', ModItems.plate_steel, 'I', ModItems.ingot_steel, 'C', ModItems.circuit_gold, 'B', Item.getItemFromBlock(ModBlocks.machine_battery) });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.book_guide), 1), new Object[] { "IBI", "LBL", "IBI", 'B', Items.book, 'I', new ItemStack(Items.dye, 1, 0), 'L', new ItemStack(Items.dye, 1, 4) });

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.bomb_multi), 1), new Object[] { "AAD", "CHF", "AAD", 'A', ModItems.wire_aluminium, 'C', ModItems.circuit_aluminium, 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', new ItemStack(Items.dye, 1, 15) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pellet_cluster, 8), new Object[] { ModItems.plate_iron, Item.getItemFromBlock(Blocks.tnt), ModItems.plate_steel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.powder_fire, 4), new Object[] { Items.blaze_powder, ModItems.sulfur, Items.redstone });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.powder_poison, 4), new Object[] { Items.spider_eye, Items.redstone, Items.quartz });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pellet_gas, 2), new Object[] { Items.water_bucket, Items.glowstone_dust, ModItems.plate_steel });

		GameRegistry.addRecipe(new ItemStack(ModItems.flame_pony, 1), new Object[] { " O ", "DPD", " O ", 'D', new ItemStack(Items.dye, 1, 7), 'O', new ItemStack(Items.dye, 1, 5), 'P', Items.paper });
		GameRegistry.addRecipe(new ItemStack(ModItems.flame_conspiracy, 1), new Object[] { " S ", "STS", " S ", 'S', Item.getItemFromBlock(Blocks.stone), 'T', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.flame_politics, 1), new Object[] { " I ", "IPI", " I ", 'P', Items.paper, 'I', new ItemStack(Items.dye, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(ModItems.flame_opinion, 1), new Object[] { " R ", "RPR", " R ", 'P', Items.paper, 'R', new ItemStack(Items.dye, 1, 1) });
		
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.flame_war), 1), new Object[] { "WHW", "CTP", "WOW", 'W', Item.getItemFromBlock(Blocks.planks), 'T', Item.getItemFromBlock(Blocks.tnt), 'H', ModItems.flame_pony, 'C', ModItems.flame_conspiracy, 'P', ModItems.flame_politics, 'O', ModItems.flame_opinion });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.float_bomb), 1), new Object[] { "TGT", "TUT", "TGT", 'T', ModItems.plate_titanium, 'U', ModItems.levitation_unit, 'G', ModItems.circuit_gold });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.therm_endo), 1), new Object[] { "TGT", "TUT", "TGT", 'T', ModItems.plate_titanium, 'U', ModItems.thermo_unit_endo, 'G', ModItems.circuit_gold });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.therm_exo), 1), new Object[] { "TGT", "TUT", "TGT", 'T', ModItems.plate_titanium, 'U', ModItems.thermo_unit_exo, 'G', ModItems.circuit_gold });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.det_cord), 8), new Object[] { "TNT", "NGN", "TNT", 'T', ModItems.plate_titanium, 'N', ModItems.niter, 'G', Items.gunpowder });

		GameRegistry.addRecipe(new ItemStack(ModItems.gadget_core, 1), new Object[] { "PPP", "PUP", "PPP", 'P', ModItems.nugget_pu239, 'U', ModItems.nugget_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.gadget_explosive, 16), new Object[] { "ATP", "ATW", "ATP", 'P', ModItems.plate_titanium, 'A', ModItems.plate_aluminium, 'T', Item.getItemFromBlock(Blocks.tnt), 'W', ModItems.wire_gold });
		GameRegistry.addRecipe(new ItemStack(ModItems.gadget_explosive8, 1), new Object[] { "EEE", "EPE", "EEE", 'E', ModItems.gadget_explosive, 'P', ModItems.plate_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.gadget_wireing, 1), new Object[] { "WWW", "WSW", "WWW", 'W', ModItems.wire_gold, 'S', ModItems.plate_titanium });
		
		GameRegistry.addRecipe(new ItemStack(ModItems.boy_bullet, 1), new Object[] { "##", '#', ModItems.nugget_u235 });
		GameRegistry.addRecipe(new ItemStack(ModItems.boy_igniter, 1), new Object[] { " AA", "WWS", " AA", 'A', ModItems.plate_aluminium, 'W', ModItems.wire_red_copper, 'S', ModItems.circuit_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.boy_propellant, 1), new Object[] { "TTT", "PPW", "TTT", 'T', ModItems.plate_titanium, 'W', ModItems.wire_red_copper, 'P', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.boy_shielding, 1), new Object[] { "## ", "# #", "## ", '#', ModItems.neutron_reflector });
		GameRegistry.addRecipe(new ItemStack(ModItems.boy_target, 1), new Object[] { "###", "#  ", "###", '#', ModItems.nugget_u235 });
		
		GameRegistry.addRecipe(new ItemStack(ModItems.man_core, 1), new Object[] { "PPP", "PBP", "PPP", 'P', ModItems.nugget_pu239, 'B', ModItems.nugget_beryllium });
		GameRegistry.addRecipe(new ItemStack(ModItems.man_explosive, 16), new Object[] { "UTP", "UTW", "UTP", 'P', ModItems.plate_titanium, 'U', ModItems.nugget_u238, 'T', Item.getItemFromBlock(Blocks.tnt), 'W', ModItems.wire_red_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.man_explosive8, 1), new Object[] { "EEE", "ESE", "EEE", 'E', ModItems.man_explosive, 'S', ModItems.plate_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.man_igniter, 1), new Object[] { " S ", "WWW", 'W', ModItems.wire_red_copper, 'S', ModItems.circuit_aluminium });

		GameRegistry.addRecipe(new ItemStack(ModItems.mike_core, 1), new Object[] { "UPU", "UPU", "UPU", 'U', ModItems.nugget_u238, 'P', ModItems.nugget_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.mike_deut, 1), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.cell_deuterium, 'T', ModItems.tank_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.mike_cooling_unit, 1), new Object[] { "WSC", "WMC", "WAC", 'W', ModItems.coil_tungsten, 'C', ModItems.coil_copper, 'S', ModItems.plate_steel, 'M', ModItems.motor, 'A', ModItems.circuit_copper });

		GameRegistry.addRecipe(new ItemStack(ModItems.fleija_igniter, 1), new Object[] { " TT", "TSW", " TT", 'T', ModItems.plate_titanium, 'S', ModItems.circuit_schrabidium, 'W', ModItems.wire_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.fleija_propellant, 1), new Object[] { "PPP", "TST", "PPP", 'P', ModItems.plate_schrabidium, 'S', ModItems.ingot_schrabidium, 'T', Item.getItemFromBlock(Blocks.tnt) });
		GameRegistry.addRecipe(new ItemStack(ModItems.fleija_core, 1), new Object[] { "NUU", "BHW", "NUU", 'N', ModItems.nugget_neptunium, 'B', ModItems.nugget_beryllium, 'U', ModItems.nugget_u235, 'H', ModItems.coil_copper, 'W', ModItems.wire_red_copper });

		GameRegistry.addRecipe(new ItemStack(ModItems.battery_generic, 1, 50), new Object[] { " A ", "PRP", "PRP", 'A', ModItems.wire_aluminium, 'P', ModItems.plate_aluminium, 'R', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(ModItems.battery_advanced, 1, 200), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', ModItems.plate_copper, 'S', ModItems.sulfur, 'L', ModItems.powder_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.battery_advanced, 1, 200), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', ModItems.plate_copper, 'S', ModItems.sulfur, 'L', ModItems.powder_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.battery_schrabidium, 1, 1000), new Object[] { " A ", "PNP", "PSP", 'A', ModItems.wire_schrabidium, 'P', ModItems.plate_schrabidium, 'S', ModItems.powder_schrabidium, 'N', ModItems.powder_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModItems.battery_schrabidium, 1, 1000), new Object[] { " A ", "PSP", "PNP", 'A', ModItems.wire_schrabidium, 'P', ModItems.plate_schrabidium, 'S', ModItems.powder_schrabidium, 'N', ModItems.powder_neptunium });

		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_schrabidium, 'S', Items.stick });

		GameRegistry.addRecipe(new ItemStack(ModItems.chainsaw, 1), new Object[] { "TTG", "SSM", 'T', ModItems.plate_iron, 'S', ModItems.plate_steel, 'M', ModItems.motor, 'G', ModItems.circuit_gold });

		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_helmet, 1), new Object[] { "EEE", "EIE", " P ", 'E', ModItems.hazmat_cloth, 'I', Item.getItemFromBlock(Blocks.glass_pane), 'P', ModItems.plate_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth });

		GameRegistry.addRecipe(new ItemStack(ModItems.goggles, 1), new Object[] { "P P", "GPG", 'G', Item.getItemFromBlock(Blocks.glass_pane), 'P', ModItems.plate_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.gas_mask, 1), new Object[] { "PPP", "GPG", " P ", 'G', Item.getItemFromBlock(Blocks.glass_pane), 'P', ModItems.plate_steel });

		GameRegistry.addRecipe(new ItemStack(ModItems.igniter, 1), new Object[] { " W", "SC", "CE", 'S', ModItems.plate_steel, 'W', ModItems.wire_schrabidium, 'C', ModItems.circuit_schrabidium, 'E', ModItems.ingot_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_plate, 1), new Object[] { "E E", "EWE", "EEE", 'E', ModItems.ingot_euphemium, 'W', ModItems.watch });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.watch, 1), new Object[] { "LEL", "EWE", "LEL", 'E', ModItems.ingot_euphemium, 'L', new ItemStack(Items.dye, 1, 4), 'W', Items.clock });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_euphemium, 1), new Object[] { "EEE", "EAE", "EEE", 'E', ModItems.nugget_euphemium, 'A', Items.apple });

		GameRegistry.addRecipe(new ItemStack(ModItems.mask_of_infamy, 1), new Object[] { "III", "III", " I ", 'I', ModItems.plate_iron });
		GameRegistry.addRecipe(new ItemStack(ModItems.designator, 1), new Object[] { "  A", "#B#", "#B#", '#', ModItems.plate_iron, 'A', ModItems.plate_steel, 'B', ModItems.circuit_red_copper });
	}
	
	public static void AddSmeltingRec()
	{
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_uranium), new ItemStack(ModItems.ingot_uranium), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_titanium), new ItemStack(ModItems.ingot_titanium), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_copper), new ItemStack(ModItems.ingot_copper), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_tungsten), new ItemStack(ModItems.ingot_tungsten), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_aluminium), new ItemStack(ModItems.ingot_aluminium), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_lead), new ItemStack(ModItems.ingot_lead), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_beryllium), new ItemStack(ModItems.ingot_beryllium), 2.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 120.0F);

		GameRegistry.addSmelting(ModItems.powder_lead, new ItemStack(ModItems.ingot_lead), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_neptunium, new ItemStack(ModItems.ingot_neptunium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_schrabidium, new ItemStack(ModItems.ingot_schrabidium), 5.0F);
		
		GameRegistry.addSmelting(Items.bone, new ItemStack(Items.slime_ball, 3), 1.5F);
		GameRegistry.addSmelting(new ItemStack(Items.dye, 1, 15), new ItemStack(Items.slime_ball, 1), 0.5F);
	}
}
