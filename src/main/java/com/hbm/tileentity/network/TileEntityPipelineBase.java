package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluidmk2.FluidNode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

// copy pasted crap class
public abstract class TileEntityPipelineBase extends TileEntityPipeBaseNT {

	protected List<int[]> connected = new ArrayList<>();

	@Override
	public FluidNode createNode(FluidType type) {
		TileEntity tile = (TileEntity) this;
		FluidNode node = new FluidNode(type.getNetworkProvider(), new BlockPos(tile.xCoord, tile.yCoord, tile.zCoord)).setConnections(new DirPos(xCoord, yCoord, zCoord, ForgeDirection.UNKNOWN));
		for(int[] pos : this.connected) node.addConnection(new DirPos(pos[0], pos[1], pos[2], ForgeDirection.UNKNOWN));
		return node;
	}

	public void addConnection(int x, int y, int z) {

		connected.add(new int[] {x, y, z});

		FluidNode node = (FluidNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, this.type.getNetworkProvider());
		node.recentlyChanged = true;
		node.addConnection(new DirPos(x, y, z, ForgeDirection.UNKNOWN));

		this.markDirty();

		if(worldObj instanceof WorldServer) {
			WorldServer world = (WorldServer) worldObj;
			world.getPlayerManager().markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public void disconnectAll() {

		for(int[] pos : connected) {
			TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
			if(te == this) continue;

			if(te instanceof TileEntityPipelineBase) {
				TileEntityPipelineBase pipeline = (TileEntityPipelineBase) te;
				UniNodespace.destroyNode(worldObj, pos[0], pos[1], pos[2], this.type.getNetworkProvider());

				for(int i = 0; i < pipeline.connected.size(); i++) {
					int[] conPos = pipeline.connected.get(i);

					if(conPos[0] == xCoord && conPos[1] == yCoord && conPos[2] == zCoord) {
						pipeline.connected.remove(i);
						i--;
					}
				}

				pipeline.markDirty();

				if(worldObj instanceof WorldServer) {
					WorldServer world = (WorldServer) worldObj;
					world.getPlayerManager().markBlockForUpdate(pipeline.xCoord, pipeline.yCoord, pipeline.zCoord);
				}
			}
		}

		UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, this.type.getNetworkProvider());
	}

	@Override
	public void invalidate() {
		super.invalidate();
		disconnectAll();
	}

	/**
	 * Returns a status code based on the operation.<br>
	 * 0: Connected<br>
	 * 1: Connections are incompatible<br>
	 * 2: Both parties are the same block<br>
	 * 3: Connection length exceeds maximum
	 * 4: Pipeline fluid types do not match
	 */
	public static int canConnect(TileEntityPipelineBase first, TileEntityPipelineBase second) {

		if(first.getConnectionType() != second.getConnectionType()) return 1;
		if(first == second) return 2;
		if(first.type != second.type) return 4;

		double len = Math.min(first.getMaxPipeLength(), second.getMaxPipeLength());

		Vec3 firstPos = first.getConnectionPoint();
		Vec3 secondPos = second.getConnectionPoint();

		Vec3 delta = Vec3.createVectorHelper(
				(secondPos.xCoord) - (firstPos.xCoord),
				(secondPos.yCoord) - (firstPos.yCoord),
				(secondPos.zCoord) - (firstPos.zCoord)
				);

		return len >= delta.lengthVector() ? 0 : 3;
	}

	public abstract ConnectionType getConnectionType();
	public abstract Vec3 getMountPos();
	public abstract double getMaxPipeLength();

	public Vec3 getConnectionPoint() {
		Vec3 mount = this.getMountPos();
		return mount.addVector(xCoord, yCoord, zCoord);
	}
	
	public List<int[]> getConnected() {
		return connected;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("conCount", connected.size());

		for(int i = 0; i < connected.size(); i++) {
			nbt.setIntArray("con" + i, connected.get(i));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		int count = nbt.getInteger("conCount");

		this.connected.clear();

		for(int i = 0; i < count; i++) {
			connected.add(nbt.getIntArray("con" + i));
		}
	}

	@Override
	public Packet getDescriptionPacket() {

		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	public enum ConnectionType {
		SMALL
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB; // not great!
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
