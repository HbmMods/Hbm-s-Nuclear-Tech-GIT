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
		public final float divisor;
		public final float damageThreshold;
		
		public StatBundle(double health, double speed, double damage, float divisor, float damageThreshold) {
			this.health = health;
			this.speed = speed;
			this.damage = damage;
			this.divisor = divisor;
			this.damageThreshold = damageThreshold;
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

					if(amount < glyphid.getDamageThreshold()) return false;

					 //chances of armor being broken off
					if(amount > 1 && glyphid.isArmorBroken(amount)) {
						glyphid.breakOffArmor();
						amount *= 0.25F;
					}

					amount -= glyphid.getDamageThreshold();
					if(amount < 0) return true;
				}

				amount = glyphid.calculateDamage(amount);
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
			this.statsGrunt =		new StatBundle(20D,		1D,		2D,		0.25F,	0F);
			this.statsBombardier =	new StatBundle(15D,		1D,		2D,		0.25F,	0F);
			this.statsBrawler =		new StatBundle(35D,		1D,		10D,	0.5F,	0.5F);
			this.statsDigger =		new StatBundle(50D,		1D,		10D,	0.5F,	0.5F);
			this.statsBlaster =		new StatBundle(35D,		1D,		10D,	0.5F,	0.5F);
			this.statsBehemoth =	new StatBundle(125D,	0.8D,	25D,	1.5F,	2F);
			this.statsBrenda =		new StatBundle(250D,	1.2D,	50D,	2.5F,	5F);
			this.statsNuclear =		new StatBundle(100D,	0.8D,	50D,	2.5F,	5F);
			this.statsScout =		new StatBundle(20D,		1.5D,	5D,		0.5F,	0F);
		}

		@Override
		public boolean handleAttack(EntityGlyphid glyphid, DamageSource source, float amount) {
			
			// Completely immune to acid from other glyphids
			if((source == ModDamageSource.acid || ModDamageSource.s_acid.equals(source.getDamageType())) && source.getSourceOfDamage() instanceof EntityGlyphid) return false;
			
			// If damage is armor piercing or nuclear damage, don't apply any armor calculation
			if(isNuclearDamage(source) || source.isDamageAbsolute() || source.isUnblockable()) {
				if(source == DamageSource.inWall) amount *= 15F;
				return glyphid.attackSuperclass(source, amount);
				// This ensures that nukes will remain hyper-effective
			}
			
			// If damage is fire damage, reduce damage above 5 then ignore armor
			if(source.isFireDamage()) {
				float dmg = Math.min(amount, 5F);
				if(amount > 5) dmg += (amount - 5F) * 0.1F;
				return glyphid.attackSuperclass(source, dmg);
				// This ensures that afterburn and flamethrowers remain effective wihin reason
			}
			
			// If damage is explosive, reduce by 25% then ignore armor
			if(source.isExplosion()) {
				amount *= 0.5F;
				return glyphid.attackSuperclass(source, amount);
				// This ensures that explosions remain mostly effective
			}

			byte armor = glyphid.getDataWatcher().getWatchableObjectByte(glyphid.DW_ARMOR);
			amount -= glyphid.getDamageThreshold();
			if(amount < 0) return armor == 0; // if armor is present, knockback from 0 damage attacks is negated
			
			if(armor != 0) {
				if(glyphid.isArmorBroken(amount)) {
					glyphid.breakOffArmor();
					amount *= 0.5F;
				}
				
				amount = glyphid.calculateDamage((float) Math.min(amount, Math.sqrt(amount) * 50D / 7D));
				// This ensures that higher numbers have a diminishing effect
			}
			
			return glyphid.attackSuperclass(source, amount);
		}
		
		public boolean isNuclearDamage(DamageSource source) {
			return source == ModDamageSource.nuclearBlast || source == ModDamageSource.radiation;
		}
	}
}
