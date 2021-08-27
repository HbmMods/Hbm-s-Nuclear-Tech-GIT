package com.hbm.blocks.generic;

import com.hbm.interfaces.IBlockRadShield;
import com.hbm.items.block.ItemBlockLore;
import com.hbm.modules.BlockRadShieldModule;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockGeneric extends Block implements IBlockRadShield
{
	BlockRadShieldModule sModule;
	public BlockGeneric(Material p_i45394_1_) {
		super(p_i45394_1_);
		sModule = new BlockRadShieldModule();
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

	@Override
	public BlockRadShieldModule getShieldModule()
	{
		return sModule;
	}
}
