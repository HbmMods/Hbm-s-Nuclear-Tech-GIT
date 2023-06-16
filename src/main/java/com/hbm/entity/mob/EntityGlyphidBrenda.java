package com.hbm.entity.mob;

import com.hbm.main.ResourceManager;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidBrenda extends EntityGlyphid {

	public EntityGlyphidBrenda(World world) {
		super(world);
		this.setSize(2.5F, 2F);
	}
	
	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_brenda_tex;
	}

	@Override
	public double getScale() {
		return 2D;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(250D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.8D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50D);
	}

	@Override
	public int getArmorBreakChance(float amount) {
		return amount < 25 ? 100 : amount > 500 ? 1 : 10;
	}

	@Override
	public float calculateDamage(float amount) {

		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		int divisor = 1;
		
		for(int i = 0; i < 5; i++) {
			if((armor & (1 << i)) > 0) {
				divisor += 5;
			}
		}
		
		amount /= divisor;
		
		return amount;
	}

	@Override
	public float getDamageThreshold() {
		return 10F;
	}
}
