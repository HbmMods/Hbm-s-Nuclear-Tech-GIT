package com.hbm.tileentity;

import io.netty.buffer.ByteBuf;

public interface IBufPacketReceiver {

	public void serialize(ByteBuf buf);
	public void deserialize(ByteBuf buf);

	// Quick and simple way for TEs to check if their data has even been updated.
	// Why do *anything* with the packets if the data we're planning to send wouldn't change anything?
	// This is a default method that needs to be overriden because it would be obtuse to implement in
	// some TEs.
	// Anything that's simple and doesn't change very often should use these functions.
	default void dataUpdated() {}

	// Clears the dataUpdated boolean. Done whenever the tile has sucessfully sent its changes to clients.
	default void dataSent() {}

	default boolean hasDataUpdated() {
		return true;
	}
}
