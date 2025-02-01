package com.hbm.entity.mob.glyphid;

import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.util.DamageSource;

public abstract class GlyphidStats {

	public static GlyphidStats GLYPHID_STATS_70K = new GlyphidStats70K();
	public static GlyphidStats GLYPHID_STATS_NT = new GlyphidStatsNT();
	
	public static GlyphidStats getStats() {
		return GLYPHID_STATS_NT;
	}

	protected StatBundle statsGrunt;
	protected StatBundle statsBombardier;
	protected StatBundle statsBrawler;
	protected StatBundle statsDigger;
	protected StatBundle statsBlaster;
	protected StatBundle statsBehemoth;
	protected StatBundle statsBrenda;
	protected StatBundle statsNuclear;
	protected StatBundle statsScout;
	
	public static class StatBundle {
		public final double health;
		public final double speed;
		public final double damage;
		@Deprecated public final float divisor;
		@Deprecated public final float damageThreshold;
		/** Base threshold is calculated using this number * the glyphid's armor */
		public final float thresholdMultForArmor;
		public final float resistanceMult;
		
		public StatBundle(double health, double speed, double damage, float divisor, float damageThreshold) {
			this(health, speed, damage, divisor, damageThreshold, 0F, 0F);
		}
		
		public StatBundle(double health, double speed, double damage, float divisor, float damageThreshold, float thresholdMultPerArmor, float resistanceMult) {
			this.health = health;
			this.speed = speed;
			this.damage = damage;
			this.divisor = divisor;
			this.damageThreshold = damageThreshold;
			this.thresholdMultForArmor = thresholdMultPerArmor;
			this.resistanceMult = resistanceMult;
		}
	}
	
	public abstract boolean handleAttack(EntityGlyphid glyphid, DamageSource source, float amount);

	/** Tier 1 */				public StatBundle getGrunt() { return statsGrunt; }
	/** Tier 1 Ranged */		public StatBundle getBombardier() { return statsBombardier; }
	/** Tier 2 */				public StatBundle getBrawler() { return statsBrawler; }
	/** Tier 2 Specialist */	public StatBundle getDigger() { return statsDigger; }
	/** Tier 2 Ranged */		public StatBundle getBlaster() { return statsBlaster; }
	/** Tier 3 */				public StatBundle getBehemoth() { return statsBehemoth; }
	/** Tier 4 */				public StatBundle getBrenda() { return statsBrenda; }
	/** Tier 4 Specialist */	public StatBundle getNuclear() { return statsNuclear; }
	/** Tier 0 */				public StatBundle getScout() { return statsScout; }
	
	public static class GlyphidStats70K extends GlyphidStats {
		
		public GlyphidStats70K() {
			this.statsGrunt =		new StatBundle(30D,		1D,		5D,		1F,		0.5F);
			this.statsBombardier =	new StatBundle(20D,		1D,		5D,		1F,		0.5F);
			this.statsBrawler =		new StatBundle(50D,		1D,		10D,	3F,		1F);
			this.statsDigger =		new StatBundle(50D,		1D,		5D,		1F,		0.5F);
			this.statsBlaster =		new StatBundle(50D,		1D,		10D,	2F,		1F);
			this.statsBehemoth =	new StatBundle(130D,	0.8D,	25D,	4F,		2.5F);
			this.statsBrenda =		new StatBundle(250D,	1.2D,	50D,	5F,		10F);
			this.statsNuclear =		new StatBundle(100D,	0.8D,	50D,	5F,		10F);
			this.statsScout =		new StatBundle(20D,		1.5D,	2D,		1F,		0.5F);
		}

		@Override
		public boolean handleAttack(EntityGlyphid glyphid, DamageSource source, float amount) {

			if(!source.isDamageAbsolute() && !source.isUnblockable() && !glyphid.worldObj.isRemote && !source.isFireDamage() && !source.getDamageType().equals(ModDamageSource.s_cryolator)) {
				byte armor = glyphid.getDataWatcher().getWatchableObjectByte(glyphid.DW_ARMOR);

				if(armor != 0) { //if at least one bit of armor is present

					if(amount < glyphid.getStats().damageThreshold) return false;

					 //chances of armor being broken off
					if(amount > 1 && glyphid.isArmorBroken(amount)) {
						glyphid.breakOffArmor();
						amount *= 0.25F;
					}

					amount -= glyphid.getStats().damageThreshold;
					if(amount < 0) return true;
				}

				//amount = glyphid.calculateDamage(amount);
			}

			if(source.isFireDamage()) {
				amount *= 0.7F;
			} else if(source.getDamageType().equals("player")) {
				amount *= glyphid.getScale() < 1.25 ? 1.5 : glyphid.getScale() < 1.3 ? 0.8 : 0.5;
			} else if(source == ModDamageSource.acid || ModDamageSource.s_acid.equals(source.getDamageType())) {
				amount = 0;
			} else if(source == DamageSource.inWall) {
				amount *= 15F;
			}

			if(glyphid.isPotionActive(HbmPotion.phosphorus.getId())){
				amount *= 1.5F;
			}

			return glyphid.attackSuperclass(source, amount);
		}
	}
	
	/** UNTESTED! Spreadsheet will be consulted soon */
	public static class GlyphidStatsNT extends GlyphidStats {
		
		public GlyphidStatsNT() {
			this.statsGrunt =		new StatBundle(20D,		1D,		2D,		0.25F,	0F,		1F,		0.1F);
			this.statsBombardier =	new StatBundle(15D,		1D,		2D,		0.25F,	0F,		1F,		0.1F);
			this.statsBrawler =		new StatBundle(35D,		1D,		10D,	0.5F,	0.5F,	2F,		0.15F);
			this.statsDigger =		new StatBundle(50D,		1D,		10D,	0.5F,	0.5F,	3F,		0.20F);
			this.statsBlaster =		new StatBundle(35D,		1D,		10D,	0.5F,	0.5F,	2F,		0.15F);
			this.statsBehemoth =	new StatBundle(125D,	0.8D,	25D,	1.5F,	2F,		5F,		0.35F);
			this.statsBrenda =		new StatBundle(250D,	1.2D,	50D,	2.5F,	5F,		10F,	0.5F);
			this.statsNuclear =		new StatBundle(100D,	0.8D,	50D,	2.5F,	5F,		10F,	0.5F);
			this.statsScout =		new StatBundle(20D,		1.5D,	5D,		0.5F,	0F,		0.5F,	0.5F);
		}

		@Override
		public boolean handleAttack(EntityGlyphid glyphid, DamageSource source, float amount) {
			// Completely immune to acid from other glyphids
			if((source == ModDamageSource.acid || ModDamageSource.s_acid.equals(source.getDamageType())) && source.getSourceOfDamage() instanceof EntityGlyphid) return false;
			return glyphid.attackSuperclass(source, amount);
		}
	}
}
