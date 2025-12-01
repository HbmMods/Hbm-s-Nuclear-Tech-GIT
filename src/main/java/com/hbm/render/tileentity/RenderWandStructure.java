package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockWandStructure.TileEntityWandStructure;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderWandStructure extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		if(!(tile instanceof TileEntityWandStructure)) return;
		TileEntityWandStructure structure = (TileEntityWandStructure) tile;

		if(tile.getBlockMetadata() != 0) return;

		GL11.glPushMatrix();
		{

			double x1 = x;
			double y1 = y + 1;
			double z1 = z;

			double x2 = x + structure.sizeX;
			double y2 = y + structure.sizeY + 1;
			double z2 = z + structure.sizeZ;


			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1F, 1F, 1F);



			Tessellator tess = Tessellator.instance;
			tess.startDrawing(GL11.GL_LINES);
			tess.setBrightness(240);
			tess.setColorRGBA_F(1F, 1F, 1F, 1F);

			// top
			tess.addVertex(x1, y2, z1);
			tess.addVertex(x1, y2, z2);

			tess.addVertex(x1, y2, z2);
			tess.addVertex(x2, y2, z2);

			tess.addVertex(x2, y2, z2);
			tess.addVertex(x2, y2, z1);

			tess.addVertex(x2, y2, z1);
			tess.addVertex(x1, y2, z1);

			// bottom
			tess.addVertex(x1, y1, z1);
			tess.addVertex(x1, y1, z2);

			tess.addVertex(x1, y1, z2);
			tess.addVertex(x2, y1, z2);

			tess.addVertex(x2, y1, z2);
			tess.addVertex(x2, y1, z1);

			tess.addVertex(x2, y1, z1);
			tess.addVertex(x1, y1, z1);

			// sides
			tess.addVertex(x1, y1, z1);
			tess.addVertex(x1, y2, z1);

			tess.addVertex(x2, y1, z1);
			tess.addVertex(x2, y2, z1);

			tess.addVertex(x2, y1, z2);
			tess.addVertex(x2, y2, z2);

			tess.addVertex(x1, y1, z2);
			tess.addVertex(x1, y2, z2);

			tess.draw();


			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);

		}
		GL11.glPopMatrix();
	}

}
