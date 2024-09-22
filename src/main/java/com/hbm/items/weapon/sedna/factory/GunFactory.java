package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GunFactory {

	public static BulletConfig ammo_debug;
	public static BulletConfig ammo_debug_buckshot;

	public static void init() {
		
		/// AMMO ITEMS ///
		ModItems.ammo_debug = new Item().setUnlocalizedName("ammo_debug").setTextureName(RefStrings.MODID + ":ammo_45");

		/// BULLLET CFGS ///
		ammo_debug = new BulletConfig().setItem(ModItems.ammo_debug).setSpread(0.01F);
		ammo_debug_buckshot = new BulletConfig().setItem(ModItems.ammo_12gauge).setSpread(0.1F).setProjectiles(6, 6);

		/// GUNS ///
		ModItems.gun_debug = new ItemGunBaseNT(new GunConfig()
				.dura(600F).draw(15).inspect(23).crosshair(Crosshair.L_CLASSIC).hud(Lego.HUD_COMPONENT_DURABILITY, Lego.HUD_COMPONENT_AMMO).smoke(true).orchestra(DEBUG_ORCHESTRA)
				.rec(new Receiver(0)
						.dmg(10F).delay(14).reload(46).sound("hbm:weapon.44Shoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 12).addConfigs(ammo_debug, ammo_debug_buckshot))
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(Lego.LAMBDA_STANDARD_FIRE))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY) .pr(Lego.LAMBDA_STANDARD_RELOAD) .pt(Lego.LAMBDA_TOGGLE_AIM)
				.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(Lego.LAMBDA_DEBUG_ANIMS)
				).setUnlocalizedName("gun_debug").setTextureName(RefStrings.MODID + ":gun_darter");

		/// PROXY BULLSHIT ///
		MainRegistry.proxy.registerGunCfg();
	}
	
	public static BiConsumer<ItemStack, LambdaContext> DEBUG_ORCHESTRA = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack);
		int timer = ItemGunBaseNT.getAnimTimer(stack);
		
		if(type == AnimType.RELOAD) {
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 10) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 34) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 40) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 11) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 11) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 16) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};
}
