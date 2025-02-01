package com.hbm.tileentity.machine.albion;

import com.hbm.tileentity.machine.albion.TileEntityPASource.Particle;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPABeamline extends TileEntity implements IParticleUser {

	@Override
	public boolean canParticleEnter(Particle particle, ForgeDirection dir, int x, int y, int z) {
		ForgeDirection beamlineDir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.DOWN);
		BlockPos input = new BlockPos(xCoord, yCoord, zCoord).offset(beamlineDir, -1);
		return input.compare(x, y, z) && beamlineDir == dir;
	}

	@Override
	public void onEnter(Particle particle, ForgeDirection dir) { }

	@Override
	public BlockPos getExitPos(Particle particle) {
		ForgeDirection beamlineDir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.DOWN);
		return new BlockPos(xCoord, yCoord, zCoord).offset(beamlineDir, 2);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 1,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
