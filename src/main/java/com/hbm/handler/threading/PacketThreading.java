package com.hbm.handler.threading;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PrecompiledPacket;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PacketThreading {

	private static final ThreadFactory packetThreadFactory = new ThreadFactoryBuilder().setNameFormat("NTM-Packet-Thread-%d").build();

	private static final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, packetThreadFactory);

	private static int totalCnt = 0;

	private static int processedCnt = 0;

	private static final List<Future<?>> futureList = new ArrayList<>();

	/**
	 * Adds a packet to the thread pool to be processed in the future. This is only compatible with the `sendToAllAround` dispatch operation.
	 * @param message Message to process.
	 * @param target TargetPoint to send to.
	 */
	public static void createThreadedPacket(IMessage message, TargetPoint target) {

		// `message` can be precompiled or not.
		if(message instanceof PrecompiledPacket)
			((PrecompiledPacket) message).getPreBuf(); // Gets the precompiled buffer, doing nothing if it already exists.

		totalCnt++;
		futureList.add(threadPool.submit(() -> {
			PacketDispatcher.wrapper.sendToAllAround(message, target);
			if(message instanceof PrecompiledPacket)
				((PrecompiledPacket) message).getPreBuf().release();
			processedCnt++;
		}));
	}

	/**
	 * Wait until the packet thread is finished processing.
	 */
	public static void waitUntilThreadFinished() {
		try {
			for(Future<?> future : futureList) {
				future.get(50, TimeUnit.MILLISECONDS); // I HATE EVERYTHING
			}
			futureList.clear();
		} catch (ExecutionException ignored) {
			// impossible
		} catch (TimeoutException e) {
			MainRegistry.logger.warn("A packet has taken >50ms to process, discarding {}/{} packets to prevent pausing of main thread.", totalCnt-processedCnt, totalCnt);
			threadPool.getQueue().clear();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // maybe not the best thing but it's gotta be here
		} finally {
			processedCnt = 0;
			totalCnt = 0;
		}
	}
}
