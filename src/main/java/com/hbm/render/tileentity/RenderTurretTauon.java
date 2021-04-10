package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.turret.TileEntityTurretTauon;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTurretTauon extends RenderTurretBase {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
		TileEntityTurretTauon turret = (TileEntityTurretTauon)te;
		Vec3 off = turret.getHorizontalOffset();

		GL11.glPushMatrix();
		GL11.glTranslated(x + off.xCoord, y, z + off.zCoord);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		this.renderConnectors(turret, true, false, FluidType.NONE);

		bindTexture(ResourceManager.turret_base_tex);
		ResourceManager.turret_chekhov.renderPart("Base");
		
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * interp) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * interp);
		
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.turret_carriage_tex);
		ResourceManager.turret_chekhov.renderPart("Carriage");
		
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -1.5, 0);
		bindTexture(ResourceManager.turret_tauon_tex);
		ResourceManager.turret_tauon.renderPart("Cannon");

		if(turret.beam > 0) {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glTranslated(0, 1.5D, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(turret.lastDist, 0, 0), EnumWaveType.RANDOM, EnumBeamType.LINE, 0xffa200, 0xffd000, (int)te.getWorldObj().getTotalWorldTime() / 5 % 360, (int)turret.lastDist + 1, 0.1F, 0, 0);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
		
		float rot = turret.lastSpin + (turret.spin - turret.lastSpin) * interp;
		GL11.glTranslated(0, 1.375, 0);
		GL11.glRotated(rot, -1, 0, 0);
		GL11.glTranslated(0, -1.375, 0);
		ResourceManager.turret_tauon.renderPart("Rotor");

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
