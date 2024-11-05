package com.hbm.tileentity;

import com.hbm.handler.threading.BufPacketThreading;
import io.netty.buffer.ByteBuf;

public interface IBufPacketReceiver {

	public void serialize(ByteBuf buf);
	public void deserialize(ByteBuf buf);

	public default void sendStandard(int range) {
		BufPacketThreading.createBufPacket(this, range);
	}
}
