package com.hbm.items.special;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemRadioactive extends Item {
	
    public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	if((entity instanceof EntityPlayer && !Library.checkForHazmat((EntityPlayer)entity)) || !(entity instanceof EntityPlayer))
    	{
    		doRadiationDamage(entity);
    	}
    }
    
    public void doRadiationDamage(Entity entity) {
    	
    	//Ultra Deadly: Watz Fuel
    	//Strong: U235, Pu239, Pu240, all fuels except Schrabidium
    	//Medium: Plutonium, Neptunium, Pu238, RTG Pellet
    	//Weak: Uranium, U238
    	//Incendiary: All radioactive powders
    	//Schrabidic: All Schrabidium related materials

		if (entity instanceof EntityLivingBase) {

			EntityLivingBase living = (EntityLivingBase)entity;

			//Ultra Deadly
			
			if (this == ModItems.pellet_hes || 
					this == ModItems.pellet_les || 
					this == ModItems.pellet_mes || 
					this == ModItems.pellet_neptunium || 
					this == ModItems.pellet_schrabidium) {
				living.addPotionEffect(new PotionEffect(Potion.poison.id, 120 * 20, 4));
				living.addPotionEffect(new PotionEffect(Potion.wither.id, 30 * 20, 2));
				living.attackEntityFrom(ModDamageSource.radiation, 15);
			}

			//Strong
			
			if (this == ModItems.ingot_u235 || 
					this == ModItems.ingot_pu239 || 
					this == ModItems.ingot_pu240 || 
					this == ModItems.ingot_uranium_fuel || 
					this == ModItems.ingot_plutonium_fuel || 
					this == ModItems.ingot_mox_fuel || 
					this == ModItems.rod_quad_u235 || 
					this == ModItems.rod_dual_u235 || 
					this == ModItems.rod_quad_pu239 || 
					this == ModItems.rod_dual_pu239 || 
					this == ModItems.rod_quad_pu240 || 
					this == ModItems.rod_dual_pu240 || 
					this == ModItems.rod_quad_uranium_fuel || 
					this == ModItems.rod_dual_uranium_fuel || 
					this == ModItems.rod_quad_plutonium_fuel || 
					this == ModItems.rod_dual_plutonium_fuel || 
					this == ModItems.rod_quad_mox_fuel || 
					this == ModItems.rod_dual_mox_fuel ||
					this == ModItems.rod_quad_uranium_fuel_depleted || 
					this == ModItems.rod_dual_uranium_fuel_depleted || 
					this == ModItems.rod_quad_plutonium_fuel_depleted || 
					this == ModItems.rod_dual_plutonium_fuel_depleted || 
					this == ModItems.rod_quad_mox_fuel_depleted || 
					this == ModItems.rod_dual_mox_fuel_depleted || 
					this == ModItems.boy_bullet || 
					this == ModItems.boy_target || 
					this == ModItems.gadget_core || 
					this == ModItems.man_core || 
					this == ModItems.nuclear_waste) {
				living.addPotionEffect(new PotionEffect(Potion.poison.id, 45 * 20, 4));
				living.addPotionEffect(new PotionEffect(Potion.wither.id, 5 * 20, 2));
				living.attackEntityFrom(ModDamageSource.radiation, 3);
			}
			
			//Strong Nuggets
			
			if (this == ModItems.nugget_u235 || 
					this == ModItems.nugget_pu239 || 
					this == ModItems.nugget_pu240 || 
					this == ModItems.nugget_uranium_fuel || 
					this == ModItems.nugget_plutonium_fuel || 
					this == ModItems.nugget_mox_fuel || 
					this == ModItems.rod_u235 || 
					this == ModItems.rod_pu239 || 
					this == ModItems.rod_pu240 || 
					this == ModItems.rod_uranium_fuel || 
					this == ModItems.rod_plutonium_fuel || 
					this == ModItems.rod_mox_fuel || 
					this == ModItems.rod_uranium_fuel_depleted || 
					this == ModItems.rod_plutonium_fuel_depleted || 
					this == ModItems.rod_mox_fuel_depleted) {
				living.addPotionEffect(new PotionEffect(Potion.poison.id, 35 * 20, 4));
				living.addPotionEffect(new PotionEffect(Potion.wither.id, 1 * 20, 2));
				living.attackEntityFrom(ModDamageSource.radiation, 1);
			}
			
			//Medium

			if (this == ModItems.ingot_neptunium || 
					this == ModItems.ingot_pu238 || 
					this == ModItems.ingot_plutonium || 
					this == ModItems.pellet_rtg || 
					this == ModItems.rod_quad_neptunium || 
					this == ModItems.rod_dual_neptunium || 
					this == ModItems.rod_quad_pu238 || 
					this == ModItems.rod_dual_pu238 || 
					this == ModItems.rod_quad_plutonium || 
					this == ModItems.rod_dual_plutonium || 
					this == ModItems.mike_core || 
					this == ModItems.tsar_core || 
					this == ModItems.trinitite) {
				living.addPotionEffect(new PotionEffect(Potion.poison.id, 25 * 20, 4));
				living.attackEntityFrom(ModDamageSource.radiation, 1);
			}
			
			//Medium Nuggets

			if (this == ModItems.nugget_neptunium || 
					this == ModItems.nugget_pu238 || 
					this == ModItems.nugget_plutonium || 
					this == ModItems.rod_neptunium || 
					this == ModItems.rod_pu238 || 
					this == ModItems.rod_plutonium) {
				living.addPotionEffect(new PotionEffect(Potion.poison.id, 15 * 20, 2));
			}
			
			//Weak

			if (this == ModItems.ingot_uranium || 
					this == ModItems.ingot_u238 || 
					this == ModItems.rod_quad_uranium || 
					this == ModItems.rod_dual_uranium || 
					this == ModItems.rod_quad_u238 || 
					this == ModItems.rod_dual_u238 || 
					this == ModItems.rod_quad_pu238 || 
					this == ModItems.rod_dual_pu238) {
				living.addPotionEffect(new PotionEffect(Potion.poison.id, 15 * 20, 0));
			}
			
			//Weak Nuggets

			if (this == ModItems.nugget_uranium || 
					this == ModItems.nugget_u238 || 
					this == ModItems.rod_uranium || 
					this == ModItems.rod_u238 || 
					this == ModItems.cell_tritium || 
					this == ModItems.rod_tritium || 
					this == ModItems.rod_dual_tritium || 
					this == ModItems.rod_quad_tritium) {
				living.addPotionEffect(new PotionEffect(Potion.poison.id, 5 * 20, 0));
			}
			
			//Powder

			if (this == ModItems.powder_neptunium || 
					this == ModItems.powder_plutonium) {
				living.addPotionEffect(new PotionEffect(Potion.poison.id, 25 * 20, 4));
				living.attackEntityFrom(ModDamageSource.radiation, 1);
				living.setFire(5);
			}

			if (this == ModItems.powder_uranium) {
				living.addPotionEffect(new PotionEffect(Potion.poison.id, 15 * 20, 0));
				living.setFire(5);
			}
			
			//Schrabidic

			if (this == ModItems.ingot_schrabidium || 
					this == ModItems.ingot_schrabidium_fuel || 
					this == ModItems.ingot_hes || 
					this == ModItems.ingot_les || 
					this == ModItems.cell_sas3 || 
					this == ModItems.fleija_propellant || 
					this == ModItems.gun_revolver_schrabidium_ammo || 
					this == ModItems.rod_schrabidium || 
					this == ModItems.rod_dual_schrabidium || 
					this == ModItems.rod_quad_schrabidium || 
					this == ModItems.rod_schrabidium_fuel || 
					this == ModItems.rod_dual_schrabidium_fuel || 
					this == ModItems.rod_quad_schrabidium_fuel) {
				living.addPotionEffect(new PotionEffect(Potion.blindness.id, 2 * 60 * 20, 0));
				living.attackEntityFrom(ModDamageSource.radiation, 30);
			}

			if (this == ModItems.nugget_schrabidium || 
					this == ModItems.nugget_schrabidium_fuel || 
					this == ModItems.nugget_hes || 
					this == ModItems.nugget_les) {
				living.addPotionEffect(new PotionEffect(Potion.blindness.id, 2 * 60 * 20, 0));
				living.attackEntityFrom(ModDamageSource.radiation, 10);
			}

			if (this == ModItems.plate_schrabidium || 
					this == ModItems.wire_schrabidium) {
				living.addPotionEffect(new PotionEffect(Potion.blindness.id, 2 * 60 * 20, 0));
				living.attackEntityFrom(ModDamageSource.radiation, 15);
			}

			if (this == ModItems.powder_schrabidium) {
				living.addPotionEffect(new PotionEffect(Potion.blindness.id, 2 * 60 * 20, 0));
				living.attackEntityFrom(ModDamageSource.radiation, 30);
				living.setFire(5);
			}
		}
    }
}
