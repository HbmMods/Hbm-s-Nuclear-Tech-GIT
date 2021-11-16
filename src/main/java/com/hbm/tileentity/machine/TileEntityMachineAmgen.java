package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.lib.Library;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAmgen extends TileEntity implements IEnergyGenerator {

	public long power;
	public long maxPower = 500;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
			
			if(block == ModBlocks.machine_amgen) {
				float rad = ChunkRadiationManager.proxy.getRadiation(worldObj, xCoord, yCoord, zCoord);
				
				power += rad;
				
				ChunkRadiationManager.proxy.decrementRad(worldObj, xCoord, yCoord, zCoord, 5F);
				
			} else if(block == ModBlocks.machine_geo) {
				
				Block b = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
				
				if(b == ModBlocks.geysir_water) {
					power += 75;
				} else if(b == ModBlocks.geysir_chlorine) {
					power += 100;
				} else if(b == ModBlocks.geysir_vapor) {
					power += 50;
				} else if(b == ModBlocks.geysir_nether) {
					power += 500;
				} else if(b == Blocks.lava) {
					power += 100;
					
					if(worldObj.rand.nextInt(1200) == 0) {
						worldObj.setBlock(xCoord, yCoord - 1, zCoord, Blocks.obsidian);
					}
				} else if(b == Blocks.flowing_lava) {
					power += 25;
					
					if(worldObj.rand.nextInt(600) == 0) {
						worldObj.setBlock(xCoord, yCoord - 1, zCoord, Blocks.cobblestone);
					}
				}
				
				b = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
				
				if(b == Blocks.lava) {
					power += 100;
					
					if(worldObj.rand.nextInt(1200) == 0) {
						worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.obsidian);
					}
				} else if(b == Blocks.flowing_lava) {
					power += 25;
					
					if(worldObj.rand.nextInt(600) == 0) {
						worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.cobblestone);
					}
				}
			}
			
			if(power > maxPower)
				power = maxPower;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
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
}
