package com.hbm.blocks.machine.fusion;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.fusion.TileEntityFusionMHDT;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineFusionMHDT extends BlockDummyable {

	public MachineFusionMHDT() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFusionMHDT();
		if(meta >= 6) return new TileEntityProxyCombo().power().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 2, 0, 6, 7, 2, 2 };
	}

	@Override
	public int getOffset() {
		return 7;
	}

	@Override
	public boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -2, 6, 2, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -2, -6, 7, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -2, -3, 5, 2, 2}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -3, -3, 5, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * (o + 3), y, z + dir.offsetZ * (o + 3), new int[] {1, 0, 0, 1, 3, 3}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {3, -2, 6, 2, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {3, -2, -6, 7, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {3, -2, -3, 5, 2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {4, -3, -3, 5, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * (o + 3), y, z + dir.offsetZ * (o + 3), new int[] {1, 0, 0, 1, 3, 3}, this, dir);
	}
}
