package com.hbm.entity.grenade;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.effect.EntityStun;
import com.hbm.explosion.ExplosionHurtUtil;
import com.hbm.tileentity.machine.TileEntityTesla;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeStunning extends EntityGrenadeBase
{
	public List<double[]> targets = new ArrayList<double[]>();
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
			targets = TileEntityTesla.zap(worldObj, posX, posY, posZ, 10, getThrower());
			ExplosionHurtUtil.doStun(worldObj, this.posX, this.posY, this.posZ, 10);
			EntityStun wave = new EntityStun(worldObj, 10);
			wave.posX = this.posX + 0.5;
			wave.posY = this.posY + 0.5;
			wave.posZ = this.posZ + 0.5;
			worldObj.spawnEntityInWorld(wave);
			this.setDead();
		}
	}
}
