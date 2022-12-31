package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineAssemfac;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineAssemfac extends BlockDummyable {

	public MachineAssemfac(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineAssemfac();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 4, 3, 4, 3};
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int getOffset() {
		return 3;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		this.safeRem = true;

		this.makeExtra(world, x + dir.offsetX * 3 + rot.offsetX * 2, y, z + dir.offsetZ * 3 + rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * 3 - rot.offsetX * 3, y, z + dir.offsetZ * 3 - rot.offsetZ * 3);
		this.makeExtra(world, x - dir.offsetX * 4 + rot.offsetX * 2, y, z - dir.offsetZ * 4 + rot.offsetZ * 2);
		this.makeExtra(world, x - dir.offsetX * 4 - rot.offsetX * 3, y, z - dir.offsetZ * 4 - rot.offsetZ * 3);

		this.makeExtra(world, x + rot.offsetX * 3 + dir.offsetX * 2, y, z + rot.offsetZ * 3 + dir.offsetZ * 2);
		this.makeExtra(world, x + rot.offsetX * 3 - dir.offsetX * 3, y, z + rot.offsetZ * 3 - dir.offsetZ * 3);
		this.makeExtra(world, x - rot.offsetX * 4 + dir.offsetX * 2, y, z - rot.offsetZ * 4 + dir.offsetZ * 2);
		this.makeExtra(world, x - rot.offsetX * 4 - dir.offsetX * 3, y, z - rot.offsetZ * 4 - dir.offsetZ * 3);
		
		this.safeRem = false;
	}
}
