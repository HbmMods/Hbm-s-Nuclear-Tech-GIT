package com.hbm.tileentity;

import com.hbm.packet.BufPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public interface IBufPacketReceiver {

	public void serialize(ByteBuf buf);
	public void deserialize(ByteBuf buf);
	
	public default void sendStandard(int range) {
		TileEntity te = (TileEntity) this;
		PacketDispatcher.wrapper.sendToAllAround(new BufPacket(te.xCoord, te.yCoord, te.zCoord, this), new TargetPoint(te.getWorldObj().provider.dimensionId, te.xCoord, te.yCoord, te.zCoord, range));
	}
}
