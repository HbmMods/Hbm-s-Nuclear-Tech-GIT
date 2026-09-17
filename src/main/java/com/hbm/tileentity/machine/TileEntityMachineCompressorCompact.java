package com.hbm.tileentity.machine;

import com.hbm.tileentity.TilePortShapes;
import com.hbm.tileentity.TilePort.PortDef;

import net.minecraft.util.AxisAlignedBB;

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
	
	protected PortDef[] cachedPorts;
	public PortDef[] getPorts() { if(cachedPorts == null) cachedPorts = TilePortShapes.condenser(xCoord, yCoord, zCoord, this.getBlockMetadata()); return cachedPorts; }
	
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
