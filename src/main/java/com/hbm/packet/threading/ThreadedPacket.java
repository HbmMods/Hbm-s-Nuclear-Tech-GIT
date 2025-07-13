package com.hbm.packet.threading;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * This is the base class for any packets passing through the PacketThreading system.
 */
public abstract class ThreadedPacket implements IMessage {

	ByteBuf compiledBuffer;

	public void compile() {
		if(compiledBuffer != null)
			compiledBuffer.release();

		compiledBuffer = Unpooled.buffer();

		this.toBytes(compiledBuffer); // Create buffer and read data to it.
	}

	/**
	 * Returns the compiled buffer.
	 */
	public ByteBuf getCompiledBuffer() {
		if(compiledBuffer == null || compiledBuffer.readableBytes() <= 0 /* No data written */)
			this.compile();
		return compiledBuffer;
	}
}
