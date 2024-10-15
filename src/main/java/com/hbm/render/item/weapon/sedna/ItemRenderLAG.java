package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderLAG extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.5F * offset, -1F * offset, 1.5F * offset,
				0, -3.375 / 8D, 0.5);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mike_hawk_tex);
		double scale = 0.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.mike_hawk.renderPart("Grip");
		ResourceManager.mike_hawk.renderPart("Slide");
		ResourceManager.mike_hawk.renderPart("Hammer");
		ResourceManager.mike_hawk.renderPart("Bullet");
		ResourceManager.mike_hawk.renderPart("Magazine");

		double smokeScale = 0.5;
		
		GL11.glPushMatrix();
		GL11.glTranslated(-10.25, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(-10.25, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 7.5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, 1, 1);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(2.5, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glRotated(90, 0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mike_hawk_tex);
		ResourceManager.mike_hawk.renderPart("Grip");
		ResourceManager.mike_hawk.renderPart("Slide");
		ResourceManager.mike_hawk.renderPart("Hammer");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
