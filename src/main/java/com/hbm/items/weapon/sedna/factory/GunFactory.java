package com.hbm.items.weapon.sedna.factory;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.mags.MagazineRevolverDrum;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.item.Item;

public class GunFactory {
	
	public static BulletConfig ammo_debug;

	public static void init() {
		
		/// AMMO ITEMS ///
		ModItems.ammo_debug = new Item().setUnlocalizedName("ammo_debug").setTextureName(RefStrings.MODID + ":ammo_45");

		/// BULLLET CFGS ///
		ammo_debug = new BulletConfig().setItem(ModItems.ammo_debug).setSpread(0.01F);

		/// GUNS ///
		ModItems.gun_debug = new ItemGunBaseNT(new GunConfig()
				.dura(600).draw(15).crosshair(Crosshair.L_CLASSIC)
				.rec(new Receiver(0)
						.dmg(10F).delay(12).reload(20).sound("hbm:weapon.44Shoot", 1.0F, 1.0F)
						.mag(new MagazineRevolverDrum(0, 6).addConfigs(ammo_debug))
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY) .pr(Lego.LAMBDA_STANDARD_RELOAD) .pt(Lego.LAMBDA_TOGGLE_AIM)
				.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(Lego.LAMBDA_DEBUG_ANIMS)
				).setUnlocalizedName("gun_debug").setTextureName(RefStrings.MODID + ":gun_darter");

		/// PROXY BULLSHIT ///
		MainRegistry.proxy.registerGunCfg();
	}
}
