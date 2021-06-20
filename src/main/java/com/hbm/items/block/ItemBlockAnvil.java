package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.machine.NTMAnvil;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockAnvil extends ItemBlock {
	
	public ItemBlockAnvil(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(stack, player, list, bool);
		
		if(this.field_150939_a instanceof NTMAnvil) {
			list.add(EnumChatFormatting.GOLD + "Tier " + ((NTMAnvil)this.field_150939_a).tier + " Anvil");
		} else {
			list.add("can someone wake bob up and tell him he used ItemBlockAnvil.class on a non-anvil block? thanks.");
		}
	}
}
