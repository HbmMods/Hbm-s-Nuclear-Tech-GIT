package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityICF extends TileEntityMachineBase {

	public TileEntityICF() {
		super(0);
	}

	@Override
	public String getName() {
		return "container.machineICF";
	}

	@Override
	public void updateEntity() {
		
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + 0.5 - 8,
					yCoord,
					zCoord + 0.5 - 8,
					xCoord + 0.5 + 9,
					yCoord + 0.5 + 5,
					zCoord + 0.5 + 9
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
