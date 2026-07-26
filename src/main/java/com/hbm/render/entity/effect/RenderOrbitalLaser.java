package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderOrbitalLaser extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDepthMask(false);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		GL11.glColor3f(1F, 0F, 0F);

		Vec3 vector = Vec3.createVectorHelper(0.5D, 0, 0);

		Tessellator tessellator = Tessellator.instance;
		RenderHelper.disableStandardItemLighting();

		for(int i = 0; i < 8; i++) {
			tessellator.startDrawing(6);
			tessellator.addVertex(vector.xCoord, 250.0D, vector.zCoord);
			tessellator.addVertex(vector.xCoord, 0.0D, vector.zCoord);
			vector.rotateAroundY(45);
			tessellator.addVertex(vector.xCoord, 0.0D, vector.zCoord);
			tessellator.addVertex(vector.xCoord, 250.0D, vector.zCoord);
			tessellator.draw();
		}

		for(int i = 0; i < 8; i++) {
			tessellator.startDrawing(6);
			tessellator.addVertex(vector.xCoord / 2, 250.0D, vector.zCoord / 2);
			tessellator.addVertex(vector.xCoord / 2, 0.0D, vector.zCoord / 2);
			vector.rotateAroundY(45);
			tessellator.addVertex(vector.xCoord / 2, 0.0D, vector.zCoord / 2);
			tessellator.addVertex(vector.xCoord / 2, 250.0D, vector.zCoord / 2);
			tessellator.draw();
		}
		GL11.glPopMatrix();

		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
