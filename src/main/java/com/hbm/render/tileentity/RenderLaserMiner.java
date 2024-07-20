package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RenderLaserMiner extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interpolation) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y - 1, z + 0.5);
		
		TileEntityMachineMiningLaser laser = (TileEntityMachineMiningLaser)te;

		double tx = (laser.targetX - laser.lastTargetX) * interpolation + laser.lastTargetX;
		double ty = (laser.targetY - laser.lastTargetY) * interpolation + laser.lastTargetY;
		double tz = (laser.targetZ - laser.lastTargetZ) * interpolation + laser.lastTargetZ;
		double vx = tx - laser.xCoord;
		double vy = ty - laser.yCoord + 3;
		double vz = tz - laser.zCoord;
		
		Vec3 nVec = Vec3.createVectorHelper(vx, vy, vz);
		nVec = nVec.normalize();
		
		double d = 1.5D;
		nVec.xCoord *= d;
		nVec.yCoord *= d;
		nVec.zCoord *= d;
		
		Vec3 vec = Vec3.createVectorHelper(vx - nVec.xCoord, vy - nVec.yCoord, vz - nVec.zCoord);

		double length = vec.lengthVector();
		double yaw = Math.toDegrees(Math.atan2(vec.xCoord, vec.zCoord));
		double sqrt = MathHelper.sqrt_double(vec.xCoord * vec.xCoord + vec.zCoord * vec.zCoord);
		double pitch = Math.toDegrees(Math.atan2(vec.yCoord, sqrt));
		//turns out using tan(vec.yCoord, length) was inaccurate,
		//the emitter wouldn't match the laser perfectly when pointing down

		bindTexture(ResourceManager.mining_laser_base_tex);
		ResourceManager.mining_laser.renderPart("Base");

		//GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glPushMatrix();
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.mining_laser_pivot_tex);
		ResourceManager.mining_laser.renderPart("Pivot");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glRotated(yaw, 0, 1, 0);
		GL11.glTranslated(0, -1, 0);
		GL11.glRotated(pitch + 90, -1, 0, 0);
		GL11.glTranslated(0, 1, 0);
		bindTexture(ResourceManager.mining_laser_laser_tex);
		ResourceManager.mining_laser.renderPart("Laser");
		GL11.glPopMatrix();
		//GL11.glShadeModel(GL11.GL_FLAT);
		
		if(laser.beam) {
			length = vec.lengthVector();
			GL11.glTranslated(nVec.xCoord, nVec.yCoord - 1, nVec.zCoord);
			int range = (int)Math.ceil(length * 0.5);
	        BeamPronter.prontBeam(vec, EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0xa00000, 0xa00000, (int)te.getWorldObj().getTotalWorldTime() * -25 % 360, range * 2, 0.075F, 3, 0.025F, 0.5F);
	        BeamPronter.prontBeam(vec, EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0xa00000, 0xa00000, (int)te.getWorldObj().getTotalWorldTime() * -25 % 360 + 120, range * 2, 0.075F, 3, 0.025F, range);
	        BeamPronter.prontBeam(vec, EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0xa00000, 0xa00000, (int)te.getWorldObj().getTotalWorldTime() * -25 % 360 + 240, range * 2, 0.075F, 3, 0.025F, 0.5F);
		}
        
		GL11.glPopMatrix();
	}

}
