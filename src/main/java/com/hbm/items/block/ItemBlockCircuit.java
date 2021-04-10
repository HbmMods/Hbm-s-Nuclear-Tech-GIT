package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockCircuit;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockCircuit extends ItemBlock
{
	public ItemBlockCircuit(Block block)
	{
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Tier " + Character.toString(stack.getUnlocalizedName().charAt(24)));
		list.add("Computational Strength: " + ((BlockCircuit)field_150939_a).strength);
		if (this.field_150939_a == ModBlocks.block_circuit_tier_1)
		{
			list.add(EnumChatFormatting.ITALIC + "Cheapo");
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		if (this.field_150939_a == ModBlocks.block_circuit_tier_5)
		{
			return EnumRarity.rare;
		}
		if (this.field_150939_a == ModBlocks.block_circuit_tier_6)
		{
			return EnumRarity.epic;
		}
		return EnumRarity.common;
	}
}
