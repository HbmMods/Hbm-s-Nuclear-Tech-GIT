package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityElectrolyser extends TileEntityMachineBase implements IEnergyUser, IFluidSource, IFluidAcceptor /* TODO: new fluid API */ {
	
	public long power;
	public static final long maxPower = 20000000;
	public static final int usageBase = 10000;
	public int usage;
	
	public int progressFluid;
	public static final int processFluidTimeBase = 100;
	public int processFluidTime;
	public int progressOre;
	public static final int processOreTimeBase = 1000;
	public int processOreTime;
	
	public FluidTank[] tanks;

	public TileEntityElectrolyser() {
		super(24);
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(Fluids.WATER, 16000, 0);
		tanks[1] = new FluidTank(Fluids.HYDROGEN, 16000, 1);
		tanks[2] = new FluidTank(Fluids.OXYGEN, 16000, 2);
	}

	@Override
	public String getName() {
		return "container.machineElectrolyser";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			this.tanks[0].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progressFluid", this.progressFluid);
			data.setInteger("progressOre", this.progressOre);
			data.setInteger("usage", this.usage);
			data.setInteger("processFluidTime", this.processFluidTime);
			data.setInteger("processOreTime", this.processOreTime);
			this.networkPack(data, 50);
			
			fillFluidInit(tanks[1].getTankType());
			fillFluidInit(tanks[2].getTankType());
		}

	}
	
	@Override
	public void fillFluidInit(FluidType type) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		fillFluid(xCoord + dir.offsetX * 5 + rot.offsetX * -1, yCoord-1, zCoord + dir.offsetZ * 5 + rot.offsetZ * -1, getTact(), type);
		fillFluid(xCoord + dir.offsetX * 5 + rot.offsetX * -1, yCoord-1, zCoord + dir.offsetZ * 5 + rot.offsetZ * 1, getTact(), type);
		fillFluid(xCoord + dir.offsetX * -5 + rot.offsetX * -1, yCoord-1, zCoord + dir.offsetZ * 5 + rot.offsetZ * -1, getTact(), type);
		fillFluid(xCoord + dir.offsetX * -5 + rot.offsetX * -1, yCoord-1, zCoord + dir.offsetZ * 5 + rot.offsetZ * 1, getTact(), type);

	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord - 0,
					zCoord - 4,
					xCoord + 3,
					yCoord + 4,
					zCoord + 4
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
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tanks[index].setFill(fill);
		
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		for(int i = 0; i < 3; i++) {
			if(type == tanks[i].getTankType())
				tanks[i].setFill(fill);
		}
		
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tanks[index].setTankType(type);
		
	}

	@Override
	public int getFluidFill(FluidType type) {
		for(int i = 0; i < 3; i++) {
			if(type == tanks[i].getTankType() && tanks[i].getFill() != 0)
				return tanks[i].getFill();
		}
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		for(int i = 0; i < 3; i++) {
			if(type == tanks[i].getTankType() && tanks[i].getMaxFill() != 0)
				return tanks[i].getMaxFill();
		}
		return 0;
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 20 < 10;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		return;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

}
