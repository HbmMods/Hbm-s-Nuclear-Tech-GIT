package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class ParticleDebugLine extends EntityFX {
	
	int color;

	public ParticleDebugLine(World world, double x, double y, double z, double lx, double ly, double lz, int color) {
		super(world, x, y, z, lx, ly, lz);
		this.motionX = lx;
		this.motionY = ly;
		this.motionZ = lz;
		this.color = color;
		this.particleMaxAge = 60;
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if(this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		double pX = this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX;
		double pY = this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY;
		double pZ = this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ;

		double mX = pX + motionX;
		double mY = pY + motionY;
		double mZ = pZ + motionZ;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_COLOR_MATERIAL);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		tess.startDrawing(GL11.GL_LINES);
		tess.setBrightness((int) (240 - (240 * (this.particleAge + interp) / this.particleMaxAge)));
		tess.setColorOpaque_I(color);
		tess.addVertex(pX, pY, pZ);
		tess.addVertex(mX, mY, mZ);
		tess.draw();
		
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}
}
