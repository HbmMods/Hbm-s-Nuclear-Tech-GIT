package com.hbm.tileentity.deco;

import net.minecraft.tileentity.TileEntity;

public class TileEntityRotationTester extends TileEntity {
	
	@Override
	public int getBlockMetadata()
    {
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }

}
