package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;
import com.hbm.util.Vec3NT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleAshes extends EntityFXRotating {

	public ParticleAshes(World world, double x, double y, double z, float scale) {
		super(world, x, y, z);
		particleIcon = ModEventHandlerClient.particleBase;
		this.particleMaxAge = 1200 + rand.nextInt(20);
		this.particleScale = scale * 0.9F + rand.nextFloat() * 0.2F;
		
		this.particleGravity = 0.01F;

		this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.1F + 0.1F;
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
		
		this.motionY -= particleGravity;
		this.prevRotationPitch = this.rotationPitch;
		
		if(!this.onGround) this.rotationPitch += 2 * ((this.getEntityId() % 2) - 0.5);

		this.motionX *= 0.95D;
		this.motionY *= 0.99D;
		this.motionZ *= 0.95D;
		
		boolean wasOnGround = this.onGround;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		if(!wasOnGround && this.onGround) this.rotationPitch = rand.nextFloat() * 360F;
		
		if(this.getEntityId() % 5 == 0 && this.onGround && rand.nextInt(15) == 0) {
			worldObj.spawnParticle("smoke", posX, posY + 0.125, posZ, 0, 0.05, 0);
		}
	}

	@Override
	public void renderParticle(Tessellator tess, float interp, float sX, float sY, float sZ, float dX, float dZ) {

		float timeLeft = this.particleMaxAge - (this.particleAge + interp);
		
		if(timeLeft < 40) {
			this.particleAlpha = timeLeft / 40F;
		} else {
			this.particleAlpha = 1F;
		}
		
		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		tess.setNormal(0.0F, 1.0F, 0.0F);
		
		if(this.onGround) {
			float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
			float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
			float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);
			
			Vec3NT vec = new Vec3NT(particleScale, 0, particleScale).rotateAroundYDeg(this.rotationPitch);

			tess.addVertexWithUV(pX + vec.xCoord, pY + 0.05, pZ + vec.zCoord, particleIcon.getMaxU(), particleIcon.getMaxV());
			vec.rotateAroundYDeg(90);
			tess.addVertexWithUV(pX + vec.xCoord, pY + 0.05, pZ + vec.zCoord, particleIcon.getMaxU(), particleIcon.getMinV());
			vec.rotateAroundYDeg(90);
			tess.addVertexWithUV(pX + vec.xCoord, pY + 0.05, pZ + vec.zCoord, particleIcon.getMinU(), particleIcon.getMinV());
			vec.rotateAroundYDeg(90);
			tess.addVertexWithUV(pX + vec.xCoord, pY + 0.05, pZ + vec.zCoord, particleIcon.getMinU(), particleIcon.getMaxV());
		} else {
			renderParticleRotated(tess, interp, sX, sY, sZ, dX, dZ, this.particleScale);
		}
	}
}
