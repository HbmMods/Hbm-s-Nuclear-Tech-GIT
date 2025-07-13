package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;

public class ModelArmorWingsPheo extends ModelArmorBase {

	ModelRendererObj axe;

	public ModelArmorWingsPheo() {
		super(0);
		this.axe = new ModelRendererObj(ResourceManager.armor_axepack, "Wings");
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		this.body.copyTo(this.axe);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.wings_pheo);
		this.axe.render(scaleFactor);

		GL11.glPushMatrix();
		float lastX = OpenGlHelper.lastBrightnessX;
		float lastY = OpenGlHelper.lastBrightnessY;
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		double pixel = 0.0625D;

		if(entity.isSneaking()) {
			GL11.glRotated(28.6479D, 1, 0, 0);
		}

		GL11.glPushMatrix();
		GL11.glTranslated(0, pixel * 15, pixel * 5.5);
		renderFlame();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, pixel * 3, pixel * 5.5);
		GL11.glRotated(-25, 0, 1, 0);
		GL11.glRotated(-90, 0, 0, 1);
		GL11.glTranslated(0, pixel * 5, 0);
		renderFlame();

		GL11.glPushMatrix();
		GL11.glTranslated(0, -pixel * 5, 0);
		GL11.glRotated(45, 0, 0, 1);
		GL11.glTranslated(-pixel, pixel * 5.5, 0);
		renderFlame();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, -pixel * 5, 0);
		GL11.glRotated(-45, 0, 0, 1);
		GL11.glTranslated(pixel, pixel * 5.5, 0);
		renderFlame();
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, pixel * 15, pixel * 5.5);
		renderFlame();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, pixel * 3, pixel * 5.5);
		GL11.glRotated(25, 0, 1, 0);
		GL11.glRotated(90, 0, 0, 1);
		GL11.glTranslated(0, pixel * 5, 0);
		renderFlame();

		GL11.glPushMatrix();
		GL11.glTranslated(0, -pixel * 5, 0);
		GL11.glRotated(45, 0, 0, 1);
		GL11.glTranslated(-pixel, pixel * 5.5, 0);
		renderFlame();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, -pixel * 5, 0);
		GL11.glRotated(-45, 0, 0, 1);
		GL11.glTranslated(pixel, pixel * 5.5, 0);
		renderFlame();
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);
	}

	private static void renderFlame() {

		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_TRIANGLES);

		double b = 0.125D;
		double t = 0.375;
		double w = 0.0625D;
		double s2 = Math.sqrt(2D) / 2D * w;
		int colorBase = 0x808080;
		int colorFlame = 0x004040;
		int colorTip = 0x000000;

		tess.setColorOpaque_I(colorBase);
		tess.addVertex(0, 0, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(w, b, 0);
		tess.addVertex(s2, b, s2);

		tess.setColorOpaque_I(colorBase);
		tess.addVertex(0, 0, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(s2, b, s2);
		tess.addVertex(0, b, w);

		tess.setColorOpaque_I(colorBase);
		tess.addVertex(0, 0, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(0, b, w);
		tess.addVertex(-s2, b, s2);

		tess.setColorOpaque_I(colorBase);
		tess.addVertex(0, 0, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(-s2, b, s2);
		tess.addVertex(-w, b, 0);

		tess.setColorOpaque_I(colorBase);
		tess.addVertex(0, 0, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(-w, b, 0);
		tess.addVertex(-s2, b, -s2);

		tess.setColorOpaque_I(colorBase);
		tess.addVertex(0, 0, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(-s2, b, -s2);
		tess.addVertex(0, b, -w);

		tess.setColorOpaque_I(colorBase);
		tess.addVertex(0, 0, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(0, b, -w);
		tess.addVertex(s2, b, -s2);

		tess.setColorOpaque_I(colorBase);
		tess.addVertex(0, 0, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(s2, b, -s2);
		tess.addVertex(w, b, 0);

		////////////////////////

		tess.setColorOpaque_I(colorTip);
		tess.addVertex(0, t, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(w, b, 0);
		tess.addVertex(s2, b, s2);

		tess.setColorOpaque_I(colorTip);
		tess.addVertex(0, t, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(s2, b, s2);
		tess.addVertex(0, b, w);

		tess.setColorOpaque_I(colorTip);
		tess.addVertex(0, t, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(0, b, w);
		tess.addVertex(-s2, b, s2);

		tess.setColorOpaque_I(colorTip);
		tess.addVertex(0, t, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(-s2, b, s2);
		tess.addVertex(-w, b, 0);

		tess.setColorOpaque_I(colorTip);
		tess.addVertex(0, t, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(-w, b, 0);
		tess.addVertex(-s2, b, -s2);

		tess.setColorOpaque_I(colorTip);
		tess.addVertex(0, t, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(-s2, b, -s2);
		tess.addVertex(0, b, -w);

		tess.setColorOpaque_I(colorTip);
		tess.addVertex(0, t, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(0, b, -w);
		tess.addVertex(s2, b, -s2);

		tess.setColorOpaque_I(colorTip);
		tess.addVertex(0, t, 0);
		tess.setColorOpaque_I(colorFlame);
		tess.addVertex(s2, b, -s2);
		tess.addVertex(w, b, 0);

		tess.draw();
	}
}
