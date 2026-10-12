package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TilePort.PortDef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDeuteriumTower extends TileEntityDeuteriumExtractor {

	public TileEntityDeuteriumTower() {
		super();
		tanks[0] = new FluidTank(Fluids.WATER, 50000);
		tanks[1] = new FluidTank(Fluids.HEAVYWATER, 5000);
	}

	@Override
	public PortDef[] getPorts() {
		if(cachedPorts == null) {
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
			
			cachedPorts = new PortDef[] {
					PortDef.make(xCoord - dir.offsetX, yCoord, zCoord - dir.offsetZ, dir.getOpposite(), rot.getOpposite()),
					PortDef.make(xCoord - dir.offsetX + rot.offsetX, yCoord, zCoord - dir.offsetZ + rot.offsetZ, dir.getOpposite(), rot),
					PortDef.make(xCoord, yCoord, zCoord, rot.getOpposite(), dir),
					PortDef.make(xCoord + rot.offsetX, yCoord, zCoord  + rot.offsetZ, dir, rot),
			};
		}
		return cachedPorts;
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
					yCoord + 10,
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

	@Override
	public long getMaxPower() {
		return 100_000;
	}
}