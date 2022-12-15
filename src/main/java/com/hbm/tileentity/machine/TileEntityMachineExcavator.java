package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityMachineExcavator extends TileEntityMachineBase {

	public boolean enableDrill = false;
	public boolean enableCrusher = false;
	public boolean enableWalling = false;
	public boolean enableVeinMiner = false;
	public boolean enableSilkTouch = false;

	public TileEntityMachineExcavator() {
		super(14);
	}

	@Override
	public String getName() {
		return "container.machineExcavator";
	}

	@Override
	public void updateEntity() {
		
	}
}
