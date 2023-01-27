package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityAtmoExtractor;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAtmoVent extends TileEntitySpecialRenderer {
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.0D, y, z + 0.0D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180F, 0F, 1F, 0F);
		
		TileEntityAtmoExtractor atmo = (TileEntityAtmoExtractor) tileEntity;
		switch(tileEntity.getBlockMetadata() - 10) {
		case 2:
			GL11.glRotatef(0F, 0F, 1F, 0F);
			GL11.glTranslatef(0F, 0F, -1F);
			break;
		case 3:
			GL11.glRotatef(180F, 0F, 1F, 0F);
			GL11.glTranslatef(1F, 0F, 0F);
			break;
		case 4:
			GL11.glRotatef(90F, 0F, 1F, 0F);
			GL11.glTranslatef(1F, 0F, -1F);
			break;
		case 5:
			GL11.glRotatef(270F, 0F, 1F, 0F);
			break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.atmo_vent_tex);
		ResourceManager.atmo_vent.renderPart("cylinder");

		
		float rot = atmo.prevRot + (atmo.rot - atmo.prevRot) * f;
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 0);
		GL11.glRotated(rot, 0, -0.5, 0);
		GL11.glTranslated(0, 0, 0);
		ResourceManager.atmo_vent.renderPart("Fan1");
		GL11.glPopMatrix();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}