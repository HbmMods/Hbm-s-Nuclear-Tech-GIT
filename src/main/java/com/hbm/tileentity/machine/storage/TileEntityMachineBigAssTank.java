package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

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

	@Override public long getReceiverSpeed(FluidType type, int pressure) { return Math.max(50_000, (tank.getMaxFill() - tank.getFill()) / 100); }
	@Override public long getProviderSpeed(FluidType type, int pressure) { return Math.max(50_000, tank.getFill() / 100); }

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
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 7, yCoord, zCoord + dir.offsetZ * 7, dir),
				new DirPos(xCoord - dir.offsetX * 7, yCoord, zCoord - dir.offsetZ * 7, dir.getOpposite())
		};
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
