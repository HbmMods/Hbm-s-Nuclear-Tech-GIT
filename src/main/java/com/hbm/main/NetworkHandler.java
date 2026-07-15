package com.hbm.main;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.packet.IDiscardablePacket;
import com.hbm.packet.threading.ThreadedPacket;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleChannelHandlerWrapper;
import cpw.mods.fml.relauncher.Side;
import gnu.trove.map.hash.TByteObjectHashMap;
import gnu.trove.map.hash.TObjectByteHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.entity.player.EntityPlayerMP;


import java.lang.ref.WeakReference;
import java.util.EnumMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec.INBOUNDPACKETTRACKER;

// Essentially the `SimpleNetworkWrapper` from FML but doesn't flush the packets immediately. Also now with a custom codec!
public class NetworkHandler {

	private static final int MAX_PENDING_MESSAGES = 8192;
	private static final int MAX_MESSAGES_PER_TICK = 4096;
	private static final Queue<Runnable> clientTasks = new ConcurrentLinkedQueue<>();
	private static final Queue<Runnable> serverTasks = new ConcurrentLinkedQueue<>();
	private static final AtomicInteger pendingClientTasks = new AtomicInteger();
	private static final AtomicInteger pendingServerTasks = new AtomicInteger();

	// Network codec for allowing packets to be "precompiled".
	@ChannelHandler.Sharable
	private static class PrecompilingNetworkCodec extends MessageToMessageCodec<FMLProxyPacket, Object> {

		private final TByteObjectHashMap<Class<? extends IMessage>> discriminators = new TByteObjectHashMap<>();
		private final TObjectByteHashMap<Class<? extends IMessage>> types = new TObjectByteHashMap<>();

		public void addDiscriminator(int discriminator, Class<? extends IMessage> type) {
			discriminators.put((byte) discriminator, type);
			types.put(type, (byte) discriminator);
		}

		@Override
		public void handlerAdded(ChannelHandlerContext ctx) {
			ctx.attr(INBOUNDPACKETTRACKER).set(new ThreadLocal<>());
		}

