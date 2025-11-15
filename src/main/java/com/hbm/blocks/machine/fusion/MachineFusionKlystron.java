package com.hbm.blocks.machine.fusion;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.fusion.TileEntityFusionKlystron;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineFusionKlystron extends BlockDummyable {

	public MachineFusionKlystron() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFusionKlystron();
		if(meta >= 6) return new TileEntityProxyCombo().power().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 4, 3, 2, 2 };
	}

	@Override
	public int getOffset() {
		return 3;
	}

	@Override
	public boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -3, 4, 3, 1, 1}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {4, -3, 4, 3, 1, 1}, this, dir);
	}
}
