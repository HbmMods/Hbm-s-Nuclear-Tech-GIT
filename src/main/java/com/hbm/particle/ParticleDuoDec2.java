package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleDuoDec2 extends EntityFX {

	public ParticleDuoDec2(TextureManager texman, World world, double x, double y, double z) {
		super(world, x, y, z);
		particleIcon = ModEventHandlerClient.particleLen;
		this.particleRed =  this.particleGreen = this.particleBlue = 1F - world.rand.nextFloat() * 0.2F;
		this.particleScale = 0.3F;
		this.particleMaxAge = 50 + world.rand.nextInt(50);
		this.particleGravity = 0.2F;
	}

	public int getFXLayer() {
		return 1;
	}

	public void onUpdate() {
		super.onUpdate();

		if(!this.onGround) {
			this.motionX += rand.nextGaussian() * 0.002D;
			this.motionZ += rand.nextGaussian() * 0.002D;
			
			if(this.motionY < -0.025D)
				this.motionY = -0.025D;
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

	    // Vertex 1
	    tess.addVertexWithUV((double) (pX - fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ - fZ * scale - sZ * scale), particleIcon.getMaxU(), particleIcon.getMaxV());

	    // Vertex 2
	    tess.addVertexWithUV((double) (pX - fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ - fZ * scale + sZ * scale), particleIcon.getMaxU(), particleIcon.getMinV());

	    // Vertex 3
	    tess.addVertexWithUV((double) (pX + fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ + fZ * scale + sZ * scale), particleIcon.getMinU(), particleIcon.getMinV());

	    // Vertex 4
	    tess.addVertexWithUV((double) (pX + fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ + fZ * scale - sZ * scale), particleIcon.getMinU(), particleIcon.getMaxV());
	}

}