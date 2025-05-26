package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.RBMKColumn;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.RBMKScreen;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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

		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		GL11.glTranslatef(-0.42F, 3.5F, 1.75F);
		GL11.glDepthMask(false);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		for(int i = 0; i < console.screens.length; i++) {
			
			GL11.glPushMatrix();
			
			if(i % 2 == 1)
				GL11.glTranslatef(0, 0, 1.75F * -2);
			
			GL11.glTranslatef(0, -0.75F * (i / 2), 0);
			
			RBMKScreen screen = console.screens[i];
			String text = screen.display;
			
			if(text != null && ! text.isEmpty()) {
				
				String[] parts = text.split("=");
				
				if(parts.length == 2) {
					text = I18nUtil.resolveKey(parts[0], parts[1]);
				}

				int width = font.getStringWidth(text);
				int height = font.FONT_HEIGHT;
				
				float f3 = Math.min(0.03F, 0.8F / Math.max(width, 1));
				GL11.glScalef(f3, -f3, f3);
				GL11.glNormal3f(0.0F, 0.0F, -1.0F);
				GL11.glRotatef(90, 0, 1, 0);
				
				font.drawString(text, - width / 2, - height / 2, 0x00ff00);
			}
			GL11.glPopMatrix();
		}
		
		GL11.glDepthMask(true);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);

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
