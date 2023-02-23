package com.hbm.tileentity.turret;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class TileEntityTurretBrandon extends TileEntityTurretBaseNT {

	@Override
	public long getMaxPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateFiringTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<Integer> getAmmoList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

}
