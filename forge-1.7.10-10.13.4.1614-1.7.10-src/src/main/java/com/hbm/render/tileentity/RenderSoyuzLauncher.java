package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.util.SoyuzLauncherPronter;
import com.hbm.render.util.SoyuzPronter;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSoyuzLauncher extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float inter) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y - 4, (float) z + 0.5F);
		
		TileEntitySoyuzLauncher launcher = (TileEntitySoyuzLauncher)te;
		
		double open = 45D;
		int timer = 20;
		
		double rot = open;
		
		if(launcher.rocketType >=0)
			rot = 0;
		
		if(launcher.starting && launcher.countdown < timer) {
			
			rot = (timer - launcher.countdown + inter) * open / timer;
		}
		
		SoyuzLauncherPronter.prontLauncher(rot);
		
		if(launcher.rocketType >= 0) {
			GL11.glTranslatef(0.0F, 5.0F, 0.0F);
			SoyuzPronter.prontSoyuz(launcher.rocketType);
		}
		
		GL11.glPopMatrix();
	}

}
