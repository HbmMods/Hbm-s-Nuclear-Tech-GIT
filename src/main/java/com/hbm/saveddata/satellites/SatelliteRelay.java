package com.hbm.saveddata.satellites;

import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SatelliteRelay extends SatelliteBase {
	
	public SatelliteRelay() { }

	@Override public String getType() { return "RX/TX"; }

	public void onOrbit(World world, double x, double y, double z) {

		for(Object p : world.playerEntities)
			((EntityPlayer)p).triggerAchievement(MainRegistry.achFOEQ);
	}
}
