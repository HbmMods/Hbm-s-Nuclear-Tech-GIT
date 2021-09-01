package com.hbm.blocks.machine.pile;

import java.util.ArrayList;

import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.pile.TileEntityPileSource;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGraphiteSource extends BlockGraphiteDrilledTE {

	@Override
	public TileEntity createNewTileEntity(World world, int mets) {
		return new TileEntityPileSource();
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = super.getDrops(world, x, y, z, metadata, fortune);
		drops.add(new ItemStack(ModItems.pile_rod_source));
		return drops;
	}
}
