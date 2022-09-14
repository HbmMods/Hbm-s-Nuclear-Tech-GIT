package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public abstract class TileEntityTurretBaseArtillery extends TileEntityTurretBaseNT {
	
	protected List<Vec3> targetQueue = new ArrayList();
	
	public void enqueueTarget(double x, double y, double z) {
		
		Vec3 pos = this.getTurretPos();
		Vec3 delta = Vec3.createVectorHelper(x - pos.xCoord, y - pos.yCoord, z - pos.zCoord);
		if(delta.lengthVector() <= this.getDecetorRange()) {
			this.targetQueue.add(Vec3.createVectorHelper(x, y, z));
		}
	}
	
	public abstract boolean doLOSCheck();
	
	@Override
	public boolean entityInLOS(Entity e) {
		
		if(doLOSCheck()) {
			return super.entityInLOS(e);
			
		} else {
			Vec3 pos = this.getTurretPos();
			Vec3 ent = this.getEntityPos(e);
			Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);
			double length = delta.lengthVector();
			
			if(length < this.getDecetorGrace() || length > this.getDecetorRange() * 1.1) //the latter statement is only relevant for entities that have already been detected
				return false;
			
			int height = worldObj.getHeightValue((int) Math.floor(e.posX), (int) Math.floor(e.posZ));
			return height < (e.posY + e.height);
		}
	}
}
