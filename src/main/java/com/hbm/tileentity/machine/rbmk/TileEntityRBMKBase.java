package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Base class for all RBMK components, active or passive. Handles heat and the explosion sequence
 * @author hbm
 *
 */
public abstract class TileEntityRBMKBase extends TileEntity {
	
	public double heat;

	public boolean hasLid() {
		return false;
	}
	
	/**
	 * Approx melting point of steel
	 * This metric won't be used because fuel tends to melt much earlier than that
	 * @return
	 */
	public double maxHeat() {
		return 1500D;
	}
	
	/**
	 * Around the same for every component except boilers which do not have passive cooling
	 * @return
	 */
	public double passiveCooling() {
		return 5D;
	}
	
	//necessary checks to figure out whether players are close enough to ensure that the reactor can be safely used
	public boolean shouldUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		moveHeat();
		coolPassively();
	}
	
	public static final ForgeDirection[] heatDirs = new ForgeDirection[] {
			ForgeDirection.NORTH,
			ForgeDirection.EAST,
			ForgeDirection.SOUTH,
			ForgeDirection.WEST
	};
	
	/**
	 * Moves heat to neighboring parts, if possible, in a relatively fair manner
	 */
	private void moveHeat() {
		
		List<TileEntityRBMKBase> rec = new ArrayList();
		double req = 0;
		
		for(ForgeDirection dir : heatDirs) {
			
			TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
			
			if(te instanceof TileEntityRBMKBase) {
				TileEntityRBMKBase base = (TileEntityRBMKBase) te;
				
				if(base.heat < this.heat) {
					rec.add(base);
					
					req += (this.heat - base.heat) / 2D;
				}
			}
		}
		
		if(rec.size() > 0) {
			
			double max = req / rec.size();
			
			for(TileEntityRBMKBase base : rec) {
				
				double move = (this.heat - base.heat) / 2D;
				
				if(move > max)
					move = max;
				
				base.heat += move;
				this.heat -= move;
			}
		}
	}
	
	/**
	 * TODO: add faster passive cooling based on temperature (blackbody radiation has an exponent of 4!)
	 */
	private void coolPassively() {
		this.heat -= this.passiveCooling();
	}
}
