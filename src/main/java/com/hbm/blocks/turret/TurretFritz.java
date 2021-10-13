package com.hbm.blocks.turret;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.turret.TileEntityTurretFritz;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TurretFritz extends TurretBaseNT {

	public TurretFritz(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityTurretFritz();
		return new TileEntityProxyCombo(true, true, true);
	}

	@Override
	public void openGUI(World world, EntityPlayer player, int x, int y, int z) {
		FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_fritz, world, x, y, z);
	}
}
