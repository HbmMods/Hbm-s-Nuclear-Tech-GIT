package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemAssemblyTemplate.EnumAssemblyTemplate;
import com.hbm.items.tool.ItemChemistryTemplate;
import com.hbm.main.MainRegistry;

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

		if (mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, "gemCoal")
				|| mODE(item, "gemCoal") && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})) {
			return new ItemStack(ModItems.neutron_reflector, 2);
		}

		if (mODE(item, new String[] {"ingotLead", "dustLead"}) && mODE(item2, new String[] {"ingotCopper", "dustCopper"})
				|| mODE(item, new String[] {"ingotCopper", "dustCopper"}) && mODE(item2, new String[] {"ingotLead", "dustLead"})) {
			return new ItemStack(ModItems.neutron_reflector, 4);
		}

		if (mODE(item, "plateLead") && mODE(item2, "plateCopper")
				|| mODE(item, "plateCopper") && mODE(item2, "plateLead")) {
			return new ItemStack(ModItems.neutron_reflector, 1);
		}

		if (mODE(item, new String[] {"ingotIron", "dustIron"}) && mODE(item2, new String[] {"gemCoal", "dustCoal"})
				|| mODE(item, new String[] {"gemCoal", "dustCoal"}) && mODE(item2, new String[] {"ingotIron", "dustIron"})) {
			return new ItemStack(ModItems.ingot_steel, 2);
		}

		if (mODE(item, new String[] {"ingotCopper", "dustCopper"}) && item2 == Items.redstone
				|| item == Items.redstone && mODE(item2, new String[] {"ingotCopper", "dustCopper"})) {
			return new ItemStack(ModItems.ingot_red_copper, 2);
		}

		if (item == ModItems.canister_fuel && item2 == Items.slime_ball
				|| item == Items.slime_ball && item2 == ModItems.canister_fuel) {
			return new ItemStack(ModItems.canister_napalm, 1);
		}

		if (mODE(item, new String[] {"ingotRedstoneAlloy", "dustRedstoneAlloy"}) && mODE(item2, new String[] {"ingotSteel", "dustSteel"})
				|| mODE(item, new String[] {"ingotSteel", "dustSteel"}) && mODE(item2, new String[] {"ingotRedstoneAlloy", "dustRedstoneAlloy"})) {
			return new ItemStack(ModItems.ingot_advanced_alloy, 2);
		}

		if (mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, "nuggetSchrabidium")
				|| mODE(item, "nuggetSchrabidium") && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})) {
			return new ItemStack(ModItems.ingot_magnetized_tungsten, 1);
		}

		if (item == ModItems.plate_mixed && mODE(item2, "plateGold")
				|| mODE(item, "plateGold") && item2 == ModItems.plate_mixed) {
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

		if (mODE(item, new String[] {"gemCoal", "dustCoal"}) && mODE(item2, "dustSalpeter")
				|| mODE(item, "dustSalpeter") && mODE(item2, new String[] {"gemCoal", "dustCoal"})) {
			return new ItemStack(ModItems.ingot_polymer, 2);
		}

		if (mODE(item, new String[] {"ingotSteel", "dustSteel"}) && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})
				|| mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, new String[] {"ingotSteel", "dustSteel"})) {
			return new ItemStack(ModItems.ingot_dura_steel, 2);
		}

		if (mODE(item, new String[] {"ingotSteel", "dustSteel"}) && item2 == ModItems.powder_cobalt
				|| item == ModItems.powder_cobalt && mODE(item2, new String[] {"ingotSteel", "dustSteel"})) {
			return new ItemStack(ModItems.ingot_dura_steel, 2);
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
				return new ItemStack(ModItems.powder_tungsten, 1);
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
				return new ItemStack(ModItems.powder_astatine, 1);
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
			if(item.getItem() == ModItems.cell_antimatter)
				return new ItemStack(ModItems.cell_anti_schrabidium, 1);
		}

		return null;
	}

	public Map<Object[], Object> getAlloyRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		
		if (MainRegistry.enableDebugMode) {
			recipes.put(new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.quartz) },
					new ItemStack(Item.getItemFromBlock(ModBlocks.test_render)));
		}
		try {
			recipes.put(new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.coal) },
				getFurnaceOutput(Items.iron_ingot, Items.coal).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_lead), new ItemStack(ModItems.ingot_copper) },
					getFurnaceOutput(ModItems.ingot_lead, ModItems.ingot_copper).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.plate_lead), new ItemStack(ModItems.plate_copper) },
					getFurnaceOutput(ModItems.plate_lead, ModItems.plate_copper).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_tungsten), new ItemStack(Items.coal) },
					getFurnaceOutput(ModItems.ingot_tungsten, Items.coal).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_copper), new ItemStack(Items.redstone) },
					getFurnaceOutput(ModItems.ingot_copper, Items.redstone).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_red_copper), new ItemStack(ModItems.ingot_steel) },
					getFurnaceOutput(ModItems.ingot_red_copper, ModItems.ingot_steel).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.canister_fuel), new ItemStack(Items.slime_ball) },
					getFurnaceOutput(ModItems.canister_fuel, Items.slime_ball).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_tungsten), new ItemStack(ModItems.nugget_schrabidium) },
					getFurnaceOutput(ModItems.ingot_tungsten, ModItems.nugget_schrabidium).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.plate_mixed), new ItemStack(ModItems.plate_gold) },
					getFurnaceOutput(ModItems.plate_mixed, ModItems.plate_gold).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.ingot_tungsten) },
					getFurnaceOutput(ModItems.ingot_steel, ModItems.ingot_tungsten).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.powder_cobalt) },
					getFurnaceOutput(ModItems.ingot_steel, ModItems.powder_cobalt).copy());
			recipes.put(new ItemStack[] { new ItemStack(Items.coal), new ItemStack(ModItems.niter) },
					getFurnaceOutput(Items.coal, ModItems.niter).copy());
		} catch (Exception x) {
			System.out.println("Unable to register alloy recipes for NEI!");
		}
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
	
	public Map<Object[], Object> getCyclotronRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		Item part = ModItems.part_lithium;
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.niter) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.niter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_coal) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_coal)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_gold) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_gold)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_uranium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_uranium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_schrabidium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_schrabidium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_thorium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_thorium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_caesium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_caesium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_reiium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_reiium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cobalt) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cobalt)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cerium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cerium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_actinium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_actinium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lanthanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lanthanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));

		part = ModItems.part_beryllium;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.sulfur) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.sulfur)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.fluorite) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.fluorite)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_titanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_titanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_copper) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_copper)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tungsten) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tungsten)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lead) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lead)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_thorium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_thorium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_astatine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_astatine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_caesium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_caesium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_weidanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_weidanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_strontium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_strontium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_bromine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_bromine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_actinium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_actinium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lanthanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lanthanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));
		
		part = ModItems.part_carbon;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.sulfur) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.sulfur)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.niter) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.niter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.fluorite) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.fluorite)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_coal) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_coal)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_gold) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_gold)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_plutonium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_plutonium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neptunium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neptunium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_titanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_titanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_copper) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_copper)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tungsten) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tungsten)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lead) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lead)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neodymium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neodymium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_australium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_australium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_strontium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_strontium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cobalt) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cobalt)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_bromine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_bromine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_niobium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_niobium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tennessine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tennessine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cerium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cerium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));
		
		part = ModItems.part_copper;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.sulfur) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.sulfur)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.niter) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.niter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.fluorite) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.fluorite)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_coal) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_coal)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_gold) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_gold)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_uranium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_uranium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_titanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_titanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_copper) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_copper)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tungsten) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tungsten)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lead) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lead)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_thorium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_thorium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neodymium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neodymium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_astatine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_astatine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_caesium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_caesium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_verticium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_verticium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cobalt) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cobalt)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_bromine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_bromine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_niobium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_niobium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tennessine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tennessine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cerium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cerium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_actinium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_actinium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lanthanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lanthanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));
		
		part = ModItems.part_plutonium;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_uranium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_uranium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_plutonium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_plutonium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neptunium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neptunium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_unobtainium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_unobtainium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.cell_antimatter) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.cell_antimatter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));
		
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
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 2, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("rod")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 2, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 5 && s.substring(0, 5).equals("block")) {
						ItemStack stack = canFindDustByName(s.substring(5));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 9, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("gem")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
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
		
		for(int i = 0; i < MachineRecipes.recipes.size(); i++) {
			if(MachineRecipes.recipes.get(i) != null && MachineRecipes.recipes.get(i).output.getItem() != ModItems.scrap)
				recipes.put(MachineRecipes.recipes.get(i).input, getShredderResult(MachineRecipes.recipes.get(i).input));
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
		fuels.add(new ItemStack(ModItems.battery_red_cell));
		fuels.add(new ItemStack(ModItems.battery_red_cell_6));
		fuels.add(new ItemStack(ModItems.battery_red_cell_24));
		fuels.add(new ItemStack(ModItems.battery_advanced));
		fuels.add(new ItemStack(ModItems.battery_advanced_cell));
		fuels.add(new ItemStack(ModItems.battery_advanced_cell_4));
		fuels.add(new ItemStack(ModItems.battery_advanced_cell_12));
		fuels.add(new ItemStack(ModItems.battery_lithium));
		fuels.add(new ItemStack(ModItems.battery_lithium_cell));
		fuels.add(new ItemStack(ModItems.battery_lithium_cell_3));
		fuels.add(new ItemStack(ModItems.battery_lithium_cell_6));
		fuels.add(new ItemStack(ModItems.battery_schrabidium));
		fuels.add(new ItemStack(ModItems.battery_schrabidium_cell));
		fuels.add(new ItemStack(ModItems.battery_schrabidium_cell_2));
		fuels.add(new ItemStack(ModItems.battery_schrabidium_cell_4));
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
	
	public static boolean mODE(Item item, String[] names) {
		return mODE(new ItemStack(item), names);
	}
	
	public static boolean mODE(ItemStack item, String[] names) {
		boolean flag = false;
		if(names.length > 0) {
			for(int i = 0; i < names.length; i++) {
				if(mODE(item, names[i]))
					flag = true;
			}
		}
		
		return flag;
	}
	
	public static boolean mODE(Item item, String name) {
		return mODE(new ItemStack(item), name);
	}
	
	//Matches Ore Dict Entry
	public static boolean mODE(ItemStack stack, String name) {
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 0 && s.equals(name))
				return true;
		}
		
		return false;
	}
	
	public static List<ItemStack> getRecipeFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemAssemblyTemplate))
			return null;
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		switch(ItemAssemblyTemplate.EnumAssemblyTemplate.getEnum(stack.getItemDamage())) {
		case TEST:
			list.add(new ItemStack(Items.iron_ingot, 4));
			list.add(new ItemStack(Items.gold_ingot, 2));
			list.add(new ItemStack(Items.coal, 8));
			break;
		case MIXED_PLATE:
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.neutron_reflector, 2));
			list.add(new ItemStack(ModItems.plate_combine_steel, 1));
			list.add(new ItemStack(ModItems.plate_lead, 4));
			break;
		case HAZMAT_CLOTH:
			list.add(new ItemStack(ModItems.powder_lead, 4));
			list.add(new ItemStack(Items.string, 8));
			break;
		case ASBESTOS_CLOTH:
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			list.add(new ItemStack(Items.string, 6));
			list.add(new ItemStack(Blocks.wool, 1));
			break;
		case COAL_FILTER:
			list.add(new ItemStack(ModItems.powder_coal, 4));
			list.add(new ItemStack(Items.string, 6));
			list.add(new ItemStack(Items.paper, 1));
			break;
		case CENTRIFUGE_ELEMENT:
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.coil_tungsten, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case CENTRIFUGE_TOWER:
			list.add(new ItemStack(ModItems.centrifuge_element, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.powder_lapis, 2));
			break;
		case DEE_MAGNET:
			list.add(new ItemStack(ModBlocks.fusion_conductor, 6));
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 1));
			break;
		case FLAT_MAGNET:
			list.add(new ItemStack(ModBlocks.fusion_conductor, 5));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			break;
		case CYCLOTRON_TOWER:
			list.add(new ItemStack(ModItems.magnet_circular, 6));
			list.add(new ItemStack(ModItems.magnet_dee, 3));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 8));
			break;
		case REACTOR_CORE:
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			break;
		case RTG_UNIT:
			list.add(new ItemStack(ModItems.thermo_element, 6));
			list.add(new ItemStack(ModItems.board_copper, 2));
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.circuit_copper, 2));
			break;
		case HEAT_UNIT:
			list.add(new ItemStack(ModItems.coil_copper_torus, 3));
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case GRAVITY_UNIT:
			list.add(new ItemStack(ModItems.coil_copper, 4));
			list.add(new ItemStack(ModItems.coil_tungsten, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.nugget_schrabidium, 2));
			break;
		case TITANIUM_DRILL:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.ingot_dura_steel, 2));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case TELEPAD:
			list.add(new ItemStack(ModItems.plate_schrabidium, 2));
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 1));
			break;
		case TELEKIT:
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 6));
			list.add(new ItemStack(ModItems.plate_lead, 16));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			list.add(new ItemStack(ModItems.singularity_counter_resonant, 1));
			list.add(new ItemStack(ModItems.singularity_super_heated, 1));
			list.add(new ItemStack(ModItems.powder_power, 4));
			break;
		case GEASS_REACTOR:
			list.add(new ItemStack(ModItems.plate_steel, 15));
			list.add(new ItemStack(ModItems.ingot_lead, 5));
			list.add(new ItemStack(ModItems.rod_quad_empty, 10));
			list.add(new ItemStack(Items.dye, 4, 3));
			break;
		case GENERATOR_FRONT:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.turbine_titanium, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.wire_gold, 4));
			break;
		case WT1_GENERIC:
			list.add(new ItemStack(ModItems.plate_titanium, 5));
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(Blocks.tnt, 2));
			break;
		case WT2_GENERIC:
			list.add(new ItemStack(ModItems.plate_titanium, 8));
			list.add(new ItemStack(ModItems.plate_steel, 5));
			list.add(new ItemStack(Blocks.tnt, 4));
			break;
		case WT3_GENERIC:
			list.add(new ItemStack(ModItems.plate_titanium, 15));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(Blocks.tnt, 8));
			break;
		case WT1_FIRE:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModItems.powder_fire, 4));
			break;
		case WT2_FIRE:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModItems.powder_fire, 8));
			break;
		case WT3_FIRE:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.powder_fire, 16));
			break;
		case WT1_CLUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 4));
			break;
		case WT2_CLUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 8));
			break;
		case WT3_CLUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 16));
			break;
		case WT1_BUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModBlocks.det_cord, 8));
			break;
		case WT2_BUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModBlocks.det_cord, 4));
			list.add(new ItemStack(ModBlocks.det_charge, 4));
			break;
		case WT3_BUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModBlocks.det_charge, 8));
			break;
		case W_NUCLEAR:
			list.add(new ItemStack(ModItems.boy_shielding, 1));
			list.add(new ItemStack(ModItems.boy_target, 1));
			list.add(new ItemStack(ModItems.boy_bullet, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			break;
		case W_MIRVLET:
			list.add(new ItemStack(ModItems.ingot_steel, 5));
			list.add(new ItemStack(ModItems.plate_steel, 18));
			list.add(new ItemStack(ModItems.ingot_pu239, 1));
			list.add(new ItemStack(Blocks.tnt, 2));
			break;
		case W_MIRV:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModBlocks.det_cord, 8));
			list.add(new ItemStack(ModItems.warhead_mirvlet, 8));
			break;
		case W_ENDOTHERMIC:
			list.add(new ItemStack(ModBlocks.therm_endo, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			break;
		case W_EXOTHERMIC:
			list.add(new ItemStack(ModBlocks.therm_exo, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			break;
		case T1_TANK:
			list.add(new ItemStack(ModItems.canister_kerosene, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case T2_TANK:
			list.add(new ItemStack(ModItems.fuel_tank_small, 3));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case T3_TANK:
			list.add(new ItemStack(ModItems.fuel_tank_medium, 3));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case T1_THRUSTER:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.wire_aluminium, 4));
			break;
		case T2_THRUSTER:
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.wire_copper, 4));
			break;
		case T3_THRUSTER:
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case CHOPPER_HEAD:
			list.add(new ItemStack(ModBlocks.reinforced_glass, 2));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 1));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 22));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			break;
		case CHOPPER_GUN:
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 2));
			list.add(new ItemStack(ModItems.wire_tungsten, 6));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 1));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case CHOPPER_BODY:
			list.add(new ItemStack(ModItems.ingot_combine_steel, 26));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 1));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.chopper_blades, 2));
			break;
		case CHOPPER_TAIL:
			list.add(new ItemStack(ModItems.plate_combine_steel, 8));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 5));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.chopper_blades, 2));
			break;
		case CHOPPER_WING:
			list.add(new ItemStack(ModItems.plate_combine_steel, 6));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 3));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 2));
			break;
		case CHOPPER_BLADES:
			list.add(new ItemStack(ModItems.plate_combine_steel, 8));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 2));
			break;
		case CIRCUIT_2:
			list.add(new ItemStack(ModItems.circuit_aluminium, 1));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			break;
		case CIRCUIT_3:
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.powder_gold, 4));
			break;
		case RTG_PELLET:
			list.add(new ItemStack(ModItems.nugget_pu238, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case WEAK_PELLET:
			list.add(new ItemStack(ModItems.nugget_u238, 4));
			list.add(new ItemStack(ModItems.nugget_pu238, 1));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case FUSION_PELLET:
			list.add(new ItemStack(ModItems.cell_deuterium, 6));
			list.add(new ItemStack(ModItems.cell_tritium, 2));
			list.add(new ItemStack(ModItems.lithium, 4));
			break;
		case CLUSTER_PELLETS:
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(Blocks.tnt, 1));
			break;
		case GUN_PELLETS:
			list.add(new ItemStack(ModItems.nugget_lead, 6));
			break;
		case AUSTRALIUM_MACHINE:
			list.add(new ItemStack(ModItems.rod_australium, 1));
			list.add(new ItemStack(ModItems.ingot_steel, 1));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			break;
		case MAGNETRON:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 1));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.wire_tungsten, 1));
			list.add(new ItemStack(ModItems.coil_tungsten, 1));
			break;
		case W_SP:
			list.add(new ItemStack(ModItems.ingot_schrabidium, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_SHE:
			list.add(new ItemStack(ModItems.ingot_hes, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_SME:
			list.add(new ItemStack(ModItems.ingot_schrabidium_fuel, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_SLE:
			list.add(new ItemStack(ModItems.ingot_les, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_B:
			list.add(new ItemStack(ModItems.ingot_beryllium, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_N:
			list.add(new ItemStack(ModItems.ingot_neptunium, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_L:
			list.add(new ItemStack(ModItems.ingot_lead, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_A:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case UPGRADE_TEMPLATE:
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.plate_iron, 4));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			break;
		case UPGRADE_RED_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_red_copper, 4));
			list.add(new ItemStack(Items.redstone, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_RED_II:
			list.add(new ItemStack(ModItems.upgrade_speed_1, 1));
			list.add(new ItemStack(ModItems.powder_red_copper, 2));
			list.add(new ItemStack(Items.redstone, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_RED_III:
			list.add(new ItemStack(ModItems.upgrade_speed_2, 1));
			list.add(new ItemStack(ModItems.powder_red_copper, 2));
			list.add(new ItemStack(Items.redstone, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_GREEN_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_dura_steel, 4));
			list.add(new ItemStack(ModItems.powder_steel, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_GREEN_II:
			list.add(new ItemStack(ModItems.upgrade_effect_1, 1));
			list.add(new ItemStack(ModItems.powder_dura_steel, 2));
			list.add(new ItemStack(ModItems.powder_steel, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_GREEN_III:
			list.add(new ItemStack(ModItems.upgrade_effect_2, 1));
			list.add(new ItemStack(ModItems.powder_dura_steel, 2));
			list.add(new ItemStack(ModItems.powder_steel, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_BLUE_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_lapis, 4));
			list.add(new ItemStack(Items.glowstone_dust, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_BLUE_II:
			list.add(new ItemStack(ModItems.upgrade_power_1, 1));
			list.add(new ItemStack(ModItems.powder_lapis, 2));
			list.add(new ItemStack(Items.glowstone_dust, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_BLUE_III:
			list.add(new ItemStack(ModItems.upgrade_power_2, 1));
			list.add(new ItemStack(ModItems.powder_lapis, 2));
			list.add(new ItemStack(Items.glowstone_dust, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_PURPLE_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 4));
			list.add(new ItemStack(ModItems.powder_iron, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_PURPLE_II:
			list.add(new ItemStack(ModItems.upgrade_fortune_1, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 2));
			list.add(new ItemStack(ModItems.powder_iron, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_PURPLE_III:
			list.add(new ItemStack(ModItems.upgrade_fortune_2, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 2));
			list.add(new ItemStack(ModItems.powder_iron, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case FUSE:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(Blocks.glass_pane, 1));
			list.add(new ItemStack(ModItems.wire_aluminium, 1));
			break;
		case REDCOIL_CAPACITOR:
			list.add(new ItemStack(ModItems.plate_gold, 3));
			list.add(new ItemStack(ModItems.fuse, 1));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 6));
			list.add(new ItemStack(Blocks.redstone_block, 2));
			break;
		case TITANIUM_FILTER:
			list.add(new ItemStack(ModItems.plate_lead, 3));
			list.add(new ItemStack(ModItems.fuse, 1));
			list.add(new ItemStack(ModItems.wire_tungsten, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.ingot_u238, 2));
			break;
		case LITHIUM_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_lithium, 2));
			break;
		case BERYLLIUM_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_beryllium, 2));
			break;
		case COAL_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_coal, 2));
			break;
		case COPPER_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_copper, 2));
			break;
		case PLUTONIUM_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_plutonium, 2));
			break;
		case THERMO_ELEMENT:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 2));
			list.add(new ItemStack(ModItems.wire_aluminium, 2));
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			break;
		case LIMITER:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.circuit_copper, 2));
			list.add(new ItemStack(ModItems.wire_copper, 4));
			break;
		case ANGRY_METAL:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			break;
		case CMB_TILE:
			list.add(new ItemStack(ModItems.ingot_combine_steel, 1));
			list.add(new ItemStack(ModItems.plate_combine_steel, 8));
			break;
		case CMB_BRICKS:
			list.add(new ItemStack(ModBlocks.block_magnetized_tungsten, 4));
			list.add(new ItemStack(ModBlocks.brick_concrete, 4));
			list.add(new ItemStack(ModBlocks.cmb_brick, 1));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			break;
		case HATCH_FRAME:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.wire_aluminium, 4));
			list.add(new ItemStack(Items.redstone, 2));
			list.add(new ItemStack(ModBlocks.steel_roof, 5));
			break;
		case HATCH_CONTROLLER:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.ingot_red_copper, 1));
			list.add(new ItemStack(Items.redstone, 4));
			list.add(new ItemStack(ModBlocks.steel_roof, 5));
			break;
		case CENTRIFUGE:
			list.add(new ItemStack(ModItems.centrifuge_tower, 1));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			break;
		case BREEDING_REACTOR:
			list.add(new ItemStack(ModItems.reactor_core, 1));
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			break;
		case RTG_FURNACE:
			list.add(new ItemStack(Blocks.furnace, 1));
			list.add(new ItemStack(ModItems.rtg_unit, 3));
			list.add(new ItemStack(ModItems.plate_lead, 6));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			break;
		case DIESEL_GENERATOR:
			list.add(new ItemStack(ModItems.hull_small_steel, 4));
			list.add(new ItemStack(Blocks.piston, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.plate_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			break;
		case NUCLEAR_GENERATOR:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.plate_lead, 8));
			list.add(new ItemStack(ModItems.plate_copper, 4));
			list.add(new ItemStack(ModItems.ingot_lead, 12));
			list.add(new ItemStack(ModItems.ingot_red_copper, 6));
			list.add(new ItemStack(ModItems.circuit_copper, 8));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			break;
		case INDUSTRIAL_GENERATOR:
			list.add(new ItemStack(ModItems.generator_front, 1));
			list.add(new ItemStack(ModItems.generator_steel, 3));
			list.add(new ItemStack(ModItems.rotor_steel, 3));
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.board_copper, 4));
			list.add(new ItemStack(ModItems.wire_gold, 8));
			list.add(new ItemStack(ModBlocks.red_wire_coated, 2));
			list.add(new ItemStack(ModItems.pedestal_steel, 2));
			list.add(new ItemStack(ModItems.circuit_copper, 4));
			break;
		case CYCLOTRON:
			list.add(new ItemStack(ModItems.cyclotron_tower, 1));
			list.add(new ItemStack(ModItems.board_copper, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModBlocks.machine_battery, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 20));
			list.add(new ItemStack(ModItems.circuit_red_copper, 12));
			list.add(new ItemStack(ModItems.circuit_gold, 3));
			break;
		case RT_GENERATOR:
			list.add(new ItemStack(ModItems.rtg_unit, 6));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case BATTERY:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.sulfur, 12));
			list.add(new ItemStack(ModItems.powder_lead, 12));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case HE_TO_RF:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			list.add(new ItemStack(ModItems.coil_copper_torus, 1));
			break;
		case RF_TO_HE:
			list.add(new ItemStack(ModItems.ingot_beryllium, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			list.add(new ItemStack(ModItems.coil_copper_torus, 1));
			break;
		case SHREDDER:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 2));
			list.add(new ItemStack(ModBlocks.steel_beam, 2));
			list.add(new ItemStack(Blocks.iron_bars, 2));
			list.add(new ItemStack(ModBlocks.red_wire_coated, 1));
			break;
		case DEUTERIUM_EXTRACTOR:
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.coil_tungsten, 4));
			break;
		case DERRICK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 20));
			list.add(new ItemStack(ModBlocks.steel_beam, 8));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.pipes_steel, 3));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			break;
		case FLARE_STACK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 28));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.thermo_element, 3));
			break;
		case REFINERY:
			list.add(new ItemStack(ModItems.ingot_titanium, 6));
			list.add(new ItemStack(ModItems.plate_copper, 4));
			list.add(new ItemStack(ModItems.tank_steel, 6));
			list.add(new ItemStack(ModItems.coil_tungsten, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			break;
		default:
			break;
		}
		
		if(list.isEmpty())
			return null;
		else
			return list;
	}
	
	public static ItemStack getOutputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemAssemblyTemplate))
			return null;
		
		ItemStack output = null;
		
		switch(ItemAssemblyTemplate.EnumAssemblyTemplate.getEnum(stack.getItemDamage())) {
		case TEST:
			output = new ItemStack(ModItems.rotor_steel, 2);
			break;
		case MIXED_PLATE:
			output = new ItemStack(ModItems.plate_mixed, 6);
			break;
		case HAZMAT_CLOTH:
			output = new ItemStack(ModItems.hazmat_cloth, 4);
			break;
		case ASBESTOS_CLOTH:
			output = new ItemStack(ModItems.asbestos_cloth, 4);
			break;
		case COAL_FILTER:
			output = new ItemStack(ModItems.filter_coal, 1);
			break;
		case CENTRIFUGE_ELEMENT:
			output = new ItemStack(ModItems.centrifuge_element, 1);
			break;
		case CENTRIFUGE_TOWER:
			output = new ItemStack(ModItems.centrifuge_tower, 1);
			break;
		case DEE_MAGNET:
			output = new ItemStack(ModItems.magnet_dee, 1);
			break;
		case FLAT_MAGNET:
			output = new ItemStack(ModItems.magnet_circular, 1);
			break;
		case CYCLOTRON_TOWER:
			output = new ItemStack(ModItems.cyclotron_tower, 1);
			break;
		case REACTOR_CORE:
			output = new ItemStack(ModItems.reactor_core, 1);
			break;
		case RTG_UNIT:
			output = new ItemStack(ModItems.rtg_unit, 2);
			break;
		case HEAT_UNIT:
			output = new ItemStack(ModItems.thermo_unit_empty, 1);
			break;
		case GRAVITY_UNIT:
			output = new ItemStack(ModItems.levitation_unit, 1);
			break;
		case TITANIUM_DRILL:
			output = new ItemStack(ModItems.drill_titanium, 1);
			break;
		case TELEPAD:
			output = new ItemStack(ModItems.telepad, 1);
			break;
		case TELEKIT:
			output = new ItemStack(ModItems.entanglement_kit, 1);
			break;
		case GEASS_REACTOR:
			output = new ItemStack(ModItems.dysfunctional_reactor, 1);
			break;
		case GENERATOR_FRONT:
			output = new ItemStack(ModItems.generator_front, 1);
			break;
		case WT1_GENERIC:
			output = new ItemStack(ModItems.warhead_generic_small, 1);
			break;
		case WT2_GENERIC:
			output = new ItemStack(ModItems.warhead_generic_medium, 1);
			break;
		case WT3_GENERIC:
			output = new ItemStack(ModItems.warhead_generic_large, 1);
			break;
		case WT1_FIRE:
			output = new ItemStack(ModItems.warhead_incendiary_small, 1);
			break;
		case WT2_FIRE:
			output = new ItemStack(ModItems.warhead_incendiary_medium, 1);
			break;
		case WT3_FIRE:
			output = new ItemStack(ModItems.warhead_incendiary_large, 1);
			break;
		case WT1_CLUSTER:
			output = new ItemStack(ModItems.warhead_cluster_small, 1);
			break;
		case WT2_CLUSTER:
			output = new ItemStack(ModItems.warhead_cluster_medium, 1);
			break;
		case WT3_CLUSTER:
			output = new ItemStack(ModItems.warhead_cluster_large, 1);
			break;
		case WT1_BUSTER:
			output = new ItemStack(ModItems.warhead_buster_small, 1);
			break;
		case WT2_BUSTER:
			output = new ItemStack(ModItems.warhead_buster_medium, 1);
			break;
		case WT3_BUSTER:
			output = new ItemStack(ModItems.warhead_buster_large, 1);
			break;
		case W_NUCLEAR:
			output = new ItemStack(ModItems.warhead_nuclear, 1);
			break;
		case W_MIRVLET:
			output = new ItemStack(ModItems.warhead_mirvlet, 1);
			break;
		case W_MIRV:
			output = new ItemStack(ModItems.warhead_mirv, 1);
			break;
		case W_ENDOTHERMIC:
			output = new ItemStack(ModItems.warhead_thermo_endo, 1);
			break;
		case W_EXOTHERMIC:
			output = new ItemStack(ModItems.warhead_thermo_exo, 1);
			break;
		case T1_TANK:
			output = new ItemStack(ModItems.fuel_tank_small, 1);
			break;
		case T2_TANK:
			output = new ItemStack(ModItems.fuel_tank_medium, 1);
			break;
		case T3_TANK:
			output = new ItemStack(ModItems.fuel_tank_large, 1);
			break;
		case T1_THRUSTER:
			output = new ItemStack(ModItems.thruster_small, 1);
			break;
		case T2_THRUSTER:
			output = new ItemStack(ModItems.thruster_medium, 1);
			break;
		case T3_THRUSTER:
			output = new ItemStack(ModItems.thruster_large, 1);
			break;
		case CHOPPER_HEAD:
			output = new ItemStack(ModItems.chopper_head, 1);
			break;
		case CHOPPER_GUN:
			output = new ItemStack(ModItems.chopper_gun, 1);
			break;
		case CHOPPER_BODY:
			output = new ItemStack(ModItems.chopper_torso, 1);
			break;
		case CHOPPER_TAIL:
			output = new ItemStack(ModItems.chopper_tail, 1);
			break;
		case CHOPPER_WING:
			output = new ItemStack(ModItems.chopper_wing, 1);
			break;
		case CHOPPER_BLADES:
			output = new ItemStack(ModItems.chopper_blades, 1);
			break;
		case CIRCUIT_2:
			output = new ItemStack(ModItems.circuit_copper, 1);
			break;
		case CIRCUIT_3:
			output = new ItemStack(ModItems.circuit_red_copper, 1);
			break;
		case RTG_PELLET:
			output = new ItemStack(ModItems.pellet_rtg, 1);
			break;
		case WEAK_PELLET:
			output = new ItemStack(ModItems.pellet_rtg_weak, 1);
			break;
		case FUSION_PELLET:
			output = new ItemStack(ModItems.tritium_deuterium_cake, 1);
			break;
		case CLUSTER_PELLETS:
			output = new ItemStack(ModItems.pellet_cluster, 1);
			break;
		case GUN_PELLETS:
			output = new ItemStack(ModItems.pellet_buckshot, 1);
			break;
		case AUSTRALIUM_MACHINE:
			output = new ItemStack(ModItems.australium_iii, 1);
			break;
		case MAGNETRON:
			output = new ItemStack(ModItems.magnetron, 1);
			break;
		case W_SP:
			output = new ItemStack(ModItems.pellet_schrabidium, 1);
			break;
		case W_SHE:
			output = new ItemStack(ModItems.pellet_hes, 1);
			break;
		case W_SME:
			output = new ItemStack(ModItems.pellet_mes, 1);
			break;
		case W_SLE:
			output = new ItemStack(ModItems.pellet_les, 1);
			break;
		case W_B:
			output = new ItemStack(ModItems.pellet_beryllium, 1);
			break;
		case W_N:
			output = new ItemStack(ModItems.pellet_neptunium, 1);
			break;
		case W_L:
			output = new ItemStack(ModItems.pellet_lead, 1);
			break;
		case W_A:
			output = new ItemStack(ModItems.pellet_advanced, 1);
			break;
		case UPGRADE_TEMPLATE:
			output = new ItemStack(ModItems.upgrade_template, 1);
			break;
		case UPGRADE_RED_I:
			output = new ItemStack(ModItems.upgrade_speed_1, 1);
			break;
		case UPGRADE_RED_II:
			output = new ItemStack(ModItems.upgrade_speed_2, 1);
			break;
		case UPGRADE_RED_III:
			output = new ItemStack(ModItems.upgrade_speed_3, 1);
			break;
		case UPGRADE_GREEN_I:
			output = new ItemStack(ModItems.upgrade_effect_1, 1);
			break;
		case UPGRADE_GREEN_II:
			output = new ItemStack(ModItems.upgrade_effect_2, 1);
			break;
		case UPGRADE_GREEN_III:
			output = new ItemStack(ModItems.upgrade_effect_3, 1);
			break;
		case UPGRADE_BLUE_I:
			output = new ItemStack(ModItems.upgrade_power_1, 1);
			break;
		case UPGRADE_BLUE_II:
			output = new ItemStack(ModItems.upgrade_power_2, 1);
			break;
		case UPGRADE_BLUE_III:
			output = new ItemStack(ModItems.upgrade_power_3, 1);
			break;
		case UPGRADE_PURPLE_I:
			output = new ItemStack(ModItems.upgrade_fortune_1, 1);
			break;
		case UPGRADE_PURPLE_II:
			output = new ItemStack(ModItems.upgrade_fortune_2, 1);
			break;
		case UPGRADE_PURPLE_III:
			output = new ItemStack(ModItems.upgrade_fortune_3, 1);
			break;
		case FUSE:
			output = new ItemStack(ModItems.fuse, 1);
			break;
		case REDCOIL_CAPACITOR:
			output = new ItemStack(ModItems.redcoil_capacitor, 1);
			break;
		case TITANIUM_FILTER:
			output = new ItemStack(ModItems.titanium_filter, 1);
			break;
		case LITHIUM_BOX:
			output = new ItemStack(ModItems.part_lithium, 1);
			break;
		case BERYLLIUM_BOX:
			output = new ItemStack(ModItems.part_beryllium, 1);
			break;
		case COAL_BOX:
			output = new ItemStack(ModItems.part_carbon, 1);
			break;
		case COPPER_BOX:
			output = new ItemStack(ModItems.part_copper, 1);
			break;
		case PLUTONIUM_BOX:
			output = new ItemStack(ModItems.part_plutonium, 1);
			break;
		case THERMO_ELEMENT:
			output = new ItemStack(ModItems.thermo_element, 1);
			break;
		case LIMITER:
			output = new ItemStack(ModItems.limiter, 1);
			break;
		case ANGRY_METAL:
			output = new ItemStack(ModItems.plate_dalekanium, 1);
			break;
		case CMB_TILE:
			output = new ItemStack(ModBlocks.cmb_brick, 8);
			break;
		case CMB_BRICKS:
			output = new ItemStack(ModBlocks.cmb_brick_reinforced, 8);
			break;
		case HATCH_FRAME:
			output = new ItemStack(ModBlocks.seal_frame, 1);
			break;
		case HATCH_CONTROLLER:
			output = new ItemStack(ModBlocks.seal_controller, 1);
			break;
		case CENTRIFUGE:
			output = new ItemStack(ModBlocks.machine_centrifuge, 1);
			break;
		case BREEDING_REACTOR:
			output = new ItemStack(ModBlocks.machine_reactor, 1);
			break;
		case RTG_FURNACE:
			output = new ItemStack(ModBlocks.machine_rtg_furnace_off, 1);
			break;
		case DIESEL_GENERATOR:
			output = new ItemStack(ModBlocks.machine_diesel, 1);
			break;
		case NUCLEAR_GENERATOR:
			output = new ItemStack(ModBlocks.machine_generator, 1);
			break;
		case INDUSTRIAL_GENERATOR:
			output = new ItemStack(ModBlocks.machine_industrial_generator, 1);
			break;
		case CYCLOTRON:
			output = new ItemStack(ModBlocks.machine_cyclotron, 1);
			break;
		case RT_GENERATOR:
			output = new ItemStack(ModBlocks.machine_rtg_grey, 1);
			break;
		case BATTERY:
			output = new ItemStack(ModBlocks.machine_battery, 1);
			break;
		case HE_TO_RF:
			output = new ItemStack(ModBlocks.machine_converter_he_rf, 1);
			break;
		case RF_TO_HE:
			output = new ItemStack(ModBlocks.machine_converter_rf_he, 1);
			break;
		case SHREDDER:
			output = new ItemStack(ModBlocks.machine_shredder, 1);
			break;
		case DEUTERIUM_EXTRACTOR:
			output = new ItemStack(ModBlocks.machine_deuterium, 1);
			break;
		case DERRICK:
			output = new ItemStack(ModBlocks.machine_well, 1);
			break;
		case FLARE_STACK:
			output = new ItemStack(ModBlocks.machine_flare, 1);
			break;
		case REFINERY:
			output = new ItemStack(ModBlocks.machine_refinery, 1);
			break;
		default:
			break;
		}
		
		return output;
	}
	
	public Map<Object[], Object> getAssemblyRecipes() {

		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		
        for (int i = 0; i < EnumAssemblyTemplate.values().length; ++i)
        {
        	ItemStack[] array = new ItemStack[13];
        	array[12] = new ItemStack(ModItems.assembly_template, 1, i);
        	List<ItemStack> list = MachineRecipes.getRecipeFromTempate(array[12]);
        	
        	for(int j = 0; j < list.size(); j++)
        		array[j] = list.get(j).copy();
        	
        	for(int j = 0; j < 12; j++)
        		if(array[j] == null)
        			array[j] = new ItemStack(ModItems.nothing);
        	
        	recipes.put(array, MachineRecipes.getOutputFromTempate(array[12]));
        }
		
		return recipes;
	}
	
	public Map<Object, Object[]> getRefineryRecipe() {

		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
        recipes.put(new ItemStack(ModItems.canister_oil, 1) , new ItemStack[] { 
        		new ItemStack(ModItems.canister_smear, 1), 
        		new ItemStack(ModItems.canister_canola, 1), 
        		new ItemStack(ModItems.canister_fuel, 1), 
        		new ItemStack(ModItems.canister_kerosene, 1), 
        		new ItemStack(ModItems.sulfur, 1) });
		
		return recipes;
	}
	
	public static List<ItemStack> getChemInputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		switch(ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
		case TEST:
			list.add(new ItemStack(Items.iron_ingot, 4));
			list.add(new ItemStack(Items.gold_ingot, 2));
			list.add(new ItemStack(Items.coal, 8));
			break;
        case CC_I:
			list.add(new ItemStack(Items.coal, 8));
			break;
        case CC_HEATING:
			list.add(new ItemStack(Items.coal, 8));
			break;
        case CC_HEAVY:
			list.add(new ItemStack(Items.coal, 10));
			break;
        case CC_NAPHTHA:
			list.add(new ItemStack(Items.coal, 10));
			break;
		default:
			break;
		}
		
		if(list.isEmpty())
			return null;
		else
			return list;
	}
	
	public static FluidStack[] getFluidInputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		
		FluidStack[] input = new FluidStack[2];
		
		switch(ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
		case TEST:
			input[0] = new FluidStack(400, FluidType.LAVA);
			input[1] = new FluidStack(200, FluidType.KEROSENE);
			break;
        case FP_HEAVYOIL:
			input[0] = new FluidStack(1000, FluidType.HEAVYOIL);
			break;
        case FP_SMEAR:
			input[0] = new FluidStack(1000, FluidType.SMEAR);
			break;
        case FP_NAPHTHA:
			input[0] = new FluidStack(1000, FluidType.NAPHTHA);
			break;
        case FP_LIGHTOIL:
			input[0] = new FluidStack(1000, FluidType.LIGHTOIL);
			break;
        case FR_REOIL:
			input[0] = new FluidStack(1000, FluidType.SMEAR);
			break;
        case FR_PETROIL:
			input[0] = new FluidStack(800, FluidType.RECLAIMED);
			input[1] = new FluidStack(200, FluidType.LUBRICANT);
			break;
        case FC_I_NAPHTHA:
			input[0] = new FluidStack(1400, FluidType.SMEAR);
			input[1] = new FluidStack(800, FluidType.WATER);
			break;
        case FC_GAS_PETROLEUM:
			input[0] = new FluidStack(1800, FluidType.GAS);
			input[1] = new FluidStack(1200, FluidType.WATER);
			break;
        case FC_DIESEL_KEROSENE:
			input[0] = new FluidStack(1200, FluidType.DIESEL);
			input[1] = new FluidStack(2000, FluidType.STEAM);
			break;
        case FC_KEROSENE_PETROLEUM:
			input[0] = new FluidStack(1400, FluidType.KEROSENE);
			input[1] = new FluidStack(2000, FluidType.STEAM);
			break;
        case CC_I:
			input[0] = new FluidStack(800, FluidType.SMEAR);
			input[1] = new FluidStack(1800, FluidType.WATER);
			break;
        case CC_HEATING:
			input[0] = new FluidStack(800, FluidType.HEATINGOIL);
			input[1] = new FluidStack(2000, FluidType.STEAM);
			break;
        case CC_HEAVY:
			input[0] = new FluidStack(600, FluidType.HEAVYOIL);
			input[1] = new FluidStack(1400, FluidType.WATER);
			break;
        case CC_NAPHTHA:
			input[0] = new FluidStack(1200, FluidType.NAPHTHA);
			input[1] = new FluidStack(2400, FluidType.STEAM);
			break;
		default:
			break;
		}
		
		return input;
	}
	
	public static ItemStack[] getChemOutputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		
		ItemStack[] output = new ItemStack[4];
		
		switch(ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
		case TEST:
			output[0] = new ItemStack(ModItems.ingot_steel);
			output[1] = new ItemStack(ModItems.ingot_desh, 2);
			break;
		default:
			break;
		}
		
		return output;
	}
	
	public static FluidStack[] getFluidOutputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		
		FluidStack[] input = new FluidStack[2];
		
		switch(ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
		case TEST:
			input[0] = new FluidStack(200, FluidType.WATER);
			break;
        case FP_HEAVYOIL:
			input[0] = new FluidStack(300, FluidType.BITUMEN);
			input[1] = new FluidStack(700, FluidType.SMEAR);
			break;
        case FP_SMEAR:
			input[0] = new FluidStack(600, FluidType.HEATINGOIL);
			input[1] = new FluidStack(400, FluidType.LUBRICANT);
			break;
        case FP_NAPHTHA:
			input[0] = new FluidStack(400, FluidType.HEATINGOIL);
			input[1] = new FluidStack(600, FluidType.DIESEL);
			break;
        case FP_LIGHTOIL:
			input[0] = new FluidStack(400, FluidType.DIESEL);
			input[1] = new FluidStack(600, FluidType.KEROSENE);
			break;
        case FR_REOIL:
			input[0] = new FluidStack(800, FluidType.RECLAIMED);
			break;
        case FR_PETROIL:
			input[0] = new FluidStack(1000, FluidType.PETROIL);
			break;
        case FC_I_NAPHTHA:
			input[0] = new FluidStack(800, FluidType.NAPHTHA);
			break;
        case FC_GAS_PETROLEUM:
			input[0] = new FluidStack(800, FluidType.PETROLEUM);
			break;
        case FC_DIESEL_KEROSENE:
			input[0] = new FluidStack(400, FluidType.KEROSENE);
			break;
        case FC_KEROSENE_PETROLEUM:
			input[0] = new FluidStack(800, FluidType.PETROLEUM);
			break;
        case CC_I:
			input[0] = new FluidStack(800, FluidType.SMEAR);
			break;
        case CC_HEATING:
			input[0] = new FluidStack(800, FluidType.HEATINGOIL);
			break;
        case CC_HEAVY:
			input[0] = new FluidStack(1800, FluidType.HEAVYOIL);
			break;
        case CC_NAPHTHA:
			input[0] = new FluidStack(2000, FluidType.NAPHTHA);
			break;
		default:
			break;
		}
		
		return input;
	}
}
