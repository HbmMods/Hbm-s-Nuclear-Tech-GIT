package com.hbm.tileentity.network;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluidmk2.FluidNode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPipeAnchor extends TileEntityPipelineBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SMALL;
	}

	@Override
	public Vec3 getMountPos() {
		return Vec3.createVectorHelper(0.5, 0.5, 0.5);
	}

	@Override
	public double getMaxPipeLength() {
		return 10;
	}

	@Override
	public FluidNode createNode(FluidType type) {
		TileEntity tile = (TileEntity) this;
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		FluidNode node = new FluidNode(type.getNetworkProvider(), new BlockPos(tile.xCoord, tile.yCoord, tile.zCoord)).setConnections(
				new DirPos(xCoord, yCoord, zCoord, ForgeDirection.UNKNOWN),
				new DirPos(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir));
		for(int[] pos : this.connected) node.addConnection(new DirPos(pos[0], pos[1], pos[2], ForgeDirection.UNKNOWN));
		return node;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite() == dir && type == this.type;
	}
}
