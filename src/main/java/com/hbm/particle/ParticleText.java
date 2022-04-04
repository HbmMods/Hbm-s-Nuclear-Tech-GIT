package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class ParticleText extends EntityFX {

	int color;
	String text;

	public ParticleText(World world, double x, double y, double z, int color, String text) {
		super(world, x, y, z);
		this.particleMaxAge = 100;
		this.color = color;
		this.text = text;
		
		this.motionY = 0.01D;
		this.noClip = true;
	}

	public int getFXLayer() {
		return 3;
	}

	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.0F);
		RenderHelper.disableStandardItemLighting();

		Minecraft mc = Minecraft.getMinecraft();
		FontRenderer font = mc.fontRenderer;

		this.rotationYaw = -mc.thePlayer.rotationYaw;
		this.rotationPitch = mc.thePlayer.rotationPitch;

		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);

		GL11.glTranslatef(pX, pY, pZ);
		GL11.glRotatef(this.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(this.rotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);

		GL11.glScaled(particleScale * 0.01, particleScale * 0.01, particleScale * 0.01);

		font.drawStringWithShadow(text, -(int) (font.getStringWidth(text) * 0.5F), -(int) (font.FONT_HEIGHT * 0.5F), color);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopMatrix();
	}
}
