package com.hbm.blocks.turret;

import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.turret.TileEntityTurretHoward;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TurretHoward extends TurretBaseNT {

	public TurretHoward(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityTurretHoward();
		return new TileEntityProxyCombo().inventory().power();
	}
}
