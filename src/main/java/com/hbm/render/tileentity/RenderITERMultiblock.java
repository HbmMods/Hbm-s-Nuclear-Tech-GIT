package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderITERMultiblock extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);

		GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.iter_glass);
        ResourceManager.iter.renderPart("Windows");
        bindTexture(ResourceManager.iter_motor);
        ResourceManager.iter.renderPart("Motors");
        bindTexture(ResourceManager.iter_rails);
        ResourceManager.iter.renderPart("Rails");
        bindTexture(ResourceManager.iter_toroidal);
        ResourceManager.iter.renderPart("Toroidal");
        bindTexture(ResourceManager.iter_torus);
        ResourceManager.iter.renderPart("Torus");
        
        GL11.glPushMatrix();
        GL11.glRotated(System.currentTimeMillis() / 5D % 360, 0, 1, 0);
        bindTexture(ResourceManager.iter_solenoid);
        ResourceManager.iter.renderPart("Solenoid");
		GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glRotated(System.currentTimeMillis() / 50D % 360, 0, 1, 0);
        GL11.glDisable(GL11.GL_LIGHTING);
        bindTexture(ResourceManager.iter_plasma);
        ResourceManager.iter.renderPart("Plasma");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
