package com.hbm.entity.mob;

import com.hbm.main.ResourceManager;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidBehemoth extends EntityGlyphid {

	public EntityGlyphidBehemoth(World world) {
		super(world);
		this.setSize(2.25F, 1.25F);
	}
	
	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_behemoth_tex;
	}

	@Override
	public double getScale() {
		return 1.5D;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.8D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(25D);
	}

	@Override
	public int getArmorBreakChance(float amount) {
		return amount < 20 ? 10 : amount < 100 ? 5 : amount > 200 ? 1 : 3;
	}

	@Override
	public float calculateDamage(float amount) {

		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		int divisor = 1;
		
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
		return 2.5F;
	}
}
