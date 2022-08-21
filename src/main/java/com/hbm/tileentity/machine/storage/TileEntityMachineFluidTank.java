package com.hbm.tileentity.machine.storage;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.FluidType.FluidTrait;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineFluidTank extends TileEntityMachineBase implements IFluidContainer, IFluidSource, IFluidAcceptor, IFluidUser {
	
	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
	
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	
	public TileEntityMachineFluidTank() {
		super(6);
		tank = new FluidTank(Fluids.NONE, 256000, 0);
	}

	@Override
	public String getName() {
		return "container.fluidtank";
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			age++;
			
			if(age >= 20)
				age = 0;
			
			if(this.mode == 1 || this.mode == 2) {
				FluidType type = tank.getTankType();
				sendFluid(type, worldObj, xCoord + 2, yCoord, zCoord - 1, Library.POS_X);
				sendFluid(type, worldObj, xCoord + 2, yCoord, zCoord + 1, Library.POS_X);
				sendFluid(type, worldObj, xCoord - 2, yCoord, zCoord - 1, Library.NEG_X);
				sendFluid(type, worldObj, xCoord - 2, yCoord, zCoord + 1, Library.NEG_X);
				sendFluid(type, worldObj, xCoord - 1, yCoord, zCoord + 2, Library.POS_Z);
				sendFluid(type, worldObj, xCoord + 1, yCoord, zCoord + 2, Library.POS_Z);
				sendFluid(type, worldObj, xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z);
				sendFluid(type, worldObj, xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z);
			}
			
			if((mode == 1 || mode == 2) && (age == 9 || age == 19))
				fillFluidInit(tank.getTankType());
			
			tank.loadTank(2, 3, slots);
			tank.setType(0, 1, slots);
			
			if(tank.getFill() > 0) {
				if(tank.getTankType().isAntimatter()) {
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, true);
				}
				
				if(tank.getTankType().traits.contains(FluidTrait.CORROSIVE_2)) {
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
				}
			}
			
			tank.unloadTank(4, 5, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("mode", mode);
			this.networkPack(data, 50);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		
		mode = data.getShort("mode");
	}
	
	public void handleButtonPacket(int value, int meta) {
		
		mode = (short) ((mode + 1) % modes);
		markDirty();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(mode == 2 || mode == 3)
			return 0;
		
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 2, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		mode = nbt.getShort("mode");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", mode);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public long transferFluid(FluidType type, long fluid) {
		long toTransfer = Math.min(getDemand(type), fluid);
		tank.setFill(tank.getFill() + (int) toTransfer);
		return fluid - toTransfer;
	}

	@Override
	public long getDemand(FluidType type) {
		
		if(this.mode == 2 || this.mode == 3)
			return 0;
		
		return type == tank.getTankType() ? tank.getMaxFill() - tank.getFill() : 0;
	}
}
