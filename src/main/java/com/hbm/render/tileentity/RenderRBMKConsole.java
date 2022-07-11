package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.RBMKColumn;
import com.hbm.tileentity.machine.storage.TileEntityMachineFENSU;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRBMKConsole extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(0.5, 0, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.rbmk_console_tex);
		ResourceManager.rbmk_console.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		TileEntityRBMKConsole console = (TileEntityRBMKConsole) te;
		
		Tessellator tess = Tessellator.instance;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		tess.startDrawingQuads();
		tess.setBrightness(240);
		tess.setNormal(1, 0, 0);
		
		for(int i = 0; i < console.columns.length; i++) {
			
			RBMKColumn col = console.columns[i];
			
			if(col == null)
				continue;

			double kx = -0.3725D;
			double ky = -(i / 15) * 0.125 + 3.625;
			double kz = -(i % 15) * 0.125 + 0.125D * 7;
			
			drawColumn(tess, kx, ky, kz, (float)(0.65D + (i % 2) * 0.05D), col.data.getDouble("heat") / col.data.getDouble("maxHeat"));
			
			switch(col.type) {
			case FUEL:
			case FUEL_SIM:		drawFuel(tess, kx + 0.01, ky, kz, col.data.getDouble("enrichment")); break;
			case CONTROL:		drawControl(tess, kx + 0.01, ky, kz, col.data.getDouble("level")); break;
			case CONTROL_AUTO:	drawControlAuto(tess, kx + 0.01, ky, kz, col.data.getDouble("level")); break;
			default:
			}
		}
		
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glPopMatrix();
	}
	
	private void drawColumn(Tessellator tess, double x, double y, double z, float color, double heat) {
		
		double width = 0.0625D * 0.75;
		
		tess.setColorOpaque_F((float) (color + ((1 - color) * heat)), color, color);
		tess.addVertex(x, y + width, z - width);
		tess.addVertex(x, y + width, z + width);
		tess.addVertex(x, y - width, z + width);
		tess.addVertex(x, y - width, z - width);
	}
	
	private void drawFuel(Tessellator tess, double x, double y, double z, double enrichment) {
		this.drawDot(tess, x, y, z, 0F, 0.25F + (float) (enrichment * 0.75D), 0F);
	}
	
	private void drawControl(Tessellator tess, double x, double y, double z, double level) {
		this.drawDot(tess, x, y, z, (float) level, (float) level, 0F);
	}
	
	private void drawControlAuto(Tessellator tess, double x, double y, double z, double level) {
		this.drawDot(tess, x, y, z, (float) level, 0F, (float) level);
	}
	
	private void drawDot(Tessellator tess, double x, double y, double z, float r, float g, float b) {
		
		double width = 0.03125D;
		double edge = 0.022097D;
		
		tess.setColorOpaque_F(r, g, b);
		
		tess.addVertex(x, y + width, z);
		tess.addVertex(x, y + edge, z + edge);
		tess.addVertex(x, y, z + width);
		tess.addVertex(x, y - edge, z + edge);

		tess.addVertex(x, y + edge, z - edge);
		tess.addVertex(x, y + width, z);
		tess.addVertex(x, y - edge, z - edge);
		tess.addVertex(x, y, z - width);
		
		tess.addVertex(x, y + width, z);
		tess.addVertex(x, y - edge, z + edge);
		tess.addVertex(x, y - width, z);
		tess.addVertex(x, y - edge, z - edge);
		
		tess.setColorOpaque_F(1F, 1F, 1F);
	}
}
