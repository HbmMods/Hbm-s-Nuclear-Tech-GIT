package com.hbm.blocks.generic;

import com.hbm.items.block.ItemBlockLore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockGeneric extends Block {

	public BlockGeneric(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	public BlockGeneric addEpic()
	{
		ItemBlockLore.epicList.add(this);
		return this;
	}
	
	public BlockGeneric addRare()
	{
		ItemBlockLore.rareList.add(this);
		return this;
	}
	
	public BlockGeneric addUncommon()
	{
		ItemBlockLore.uncommonList.add(this);
		return this;
	}
}