		@Override
		protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) {
			ByteBuf outboundBuf = PooledByteBufAllocator.DEFAULT.heapBuffer();
			try {
				Class<?> msgClass = msg.getClass();
				if(!types.containsKey(msgClass)) throw new CodecException("Unregistered packet class " + msgClass.getName());
				outboundBuf.writeByte(types.get(msgClass));

				if(msg instanceof ThreadedPacket) {
					ByteBuf compiled = ((ThreadedPacket) msg).getCompiledBuffer();
					outboundBuf.writeBytes(compiled, compiled.readerIndex(), compiled.readableBytes());
				} else if(msg instanceof IMessage) {
					((IMessage) msg).toBytes(outboundBuf);
				} else {
					throw new CodecException("Unknown packet codec requested during encoding, expected IMessage, got " + msgClass.getName());
				}

				FMLProxyPacket proxy = new FMLProxyPacket(Unpooled.buffer(outboundBuf.readableBytes()).writeBytes(outboundBuf), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
				WeakReference<FMLProxyPacket> ref = ctx.attr(INBOUNDPACKETTRACKER).get().get();
				FMLProxyPacket old = ref == null ? null : ref.get();
				if (old != null) proxy.setDispatcher(old.getDispatcher());
				out.add(proxy);
			} finally {
				outboundBuf.release();
			}
		}

		@Override
		protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
			ByteBuf inboundBuf = msg.payload();
			byte discriminator = inboundBuf.readByte();
			Class<?> originalMsgClass = discriminators.get(discriminator);

			if(originalMsgClass == null)
				throw new CodecException("Undefined message for discriminator " + discriminator + " in channel " + msg.channel());

			Object newMsg = originalMsgClass.newInstance();
			ctx.attr(INBOUNDPACKETTRACKER).get().set(new WeakReference<>(msg));

			if(newMsg instanceof IMessage) // pretty much always the case
				((IMessage) newMsg).fromBytes(inboundBuf.slice());
			else
				throw new CodecException("Unknown packet codec requested during decoding, expected IMessage/PrecompiledPacket, got " + msg.getClass().getName());

			out.add(newMsg);
		}
	}

	private static FMLEmbeddedChannel clientChannel;
	private static FMLEmbeddedChannel serverChannel;

	private static PrecompilingNetworkCodec packetCodec;

	public NetworkHandler(String name) {
		packetCodec = new PrecompilingNetworkCodec();
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
		String type = channel.findChannelHandlerNameForType(PrecompilingNetworkCodec.class);
		SimpleChannelHandlerWrapper<REQ, REPLY> handler;
		IMessageHandler<REQ, REPLY> delegate;
		try {
			delegate = messageHandler.newInstance();
		} catch(ReflectiveOperationException ex) {
			throw new IllegalArgumentException("Could not instantiate packet handler " + messageHandler.getName(), ex);
		}
		handler = new SimpleChannelHandlerWrapper<>(new MainThreadMessageHandler<>(this, delegate, side), side, requestMessageType);
		channel.pipeline().addAfter(type, messageHandler.getName(), handler);
	}

	private static class MainThreadMessageHandler<REQ extends IMessage, REPLY extends IMessage> implements IMessageHandler<REQ, REPLY> {

		private final NetworkHandler network;
		private final IMessageHandler<REQ, REPLY> delegate;
		private final Side side;

		private MainThreadMessageHandler(NetworkHandler network, IMessageHandler<REQ, REPLY> delegate, Side side) {
			this.network = network;
			this.delegate = delegate;
			this.side = side;
		}

		@Override
		public REPLY onMessage(final REQ message, final cpw.mods.fml.common.network.simpleimpl.MessageContext context) {
			boolean queued = enqueue(side, new DiscardableRunnable() {
				@Override
				public void run() {
					try {
						REPLY reply = delegate.onMessage(message, context);
						if(reply != null) {
							if(side.isServer()) {
								network.sendTo(reply, context.getServerHandler().playerEntity);
							} else {
								network.sendToServer(reply);
							}
						}
					} catch(Throwable ex) {
						MainRegistry.logger.error("Failed to process {} on the main {} thread", message.getClass().getName(), side.name().toLowerCase(), ex);
					} finally {
						discardTask();
					}
				}

				@Override
				public void discardTask() {
					NetworkHandler.discard(message);
				}
			});

			if(!queued) {
				discard(message);
				MainRegistry.logger.warn("Discarding {} because the {} packet queue reached {} entries", message.getClass().getSimpleName(), side.name().toLowerCase(), MAX_PENDING_MESSAGES);
			}

			// Replies are dispatched after the queued handler has run.
			return null;
		}
	}

	private interface DiscardableRunnable extends Runnable {
		void discardTask();
	}

	private static boolean enqueue(Side side, Runnable task) {
		Queue<Runnable> queue = side.isClient() ? clientTasks : serverTasks;
		AtomicInteger count = side.isClient() ? pendingClientTasks : pendingServerTasks;
		int pending = count.incrementAndGet();
		if(pending > MAX_PENDING_MESSAGES) {
			count.decrementAndGet();
			return false;
		}
		queue.add(task);
		return true;
	}

	private static void discard(IMessage message) {
		if(message instanceof IDiscardablePacket) {
			try {
				((IDiscardablePacket) message).discard();
			} catch(RuntimeException ex) {
				MainRegistry.logger.warn("Failed to release resources owned by discarded packet " + message.getClass().getName(), ex);
			}
		}
	}

	public static void drainMainThreadQueue(Side side) {
		Queue<Runnable> queue = side.isClient() ? clientTasks : serverTasks;
		AtomicInteger count = side.isClient() ? pendingClientTasks : pendingServerTasks;

		for(int processed = 0; processed < MAX_MESSAGES_PER_TICK; processed++) {
			Runnable task = queue.poll();
			if(task == null) break;
			count.decrementAndGet();
			task.run();
		}
	}

	public static void clearMainThreadQueue(Side side) {
		Queue<Runnable> queue = side.isClient() ? clientTasks : serverTasks;
		AtomicInteger count = side.isClient() ? pendingClientTasks : pendingServerTasks;
		Runnable task;
		while((task = queue.poll()) != null) {
			count.decrementAndGet();
			if(task instanceof DiscardableRunnable) ((DiscardableRunnable) task).discardTask();
		}
	}

	public static void flush() {
		clientChannel.flush();
		serverChannel.flush();
	}

	public void sendToServer(IMessage message) { // No thread protection needed here, since the client never threads packets to the server.
		clientChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		clientChannel.write(message);
	}

	public void sendToDimension(IMessage message, int dimensionId) {
		if(!Thread.currentThread().getName().contains(PacketThreading.threadPrefix)) {
			try {
				PacketThreading.lock.lock();
				serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
				serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
				serverChannel.write(message);
			} finally {
				PacketThreading.lock.unlock();
			}
		} else {
			serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
			serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
			serverChannel.write(message);
		}
	}

	public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		if(!Thread.currentThread().getName().contains(PacketThreading.threadPrefix)) {
			try {
				PacketThreading.lock.lock();
				serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
				serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
				serverChannel.write(message);
			} finally {
				PacketThreading.lock.unlock();
			}
		} else {
			serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
			serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
			serverChannel.write(message);
		}
	}

	public void sendToAllAround(ByteBuf message, NetworkRegistry.TargetPoint point) {
		if(!Thread.currentThread().getName().contains(PacketThreading.threadPrefix)) {
			try {
				PacketThreading.lock.lock();
				serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
				serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
				serverChannel.write(message);
			} finally {
				PacketThreading.lock.unlock();
			}
		} else {
			serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
			serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
			serverChannel.write(message);
		}
	}

	public void sendTo(IMessage message, EntityPlayerMP player) {
		if(!Thread.currentThread().getName().contains(PacketThreading.threadPrefix)) {
			try {
				PacketThreading.lock.lock();
				serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
				serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
				serverChannel.write(message);
			} finally {
				PacketThreading.lock.unlock();
			}
		} else {
			serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
			serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
			serverChannel.write(message);
		}
	}

	public void sendToAll(IMessage message) {
		if(!Thread.currentThread().getName().contains(PacketThreading.threadPrefix)) {
			try {
				PacketThreading.lock.lock();
				serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
				serverChannel.write(message);
			} finally {
				PacketThreading.lock.unlock();
			}
		} else {
			serverChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
			serverChannel.write(message);
		}
	}
}
