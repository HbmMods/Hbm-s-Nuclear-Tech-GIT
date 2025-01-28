package com.hbm.blocks.generic;

import java.util.ArrayList;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.BlockEnums;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ItemEnums.EnumChunkType;
import com.hbm.items.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockResourceStone extends BlockEnumMulti {

	public BlockResourceStone() {
		super(Material.rock, BlockEnums.EnumStoneType.class, true, true);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
		
		if(meta == BlockEnums.EnumStoneType.ASBESTOS.ordinal()) {
			world.setBlock(x, y, z, ModBlocks.gas_asbestos);
		}
		
		super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		
		if(meta == BlockEnums.EnumStoneType.MALACHITE.ordinal()) {
			ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
			ret.add(DictFrame.fromOne(ModItems.chunk_ore, EnumChunkType.MALACHITE, 3 + fortune + world.rand.nextInt(fortune + 2)));
			return ret;
		}
		
		return super.getDrops(world, x, y, z, meta, fortune);
	}
}
