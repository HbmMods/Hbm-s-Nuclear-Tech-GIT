package com.hbm.items;

import com.hbm.blocks.ModBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStarterKit extends Item {

    public ItemStarterKit()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(1);
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		stack.damageItem(5, player);
		
		if(this == ModItems.nuke_starter_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 1));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_centrifuge), 1));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_reactor), 1));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), 1));

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

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 4));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_empty, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_uf6, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_empty, 16));
		}
		
		if(this == ModItems.nuke_advanced_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_centrifuge), 4));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_reactor), 3));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_deuterium), 1));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_uranium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_u235, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_u238, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_plutonium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_pu238, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_pu239, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_pu240, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_neptunium, 16));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_copper, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_red_copper, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_steel, 64));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_tungsten, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_lead, 32));

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.wire_red_copper, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.wire_tungsten, 32));
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

			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 16));
		}
		
		if(this == ModItems.nuke_commercially_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_generator), 4));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_rtg_furnace_off), 2));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 4));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 32));
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
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_coolant, 1));
		}

		if(this == ModItems.nuke_electric_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_coal_off), 4));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 64));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 64));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_battery), 6));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_generic, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_generic, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_generic, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_generic, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_generic, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_generic, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_advanced, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_advanced, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_advanced, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_advanced, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_schrabidium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_aluminium, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_titanium, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_steel, 32));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_aluminium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_copper, 8));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_red_copper, 4));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.circuit_gold, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.motor, 4));
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
		}
		
		if(this == ModItems.boy_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_boy), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_shielding, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_target, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_bullet, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_igniter, 1));
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
		
		if(this == ModItems.grenade_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_generic, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_strong, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_frag, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_fire, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cluster, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_flare, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_electric, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_poison, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gas, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_schrabidium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_nuke, 16));
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
		}
		
		return stack;
		
	}
	
    @Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int i) {
    	
    }

}
