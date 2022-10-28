package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.network.TileEntitySubstation;

import net.minecraft.tileentity.TileEntity;

public class RenderSubstation extends RenderPylonBase {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);

		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 4:
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2:
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.substation_tex);
		ResourceManager.substation.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		TileEntitySubstation sub = (TileEntitySubstation) tile;
		this.renderLinesGeneric(sub, x, y, z);
		GL11.glPopMatrix();
	}

}
