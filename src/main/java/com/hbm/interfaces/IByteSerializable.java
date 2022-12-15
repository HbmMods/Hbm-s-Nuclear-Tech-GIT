package com.hbm.interfaces;

import com.hbm.main.DeserializationException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public interface IByteSerializable
{
	public void writeToBytes(ByteBuf buf);
	public void readFromBytes(byte[] bytes) throws DeserializationException;
	
	public default byte[] writeToBytes()
	{
		final ByteBuf buf = Unpooled.buffer();
		writeToBytes(buf);
		return buf.array();
	}
}
