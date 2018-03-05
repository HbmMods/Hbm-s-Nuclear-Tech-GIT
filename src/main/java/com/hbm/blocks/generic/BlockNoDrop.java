package com.hbm.blocks.generic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockNoDrop extends Block {

	public BlockNoDrop(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		return null;
    }
}
