package com.hbm.particle;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class ParticleSpark extends EntityFX {

	List<double[]> steps = new ArrayList();
	int thresh;

	public ParticleSpark(World world, double x, double y, double z, double mX, double mY, double mZ) {
		super(world, x, y, z, mX, mY, mZ);
		thresh = 4 + rand.nextInt(3);
		steps.add(new double[] { motionX, motionY, motionZ });
		this.particleMaxAge = 20 + rand.nextInt(10);
		this.particleGravity = 0.5F;
	}

	public int getFXLayer() {
		return 3;
	}

	public void onUpdate() {

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if(this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}

		steps.add(new double[] { motionX, motionY, motionZ });

		while(steps.size() > thresh)
			steps.remove(0);

		this.motionY -= 0.04D * (double) this.particleGravity;
		double lastY = this.motionY;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		if(this.onGround) {
			this.onGround = false;
			motionY = -lastY * 0.8D;
		}
	}

	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		if(steps.size() < 2)
			return;

		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glLineWidth(3F);

		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);
		
		tess.startDrawing(3);
		tess.setBrightness(240);
		tess.setColorOpaque_I(0xffffff);

		double[] prev = new double[] { pX, pY, pZ };

		for(int i = 1; i < steps.size(); i++) {

			double[] curr = new double[] { prev[0] + steps.get(i)[0], prev[1] + steps.get(i)[1], prev[2] + steps.get(i)[2] };

			tess.addVertex(prev[0], prev[1], prev[2]);

			prev = curr;
		}
		tess.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}
}
