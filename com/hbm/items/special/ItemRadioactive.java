package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RadEntitySavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemRadioactive extends ItemCustomLore {
	
    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	
    	doRadiationDamage(entity, stack.stackSize);
    }
    
    public void doRadiationDamage(Entity entity, float mod) {
    	
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
        		//Library.applyRadiation(living, 80, 24, 60, 19);
				Library.applyRadData(living, 50F/20F * mod);
			}

			//Strong
			
			if (this == ModItems.ingot_u233 || 
					this == ModItems.ingot_u235 || 
					this == ModItems.ingot_pu239 || 
					this == ModItems.ingot_pu239 || 
					this == ModItems.ingot_pu240 || 
					this == ModItems.ingot_uranium_fuel || 
					this == ModItems.ingot_plutonium_fuel || 
					this == ModItems.ingot_mox_fuel || 
					this == ModItems.rod_quad_u233 || 
					this == ModItems.rod_dual_u233 || 
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
					this == ModItems.nuclear_waste || 
					this == ModItems.waste_uranium || 
					this == ModItems.waste_plutonium || 
					this == ModItems.waste_mox || 
					this == ModItems.waste_schrabidium) {
        		//Library.applyRadiation(living, 60, 19, 40, 14);
				Library.applyRadData(living, 5F/20F * mod);
			}

			//Strong Fire
			
			if (this == ModItems.waste_uranium_hot || 
					this == ModItems.waste_plutonium_hot || 
					this == ModItems.waste_mox_hot || 
					this == ModItems.waste_schrabidium_hot) {
        		//Library.applyRadiation(living, 60, 19, 40, 14);
				Library.applyRadData(living, 5F/20F * mod);
				living.setFire(5);
			}
			
			//Strong Nuggets
			
			if (this == ModItems.nugget_u233 || 
					this == ModItems.nugget_u235 || 
					this == ModItems.nugget_pu239 || 
					this == ModItems.nugget_pu240 || 
					this == ModItems.nugget_uranium_fuel || 
					this == ModItems.nugget_plutonium_fuel || 
					this == ModItems.nugget_mox_fuel || 
					this == ModItems.rod_u233 || 
					this == ModItems.rod_u235 || 
					this == ModItems.rod_pu239 || 
					this == ModItems.rod_pu240 || 
					this == ModItems.rod_uranium_fuel || 
					this == ModItems.rod_plutonium_fuel || 
					this == ModItems.rod_mox_fuel || 
					this == ModItems.rod_uranium_fuel_depleted || 
					this == ModItems.rod_plutonium_fuel_depleted || 
					this == ModItems.rod_mox_fuel_depleted || 
					this == ModItems.ingot_thorium_fuel) {
        		//Library.applyRadiation(living, 45, 19, 30, 14);
				Library.applyRadData(living, 3.5F/20F * mod);
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
        		//Library.applyRadiation(living, 30, 14, 15, 9);
				Library.applyRadData(living, 2.5F/20F * mod);
			}
			
			//Medium Nuggets

			if (this == ModItems.nugget_neptunium || 
					this == ModItems.nugget_pu238 || 
					this == ModItems.nugget_plutonium || 
					this == ModItems.rod_neptunium || 
					this == ModItems.rod_pu238 || 
					this == ModItems.rod_plutonium || 
					this == ModItems.pellet_rtg_weak || 
					this == ModItems.waste_thorium) {
        		//Library.applyRadiation(living, 20, 14, 5, 9);
				Library.applyRadData(living, 1F/20F * mod);
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
        		//Library.applyRadiation(living, 20, 4, 5, 0);
				Library.applyRadData(living, 0.5F/20F * mod);
			}
			
			//Weak Nuggets

			if (this == ModItems.nugget_uranium || 
					this == ModItems.nugget_u238 || 
					this == ModItems.rod_uranium || 
					this == ModItems.rod_u238 || 
					this == ModItems.powder_yellowcake) {
        		//Library.applyRadiation(living, 10, 4, 0, 0);
				Library.applyRadData(living, 0.25F/20F * mod);
			}
			
			//Tritium

			if (this == ModItems.cell_tritium || 
					this == ModItems.rod_tritium || 
					this == ModItems.rod_dual_tritium || 
					this == ModItems.rod_quad_tritium || 
					this == ModItems.nugget_thorium_fuel) {
        		//Library.applyRadiation(living, 10, 4, 0, 0);
				Library.applyRadData(living, 0.75F/20F * mod);
			}
			
			//Powder

			if (this == ModItems.powder_neptunium || 
					this == ModItems.powder_plutonium) {
        		//Library.applyRadiation(living, 60, 19, 45, 14);
				Library.applyRadData(living, 2F/20F * mod);
				
				if(living instanceof EntityPlayer && !Library.checkForHazmat((EntityPlayer)living))
					living.setFire(5);
			}

			if (this == ModItems.powder_uranium) {
        		//Library.applyRadiation(living, 20, 4, 0, 0);
				Library.applyRadData(living, 1F/20F * mod);
				
				if(living instanceof EntityPlayer && !Library.checkForHazmat((EntityPlayer)living))
					living.setFire(5);
			}
			
			//Schrabidic

			if (this == ModItems.ingot_schrabidium || 
					this == ModItems.ingot_solinium || 
					this == ModItems.ingot_schrabidium_fuel || 
					this == ModItems.ingot_hes || 
					this == ModItems.ingot_les || 
					this == ModItems.cell_sas3 || 
					this == ModItems.fleija_propellant || 
					this == ModItems.solinium_core || 
					this == ModItems.rod_schrabidium || 
					this == ModItems.rod_dual_schrabidium || 
					this == ModItems.rod_quad_schrabidium || 
					this == ModItems.rod_solinium || 
					this == ModItems.rod_dual_solinium || 
					this == ModItems.rod_quad_solinium || 
					this == ModItems.rod_schrabidium_fuel || 
					this == ModItems.rod_dual_schrabidium_fuel || 
					this == ModItems.rod_quad_schrabidium_fuel) {
				
				if(living instanceof EntityPlayer && !Library.checkForHazmat((EntityPlayer)living))
					living.addPotionEffect(new PotionEffect(Potion.blindness.id, 60 * 20, 0));
				
        		//Library.applyRadiation(living, 100, 29, 75, 24);
				Library.applyRadData(living, 7.5F/20F * mod);
			}

			if (this == ModItems.nugget_schrabidium || 
					this == ModItems.nugget_solinium || 
					this == ModItems.nugget_schrabidium_fuel || 
					this == ModItems.nugget_hes || 
					this == ModItems.nugget_les) {
				//living.addPotionEffect(new PotionEffect(Potion.blindness.id, 60 * 20, 0));
				Library.applyRadData(living, 5F/20F * mod);
        		//Library.applyRadiation(living, 75, 29, 60, 24);
			}

			if (this == ModItems.plate_schrabidium || 
					this == ModItems.wire_schrabidium) {
				//living.addPotionEffect(new PotionEffect(Potion.blindness.id, 60 * 20, 0));
				Library.applyRadData(living, 7.5F/20F * mod);
        		//Library.applyRadiation(living, 80, 29, 60, 24);
			}

			if (this == ModItems.powder_schrabidium) {
        		//Library.applyRadiation(living, 100, 29, 75, 24);
				Library.applyRadData(living, 7.5F/20F * mod);
				
				if(living instanceof EntityPlayer && !Library.checkForHazmat((EntityPlayer)living)) {
					living.addPotionEffect(new PotionEffect(Potion.blindness.id, 60 * 20, 0));
					living.setFire(5);
				}
			}
		}
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		super.addInformation(stack, player, list, bool);
		
		list.add(EnumChatFormatting.GREEN + "[Radioactive]");
	}
}
