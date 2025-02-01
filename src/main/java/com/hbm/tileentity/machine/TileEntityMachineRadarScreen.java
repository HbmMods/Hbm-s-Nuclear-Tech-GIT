package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.IBufPacketReceiver;

import api.hbm.entity.RadarEntry;
import com.hbm.tileentity.TileEntityLoadedBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineRadarScreen extends TileEntityLoadedBase implements IBufPacketReceiver {

	public List<RadarEntry> entries = new ArrayList();
	public int refX;
	public int refY;
	public int refZ;
	public int range;
	public boolean linked;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			this.networkPackNT(100);
			entries.clear();
			this.linked = false;
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(linked);
		buf.writeInt(refX);
		buf.writeInt(refY);
		buf.writeInt(refZ);
		buf.writeInt(range);
		buf.writeInt(entries.size());
		for(RadarEntry entry : entries) entry.toBytes(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		linked = buf.readBoolean();
		refX = buf.readInt();
		refY = buf.readInt();
		refZ = buf.readInt();
		range = buf.readInt();
		int count = buf.readInt();
		this.entries.clear();
		for(int i = 0; i < count; i++) {
			RadarEntry entry = new RadarEntry();
			entry.fromBytes(buf);
			this.entries.add(entry);
		}
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
