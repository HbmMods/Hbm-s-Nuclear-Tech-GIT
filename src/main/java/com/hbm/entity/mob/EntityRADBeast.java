package com.hbm.entity.mob;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.RadiationSavedData;

import api.hbm.entity.IRadiationImmune;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRADBeast extends EntityMob implements IRadiationImmune {

	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;

	public EntityRADBeast(World world) {
		super(world);
		this.isImmuneToFire = true;
		this.experienceValue = 30;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(16.0D);
	}

	public EntityRADBeast makeLeader() {
		this.setEquipmentDropChance(0, 1F);
		this.setCurrentItemOrArmor(0, new ItemStack(ModItems.coin_radiation));
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(360.0D);
		this.heal(this.getMaxHealth());
		return this;
	}

	protected boolean canDespawn() {
		return false;
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, (int) 0);
	}

	protected String getLivingSound() {
		return "hbm:item.geiger" + (1 + rand.nextInt(6));
	}

	protected String getHurtSound() {
		return "mob.blaze.hit";
	}

	protected String getDeathSound() {
		return "hbm:step.iron";
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {
		return 15728880;
	}

	public float getBrightness(float f) {
		return 1.0F;
	}

	public int getTotalArmorValue() {
		return 8;
	}

	public void onLivingUpdate() {

		if(!this.worldObj.isRemote) {

			if(this.isWet()) {
				this.attackEntityFrom(DamageSource.drown, 1.0F);
			}

			--this.heightOffsetUpdateTime;

			if(this.heightOffsetUpdateTime <= 0) {
				this.heightOffsetUpdateTime = 100;
				this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
			}

			if(this.getEntityToAttack() != null && this.getEntityToAttack().posY + (double) this.getEntityToAttack().getEyeHeight() > this.posY + (double) this.getEyeHeight() + (double) this.heightOffset) {
				this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
			}

			if(this.entityToAttack != null && attackTime < 10) {

				if(this.dataWatcher.getWatchableObjectInt(16) != entityToAttack.getEntityId())
					this.dataWatcher.updateObject(16, entityToAttack.getEntityId());
			} else {
				this.dataWatcher.updateObject(16, 0);
			}
		}

		if(!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.6D;
		}

		if(this.getMaxHealth() <= 150) {

			for(int i = 0; i < 6; i++) {
				this.worldObj.spawnParticle("townaura", this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width * 1.5, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width * 1.5, 0.0D, 0.0D, 0.0D);
			}

			if(this.rand.nextInt(6) == 0) {

				this.worldObj.spawnParticle("flame", this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height * 0.75, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
			}

		} else {
			this.worldObj.spawnParticle("lava", this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height * 0.75, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
		}

		super.onLivingUpdate();
	}

	protected void attackEntity(Entity target, float dist) {

		if(this.attackTime <= 0 && dist < 2.0F && target.boundingBox.maxY > this.boundingBox.minY && target.boundingBox.minY < this.boundingBox.maxY) {
			this.attackTime = 20;
			this.attackEntityAsMob(target);

		} else if(dist < 30.0F) {

			double deltaX = target.posX - this.posX;
			double deltaZ = target.posZ - this.posZ;

			if(this.attackTime == 0 && getEntityToAttack() != null) {

				RadiationSavedData.incrementRad(worldObj, (int) posX, (int) posZ, 150, 1000);
				target.attackEntityFrom(ModDamageSource.radiation, 16.0F);
				this.swingItem();
				this.playLivingSound();
				this.attackTime = 20;
			}

			this.rotationYaw = (float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) - 90.0F;
			this.hasAttacked = true;
		}
	}

	public Entity getUnfortunateSoul() {

		int id = this.dataWatcher.getWatchableObjectInt(16);
		return worldObj.getEntityByID(id);
	}

	protected void fall(float p_70069_1_) {
	}

	protected Item getDropItem() {
		return ModItems.rod_uranium_fuel_depleted;
	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		if(this.getMaxHealth() > 150) {
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50, 50, 50));

			for(EntityPlayer player : players) {
				player.triggerAchievement(MainRegistry.bossMeltdown);
			}
		}
	}

	protected void dropFewItems(boolean beenHit, int looting) {

		if(beenHit) {

			if(looting > 0) {
				this.dropItem(ModItems.nugget_polonium, looting);
			}

			int count = this.rand.nextInt(3) + 1;

			for(int i = 0; i < count; i++) {

				int r = this.rand.nextInt(3);

				if(r == 0) {
					this.dropItem(this.isWet() ? ModItems.waste_uranium : ModItems.rod_uranium_fuel_depleted, 1);

				} else if(r == 1) {
					this.dropItem(this.isWet() ? ModItems.waste_mox : ModItems.rod_mox_fuel_depleted, 1);

				} else if(r == 2) {
					this.dropItem(this.isWet() ? ModItems.waste_plutonium : ModItems.rod_plutonium_fuel_depleted, 1);

				}
			}
		}
	}
}
