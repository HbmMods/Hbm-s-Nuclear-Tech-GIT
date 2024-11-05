package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderSTG77 extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.5F * offset, -1F * offset, 2.5F * offset,
			0, -5.75 / 8D, 1);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.stg77_tex);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] mag = HbmAnimations.getRelevantTransformation("MAG");
		double[] speen = HbmAnimations.getRelevantTransformation("SPEEN");
		double[] bolt = HbmAnimations.getRelevantTransformation("BOLT");
		double[] handle = HbmAnimations.getRelevantTransformation("HANDLE");
		double[] bullet = HbmAnimations.getRelevantTransformation("BULLET");
		double[] safety = HbmAnimations.getRelevantTransformation("SAFETY");
		
		GL11.glTranslated(0, -1, -4);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 4);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		ResourceManager.stg77.renderPart("Gun");
		ResourceManager.stg77.renderPart("Barrel");
		ResourceManager.stg77.renderPart("Lever");
		ResourceManager.stg77.renderPart("Magazine");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, bolt[2]);
		ResourceManager.stg77.renderPart("Handle");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(safety[0], 0, 0);
		ResourceManager.stg77.renderPart("Safety");
		GL11.glPopMatrix();

		double smokeScale = 0.75;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 7.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(0.25, 0.25, 0.25);
		GL11.glRotated(-5 + gun.shotRand * 10, 1, 0, 0);
		this.renderGapFlash(gun.lastShot[0]);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 1, 2);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.375D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.stg77_tex);
		ResourceManager.stg77.renderPart("Gun");
		ResourceManager.stg77.renderPart("Barrel");
		ResourceManager.stg77.renderPart("Lever");
		ResourceManager.stg77.renderPart("Magazine");
		ResourceManager.stg77.renderPart("Safety");
		ResourceManager.stg77.renderPart("Handle");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
