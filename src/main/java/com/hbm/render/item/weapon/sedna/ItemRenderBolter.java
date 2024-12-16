package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;

public class ItemRenderBolter extends ItemRenderWeaponBase {

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
				-1.5F * offset, -2F * offset, 2.5F * offset,
				0, -10.5 / 8D, 1.25);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bolter_tex);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);
		
		GL11.glRotated(180, 0, 1, 0);
		
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		GL11.glRotated(recoil[0] * 5, 1, 0, 0);
		GL11.glTranslated(0, 0, recoil[0]);

		double[] tilt = HbmAnimations.getRelevantTransformation("TILT");
		GL11.glTranslated(0, tilt[0], 3);
		GL11.glRotated(tilt[0] * 35, 1, 0, 0);
		GL11.glTranslated(0, 0, -3);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.bolter.renderPart("Body");

		double[] mag = HbmAnimations.getRelevantTransformation("MAG");
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 5);
		GL11.glRotated(mag[0] * 60 * (mag[2] == 1 ? 2.5 : 1), -1, 0, 0);
		GL11.glTranslated(0, 0, -5);
		ResourceManager.bolter.renderPart("Mag");
		if(mag[2] != 1) ResourceManager.bolter.renderPart("Bullet");
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		String s = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, null) + "";
		float f3 = 0.04F;
		GL11.glTranslatef(0.025F - (font.getStringWidth(s) / 2) * 0.04F, 2.11F, 2.91F);
		GL11.glScalef(f3, -f3, f3);
		GL11.glRotatef(45, 1, 0, 0);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
		font.drawString(s, 0, 0, 0xff0000);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, -0.75, 1.25);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 2.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.25, -0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glRotated(180, 0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bolter_tex);
		ResourceManager.bolter.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
