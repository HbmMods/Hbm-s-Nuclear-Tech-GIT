package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;

import com.hbm.config.ClientConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.impl.ItemGunStinger;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.main.MainRegistry;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.helper.CasingCreator;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.sound.AudioWrapper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/** Orchestras are server-side components that run along client-side animations.
 * The orchestra only knows what animation is or was playing and how long it started, but not if it is still active.
 * Orchestras are useful for things like playing server-side sound, spawning casings or sending particle packets.*/
public class Orchestras {
	
	public static BiConsumer<ItemStack, LambdaContext> DEBUG_ORCHESTRA = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		if(type == AnimType.RELOAD) {
			if(timer == 3) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 10) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 34) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 40) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);

			if(timer == 16) {
				Receiver rec = ctx.config.getReceivers(stack)[0];
				IMagazine mag = rec.getMagazine(stack);
				SpentCasing casing = mag.getCasing(stack);
				if(casing != null) for(int i = 0; i < mag.getCapacity(stack); i++) CasingCreator.composeEffect(entity.worldObj, entity, 0.25, -0.125, -0.125, -0.05, 0, 0, 0.01, casing.getName());
			}
		}
		if(type == AnimType.CYCLE) {
			if(timer == 11) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 11) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 3) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_PEPPERBOX = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 24) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 55) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverSpin", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 21) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.6F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.8F);
			if(timer == 11) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.6F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 3) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverSpin", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 28) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.75F);
			if(timer == 45) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.6F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_ATLAS = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 36) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 44) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 14) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 14) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 24) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 34) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_DANI = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 36) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 44) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 9) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 9) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 24) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 34) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_HENRY = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_CYCLE) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_END) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 0.9F);
			if(timer == 12 && ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmountBeforeReload(stack) <= 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 0.9F);
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 1F);
			if(timer == 36) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 1F);
			if(timer == 44) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 14) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.5, -0.125, aiming ? -0.125 : -0.375D, 0, 0.12, -0.12, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_GREASEGUN = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.EQUIP) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.openLatch", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 2) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.55, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, casing.getName());
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.8F);
			if(timer == 11) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
			
		}
		if(type == AnimType.RELOAD) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 24) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
			if(timer == 36) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.8F);
			if(timer == 26) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1.25F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 11) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
			if(timer == 26) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_MARESLEG = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.8F);
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
		if(type == AnimType.RELOAD_CYCLE) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
		if(type == AnimType.RELOAD_END) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.7F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.7F);
			if(timer == 17) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 0.8F);
			if(timer == 29) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 0.8F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 14) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.3125, -0.125, aiming ? -0.125 : -0.375D, 0, 0.18, -0.12, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 0.8F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 0.8F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_MARESLEG_SHORT = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.8F);
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
		if(type == AnimType.RELOAD_CYCLE) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
		if(type == AnimType.RELOAD_END) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.7F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.7F);
			if(timer == 17) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 0.8F);
			if(timer == 29) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 0.8F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 14) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.3125, -0.125, aiming ? -0.125 : -0.375D, 0, -0.08, 0, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 0.8F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 0.8F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_MARESLEG_AKIMBO = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
		
		if(type == AnimType.CYCLE) {
			if(timer == 14) {
				int offset = ctx.configIndex == 0 ? -1 : 1;
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.3125, -0.125, aiming ? -0.125 * offset : -0.375D * offset, 0, -0.08, 0, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.leverCock", 1F, 0.8F);
			return;
		}
		
		ORCHESTRA_MARESLEG_SHORT.accept(stack, ctx);
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_FLAREGUN = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 0.8F);
			if(timer == 4) {
				IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
				if(mag.getAmountAfterReload(stack) > 0) {
					SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
					if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.625, -0.125, aiming ? -0.125 : -0.375D, -0.12, 0.18, 0, 0.01, casing.getName(), true, 60, 0.5D, 20);
					mag.setAmountBeforeReload(stack, 0);
				}
			}
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.insertCanister", 1F, 1F);
			if(timer == 24) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 10) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 0.8F);
			if(timer == 29) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_NOPIP = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		if(type == AnimType.RELOAD) {
			if(timer == 3) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 10) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 34) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 40) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			
			if(timer == 16) {
				Receiver rec = ctx.config.getReceivers(stack)[0];
				IMagazine mag = rec.getMagazine(stack);
				SpentCasing casing = mag.getCasing(stack);
				if(casing != null) for(int i = 0; i < mag.getCapacity(stack); i++) CasingCreator.composeEffect(entity.worldObj, entity, 0.25, -0.125, -0.125, -0.05, 0, 0, 0.01, casing.getName());
			}
		}
		if(type == AnimType.CYCLE) {
			if(timer == 11) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 11) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 3) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_CARBIBE = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
		
		if(type == AnimType.CYCLE) {
			if(timer == 2) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.3125, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.06, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
		if(type == AnimType.RELOAD) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 26) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_END) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
			if(timer == 31) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 6) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			if(timer == 30) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_AM180 = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
		
		if(ClientConfig.GUN_ANIMS_LEGACY.get()) {
			if(type == AnimType.CYCLE) {
				if(timer == 0) {
					SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
					if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.4375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, -0.06, 0, 0.01, casing.getName());
				}
			}
			if(type == AnimType.CYCLE_DRY) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
				if(timer == 6) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.9F);
			}
			if(type == AnimType.RELOAD) {
				if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
				if(timer == 20) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.impact", 0.25F, 1F);
				if(timer == 32) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
				if(timer == 40) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.9F);
			}
			if(type == AnimType.JAMMED) {
				if(timer == 15) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
			}
			if(type == AnimType.INSPECT) {
				if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
				if(timer == 35) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
			}
		} else {
			if(type == AnimType.CYCLE) {
				if(timer == 0) {
					SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
					if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.4375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, -0.06, 0, 0.01, casing.getName());
				}
			}
			if(type == AnimType.CYCLE_DRY) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
				if(timer == 6) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.9F);
			}
			if(type == AnimType.RELOAD) {
				if(timer == 6) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
				if(timer == 26) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.impact", 0.25F, 1F);
				if(timer == 48) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
				if(timer == 54) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.9F);
			}
			if(type == AnimType.JAMMED) {
				if(timer == 6) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
				if(timer == 20) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 1.0F);
			}
			if(type == AnimType.INSPECT) {
				if(timer == 6) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
				if(timer == 53) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
			}
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_LIBERATOR = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.75F);
			if(timer == 4) {
				IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
				int toEject = mag.getAmountAfterReload(stack) - mag.getAmount(stack);
				SpentCasing casing = mag.getCasing(stack);
				if(casing != null) for(int i = 0; i < toEject; i++) CasingCreator.composeEffect(entity.worldObj, entity, 0.625, -0.1875, -0.375D, -0.12, 0.18, 0, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
			if(timer == 15) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_CYCLE) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_END) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.75F);
			if(timer == 26) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.75F);
			IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
			int toEject = mag.getAmountAfterReload(stack) - mag.getAmount(stack);
			if(timer == 4 && toEject > 0) {
				SpentCasing casing = mag.getCasing(stack);
				if(casing != null) for(int i = 0; i < toEject; i++) CasingCreator.composeEffect(entity.worldObj, entity, 0.625, -0.1875, -0.375D, -0.12, 0.18, 0, 0.01, casing.getName(), true, 60, 0.5D, 20);
				mag.setAmountAfterReload(stack, 0);
			}
			if(timer == 20) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_CONGOLAKE = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
		
		if(type == AnimType.CYCLE) {
			if(timer == 15) {
				IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
				SpentCasing casing = mag.getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.625, aiming ? -0.0625 : -0.25, aiming ? 0 : -0.375D, 0, 0.18, 0.12, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
		}
		if(type == AnimType.RELOAD || type == AnimType.RELOAD_CYCLE) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.glReload", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 9) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.glOpen", 1F, 1F);
			if(timer == 27) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.glClose", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_FLAMER = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		if(type == AnimType.CYCLE && entity.worldObj.isRemote) {
			AudioWrapper runningAudio = ItemGunBaseNT.loopedSounds.get(entity);
			
			if(timer < 5) {
				//start sound
				if(runningAudio == null || !runningAudio.isPlaying()) {
					AudioWrapper audio = MainRegistry.proxy.getLoopedSound("hbm:weapon.fire.flameLoop", (float) entity.posX, (float) entity.posY, (float) entity.posZ, 1F, 15F, 1F, 10);
					ItemGunBaseNT.loopedSounds.put(entity, audio);
					audio.startSound();
				}
				//keepalive
				if(runningAudio != null && runningAudio.isPlaying()) {
					runningAudio.keepAlive();
					runningAudio.updatePosition((float) entity.posX, (float) entity.posY, (float) entity.posZ);
				}
			} else {
				//stop sound due to timeout
				if(runningAudio != null && runningAudio.isPlaying()) runningAudio.stopSound();
			}
		}
		//stop sound due to state change
		if(type != AnimType.CYCLE && entity.worldObj.isRemote) {
			AudioWrapper runningAudio = ItemGunBaseNT.loopedSounds.get(entity);
			if(runningAudio != null && runningAudio.isPlaying()) runningAudio.stopSound();
		}
		if(entity.worldObj.isRemote) return;
		
		if(type == AnimType.RELOAD) {
			if(timer == 15) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.openLatch", 1F, 1F);
			if(timer == 35) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.impact", 0.5F, 1F);
			if(timer == 60) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.75F);
			if(timer == 70) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.insertCanister", 1F, 1F);
			if(timer == 85) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pressureValve", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_LAG = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_UZI = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.EQUIP) {
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.openLatch", 1F, 1.25F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 1) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, casing.getName());
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 1F);
			
		}
		if(type == AnimType.RELOAD) {
			if(timer == 4) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 26) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
			if(timer == 36) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 17) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 1F);
			if(timer == 31) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_SPAS = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
		
		if(type == AnimType.CYCLE || type == AnimType.ALT_CYCLE) {
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunCock", 1F, 1F);
			if(timer == 10) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, casing.getName());
			}
		}
		if(type == AnimType.RELOAD) {
			IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
			if(mag.getAmount(stack) == 0) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
				if(timer == 7) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			}
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
		if(type == AnimType.RELOAD_CYCLE) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_PANERSCHRECK = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		if(type == AnimType.RELOAD) {
			if(timer == 30) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.insertCanister", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_G3 = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
		
		if(type == AnimType.CYCLE) {
			if(timer == 0) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.5, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, casing.getName());
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.8F);
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
			if(timer == 9) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			
		}
		if(type == AnimType.RELOAD) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 4) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
			if(timer == 32) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
			if(timer == 36) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 28) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
			if(timer == 20) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			if(timer == 24) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
			if(timer == 28) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_STINGER = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		AudioWrapper runningAudio = ItemGunBaseNT.loopedSounds.get(entity);
		if(ItemGunStinger.getLockonProgress(stack) > 0 && !ItemGunStinger.getIsLockedOn(stack)) {
			//start sound
			if(runningAudio == null || !runningAudio.isPlaying()) {
				AudioWrapper audio = MainRegistry.proxy.getLoopedSound("hbm:weapon.fire.lockon", (float) entity.posX, (float) entity.posY, (float) entity.posZ, 1F, 15F, 1F, 10);
				ItemGunBaseNT.loopedSounds.put(entity, audio);
				audio.startSound();
			}
			//keepalive
			if(runningAudio != null && runningAudio.isPlaying()) {
				runningAudio.keepAlive();
				runningAudio.updatePosition((float) entity.posX, (float) entity.posY, (float) entity.posZ);
			}
		} else {
			//stop sound due to timeout
			if(runningAudio != null && runningAudio.isPlaying()) runningAudio.stopSound();
		}
		
		if(type == AnimType.RELOAD) {
			if(timer == 30) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.insertCanister", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_CHEMTHROWER = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		if(type == AnimType.CYCLE && entity.worldObj.isRemote) {
			AudioWrapper runningAudio = ItemGunBaseNT.loopedSounds.get(entity);
			
			if(timer < 5) {
				//start sound
				if(runningAudio == null || !runningAudio.isPlaying()) {
					AudioWrapper audio = MainRegistry.proxy.getLoopedSound("hbm:weapon.fire.flameLoop", (float) entity.posX, (float) entity.posY, (float) entity.posZ, 1F, 15F, 1F, 10);
					ItemGunBaseNT.loopedSounds.put(entity, audio);
					audio.startSound();
				}
				//keepalive
				if(runningAudio != null && runningAudio.isPlaying()) {
					runningAudio.keepAlive();
					runningAudio.updatePosition((float) entity.posX, (float) entity.posY, (float) entity.posZ);
				}
			} else {
				//stop sound due to timeout
				if(runningAudio != null && runningAudio.isPlaying()) runningAudio.stopSound();
			}
		}
		//stop sound due to state change
		if(type != AnimType.CYCLE && entity.worldObj.isRemote) {
			AudioWrapper runningAudio = ItemGunBaseNT.loopedSounds.get(entity);
			if(runningAudio != null && runningAudio.isPlaying()) runningAudio.stopSound();
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_M2 = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.CYCLE) {
			if(timer == 0) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.375, aiming ? 0 : -0.125, aiming ? 0 : -0.3125D, 0, 0.06, -0.18, 0.01, casing.getName());
			}
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_SHREDDER = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.CYCLE) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.fire.shredderCycle", 0.25F, 1.5F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.fire.shredderCycle", 0.25F, 1.5F);
		}
		if(type == AnimType.RELOAD) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 32) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 28) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_QUADRO = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		if(type == AnimType.RELOAD) {
			if(timer == 30) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.insertCanister", 1F, 1F);
		}
	};
}
