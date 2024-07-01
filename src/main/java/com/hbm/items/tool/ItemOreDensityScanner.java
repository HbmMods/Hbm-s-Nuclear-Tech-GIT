package com.hbm.items.tool;

import com.hbm.items.special.ItemBedrockOreBase;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemOreDensityScanner extends Item {

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		
		if(!(entity instanceof EntityPlayerMP) || world.getTotalWorldTime() % 5 != 0) return;
		
		EntityPlayerMP player = (EntityPlayerMP) entity;
		
		for(BedrockOreType type : BedrockOreType.values()) {
			double level = ItemBedrockOreBase.getOreLevel((int) Math.floor(player.posX), (int) Math.floor(player.posZ), type);
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(
					StatCollector.translateToLocalFormatted("item.bedrock_ore.type." + type.suffix + ".name") + ": " + ((int) (level * 100) / 100D) + " (" + translateDensity(level) + EnumChatFormatting.RESET + ")",
			777 + type.ordinal(), 4000), player);
		}
	}
	
	public static String translateDensity(double density) {
		if(density <= 0.1) return EnumChatFormatting.DARK_RED + "Very poor";
		if(density <= 0.35) return EnumChatFormatting.RED + "Poor";
		if(density <= 0.75) return EnumChatFormatting.GOLD + "Low";
		if(density >= 1.9) return EnumChatFormatting.AQUA + "Excellent";
		if(density >= 1.65) return EnumChatFormatting.BLUE + "Very high";
		if(density >= 1.25) return EnumChatFormatting.GREEN + "High";
		return EnumChatFormatting.YELLOW + "Moderate";
	}
}
