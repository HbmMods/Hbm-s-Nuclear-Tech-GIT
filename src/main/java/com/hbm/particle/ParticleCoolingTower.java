package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleCoolingTower extends EntityFX {

	private float baseScale = 1.0F;
	private float maxScale = 1.0F;
	private float lift = 0.3F;

	public ParticleCoolingTower(TextureManager texman, World world, double x, double y, double z) {
		super(world, x, y, z);
		particleIcon = ModEventHandlerClient.particleBase;
		this.particleRed = this.particleGreen = this.particleBlue = 0.9F + world.rand.nextFloat() * 0.05F;
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
