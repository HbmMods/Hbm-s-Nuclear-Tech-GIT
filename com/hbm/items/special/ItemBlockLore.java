package com.hbm.items.special;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.RedBarrel;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
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
	}

}
