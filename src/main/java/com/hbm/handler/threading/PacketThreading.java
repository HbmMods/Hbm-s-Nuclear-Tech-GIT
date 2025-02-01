package com.hbm.handler.threading;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PrecompiledPacket;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class PacketThreading {

	public static final String threadPrefix = "NTM-Packet-Thread-";

	public static final ThreadFactory packetThreadFactory = new ThreadFactoryBuilder().setNameFormat(threadPrefix + "%d").build();

	public static final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, packetThreadFactory);

	public static int totalCnt = 0;

	public static long nanoTimeWaited = 0;

	public static final List<Future<?>> futureList = new ArrayList<>();

	public static ReentrantLock lock = new ReentrantLock();

	/**
	 * Sets up thread pool settings during mod initialization.
 	 */
	public static void init() {
		threadPool.setKeepAliveTime(50, TimeUnit.MILLISECONDS);
		if (GeneralConfig.enablePacketThreading) {
			if (GeneralConfig.packetThreadingCoreCount < 0 || GeneralConfig.packetThreadingMaxCount <= 0) {
				MainRegistry.logger.error("0.02_packetThreadingCoreCount < 0 or 0.03_packetThreadingMaxCount is <= 0, defaulting to 1 each.");
				threadPool.setCorePoolSize(1); // beugh
				threadPool.setMaximumPoolSize(1);
			} else if (GeneralConfig.packetThreadingMaxCount > GeneralConfig.packetThreadingCoreCount) {
				MainRegistry.logger.error("0.03_packetThreadingMaxCount is > 0.02_packetThreadingCoreCount, defaulting to 1 each.");
				threadPool.setCorePoolSize(1);
				threadPool.setMaximumPoolSize(1);
			} else {
				threadPool.setCorePoolSize(GeneralConfig.packetThreadingCoreCount);
				threadPool.setMaximumPoolSize(GeneralConfig.packetThreadingMaxCount);
			}
			threadPool.allowCoreThreadTimeOut(false);
		} else {
			threadPool.allowCoreThreadTimeOut(true);
			try {
				lock.lock();
				for (Runnable task : threadPool.getQueue()) {
					task.run(); // Run all tasks async just in-case there *are* tasks left to run.
				}
				clearThreadPoolTasks();
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * Adds a packet to the thread pool to be processed in the future. This is only compatible with the `sendToAllAround` dispatch operation.
	 * @param message Message to process.
	 * @param target TargetPoint to send to.
	 */
	public static void createAllAroundThreadedPacket(IMessage message, TargetPoint target) {
		// `message` can be precompiled or not.
		if(message instanceof PrecompiledPacket)
			((PrecompiledPacket) message).getPreBuf(); // Gets the precompiled buffer, doing nothing if it already exists.
		totalCnt++;

		Runnable task = () -> {
			try {
				lock.lock();
				PacketDispatcher.wrapper.sendToAllAround(message, target);
				if (message instanceof PrecompiledPacket)
					((PrecompiledPacket) message).getPreBuf().release();
			} finally {
				lock.unlock();
			}
		};

		addTask(task);
	}

	/**
	 * Adds a packet to the thread pool to be processed in the future. This is only compatible with the `sendTo` dispatch operation.
	 *
	 * @param message Message to process.
	 * @param player PlayerMP to send to.
	 */
	public static void createSendToThreadedPacket(IMessage message, EntityPlayerMP player) {
		if(message instanceof PrecompiledPacket)
			((PrecompiledPacket) message).getPreBuf();
		totalCnt++;

		Runnable task = () -> {
			try {
				lock.lock();
				PacketDispatcher.wrapper.sendTo(message, player);
				if (message instanceof PrecompiledPacket)
					((PrecompiledPacket) message).getPreBuf().release();
			} finally {
				lock.unlock();
			}
		};

		addTask(task);
	}

	private static void addTask(Runnable task) {
		if(isTriggered())
			task.run();
		else if(GeneralConfig.enablePacketThreading)
			futureList.add(threadPool.submit(task));
		else
			task.run();
	}

	/**
	 * Wait until the packet thread is finished processing.
	 */
	public static void waitUntilThreadFinished() {
		long startTime = System.nanoTime();
		try {
			if (GeneralConfig.enablePacketThreading && (!GeneralConfig.packetThreadingErrorBypass && !hasTriggered)) {
				for (Future<?> future : futureList) {
					nanoTimeWaited = System.nanoTime() - startTime;
					future.get(50, TimeUnit.MILLISECONDS); // I HATE EVERYTHING
					// if(TimeUnit.MILLISECONDS.convert(nanoTimeWaited, TimeUnit.NANOSECONDS) > 50) throw new TimeoutException(); // >50ms total time? timeout? yes sir, ooh rah!
					// this seems to cause big problems with large worlds, never mind...
				}
			}
		} catch (ExecutionException ignored) {
			// impossible
		} catch (TimeoutException e) {
			if(!GeneralConfig.packetThreadingErrorBypass && !hasTriggered)
				MainRegistry.logger.warn("A packet has taken >50ms to process, discarding {}/{} packets to prevent pausing of main thread ({} total futures).", threadPool.getQueue().size(), totalCnt, futureList.size());
			clearThreadPoolTasks();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // maybe not the best thing but it's gotta be here
		} finally {
			futureList.clear();
			if(!threadPool.getQueue().isEmpty()) {
				if(!GeneralConfig.packetThreadingErrorBypass && !hasTriggered)
					MainRegistry.logger.warn("Residual packets in packet threading queue detected, discarding {}/{} packets.", threadPool.getQueue().size(), totalCnt);
				clearThreadPoolTasks();  // Just in case the thread somehow doesn't process all the tasks, we don't want this backing up too far.
			}

			totalCnt = 0;
		}
	}

	public static int clearCnt = 0;

	public static boolean hasTriggered = false;

	public static void clearThreadPoolTasks() {

		if(threadPool.getQueue().isEmpty()) {
			clearCnt = 0;
			return;
		}

		threadPool.getQueue().clear();

		if(!GeneralConfig.packetThreadingErrorBypass && !hasTriggered)
			MainRegistry.logger.warn("Packet work queue cleared forcefully (clear count: {}).", clearCnt);

		clearCnt++;

		if(clearCnt > 5 && !isTriggered()) {
			// If it's been cleared 5 times in a row, something may have gone really wrong.
			// Best case scenario here, the server is lagging terribly, has a bad CPU, or has a poor network connection
			// Worst case scenario, the entire packet thread is dead. (very not good)
			// So just log it with a special message and only once.
			MainRegistry.logger.error(
				"Something has gone wrong and the packet pool has cleared 5 times (or more) in a row. "
					+ "This can indicate that the thread has been killed, suspended, or is otherwise non-functioning. "
					+ "This message will only be logged once, further packet operations will continue on the main thread. "
					+ "If this message is a common occurrence and is *completely expected*, then it can be bypassed permanently by setting "
					+ "the \"0.04_packetThreadingErrorBypass\" config option to true. This can lead to adverse effects, so do this at your own risk. "
					+ "Running \"/ntmpacket resetState\" resets this trigger as a temporary fix."
			);
			hasTriggered = true;
		}
	}

	public static boolean isTriggered() {
		return hasTriggered && !GeneralConfig.packetThreadingErrorBypass;
	}
}
