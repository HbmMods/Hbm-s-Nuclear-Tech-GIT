package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.IRadarCommandReceiver;

import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityTurretBaseArtillery extends TileEntityTurretBaseNT implements IRadarCommandReceiver {
	
	protected List<Vec3> targetQueue = new ArrayList();

	public boolean sendCommandPosition(int x, int y, int z) {
		this.enqueueTarget(x + 0.5, y, z + 0.5);
		return true;
	}
	
	public boolean sendCommandEntity(Entity target) {
		this.enqueueTarget(target.posX, target.posY, target.posZ);
		return true;
	}
	
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

	@Override
	protected void updateConnections() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 4; j++) {
				this.trySubscribe(worldObj, xCoord + dir.offsetX * (-1 + j) + rot.offsetX * -3, yCoord + i, zCoord + dir.offsetZ * (-1 + j) + rot.offsetZ * -3, ForgeDirection.SOUTH);
				this.trySubscribe(worldObj, xCoord + dir.offsetX * (-1 + j) + rot.offsetX * 2, yCoord + i, zCoord + dir.offsetZ * (-1 + j) + rot.offsetZ * 2, ForgeDirection.NORTH);
				this.trySubscribe(worldObj, xCoord + dir.offsetX * -2 + rot.offsetX * (1 - j), yCoord + i, zCoord + dir.offsetZ * -2 + rot.offsetZ * (1 - j), ForgeDirection.EAST);
				this.trySubscribe(worldObj, xCoord + dir.offsetX * 3 + rot.offsetX * (1 - j), yCoord + i, zCoord + dir.offsetZ * 3 + rot.offsetZ * (1 - j), ForgeDirection.WEST);
			}
		}
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_artillery";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCurrentTarget(Context context, Arguments args) {
		return new Object[] {targetQueue.get(0).xCoord, targetQueue.get(0).yCoord, targetQueue.get(0).zCoord};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTargetDistance(Context context, Arguments args) {
		return new Object[] {Math.sqrt(Math.pow(xCoord - args.checkDouble(0), 2)+Math.pow(yCoord - args.checkDouble(1), 2)+Math.pow(zCoord - args.checkDouble(2), 2))};
	}
}
