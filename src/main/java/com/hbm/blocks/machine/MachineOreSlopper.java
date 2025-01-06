package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineOreSlopper;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineOreSlopper extends BlockDummyable {

	public MachineOreSlopper() {
		super(Material.iron);
		
		//Base
		this.bounding.add(AxisAlignedBB.getBoundingBox(-3.5, 0, -1.5, 3.5, 1, 1.5));
		//Slop bucket
		this.bounding.add(AxisAlignedBB.getBoundingBox(0.5, 1, -1.5, 3.5, 3.25, 1.5));
		//Shredder
		this.bounding.add(AxisAlignedBB.getBoundingBox(-2.25, 1, -1.5, 0.25, 3.25, -0.75));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-2.25, 1, 0.75, 0.25, 3.25, 1.5));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-2.25, 1, -1.5, -2, 3.25, 1.5));
		this.bounding.add(AxisAlignedBB.getBoundingBox(0, 1, -1.5, 0.25, 3.25, 1.5));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-2, 1, -0.75, 0, 2, 0.75));
		//Outlet
		this.bounding.add(AxisAlignedBB.getBoundingBox(-3.25, 1, -1, -2.25, 3, 1));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineOreSlopper();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 3, 3, 1, 1};
	}

	@Override
	public int getOffset() {
		return 3;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, side);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + dir.offsetX * 3, y, z + dir.offsetZ * 3);
		this.makeExtra(world, x - dir.offsetX * 3, y, z - dir.offsetZ * 3);
		this.makeExtra(world, x + rot.offsetX, y, z + rot.offsetZ);
		this.makeExtra(world, x - rot.offsetX, y, z - rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 2 + rot.offsetX, y, z + dir.offsetZ * 2 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 2 - rot.offsetX, y, z + dir.offsetZ * 2 - rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 2 + rot.offsetX, y, z - dir.offsetZ * 2 + rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 2 - rot.offsetX, y, z - dir.offsetZ * 2 - rot.offsetZ);
	}
}
