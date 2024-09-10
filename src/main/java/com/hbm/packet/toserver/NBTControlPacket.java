package com.hbm.packet.toserver;

import java.io.IOException;

import com.hbm.interfaces.IControlReceiver;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class NBTControlPacket implements IMessage {
	
	PacketBuffer buffer;
	int x;
	int y;
	int z;

	public NBTControlPacket() { }

	public NBTControlPacket(NBTTagCompound nbt, int x, int y, int z) {
		
		this.buffer = new PacketBuffer(Unpooled.buffer());
		this.x = x;
		this.y = y;
		this.z = z;
		
		try {
			buffer.writeNBTTagCompoundToBuffer(nbt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		
		if(buffer == null) buffer = new PacketBuffer(Unpooled.buffer());
		
		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		
		if (buffer == null) buffer = new PacketBuffer(Unpooled.buffer());
		
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<NBTControlPacket, IMessage> {
		
		@Override
		public IMessage onMessage(NBTControlPacket m, MessageContext ctx) {

			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			if(p.worldObj == null)
				return null;
			
			TileEntity te = p.worldObj.getTileEntity(m.x, m.y, m.z);
			
			try {
				
				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();
				
				if(nbt != null) {
					if(te instanceof IControlReceiver) {
						
						IControlReceiver tile = (IControlReceiver)te;
						
						if(tile.hasPermission(p)) {
							tile.receiveControl(p, nbt);
							tile.receiveControl(nbt);
						}
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}
}
