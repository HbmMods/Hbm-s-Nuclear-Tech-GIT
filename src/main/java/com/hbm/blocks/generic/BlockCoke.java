package com.hbm.blocks.generic;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.items.ItemEnums;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCoke extends BlockEnumMulti {

	public BlockCoke() {
		super(Material.iron, ItemEnums.EnumCokeType.class, true, true);
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 10;
	}
}
