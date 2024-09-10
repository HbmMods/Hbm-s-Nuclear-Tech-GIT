package com.hbm.items.tool;

import com.hbm.items.special.ItemBedrockOreBase;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.PlayerInformPacket;
import com.hbm.util.ChatBuilder;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemOreDensityScanner extends Item {

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		
		if(!(entity instanceof EntityPlayerMP) || world.getTotalWorldTime() % 5 != 0) return;
		
		EntityPlayerMP player = (EntityPlayerMP) entity;
		
		for(BedrockOreType type : BedrockOreType.values()) {
			double level = ItemBedrockOreBase.getOreLevel((int) Math.floor(player.posX), (int) Math.floor(player.posZ), type);
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(
					ChatBuilder.startTranslation("item.bedrock_ore.type." + type.suffix + ".name")
					.next(": " + ((int) (level * 100) / 100D) + " (")
					.nextTranslation(translateDensity(level)).color(getColor(level))
					.next(")").color(EnumChatFormatting.RESET).flush(),
			777 + type.ordinal(), 4000), player);
		}
	}
	
	public static String translateDensity(double density) {
		if(density <= 0.1) return "item.ore_density_scanner.verypoor";
		if(density <= 0.35) return "item.ore_density_scanner.poor";
		if(density <= 0.75) return "item.ore_density_scanner.low";
		if(density >= 1.9) return "item.ore_density_scanner.excellent";
		if(density >= 1.65) return "item.ore_density_scanner.veryhigh";
		if(density >= 1.25) return "item.ore_density_scanner.high";
		return "item.ore_density_scanner.moderate";
	}
	
	public static EnumChatFormatting getColor(double density) {
		if(density <= 0.1) return EnumChatFormatting.DARK_RED;
		if(density <= 0.35) return EnumChatFormatting.RED;
		if(density <= 0.75) return EnumChatFormatting.GOLD;
		if(density >= 1.9) return EnumChatFormatting.AQUA;
		if(density >= 1.65) return EnumChatFormatting.BLUE;
		if(density >= 1.25) return EnumChatFormatting.GREEN;
		return EnumChatFormatting.YELLOW;
	}
}
