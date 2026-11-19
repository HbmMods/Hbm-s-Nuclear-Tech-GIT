package com.hbm.handler;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.IKeybindReceiver;
import com.hbm.items.weapon.sedna.factory.XFactoryTurret;
import com.hbm.main.NTMSounds;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HbmKeybindsServer {
	
	/** Can't put this in HbmKeybinds because it's littered with clientonly stuff */
	public static void onPressedServer(EntityPlayer player, EnumKeybind key, boolean state) {
		
		// EXTPROP HANDLING
		HbmPlayerProps props = HbmPlayerProps.getData(player);
		props.setKeyPressed(key, state);
		
		// ITEM HANDLING
		ItemStack held = player.getHeldItem();
		if(held != null && held.getItem() instanceof IKeybindReceiver) {
			IKeybindReceiver rec = (IKeybindReceiver) held.getItem();
			if(rec.canHandleKeybind(player, held, key)) rec.handleKeybind(player, held, key, state);
		}
		
		if(key == EnumKeybind.GUN_PRIMARY && state && player.swingProgressInt == 0 && !player.isBlocking()) {
			boolean hasSword = player.getHeldItem() != null && player.getHeldItem().getAttributeModifiers().containsKey(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
	
			/// sword beam ///
			if(player.isPotionActive(HbmPotion.estradiol) && hasSword) {
				EntityBulletBaseMK4 mk4 = new EntityBulletBaseMK4(player, XFactoryTurret.bullet_config_swordbeam, 10F, 0F, -0.1875, -0.0625, 0.5);
				player.worldObj.spawnEntityInWorld(mk4);
				mk4.motionX += player.motionX;
				mk4.motionZ += player.motionZ;
				player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, NTMSounds.GUN_TESLA_FIRE, 0.5F, 2F);
			}
		}
	}
}
