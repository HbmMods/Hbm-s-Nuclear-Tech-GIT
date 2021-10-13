package com.hbm.util;

import java.lang.reflect.Field;

<<<<<<< HEAD
import org.apache.logging.log4j.Level;

import com.hbm.main.MainRegistry;
=======
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
>>>>>>> master

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
<<<<<<< HEAD
import net.minecraft.util.DamageSource;
=======
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
>>>>>>> master

public class EntityDamageUtil {
	
	public static boolean attackEntityFromIgnoreIFrame(Entity victim, DamageSource src, float damage) {

		if(!victim.attackEntityFrom(src, damage)) {
			try {
				Field lastDamage = ReflectionHelper.findField(EntityLivingBase.class, "lastDamage", "field_110153_bc");
				
<<<<<<< HEAD
				float dmg = damage + lastDamage.getFloat(victim);
				
				return victim.attackEntityFrom(src, dmg);
			} catch (Exception x) {
				MainRegistry.logger.catching(Level.WARN, x);
=======
				float dmg = (float) damage + lastDamage.getFloat(victim);
				
				return victim.attackEntityFrom(src, dmg);
			} catch (Exception x) {
>>>>>>> master
				return false;
			}
		} else {
			return true;
		}
	}
<<<<<<< HEAD
=======
	
	public static float getLastDamage(Entity victim) {
		
		try {
			Field lastDamage = ReflectionHelper.findField(EntityLivingBase.class, "lastDamage", "field_110153_bc");

			return lastDamage.getFloat(victim);
		} catch(Exception x) {
			return 0F;
		}
	}
	
	public static boolean wasAttackedByV1(DamageSource source) {

		if(source instanceof EntityDamageSource) {
			Entity attacker = ((EntityDamageSource) source).getEntity();
			
			if(attacker instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) attacker;
				ItemStack chestplate = player.inventory.armorInventory[2];
				
				if(chestplate != null && ArmorModHandler.hasMods(chestplate)) {
					ItemStack[] mods = ArmorModHandler.pryMods(chestplate);
					
					if(mods[ArmorModHandler.extra] != null && mods[ArmorModHandler.extra].getItem() == ModItems.v1) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
>>>>>>> master
}
