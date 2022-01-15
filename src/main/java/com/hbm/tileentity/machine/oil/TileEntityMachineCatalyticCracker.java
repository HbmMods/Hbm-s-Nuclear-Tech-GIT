package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.lib.Library;
import com.hbm.util.Tuple.Quartet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import scala.actors.threadpool.Arrays;

public class TileEntityMachineCatalyticCracker extends TileEntity implements IFluidSource, IFluidAcceptor {
	
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list1 = new ArrayList();
	public List<IFluidAcceptor> list2 = new ArrayList();
	
	public TileEntityMachineCatalyticCracker() {
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(FluidTypeTheOldOne.BITUMEN, 4000, 0);
		tanks[1] = new FluidTank(FluidTypeTheOldOne.STEAM, 8000, 1);
		tanks[2] = new FluidTank(FluidTypeTheOldOne.OIL, 4000, 2);
		tanks[3] = new FluidTank(FluidTypeTheOldOne.PETROLEUM, 4000, 3);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			setupTanks();
			
			if(worldObj.getTotalWorldTime() % 20 == 0)
				crack();
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(tanks[2].getTankType());
				fillFluidInit(tanks[3].getTankType());
			}
		}
	}
	
	private void crack() {
		
		Quartet<FluidType, FluidType, Integer, Integer> quart = RefineryRecipes.getCracking(tanks[0].getTankType());
		
		if(quart != null) {
			
			int left = quart.getY();
			int right = quart.getZ();
			
			if(tanks[0].getFill() >= 100 && tanks[1].getFill() >= 100 && hasSpace(left, right)) {
				tanks[0].setFill(tanks[0].getFill() - 100);
				tanks[1].setFill(tanks[1].getFill() - 200);
				tanks[2].setFill(tanks[2].getFill() + left);
				tanks[3].setFill(tanks[3].getFill() + right);
			}
		}
	}
	
	private boolean hasSpace(int left, int right) {
		return tanks[2].getFill() + left <= tanks[2].getMaxFill() && tanks[3].getFill() + right <= tanks[3].getMaxFill();
	}
	
	private void setupTanks() {
		
		Quartet<FluidType, FluidType, Integer, Integer> quart = RefineryRecipes.getCracking(tanks[0].getTankType());
		
		if(quart != null) {
			tanks[1].setTankType(FluidTypeTheOldOne.STEAM);
			tanks[2].setTankType(quart.getW());
			tanks[3].setTankType(quart.getX());
		} else {
			tanks[0].setTankType(FluidTypeTheOldOne.NONE);
			tanks[1].setTankType(FluidTypeTheOldOne.NONE);
			tanks[2].setTankType(FluidTypeTheOldOne.NONE);
			tanks[3].setTankType(FluidTypeTheOldOne.NONE);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		for(int i = 0; i < 3; i++)
			tanks[i].readFromNBT(nbt, "tank" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		for(int i = 0; i < 3; i++)
			tanks[i].writeToNBT(nbt, "tank" + i);
	}

	@Override
	public void setFillstate(int fill, int index) {
		if(index < 4 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				tank.setFill(fill);
			}
		}
	}

	@Override
	public void setType(FluidType type, int index) {
		this.tanks[index].setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return Arrays.asList(this.tanks);
	}

	@Override
	public int getFluidFill(FluidType type) {
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				return tank.getFill();
			}
		}
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == tanks[0].getTankType())
			return tanks[0].getMaxFill();
		else if(type == tanks[1].getTankType())
			return tanks[1].getMaxFill();
		else
			return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		fillFluid(xCoord + dir.offsetX * 4 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 4 + rot.offsetZ * 1, this.getTact(), type);
		fillFluid(xCoord + dir.offsetX * 4 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 4 - rot.offsetZ * 2, this.getTact(), type);
		fillFluid(xCoord - dir.offsetX * 4 + rot.offsetX * 1, yCoord, zCoord - dir.offsetZ * 4 + rot.offsetZ * 1, this.getTact(), type);
		fillFluid(xCoord - dir.offsetX * 4 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 4 - rot.offsetZ * 2, this.getTact(), type);

		fillFluid(xCoord + dir.offsetX * 2 + rot.offsetX * 3, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 3, this.getTact(), type);
		fillFluid(xCoord + dir.offsetX * 2 - rot.offsetX * 4, yCoord, zCoord + dir.offsetZ * 2 - rot.offsetZ * 4, this.getTact(), type);
		fillFluid(xCoord - dir.offsetX * 2 + rot.offsetX * 3, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * 3, this.getTact(), type);
		fillFluid(xCoord - dir.offsetX * 2 - rot.offsetX * 4, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ * 4, this.getTact(), type);
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
		if(type.name().equals(tanks[2].getTankType().name()))
			return list1;
		if(type.name().equals(tanks[3].getTankType().name()))
			return list2;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type.name().equals(tanks[2].getTankType().name()))
			list1.clear();
		if(type.name().equals(tanks[3].getTankType().name()))
			list2.clear();
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 16,
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
}
