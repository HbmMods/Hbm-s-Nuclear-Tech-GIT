package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.NotableComments;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.factory.ConfettiUtil;
import com.hbm.items.weapon.sedna.factory.XFactoryPA;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.AnimationEnums.GunAnimation;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.util.EntityDamageUtil;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;

@NotableComments
public class ArmorRPAMelee implements IPAMelee {

	@Override public void clickPrimary(ItemStack stack, LambdaContext ctx) { XFactoryPA.doSwing(stack, ctx, GunAnimation.CYCLE, 14); }
	@Override public void clickSecondary(ItemStack stack, LambdaContext ctx) { XFactoryPA.doSwing(stack, ctx, GunAnimation.ALT_CYCLE, 20); }
	
	@Override
	public void orchestra(ItemStack stack, LambdaContext ctx) {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		GunAnimation type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		
		// refire check so you can just continuously beat the shit out of someone
		if(type == GunAnimation.CYCLE && timer == 14 && ItemGunBaseNT.getPrimary(stack, 0)) {
			XFactoryPA.doSwing(stack, ctx, GunAnimation.CYCLE, 14);
		}
		
		boolean swings = type == GunAnimation.CYCLE && (timer == 3 || timer == 9);
		boolean slap = type == GunAnimation.ALT_CYCLE && timer == 8;
		
		if((swings || slap) && ctx.getPlayer() != null) {
			MovingObjectPosition mop = EntityDamageUtil.getMouseOver(ctx.getPlayer(), 3.0D, 0.5D);
			
			if(mop != null) {
				if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
					float damage = swings ? XFactoryPA.MELEE_RPA_SWINGS_DAMAGE.get() : XFactoryPA.MELEE_RPA_SLAP_DAMAGE.get();
					float knockback = swings ? 0F : 1.5F;
					float dt = swings ? 5F : 15F;
					float pierce = swings ? 0.1F : 0.25F;
					
					if(mop.entityHit instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase) mop.entityHit;
						if(living.getMaxHealth() >= 100) damage *= XFactoryPA.MELEE_RPA_LARGE_MULT.get();
						EntityDamageUtil.attackEntityFromNT((EntityLivingBase) mop.entityHit, DamageSource.causePlayerDamage(ctx.getPlayer()), damage, true, false, knockback, dt, pierce);
						if(living.getRNG().nextInt(slap ? 3 : 10) == 0 && !living.isEntityAlive()) ConfettiUtil.gib(living);
					} else {
						mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(ctx.getPlayer()), damage);
					}
					
					entity.worldObj.playSoundAtEntity(mop.entityHit, "hbm:weapon.fire.smack", 1F, 0.9F + entity.getRNG().nextFloat() * 0.2F);
				}
				if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
					Block b = entity.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
					entity.worldObj.playSoundEffect(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, b.stepSound.getStepResourcePath(), 2F, 0.9F + entity.getRNG().nextFloat() * 0.2F);
				}
			}
		}
	}
	
	@Override
	public BusAnimation playAnim(ItemStack stack, GunAnimation type) {
		if(type == GunAnimation.EQUIP) return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().setPos(-1, 0, 0).addPos(0, 0, 0, 250, IType.SIN_DOWN));
		if(type == GunAnimation.CYCLE) return new BusAnimation()
				.addBus("SWINGRIGHT", new BusAnimationSequence().addPos(1, 0, 0, 150, IType.SIN_DOWN).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("SWINGLEFT", new BusAnimationSequence().addPos(0, 0, 0, 300).addPos(1, 0, 0, 150, IType.SIN_DOWN).addPos(0, 0, 0, 250, IType.SIN_FULL));
		if(type == GunAnimation.ALT_CYCLE) return new BusAnimation()
				.addBus("SLAPTURN", new BusAnimationSequence().addPos(1, 0, 0, 250, IType.LINEAR).hold(150).addPos(0, 0, 0, 350, IType.LINEAR))
				.addBus("SLAP", new BusAnimationSequence().hold(250).addPos(1, 0, 0, 150, IType.SIN_DOWN).addPos(0, 0, 0, 350, IType.SIN_FULL));
			
		return null;
	}

	@Override public void setupFirstPerson(ItemStack stack) { }
	
	@Override
	public void renderFirstPerson(ItemStack stack) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.rpa_arm);
		
		GL11.glTranslated(0, -1.5, 0.5);
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);
		
		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double swingRight = HbmAnimations.getRelevantTransformation("SWINGRIGHT")[0];
		double swingLeft = HbmAnimations.getRelevantTransformation("SWINGLEFT")[0];
		double slapTurn = HbmAnimations.getRelevantTransformation("SLAPTURN")[0];
		double slap = HbmAnimations.getRelevantTransformation("SLAP")[0];
		
		double forwardTilt = 60 - 60 * equip[0];
		double offsetOutward = 3;
		double roll = 60;
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(-12 * swingLeft + 2 * slapTurn - 5 * slap, 6 * slap, 5 * swingLeft + 8 * slap);
		GL11.glRotated(forwardTilt - swingRight * 20, 1, 0, 0);
		
		GL11.glTranslated(offsetOutward, 0, 0);
		GL11.glTranslated(6, 8, 0);
		GL11.glRotated(60 * swingLeft + 45 * slap, 0, 0, 1);
		GL11.glRotated(roll + 15 * swingLeft + 45 * slapTurn, 0, 1, 0);
		GL11.glTranslated(-6, -8, 0);
		ResourceManager.armor_remnant.renderPart("LeftArm");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		
		GL11.glTranslated(12 * swingRight - 2 * slapTurn + 5 * slap, 6 * slap, 5 * swingRight + 8 * slap);
		GL11.glRotated(forwardTilt - swingLeft * 20, 1, 0, 0);
		
		GL11.glTranslated(-offsetOutward, 0, 0);
		GL11.glTranslated(-6, 8, 0);
		GL11.glRotated(-60 * swingRight - 45 * slap, 0, 0, 1);
		GL11.glRotated(-roll - 15 * swingRight - 45 * slapTurn, 0, 1, 0);
		GL11.glTranslated(6, -8, 0);
		ResourceManager.armor_remnant.renderPart("RightArm");
		GL11.glPopMatrix();
	}
}
