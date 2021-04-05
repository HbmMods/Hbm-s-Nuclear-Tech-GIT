package com.hbm.tileentity.machine.rbmk;

import net.minecraft.tileentity.TileEntity;

/**
 * Base class for all RBMK components, active or passive. Handles heat and the explosion sequence
 * @author hbm
 *
 */
public abstract class TileEntityRBMKBase extends TileEntity {
	
	public float heat;

	public boolean hasLid() {
		return false;
	}
	
	public float maxHeat() {
		return 1500;
	}
	
	public float passiveCooling() {
		return 5;
	}
	
	//necessary checks to figure out whether players are close enough to ensure that the reactor can be safely used
	public boolean shouldUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		moveHeat();
	}
	
	private void moveHeat() {
		
	}
}
