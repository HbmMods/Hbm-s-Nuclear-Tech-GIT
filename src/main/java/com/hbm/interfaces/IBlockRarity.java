package com.hbm.interfaces;

import com.hbm.items.block.ItemBlockLore;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;

public interface IBlockRarity
{
	public default Block setRarity(EnumRarity rarity)
	{
		ItemBlockLore.formattingMap.put((Block) this, rarity);
		return (Block) this;
	}
}
