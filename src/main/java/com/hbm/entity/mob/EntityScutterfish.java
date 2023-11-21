package com.hbm.entity.mob;

import java.util.function.Predicate;

import com.hbm.entity.mob.ai.EntityAIEatBread;
import com.hbm.entity.mob.ai.EntityAISwimmingConditional;
import com.hbm.entity.mob.ai.EntityAISwimmingVTwo;
import com.hbm.entity.mob.ai.EntityAIWanderConditional;

import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityScutterfish extends EntityWaterMob implements IAnimals {

	public EntityScutterfish(World world) {
		super(world);
        Predicate<EntityPlayer> tooCloseCondition = player -> this.getDistanceToEntity(player) < 5.0D;

		this.tasks.addTask(0, new EntityAISwimmingVTwo(this, (float) 5.0));
		this.tasks.addTask(1, new EntityAISwimmingConditional(this, tooCloseCondition));
		this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(1, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAISwimming(this));
		this.setSize(2.0F, 2.0F);

	}
	protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_) {
		super.damageEntity(p_70665_1_, p_70665_2_);
	}
	@Override
    public boolean isInWater()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
    }

	public void onDeath(DamageSource p_70645_1_) {
		if(!worldObj.isRemote) MinecraftServer.getServer().getConfigurationManager().sendChatMsg(this.func_110142_aN().func_151521_b());
		super.onDeath(p_70645_1_);
	}
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
	}
}
