package com.hbm.entity.mob.glyphid;

import com.hbm.entity.effect.EntityMist;
import com.hbm.entity.mob.glyphid.GlyphidStats.StatBundle;
import com.hbm.entity.projectile.EntityChemical;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidBehemoth extends EntityGlyphid {

	public EntityGlyphidBehemoth(World world) {
		super(world);
		this.setSize(2.5F, 1.5F);
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
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(GlyphidStats.getStats().getBehemoth().health);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(GlyphidStats.getStats().getBehemoth().speed);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(GlyphidStats.getStats().getBehemoth().damage);
	}
	
	public StatBundle getStats() {
		return GlyphidStats.getStats().statsBehemoth;
	}
	
	public int timer = 120;
	int breathTime = 0;

	@Override
	public void onUpdate(){
		super.onUpdate();
		Entity e = this.getEntityToAttack();
		if (e == null) {
			timer = 120;
			breathTime = 0;
		} else {
			if (breathTime > 0) {
				if(!isSwingInProgress){
					this.swingItem();
				}
				acidAttack();
				rotationYaw = prevRotationYaw;
				breathTime--;
			} else if (--timer <= 0) {
				breathTime = 120;
				timer = 120;
			}
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		if (!worldObj.isRemote) {
			EntityMist mist = new EntityMist(worldObj);
			mist.setType(Fluids.SULFURIC_ACID);
			mist.setPosition(posX, posY, posZ);
			mist.setArea(10, 4);
			mist.setDuration(120);
			worldObj.spawnEntityInWorld(mist);
		}
	}


	public void acidAttack(){
		if(!worldObj.isRemote && entityToAttack instanceof EntityLivingBase && this.getDistanceToEntity(entityToAttack) < 20) {
			this.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 20, 6));
			EntityChemical chem = new EntityChemical(worldObj, this, 0, 0, 0);

			chem.setFluid(Fluids.SULFURIC_ACID);
			worldObj.spawnEntityInWorld(chem);
		}
	}

	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		this.entityDropItem(new ItemStack(ModItems.glyphid_gland, 1, Fluids.SULFURIC_ACID.getID()), 1);
		super.dropFewItems(byPlayer, looting);
	}
	@Override
	public boolean isArmorBroken(float amount) {
		// amount < 5 ? 5 : amount < 10 ? 3 : 2;
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.15, 2), 100);
	}
	@Override
	public int swingDuration() {
		return 100;
	}
}
