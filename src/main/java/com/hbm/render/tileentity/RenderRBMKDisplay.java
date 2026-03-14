package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKDisplay;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.RBMKColumn;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRBMKDisplay extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(te.getBlockMetadata()) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityRBMKDisplay display = (TileEntityRBMKDisplay) te;
		
		GL11.glTranslated(0, 0.5, 0);
		GL11.glScaled(1, 8D / 7D, 8D / 7D);
		GL11.glTranslated(0, -0.5, 0);
		
		Tessellator tess = Tessellator.instance;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		tess.startDrawingQuads();
		tess.setBrightness(240);
		tess.setNormal(1, 0, 0);
		
		for(int i = 0; i < display.columns.length; i++) {
			
			RBMKColumn col = display.columns[i];
			
			if(col == null) continue;

			double kx = 0.28125D;
			double ky = -(i / 7) * 0.125 + 0.875;
			double kz = -(i % 7) * 0.125 + 0.125D * 3;

			if(col.data.hasKey("color") && col.data.getByte("color") >= 0) {
				byte color = col.data.getByte("color");
				if(color == 0) tess.setColorOpaque_I(0xFF0000);
				if(color == 1) tess.setColorOpaque_I(0xFFFF00);
				if(color == 2) tess.setColorOpaque_I(0x008000);
				if(color == 3) tess.setColorOpaque_I(0x0000FF);
				if(color == 4) tess.setColorOpaque_I(0x8000FF);
			} else {
				double heat = col.data.getDouble("heat") / col.data.getDouble("maxHeat");
				double color = 0.65D + (i % 2) * 0.05D;
				tess.setColorOpaque_F((float) (color + ((1 - color) * heat)), (float) color, (float) color);
			}
			
			if(col.data.getByte("indicator") > 0) tess.setColorOpaque_F(1F, 1F, 0F);
			
			drawColumn(tess, kx, ky, kz);
			
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
	
	private void drawColumn(Tessellator tess, double x, double y, double z) {
		
		double width = 0.0625D * 0.75;
		
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
