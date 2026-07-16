package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderPileVent extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch(te.getBlockMetadata() % 4) {
		case 0: GL11.glRotated(90, 0, 1, 0); break;
		case 1: GL11.glRotated(270, 0, 1, 0); break;
		case 2: GL11.glRotated(180, 0, 1, 0); break;
		case 3: GL11.glRotated(0, 0, 1, 0); break;
		}
		
		bindTexture(ResourceManager.pile_vent_tex);
		ResourceManager.pile_vent.renderPart("Pipe");
		GL11.glRotated((te.getWorldObj().getTotalWorldTime() % 360 + interp) * 45, 0, -1, 0);
		ResourceManager.pile_vent.renderPart("Fan");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
