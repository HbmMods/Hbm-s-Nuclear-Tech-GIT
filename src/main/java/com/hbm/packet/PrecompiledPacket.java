package com.hbm.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Abstract class for precompiled packets. This should be used if the packet is going to be threaded through the `PacketThreading` handler.
 */
public abstract class PrecompiledPacket implements IMessage {

	ByteBuf preCompiledBuf;

	/**
	 * Returns a precompiled buffer used to avoid race conditions when sending certain packets in threads.
	 * @return The precompiled packet in a `ByteBuf`.
	 */
	public ByteBuf getPreBuf() {
		if(preCompiledBuf == null || preCompiledBuf.readableBytes() <= 0 /* No data written */)
			this.makePreBuf();
		return preCompiledBuf;
	}

	/**
	 * Forcefully creates the precompiled buffer, use `getPreBuf()` whenever possible.
	 */
	public void makePreBuf() {
		if(preCompiledBuf != null)
			preCompiledBuf.release();

		preCompiledBuf = Unpooled.buffer();

		this.toBytes(preCompiledBuf); // Create buffer and read data to it.
	}
}
