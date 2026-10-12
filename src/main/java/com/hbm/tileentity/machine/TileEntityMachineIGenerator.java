package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMachineIGenerator extends TileEntityMachineBase {
	
	public TileEntityMachineIGenerator() {
		super(21);
	}

	@Override
	public String getName() {
		return "container.iGenerator";
	}

	@Override
	public void updateEntity() {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
