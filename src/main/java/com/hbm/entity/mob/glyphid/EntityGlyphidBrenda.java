package com.hbm.entity.mob.glyphid;

import com.hbm.entity.effect.EntityMist;
import com.hbm.entity.mob.glyphid.GlyphidStats.StatBundle;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
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
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(GlyphidStats.getStats().getBrenda().health);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(GlyphidStats.getStats().getBrenda().speed);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(GlyphidStats.getStats().getBrenda().damage);
	}
	
	public StatBundle getStats() {
		return GlyphidStats.getStats().statsBrenda;
	}

	@Override
	public boolean isArmorBroken(float amount) {
		// amount < 5 ? 5 : amount < 10 ? 3 : 2;
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.12, 2), 100);
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		if(!this.worldObj.isRemote && this.getHealth() <= 0.0F) {
			EntityMist mist = new EntityMist(worldObj);
			mist.setType(Fluids.PHEROMONE);
			mist.setPosition(posX, posY, posZ);
			mist.setArea(14, 6);
			mist.setDuration(80);
			worldObj.spawnEntityInWorld(mist);
			for(int i = 0; i < 12; ++i) {
				EntityGlyphid glyphid = new EntityGlyphid(worldObj);
				glyphid.setLocationAndAngles(this.posX, this.posY + 0.5D, this.posZ, rand.nextFloat() * 360.0F, 0.0F);
				this.worldObj.spawnEntityInWorld(glyphid);
				glyphid.moveEntity(rand.nextGaussian(), 0, rand.nextGaussian());
			}
		}
	}
	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		super.dropFewItems(byPlayer, looting);
		if(rand.nextInt(3) == 0) this.entityDropItem(new ItemStack(ModItems.glyphid_gland, 1, Fluids.PHEROMONE.getID()), 1);
	}

}
