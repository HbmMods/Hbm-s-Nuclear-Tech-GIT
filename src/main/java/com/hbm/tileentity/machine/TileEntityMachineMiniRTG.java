package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineMiniRTG extends TileEntityLoadedBase implements IEnergyGenerator {

	public long power;
	boolean tact = false;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			if(this.getBlockType() == ModBlocks.machine_powerrtg)
				power += 2500;
			else
				power += 700;
			
			if(power > getMaxPower())
				power = getMaxPower();

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
		}
	}


	@Override
	public long getMaxPower() {
		
		if(this.getBlockType() == ModBlocks.machine_powerrtg)
			return 50000;
		
		return 1400;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}
}
