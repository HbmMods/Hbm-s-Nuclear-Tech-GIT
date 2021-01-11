package com.hbm.packet;

import com.hbm.extprop.HbmLivingProps;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RadSurveyPacket implements IMessage {

	float rad;

	public RadSurveyPacket()
	{
		
	}

	public RadSurveyPacket(float rad)
	{
		this.rad = rad;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		rad = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeFloat(rad);
	}

	public static class Handler implements IMessageHandler<RadSurveyPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(RadSurveyPacket m, MessageContext ctx) {
			try {
				
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				HbmLivingProps.setRadiation(player, m.rad);
				
			} catch (Exception x) { }
			return null;
		}
	}
}
