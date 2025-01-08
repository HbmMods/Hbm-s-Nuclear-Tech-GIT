package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineCombustionEngine;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineCombustionEngine extends BlockDummyable {

	public MachineCombustionEngine() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12)
			return new TileEntityMachineCombustionEngine();
		if(hasExtra(meta))
			return new TileEntityProxyCombo().power().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 1, 0, 3, 2};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + rot.offsetX, y, z + rot.offsetZ);
		this.makeExtra(world, x - rot.offsetX, y, z - rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX + rot.offsetX, y, z - dir.offsetZ + rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX - rot.offsetX, y, z - dir.offsetZ - rot.offsetZ);
	}
}
