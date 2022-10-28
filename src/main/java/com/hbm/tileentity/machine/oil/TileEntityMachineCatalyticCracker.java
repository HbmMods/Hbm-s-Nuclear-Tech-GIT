package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.lib.Library;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCatalyticCracker extends TileEntity implements IFluidSource, IFluidAcceptor, INBTPacketReceiver, IFluidStandardTransceiver {
	
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list1 = new ArrayList();
	public List<IFluidAcceptor> list2 = new ArrayList();
	public List<IFluidAcceptor> list3 = new ArrayList();
	
	public TileEntityMachineCatalyticCracker() {
		tanks = new FluidTank[5];
		tanks[0] = new FluidTank(Fluids.BITUMEN, 4000, 0);
		tanks[1] = new FluidTank(Fluids.STEAM, 8000, 1);
		tanks[2] = new FluidTank(Fluids.OIL, 4000, 2);
		tanks[3] = new FluidTank(Fluids.PETROLEUM, 4000, 3);
		tanks[4] = new FluidTank(Fluids.SPENTSTEAM, 800, 4);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			setupTanks();
			updateConnections();
			
			if(worldObj.getTotalWorldTime() % 20 == 0)
				crack();
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(tanks[2].getTankType());
				fillFluidInit(tanks[3].getTankType());
				fillFluidInit(tanks[4].getTankType());
				
				for(DirPos pos : getConPos()) {
					for(int i = 2; i <= 4; i++) {
						if(tanks[i].getFill() > 0) this.sendFluid(tanks[i].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					}
				}
				
				NBTTagCompound data = new NBTTagCompound();

				for(int i = 0; i < 5; i++)
					tanks[i].writeToNBT(data, "tank" + i);
				
				INBTPacketReceiver.networkPack(this, data, 50);
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		for(int i = 0; i < 5; i++)
			tanks[i].readFromNBT(nbt, "tank" + i);
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private void crack() {
		
		Pair<FluidStack, FluidStack> quart = RefineryRecipes.getCracking(tanks[0].getTankType());
		
		if(quart != null) {
			
			int left = quart.getKey().fill;
			int right = quart.getValue().fill;
			
			for(int i = 0; i < 2; i++) {
				if(tanks[0].getFill() >= 100 && tanks[1].getFill() >= 100 && hasSpace(left, right)) {
					tanks[0].setFill(tanks[0].getFill() - 100);
					tanks[1].setFill(tanks[1].getFill() - 200);
					tanks[2].setFill(tanks[2].getFill() + left);
					tanks[3].setFill(tanks[3].getFill() + right);
					tanks[4].setFill(tanks[4].getFill() + 2); //LPS has the density of WATER not STEAM (1%!)
				}
			}
		}
	}
	
	private boolean hasSpace(int left, int right) {
		return tanks[2].getFill() + left <= tanks[2].getMaxFill() && tanks[3].getFill() + right <= tanks[3].getMaxFill() && tanks[4].getFill() + 200 <= tanks[4].getMaxFill();
	}
	
	private void setupTanks() {
		
		Pair<FluidStack, FluidStack> quart = RefineryRecipes.getCracking(tanks[0].getTankType());
		
		if(quart != null) {
			tanks[1].setTankType(Fluids.STEAM);
			tanks[2].setTankType(quart.getKey().type);
			tanks[3].setTankType(quart.getValue().type);
			tanks[4].setTankType(Fluids.SPENTSTEAM);
		} else {
			tanks[0].setTankType(Fluids.NONE);
			tanks[1].setTankType(Fluids.NONE);
			tanks[2].setTankType(Fluids.NONE);
			tanks[3].setTankType(Fluids.NONE);
			tanks[4].setTankType(Fluids.NONE);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		for(int i = 0; i < 5; i++)
			tanks[i].readFromNBT(nbt, "tank" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		for(int i = 0; i < 5; i++)
			tanks[i].writeToNBT(nbt, "tank" + i);
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 5 && tanks[index] != null)
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
	public void setTypeForSync(FluidType type, int index) {
		this.tanks[index].setTankType(type);
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
	
	protected DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 4 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 4 + rot.offsetZ * 1, dir),
				new DirPos(xCoord + dir.offsetX * 4 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 4 - rot.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 4 + rot.offsetX * 1, yCoord, zCoord - dir.offsetZ * 4 + rot.offsetZ * 1, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 4 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 4 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX * 3, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 3, rot),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX * 4, yCoord, zCoord + dir.offsetZ * 2 - rot.offsetZ * 4, rot),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX * 3, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * 3, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX * 4, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ * 4, rot.getOpposite())
		};
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
		if(type == tanks[2].getTankType()) return list1;
		if(type == tanks[3].getTankType()) return list2;
		if(type == tanks[4].getTankType()) return list3;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type == tanks[2].getTankType()) list1.clear();
		if(type == tanks[3].getTankType()) list2.clear();
		if(type == tanks[4].getTankType()) list3.clear();
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

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[2], tanks[3], tanks[4]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0], tanks[1]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}
}
