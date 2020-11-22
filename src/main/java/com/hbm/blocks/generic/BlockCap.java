package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockPillar;
import com.hbm.items.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockCap extends BlockPillar {

	public BlockCap(Material mat, String tex) {
		super(mat, tex);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {

		if(this == ModBlocks.block_cap_nuka)
			return ModItems.cap_nuka;
		if(this == ModBlocks.block_cap_quantum)
			return ModItems.cap_quantum;
		if(this == ModBlocks.block_cap_sparkle)
			return ModItems.cap_sparkle;
		if(this == ModBlocks.block_cap_rad)
			return ModItems.cap_rad;
		if(this == ModBlocks.block_cap_korl)
			return ModItems.cap_korl;
		if(this == ModBlocks.block_cap_fritz)
			return ModItems.cap_fritz;
		if(this == ModBlocks.block_cap_sunset)
			return ModItems.cap_sunset;
		if(this == ModBlocks.block_cap_star)
			return ModItems.cap_star;
		
		return null;
    }
    
    @Override
	public int quantityDropped(Random rand) {
    	return 128;
    }
}
