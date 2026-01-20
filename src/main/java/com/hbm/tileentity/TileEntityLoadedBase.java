package com.hbm.tileentity;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.BufPacket;
import com.hbm.sound.AudioWrapper;

import api.hbm.tile.ILoadedTile;
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
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("muffled", muffled);
	}

	public float getVolume(float baseVolume) {
		return muffled ? baseVolume * 0.1F : baseVolume;
	}

	private ByteBuf lastPackedBuf;

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(muffled);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.muffled = buf.readBoolean();
	}

	@Override
	public void dataUpdated() {
		hasDataChanged = true;
	}

	@Override
	public boolean hasDataUpdated() {
		return hasDataChanged;
	}

	/** Sends a sync packet that uses ByteBuf for efficient information-cramming */
	public void networkPackNT(int range) {
		if(worldObj.isRemote) return;

		boolean forceSend = playersNeedingData != null && !playersNeedingData.isEmpty();

		Set<EntityPlayerMP> toSendTo = new HashSet<>();

		// See docs for this function.
		// Anything that doesn't override this function will just always be sent to client (always modified).
		// We still need to send data to the people who need it (first-load), so we make sure to include that.
		if (this.hasDataUpdated()) {

			// Check if any players are actually within range.
			// If no player is actually close enough to receive the data, why compile/send it?
			// This also acts as a pre-calculation step for later where we need to send to all nearby players,
			// hence why this uses a `SendTo` instead of an `AllAround`.
			for (Object obj : worldObj.playerEntities) {
				EntityPlayerMP player = (EntityPlayerMP) obj;
				if (player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= range * range)
					toSendTo.add(player);
			}
		}

		boolean hasNoNormalReceivers = toSendTo.isEmpty();

		// skip compiling the packet at all if it won't be sent to anyone
		if (hasNoNormalReceivers && !forceSend) return;

		BufPacket packet = new BufPacket(xCoord, yCoord, zCoord, this);

		// Send data to players that need it, ignoring the cached hashcode.
		if (forceSend) {
			//MainRegistry.logger.info("LOAD: Sent TE data in chunk ({}, {})", this.xCoord >> 4, this.zCoord >> 4);
			sendToPlayers(packet, playersNeedingData);
			dataSent();
			playersNeedingData = null;
		}

		// skip sending the packet if there's no receivers (already sent to anyone who needs it (forceSend).
		if (hasNoNormalReceivers) return;

		// Don't send unnecessary packets, except for maybe one every second or so.
		// If we stop sending duplicate packets entirely, this causes issues when
		// a client unloads and then loads back a chunk with an unchanged tile entity.
		// For that client, the tile entity will appear default until anything changes about it.
		// In my testing, this can be reliably reproduced with a full fluid barrel, for instance.
		// I think it might be fixable by doing something with getDescriptionPacket() and onDataPacket(),
		// but this sidesteps the problem for the mean time.

		// UPDATE: The above problem has been found and has been fixed with the new `TEDataRequestPacket`.
		// This issue was caused when a player loads in a TE and immediately sends its data to the client.
		// If the client is outside the sending range of the TE (`range`), the data will be cached *as if* it were sent to the client,
		// but the client never receives it. Since the TE never knows when the client goes in range of the TE, it never gets updated
		// and re-sent.

		ByteBuf preBuf = packet.getCompiledBuffer();

		int hash = preBuf.hashCode();
		if(lastBufHash != -1 && hash == lastBufHash) return;

		this.lastBufHash = hash;

		//MainRegistry.logger.info("UPDATE: Sent TE data in chunk ({}, {})", this.xCoord >> 4, this.zCoord >> 4);
		sendToPlayers(packet, toSendTo);
		dataSent();
	}

	private void sendToPlayers(IMessage message, Iterable<EntityPlayerMP> list) {
		for(EntityPlayerMP player : list) {
			PacketDispatcher.wrapper.sendTo(message, player);
			//MainRegistry.logger.info("Sent TE data in chunk ({}, {}) to {}", this.xCoord >> 4, this.zCoord >> 4, player.getCommandSenderName());
		}
	}

	public void playerNeedsData(EntityPlayerMP player) {
		if (playersNeedingData == null)
			playersNeedingData = new ArrayList<>();
		playersNeedingData.add(player);
	}
}
