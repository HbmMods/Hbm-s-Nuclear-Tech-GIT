package com.hbm.items.weapon;

import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityBulletLaser;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityLaser;
import com.hbm.entity.projectile.EntityLaserBeam;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.typesafe.config.Config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunBrimstone extends ItemGunBase {
	public ItemGunBrimstone(GunConfiguration config) {
		super(config);
	}
	
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		//EntityLaser laser = new EntityLaser(world, player);
		EntityBulletLaser laser = new EntityBulletLaser(world, player);
		
		if (!world.isRemote) {
			world.spawnEntityInWorld(laser);
		}
	}
}
