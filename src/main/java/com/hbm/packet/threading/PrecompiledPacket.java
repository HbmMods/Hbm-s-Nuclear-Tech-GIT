package com.hbm.packet.threading;

import io.netty.buffer.ByteBuf;

/**
 * Abstract class for precompiled packets.
 * Contains no content of its own; purely for distinction between precompiling and normal packets.
 * */
public abstract class PrecompiledPacket extends ThreadedPacket { }
