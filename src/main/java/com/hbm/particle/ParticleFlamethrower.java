package com.hbm.particle;

import java.awt.Color;

import com.hbm.main.ModEventHandlerClient;
import com.hbm.particle.helper.FlameCreator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleFlamethrower extends EntityFXRotating {

	public ParticleFlamethrower(World world, double x, double y, double z, int type) {
		super(world, x, y, z);
		particleIcon = ModEventHandlerClient.particleBase;
		this.particleMaxAge = 20 + rand.nextInt(10);
		this.particleScale = 0.5F;

		this.motionX = world.rand.nextGaussian() * 0.02;
		this.motionZ = world.rand.nextGaussian() * 0.02;
		
		float initialColor = 15F + rand.nextFloat() * 25F;

		if(type == FlameCreator.META_BALEFIRE) initialColor = 65F + rand.nextFloat() * 35F;
		if(type == FlameCreator.META_DIGAMMA) initialColor = 0F - rand.nextFloat() * 15F;

		Color color = Color.getHSBColor(initialColor / 255F, 1F, 1F);
		this.particleRed = color.getRed() / 255F;
		this.particleGreen = color.getGreen() / 255F;
		this.particleBlue = color.getBlue() / 255F;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.particleAge++;

		if(this.particleAge >= this.particleMaxAge) {
			this.setDead();
		}

		this.motionX *= 0.91D;
		this.motionY *= 0.91D;
		this.motionZ *= 0.91D;
		
		this.motionY += 0.01D;
		this.prevRotationPitch = this.rotationPitch;
		this.rotationPitch += 30 * ((this.getEntityId() % 2) - 0.5);
		
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
	}

	@Override
	public void renderParticle(Tessellator tess, float interp, float sX, float sY, float sZ, float dX, float dZ) {

		double ageScaled = (double) this.particleAge / (double) this.particleMaxAge;

		this.particleAlpha = (float) Math.pow(1 - Math.min(ageScaled, 1), 0.5);
		float add = 0.75F - (float) ageScaled;

		tess.setColorRGBA_F(this.particleRed + add, this.particleGreen + add, this.particleBlue + add, this.particleAlpha * 0.5F);
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);

		double scale = (ageScaled * 1.25 + 0.25) * particleScale;
		renderParticleRotated(tess, interp, sX, sY, sZ, dX, dZ, scale);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}
}
