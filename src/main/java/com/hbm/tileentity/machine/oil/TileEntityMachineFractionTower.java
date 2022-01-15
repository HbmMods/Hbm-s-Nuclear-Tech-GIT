package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import scala.actors.threadpool.Arrays;

public class TileEntityMachineFractionTower extends TileEntity implements IFluidSource, IFluidAcceptor {
	
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list1 = new ArrayList();
	public List<IFluidAcceptor> list2 = new ArrayList();
	
	public TileEntityMachineFractionTower() {
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(FluidTypeTheOldOne.HEAVYOIL, 4000, 0);
		tanks[1] = new FluidTank(FluidTypeTheOldOne.BITUMEN, 4000, 1);
		tanks[2] = new FluidTank(FluidTypeTheOldOne.SMEAR, 4000, 2);
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			TileEntity stack = worldObj.getTileEntity(xCoord, yCoord + 3, zCoord);
			
			
			if(stack instanceof TileEntityMachineFractionTower) {
				TileEntityMachineFractionTower frac = (TileEntityMachineFractionTower) stack;
				
				//make types equal
				for(int i = 0; i < 3; i++) {
					frac.tanks[i].setTankType(tanks[i].getTankType());
				}
				
				//calculate transfer
				int oil = Math.min(tanks[0].getFill(), frac.tanks[0].getMaxFill() - frac.tanks[0].getFill());
				int left = Math.min(frac.tanks[1].getFill(), tanks[1].getMaxFill() - tanks[1].getFill());
				int right = Math.min(frac.tanks[2].getFill(), tanks[2].getMaxFill() - tanks[2].getFill());
				
				//move oil up, pull fractions down
				tanks[0].setFill(tanks[0].getFill() - oil);
				tanks[1].setFill(tanks[1].getFill() + left);
				tanks[2].setFill(tanks[2].getFill() + right);
				frac.tanks[0].setFill(frac.tanks[0].getFill() + oil);
				frac.tanks[1].setFill(frac.tanks[1].getFill() - left);
				frac.tanks[2].setFill(frac.tanks[2].getFill() - right);
			}
			
			setupTanks();
			
			if(worldObj.getTotalWorldTime() % 20 == 0)
				fractionate();
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(tanks[1].getTankType());
				fillFluidInit(tanks[2].getTankType());
			}
		}
	}
	
	private void setupTanks() {
		
		Quartet<FluidType, FluidType, Integer, Integer> quart = RefineryRecipes.getFractions(tanks[0].getTankType());
		
		if(quart != null) {
			tanks[1].setTankType(quart.getW());
			tanks[2].setTankType(quart.getX());
		} else {
			tanks[0].setTankType(FluidTypeTheOldOne.NONE);
			tanks[1].setTankType(FluidTypeTheOldOne.NONE);
			tanks[2].setTankType(FluidTypeTheOldOne.NONE);
		}
	}
	
	private void fractionate() {
		
		Quartet<FluidType, FluidType, Integer, Integer> quart = RefineryRecipes.getFractions(tanks[0].getTankType());
		
		if(quart != null) {
			
			int left = quart.getY();
			int right = quart.getZ();
			
			if(tanks[0].getFill() >= 100 && hasSpace(left, right)) {
				tanks[0].setFill(tanks[0].getFill() - 100);
				tanks[1].setFill(tanks[1].getFill() + left);
				tanks[2].setFill(tanks[2].getFill() + right);
			}
		}
	}
	
	private boolean hasSpace(int left, int right) {
		return tanks[1].getFill() + left <= tanks[1].getMaxFill() && tanks[2].getFill() + right <= tanks[2].getMaxFill();
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
		if(index < 3 && tanks[index] != null)
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
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else
			return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		for(int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			fillFluid(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2, this.getTact(), type);
		}
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
		if(type.name().equals(tanks[1].getTankType().name()))
			return list1;
		if(type.name().equals(tanks[2].getTankType().name()))
			return list2;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type.name().equals(tanks[1].getTankType().name()))
			list1.clear();
		if(type.name().equals(tanks[2].getTankType().name()))
			list2.clear();
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
					yCoord + 3,
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
