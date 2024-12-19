package com.hbm.commands;

import com.hbm.config.GeneralConfig;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.util.BobMathUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import static com.hbm.handler.threading.PacketThreading.processedCnt;
import static com.hbm.handler.threading.PacketThreading.totalCnt;

public class CommandPacketInfo extends CommandBase {
	@Override
	public String getCommandName() {
		return "ntmpackets";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmpackets <infoall/threadpool/packets>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 1 && "infoall".equals(args[0])) {
			sender.addChatMessage(new ChatComponentText("NTM Packet Debugger"));

			if(GeneralConfig.enablePacketThreading)
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Packet Threading Active"));
			else
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Packet Threading Inactive"));

			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Thread Pool Info"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Thread pool size: " + PacketThreading.threadPool.getPoolSize()));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Thread pool queue: " + PacketThreading.threadPool.getQueue().size()));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Packet Info: " + PacketThreading.threadPool.getPoolSize()));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Amount Processed: " + processedCnt));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Amount Remaining: " + totalCnt));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "% Processed: " + BobMathUtil.roundDecimal(((double) processedCnt / totalCnt) * 100, 2)));
			return;
		}

		if(args.length == 1 && "threadpool".equals(args[0])) {
			sender.addChatMessage(new ChatComponentText("NTM Packet Debugger"));

			if(GeneralConfig.enablePacketThreading)
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Packet Threading Active"));
			else
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Packet Threading Inactive"));

			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Thread Pool Info"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Thread pool size: " + PacketThreading.threadPool.getPoolSize()));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Thread pool queue: " + PacketThreading.threadPool.getQueue().size()));
			return;
		}

		if(args.length == 1 && "packets".equals(args[0])) {
			sender.addChatMessage(new ChatComponentText("NTM Packet Debugger"));

			if(GeneralConfig.enablePacketThreading)
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Packet Threading Active"));
			else
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Packet Threading Inactive"));

			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Packet Info: " + PacketThreading.threadPool.getPoolSize()));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Amount Processed: " + processedCnt));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Amount Remaining: " + totalCnt));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "% Processed: " + BobMathUtil.roundDecimal(((double) processedCnt / totalCnt) * 100, 2)));

			return;
		}

		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + getCommandUsage(sender)));
	}
}
