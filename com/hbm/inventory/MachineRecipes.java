package com.hbm.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemAssemblyTemplate.EnumAssemblyTemplate;
import com.hbm.items.tool.ItemChemistryTemplate;
import com.hbm.items.tool.ItemFluidIcon;
import com.hbm.main.MainRegistry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
		recipes.put(new ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted),
				getCentrifugeOutput(ModItems.rod_quad_schrabidium_fuel_depleted));
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
		recipes.put(new ItemStack(ModItems.rod_quad_schrabidium), getReactorOutput(ModItems.rod_quad_schrabidium));
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

			System.out.println("Added " + recipesShredder.size() + " in total.");
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
			
			recipesShredder.add(recipe);
		}
		
		public void overridePreSetRecipe(ItemStack inp, ItemStack outp) {
			
			boolean flag = false;
			
			for(int i = 0; i < recipesShredder.size(); i++)
			{
				if(recipesShredder.get(i) != null && 
						recipesShredder.get(i).input != null && 
						recipesShredder.get(i).output != null && 
						inp != null && 
						outp != null && 
						recipesShredder.get(i).input.getItem() == inp.getItem() && 
						recipesShredder.get(i).input.getItemDamage() == inp.getItemDamage()) {
					recipesShredder.get(i).output = outp;
					flag = true;
				}
			}
			
			if(!flag) {
				ShredderRecipe rec = new ShredderRecipe();
				rec.input = inp;
				rec.output = outp;
				recipesShredder.add(rec);
			}
		}
		
		public void removeDuplicates() {
			List<ShredderRecipe> newList = new ArrayList<ShredderRecipe>();
			
			for(ShredderRecipe piv : recipesShredder)
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

			System.out.println("TWT: " + theWholeThing.size() + ", REC: " + recipesShredder.size());
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

	public static List<ShredderRecipe> recipesShredder = new ArrayList<ShredderRecipe>();
	public static List<DictCouple> theWholeThing = new ArrayList<DictCouple>();
	public static int dustCount = 0;
	
	public static ItemStack getShredderResult(ItemStack stack) {
		for(ShredderRecipe rec : recipesShredder)
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
		
		for(int i = 0; i < MachineRecipes.recipesShredder.size(); i++) {
			if(MachineRecipes.recipesShredder.get(i) != null && MachineRecipes.recipesShredder.get(i).output.getItem() != ModItems.scrap)
				recipes.put(MachineRecipes.recipesShredder.get(i).input, getShredderResult(MachineRecipes.recipesShredder.get(i).input));
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
		case SCHRABIDIUM_HAMMER:
			list.add(new ItemStack(ModBlocks.block_schrabidium, 15));
			list.add(new ItemStack(ModItems.ingot_polymer, 128));
			list.add(new ItemStack(Items.nether_star, 2));
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
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
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
			list.add(new ItemStack(ModItems.ingot_polymer, 12));
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
			list.add(new ItemStack(ModItems.plate_copper, 1));
			break;
		case CIRCUIT_3:
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.powder_gold, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 1));
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
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
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
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
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
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
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
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_PURPLE_III:
			list.add(new ItemStack(ModItems.upgrade_fortune_2, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 2));
			list.add(new ItemStack(ModItems.powder_iron, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_PINK_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_polymer, 4));
			list.add(new ItemStack(ModItems.powder_tungsten, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_PINK_II:
			list.add(new ItemStack(ModItems.upgrade_afterburn_1, 1));
			list.add(new ItemStack(ModItems.powder_polymer, 2));
			list.add(new ItemStack(ModItems.powder_tungsten, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_PINK_III:
			list.add(new ItemStack(ModItems.upgrade_afterburn_2, 1));
			list.add(new ItemStack(ModItems.powder_polymer, 2));
			list.add(new ItemStack(ModItems.powder_tungsten, 6));
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
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.ingot_red_copper, 1));
			list.add(new ItemStack(Items.redstone, 4));
			list.add(new ItemStack(ModBlocks.steel_roof, 5));
			break;
		case CENTRIFUGE:
			list.add(new ItemStack(ModItems.centrifuge_tower, 1));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
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
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
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
			list.add(new ItemStack(ModItems.ingot_polymer, 24));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModBlocks.machine_battery, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 20));
			list.add(new ItemStack(ModItems.circuit_red_copper, 12));
			list.add(new ItemStack(ModItems.circuit_gold, 3));
			break;
		case RT_GENERATOR:
			list.add(new ItemStack(ModItems.rtg_unit, 5));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
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
		/*case DEUTERIUM_EXTRACTOR:
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.coil_tungsten, 4));
			break;*/
		case DERRICK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 20));
			list.add(new ItemStack(ModBlocks.steel_beam, 8));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.pipes_steel, 3));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			break;
		case PUMPJACK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 8));
			list.add(new ItemStack(ModBlocks.block_steel, 8));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 24));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModItems.plate_aluminium, 6));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			break;
		case FLARE_STACK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 28));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.thermo_element, 3));
			break;
		case REFINERY:
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.plate_steel, 24));
			list.add(new ItemStack(ModItems.plate_copper, 16));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.hull_big_steel, 6));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(ModItems.coil_tungsten, 10));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			break;
		case CHEMPLANT:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.plate_copper, 6));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 16));
			list.add(new ItemStack(ModItems.wire_tungsten, 3));
			list.add(new ItemStack(ModItems.circuit_copper, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 2));
			break;
		case TANK:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.hull_big_steel, 4));
			break;
		case MINER:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 12));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.circuit_copper, 2));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.ingot_dura_steel, 6));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 2));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			break;
		case TURBOFAN:
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_titanium, 3));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.turbine_tungsten, 1));
			list.add(new ItemStack(ModItems.turbine_titanium, 7));
			list.add(new ItemStack(ModItems.bolt_compound, 8));
			list.add(new ItemStack(ModItems.ingot_red_copper, 24));
			break;
		case TELEPORTER:
			list.add(new ItemStack(ModItems.ingot_titanium, 6));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 12));
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(ModItems.telepad, 1));
			list.add(new ItemStack(ModItems.entanglement_kit, 1));
			list.add(new ItemStack(ModBlocks.machine_battery, 2));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 4));
			break;
		case SCHRABTRANS:
			list.add(new ItemStack(ModItems.ingot_titanium, 24));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 18));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModBlocks.machine_battery, 5));
			list.add(new ItemStack(ModItems.ingot_magnetized_tungsten, 1));
			break;
		case CMB_FURNACE:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.plate_copper, 6));
			list.add(new ItemStack(ModItems.circuit_gold, 6));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.coil_tungsten, 4));
			list.add(new ItemStack(ModItems.ingot_magnetized_tungsten, 12));
			break;
		case FA_HULL:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 6));
			break;
		case FA_HATCH:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 2));
			break;
		case FA_CORE:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.motor, 16));
			list.add(new ItemStack(Blocks.piston, 6));
			break;
		case FA_PORT:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.fuse, 6));
			break;
		case LR_ELEMENT:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.neutron_reflector, 2));
			list.add(new ItemStack(ModItems.plate_lead, 2));
			list.add(new ItemStack(ModItems.rod_empty, 3));
			break;
		case LR_CONTROL:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case LR_HATCH:
			list.add(new ItemStack(ModBlocks.brick_concrete, 1));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			break;
		case LR_PORT:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.fuse, 6));
			break;
		case LR_CORE:
			list.add(new ItemStack(ModBlocks.reactor_conductor, 2));
			list.add(new ItemStack(ModItems.circuit_red_copper, 12));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			break;
		case LF_MAGNET:
			list.add(new ItemStack(ModItems.plate_steel, 10));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 5));
			break;
		case LF_CENTER:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 24));
			break;
		case LF_MOTOR:
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.motor, 4));
			break;
		case LF_HEATER:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.neutron_reflector, 6));
			list.add(new ItemStack(ModItems.magnetron, 4));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 4));
			break;
		case LF_HATCH:
			list.add(new ItemStack(ModBlocks.fusion_heater, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case LF_CORE:
			list.add(new ItemStack(ModBlocks.fusion_center, 3));
			list.add(new ItemStack(ModItems.circuit_red_copper, 48));
			list.add(new ItemStack(ModItems.circuit_gold, 12));
			break;
		case LW_ELEMENT:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.rod_empty, 4));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 2));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			break;
		case LW_CONTROL:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.ingot_lead, 2));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			list.add(new ItemStack(ModItems.circuit_copper, 2));
			break;
		case LW_COOLER:
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.niter, 6));
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			break;
		case LW_STRUTURE:
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.ingot_lead, 2));
			list.add(new ItemStack(ModItems.ingot_steel, 5));
			break;
		case LW_HATCH:
			list.add(new ItemStack(ModBlocks.reinforced_brick, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case LW_PORT:
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.ingot_lead, 2));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 2));
			list.add(new ItemStack(ModItems.fuse, 4));
			break;
		case LW_CORE:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 8));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 2));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 12));
			break;
		case FW_PORT:
			list.add(new ItemStack(ModItems.ingot_tungsten, 6));
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			break;
		case FW_MAGNET:
			list.add(new ItemStack(ModItems.plate_combine_steel, 10));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 5));
			break;
		case FW_COMPUTER:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 16));
			list.add(new ItemStack(ModItems.powder_diamond, 6));
			list.add(new ItemStack(ModItems.powder_magnetized_tungsten, 6));
			list.add(new ItemStack(ModItems.powder_desh, 4));
			break;
		case FW_CORE:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 24));
			list.add(new ItemStack(ModItems.powder_diamond, 8));
			list.add(new ItemStack(ModItems.powder_magnetized_tungsten, 12));
			list.add(new ItemStack(ModItems.powder_desh, 8));
			list.add(new ItemStack(ModItems.upgrade_power_3, 1));
			list.add(new ItemStack(ModItems.upgrade_speed_3, 1));
			break;
		case GADGET:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.fins_flat, 2));
			list.add(new ItemStack(ModItems.pedestal_steel, 1));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			list.add(new ItemStack(Items.dye, 6, 8));
			break;
		case LITTLE_BOY:
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_small_steel, 1));
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(new ItemStack(ModItems.wire_aluminium, 6));
			list.add(new ItemStack(Items.dye, 4, 4));
			break;
		case FAT_MAN:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.fins_big_steel, 1));
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			list.add(new ItemStack(Items.dye, 6, 11));
			break;
		case IVY_MIKE:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_aluminium, 4));
			list.add(new ItemStack(ModItems.cap_aluminium, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			list.add(new ItemStack(ModItems.wire_gold, 18));
			list.add(new ItemStack(Items.dye, 12, 7));
			break;
		case TSAR_BOMB:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_titanium, 6));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_tri_steel, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 6));
			list.add(new ItemStack(ModItems.wire_gold, 24));
			list.add(new ItemStack(ModItems.wire_tungsten, 12));
			list.add(new ItemStack(Items.dye, 6, 0));
			break;
		case PROTOTYPE:
			list.add(new ItemStack(ModItems.dysfunctional_reactor, 1));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.ingot_euphemium, 3, 34));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.wire_gold, 16));
			break;
		case FLEIJA:
			list.add(new ItemStack(ModItems.hull_small_aluminium, 1));
			list.add(new ItemStack(ModItems.fins_quad_titanium, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.wire_gold, 8));
			list.add(new ItemStack(Items.dye, 4, 15));
			break;
		case CUSTOM_NUKE:
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_small_steel, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			list.add(new ItemStack(ModItems.wire_gold, 12));
			list.add(new ItemStack(Items.dye, 4, 8));
			break;
		case BOMB_LEV:
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.levitation_unit, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			break;
		case BOMB_ENDO:
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.thermo_unit_endo, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			break;
		case BOMB_EXO:
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.thermo_unit_exo, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			break;
		case LAUNCH_PAD:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModBlocks.machine_battery, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			break;
		case TURRET_LIGHT:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_red_copper, 2));
			break;
		case TURRET_HEAVY:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_aluminium, 4));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.ingot_red_copper, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_red_copper, 3));
			break;
		case TURRET_ROCKET:
			list.add(new ItemStack(ModItems.ingot_steel, 12));
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.hull_small_steel, 8));
			list.add(new ItemStack(ModItems.ingot_red_copper, 6));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			break;
		case TURRET_FLAMER:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 1));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.ingot_red_copper, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_red_copper, 2));
			break;
		case TURRET_TAU:
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.ingot_titanium, 8));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.redcoil_capacitor, 3));
			list.add(new ItemStack(ModItems.ingot_red_copper, 12));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			break;
		case HUNTER_CHOPPER:
			list.add(new ItemStack(ModItems.chopper_blades, 5));
			list.add(new ItemStack(ModItems.chopper_gun, 1));
			list.add(new ItemStack(ModItems.chopper_head, 1));
			list.add(new ItemStack(ModItems.chopper_tail, 1));
			list.add(new ItemStack(ModItems.chopper_torso, 1));
			list.add(new ItemStack(ModItems.chopper_wing, 2));
			break;
		case MISSILE_HE_1:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case MISSILE_FIRE_1:
			list.add(new ItemStack(ModItems.warhead_incendiary_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case MISSILE_CLUSTER_1:
			list.add(new ItemStack(ModItems.warhead_cluster_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case MISSILE_BUSTER_1:
			list.add(new ItemStack(ModItems.warhead_buster_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case MISSILE_HE_2:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			break;
		case MISSILE_FIRE_2:
			list.add(new ItemStack(ModItems.warhead_incendiary_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			break;
		case MISSILE_CLUSTER_2:
			list.add(new ItemStack(ModItems.warhead_cluster_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			break;
		case MISSILE_BUSTER_2:
			list.add(new ItemStack(ModItems.warhead_buster_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			break;
		case MISSILE_HE_3:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			break;
		case MISSILE_FIRE_3:
			list.add(new ItemStack(ModItems.warhead_incendiary_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			break;
		case MISSILE_CLUSTER_3:
			list.add(new ItemStack(ModItems.warhead_cluster_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			break;
		case MISSILE_BUSTER_3:
			list.add(new ItemStack(ModItems.warhead_buster_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			break;
		case MISSILE_NUCLEAR:
			list.add(new ItemStack(ModItems.warhead_nuclear, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 24));
			list.add(new ItemStack(ModItems.plate_aluminium, 16));
			break;
		case MISSILE_MIRV:
			list.add(new ItemStack(ModItems.warhead_mirv, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 24));
			list.add(new ItemStack(ModItems.plate_aluminium, 16));
			break;
		case MISSILE_ENDO:
			list.add(new ItemStack(ModItems.warhead_thermo_endo, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			break;
		case MISSILE_EXO:
			list.add(new ItemStack(ModItems.warhead_thermo_exo, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			break;
		case DEFAB:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.ingot_polymer, 8));
			list.add(new ItemStack(ModItems.plate_iron, 5));
			list.add(new ItemStack(Items.diamond, 1));
			list.add(new ItemStack(ModItems.plate_dalekanium, 3));
			break;
		case MINI_NUKE:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.nugget_pu239, 3));
			break;
		case MINI_MIRV:
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_iron, 10));
			list.add(new ItemStack(ModItems.nugget_pu239, 24));
			break;
		case DARK_PLUG:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(Items.redstone, 1));
			list.add(new ItemStack(Items.glowstone_dust, 1));
			break;
		case COMBINE_BALL:
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(Items.redstone, 7));
			list.add(new ItemStack(ModItems.powder_power, 3));
			break;
		case GRENADE_FLAME:
			list.add(new ItemStack(ModItems.grenade_frag, 1));
			list.add(new ItemStack(ModItems.powder_fire, 1));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			break;
		case GRENADE_SHRAPNEL:
			list.add(new ItemStack(ModItems.grenade_frag, 1));
			list.add(new ItemStack(ModItems.pellet_buckshot, 1));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case GRENAGE_CLUSTER:
			list.add(new ItemStack(ModItems.grenade_frag, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 1));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case GREANADE_FLARE:
			list.add(new ItemStack(ModItems.grenade_generic, 1));
			list.add(new ItemStack(Items.glowstone_dust, 1));
			list.add(new ItemStack(ModItems.plate_aluminium, 2));
			break;
		case GRENADE_LIGHTNING:
			list.add(new ItemStack(ModItems.grenade_generic, 1));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.plate_gold, 2));
			break;
		case GRENADE_IMPULSE:
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.plate_iron, 3));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(Items.diamond, 1));
			break;
		case GRENADE_PLASMA:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 1));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 1));
			list.add(new ItemStack(ModItems.cell_deuterium, 1));
			list.add(new ItemStack(ModItems.cell_tritium, 1));
			break;
		case GRENADE_TAU:
			list.add(new ItemStack(ModItems.plate_lead, 3));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 1));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 1));
			list.add(new ItemStack(ModItems.gun_xvl1456_ammo, 1));
			break;
		case GRENADE_SCHRABIDIUM:
			list.add(new ItemStack(ModItems.grenade_flare, 1));
			list.add(new ItemStack(ModItems.powder_schrabidium, 1));
			list.add(new ItemStack(ModItems.neutron_reflector, 2));
			break;
		case GRENADE_NUKE:
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.nugget_pu239, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 2));
			break;
		case GRENADE_ZOMG:
			list.add(new ItemStack(ModItems.plate_paa, 3));
			list.add(new ItemStack(ModItems.neutron_reflector, 1));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 3));
			list.add(new ItemStack(ModItems.powder_power, 3));
			break;
		case GRENADE_BLACK_HOLE:
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			list.add(new ItemStack(ModItems.neutron_reflector, 3));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 2));
			list.add(new ItemStack(ModItems.black_hole, 1));
			break;
		case POWER_FIST:
			list.add(new ItemStack(ModItems.rod_reiium, 1));
			list.add(new ItemStack(ModItems.rod_weidanium, 1));
			list.add(new ItemStack(ModItems.rod_australium, 1));
			list.add(new ItemStack(ModItems.rod_verticium, 1));
			list.add(new ItemStack(ModItems.rod_unobtainium, 1));
			list.add(new ItemStack(ModItems.rod_daffergon, 1));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			list.add(new ItemStack(ModItems.ducttape, 1));
			break;
		case GADGET_PROPELLANT:
			list.add(new ItemStack(Blocks.tnt, 3));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.plate_aluminium, 4));
			list.add(new ItemStack(ModItems.wire_gold, 3));
			break;
		case GADGET_WIRING:
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.wire_gold, 12));
			break;
		case GADGET_CORE:
			list.add(new ItemStack(ModItems.nugget_pu239, 7));
			list.add(new ItemStack(ModItems.nugget_u238, 3));
			break;
		case BOY_SHIELDING:
			list.add(new ItemStack(ModItems.neutron_reflector, 12));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			break;
		case BOY_TARGET:
			list.add(new ItemStack(ModItems.nugget_u235, 7));
			break;
		case BOY_BULLET:
			list.add(new ItemStack(ModItems.nugget_u235, 3));
			break;
		case BOY_PRPELLANT:
			list.add(new ItemStack(Blocks.tnt, 3));
			list.add(new ItemStack(ModItems.plate_iron, 8));
			list.add(new ItemStack(ModItems.plate_aluminium, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case BOY_IGNITER:
			list.add(new ItemStack(ModItems.plate_aluminium, 6));
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 3));
			break;
		case MAN_PROPELLANT:
			list.add(new ItemStack(Blocks.tnt, 3));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 3));
			break;
		case MAN_IGNITER:
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 9));
			break;
		case MAN_CORE:
			list.add(new ItemStack(ModItems.nugget_pu239, 8));
			list.add(new ItemStack(ModItems.nugget_beryllium, 2));
			break;
		case MIKE_TANK:
			list.add(new ItemStack(ModItems.nugget_u238, 24));
			list.add(new ItemStack(ModItems.ingot_lead, 6));
			break;
		case MIKE_DEUT:
			list.add(new ItemStack(ModItems.plate_iron, 12));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModItems.cell_deuterium, 10));
			break;
		case MIKE_COOLER:
			list.add(new ItemStack(ModItems.plate_iron, 8));
			list.add(new ItemStack(ModItems.coil_copper, 5));
			list.add(new ItemStack(ModItems.coil_tungsten, 5));
			list.add(new ItemStack(ModItems.motor, 2));
			break;
		case FLEIIJA_IGNITER:
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_schrabidium, 2));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 1));
			break;
		case FLEIJA_CORE:
			list.add(new ItemStack(ModItems.nugget_u235, 8));
			list.add(new ItemStack(ModItems.nugget_neptunium, 2));
			list.add(new ItemStack(ModItems.nugget_beryllium, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			break;
		case FLEIJA_PROPELLANT:
			list.add(new ItemStack(Blocks.tnt, 3));
			list.add(new ItemStack(ModItems.plate_schrabidium, 8));
			break;
		default:
			list.add(new ItemStack(Items.stick));
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
		case SCHRABIDIUM_HAMMER:
			output = new ItemStack(ModItems.schrabidium_hammer, 1);
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
		case UPGRADE_PINK_I:
			output = new ItemStack(ModItems.upgrade_afterburn_1, 1);
			break;
		case UPGRADE_PINK_II:
			output = new ItemStack(ModItems.upgrade_afterburn_2, 1);
			break;
		case UPGRADE_PINK_III:
			output = new ItemStack(ModItems.upgrade_afterburn_3, 1);
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
		//case DEUTERIUM_EXTRACTOR:
		//	output = new ItemStack(ModBlocks.machine_deuterium, 1);
		//	break;
		case DERRICK:
			output = new ItemStack(ModBlocks.machine_well, 1);
			break;
		case PUMPJACK:
			output = new ItemStack(ModBlocks.machine_pumpjack, 1);
			break;
		case FLARE_STACK:
			output = new ItemStack(ModBlocks.machine_flare, 1);
			break;
		case REFINERY:
			output = new ItemStack(ModBlocks.machine_refinery, 1);
			break;
		case CHEMPLANT:
			output = new ItemStack(ModBlocks.machine_chemplant, 1);
			break;
		case TANK:
			output = new ItemStack(ModBlocks.machine_fluidtank, 1);
			break;
		case MINER:
			output = new ItemStack(ModBlocks.machine_drill, 1);
			break;
		case TURBOFAN:
			output = new ItemStack(ModBlocks.machine_turbofan, 1);
			break;
		case TELEPORTER:
			output = new ItemStack(ModBlocks.machine_teleporter, 1);
			break;
		case SCHRABTRANS:
			output = new ItemStack(ModBlocks.machine_schrabidium_transmutator, 1);
			break;
		case CMB_FURNACE:
			output = new ItemStack(ModBlocks.machine_combine_factory, 1);
			break;
		case FA_HULL:
			output = new ItemStack(ModBlocks.factory_advanced_hull, 1);
			break;
		case FA_HATCH:
			output = new ItemStack(ModBlocks.factory_advanced_furnace, 1);
			break;
		case FA_CORE:
			output = new ItemStack(ModBlocks.factory_advanced_core, 1);
			break;
		case FA_PORT:
			output = new ItemStack(ModBlocks.factory_advanced_conductor, 1);
			break;
		case LR_ELEMENT:
			output = new ItemStack(ModBlocks.reactor_element, 1);
			break;
		case LR_CONTROL:
			output = new ItemStack(ModBlocks.reactor_control, 1);
			break;
		case LR_HATCH:
			output = new ItemStack(ModBlocks.reactor_hatch, 1);
			break;
		case LR_PORT:
			output = new ItemStack(ModBlocks.reactor_conductor, 1);
			break;
		case LR_CORE:
			output = new ItemStack(ModBlocks.reactor_computer, 1);
			break;
		case LF_MAGNET:
			output = new ItemStack(ModBlocks.fusion_conductor, 1);
			break;
		case LF_CENTER:
			output = new ItemStack(ModBlocks.fusion_center, 1);
			break;
		case LF_MOTOR:
			output = new ItemStack(ModBlocks.fusion_motor, 1);
			break;
		case LF_HEATER:
			output = new ItemStack(ModBlocks.fusion_heater, 1);
			break;
		case LF_HATCH:
			output = new ItemStack(ModBlocks.fusion_hatch, 1);
			break;
		case LF_CORE:
			output = new ItemStack(ModBlocks.fusion_core, 1);
			break;
		case LW_ELEMENT:
			output = new ItemStack(ModBlocks.watz_element, 1);
			break;
		case LW_CONTROL:
			output = new ItemStack(ModBlocks.watz_control, 1);
			break;
		case LW_COOLER:
			output = new ItemStack(ModBlocks.watz_cooler, 1);
			break;
		case LW_STRUTURE:
			output = new ItemStack(ModBlocks.watz_end, 1);
			break;
		case LW_HATCH:
			output = new ItemStack(ModBlocks.watz_hatch, 1);
			break;
		case LW_PORT:
			output = new ItemStack(ModBlocks.watz_conductor, 1);
			break;
		case LW_CORE:
			output = new ItemStack(ModBlocks.watz_core, 1);
			break;
		case FW_PORT:
			output = new ItemStack(ModBlocks.fwatz_hatch, 1);
			break;
		case FW_MAGNET:
			output = new ItemStack(ModBlocks.fwatz_conductor, 1);
			break;
		case FW_COMPUTER:
			output = new ItemStack(ModBlocks.fwatz_computer, 1);
			break;
		case FW_CORE:
			output = new ItemStack(ModBlocks.fwatz_core, 1);
			break;
		case GADGET:
			output = new ItemStack(ModBlocks.nuke_gadget, 1);
			break;
		case LITTLE_BOY:
			output = new ItemStack(ModBlocks.nuke_boy, 1);
			break;
		case FAT_MAN:
			output = new ItemStack(ModBlocks.nuke_man, 1);
			break;
		case IVY_MIKE:
			output = new ItemStack(ModBlocks.nuke_mike, 1);
			break;
		case TSAR_BOMB:
			output = new ItemStack(ModBlocks.nuke_tsar, 1);
			break;
		case PROTOTYPE:
			output = new ItemStack(ModBlocks.nuke_prototype, 1);
			break;
		case FLEIJA:
			output = new ItemStack(ModBlocks.nuke_fleija, 1);
			break;
		case CUSTOM_NUKE:
			output = new ItemStack(ModBlocks.nuke_custom, 1);
			break;
		case BOMB_LEV:
			output = new ItemStack(ModBlocks.float_bomb, 1);
			break;
		case BOMB_ENDO:
			output = new ItemStack(ModBlocks.therm_endo, 1);
			break;
		case BOMB_EXO:
			output = new ItemStack(ModBlocks.therm_exo, 1);
			break;
		case LAUNCH_PAD:
			output = new ItemStack(ModBlocks.launch_pad, 1);
			break;
		case TURRET_LIGHT:
			output = new ItemStack(ModBlocks.turret_light, 1);
			break;
		case TURRET_HEAVY:
			output = new ItemStack(ModBlocks.turret_heavy, 1);
			break;
		case TURRET_ROCKET:
			output = new ItemStack(ModBlocks.turret_rocket, 1);
			break;
		case TURRET_FLAMER:
			output = new ItemStack(ModBlocks.turret_flamer, 1);
			break;
		case TURRET_TAU:
			output = new ItemStack(ModBlocks.turret_tau, 1);
			break;
		case HUNTER_CHOPPER:
			output = new ItemStack(ModItems.chopper, 1);
			break;
		case MISSILE_HE_1:
			output = new ItemStack(ModItems.missile_generic, 1);
			break;
		case MISSILE_FIRE_1:
			output = new ItemStack(ModItems.missile_incendiary, 1);
			break;
		case MISSILE_CLUSTER_1:
			output = new ItemStack(ModItems.missile_cluster, 1);
			break;
		case MISSILE_BUSTER_1:
			output = new ItemStack(ModItems.missile_buster, 1);
			break;
		case MISSILE_HE_2:
			output = new ItemStack(ModItems.missile_strong, 1);
			break;
		case MISSILE_FIRE_2:
			output = new ItemStack(ModItems.missile_incendiary_strong, 1);
			break;
		case MISSILE_CLUSTER_2:
			output = new ItemStack(ModItems.missile_cluster_strong, 1);
			break;
		case MISSILE_BUSTER_2:
			output = new ItemStack(ModItems.missile_buster_strong, 1);
			break;
		case MISSILE_HE_3:
			output = new ItemStack(ModItems.missile_burst, 1);
			break;
		case MISSILE_FIRE_3:
			output = new ItemStack(ModItems.missile_inferno, 1);
			break;
		case MISSILE_CLUSTER_3:
			output = new ItemStack(ModItems.missile_rain, 1);
			break;
		case MISSILE_BUSTER_3:
			output = new ItemStack(ModItems.missile_drill, 1);
			break;
		case MISSILE_NUCLEAR:
			output = new ItemStack(ModItems.missile_nuclear, 1);
			break;
		case MISSILE_MIRV:
			output = new ItemStack(ModItems.missile_nuclear_cluster, 1);
			break;
		case MISSILE_ENDO:
			output = new ItemStack(ModItems.missile_endo, 1);
			break;
		case MISSILE_EXO:
			output = new ItemStack(ModItems.missile_exo, 1);
			break;
		case DEFAB:
			output = new ItemStack(ModItems.gun_defabricator, 1);
			break;
		case MINI_NUKE:
			output = new ItemStack(ModItems.gun_fatman_ammo, 1);
			break;
		case MINI_MIRV:
			output = new ItemStack(ModItems.gun_mirv_ammo, 1);
			break;
		case DARK_PLUG:
			output = new ItemStack(ModItems.gun_osipr_ammo, 24);
			break;
		case COMBINE_BALL:
			output = new ItemStack(ModItems.gun_osipr_ammo2, 1);
			break;
		case GRENADE_FLAME:
			output = new ItemStack(ModItems.grenade_fire, 1);
			break;
		case GRENADE_SHRAPNEL:
			output = new ItemStack(ModItems.grenade_shrapnel, 1);
			break;
		case GRENAGE_CLUSTER:
			output = new ItemStack(ModItems.grenade_cluster, 1);
			break;
		case GREANADE_FLARE:
			output = new ItemStack(ModItems.grenade_flare, 1);
			break;
		case GRENADE_LIGHTNING:
			output = new ItemStack(ModItems.grenade_electric, 1);
			break;
		case GRENADE_IMPULSE:
			output = new ItemStack(ModItems.grenade_pulse, 4);
			break;
		case GRENADE_PLASMA:
			output = new ItemStack(ModItems.grenade_plasma, 2);
			break;
		case GRENADE_TAU:
			output = new ItemStack(ModItems.grenade_tau, 2);
			break;
		case GRENADE_SCHRABIDIUM:
			output = new ItemStack(ModItems.grenade_schrabidium, 1);
			break;
		case GRENADE_NUKE:
			output = new ItemStack(ModItems.grenade_nuclear, 1);
			break;
		case GRENADE_ZOMG:
			output = new ItemStack(ModItems.grenade_zomg, 1);
			break;
		case GRENADE_BLACK_HOLE:
			output = new ItemStack(ModItems.grenade_black_hole, 1);
			break;
		case POWER_FIST:
			ItemStack multitool = new ItemStack(ModItems.multitool_dig, 1);
			multitool.addEnchantment(Enchantment.looting, 3);
			multitool.addEnchantment(Enchantment.fortune, 3);
			output = multitool.copy();
			break;
		case GADGET_PROPELLANT:
			output = new ItemStack(ModItems.gadget_explosive, 1);
			break;
		case GADGET_WIRING:
			output = new ItemStack(ModItems.gadget_wireing, 1);
			break;
		case GADGET_CORE:
			output = new ItemStack(ModItems.gadget_core, 1);
			break;
		case BOY_SHIELDING:
			output = new ItemStack(ModItems.boy_shielding, 1);
			break;
		case BOY_TARGET:
			output = new ItemStack(ModItems.boy_target, 1);
			break;
		case BOY_BULLET:
			output = new ItemStack(ModItems.boy_bullet, 1);
			break;
		case BOY_PRPELLANT:
			output = new ItemStack(ModItems.boy_propellant, 1);
			break;
		case BOY_IGNITER:
			output = new ItemStack(ModItems.boy_igniter, 1);
			break;
		case MAN_PROPELLANT:
			output = new ItemStack(ModItems.man_explosive, 1);
			break;
		case MAN_IGNITER:
			output = new ItemStack(ModItems.man_igniter, 1);
			break;
		case MAN_CORE:
			output = new ItemStack(ModItems.man_core, 1);
			break;
		case MIKE_TANK:
			output = new ItemStack(ModItems.mike_core, 1);
			break;
		case MIKE_DEUT:
			output = new ItemStack(ModItems.mike_deut, 1);
			break;
		case MIKE_COOLER:
			output = new ItemStack(ModItems.mike_cooling_unit, 1);
			break;
		case FLEIIJA_IGNITER:
			output = new ItemStack(ModItems.fleija_igniter, 1);
			break;
		case FLEIJA_CORE:
			output = new ItemStack(ModItems.fleija_core, 1);
			break;
		case FLEIJA_PROPELLANT:
			output = new ItemStack(ModItems.fleija_propellant, 1);
			break;
		default:
			output = new ItemStack(Items.stick, 1);
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
	
	public Map<Object[], Object[]> getChemistryRecipes() {

		Map<Object[], Object[]> recipes = new HashMap<Object[], Object[]>();
		
        for (int i = 0; i < ItemChemistryTemplate.EnumChemistryTemplate.values().length; ++i)
        {
        	ItemStack[] inputs = new ItemStack[7];
        	ItemStack[] outputs = new ItemStack[6];
        	inputs[6] = new ItemStack(ModItems.chemistry_template, 1, i);
        	
        	List<ItemStack> listIn = MachineRecipes.getChemInputFromTempate(inputs[6]);
        	if(listIn != null)
        		for(int j = 0; j < listIn.size(); j++)
        			if(listIn.get(j) != null)
        				inputs[j + 2] = listIn.get(j).copy();
        	
        	FluidStack[] fluidIn = MachineRecipes.getFluidInputFromTempate(inputs[6]);
        	for(int j = 0; j < fluidIn.length; j++)
        		if(fluidIn[j] != null)
        			inputs[j] = ItemFluidIcon.addQuantity(new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(fluidIn[j].type)), fluidIn[j].fill);
        	
        	ItemStack[] listOut = MachineRecipes.getChemOutputFromTempate(inputs[6]);
        	for(int j = 0; j < listOut.length; j++)
        		if(listOut[j] != null)
        			outputs[j + 2] = listOut[j].copy();
        	
        	FluidStack[] fluidOut = MachineRecipes.getFluidOutputFromTempate(inputs[6]);
        	for(int j = 0; j < fluidOut.length; j++)
        		if(fluidOut[j] != null)
        			outputs[j] = ItemFluidIcon.addQuantity(new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(fluidOut[j].type)), fluidOut[j].fill);
        	
        	for(int j = 0; j < inputs.length; j++)
        		if(inputs[j] == null)
        			inputs[j] = new ItemStack(ModItems.nothing);
        	
        	for(int j = 0; j < outputs.length; j++)
        		if(outputs[j] == null)
        			outputs[j] = new ItemStack(ModItems.nothing);
        	
        	recipes.put(inputs, outputs);
        }
		
		return recipes;
	}
	
	public Map<Object, Object[]> getRefineryRecipe() {

		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();

		ItemStack oil = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.OIL));
		oil.stackTagCompound = new NBTTagCompound();
		oil.stackTagCompound.setInteger("fill", 1000);
		
		ItemStack heavy = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.HEAVYOIL));
		heavy.stackTagCompound = new NBTTagCompound();
		heavy.stackTagCompound.setInteger("fill", 500);
		
		ItemStack naphtha = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.NAPHTHA));
		naphtha.stackTagCompound = new NBTTagCompound();
		naphtha.stackTagCompound.setInteger("fill", 250);
		
		ItemStack light = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.LIGHTOIL));
		light.stackTagCompound = new NBTTagCompound();
		light.stackTagCompound.setInteger("fill", 150);
		
		ItemStack petroleum = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.PETROLEUM));
		petroleum.stackTagCompound = new NBTTagCompound();
		petroleum.stackTagCompound.setInteger("fill", 100);
		
        recipes.put(oil , new ItemStack[] { 
        		heavy, 
        		naphtha, 
        		light, 
        		petroleum, 
        		new ItemStack(ModItems.sulfur, 1) });
		
		return recipes;
	}
	
	public static List<ItemStack> getChemInputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		switch(ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
        case CC_OIL:
			list.add(new ItemStack(Items.coal, 10));
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
        case ASPHALT:
			list.add(new ItemStack(Blocks.gravel, 2));
			list.add(new ItemStack(Blocks.sand, 6));
			break;
        case COOLANT:
			list.add(new ItemStack(ModItems.niter, 1));
			break;
        case DESH:
			list.add(new ItemStack(ModItems.powder_desh_mix, 2));
			break;
        case CIRCUIT_4:
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			list.add(new ItemStack(ModItems.powder_lapis, 4));
			list.add(new ItemStack(ModItems.ingot_desh, 1));
			break;
        case CIRCUIT_5:
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			list.add(new ItemStack(ModItems.wire_schrabidium, 6));
			list.add(new ItemStack(ModItems.powder_diamond, 4));
			list.add(new ItemStack(ModItems.powder_power, 1));
			break;
        case POLYMER:
			list.add(new ItemStack(Items.coal, 2));
			list.add(new ItemStack(ModItems.fluorite, 1));
			break;
        case DEUTERIUM:
			list.add(new ItemStack(ModItems.sulfur, 1));
			break;
        case BP_BIOGAS:
			list.add(new ItemStack(ModItems.biomass, 16));
			break;
        case UF6:
			list.add(new ItemStack(ModItems.powder_uranium, 1));
			list.add(new ItemStack(ModItems.fluorite, 3));
			break;
        case PUF6:
			list.add(new ItemStack(ModItems.powder_plutonium, 1));
			list.add(new ItemStack(ModItems.fluorite, 3));
			break;
        case SAS3:
			list.add(new ItemStack(ModItems.powder_schrabidium, 1));
			list.add(new ItemStack(ModItems.sulfur, 2));
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
        case CC_OIL:
			input[0] = new FluidStack(600, FluidType.OIL);
			input[1] = new FluidStack(1400, FluidType.STEAM);
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
        case ASPHALT:
			input[0] = new FluidStack(8000, FluidType.BITUMEN);
			break;
        case COOLANT:
			input[0] = new FluidStack(1800, FluidType.WATER);
			break;
        case DESH:
			input[0] = new FluidStack(800, FluidType.ACID);
			input[1] = new FluidStack(200, FluidType.LIGHTOIL);
			break;
        case PEROXIDE:
			input[0] = new FluidStack(1000, FluidType.WATER);
        	break;
        case CIRCUIT_4:
			input[0] = new FluidStack(400, FluidType.ACID);
        	break;
        case CIRCUIT_5:
			input[0] = new FluidStack(800, FluidType.ACID);
			input[1] = new FluidStack(400, FluidType.PETROLEUM);
        	break;
        case SF_OIL:
			input[0] = new FluidStack(350, FluidType.OIL);
        	break;
        case SF_HEAVYOIL:
			input[0] = new FluidStack(250, FluidType.HEAVYOIL);
        	break;
        case SF_SMEAR:
			input[0] = new FluidStack(200, FluidType.SMEAR);
        	break;
        case SF_HEATINGOIL:
			input[0] = new FluidStack(100, FluidType.HEATINGOIL);
        	break;
        case SF_RECLAIMED:
			input[0] = new FluidStack(200, FluidType.RECLAIMED);
        	break;
        case SF_PETROIL:
			input[0] = new FluidStack(250, FluidType.PETROIL);
        	break;
    	case SF_LUBRICANT:
			input[0] = new FluidStack(250, FluidType.LUBRICANT);
        	break;
    	case SF_NAPHTHA:
			input[0] = new FluidStack(300, FluidType.NAPHTHA);
        	break;
    	case SF_DIESEL:
			input[0] = new FluidStack(400, FluidType.DIESEL);
        	break;
    	case SF_LIGHTOIL:
			input[0] = new FluidStack(450, FluidType.LIGHTOIL);
        	break;
    	case SF_KEROSENE:
			input[0] = new FluidStack(550, FluidType.KEROSENE);
        	break;
    	case SF_GAS:
			input[0] = new FluidStack(750, FluidType.GAS);
        	break;
    	case SF_PETROLEUM:
			input[0] = new FluidStack(600, FluidType.PETROLEUM);
        	break;
    	case SF_BIOGAS:
			input[0] = new FluidStack(400, FluidType.BIOGAS);
        	break;
    	case SF_BIOFUEL:
			input[0] = new FluidStack(300, FluidType.BIOFUEL);
        	break;
        case POLYMER:
			input[0] = new FluidStack(600, FluidType.PETROLEUM);
        	break;
        case DEUTERIUM:
			input[0] = new FluidStack(4000, FluidType.WATER);
        	break;
        case STEAM:
			input[0] = new FluidStack(1000, FluidType.WATER);
        	break;
        case BP_BIOFUEL:
			input[0] = new FluidStack(2000, FluidType.BIOGAS);
        	break;
        case UF6:
			input[0] = new FluidStack(1000, FluidType.WATER);
        	break;
        case PUF6:
			input[0] = new FluidStack(1000, FluidType.WATER);
        	break;
        case SAS3:
			input[0] = new FluidStack(2000, FluidType.ACID);
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
		case ASPHALT:
			output[0] = new ItemStack(ModBlocks.asphalt, 4);
			output[1] = new ItemStack(ModBlocks.asphalt, 4);
			output[2] = new ItemStack(ModBlocks.asphalt, 4);
			output[3] = new ItemStack(ModBlocks.asphalt, 4);
			break;
		case DESH:
			output[0] = new ItemStack(ModItems.ingot_desh, 1);
			break;
		case CIRCUIT_4:
			output[0] = new ItemStack(ModItems.circuit_gold, 1);
			break;
		case CIRCUIT_5:
			output[0] = new ItemStack(ModItems.circuit_schrabidium, 1);
			break;
        case SF_OIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_HEAVYOIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_SMEAR:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_HEATINGOIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_RECLAIMED:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_PETROIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_LUBRICANT:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_NAPHTHA:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_DIESEL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_LIGHTOIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_KEROSENE:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_GAS:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_PETROLEUM:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_BIOGAS:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_BIOFUEL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case POLYMER:
			output[0] = new ItemStack(ModItems.ingot_polymer, 1);
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
        case CC_OIL:
			input[0] = new FluidStack(1000, FluidType.OIL);
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
        case COOLANT:
			input[0] = new FluidStack(2000, FluidType.COOLANT);
			break;
        case PEROXIDE:
			input[0] = new FluidStack(800, FluidType.ACID);
			break;
        case DEUTERIUM:
			input[0] = new FluidStack(500, FluidType.DEUTERIUM);
        	break;
        case STEAM:
			input[0] = new FluidStack(1000, FluidType.STEAM);
        	break;
        case BP_BIOGAS:
			input[0] = new FluidStack(4000, FluidType.BIOGAS);
        	break;
        case BP_BIOFUEL:
			input[0] = new FluidStack(1000, FluidType.BIOFUEL);
        	break;
        case UF6:
			input[0] = new FluidStack(1000, FluidType.UF6);
        	break;
        case PUF6:
			input[0] = new FluidStack(1000, FluidType.PUF6);
        	break;
        case SAS3:
			input[0] = new FluidStack(1000, FluidType.SAS3);
        	break;
		default:
			break;
		}
		
		return input;
	}
	
	public String[] getInfoFromItem(ItemStack stack) {
		stack.stackSize = 1;

		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.asphalt))
			return new String[] { "Explosion resisant block", "Made from bitumen" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_uranium))
			return new String[] { "Block of uranium ore", "Found below Y:25" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_titanium))
			return new String[] { "Block of titanium ore", "Found below Y:35" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_sulfur))
			return new String[] { "Block of sulfur ore", "Found below Y:35", "Drops as sulfur dust" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_niter))
			return new String[] { "Block of niter ore", "Found below Y:35", "Drops as niter dust" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_copper))
			return new String[] { "Block of copper ore", "Found below Y:50" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_tungsten))
			return new String[] { "Block of tungsten ore", "Found below Y:35" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_aluminium))
			return new String[] { "Block of aluminium ore", "Found below Y:45" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_fluorite))
			return new String[] { "Block of fluorite ore", "Found below Y:40" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_beryllium))
			return new String[] { "Block of beryllium ore", "Found below Y:35" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_lead))
			return new String[] { "Block of lead ore", "Found below Y:35" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_oil))
			return new String[] { "Block of oil deposit", "Found below Y:25", "Spawns in large bubbles", "Exctractable via oil derrick" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_schrabidium))
			return new String[] { "Block of schrabidium ore", "Does not spawn naturally", "Created by nukes near uranium" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_australium))
			return new String[] { "Block of australium ore", "Found between Y:15 and Y:30", "Deposit location: X:-400, Z:-400" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_weidanium))
			return new String[] { "Block of weidanium ore", "Found below Y:25", "Deposit location: X:0, Z:300" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_reiium))
			return new String[] { "Block of reiium ore", "Found below Y:35", "Deposit location: X:0, Z:0" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_unobtainium))
			return new String[] { "Block of unobtainium ore", "Found below Y:128", "Deposit location: X:200, Z:200" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_daffergon))
			return new String[] { "Block of daffergon ore", "Found below Y:10", "Deposit location: X:400, Z:-200" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_verticium))
			return new String[] { "Block of verticium ore", "Found between Y:25 and Y:50", "Deposit location: X:-300, Z:200" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_uranium))
			return new String[] { "Block of uranium ore", "Only found in the nether" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_plutonium))
			return new String[] { "Block of plutonium ore", "Only found in the nether", "Disabled in config by default" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_tungsten))
			return new String[] { "Block of tungsten ore", "Only found in the nether" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_sulfur))
			return new String[] { "Block of sulfur ore", "Only found in the nether" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_fire))
			return new String[] { "Block of fire ore", "Only found in the nether", "Drops blaze or fire powder" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_schrabidium))
			return new String[] { "Block of schrabidium ore", "Does not spawn naturally" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.reinforced_brick))
			return new String[] { "Reinforced block", "Withstands nuclear explosions" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.reinforced_glass))
			return new String[] { "Reinforced block", "Withstands nuclear explosions" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.reinforced_light))
			return new String[] { "Reinforced block", "Withstands nuclear explosions" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.reinforced_sand))
			return new String[] { "Reinforced block", "Withstands nuclear explosions" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.reinforced_lamp_off))
			return new String[] { "Reinforced block", "Withstands nuclear explosions" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.brick_concrete))
			return new String[] { "Reinforced block", "Mostly withstands nukes" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.brick_obsidian))
			return new String[] { "Reinforced block", "Greatly withstands nukes" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.brick_light))
			return new String[] { "Reinforced block", "Barely withstands nukes" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.cmb_brick))
			return new String[] { "Reinforced block", "Withstands nuclear explosions" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.cmb_brick_reinforced))
			return new String[] { "Reinforced block", "Withstands nuclear explosions" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.cmb_brick_reinforced))
			return new String[] { "Reinforced block", "Withstands nuclear explosions" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.block_meteor))
			return new String[] { "Only found on space ships", "Used for late-game reactors", "Sometimes drops angry metal" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.tape_recorder))
			return new String[] { "Decorative block" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.steel_poles))
			return new String[] { "Decorative block" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.pole_top))
			return new String[] { "Decorative block" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.pole_satellite_receiver))
			return new String[] { "Decorative block" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.steel_wall))
			return new String[] { "Decorative block" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.steel_corner))
			return new String[] { "Decorative block" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.steel_roof))
			return new String[] { "Decorative block" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.steel_beam))
			return new String[] { "Decorative block" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.steel_scaffold))
			return new String[] { "Decorative block" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.mush))
			return new String[] { "Only grows on dead grass", "or on glowing mycelium", "Spreads" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.waste_earth))
			return new String[] { "Radioactive grass", "Created by nuclear explosions" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.waste_mycelium))
			return new String[] { "Radioactive mycelium", "Spreads onto dirt blocks", "Spread disabled by default" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.waste_trinitite))
			return new String[] { "Radioactive sand ore", "Created by nuclear explosions", "Drops trinitite" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.waste_trinitite_red))
			return new String[] { "Radioactive sand ore", "Created by nuclear explosions", "Drops trinitite" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.waste_log))
			return new String[] { "Burnt log block", "Created by nuclear explosions", "Drops charcoal" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.waste_planks))
			return new String[] { "Burnt planks block", "Created by nuclear explosions", "Drops charcoal" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.frozen_grass))
			return new String[] { "Frozen grass block", "Drops snowballs" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.frozen_dirt))
			return new String[] { "Frozen dirt block", "Drops snowballs" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.frozen_log))
			return new String[] { "Frozen log block", "Drops snowballs" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.frozen_planks))
			return new String[] { "Frozen planks block", "Drops snowballs" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.seal_frame))
			return new String[] { "Silo hatch frame piece", "Instructions:", "Build square from frame blocks", "Possible sizes: 3x3 - 11x11", "Add controller to any side" };
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.seal_controller))
			return new String[] { "Silo hatch controller", "Instructions:", "Build square from frame blocks", "Possible sizes: 3x3 - 11x11", "Add controller to any side" };
		
		return new String[] { "xxx", "xxx" };
	}
	
	public Map<Object, Object[]> getItemInfo() {
		Map<Object, Object[]> map = new HashMap<Object, Object[]>();
		map.put(new ItemStack(ModBlocks.asphalt), getInfoFromItem(new ItemStack(ModBlocks.asphalt)));
		map.put(new ItemStack(ModBlocks.ore_uranium), getInfoFromItem(new ItemStack(ModBlocks.ore_uranium)));
		map.put(new ItemStack(ModBlocks.ore_titanium), getInfoFromItem(new ItemStack(ModBlocks.ore_titanium)));
		map.put(new ItemStack(ModBlocks.ore_sulfur), getInfoFromItem(new ItemStack(ModBlocks.ore_sulfur)));
		map.put(new ItemStack(ModBlocks.ore_niter), getInfoFromItem(new ItemStack(ModBlocks.ore_niter)));
		map.put(new ItemStack(ModBlocks.ore_copper), getInfoFromItem(new ItemStack(ModBlocks.ore_copper)));
		map.put(new ItemStack(ModBlocks.ore_tungsten), getInfoFromItem(new ItemStack(ModBlocks.ore_tungsten)));
		map.put(new ItemStack(ModBlocks.ore_aluminium), getInfoFromItem(new ItemStack(ModBlocks.ore_aluminium)));
		map.put(new ItemStack(ModBlocks.ore_fluorite), getInfoFromItem(new ItemStack(ModBlocks.ore_fluorite)));
		map.put(new ItemStack(ModBlocks.ore_beryllium), getInfoFromItem(new ItemStack(ModBlocks.ore_beryllium)));
		map.put(new ItemStack(ModBlocks.ore_lead), getInfoFromItem(new ItemStack(ModBlocks.ore_lead)));
		map.put(new ItemStack(ModBlocks.ore_oil), getInfoFromItem(new ItemStack(ModBlocks.ore_oil)));
		map.put(new ItemStack(ModBlocks.ore_schrabidium), getInfoFromItem(new ItemStack(ModBlocks.ore_schrabidium)));
		map.put(new ItemStack(ModBlocks.ore_australium), getInfoFromItem(new ItemStack(ModBlocks.ore_australium)));
		map.put(new ItemStack(ModBlocks.ore_weidanium), getInfoFromItem(new ItemStack(ModBlocks.ore_weidanium)));
		map.put(new ItemStack(ModBlocks.ore_reiium), getInfoFromItem(new ItemStack(ModBlocks.ore_reiium)));
		map.put(new ItemStack(ModBlocks.ore_unobtainium), getInfoFromItem(new ItemStack(ModBlocks.ore_unobtainium)));
		map.put(new ItemStack(ModBlocks.ore_daffergon), getInfoFromItem(new ItemStack(ModBlocks.ore_daffergon)));
		map.put(new ItemStack(ModBlocks.ore_verticium), getInfoFromItem(new ItemStack(ModBlocks.ore_verticium)));
		map.put(new ItemStack(ModBlocks.ore_nether_uranium), getInfoFromItem(new ItemStack(ModBlocks.ore_nether_uranium)));
		map.put(new ItemStack(ModBlocks.ore_nether_plutonium), getInfoFromItem(new ItemStack(ModBlocks.ore_nether_plutonium)));
		map.put(new ItemStack(ModBlocks.ore_nether_tungsten), getInfoFromItem(new ItemStack(ModBlocks.ore_nether_tungsten)));
		map.put(new ItemStack(ModBlocks.ore_nether_sulfur), getInfoFromItem(new ItemStack(ModBlocks.ore_nether_sulfur)));
		map.put(new ItemStack(ModBlocks.ore_nether_fire), getInfoFromItem(new ItemStack(ModBlocks.ore_nether_fire)));
		map.put(new ItemStack(ModBlocks.ore_nether_schrabidium), getInfoFromItem(new ItemStack(ModBlocks.ore_nether_schrabidium)));
		map.put(new ItemStack(ModBlocks.reinforced_brick), getInfoFromItem(new ItemStack(ModBlocks.reinforced_brick)));
		map.put(new ItemStack(ModBlocks.reinforced_glass), getInfoFromItem(new ItemStack(ModBlocks.reinforced_glass)));
		map.put(new ItemStack(ModBlocks.reinforced_light), getInfoFromItem(new ItemStack(ModBlocks.reinforced_light)));
		map.put(new ItemStack(ModBlocks.reinforced_lamp_off), getInfoFromItem(new ItemStack(ModBlocks.reinforced_lamp_off)));
		map.put(new ItemStack(ModBlocks.reinforced_sand), getInfoFromItem(new ItemStack(ModBlocks.reinforced_sand)));
		map.put(new ItemStack(ModBlocks.brick_concrete), getInfoFromItem(new ItemStack(ModBlocks.brick_concrete)));
		map.put(new ItemStack(ModBlocks.brick_obsidian), getInfoFromItem(new ItemStack(ModBlocks.brick_obsidian)));
		map.put(new ItemStack(ModBlocks.brick_light), getInfoFromItem(new ItemStack(ModBlocks.brick_light)));
		map.put(new ItemStack(ModBlocks.cmb_brick), getInfoFromItem(new ItemStack(ModBlocks.cmb_brick)));
		map.put(new ItemStack(ModBlocks.cmb_brick_reinforced), getInfoFromItem(new ItemStack(ModBlocks.cmb_brick_reinforced)));
		map.put(new ItemStack(ModBlocks.block_meteor), getInfoFromItem(new ItemStack(ModBlocks.block_meteor)));
		map.put(new ItemStack(ModBlocks.tape_recorder), getInfoFromItem(new ItemStack(ModBlocks.tape_recorder)));
		map.put(new ItemStack(ModBlocks.steel_poles), getInfoFromItem(new ItemStack(ModBlocks.steel_poles)));
		map.put(new ItemStack(ModBlocks.pole_top), getInfoFromItem(new ItemStack(ModBlocks.pole_top)));
		map.put(new ItemStack(ModBlocks.pole_satellite_receiver), getInfoFromItem(new ItemStack(ModBlocks.pole_satellite_receiver)));
		map.put(new ItemStack(ModBlocks.steel_wall), getInfoFromItem(new ItemStack(ModBlocks.steel_wall)));
		map.put(new ItemStack(ModBlocks.steel_corner), getInfoFromItem(new ItemStack(ModBlocks.steel_corner)));
		map.put(new ItemStack(ModBlocks.steel_roof), getInfoFromItem(new ItemStack(ModBlocks.steel_roof)));
		map.put(new ItemStack(ModBlocks.steel_beam), getInfoFromItem(new ItemStack(ModBlocks.steel_beam)));
		map.put(new ItemStack(ModBlocks.mush), getInfoFromItem(new ItemStack(ModBlocks.mush)));
		map.put(new ItemStack(ModBlocks.waste_earth), getInfoFromItem(new ItemStack(ModBlocks.waste_earth)));
		map.put(new ItemStack(ModBlocks.waste_mycelium), getInfoFromItem(new ItemStack(ModBlocks.waste_mycelium)));
		map.put(new ItemStack(ModBlocks.waste_trinitite), getInfoFromItem(new ItemStack(ModBlocks.waste_trinitite)));
		map.put(new ItemStack(ModBlocks.waste_trinitite_red), getInfoFromItem(new ItemStack(ModBlocks.waste_trinitite_red)));
		map.put(new ItemStack(ModBlocks.waste_log), getInfoFromItem(new ItemStack(ModBlocks.waste_log)));
		map.put(new ItemStack(ModBlocks.waste_planks), getInfoFromItem(new ItemStack(ModBlocks.waste_planks)));
		map.put(new ItemStack(ModBlocks.frozen_grass), getInfoFromItem(new ItemStack(ModBlocks.frozen_grass)));
		map.put(new ItemStack(ModBlocks.frozen_dirt), getInfoFromItem(new ItemStack(ModBlocks.frozen_dirt)));
		map.put(new ItemStack(ModBlocks.frozen_log), getInfoFromItem(new ItemStack(ModBlocks.frozen_log)));
		map.put(new ItemStack(ModBlocks.frozen_planks), getInfoFromItem(new ItemStack(ModBlocks.frozen_planks)));
		map.put(new ItemStack(ModBlocks.seal_frame), getInfoFromItem(new ItemStack(ModBlocks.seal_frame)));
		map.put(new ItemStack(ModBlocks.seal_controller), getInfoFromItem(new ItemStack(ModBlocks.seal_controller)));
		
		return map;
	}
	
	public Map<Object, Object> getFluidContainers() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		for(FluidContainer con : FluidContainerRegistry.instance.allContainers) {
			if(con != null) {
				ItemStack fluid = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(con.type));
				fluid.stackTagCompound = new NBTTagCompound();
				fluid.stackTagCompound.setInteger("fill", con.content);
				map.put(fluid, con.fullContainer);
			}
		}
		
		return map;
	}
}
