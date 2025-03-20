package com.hbm.items.weapon.sedna.mods;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.factory.Orchestras;
import com.hbm.items.weapon.sedna.factory.XFactory44;
import com.hbm.items.weapon.sedna.factory.XFactory762mm;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.util.EntityDamageUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;

public class WeaponModMASBayonet extends WeaponModBase {

	public WeaponModMASBayonet(int id) {
		super(id, "BAYONET");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == GunConfig.FUN_ANIMNATIONS) return (T) LAMBDA_MAS36_ANIMS;
		if(key == GunConfig.I_INSPECTDURATION) return cast(30, base);
		if(key == GunConfig.CON_ONPRESSSECONDARY) return (T) XFactory44.SMACK_A_FUCKER;
		if(key == GunConfig.CON_ORCHESTRA) return (T) ORCHESTRA_MAS36;
		if(key == GunConfig.I_INSPECTCANCEL) return cast(false, base);
		return base;
	}
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_MAS36 = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);

		if(type == AnimType.INSPECT) {
			
			if(timer == 15 && ctx.getPlayer() != null) {
				MovingObjectPosition mop = EntityDamageUtil.getMouseOver(ctx.getPlayer(), 3.0D);
				if(mop != null) {
					if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
						float damage = 10F;
						mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(ctx.getPlayer()), damage);
						mop.entityHit.motionX *= 2;
						mop.entityHit.motionZ *= 2;
						entity.worldObj.playSoundAtEntity(mop.entityHit, "hbm:weapon.fire.stab", 1F, 0.9F + entity.getRNG().nextFloat() * 0.2F);
					}
					if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
						Block b = entity.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
						entity.worldObj.playSoundEffect(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, b.stepSound.getStepResourcePath(), 2F, 0.9F + entity.getRNG().nextFloat() * 0.2F);
					}
				}
			}
			return;
		}
		
		Orchestras.ORCHESTRA_MAS36.accept(stack, ctx);
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_MAS36_ANIMS = (stack, type) -> {
		switch(type) {
		case INSPECT: return new BusAnimation()
				.addBus("STAB", new BusAnimationSequence().addPos(0, 1, -2, 250, IType.SIN_DOWN).hold(250).addPos(0, 1, 5, 250, IType.SIN_UP).hold(250).addPos(0, 0, 0, 500, IType.SIN_FULL));
		}
		
		return XFactory762mm.LAMBDA_MAS36_ANIMS.apply(stack, type);
	};
}
