package com.hbm.entity.mob;

import com.hbm.main.ResourceManager;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidBrenda extends EntityGlyphid {

	public EntityGlyphidBrenda(World world) {
		super(world);
		this.setSize(2.5F, 1.75F);
		this.isImmuneToFire = true;
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
		return amount < 25 ? 100 : amount > 1000 ? 1 : 10;
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

	@Override
	public void setDead() {
		if(!this.worldObj.isRemote && this.getHealth() <= 0.0F) {
			for(int i = 0; i < 12; ++i) {
				EntityGlyphid glyphid = new EntityGlyphid(worldObj);
				glyphid.setLocationAndAngles(this.posX, this.posY + 0.5D, this.posZ, rand.nextFloat() * 360.0F, 0.0F);
				glyphid.addPotionEffect(new PotionEffect(Potion.resistance.id, 5 * 60 * 20, 2));
				glyphid.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 5 * 60 * 20, 0));
				glyphid.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 5 * 60 * 20, 4));
				glyphid.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 5 * 60 * 20, 19));
				this.worldObj.spawnEntityInWorld(glyphid);
				glyphid.moveEntity(rand.nextGaussian(), 0, rand.nextGaussian());
			}
		}

		super.setDead();
	}
}
