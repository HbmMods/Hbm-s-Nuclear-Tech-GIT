package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretArty;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTurretArty extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
		TileEntityTurretArty turret = (TileEntityTurretArty)te;
		Vec3 pos = turret.getHorizontalOffset();

		GL11.glPushMatrix();
		GL11.glTranslated(x + pos.xCoord, y, z + pos.zCoord);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		bindTexture(ResourceManager.turret_arty_tex);
		ResourceManager.turret_arty.renderPart("Base");
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * interp) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * interp);
		
		GL11.glRotated(yaw - 90, 0, 1, 0);
		ResourceManager.turret_arty.renderPart("Carriage");
		
		GL11.glTranslated(0, 3, 0);
		GL11.glRotated(pitch, 1, 0, 0);
		GL11.glTranslated(0, -3, 0);
		ResourceManager.turret_arty.renderPart("Cannon");
		double barrel = turret.lastBarrelPos + (turret.barrelPos - turret.lastBarrelPos) * interp;
		double length = 2.5;
		GL11.glTranslated(0, 0, barrel * length);
		ResourceManager.turret_arty.renderPart("Barrel");

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
