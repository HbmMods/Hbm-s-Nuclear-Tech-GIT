package com.hbm.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleBlackPowderSpark extends EntityFX {

	public ParticleBlackPowderSpark(World world, double x, double y, double z, double mX, double mY, double mZ) {
		super(world, x, y, z, mX, mY, mZ);

		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
		
		float f = this.rand.nextFloat() * 0.1F + 0.2F;
		this.particleRed = f + 0.7F;
		this.particleGreen = f + 0.5F;
		this.particleBlue = f;
		this.setParticleTextureIndex(0);
		this.setSize(0.02F, 0.02F);
		this.particleScale *= this.rand.nextFloat() * 0.6F + 0.5F;
		this.particleMaxAge = 15 + this.rand.nextInt(5);
		
		this.particleGravity = 0.01F;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= particleGravity;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.95D;
		this.motionY *= 0.95D;
		this.motionZ *= 0.95D;

		if(this.particleMaxAge-- <= 0) {
			this.setDead();
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float interp) {
		return 15728880;
	}
}
