package com.hbm.blocks;

import net.minecraft.tileentity.TileEntity;

public class TileEntityRotationTester extends TileEntity {
	
	public int getBlockMetadata()
    {
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }

}
