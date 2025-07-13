package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;

public class ItemRenderStinger extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.5F);
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-3.75F * offset, -9F * offset, -3.5F * offset,
				-2.625F * offset, -6.5, -8.5F);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		if(ItemGunBaseNT.prevAimingProgress == 1 && ItemGunBaseNT.aimingProgress == 1) return;
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.stinger_tex);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] reload = HbmAnimations.getRelevantTransformation("RELOAD");
		double[] rocket = HbmAnimations.getRelevantTransformation("ROCKET");
		
		GL11.glTranslated(0, -1, -1);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 1);
		
		GL11.glTranslated(0, -4, -3);
		GL11.glRotated(reload[0], 1, 0, 0);
		GL11.glTranslated(0, 4, 3);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		GL11.glRotated(180, 0, 1, 0);
		ResourceManager.stinger.renderAll();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.panzerschreck_tex);
		GL11.glTranslated(rocket[0], rocket[1] + 3.5, rocket[2] - 3);
		ResourceManager.panzerschreck.renderPart("Rocket");
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

		String label = "Not accurate";
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		float f3 = 0.04F;
		GL11.glTranslatef(0.025F, -0.5F, (font.getStringWidth(label) / 2) * f3 - 3);
		GL11.glScalef(f3, -f3, f3);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(45, -1, 0, 0);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
		font.drawString(label, 0, 0, 0xff0000);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 6.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(0.75, 0.75, 0.75);
		this.renderMuzzleFlash(gun.lastShot[0], 150, 7.5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, -2.5, -3.5);
		GL11.glRotated(180, 0, 1, 0);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.0625D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(225, 0, 1, 0);
		GL11.glTranslated(0.25, -2.5, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -7.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glTranslated(0, -4, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_CULL_FACE);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.stinger_tex);
		ResourceManager.stinger.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
