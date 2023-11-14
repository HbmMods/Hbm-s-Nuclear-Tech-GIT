package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.RedBarrel;

import com.hbm.util.I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockLore extends ItemBlockBase {

	public ItemBlockLore(Block p_i45328_1_) {
		super(p_i45328_1_);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(itemstack, player, list, bool);
		
		if(this.field_150939_a instanceof RedBarrel) {
			list.add(I18nUtil.resolveKey("tile.red_barrel.desc"));
		}
		
		if(this.field_150939_a == ModBlocks.meteor_battery) {
			list.add(I18nUtil.resolveKey("tile.meteor_battery.desc"));
		}
		
		if(this.field_150939_a == ModBlocks.ore_oil) {
			for(String s : I18nUtil.resolveKeyArray("tile.ore_oil.desc"))
				list.add(s);
		}
		
		if(this.field_150939_a == ModBlocks.gravel_diamond) {
			for(String s : I18nUtil.resolveKeyArray("tile.gravel_diamond.desc"))
				list.add(s);
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		if(this.field_150939_a == ModBlocks.gravel_diamond)
			return EnumRarity.rare;
		
		if(this.field_150939_a == ModBlocks.block_euphemium || this.field_150939_a == ModBlocks.block_euphemium_cluster ||
				this.field_150939_a == ModBlocks.plasma || this.field_150939_a == ModBlocks.fwatz_plasma)
			return EnumRarity.epic;

		return EnumRarity.common;
	}

}
