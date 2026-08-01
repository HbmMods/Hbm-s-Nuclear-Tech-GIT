package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineRockMill;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineRockMill extends BlockDummyable {

	public MachineRockMill(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineRockMill();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override public int[] getDimensions() { return new int[] {2, 0, 2, 2, 2, 2}; }
	@Override public int getOffset() { return 2; }

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		this.makeExtra(world, x + 2, y, z + 1);
		this.makeExtra(world, x - 2, y, z + 1);
		this.makeExtra(world, x + 2, y, z - 1);
		this.makeExtra(world, x - 2, y, z - 1);
		this.makeExtra(world, x + 1, y, z + 2);
		this.makeExtra(world, x + 1, y, z - 2);
		this.makeExtra(world, x - 1, y, z + 2);
		this.makeExtra(world, x - 1, y, z - 2);
	}
}
