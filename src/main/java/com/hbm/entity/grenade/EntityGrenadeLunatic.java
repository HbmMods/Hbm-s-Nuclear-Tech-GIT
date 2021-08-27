package com.hbm.entity.grenade;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeLunatic extends EntityGrenadeBouncyBase
{
	public EntityGrenadeLunatic(World p_i1773_1_)
	{
		super(p_i1773_1_);
	}
	
	public EntityGrenadeLunatic(World worldIn, EntityLivingBase entity)
	{
		super(worldIn, entity);
	}
	
	public EntityGrenadeLunatic(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	@Override
	public void explode()
	{
		EntityNukeExplosionMK3 explosionEntity = new EntityNukeExplosionMK3(worldObj);
		explosionEntity.posX = this.posX;
		explosionEntity.posY = this.posY;
		explosionEntity.posZ = this.posZ;
		explosionEntity.destructionRange = 25;
		explosionEntity.speed = BombConfig.blastSpeed;
		explosionEntity.coefficient = 15F;
		explosionEntity.coefficient2 = 45F;
		explosionEntity.waste = false;
		explosionEntity.extType = 2;
		worldObj.spawnEntityInWorld(explosionEntity);
		
		EntityCloudTom cloud = new EntityCloudTom(worldObj, 25);
		cloud.posX = this.posX;
		cloud.posY = this.posY;
		cloud.posZ = this.posZ;
		worldObj.spawnEntityInWorld(cloud);
		
		this.setDead();
	}
	@Override
	protected int getMaxTimer()
	{
		return ItemGrenade.getFuseTicks(ModItems.grenade_lunatic);
	}
	@Override
	protected double getBounceMod()
	{
		return 0.25D;
	}
}
