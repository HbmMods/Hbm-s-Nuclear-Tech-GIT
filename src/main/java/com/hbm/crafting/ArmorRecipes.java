package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.items.ModItems;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * For player armor
 * @author hbm
 */
public class ArmorRecipes {
	
	public static void register() {
		
		//Armor mod table
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_armor_table, 1), new Object[] { "PPP", "TCT", "TST", 'P', "plateSteel", 'T', "ingotTungsten", 'C', Blocks.crafting_table, 'S', "blockSteel" }));
		
		// Armor plates
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.plate_armor_du, 1, 0), new Object[] { "BPB", "PAP", "BPB", 'B', ModItems.bolt_dura_steel, 'A', ModItems.plate_armor_ajr, 'P', ModItems.plate_du_dioxide});
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.plate_armor_du, 1, 1), new Object[] { "BPB", "PAP", "BPB", 'B', ModItems.bolt_staballoy, 'P', new ItemStack(ModItems.plate_armor_du, 1, 0), 'A', ModItems.ingot_du_dioxide });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.plate_armor_du, 1, 2), new Object[] { "BPB", "PAP", "BPB", 'B', ModItems.ingot_staballoy, 'A', new ItemStack(ModItems.plate_armor_du, 1, 1), 'P', ModItems.ingot_ferrouranium});
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.plate_armor_du, 1, 3), new Object[] { "BPB", "PAP", "BPB", 'A', ModBlocks.block_staballoy, 'B', new ItemStack(ModItems.plate_armor_du, 1, 2), 'P', ModItems.plate_combine_steel});
		
		//Regular armor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.steel_helmet, 1), new Object[] { "EEE", "E E", 'E', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.steel_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.steel_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.steel_boots, 1), new Object[] { "E E", "E E", 'E', "ingotSteel" }));
		basicArmorSet(new Item[] { ModItems.titanium_helmet, ModItems.titanium_plate, ModItems.titanium_legs, ModItems.titanium_boots }, ModItems.ingot_titanium);
		Item[] aAlloySet = new Item[] { ModItems.alloy_helmet, ModItems.alloy_plate, ModItems.alloy_legs, ModItems.alloy_boots };
		basicArmorSet(aAlloySet, ModItems.ingot_advanced_alloy);
		basicArmorSet(new Item[] { ModItems.cmb_helmet, ModItems.cmb_plate, ModItems.cmb_legs, ModItems.cmb_boots }, ModItems.ingot_combine_steel);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.security_helmet, 1), new Object[] { "SSS", "IGI", 'S', "plateSteel", 'I', ModItems.plate_polymer, 'G', "paneGlass" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.security_plate, 1), new Object[] { "KWK", "IKI", "WKW", 'K', ModItems.plate_kevlar, 'I', ModItems.ingot_polymer, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addRecipe(new ItemStack(ModItems.security_legs, 1), new Object[] { "IWI", "K K", "W W", 'K', ModItems.plate_kevlar, 'I', ModItems.ingot_polymer, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.security_boots, 1), new Object[] { "P P", "I I", 'P', "plateSteel", 'I', ModItems.plate_polymer }));
		Item[] cobaltSet = new Item[] { ModItems.cobalt_helmet, ModItems.cobalt_plate, ModItems.cobalt_legs, ModItems.cobalt_boots };
		basicArmorSet(cobaltSet, ModItems.ingot_cobalt);
		GameRegistry.addRecipe(new ItemStack(ModItems.dnt_helmet, 1), new Object[] { "EEE", "EE ", 'E', ModItems.ingot_dineutronium });
		GameRegistry.addRecipe(new ItemStack(ModItems.dnt_plate, 1), new Object[] { "EE ", "EEE", "EEE", 'E', ModItems.ingot_dineutronium });
		GameRegistry.addRecipe(new ItemStack(ModItems.dnt_legs, 1), new Object[] { "EE ", "EEE", "E E", 'E', ModItems.ingot_dineutronium });
		GameRegistry.addRecipe(new ItemStack(ModItems.dnt_boots, 1), new Object[] { "  E", "E  ", "E E", 'E', ModItems.ingot_dineutronium });
		GameRegistry.addRecipe(new ItemStack(ModItems.zirconium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_zirconium });
		Item[] ferrouraniumSet = new Item[] {ModItems.ferrouranium_helmet, ModItems.ferrouranium_plate, ModItems.ferrouranium_legs, ModItems.ferrouranium_boots};
		upgradeArmorSet(ferrouraniumSet, aAlloySet, ModItems.ingot_ferrouranium);
		
		//Power armor
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.t45_helmet), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_titanium, 'C', ModItems.circuit_targeting_tier3, 'I', ModItems.plate_polymer, 'X', ModItems.gas_mask_m65, 'B', ModItems.titanium_helmet });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.t45_plate), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'T', ModItems.gas_empty, 'B', ModItems.titanium_plate });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.t45_legs), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_legs });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.t45_boots), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_boots });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.ajr_helmet), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_ajr, 'C', ModItems.circuit_targeting_tier4, 'I', ModItems.ingot_polymer, 'X', ModItems.gas_mask_m65, 'B', ModItems.alloy_helmet });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.ajr_plate), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'T', ModItems.gas_empty, 'B', ModItems.alloy_plate });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.ajr_legs), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_legs });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.ajr_boots), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_boots });
		GameRegistry.addRecipe(new ShapelessOreRecipe(IBatteryItem.emptyBattery(ModItems.ajro_helmet), new Object[] { ModItems.ajr_helmet, "dyeRed", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(IBatteryItem.emptyBattery(ModItems.ajro_plate), new Object[] { ModItems.ajr_plate, "dyeRed", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(IBatteryItem.emptyBattery(ModItems.ajro_legs), new Object[] { ModItems.ajr_legs, "dyeRed", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(IBatteryItem.emptyBattery(ModItems.ajro_boots), new Object[] { ModItems.ajr_boots, "dyeRed", "dyeBlack" }));
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.bj_helmet), new Object[] { "SBS", " C ", " I ", 'S', Items.string, 'B', new ItemStack(Blocks.wool, 1, 15), 'C', ModItems.circuit_targeting_tier4, 'I', ModItems.ingot_starmetal });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.bj_plate), new Object[] { "N N", "MSM", "NCN", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_plate, 'C', ModItems.circuit_targeting_tier5 });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.bj_plate_jetpack), new Object[] { "NFN", "TPT", "ICI", 'N', ModItems.plate_armor_lunar, 'F', ModItems.fins_quad_titanium, 'T', ModItems.gas_xenon, 'P', ModItems.bj_plate, 'I', ModItems.mp_thruster_10_xenon, 'C', ModItems.crystal_phosphorus });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.bj_legs), new Object[] { "MBM", "NSN", "N N", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_legs, 'B', ModBlocks.block_starmetal });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.bj_boots), new Object[] { "N N", "BSB", 'N', ModItems.plate_armor_lunar, 'S', ModItems.starmetal_boots, 'B', ModBlocks.block_starmetal });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.hev_helmet), new Object[] { "PPC", "PBP", "IFI", 'P', ModItems.plate_armor_hev, 'C', ModItems.circuit_targeting_tier4, 'B', ModItems.titanium_helmet, 'I', ModItems.plate_polymer, 'F', ModItems.gas_mask_filter });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.hev_plate), new Object[] { "MPM", "IBI", "PPP", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_plate, 'I', ModItems.ingot_polymer, 'M', ModItems.motor_desh });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.hev_legs), new Object[] { "MPM", "IBI", "P P", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_legs, 'I', ModItems.ingot_polymer, 'M', ModItems.motor_desh });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.hev_boots), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_boots });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.fau_helmet), new Object[] { "PWP", "PBP", "FSF", 'P', ModItems.plate_armor_fau, 'W', new ItemStack(Blocks.wool, 1, 14), 'B', ModItems.starmetal_helmet, 'F', ModItems.gas_mask_filter, 'S', ModItems.pipes_steel });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.fau_plate), new Object[] { "MCM", "PBP", "PSP", 'M', ModItems.motor_desh, 'C', ModItems.demon_core_closed, 'P', ModItems.plate_armor_fau, 'B', ModItems.starmetal_plate, 'S', ModBlocks.ancient_scrap });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.fau_legs), new Object[] { "MPM", "PBP", "PDP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_fau, 'B', ModItems.starmetal_legs, 'D', ModItems.billet_polonium });
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.fau_boots), new Object[] { "PDP", "PBP", 'P', ModItems.plate_armor_fau, 'D', ModItems.billet_polonium, 'B', ModItems.starmetal_boots });

		//Euphemium armor
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.plate_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_plate, 1), new Object[] { "EWE", "EEE", "EEE", 'E', ModItems.plate_euphemium, 'W', ModItems.watch });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.plate_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.plate_euphemium });
		
		//Jetpacks
		GameRegistry.addRecipe(new ItemStack(ModItems.jetpack_fly, 1), new Object[] { "ACA", "TLT", "D D", 'A', ModItems.cap_aluminium, 'C', ModItems.circuit_targeting_tier1, 'T', ModItems.tank_steel, 'L', Items.leather, 'D', ModItems.thruster_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.jetpack_break, 1), new Object[] { "ICI", "TJT", "I I", 'C', ModItems.circuit_targeting_tier2, 'T', ModItems.ingot_dura_steel, 'J', ModItems.jetpack_fly, 'I', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.jetpack_vector, 1), new Object[] { "TCT", "MJM", "B B", 'C', ModItems.circuit_targeting_tier3, 'T', ModItems.tank_steel, 'J', ModItems.jetpack_break, 'M', ModItems.motor, 'B', ModItems.bolt_dura_steel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.jetpack_boost, 1), new Object[] { "PCP", "DJD", "PAP", 'C', ModItems.circuit_targeting_tier4, 'P', ModItems.plate_saturnite, 'D', "ingotDesh", 'J', ModItems.jetpack_vector, 'A', ModItems.board_copper }));
		
		//Hazmat
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet, 1), new Object[] { "EEE", "EIE", "FPF", 'E', ModItems.hazmat_cloth, 'I', "paneGlass", 'P', "plateSteel", 'F', ModItems.filter_coal }));
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet_red, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_red, 'I', "paneGlass", 'P', "plateSteel", 'F', ModItems.gas_mask_filter }));
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_plate_red, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_red });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_legs_red, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_boots_red, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet_grey, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_grey, 'I', "paneGlass", 'P', "plateSteel", 'F', ModItems.gas_mask_filter }));
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_plate_grey, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_grey });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_legs_grey, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_boots_grey, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
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
		
		//Liquidator Suit
		GameRegistry.addRecipe(new ItemStack(ModItems.liquidator_helmet, 1), new Object[] { "III", "CBC", "IFI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_helmet_grey, 'F', ModItems.gas_mask_filter });
		GameRegistry.addRecipe(new ItemStack(ModItems.liquidator_plate, 1), new Object[] { "ICI", "TBT", "ICI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_plate_grey, 'T', ModItems.gas_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.liquidator_legs, 1), new Object[] { "III", "CBC", "I I", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_legs_grey });
		GameRegistry.addRecipe(new ItemStack(ModItems.liquidator_boots, 1), new Object[] { "ICI", "IBI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_boots_grey });

		//Masks
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.goggles, 1), new Object[] { "P P", "GPG", 'G', "paneGlass", 'P', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_mask, 1), new Object[] { "PPP", "GPG", " F ", 'G', "paneGlass", 'P', "plateSteel", 'F', ModItems.gas_mask_filter }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_mask_m65, 1), new Object[] { "PPP", "GPG", " F ", 'G', "paneGlass", 'P', ModItems.plate_polymer, 'F', ModItems.gas_mask_filter }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mask_of_infamy, 1), new Object[] { "III", "III", " I ", 'I', "plateIron" }));

		//Capes
		GameRegistry.addRecipe(new ItemStack(ModItems.cape_radiation, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), 'D', new ItemStack(Items.dye, 1, 11), 'I', ModItems.nuclear_waste });
		GameRegistry.addRecipe(new ItemStack(ModItems.cape_gasmask, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), 'D', new ItemStack(Items.dye, 1, 0), 'I', ModItems.gas_mask });
		GameRegistry.addRecipe(new ItemStack(ModItems.cape_schrabidium, 1), new Object[] { "W W", "WIW", "WDW", 'W', ModItems.ingot_schrabidium, 'D', new ItemStack(Items.dye, 1, 0), 'I', ModItems.circuit_red_copper });
		
		GameRegistry.addRecipe(new ItemStack(ModItems.robes_boots, 1), new Object[] { "R R", "P P", 'R', ModItems.rag, 'P', ModItems.plate_polymer });
		//Configged
		if(GeneralConfig.enableBabyMode) {
			basicArmorSet(new Item[] { ModItems.starmetal_helmet, ModItems.starmetal_plate, ModItems.starmetal_legs, ModItems.starmetal_boots }, ModItems.ingot_starmetal);
			basicArmorSet(new Item[] { ModItems.schrabidium_helmet, ModItems.schrabidium_plate, ModItems.schrabidium_legs, ModItems.schrabidium_boots }, ModItems.ingot_schrabidium);
		} else {
			upgradeArmorSet(new Item[] { ModItems.starmetal_helmet, ModItems.starmetal_plate, ModItems.starmetal_legs, ModItems.starmetal_boots }, cobaltSet, ModItems.ingot_starmetal);
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_helmet, 1), new Object[] { "EEE", "ESE", " P ", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_helmet, 'P', ModItems.pellet_charged });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_plate, 1), new Object[] { "ESE", "EPE", "EEE", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_plate, 'P', ModItems.pellet_charged });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_legs, 1), new Object[] { "EEE", "ESE", "EPE", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_legs, 'P', ModItems.pellet_charged });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_boots, 1), new Object[] { "EPE", "ESE", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_boots, 'P', ModItems.pellet_charged });
		}
		
		// Armor mods
		GameRegistry.addRecipe(IBatteryItem.emptyBattery(ModItems.goggles_night_vision), "BPC", "IGI", 'B', ModItems.battery_lithium, 'P', ModItems.ingot_polymer, 'C', ModItems.circuit_copper, 'I', ModItems.plate_polymer, 'G', ModItems.goggles);
	}
	/** Register an armor set using vanilla style recipes
	 * @param outs - Must be in the order: helmet, chestplate, leggings, then boots
	 * @param material - The material the set is made of**/
	public static void basicArmorSet(Item[] outs, Item material)
	{
		if (outs.length != 4)
			throw new IllegalArgumentException("Output array size must be equal to 4!");
		
		GameRegistry.addRecipe(new ItemStack(outs[0]), new Object[] { "MMM", "M M", 'M', material });
		GameRegistry.addRecipe(new ItemStack(outs[1]), new Object[] { "M M", "MMM", "MMM", 'M', material });
		GameRegistry.addRecipe(new ItemStack(outs[2]), new Object[] { "MMM", "M M", "M M", 'M', material });
		GameRegistry.addRecipe(new ItemStack(outs[3]), new Object[] { "M M", "M M", 'M', material });
	}
	/**
	 * Register an upgrade based armor set still using vanilla style recipes
	 * @param outs - Must be in the order: helmet, chestplate, leggings, then boots
	 * @param priorTier - Previous tier of armor, must be in the same order as before
	 * @param material - Material the set is made of
	 */
	public static void upgradeArmorSet(Item[] outs, Item[] priorTier, Item material)
	{
		if (outs.length != 4)
			throw new IllegalArgumentException("Output array size must be equal to 4!");
		if (priorTier.length != 4)
			throw new IllegalArgumentException("Input array size must be equal to 4!");
		
		GameRegistry.addRecipe(new ItemStack(outs[0]), new Object[] { "MMM", "MPM", 'M', material, 'P', priorTier[0] });
		GameRegistry.addRecipe(new ItemStack(outs[1]), new Object[] { "MPM", "MMM", "MMM", 'M', material, 'P', priorTier[1] });
		GameRegistry.addRecipe(new ItemStack(outs[2]), new Object[] { "MMM", "MPM", "M M", 'M', material, 'P', priorTier[2] });
		GameRegistry.addRecipe(new ItemStack(outs[3]), new Object[] { "M M", "MPM", 'M', material, 'P', priorTier[3] });
	}
}
