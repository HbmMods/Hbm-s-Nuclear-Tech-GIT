package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticlePlasmaBlast extends EntityFX {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/shockwave.png");
	private TextureManager theRenderEngine;

	public ParticlePlasmaBlast(TextureManager texman, World world, double x, double y, double z, float r, float g, float b, float pitch, float yaw) {
		super(world, x, y, z);
		this.theRenderEngine = texman;
		this.particleMaxAge = 20;
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		this.rotationPitch = pitch;
		this.rotationYaw = yaw;
	}
	
	public void setMaxAge(int maxAge) {
		this.particleMaxAge = maxAge;
	}
	
	public void setScale(float scale) {
		this.particleScale = scale;
	}

	public int getFXLayer() {
		return 3;
	}

	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {
		
		this.theRenderEngine.bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_CULL_FACE);
		RenderHelper.disableStandardItemLighting();
		
		boolean fog = GL11.glIsEnabled(GL11.GL_FOG);
		if(fog) GL11.glDisable(GL11.GL_FOG);
		
		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double)interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double)interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double)interp - interpPosZ);

		GL11.glTranslatef(pX, pY, pZ);
		GL11.glRotated(this.rotationYaw, 0, 1, 0);
		GL11.glRotated(this.rotationPitch, 1, 0, 0);
			
		tess.startDrawingQuads();
		
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);
		
		this.particleAlpha = 1 - (((float)this.particleAge + interp) / (float)this.particleMaxAge);
		float scale = (1 - (float)Math.pow(Math.E, (this.particleAge + interp) * -0.125)) * this.particleScale;
		
		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		
		tess.addVertexWithUV((double)(- 1 * scale), 0, (double)(- 1 * scale), 1, 1);
		tess.addVertexWithUV((double)(- 1 * scale), 0, (double)(+ 1 * scale), 1, 0);
		tess.addVertexWithUV((double)(+ 1 * scale), 0, (double)(+ 1 * scale), 0, 0);
		tess.addVertexWithUV((double)(+ 1 * scale), 0, (double)(- 1 * scale), 0, 1);
		tess.draw();

		if(fog) GL11.glEnable(GL11.GL_FOG);
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDepthMask(true);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glPopMatrix();
	}
}
