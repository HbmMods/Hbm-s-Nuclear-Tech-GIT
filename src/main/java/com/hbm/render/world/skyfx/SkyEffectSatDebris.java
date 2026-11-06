package com.hbm.render.world.skyfx;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class SkyEffectSatDebris extends SkyEffect {
	
	protected int lifetime;
	protected int burnDelay;

	public SkyEffectSatDebris(World world) {
		super(world);
		
		this.lifetime = 900 + world.rand.nextInt(300);
		this.burnDelay = 200 + world.rand.nextInt(200);
	}

	@Override
	public void updateTick() {
		super.updateTick();
		
		if(this.ticksExisted > lifetime) this.expire();
	}

	@Override
	public void renderTick(float interp) {
		
		GL11.glPushMatrix();

		double x = prevPosX + (posX - prevPosX) * interp;
		double y = prevPosY + (posY - prevPosY) * interp;
		double z = prevPosZ + (posZ - prevPosZ) * interp;

		float angleYaw = prevYaw + (yaw - prevYaw) * interp;
		float anglePitch = prevPitch + (pitch - prevPitch) * interp;
		
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(angleYaw, 0, 1, 0);
		GL11.glRotatef(anglePitch + 45, 0, 0, 1);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		int dark = 0x404040;
		int light = 0xffffff;
		double length = 10;
		double widthB = 0.02D;
		double widthF = 0.25D;
		double alpha = ticksExisted < burnDelay ? 0D : ticksExisted > lifetime - 100 ? 1D - (ticksExisted - lifetime + 100) / 100D : (ticksExisted - burnDelay) / (double) burnDelay;
		int alphaI = (int) (alpha * 255);
		
		Tessellator tess = Tessellator.instance;
		
		tess.startDrawingQuads();
		tess.setBrightness(240);
		tess.setNormal(0F, 1F, 0F);
		tess.setColorRGBA_I(dark, alphaI);
		tess.addVertex(length, widthB, -widthB); tess.addVertex(length, widthB, widthB);
		tess.setColorRGBA_I(light, alphaI);
		tess.addVertex(0, widthF, widthF); tess.addVertex(0, widthF, -widthF);
		tess.setColorRGBA_I(dark, alphaI);
		tess.addVertex(length, -widthB, -widthB); tess.addVertex(length, -widthB, widthB);
		tess.setColorRGBA_I(light, alphaI);
		tess.addVertex(0, -widthF, widthF); tess.addVertex(0, -widthF, -widthF);
		tess.setColorRGBA_I(dark, alphaI);
		tess.addVertex(length, -widthB, widthB); tess.addVertex(length, widthB, widthB);
		tess.setColorRGBA_I(light, alphaI);
		tess.addVertex(0, widthF, widthF); tess.addVertex(0, -widthF, widthF);
		tess.setColorRGBA_I(dark, alphaI);
		tess.addVertex(length, -widthB, -widthB); tess.addVertex(length, widthB, -widthB);
		tess.setColorRGBA_I(light, alphaI);
		tess.addVertex(0, widthF, -widthF); tess.addVertex(0, -widthF, -widthF);
		tess.setColorRGBA_I(dark, alphaI);
		tess.addVertex(length, widthB, widthB); tess.addVertex(length, widthB, -widthB);
		tess.addVertex(length, -widthB, -widthB); tess.addVertex(length, -widthB, widthB);
		tess.setColorRGBA_I(light, alphaI);
		tess.addVertex(0, widthF, widthF); tess.addVertex(0, widthF, -widthF);
		tess.addVertex(0, -widthF, -widthF); tess.addVertex(0, -widthF, widthF);
		tess.draw();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		
		GL11.glPopMatrix();
	}
}
