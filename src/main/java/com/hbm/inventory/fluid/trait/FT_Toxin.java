package com.hbm.inventory.fluid.trait;

import java.util.ArrayList;
import java.util.List;

import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;

public class FT_Toxin extends FluidTrait {
	
	public List<ToxinEntry> entries = new ArrayList();
	
	public FT_Toxin addEntry(ToxinEntry entry) {
		entries.add(entry);
		return this;
	}
	
	@Override
	public void addInfoHidden(List<String> info) {
		info.add(EnumChatFormatting.LIGHT_PURPLE + "[Toxin]");
		
		for(ToxinEntry entry : entries) {
			entry.addInfo(info);
		}
	}
	
	public void affect(EntityLivingBase entity, double intensity) {
		
		for(ToxinEntry entry : entries) {
			entry.poison(entity, intensity);
		}
	}

	public static abstract class ToxinEntry {
		
		public HazardClass clazz;
		public boolean fullBody = false;
		
		public ToxinEntry(HazardClass clazz, boolean fullBody) {
			this.clazz = clazz;
			this.fullBody = fullBody;
		}
		
		public boolean isProtected(EntityLivingBase entity) {
			
			boolean hasMask = clazz == null;
			boolean hasSuit = !fullBody;
			
			if(clazz != null && ArmorRegistry.hasAllProtection(entity, 3, clazz)) {
				ArmorUtil.damageGasMaskFilter(entity, 1);
				hasMask = true;
			}
			
			if(fullBody && ArmorUtil.checkForHazmat(entity)) {
				hasSuit = true;
			}
			
			return hasMask && hasSuit;
		}

		public abstract void poison(EntityLivingBase entity, double intensity);
		public abstract void addInfo(List<String> info);
	}

	public static class ToxinDirectDamage extends ToxinEntry {

		public DamageSource damage;
		public float amount;
		public int delay;
		
		public ToxinDirectDamage(DamageSource damage, float amount, int delay, HazardClass clazz, boolean fullBody) {
			super(clazz, fullBody);
			this.damage = damage;
			this.amount = amount;
			this.delay = delay;
		}

		@Override
		public void poison(EntityLivingBase entity, double intensity) {
			
			if(isProtected(entity)) return;
			
			if(delay == 0 || entity.worldObj.getTotalWorldTime() % delay == 0) {
				entity.attackEntityFrom(damage, (float) (amount * intensity));
			}
		}

		@Override
		public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.YELLOW + "- " + I18nUtil.resolveKey(clazz.lang) + (fullBody ? EnumChatFormatting.RED + " (requires hazmat suit)" : "") + ": " + EnumChatFormatting.YELLOW + String.format("%,.1f", amount * 20 / delay) + " DPS");
		}
	}

	public static class ToxinEffects extends ToxinEntry {

		public List<PotionEffect> effects = new ArrayList();
		
		public ToxinEffects(HazardClass clazz, boolean fullBody) {
			super(clazz, fullBody);
		}
		
		public ToxinEffects add(PotionEffect... effs) {
			for(PotionEffect eff : effs) this.effects.add(eff);
			return this;
		}

		@Override
		public void poison(EntityLivingBase entity, double intensity) {
			
			if(isProtected(entity)) return;
			
			for(PotionEffect eff : effects) {
				entity.addPotionEffect(new PotionEffect(eff.getPotionID(), (int) (eff.getDuration() * intensity), eff.getAmplifier()));
			}
		}

		@Override
		public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.YELLOW + "- " + I18nUtil.resolveKey(clazz.lang) + (fullBody ? EnumChatFormatting.RED + " (requires hazmat suit)" + EnumChatFormatting.YELLOW : "") + ":");
			
			for(PotionEffect eff : effects) {
				info.add(EnumChatFormatting.YELLOW + "   - " + I18nUtil.resolveKey(eff.getEffectName()) + (eff.getAmplifier() > 0 ? " " + StatCollector.translateToLocal("potion.potency." + eff.getAmplifier()).trim() : "") + " " + StringUtils.ticksToElapsedTime(eff.getDuration()));
			}
		}
	}
}
