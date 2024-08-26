package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityAtmoExtractor;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAtmoExtractor extends TileEntitySpecialRenderer {
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.0D, y, z + 0.0D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180F, 0F, 1F, 0F);
		//TODO: fix this shit wtf
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
		ResourceManager.atmo_vent.renderPart("Body_Cylinder");
		//ResourceManager.atmo_vent.renderAll();
		
		float rot = atmo.prevRot + (atmo.rot - atmo.prevRot) * f;
		//this somehow fucking works
		GL11.glTranslated(-0.19, 0, 0.19);
		GL11.glRotated(rot, 0, -1, 0);
		GL11.glTranslated(0.19, 0, -0.19);
		ResourceManager.atmo_vent.renderPart("Fan_Cylinder.001");

		GL11.glPopMatrix();
	}
}