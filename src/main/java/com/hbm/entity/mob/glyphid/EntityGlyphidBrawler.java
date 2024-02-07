package com.hbm.entity.mob.glyphid;

import com.hbm.main.ResourceManager;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidBrawler extends EntityGlyphid {

	public EntityGlyphidBrawler(World world) {
		super(world);
		this.setSize(2F, 1.125F);
	}
	
	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_brawler_tex;
	}

	@Override
	public double getScale() {
		return 1.25D;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(GlyphidStats.getStats().getBrawler().health);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(GlyphidStats.getStats().getBrawler().speed);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(GlyphidStats.getStats().getBrawler().damage);
	}

	@Override public float getDivisorPerArmorPoint() { return GlyphidStats.getStats().getBrawler().divisor; }
	@Override public float getDamageThreshold() { return GlyphidStats.getStats().getBrawler().damageThreshold; }

	@Override
	public boolean isArmorBroken(float amount) {
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.25, 2), 100);
	}
}
