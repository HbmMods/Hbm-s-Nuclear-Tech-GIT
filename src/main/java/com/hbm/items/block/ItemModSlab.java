package com.hbm.items.block;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ItemModSlab extends ItemSlab {

	public ItemModSlab(Block block) {
		super(block, shittyFuckingHackSingle(block), shittyFuckingHackDouble(block), shittyFuckingHackDouble(block) == block);
	}
	
	public static BlockSlab shittyFuckingHackSingle(Block b) {
		if(b == ModBlocks.concrete_slab || b == ModBlocks.concrete_double_slab) return (BlockSlab) ModBlocks.concrete_slab;
		if(b == ModBlocks.concrete_brick_slab || b == ModBlocks.concrete_brick_double_slab) return (BlockSlab) ModBlocks.concrete_brick_slab;
		if(b == ModBlocks.brick_slab || b == ModBlocks.brick_double_slab) return (BlockSlab) ModBlocks.brick_slab;
		int indexColoredSingle = ModBlocks.concrete_colored_slabs.indexOf(b);
		if(indexColoredSingle >= 0) return (BlockSlab)b;
		int indexColoredDouble = ModBlocks.concrete_colored_double_slabs.indexOf(b);
		if(indexColoredDouble >= 0) return (BlockSlab)ModBlocks.concrete_colored_slabs.get(indexColoredDouble);
		int indexColoredExtSingle = ModBlocks.concrete_colored_ext_slabs.indexOf(b);
		if(indexColoredExtSingle >= 0) return (BlockSlab)b;
		int indexColoredExtDouble = ModBlocks.concrete_colored_ext_double_slabs.indexOf(b);
		if(indexColoredExtDouble >= 0) return (BlockSlab)ModBlocks.concrete_colored_ext_slabs.get(indexColoredExtDouble);
		return null;
	}
	
	public static BlockSlab shittyFuckingHackDouble(Block b) {
		if(b == ModBlocks.concrete_slab || b == ModBlocks.concrete_double_slab) return (BlockSlab) ModBlocks.concrete_double_slab;
		if(b == ModBlocks.concrete_brick_slab || b == ModBlocks.concrete_brick_double_slab) return (BlockSlab) ModBlocks.concrete_brick_double_slab;
		if(b == ModBlocks.brick_slab || b == ModBlocks.brick_double_slab) return (BlockSlab) ModBlocks.brick_double_slab;
		int indexColoredDouble = ModBlocks.concrete_colored_double_slabs.indexOf(b);
		if(indexColoredDouble >= 0) return (BlockSlab)b;
		int indexColoredSingle = ModBlocks.concrete_colored_slabs.indexOf(b);
		if(indexColoredSingle >= 0) return (BlockSlab)ModBlocks.concrete_colored_double_slabs.get(indexColoredSingle);
		int indexColoredExtDouble = ModBlocks.concrete_colored_ext_double_slabs.indexOf(b);
		if(indexColoredExtDouble >= 0) return (BlockSlab)b;
		int indexColoredExtSingle = ModBlocks.concrete_colored_ext_slabs.indexOf(b);
		if(indexColoredExtSingle >= 0) return (BlockSlab)ModBlocks.concrete_colored_ext_double_slabs.get(indexColoredExtSingle);
		return null;
	}
}
