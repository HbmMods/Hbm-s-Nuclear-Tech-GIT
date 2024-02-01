package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineHydrotreater;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineHydrotreater extends BlockDummyable {

	public MachineHydrotreater(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineHydrotreater();
		if(meta >= 6) return new TileEntityProxyCombo().fluid().power();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, side);
	}

	@Override public int[] getDimensions() { return new int[] {6, 0, 1, 1, 1, 1}; }
	@Override public int getOffset() { return 1; }

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		this.makeExtra(world, x - dir.offsetX + 1, y, z - dir.offsetZ + 1);
		this.makeExtra(world, x - dir.offsetX + 1, y, z - dir.offsetZ - 1);
		this.makeExtra(world, x - dir.offsetX - 1, y, z - dir.offsetZ + 1);
		this.makeExtra(world, x - dir.offsetX - 1, y, z - dir.offsetZ - 1);
	}
}
