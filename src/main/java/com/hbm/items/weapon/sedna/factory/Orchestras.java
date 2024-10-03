package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;

import com.hbm.handler.CasingEjector;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.helper.CasingCreator;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** Orchestras are server-side components that run along client-side animations.
 * The orchestra only knows what animation is or was playing and how long it started, but not if it is still active.
 * Orchestras are useful for things like playing server-side sound, spawning casings or sending particle packets.*/
public class Orchestras {
	
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
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 11) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 16) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_PEPPERBOX = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack);
		int timer = ItemGunBaseNT.getAnimTimer(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 24) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 55) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverSpin", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 21) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.6F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 0.8F);
			if(timer == 11) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.6F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverSpin", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 28) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 0.75F);
			if(timer == 45) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 0.6F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_ATLAS = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack);
		int timer = ItemGunBaseNT.getAnimTimer(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 36) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 44) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 14) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 14) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 24) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 12) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 34) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_HENRY = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack);
		int timer = ItemGunBaseNT.getAnimTimer(stack);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 8) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 16) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_CYCLE) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_END) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 0.9F);
			if(timer == 12) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 0.9F);
			if(timer == 12) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 1F);
			if(timer == 36) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 1F);
			if(timer == 44) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 14) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				casing.setupSmoke(1F, 0.5D, 60, 20);
				CasingCreator.composeEffect(player.worldObj, player, 0.5, -0.125, aiming ? -0.125 : -0.375D, 0, 0.12, -0.12, casing.getName());
			}
			if(timer == 12) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 12) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_GREASEGUN = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack);
		int timer = ItemGunBaseNT.getAnimTimer(stack);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
	};
}
