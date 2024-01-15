package com.hbm.tileentity.machine;

import com.hbm.lib.Library;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineRadarLarge extends TileEntityMachineRadarNT {
	
	@Override
	public int getRange() {
		return radarLargeRange;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z),
		};
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord,
					zCoord - 5,
					xCoord + 6,
					yCoord + 10,
					zCoord + 6
					);
		}
		
		return bb;
	}
}
