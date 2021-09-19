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
public class ParticleCoolingTower extends EntityFX {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");
	private TextureManager theRenderEngine;
	private float baseScale = 1.0F;
	private float maxScale = 1.0F;
	private float lift = 0.3F;

	public ParticleCoolingTower(TextureManager texman, World world, double x, double y, double z) {
		super(world, x, y, z);
		this.particleRed = this.particleGreen = this.particleBlue = 0.9F + world.rand.nextFloat() * 0.05F;
		this.theRenderEngine = texman;
		this.noClip = true;
	}
	
	public void setBaseScale(float f) {
		this.baseScale = f;
	}
	
	public void setMaxScale(float f) {
		this.maxScale = f;
	}
	
	public void setLift(float f) {
		this.lift = f;
	}
	
	public void setLife(int i) {
		this.particleMaxAge = i;
	}

	public void onUpdate() {
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		float ageScale = (float) this.particleAge / (float) this.particleMaxAge;
		
		this.particleAlpha = 0.25F - ageScale * 0.25F;
		this.particleScale = baseScale + (float)Math.pow((maxScale * ageScale - baseScale), 2);

		this.particleAge++;
		
		if(this.motionY < this.lift) {
			this.motionY += 0.01F;
		}

		this.motionX += rand.nextGaussian() * 0.075D * ageScale;
		this.motionZ += rand.nextGaussian() * 0.075D * ageScale;

		this.motionX += 0.02 * ageScale;
		this.motionX -= 0.01 * ageScale;

		if(this.particleAge == this.particleMaxAge) {
			this.setDead();
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		motionX *= 0.925;
		motionY *= 0.925;
		motionZ *= 0.925;
	}

	public int getFXLayer() {
		return 3;
	}

	public void renderParticle(Tessellator tess, float interp, float fX, float fY, float fZ, float sX, float sZ) {

		this.theRenderEngine.bindTexture(texture);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();

		tess.startDrawingQuads();

		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		tess.setNormal(0.0F, 1.0F, 0.0F);

		float scale = this.particleScale;
		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);

		tess.addVertexWithUV((double) (pX - fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ - fZ * scale - sZ * scale), 1, 1);
		tess.addVertexWithUV((double) (pX - fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ - fZ * scale + sZ * scale), 1, 0);
		tess.addVertexWithUV((double) (pX + fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ + fZ * scale + sZ * scale), 0, 0);
		tess.addVertexWithUV((double) (pX + fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ + fZ * scale - sZ * scale), 0, 1);
		tess.draw();

		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
	}
}
