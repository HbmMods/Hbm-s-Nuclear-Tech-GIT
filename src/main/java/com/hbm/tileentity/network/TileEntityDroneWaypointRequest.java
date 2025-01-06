package com.hbm.tileentity.network;

import com.hbm.tileentity.network.RequestNetwork.PathNode;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDroneWaypointRequest extends TileEntityRequestNetwork {

	public int height = 5;
	
	@Override
	public BlockPos getCoord() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		return new BlockPos(xCoord + dir.offsetX * height, yCoord + dir.offsetY * height, zCoord + dir.offsetZ * height);
	}
	
	public void addHeight(int h) {
		height += h;
		height = MathHelper.clamp_int(height, 1, 15);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.height = nbt.getInteger("height");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("height", height);
	}

	@Override
	public PathNode createNode(BlockPos pos) {
		return new PathNode(pos, this.reachableNodes);
	}
}
