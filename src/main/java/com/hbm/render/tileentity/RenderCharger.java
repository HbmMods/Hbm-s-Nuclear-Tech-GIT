package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityCharger;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCharger extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glRotatef(90, 0F, 1F, 0F);
		switch(tile.getBlockMetadata()) {
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.charger_tex);
		ResourceManager.charger.renderPart("Base");
		
		TileEntityCharger charger = (TileEntityCharger) tile;
		
		double time = (charger.lastUsingTicks + (charger.usingTicks - charger.lastUsingTicks) * interp) / (double) charger.delay;
		
		double extend = Math.min(1, time * 2);
		double swivel = Math.max(0, (time - 0.5) * 2);
		
		GL11.glPushMatrix();

		GL11.glTranslated(-0.34375D, 0.25D, 0);
		GL11.glRotated(10, 0, 0, 1);
		GL11.glTranslated(0.34375D, -0.25D, 0);
		
		GL11.glTranslated(0, -0.25 * extend, 0);
		
		//ResourceManager.charger.renderPart("Slide");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.28D, 0);
		GL11.glRotated(30 * swivel, 1, 0, 0);
		GL11.glTranslated(0, -0.28D, 0);
		ResourceManager.charger.renderPart("Left");
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.28D, 0);
		GL11.glRotated(-30 * swivel, 1, 0, 0);
		GL11.glTranslated(0, -0.28D, 0);
		ResourceManager.charger.renderPart("Right");
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

		GL11.glColor3f(1F, 0.75F, 0F);
		ResourceManager.charger.renderPart("Light");
		GL11.glColor3f(1F, 1F, 1F);

		GL11.glTranslated(-0.34375D, 0.25D, 0);
		GL11.glRotated(10, 0, 0, 1);
		GL11.glTranslated(0.34375D, -0.25D, 0);
		GL11.glTranslated(0, -0.25 * extend, 0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		ResourceManager.charger.renderPart("Slide");
		
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopAttrib();
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
