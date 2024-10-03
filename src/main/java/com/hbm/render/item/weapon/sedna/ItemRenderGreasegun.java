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
	protected void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.25F * offset, -1F * offset, 1.75F * offset,
				0, -2.625 / 8D, 1.125);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.greasegun_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.greasegun.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		this.renderMuzzleFlash(gun.lastShot, 75, 10);
		GL11.glPopMatrix();
	}

	@Override
	protected void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, 1, 3);

	}

	@Override
	protected void setupInv(ItemStack stack) {
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
