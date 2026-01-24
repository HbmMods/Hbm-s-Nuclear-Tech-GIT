package com.hbm.tileentity;

import com.hbm.main.NetworkHandler;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.BufPacket;
import com.hbm.sound.AudioWrapper;

import api.hbm.tile.ILoadedTile;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileEntityLoadedBase extends TileEntity implements ILoadedTile, IBufPacketReceiver {

	public boolean isLoaded = true;
	public boolean muffled = false;

	protected boolean hasDataChanged = true;

	private int lastBufHash = -1;
	private ByteBuf lastBuf = null;
	// Save on memory. If no players need this data (not being newly-loaded or anything),
	// just set it to null. This slows down things a *tiny* bit during load due to initializing
	// the list, but it's not a big deal.
	private List<EntityPlayerMP> playersNeedingData = null;

	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		this.isLoaded = false;
	}

	public AudioWrapper createAudioLoop() { return null; }

	public AudioWrapper rebootAudio(AudioWrapper wrapper) {
		wrapper.stopSound();
		AudioWrapper audio = createAudioLoop();
		audio.startSound();
		return audio;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.muffled = nbt.getBoolean("muffled");
		this.hasDataChanged = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("muffled", muffled);
	}

	public float getVolume(float baseVolume) {
		return muffled ? baseVolume * 0.1F : baseVolume;
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(muffled);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.muffled = buf.readBoolean();
	}

	public void setMuffled(boolean muffled) {
		this.muffled = muffled;
		dataChanged();
	}

	public void dataChanged() {
		this.hasDataChanged = true;
	}

	/** Sends a sync packet that uses ByteBuf for efficient information-cramming */
	public void networkPackNT(int range) {
		if(worldObj.isRemote) return;

		boolean forceSend = playersNeedingData != null && !playersNeedingData.isEmpty();

		boolean hasNoNormalReceivers = getPlayersToSendTo(range).isEmpty();

		// skip compiling the packet at all in the event it won't be sent to anyone
		if (hasNoNormalReceivers && !forceSend) return;

		BufPacket packet = new BufPacket(xCoord, yCoord, zCoord, this);
		ByteBuf preBuf = packet.getCompiledBuffer();

		// Send data to players that need it, ignoring the cached hashcode.
		if (forceSend) {
			//MainRegistry.logger.info("LOAD: Sent TE data in chunk ({}, {})", this.xCoord >> 4, this.zCoord >> 4);
			sendToPlayers(packet, playersNeedingData);
			playersNeedingData = null;
		}

		// skip sending the packet if there are no receivers (already sent to anyone who needs it with forceSend).
		if (hasNoNormalReceivers) return;

		int hash = preBuf.hashCode();
		if(lastBufHash != -1 && hash == lastBufHash) return;

		this.lastBufHash = hash;

		//MainRegistry.logger.info("UPDATE: Sent TE data in chunk ({}, {})", this.xCoord >> 4, this.zCoord >> 4);
		PacketDispatcher.wrapper.sendToAllAround(packet, new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}

	/**
	 * Sends a sync packet that uses ByteBuf for efficient information-cramming, employing optimization tricks along the way.
	 * <p>
	 * This should be used instead of `networkPackNT()` if this tile supports the Data-Change optimization (see docs).
	 * If this is used when the tile does not support the aforementioned optimization, data may not be sent to clients correctly.
	 */
	public boolean networkPackMK2(int range) {
		if(worldObj.isRemote) return false;

		boolean forceSend = playersNeedingData != null && !playersNeedingData.isEmpty();

		Set<EntityPlayerMP> toSendTo = null;

		if (this.hasDataChanged)
			toSendTo = getPlayersToSendTo(range);

		boolean hasNoNormalReceivers = toSendTo == null || toSendTo.isEmpty();

		// skip compiling the packet at all in the event it won't be sent to anyone
		if (hasNoNormalReceivers && !forceSend) return false;

		BufPacket packet = new BufPacket(xCoord, yCoord, zCoord, this);

		if (this.hasDataChanged || lastBuf == null) {
			ByteBuf buffer = packet.getCompiledBuffer();
			this.lastBuf = buffer.copy();
		} else
			packet.writeToCompiledBuffer(lastBuf); // Reuse the last buffer if the data hasn't changed.

		// Send data to players that need it.
		if (forceSend) {
			//MainRegistry.logger.info("LOAD: Sent TE data in chunk ({}, {})", this.xCoord >> 4, this.zCoord >> 4);
			sendToPlayers(packet, playersNeedingData);
			playersNeedingData = null;
			hasDataChanged = false;
		}

		// skip sending the packet if there are no receivers (already sent to anyone who needs it with forceSend).
		if (hasNoNormalReceivers) return true;

		//MainRegistry.logger.info("UPDATE: Sent TE data in chunk ({}, {})", this.xCoord >> 4, this.zCoord >> 4);
		PacketDispatcher.wrapper.sendToAllAround(packet, new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
		hasDataChanged = false;
		return true;
	}

	private Set<EntityPlayerMP> getPlayersToSendTo(int range) {
		Set<EntityPlayerMP> toSendTo = new HashSet<>();
		// Check if any players are actually within range.
		// If no player is actually close enough to receive the data, why compile/send it?
		// This also acts as a pre-calculation step for later where we need to send to all nearby players,
		// hence why this uses a `SendTo` instead of an `AllAround`.
		// I have no idea why NTM never did this.
		for (Object obj : worldObj.playerEntities) {
			EntityPlayerMP player = (EntityPlayerMP) obj;
			if (player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= range * range)
				toSendTo.add(player);
		}
		return toSendTo;
	}

	private static void sendToPlayers(IMessage message, Iterable<EntityPlayerMP> list) {
		for(EntityPlayerMP player : list) {
			PacketDispatcher.wrapper.sendTo(message, player);
			//MainRegistry.logger.info("Sent TE data in chunk ({}, {}) to {}", this.xCoord >> 4, this.zCoord >> 4, player.getCommandSenderName());
		}
	}

	@Override
	public void playerNeedsData(EntityPlayerMP player) {
		if (playersNeedingData == null)
			playersNeedingData = new ArrayList<>();
		playersNeedingData.add(player);
	}
}
