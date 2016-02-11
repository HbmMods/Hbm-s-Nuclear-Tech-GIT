package com.hbm.gui;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MachineRecipes {

	public MachineRecipes() {
		
	}
	
	public static ItemStack getFurnaceProcessingResult(Item item, Item item2) {
		return getFurnaceOutput(item ,item2);
	}
	
	public static ItemStack getFurnaceOutput(Item item, Item item2) {
		if(item == Items.iron_ingot && item2 == Items.quartz || item == Items.quartz && item2 == Items.iron_ingot) {
			return new ItemStack(ModBlocks.test_render, 1); 
		}
		

		if(item == ModItems.ingot_tungsten && item2 == Items.coal || item == Items.coal && item2 == ModItems.ingot_tungsten) {
			return new ItemStack(ModItems.neutron_reflector, 1); 
		}
		

		if(item == ModItems.ingot_lead && item2 == ModItems.ingot_copper || item == ModItems.ingot_copper && item2 == ModItems.ingot_lead) {
			return new ItemStack(ModItems.neutron_reflector, 2); 
		}
		

		if(item == ModItems.plate_lead && item2 == ModItems.ingot_copper || item == ModItems.ingot_copper && item2 == ModItems.plate_lead) {
			return new ItemStack(ModItems.neutron_reflector, 1); 
		}
		

		if(item == Items.iron_ingot && item2 == Items.coal || item == Items.coal && item2 == Items.iron_ingot) {
			return new ItemStack(ModItems.ingot_steel, 2); 
		}
		

		if(item == ModItems.ingot_copper && item2 == Items.redstone || item == Items.redstone && item2 == ModItems.ingot_copper) {
			return new ItemStack(ModItems.ingot_red_copper, 1); 
		}
		

		if(item == ModItems.canister_empty && item2 == Item.getItemFromBlock(Blocks.coal_block) || item == Item.getItemFromBlock(Blocks.coal_block) && item2 == ModItems.canister_empty) {
			return new ItemStack(ModItems.canister_fuel, 1); 
		}
		

		if(item == ModItems.ingot_red_copper && item2 == ModItems.ingot_steel || item == ModItems.ingot_steel && item2 == ModItems.ingot_red_copper) {
			return new ItemStack(ModItems.ingot_advanced_alloy, 2); 
		}
		
		return null;
	}
	
	//Arrays!
	
	public static ItemStack[] getCentrifugeProcessingResult(Item item) {
		return getCentrifugeOutput(item);
	}
	
	public static ItemStack[] getCentrifugeOutput(Item item) {
		
		ItemStack[] uranium = new ItemStack[] {new ItemStack(ModItems.nugget_u238, 8), new ItemStack(ModItems.nugget_u235, 1), null, new ItemStack(ModItems.cell_empty, 1)};
		ItemStack[] plutonium = new ItemStack[] {new ItemStack(ModItems.nugget_pu238, 3), new ItemStack(ModItems.nugget_pu239, 1), new ItemStack(ModItems.nugget_pu240, 5), new ItemStack(ModItems.cell_empty, 1)};
		ItemStack[] test = new ItemStack[] {new ItemStack(Items.apple, 3), new ItemStack(Items.leather, 1), new ItemStack(Items.sugar, 3), new ItemStack(Items.blaze_powder, 2)};
		ItemStack[] euphemium = new ItemStack[] {new ItemStack(ModItems.nugget_euphemium, 3), null, null, new ItemStack(ModItems.rod_quad_empty, 1)};
		ItemStack[] schrabidium = new ItemStack[] {new ItemStack(ModItems.ingot_schrabidium, 1), new ItemStack(ModItems.sulfur, 2), null, new ItemStack(ModItems.cell_empty, 1)};

		ItemStack[] uran1 = new ItemStack[] {new ItemStack(ModItems.nugget_u235, 1), new ItemStack(ModItems.nugget_u238, 3), new ItemStack(ModItems.nugget_pu239, 2), new ItemStack(ModItems.rod_waste, 1)};
		ItemStack[] uran2 = new ItemStack[] {new ItemStack(ModItems.nugget_u235, 2), new ItemStack(ModItems.nugget_u238, 6), new ItemStack(ModItems.nugget_pu239, 4), new ItemStack(ModItems.rod_dual_waste, 1)};
		ItemStack[] uran3 = new ItemStack[] {new ItemStack(ModItems.nugget_u235, 4), new ItemStack(ModItems.nugget_u238, 12), new ItemStack(ModItems.nugget_pu239, 8), new ItemStack(ModItems.rod_quad_waste, 1)};
		ItemStack[] plutonium1 = new ItemStack[] {new ItemStack(ModItems.nugget_pu239, 1), new ItemStack(ModItems.nugget_pu240, 3), new ItemStack(ModItems.nugget_lead, 2), new ItemStack(ModItems.rod_waste, 1)};
		ItemStack[] plutonium2 = new ItemStack[] {new ItemStack(ModItems.nugget_pu239, 2), new ItemStack(ModItems.nugget_pu240, 6), new ItemStack(ModItems.nugget_lead, 4), new ItemStack(ModItems.rod_dual_waste, 1)};
		ItemStack[] plutonium3 = new ItemStack[] {new ItemStack(ModItems.nugget_pu239, 4), new ItemStack(ModItems.nugget_pu240, 12), new ItemStack(ModItems.nugget_lead, 8), new ItemStack(ModItems.rod_quad_waste, 1)};
		ItemStack[] mox1 = new ItemStack[] {new ItemStack(ModItems.nugget_mox_fuel, 1), new ItemStack(ModItems.nugget_neptunium, 3), new ItemStack(ModItems.nugget_u238, 2), new ItemStack(ModItems.rod_waste, 1)};
		ItemStack[] mox2 = new ItemStack[] {new ItemStack(ModItems.nugget_mox_fuel, 2), new ItemStack(ModItems.nugget_neptunium, 6), new ItemStack(ModItems.nugget_u238, 4), new ItemStack(ModItems.rod_dual_waste, 1)};
		ItemStack[] mox3 = new ItemStack[] {new ItemStack(ModItems.nugget_mox_fuel, 4), new ItemStack(ModItems.nugget_neptunium, 12), new ItemStack(ModItems.nugget_u238, 8), new ItemStack(ModItems.rod_quad_waste, 1)};
		ItemStack[] schrabidium1 = new ItemStack[] {new ItemStack(ModItems.nugget_schrabidium_fuel, 1), new ItemStack(ModItems.nugget_lead, 3), new ItemStack(ModItems.nugget_schrabidium, 2), new ItemStack(ModItems.rod_waste, 1)};
		ItemStack[] schrabidium2 = new ItemStack[] {new ItemStack(ModItems.nugget_schrabidium_fuel, 2), new ItemStack(ModItems.nugget_lead, 6), new ItemStack(ModItems.nugget_schrabidium, 4), new ItemStack(ModItems.rod_dual_waste, 1)};
		ItemStack[] schrabidium3 = new ItemStack[] {new ItemStack(ModItems.nugget_schrabidium_fuel, 4), new ItemStack(ModItems.nugget_lead, 19), new ItemStack(ModItems.nugget_euphemium, 1), new ItemStack(ModItems.rod_quad_waste, 1)};
		
		if(item == ModItems.cell_uf6)
		{
			return uranium;
		}
		
		if(item == ModItems.cell_puf6)
		{
			return plutonium;
		}
		
		if(item == Item.getItemFromBlock(ModBlocks.test_render))
		{
			return test;
		}
		
		if(item == ModItems.rod_quad_euphemium)
		{
			return euphemium;
		}
		
		if(item == ModItems.cell_sas3)
		{
			return schrabidium;
		}
		
		if(item == ModItems.rod_uranium_fuel_depleted)
		{
			return uran1;
		}
		
		if(item == ModItems.rod_dual_uranium_fuel_depleted)
		{
			return uran2;
		}
		
		if(item == ModItems.rod_quad_uranium_fuel_depleted)
		{
			return uran3;
		}
		
		if(item == ModItems.rod_plutonium_fuel_depleted)
		{
			return plutonium1;
		}
		
		if(item == ModItems.rod_dual_plutonium_fuel_depleted)
		{
			return plutonium2;
		}
		
		if(item == ModItems.rod_quad_plutonium_fuel_depleted)
		{
			return plutonium3;
		}
		
		if(item == ModItems.rod_mox_fuel_depleted)
		{
			return mox1;
		}
		
		if(item == ModItems.rod_dual_mox_fuel_depleted)
		{
			return mox2;
		}
		
		if(item == ModItems.rod_quad_mox_fuel_depleted)
		{
			return mox3;
		}
		
		if(item == ModItems.rod_schrabidium_fuel_depleted)
		{
			return schrabidium1;
		}
		
		if(item == ModItems.rod_dual_schrabidium_fuel_depleted)
		{
			return schrabidium2;
		}
		
		if(item == ModItems.rod_quad_schrabidium_fuel_depleted)
		{
			return schrabidium3;
		}
		
		return null;
	}
	
	public static ItemStack getReactorProcessingResult(Item item) {
		return getReactorOutput(item);
	}
	
	public static ItemStack getReactorOutput(Item item) {
		
		if(item == ModItems.rod_uranium)
		{
			return new ItemStack(ModItems.rod_plutonium, 1);
		}
		
		if(item == ModItems.rod_u235)
		{
			return new ItemStack(ModItems.rod_neptunium, 1);
		}
		
		if(item == ModItems.rod_u238)
		{
			return new ItemStack(ModItems.rod_pu239, 1);
		}
		
		if(item == ModItems.rod_neptunium)
		{
			return new ItemStack(ModItems.rod_pu238, 1);
		}
		
		if(item == ModItems.rod_plutonium)
		{
			return new ItemStack(ModItems.rod_lead, 1);
		}
		
		if(item == ModItems.rod_pu238)
		{
			return new ItemStack(ModItems.rod_pu239, 1);
		}
		
		if(item == ModItems.rod_pu239)
		{
			return new ItemStack(ModItems.rod_pu240, 1);
		}
		
		if(item == ModItems.rod_pu240)
		{
			return new ItemStack(ModItems.rod_lead, 1);
		}
		
		if(item == ModItems.rod_dual_uranium)
		{
			return new ItemStack(ModItems.rod_dual_plutonium, 1);
		}
		
		if(item == ModItems.rod_dual_u235)
		{
			return new ItemStack(ModItems.rod_dual_neptunium, 1);
		}
		
		if(item == ModItems.rod_dual_u238)
		{
			return new ItemStack(ModItems.rod_dual_pu239, 1);
		}
		
		if(item == ModItems.rod_dual_neptunium)
		{
			return new ItemStack(ModItems.rod_dual_pu238, 1);
		}
		
		if(item == ModItems.rod_dual_plutonium)
		{
			return new ItemStack(ModItems.rod_dual_lead, 1);
		}
		
		if(item == ModItems.rod_dual_pu238)
		{
			return new ItemStack(ModItems.rod_dual_pu239, 1);
		}
		
		if(item == ModItems.rod_dual_pu239)
		{
			return new ItemStack(ModItems.rod_dual_pu240, 1);
		}
		
		if(item == ModItems.rod_dual_pu240)
		{
			return new ItemStack(ModItems.rod_dual_lead, 1);
		}
		
		if(item == ModItems.rod_quad_uranium)
		{
			return new ItemStack(ModItems.rod_quad_plutonium, 1);
		}
		
		if(item == ModItems.rod_quad_u235)
		{
			return new ItemStack(ModItems.rod_quad_neptunium, 1);
		}
		
		if(item == ModItems.rod_quad_u238)
		{
			return new ItemStack(ModItems.rod_quad_pu239, 1);
		}
		
		if(item == ModItems.rod_quad_neptunium)
		{
			return new ItemStack(ModItems.rod_quad_pu238, 1);
		}
		
		if(item == ModItems.rod_quad_plutonium)
		{
			return new ItemStack(ModItems.rod_quad_lead, 1);
		}
		
		if(item == ModItems.rod_quad_pu238)
		{
			return new ItemStack(ModItems.rod_quad_pu239, 1);
		}
		
		if(item == ModItems.rod_quad_pu239)
		{
			return new ItemStack(ModItems.rod_quad_pu240, 1);
		}
		
		if(item == ModItems.rod_quad_pu240)
		{
			return new ItemStack(ModItems.rod_quad_lead, 1);
		}
		
		if(item == ModItems.rod_quad_schrabidium)
		{
			return new ItemStack(ModItems.rod_quad_euphemium, 1);
		}
		
		return null;
	}
}
