package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityTowerSmall;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSmallTower extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		TileEntityTowerSmall towerEntity = (TileEntityTowerSmall)tileEntity;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(towerEntity.tanks[0].getTankType() == FluidType.SPENTSTEAM ? ResourceManager.tower_small_tex : ResourceManager.tower_small_heavy_tex);
		ResourceManager.tower_small.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
