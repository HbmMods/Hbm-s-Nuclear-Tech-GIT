package com.hbm.entity.mob;

import java.util.List;

import com.hbm.entity.logic.EntitySound;
import com.hbm.items.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDoner extends EntityCreature implements IAnimals {


	public EntityDoner(World world) {
		super(world);
		this.setSize(0.6F, 1.8F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 15.0F));
        
		
		this.renderDistanceWeight *= 10;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.isEntityAlive() && this.getHealth() < this.getMaxHealth() && this.ticksExisted % 10 == 0) {
			this.heal(1.0F);
		}
	}

	// i wasted 4 fucking hours, costed my sleep, and fucking everything else when i could have just done this
	// wack me.
    @Override
    public boolean interact(EntityPlayer player) {
        ItemStack heldItem = player.getHeldItem();

        if (heldItem != null && heldItem.getItem() == ModItems.ingot_money) {
            if (!this.worldObj.isRemote) { 
                heldItem.stackSize--; 

                if (heldItem.stackSize <= 0) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(ModItems.ingot_money));
                } else {
                    player.inventory.addItemStackToInventory(new ItemStack(ModItems.doner));
                }
            }

            return true;
        }

        return false;

    }
	@Override
	public void setHealth(float health) {
		super.setHealth(health);
	}
	
	@Override
	public boolean isEntityInvulnerable() {
		return false;
	}
  
    protected boolean canDespawn() {
        return false;
    }
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);
	}
	protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_) {
		super.damageEntity(p_70665_1_, p_70665_2_);
	}

}
