package com.hbm.items.weapon;

import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.typesafe.config.Config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemGunEnergyPistol extends ItemGunBase {
	public int maximumCharges;
	public ItemGunEnergyPistol(GunConfiguration config) {
		super(config);
		maximumCharges = 10;
	}
	
	protected void explode(World world, EntityPlayer player) {
		world.playSoundEffect(player.posX, player.posY, player.posZ, "random.explode", 100.0f, world.rand.nextFloat() * 0.1F + 0.9F);

		EntityNukeExplosionMK3 exp = new EntityNukeExplosionMK3(world);
		exp.posX = player.posX;
		exp.posY = player.posY;
		exp.posZ = player.posZ;
		exp.destructionRange = 60;
		exp.speed = 25;
		exp.coefficient = 1.0F;
		exp.waste = false;

		world.spawnEntityInWorld(exp);
		
		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(world, 60);
		cloud.posX = player.posX;
		cloud.posY = player.posY;
		cloud.posZ = player.posZ;
		world.spawnEntityInWorld(cloud);
	}
	
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		setCharge(stack, getCharge(stack) + 1);
		/*for(int j = 0; j < player.inventory.mainInventory.length; j++) {
			if(player.inventory.mainInventory[j] != null && player.inventory.mainInventory[j].getItem() == ModItems.gun_b92_ammo) {
				GunB92Cell.charge(player.inventory.mainInventory[j]);
				break;
			}
		}*/
		// Play the charge sound at the player
		world.playSoundAtEntity(player, "hbm:weapon.b92Reload", 2F, 0.9F);
		
		if (getCharge(stack) > maximumCharges) {
			// Blow up
			setCharge(stack, 0);
			// Create a FLEIJA type explosion
			if(!world.isRemote) {
				explode(world, player);
			}
		} else {
			// 10 ticks each charge is good, as it's about the length of the sound
			setDelay(stack, getDelay(stack) + 10);
		}
	}
	
	@Override
	protected boolean canShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		if (main && getCharge(stack) > 0) {
			return true;
		}
		// For some reason it's been able to reload with the delay on
		if (!main && getDelay(stack) <= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		EntityBulletBase bullet = new EntityBulletBase(world, config, player);
		float c;
		switch(getCharge(stack)) {
			case 1: c = 1.5F; break;
			case 2: c = 1.75F; break;
			case 3: c = 2.125F; break;
			case 4: c = 2.25F; break;
			case 5: c = 2.75F; break;
			case 6: c = 3.25F; break;
			case 7: c = 3.75F; break;
			case 8: c = 4.25F; break;
			case 9: c = 4.5F; break;
			case 10: c = 5.0F; break;			
			default: c = 0.0F; break;
		}
		bullet.powerMultiplier = c;
		setCharge(stack, 0);
		world.spawnEntityInWorld(bullet);
	}
	
	
	// Stuff to manipulate the gun charge value
	public static void setCharge(ItemStack stack, int i) {
		writeNBT(stack, "energypistol_charge", i);
	}
	
	public static int getCharge(ItemStack stack) {
		return readNBT(stack, "energypistol_charge");
	}
}
