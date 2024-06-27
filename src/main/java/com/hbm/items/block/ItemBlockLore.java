package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.RedBarrel;

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
			list.add("Static fluid barrel");
		}
		
		if(this.field_150939_a == ModBlocks.meteor_battery) {
			list.add("Provides infinite charge to tesla coils");
		}
		
		if(this.field_150939_a == ModBlocks.ore_oil || this.field_150939_a == ModBlocks.ore_gas) {
			list.add("You weren't supposed to mine that.");
			list.add("Come on, get a derrick you doofus.");
		}
		
		if(this.field_150939_a == ModBlocks.gravel_diamond) {
			list.add("There is some kind of joke here,");
			list.add("but I can't quite tell what it is.");
			list.add("");
			list.add("Update, 2020-07-04:");
			list.add("We deny any implications of a joke on");
			list.add("the basis that it was so severely unfunny");
			list.add("that people started stabbing their eyes out.");
			list.add("");
			list.add("Update, 2020-17-04:");
			list.add("As it turns out, \"Diamond Gravel\" was");
			list.add("never really a thing, rendering what might");
			list.add("have been a joke as totally nonsensical.");
			list.add("We apologize for getting your hopes up with");
			list.add("this non-joke that hasn't been made.");
			list.add("");
			list.add("i added an item for a joke that isn't even here, what am i, stupid? can't even tell the difference between gravel and a gavel, how did i not forget how to breathe yet?");
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		if(this.field_150939_a == ModBlocks.gravel_diamond)
			return EnumRarity.rare;
		
		if(this.field_150939_a == ModBlocks.block_euphemium || this.field_150939_a == ModBlocks.block_euphemium_cluster || this.field_150939_a == ModBlocks.plasma)
			return EnumRarity.epic;

		return EnumRarity.common;
	}

}
