package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.RedBarrel;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockLore extends ItemBlock {

	public ItemBlockLore(Block p_i45328_1_) {
		super(p_i45328_1_);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		if(this.field_150939_a instanceof RedBarrel) {
			list.add("Static fluid barrel");
		}
		
		if(this.field_150939_a == ModBlocks.barrel_plastic) {
			list.add("Cannot store hot fluids");
			list.add("Cannot store corrosive fluids");
			list.add("Cannot store antimatter");
		}
		
		if(this.field_150939_a == ModBlocks.barrel_iron) {
			list.add("Can store hot fluids");
			list.add("Cannot store corrosive fluids properly");
			list.add("Cannot store antimatter");
		}
		
		if(this.field_150939_a == ModBlocks.barrel_steel) {
			list.add("Can store hot fluids");
			list.add("Can store corrosive fluids");
			list.add("Cannot store antimatter");
		}
		
		if(this.field_150939_a == ModBlocks.barrel_antimatter) {
			list.add("Can store hot fluids");
			list.add("Can store corrosive fluids");
			list.add("Can store antimatter");
		}
		
		if(this.field_150939_a == ModBlocks.meteor_battery) {
			list.add("Provides infinite charge to tesla coils");
		}
		
		if(this.field_150939_a == ModBlocks.ore_oil) {
			list.add("You weren't supposed to mine that.");
			list.add("Come on, get a derrick you doofus.");
		}
		
		if(this.field_150939_a == ModBlocks.block_lithium) {
			list.add("It's not my fault you didn't pay");
			list.add("attention in chemistry class.");
		}
	}

    @Override
	public EnumRarity getRarity(ItemStack stack) {

		if(this.field_150939_a == ModBlocks.ore_schrabidium || this.field_150939_a == ModBlocks.ore_nether_schrabidium ||
				this.field_150939_a == ModBlocks.block_schrabidium || this.field_150939_a == ModBlocks.block_schrabidium_cluster ||
				this.field_150939_a == ModBlocks.block_schrabidium_fuel || this.field_150939_a == ModBlocks.block_solinium)
			return EnumRarity.rare;
		
		if(this.field_150939_a == ModBlocks.block_euphemium || this.field_150939_a == ModBlocks.block_euphemium_cluster ||
				this.field_150939_a == ModBlocks.plasma || this.field_150939_a == ModBlocks.fwatz_plasma)
			return EnumRarity.epic;
    	
    	return EnumRarity.common;
    }

}
