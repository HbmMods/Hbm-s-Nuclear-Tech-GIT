package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;

import com.hbm.handler.CasingEjector;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
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
			if(timer == 20) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 55) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverSpin", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 21) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 11) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverSpin", 1F, 1F);
		}
	};
}
