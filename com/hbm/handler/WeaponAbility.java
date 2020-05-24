package com.hbm.handler;

import com.hbm.items.tool.ItemToolAbility;
import com.hbm.lib.Library;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public abstract class WeaponAbility {
	
	public abstract void onHit(World world, EntityPlayer player, Entity victim, ItemToolAbility tool);
	public abstract String getName();
	public abstract String getFullName();
	
	public static class RadiationAbility extends WeaponAbility {
		
		float rad;
		
		public RadiationAbility(float rad) {
			this.rad = rad;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, ItemToolAbility tool) {
			
			Library.applyRadData(victim, rad);
		}

		@Override
		public String getName() {
			return "weapon.ability.radiation";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + rad + ")";
		}
	}
	
	public static class VampireAbility extends WeaponAbility {
		
		float amount;
		
		public VampireAbility(float amount) {
			this.amount = amount;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, ItemToolAbility tool) {
			
			if(victim instanceof EntityLivingBase) {
				
				EntityLivingBase living = (EntityLivingBase) victim;
				
				living.setHealth(living.getHealth() - amount);
				player.heal(amount);
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.vampire";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + amount + ")";
		}
	}
	
	public static class StunAbility extends WeaponAbility {
		
		int duration;
		
		public StunAbility(int duration) {
			this.duration = duration;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, ItemToolAbility tool) {
			
			if(victim instanceof EntityLivingBase) {
				
				EntityLivingBase living = (EntityLivingBase) victim;

				living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 4));
				living.addPotionEffect(new PotionEffect(Potion.weakness.id, duration * 20, 4));
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.stun";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + duration + ")";
		}
	}

}
