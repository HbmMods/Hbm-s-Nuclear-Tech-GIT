package com.hbm.blocks.turret;

import com.hbm.tileentity.turret.TileEntityTurretHowardDamaged;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TurretHowardDamaged extends TurretBaseNT {

	public TurretHowardDamaged(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityTurretHowardDamaged();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {
		return false;
	}
}
