package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineChemplant extends BlockDummyable {

	public MachineChemplant(Material p_i45386_1_) {
		super(p_i45386_1_);
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.0, 0.0, -2.0, 2.0, 3.0, 1.0));
		
		// some guy once told me not to use magic numbers
		// so I turned him into a newt
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.14375, 0.34375, -2.5, 0.15625, 0.65625, 1.5));
		this.bounding.add(AxisAlignedBB.getBoundingBox(0.85625, 0.34375, -2.5, 1.15625, 0.65625, 1.5));

		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.5, 0.0, -0.5, 0.0, 1.0, 0.5));
		this.bounding.add(AxisAlignedBB.getBoundingBox(1.0, 0.0, -1.5, 2.5, 1.0, -0.5));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineChemplant();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 2, 1, 2, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x -= dir.offsetX;
		z -= dir.offsetZ;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + rot.offsetX * 2,					y,	z + rot.offsetZ * 2);
		this.makeExtra(world, x - rot.offsetX * 1,					y,	z - rot.offsetZ * 1);
		this.makeExtra(world, x + rot.offsetX * 2 - dir.offsetX,	y,	z + rot.offsetZ * 2 - dir.offsetZ);
		this.makeExtra(world, x - rot.offsetX * 1 - dir.offsetX,	y,	z - rot.offsetZ * 1 - dir.offsetZ);
	}
}
