package com.hbm.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.machine.BlockHadronAccess;
import com.hbm.tileentity.machine.TileEntityHadron;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityProxyBase extends TileEntityLoadedBase {

	public boolean canUpdate() {
		return false;
	}

	public TileEntity getTE() {

		if(this.getBlockType() instanceof BlockDummyable) {

			BlockDummyable dummy = (BlockDummyable) this.getBlockType();

			int[] pos = dummy.findCore(worldObj, xCoord, yCoord, zCoord);

			if(pos != null) {

				TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);

				if(te != null && te != this)
					return te;
			}
		}

		/// this spares me the hassle of registering a new child class TE that
		/// aims at the right target ///

		if(this.getBlockType() instanceof BlockHadronAccess) {
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());

			for(int i = 1; i < 3; i++) {
				TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX * i, yCoord + dir.offsetY * i, zCoord + dir.offsetZ * i);

				if(te instanceof TileEntityHadron) {
					return te;
				}
			}
		}

		return null;
	}
}
