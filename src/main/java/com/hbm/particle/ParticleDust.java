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
        this.particleRed = 0.4f;
        this.particleGreen = 0.2f;
        this.particleBlue = 0.1f;
		this.particleScale = 4F;
        this.motionX = mX;
        this.motionY = mY;
        this.motionZ = mZ;
        this.particleAge = 1;
		this.particleMaxAge = 50 + world.rand.nextInt(50);
		this.particleGravity = 0.2F;
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


}