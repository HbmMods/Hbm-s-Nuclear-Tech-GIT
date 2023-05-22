package com.hbm.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.IProxyController;
import com.hbm.util.Compat;

import net.minecraft.tileentity.TileEntity;

public class TileEntityProxyBase extends TileEntityLoadedBase {

	public boolean canUpdate() {
		return false;
	}

	public TileEntity getTE() {

		if(this.getBlockType() instanceof BlockDummyable) {

			BlockDummyable dummy = (BlockDummyable) this.getBlockType();

			int[] pos = dummy.findCore(worldObj, xCoord, yCoord, zCoord);

			if(pos != null) {

				TileEntity te = Compat.getTileStandard(worldObj, pos[0], pos[1], pos[2]);
				if(te != null && te != this) return te;
			}
		}

		if(this.getBlockType() instanceof IProxyController) {
			IProxyController controller = (IProxyController) this.getBlockType();
			TileEntity tile = controller.getCore(worldObj, xCoord, yCoord, zCoord);
			
			if(tile != null && tile != this) return tile;
		}

		return null;
	}
}
