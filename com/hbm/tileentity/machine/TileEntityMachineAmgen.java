package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;

public class TileEntityMachineAmgen extends TileEntity implements ISource {

	public List<IConsumer> list = new ArrayList();
	public long power;
	public long maxPower = 100;
	boolean tact = false;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
			
			if(block == ModBlocks.machine_amgen) {
				RadiationSavedData data = RadiationSavedData.getData(worldObj);
				Chunk c = worldObj.getChunkFromBlockCoords(xCoord, zCoord);
				float rad = data.getRadNumFromCoord(c.xPosition, c.zPosition);
				
				power += rad;
				
				data.decrementRad(worldObj, xCoord, zCoord, 5F);
				
			} else if(block == ModBlocks.machine_minirtg) {
				
				power += 25;
				
			} else if(block == ModBlocks.machine_geo) {
				
				Block b = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
				
				if(b == ModBlocks.geysir_water) {
					power += 75;
				} else if(b == ModBlocks.geysir_chlorine) {
					power += 100;
				} else if(b == ModBlocks.geysir_vapor) {
					power += 50;
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

			tact = false;
			ffgeuaInit();
			tact = true;
			ffgeuaInit();
		}
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 1, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public boolean getTact() {
		return tact;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		list.clear();
	}

}
