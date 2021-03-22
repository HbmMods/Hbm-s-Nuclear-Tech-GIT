package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityFEL extends TileEntityMachineBase {
	
	public TileEntityFEL() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.machineFEL";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(
				xCoord - 4,
				yCoord,
				zCoord - 4,
				xCoord + 5,
				yCoord + 3,
				zCoord + 5
			);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
