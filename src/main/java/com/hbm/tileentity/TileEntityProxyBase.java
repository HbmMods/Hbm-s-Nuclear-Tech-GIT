package com.hbm.tileentity;

import com.hbm.blocks.BlockDummyable;

import net.minecraft.tileentity.TileEntity;

public class TileEntityProxyBase extends TileEntity {
	
    public boolean canUpdate()
    {
        return false;
    }

	public TileEntity getTE() {
		
		if(this.getBlockType() instanceof BlockDummyable) {
			
			BlockDummyable dummy = (BlockDummyable)this.getBlockType();
			
			int[] pos = dummy.findCore(worldObj, xCoord, yCoord, zCoord);
			
			if(pos != null) {
				
				TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
				
				if(te != null)
					return te;
			}
		}
		
		return null;
	}
}
