package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityITER;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderITER extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		TileEntityITER iter = (TileEntityITER) te;

		GL11.glPushMatrix();

		GL11.glTranslatef((float) x + 0.5F, (float) y - 2, (float) z + 0.5F);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.iter_glass);
		ResourceManager.iter.renderPart("Windows");
		bindTexture(ResourceManager.iter_motor);
		ResourceManager.iter.renderPart("Motors");
		bindTexture(ResourceManager.iter_rails);
		ResourceManager.iter.renderPart("Rails");
		bindTexture(ResourceManager.iter_toroidal);
		ResourceManager.iter.renderPart("Toroidal");

		switch(iter.blanket) {
		case 0: bindTexture(ResourceManager.iter_torus); break;
		case 1: bindTexture(ResourceManager.iter_torus_tungsten); break;
		case 2: bindTexture(ResourceManager.iter_torus_desh); break;
		case 3: bindTexture(ResourceManager.iter_torus_chlorophyte); break;
		case 4: bindTexture(ResourceManager.iter_torus_vaporwave); break;
		default: bindTexture(ResourceManager.iter_torus); break;
		}
		ResourceManager.iter.renderPart("Torus");

		GL11.glPushMatrix();
		GL11.glRotated(iter.lastRotor + (iter.rotor - iter.lastRotor) * f, 0, 1, 0);
		bindTexture(ResourceManager.iter_solenoid);
		ResourceManager.iter.renderPart("Solenoid");
		GL11.glPopMatrix();

		if(iter.plasma.getFill() > 0) {
			GL11.glPushMatrix();
			GL11.glRotated(System.currentTimeMillis() / 50D % 360, 0, 1, 0);

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDepthMask(false);

			int color = iter.plasma.getTankType().getColor();
			double alpha = (double) iter.plasma.getFill() / (double) iter.plasma.getMaxFill();

			int r = (int) (((color & 0xFF0000) >> 16) / 2 * alpha);
			int g = (int) (((color & 0xFF00) >> 8) / 2 * alpha);
			int b = (int) ((color & 0xFF) / 2 * alpha);

			GL11.glColor3b((byte) r, (byte) g, (byte) b);

			GL11.glTranslatef(0, 2.5F, 0);
			GL11.glScaled(1, alpha, 1);
			GL11.glTranslatef(0, -2.5F, 0);

			bindTexture(ResourceManager.iter_plasma);
			ResourceManager.iter.renderPart("Plasma");

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthMask(true);

			GL11.glPopMatrix();
		}

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

}
