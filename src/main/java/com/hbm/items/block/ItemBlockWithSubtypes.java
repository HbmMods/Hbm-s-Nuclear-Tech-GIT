package com.hbm.items.block;

import com.hbm.blocks.generic.BlockWithSubtypes;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockWithSubtypes extends ItemBlockLore
{
	private boolean hasCustomNames;
	private int setSize;
	private String[] names;
	public ItemBlockWithSubtypes(Block blockIn)
	{
		super(blockIn);
		setMaxDamage(0);
		setHasSubtypes(true);
		BlockWithSubtypes b = (BlockWithSubtypes) blockIn;
		hasCustomNames = b.hasCustomNames;
		setSize = b.getSetSize();
		names = b.hasCustomNames ? b.getNames() : null;
	}
	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return hasCustomNames ? "tile.".concat(names[stack.getItemDamage()]) : String.format("%s_%s", super.getUnlocalizedName(), stack.getItemDamage());
	}
}
