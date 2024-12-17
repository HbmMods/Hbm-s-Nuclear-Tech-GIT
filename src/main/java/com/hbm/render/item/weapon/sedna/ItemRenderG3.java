package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderG3 extends ItemRenderWeaponBase {

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
				-1.25F * offset, -1F * offset, 2.75F * offset,
			0, -3.5625 / 8D, 1.75);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.g3_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] mag = HbmAnimations.getRelevantTransformation("MAG");
		double[] speen = HbmAnimations.getRelevantTransformation("SPEEN");
		double[] bolt = HbmAnimations.getRelevantTransformation("BOLT");
		double[] handle = HbmAnimations.getRelevantTransformation("HANDLE");
		double[] bullet = HbmAnimations.getRelevantTransformation("BULLET");
		
		GL11.glTranslated(0, -2, -6);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 2, 6);
		
		GL11.glTranslated(0, 0, -4);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 0, 4);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		ResourceManager.g3.renderPart("Rifle");
		ResourceManager.g3.renderPart("Stock");
		ResourceManager.g3.renderPart("FlashHider");
		
		GL11.glPushMatrix();
		GL11.glTranslated(mag[0], mag[1], mag[2]);
		GL11.glTranslated(0, -1.75, -0.5);
		GL11.glRotated(speen[2], 0, 0, 1);
		GL11.glRotated(speen[1], 0, 1, 0);
		GL11.glTranslated(0, 1.75, 0.5);
		ResourceManager.g3.renderPart("Magazine");
		if(bullet[0] == 0) ResourceManager.g3.renderPart("Bullet");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, bolt[2]);
		ResourceManager.g3.renderPart("Bolt");
		GL11.glTranslated(0, 0.625, 0);
		GL11.glRotated(handle[2], 0, 0, 1);
		GL11.glTranslated(0, -0.625, 0);
		ResourceManager.g3.renderPart("Handle");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.875, -3.5);
		GL11.glRotated(-30 * (1 - ItemGunBaseNT.getMode(stack, 0)), 1, 0, 0);
		GL11.glTranslated(0, 0.875, 3.5);
		ResourceManager.g3.renderPart("Selector");
		GL11.glPopMatrix();

		double smokeScale = 0.75;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 13);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 12);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(-25 + gun.shotRand * 10, 1, 0, 0);
		GL11.glScaled(0.75, 0.75, 0.75);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 10);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 2, 4);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 0.875D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.g3_tex);
		ResourceManager.g3.renderPart("Rifle");
		ResourceManager.g3.renderPart("Stock");
		ResourceManager.g3.renderPart("Magazine");
		ResourceManager.g3.renderPart("FlashHider");
		ResourceManager.g3.renderPart("Bolt");
		ResourceManager.g3.renderPart("Handle");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.875, -3.5);
		GL11.glRotated(-30, 1, 0, 0);
		GL11.glTranslated(0, 0.875, 3.5);
		ResourceManager.g3.renderPart("Selector");
		GL11.glPopMatrix();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
