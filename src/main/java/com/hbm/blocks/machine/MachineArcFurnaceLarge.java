package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineArcFurnaceLarge extends BlockDummyable {

	public MachineArcFurnaceLarge() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineArcFurnaceLarge();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 2, 2, 2, 2};
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y, z + dir.offsetZ * o, new int[] {4, 0, 3, -2, 1, 1}, this, dir);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		if(!super.checkRequirement(world, x, y, z, dir, o)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y, z + dir.offsetZ * o, new int[] {4, 0, 3, -2, 1, 1}, x, y, z, dir)) return false;
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return super.standardOpenBehavior(world, x, y, z, player, 0);
	}
}
