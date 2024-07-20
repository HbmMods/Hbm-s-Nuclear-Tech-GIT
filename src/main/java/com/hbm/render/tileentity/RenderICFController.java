package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityICFController;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderICFController extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
		TileEntityICFController controller = (TileEntityICFController) tile;
		
		if(controller.laserLength > 0) {
			
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
			GL11.glRotatef(90, 0F, 1F, 0F);
			switch(tile.getBlockMetadata()) {
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}
	
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			BeamPronter.prontBeam(Vec3.createVectorHelper(controller.laserLength, 0, 0), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x202020, 0x100000, 0, 1, 0F, 10, 0.125F, 0.5F);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			
			GL11.glPopMatrix();
		}
	}
}
