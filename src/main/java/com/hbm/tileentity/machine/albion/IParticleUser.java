package com.hbm.tileentity.machine.albion;

import com.hbm.tileentity.machine.albion.TileEntityPASource.Particle;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraftforge.common.util.ForgeDirection;

public interface IParticleUser {

	public boolean canParticleEnter(Particle particle, ForgeDirection dir, int x, int y, int z);
	public void onEnter(Particle particle, ForgeDirection dir);
	public BlockPos getExitPos(Particle particle);
}
