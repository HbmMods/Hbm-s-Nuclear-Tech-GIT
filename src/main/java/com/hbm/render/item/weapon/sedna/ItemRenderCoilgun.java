package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderCoilgun extends ItemRenderWeaponBase {

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
				-1.25F * offset, -1.5F * offset, 2.5F * offset,
				0, -7.5 / 8D, 1);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flaregun_tex);
		double scale = 0.75D;
		GL11.glScaled(scale, scale, scale);
		
		GL11.glRotated(-90, 0, 1, 0);

		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		GL11.glTranslated(-1.5 - recoil[0] * 0.5, 0, 0);
		GL11.glRotated(recoil[0] * 45, 0, 0, 1);
		GL11.glTranslated(1.5, 0, 0);

		double[] reload = HbmAnimations.getRelevantTransformation("RELOAD");
		GL11.glTranslated(-2.5, 0, 0);
		GL11.glRotated(reload[0] * -45, 0, 0, 1);
		GL11.glTranslated(2.5, 0, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.coilgun_tex);
		ResourceManager.coilgun.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 3D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.25, 1.25);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 4D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.25, -0.25, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -20D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -0.25, 0.5);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glRotated(-90, 0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.coilgun_tex);
		ResourceManager.coilgun.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
