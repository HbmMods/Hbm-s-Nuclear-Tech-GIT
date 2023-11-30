package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderExposureChamber extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.exposure_chamber_tex);
		ResourceManager.exposure_chamber.renderPart("Chamber");
		
		GL11.glPushMatrix();
		GL11.glRotated((tileEntity.getWorldObj().getTotalWorldTime() % 360D + f) * 5, 0, 1, 0);
		GL11.glTranslated(0, Math.sin((tileEntity.getWorldObj().getTotalWorldTime() % (Math.PI * 16D) + f) * 0.125) * 0.0625, 0);
		ResourceManager.exposure_chamber.renderPart("Core");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glRotated((tileEntity.getWorldObj().getTotalWorldTime() % 360D + f) * 10, 0, 1, 0);
		ResourceManager.exposure_chamber.renderPart("Magnets");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
	}
}
