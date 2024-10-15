package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.GunState;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GunStateDecider {
	
	/** 
	 * The meat and bones of the gun system's state machine.
	 * This standard decider can handle guns with an automatic primary receiver, as well as one receiver's reloading state.
	 * It supports draw delays as well as semi and auto fire
	 */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_DECIDER = (stack, ctx) -> {
		int index = ctx.configIndex;
		GunState lastState = ItemGunBaseNT.getState(stack, index);
		deciderStandardFinishDraw(stack, lastState, index);
		deciderStandardClearJam(stack, lastState, index);
		deciderStandardReload(stack, ctx, lastState, 0, index);
		deciderAutoRefire(stack, ctx, lastState, 0, index, () -> { return ItemGunBaseNT.getPrimary(stack, index); });
	};
	
	/** Transitions the gun from DRAWING to IDLE */
	public static void deciderStandardFinishDraw(ItemStack stack, GunState lastState, int index) {
		
		//transition to idle
		if(lastState == GunState.DRAWING) {
			ItemGunBaseNT.setState(stack, index, GunState.IDLE);
			ItemGunBaseNT.setTimer(stack, index, 0);
		}
	}
	
	/** Transitions the gun from DRAWING to IDLE */
	public static void deciderStandardClearJam(ItemStack stack, GunState lastState, int index) {
		
		//transition to idle
		if(lastState == GunState.JAMMED) {
			ItemGunBaseNT.setState(stack, index, GunState.IDLE);
			ItemGunBaseNT.setTimer(stack, index, 0);
		}
	}
	
	/** Triggers a reload action on the first receiver. If the mag is not full and reloading is still possible, set to RELOADING, otherwise IDLE */
	public static void deciderStandardReload(ItemStack stack, LambdaContext ctx, GunState lastState, int recIndex, int gunIndex) {
		
		if(lastState == GunState.RELOADING) {
			
			EntityPlayer player = ctx.player;
			GunConfig cfg = ctx.config;
			Receiver rec = cfg.getReceivers(stack)[recIndex];
			IMagazine mag = rec.getMagazine(stack);
			
			mag.reloadAction(stack, player);
			
			//if after reloading the gun can still reload, assume a tube mag and resume reloading
			if(mag.canReload(stack, player)) {
				ItemGunBaseNT.setState(stack, gunIndex, GunState.RELOADING);
				ItemGunBaseNT.setTimer(stack, gunIndex, rec.getReloadCycleDuration(stack));
				ItemGunBaseNT.playAnimation(player, stack, AnimType.RELOAD_CYCLE, gunIndex);
			//if no more reloading can be done, go idle
			} else {
				
				if(getStandardJamChance(stack, cfg, gunIndex) > player.getRNG().nextFloat()) {
					ItemGunBaseNT.setState(stack, gunIndex, GunState.JAMMED);
					ItemGunBaseNT.setTimer(stack, gunIndex, rec.getJamDuration(stack));
					ItemGunBaseNT.playAnimation(player, stack, AnimType.JAMMED, gunIndex);
				} else {
					ItemGunBaseNT.setState(stack, gunIndex, GunState.DRAWING);
					int duration = rec.getReloadEndDuration(stack) + (mag.getAmountBeforeReload(stack) <= 0 ? rec.getReloadCockOnEmpty(stack) : 0);
					ItemGunBaseNT.setTimer(stack, gunIndex, duration);
					ItemGunBaseNT.playAnimation(player, stack, AnimType.RELOAD_END, gunIndex);
				}
			}
			
			mag.setAmountAfterReload(stack, mag.getAmount(stack));
		}
	}
	
	public static float getStandardJamChance(ItemStack stack, GunConfig config, int index) {
		float percent = (float) ItemGunBaseNT.getWear(stack, index) / config.getDurability(stack);
		if(percent < 0.66F) return 0F;
		return Math.min((percent - 0.66F) * 4F, 1F);
	}
	
	/** Triggers a re-fire of the primary if the fire delay has expired, the left mouse button is down and re-firing is enabled, otherwise switches to IDLE */
	public static void deciderAutoRefire(ItemStack stack, LambdaContext ctx, GunState lastState, int recIndex, int gunIndex, BooleanSupplier refireCondition) {
		
		if(lastState == GunState.COOLDOWN) {

			EntityPlayer player = ctx.player;
			GunConfig cfg = ctx.config;
			Receiver rec = cfg.getReceivers(stack)[recIndex];
			
			//if the gun supports re-fire (i.e. if it's an auto)
			if(rec.getRefireOnHold(stack) && refireCondition.getAsBoolean()) {
				//if there's a bullet loaded, fire again
				if(rec.getCanFire(stack).apply(stack, ctx)) {
					rec.getOnFire(stack).accept(stack, ctx);
					ItemGunBaseNT.setState(stack, gunIndex, GunState.COOLDOWN);
					ItemGunBaseNT.setTimer(stack, gunIndex, rec.getDelayAfterFire(stack));
					
					if(rec.getFireSound(stack) != null) player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, rec.getFireSound(stack), rec.getFireVolume(stack), rec.getFirePitch(stack));
					
					int remaining = rec.getRoundsPerCycle(stack) - 1;
					for(int i = 0; i < remaining; i++) if(rec.getCanFire(stack).apply(stack, ctx)) rec.getOnFire(stack).accept(stack, ctx);
				//if not, revert to idle
				} else /*if(rec.getDoesDryFire(stack)) {
					ItemGunBaseNT.setState(stack, gunIndex, GunState.DRAWING);
					ItemGunBaseNT.setTimer(stack, gunIndex, rec.getDelayAfterDryFire(stack));
					ItemGunBaseNT.playAnimation(player, stack, AnimType.CYCLE_DRY, gunIndex);
				} else*/ {
					ItemGunBaseNT.setState(stack, gunIndex, GunState.IDLE);
					ItemGunBaseNT.setTimer(stack, gunIndex, 0);
				}
			//if not, go idle
			} else {
				ItemGunBaseNT.setState(stack, gunIndex, GunState.IDLE);
				ItemGunBaseNT.setTimer(stack, gunIndex, 0);
			}
		}
	}
}
