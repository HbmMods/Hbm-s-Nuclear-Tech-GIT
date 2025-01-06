package com.hbm.packet.toclient;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class ExplosionKnockbackPacket implements IMessage {

	float motionX;
	float motionY;
	float motionZ;

	public ExplosionKnockbackPacket() { }

	public ExplosionKnockbackPacket(Vec3 vec) {
		this.motionX = (float) vec.xCoord;
		this.motionY = (float) vec.yCoord;
		this.motionZ = (float) vec.zCoord;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.motionX = buf.readFloat();
		this.motionY = buf.readFloat();
		this.motionZ = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.motionX);
		buf.writeFloat(this.motionY);
		buf.writeFloat(this.motionZ);
	}

	public static class Handler implements IMessageHandler<ExplosionKnockbackPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(ExplosionKnockbackPacket m, MessageContext ctx) {

			EntityPlayer thePlayer = Minecraft.getMinecraft().thePlayer;
			thePlayer.motionX += m.motionX;
			thePlayer.motionY += m.motionY;
			thePlayer.motionZ += m.motionZ;
			
			return null;
		}
	}
}
