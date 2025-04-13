package com.hbm.render.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCrashedBomb extends TileEntitySpecialRenderer {
	
	public static Random rand = new Random();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		rand.setSeed(BlockPos.getIdentity(tile.xCoord, tile.yCoord, tile.zCoord));
		double yaw = rand.nextDouble() * 360;
		double pitch = rand.nextDouble() * 45 + 45;
		double roll = rand.nextDouble() * 360;
		double offset = rand.nextDouble() * 2 - 1;

		GL11.glRotated(yaw, 0, 1, 0);
		GL11.glRotated(pitch, 1, 0, 0);
		GL11.glRotated(roll, 0, 0, 1);
		GL11.glTranslated(0, 0, -offset);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.dud_balefire_tex);
		ResourceManager.dud_balefire.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}
