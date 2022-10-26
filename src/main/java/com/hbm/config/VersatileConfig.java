package com.hbm.config;

import java.util.ArrayList;

import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;

public class VersatileConfig {
	
	public static Item getTransmutatorItem() {
		
		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMFullSchrab)
			return ModItems.ingot_schrabidium;

		return ModItems.ingot_schraranium;
	}
	
	public static int getSchrabOreChance() {
		
		if(GeneralConfig.enableLBSM)
			return GeneralConfig.schrabRate;
		
		return 100;
	}

	public static void applyPotionSickness(EntityLivingBase entity, int duration) {
		
		if(PotionConfig.potionSickness == 0)
			return;
		
		if(PotionConfig.potionSickness == 2)
			duration *= 12;
		
		PotionEffect eff = new PotionEffect(HbmPotion.potionsickness.id, duration * 20);
		eff.setCurativeItems(new ArrayList());
		entity.addPotionEffect(eff);
	}

	public static boolean hasPotionSickness(EntityLivingBase entity) {
		return entity.isPotionActive(HbmPotion.potionsickness);
	}
	
	public static boolean rtgDecay() {
		return GeneralConfig.enable528 || MachineConfig.doRTGsDecay;
	}
	
	public static boolean scaleRTGPower() {
		return GeneralConfig.enable528 || MachineConfig.scaleRTGPower;
	}

	static int minute = 60 * 20;
	static int hour = 60 * minute;
	
	public static int getLongDecayChance() {
		return GeneralConfig.enable528 ? 15 * hour : (GeneralConfig.enableLBSM && GeneralConfig.enableLBSMShorterDecay) ? 15 * minute : 3 * hour;
	}

	public static int getShortDecayChance() {
		return GeneralConfig.enable528 ? 3 * hour : (GeneralConfig.enableLBSM && GeneralConfig.enableLBSMShorterDecay) ? 3 * minute : 15 * minute;
	}
}
