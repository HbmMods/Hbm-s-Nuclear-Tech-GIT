package com.hbm.items.block;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.NotableComments;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

@NotableComments
public class ItemModSlab extends ItemSlab {

	public ItemModSlab(Block block) {
		super(block, shittyFuckingHackSingle(block), shittyFuckingHackDouble(block), shittyFuckingHackDouble(block) == block);
	}
	
	public static BlockSlab shittyFuckingHackSingle(Block b) {
		if(b == ModBlocks.concrete_slab || b == ModBlocks.concrete_double_slab) return (BlockSlab) ModBlocks.concrete_slab;
		if(b == ModBlocks.concrete_brick_slab || b == ModBlocks.concrete_brick_double_slab) return (BlockSlab) ModBlocks.concrete_brick_slab;
		if(b == ModBlocks.brick_slab || b == ModBlocks.brick_double_slab) return (BlockSlab) ModBlocks.brick_slab;
		if(b == ModBlocks.stones_slab || b == ModBlocks.stones_double_slab) return (BlockSlab) ModBlocks.stones_slab;
		return null;
	}
	
	public static BlockSlab shittyFuckingHackDouble(Block b) {
		if(b == ModBlocks.concrete_slab || b == ModBlocks.concrete_double_slab) return (BlockSlab) ModBlocks.concrete_double_slab;
		if(b == ModBlocks.concrete_brick_slab || b == ModBlocks.concrete_brick_double_slab) return (BlockSlab) ModBlocks.concrete_brick_double_slab;
		if(b == ModBlocks.brick_slab || b == ModBlocks.brick_double_slab) return (BlockSlab) ModBlocks.brick_double_slab;
		if(b == ModBlocks.stones_slab || b == ModBlocks.stones_double_slab) return (BlockSlab) ModBlocks.stones_double_slab;
		return null;
	}
}
