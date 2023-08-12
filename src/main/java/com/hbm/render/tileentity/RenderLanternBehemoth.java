package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.deco.TileEntityLanternBehemoth;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderLanternBehemoth extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		TileEntityLanternBehemoth lantern = (TileEntityLanternBehemoth) tile;
		if(lantern.isBroken) {
			GL11.glRotated(5, 1, 0, 0);
			GL11.glRotated(10, 0, 0, 1);
		}

		bindTexture(ResourceManager.lantern_rusty_tex);
		ResourceManager.lantern.renderPart("Lantern");

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		
		if(lantern.isBroken) {
			float mult = (float) (Math.sin(System.currentTimeMillis() / 200D) / 2 + 0.5);
			GL11.glColor3f(1F * mult, 0, 0);
		} else {
			float mult = (float) (Math.sin(System.currentTimeMillis() / 200D) / 2 + 0.5) * 0.5F + 0.5F;
			GL11.glColor3f(0, 1F * mult, 0);
		}
		ResourceManager.lantern.renderPart("Light");
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}
}
