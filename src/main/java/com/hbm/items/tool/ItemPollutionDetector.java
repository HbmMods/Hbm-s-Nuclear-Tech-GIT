package com.hbm.items.tool;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionData;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.ChatBuilder;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemPollutionDetector extends Item {

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		
		if(!(entity instanceof EntityPlayerMP) || world.getTotalWorldTime() % 10 != 0) return;
		
		PollutionData data = PollutionHandler.getPollutionData(world, (int) Math.floor(entity.posX), (int) Math.floor(entity.posY), (int) Math.floor(entity.posZ));
		if(data == null) data = new PollutionData();
		
		PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("Soot: " + data.pollution[PollutionType.SOOT.ordinal()]).color(EnumChatFormatting.YELLOW).flush(), 100, 2000), (EntityPlayerMP) entity);
		PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("Poison: " + data.pollution[PollutionType.POISON.ordinal()]).color(EnumChatFormatting.YELLOW).flush(), 101, 2000), (EntityPlayerMP) entity);
		PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("Heavy metal: " + data.pollution[PollutionType.HEAVYMETAL.ordinal()]).color(EnumChatFormatting.YELLOW).flush(), 102, 2000), (EntityPlayerMP) entity);
		PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("Fallout: " + data.pollution[PollutionType.FALLOUT.ordinal()]).color(EnumChatFormatting.YELLOW).flush(), 103, 2000), (EntityPlayerMP) entity);
	}
}
