package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * For foods, drinks or other consumables
 * @author hbm
 */
public class ConsumableRecipes {
	
	public static void register() {
		
		//Airstikes
		GameRegistry.addRecipe(new ItemStack(ModItems.bomb_caller, 1, 0), new Object[] { "TTT", "TRT", "TTT", 'T', Blocks.tnt, 'R', ModItems.detonator_laser });
		GameRegistry.addRecipe(new ItemStack(ModItems.bomb_caller, 1, 1), new Object[] { "TTT", "TRT", "TTT", 'T', ModItems.grenade_gascan, 'R', ModItems.detonator_laser });
		GameRegistry.addRecipe(new ItemStack(ModItems.bomb_caller, 1, 2), new Object[] { "TTT", "TRT", "TTT", 'T', ModItems.pellet_gas, 'R', ModItems.detonator_laser });
		GameRegistry.addRecipe(new ItemStack(ModItems.bomb_caller, 1, 3), new Object[] { "TRT", 'T', ModItems.grenade_cloud, 'R', ModItems.detonator_laser });
		GameRegistry.addRecipe(new ItemStack(ModItems.bomb_caller, 1, 4), new Object[] { "TRT", 'T', ModItems.gun_fatman_ammo, 'R', ModItems.detonator_laser });

		//Food
		GameRegistry.addRecipe(new ItemStack(ModItems.bomb_waffle, 1), new Object[] { "WEW", "MPM", "WEW", 'W', Items.wheat, 'E', Items.egg, 'M', Items.milk_bucket, 'P', ModItems.man_core });
		GameRegistry.addRecipe(new ItemStack(ModItems.schnitzel_vegan, 3), new Object[] { "RWR", "WPW", "RWR", 'W', ModItems.nuclear_waste, 'R', Items.reeds, 'P', Items.pumpkin_seeds });
		GameRegistry.addRecipe(new ItemStack(ModItems.cotton_candy, 2), new Object[] { " S ", "SPS", " H ", 'P', ModItems.nugget_pu239, 'S', Items.sugar, 'H', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', ModItems.nugget_schrabidium, 'A', Items.apple });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 1), new Object[] { "SSS", "SAS", "SSS", 'S', ModItems.ingot_schrabidium, 'A', Items.apple });
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 2), new Object[] { "SSS", "SAS", "SSS", 'S', Item.getItemFromBlock(ModBlocks.block_schrabidium), 'A', Items.apple });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.apple_lead, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', "nuggetLead", 'A', Items.apple }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.apple_lead, 1, 1), new Object[] { "SSS", "SAS", "SSS", 'S', "ingotLead", 'A', Items.apple }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.apple_lead, 1, 2), new Object[] { "SSS", "SAS", "SSS", 'S', "blockLead", 'A', Items.apple }));
		GameRegistry.addRecipe(new ItemStack(ModItems.apple_euphemium, 1), new Object[] { "EEE", "EAE", "EEE", 'E', ModItems.nugget_euphemium, 'A', Items.apple });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 0), new Object[] { Items.gold_nugget, Items.paper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 1), new Object[] { Items.gold_nugget, Items.gold_nugget, Items.gold_nugget, Items.paper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 2), new Object[] { Items.gold_ingot, Items.gold_ingot, Items.gold_nugget, Items.gold_nugget, Items.paper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.glowing_stew, 1), new Object[] { Items.bowl, Item.getItemFromBlock(ModBlocks.mush), Item.getItemFromBlock(ModBlocks.mush) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.balefire_scrambled, 1), new Object[] { Items.bowl, ModItems.egg_balefire });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.balefire_and_ham, 1), new Object[] { ModItems.balefire_scrambled, Items.cooked_beef });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.med_ipecac, 1), new Object[] { Items.glass_bottle, Items.nether_wart });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.med_ptsd, 1), new Object[] { ModItems.med_ipecac });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pancake, 1), new Object[] { "dustRedstone", "dustDiamond", Items.wheat, ModItems.bolt_tungsten, ModItems.wire_copper, "plateSteel" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pancake, 1), new Object[] { "dustRedstone", "dustEmerald", Items.wheat, ModItems.bolt_tungsten, ModItems.wire_copper, "plateSteel" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chocolate_milk, 1), new Object[] { "paneGlass", new ItemStack(Items.dye, 1, 3), Items.milk_bucket, ModBlocks.block_niter, ModItems.sulfur, ModItems.sulfur, ModItems.sulfur, ModItems.powder_fire }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.loops), new Object[] { ModItems.flame_pony, Items.wheat, Items.sugar });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.loop_stew), new Object[] { ModItems.loops, ModItems.can_smart, Items.bowl });
		
		//Cans
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.can_empty, 1), new Object[] { "P", "P", 'P', "plateAluminum" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_smart, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.niter });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_creature, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.canister_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_redbomb, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.pellet_cluster });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_mrsugar, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.fluorite });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_overcharge, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.sulfur });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.can_luna, 1), new Object[] { ModItems.can_empty, Items.potionitem, Items.sugar, ModItems.powder_meteorite_tiny });

		//Canteens
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.canteen_13, 1), new Object[] { "O", "P", 'O', Items.potionitem, 'P', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.canteen_vodka, 1), new Object[] { "O", "P", 'O', Items.potato, 'P', "plateSteel" }));

		//Soda
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bottle_empty, 6), new Object[] { " G ", "G G", "GGG", 'G', "paneGlass" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.bottle_nuka, 1), new Object[] { ModItems.bottle_empty, Items.potionitem, Items.sugar, "dustCoal" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle_cherry, 1), new Object[] { ModItems.bottle_empty, Items.potionitem, Items.sugar, Items.redstone });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle_quantum, 1), new Object[] { ModItems.bottle_empty, Items.potionitem, Items.sugar, ModItems.trinitite });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle_sparkle), new Object[] { ModItems.bottle_nuka, Items.carrot, Items.gold_nugget });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bottle_rad), new Object[] { ModItems.bottle_quantum, Items.carrot, Items.gold_nugget });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bottle2_empty, 6), new Object[] { " G ", "G G", "G G", 'G', "paneGlass" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.bottle2_korl, 1), new Object[] { ModItems.bottle2_empty, Items.potionitem, Items.sugar, "dustCopper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.bottle2_fritz, 1), new Object[] { ModItems.bottle2_empty, Items.potionitem, Items.sugar, "dustTungsten" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.bottle2_korl_special, 1), new Object[] { ModItems.bottle2_empty, Items.potionitem, Items.sugar, "dustCopper", ModItems.powder_strontium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.bottle2_fritz_special, 1), new Object[] { ModItems.bottle2_empty, Items.potionitem, Items.sugar, "dustTungsten", ModItems.powder_thorium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.bottle2_sunset, 1), new Object[] { ModItems.bottle2_empty, Items.potionitem, Items.sugar, "dustGold" }));

		//Syringes
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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', ModItems.bottle_nuka, 'P', "plateSteel", 'S', ModItems.syringe_metal_stimpak, 'L', Items.leather }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', ModItems.bottle_nuka, 'P', "plateSteel", 'S', ModItems.syringe_metal_stimpak, 'L', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', ModItems.bottle_cherry, 'P', "plateSteel", 'S', ModItems.syringe_metal_stimpak, 'L', Items.leather }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', ModItems.bottle_cherry, 'P', "plateSteel", 'S', ModItems.syringe_metal_stimpak, 'L', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.syringe_taint), new Object[] { ModItems.bottle2_empty, ModItems.syringe_metal_empty, ModItems.ducttape, ModItems.powder_magic, "nuggetSchrabidium", Items.potionitem }));
		
		//Medicine
		GameRegistry.addRecipe(new ItemStack(ModItems.pill_iodine, 8), new Object[] { "IF", 'I', ModItems.powder_iodine, 'F', ModItems.fluorite });
		GameRegistry.addRecipe(new ItemStack(ModItems.plan_c, 1), new Object[] { "PFP", 'P', ModItems.powder_poison, 'F', ModItems.fluorite });
		GameRegistry.addRecipe(new ItemStack(ModItems.radx, 1), new Object[] { "P", "F", 'P', ModItems.powder_coal, 'F', ModItems.fluorite });
		
		//Med bags
		GameRegistry.addRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', Items.leather, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.syringe_antidote });
		GameRegistry.addRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', Items.leather, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.pill_iodine });
		GameRegistry.addRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LL", "SI", "LL", 'L', Items.leather, 'S', ModItems.syringe_metal_super, 'I', ModItems.radaway });
		GameRegistry.addRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', ModItems.plate_polymer, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.syringe_antidote });
		GameRegistry.addRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', ModItems.plate_polymer, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.pill_iodine });
		GameRegistry.addRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LL", "SI", "LL", 'L', ModItems.plate_polymer, 'S', ModItems.syringe_metal_super, 'I', ModItems.radaway });
		
		//Radaway
		GameRegistry.addRecipe(new ItemStack(ModItems.radaway, 1), new Object[] { "S", "M", "W", 'S', ModItems.plate_polymer, 'M', ModBlocks.mush, 'W', Items.potionitem });
		GameRegistry.addRecipe(new ItemStack(ModItems.radaway_strong, 1), new Object[] { "S", "M", "W", 'S', Items.pumpkin_seeds, 'M', ModBlocks.mush, 'W', ModItems.radaway });
		GameRegistry.addRecipe(new ItemStack(ModItems.radaway_flush, 1), new Object[] { "S", "M", "W", 'S', ModItems.powder_iodine, 'M', ModBlocks.mush, 'W', ModItems.radaway_strong });

		//Cladding
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.cladding_paint, 1), new Object[] { "dustLead", Items.clay_ball, Items.glass_bottle }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cladding_rubber, 1), new Object[] { "RCR", "CDC", "RCR", 'R', ModItems.plate_polymer, 'C', "dustCoal", 'D', ModItems.ducttape }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cladding_lead, 1), new Object[] { "DPD", "PRP", "DPD", 'R', ModItems.cladding_rubber, 'P', "plateLead", 'D', ModItems.ducttape }));
		GameRegistry.addRecipe(new ItemStack(ModItems.cladding_desh, 1), new Object[] { "DPD", "PRP", "DPD", 'R', ModItems.cladding_lead, 'P', ModItems.plate_desh, 'D', ModItems.ducttape });
		
		//Stealth boy
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stealth_boy, 1), new Object[] { " B", "LI", "LC", 'B', Item.getItemFromBlock(Blocks.stone_button), 'L', Items.leather, 'I', "ingotSteel", 'C', ModItems.circuit_red_copper }));
	}
}
