package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderHangman extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

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
				-1.5F * offset, -0.875F * offset, 1.75F * offset,
				0, -1.5 / 8D, 1.25);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hangman_tex);
		float offset = 0.8F;

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] roll = HbmAnimations.getRelevantTransformation("ROLL");
		double[] turn = HbmAnimations.getRelevantTransformation("TURN");
		double[] smack = HbmAnimations.getRelevantTransformation("SMACK");
		double[] lid = HbmAnimations.getRelevantTransformation("LID");
		double[] mag = HbmAnimations.getRelevantTransformation("MAG");
		double[] bullets = HbmAnimations.getRelevantTransformation("BULLETS");
		
		GL11.glTranslated(1.5F * offset, 0, -1);
		GL11.glRotated(turn[1], 0, 1, 0);
		GL11.glTranslated(-1.5F * offset, 0, 1);
		
		GL11.glRotated(roll[2], 0, 0, 1);
		GL11.glTranslated(smack[0], smack[1], smack[2]);
		
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);
		
		GL11.glTranslated(0, -4, -10);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 4, 10);
		
		GL11.glTranslated(0, 0, recoil[2]);

		GL11.glShadeModel(GL11.GL_SMOOTH);

		ResourceManager.hangman.renderPart("Rifle");
		ResourceManager.hangman.renderPart("Internals");
		
		GL11.glPushMatrix();
		//i give the fuck up
		GL11.glTranslated(-2.1875, -1.75, 0);
		GL11.glRotated(lid[2], 0, 0, 1);
		GL11.glTranslated(2.1875, 1.75, 0);
		ResourceManager.hangman.renderPart("Lid");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(mag[0], mag[1], mag[2]);
		ResourceManager.hangman.renderPart("Magazine");
		if(bullets[0] == 0) ResourceManager.hangman.renderPart("Bullets");
		GL11.glPopMatrix();

		double smokeScale = 1.5;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 29);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 29);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(2, 2, 2);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 7.5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 4.25, 11);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 2.5, 0);
	}

	@Override
	public void setupEntity(ItemStack stack) {
		double scale = 0.0625D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -2.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hangman_tex);
		ResourceManager.hangman.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
