package com.hbm.main;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.EnumMap;

// Essentially the `SimpleNetworkWrapper` from FML but doesn't flush the packets immediately.
public class NetworkHandler {

	private static FMLEmbeddedChannel clientChannel;
	private static FMLEmbeddedChannel serverChannel;

	private static SimpleIndexedCodec packetCodec;

	public NetworkHandler(String name) {
		packetCodec = new SimpleIndexedCodec();
		EnumMap<Side, FMLEmbeddedChannel> channels = NetworkRegistry.INSTANCE.newChannel(name, packetCodec);
		clientChannel = channels.get(Side.CLIENT);
		serverChannel = channels.get(Side.SERVER);
	}

	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, int discriminator, Side side) {

		packetCodec.addDiscriminator(discriminator, requestMessageType);
		FMLEmbeddedChannel channel;
		if(side.isClient())
			channel = clientChannel;
		else
			channel = serverChannel;
		String type = channel.findChannelHandlerNameForType(SimpleIndexedCodec.class);
		SimpleChannelHandlerWrapper<REQ, REPLY> handler;
		if (side == Side.SERVER) {
			handler = new SimpleChannelHandlerWrapper<>(messageHandler, side, requestMessageType);
		} else {
			handler = new SimpleChannelHandlerWrapper<>(messageHandler, side, requestMessageType);
		}
		channel.pipeline().addAfter(type, messageHandler.getName(), handler);
	}

	public static void flush() {
		clientChannel.flush();
		serverChannel.flush();
	}

	public void sendToServer(IMessage message) {
		clientChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		clientChannel.write(message);
	}

	public void sendToDimension(IMessage message, int dimensionId) {
		serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
		serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
		serverChannel.write(message);

	}

	public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
		serverChannel.write(message);

	}

	public void sendTo(IMessage message, EntityPlayerMP player) {
		serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		serverChannel.write(message);
	}

	public void sendToAll(IMessage message) {
		serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		serverChannel.write(message);
	}
}
