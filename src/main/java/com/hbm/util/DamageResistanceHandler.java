package com.hbm.util;

import java.util.HashMap;

import com.hbm.util.Tuple.Quartet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Basic handling/registry class for our custom resistance stats.
 * Handles resistances for individual armor pieces, full sets as well as entity classes for innate damage resistance
 * 
 * @author hbm
 */
public class DamageResistanceHandler {
	
	/** Currently cached DT reduction */
	public static float currentPDT = 0F;
	/** Currently cached armor piercing % */
	public static float currentPDR = 0F;
	
	public static final String KEY_EXPLOSION = "EXPL";

	public static HashMap<Item, ResistanceStats> itemStats = new HashMap();
	public static HashMap<Quartet<Item, Item, Item, Item>, ResistanceStats> setStats = new HashMap();
	public static HashMap<Class<? extends Entity>, ResistanceStats> entityStats = new HashMap();
	
	public static enum DamageClass {
		PHYSICAL,
		FIRE,
		EXPLOSIVE,
		ELECTRIC,
		LASER,
		SUBATOMIC
	}

	public static void init() {
		entityStats.put(EntityCreeper.class, new ResistanceStats().add(KEY_EXPLOSION, 2F, 0.5F));
	}
	
	public static void setup(float dt, float dr) {
		currentPDT = dt;
		currentPDR = dr;
	}
	
	public static void reset() {
		currentPDT = 0;
		currentPDR = 0;
	}
	
	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event) {
		EntityLivingBase e = event.entityLiving;
		float amount = event.ammount;
		
		float[] vals = getDTDR(e, event.source, amount, currentPDT, currentPDR);
		float dt = vals[0];
		
		if(dt > 0 && dt >= event.ammount) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onEntityDamaged(LivingHurtEvent event) {
		event.ammount = calculateDamage(event.entityLiving, event.source, event.ammount, currentPDT, currentPDR);
	}
	
	public static String typeToKey(DamageSource source) {
		if(source.isExplosion()) return KEY_EXPLOSION;
		return source.damageType;
	}
	
	public static float calculateDamage(EntityLivingBase entity, DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isDamageAbsolute() || damage.isUnblockable()) return amount;
		
		float[] vals = getDTDR(entity, damage, amount, pierceDT, pierce);
		float dt = vals[0];
		float dr = vals[1];
		
		dt = Math.max(0F, dt - pierceDT);
		if(dt >= amount) return 0F;
		amount -= dt;
		dr *= MathHelper.clamp_float(1F - pierce, 0F, 1F);
		
		return amount *= (1F - dr);
	}
	
	public static float[] getDTDR(EntityLivingBase entity, DamageSource damage, float amount, float pierceDT, float pierce) {
		
		String key = typeToKey(damage);
		float dt = 0;
		float dr = 0;
		
		/// SET HANDLING ///
		Quartet wornSet = new Quartet(
				entity.getEquipmentInSlot(1) != null ? entity.getEquipmentInSlot(1).getItem() : null,
				entity.getEquipmentInSlot(2) != null ? entity.getEquipmentInSlot(2).getItem() : null,
				entity.getEquipmentInSlot(3) != null ? entity.getEquipmentInSlot(3).getItem() : null,
				entity.getEquipmentInSlot(4) != null ? entity.getEquipmentInSlot(4).getItem() : null
				);
		
		ResistanceStats setResistance = setStats.get(wornSet);
		if(setResistance != null) {
			Resistance res = setResistance.resistances.get(key);
			if(res != null) {
				dt += res.threshold;
				dr += res.resistance;
			}
		}

		/// ARMOR ///
		for(int i = 1; i <= 4; i++) {
			ItemStack armor = entity.getEquipmentInSlot(i);
			if(armor == null) continue;
			ResistanceStats stats = itemStats.get(armor.getItem());
			if(stats == null) continue;
			Resistance res = stats.resistances.get(key);
			if(res == null) continue;
			dt += res.threshold;
			dr += res.resistance;
		}

		/// ENTITY CLASS HANDLING ///
		ResistanceStats innateResistance = entityStats.get(entity.getClass());
		if(innateResistance != null) {
			Resistance res = innateResistance.resistances.get(key);
			if(res != null) {
				dt += res.threshold;
				dr += res.resistance;
			}
		}
		
		return new float[] {dt, dr};
	}
	
	public static class ResistanceStats {
		
		public HashMap<String, Resistance> resistances = new HashMap();
		
		public ResistanceStats add(String type, float threshold, float resistance) {
			resistances.put(type, new Resistance(threshold, resistance));
			return this;
		}
	}
	
	public static class Resistance {
		
		public float threshold;
		public float resistance;
		
		public Resistance(float threshold, float resistance) {
			this.threshold = threshold;
			this.resistance = resistance;
		}
	}
}
