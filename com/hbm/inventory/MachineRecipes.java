package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MachineRecipes {

	public MachineRecipes() {

	}

	public static MachineRecipes instance() {
		return new MachineRecipes();
	}

	public static ItemStack getFurnaceProcessingResult(Item item, Item item2) {
		return getFurnaceOutput(item, item2);
	}

	public static ItemStack getFurnaceOutput(Item item, Item item2) {
		if (MainRegistry.enableDebugMode) {
			if (item == Items.iron_ingot && item2 == Items.quartz
					|| item == Items.quartz && item2 == Items.iron_ingot) {
				return new ItemStack(ModBlocks.test_render, 1);
			}
		}

		if (item == ModItems.ingot_tungsten && item2 == Items.coal
				|| item == Items.coal && item2 == ModItems.ingot_tungsten) {
			return new ItemStack(ModItems.neutron_reflector, 2);
		}

		if (item == ModItems.ingot_lead && item2 == ModItems.ingot_copper
				|| item == ModItems.ingot_copper && item2 == ModItems.ingot_lead) {
			return new ItemStack(ModItems.neutron_reflector, 2);
		}

		if (item == ModItems.plate_lead && item2 == ModItems.plate_copper
				|| item == ModItems.plate_copper && item2 == ModItems.plate_lead) {
			return new ItemStack(ModItems.neutron_reflector, 1);
		}

		if (item == Items.iron_ingot && item2 == Items.coal || item == Items.coal && item2 == Items.iron_ingot) {
			return new ItemStack(ModItems.ingot_steel, 2);
		}

		if (item == ModItems.ingot_copper && item2 == Items.redstone
				|| item == Items.redstone && item2 == ModItems.ingot_copper) {
			return new ItemStack(ModItems.ingot_red_copper, 2);
		}

		if (item == ModItems.canister_empty && item2 == Items.coal
				|| item == Items.coal && item2 == ModItems.canister_empty) {
			return new ItemStack(ModItems.canister_fuel, 1);
		}

		if (item == ModItems.canister_fuel && item2 == Items.slime_ball
				|| item == Items.slime_ball && item2 == ModItems.canister_fuel) {
			return new ItemStack(ModItems.canister_napalm, 1);
		}

		if (item == ModItems.ingot_red_copper && item2 == ModItems.ingot_steel
				|| item == ModItems.ingot_steel && item2 == ModItems.ingot_red_copper) {
			return new ItemStack(ModItems.ingot_advanced_alloy, 2);
		}

		if (item == ModItems.ingot_tungsten && item2 == ModItems.nugget_schrabidium
				|| item == ModItems.nugget_schrabidium && item2 == ModItems.ingot_tungsten) {
			return new ItemStack(ModItems.ingot_magnetized_tungsten, 1);
		}

		if (item == ModItems.plate_mixed && item2 == ModItems.plate_gold
				|| item == ModItems.plate_gold && item2 == ModItems.plate_mixed) {
			return new ItemStack(ModItems.plate_paa, 2);
		}

		if (item == ModItems.rod_quad_euphemium && item2 == ModItems.powder_caesium
				|| item == ModItems.powder_caesium && item2 == ModItems.rod_quad_euphemium) {
			return new ItemStack(ModItems.nugget_euphemium, 2, 34);
		}

		if (item == ModItems.rod_quad_euphemium && item2 == ModItems.powder_astatine
				|| item == ModItems.powder_astatine && item2 == ModItems.rod_quad_euphemium) {
			return new ItemStack(ModItems.nugget_euphemium, 2, 34);
		}

		if (item == ModItems.rod_quad_euphemium && item2 == ModItems.powder_tennessine
				|| item == ModItems.powder_tennessine && item2 == ModItems.rod_quad_euphemium) {
			return new ItemStack(ModItems.nugget_euphemium, 2, 34);
		}

		if (item == ModItems.rod_quad_euphemium && item2 == ModItems.powder_cerium
				|| item == ModItems.powder_cerium && item2 == ModItems.rod_quad_euphemium) {
			return new ItemStack(ModItems.nugget_euphemium, 2, 34);
		}

		if (item == ModItems.oil_canola && item2 == ModItems.canister_empty
				|| item == ModItems.canister_empty && item2 == ModItems.oil_canola) {
			return new ItemStack(ModItems.canister_canola);
		}

		return null;
	}

	// Arrays!

	public static ItemStack[] getCentrifugeProcessingResult(Item item) {
		return getCentrifugeOutput(item);
	}

	public static ItemStack[] getCentrifugeOutput(Item item) {

		ItemStack[] uranium = new ItemStack[] { new ItemStack(ModItems.nugget_u238, 4),
				new ItemStack(ModItems.nugget_u238, 4), new ItemStack(ModItems.nugget_u235, 1),
				new ItemStack(ModItems.cell_empty, 1) };
		ItemStack[] plutonium = new ItemStack[] { new ItemStack(ModItems.nugget_pu238, 3),
				new ItemStack(ModItems.nugget_pu239, 1), new ItemStack(ModItems.nugget_pu240, 5),
				new ItemStack(ModItems.cell_empty, 1) };
		ItemStack[] test = new ItemStack[] { new ItemStack(Items.apple, 3), new ItemStack(Items.leather, 1),
				new ItemStack(Items.sugar, 3), new ItemStack(Items.blaze_powder, 2) };
		ItemStack[] schrabidium = new ItemStack[] { new ItemStack(ModItems.ingot_schrabidium, 1),
				new ItemStack(ModItems.sulfur, 1), new ItemStack(ModItems.sulfur, 1),
				new ItemStack(ModItems.cell_empty, 1) };
		ItemStack[] lithium2 = new ItemStack[] { new ItemStack(ModItems.lithium, 1), new ItemStack(ModItems.lithium, 1),
				new ItemStack(ModItems.lithium, 1), new ItemStack(ModItems.lithium, 1) };
		ItemStack[] lithium3 = new ItemStack[] { new ItemStack(ModItems.lithium, 4), new ItemStack(ModItems.lithium, 4),
				new ItemStack(ModItems.lithium, 4), new ItemStack(ModItems.lithium, 4) };

		ItemStack[] uran1 = new ItemStack[] { new ItemStack(ModItems.nugget_u235, 1),
				new ItemStack(ModItems.nugget_u238, 3), new ItemStack(ModItems.nugget_pu239, 2),
				new ItemStack(ModItems.rod_waste, 1) };
		ItemStack[] uran2 = new ItemStack[] { new ItemStack(ModItems.nugget_u235, 2),
				new ItemStack(ModItems.nugget_u238, 6), new ItemStack(ModItems.nugget_pu239, 4),
				new ItemStack(ModItems.rod_dual_waste, 1) };
		ItemStack[] uran3 = new ItemStack[] { new ItemStack(ModItems.nugget_u235, 4),
				new ItemStack(ModItems.nugget_u238, 12), new ItemStack(ModItems.nugget_pu239, 8),
				new ItemStack(ModItems.rod_quad_waste, 1) };
		ItemStack[] plutonium1 = new ItemStack[] { new ItemStack(ModItems.nugget_pu239, 1),
				new ItemStack(ModItems.nugget_pu240, 3), new ItemStack(ModItems.nugget_lead, 2),
				new ItemStack(ModItems.rod_waste, 1) };
		ItemStack[] plutonium2 = new ItemStack[] { new ItemStack(ModItems.nugget_pu239, 2),
				new ItemStack(ModItems.nugget_pu240, 6), new ItemStack(ModItems.nugget_lead, 4),
				new ItemStack(ModItems.rod_dual_waste, 1) };
		ItemStack[] plutonium3 = new ItemStack[] { new ItemStack(ModItems.nugget_pu239, 4),
				new ItemStack(ModItems.nugget_pu240, 12), new ItemStack(ModItems.nugget_lead, 8),
				new ItemStack(ModItems.rod_quad_waste, 1) };
		ItemStack[] mox1 = new ItemStack[] { new ItemStack(ModItems.nugget_mox_fuel, 1),
				new ItemStack(ModItems.nugget_neptunium, 3), new ItemStack(ModItems.nugget_u238, 2),
				new ItemStack(ModItems.rod_waste, 1) };
		ItemStack[] mox2 = new ItemStack[] { new ItemStack(ModItems.nugget_mox_fuel, 2),
				new ItemStack(ModItems.nugget_neptunium, 6), new ItemStack(ModItems.nugget_u238, 4),
				new ItemStack(ModItems.rod_dual_waste, 1) };
		ItemStack[] mox3 = new ItemStack[] { new ItemStack(ModItems.nugget_mox_fuel, 4),
				new ItemStack(ModItems.nugget_neptunium, 12), new ItemStack(ModItems.nugget_u238, 8),
				new ItemStack(ModItems.rod_quad_waste, 1) };
		ItemStack[] schrabidium1 = new ItemStack[] { new ItemStack(ModItems.nugget_schrabidium_fuel, 1),
				new ItemStack(ModItems.nugget_lead, 3), new ItemStack(ModItems.nugget_schrabidium, 2),
				new ItemStack(ModItems.rod_waste, 1) };
		ItemStack[] schrabidium2 = new ItemStack[] { new ItemStack(ModItems.nugget_schrabidium_fuel, 2),
				new ItemStack(ModItems.nugget_lead, 6), new ItemStack(ModItems.nugget_schrabidium, 4),
				new ItemStack(ModItems.rod_dual_waste, 1) };
		ItemStack[] schrabidium3 = new ItemStack[] { new ItemStack(ModItems.nugget_schrabidium_fuel, 4),
				new ItemStack(ModItems.nugget_lead, 19), new ItemStack(ModItems.nugget_neptunium, 1),
				new ItemStack(ModItems.rod_quad_euphemium, 1, 34) };

		if (item == ModItems.cell_uf6) {
			return uranium;
		}

		if (item == ModItems.cell_puf6) {
			return plutonium;
		}

		if (MainRegistry.enableDebugMode) {
			if (item == Item.getItemFromBlock(ModBlocks.test_render)) {
				return test;
			}
		}

		if (item == ModItems.cell_sas3) {
			return schrabidium;
		}

		if (item == ModItems.rod_uranium_fuel_depleted) {
			return uran1;
		}

		if (item == ModItems.rod_dual_uranium_fuel_depleted) {
			return uran2;
		}

		if (item == ModItems.rod_quad_uranium_fuel_depleted) {
			return uran3;
		}

		if (item == ModItems.rod_plutonium_fuel_depleted) {
			return plutonium1;
		}

		if (item == ModItems.rod_dual_plutonium_fuel_depleted) {
			return plutonium2;
		}

		if (item == ModItems.rod_quad_plutonium_fuel_depleted) {
			return plutonium3;
		}

		if (item == ModItems.rod_mox_fuel_depleted) {
			return mox1;
		}

		if (item == ModItems.rod_dual_mox_fuel_depleted) {
			return mox2;
		}

		if (item == ModItems.rod_quad_mox_fuel_depleted) {
			return mox3;
		}

		if (item == ModItems.rod_schrabidium_fuel_depleted) {
			return schrabidium1;
		}

		if (item == ModItems.rod_dual_schrabidium_fuel_depleted) {
			return schrabidium2;
		}

		if (item == ModItems.rod_quad_schrabidium_fuel_depleted) {
			return schrabidium3;
		}

		if (item == item.getItemFromBlock(Blocks.quartz_block) || item == item.getItemFromBlock(Blocks.quartz_stairs)) {
			return lithium3;
		}

		if (item == Items.quartz) {
			return lithium2;
		}

		return null;
	}

	public static ItemStack getReactorProcessingResult(Item item) {
		return getReactorOutput(item);
	}

	public static ItemStack getReactorOutput(Item item) {

		if (item == ModItems.rod_uranium) {
			return new ItemStack(ModItems.rod_plutonium, 1);
		}

		if (item == ModItems.rod_u235) {
			return new ItemStack(ModItems.rod_neptunium, 1);
		}

		if (item == ModItems.rod_u238) {
			return new ItemStack(ModItems.rod_pu239, 1);
		}

		if (item == ModItems.rod_neptunium) {
			return new ItemStack(ModItems.rod_pu238, 1);
		}

		if (item == ModItems.rod_plutonium) {
			return new ItemStack(ModItems.rod_lead, 1);
		}

		if (item == ModItems.rod_pu238) {
			return new ItemStack(ModItems.rod_pu239, 1);
		}

		if (item == ModItems.rod_pu239) {
			return new ItemStack(ModItems.rod_pu240, 1);
		}

		if (item == ModItems.rod_pu240) {
			return new ItemStack(ModItems.rod_lead, 1);
		}

		if (item == ModItems.rod_dual_uranium) {
			return new ItemStack(ModItems.rod_dual_plutonium, 1);
		}

		if (item == ModItems.rod_dual_u235) {
			return new ItemStack(ModItems.rod_dual_neptunium, 1);
		}

		if (item == ModItems.rod_dual_u238) {
			return new ItemStack(ModItems.rod_dual_pu239, 1);
		}

		if (item == ModItems.rod_dual_neptunium) {
			return new ItemStack(ModItems.rod_dual_pu238, 1);
		}

		if (item == ModItems.rod_dual_plutonium) {
			return new ItemStack(ModItems.rod_dual_lead, 1);
		}

		if (item == ModItems.rod_dual_pu238) {
			return new ItemStack(ModItems.rod_dual_pu239, 1);
		}

		if (item == ModItems.rod_dual_pu239) {
			return new ItemStack(ModItems.rod_dual_pu240, 1);
		}

		if (item == ModItems.rod_dual_pu240) {
			return new ItemStack(ModItems.rod_dual_lead, 1);
		}

		if (item == ModItems.rod_quad_uranium) {
			return new ItemStack(ModItems.rod_quad_plutonium, 1);
		}

		if (item == ModItems.rod_quad_u235) {
			return new ItemStack(ModItems.rod_quad_neptunium, 1);
		}

		if (item == ModItems.rod_quad_u238) {
			return new ItemStack(ModItems.rod_quad_pu239, 1);
		}

		if (item == ModItems.rod_quad_neptunium) {
			return new ItemStack(ModItems.rod_quad_pu238, 1);
		}

		if (item == ModItems.rod_quad_plutonium) {
			return new ItemStack(ModItems.rod_quad_lead, 1);
		}

		if (item == ModItems.rod_quad_pu238) {
			return new ItemStack(ModItems.rod_quad_pu239, 1);
		}

		if (item == ModItems.rod_quad_pu239) {
			return new ItemStack(ModItems.rod_quad_pu240, 1);
		}

		if (item == ModItems.rod_quad_pu240) {
			return new ItemStack(ModItems.rod_quad_lead, 1);
		}

		if (item == ModItems.rod_quad_schrabidium) {
			return new ItemStack(ModItems.rod_quad_euphemium, 1);
		}

		if (item == ModItems.rod_lithium) {
			return new ItemStack(ModItems.rod_tritium, 1);
		}

		if (item == ModItems.rod_dual_lithium) {
			return new ItemStack(ModItems.rod_dual_tritium, 1);
		}

		if (item == ModItems.rod_quad_lithium) {
			return new ItemStack(ModItems.rod_quad_tritium, 1);
		}

		return null;
	}

	public static ItemStack getCyclotronOutput(ItemStack part, ItemStack item) {

		if(part == null || item == null)
			return null;
		
		//LITHIUM
		if (part.getItem() == ModItems.part_lithium) {
			if(item.getItem() == ModItems.niter)
				return new ItemStack(ModItems.fluorite, 1);
			if(item.getItem() == ModItems.powder_coal)
				return new ItemStack(ModItems.fluorite, 1);
			if(item.getItem() == ModItems.powder_iron)
				return new ItemStack(ModItems.powder_cobalt, 1);
			if(item.getItem() == ModItems.powder_gold)
				return new ItemStack(ModItems.powder_lead, 1);
			if(item.getItem() == ModItems.powder_quartz)
				return new ItemStack(ModItems.sulfur, 1);
			if(item.getItem() == ModItems.powder_uranium)
				return new ItemStack(ModItems.powder_plutonium, 1);
			if(item.getItem() == ModItems.powder_aluminium)
				return new ItemStack(ModItems.powder_quartz, 1);
			if(item.getItem() == ModItems.powder_beryllium)
				return new ItemStack(ModItems.powder_coal, 1);
			if(item.getItem() == ModItems.powder_schrabidium)
				return new ItemStack(ModItems.powder_reiium, 1);
			if(item.getItem() == ModItems.powder_lithium)
				return new ItemStack(ModItems.powder_coal, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_caesium, 1);
			if(item.getItem() == ModItems.powder_thorium)
				return new ItemStack(ModItems.powder_uranium, 1);
			if(item.getItem() == ModItems.powder_caesium)
				return new ItemStack(ModItems.powder_lanthanium, 1);
			if(item.getItem() == ModItems.powder_reiium)
				return new ItemStack(ModItems.powder_weidanium, 1);
			if(item.getItem() == ModItems.powder_cobalt)
				return new ItemStack(ModItems.powder_copper, 1);
			if(item.getItem() == ModItems.powder_cerium)
				return new ItemStack(ModItems.powder_neodymium, 1);
			if(item.getItem() == ModItems.powder_actinium)
				return new ItemStack(ModItems.powder_thorium, 1);
			if(item.getItem() == ModItems.powder_lanthanium)
				return new ItemStack(ModItems.powder_cerium, 1);
		}
		
		//BERYLLIUM
		if (part.getItem() == ModItems.part_beryllium) {
			if(item.getItem() == ModItems.sulfur)
				return new ItemStack(ModItems.powder_titanium, 1);
			if(item.getItem() == ModItems.fluorite)
				return new ItemStack(ModItems.powder_aluminium, 1);
			if(item.getItem() == ModItems.powder_iron)
				return new ItemStack(ModItems.powder_copper, 1);
			if(item.getItem() == ModItems.powder_quartz)
				return new ItemStack(ModItems.powder_titanium, 1);
			if(item.getItem() == ModItems.powder_titanium)
				return new ItemStack(ModItems.powder_iron, 1);
			if(item.getItem() == ModItems.powder_copper)
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.powder_tungsten)
				return new ItemStack(ModItems.powder_gold, 1);
			if(item.getItem() == ModItems.powder_aluminium)
				return new ItemStack(ModItems.sulfur, 1);
			if(item.getItem() == ModItems.powder_lead)
				return new ItemStack(ModItems.powder_astatine, 1);
			if(item.getItem() == ModItems.powder_beryllium)
				return new ItemStack(ModItems.niter, 1);
			if(item.getItem() == ModItems.powder_lithium)
				return new ItemStack(ModItems.niter, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_cerium, 1);
			if(item.getItem() == ModItems.powder_thorium)
				return new ItemStack(ModItems.powder_neptunium, 1);
			if(item.getItem() == ModItems.powder_astatine)
				return new ItemStack(ModItems.powder_actinium, 1);
			if(item.getItem() == ModItems.powder_caesium)
				return new ItemStack(ModItems.powder_neodymium, 1);
			if(item.getItem() == ModItems.powder_weidanium)
				return new ItemStack(ModItems.powder_australium, 1);
			if(item.getItem() == ModItems.powder_strontium)
				return new ItemStack(ModItems.powder_niobium, 1);
			if(item.getItem() == ModItems.powder_bromine)
				return new ItemStack(ModItems.powder_strontium, 1);
			if(item.getItem() == ModItems.powder_actinium)
				return new ItemStack(ModItems.powder_uranium, 1);
			if(item.getItem() == ModItems.powder_lanthanium)
				return new ItemStack(ModItems.powder_neodymium, 1);
		}
		
		//CARBON
		if (part.getItem() == ModItems.part_carbon) {
			if(item.getItem() == ModItems.sulfur)
				return new ItemStack(ModItems.powder_iron, 1);
			if(item.getItem() == ModItems.niter)
				return new ItemStack(ModItems.powder_aluminium, 1);
			if(item.getItem() == ModItems.fluorite)
				return new ItemStack(ModItems.sulfur, 1);
			if(item.getItem() == ModItems.powder_coal)
				return new ItemStack(ModItems.powder_aluminium, 1);
			if(item.getItem() == ModItems.powder_iron)
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.powder_gold)
				return new ItemStack(ModItems.powder_astatine, 1);
			if(item.getItem() == ModItems.powder_quartz)
				return new ItemStack(ModItems.powder_iron, 1);
			if(item.getItem() == ModItems.powder_plutonium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_neptunium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_titanium)
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.powder_copper)
				return new ItemStack(ModItems.powder_strontium, 1);
			if(item.getItem() == ModItems.powder_tungsten)
				return new ItemStack(ModItems.powder_lead, 1);
			if(item.getItem() == ModItems.powder_aluminium)
				return new ItemStack(ModItems.powder_titanium, 1);
			if(item.getItem() == ModItems.powder_lead)
				return new ItemStack(ModItems.powder_thorium, 1);
			if(item.getItem() == ModItems.powder_beryllium)
				return new ItemStack(ModItems.fluorite, 1);
			if(item.getItem() == ModItems.powder_lithium)
				return new ItemStack(ModItems.fluorite, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_astatine, 1);
			if(item.getItem() == ModItems.powder_neodymium)
				return new ItemStack(ModItems.powder_tungsten, 1);
			if(item.getItem() == ModItems.powder_australium)
				return new ItemStack(ModItems.powder_verticium, 1);
			if(item.getItem() == ModItems.powder_strontium)
				return new ItemStack(ModItems.powder_iodine, 1);
			if(item.getItem() == ModItems.powder_cobalt)
				return new ItemStack(ModItems.powder_strontium, 1);
			if(item.getItem() == ModItems.powder_bromine)
				return new ItemStack(ModItems.powder_niobium, 1);
			if(item.getItem() == ModItems.powder_niobium)
				return new ItemStack(ModItems.powder_iodine, 1);
			if(item.getItem() == ModItems.powder_tennessine)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_cerium)
				return new ItemStack(ModItems.powder_tungsten, 1);
		}
		
		//COPPER
		if (part.getItem() == ModItems.part_copper) {
			if(item.getItem() == ModItems.sulfur)
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.niter)
				return new ItemStack(ModItems.powder_cobalt, 1);
			if(item.getItem() == ModItems.fluorite)
				return new ItemStack(ModItems.powder_iron, 1);
			if(item.getItem() == ModItems.powder_coal)
				return new ItemStack(ModItems.powder_iron, 1);
			if(item.getItem() == ModItems.powder_iron)
				return new ItemStack(ModItems.powder_niobium, 1);
			if(item.getItem() == ModItems.powder_gold)
				return new ItemStack(ModItems.powder_lanthanium, 1);
			if(item.getItem() == ModItems.powder_quartz)
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.powder_uranium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_titanium)
				return new ItemStack(ModItems.powder_strontium, 1);
			if(item.getItem() == ModItems.powder_copper)
				return new ItemStack(ModItems.powder_niobium, 1);
			if(item.getItem() == ModItems.powder_tungsten)
				return new ItemStack(ModItems.powder_actinium, 1);
			if(item.getItem() == ModItems.powder_aluminium)
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.powder_lead)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_beryllium)
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.powder_lithium)
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_tungsten, 1);
			if(item.getItem() == ModItems.powder_thorium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_neodymium)
				return new ItemStack(ModItems.powder_lead, 1);
			if(item.getItem() == ModItems.powder_astatine)
				return new ItemStack(ModItems.powder_plutonium, 1);
			if(item.getItem() == ModItems.powder_caesium)
				return new ItemStack(ModItems.powder_tungsten, 1);
			if(item.getItem() == ModItems.powder_verticium)
				return new ItemStack(ModItems.powder_unobtainium, 1);
			if(item.getItem() == ModItems.powder_cobalt)
				return new ItemStack(ModItems.powder_iodine, 1);
			if(item.getItem() == ModItems.powder_bromine)
				return new ItemStack(ModItems.powder_caesium, 1);
			if(item.getItem() == ModItems.powder_niobium)
				return new ItemStack(ModItems.powder_cerium, 1);
			if(item.getItem() == ModItems.powder_tennessine)
				return new ItemStack(ModItems.powder_reiium, 1);
			if(item.getItem() == ModItems.powder_cerium)
				return new ItemStack(ModItems.powder_lead, 1);
			if(item.getItem() == ModItems.powder_actinium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_lanthanium)
				return new ItemStack(ModItems.powder_astatine, 1);
		}
		
		//PLUTONIUM
		if (part.getItem() == ModItems.part_plutonium) {
			if(item.getItem() == ModItems.powder_uranium)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_plutonium)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_neptunium)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_unobtainium)
				return new ItemStack(ModItems.powder_daffergon, 1);
		}

		return null;
	}

	public Map<Object[], Object> getAlloyRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		if (MainRegistry.enableDebugMode) {
			recipes.put(new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.quartz) },
					new ItemStack(Item.getItemFromBlock(ModBlocks.test_render)));
		}
		recipes.put(new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.coal) },
				new ItemStack(ModItems.ingot_steel));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_lead), new ItemStack(ModItems.ingot_copper) },
				new ItemStack(ModItems.neutron_reflector, 2));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.plate_lead), new ItemStack(ModItems.plate_copper) },
				new ItemStack(ModItems.neutron_reflector));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_tungsten), new ItemStack(Items.coal) },
				new ItemStack(ModItems.neutron_reflector, 2));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_copper), new ItemStack(Items.redstone) },
				new ItemStack(ModItems.ingot_red_copper, 2));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_red_copper), new ItemStack(ModItems.ingot_steel) },
				new ItemStack(ModItems.ingot_advanced_alloy, 2));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.canister_empty), new ItemStack(Items.coal) },
				new ItemStack(ModItems.canister_fuel, 1));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.canister_fuel), new ItemStack(Items.slime_ball) },
				new ItemStack(ModItems.canister_napalm, 1));
		recipes.put(
				new ItemStack[] { new ItemStack(ModItems.ingot_tungsten), new ItemStack(ModItems.nugget_schrabidium) },
				new ItemStack(ModItems.ingot_magnetized_tungsten, 1));
		recipes.put(
				new ItemStack[] { new ItemStack(ModItems.plate_mixed), new ItemStack(ModItems.plate_gold) },
				new ItemStack(ModItems.plate_paa, 2));
		recipes.put(
				new ItemStack[] { new ItemStack(ModItems.canister_empty), new ItemStack(ModItems.oil_canola) },
				new ItemStack(ModItems.canister_canola, 1));
		return recipes;
	}

	public ArrayList<ItemStack> getAlloyFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(Items.coal));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.coal_block)));
		fuels.add(new ItemStack(Items.lava_bucket));
		fuels.add(new ItemStack(Items.redstone));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.redstone_block)));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.netherrack)));
		fuels.add(new ItemStack(Items.blaze_rod));
		fuels.add(new ItemStack(Items.blaze_powder));
		return fuels;
	}

	public Map<Object, Object[]> getCentrifugeRecipes() {
		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		recipes.put(new ItemStack(ModItems.cell_uf6), getCentrifugeOutput(ModItems.cell_uf6));
		recipes.put(new ItemStack(ModItems.cell_puf6), getCentrifugeOutput(ModItems.cell_puf6));
		if (MainRegistry.enableDebugMode) {
			recipes.put(new ItemStack(Item.getItemFromBlock(ModBlocks.test_render)),
					getCentrifugeOutput(Item.getItemFromBlock(ModBlocks.test_render)));
		}
		// [REDACTED]
		// recipes.put(new ItemStack(ModItems.rod_quad_euphemium),
		// getCentrifugeOutput(ModItems.rod_quad_euphemium));
		recipes.put(new ItemStack(ModItems.cell_sas3), getCentrifugeOutput(ModItems.cell_sas3));
		recipes.put(new ItemStack(ModItems.rod_uranium_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_uranium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_dual_uranium_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_dual_uranium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_quad_uranium_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_quad_uranium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_plutonium_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_plutonium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_dual_plutonium_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_dual_plutonium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_quad_plutonium_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_quad_plutonium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_mox_fuel_depleted), getCentrifugeOutput(ModItems.rod_mox_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_dual_mox_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_dual_mox_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_quad_mox_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_quad_mox_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_schrabidium_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_schrabidium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_dual_schrabidium_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_dual_schrabidium_fuel_depleted));
		// [REDACTED]
		// recipes.put(new
		// ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted),
		// getCentrifugeOutput(ModItems.rod_quad_schrabidium_fuel_depleted));
		recipes.put(new ItemStack(Item.getItemFromBlock(Blocks.quartz_block)),
				getCentrifugeOutput(Item.getItemFromBlock(Blocks.quartz_block)));
		recipes.put(new ItemStack(Items.quartz), getCentrifugeOutput(Items.quartz));
		return recipes;
	}

	public ArrayList<ItemStack> getCentrifugeFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(Items.coal));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.coal_block)));
		fuels.add(new ItemStack(Items.lava_bucket));
		fuels.add(new ItemStack(Items.redstone));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.redstone_block)));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.netherrack)));
		fuels.add(new ItemStack(Items.blaze_rod));
		fuels.add(new ItemStack(Items.blaze_powder));
		return fuels;
	}

	public Map<Object, Object> getReactorRecipes() {
		Map<Object, Object> recipes = new HashMap<Object, Object>();
		recipes.put(new ItemStack(ModItems.rod_uranium), getReactorOutput(ModItems.rod_uranium));
		recipes.put(new ItemStack(ModItems.rod_dual_uranium), getReactorOutput(ModItems.rod_dual_uranium));
		recipes.put(new ItemStack(ModItems.rod_quad_uranium), getReactorOutput(ModItems.rod_quad_uranium));
		recipes.put(new ItemStack(ModItems.rod_u235), getReactorOutput(ModItems.rod_u235));
		recipes.put(new ItemStack(ModItems.rod_dual_u235), getReactorOutput(ModItems.rod_dual_u235));
		recipes.put(new ItemStack(ModItems.rod_quad_u235), getReactorOutput(ModItems.rod_quad_u235));
		recipes.put(new ItemStack(ModItems.rod_u238), getReactorOutput(ModItems.rod_u238));
		recipes.put(new ItemStack(ModItems.rod_dual_u238), getReactorOutput(ModItems.rod_dual_u238));
		recipes.put(new ItemStack(ModItems.rod_quad_u238), getReactorOutput(ModItems.rod_quad_u238));
		recipes.put(new ItemStack(ModItems.rod_plutonium), getReactorOutput(ModItems.rod_plutonium));
		recipes.put(new ItemStack(ModItems.rod_dual_plutonium), getReactorOutput(ModItems.rod_dual_plutonium));
		recipes.put(new ItemStack(ModItems.rod_quad_plutonium), getReactorOutput(ModItems.rod_quad_plutonium));
		recipes.put(new ItemStack(ModItems.rod_pu238), getReactorOutput(ModItems.rod_pu238));
		recipes.put(new ItemStack(ModItems.rod_dual_pu238), getReactorOutput(ModItems.rod_dual_pu238));
		recipes.put(new ItemStack(ModItems.rod_quad_pu238), getReactorOutput(ModItems.rod_quad_pu238));
		recipes.put(new ItemStack(ModItems.rod_pu239), getReactorOutput(ModItems.rod_pu239));
		recipes.put(new ItemStack(ModItems.rod_dual_pu239), getReactorOutput(ModItems.rod_dual_pu239));
		recipes.put(new ItemStack(ModItems.rod_quad_pu239), getReactorOutput(ModItems.rod_quad_pu239));
		recipes.put(new ItemStack(ModItems.rod_pu240), getReactorOutput(ModItems.rod_pu240));
		recipes.put(new ItemStack(ModItems.rod_dual_pu240), getReactorOutput(ModItems.rod_dual_pu240));
		recipes.put(new ItemStack(ModItems.rod_quad_pu240), getReactorOutput(ModItems.rod_quad_pu240));
		recipes.put(new ItemStack(ModItems.rod_neptunium), getReactorOutput(ModItems.rod_neptunium));
		recipes.put(new ItemStack(ModItems.rod_dual_neptunium), getReactorOutput(ModItems.rod_dual_neptunium));
		recipes.put(new ItemStack(ModItems.rod_quad_neptunium), getReactorOutput(ModItems.rod_quad_neptunium));
		// [REDACTED]
		// recipes.put(new ItemStack(ModItems.rod_quad_schrabidium),
		// getReactorOutput(ModItems.rod_quad_schrabidium));
		recipes.put(new ItemStack(ModItems.rod_lithium), getReactorOutput(ModItems.rod_lithium));
		recipes.put(new ItemStack(ModItems.rod_dual_lithium), getReactorOutput(ModItems.rod_dual_lithium));
		recipes.put(new ItemStack(ModItems.rod_quad_lithium), getReactorOutput(ModItems.rod_quad_lithium));
		return recipes;
	}

	public ArrayList<ItemStack> getReactorFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(ModItems.rod_u238));
		fuels.add(new ItemStack(ModItems.rod_dual_u238));
		fuels.add(new ItemStack(ModItems.rod_quad_u238));
		fuels.add(new ItemStack(ModItems.rod_u235));
		fuels.add(new ItemStack(ModItems.rod_dual_u235));
		fuels.add(new ItemStack(ModItems.rod_quad_u235));
		fuels.add(new ItemStack(ModItems.rod_pu238));
		fuels.add(new ItemStack(ModItems.rod_dual_pu238));
		fuels.add(new ItemStack(ModItems.rod_quad_pu238));
		fuels.add(new ItemStack(ModItems.rod_pu239));
		fuels.add(new ItemStack(ModItems.rod_dual_pu239));
		fuels.add(new ItemStack(ModItems.rod_quad_pu239));
		fuels.add(new ItemStack(ModItems.rod_pu240));
		fuels.add(new ItemStack(ModItems.rod_dual_pu240));
		fuels.add(new ItemStack(ModItems.rod_quad_pu240));
		fuels.add(new ItemStack(ModItems.rod_neptunium));
		fuels.add(new ItemStack(ModItems.rod_dual_neptunium));
		fuels.add(new ItemStack(ModItems.rod_quad_neptunium));
		fuels.add(new ItemStack(ModItems.rod_schrabidium));
		fuels.add(new ItemStack(ModItems.rod_dual_schrabidium));
		fuels.add(new ItemStack(ModItems.rod_quad_schrabidium));
		fuels.add(new ItemStack(ModItems.pellet_rtg));
		return fuels;
	}

	public class ShredderRecipe {

		public ItemStack input;
		public ItemStack output;

		public void registerEverythingImSrs() {
			
			//Makes the OreDict easily accessible. Neat.
			
			//You see that guy up there? He's a liar. "easily accessible" may be true, but the detection is bullshit.

			/*System.out.println("Loading all items and blocks, please wait...");
			System.out.println("This process normally takes very long due to the incompetence of other modders I have to compensate for. Sorry for the inconvenience.");

			for (Object item : GameData.getItemRegistry()) {

				List<String> list = new ArrayList<String>();
				int[] array;

				if (item instanceof Item) {
					
					int x = 1;
					//if(((Item)item).getHasSubtypes())
					//	x = 126;

					for(int j = 0; j < x; j++)
					{
						ItemStack stack = new ItemStack((Item) item, 1, j);
						array = OreDictionary.getOreIDs(stack);

						for (int i = 0; i < array.length; i++) {
							// if
							// (!OreDictionary.getOreName(array[i]).equals("Unknown"))
							// {
							list.add(OreDictionary.getOreName(array[i]));
							// }
						}
						// if(list.size() > 0)
						theWholeThing.add(new DictCouple(stack, list));
					}
				}
			}

			for (Object block : GameData.getBlockRegistry()) {

				List<String> list = new ArrayList<String>();
				int[] array;

				if (block instanceof Block) {
					Item item = Item.getItemFromBlock((Block)block);
					
					int x = 1;
					//if(item != null && item.getHasSubtypes())
					//	x = 16;
					
					for(int j = 0; j < x; j++)
					{
						ItemStack stack = new ItemStack((Block) block, 1, j);
						array = OreDictionary.getOreIDs(stack);

						for (int i = 0; i < array.length; i++) {
							// if
							// (!OreDictionary.getOreName(array[i]).equals("Unknown"))
							// {
							list.add(OreDictionary.getOreName(array[i]));
							// }
						}

						// if(list.size() > 0)
						if(!doesExist(stack))
							theWholeThing.add(new DictCouple(stack, list));
					}
				}
			}
			
			System.out.println("Added " + theWholeThing.size() + " elements from the Ore Dict!");*/
			
			String[] names = OreDictionary.getOreNames();
			List<ItemStack> stacks = new ArrayList<ItemStack>();
			
			for(int i = 0; i < names.length; i++) {
				stacks.addAll(OreDictionary.getOres(names[i]));
			}
			
			for(int i = 0; i < stacks.size(); i++) {
				
				int[] ids = OreDictionary.getOreIDs(stacks.get(i));

				List<String> oreNames = new ArrayList<String>();
				
				for(int j = 0; j < ids.length; j++) {
					oreNames.add(OreDictionary.getOreName(ids[j]));
				}
				
				theWholeThing.add(new DictCouple(stacks.get(i), oreNames));
			}
			
			System.out.println("Added " + theWholeThing.size() + " elements from the Ore Dict!");
		}
		
		public boolean doesExist(ItemStack stack) {
			
			for(DictCouple dic : theWholeThing) {
				if(dic.item.getItem() == stack.getItem())
					return true;
			}
			
			return false;
		}

		public void addRecipes() {

			// Not very efficient, I know, but at least it works AND it's
			// somewhat smart!
			
			for(int i = 0; i < theWholeThing.size(); i++)
			{
				for(int j = 0; j < theWholeThing.get(i).list.size(); j++)
				{
					String s = theWholeThing.get(i).list.get(j);
					
					if (s.length() > 5 && s.substring(0, 5).equals("ingot")) {
						ItemStack stack = canFindDustByName(s.substring(5));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, stack);
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("ore")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 2));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("rod")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 2));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 5 && s.substring(0, 5).equals("block")) {
						ItemStack stack = canFindDustByName(s.substring(5));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 9));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("gem")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 1));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 4 && s.substring(0, 4).equals("dust")) {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.dust));
					} else if (s.length() > 6 && s.substring(0, 6).equals("powder")) {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.dust));
					} else {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
					}
				}

				if(theWholeThing.get(i).list.isEmpty())
					setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
				if(!theWholeThing.get(i).list.isEmpty() && theWholeThing.get(i).list.get(0).equals("Unknown"))
					setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
			}

			System.out.println("Added " + recipes.size() + " in total.");
			System.out.println("Added " + dustCount + " ore dust recipes.");
		}
		
		public ItemStack canFindDustByName(String s) {
			
			for(DictCouple d : theWholeThing)
			{
				for(String s1 : d.list)
				{
					if(s1.length() > 4 && s1.substring(0, 4).equals("dust") && s1.substring(4).equals(s))
					{
						dustCount++;
						return d.item;
					}
				}
			}
			
			return null;
		}
		
		public void setRecipe(ItemStack inp, ItemStack outp) {
			ShredderRecipe recipe = new ShredderRecipe();
			
			recipe.input = inp;
			recipe.output = outp;
			
			recipes.add(recipe);
		}
		
		public void overridePreSetRecipe(ItemStack inp, ItemStack outp) {
			
			boolean flag = false;
			
			for(int i = 0; i < recipes.size(); i++)
			{
				if(recipes.get(i) != null && 
						recipes.get(i).input != null && 
						recipes.get(i).output != null && 
						inp != null && 
						outp != null && 
						recipes.get(i).input.getItem() == inp.getItem() && 
						recipes.get(i).input.getItemDamage() == inp.getItemDamage()) {
					recipes.get(i).output = outp;
					flag = true;
				}
			}
			
			if(!flag) {
				ShredderRecipe rec = new ShredderRecipe();
				rec.input = inp;
				rec.output = outp;
				recipes.add(rec);
			}
		}
		
		public void removeDuplicates() {
			List<ShredderRecipe> newList = new ArrayList<ShredderRecipe>();
			
			for(ShredderRecipe piv : recipes)
			{
				boolean flag = false;
				
				if(newList.size() == 0)
				{
					newList.add(piv);
				} else {
					for(ShredderRecipe rec : newList) {
						if(piv != null && rec != null && piv.input != null && rec.input != null && rec.input.getItem() != null && piv.input.getItem() != null && rec.input.getItemDamage() == piv.input.getItemDamage() && rec.input.getItem() == piv.input.getItem())
							flag = true;
						if(piv == null || rec == null || piv.input == null || rec.input == null)
							flag = true;
					}
				}
				
				if(!flag)
				{
					newList.add(piv);
				}
			}
		}
		
		public void PrintRecipes() {
			/*for(int i = 0; i < recipes.size(); i++) {
				System.out.println("Recipe #" + i + ", " + recipes.get(i).input + " - " + recipes.get(i).output);
			}*/
			/*for(int i = 0; i < theWholeThing.size(); i++) {
			System.out.println(theWholeThing.get(i).item);
			}*/
			/*for(int i = 0; i < theWholeThing.size(); i++) {
				//for(int j = 0; j < theWholeThing.get(i).list.size(); j++)
				{
					//System.out.println(theWholeThing.get(i).item + " | " + getShredderResult(theWholeThing.get(i).item));
				}
				
				
			}*/

			/*for (int j = 0; j < recipes.size(); j++) {
				if (recipes.get(j) != null && recipes.get(j).input != null && recipes.get(j).output != null &&
						recipes.get(j).input.getItem() != null && recipes.get(j).output.getItem() != null)
					System.out.println(recipes.get(j).input + " | " + recipes.get(j).output);
				else
					System.out.println(recipes.get(j));
			}*/

			System.out.println("TWT: " + theWholeThing.size() + ", REC: " + recipes.size());
		}
	}

	public static class DictCouple {
		
		public ItemStack item;
		public List<String> list;

		public DictCouple(ItemStack item, List<String> list) {
			this.item = item;
			this.list = list;
		}
		
		public static List<String> findWithStack(ItemStack stack) {
			for(DictCouple couple : theWholeThing) {
				if(couple.item == stack);
					return couple.list;
			}
			
			return null;
		}
	}

	public static List<ShredderRecipe> recipes = new ArrayList<ShredderRecipe>();
	public static List<DictCouple> theWholeThing = new ArrayList<DictCouple>();
	public static int dustCount = 0;
	
	public static ItemStack getShredderResult(ItemStack stack) {
		for(ShredderRecipe rec : recipes)
		{
			if(stack != null && 
					rec.input.getItem() == stack.getItem() && 
					rec.input.getItemDamage() == stack.getItemDamage())
				return rec.output.copy();
		}
		
		return new ItemStack(ModItems.scrap);
	}
	
	public Map<Object, Object> getShredderRecipes() {
		Map<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(int i = 0; i < this.recipes.size(); i++) {
			if(this.recipes.get(i) != null && this.recipes.get(i).output.getItem() != ModItems.scrap)
				recipes.put(((ShredderRecipe)this.recipes.get(i)).input, getShredderResult(((ShredderRecipe)this.recipes.get(i)).input));
		}
		
		return recipes;
	}

	public Map<Object[], Object> getCMBRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_advanced_alloy), new ItemStack(ModItems.ingot_magnetized_tungsten) },
				new ItemStack(ModItems.ingot_combine_steel, 4));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.powder_advanced_alloy), new ItemStack(ModItems.powder_magnetized_tungsten) },
				new ItemStack(ModItems.ingot_combine_steel, 4));
		return recipes;
	}

	public ArrayList<ItemStack> getBatteries() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(ModItems.battery_generic));
		fuels.add(new ItemStack(ModItems.battery_advanced));
		fuels.add(new ItemStack(ModItems.battery_schrabidium));
		fuels.add(new ItemStack(ModItems.fusion_core));
		fuels.add(new ItemStack(ModItems.energy_core));
		return fuels;
	}

	public ArrayList<ItemStack> getBlades() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(ModItems.blades_advanced_alloy));
		fuels.add(new ItemStack(ModItems.blades_aluminium));
		fuels.add(new ItemStack(ModItems.blades_combine_steel));
		fuels.add(new ItemStack(ModItems.blades_gold));
		fuels.add(new ItemStack(ModItems.blades_iron));
		fuels.add(new ItemStack(ModItems.blades_steel));
		fuels.add(new ItemStack(ModItems.blades_titanium));
		fuels.add(new ItemStack(ModItems.blades_schrabidium));
		return fuels;
	}
}
