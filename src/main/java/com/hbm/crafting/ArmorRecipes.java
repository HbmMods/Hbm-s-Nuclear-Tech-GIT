package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.OreDictManager;
import com.hbm.items.ModItems;

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
		
		//Regular armor
		addHelmet(	"ingotSteel", ModItems.steel_helmet);
		addChest(	"ingotSteel", ModItems.steel_plate);
		addLegs(	"ingotSteel", ModItems.steel_legs);
		addBoots(	"ingotSteel", ModItems.steel_boots);
		addHelmet(	"ingotTitanium", ModItems.titanium_helmet);
		addChest(	"ingotTitanium", ModItems.titanium_plate);
		addLegs(	"ingotTitanium", ModItems.titanium_legs);
		addBoots(	"ingotTitanium", ModItems.titanium_boots);
		addHelmet(	ModItems.ingot_advanced_alloy, ModItems.alloy_helmet);
		addChest(	ModItems.ingot_advanced_alloy, ModItems.alloy_plate);
		addLegs(	ModItems.ingot_advanced_alloy, ModItems.alloy_legs);
		addBoots(	ModItems.ingot_advanced_alloy, ModItems.alloy_boots);
		addHelmet(	ModItems.ingot_combine_steel, ModItems.cmb_helmet);
		addChest(	ModItems.ingot_combine_steel, ModItems.cmb_plate);
		addLegs(	ModItems.ingot_combine_steel, ModItems.cmb_legs);
		addBoots(	ModItems.ingot_combine_steel, ModItems.cmb_boots);
		addHelmet(	"ingotCobalt", ModItems.cobalt_helmet);
		addChest(	"ingotCobalt", ModItems.cobalt_plate);
		addLegs(	"ingotCobalt", ModItems.cobalt_legs);
		addBoots(	"ingotCobalt", ModItems.cobalt_boots);
		addHelmet(	ModItems.rag, ModItems.robes_helmet);
		addChest(	ModItems.rag, ModItems.robes_plate);
		addLegs(	ModItems.rag, ModItems.robes_legs);
		GameRegistry.addRecipe(new ItemStack(ModItems.robes_boots, 1), new Object[] { "R R", "P P", 'R', ModItems.rag, 'P', ModItems.plate_polymer });
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.security_helmet, 1), new Object[] { "SSS", "IGI", 'S', "plateSteel", 'I', ModItems.plate_polymer, 'G', "paneGlass" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.security_plate, 1), new Object[] { "KWK", "IKI", "WKW", 'K', ModItems.plate_kevlar, 'I', ModItems.ingot_polymer, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addRecipe(new ItemStack(ModItems.security_legs, 1), new Object[] { "IWI", "K K", "W W", 'K', ModItems.plate_kevlar, 'I', ModItems.ingot_polymer, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.security_boots, 1), new Object[] { "P P", "I I", 'P', "plateSteel", 'I', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ItemStack(ModItems.dnt_helmet, 1), new Object[] { "EEE", "EE ", 'E', ModItems.ingot_dineutronium });
		GameRegistry.addRecipe(new ItemStack(ModItems.dnt_plate, 1), new Object[] { "EE ", "EEE", "EEE", 'E', ModItems.ingot_dineutronium });
		GameRegistry.addRecipe(new ItemStack(ModItems.dnt_legs, 1), new Object[] { "EE ", "EEE", "E E", 'E', ModItems.ingot_dineutronium });
		GameRegistry.addRecipe(new ItemStack(ModItems.dnt_boots, 1), new Object[] { "  E", "E  ", "E E", 'E', ModItems.ingot_dineutronium });
		GameRegistry.addRecipe(new ItemStack(ModItems.zirconium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_zirconium });

		//Power armor
		GameRegistry.addRecipe(new ItemStack(ModItems.t45_helmet, 1), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_titanium, 'C', ModItems.circuit_targeting_tier3, 'I', ModItems.plate_polymer, 'X', ModItems.gas_mask_m65, 'B', ModItems.titanium_helmet });
		GameRegistry.addRecipe(new ItemStack(ModItems.t45_plate, 1), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'T', ModItems.gas_empty, 'B', ModItems.titanium_plate });
		GameRegistry.addRecipe(new ItemStack(ModItems.t45_legs, 1), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_legs });
		GameRegistry.addRecipe(new ItemStack(ModItems.t45_boots, 1), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_boots });
		GameRegistry.addRecipe(new ItemStack(ModItems.ajr_helmet, 1), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_ajr, 'C', ModItems.circuit_targeting_tier4, 'I', ModItems.ingot_polymer, 'X', ModItems.gas_mask_m65, 'B', ModItems.alloy_helmet });
		GameRegistry.addRecipe(new ItemStack(ModItems.ajr_plate, 1), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'T', ModItems.gas_empty, 'B', ModItems.alloy_plate });
		GameRegistry.addRecipe(new ItemStack(ModItems.ajr_legs, 1), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_legs });
		GameRegistry.addRecipe(new ItemStack(ModItems.ajr_boots, 1), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_boots });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ajro_helmet, 1), new Object[] { ModItems.ajr_helmet, "dyeRed", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ajro_plate, 1), new Object[] { ModItems.ajr_plate, "dyeRed", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ajro_legs, 1), new Object[] { ModItems.ajr_legs, "dyeRed", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ajro_boots, 1), new Object[] { ModItems.ajr_boots, "dyeRed", "dyeBlack" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.bj_helmet, 1), new Object[] { "SBS", " C ", " I ", 'S', Items.string, 'B', new ItemStack(Blocks.wool, 1, 15), 'C', ModItems.circuit_targeting_tier4, 'I', ModItems.ingot_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.bj_plate, 1), new Object[] { "N N", "MSM", "NCN", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_plate, 'C', ModItems.circuit_targeting_tier5 });
		GameRegistry.addRecipe(new ItemStack(ModItems.bj_plate_jetpack, 1), new Object[] { "NFN", "TPT", "ICI", 'N', ModItems.plate_armor_lunar, 'F', ModItems.fins_quad_titanium, 'T', new ItemStack(ModItems.fluid_tank_full, 1, FluidType.XENON.ordinal()), 'P', ModItems.bj_plate, 'I', ModItems.mp_thruster_10_xenon, 'C', ModItems.crystal_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.bj_legs, 1), new Object[] { "MBM", "NSN", "N N", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_legs, 'B', ModBlocks.block_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.bj_boots, 1), new Object[] { "N N", "BSB", 'N', ModItems.plate_armor_lunar, 'S', ModItems.starmetal_boots, 'B', ModBlocks.block_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.hev_helmet, 1), new Object[] { "PPC", "PBP", "IFI", 'P', ModItems.plate_armor_hev, 'C', ModItems.circuit_targeting_tier4, 'B', ModItems.titanium_helmet, 'I', ModItems.plate_polymer, 'F', ModItems.gas_mask_filter });
		GameRegistry.addRecipe(new ItemStack(ModItems.hev_plate, 1), new Object[] { "MPM", "IBI", "PPP", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_plate, 'I', ModItems.ingot_polymer, 'M', ModItems.motor_desh });
		GameRegistry.addRecipe(new ItemStack(ModItems.hev_legs, 1), new Object[] { "MPM", "IBI", "P P", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_legs, 'I', ModItems.ingot_polymer, 'M', ModItems.motor_desh });
		GameRegistry.addRecipe(new ItemStack(ModItems.hev_boots, 1), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_boots });
		GameRegistry.addRecipe(new ItemStack(ModItems.fau_helmet, 1), new Object[] { "PWP", "PBP", "FSF", 'P', ModItems.plate_armor_fau, 'W', new ItemStack(Blocks.wool, 1, 14), 'B', ModItems.starmetal_helmet, 'F', ModItems.gas_mask_filter, 'S', ModItems.pipes_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.fau_plate, 1), new Object[] { "MCM", "PBP", "PSP", 'M', ModItems.motor_desh, 'C', ModItems.demon_core_closed, 'P', ModItems.plate_armor_fau, 'B', ModItems.starmetal_plate, 'S', ModBlocks.ancient_scrap });
		GameRegistry.addRecipe(new ItemStack(ModItems.fau_legs, 1), new Object[] { "MPM", "PBP", "PDP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_fau, 'B', ModItems.starmetal_legs, 'D', ModItems.billet_polonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.fau_boots, 1), new Object[] { "PDP", "PBP", 'P', ModItems.plate_armor_fau, 'D', ModItems.billet_polonium, 'B', ModItems.starmetal_boots });
		GameRegistry.addRecipe(new ItemStack(ModItems.dns_helmet, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_helmet, 'C', ModItems.circuit_targeting_tier6 });
		GameRegistry.addRecipe(new ItemStack(ModItems.dns_plate, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_plate_jetpack, 'C', ModItems.singularity_spark });
		GameRegistry.addRecipe(new ItemStack(ModItems.dns_legs, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_legs, 'C', ModItems.coin_worm });
		GameRegistry.addRecipe(new ItemStack(ModItems.dns_boots, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_boots, 'C', ModItems.demon_core_closed });

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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet, 1), new Object[] { "EEE", "EIE", " P ", 'E', ModItems.hazmat_cloth, 'I', "paneGlass", 'P', "plateIron" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet_red, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_red, 'I', "paneGlass", 'F', "plateIron" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_plate_red, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_red });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_legs_red, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_boots_red, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet_grey, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_grey, 'I', "paneGlass", 'F', "plateIron" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_plate_grey, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_grey });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_legs_grey, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_boots_grey, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.asbestos_helmet, 1), new Object[] { "EEE", "EIE", 'E', ModItems.asbestos_cloth, 'I', "plateGold" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.asbestos_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.asbestos_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.asbestos_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.asbestos_cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.asbestos_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.asbestos_cloth });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_paa_helmet, 1), new Object[] { "EEE", "IEI", " P ", 'E', ModItems.plate_paa, 'I', "paneGlass", 'P', "plateIron" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_paa_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.plate_paa });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_paa_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.plate_paa });
		GameRegistry.addRecipe(new ItemStack(ModItems.hazmat_paa_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.plate_paa });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.paa_plate, 1), new Object[] { "E E", "NEN", "ENE", 'E', ModItems.plate_paa, 'N', OreDictManager.getReflector() }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.paa_legs, 1), new Object[] { "EEE", "N N", "E E", 'E', ModItems.plate_paa, 'N', OreDictManager.getReflector() }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.paa_boots, 1), new Object[] { "E E", "N N", 'E', ModItems.plate_paa, 'N', OreDictManager.getReflector() }));
		
		//Liquidator Suit
		GameRegistry.addRecipe(new ItemStack(ModItems.liquidator_helmet, 1), new Object[] { "III", "CBC", "III", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_helmet_grey });
		GameRegistry.addRecipe(new ItemStack(ModItems.liquidator_plate, 1), new Object[] { "ICI", "TBT", "ICI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_plate_grey, 'T', ModItems.gas_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.liquidator_legs, 1), new Object[] { "III", "CBC", "I I", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_legs_grey });
		GameRegistry.addRecipe(new ItemStack(ModItems.liquidator_boots, 1), new Object[] { "ICI", "IBI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_boots_grey });

		//Masks
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.goggles, 1), new Object[] { "P P", "GPG", 'G', "paneGlass", 'P', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_mask, 1), new Object[] { "PPP", "GPG", " F ", 'G', "paneGlass", 'P', "plateSteel", 'F', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_mask_m65, 1), new Object[] { "PPP", "GPG", " F ", 'G', "paneGlass", 'P', ModItems.plate_polymer, 'F', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_mask_olde, 1), new Object[] { "PPP", "GPG", " F ", 'G', "paneGlass", 'P', Items.leather, 'F', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_mask_mono, 1), new Object[] { " P ", "PPP", " F ", 'P', ModItems.plate_polymer, 'F', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mask_of_infamy, 1), new Object[] { "III", "III", " I ", 'I', "plateIron" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ashglasses, 1), new Object[] { "I I", "GPG", 'I', ModItems.plate_polymer, 'G', ModBlocks.glass_ash, 'P', ModItems.ingot_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.mask_rag, 1), new Object[] { "RRR", 'R', ModItems.rag_damp });
		GameRegistry.addRecipe(new ItemStack(ModItems.mask_piss, 1), new Object[] { "RRR", 'R', ModItems.rag_piss });
		
		//Capes
		GameRegistry.addRecipe(new ItemStack(ModItems.cape_radiation, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), 'D', new ItemStack(Items.dye, 1, 11), 'I', ModItems.nuclear_waste });
		GameRegistry.addRecipe(new ItemStack(ModItems.cape_gasmask, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), 'D', new ItemStack(Items.dye, 1, 0), 'I', ModItems.gas_mask });
		GameRegistry.addRecipe(new ItemStack(ModItems.cape_schrabidium, 1), new Object[] { "W W", "WIW", "WDW", 'W', ModItems.ingot_schrabidium, 'D', new ItemStack(Items.dye, 1, 0), 'I', ModItems.circuit_red_copper });
		
		//Configged
		if(GeneralConfig.enableBabyMode) {
			addHelmet(	ModItems.ingot_starmetal, ModItems.starmetal_helmet);
			addChest(	ModItems.ingot_starmetal, ModItems.starmetal_plate);
			addLegs(	ModItems.ingot_starmetal, ModItems.starmetal_legs);
			addBoots(	ModItems.ingot_starmetal, ModItems.starmetal_boots);
			addHelmet(	ModItems.ingot_schrabidium, ModItems.schrabidium_helmet);
			addChest(	ModItems.ingot_schrabidium, ModItems.schrabidium_plate);
			addLegs(	ModItems.ingot_schrabidium, ModItems.schrabidium_legs);
			addBoots(	ModItems.ingot_schrabidium, ModItems.schrabidium_boots);
		} else {
			GameRegistry.addRecipe(new ItemStack(ModItems.starmetal_helmet, 1), new Object[] { "EEE", "ECE", 'E', ModItems.ingot_starmetal, 'C', ModItems.cobalt_helmet });
			GameRegistry.addRecipe(new ItemStack(ModItems.starmetal_plate, 1), new Object[] { "ECE", "EEE", "EEE", 'E', ModItems.ingot_starmetal, 'C', ModItems.cobalt_plate });
			GameRegistry.addRecipe(new ItemStack(ModItems.starmetal_legs, 1), new Object[] { "EEE", "ECE", "E E", 'E', ModItems.ingot_starmetal, 'C', ModItems.cobalt_legs });
			GameRegistry.addRecipe(new ItemStack(ModItems.starmetal_boots, 1), new Object[] { "E E", "ECE", 'E', ModItems.ingot_starmetal, 'C', ModItems.cobalt_boots });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_helmet, 1), new Object[] { "EEE", "ESE", " P ", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_helmet, 'P', ModItems.pellet_charged });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_plate, 1), new Object[] { "ESE", "EPE", "EEE", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_plate, 'P', ModItems.pellet_charged });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_legs, 1), new Object[] { "EEE", "ESE", "EPE", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_legs, 'P', ModItems.pellet_charged });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_boots, 1), new Object[] { "EPE", "ESE", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_boots, 'P', ModItems.pellet_charged });
		}
	}
	
	public static void addHelmet(Object ingot, Item pick) {
		addArmor(ingot, pick, patternHelmet);
	}
	public static void addChest(Object ingot, Item axe) {
		addArmor(ingot, axe, patternChetplate);
	}
	public static void addLegs(Object ingot, Item shovel) {
		addArmor(ingot, shovel, patternLeggings);
	}
	public static void addBoots(Object ingot, Item hoe) {
		addArmor(ingot, hoe, patternBoots);
	}
	
	public static void addArmor(Object ingot, Item armor, String[] pattern) {
		if(ingot instanceof Item)		addToolRec(ingot, armor, pattern);
		if(ingot instanceof ItemStack)	addToolRec(ingot, armor, pattern);
		if(ingot instanceof String)		addToolDict(ingot, armor, pattern);
	}
	public static void addToolRec(Object ingot, Item tool, String[] pattern) {
		GameRegistry.addRecipe(new ItemStack(tool), new Object[] { pattern, 'X', ingot });
	}
	public static void addToolDict(Object ingot, Item tool, String[] pattern) {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool), new Object[] { pattern, 'X', ingot }));
	}
	
	public static final String[] patternHelmet = new String[] {"XXX", "X X"};
	public static final String[] patternChetplate = new String[] {"X X", "XXX", "XXX"};
	public static final String[] patternLeggings = new String[] {"XXX", "X X", "X X"};
	public static final String[] patternBoots = new String[] {"X X", "X X"};
}
