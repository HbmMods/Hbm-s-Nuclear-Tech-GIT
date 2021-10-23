package com.hbm.blocks.generic;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemStack;
// FIXME I'm not sure what's wrong
public class BlockNTMSlab extends BlockSlab
{
	Block single = null;
	public BlockNTMSlab(boolean opaque, Block baseBlock)
	{
		super(opaque, baseBlock.getMaterial());
		setResistance(baseBlock.getExplosionResistance(null) / 2);
		setHardness(10);
		setBlockName(baseBlock.getUnlocalizedName().substring(5).concat("_slab"));
		setBlockTextureName(RefStrings.MODID  + ":" + baseBlock.getUnlocalizedName().substring(5));
		setStepSound(baseBlock.stepSound);
		setCreativeTab(MainRegistry.blockTab);
	}
	
	public BlockNTMSlab(Block singleSlab)
	{
		this(true, singleSlab);
		single = singleSlab;
	}
	
	@Override
	protected ItemStack createStackedBlock(int i)
	{
		return single == null ? new ItemStack(this, 2) : new ItemStack(single, 2);
	}
	
	// ???
	@Override
	public String func_150002_b(int p_150002_1_)
	{
		return null;
	}

}
