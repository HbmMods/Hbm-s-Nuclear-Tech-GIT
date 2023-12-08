package com.hbm.entity.mob;

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
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10D);
	}

	@Override
	public boolean isArmorBroken(float amount) {
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.25, 2), 100);
	}

	@Override
	public float calculateDamage(float amount) {

		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		float divisor = 1;
		
		for(int i = 0; i < 5; i++) {
			if((armor & (1 << i)) > 0) {
				divisor += 3;
			}
		}
		
		amount /= divisor;
		
		return amount;
	}

	@Override
	public float getDamageThreshold() {
		return 1.0F;
	}
}
