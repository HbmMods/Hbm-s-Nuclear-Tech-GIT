package com.hbm.entity.mob;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDuck extends EntityChicken {

	public EntityDuck(World world) {
		super(world);
	}

	@Override
	protected String getLivingSound() {
		return "hbm:entity.ducc";
	}

	@Override
	protected String getHurtSound() {
		return "hbm:entity.ducc";
	}

	@Override
	protected String getDeathSound() {
		return "hbm:entity.ducc";
	}

	@Override
	public EntityDuck createChild(EntityAgeable entity) {
		return new EntityDuck(this.worldObj);
	}
	
	@Override
	protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_) {
		super.damageEntity(p_70665_1_, p_70665_2_);
	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		if(!worldObj.isRemote) MinecraftServer.getServer().getConfigurationManager().sendChatMsg(this.func_110142_aN().func_151521_b());
		super.onDeath(p_70645_1_);
	}
}