package com.hbm.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class NukeCloudFX extends EntitySmokeFX {

	public NukeCloudFX(World world, double x, double y, double z, double moX, double moY, double moZ) {
		super(world, x, y, z, moX, moY, moZ, 1.0F);
		this.particleMaxAge *= 3;
	}

	public NukeCloudFX(World world, double x, double y, double z, double moX, double moY, double moZ, float scale) {
		super(world, x, y, z, moX, moY, moZ, scale);
		this.particleMaxAge *= 3;
	}
	
	@Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        //this.motionY += 0.004D;
        //this.moveEntity(/*this.motionX, this.motionY, this.motionZ*/1, 1, 1);

        /*if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }*/
    }

}
