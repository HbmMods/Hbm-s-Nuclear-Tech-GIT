package com.hbm.items.special;

import java.util.List;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemOreBlock extends ItemBlock {

	public ItemOreBlock(Block p_i45328_1_) {
		super(p_i45328_1_);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
    {
		return EnumRarity.uncommon;
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this.field_150939_a == ModBlocks.ore_australium) {
			list.add("Australium ore");
			list.add("Deposit location: X:-400; Z:-400");
			list.add("Estimated quantity: 490");
		}
		
		if(this.field_150939_a == ModBlocks.ore_weidanium) {
			list.add("Weidanium ore");
			list.add("Deposit location: X:0; Z:300");
			list.add("Estimated quantity: 2800");
		}
			
		if(this.field_150939_a == ModBlocks.ore_reiium) {
			list.add("Reiium ore");
			list.add("Deposit location: X:0; Z:0");
			list.add("Estimated quantity: 2800");
		}
			
		if(this.field_150939_a == ModBlocks.ore_unobtainium) {
			list.add("Unobtainium ore");
			list.add("Deposit location: X:200; Z:200");
			list.add("Estimated quantity: 12480");
		}
			
		if(this.field_150939_a == ModBlocks.ore_daffergon) {
			list.add("Daffergon ore");
			list.add("Deposit location: X:400; Z:-200");
			list.add("Estimated quantity: 14980");
		}
			
		if(this.field_150939_a == ModBlocks.ore_verticium) {
			list.add("Verticium ore");
			list.add("Deposit location: X:-300; Z:200");
			list.add("Estimated quantity: 4680");
		}
	}

}
