package com.hbm.render.item.weapon.sedna;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.particle.SpentCasing;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderSPAS12 extends ItemRenderWeaponBase {

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
				-1.25F * offset, -1.75F * offset, -0.5F * offset,
				0, 0, 0);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.spas_12_tex);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(180, 0, 1, 0);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		
		GL11.glRotated(equip[0], 1, 0, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);

		HbmAnimations.applyRelevantTransformation("MainBody");
		ResourceManager.spas_12.renderPart("MainBody");
		
		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("PumpGrip");
		ResourceManager.spas_12.renderPart("PumpGrip");
		GL11.glPopMatrix();


		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.casings_tex);

		HbmAnimations.applyRelevantTransformation("Shell");
		SpentCasing casing = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getCasing(stack, MainRegistry.proxy.me().inventory);
		int color0 = SpentCasing.COLOR_CASE_BRASS;
		int color1 = SpentCasing.COLOR_CASE_BRASS;
		
		if(casing != null) {
			int[] colors = casing.getColors();
			color0 = colors[0];
			color1 = colors[colors.length > 1 ? 1 : 0];
		}

		Color shellColor = new Color(color1);
		GL11.glColor3f(shellColor.getRed() / 255F, shellColor.getGreen() / 255F, shellColor.getBlue() / 255F);
		ResourceManager.spas_12.renderPart("Shell");

		Color shellForeColor = new Color(color0);
		GL11.glColor3f(shellForeColor.getRed() / 255F, shellForeColor.getGreen() / 255F, shellForeColor.getBlue() / 255F);
		ResourceManager.spas_12.renderPart("ShellFore");

		GL11.glColor3f(1F, 1F, 1F);

		double smokeScale = 0.25;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, -11);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.75D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, -11);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 7.5);
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, -0.75, 0);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 2D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(4.25, -0.5, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -10D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -0.5, -4.25);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glRotated(180, 0, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.spas_12_tex);
		ResourceManager.spas_12.renderPart("MainBody");
		ResourceManager.spas_12.renderPart("PumpGrip");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
