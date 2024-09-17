package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.CompatEnergyControl;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.tile.IInfoProviderEC;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAmgen extends TileEntityLoadedBase implements IEnergyProviderMK2, IInfoProviderEC {

	public long power;
	public long maxPower = 500;
	protected long output = 0;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.output = 0;

			Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
			
			if(block == ModBlocks.machine_geo) {
				this.checkGeoInteraction(xCoord, yCoord + 1, zCoord);
				this.checkGeoInteraction(xCoord, yCoord - 1, zCoord);
			}
			
			this.power += this.output;
			if(power > maxPower)
				power = maxPower;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.tryProvide(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
		}
	}
	
	private void checkGeoInteraction(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x, y, z);
		
		if(b == ModBlocks.geysir_water) {
			this.output += 75;
		} else if(b == ModBlocks.geysir_chlorine) {
			this.output += 100;
		} else if(b == ModBlocks.geysir_vapor) {
			this.output += 50;
		} else if(b == ModBlocks.geysir_nether) {
			this.output += 500;
		} else if(b == Blocks.lava) {
			this.output += 100;
			
			if(worldObj.rand.nextInt(6000) == 0) {
				worldObj.setBlock(xCoord, yCoord - 1, zCoord, Blocks.obsidian);
			}
		} else if(b == Blocks.flowing_lava) {
			this.output += 25;
			
			if(worldObj.rand.nextInt(3000) == 0) {
				worldObj.setBlock(xCoord, yCoord - 1, zCoord, Blocks.cobblestone);
			}
		}
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
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, this.output > 0);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, this.output);
	}
}
