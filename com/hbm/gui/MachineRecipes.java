package com.hbm.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MachineRecipes {
	
	public MachineRecipes() {
		
	}
	
	public static MachineRecipes instance() {
		return new MachineRecipes();
	}
	
	public static ItemStack getFurnaceProcessingResult(Item item, Item item2) {
		return getFurnaceOutput(item ,item2);
	}
	
	public static ItemStack getFurnaceOutput(Item item, Item item2) {
		if(item == Items.iron_ingot && item2 == Items.quartz || item == Items.quartz && item2 == Items.iron_ingot) {
			return new ItemStack(ModBlocks.test_render, 1); 
		}
		

		if(item == ModItems.ingot_tungsten && item2 == Items.coal || item == Items.coal && item2 == ModItems.ingot_tungsten) {
			return new ItemStack(ModItems.neutron_reflector, 2); 
		}
		

		if(item == ModItems.ingot_lead && item2 == ModItems.ingot_copper || item == ModItems.ingot_copper && item2 == ModItems.ingot_lead) {
			return new ItemStack(ModItems.neutron_reflector, 2); 
		}
		

		if(item == ModItems.plate_lead && item2 == ModItems.plate_copper || item == ModItems.plate_copper && item2 == ModItems.plate_lead) {
			return new ItemStack(ModItems.neutron_reflector, 1); 
		}
		

		if(item == Items.iron_ingot && item2 == Items.coal || item == Items.coal && item2 == Items.iron_ingot) {
			return new ItemStack(ModItems.ingot_steel, 2); 
		}
		

		if(item == ModItems.ingot_copper && item2 == Items.redstone || item == Items.redstone && item2 == ModItems.ingot_copper) {
			return new ItemStack(ModItems.ingot_red_copper, 2); 
		}
		

		if(item == ModItems.canister_empty && item2 == Items.coal || item == Items.coal && item2 == ModItems.canister_empty) {
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
		
		ItemStack[] uranium = new ItemStack[] {new ItemStack(ModItems.nugget_u238, 4), new ItemStack(ModItems.nugget_u238, 4), new ItemStack(ModItems.nugget_u235, 1), new ItemStack(ModItems.cell_empty, 1)};
		ItemStack[] plutonium = new ItemStack[] {new ItemStack(ModItems.nugget_pu238, 3), new ItemStack(ModItems.nugget_pu239, 1), new ItemStack(ModItems.nugget_pu240, 5), new ItemStack(ModItems.cell_empty, 1)};
		ItemStack[] test = new ItemStack[] {new ItemStack(Items.apple, 3), new ItemStack(Items.leather, 1), new ItemStack(Items.sugar, 3), new ItemStack(Items.blaze_powder, 2)};
		ItemStack[] euphemium = new ItemStack[] {new ItemStack(ModItems.nugget_euphemium, 1), new ItemStack(ModItems.nugget_euphemium, 1), new ItemStack(ModItems.nugget_euphemium, 1), new ItemStack(ModItems.rod_quad_empty, 1)};
		ItemStack[] schrabidium = new ItemStack[] {new ItemStack(ModItems.ingot_schrabidium, 1), new ItemStack(ModItems.sulfur, 1), new ItemStack(ModItems.sulfur, 1), new ItemStack(ModItems.cell_empty, 1)};
		ItemStack[] lithium = new ItemStack[] {new ItemStack(ModItems.lithium, 1), new ItemStack(ModItems.lithium, 1), new ItemStack(ModItems.lithium, 1), new ItemStack(Item.getItemFromBlock(Blocks.gravel))};
		ItemStack[] lithium2 = new ItemStack[] {new ItemStack(ModItems.lithium, 1), new ItemStack(ModItems.lithium, 1), new ItemStack(ModItems.lithium, 1), new ItemStack(ModItems.lithium, 1)};
		ItemStack[] lithium3 = new ItemStack[] {new ItemStack(ModItems.lithium, 4), new ItemStack(ModItems.lithium, 4), new ItemStack(ModItems.lithium, 4), new ItemStack(ModItems.lithium, 4)};

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
		
		if(item == item.getItemFromBlock(Blocks.cobblestone) || item == item.getItemFromBlock(Blocks.stone))
		{
			return lithium;
		}
		
		if(item == item.getItemFromBlock(Blocks.quartz_block) || item == item.getItemFromBlock(Blocks.quartz_stairs))
		{
			return lithium3;
		}
		
		if(item == Items.quartz)
		{
			return lithium2;
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
		
		if(item == ModItems.rod_lithium)
		{
			return new ItemStack(ModItems.rod_tritium, 1);
		}
		
		if(item == ModItems.rod_dual_lithium)
		{
			return new ItemStack(ModItems.rod_dual_tritium, 1);
		}
		
		if(item == ModItems.rod_quad_lithium)
		{
			return new ItemStack(ModItems.rod_quad_tritium, 1);
		}
		
		return null;
	}
	
	public Map<Object[], Object> getAlloyRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		recipes.put(new ItemStack[] {new ItemStack(Items.iron_ingot), new ItemStack(Items.quartz)}, new ItemStack(Item.getItemFromBlock(ModBlocks.test_render)));
		recipes.put(new ItemStack[] {new ItemStack(Items.iron_ingot), new ItemStack(Items.coal)}, new ItemStack(ModItems.ingot_steel));
		recipes.put(new ItemStack[] {new ItemStack(ModItems.ingot_lead), new ItemStack(ModItems.ingot_copper)}, new ItemStack(ModItems.neutron_reflector, 2));
		recipes.put(new ItemStack[] {new ItemStack(ModItems.plate_lead), new ItemStack(ModItems.plate_copper)}, new ItemStack(ModItems.neutron_reflector));
		recipes.put(new ItemStack[] {new ItemStack(ModItems.ingot_tungsten), new ItemStack(Items.coal)}, new ItemStack(ModItems.neutron_reflector, 2));
		recipes.put(new ItemStack[] {new ItemStack(ModItems.ingot_copper), new ItemStack(Items.redstone)}, new ItemStack(ModItems.ingot_red_copper, 2));
		recipes.put(new ItemStack[] {new ItemStack(ModItems.ingot_red_copper), new ItemStack(ModItems.ingot_steel)}, new ItemStack(ModItems.ingot_advanced_alloy, 2));
		recipes.put(new ItemStack[] {new ItemStack(ModItems.canister_empty), new ItemStack(Items.coal)}, new ItemStack(ModItems.canister_fuel, 1));
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
		recipes.put(new ItemStack(Item.getItemFromBlock(ModBlocks.test_render)), getCentrifugeOutput(Item.getItemFromBlock(ModBlocks.test_render)));
		recipes.put(new ItemStack(ModItems.rod_quad_euphemium), getCentrifugeOutput(ModItems.rod_quad_euphemium));
		recipes.put(new ItemStack(ModItems.cell_sas3), getCentrifugeOutput(ModItems.cell_sas3));
		recipes.put(new ItemStack(Item.getItemFromBlock(Blocks.stone)), getCentrifugeOutput(Item.getItemFromBlock(Blocks.stone)));
		recipes.put(new ItemStack(Item.getItemFromBlock(Blocks.cobblestone)), getCentrifugeOutput(Item.getItemFromBlock(Blocks.cobblestone)));
		recipes.put(new ItemStack(ModItems.rod_uranium_fuel_depleted), getCentrifugeOutput(ModItems.rod_uranium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_dual_uranium_fuel_depleted), getCentrifugeOutput(ModItems.rod_dual_uranium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_quad_uranium_fuel_depleted), getCentrifugeOutput(ModItems.rod_quad_uranium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_plutonium_fuel_depleted), getCentrifugeOutput(ModItems.rod_plutonium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_dual_plutonium_fuel_depleted), getCentrifugeOutput(ModItems.rod_dual_plutonium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_quad_plutonium_fuel_depleted), getCentrifugeOutput(ModItems.rod_quad_plutonium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_mox_fuel_depleted), getCentrifugeOutput(ModItems.rod_mox_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_dual_mox_fuel_depleted), getCentrifugeOutput(ModItems.rod_dual_mox_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_quad_mox_fuel_depleted), getCentrifugeOutput(ModItems.rod_quad_mox_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_schrabidium_fuel_depleted), getCentrifugeOutput(ModItems.rod_schrabidium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_dual_schrabidium_fuel_depleted), getCentrifugeOutput(ModItems.rod_dual_schrabidium_fuel_depleted));
		recipes.put(new ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted), getCentrifugeOutput(ModItems.rod_quad_schrabidium_fuel_depleted));
		recipes.put(new ItemStack(Item.getItemFromBlock(Blocks.quartz_block)), getCentrifugeOutput(Item.getItemFromBlock(Blocks.quartz_block)));
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
		recipes.put(new ItemStack(ModItems.rod_quad_schrabidium), getReactorOutput(ModItems.rod_quad_schrabidium));
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
}
