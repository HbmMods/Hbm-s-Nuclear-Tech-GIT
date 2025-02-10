package com.hbm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Quartet;

import api.hbm.entity.IResistanceProvider;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
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

	public static final String CATEGORY_EXPLOSION = "EXPL";
	public static final String CATEGORY_FIRE = "FIRE";
	public static final String CATEGORY_PROJECTILE = "PROJ";
	public static final String CATEGORY_ENERGY = "EN";

	public static HashMap<Item, ResistanceStats> itemStats = new HashMap();
	public static HashMap<Quartet<Item, Item, Item, Item>, ResistanceStats> setStats = new HashMap();
	public static HashMap<Class<? extends Entity>, ResistanceStats> entityStats = new HashMap();
	
	public static HashMap<Item, List<Quartet<Item, Item, Item, Item>>> itemInfoSet = new HashMap();

	public static void init() {
		
		itemStats.clear();
		setStats.clear();
		entityStats.clear();
		itemInfoSet.clear();
		
		entityStats.put(EntityCreeper.class, new ResistanceStats().addCategory(CATEGORY_EXPLOSION, 2F, 0.5F));

		itemStats.put(ModItems.jackt, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 5F, 0.5F));
		itemStats.put(ModItems.jackt2, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 5F, 0.5F));

		registerSet(ModItems.steel_helmet, ModItems.steel_plate, ModItems.steel_legs, ModItems.steel_boots, new ResistanceStats());
		registerSet(ModItems.titanium_helmet, ModItems.titanium_plate, ModItems.titanium_legs, ModItems.titanium_boots, new ResistanceStats());
		registerSet(ModItems.alloy_helmet, ModItems.alloy_plate, ModItems.alloy_legs, ModItems.alloy_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 2F, 0.1F));
		registerSet(ModItems.cobalt_helmet, ModItems.cobalt_plate, ModItems.cobalt_legs, ModItems.cobalt_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 2F, 0.1F));
		registerSet(ModItems.starmetal_helmet, ModItems.starmetal_plate, ModItems.starmetal_legs, ModItems.starmetal_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 3F, 0.25F)
				.setOther(1F, 0.1F));
		registerSet(ModItems.zirconium_legs, ModItems.zirconium_legs, ModItems.zirconium_legs, ModItems.zirconium_legs, new ResistanceStats()
				.setOther(0F, 1F));
		registerSet(ModItems.dnt_helmet, ModItems.dnt_plate, ModItems.dnt_legs, ModItems.dnt_boots, new ResistanceStats());
		registerSet(ModItems.cmb_helmet, ModItems.cmb_plate, ModItems.cmb_legs, ModItems.cmb_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 5F, 0.5F)
				.setOther(5F, 0.25F));
		registerSet(ModItems.schrabidium_helmet, ModItems.schrabidium_plate, ModItems.schrabidium_legs, ModItems.schrabidium_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 10F, 0.65F)
				.setOther(5F, 0.5F));
		registerSet(ModItems.robes_helmet, ModItems.robes_plate, ModItems.robes_legs, ModItems.robes_boots, new ResistanceStats());

		registerSet(ModItems.security_helmet, ModItems.security_plate, ModItems.security_legs, ModItems.security_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 5F, 0.5F)
				.addCategory(CATEGORY_EXPLOSION, 2F, 0.25F));
		registerSet(ModItems.steamsuit_helmet, ModItems.steamsuit_plate, ModItems.steamsuit_legs, ModItems.steamsuit_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 2F, 0.15F)
				.addCategory(CATEGORY_FIRE, 0.5F, 0.25F)
				.addExact(DamageSource.fall.damageType, 5F, 0.25F)
				.setOther(0F, 0.1F));
		registerSet(ModItems.dieselsuit_helmet, ModItems.dieselsuit_plate, ModItems.dieselsuit_legs, ModItems.dieselsuit_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 1F, 0.15F)
				.addCategory(CATEGORY_FIRE, 0.5F, 0.5F)
				.addCategory(CATEGORY_EXPLOSION, 2F, 0.15F)
				.setOther(0F, 0.1F));
		registerSet(ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 2F, 0.15F)
				.addCategory(CATEGORY_FIRE, 0.5F, 0.35F)
				.addCategory(CATEGORY_EXPLOSION, 5F, 0.25F)
				.addExact(DamageSource.fall.damageType, 0F, 1F)
				.setOther(0F, 0.1F));
		registerSet(ModItems.ajr_helmet, ModItems.ajr_plate, ModItems.ajr_legs, ModItems.ajr_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 4F, 0.15F)
				.addCategory(CATEGORY_FIRE, 0.5F, 0.35F)
				.addCategory(CATEGORY_EXPLOSION, 7.5F, 0.25F)
				.addExact(DamageSource.fall.damageType, 0F, 1F)
				.setOther(0F, 0.15F));
		registerSet(ModItems.ajro_helmet, ModItems.ajro_plate, ModItems.ajro_legs, ModItems.ajro_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 4F, 0.15F)
				.addCategory(CATEGORY_FIRE, 0.5F, 0.35F)
				.addCategory(CATEGORY_EXPLOSION, 7.5F, 0.25F)
				.addExact(DamageSource.fall.damageType, 0F, 1F)
				.setOther(0F, 0.15F));
		registerSet(ModItems.rpa_helmet, ModItems.rpa_plate, ModItems.rpa_legs, ModItems.rpa_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 20F, 0.65F)
				.addCategory(CATEGORY_FIRE, 10F, 0.75F)
				.addCategory(CATEGORY_EXPLOSION, 15F, 0.25F)
				.addExact(DamageClass.LASER.name(), 10F, 0.75F)
				.addExact(DamageSource.fall.damageType, 0F, 1F)
				.setOther(10F, 0.15F));
		ResistanceStats bj = new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 5F, 0.5F)
				.addCategory(CATEGORY_FIRE, 2.5F, 0.5F)
				.addCategory(CATEGORY_EXPLOSION, 10F, 0.25F)
				.addExact(DamageSource.fall.damageType, 0F, 1F)
				.setOther(2F, 0.15F);
		registerSet(ModItems.bj_helmet, ModItems.bj_plate, ModItems.bj_legs, ModItems.bj_boots, bj);
		registerSet(ModItems.bj_helmet, ModItems.bj_plate_jetpack, ModItems.bj_legs, ModItems.bj_boots, bj);
		registerSet(ModItems.envsuit_helmet, ModItems.envsuit_plate, ModItems.envsuit_legs, ModItems.envsuit_boots, new ResistanceStats()
				.addCategory(CATEGORY_FIRE, 2F, 0.75F)
				.addExact(DamageSource.drown.damageType, 0F, 1F)
				.addExact(DamageSource.fall.damageType, 5F, 0.75F)
				.setOther(0F, 0.1F));
		registerSet(ModItems.hev_helmet, ModItems.hev_plate, ModItems.hev_legs, ModItems.hev_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 2F, 0.25F)
				.addCategory(CATEGORY_FIRE, 0.5F, 0.5F)
				.addCategory(CATEGORY_EXPLOSION, 5F, 0.25F)
				.addExact(DamageSource.onFire.damageType, 0F, 1F)
				.addExact(DamageSource.fall.damageType, 10F, 0F)
				.setOther(2F, 0.25F));
		registerSet(ModItems.bismuth_helmet, ModItems.bismuth_plate, ModItems.bismuth_legs, ModItems.bismuth_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 2F, 0.15F)
				.addCategory(CATEGORY_FIRE, 5F, 0.5F)
				.addCategory(CATEGORY_EXPLOSION, 5F, 0.25F)
				.addExact(DamageSource.fall.damageType, 0F, 1F)
				.setOther(2F, 0.25F));
		registerSet(ModItems.fau_helmet, ModItems.fau_plate, ModItems.fau_legs, ModItems.fau_boots, new ResistanceStats()
				.addCategory(CATEGORY_EXPLOSION, 50F, 0.95F)
				.addExact(DamageClass.LASER.name(), 25F, 0.95F)
				.addExact(DamageSource.fall.damageType, 0F, 1F)
				.setOther(100F, 0.99F));
		registerSet(ModItems.dns_helmet, ModItems.dns_plate, ModItems.dns_legs, ModItems.dns_boots, new ResistanceStats()
				.addCategory(CATEGORY_EXPLOSION, 100F, 0.99F)
				.setOther(100F, 1F));
		registerSet(ModItems.trenchmaster_helmet, ModItems.trenchmaster_plate, ModItems.trenchmaster_legs, ModItems.trenchmaster_boots, new ResistanceStats()
				.addCategory(CATEGORY_PROJECTILE, 5F, 0.5F)
				.addCategory(CATEGORY_FIRE, 5F, 0.5F)
				.addCategory(CATEGORY_EXPLOSION, 5F, 0.25F)
				.addExact(DamageClass.LASER.name(), 15F, 0.9F)
				.addExact(DamageSource.fall.damageType, 10F, 0.5F)
				.setOther(5F, 0.25F));

		registerSet(ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots, new ResistanceStats()
				.setOther(1_000_000F, 1F));
		
		registerSet(ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots, new ResistanceStats());
		registerSet(ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red, new ResistanceStats());
		registerSet(ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey, new ResistanceStats());
		registerSet(ModItems.liquidator_helmet, ModItems.liquidator_plate, ModItems.liquidator_legs, ModItems.liquidator_boots, new ResistanceStats());
		registerSet(ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots, new ResistanceStats());
		registerSet(ModItems.asbestos_helmet, ModItems.asbestos_plate, ModItems.asbestos_legs, ModItems.asbestos_boots, new ResistanceStats()
				.addCategory(CATEGORY_FIRE, 10F, 0.9F));
	}
	
	public static void registerSet(Item helmet, Item plate, Item legs, Item boots, ResistanceStats stats) {
		Quartet set = new Quartet(helmet, plate, legs, boots);
		setStats.put(set, stats);
		addToListInHashMap(helmet, itemInfoSet, set);
		addToListInHashMap(plate, itemInfoSet, set);
		addToListInHashMap(legs, itemInfoSet, set);
		addToListInHashMap(boots, itemInfoSet, set);
	}
	
	public static void addToListInHashMap(Object key, HashMap map, Object listElement) {
		List list = (List) map.get(key);
		if(list == null) {
			list = new ArrayList();
			map.put(key, list);
		}
		list.add(listElement);
	}
	
	public static void addInfo(ItemStack stack, List desc) {
		if(stack == null || stack.getItem() == null) return;
		
		if(itemInfoSet.containsKey(stack.getItem())) {
			List<Quartet<Item, Item, Item, Item>> sets = itemInfoSet.get(stack.getItem());
			
			for(Quartet<Item, Item, Item, Item> set : sets) {
				
				ResistanceStats stats = setStats.get(set);
				if(stats == null) continue;
				
				List toAdd = new ArrayList();
				
				for(Entry<String, Resistance> entry : stats.categoryResistances.entrySet()) {
					toAdd.add(I18nUtil.resolveKey("damage.category." + entry.getKey()) + ": " + entry.getValue().threshold + "/" + ((int)(entry.getValue().resistance * 100)) + "%");
				}
				for(Entry<String, Resistance> entry : stats.exactResistances.entrySet()) {
					toAdd.add(I18nUtil.resolveKey("damage.exact." + entry.getKey()) + ": " + entry.getValue().threshold + "/" + ((int)(entry.getValue().resistance * 100)) + "%");
				}
				if(stats.otherResistance != null) toAdd.add(I18nUtil.resolveKey("damage.other") + ": " + stats.otherResistance.threshold + "/" + ((int)(stats.otherResistance.resistance * 100)) + "%");
				
				if(!toAdd.isEmpty()) {
					desc.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("damage.inset"));
					//this sucks ass!
					if(set.getW() != null) desc.add(EnumChatFormatting.DARK_PURPLE + "  " + new ItemStack(set.getW()).getDisplayName());
					if(set.getX() != null) desc.add(EnumChatFormatting.DARK_PURPLE + "  " + new ItemStack(set.getX()).getDisplayName());
					if(set.getY() != null) desc.add(EnumChatFormatting.DARK_PURPLE + "  " + new ItemStack(set.getY()).getDisplayName());
					if(set.getZ() != null) desc.add(EnumChatFormatting.DARK_PURPLE + "  " + new ItemStack(set.getZ()).getDisplayName());
					desc.addAll(toAdd);
				}
				
				break; //TEMP, only show one set for now
			}
		}
		
		if(itemStats.containsKey(stack.getItem())) {
			ResistanceStats stats = itemStats.get(stack.getItem());
			
			List toAdd = new ArrayList();
			
			for(Entry<String, Resistance> entry : stats.categoryResistances.entrySet()) {
				toAdd.add(I18nUtil.resolveKey("damage.category." + entry.getKey()) + ": " + entry.getValue().threshold + "/" + ((int)(entry.getValue().resistance * 100)) + "%");
			}
			for(Entry<String, Resistance> entry : stats.exactResistances.entrySet()) {
				toAdd.add(I18nUtil.resolveKey("damage.exact." + entry.getKey()) + ": " + entry.getValue().threshold + "/" + ((int)(entry.getValue().resistance * 100)) + "%");
			}
			if(stats.otherResistance != null) toAdd.add(I18nUtil.resolveKey("damage.other") + ": " + stats.otherResistance.threshold + "/" + ((int)(stats.otherResistance.resistance * 100)) + "%");
			
			if(!toAdd.isEmpty()) {
				desc.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("damage.item"));
				desc.addAll(toAdd);
			}
		}
	}
	
	public static enum DamageClass {
		PHYSICAL,
		FIRE,
		EXPLOSIVE,
		ELECTRIC,
		LASER,
		MICROWAVE,
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
		float dt = vals[0] - currentPDT;
		
		if(dt > 0 && dt >= event.ammount) {
			event.setCanceled(true);
			EntityDamageUtil.damageArmorNT(e, amount);
		}
	}
	
	@SubscribeEvent
	public void onEntityDamaged(LivingHurtEvent event) {
		event.ammount = calculateDamage(event.entityLiving, event.source, event.ammount, currentPDT, currentPDR);
		if(event.entityLiving instanceof IResistanceProvider) {
			IResistanceProvider irp = (IResistanceProvider) event.entityLiving;
			irp.onDamageDealt(event.source, event.ammount);
		}
	}
	
	public static String typeToCategory(DamageSource source) {
		if(source.isExplosion()) return CATEGORY_EXPLOSION;
		if(source.isFireDamage()) return CATEGORY_FIRE;
		if(source.isProjectile()) return CATEGORY_PROJECTILE;
		if(source.damageType.equals(DamageClass.LASER.name())) return CATEGORY_ENERGY;
		if(source.damageType.equals(DamageClass.MICROWAVE.name())) return CATEGORY_ENERGY;
		if(source.damageType.equals(DamageClass.SUBATOMIC.name())) return CATEGORY_ENERGY;
		return source.damageType;
	}
	
	public static float calculateDamage(EntityLivingBase entity, DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isDamageAbsolute()) return amount;
		
		float[] vals = getDTDR(entity, damage, amount, pierceDT, pierce);
		float dt = vals[0];
		float dr = vals[1];
		
		dt = Math.max(0F, dt - pierceDT);
		if(dt >= amount) return 0F;
		amount -= dt;
		dr *= MathHelper.clamp_float(1F - pierce, 0F, 2F /* we allow up to -1 armor piercing, which can double effective armor values */);
		
		return amount *= (1F - dr);
	}
	
	public static float[] getDTDR(EntityLivingBase entity, DamageSource damage, float amount, float pierceDT, float pierce) {
		
		float dt = 0;
		float dr = 0;
		
		if(entity instanceof IResistanceProvider) {
			IResistanceProvider irp = (IResistanceProvider) entity;
			float[] res = irp.getCurrentDTDR(damage, amount, pierceDT, pierce);
			dt += res[0];
			dr += res[1];
		}
		
		/// SET HANDLING ///
		Quartet wornSet = new Quartet(
				entity.getEquipmentInSlot(4) != null ? entity.getEquipmentInSlot(4).getItem() : null,
				entity.getEquipmentInSlot(3) != null ? entity.getEquipmentInSlot(3).getItem() : null,
				entity.getEquipmentInSlot(2) != null ? entity.getEquipmentInSlot(2).getItem() : null,
				entity.getEquipmentInSlot(1) != null ? entity.getEquipmentInSlot(1).getItem() : null
				);
		
		ResistanceStats setResistance = setStats.get(wornSet);
		if(setResistance != null) {
			Resistance res = setResistance.getResistance(damage);
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
			Resistance res = stats.getResistance(damage);
			if(res == null) continue;
			dt += res.threshold;
			dr += res.resistance;
		}

		/// ENTITY CLASS HANDLING ///
		ResistanceStats innateResistance = entityStats.get(entity.getClass());
		if(innateResistance != null) {
			Resistance res = innateResistance.getResistance(damage);
			if(res != null) {
				dt += res.threshold;
				dr += res.resistance;
			}
		}
		
		return new float[] {dt, dr};
	}
	
	public static class ResistanceStats {

		public HashMap<String, Resistance> exactResistances = new HashMap();
		public HashMap<String, Resistance> categoryResistances = new HashMap();
		public Resistance otherResistance;
		
		public Resistance getResistance(DamageSource source) {
			Resistance exact = exactResistances.get(source.damageType);
			if(exact != null) return exact;
			Resistance category = categoryResistances.get(typeToCategory(source));
			if(category != null) return category;
			return source.isUnblockable() ? null : otherResistance;
		}

		public ResistanceStats addExact(String type, float threshold, float resistance) { exactResistances.put(type, new Resistance(threshold, resistance)); return this; }
		public ResistanceStats addCategory(String type, float threshold, float resistance) { categoryResistances.put(type, new Resistance(threshold, resistance)); return this; }
		public ResistanceStats setOther(float threshold, float resistance) { otherResistance = new Resistance(threshold, resistance); return this; }
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
