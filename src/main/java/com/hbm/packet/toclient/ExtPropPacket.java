package com.hbm.packet.toclient;

import java.io.IOException;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class ExtPropPacket implements IMessage {
	
	PacketBuffer buffer;

	public ExtPropPacket() { }

	public ExtPropPacket(NBTTagCompound nbt) {
		
		this.buffer = new PacketBuffer(Unpooled.buffer());
		
		try {
			buffer.writeNBTTagCompoundToBuffer(nbt);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<ExtPropPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(ExtPropPacket m, MessageContext ctx) {
			
			if(Minecraft.getMinecraft().theWorld == null)
				return null;
			
			try {
				
				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();
				HbmLivingProps props = HbmLivingProps.getData(Minecraft.getMinecraft().thePlayer);
				HbmPlayerProps pprps = HbmPlayerProps.getData(Minecraft.getMinecraft().thePlayer);
				props.loadNBTData(nbt);
				pprps.loadNBTData(nbt);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}
}
