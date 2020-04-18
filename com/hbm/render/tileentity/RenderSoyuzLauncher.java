package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.util.SoyuzLauncherPronter;
import com.hbm.render.util.SoyuzPronter;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSoyuzLauncher extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		
		TileEntitySoyuzLauncher launcher = (TileEntitySoyuzLauncher)te;
		
		SoyuzLauncherPronter.prontLauncher();
		
		if(launcher.rocketType >= 0) {
			GL11.glTranslatef(0.0F, 5.0F, 0.0F);
			SoyuzPronter.prontSoyuz(launcher.rocketType);
		}
		
		GL11.glPopMatrix();
	}

}
