package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.network.TileEntityPylonLarge;

import net.minecraft.tileentity.TileEntity;

public class RenderPylonLarge extends RenderPylonBase {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);

		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(135, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(45, 0F, 1F, 0F); break;
		}
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		bindTexture(ResourceManager.pylon_large_tex);
		ResourceManager.pylon_large.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		TileEntityPylonLarge pyl = (TileEntityPylonLarge)tile;
		this.renderLinesGeneric(pyl, x, y, z);
		GL11.glPopMatrix();
	}
}
