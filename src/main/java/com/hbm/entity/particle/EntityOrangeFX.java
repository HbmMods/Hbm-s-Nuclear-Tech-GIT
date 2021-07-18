package com.hbm.entity.particle;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionNukeGeneric;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityOrangeFX extends EntityModFX {

	public EntityOrangeFX(World world) {
    	super(world, 0, 0, 0);
    }

	public EntityOrangeFX(World p_i1225_1_, double p_i1225_2_, double p_i1225_4_, double p_i1225_6_, double p_i1225_8_, double p_i1225_10_, double p_i1225_12_)
    {
        this(p_i1225_1_, p_i1225_2_, p_i1225_4_, p_i1225_6_, p_i1225_8_, p_i1225_10_, p_i1225_12_, 1.0F);
    }

	public EntityOrangeFX(World p_i1226_1_, double p_i1226_2_, double p_i1226_4_, double p_i1226_6_, double p_i1226_8_, double p_i1226_10_, double p_i1226_12_, float p_i1226_14_)
    {
        super(p_i1226_1_, p_i1226_2_, p_i1226_4_, p_i1226_6_, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += p_i1226_8_;
        this.motionY += p_i1226_10_;
        this.motionZ += p_i1226_12_;
        this.particleRed = this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896D);
        this.particleScale *= 0.75F;
        this.particleScale *= p_i1226_14_;
        this.smokeParticleScale = this.particleScale;
        this.noClip = false;
    }

	@Override
	public void onUpdate() {

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (maxAge < 900) {
			maxAge = rand.nextInt(301) + 900;
		}

		if (!worldObj.isRemote && rand.nextInt(50) == 0)
			ExplosionChaos.poison(worldObj, (int) posX, (int) posY, (int) posZ, 2);

		this.particleAge++;

		if (this.particleAge >= maxAge) {
			this.setDead();
		}

		this.motionX *= 0.8599999785423279D;
		this.motionY *= 0.8599999785423279D;
		this.motionZ *= 0.8599999785423279D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
		
		this.motionY -= 0.1;
		
		double subdivisions = 4;
		
		for(int i = 0; i < subdivisions; i++) {
	
			this.posX += this.motionX/subdivisions;
			this.posY += this.motionY/subdivisions;
			this.posZ += this.motionZ/subdivisions;
			
			if(worldObj.getBlock((int) posX, (int) posY, (int) posZ).getMaterial() != Material.air) {
				this.setDead();
				
				for(int a = -1; a < 2; a++) {
					for(int b = -1; b < 2; b++) {
						for(int c = -1; c < 2; c++) {
							
							Block bl = worldObj.getBlock((int) posX + a, (int) posY + b, (int) posZ + c);
							if(bl == Blocks.grass) {
								worldObj.setBlock((int) posX + a, (int) posY + b, (int) posZ + c, Blocks.dirt, 1, 3);
							} else {
								ExplosionNukeGeneric.solinium(worldObj, (int) posX + a, (int) posY + b, (int) posZ + c);
							}
						}
					}
				}
			}
		}
	}
}
