package com.hbm.render.item.weapon.sedna;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemRenderMissileLauncher extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.5F * offset, -1.25F * offset, 0.5F * offset,
				-1F * offset, -1.25F * offset, 0F * offset);
	}
	
	protected static String label = "AUTO";
	
	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.missile_launcher_tex);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] barrel = HbmAnimations.getRelevantTransformation("BARREL");
		double[] open = HbmAnimations.getRelevantTransformation("OPEN");
		double[] missile = HbmAnimations.getRelevantTransformation("MISSILE");
		
		GL11.glTranslated(0, -2, -2);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 2, 2);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		ResourceManager.missile_launcher.renderPart("Launcher");
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(0, 0.25, 1.6875);
		GL11.glRotated(open[0], 1, 0, 0);
		GL11.glTranslated(0, -0.25, -1.6875);
		
		ResourceManager.missile_launcher.renderPart("Front");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, barrel[2]);
		ResourceManager.missile_launcher.renderPart("Barrel");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(missile[0], missile[1], missile[2]);
		ResourceManager.missile_launcher.renderPart("Missile");
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
		
		if(gun.prevAimingProgress >= 1F && gun.aimingProgress >= 1F) {

			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			FontRenderer font = Minecraft.getMinecraft().fontRenderer;
			float f3 = 0.04F;
			GL11.glTranslatef(0.9375F, 2.25F, -0.5625F + (font.getStringWidth(label) / 2) * f3);
			GL11.glScalef(f3, -f3, f3);
			GL11.glRotated(90D, 0, 1, 0);
			GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
			float variance = 0.7F + player.getRNG().nextFloat() * 0.3F;
			font.drawString(label, 0, 0, new Color(variance, 0F, 0F).getRGB());
			GL11.glColor3f(1F, 1F, 1F);

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
			
			int brightness = player.worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0);
			int j = brightness % 65536;
			int k = brightness / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, 6.75);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(gun.shotRand * 90, 1, 0, 0);
		GL11.glScaled(0.75, 0.75, 0.75);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 7.5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, -0.5, -2);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, -0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.missile_launcher_tex);
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.missile_launcher.renderPart("Launcher");
		ResourceManager.missile_launcher.renderPart("Barrel");
		ResourceManager.missile_launcher.renderPart("Front");
		if(gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, null) > 0) ResourceManager.missile_launcher.renderPart("Missile");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
