package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;

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
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		if(type == AnimType.RELOAD) {
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 10) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 34) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 40) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);

			if(timer == 16) {
				Receiver rec = ctx.config.getReceivers(stack)[0];
				IMagazine mag = rec.getMagazine(stack);
				SpentCasing casing = mag.getCasing(stack);
				for(int i = 0; i < mag.getCapacity(stack); i++) CasingCreator.composeEffect(player.worldObj, player, 0.25, -0.125, -0.125, -0.05, 0, 0, 0.01, casing.getName());
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
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

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
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

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
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_DANI = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 36) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 44) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 9) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 9) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
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
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
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
			if(timer == 12 && ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmountBeforeReload(stack) <= 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 1F);
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
				CasingCreator.composeEffect(player.worldObj, player, 0.5, -0.125, aiming ? -0.125 : -0.375D, 0, 0.12, -0.12, 0.01, casing.getName(), true, 60, 0.5D, 20);
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
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.EQUIP) {
			if(timer == 5) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.openLatch", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 2) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				CasingCreator.composeEffect(player.worldObj, player, 0.55, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, casing.getName());
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 0.8F);
			if(timer == 11) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
			
		}
		if(type == AnimType.RELOAD) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 24) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magInsert", 1F, 1F);
			if(timer == 36) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 5) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.8F);
			if(timer == 26) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1.25F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 11) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
			if(timer == 26) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_MARESLEG = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 8) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.8F);
			if(timer == 16) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
		if(type == AnimType.RELOAD_CYCLE) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
		if(type == AnimType.RELOAD_END) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.7F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.7F);
			if(timer == 17) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 0.8F);
			if(timer == 29) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 0.8F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 14) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				CasingCreator.composeEffect(player.worldObj, player, 0.3125, -0.125, aiming ? -0.125 : -0.375D, 0, 0.18, -0.12, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
			if(timer == 8) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 0.8F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 8) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.leverCock", 1F, 0.8F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_FLAREGUN = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 0.8F);
			if(timer == 4) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				CasingCreator.composeEffect(player.worldObj, player, 0.625, -0.125, aiming ? -0.125 : -0.375D, -0.12, 0.18, 0, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
			if(timer == 16) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.insertCanister", 1F, 1F);
			if(timer == 24) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 10) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 0.8F);
			if(timer == 29) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 12) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 12) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_NOPIP = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		if(type == AnimType.RELOAD) {
			if(timer == 3) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 10) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 34) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
			if(timer == 40) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);
			
			if(timer == 16) {
				Receiver rec = ctx.config.getReceivers(stack)[0];
				IMagazine mag = rec.getMagazine(stack);
				SpentCasing casing = mag.getCasing(stack);
				for(int i = 0; i < mag.getCapacity(stack); i++) CasingCreator.composeEffect(player.worldObj, player, 0.25, -0.125, -0.125, -0.05, 0, 0, 0.01, casing.getName());
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

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_CARBIBE = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
		
		if(type == AnimType.CYCLE) {
			if(timer == 2) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				CasingCreator.composeEffect(player.worldObj, player, 0.3125, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.06, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 8) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
		if(type == AnimType.RELOAD) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 26) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_END) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
			if(timer == 31) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 6) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 1F);
			if(timer == 30) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_AM180 = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
		
		if(type == AnimType.CYCLE) {
			if(timer == 0) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack);
				CasingCreator.composeEffect(player.worldObj, player, 0.4375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, -0.06, 0, 0.01, casing.getName());
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 6) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.9F);
		}
		if(type == AnimType.RELOAD) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 20) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.impact", 0.25F, 1F);
			if(timer == 32) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magInsert", 1F, 1F);
			if(timer == 40) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.9F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 15) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.pistolCock", 1F, 0.8F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 35) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magInsert", 1F, 1F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_LIBERATOR = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.75F);
			if(timer == 4) {
				IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
				int toEject = mag.getAmountAfterReload(stack) - mag.getAmount(stack);
				SpentCasing casing = mag.getCasing(stack);
				for(int i = 0; i < toEject; i++) CasingCreator.composeEffect(player.worldObj, player, 0.625, -0.1875, -0.375D, -0.12, 0.18, 0, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
			if(timer == 15) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_CYCLE) {
			if(timer == 5) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
		}
		if(type == AnimType.RELOAD_END) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 2) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
			if(timer == 12) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.75F);
			if(timer == 26) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.dryFireClick", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverCock", 1F, 0.75F);
			IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
			int toEject = mag.getAmountAfterReload(stack) - mag.getAmount(stack);
			if(timer == 4 && toEject <= 0) {
				SpentCasing casing = mag.getCasing(stack);
				for(int i = 0; i < toEject; i++) CasingCreator.composeEffect(player.worldObj, player, 0.625, -0.1875, -0.375D, -0.12, 0.18, 0, 0.01, casing.getName(), true, 60, 0.5D, 20);
				mag.setAmountAfterReload(stack, 0);
			}
			if(timer == 20) player.worldObj.playSoundAtEntity(player, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_CONGOLAKE = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);
		
		if(type == AnimType.CYCLE) {
			if(timer == 15) {
				IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
				SpentCasing casing = mag.getCasing(stack);
				CasingCreator.composeEffect(player.worldObj, player, 0.625, aiming ? -0.0625 : -0.25, aiming ? 0 : -0.375D, 0, 0.18, 0.12, 0.01, casing.getName(), true, 60, 0.5D, 20);
			}
		}
		if(type == AnimType.RELOAD || type == AnimType.RELOAD_CYCLE) {
			if(timer == 0) player.worldObj.playSoundAtEntity(player, "hbm:weapon.glReload", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 9) player.worldObj.playSoundAtEntity(player, "hbm:weapon.glOpen", 1F, 1F);
			if(timer == 27) player.worldObj.playSoundAtEntity(player, "hbm:weapon.glClose", 1F, 1F);
		}
	};
}
