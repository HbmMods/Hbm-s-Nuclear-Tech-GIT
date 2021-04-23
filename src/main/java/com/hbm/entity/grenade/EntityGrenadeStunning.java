package com.hbm.entity.grenade;

import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.explosion.ExplosionHurtUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

public class EntityGrenadeStunning extends EntityGrenadeBase
{
	public EntityGrenadeStunning(World world)
	{
		super(world);
	}
	
	public EntityGrenadeStunning(World world, EntityLivingBase entity)
	{
		super(world, entity);
	}
	
	public EntityGrenadeStunning(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	@Override
	public void explode()
	{
		if (!this.worldObj.isRemote)
		{
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 100.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:weapon.sparkShoot", 5.0f, worldObj.rand.nextFloat() * 0.2F + 0.9F);
			this.setDead();
			
			ExplosionHurtUtil.doStun(worldObj, this.posX, this.posY, this.posZ, 10);
			//ExplosionLarge.spawnShock(worldObj, this.posX, this.posY, this.posZ, 24, 2);
			//this.worldObj.spawnEntityInWorld(new EntityLightningBolt(worldObj, this.posX, this.posY, this.posZ));
			EntityEMPBlast wave = new EntityEMPBlast(worldObj, 10);
			wave.posX = this.posX + 0.5;
			wave.posY = this.posY + 0.5;
			wave.posZ = this.posZ + 0.5;
			worldObj.spawnEntityInWorld(wave);
		}
	}
}
