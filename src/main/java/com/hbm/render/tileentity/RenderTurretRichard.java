package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretRichard;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTurretRichard extends RenderTurretBase {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
		TileEntityTurretRichard turret = (TileEntityTurretRichard)te;
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
		bindTexture(ResourceManager.turret_carriage_tex);
		ResourceManager.turret_chekhov.renderPart("Carriage");
		
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -1.5, 0);
		bindTexture(ResourceManager.turret_richard_tex);
		ResourceManager.turret_richard.renderPart("Launcher");
		
		GL11.glTranslated(0, 0.375, 0.1875);
		
		for(int i = 0; i < turret.loaded; i++) {
			ResourceManager.turret_richard.renderPart("MissileLoaded");
			
			if(i == 2 || i == 6 || i == 9 || i == 13) {
				GL11.glTranslated(0, -0.1875, 0.1875 * 2 + 0.09375);
			} else {

				GL11.glTranslated(0, 0, -0.1875);
			}
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
