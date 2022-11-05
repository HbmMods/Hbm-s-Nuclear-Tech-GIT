package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySteamEngine extends TileEntityLoadedBase implements IFluidAcceptor, IFluidSource, IEnergyGenerator, IFluidStandardTransceiver {

	public long power;
	public static final long maxPower = 1_000_000L;
	
	public float rotor;
	public float lastRotor;
	public List<IFluidAcceptor> list2 = new ArrayList();
	public FluidTank[] tanks;
	
	public TileEntitySteamEngine() {
		
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.STEAM, 8_000, 0);
		tanks[1] = new FluidTank(Fluids.SPENTSTEAM, 80, 1);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 2 == 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == tanks[0].getTankType())
			tanks[0].setFill(i);
		else if(type == tanks[1].getTankType())
			tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == tanks[0].getTankType())
			return tanks[0].getFill();
		else if(type == tanks[1].getTankType())
			return tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == tanks[0].getTankType())
			return tanks[0].getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list2;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list2.clear();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN && dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}
}
