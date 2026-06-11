package com.hbm.packet.toclient;

import com.hbm.inventory.container.ICustomPayloadReceiver;
import com.hbm.main.MainRegistry;
import com.hbm.util.BufferUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerCustomPayloadPacket implements IMessage {
	
	int windowId;
	NBTTagCompound data;
	
	public ContainerCustomPayloadPacket() { }

	public ContainerCustomPayloadPacket(int windowId, NBTTagCompound data) {
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
	
	public static class Handler implements IMessageHandler<ContainerCustomPayloadPacket, IMessage> {
		
		@SideOnly(Side.CLIENT) @Override
		public IMessage onMessage(ContainerCustomPayloadPacket m, MessageContext ctx) {
			EntityPlayer player = MainRegistry.proxy.me();
			if(player.openContainer instanceof ICustomPayloadReceiver) {
				ICustomPayloadReceiver cus = (ICustomPayloadReceiver) player.openContainer;
				cus.acceptData(m.windowId, m.data);
			}
			return null;
		}
	}
}
