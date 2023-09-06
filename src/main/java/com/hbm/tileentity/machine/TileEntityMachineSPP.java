package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hfr.faction.relations.FactionRelations;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.init.Blocks;

public class TileEntityMachineSPP extends TileEntityLoadedBase implements IEnergyGenerator {
	
	public long power;
	public static final long maxPower = 100000;
	public int age = 0;
	public int gen = 0;
	
	@Override
	public void updateEntity() {
		if(FactionRelations.isWarday())
			return;
		if(!worldObj.isRemote) {

			this.sendPower(worldObj, xCoord + 1, yCoord, zCoord, Library.POS_X);
			this.sendPower(worldObj, xCoord - 1, yCoord, zCoord, Library.NEG_X);
			this.sendPower(worldObj, xCoord, yCoord, zCoord + 1, Library.POS_Z);
			this.sendPower(worldObj, xCoord, yCoord, zCoord - 1, Library.NEG_Z);
			this.sendPower(worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);
			
			if(worldObj.getTotalWorldTime() % 20 == 0)
				gen = checkStructure() * 15;
			
			if(gen > 0)
				power += gen;
			if(power > maxPower)
				power = maxPower;
		}
		
	}
	
	public int checkStructure() {

		int h = 0;
		
		for(int i = yCoord + 1; i < 254; i++)
			if(worldObj.getBlock(xCoord, i, zCoord) == ModBlocks.machine_spp_top) {
				h = i;
				break;
			}
		
		for(int i = yCoord + 1; i < h; i++)
			if(!checkSegment(i))
				return 0;

		
		return h - yCoord - 1;
	}
	
	public boolean checkSegment(int y) {
		
		//   BBB
		//   BAB
		//   BBB
		
		return (worldObj.getBlock(xCoord + 1, y, zCoord) != Blocks.air &&
				worldObj.getBlock(xCoord + 1, y, zCoord + 1) != Blocks.air &&
				worldObj.getBlock(xCoord + 1, y, zCoord - 1) != Blocks.air &&
				worldObj.getBlock(xCoord - 1, y, zCoord + 1) != Blocks.air &&
				worldObj.getBlock(xCoord - 1, y, zCoord) != Blocks.air &&
				worldObj.getBlock(xCoord - 1, y, zCoord - 1) != Blocks.air &&
				worldObj.getBlock(xCoord, y, zCoord + 1) != Blocks.air &&
				worldObj.getBlock(xCoord, y, zCoord - 1) != Blocks.air &&
				worldObj.getBlock(xCoord, y, zCoord) == Blocks.air);
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

}
