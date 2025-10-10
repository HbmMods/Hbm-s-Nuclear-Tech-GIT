package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
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
				0, -4.6825 / 8D, 0.75);

		GL11.glRotated(15, 0, 1, 0);
		GL11.glRotated(-10, 1, 0, 0);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.drill_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.drill.renderPart("Base");

		GL11.glPushMatrix();
		GL11.glTranslated(1, 2.0625, -1.75);
		GL11.glRotated(45, 1, 0, 0);
		GL11.glRotated(-135, 0, 0, 1);
		GL11.glRotated(-45, 1, 0, 0);
		GL11.glTranslated(-1, -2.0625, 1.75);
		ResourceManager.drill.renderPart("Gauge");
		GL11.glPopMatrix();
		
		ResourceManager.drill.renderPart("Piston1");
		ResourceManager.drill.renderPart("Piston2");
		ResourceManager.drill.renderPart("Piston3");
		
		GL11.glPushMatrix();
		GL11.glRotated(System.currentTimeMillis() / 3 % 360D, 0, 0, -1);
		ResourceManager.drill.renderPart("DrillBack");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotated(System.currentTimeMillis() / 3 % 360D, 0, 0, 1);
		ResourceManager.drill.renderPart("DrillFront");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
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
		double scale = -7.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 2, 0);
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
