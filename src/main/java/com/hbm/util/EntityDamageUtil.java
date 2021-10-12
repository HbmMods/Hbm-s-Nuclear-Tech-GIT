package com.hbm.util;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Level;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class EntityDamageUtil {
	
	public static boolean attackEntityFromIgnoreIFrame(Entity victim, DamageSource src, float damage) {

		if(!victim.attackEntityFrom(src, damage)) {
			try {
				Field lastDamage = ReflectionHelper.findField(EntityLivingBase.class, "lastDamage", "field_110153_bc");
				
				float dmg = damage + lastDamage.getFloat(victim);
				
				return victim.attackEntityFrom(src, dmg);
			} catch (Exception x) {
				MainRegistry.logger.catching(Level.WARN, x);
				return false;
			}
		} else {
			return true;
		}
	}
}
