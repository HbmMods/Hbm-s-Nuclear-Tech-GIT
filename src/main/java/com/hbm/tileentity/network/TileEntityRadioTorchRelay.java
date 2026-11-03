package com.hbm.tileentity.network;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BufferUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityRadioTorchRelay extends TileEntityLoadedBase implements IControlReceiver {

	public boolean forceUpdate = false;
	public boolean polling = false;
	public String channel = "";
	public String expression = "";
	public NBTTagCompound variables = new NBTTagCompound();
	public String lastState = "";

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			//TODO
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.polling);
		BufferUtil.writeString(buf, channel);
		BufferUtil.writeString(buf, expression);
		BufferUtil.writeString(buf, lastState);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.polling = buf.readBoolean();
		this.channel = BufferUtil.readString(buf);
		this.expression = BufferUtil.readString(buf);
		this.lastState = BufferUtil.readString(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.polling = nbt.getBoolean("p");
		this.channel = nbt.getString("c");
		this.expression = nbt.getString("e");
		this.lastState = nbt.getString("l");
		
		this.variables = nbt.getCompoundTag("v");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("p", polling);
		nbt.setString("c", channel);
		nbt.setString("e", expression);
		nbt.setString("l", lastState);
		
		nbt.setTag("v", variables);
	}

	public void setChannel(String channel) {
		if(this.channel.equals(channel)) return;

		this.channel = channel;
		this.forceUpdate = true;
	}
	
	public void setExpression(String expression) {
		if(this.expression.equals(expression)) return;
		
		this.expression = expression;
		this.forceUpdate = true;
		
		//TODO: clear var NBT compound and add all detected substitution keys
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 16D;
	}

	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {
		if(data.hasKey("p")) this.polling = data.getBoolean("p");
		
		//TODO

		this.markDirty();
	}
}
