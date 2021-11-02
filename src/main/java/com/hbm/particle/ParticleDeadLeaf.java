package com.hbm.particle;

import com.hbm.lib.RefStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleDeadLeaf extends EntityFX {
	
	public ParticleDeadLeaf(TextureManager texman, World world, double x, double y, double z) {
		super(world, x, y, z);
		this.particleRed = 0.7F + world.rand.nextFloat() * 0.05F;
		this.particleGreen = 0.2F + world.rand.nextFloat() * 0.05F;
		this.particleBlue = 0.2F + world.rand.nextFloat() * 0.05F;
		this.particleScale = 1F;
		this.particleMaxAge = 100 + world.rand.nextInt(20);
		this.particleGravity = 0.2F;
	}
	
	public int getFXLayer() {
		return 1;
	}
	
	public void onUpdate() {
		super.onUpdate();
		
		if(!this.onGround) {
			this.motionX += rand.nextGaussian() * 0.075D;
			this.motionZ += rand.nextGaussian() * 0.075D;
		}
	}
	
	@Override
	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		
		float f10 = this.particleScale * 0.1F;
		float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);
		
		tess.addVertexWithUV((double) (f11 - x * f10 - tx * f10), (double) (f12 - y * f10), (double) (f13 - z * f10 - tz * f10), (double) 0, (double) 0);
		tess.addVertexWithUV((double) (f11 - x * f10 + tx * f10), (double) (f12 + y * f10), (double) (f13 - z * f10 + tz * f10), (double) 0, (double) 1);
		tess.addVertexWithUV((double) (f11 + x * f10 + tx * f10), (double) (f12 + y * f10), (double) (f13 + z * f10 + tz * f10), (double) 1, (double) 1);
		tess.addVertexWithUV((double)(f11 + x * f10 - tx * f10), (double)(f12 - y * f10), (double)(f13 + z * f10 - tz * f10), (double)1, (double)0);
	}
}
