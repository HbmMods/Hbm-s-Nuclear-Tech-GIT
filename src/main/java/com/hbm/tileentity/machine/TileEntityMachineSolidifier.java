package com.hbm.tileentity.machine;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.LiquefactionRecipes;
import com.hbm.inventory.recipes.SolidificationRecipes;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineSolidifier extends TileEntityMachineBase implements IEnergyUser {

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
			tank.updateTank(this);

			this.trySubscribe(worldObj, xCoord + 2, yCoord + 1, zCoord, Library.POS_X);
			this.trySubscribe(worldObj, xCoord - 2, yCoord + 1, zCoord, Library.NEG_X);
			this.trySubscribe(worldObj, xCoord, yCoord + 1, zCoord + 2, Library.POS_Z);
			this.trySubscribe(worldObj, xCoord, yCoord + 1, zCoord - 2, Library.NEG_Z);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			this.networkPack(data, 50);
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
