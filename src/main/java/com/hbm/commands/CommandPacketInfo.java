package com.hbm.commands;

import com.hbm.config.GeneralConfig;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.main.MainRegistry;
import com.hbm.util.BobMathUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.concurrent.TimeUnit;

import static com.hbm.handler.threading.PacketThreading.totalCnt;

public class CommandPacketInfo extends CommandBase {
	@Override
	public String getCommandName() {
		return "ntmpackets";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return EnumChatFormatting.RED + "/ntmpackets [info/resetState/toggleThreadingStatus/forceLock/forceUnlock]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {

		if (args.length > 0) {
			switch (args[0]) {
				case "resetState":
					PacketThreading.hasTriggered = false;
					PacketThreading.clearCnt = 0;
					return;
				case "toggleThreadingStatus":
					GeneralConfig.enablePacketThreading = !GeneralConfig.enablePacketThreading; // Force toggle.
					PacketThreading.init(); // Reinit threads.
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Packet sending status toggled to " + GeneralConfig.enablePacketThreading + "."));
					return;
				case "forceLock":
					PacketThreading.lock.lock(); // oh my fucking god never do this please unless you really have to
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Packet thread lock acquired, this may freeze the main thread!"));
					MainRegistry.logger.error("Packet thread lock acquired by {}, this may freeze the main thread!", sender.getCommandSenderName());
					return;
				case "forceUnlock":
					PacketThreading.lock.unlock();
					MainRegistry.logger.warn("Packet thread lock released by {}.", sender.getCommandSenderName());
					return;
				case "info":
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "NTM Packet Debugger v1.2"));

					if (PacketThreading.isTriggered() && GeneralConfig.enablePacketThreading)
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Packet Threading Errored, check log."));
					else if (GeneralConfig.enablePacketThreading)
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Packet Threading Active"));
					else
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Packet Threading Inactive"));

					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Thread Pool Info"));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "# Threads (total): " + PacketThreading.threadPool.getPoolSize()));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "# Threads (core): " + PacketThreading.threadPool.getCorePoolSize()));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "# Threads (idle): " + (PacketThreading.threadPool.getPoolSize() - PacketThreading.threadPool.getActiveCount())));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "# Threads (maximum): " + PacketThreading.threadPool.getMaximumPoolSize()));

					for (ThreadInfo thread : ManagementFactory.getThreadMXBean().dumpAllThreads(false, false))
						if (thread.getThreadName().startsWith(PacketThreading.threadPrefix)) {
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Thread Name: " + thread.getThreadName()));
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Thread ID: " + thread.getThreadId()));
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Thread state: " + thread.getThreadState()));
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Locked by: " + (thread.getLockOwnerName() == null ? "None" : thread.getLockName())));
						}

					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Packet Info: "));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Amount total: " + totalCnt));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Amount remaining: " + PacketThreading.threadPool.getQueue().size()));

					if (totalCnt != 0)
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "% Remaining to process: " + BobMathUtil.roundDecimal(((double) PacketThreading.threadPool.getQueue().size() / totalCnt) * 100, 2) + "%"));

					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Time spent waiting on thread(s) last tick: " + BobMathUtil.roundDecimal(TimeUnit.MILLISECONDS.convert(PacketThreading.nanoTimeWaited, TimeUnit.NANOSECONDS), 4) + "ms"));
					return;
			}
		}
		sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
	}
}
