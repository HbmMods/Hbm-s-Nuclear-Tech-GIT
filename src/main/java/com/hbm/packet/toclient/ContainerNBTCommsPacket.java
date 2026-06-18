package com.hbm.packet.toclient;

import com.hbm.inventory.container.ICustomPayloadReceiver;
import com.hbm.main.MainRegistry;
import com.hbm.util.BufferUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerNBTCommsPacket implements IMessage {
	
	int windowId;
	NBTTagCompound data;
	
	public ContainerNBTCommsPacket() { }

	public ContainerNBTCommsPacket(int windowId, NBTTagCompound data) {
		this.windowId = windowId;
		this.data = data;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.windowId = buf.readInt();
		this.data = BufferUtil.readNBT(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.windowId);
		BufferUtil.writeNBT(buf, this.data);
	}
	
	public static class Handler implements IMessageHandler<ContainerNBTCommsPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ContainerNBTCommsPacket m, MessageContext ctx) {

			EntityPlayer player = ctx.side.isClient() ? MainRegistry.proxy.me() : ctx.getServerHandler().playerEntity;
			if(player.openContainer instanceof ICustomPayloadReceiver) {
				ICustomPayloadReceiver cus = (ICustomPayloadReceiver) player.openContainer;
				cus.acceptData(ctx.side, m.windowId, m.data);
			}
			return null;
		}
	}
}
