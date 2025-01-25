package com.hbm.tileentity.machine.albion;

import com.hbm.tileentity.machine.albion.TileEntityPASource.Particle;

import net.minecraftforge.common.util.ForgeDirection;

public interface IParticleUser {

	public boolean canParticleEnter(Particle particle, ForgeDirection dir);
	public void onEnter(Particle particle, ForgeDirection dir);
}
