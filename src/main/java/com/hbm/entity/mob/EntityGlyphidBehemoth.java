package com.hbm.entity.mob;

import com.hbm.entity.effect.EntityMist;
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
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(130D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.8D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(25D);
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
	public boolean attackEntityAsMob(Entity victum) {
		return super.attackEntityAsMob(victum);
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		if (!worldObj.isRemote) {
			EntityMist mist = new EntityMist(worldObj);
			mist.setType(Fluids.ACID);
			mist.setPosition(posX, posY, posZ);
			mist.setArea(10, 4);
			mist.setDuration(120);
			worldObj.spawnEntityInWorld(mist);
		}
	}


	public void acidAttack(){
		if(!worldObj.isRemote && entityToAttack instanceof EntityLivingBase && this.getDistanceToEntity(entityToAttack) < 20) {
			this.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 20, 6));
			EntityChemical chem = new EntityChemical(worldObj, this);

			chem.setFluid(Fluids.ACID);
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
	@Override
	public float calculateDamage(float amount) {

		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		int divisor = 1;
		
		for(int i = 0; i < 5; i++) {
			if((armor & (1 << i)) > 0) {
				divisor += 4;
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
