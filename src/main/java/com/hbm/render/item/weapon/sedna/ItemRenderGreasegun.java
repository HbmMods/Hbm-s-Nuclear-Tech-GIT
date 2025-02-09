package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderGreasegun extends ItemRenderWeaponBase {

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
				-1.5F * offset, -1F * offset, 1.75F * offset,
				0, -2.625 / 8D, 1.125);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.greasegun_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] stock = HbmAnimations.getRelevantTransformation("STOCK");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] flap = HbmAnimations.getRelevantTransformation("FLAP");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] handle = HbmAnimations.getRelevantTransformation("HANDLE");
		double[] mag = HbmAnimations.getRelevantTransformation("MAG");
		double[] turn = HbmAnimations.getRelevantTransformation("TURN");
		double[] bullet = HbmAnimations.getRelevantTransformation("BULLET");
		
		GL11.glTranslated(0, -3, -3);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 3, 3);
		
		GL11.glTranslated(0, -3, -3);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 3, 3);

		if(gun.aimingProgress < 1F) GL11.glRotated(turn[2], 0, 0, 1);
		
		GL11.glTranslated(0, 0, recoil[2]);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.greasegun.renderPart("Gun");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -4 - stock[2]);
		ResourceManager.greasegun.renderPart("Stock");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(mag[0], mag[1], mag[2]);
		ResourceManager.greasegun.renderPart("Magazine");
		if(bullet[0] != 1) ResourceManager.greasegun.renderPart("Bullet");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, -1.4375, -0.125);
		GL11.glRotated(handle[0], 1, 0, 0);
		GL11.glTranslated(0, 1.4375, 0.125);
		ResourceManager.greasegun.renderPart("Handle");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.53125, 0);
		GL11.glRotated(flap[2], 0, 0, 1);
		GL11.glTranslated(0, -0.5125, 0);
		ResourceManager.greasegun.renderPart("Flap");
		GL11.glPopMatrix();

		double smokeScale = 0.25;
		
		GL11.glPushMatrix();
		GL11.glTranslated(-0.25, 0, 1.5);
		GL11.glRotated(turn[2], 0, 0, -1);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 1D);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 8);
		GL11.glRotated(turn[2], 0, 0, -1);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 1D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 7.5);
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
		GL11.glTranslated(-0.5, 2, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.greasegun_tex);
		ResourceManager.greasegun.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
