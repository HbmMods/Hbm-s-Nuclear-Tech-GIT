package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityLaser;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderLaser extends Render {

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		this.doRender((EntityLaser)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityLaser laser, double x, double y, double z, float p_76986_8_,
			float p_76986_9_) {
		drawPowerLine(x, y, z,
				x + (laser.getPlayerCoord()[0] - laser.posX),
				y + (laser.getPlayerCoord()[1] - laser.posY),
				z + (laser.getPlayerCoord()[2] - laser.posZ));
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
	
	public void drawPowerLine(double x, double y, double z, double a, double b, double c) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
        tessellator.addVertex(x, y + 0.05F, z);
        tessellator.addVertex(x, y - 0.05F, z);
        tessellator.addVertex(a, b + 0.05F, c);
        tessellator.addVertex(a, b - 0.05F, c);
        tessellator.draw();
        tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
        tessellator.addVertex(x + 0.05F, y, z);
        tessellator.addVertex(x - 0.05F, y, z);
        tessellator.addVertex(a + 0.05F, b, c);
        tessellator.addVertex(a - 0.05F, b, c);
        tessellator.draw();
        tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
        tessellator.addVertex(x, y, z + 0.05F);
        tessellator.addVertex(x, y, z - 0.05F);
        tessellator.addVertex(a, b, c + 0.05F);
        tessellator.addVertex(a, b, c - 0.05F);
        tessellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
	}

}
