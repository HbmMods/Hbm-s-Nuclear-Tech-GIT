package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;

import com.hbm.handler.CasingEjector;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GunFactory {

	public static BulletConfig ammo_debug;
	public static BulletConfig ammo_debug_buckshot;
	
	public static SpentCasing CASING44 = new SpentCasing(CasingType.STRAIGHT).setScale(1.5F, 1.0F, 1.5F).setBounceMotion(0.01F, 0.05F).setColor(SpentCasing.COLOR_CASE_44);

	public static void init() {
		
		/// AMMO ITEMS ///
		ModItems.ammo_debug = new Item().setUnlocalizedName("ammo_debug").setTextureName(RefStrings.MODID + ":ammo_45");

		/// BULLLET CFGS ///
		ammo_debug = new BulletConfig().setItem(ModItems.ammo_debug).setSpread(0.01F).setRicochetAngle(45).setCasing(CASING44.clone().register("DEBUG0"));
		ammo_debug_buckshot = new BulletConfig().setItem(ModItems.ammo_12gauge).setSpread(0.1F).setRicochetAngle(45).setProjectiles(6, 6).setCasing(CASING44.clone().register("DEBUG1"));

		/// GUNS ///
		ModItems.gun_debug = new ItemGunBaseNT(new GunConfig()
				.dura(600F).draw(15).inspect(23).crosshair(Crosshair.L_CLASSIC).hud(Lego.HUD_COMPONENT_DURABILITY, Lego.HUD_COMPONENT_AMMO).smoke(true).orchestra(DEBUG_ORCHESTRA)
				.rec(new Receiver(0)
						.dmg(10F).delay(14).reload(46).sound("hbm:weapon.44Shoot", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 12).addConfigs(ammo_debug, ammo_debug_buckshot))
						.ejector(new CasingEjector().setMotion(0, -0.1, 0).setAngleRange(0.01F, 0.025F))
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
			
			if(timer == 20) {
				Receiver rec = ctx.config.getReceivers(stack)[0];
				IMagazine mag = rec.getMagazine(stack);
				CasingEjector ejector = rec.getEjector(stack);
				BulletConfig bullet = (BulletConfig) mag.getType(stack);
				for(int i = 0; i < mag.getCapacity(stack); i++) ItemGunBaseNT.trySpawnCasing(player, ejector, bullet, stack);
			}
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
