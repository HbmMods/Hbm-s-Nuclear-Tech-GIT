package com.hbm.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleHadron extends EntityFX {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/hadron.png");
	private TextureManager theRenderEngine;
	
	public ParticleHadron(TextureManager texman, World world, double x, double y, double z) {
		super(world, x, y, z);
		this.theRenderEngine = texman;
		this.particleMaxAge = 10;
	}

	public int getFXLayer() {
		return 3;
	}

	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {
		
		this.theRenderEngine.bindTexture(texture);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		RenderHelper.disableStandardItemLighting();
			
		tess.startDrawingQuads();
		
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);
		
		this.particleAlpha = 1 - (((float)this.particleAge + interp) / (float)this.particleMaxAge);
		float scale = (this.particleAge + interp) * 0.15F;
		
		tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, this.particleAlpha);
		
	    float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double)interp - interpPosX);
	    float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double)interp - interpPosY);
	    float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double)interp - interpPosZ);
	       
		tess.addVertexWithUV((double)(pX - x * scale - tx * scale), (double)(pY - y * scale), (double)(pZ - z * scale - tz * scale), 1, 1);
		tess.addVertexWithUV((double)(pX - x * scale + tx * scale), (double)(pY + y * scale), (double)(pZ - z * scale + tz * scale), 1, 0);
		tess.addVertexWithUV((double)(pX + x * scale + tx * scale), (double)(pY + y * scale), (double)(pZ + z * scale + tz * scale), 0, 0);
		tess.addVertexWithUV((double)(pX + x * scale - tx * scale), (double)(pY - y * scale), (double)(pZ + z * scale - tz * scale), 0, 1);
		tess.draw();
		
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
