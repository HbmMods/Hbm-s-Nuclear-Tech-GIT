package com.hbm.tileentity.network;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;

import com.hbm.util.BufferUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityRadioTorchLogic extends TileEntityLoadedBase implements IControlReceiver {

	/** channel we're broadcasting on/listening to */
	public String channel = "";
	/** previous redstone state for input/output, needed for state change detection */
	public int lastState = 0;
	/** last update tick, needed for receivers listening for changes */
	public long lastUpdate;
	/** switches state change mode to tick-based polling */
	public boolean polling = false;
	/** switches evaluation of conditions from ascending to descending */
	public boolean descending = false;
	/** mapping for constants to compare against */
	public String[] mapping;
	/** mapping for conditions through [1, 10], being (<, <=, >=, >, ==, !=, equals, !equals, contains, !contains) */
	public int[] conditions;

	public TileEntityRadioTorchLogic() {
		this.mapping = new String[16];
		for(int i = 0; i < 16; i++) this.mapping[i] = "";
		this.conditions = new int[16];
		for(int i = 0; i < 16; i++) this.conditions[i] = 0;
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(!this.channel.isEmpty()) {

				RTTYChannel chan = RTTYSystem.listen(worldObj, this.channel);

				if(chan != null && (this.polling || (chan.timeStamp > this.lastUpdate - 1 && chan.timeStamp != -1))) { // if we're either polling or a new message has come in
					String msg = "" + chan.signal;
					this.lastUpdate = worldObj.getTotalWorldTime();
					int nextState = 0; //if no remap apply, default to 0

					if(chan.timeStamp < this.lastUpdate - 2 && this.polling) {
						/* the vast majority use-case for this is going to be inequalities, NOT parsing, and the input is undefined - not the output
						 * if no signal => 0 for polling, advanced users parsing strings can easily accommodate this fact instead of breaking numerical torches */
						msg = "0";
					}

					if(descending) {
						for(int i = 15; i >= 0; i--) {
							if(!mapping[i].equals("") && parseSignal(msg, i)) {
								nextState = i;
								break;
							}
						}
					} else {
						for(int i = 0; i <= 15; i++) {
							if(!mapping[i].equals("") && parseSignal(msg, i)) {
								nextState = i;
								break;
							}
						}
					}

					if(this.lastState != nextState) {
						this.lastState = nextState;
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType());
						this.markDirty();
					}
				}
			}

			networkPackNT(50);
		}
	}

	public boolean parseSignal(String signal, int index) {
		if(conditions[index] <= 5) { //if a non-string operator
			int sig = 0;
			int map = 0;
			try { sig = Integer.parseInt(signal); map = Integer.parseInt(mapping[index]); } catch(Exception x) {
				return false; //not a valid input; skip! slightly annoying about the mapping but we'll restrict input anyway
			};

			switch(conditions[index]) {
			default: return sig < map;
			case 1: return sig <= map;
			case 2: return sig >= map;
			case 3: return sig > map;
			case 4: return sig == map;
			case 5: return sig != map;
			}
		}

		switch(conditions[index]) {
		default: return signal.equals(mapping[index]);
		case 7: return !signal.equals(mapping[index]);
		case 8: return signal.contains(mapping[index]);
		case 9: return !signal.contains(mapping[index]);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.polling = nbt.getBoolean("p");
		this.descending = nbt.getBoolean("d");
		this.lastState = nbt.getInteger("l");
		this.lastUpdate = nbt.getLong("u");
		this.channel = nbt.getString("c");
		for(int i = 0; i < 16; i++) this.mapping[i] = nbt.getString("m" + i);
		for(int i = 0; i < 16; i++) this.conditions[i] = nbt.getInteger("c" + i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("p", polling);
		nbt.setBoolean("d", descending);
		nbt.setInteger("l", lastState);
		nbt.setLong("u", lastUpdate);
		if(channel != null) nbt.setString("c", channel);
		for(int i = 0; i < 16; i++) if(!mapping[i].equals("")) nbt.setString("m" + i, mapping[i]);
		for(int i = 0; i < 16; i++) if(conditions[i] > 0) nbt.setInteger("c" + i, conditions[i]);
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.polling);
		BufferUtil.writeString(buf, this.channel);
		buf.writeBoolean(this.descending);
		for(int i = 0; i < 16; i++) BufferUtil.writeString(buf, this.mapping[i]);
		for(int i = 0; i < 16; i++) buf.writeInt(this.conditions[i]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.polling = buf.readBoolean();
		this.channel = BufferUtil.readString(buf);
		this.descending = buf.readBoolean();
		for(int i = 0; i < 16; i++) this.mapping[i] = BufferUtil.readString(buf);
		for(int i = 0; i < 16; i++) this.conditions[i] = buf.readInt();
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("l", (byte) this.lastState);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		int last = this.lastState;
		this.lastState = pkt.func_148857_g().getByte("l");
		if(this.lastState != last) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 16D;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("p")) this.polling = data.getBoolean("p");
		if(data.hasKey("c")) this.channel = data.getString("c");
		if(data.hasKey("d")) this.descending = data.getBoolean("d");
		for(int i = 0; i < 16; i++) if(data.hasKey("m" + i)) this.mapping[i] = data.getString("m" + i);
		for(int i = 0; i < 16; i++) if(data.hasKey("c" + i)) this.conditions[i] = data.getInteger("c" + i);

		this.markDirty();
	}
}
