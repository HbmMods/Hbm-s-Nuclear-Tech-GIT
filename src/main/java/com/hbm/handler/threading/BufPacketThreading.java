package com.hbm.handler.threading;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.BufPacket;
import com.hbm.tileentity.IBufPacketReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BufPacketThreading {

	private static final ThreadFactory packetThreadFactory = new ThreadFactoryBuilder().setNameFormat("NTM-Packet-Thread").build();

	private static final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, packetThreadFactory);

	private static int total = 0;

	private static final List<Future<?>> futureList = new ArrayList<>();

	public static void createBufPacket(IMessage message, TargetPoint target) {
		Runnable task = () -> PacketDispatcher.wrapper.sendToAllAround(message, target);
		total++;
		futureList.add(threadPool.submit(task));
	}

	public static void createBufPacket(IBufPacketReceiver that, int range) {
		Runnable task = () -> {
			TileEntity te = (TileEntity) that;
			TargetPoint target = new TargetPoint(te.getWorldObj().provider.dimensionId, te.xCoord, te.yCoord, te.zCoord, range);
			BufPacket message = new BufPacket(te.xCoord, te.yCoord, te.zCoord, that);
			PacketDispatcher.wrapper.sendToAllAround(message, target);
		};
		total++;
		futureList.add(threadPool.submit(task));
	}

	public static void waitUntilThreadFinished() {
		try {
			for(Future<?> future : futureList) {
				future.get(200, TimeUnit.MILLISECONDS); // I HATE EVERYTHING
			}
		} catch (ExecutionException ignored) {
			// impossible
		} catch (TimeoutException e) {
			MainRegistry.logger.warn("A packet has taken >200ms to process, discarding {}/{} ({}%) packets to prevent pausing of main thread.", threadPool.getQueue().size(), total, (double) (threadPool.getQueue().size() / total * 100));
			threadPool.getQueue().clear();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // maybe not the best thing but it's gotta be here
		}
	}
}
