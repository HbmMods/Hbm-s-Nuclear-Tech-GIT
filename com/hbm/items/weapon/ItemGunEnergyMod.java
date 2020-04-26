package com.hbm.items.weapon;

import com.hbm.entity.effect.EntityCloudCustom;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.sound.AudioWrapper;
import com.typesafe.config.Config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGunEnergyMod extends ItemGunEnergyPistol {
	public ItemGunEnergyMod(GunConfiguration config) {
		super(config);
		maximumCharges = 13;
	}
	
	@Override
	protected void explode(World world, EntityPlayer player) {
		world.playSoundEffect(player.posX, player.posY, player.posZ, "random.explode", 100.0f, world.rand.nextFloat() * 0.1F + 0.9F);
		ExplosionLarge.explode(world, player.posX, player.posY, player.posZ, 10, true, false, false);
		// Kill the player no matter what, and have a very anticlimactic explosion
		player.addPotionEffect(new PotionEffect(HbmPotion.bang.id, 60, 0));
	}
	
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		EntityBulletBase bullet = new EntityBulletBase(world, config, player);
		bullet.powerMultiplier = getCharge(stack);
		setCharge(stack, 0);
		world.spawnEntityInWorld(bullet);
	}
}
