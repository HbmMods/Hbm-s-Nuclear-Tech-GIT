package com.hbm.entity.mob;

import java.util.List;

import com.hbm.entity.mob.ai.EntityAIMaskmanCasualApproach;
import com.hbm.entity.mob.ai.EntityAIMaskmanLasergun;
import com.hbm.entity.mob.ai.EntityAIMaskmanMinigun;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.ArmorUtil;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

public class EntityMaskMan extends EntityMob implements IBossDisplayData, IRadiationImmune {

	public EntityMaskMan(World world) {
		super(world);
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIMaskmanCasualApproach(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(2, new EntityAIMaskmanMinigun(this, true, true, 3));
		this.tasks.addTask(3, new EntityAIMaskmanLasergun(this, true, true));
		this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

		this.setSize(2F, 5F);
		this.isImmuneToFire = true;
		this.experienceValue = 100;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000.0D);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(source instanceof EntityDamageSourceIndirect && ((EntityDamageSourceIndirect) source).getSourceOfDamage() instanceof EntityEgg && rand.nextInt(10) == 0) {
			this.experienceValue = 0;
			this.setHealth(0);
			return true;
		}

		if(source.isFireDamage())
			amount = 0;
		if(source.isMagicDamage())
			amount = 0;
		if(source.isProjectile())
			amount *= 0.25F;
		if(source.isExplosion())
			amount *= 0.5F;

		if(amount > 50) {
			amount = 50 + (amount - 50) * 0.25F;
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(this.prevHealth >= this.getMaxHealth() / 2 && this.getHealth() < this.getMaxHealth() / 2 && this.isEntityAlive()) {

			prevHealth = this.getHealth();

			if(!worldObj.isRemote)
				worldObj.createExplosion(this, posX, posY + 4, posZ, 2.5F, true);
		}
	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50, 50, 50));

		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.bossMaskman);
		}
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void dropFewItems(boolean bool, int i) {

		if(!worldObj.isRemote) {
			
			ItemStack mask = new ItemStack(ModItems.gas_mask_m65);
			ArmorUtil.installGasMaskFilter(mask, new ItemStack(ModItems.gas_mask_filter_combo));
			
			this.entityDropItem(mask, 0F);
			this.dropItem(ModItems.coin_maskman, 1);
			this.dropItem(ModItems.bottled_cloud, 1);
			this.dropItem(Items.skull, 1);
		}
	}
}
