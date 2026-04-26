package com.hbm.tileentity.network.pneumatic;

import api.hbm.ntl.IPneumaticConnector;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPneumoStorageAccess extends TileEntity implements IPneumaticConnector {

	@Override
	public boolean canConnectPneumatic(ForgeDirection dir) {
		ForgeDirection selfdir = ForgeDirection.getOrientation(getBlockMetadata());
		return dir == selfdir.getOpposite();
	}
}
