package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBase;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBase.GunState;
import com.hbm.items.weapon.sedna.ItemGunBase.LambdaContext;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GunStateDecider {
	
	/** 
	 * The meat and bones of the gun system's state machine.
	 * This standard decider can handle guns with an automatic primary receiver, as well as one receiver's reloading state.
	 * It supports draw delays as well as semi and auto fire
	 */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_DECIDER = (stack, ctx) -> {
		GunState lastState = ItemGunBase.getState(stack);
		deciderStandardFinishDraw(stack, lastState);
		deciderStandardReload(stack, ctx, lastState, 0);
		deciderAutoRefire(stack, ctx, lastState, 0, () -> { return ItemGunBase.getPrimary(stack); });
	};
	
	/** Transitions the gun from DRAWING to IDLE */
	public static void deciderStandardFinishDraw(ItemStack stack, GunState lastState) {
		
		//transition to idle
		if(lastState == GunState.DRAWING) {
			ItemGunBase.setState(stack, GunState.IDLE);
			ItemGunBase.setTimer(stack, 0);
		}
	}
	
	/** Triggers a reload action on the first receiver. If the mag is not full and reloading is still possible, set to RELOADING, otherwise IDLE */
	public static void deciderStandardReload(ItemStack stack, LambdaContext ctx, GunState lastState, int recIndex) {
		
		if(lastState == GunState.RELOADING) {
			
			EntityPlayer player = ctx.player;
			GunConfig cfg = ctx.config;
			Receiver rec = cfg.getReceivers(stack)[recIndex];
			
			rec.getMagazine(stack).reloadAction(stack, player);
			
			//if after reloading the gun can still reload, assume a tube mag and resume reloading
			if(cfg.getReceivers(stack)[recIndex].getMagazine(stack).canReload(stack, player)) {
				ItemGunBase.setState(stack, GunState.RELOADING);
				ItemGunBase.setTimer(stack, cfg.getReceivers(stack)[recIndex].getReloadDuration(stack));
			//if no more reloading can be done, go idle
			} else {
				ItemGunBase.setState(stack, GunState.IDLE);
				ItemGunBase.setTimer(stack, 0);
			}
		}
	}
	
	/** Triggers a re-fire of the primary if the fire delay has expired, the left mouse button is down and re-firing is enabled, otherwise switches to IDLE */
	public static void deciderAutoRefire(ItemStack stack, LambdaContext ctx, GunState lastState, int recIndex, BooleanSupplier refireCondition) {
		
		if(lastState == GunState.JUST_FIRED) {

			GunConfig cfg = ctx.config;
			Receiver rec = cfg.getReceivers(stack)[recIndex];
			
			//if the gun supports re-fire (i.e. if it's an auto)
			if(rec.getRefireOnHold(stack) && refireCondition.getAsBoolean()) {
				//if there's a bullet loaded, fire again
				if(rec.getCanFire(stack).apply(stack, ctx)) {
					rec.getOnFire(stack).accept(stack, ctx);
					ItemGunBase.setState(stack, GunState.JUST_FIRED);
					ItemGunBase.setTimer(stack, rec.getDelayAfterFire(stack));
				//if not, revert to idle
				} else {
					ItemGunBase.setState(stack, GunState.IDLE);
					ItemGunBase.setTimer(stack, 0);
				}
			//if not, go idle
			} else {
				ItemGunBase.setState(stack, GunState.IDLE);
				ItemGunBase.setTimer(stack, 0);
			}
		}
	}
}
