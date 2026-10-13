package com.hbm.blocks.network.pneumatic;

import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageExporter;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PneumoStorageExporter extends BlockMachineBase {

	public PneumoStorageExporter() {
		super(Material.iron, 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumoStorageExporter();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityPneumoStorageExporter) {
			ItemStack[] slots = ((TileEntityPneumoStorageExporter) te).slots;
			for(int i = 0; i < 9; i++) slots[i] = null;
		}

		super.breakBlock(world, x, y, z, block, meta);
	}
}
