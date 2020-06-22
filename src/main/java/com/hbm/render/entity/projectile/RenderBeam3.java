package com.hbm.render.entity.projectile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBeam3 extends Render {
	
	Random rand = new Random();

	@Override
	public void doRender(Entity rocket, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		float radius = 0.12F;
		//float radius = 0.06F;
		int distance = 4;
		Tessellator tessellator = Tessellator.instance;

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glTranslatef((float) x, (float) y, (float) z);

		GL11.glRotatef(rocket.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-rocket.rotationPitch, 1.0F, 0.0F, 0.0F);

		boolean red = true;
		boolean green = false;
		boolean blue = true;

		for (float o = 0; o <= radius; o += radius / 8) {
			float color = 1f - (o * 8.333f);
			if (color < 0)
				color = 0;
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f);
			tessellator.addVertex(0 + o, 0 - o, 0);
			tessellator.addVertex(0 + o, 0 + o, 0);
			tessellator.addVertex(0 + o, 0 + o, 0 + distance);
			tessellator.addVertex(0 + o, 0 - o, 0 + distance);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f);
			tessellator.addVertex(0 - o, 0 - o, 0);
			tessellator.addVertex(0 + o, 0 - o, 0);
			tessellator.addVertex(0 + o, 0 - o, 0 + distance);
			tessellator.addVertex(0 - o, 0 - o, 0 + distance);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f);
			tessellator.addVertex(0 - o, 0 + o, 0);
			tessellator.addVertex(0 - o, 0 - o, 0);
			tessellator.addVertex(0 - o, 0 - o, 0 + distance);
			tessellator.addVertex(0 - o, 0 + o, 0 + distance);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f);
			tessellator.addVertex(0 + o, 0 + o, 0);
			tessellator.addVertex(0 - o, 0 + o, 0);
			tessellator.addVertex(0 - o, 0 + o, 0 + distance);
			tessellator.addVertex(0 + o, 0 + o, 0 + distance);
			tessellator.draw();
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/PlasmaBeam.png");
	}
}
