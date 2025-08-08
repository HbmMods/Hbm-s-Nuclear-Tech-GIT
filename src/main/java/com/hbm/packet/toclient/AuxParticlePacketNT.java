package com.hbm.packet.toclient;

import com.hbm.main.MainRegistry;

import com.hbm.packet.threading.ThreadedPacket;
import com.hbm.util.BufferUtil;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class AuxParticlePacketNT extends ThreadedPacket {

	ByteBuf buffer;

	NBTTagCompound nbt;

	public AuxParticlePacketNT() { }

	public AuxParticlePacketNT(NBTTagCompound nbt, double x, double y, double z) {
		nbt.setDouble("posX", x);
		nbt.setDouble("posY", y);
		nbt.setDouble("posZ", z);
		this.nbt = nbt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.nbt = BufferUtil.readNBT(buf);
		this.buffer = buf;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		BufferUtil.writeNBT(buf, nbt);
	}

	public static class Handler implements IMessageHandler<AuxParticlePacketNT, IMessage> {

		@Override
		public IMessage onMessage(AuxParticlePacketNT m, MessageContext ctx) {

			if(Minecraft.getMinecraft().theWorld == null)
				return null;

			if(m.nbt != null)
				MainRegistry.proxy.effectNT(m.nbt);

			m.buffer.release();

			return null;
		}
	}
}
