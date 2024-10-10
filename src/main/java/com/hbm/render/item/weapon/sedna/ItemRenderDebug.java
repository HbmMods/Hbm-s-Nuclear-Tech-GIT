package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderDebug extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 1);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.0F * offset, -0.75F * offset, 1F * offset,
				0, -3.875 / 8D, 0);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);

		double[] equipSpin = HbmAnimations.getRelevantTransformation("ROTATE");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] reloadLift = HbmAnimations.getRelevantTransformation("RELOAD_LIFT");
		double[] reloadJolt = HbmAnimations.getRelevantTransformation("RELOAD_JOLT");
		double[] reloadTilt = HbmAnimations.getRelevantTransformation("RELAOD_TILT");
		double[] cylinderFlip = HbmAnimations.getRelevantTransformation("RELOAD_CYLINDER");
		double[] reloadBullets = HbmAnimations.getRelevantTransformation("RELOAD_BULLETS");
		
		GL11.glRotated(equipSpin[0], 0, 0, 1);

		standardAimingTransform(stack, 0, 0, recoil[2], -recoil[2], 0, 0);
		GL11.glRotated(recoil[2] * 10, 0, 0, 1);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glPushMatrix();
		GL11.glTranslated(-9, 2.5, 0);
		GL11.glRotated(recoil[2] * -10, 0, 0, 1);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glRotated(reloadLift[0], 0, 0, 1);
		GL11.glTranslated(reloadJolt[0], 0, 0);
		GL11.glRotated(reloadTilt[0], 1, 0, 0);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.debug_gun_tex);
		ResourceManager.lilmac.renderPart("Gun");

		GL11.glPushMatrix();
		GL11.glRotated(cylinderFlip[0], 1, 0, 0);
		ResourceManager.lilmac.renderPart("Pivot");
		GL11.glTranslated(0, 1.75, 0);
		GL11.glRotated(HbmAnimations.getRelevantTransformation("DRUM")[2] * -60, 1, 0, 0);
		GL11.glTranslated(0, -1.75, 0);
		ResourceManager.lilmac.renderPart("Cylinder");
		GL11.glTranslated(reloadBullets[0], reloadBullets[1], reloadBullets[2]);
		if(HbmAnimations.getRelevantTransformation("RELOAD_BULLETS_CON")[0] != 1)
		ResourceManager.lilmac.renderPart("Bullets");
		ResourceManager.lilmac.renderPart("Casings");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix(); /// HAMMER ///
		GL11.glTranslated(4, 1.25, 0);
		GL11.glRotated(-30 + 30 * HbmAnimations.getRelevantTransformation("HAMMER")[2], 0, 0, 1);
		GL11.glTranslated(-4, -1.25, 0);
		ResourceManager.lilmac.renderPart("Hammer");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0.125, 2.5, 0);
		this.renderGapFlash(gun.lastShot[0]);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(-9.5, 2.5, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		//this.renderMuzzleFlash(gun.lastShot);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glScaled(0.75, 0.75, 0.75);
		GL11.glTranslated(0, 1, 3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {

		GL11.glRotated(90, 0, 1, 0);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.debug_gun_tex);
		ResourceManager.lilmac.renderPart("Gun");
		ResourceManager.lilmac.renderPart("Cylinder");
		ResourceManager.lilmac.renderPart("Bullets");
		ResourceManager.lilmac.renderPart("Casings");
		ResourceManager.lilmac.renderPart("Pivot");
		ResourceManager.lilmac.renderPart("Hammer");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
