package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderTau extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.75F * offset, -1.75F * offset, 3.5F * offset,
				-1.75F * offset, -1.75F * offset, 3.5F * offset);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.tau_tex);
		double scale = 0.75D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] rotate = HbmAnimations.getRelevantTransformation("ROTATE");
		
		GL11.glTranslated(0, -1, -4);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 4);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glTranslated(0, 0, -2);
		GL11.glRotated(recoil[2] * 5, 1, 0, 0);
		GL11.glTranslated(0, 0, 2);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_CULL_FACE);

		ResourceManager.tau.renderPart("Body");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.25, 0);
		GL11.glRotated(rotate[2], 0, 0, 1);
		GL11.glTranslated(0, 0.25, 0);
		ResourceManager.tau.renderPart("Rotor");
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 1, 2);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 2D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.25, 0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.tau_tex);
		ResourceManager.tau.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
