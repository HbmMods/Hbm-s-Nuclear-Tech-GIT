package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityCombineBallNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunOSIPR extends ItemGunBase {

	public ItemGunOSIPR(GunConfiguration config, GunConfiguration alt) {
		super(config, alt);
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		
		setCharge(stack, 1);
		world.playSoundAtEntity(player, "hbm:weapon.osiprCharging", 1.0F, 1F);
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		super.updateServer(stack, world, player, slot, isCurrentItem);
		
		if(!isCurrentItem) {
			setCharge(stack, 0);
			return;
		}
		
		int i = getCharge(stack);
		
		if(i >= 20) {
			final EntityCombineBallNT energyBall = new EntityCombineBallNT(world, BulletConfigSyncingUtil.SPECIAL_OSIPR_CHARGED, player);
			world.spawnEntityInWorld(energyBall);
			world.playSoundAtEntity(player, altConfig.firingSound, 1.0F, 1F);
			setCharge(stack, 0);
			setDelay(stack, altConfig.rateOfFire);
			player.inventory.consumeInventoryItem(ModItems.gun_osipr_ammo2);
			
		} else if(i > 0)
			setCharge(stack, i + 1);
	}
	
	@Override
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		return super.tryShoot(stack, world, player, main) && getCharge(stack) == 0;
	}
	
	/// CMB charge state ///
	public static void setCharge(ItemStack stack, int i) {
		writeNBT(stack, "cmb_charge", i);
	}
	
	public static int getCharge(ItemStack stack) {
		return readNBT(stack, "cmb_charge");
	}
}
