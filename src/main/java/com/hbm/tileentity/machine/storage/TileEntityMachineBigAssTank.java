package com.hbm.tileentity.machine.storage;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineBigAssTank extends TileEntityBarrel {

	public TileEntityMachineBigAssTank() {
		super(16_000_000);
	}
	
	@Override
	public String getName() {
		return "container.bigAssTank";
	}

	@Override public long getReceiverSpeed(FluidType type, int pressure) { return (mode == 0 || mode == 1) ? Math.max(50_000, (tank.getMaxFill() - tank.getFill()) / 100) : 0; }
	@Override public long getProviderSpeed(FluidType type, int pressure) { return (mode == 1 || mode == 2) ? Math.max(50_000, tank.getFill() / 100) : 0; }

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.checkTilt(TiltType.UNAVOIDABLE, true);
		}
		
		super.updateEntity();
	}
	
	@Override public int getFloorCount() { return 4 * 4; }
	@Override public BlockPos getFloorPosFromIndex(int index) { return this.standardFloor7x7(index); }
	
	@Override
	public void checkFluidInteraction() {
		
		if(tank.getTankType().isAntimatter()) {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 10, true, true);
		}
	}
	
	@Override
	public PortDef[] getPorts() {
		if(cachedPorts == null) {
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			cachedPorts = new PortDef[] {
					PortDef.make(xCoord + dir.offsetX * 6, yCoord, zCoord + dir.offsetZ * 6, dir),
					PortDef.make(xCoord - dir.offsetX * 6, yCoord, zCoord - dir.offsetZ * 6, dir.getOpposite())
			};
		}
		return cachedPorts;
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 6,
					yCoord,
					zCoord - 6,
					xCoord + 7,
					yCoord + 5,
					zCoord + 7
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
