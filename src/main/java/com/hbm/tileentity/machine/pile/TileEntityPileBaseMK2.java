package com.hbm.tileentity.machine.pile;

import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.util.Compat;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/** Base class for all tile entities used by the Chicago Pile, except for the core tile. */
public class TileEntityPileBaseMK2 extends TileEntityTickingBase {
	
	public TileEntityPileCore cachedCore;
	public int coreX;
	public int coreY;
	public int coreZ;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				
				TileEntityPileCore controller = getCore();
				
				if((controller == null || controller.isInvalid()) && worldObj.getChunkProvider().chunkExists(coreX >> 4, coreZ >> 4)) {
					this.getBlockType().breakBlock(worldObj, xCoord, yCoord, zCoord, this.getBlockType(), this.getBlockMetadata());
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		coreX = nbt.getInteger("cX");
		coreY = nbt.getInteger("cY");
		coreZ = nbt.getInteger("cZ");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("cX", coreX);
		nbt.setInteger("cY", coreY);
		nbt.setInteger("cZ", coreZ);
	}
	
	@Override
	public void markDirty() {
		if(this.worldObj != null) {
			this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		}
	}
	
	public TileEntityPileCore getCore() {
		if(cachedCore != null && !cachedCore.isInvalid()) return cachedCore;

		if(worldObj.getChunkProvider().chunkExists(coreX >> 4, coreZ >> 4)) {
			
			TileEntity tile = Compat.getTileStandard(worldObj, coreX, coreY, coreZ);
			if(tile instanceof TileEntityPileCore) {
				TileEntityPileCore core = (TileEntityPileCore) tile;
				cachedCore = core;
				return core;
			}
		}
		return null;
	}
}
