package com.hbm.tileentity.deco;

import com.hbm.blocks.generic.DecoTapeRecorder;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDecoTapeRecorder extends TileEntity {

	private int rot = 0;
	
	public int getRotation() {
		if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof DecoTapeRecorder)
		{
			if(this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord))
			{
				rot += 3;
				if(rot >= 360)
				{
					rot -=360;
				}
				return rot;
			}
			
			rot = 0;
		}
		
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
