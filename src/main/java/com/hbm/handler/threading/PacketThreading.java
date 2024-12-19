package com.hbm.handler.threading;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hbm.config.GeneralConfig;
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

	public static final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, packetThreadFactory);

	public static int totalCnt = 0;

	public static int processedCnt = 0;

	public static final List<Future<?>> futureList = new ArrayList<>();

	/**
	 * Sets up thread pool settings during mod initialization.
 	 */
	public static void init() {
		threadPool.setKeepAliveTime(50, TimeUnit.MILLISECONDS);
		threadPool.allowCoreThreadTimeOut(true);
	}

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

		Runnable task = () -> {
			PacketDispatcher.wrapper.sendToAllAround(message, target);
		if(message instanceof PrecompiledPacket)
			((PrecompiledPacket) message).getPreBuf().release();
		processedCnt++;
		};

		if(GeneralConfig.enablePacketThreading)
			futureList.add(threadPool.submit(task)); // Thread it
		else
			task.run(); // no threading :(
	}

	/**
	 * Wait until the packet thread is finished processing.
	 */
	public static void waitUntilThreadFinished() {
		try {
			if (!(processedCnt >= totalCnt) && !GeneralConfig.enablePacketThreading) {
				for (Future<?> future : futureList) {
					future.get(50, TimeUnit.MILLISECONDS); // I HATE EVERYTHING
				}
			}
		} catch (ExecutionException ignored) {
			// impossible
		} catch (TimeoutException e) {
			MainRegistry.logger.warn("A packet has taken >50ms to process, discarding {}/{} packets to prevent pausing of main thread ({} total futures).", totalCnt-processedCnt, totalCnt, futureList.size());
			threadPool.getQueue().clear();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // maybe not the best thing but it's gotta be here
		} finally {
			futureList.clear();
			processedCnt = 0;
			totalCnt = 0;
		}
	}
}
