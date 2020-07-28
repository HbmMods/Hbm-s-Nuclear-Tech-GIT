package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMeteor extends Render {

	public RenderMeteor() { }

	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef((rocket.ticksExisted % 360) * 10, 1, 1, 1);
		

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glScalef(5.0F, 5.0F, 5.0F);
		renderBlock(getEntityTexture(rocket), 0, 0, 0);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}
	
	public void renderBlock(ResourceLocation loc1, double x, double y, double z) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(180, 0F, 0F, 1F);
		Tessellator tesseract = Tessellator.instance;
		tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(-0.5, -0.5, -0.5, 1, 0);
			tesseract.addVertexWithUV(+0.5, -0.5, -0.5, 0, 0);
			tesseract.addVertexWithUV(+0.5, +0.5, -0.5, 0, 1);
			tesseract.addVertexWithUV(-0.5, +0.5, -0.5, 1, 1);
			this.bindTexture(loc1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(-0.5, -0.5, +0.5, 1, 0);
			tesseract.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
			tesseract.addVertexWithUV(-0.5, +0.5, -0.5, 0, 1);
			tesseract.addVertexWithUV(-0.5, +0.5, +0.5, 1, 1);
			this.bindTexture(loc1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(+0.5, -0.5, +0.5, 1, 0);
			tesseract.addVertexWithUV(-0.5, -0.5, +0.5, 0, 0);
			tesseract.addVertexWithUV(-0.5, +0.5, +0.5, 0, 1);
			tesseract.addVertexWithUV(+0.5, +0.5, +0.5, 1, 1);
			this.bindTexture(loc1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(+0.5, -0.5, -0.5, 1, 0);
			tesseract.addVertexWithUV(+0.5, -0.5, +0.5, 0, 0);
			tesseract.addVertexWithUV(+0.5, +0.5, +0.5, 0, 1);
			tesseract.addVertexWithUV(+0.5, +0.5, -0.5, 1, 1);
			this.bindTexture(loc1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(-0.5, -0.5, +0.5, 1, 0);
			tesseract.addVertexWithUV(+0.5, -0.5, +0.5, 0, 0);
			tesseract.addVertexWithUV(+0.5, -0.5, -0.5, 0, 1);
			tesseract.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);
			this.bindTexture(loc1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(+0.5, +0.5, +0.5, 1, 0);
			tesseract.addVertexWithUV(-0.5, +0.5, +0.5, 0, 0);
			tesseract.addVertexWithUV(-0.5, +0.5, -0.5, 0, 1);
			tesseract.addVertexWithUV(+0.5, +0.5, -0.5, 1, 1);
			this.bindTexture(loc1);
			tesseract.draw();
		GL11.glPopMatrix();
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/blocks/block_meteor_molten.png");
	}
}
