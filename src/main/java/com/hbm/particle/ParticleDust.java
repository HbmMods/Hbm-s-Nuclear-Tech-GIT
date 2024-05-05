package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleDust extends EntityFX {
	public ParticleDust(World world, double x, double y, double z, double mX, double mY, double mZ) {
		super(world, x, y, z, mX, mY, mZ);
		particleIcon = ModEventHandlerClient.particleBase;
        this.particleRed = 0.4f;
        this.particleGreen = 0.2f;
        this.particleBlue = 0.1f;
		this.particleScale = 4F;
        this.motionX = mX;
        this.motionY = mY;
        this.motionZ = mZ;
        this.particleAge = 1;
		this.particleMaxAge = 50 + world.rand.nextInt(50);
		this.particleAlpha = 0.5F;
		this.noClip = true;

	}


	public void onUpdate() {
		super.onUpdate();

	}

	public int getFXLayer() {
		return 1;
	}

	public void renderParticle(Tessellator tess, float interp, float fX, float fY, float fZ, float sX, float sZ) {

		tess.setNormal(0.0F, 1.0F, 0.0F);
		
		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);

		float scale = this.particleScale;
		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);

		tess.addVertexWithUV((double) (pX - fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ - fZ * scale - sZ * scale), particleIcon.getMaxU(), particleIcon.getMaxV());
		tess.addVertexWithUV((double) (pX - fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ - fZ * scale + sZ * scale), particleIcon.getMaxU(), particleIcon.getMinV());
		tess.addVertexWithUV((double) (pX + fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ + fZ * scale + sZ * scale), particleIcon.getMinU(), particleIcon.getMinV());
		tess.addVertexWithUV((double) (pX + fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ + fZ * scale - sZ * scale), particleIcon.getMinU(), particleIcon.getMaxV());
	}
}