package com.hbm.packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hbm.items.tool.ItemTransporterLinker;
import com.hbm.items.tool.ItemTransporterLinker.TransporterInfo;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.Constants;

public class TransporterLinkerPacket implements IMessage {
	
	PacketBuffer buffer;

	public TransporterLinkerPacket() {

	}

	public TransporterLinkerPacket(List<TransporterInfo> transporters) {
		buffer = new PacketBuffer(Unpooled.buffer());

		NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList transporterList = new NBTTagList();
        for(TransporterInfo info : transporters) {
            NBTTagCompound tag = new NBTTagCompound();
            info.writeToNBT(tag);
            transporterList.appendTag(tag);
        }
        nbt.setTag("a", transporterList);
		
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

	public static class Handler implements IMessageHandler<TransporterLinkerPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TransporterLinkerPacket m, MessageContext ctx) {
			
			Minecraft.getMinecraft();

			try {
				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();
				ItemTransporterLinker.currentTransporters = new ArrayList<>();
				
				if(nbt != null) {
                    NBTTagList transporterList = nbt.getTagList("a", Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < transporterList.tagCount(); i++) {
                        NBTTagCompound tag = transporterList.getCompoundTagAt(i);
                        TransporterInfo info = TransporterInfo.readFromNBT(tag);
                        ItemTransporterLinker.currentTransporters.add(info);
                    }
                }
				
			} catch (Exception x) {
			}
			return null;
		}
	}
}
