package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityMachineArcFurnaceLarge extends TileEntityMachineBase {
	
	public long power;
	public static final long maxPower = 10_000_000;
	public boolean liquidMode = false;
	public float progress;
	public float lid;
	public float prevLid;
	
	public List<MaterialStack> liquids = new ArrayList();

	public TileEntityMachineArcFurnaceLarge() {
		super(25);
	}

	@Override
	public String getName() {
		return "container.machineArcFurnaceLarge";
	}

	@Override
	public void updateEntity() {
		
	}
}
