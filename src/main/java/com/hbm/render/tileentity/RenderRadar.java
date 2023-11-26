package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRadar extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		bindTexture(ResourceManager.radar_base_tex);
		ResourceManager.radar.renderPart("Base");

		TileEntityMachineRadarNT radar = (TileEntityMachineRadarNT) tileEntity;
		GL11.glRotatef(radar.prevRotation + (radar.rotation - radar.prevRotation) * f, 0F, -1F, 0F);
		GL11.glTranslated(-0.125D, 0, 0);

		bindTexture(ResourceManager.radar_dish_tex);
		ResourceManager.radar.renderPart("Dish");

		GL11.glPopMatrix();
	}
}
