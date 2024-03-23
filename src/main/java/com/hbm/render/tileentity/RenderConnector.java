package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.network.TileEntityConnector;

import net.minecraft.tileentity.TileEntity;

public class RenderConnector extends RenderPylonBase {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		TileEntityConnector con = (TileEntityConnector) te;
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		
		switch(te.getBlockMetadata()) {
		case 0: GL11.glRotated(180, 1, 0, 0); break;
		case 1: break;
		case 2: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(180, 0, 0, 1); break;
		case 3: GL11.glRotated(90, 1, 0, 0); break;
		case 4: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(90, 0, 0, 1); break;
		case 5: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(270, 0, 0, 1); break;
		}

		GL11.glTranslated(0, -0.5F, 0);
		
		bindTexture(ResourceManager.connector_tex);
		ResourceManager.connector.renderAll();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		this.renderLinesGeneric(con, x, y, z);
		GL11.glPopMatrix();
	}
}
