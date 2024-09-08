package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.weapon.sedna.ItemGunBase;
import com.hbm.items.weapon.sedna.ItemGunBase.GunState;
import com.hbm.items.weapon.sedna.ItemGunBase.LambdaContext;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * "LEGO" - i.e. standardized building blocks which can be used to set up gun configs easily.
 * 
 * @author hbm
 */
public class Lego {
	
	/**
	 * If IDLE and the mag of receiver 0 can be loaded, set state to RELOADING. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_RELOAD = (stack, ctx) -> {
		
		if(ItemGunBase.getState(stack) == GunState.IDLE && ctx.config.getReceivers(stack)[0].getMagazine(stack).canReload(stack, ctx.player)) {
			ItemGunBase.setState(stack, GunState.RELOADING);
			ItemGunBase.setTimer(stack, ctx.config.getReceivers(stack)[0].getReloadDuration(stack));
		}
	};
	
	/**
	 * If IDLE and ammo is loaded, fire and set to JUST_FIRED. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_FIRE = (stack, ctx) -> {
		
		if(ItemGunBase.getState(stack) == GunState.IDLE && ctx.config.getReceivers(stack)[0].getCanFire(stack).apply(stack, ctx)) {
			ItemGunBase.setState(stack, GunState.JUST_FIRED);
			ItemGunBase.setTimer(stack, ctx.config.getReceivers(stack)[0].getDelayAfterFire(stack));
			ctx.config.getReceivers(stack)[0].getOnFire(stack).accept(stack, ctx);
		}
	};
	
	/** Toggles isAiming. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_TOGGLE_AIM = (stack, ctx) -> { ItemGunBase.setIsAiming(stack, !ItemGunBase.getIsAiming(stack)); };
	
	/** Returns true if the mag has ammo in it. Used by keybind functions on whether to fire, and deciders on whether to trigger a refire, */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_STANDARD_CAN_FIRE = (stack, ctx) -> { return ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmount(stack) > 0; };

	
	
	
	/** JUMPER - bypasses mag testing and just allows constant fire */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_DEBUG_CAN_FIRE = (stack, ctx) -> { return true; };
	/** simply plays a sound to indicate that the keybind has triggered */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_DEBUG_FIRE = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.shotgunShoot", 1F, 1F);
	};
}
