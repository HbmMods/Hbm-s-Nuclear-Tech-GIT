package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.GunState;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.GunAnimationPacket;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
		
		if(ItemGunBaseNT.getState(stack) == GunState.IDLE && ctx.config.getReceivers(stack)[0].getMagazine(stack).canReload(stack, ctx.player)) {
			ItemGunBaseNT.setState(stack, GunState.RELOADING);
			ItemGunBaseNT.setTimer(stack, ctx.config.getReceivers(stack)[0].getReloadDuration(stack));
		}
	};
	
	/**
	 * If IDLE and ammo is loaded, fire and set to JUST_FIRED. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_STANDARD_FIRE = (stack, ctx) -> {
		
		if(ItemGunBaseNT.getState(stack) == GunState.IDLE && ctx.config.getReceivers(stack)[0].getCanFire(stack).apply(stack, ctx)) {
			ItemGunBaseNT.setState(stack, GunState.COOLDOWN);
			ItemGunBaseNT.setTimer(stack, ctx.config.getReceivers(stack)[0].getDelayAfterFire(stack));
			ctx.config.getReceivers(stack)[0].getOnFire(stack).accept(stack, ctx);
		}
	};
	
	/** Toggles isAiming. Used by keybinds. */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_TOGGLE_AIM = (stack, ctx) -> { ItemGunBaseNT.setIsAiming(stack, !ItemGunBaseNT.getIsAiming(stack)); };
	
	/** Returns true if the mag has ammo in it. Used by keybind functions on whether to fire, and deciders on whether to trigger a refire, */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_STANDARD_CAN_FIRE = (stack, ctx) -> { return ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmount(stack) > 0; };

	
	
	
	/** JUMPER - bypasses mag testing and just allows constant fire */
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_DEBUG_CAN_FIRE = (stack, ctx) -> { return true; };
	
	/** simply plays a sound to indicate that the keybind has triggered */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_DEBUG_FIRE = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		if(player instanceof EntityPlayerMP) PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
		
		double sideOffset = ItemGunBaseNT.getIsAiming(stack) ? 0 : -0.2D;
		float aim = ItemGunBaseNT.getIsAiming(stack) ? 0.25F : 1F;
		Receiver primary = ctx.config.getReceivers(stack)[0];
		
		EntityBulletBaseMK4 mk4 = new EntityBulletBaseMK4(player, (BulletConfig) primary.getMagazine(stack).getType(stack), primary.getBaseDamage(stack), primary.getSpreadMod(stack) * aim, sideOffset, -0.1, 0.75);
		player.worldObj.spawnEntityInWorld(mk4);
		player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.shotgunShoot", 1F, 1F);
	};
	
	/** No reload, simply play inspect animation */
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_DEBUG_RELOAD = (stack, ctx) -> {
		EntityPlayer player = ctx.player;
		if(player instanceof EntityPlayerMP) PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.INSPECT.ordinal()), (EntityPlayerMP) player);
	};
	
	/** anims for the DEBUG revolver, mostly a copy of the li'lpip but with some fixes regarding the cylinder movement */
	public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_DEBUG_ANIMS = (stack, type) -> {
		switch(type) {
		case CYCLE: 
			return new BusAnimation()
					.addBus("RECOIL", new BusAnimationSequence().addKeyframePosition(0, 0, 0, 50).addKeyframePosition(0, 0, -3, 50).addKeyframePosition(0, 0, 0, 250))
					.addBus("HAMMER", new BusAnimationSequence().addKeyframePosition(0, 0, 1, 50).addKeyframePosition(0, 0, 1, 300 + 100).addKeyframePosition(0, 0, 0, 200))
					.addBus("DRUM", new BusAnimationSequence().addKeyframePosition(0, 0, 0, 350 + 100).addKeyframePosition(0, 0, 1, 200));
		case CYCLE_EMPTY: break;
		case ALT_CYCLE: break;
		case EQUIP: return new BusAnimation().addBus("ROTATE", new BusAnimationSequence().addKeyframePosition(-360, 0, 0, 350));
		case RELOAD: break;
		case RELOAD_CYCLE: break;
		case RELOAD_EMPTY: break;
		case RELOAD_END: break;
		case SPINDOWN: break;
		case SPINUP: break;
		case INSPECT: break;
		}
		
		return null;
	};
}
