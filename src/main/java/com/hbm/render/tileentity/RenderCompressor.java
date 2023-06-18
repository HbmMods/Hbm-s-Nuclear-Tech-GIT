package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCompressor extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.compressor_tex);
		ResourceManager.compressor.renderPart("Compressor");
		
		double h = (System.currentTimeMillis() * 0.005) % 6D;
		
		if(h > 3) h = 6 - h;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, h - 3, 0);
		ResourceManager.compressor.renderPart("Pump");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated((System.currentTimeMillis() * -0.5) % 360, 1, 0, 0);
		GL11.glTranslated(0, -1.5, 0);
		ResourceManager.compressor.renderPart("Fan");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}
}
