package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.network.TileEntityConnector;

import net.minecraft.tileentity.TileEntity;

public class RenderConnector extends RenderPylonBase {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		TileEntityConnector con = (TileEntityConnector) te;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		double s = 0.5;
		GL11.glScaled(s, s, s);
		bindTexture(ResourceManager.universal);
		ResourceManager.barrel.renderAll();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		this.renderSingleLine(con, x, y, z);
		GL11.glPopMatrix();
	}
}
