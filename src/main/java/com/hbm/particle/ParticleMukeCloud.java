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
public class ParticleMukeCloud extends EntityFX {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/explosion.png");
	private TextureManager theRenderEngine;
	
	private float friction;

	public ParticleMukeCloud(TextureManager texman, World world, double x, double y, double z, double mx, double my, double mz) {
		super(world, x, y, z);
		this.theRenderEngine = texman;
		this.motionX = mx;
		this.motionY = my;
		this.motionZ = mz;
		
		if(motionY > 0) {
			this.friction = 0.9F;
			
			if(motionY > 0.1)
				this.particleMaxAge = 92 + rand.nextInt(11) + (int)(motionY * 20);
			else
				this.particleMaxAge = 72 + rand.nextInt(11);
			
		} else if (motionY == 0) {
			
			this.friction = 0.95F;
			this.particleMaxAge = 52 + rand.nextInt(11);
			
		} else {
			
			this.friction = 0.85F;
			this.particleMaxAge = 122 + rand.nextInt(31);
			this.particleAge = 80;
		}
	}

	public int getFXLayer() {
		return 3;
	}
	
	public void onUpdate() {
		
		this.noClip = this.particleAge <= 2;

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if(this.particleAge++ >= this.particleMaxAge - 2) {
			this.setDead();
		}

		this.motionY -= 0.04D * (double) this.particleGravity;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= friction;
		this.motionY *= friction;
		this.motionZ *= friction;

		if(this.onGround) {
			this.motionX *= 0.7D;
			this.motionZ *= 0.7D;
		}
	}

	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		this.theRenderEngine.bindTexture(getTexture());
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();
		
		if(this.particleAge > this.particleMaxAge)
			this.particleAge = this.particleMaxAge;
		
		int texIndex = this.particleAge * 25 / this.particleMaxAge;
		float f0 = 1F / 5F;

		float uMin = texIndex % 5 * f0;
		float uMax = uMin + f0;
		float vMin = texIndex / 5 * f0;
		float vMax = vMin + f0;
			
		tess.startDrawingQuads();
		
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);

		this.particleAlpha = 1F;
		this.particleScale = 3;
		
		tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, this.particleAlpha);

		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);

		tess.addVertexWithUV((double) (pX - x * this.particleScale - tx * this.particleScale), (double) (pY - 1 * this.particleScale), (double) (pZ - z * this.particleScale - tz * this.particleScale), uMax, vMax);
		tess.addVertexWithUV((double) (pX - x * this.particleScale + tx * this.particleScale), (double) (pY + 1 * this.particleScale), (double) (pZ - z * this.particleScale + tz * this.particleScale), uMax, vMin);
		tess.addVertexWithUV((double) (pX + x * this.particleScale + tx * this.particleScale), (double) (pY + 1 * this.particleScale), (double) (pZ + z * this.particleScale + tz * this.particleScale), uMin, vMin);
		tess.addVertexWithUV((double) (pX + x * this.particleScale - tx * this.particleScale), (double) (pY - 1 * this.particleScale), (double) (pZ + z * this.particleScale - tz * this.particleScale), uMin, vMax);
		
		tess.draw();
		
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	protected ResourceLocation getTexture() {
		return texture;
	}
}
