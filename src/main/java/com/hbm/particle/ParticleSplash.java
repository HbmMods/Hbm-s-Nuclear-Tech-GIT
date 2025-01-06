package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleSplash extends EntityFX {

	public ParticleSplash(TextureManager texman, World world, double x, double y, double z) {
		super(world, x, y, z);
		particleIcon = ModEventHandlerClient.particleSplash;
		this.particleRed =  this.particleGreen = this.particleBlue = 1F - world.rand.nextFloat() * 0.2F;
		this.particleAlpha = 0.5F;
		this.particleScale = 0.4F;
		this.particleMaxAge = 200 + world.rand.nextInt(50);
		this.particleGravity = 0.4F;
	}

	public int getFXLayer() {
		return 1;
	}

	public void onUpdate() {
		super.onUpdate();

		if(!this.onGround) {
			this.motionX += rand.nextGaussian() * 0.002D;
			this.motionZ += rand.nextGaussian() * 0.002D;
			
			if(this.motionY < -0.5D)
				this.motionY = -0.5D;
		} else {
			this.setDead();
		}
	}

	@Override
	public void renderParticle(Tessellator tess, float interp, float fX, float fY, float fZ, float sX, float sZ) {

		tess.setNormal(0.0F, 1.0F, 0.0F);

		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);

		float scale = this.particleScale;
		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);

		boolean flipU = this.getEntityId() % 2 == 0;
		boolean flipV = this.getEntityId() % 4 < 2;

		double minU = flipU ? particleIcon.getMaxU() : particleIcon.getMinU();
		double maxU = flipU ? particleIcon.getMinU() : particleIcon.getMaxU();
		double minV = flipV ? particleIcon.getMaxV() : particleIcon.getMinV();
		double maxV = flipV ? particleIcon.getMinV() : particleIcon.getMaxV();

		tess.addVertexWithUV((double) (pX - fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ - fZ * scale - sZ * scale), maxU, maxV);
		tess.addVertexWithUV((double) (pX - fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ - fZ * scale + sZ * scale), maxU, minV);
		tess.addVertexWithUV((double) (pX + fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ + fZ * scale + sZ * scale), minU, minV);
		tess.addVertexWithUV((double) (pX + fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ + fZ * scale - sZ * scale), minU, maxV);
	}
}