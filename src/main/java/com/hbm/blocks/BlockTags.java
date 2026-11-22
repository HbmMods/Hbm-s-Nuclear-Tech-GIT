package com.hbm.blocks;

import java.util.HashMap;
import java.util.Locale;

import com.hbm.util.i18n.I18nUtil;

import static com.hbm.blocks.ModBlocks.*;
import static com.hbm.blocks.BlockTags.EnumBlockTag.*;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class BlockTags {
	
	public static HashMap<Item, EnumBlockTag[]> tagMap = new HashMap();

	public static void init() {

		registerTags(heater_firebox, HEAT_SOURCE, POLLUTING, EXHAUST_COMPATIBLE);
		registerTags(heater_oilburner, HEAT_SOURCE, POLLUTING, EXHAUST_COMPATIBLE);
		registerTags(heater_electric, HEAT_SOURCE);
		registerTags(heater_heatex, HEAT_SOURCE);
	}
	
	public static void registerTags(Block machine, EnumBlockTag... tags) {
		tagMap.put(Item.getItemFromBlock(machine), tags);
	}
	
	public static enum EnumBlockTag {

		HEAT_SOURCE(EnumChatFormatting.RED),
		POLLUTING(EnumChatFormatting.GRAY),
		EXHAUST_COMPATIBLE(EnumChatFormatting.GRAY);
		
		public final EnumChatFormatting color;
		public final String unlocalizedName;
		
		private EnumBlockTag(EnumChatFormatting color) {
			this.color = color;
			this.unlocalizedName = "blocktag." + this.name().toLowerCase(Locale.US);
		}
		
		public String getUnlocalizedName() {
			return unlocalizedName;
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void drawTooltip(ItemTooltipEvent event) {
		
		ItemStack stack = event.itemStack;
		Item item = stack.getItem();
		EnumBlockTag[] tags = tagMap.get(item);
		if(tags != null) {
			for(EnumBlockTag tag : tags) {
				event.toolTip.add(tag.color + I18nUtil.resolveKey(tag.getUnlocalizedName()));
			}
		}
	}
}
