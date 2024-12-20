package com.hbm.tileentity;

import io.netty.buffer.ByteBuf;

public interface IBufPacketReceiver {

	public void serialize(ByteBuf buf);
	public void deserialize(ByteBuf buf);
}
