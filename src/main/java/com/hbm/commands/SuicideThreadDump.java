package com.hbm.commands;

import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;

import org.apache.logging.log4j.Level;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;

public class SuicideThreadDump extends CommandBase {

	public static void register() {
		if(FMLLaunchHandler.side() != Side.CLIENT) return;
		ClientCommandHandler.instance.registerCommand(new SuicideThreadDump());
	}

	@Override
	public String getCommandName() {
		return "dumpthreadsandcrashgame";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/dumpthreadsandcrashgame [dump/crash]";
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender instanceof EntityPlayer;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if(args.length != 1 || !(args[0].equals("dump") || args[0].equals("crash"))) {
			throw new CommandException("Requires argument \"dump\" or \"crash\"!");
		}
		
		ThreadInfo[] threads = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
		
		for(ThreadInfo thread : threads) {
			dumpThread(thread);
		}
		
		if(args[0].equals("crash")) {
			FMLCommonHandler.instance().exitJava(0, true);
		}
	}
	
	private static void dumpThread(ThreadInfo info) {

		MainRegistry.logger.log(Level.FATAL, "===========================================");
		MainRegistry.logger.log(Level.FATAL, "Thread: " + info.getThreadName() + " PID: " + info.getThreadId());
		MainRegistry.logger.log(Level.FATAL, "Suspended: " + info.isSuspended());
		MainRegistry.logger.log(Level.FATAL, "Blocked: " + info.getBlockedTime() + "ms, " + info.getBlockedCount() + "x");
		MainRegistry.logger.log(Level.FATAL, "Runs Native: " + info.isInNative());
		MainRegistry.logger.log(Level.FATAL, "State: " + info.getThreadState().name());
		MainRegistry.logger.log(Level.FATAL, "-------------------------------------------");
		
		if(info.getLockedMonitors().length != 0) {
			MainRegistry.logger.log(Level.FATAL, "Following locks found:");
			for(MonitorInfo monitor : info.getLockedMonitors()) {
				MainRegistry.logger.log(Level.FATAL, "- " + monitor.getLockedStackFrame());
			}
			MainRegistry.logger.log(Level.FATAL, "-------------------------------------------");
		}
		
		MainRegistry.logger.log(Level.FATAL, "Stacktrace:");
		for(StackTraceElement line : info.getStackTrace()) {
			MainRegistry.logger.log(Level.FATAL, "- " + line);
		}
		MainRegistry.logger.log(Level.FATAL, "===========================================");
	}
}
