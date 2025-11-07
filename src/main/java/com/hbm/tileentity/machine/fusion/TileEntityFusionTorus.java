package com.hbm.tileentity.machine.fusion;

import com.hbm.tileentity.machine.albion.TileEntityCooledBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityFusionTorus extends TileEntityCooledBase {

	public TileEntityFusionTorus() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.fusionTorus";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public long getMaxPower() {
		return 0;
	}

	@Override
	public DirPos[] getConPos() {
		return null;
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 8,
					yCoord,
					zCoord - 8,
					xCoord + 9,
					yCoord + 5,
					zCoord + 9
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
