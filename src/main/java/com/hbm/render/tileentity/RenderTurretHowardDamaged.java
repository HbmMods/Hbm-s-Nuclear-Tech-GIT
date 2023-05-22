package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretHowardDamaged;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTurretHowardDamaged extends RenderTurretBase {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
		TileEntityTurretHowardDamaged turret = (TileEntityTurretHowardDamaged)te;
		Vec3 pos = turret.getHorizontalOffset();

		GL11.glPushMatrix();
		GL11.glTranslated(x + pos.xCoord, y, z + pos.zCoord);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		bindTexture(ResourceManager.turret_base_rusted);
		ResourceManager.turret_chekhov.renderPart("Base");
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * interp) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * interp);
		
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.turret_carriage_ciws_rusted);
		ResourceManager.turret_howard_damaged.renderPart("Carriage");
		
		GL11.glTranslated(0, 2.25, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -2.25, 0);
		bindTexture(ResourceManager.turret_howard_rusted);
		ResourceManager.turret_howard_damaged.renderPart("Body");
		
		float rot = turret.lastSpin + (turret.spin - turret.lastSpin) * interp;

		bindTexture(ResourceManager.turret_howard_barrels_rusted);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 2.5, 0);
		GL11.glRotated(rot, -1, 0, 0);
		GL11.glTranslated(0, -2.5, 0);
		ResourceManager.turret_howard_damaged.renderPart("BarrelsTop");
		GL11.glPopMatrix();
		
		ResourceManager.turret_howard_damaged.renderPart("BarrelsBottom");

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
