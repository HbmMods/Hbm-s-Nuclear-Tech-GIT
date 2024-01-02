package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAtmoTower extends TileEntitySpecialRenderer {
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.0D, y+60, z + 0.0D);
		GL11.glScaled(8, 8, 8);
		GL11.glRotatef(90F, 90F, 0F, 0F);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glRotatef(180F, 0F, 1F, 0F);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.atmo_tower_tex);
		ResourceManager.atmo_tower.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}