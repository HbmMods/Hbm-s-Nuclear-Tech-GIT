package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineMixer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderMixer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.mixer_tex);
		ResourceManager.mixer.renderPart("Main");
		
		TileEntityMachineMixer mixer = (TileEntityMachineMixer) tile;
		
		GL11.glPushMatrix();
		GL11.glRotatef(mixer.prevRotation + (mixer.rotation - mixer.prevRotation) * interp, 0, 1, 0);
		ResourceManager.mixer.renderPart("Mixer");
		GL11.glPopMatrix();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(1F, 1F, 1F);
		ResourceManager.mixer.renderPart("Fluid");
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}
}
