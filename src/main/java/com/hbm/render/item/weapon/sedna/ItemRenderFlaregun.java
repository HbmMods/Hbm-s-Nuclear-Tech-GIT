package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderFlaregun extends ItemRenderWeaponBase {

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
				-1.25F * offset, -1.5F * offset, 2F * offset,
				0, -5.5 / 8D, 0.5);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {

		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flaregun_tex);
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] hammer = HbmAnimations.getRelevantTransformation("HAMMER");
		double[] open = HbmAnimations.getRelevantTransformation("OPEN");
		double[] shell = HbmAnimations.getRelevantTransformation("SHELL");
		double[] flip = HbmAnimations.getRelevantTransformation("FLIP");
		
		GL11.glTranslated(recoil[0], recoil[1], recoil[2]);
		GL11.glRotated(recoil[2] * 10, 1, 0, 0);
		GL11.glRotated(flip[0], 1, 0, 0);

		GL11.glTranslated(0, 0, -8);
		GL11.glRotated(equip[0], -1, 0, 0);
		GL11.glTranslated(0, 0, 8);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.flaregun.renderPart("Gun");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.8125, -4);
		GL11.glRotated(hammer[0] - 15, 1, 0, 0);
		GL11.glTranslated(0, -1.8125, 4);
		ResourceManager.flaregun.renderPart("Hammer");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 2.156, 1.78);
		GL11.glRotated(open[0], 1, 0, 0);
		GL11.glTranslated(0, -2.156, -1.78);
		ResourceManager.flaregun.renderPart("Barrel");
		GL11.glTranslated(shell[0], shell[1], shell[2]);
		ResourceManager.flaregun.renderPart("Flare");
		GL11.glPopMatrix();

		double smokeScale = 0.5;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 4, 9);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 2.5D);
		GL11.glTranslated(0, 0, 0.1);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 2D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.25, 3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flaregun_tex);
		ResourceManager.flaregun.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
