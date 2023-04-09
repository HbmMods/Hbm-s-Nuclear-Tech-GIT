package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderWatz extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		GL11.glPushMatrix();

		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.watz_tex);
		ResourceManager.watz.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
