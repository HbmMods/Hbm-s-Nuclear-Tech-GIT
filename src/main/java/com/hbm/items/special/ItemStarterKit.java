package com.hbm.items.special;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemStarterKit extends Item {

    public ItemStarterKit()
    {
        this.maxStackSize = 1;
    }
    
    private void giveHaz(World world, EntityPlayer p, int tier) {
    	
    	for(int i = 0; i < 4; i++) {
    		
    		if(p.inventory.armorInventory[i] != null && !world.isRemote) {
    			world.spawnEntityInWorld(new EntityItem(world, p.posX, p.posY + p.eyeHeight, p.posZ, p.inventory.armorInventory[i]));
    		}
    	}

    	switch(tier) {
    	case 0:
	    	p.inventory.armorInventory[3] = new ItemStack(ModItems.hazmat_helmet);
	    	p.inventory.armorInventory[2] = new ItemStack(ModItems.hazmat_plate);
	    	p.inventory.armorInventory[1] = new ItemStack(ModItems.hazmat_legs);
	    	p.inventory.armorInventory[0] = new ItemStack(ModItems.hazmat_boots);
	    	break;
    	case 1:
	    	p.inventory.armorInventory[3] = new ItemStack(ModItems.hazmat_helmet_red);
	    	p.inventory.armorInventory[2] = new ItemStack(ModItems.hazmat_plate_red);
	    	p.inventory.armorInventory[1] = new ItemStack(ModItems.hazmat_legs_red);
	    	p.inventory.armorInventory[0] = new ItemStack(ModItems.hazmat_boots_red);
	    	break;
    	case 2:
	    	p.inventory.armorInventory[3] = new ItemStack(ModItems.hazmat_helmet_grey);
	    	p.inventory.armorInventory[2] = new ItemStack(ModItems.hazmat_plate_grey);
	    	p.inventory.armorInventory[1] = new ItemStack(ModItems.hazmat_legs_grey);
	    	p.inventory.armorInventory[0] = new ItemStack(ModItems.hazmat_boots_grey);
	    	break;
    	}
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		//stack.damageItem(5, player);
		
		if(this == ModItems.nuke_starter_kit)
		{
			/*player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_difurnace_off, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_centrifuge, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_reactor, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_uf6_tank, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_assembler, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_press, 1));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.template_folder, 1));
			
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_uranium, 6));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_red_copper, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_tungsten, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_steel, 12));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_lead, 8));
		
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_titanium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_aluminium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_iron, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_steel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_lead, 16));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.wire_red_copper, 28));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.wire_tungsten, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.coil_copper, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.tank_steel, 4));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_empty, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_uf6, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_empty, 16));*/

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_uranium, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.powder_yellowcake, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.template_folder, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_press, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_difurnace_off, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_gascent, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_puf6_tank, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_reactor, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_nuke_furnace_off, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_assembler, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_chemplant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_reactor_small, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_turbine, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.radaway, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.radx, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.stamp_titanium_flat, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.stamp_titanium_flat, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.stamp_titanium_flat, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_steel, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_lead, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_copper, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gas_mask_m65, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.geiger_counter, 1));
			
			giveHaz(world, player, 1);
		}
		
		if(this == ModItems.nuke_advanced_kit)
		{
			/*player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_centrifuge), 4));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_reactor), 3));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_assembler, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_chemplant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_press, 1));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_uranium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_u235, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_u238, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_plutonium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_pu238, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_pu239, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_pu240, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_neptunium, 16));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.wire_red_copper, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.coil_copper, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.coil_tungsten, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.coil_copper_torus, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.tank_steel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.motor, 8));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_aluminium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_titanium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_iron, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_steel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.neutron_reflector, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_lead, 16));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_empty, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_empty, 64));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));*/

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.powder_yellowcake, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.powder_plutonium, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_steel, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_copper, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_tungsten, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_lead, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_polymer, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_difurnace_off, 3));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_gascent, 3));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_centrifuge, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_uf6_tank, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_puf6_tank, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_reactor, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_rtg_furnace_off, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_reactor_small, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_turbine, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_radgen, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_rtg_grey, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_assembler, 3));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_chemplant, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_fluidtank, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg_weak, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg_weak, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg_weak, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_empty, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_empty, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fluid_barrel_full, 4, FluidType.COOLANT.getID()));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.radaway_strong, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.radx, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pill_iodine, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.tritium_deuterium_cake, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.geiger_counter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.survey_scanner, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gas_mask_m65, 1));
			
			giveHaz(world, player, 2);
		}
		
		if(this == ModItems.nuke_commercially_kit)
		{
			/*player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_generator), 4));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_rtg_furnace_off), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 4));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.red_cable), 64));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_pu238, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_pu238, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_mox_fuel, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_mox_fuel, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_mox_fuel, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_mox_fuel, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_dual_mox_fuel, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_dual_mox_fuel, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_dual_mox_fuel, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_dual_mox_fuel, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_water, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_water, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_water, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_water, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_coolant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_coolant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_coolant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_coolant, 1));*/

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_pu238, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_uranium_fuel, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_plutonium_fuel, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_mox_fuel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rtg_unit, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.motor, 3));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.reactor_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_empty, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_empty, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fluid_barrel_full, 16, FluidType.WATER.getID()));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fluid_barrel_full, 8, FluidType.COOLANT.getID()));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_assembler, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_chemplant, 3));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_gascent, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_nuke_furnace_off, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_rtg_furnace_off, 3));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_rtg_grey, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_reactor_small, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_turbine, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_lithium_battery, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.red_cable, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.red_wire_coated, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg_weak, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg_weak, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg_weak, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.radaway_strong, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.radaway_flush, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.radx, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pill_iodine, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.geiger_counter, 1));
			
			giveHaz(world, player, 2);
		}

		if(this == ModItems.nuke_electric_kit)
		{
			/*player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_coal_off), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_diesel), 1));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.red_cable), 64));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.red_cable), 64));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 64));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_battery), 6));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_chemplant), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_red_cell_24, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_advanced_cell_4, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_advanced_cell_4, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_lithium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fusion_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.energy_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_aluminium, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_titanium, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_steel, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_aluminium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_copper, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_red_copper, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_gold, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.motor, 4));*/

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.coil_copper, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.coil_gold, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.coil_tungsten, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.motor, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_aluminium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_copper, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_red_copper, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.wiring_red_copper, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.magnetron, 5));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.piston_selenium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.piston_selenium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.piston_selenium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.canister_fuel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.canister_biofuel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_advanced_cell_4, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_advanced_cell_4, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_lithium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_lithium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_potato, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.limiter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.screwdriver, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_coal_off, 3));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_diesel, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_selenium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.red_cable, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.red_wire_coated, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.red_pylon, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_battery, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_lithium_battery, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_converter_he_rf, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_converter_rf_he, 1));
		}
		
		if(this == ModItems.gadget_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_gadget), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_wireing, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_core, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.boy_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_boy), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_shielding, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_target, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_bullet, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_igniter, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.man_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_man), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_core, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.mike_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_mike), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.mike_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.mike_deut, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.mike_cooling_unit, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.tsar_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_tsar), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.tsar_core, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.multi_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.bomb_multi), 6));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(Blocks.tnt), 26));
			player.inventory.addItemStackToInventory(new ItemStack(Items.gunpowder, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_cluster, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.powder_fire, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.powder_poison, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_gas, 2));
		}
		
		if(this == ModItems.custom_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.nuke_custom));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_nuke, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_nuke, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_nuke, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_nuke, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_hydro, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_hydro, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_amat, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_amat, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_dirty, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_dirty, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_dirty, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_schrab, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_fall, 1));
		}
		
		if(this == ModItems.grenade_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_generic, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_strong, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_frag, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_fire, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_shrapnel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cluster, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_flare, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_electric, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_poison, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gas, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cloud, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_pink_cloud, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_smart, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_mirv, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_breach, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_burst, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_pulse, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_plasma, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_tau, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_schrabidium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_lemon, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gascan, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_mk2, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_aschrab, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_nuke, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_nuclear, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_zomg, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_black_hole, 16));
		}
		
		if(this == ModItems.fleija_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_fleija), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			
			giveHaz(world, player, 2);
		}
		
		if(this == ModItems.solinium_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_solinium), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_core, 1));
			
			giveHaz(world, player, 1);
		}
		
		if(this == ModItems.prototype_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_prototype), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_sas3, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_uranium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_uranium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_lead, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_lead, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_neptunium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_neptunium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_lead, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_lead, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_uranium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_uranium, 1));
			
			giveHaz(world, player, 2);
		}
		
		if(this == ModItems.missile_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.launch_pad), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.designator, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.designator_range, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.designator_manual, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_schrabidium_cell_4, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_generic, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_strong, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_burst, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_incendiary, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_incendiary_strong, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_inferno, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_cluster, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_cluster_strong, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_rain, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_buster, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_buster_strong, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_drill, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_nuclear, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_nuclear_cluster, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_volcano, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_endo, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_exo, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_doomsday, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_taint, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_micro, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_bhole, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_schrabidium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_emp, 1));
		}
		
		if(this == ModItems.t45_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.t45_helmet, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.t45_plate, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.t45_legs, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.t45_boots, 1));
			player.inventory.addItemStackToInventory(ItemBattery.getFullBattery(ModItems.fusion_core));
			player.inventory.addItemStackToInventory(ItemBattery.getFullBattery(ModItems.fusion_core));
			player.inventory.addItemStackToInventory(ItemBattery.getFullBattery(ModItems.fusion_core));
			player.inventory.addItemStackToInventory(ItemBattery.getFullBattery(ModItems.fusion_core));
			player.inventory.addItemStackToInventory(ItemBattery.getFullBattery(ModItems.fusion_core));
			player.inventory.addItemStackToInventory(ItemBattery.getFullBattery(ModItems.fusion_core));
			player.inventory.addItemStackToInventory(ItemBattery.getFullBattery(ModItems.fusion_core));
		}
		
		if(this == ModItems.stealth_boy)
		{
			player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 30 * 20, 1, true));
		}
		
		if(this == ModItems.euphemium_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.euphemium_helmet, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.euphemium_plate, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.euphemium_legs, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.euphemium_boots, 1));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.statue_elb), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_revolver_cursed, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.watch, 1));
		}
		
		if(this == ModItems.hazmat_kit)
		{
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.hazmat_red_kit)
		{
			giveHaz(world, player, 1);
		}
		
		if(this == ModItems.hazmat_grey_kit)
		{
			giveHaz(world, player, 2);
		}
		
		if(this == ModItems.letter && world.isRemote)
		{
			if(player.getUniqueID().toString().equals(Library.a20)) {
				player.addChatMessage(new ChatComponentText("Error: null reference @ com.hbm.items.ItemStarterKit.class, please report this to the modder!"));
			} else {
				player.addChatMessage(new ChatComponentText("You rip the letter in half; nothing happens."));
			}
		}

		world.playSoundAtEntity(player, "hbm:item.unpack", 1.0F, 1.0F);
		stack.stackSize--;
		return stack;
		
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {

    	if(this == ModItems.nuke_starter_kit ||
    			this == ModItems.nuke_advanced_kit ||
    			this == ModItems.nuke_commercially_kit ||
    			this == ModItems.nuke_electric_kit ||
    			this == ModItems.gadget_kit ||
    			this == ModItems.boy_kit ||
    			this == ModItems.man_kit ||
    			this == ModItems.mike_kit ||
    			this == ModItems.tsar_kit ||
    			this == ModItems.prototype_kit ||
    			this == ModItems.fleija_kit ||
    			this == ModItems.solinium_kit ||
    			this == ModItems.t45_kit ||
    			this == ModItems.grenade_kit ||
    			this == ModItems.missile_kit ||
    			this == ModItems.multi_kit) {
    		list.add("Please empty inventory before opening!");
    	}
    	if(this == ModItems.nuke_starter_kit ||
    			this == ModItems.nuke_advanced_kit ||
    			this == ModItems.nuke_commercially_kit ||
    			this == ModItems.gadget_kit ||
    			this == ModItems.boy_kit ||
    			this == ModItems.man_kit ||
    			this == ModItems.mike_kit ||
    			this == ModItems.tsar_kit ||
    			this == ModItems.prototype_kit ||
    			this == ModItems.fleija_kit ||
    			this == ModItems.solinium_kit ||
    			this == ModItems.hazmat_kit) {
    		list.add("Armor will be displaced by hazmat suit.");
    	}
    }

}
