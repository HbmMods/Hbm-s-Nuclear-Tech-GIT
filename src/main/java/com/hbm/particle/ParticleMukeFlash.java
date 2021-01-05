package com.hbm.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleMukeFlash extends EntityFX {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");
	private TextureManager theRenderEngine;
	
	public ParticleMukeFlash(TextureManager texman, World world, double x, double y, double z) {
		super(world, x, y, z);
		this.theRenderEngine = texman;
		this.particleMaxAge = 20;
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
		float scale = (this.particleAge + interp) * 0.75F + 5;
		
		tess.setColorRGBA_F(1.0F, 0.9F, 0.75F, this.particleAlpha * 0.5F);

		float dX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float dY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float dZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);
		
		Random rand = new Random();

		for(int i = 0; i < 24; i++) {
			
			rand.setSeed(i * 31 + 1);

			float pX = (float) (dX + rand.nextDouble() * 10 - 5);
			float pY = (float) (dY + rand.nextDouble() * 5 - 2.5);
			float pZ = (float) (dZ + rand.nextDouble() * 10 - 5);
			
			tess.addVertexWithUV((double) (pX - x * scale - tx * scale), (double) (pY - y * scale), (double) (pZ - z * scale - tz * scale), 1, 1);
			tess.addVertexWithUV((double) (pX - x * scale + tx * scale), (double) (pY + y * scale), (double) (pZ - z * scale + tz * scale), 1, 0);
			tess.addVertexWithUV((double) (pX + x * scale + tx * scale), (double) (pY + y * scale), (double) (pZ + z * scale + tz * scale), 0, 0);
			tess.addVertexWithUV((double) (pX + x * scale - tx * scale), (double) (pY - y * scale), (double) (pZ + z * scale - tz * scale), 0, 1);
		}
		tess.draw();
		
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
