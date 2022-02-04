package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.SolidificationRecipes;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Pair;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineSolidifier extends TileEntityMachineBase implements IEnergyUser, IFluidAcceptor {

	public long power;
	public static final long maxPower = 100000;
	public static final int usage = 500;
	public int progress;
	public static final int processTime = 200;
	
	public FluidTank tank;

	public TileEntityMachineSolidifier() {
		super(5);
		tank = new FluidTank(Fluids.NONE, 24000, 0);
	}

	@Override
	public String getName() {
		return "container.machineSolidifier";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(slots, 1, power, maxPower);
			tank.setType(4, slots);
			tank.updateTank(this);

			this.trySubscribe(worldObj, xCoord + 2, yCoord + 1, zCoord, Library.POS_X);
			this.trySubscribe(worldObj, xCoord - 2, yCoord + 1, zCoord, Library.NEG_X);
			this.trySubscribe(worldObj, xCoord, yCoord + 1, zCoord + 2, Library.POS_Z);
			this.trySubscribe(worldObj, xCoord, yCoord + 1, zCoord - 2, Library.NEG_Z);
			
			if(this.canProcess())
				this.process();
			else
				this.progress = 0;
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			this.networkPack(data, 50);
		}
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}
	
	public boolean canProcess() {
		
		if(this.power < usage)
			return false;
		
		Pair<Integer, ItemStack> out = SolidificationRecipes.getOutput(tank.getTankType());
		
		if(out == null)
			return false;
		
		int req = out.getKey();
		ItemStack stack = out.getValue();
		
		if(req > tank.getFill())
			return false;
		
		if(slots[0] != null) {
			
			if(slots[0].getItem() != stack.getItem())
				return false;
			
			if(slots[0].getItemDamage() != stack.getItemDamage())
				return false;
			
			if(slots[0].stackSize + stack.stackSize > slots[0].getMaxStackSize())
				return false;
		}
		
		return true;
	}
	
	public void process() {
		
		this.power -= usage;
		
		progress++;
		
		if(progress >= processTime) {
			
			Pair<Integer, ItemStack> out = SolidificationRecipes.getOutput(tank.getTankType());
			int req = out.getKey();
			ItemStack stack = out.getValue();
			tank.setFill(tank.getFill() - req);
			
			if(slots[0] == null) {
				slots[0] = stack.copy();
			} else {
				slots[0].stackSize += stack.stackSize;
			}
			
			progress = 0;
			
			this.markDirty();
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public void setPower(long power) {
		this.power = power;
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
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == tank.getTankType())
			tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> tanks = new ArrayList();
		tanks.add(tank);
		return tanks;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return tank.getTankType() == type ? tank.getFill() : 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return tank.getTankType() == type ? tank.getMaxFill() : 0;
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
					yCoord + 4,
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
