package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntitySolarBoiler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class RenderSolarBoiler extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPushMatrix();
		GL11.glRotatef(90, 0F, 1F, 0F);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.solar_tex);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.solar_boiler.renderPart("Base");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		
		if(te instanceof TileEntitySolarBoiler && Minecraft.getMinecraft().gameSettings.fancyGraphics) {
			TileEntitySolarBoiler boiler = (TileEntitySolarBoiler) te;

			Tessellator tess = Tessellator.instance;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0);
			GL11.glDepthMask(false);
			
			for(ChunkCoordinates co : boiler.secondary) {
				
				int dx = boiler.xCoord - co.posX;
				int dy = boiler.yCoord - co.posY;
				int dz = boiler.zCoord - co.posZ;
				
				double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

				float min = 0.005F;
				float max = 0.01F;
				
				GL11.glPushMatrix();
				
				GL11.glTranslated(-dx, -dy, -dz);

				double pitch = Math.toDegrees(-Math.asin((dy + 0.5) / dist)) + 90;
				double yaw = Math.toDegrees(-Math.atan2(dz, dx)) + 180;

				GL11.glTranslated(0, 1, 0);
				GL11.glRotated(yaw, 0, 1, 0);
				GL11.glRotated(pitch, 0, 0, 1);
				GL11.glTranslated(0, -1, 0);
				
				tess.startDrawingQuads();
				tess.setColorRGBA_F(1F, 1F, 1F, max);
				tess.addVertex(0.5, 1.0625, 0.5);
				tess.addVertex(0.5, 1.0625, -0.5);
				tess.setColorRGBA_F(1F, 1F, 1F, min);
				tess.addVertex(0.5, dist, -0.5);
				tess.addVertex(0.5, dist, 0.5);

				tess.setColorRGBA_F(1F, 1F, 1F, max);
				tess.addVertex(-0.5, 1.0625, 0.5);
				tess.addVertex(-0.5, 1.0625, -0.5);
				tess.setColorRGBA_F(1F, 1F, 1F, min);
				tess.addVertex(-0.5, dist, -0.5);
				tess.addVertex(-0.5, dist, 0.5);

				tess.setColorRGBA_F(1F, 1F, 1F, max);
				tess.addVertex(0.5, 1.0625, 0.5);
				tess.addVertex(-0.5, 1.0625, 0.5);
				tess.setColorRGBA_F(1F, 1F, 1F, min);
				tess.addVertex(-0.5, dist, 0.5);
				tess.addVertex(0.5, dist, 0.5);

				tess.setColorRGBA_F(1F, 1F, 1F, max);
				tess.addVertex(0.5, 1.0625, -0.5);
				tess.addVertex(-0.5, 1.0625, -0.5);
				tess.setColorRGBA_F(1F, 1F, 1F, min);
				tess.addVertex(-0.5, dist, -0.5);
				tess.addVertex(0.5, dist, -0.5);
				
				tess.draw();
				GL11.glPopMatrix();
			}

			GL11.glDepthMask(true);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}

		GL11.glPopMatrix();
	}

}
