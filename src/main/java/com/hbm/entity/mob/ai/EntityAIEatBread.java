package com.hbm.entity.mob.ai;

import java.util.List;

import com.hbm.entity.mob.EntityPigeon;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class EntityAIEatBread extends EntityAIBase {
	
	private EntityPigeon pigeon;
	private double speed;
	private EntityItem item;
	
	public EntityAIEatBread(EntityPigeon pigeon, double speed) {
		this.pigeon = pigeon;
		this.speed = speed;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		if(pigeon.isFat() || pigeon.getFlyingState() != pigeon.STATE_WALKING) return false;
		
		List<EntityItem> items = pigeon.worldObj.getEntitiesWithinAABB(EntityItem.class, this.pigeon.boundingBox.expand(10, 10, 10));
		
		for(EntityItem item : items) {
			if(item.getEntityItem().getItem() == Items.bread) {
				this.item = item;
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean continueExecuting() {
		return this.item != null && !this.item.isDead && this.shouldExecute();
	}
	
	@Override
	public void updateTask() {
		this.pigeon.getLookHelper().setLookPositionWithEntity(this.item, 30.0F, (float) this.pigeon.getVerticalFaceSpeed());
		
		if(this.pigeon.getDistanceToEntity(this.item) > 1) {
			this.pigeon.getNavigator().tryMoveToEntityLiving(this.item, this.speed);
		} else {
			
			if(this.pigeon.getRNG().nextInt(3) == 0) {
				ItemStack stack = this.item.getEntityItem();
				
				if(stack.stackSize > 1) {
					stack.stackSize--;
					EntityItem newItem = new EntityItem(this.pigeon.worldObj);
					newItem.setPosition(this.item.posX, this.item.posY, this.item.posZ);
					newItem.setEntityItemStack(stack);
					this.pigeon.worldObj.spawnEntityInWorld(newItem);
				}
				
				this.item.setDead();
			}
			this.pigeon.setFat(true);
			this.pigeon.playSound("random.eat", 0.5F + 0.5F * this.pigeon.getRNG().nextInt(2), (this.pigeon.getRNG().nextFloat() - this.pigeon.getRNG().nextFloat()) * 0.2F + 1.0F);
		}
	}

}
