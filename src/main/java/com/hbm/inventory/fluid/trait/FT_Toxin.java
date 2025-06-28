package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.i18n.I18nUtil;

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
			info.add(EnumChatFormatting.YELLOW + "- " + I18nUtil.resolveKey(clazz.lang) + (fullBody ? EnumChatFormatting.RED + " (requires hazmat suit)" : "") + ": " + EnumChatFormatting.YELLOW + String.format(Locale.US, "%,.1f", amount * 20 / delay) + " DPS");
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
	
	@Override public void serializeJSON(JsonWriter writer) throws IOException {
		
		writer.name("entries").beginArray();
		
		for(ToxinEntry entry : entries) {
			writer.beginObject();

			if(entry instanceof ToxinDirectDamage) {
				ToxinDirectDamage e = (ToxinDirectDamage) entry;
				writer.name("type").value("directdamage");
				writer.name("amount").value(e.amount);
				writer.name("source").value(e.damage.damageType);
				writer.name("delay").value(e.delay);
				writer.name("hazmat").value(e.fullBody);
				writer.name("masktype").value(e.clazz.name());
			}
			if(entry instanceof ToxinEffects) {
				ToxinEffects e = (ToxinEffects) entry;
				writer.name("type").value("effects");
				writer.name("effects").beginArray();
				writer.setIndent("");
				for(PotionEffect effect : e.effects) {
					writer.beginArray();
					writer.value(effect.getPotionID()).value(effect.getDuration()).value(effect.getAmplifier()).value(effect.getIsAmbient());
					writer.endArray();
				}
				writer.endArray();
				writer.setIndent("  ");
				writer.name("hazmat").value(e.fullBody);
				writer.name("masktype").value(e.clazz.name());
			}
			
			writer.endObject();
		}
		
		writer.endArray();
	}
	
	@Override public void deserializeJSON(JsonObject obj) {
		JsonArray array = obj.get("entries").getAsJsonArray();
		
		for(int i = 0; i < array.size(); i++) {
			JsonObject entry = array.get(i).getAsJsonObject();
			String name = entry.get("type").getAsString();
			
			if(name.equals("directdamage")) {
				ToxinDirectDamage e = new ToxinDirectDamage(
						new DamageSource(entry.get("source").getAsString()),
						entry.get("amount").getAsFloat(),
						entry.get("delay").getAsInt(),
						HazardClass.valueOf(entry.get("masktype").getAsString()),
						entry.get("hazmat").getAsBoolean()
						);
				this.entries.add(e);
			}
			
			if(name.equals("effects")) {
				ToxinEffects e = new ToxinEffects(
						HazardClass.valueOf(entry.get("masktype").getAsString()),
						entry.get("hazmat").getAsBoolean()
						);
				JsonArray effects = entry.get("effects").getAsJsonArray();
				for(int j = 0; j < effects.size(); j++) {
					JsonArray effect = effects.get(j).getAsJsonArray();
					PotionEffect potion = new PotionEffect(effect.get(0).getAsInt(), effect.get(1).getAsInt(), effect.get(2).getAsInt(), effect.get(3).getAsBoolean());
					e.effects.add(potion);
				}
				this.entries.add(e);
			}
		}
	}
}
