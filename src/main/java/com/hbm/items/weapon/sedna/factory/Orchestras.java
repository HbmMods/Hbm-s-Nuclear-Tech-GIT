package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;

import com.hbm.config.ClientConfig;
import com.hbm.items.ModItems;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.impl.ItemGunStinger;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.helper.CasingCreator;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.EntityDamageUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;

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
				SpentCasing casing = mag.getCasing(stack, ctx.inventory);
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
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
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
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.9F);
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
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.5, -0.125, aiming ? -0.125 : -0.375D, 0, 0.12, -0.12, 0.01, -7.5F + (float)entity.getRNG().nextGaussian() * 5F, (float)entity.getRNG().nextGaussian() * 1.5F, casing.getName(), true, 60, 0.5D, 20);
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
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.55, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, -7.5F + (float)entity.getRNG().nextGaussian() * 5F, 12F + (float)entity.getRNG().nextGaussian() * 5F, casing.getName());
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
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.3125, -0.125, aiming ? -0.125 : -0.375D, 0, 0.18, -0.12, 0.01, -10F + (float)entity.getRNG().nextGaussian() * 5F, (float)entity.getRNG().nextGaussian() * 2.5F, casing.getName(), true, 60, 0.5D, 20);
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
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.3125, -0.125, aiming ? -0.125 : -0.375D, 0, -0.08, 0, 0.01, -15F + (float)entity.getRNG().nextGaussian() * 5F, (float)entity.getRNG().nextGaussian() * 2.5F, casing.getName(), true, 60, 0.5D, 20);
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
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.3125, -0.125, aiming ? -0.125 * offset : -0.375D * offset, 0, -0.08, 0, 0.01, -15F + (float)entity.getRNG().nextGaussian() * 5F, (float)entity.getRNG().nextGaussian() * 2.5F, casing.getName(), true, 60, 0.5D, 20);
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
					SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
					if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.625, -0.125, aiming ? -0.125 : -0.375D, -0.12, 0.18, 0, 0.01, -15F + (float)entity.getRNG().nextGaussian() * 7.5F, (float)entity.getRNG().nextGaussian() * 5F, casing.getName(), true, 60, 0.5D, 20);
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
				SpentCasing casing = mag.getCasing(stack, ctx.inventory);
				if(casing != null) for(int i = 0; i < mag.getCapacity(stack); i++) CasingCreator.composeEffect(entity.worldObj, entity, 0.25, -0.125, -0.125, -0.05, 0, 0, 0.01, -6.5F + (float)entity.getRNG().nextGaussian() * 3F, (float)entity.getRNG().nextGaussian() * 5F, casing.getName());
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

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_CARBINE = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.CYCLE) {
			if(timer == 1) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.3125, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.21, -0.06, 0.01, -10F + (float)entity.getRNG().nextGaussian() * 2.5F, 2.5F + (float)entity.getRNG().nextGaussian() * 2F, casing.getName(), true, 60, 0.5D, 20);
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
					SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
					if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.4375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, -0.06, 0, 0.01, (float)entity.getRNG().nextGaussian() * 10F, (float)entity.getRNG().nextGaussian() * 10F, casing.getName());
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
					SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
					if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.4375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, -0.06, 0, 0.01, (float)entity.getRNG().nextGaussian() * 10F, (float)entity.getRNG().nextGaussian() * 10F, casing.getName());
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
				int toEject = mag.getAmountAfterReload(stack) - mag.getAmount(stack, ctx.inventory);
				SpentCasing casing = mag.getCasing(stack, ctx.inventory);
				if(casing != null) for(int i = 0; i < toEject; i++) CasingCreator.composeEffect(entity.worldObj, entity, 0.625, -0.1875, -0.375D, -0.12, 0.18, 0, 0.01, -15F + (float)entity.getRNG().nextGaussian() * 7.5F, (float)entity.getRNG().nextGaussian() * 5F, casing.getName(), true, 60, 0.5D, 20);
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
			int toEject = mag.getAmountAfterReload(stack) - mag.getAmount(stack, ctx.inventory);
			if(timer == 4 && toEject > 0) {
				SpentCasing casing = mag.getCasing(stack, ctx.inventory);
				if(casing != null) for(int i = 0; i < toEject; i++) CasingCreator.composeEffect(entity.worldObj, entity, 0.625, -0.1875, -0.375D, -0.12, 0.18, 0, 0.01, -15F * (float)entity.getRNG().nextGaussian() * 7.5F, (float)entity.getRNG().nextGaussian() * 5F, casing.getName(), true, 60, 0.5D, 20);
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
				SpentCasing casing = mag.getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.625, aiming ? -0.0625 : -0.25, aiming ? 0 : -0.375D, 0, 0.18, 0.12, 0.01, -5F + (float)entity.getRNG().nextGaussian() * 3.5F, -10F + entity.getRNG().nextFloat() * 5F, casing.getName(), true, 60, 0.5D, 20);
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

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_FLAMER_DAYBREAKER = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

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

		if(type == AnimType.CYCLE) {
			if(timer == 1) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.375, aiming ? 0 : -0.0625, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, -10F + (float)entity.getRNG().nextGaussian() * 5F, 10F + entity.getRNG().nextFloat() * 10F, casing.getName());
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);

		}
		if(type == AnimType.RELOAD) {
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 26) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
			if(timer == 40) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 20) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.impact", 0.5F, 1.6F);
			if(timer == 36) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
		}
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
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, -2.5F + (float)entity.getRNG().nextGaussian() * 5F, 10F + (float)entity.getRNG().nextFloat() * 15F, casing.getName());
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

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_UZI_AKIMBO = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.EQUIP) {
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.openLatch", 1F, 1.25F);
		}
		if(type == AnimType.CYCLE) {
			if(timer == 1) {
				int mult = ctx.configIndex == 0 ? -1 : 1;
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.375, -0.125, -0.375D * mult, 0, 0.18, -0.12 * mult, 0.01, -2.5F + (float)entity.getRNG().nextGaussian() * 5F, (10F + (float)entity.getRNG().nextFloat() * 15F) * mult, casing.getName());
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
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory); //turns out there's a reason why stovepipes look like that
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, -3F + (float)entity.getRNG().nextGaussian() * 2.5F, -15F + entity.getRNG().nextFloat() * -5F, casing.getName());
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 8) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunCock", 1F, 1F);
		}
		if(type == AnimType.RELOAD) {
			IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
			if(mag.getAmount(stack, ctx.inventory) == 0) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
				if(timer == 7) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			}
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
		if(type == AnimType.RELOAD_CYCLE) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunReload", 1F, 1F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunCockOpen", 1F, 1F);
			if(timer == 18) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunCockClose", 1F, 1F);
		}
		if(type == AnimType.JAMMED) {
			if(timer == 18) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.foley.gunWhack", 1F, 1F);
			if(timer == 25) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.foley.gunWhack", 1F, 1F);
			if(timer == 29) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.shotgunCockClose", 1F, 1F);
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
		boolean scoped = stack.getItem() == ModItems.gun_g3_zebra || WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_SCOPE);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack) && !scoped;

		if(type == AnimType.CYCLE) {
			if(timer == 0) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.5, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, (float)entity.getRNG().nextGaussian() * 5F, 12.5F + (float)entity.getRNG().nextFloat() * 5F, casing.getName());
			}
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.8F);
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.9F);

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
			if(timer == 26) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
			if(timer == 30) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_STINGER = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(entity.worldObj.isRemote) {
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

		if(type == AnimType.EQUIP) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:turret.howard_reload", 1F, 1F);
		}

		if(type == AnimType.CYCLE) {
			if(timer == 0) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.375, aiming ? 0 : -0.125, aiming ? 0 : -0.3125D, 0, 0.06, -0.18, 0.01, (float)entity.getRNG().nextGaussian() * 20F, 12.5F + (float)entity.getRNG().nextGaussian() * 7.5F, casing.getName());
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

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_SHREDDER_SEXY = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.CYCLE) {
			if(timer == 0 && ctx.config.getReceivers(stack)[0].getMagazine(stack).getType(stack, null) == XFactory12ga.g12_equestrian_bj) {
				ItemGunBaseNT.setTimer(stack, 0, 20);
			}
		}

		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
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

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_MINIGUN = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.CYCLE) {
			if(timer == 0) {
				int rounds = WeaponModManager.hasUpgrade(stack, ctx.configIndex, WeaponModManager.ID_MINIGUN_SPEED) ? 3 : 1;
				for(int i = 0; i < rounds; i++) {
					SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
					if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, aiming ? 0.125 : 0.5, aiming ? -0.125 : -0.25, aiming ? -0.25 : -0.5D, 0, 0.18, -0.12, 0.01, (float)entity.getRNG().nextGaussian() * 15F, (float)entity.getRNG().nextGaussian() * 15F, casing.getName());
				}
			}
			if(timer == (WeaponModManager.hasUpgrade(stack, 0, 207) ? 3 : 1)) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverSpin", 1F, 0.75F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.75F);
			if(timer == 1) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverSpin", 1F, 0.75F);
		}
		if(type == AnimType.RELOAD) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverSpin", 1F, 0.75F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverSpin", 1F, 0.75F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_MISSILE_LAUNCHER = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1.25F);
		}
		if(type == AnimType.RELOAD) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltOpen", 1F, 0.9F);
			if(timer == 30) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.insertCanister", 1F, 1F);
			if(timer == 42) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltClose", 1F, 0.9F);
		}

		if(type == AnimType.JAMMED || type == AnimType.INSPECT) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltOpen", 1F, 0.9F);
			if(timer == 27) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltClose", 1F, 0.9F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_TESLA = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.CYCLE) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.fire.shredderCycle", 0.25F, 1.25F);
		}
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.fire.shredderCycle", 0.25F, 1.25F);
		}
		if(type == AnimType.INSPECT) {
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:block.squeakyToy", 0.25F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_LASER_PISTOL = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1.5F);
		}

		if(type == AnimType.RELOAD) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 10) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1.25F);
			if(timer == 34) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1.25F);
			if(timer == 40) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1.25F);
		}
		
		if(type == AnimType.JAMMED) {
			if(timer == 10) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 1F);
			if(timer == 15) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1.25F);
			if(timer == 30) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.impact", 0.25F, 1.5F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_STG77 = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(ClientConfig.GUN_ANIMS_LEGACY.get()) {
			if(type == AnimType.CYCLE) {
				if(timer == 0) {
					SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
					if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, aiming ? 0.125 : 0.125, aiming ? -0.125 : -0.25, aiming ? -0.125 : -0.25D, 0, 0.18, -0.12, 0.01, (float)entity.getRNG().nextGaussian() * 5F, 7.5F + entity.getRNG().nextFloat() * 5F, casing.getName());
				}
				if(timer == 40) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 0.25F, 1.25F);
			}
			if(type == AnimType.CYCLE_DRY) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.8F);
				if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.9F);
				if(timer == 40) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 0.25F, 1.25F);
			}
			if(type == AnimType.RELOAD) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
				if(timer == 10) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
				if(timer == 24) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
				if(timer == 34) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			}
			if(type == AnimType.INSPECT) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
				if(timer == 10) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);

				if(timer == 114) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
				if(timer == 124) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			}
		} else {
			if(type == AnimType.CYCLE) {
				if(timer == 0) {
					SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
					if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, aiming ? 0.125 : 0.25, aiming ? -0.125 : -0.25, aiming ? -0.125 : -0.25D, 0, 0.18, -0.12, 0.01, (float)entity.getRNG().nextGaussian() * 5F, 7.5F + entity.getRNG().nextFloat() * 5F, casing.getName());
				}
				if(timer == 40) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 0.25F, 1.25F);
			}
			if(type == AnimType.CYCLE_DRY) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.8F);
				if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.9F);
				if(timer == 40) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 0.25F, 1.25F);
			}
			if(type == AnimType.RELOAD) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
				if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
				if(timer == 32) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.impact", 0.25F, 1.25F);
				if(timer == 38) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
				if(timer == 43) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			}
			if(type == AnimType.INSPECT) {
				if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.9F);
				if(timer == 11) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);

				if(timer == 72) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 1F);
				if(timer == 84) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			}
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_TAU = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.SPINUP && entity.worldObj.isRemote) {
			AudioWrapper runningAudio = ItemGunBaseNT.loopedSounds.get(entity);

			if(timer < 300) {
				if(runningAudio == null || !runningAudio.isPlaying()) {
					AudioWrapper audio = MainRegistry.proxy.getLoopedSound("hbm:weapon.fire.tauLoop", (float) entity.posX, (float) entity.posY, (float) entity.posZ, 1F, 15F, 0.75F, 10);
					audio.updatePitch(0.75F);
					ItemGunBaseNT.loopedSounds.put(entity, audio);
					audio.startSound();
				}
				if(runningAudio != null && runningAudio.isPlaying()) {
					runningAudio.keepAlive();
					runningAudio.updatePosition((float) entity.posX, (float) entity.posY, (float) entity.posZ);
					runningAudio.updatePitch(0.75F + timer * 0.01F);
				}
			} else {
				if(runningAudio != null && runningAudio.isPlaying()) runningAudio.stopSound();
			}
		}
		//stop sound due to state change
		if(type != AnimType.SPINUP && entity.worldObj.isRemote) {
			AudioWrapper runningAudio = ItemGunBaseNT.loopedSounds.get(entity);
			if(runningAudio != null && runningAudio.isPlaying()) runningAudio.stopSound();
		}
		if(entity.worldObj.isRemote) return;

		if(type == AnimType.CYCLE) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.fire.tau", 0.5F, 0.9F + entity.getRNG().nextFloat() * 0.2F);
		}

		if(type == AnimType.ALT_CYCLE) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.fire.tau", 0.5F, 0.7F + entity.getRNG().nextFloat() * 0.2F);
		}

		if(type == AnimType.SPINUP) {
			if(timer % 10 == 0 && timer < 130) {
				IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
				if(mag.getAmount(stack, ctx.inventory) <= 0) {
					ItemGunBaseNT.playAnimation(ctx.getPlayer(), stack, AnimType.CYCLE_DRY, ctx.configIndex);
					return;
				}
				mag.useUpAmmo(stack, ctx.inventory, 1);
			}

			if(timer > 200) {
				ItemGunBaseNT.playAnimation(ctx.getPlayer(), stack, AnimType.CYCLE_DRY, ctx.configIndex);

				entity.attackEntityFrom(ModDamageSource.tauBlast, 1_000F);

				ItemGunBaseNT.setWear(stack, ctx.configIndex, Math.min(ItemGunBaseNT.getWear(stack, ctx.configIndex) + 10_000F, ctx.config.getDurability(stack)));

				entity.worldObj.playSoundEffect(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, "hbm:entity.ufoBlast", 5.0F, 0.9F);
				entity.worldObj.playSoundEffect(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, "fireworks.blast", 5.0F, 0.5F);

				float yaw = entity.worldObj.rand.nextFloat() * 180F;
				for(int i = 0; i < 3; i++) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "plasmablast");
					data.setFloat("r", 1.0F);
					data.setFloat("g", 0.8F);
					data.setFloat("b", 0.5F);
					data.setFloat("pitch", -60F + 60F * i);
					data.setFloat("yaw", yaw);
					data.setFloat("scale", 2F);
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ),
							new TargetPoint(entity.dimension, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, 100));
				}
			}
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_FATMAN = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.fatmanFull", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_LASRIFLE = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1.5F);
		}

		if(type == AnimType.RELOAD) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 18) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.impact", 0.25F, 1F);
			if(timer == 30) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
			if(timer == 38) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}

		if(type == AnimType.INSPECT) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
			if(timer == 20) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}

		if(type == AnimType.JAMMED) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 1F);
			if(timer == 22) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
			if(timer == 30) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_COILGUN = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.coilgunReload", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_HANGMAN = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
		}

		if(type == AnimType.RELOAD) {

			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.8F);
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 0.8F);
			if(timer == 25) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			if(timer == 35) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.75F);

			if(timer == 10) {
				Receiver rec = ctx.config.getReceivers(stack)[0];
				IMagazine mag = rec.getMagazine(stack);
				SpentCasing casing = mag.getCasing(stack, ctx.inventory);
				if(casing != null) for(int i = 0; i < mag.getCapacity(stack); i++) CasingCreator.composeEffect(entity.worldObj, entity, 0.25, -0.25, -0.125, -0.05, 0, 0, 0.01, -6.5F + (float)entity.getRNG().nextGaussian() * 3F, (float)entity.getRNG().nextGaussian() * 5F, casing.getName());
			}
		}

		if(type == AnimType.INSPECT) {
			if(timer == 16 && ctx.getPlayer() != null) {
				MovingObjectPosition mop = EntityDamageUtil.getMouseOver(ctx.getPlayer(), 3.0D);
				if(mop != null) {
					if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
						float damage = 10F;
						mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(ctx.getPlayer()), damage);
						mop.entityHit.motionX *= 2;
						mop.entityHit.motionZ *= 2;
						entity.worldObj.playSoundAtEntity(mop.entityHit, "hbm:weapon.fire.smack", 1F, 0.9F + entity.getRNG().nextFloat() * 0.2F);
					}
					if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
						Block b = entity.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
						entity.worldObj.playSoundEffect(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, b.stepSound.getStepResourcePath(), 2F, 0.9F + entity.getRNG().nextFloat() * 0.2F);
					}
				}
			}
		}

		if(type == AnimType.JAMMED) {
			if(timer == 10) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.8F);
			if(timer == 15) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 0.8F);
			if(timer == 20) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
			if(timer == 25) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.75F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_BOLTER = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.CYCLE) {
			if(timer == 1) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.5, aiming ? 0 : -0.125, aiming ? -0.0625 : -0.25D, 0, 0.18, -0.12, 0.01, -10F + (float)entity.getRNG().nextGaussian() * 5F, 10F + entity.getRNG().nextFloat() * 10F, casing.getName());
			}
		}

		if(type == AnimType.RELOAD) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magRemove", 1F, 1F);
			if(timer == 26) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magInsert", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_FOLLY = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 20) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.screw", 1F, 1F);
			if(timer == 80) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.insertRocket", 1F, 1F);
			if(timer == 120) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.screw", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_DOUBLE_BARREL = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.RELOAD) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.75F);
			if(timer == 19) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 0.9F);
			if(timer == 29) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.8F);

			if(timer == 12) {
				IMagazine mag = ctx.config.getReceivers(stack)[0].getMagazine(stack);
				int toEject = mag.getAmountAfterReload(stack) - mag.getAmount(stack, ctx.inventory);
				SpentCasing casing = mag.getCasing(stack, ctx.inventory);
				if(casing != null) for(int i = 0; i < toEject; i++) CasingCreator.composeEffect(entity.worldObj, entity, 0, -0.1875, -0.375D, -0.24, 0.18, 0, 0.01, -20F + (float)entity.getRNG().nextGaussian() * 5F, (float)entity.getRNG().nextGaussian() * 2.5F, casing.getName(), true, 60, 0.5D, 20);
			}
		}

		if(type == AnimType.INSPECT) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverCock", 1F, 0.75F);
			if(timer == 19) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 0.8F);
		}

		if(type == AnimType.CYCLE_DRY) {
			if(timer == 2) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 1F);
		}
	};

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_ABERRATOR = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.RELOAD) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallRemove", 1F, 0.75F);
			if(timer == 32) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.magSmallInsert", 1F, 0.75F);
			if(timer == 42) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.75F);
		}

		if(type == AnimType.CYCLE) {
			if(timer == 1) {
				int cba = (stack.getItem() == ModItems.gun_aberrator_eott && ctx.configIndex == 0) ? -1 : 1;
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.5, aiming ? 0 : -0.125, aiming ? -0.0625 : -0.25D * cba, -0.05, 0.25, -0.05 * cba, 0.01, -10F + (float)entity.getRNG().nextGaussian() * 10F, (float)entity.getRNG().nextGaussian() * 12.5F, casing.getName());
			}
		}

		if(type == AnimType.CYCLE_DRY) {
			if(timer == 1) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.75F);
			if(timer == 9) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.pistolCock", 1F, 0.75F);
		}
	};
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_MAS36 = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack) && !WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_SCOPE);

		if(type == AnimType.EQUIP) {
			if(timer == 10) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.openLatch", 1F, 1F);
			if(timer == 18) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.revolverClose", 1F, 1F);
		}
		
		if(type == AnimType.CYCLE) {
			if(timer == 7) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltOpen", 0.5F, 1F);
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltClose", 0.5F, 1F);
			if(timer == 12) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity,
						0.375, aiming ? 0 : -0.125, aiming ? 0 : -0.25D,
						-0.05, 0.2, -0.025,
						0.01, -10F + (float) entity.getRNG().nextGaussian() * 10F, (float) entity.getRNG().nextGaussian() * 12.5F, casing.getName());
			}
		}
		
		if(type == AnimType.CYCLE_DRY) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.dryFireClick", 1F, 0.75F);
			if(timer == 7) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltOpen", 0.5F, 1F);
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltClose", 0.5F, 1F);
		}

		if(type == AnimType.RELOAD) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltOpen", 1F, 1F);
			if(timer == 20) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.rifleCock", 1F, 1F);
			if(timer == 36) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltClose", 1F, 1F);
		}

		if(type == AnimType.JAMMED) {
			if(timer == 5) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltOpen", 0.5F, 1F);
			if(timer == 12) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltClose", 0.5F, 1F);
			if(timer == 16) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltOpen", 0.5F, 1F);
			if(timer == 23) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltClose", 0.5F, 1F);
		}

		if(type == AnimType.INSPECT) {
			if(timer == 0) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltOpen", 0.5F, 1F);
			if(timer == 17) entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.reload.boltClose", 0.5F, 1F);
		}
	};
}
