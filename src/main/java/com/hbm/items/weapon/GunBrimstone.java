package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityLaser;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GunBrimstone extends ItemGunBase {
	
	//private AudioWrapper firingLoop;
	
	public GunBrimstone(GunConfiguration config) {
		super(config);
	}
	
	//Unused for now, planned to in the future to be used for damage ramp off
	//Random rand = new Random();
	
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		//Overrides the projectile, spawning bullet and beam
		EntityBulletBase bullet = new EntityBulletBase(world, BulletConfigSyncingUtil.BRIMSTONE_AMMO, player);
		EntityLaser laser = new EntityLaser(world, player);
		
		world.spawnEntityInWorld(laser);
		//Why did I added that second spawn laser? I don't know, I just know that makes no sense 
		//world.spawnEntityInWorld(laser);
		world.spawnEntityInWorld(bullet);
		
		//I just gave up on trying to add a loop sound for it.
		world.playSoundAtEntity(player, "hbm:weapon.brimLoop", 0.75F, 0.85F);
		}
	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Ammo: Depleted Plutonium-240 Ammo (Belt)");
		list.add("Manufacturer: Winchester Arms");
		list.add("");
		list.add("");
		list.add("");
		list.add("[LEGENDARY WEAPON]");
	}
}

