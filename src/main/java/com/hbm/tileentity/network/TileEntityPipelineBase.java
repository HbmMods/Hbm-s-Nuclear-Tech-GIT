package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.NodeNet;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.fauxpointtwelve.DirPos;

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

public abstract class TileEntityPipelineBase extends TileEntityLoadedBase {

	protected List<int[]> connected = new ArrayList<>();

	protected abstract void addNodeConnection(int x, int y, int z);
	protected abstract void destroyNode(int x, int y, int z);

	protected <T extends NodeNet, N extends GenNode<T>> N ensureNode(N node, INetworkProvider<T> provider, Supplier<N> createNode) {
		if(node == null || node.expired) {
			node = (N)UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, provider);
			if(node == null || node.expired) {
				node = createNode.get();
				UniNodespace.createNode(worldObj, node);
			}
		}
		return node;
	}

	protected <N extends GenNode<?>> N initNode(N node) {
		ForgeDirection dir = getOppositeDir();
		node.setConnections(
				new DirPos(xCoord, yCoord, zCoord, ForgeDirection.UNKNOWN),
				new DirPos(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir));
		for(int[] pos : this.connected) node.addConnection(new DirPos(pos[0], pos[1], pos[2], ForgeDirection.UNKNOWN));
		return node;
	}

	protected void linkNode(GenNode<?> node, int x, int y, int z) {
		node.recentlyChanged = true;
		node.addConnection(new DirPos(x, y, z, ForgeDirection.UNKNOWN));
	}

	public ForgeDirection getOppositeDir() {
		return ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
	}
	
	public abstract NetworkType getNetworkType();
	
	public ConnectionType getConnectionType() {
		return ConnectionType.SMALL;
	}

	public Vec3 getMountPos() {
		return Vec3.createVectorHelper(0.5D, 0.5D, 0.5D);
	}

	public double getMaxPipeLength() {
		return 10;
	}

	public void addConnection(int x, int y, int z) {
		this.connected.add(new int[] {x, y, z});
		addNodeConnection(x, y, z);

		this.markDirty();

		if(worldObj instanceof WorldServer) {
			WorldServer world = (WorldServer) worldObj;
			world.getPlayerManager().markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public void disconnectAll() {
		for(int[] pos : this.connected) {
			TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
			if(te == this) continue;

			if(te instanceof TileEntityPipelineBase) {
				TileEntityPipelineBase pipeline = (TileEntityPipelineBase) te;
				destroyNode(pos[0], pos[1], pos[2]);

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

		destroyNode(xCoord, yCoord, zCoord);
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
	 * 3: Connection length exceeds maximum<br>
	 * 4: Pipeline fluid types do not match
	 */
	public static int canConnect(TileEntityPipelineBase first, TileEntityPipelineBase second) {
		if(first.getNetworkType() != second.getNetworkType()) return 1;
		if(first.getConnectionType() != second.getConnectionType()) return 1;
		if(first == second) return 2;

		int ret = first.canConnect(second);
		if(ret != 0) return ret;

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

	protected int canConnect(TileEntityPipelineBase other) {
		return 0;
	}

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

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB; // not great!
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	public enum ConnectionType {
		SMALL
	}

	public enum NetworkType {
		FLUID,
		EXHAUST,
		PNEUMATIC
	}
}
