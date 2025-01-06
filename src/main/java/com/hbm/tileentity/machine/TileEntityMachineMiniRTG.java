package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.CompatEnergyControl;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.tile.IInfoProviderEC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineMiniRTG extends TileEntityLoadedBase implements IEnergyProviderMK2, IInfoProviderEC {

	public long power;
	boolean tact = false;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			power += this.getOutput();
			
			if(power > getMaxPower())
				power = getMaxPower();

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				this.tryProvide(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			}
		}
	}
	
	public long getOutput() {
		if(this.getBlockType() == ModBlocks.machine_powerrtg) return 2_500;
		return 700;
	}

	@Override
	public long getMaxPower() {
		if(this.getBlockType() == ModBlocks.machine_powerrtg) return 50_000;
		return 1_400;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}


	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, true);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, this.getOutput());
	}
}
