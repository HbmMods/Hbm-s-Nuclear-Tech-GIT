package com.hbm.blocks.turret;

import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.turret.TileEntityTurretMaxwell;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TurretMaxwell extends TurretBaseNT {

	public TurretMaxwell(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityTurretMaxwell();
		return new TileEntityProxyCombo().inventory().power();
	}
}