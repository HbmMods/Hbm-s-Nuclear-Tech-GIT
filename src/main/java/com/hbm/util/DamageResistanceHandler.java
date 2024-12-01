package com.hbm.util;

import java.util.HashMap;

import com.hbm.items.ModItems;
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

	public static void init() {
		entityStats.put(EntityCreeper.class, new ResistanceStats().add(KEY_EXPLOSION, 2F, 0.5F));

		setStats.put(new Quartet(ModItems.steel_helmet, ModItems.steel_plate, ModItems.steel_legs, ModItems.steel_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.titanium_helmet, ModItems.titanium_plate, ModItems.titanium_legs, ModItems.titanium_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.alloy_helmet, ModItems.alloy_plate, ModItems.alloy_legs, ModItems.alloy_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.cobalt_helmet, ModItems.cobalt_plate, ModItems.cobalt_legs, ModItems.cobalt_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.starmetal_helmet, ModItems.starmetal_plate, ModItems.starmetal_legs, ModItems.starmetal_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.zirconium_legs, ModItems.zirconium_legs, ModItems.zirconium_legs, ModItems.zirconium_legs), new ResistanceStats());
		setStats.put(new Quartet(ModItems.dnt_helmet, ModItems.dnt_plate, ModItems.dnt_legs, ModItems.dnt_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.schrabidium_helmet, ModItems.schrabidium_plate, ModItems.schrabidium_legs, ModItems.schrabidium_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.robes_helmet, ModItems.robes_plate, ModItems.robes_legs, ModItems.robes_boots), new ResistanceStats());
		
		setStats.put(new Quartet(ModItems.steamsuit_helmet, ModItems.steamsuit_plate, ModItems.steamsuit_legs, ModItems.steamsuit_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.dieselsuit_helmet, ModItems.dieselsuit_plate, ModItems.dieselsuit_legs, ModItems.dieselsuit_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.ajr_helmet, ModItems.ajr_plate, ModItems.ajr_legs, ModItems.ajr_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.ajro_helmet, ModItems.ajro_plate, ModItems.ajro_legs, ModItems.ajro_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.rpa_helmet, ModItems.rpa_plate, ModItems.rpa_legs, ModItems.rpa_boots), new ResistanceStats());
		ResistanceStats bj = new ResistanceStats();
		setStats.put(new Quartet(ModItems.bj_helmet, ModItems.bj_plate, ModItems.bj_legs, ModItems.bj_boots), bj);
		setStats.put(new Quartet(ModItems.bj_helmet, ModItems.bj_plate_jetpack, ModItems.bj_legs, ModItems.bj_boots), bj);
		setStats.put(new Quartet(ModItems.envsuit_helmet, ModItems.envsuit_plate, ModItems.envsuit_legs, ModItems.envsuit_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.hev_helmet, ModItems.hev_plate, ModItems.hev_legs, ModItems.hev_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.bismuth_helmet, ModItems.bismuth_plate, ModItems.bismuth_legs, ModItems.bismuth_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.fau_helmet, ModItems.fau_plate, ModItems.fau_legs, ModItems.fau_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.dns_helmet, ModItems.dns_plate, ModItems.dns_legs, ModItems.dns_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.trenchmaster_helmet, ModItems.trenchmaster_plate, ModItems.trenchmaster_legs, ModItems.trenchmaster_boots), new ResistanceStats());
		
		setStats.put(new Quartet(ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red), new ResistanceStats());
		setStats.put(new Quartet(ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey), new ResistanceStats());
		setStats.put(new Quartet(ModItems.liquidator_helmet, ModItems.liquidator_plate, ModItems.liquidator_legs, ModItems.liquidator_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots), new ResistanceStats());
		setStats.put(new Quartet(ModItems.asbestos_helmet, ModItems.asbestos_plate, ModItems.asbestos_legs, ModItems.asbestos_boots), new ResistanceStats());
	}
	
	public static enum DamageClass {
		PHYSICAL,
		FIRE,
		EXPLOSIVE,
		ELECTRIC,
		LASER,
		SUBATOMIC,
		OTHER
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
		
		if(dt > 0 && dt >= event.ammount) {
			event.setCanceled(true);
			EntityDamageUtil.damageArmorNT(e, amount);
		}
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
				entity.getEquipmentInSlot(4) != null ? entity.getEquipmentInSlot(4).getItem() : null,
				entity.getEquipmentInSlot(3) != null ? entity.getEquipmentInSlot(3).getItem() : null,
				entity.getEquipmentInSlot(2) != null ? entity.getEquipmentInSlot(2).getItem() : null,
				entity.getEquipmentInSlot(1) != null ? entity.getEquipmentInSlot(1).getItem() : null
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
