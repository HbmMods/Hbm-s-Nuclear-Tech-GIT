package com.hbm.tileentity.machine.rbmk;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Base class for all RBMK components that have a GUI
 * @author hbm
 *
 */
public abstract class TileEntityRBMKActiveBase extends TileEntityRBMKBase {
	
	public abstract String getName();

	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}
	}
}
