package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderHenry extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.25F * offset, -1F * offset, 1.75F * offset,
				0, -5 / 8D, 1);

		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		double r = -2.5 * aimingProgress;
		GL11.glRotated(r, 1, 0, 0);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.henry_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] sight = HbmAnimations.getRelevantTransformation("SIGHT");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] hammer = HbmAnimations.getRelevantTransformation("HAMMER");
		double[] lever = HbmAnimations.getRelevantTransformation("LEVER");
		double[] turn = HbmAnimations.getRelevantTransformation("TURN");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] twist = HbmAnimations.getRelevantTransformation("TWIST");
		double[] bullet = HbmAnimations.getRelevantTransformation("BULLET");
		double[] yeet = HbmAnimations.getRelevantTransformation("YEET");
		double[] roll = HbmAnimations.getRelevantTransformation("ROLL");

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glTranslated(recoil[0] * 2, recoil[1], recoil[2]);
		GL11.glRotated(recoil[2] * 5, 1, 0, 0);
		GL11.glRotated(turn[2], 0, 0, 1);
		
		GL11.glTranslated(yeet[0], yeet[1], yeet[2]);
		
		GL11.glTranslated(0, 1, 0);
		GL11.glRotated(roll[2], 0, 0, 1);
		GL11.glTranslated(0, -1, 0);

		GL11.glTranslated(0, -4, 4);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 4, -4);

		GL11.glTranslated(0, 2, -4);
		GL11.glRotated(equip[0], -1, 0, 0);
		GL11.glTranslated(0, -2, 4);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, 8);
		GL11.glRotated(turn[2], 0, 0, -1);
		GL11.glRotated(90, 0, 1, 0);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.25D);
		GL11.glPopMatrix();
		
		ResourceManager.henry.renderPart("Gun");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.25, -0.1875);
		GL11.glRotated(sight[0], 1, 0, 0);
		GL11.glTranslated(0, -1.25, 0.1875);
		ResourceManager.henry.renderPart("Sight");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.625, -3);
		GL11.glRotated(-30 + hammer[0], 1, 0, 0);
		GL11.glTranslated(0, -0.625, 3);
		ResourceManager.henry.renderPart("Hammer");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.25, -2.3125);
		GL11.glRotated(lever[0], 1, 0, 0);
		GL11.glTranslated(0, -0.25, 2.3125);
		ResourceManager.henry.renderPart("Lever");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, 0);
		GL11.glRotated(twist[2], 0, 0, 1);
		GL11.glTranslated(0, -1, 0);
		ResourceManager.henry.renderPart("Front");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(bullet[0], bullet[1], bullet[2] - 1);
		ResourceManager.henry.renderPart("Bullet");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.25, 3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.henry_tex);
		ResourceManager.henry.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
