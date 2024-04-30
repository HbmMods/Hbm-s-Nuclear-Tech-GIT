package com.hbm.tileentity.network;

import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.Nodespace.PowerNode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConnector extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SINGLE;
	}

	@Override
	public Vec3[] getMountPos() {
		return new Vec3[] {Vec3.createVectorHelper(0.5, 0.5, 0.5)};
	}

	@Override
	public double getMaxWireLength() {
		return 10;
	}

	@Override
	public PowerNode createNode() {
		TileEntity tile = (TileEntity) this;
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		PowerNode node = new PowerNode(new BlockPos(tile.xCoord, tile.yCoord, tile.zCoord)).setConnections(
				new DirPos(xCoord, yCoord, zCoord, ForgeDirection.UNKNOWN),
				new DirPos(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir));
		for(int[] pos : this.connected) node.addConnection(new DirPos(pos[0], pos[1], pos[2], ForgeDirection.UNKNOWN));
		return node;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) { //i've about had it with your fucking bullshit
		return ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite() == dir;
	}
}
