package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderDrill extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 0F : -0.5F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.25F * offset, -1.75F * offset, 1.75F * offset,
				-1F * offset, -1.75F * offset, 1.25F * offset);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {

		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.drill_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);
		
		IMagazine mag = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
		double gauge = (double) mag.getAmount(stack, null) / (double) mag.getCapacity(stack);

		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		GL11.glRotated(15 * (1 - aimingProgress), 0, 1, 0);
		GL11.glRotated(-10 * (1 - aimingProgress), 1, 0, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.drill.renderPart("Base");

		GL11.glPushMatrix();
		GL11.glTranslated(1, 2.0625, -1.75);
		GL11.glRotated(45, 1, 0, 0);
		GL11.glRotated(-135 + gauge * 270, 0, 0, 1);
		GL11.glRotated(-45, 1, 0, 0);
		GL11.glTranslated(-1, -2.0625, 1.75);
		ResourceManager.drill.renderPart("Gauge");
		GL11.glPopMatrix();

		double rot = System.currentTimeMillis() / 3 % 360D;
		double rot2 = rot * 5;

		GL11.glPushMatrix();
		GL11.glTranslated(0, Math.sin(rot2 * Math.PI / 180) * 0.125 - 0.125, 0);
		ResourceManager.drill.renderPart("Piston1");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, Math.sin(rot2 * Math.PI / 180 + Math.PI * 2D / 3D) * 0.125 - 0.125, 0);
		ResourceManager.drill.renderPart("Piston2");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, Math.sin(rot2 * Math.PI / 180 + Math.PI * 4D / 3D) * 0.125 - 0.125, 0);
		ResourceManager.drill.renderPart("Piston3");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glRotated(rot, 0, 0, -1);
		ResourceManager.drill.renderPart("DrillBack");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotated(rot, 0, 0, 1);
		ResourceManager.drill.renderPart("DrillFront");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 2.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(1, -2, 6);
	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -8.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 0, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.drill_tex);
		ResourceManager.drill.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
