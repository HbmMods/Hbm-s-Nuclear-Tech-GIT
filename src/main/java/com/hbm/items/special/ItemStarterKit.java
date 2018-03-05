package com.hbm.items.special;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

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
        this.setMaxDamage(1);
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		stack.damageItem(5, player);
		
		if(this == ModItems.nuke_starter_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.machine_difurnace_off, 1));
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
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_empty, 16));
		}
		
		if(this == ModItems.nuke_advanced_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 2));
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
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_rtg, 1));
		}
		
		if(this == ModItems.nuke_commercially_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_generator), 4));
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
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_coolant, 1));
		}

		if(this == ModItems.nuke_electric_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_coal_off), 2));
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
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_shrapnel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cluster, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_flare, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_electric, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_poison, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gas, 16));
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
		
		if(this == ModItems.missile_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.launch_pad), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.designator, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.designator_range, 1));
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
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_endo, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_exo, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_doomsday, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_taint, 1));
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
			player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 30 * 20, 0, true));
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
		
		if(this == ModItems.letter && world.isRemote)
		{
			if(player.getUniqueID().toString().equals(Library.a20)) {
				player.addChatMessage(new ChatComponentText("Error: null reference @ com.hbm.items.ItemStarterKit.class, please report this to the modder!"));
			} else {
				player.addChatMessage(new ChatComponentText("You rip the letter in half; nothing happens."));
			}
		}
		
		return stack;
		
	}

}
