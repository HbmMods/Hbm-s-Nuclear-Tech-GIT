package com.hbm.blocks.machine;

import com.hbm.blocks.machine.Floodlight.TileEntityFloodlight;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FloodlightBeam extends BlockBeamBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFloodlightBeam();
	}
	
	public static class TileEntityFloodlightBeam extends TileEntity {
		
		public TileEntityFloodlight cache;
		public int sourceX;
		public int sourceY;
		public int sourceZ;
		public int index;
		
		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 5 == 0) {
				
				if(cache == null) {
					
					if(worldObj.getChunkProvider().chunkExists(sourceX >> 4, sourceZ >> 4)) {
						TileEntity tile = worldObj.getTileEntity(sourceX, sourceY, sourceZ);
						if(tile instanceof TileEntityFloodlight) {
							cache = (TileEntityFloodlight) tile; // chunk is loaded, tile exists -> cache
						} else {
							worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 2); // chunk is loaded, tile does not exist -> delete self
						}
					}
				}
				
				if((cache != null && (cache.isInvalid() || !cache.isOn || !new BlockPos(xCoord, yCoord, zCoord).equals(cache.lightPos[index]))) || sourceY == 0) {
					worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 2);
				}
			}
		}
		
		public void setSource(TileEntityFloodlight floodlight, int x, int y, int z, int i) {
			cache = floodlight;
			sourceX = x;
			sourceY = y;
			sourceZ = z;
			index = i;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.sourceX = nbt.getInteger("sourceX");
			this.sourceY = nbt.getInteger("sourceY");
			this.sourceZ = nbt.getInteger("sourceZ");
			this.index = nbt.getInteger("index");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("sourceX", sourceX);
			nbt.setInteger("sourceY", sourceY);
			nbt.setInteger("sourceZ", sourceZ);
			nbt.setInteger("index", index);
		}
	}
}
