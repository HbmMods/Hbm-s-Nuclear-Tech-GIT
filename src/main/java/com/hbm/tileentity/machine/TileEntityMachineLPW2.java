package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.dim.CelestialBody;

import api.hbm.tile.IPropulsion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineLPW2 extends TileEntity implements IPropulsion {

	private boolean hasRegistered;

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote && CelestialBody.inOrbit(worldObj)) {
			if(!hasRegistered) {
				if(isFacingPrograde()) registerPropulsion();
				hasRegistered = true;
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(hasRegistered) {
			unregisterPropulsion();
			hasRegistered = false;
		}
	}

	public boolean isFacingPrograde() {
		return ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset) == ForgeDirection.SOUTH;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 10, yCoord, zCoord - 10, xCoord + 11, yCoord + 7, zCoord + 11);
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public TileEntity getTileEntity() {
		return this;
	}

	@Override
	public boolean canPerformBurn(int shipMass, int deltaV) {
		return true;
	}

	@Override
	public float getThrust() {
		return 12_000_000_000.0F; // double the F1
	}

	@Override
	public int startBurn() {
		return 50; // 2-3 seconds
	}

	@Override
	public int endBurn() {
		return 200; // Cooldown
	}
}
