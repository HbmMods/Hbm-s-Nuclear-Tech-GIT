package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderLiberator extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.33F);
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.5F * offset, -1.25F * offset, 1.25F * offset,
				0, -4.625 / 8D, 0.25);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.liberator_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] latch = HbmAnimations.getRelevantTransformation("LATCH");
		double[] brk = HbmAnimations.getRelevantTransformation("BREAK");
		double[] shell1 = HbmAnimations.getRelevantTransformation("SHELL1");
		double[] shell2 = HbmAnimations.getRelevantTransformation("SHELL2");
		double[] shell3 = HbmAnimations.getRelevantTransformation("SHELL3");
		double[] shell4 = HbmAnimations.getRelevantTransformation("SHELL4");
		
		GL11.glTranslated(0, -1, -3);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 3);
		
		GL11.glTranslated(0, -3, -3);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 3, 3);

		GL11.glTranslated(recoil[0] * 2, recoil[1], recoil[2]);
		GL11.glRotated(recoil[2] * 10, 1, 0, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		ResourceManager.liberator.renderPart("Gun");
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(0, -0.5, 0.75);
		GL11.glRotated(brk[0], 1, 0, 0);
		GL11.glTranslated(0, 0.5, -0.75);
		ResourceManager.liberator.renderPart("Barrel");
		
		GL11.glPushMatrix();
		GL11.glTranslated(shell1[0], shell1[1], shell1[2]);
		ResourceManager.liberator.renderPart("Shell1");
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(shell2[0], shell2[1], shell2[2]);
		ResourceManager.liberator.renderPart("Shell2");
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(shell3[0], shell3[1], shell3[2]);
		ResourceManager.liberator.renderPart("Shell3");
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(shell4[0], shell4[1], shell4[2]);
		ResourceManager.liberator.renderPart("Shell4");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.15625, 0.75);
		GL11.glRotated(latch[0], 1, 0, 0);
		GL11.glTranslated(0, -1.15625, -0.75);
		ResourceManager.liberator.renderPart("Latch");
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		double smokeScale = 0.375;
		
		GunConfig cfg = gun.getConfig(stack, 0);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.25, 7.25);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		GL11.glTranslated(0, 0, 0.25 / smokeScale);
		this.renderSmokeNodes(cfg.smokeNodes, 1D);
		GL11.glTranslated(0, 0, -0.5 / smokeScale);
		this.renderSmokeNodes(cfg.smokeNodes, 1D);
		GL11.glTranslated(0, 0.5 / smokeScale, 0);
		this.renderSmokeNodes(cfg.smokeNodes, 1D);
		GL11.glTranslated(0, 0, 0.5 / smokeScale);
		this.renderSmokeNodes(cfg.smokeNodes, 1D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.5, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(1.5, 1.5, 1.5);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, 1, 3);

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
	public void setupModTable(ItemStack stack) {
		double scale = -8.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.liberator_tex);
		ResourceManager.liberator.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
