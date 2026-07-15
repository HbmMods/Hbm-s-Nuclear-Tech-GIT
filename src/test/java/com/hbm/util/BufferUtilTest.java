package com.hbm.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import net.minecraft.nbt.NBTTagCompound;

public class BufferUtilTest {

	@Test
	public void stringRoundTripUsesUtf8() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			BufferUtil.writeString(buffer, "NTM — тест");
			assertEquals("NTM — тест", BufferUtil.readString(buffer));
		} finally {
			buffer.release();
		}
	}

	@Test(expected = DecoderException.class)
	public void stringLengthCannotExceedRemainingBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeInt(128);
			buffer.writeByte(1);
			BufferUtil.readString(buffer);
		} finally {
			buffer.release();
		}
	}

	@Test(expected = DecoderException.class)
	public void negativeIntegerArrayLengthIsRejected() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeInt(-2);
			BufferUtil.readIntArray(buffer);
		} finally {
			buffer.release();
		}
	}

	@Test(expected = DecoderException.class)
	public void invalidNegativeStringLengthIsRejected() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeInt(-2);
			BufferUtil.readString(buffer);
		} finally {
			buffer.release();
		}
	}

	@Test
	public void compressedNbtAboveSignedShortRangeRoundTrips() {
		byte[] randomBytes = new byte[40_000];
		new Random(12345L).nextBytes(randomBytes);
		NBTTagCompound original = new NBTTagCompound();
		original.setByteArray("payload", randomBytes);

		ByteBuf buffer = Unpooled.buffer();
		try {
			BufferUtil.writeNBT(buffer, original);
			assertTrue(buffer.getUnsignedShort(0) > Short.MAX_VALUE);
			NBTTagCompound decoded = BufferUtil.readNBT(buffer);
			assertArrayEquals(randomBytes, decoded.getByteArray("payload"));
		} finally {
			buffer.release();
		}
	}
}
