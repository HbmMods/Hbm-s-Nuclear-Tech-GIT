package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleRBMKSteam extends EntityFX {

	public static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/rbmk_jet_steam.png");
	private TextureManager theRenderEngine;

	public ParticleRBMKSteam(TextureManager texman, World world, double x, double y, double z) {
		super(world, x, y, z);
		this.theRenderEngine = texman;
		this.particleMaxAge = 10;
		this.particleAlpha = 0.25F;
		this.particleScale = 4F;
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		this.theRenderEngine.bindTexture(getTexture());
		boolean fog = GL11.glIsEnabled(GL11.GL_FOG);
		if(fog) GL11.glDisable(GL11.GL_FOG);
		
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		RenderHelper.disableStandardItemLighting();
		
		if(this.particleAge > this.particleMaxAge)
			this.particleAge = this.particleMaxAge;
		
		int texIndex = (int) (((double) this.particleAge / (double) this.particleMaxAge) * 20) % 20 - 1;
		float f0 = 1F / 20F;

		float uMin = texIndex * f0;
		float uMax = uMin + f0;
		float vMin = 0;
		float vMax = 1;
			
		tess.startDrawingQuads();
		
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);
		
		tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, this.particleAlpha);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;

		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - dX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - dY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - dZ);
		
		GL11.glTranslatef(pX + x, pY + y, pZ + z);
		GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);

		tess.addVertexWithUV(this.particleScale * -0.25 - 0.9375, -0.25, 0, uMax, vMax);
		tess.addVertexWithUV(this.particleScale * -0.25 - 0.9375, this.particleScale - 0.25, 0, uMax, vMin);
		tess.addVertexWithUV(this.particleScale * 0.25 - 0.9375, this.particleScale - 0.25, 0, uMin, vMin);
		tess.addVertexWithUV(this.particleScale * 0.25 - 0.9375, -0.25, 0, uMin, vMax);
		
		tess.draw();
		
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		if(fog) GL11.glEnable(GL11.GL_FOG);
	}
	
	protected ResourceLocation getTexture() {
		return texture;
	}
}
