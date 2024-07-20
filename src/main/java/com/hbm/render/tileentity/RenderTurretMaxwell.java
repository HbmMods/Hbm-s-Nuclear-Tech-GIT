package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.turret.TileEntityTurretMaxwell;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTurretMaxwell extends RenderTurretBase {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
		TileEntityTurretMaxwell turret = (TileEntityTurretMaxwell)te;
		Vec3 pos = turret.getHorizontalOffset();

		GL11.glPushMatrix();
		GL11.glTranslated(x + pos.xCoord, y, z + pos.zCoord);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		this.renderConnectors(turret, true, false, Fluids.NONE);

		bindTexture(ResourceManager.turret_base_tex);
		ResourceManager.turret_chekhov.renderPart("Base");
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * interp) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * interp);
		
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.turret_carriage_ciws_tex);
		ResourceManager.turret_howard.renderPart("Carriage");
		
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -1.5, 0);
		bindTexture(ResourceManager.turret_maxwell_tex);
		ResourceManager.turret_maxwell.renderPart("Microwave");

		if(turret.beam > 0) {
			
			double length = turret.lastDist - turret.getBarrelLength();
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glTranslated(turret.getBarrelLength(), 2D, 0);

			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.0F);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			
			/*Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			Vec3 v = Vec3.createVectorHelper(0, 0.375, 0);
			for(int i = 0; i < 16; i++) {

				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.35F);
				tess.addVertex(0, v.yCoord, v.zCoord);
				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0F);
				tess.addVertex(length, v.yCoord, v.zCoord);
				v.rotateAroundX((float)Math.PI * 0.25F);
				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0F);
				tess.addVertex(length, v.yCoord, v.zCoord);
				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.35F);
				tess.addVertex(0, v.yCoord, v.zCoord);
			}
			
			tess.draw();*/

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glShadeModel(GL11.GL_FLAT);
			
			for(int i = 0; i < 8; i++)
				BeamPronter.prontBeam(Vec3.createVectorHelper(length, 0, 0), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x2020ff, 0x2020ff, (int)((te.getWorldObj().getTotalWorldTime() + interp) * -50 + i * 45) % 360, (int)((turret.lastDist + 1)), 0.375F, 2, 0.05F, 0.5F);
			
			//BeamPronter.prontBeam(Vec3.createVectorHelper(length, 0, 0), EnumWaveType.SPIRAL, EnumBeamType.LINE, 0x0000ff, 0x8080ff, (int)((te.getWorldObj().getTotalWorldTime() + interp) * -50) % 360, (int)((turret.lastDist + 1) * 10), 0.4325F, 0, 0);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
