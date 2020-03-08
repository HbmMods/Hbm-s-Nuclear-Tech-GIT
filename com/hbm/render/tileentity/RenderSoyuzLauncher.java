package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.util.SoyuzLauncherPronter;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSoyuzLauncher extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		SoyuzLauncherPronter.prontLauncher();
		GL11.glPopMatrix();
	}

}
