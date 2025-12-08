package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineAnnihilator;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineAnnihilator extends BlockDummyable {

	public MachineAnnihilator() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineAnnihilator();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, side);
	}

	@Override public int[] getDimensions() { return new int[] {2, 0, 4, 4, 1, 1}; }
	@Override public int getOffset() { return 4; }

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) && 
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * (o - 3), y, z + dir.offsetZ * (o - 3), new int[] {8, -2, 1, 1, 1, 1}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * (o - 3), y, z + dir.offsetZ * (o - 3), new int[] {8, -2, 1, 1, 1, 1}, this, dir);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + dir.offsetX * 3 + rot.offsetX, y, z + dir.offsetZ * 3 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 3 - rot.offsetX, y, z + dir.offsetZ * 3 - rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 4, y, z + dir.offsetZ * 4);
	}
}
