package com.hbm.tileentity.machine;

import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCompressorCompact extends TileEntityMachineCompressorBase {
	
	public float fanSpin;
	public float prevFanSpin;

	@Override
	public void updateEntity() {
		super.updateEntity();

		if(worldObj.isRemote) {

			this.prevFanSpin = this.fanSpin;

			if(this.isOn) {
				this.fanSpin += 45;

				if(this.fanSpin >= 360) {
					this.prevFanSpin -= 360;
					this.fanSpin -= 360;
				}
			}
		}
	}
	
	@Override
	public DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 4, yCoord + 1, zCoord + rot.offsetZ * 4, rot),
				new DirPos(xCoord - rot.offsetX * 4, yCoord + 1, zCoord - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX, yCoord + 1, zCoord + dir.offsetZ * 2 - rot.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX, yCoord + 1, zCoord + dir.offsetZ * 2 + rot.offsetZ, dir),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX, yCoord + 1, zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord + 1, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite())
		};
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 3,
					zCoord + 4
					);
		}
		
		return bb;
	}
}
