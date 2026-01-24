package com.hbm.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public interface IBufPacketReceiver {

	public void serialize(ByteBuf buf);
	public void deserialize(ByteBuf buf);

	/**
	 * Called when a player needs data from this source.
	 * @implNote The default implementation does nothing.
	 * @implSpec The logic for this function is very implementation-dependent, however
	 * it should end up sending the player the TE's data on the current or next tick.
 	 */
	default void playerNeedsData(EntityPlayerMP player) {
	}
}
