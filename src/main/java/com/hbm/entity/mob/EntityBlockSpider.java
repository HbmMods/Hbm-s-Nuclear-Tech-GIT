package com.hbm.entity.mob;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityBlockSpider extends EntityMob {

	public EntityBlockSpider(World world) {
		super(world);

		this.setSize(0.95F, 1.25F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAIWander(this, 0.5F));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1F);
	}
	
	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		this.dataWatcher.addObject(12, 1);
		this.dataWatcher.addObject(13, 0);
	}
	
	public void makeBlock(Block block, int meta) {
		
		int b = Block.getIdFromBlock(block);
		
		this.dataWatcher.updateObject(12, b);
		this.dataWatcher.updateObject(13, meta);
		
		double health = Math.max(1D, block.getExplosionResistance(null));

		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(health);
		this.setHealth(this.getMaxHealth());
	}
}
