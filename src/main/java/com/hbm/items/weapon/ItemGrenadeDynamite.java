package com.hbm.items.weapon;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class ItemGrenadeDynamite extends ItemGenericGrenade {

	public ItemGrenadeDynamite(int fuse) {
		super(fuse);
	}

	public void explode(Entity grenade, EntityLivingBase thrower, World world, double x, double y, double z) {
		ExplosionVNT vnt = new ExplosionVNT(grenade.worldObj, grenade.posX, grenade.posY, grenade.posZ, 5, thrower);
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 15));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
	}
}
