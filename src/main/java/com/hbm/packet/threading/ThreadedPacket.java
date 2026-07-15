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
		if(compiledBuffer != null && compiledBuffer.refCnt() > 0)
			compiledBuffer.release();

		ByteBuf nextBuffer = Unpooled.buffer();
		try {
			this.toBytes(nextBuffer);
			compiledBuffer = nextBuffer;
		} catch(RuntimeException ex) {
			nextBuffer.release();
			compiledBuffer = null;
			throw ex;
		} catch(Error ex) {
			nextBuffer.release();
			compiledBuffer = null;
			throw ex;
		}
	}

	/**
	 * Returns the compiled buffer.
	 */
	public ByteBuf getCompiledBuffer() {
		if(compiledBuffer == null || compiledBuffer.refCnt() <= 0 || compiledBuffer.readableBytes() <= 0 /* No data written */)
			this.compile();
		return compiledBuffer;
	}

	public void releaseCompiledBuffer() {
		if(compiledBuffer != null && compiledBuffer.refCnt() > 0) compiledBuffer.release();
		compiledBuffer = null;
	}
}
